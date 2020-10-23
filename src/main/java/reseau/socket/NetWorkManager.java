package reseau.socket;

import jeu.PacketHandlerTcp;
import jeu.PacketHandlerUdp;
import reseau.packet.Packet;
import reseau.tool.NetworkTool;
import reseau.tool.PtTool;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

/**
 * <h1> Permet de gerer la partie reseau </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class NetWorkManager {
    private final Map<String, Packet> udpPackets;
    private final Map<String, Packet> tcpPackets;

    private static final String PATHTOPACKET = "Ressources\\protocol_reseau\\packets";

    private UdpSocket udpSocket;
    private TcpServerSocket tcpServerSocket;
    private InetAddress address;

    private int tcpPort;
    private final PacketHandlerUdp packetHandlerUdp;
    private final PacketHandlerTcp packetHandlerTcp;
    private SideConnection sideConnection;

    //Temp
    public String partieId; //Numero de la partie
    public String nomPartie; //Nom de la partie
    public int partieIdi; //Numero de la partie
    public int nbjtotal; //Nombre de joueurs total
    public int nbjr; //Nombre de joueurs réel
    public int nbjv; //Nombre de joueurs virtuel
    public int nbjractuel = 0; //Nombre de joueurs réel actuellement connecté
    public int nbjvactuel = 0; //Nombre de joueurs virtuel actuellement connecté
    static int partiIdAttrib = 0;

    /**
     * @param core partie
     * @throws IOException si les fichiers pour le chargement des paquets sont inaccessible
     */
    public NetWorkManager(Object core) throws IOException {
        udpPackets = PtTool.loadPacket(PATHTOPACKET, "UDP");
        tcpPackets = PtTool.loadPacket(PATHTOPACKET, "TCP");
        this.packetHandlerUdp = new PacketHandlerUdp(this, core);
        this.packetHandlerTcp = new PacketHandlerTcp(this, core);
        this.partieId = "P" + partiIdAttrib;
        partiIdAttrib++;
    }

    /**
     * Permet d'initialiser la connexion
     *
     * @param sideConnection s'il s'agit du CLIENT ou du SERVER
     * @param address        l'adresse ip a utilisée
     * @throws IllegalArgumentException si les paquets n'ont pas ete initialisé correctement
     */
    public void initConnection(SideConnection sideConnection, InetAddress address) {
        this.sideConnection = sideConnection;
        tcpPort = NetworkTool.getPortSocket(1024, 64000);
        if (udpPackets.isEmpty() || tcpPackets.isEmpty())
            throw new IllegalArgumentException("Il n'y a pas de définitions pour les paquets TCP/UDP");

        this.address = address;
        udpSocket = new UdpSocket(this);
        tcpServerSocket = new TcpServerSocket(this);
        udpSocket.start(address);
        tcpServerSocket.start(address, tcpPort);
    }

    /**
     * Obtient le serveur UDP
     *
     * @return le serveur UDP
     */
    public UdpSocket getUdpSocket() {
        return udpSocket;
    }

    /**
     * Obtient le serveur TCP
     *
     * @return le serveur TCP
     */
    public TcpServerSocket getTcpServerSocket() {
        return tcpServerSocket;
    }

    /**
     * Permet d'obtenir l'ensemble des paquets UDP
     *
     * @return l'ensemble des paquets UDP
     */
    public Map<String, Packet> getPacketsUdp() {
        return udpPackets;
    }

    /**
     * Permet d'obtenir l'ensemble des paquets TCP
     *
     * @return l'ensemble des paquets TCP
     */
    public Map<String, Packet> getPacketsTcp() {
        return tcpPackets;
    }

    /**
     * Permet d'obtenir l'adresse ip utilisée
     *
     * @return l'adresse ip utilisée
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Obtient le numero de la partie sous forme chaine de caractere
     *
     * @return le numero de la partie sous forme chaine de caractere
     */
    public String getPartieId() {
        return partieId;
    }

    /**
     * Obtient le numero du port tcp
     *
     * @return le numero du port tcp
     */
    public int getTcpPort() {
        return tcpPort;
    }

    /**
     * Obtient le packet handler udp
     *
     * @return le packet handler udp
     */
    public PacketHandlerUdp getPacketHandlerUdp() {
        return packetHandlerUdp;
    }

    /**
     * Obtient le packet handler udp
     *
     * @return le packet handler udp
     */
    public PacketHandlerTcp getPacketHandlerTcp() {
        return packetHandlerTcp;
    }

    /**
     * Obtient le serveur ou un client
     *
     * @return si c'est un serveur ou un client
     */
    public SideConnection getSideConnection() {
        return sideConnection;
    }
}
