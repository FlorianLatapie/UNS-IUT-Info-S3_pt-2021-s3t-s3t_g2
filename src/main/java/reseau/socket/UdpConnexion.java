package reseau.socket;

import reseau.tool.OsOutils;
import reseau.tool.PtOutils;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Permet de gerer un serveur UDP</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class UdpConnexion implements Runnable, IEchangeSocket, IControleSocket {
	private static final int MULTICAST_PORT = 7777;
	private static final String MULTICAST_IP = "224.7.7.7";
	private static final int BUFFER_TAILLE = 496;
	private static final Charset ENCODAGE = StandardCharsets.UTF_8;
	private final Logger logger;

	private final InetAddress groupe;
	private final InetAddress monip;

	private MulticastSocket multicastSocket;
	private boolean estLancer;

	/**
	 * Initialise le serveur UDP.
	 *
	 * @param ip L'ip sur lequel le serveur demarre
	 * @exception UnknownHostException Si le cast de l'ip provoque une erreur
	 */
	public UdpConnexion(InetAddress ip) throws UnknownHostException {
		this.monip = ip;
		this.multicastSocket = null;
		this.groupe = InetAddress.getByName(MULTICAST_IP);
		this.estLancer = false;
		this.logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Demarre un serveur UDP.
	 *
	 * @exception IOException Si l'interface ou si rejoindre le groupe est
	 *                        impossible
	 */
	private void ouvrir() throws IOException {
		logger.log(Level.FINEST, "Ouverture multicast UDP");

		multicastSocket = new MulticastSocket(MULTICAST_PORT);
 
		multicastSocket.joinGroup(groupe);
		multicastSocket.setInterface(monip);
		System.out.println("[OK] " + ReseauOutils.getWindowsIp());
		estLancer = true;

		logger.log(Level.FINEST, "Multicast UDP ouvert");
	}

	/**
	 * Demarrage dans un nouveau thread.
	 */
	@Override
	public void run() {
		logger.finest("Démarrage du serveur TCP");
		logger.log(Level.FINEST, "Serveur TCP sur l'ip {0}", MULTICAST_IP);
		logger.log(Level.FINEST, "Serveur TCP sur le port {0}", MULTICAST_PORT);

		try {
			ouvrir();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (estLancer) {
			byte[] buffer = new byte[BUFFER_TAILLE];
			DatagramPacket message = new DatagramPacket(buffer, buffer.length);
			String received;
			try {
				multicastSocket.receive(message);
			} catch (IOException e) {
				break;
			}
			received = new String(message.getData(), 0, message.getLength(), ENCODAGE);
			reception(message, received);
		}

		if (estLancer)
			try {
				arreter();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Traitement du message.
	 *
	 * @param datagramPacket Info du paquet
	 * @param message        Message a traiter
	 */
	private void reception(DatagramPacket datagramPacket, String message) {
		logger.log(Level.INFO, "Message recu : {0}", message);
		ControleurReseau.traitementPaquetUdp(PtOutils.strToPacketUdp(message), message, datagramPacket);
	}

	/**
	 * Permet d'envoyer un paquet.
	 *
	 * @param message Le message a envoyer
	 */
	public void envoyer(String message) {
		logger.log(Level.FINEST, "Message envoyé : {0}", message);
		String uftMessage = new String(message.getBytes(), ENCODAGE);
		byte[] buffer = uftMessage.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, groupe, MULTICAST_PORT);

		ThreadOutils.asyncTask("fds", () -> {
			while (multicastSocket == null)
				;
			
			try {
				multicastSocket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Stop le serveur.
	 *
	 * @throws IOException Si le socket multicast n'arrive pas a se terminer
	 */
	public void arreter() throws IOException {
		logger.log(Level.INFO, "Arret du socket UDP");
		estLancer = false;

		if (multicastSocket != null)
			multicastSocket.close();

		logger.log(Level.INFO, "Socket UDP arreté");
	}

	/**
	 * Bloque l'execution du thread tant que le client n'est pas pret a recevoir.
	 */
	public void attendreConnexion() {
		while (multicastSocket == null)
			ThreadOutils.attendre();
		while (!isPret())
			ThreadOutils.attendre();
	}

	/**
	 * Permet de savoir le multicast udp est pret.
	 * 
	 * @return si le multicast udp est connecté
	 */
	public boolean isPret() {
		return multicastSocket.isConnected();
	}

	/**
	 * Permet de savoir le multicast udp est arreter.
	 * 
	 * @return si le multicast udp est arreter
	 */
	public boolean isArreter() {
		return !multicastSocket.isClosed();
	}
}
