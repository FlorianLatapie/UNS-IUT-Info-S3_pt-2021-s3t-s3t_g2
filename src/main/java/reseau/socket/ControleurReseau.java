package reseau.socket;

import reseau.packet.Packet;
import reseau.tool.PtOutils;
import reseau.tool.ReseauOutils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <h1>Permet de gerer la partie reseau</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class ControleurReseau {
	private final Map<String, Packet> udpPaquets;
	private final Map<String, Packet> tcpPaquets;

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
		logger.finest("Initialisation");
		this.traitementPaquetUdp.init(this);
		this.traitementPaquetTcp.init(this);
		this.connexionType = connexionType;
		this.ip = ip;
		this.tcpPort = ReseauOutils.getPortSocket(1024, 64000);

		if (udpPaquets.isEmpty() || tcpPaquets.isEmpty())
			throw new IllegalArgumentException("Il n'y a pas de définitions pour les paquets TCP/UDP");
		logger.info(MessageFormat.format("Mon port est {0}", tcpPort));

		new Thread(udpConnexion = new UdpConnexion(this, ip), "udpConnexion").start();

		if (connexionType == ConnexionType.SERVER) {
			new Thread(tcpServeur = new TcpServeur(this, tcpPort), "tcpServeur").start();
		} else {
			new Thread(tcpClient = new TcpClient(this, ip, tcpPort), "tcpClient").start();
		}
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
				logger.severe(MessageFormat.format("Impossible d'arreter le serveur TCP\n{0}", e));
			}

		if (tcpClient != null)
			try {
				tcpClient.arreter();
			} catch (IOException e) {
				logger.severe(MessageFormat.format("Impossible d'arreter le client TCP\n{0}", e));
			}

		if (udpConnexion != null)
			try {
				udpConnexion.arreter();
			} catch (IOException e) {
				logger.severe(MessageFormat.format("Impossible d'arreter le serveur UDP\n{0}", e));
			}
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
	 * Permet de savoir si la clé Tcp existe.
	 *
	 * @return Si la clé existe
	 */
	public boolean isCleExisteTcp(String cle) {
		try {
			tcpPaquets.get(cle);
		} catch (NullPointerException ignored) {
			return false;
		}

		return true;
	}

	/**
	 * Permet de savoir si la clé Udp existe.
	 *
	 * @return Si la clé existe
	 */
	public boolean isCleExisteUdp(String cle) {
		try {
			udpPaquets.get(cle);
		} catch (NullPointerException ignored) {
			return false;
		}

		return true;
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets TCP.
	 *
	 * @return L'ensemble des paquets TCP
	 */
	public String construirePaquetTcp(String cle, Object... args) {
		if (!isCleExisteTcp(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpPaquets.get(cle).build(args);
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets TCP.
	 *
	 * @return L'ensemble des paquets TCP
	 */
	public Object getValueTcp(String cle, String message, int val) {
		if (!isCleExisteTcp(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpPaquets.get(cle).getValue(message, val);
	}

	/**
	 * Permet d'obtenir le paquet TCP.
	 *
	 * @param cle La clé du paquet cible
	 * @return Le paquet TCP cible
	 */
	public Packet getPaquetTcp(String cle) {
		if (!isCleExisteTcp(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé TCP !");

		return tcpPaquets.get(cle);
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
	 * Permet d'obtenir l'ensemble des paquets UDP.
	 *
	 * @return L'ensemble des paquets UDP
	 */
	public String construirePaquetUdp(String cle, Object... args) {
		if (!isCleExisteUdp(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé UDP !");

		return udpPaquets.get(cle).build(args);
	}

	/**
	 * Permet d'obtenir l'ensemble des paquets UDP.
	 *
	 * @return L'ensemble des paquets UDP
	 */
	public Object getValueUdp(String cle, String message, int val) {
		if (!isCleExisteUdp(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé UDP !");

		return udpPaquets.get(cle).getValue(message, val);
	}

	/**
	 * Permet d'obtenir le paquet UDP.
	 *
	 * @param cle La clé du paquet cible
	 * @return Le paquet UDP cible
	 */
	public Packet getPaquetUdp(String cle) {
		if (!isCleExisteUdp(cle))
			throw new IllegalArgumentException("Ce n'est pas une clé UDP !");

		return udpPaquets.get(cle);
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
	 * Obtient le client TCP.
	 *
	 * @return Le client TCP
	 */
	public TcpClient getTcpClient() {
		return tcpClient;
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
	 * Obtient le packet handler udp.
	 *
	 * @return Le packet handler udp
	 */
	public TraitementPaquet getTraitementPaquetUdp() {
		return traitementPaquetUdp;
	}

	/**
	 * Obtient le packet handler udp.
	 *
	 * @return Le packet handler udp
	 */
	public TraitementPaquet getTraitementPaquetTcp() {
		return traitementPaquetTcp;
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
