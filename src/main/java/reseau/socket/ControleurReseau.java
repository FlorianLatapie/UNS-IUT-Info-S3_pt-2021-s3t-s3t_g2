package reseau.socket;

import reseau.packet.Packet;
import reseau.tool.PtOutils;
import reseau.tool.ReseauOutils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static java.lang.System.out;

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

    /**
     * @param traitementPaquetTcp Le traiteur de paquet du coté TCP
     * @param traitementPaquetUdp Le traiteur de paquet du coté UDP
     * @throws IOException si les fichiers pour le chargement des paquets sont
     *                     inaccessible
     */
    public ControleurReseau(TraitementPaquet traitementPaquetTcp, TraitementPaquet traitementPaquetUdp) throws IOException {
        this.udpPaquets = PtOutils.loadPacket(CHEMIN_PACKET, "UDP");
        this.tcpPaquets = PtOutils.loadPacket(CHEMIN_PACKET, "TCP");
        this.traitementPaquetUdp = traitementPaquetUdp;
        this.traitementPaquetTcp = traitementPaquetTcp;
    }

    /**
     * Permet d'initialiser la connexion.
     *
     * @param connexionType S'il s'agit du CLIENT ou du SERVER
     * @param ip            L'adresse ip a utilisée
     * @throws IllegalArgumentException Si les paquets n'ont pas ete initialisé correctement
     * @throws UnknownHostException     Si le cast de l'ip provoque une erreur
     */
    public void initConnexion(ConnexionType connexionType, InetAddress ip) throws UnknownHostException {
        this.traitementPaquetUdp.init(this);
        this.traitementPaquetTcp.init(this);
        this.connexionType = connexionType;
        this.ip = ip;
        this.tcpPort = ReseauOutils.getPortSocket(1024, 64000);

        if (udpPaquets.isEmpty() || tcpPaquets.isEmpty())
            throw new IllegalArgumentException("Il n'y a pas de définitions pour les paquets TCP/UDP");
        out.println("Mon port est " + tcpPort);

        new Thread(udpConnexion = new UdpConnexion(this, ip)).start();

        if (connexionType == ConnexionType.SERVER) {
            new Thread(tcpServeur = new TcpServeur(this, tcpPort)).start();
        } else {
            new Thread(tcpClient = new TcpClient(this, ip, tcpPort)).start();
        }
    }

    /**
     * Arrete les serveurs.
     */
    public void arreter() {
        if (tcpServeur != null)
            try {
                tcpServeur.arreter();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if (tcpClient != null)
            try {
                tcpClient.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Obtient le serveur UDP.
     *
     * @return Le serveur UDP
     */
    public UdpConnexion getUdpConnexion() {
        return udpConnexion;
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
     * Permet d'obtenir l'ensemble des paquets UDP.
     *
     * @return L'ensemble des paquets UDP
     */
    public Map<String, Packet> getPacketsUdp() {
        return udpPaquets;
    }

    /**
     * Permet d'obtenir l'ensemble des paquets TCP.
     *
     * @return L'ensemble des paquets TCP
     */
    public Map<String, Packet> getPacketsTcp() {
        return tcpPaquets;
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
