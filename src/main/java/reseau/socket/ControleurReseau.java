package reseau.socket;

import reseau.paquet.Paquet;
import reseau.tool.PtOutils;
import reseau.tool.ReseauOutils;
import reseau.type.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Permet de gerer la partie reseau</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class ControleurReseau implements IControleSocket {
	private final Map<String, Paquet> udpPaquets;
	private final Map<String, Paquet> tcpPaquets;

	private static final String CHEMIN_PACKET = "Ressources/protocol_reseau/packets";

	private UdpConnexion udpConnexion;
	private TcpServeur tcpServeur;
	private TcpClient tcpClient;
	private InetAddress ip;

	private int tcpPort;
	private final TraitementPaquet traitementPaquetUdp;
	private final TraitementPaquet traitementPaquetTcp;
	private ConnexionType connexionType;

	private Logger logger;

	/**
	 * @param traitementPaquetTcp Le traiteur de paquet du coté TCP
	 * @param traitementPaquetUdp Le traiteur de paquet du coté UDP
	 * @throws IOException si les fichiers pour le chargement des paquets sont
	 *                     inaccessible
	 */
	public ControleurReseau(TraitementPaquet traitementPaquetTcp, TraitementPaquet traitementPaquetUdp)
			throws IOException {
		this.udpPaquets = PtOutils.loadPacket(CHEMIN_PACKET, "UDP");
		this.tcpPaquets = PtOutils.loadPacket(CHEMIN_PACKET, "TCP");
		this.traitementPaquetUdp = traitementPaquetUdp;
		this.traitementPaquetTcp = traitementPaquetTcp;
		this.logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Permet d'initialiser la connexion.
	 *
	 * @param connexionType S'il s'agit du CLIENT ou du SERVER
	 * @param ip            L'adresse ip a utilisée
	 * @throws IllegalArgumentException Si les paquets n'ont pas ete initialisé
	 *                                  correctement
	 * @throws UnknownHostException     Si le cast de l'ip provoque une erreur
	 */
	public void initConnexion(ConnexionType connexionType, InetAddress ip) throws UnknownHostException {
		logger.finest("Initialisation du controleur réseau");
		this.traitementPaquetUdp.init(this);
		this.traitementPaquetTcp.init(this);
		this.connexionType = connexionType;
		this.ip = ip;
		this.tcpPort = ReseauOutils.getPortSocket(1024, 65535);

		if (udpPaquets.isEmpty() || tcpPaquets.isEmpty())
			throw new IllegalArgumentException("Il n'y a pas de définitions pour les paquets TCP/UDP");
		logger.log(Level.INFO, "Mon port est {0}", tcpPort);
		new Thread(udpConnexion = new UdpConnexion(this, ip), "udpConnexion").start();

		if (connexionType == ConnexionType.SERVEUR) {
			new Thread(tcpServeur = new TcpServeur(this, tcpPort), "tcpServeur").start();
		} else {
			new Thread(tcpClient = new TcpClient(this, ip, tcpPort), "tcpClient").start();
		}
		logger.info("Controleur initialisé");
	}

	/**
	 * Arrete les serveurs.
	 */
	public void arreter() {
		logger.finest("Arret du serveur UDP et du client TCP");
		if (tcpServeur != null)
			try {
				tcpServeur.arreter();
			} catch (IOException e) {
				e.printStackTrace();
			}

		if (udpConnexion != null)
			try {
				udpConnexion.arreter();
			} catch (IOException e) {
				e.printStackTrace();
			}

		logger.log(Level.INFO, "Serveur UDP et TCP arreté");
	}

	/**
	 * Envoyer un message UDP
	 *
	 * @param message Le message a envoyer
	 */
	public void envoyerUdp(String message) {
		udpConnexion.envoyer(message);
	}

	/**
	 * Envoyer un message TCP
	 *
	 * @param message Le message a envoyer
	 */
	public void envoyerTcp(String message) {
		tcpClient.envoyer(message);
	}

	/**
	 * Arreter le serveur UDP
	 *
	 * @throws IOException Si le serveur ne peut pas etre arreté
	 */
	public void arreterUdp() throws IOException {
		if (udpConnexion == null)
			return;

		udpConnexion.arreter();
	}

	/**
	 * Permet de savoir si la clé existe.
	 *
	 * @return Si la clé existe
	 */
	public boolean isCleExiste(String cle) {
		try {
			udpPaquets.get(cle);
		} catch (NullPointerException ignored) {
			return false;
		}

		return true;
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets UDP.
	 *
	 * @return L'ensemble des paquets UDP
	 */
	public String construirePaquetUdp(String cle, Object... args) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé UDP !");

		return udpPaquets.get(cle).construire(args);
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets TCP.
	 *
	 * @return L'ensemble des paquets TCP
	 */
	public String construirePaquetTcp(String cle, Object... args) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpPaquets.get(cle).construire(args);
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets UDP.
	 *
	 * @return L'ensemble des paquets UDP
	 */
	public Object getValueUdp(String cle, String message, int val) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé UDP !");

		return udpPaquets.get(cle).getValeur(message, val);
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets TCP.
	 *
	 * @return L'ensemble des paquets TCP
	 */
	public Object getValueTcp(String cle, String message, int val) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpPaquets.get(cle).getValeur(message, val);
	}

	/**
	 * Permet d'obtenir le paquet UDP.
	 *
	 * @param cle La clé du paquet cible
	 * @return Le paquet UDP cible
	 */
	public Paquet getPaquetUdp(String cle) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé UDP !");

		return udpPaquets.get(cle);
	}

	/**
	 * Permet d'obtenir le paquet TCP.
	 *
	 * @param cle La clé du paquet cible
	 * @return Le paquet TCP cible
	 */
	public Paquet getPaquetTcp(String cle) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpPaquets.get(cle);
	}

	/**
	 * Permet d'attendre un paquet TCP.
	 *
	 * @param cle La clé du paquet cible
	 */
	public void attendreTcp(String cle) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		tcpClient.attendreMessage(cle);
	}
	
	/**
	 * Permet de recevoir un paquet TCP.
	 *
	 * @param cle La clé du paquet cible
	 * @return le message du paquet
	 */
	public String getMessageTcp(String cle) {
		if (!isCleExiste(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpClient.getMessage(cle);
	}

	/**
	 * Permet le nombre de paquet charger.
	 *
	 * @return Le nombre de paquet charger
	 */
	public int getPaquetUdpCount() {
		return udpPaquets.size();
	}

	/**
	 * Permet le nombre de paquet charger.
	 *
	 * @return Le nombre de paquet charger
	 */
	public int getPaquetTcpCount() {
		return tcpPaquets.size();
	}

	/**
	 * Obtient le serveur TCP.
	 *
	 * @return Le serveur TCP
	 */
	public TcpServeur getTcpServeur() {
		return tcpServeur;
	}

	/**
	 * Permet d'obtenir l'adresse ip utilisée.
	 *
	 * @return L'adresse ip utilisée
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * Obtient le numero du port tcp.
	 *
	 * @return Le numero du port tcp
	 */
	public int getTcpPort() {
		return tcpPort;
	}

	/**
	 * Permet de traiter un paquet UDP.
	 *
	 * @param packet  Le paquet recu encapsulé
	 * @param message Le paquet brute
	 * @param extra   Le datagram du paquet
	 */
	public void traitementPaquetUdp(Paquet packet, String message, java.net.DatagramPacket extra) {
		traitementPaquetUdp.traitement(packet, message, extra);
	}

	/**
	 * Permet de traiter un paquet TCP.
	 *
	 * @param packet  Le paquet recu encapsulé
	 * @param message Le paquet brute
	 * @param extra   Le socket du paquet
	 */
	public void traitementPaquetTcp(Paquet packet, String message, 	TcpClient extra) {
		traitementPaquetTcp.traitement(packet, message, extra);
	}

	/**
	 * Obtient le serveur ou un client.
	 *
	 * @return Si c'est un serveur ou un client
	 */
	public ConnexionType getConnexionType() {
		return connexionType;
	}
}
