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

//TODO TO DELETE

/**
 * <h1>Permet de gerer les connections du serveur individuellement</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class Connexion implements Runnable, IEchangeSocket, IControleSocket, IMessagePaquet {
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
		fluxEntre = new DataInputStream(new BufferedInputStream(socketCourant.getInputStream()));
		fluxSortie = new DataOutputStream(new BufferedOutputStream(socketCourant.getOutputStream()));
	}

	/**
	 * Démarre la connexion dans un thread.
	 */
	@Override
	public void run() {
		logger.finest("Démarrage du client TCP");
		logger.log(Level.FINEST, "Client TCP sur l'ip {0}", socketCourant.getInetAddress());
		logger.log(Level.FINEST, "Client TCP sur le port {1}", socketCourant.getLocalPort());
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
		logger.finest("Flux fermé");
	}

	/**
	 * Permet de fermer la connexion.
	 *
	 * @throws IOException Si les flux ou le socket n'a pas reussi a se terminer
	 * @throws IOException Si le socket ne peut pas etre fermé
	 */
	public void arreter() throws IOException {
		logger.finest("Arret du serveur TCP");
		fermer();
		estLancer = false;

		if (socketCourant != null) {
			socketCourant.shutdownInput();
			socketCourant.shutdownOutput();
			socketCourant.close();
		}
		logger.finest("Serveur TCP arreter");
	}

	/**
	 * Permet d'envoyer un message a un client.
	 *
	 * @param message Le message a envoyer
	 */
	public void envoyer(String message) {
		logger.log(Level.INFO, "Envoi d un message : {0}", message);
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
		logger.info("Reception des messages TCP actif");
		while (estLancer) {
			String message = "";
			try {
				message = fluxEntre.readUTF();
			} catch (IOException e) {
				logger.severe(MessageFormat.format("Erreur sur la lecture du message !\n{0}", e));
				break;
			}
			logger.log(Level.INFO, "Reception d un message : {0}", message);
			String cle = PaquetOutils.getCleMessage(message);
			controleurReseau.traitementPaquetTcp(controleurReseau.getPaquetTcp(cle), message, this);
			messagesTampon.add(message);
		}
	}

	/**
	 * Permet de rajouter un message au buffer
	 *
	 * @param message Le message a ajouter
	 */
	public void ajouterMessage(String message) {
		logger.log(Level.FINEST, "Ajouts d un nouveau message dans la mémoire : {0}", message);
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
		logger.log(Level.FINEST, "Attente d'un message : [{0}]", cle);
		while (!contient(cle))
			Thread.yield();
	}
}
