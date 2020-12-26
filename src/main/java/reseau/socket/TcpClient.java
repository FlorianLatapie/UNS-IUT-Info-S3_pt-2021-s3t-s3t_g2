package reseau.socket;

import reseau.tool.PaquetOutils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.synchronizedList;

/**
 * <h1>Permet de gerer un serveur TCP du coté client</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class TcpClient implements Runnable, IEchangeSocket, IMessagePaquet {
	private Socket socket;
	private DataOutputStream fluxSortie;
	private DataInputStream fluxEntre;
	private InetAddress ip;

	private List<String> paquetRecuEnvoyeList;
	private List<String> paquetRecuList;
	private List<String> paquetEnvoyeList;

	private final int port;
	private String cleFin;

	private boolean estLancer;
	private final List<String> messagesTampon;

	private final Logger logger;

	/**
	 * @param ip               L'ip du serveur TCP cible
	 * @param port             Le port du serveur TCP cible
	 * @param cleFin           Mot cle pour arreter le client
	 */
	public TcpClient(InetAddress ip, int port, String cleFin) {
		this.ip = ip;
		this.cleFin = cleFin;
		this.messagesTampon = synchronizedList(new ArrayList<String>());
		this.estLancer = true;
		this.port = port;
		this.logger = Logger.getLogger(getClass().getName());
		this.paquetRecuEnvoyeList = new ArrayList<>();
		this.paquetRecuList = new ArrayList<>();
		this.paquetEnvoyeList = new ArrayList<>();
	}

	/**
	 * @param socket           L'ip du serveur TCP cible
	 */
	public TcpClient(Socket socket) {
		this.messagesTampon = synchronizedList(new ArrayList<String>());
		this.estLancer = true;
		this.ip = ControleurReseau.getIp();
		this.port = ControleurReseau.getTcpPort();
		this.logger = Logger.getLogger(getClass().getName());
		this.socket = socket;
		this.paquetRecuEnvoyeList = new ArrayList<>();
		this.paquetRecuList = new ArrayList<>();
		this.paquetEnvoyeList = new ArrayList<>();
	}

	/**
	 * Permet d'ouvrir la connexion.
	 *
	 * @return Si la connexion a été ouverte corretement
	 * @throws IOException Si les flux de s'ouvre pas correctement
	 */
	private boolean ouvrir(InetAddress ip, int port) throws IOException {
		logger.log(Level.INFO, "Client TCP ouvert");
		boolean attendre = true;
		while (attendre)
			try {
				socket = new Socket(ip, port);
				attendre = false;
			} catch (Exception ignoreda) {
				ignoreda.printStackTrace();
				Thread.yield();
			}
		
		fluxSortie = new DataOutputStream(socket.getOutputStream());
		fluxEntre = new DataInputStream(socket.getInputStream());

		reception();

		return true;
	}

	private boolean ouvrir() throws IOException {
		logger.log(Level.INFO, "Serveur TCP ouvert");

		fluxSortie = new DataOutputStream(socket.getOutputStream());
		fluxEntre = new DataInputStream(socket.getInputStream());

		reception();

		return true;
	}

	@Override
	public void run() {
		logger.finest("Démarrage du client TCP");
		logger.log(Level.FINEST, "Client TCP sur l'ip {0}", ip);
		logger.log(Level.FINEST, "Client TCP sur le port {1}", port);
		try {
			if (socket == null)
				ouvrir(ip, port);
			else
				ouvrir();
		} catch (IOException e) {
			e.printStackTrace();
		}

		reception();
		try {
			arreter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'envoyer un message au serveur.
	 *
	 * @param message Le message a a envoyer
	 */
	public void envoyer(String message) {
		attendreConnexion();
		logger.log(Level.INFO, "Envoie d'un message : " + message);
		
		try {
			fluxSortie.writeUTF(message);
			fluxSortie.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.paquetEnvoyeList.add(message);
		this.paquetRecuEnvoyeList.add(message);
	}

	/**
	 * Permet de recevoir des messages du client. Bloque l'execution de thread.
	 */
	private void reception() {
		logger.log(Level.INFO, "La reception des messages est actif");
		String message;
		while (estLancer) {
			try {
				message = fluxEntre.readUTF();
			} catch (Exception e) {
				break;
			}

			logger.log(Level.INFO, "Reception de " + message);
			String cle = PaquetOutils.getCleMessage(message);

			ControleurReseau.traitementPaquetTcp(ControleurReseau.getPaquetTcp(cle), message, this);
			messagesTampon.add(message);
			this.paquetRecuList.add(message);
			this.paquetRecuEnvoyeList.add(message);
			
			if (cleFin != null)
				if (cleFin.equals(cle))
					break;
		}

		try {
			arreter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Arrete le serveur.
	 *
	 * @throws IOException Si les flux ou si le socket ne se ferme pas correctement
	 */
	public void arreter() throws IOException {
		logger.log(Level.INFO, "Arret du client TCP");
		estLancer = false;

		if (socket != null) {
			socket.close();
		}
		logger.log(Level.INFO, "Client TCP fermé");
	}

	/**
	 * Permet d'ajouter un message dans la mémoire tampon.
	 *
	 * @param message Le message a ajouter
	 */
	public void ajouterMessage(String message) {
		logger.log(Level.INFO, "Ajout d'un message dans la mémoire tampon");
		messagesTampon.add(message);
	}

	/**
	 * Permet d'obtenir un message depuis la mémoire tampon
	 *
	 * @param cle La cle du message
	 * @return Le message du mot-clé correspondant
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
	 * Permet de savoir si le message est contenu dans la mémoire tampon.
	 *
	 * @param cle La clé du message cible
	 * @return Si le message est contenu
	 */
	private boolean contient(String cle) {
		List<String> tmpList = new ArrayList<>(messagesTampon);
		for (String message : tmpList)
			if (PaquetOutils.getCleMessage(message).equals(cle))
				return true;

		return false;
	}

	/**
	 * Bloque l'execution du thread tant que le message n'a pas été recu.
	 *
	 * @param cle Mot-clé du message
	 */
	public void attendreMessage(String cle) {
		while (!contient(cle))
			Thread.yield();
	}

	/**
	 * Bloque l'execution du thread tant que le client n'est pas pret a recevoir.
	 */
	public void attendreConnexion() {
		while (socket == null)
			Thread.yield();
		while (!isPret())
			Thread.yield();
	}

	/**
	 * Permet de savoir le client tcp est pret.
	 * 
	 * @return si le client tcp est connecté
	 */
	public boolean isPret() {
		return socket.isConnected();
	}

	/**
	 * Permet de savoir le client tcp est arreter.
	 * 
	 * @return si le client tcp est arreté
	 */
	public boolean isArreter() {
		return !socket.isClosed();
	}

	public List<String> getPaquetRecuEnvoyeList() {
		return paquetRecuEnvoyeList;
	}

	public List<String> getPaquetRecuList() {
		return paquetRecuList;
	}

	public List<String> getPaquetEnvoyeList() {
		return paquetEnvoyeList;
	}
}
