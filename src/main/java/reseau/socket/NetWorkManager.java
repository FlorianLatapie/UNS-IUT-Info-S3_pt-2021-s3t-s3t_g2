package reseau.socket;

import reseau.packet.Packet;
import reseau.tool.NetworkTool;
import reseau.tool.PtTool;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import jeu.PacketHandlerTcp;
import jeu.PacketHandlerUdp;

/**
 * <h1> Permet de gerer la partie reseau </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class NetWorkManager {
    private final Map<String, Packet> udpPackets;
    private final Map<String, Packet> tcpPackets;

    private static final String PATHTOPACKET = "Ressources/protocol_reseau/packets";

    private UdpSocket udpSocket;
    private TcpServerSocket tcpServerSocket;
    private InetAddress address;

    private int tcpPort;
    private final PacketHandlerUdp packetHandlerUdp;
    private final PacketHandlerTcp packetHandlerTcp;
    private SideConnection sideConnection;

    public Socket socketTCPSocket;

    /**
     * @param core partie
     * @throws IOException si les fichiers pour le chargement des paquets sont inaccessible
     */
    public NetWorkManager(Object core) throws IOException {
        udpPackets = PtTool.loadPacket(PATHTOPACKET, "UDP");
        tcpPackets = PtTool.loadPacket(PATHTOPACKET, "TCP");
        this.packetHandlerUdp = new PacketHandlerUdp(this, core);
        this.packetHandlerTcp = new PacketHandlerTcp(this, core);
    }

    /**
     * Permet d'initialiser la connexion
     *
     * @param sideConnection s'il s'agit du CLIENT ou du SERVER
     * @param address        l'adresse ip a utilisée
     * @throws IOException              si il ne peut pas charger les fichiers du protocole réseau
     * @throws IllegalArgumentException si les paquets n'ont pas ete initialisé correctement
     */
    public void initConnection(SideConnection sideConnection, InetAddress address) throws IOException {
        this.sideConnection = sideConnection;
        tcpPort = NetworkTool.getPortSocket(1024, 64000);
        if (udpPackets.isEmpty() || tcpPackets.isEmpty())
            throw new IllegalArgumentException("Il n'y a pas de définitions pour les paquets TCP/UDP");
        System.out.println("Mon port est " + tcpPort);
        if (sideConnection == SideConnection.SERVER) {
            this.address = address;
            udpSocket = new UdpSocket(this);
            tcpServerSocket = new TcpServerSocket(this);
            udpSocket.start(address);
            tcpServerSocket.start(address, tcpPort);
        } else {
            this.address = address;
            socketTCPSocket = new Socket();
            socketTCPSocket.bind(new InetSocketAddress("localhost", tcpPort));
            udpSocket = new UdpSocket(this);
            tcpServerSocket = new TcpServerSocket(this);
            udpSocket.start(address);
            //tcpServerSocket.start(address, tcpPort);
        }
    }

    public void stopBind() {
        if (socketTCPSocket == null)
            return;

        try {
            socketTCPSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServerTCP() {
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
