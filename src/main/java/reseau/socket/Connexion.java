package reseau.socket;

import reseau.tool.PaquetOutils;

import java.io.*;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Permet de gerer les connections du serveur individuellement</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class Connexion implements Runnable {
	private final ControleurReseau controleurReseau;
	private final Socket socketCourant;
	private DataInputStream fluxEntre;
	private DataOutputStream fluxSortie;

	private boolean estLancer;
	private final List<String> messagesTampon;

	private final Logger logger;

	/**
	 * @param socket           Socket a gerer
	 * @param controleurReseau Le controleur reseau depuis le socket est issue
	 */
	public Connexion(Socket socket, ControleurReseau controleurReseau) {
		this.estLancer = true;
		this.socketCourant = socket;
		this.controleurReseau = controleurReseau;
		this.messagesTampon = Collections.synchronizedList(new ArrayList<>());
		this.logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Permet d'ouvrir la connexion.
	 *
	 * @throws IOException Si les flux n'arrive a s'ouvrir
	 */
	private void ouvrir() throws IOException {
		logger.finest("Ouverture des flux d'entré et sortie");
		fluxEntre = new DataInputStream(new BufferedInputStream(socketCourant.getInputStream()));
		fluxSortie = new DataOutputStream(new BufferedOutputStream(socketCourant.getOutputStream()));
	}

	/**
	 * Démarre la connexion dans un thread.
	 */
	@Override
	public void run() {
		try {
			ouvrir();
			reception();
			if (estLancer)
				arreter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de fermer la connexion.
	 *
	 * @throws IOException Si les flux ou le socket n'a pas reussi a se terminer
	 */
	private void fermer() throws IOException {
		logger.finest("Fermeture des flux d'entré et sortie ainsi que du socket");
		estLancer = false;
		if (socketCourant != null) {
			socketCourant.shutdownInput();
			socketCourant.shutdownOutput();
			socketCourant.close();
		}
	}

	/**
	 * Permet de fermer la connexion.
	 *
	 * @throws IOException          Si les flux ou le socket n'a pas reussi a se
	 *                              terminer
	 * @throws InterruptedException
	 */
	public void arreter() throws IOException {
		estLancer = false;

		if (socketCourant != null) {
			System.out.println("TCP SERVEUR CLIENT §§§§§§");
			socketCourant.shutdownInput();
			socketCourant.shutdownOutput();
			socketCourant.close();
		}
	}

	/**
	 * Permet d'envoyer un message a un client.
	 *
	 * @param message Le message a envoyer
	 */
	public void envoyer(String message) {
		logger.info(MessageFormat.format("Envoi d un message : {0}", message));
		try {
			fluxSortie.writeUTF(message);
			fluxSortie.flush();
		} catch (IOException e) {
			logger.severe(MessageFormat.format("Erreur sur l écriture du message ou du flush !\n{0}", e));
		}
	}

	/**
	 * Permet de recevoir des messages. Bloque l'execution du thread.
	 */
	private void reception() {
		logger.info("Lancement de la reception d'un message");
		while (estLancer) {
			String message = "";
			try {
				message = fluxEntre.readUTF();
			} catch (IOException e) {
				logger.severe(MessageFormat.format("Erreur sur la lecture du message !\n{0}", e));
				break;
			}
			logger.info(MessageFormat.format("Reception d un message : {0}", message));
			String cle = PaquetOutils.getCleMessage(message);
			controleurReseau.getTraitementPaquetTcp().traitement(controleurReseau.getPaquetTcp(cle), message, this);
			messagesTampon.add(message);
		}
	}

	/**
	 * Permet de rajouter un message au buffer
	 *
	 * @param message Le message a ajouter
	 */
	public void ajouterMessage(String message) {
		logger.finest(MessageFormat.format("Ajouts d un nouveau message : {0}", message));
		messagesTampon.add(message);
	}

	/**
	 * Permet d'obtenir la premiere occurrence du message du mot-clé correspond.
	 *
	 * @param cle Le mot-clé du message a obtenir
	 * @return Le message du mot-clé correspond
	 */
	public String getMessage(String cle) {
		for (String message : messagesTampon)
			if (PaquetOutils.getCleMessage(message).equals(cle)) {
				messagesTampon.remove(message);
				return message;
			}

		return null;
	}

	/**
	 * Permet de savoir si un type de message est contenu dans la mémoire tampon. Le
	 * type du message est identifié par une clé.
	 *
	 * @param cle Mot-clé du message
	 * @return Si le mot clé est contenu dans la mémoire tampon
	 */
	private boolean contient(String cle) {
		synchronized (messagesTampon) {
			for (String message : messagesTampon)
				if (PaquetOutils.getCleMessage(message).equals(cle))
					return true;
		}

		return false;
	}

	/**
	 * Bloque l'execution du thread tant que le message n'a pas été recu.
	 *
	 * @param cle Mot-clé du message
	 */
	public void attendreMessage(String cle) {
		logger.finest(MessageFormat.format("Attente d'un message : [{0}]", cle));
		while (!contient(cle))
			;
	}
}
