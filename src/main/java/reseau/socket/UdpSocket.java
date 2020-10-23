package reseau.socket;

import reseau.tool.PtTool;
import reseau.tool.ThreadTool;
import reseau.type.ServerState;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1> Serveur UDP </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class UdpSocket {
    private static final int MULTICAST_PORT = 7777;
    private static final String MULTICAST_IP = "224.7.7.7";
    private static final int BUFFER_SIZE = 496;
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final Logger logger = Logger.getLogger(UdpSocket.class.getName());

    private MulticastSocket multicastSocket;
    private InetAddress group;
    private boolean enableLoop;
    private Thread udpThread;
    private ServerState serverState;
    private final NetWorkManager netWorkManager;
    InetAddress myIp;

    /**
     * Initialise le serveur UDP
     *
     * @param netWorkManager le gestionnaire réseau
     */
    public UdpSocket(NetWorkManager netWorkManager) {
        this.netWorkManager = netWorkManager;
        this.serverState = ServerState.STOPPED;
        this.multicastSocket = null;
        try {
            this.group = InetAddress.getByName(MULTICAST_IP);
            logger.finest("Multicast on ip " + MULTICAST_IP);
        } catch (UnknownHostException e) {
            logger.severe("Multicast ip error on " + MULTICAST_IP + " : " + e.getMessage());
            System.exit(1);
        }
        this.enableLoop = false;
        this.udpThread = null;
    }

    /**
     * Demarre un serveur UDP
     *
     * @param ip c'est l'ip sur lequel le serveur demarre
     */
    public void start(InetAddress ip) {
        this.myIp = ip;
        if (serverState != ServerState.STOPPED) {
            logger.log(Level.WARNING, "Cannot start the server : {0}", serverState);
            return;
        }

        serverState = ServerState.INIT;
        try {

            multicastSocket = new MulticastSocket(MULTICAST_PORT);
            multicastSocket.setInterface(myIp);
            logger.finest("Multicast on port " + MULTICAST_PORT);
        } catch (IOException e) {
            logger.severe("Multicast error on port " + MULTICAST_PORT);
            e.printStackTrace();
            System.exit(1);
        }
        try {
            multicastSocket.joinGroup(group);
            logger.finest("Multicast on ip " + MULTICAST_IP);
        } catch (IOException e) {
            logger.severe("Multicast ip error on " + MULTICAST_IP + " : " + e.getMessage());
            System.exit(1);
        }
        enableLoop = true;

        serverState = ServerState.STARTED;
        udpThread = ThreadTool.asyncTask(this::startAsync);
        logger.finest("Multicast on thread " + udpThread.getId());
    }

    /**
     * Demarrage de facon asynchrone
     */
    private void startAsync() {
        if (serverState != ServerState.STARTED) {
            logger.log(Level.WARNING, "Cannot start the server : {0}", serverState);
            return;
        }

        logger.info("Server started ! ");
        while (enableLoop) {
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket message = new DatagramPacket(buffer, buffer.length);
            String received;
            try {
                multicastSocket.receive(message);
                received = new String(
                        message.getData(), 0, message.getLength(), ENCODING);
                process(message, received);
            } catch (IOException e) {
                logger.warning("Cannot received reseau.packet : " + e.getMessage());
            }
        }

        stop();
    }

    /**
     * Traitement du message
     *
     * @param datagramPacket info du paquet
     * @param message        message a traite
     * @return renvoie une reponse
     */
    private void process(DatagramPacket datagramPacket, String message) {
        logger.log(Level.INFO, "Packet received {0}", message);
        netWorkManager.getPacketHandlerUdp().traitement(PtTool.strToPacketUdp(message, netWorkManager), message);
    }

    /**
     * Permet d'envoyer un paquet
     *
     * @param message le message a envoyer
     */
    public void sendPacket(String message) {
        if (serverState != ServerState.STARTED) {
            logger.log(Level.WARNING, "Cannot send reseau.packet : {0}", serverState);
            return;
        }
        logger.log(Level.FINEST, "Message sent {0}", message);
        String uftMessage = new String(message.getBytes(), ENCODING);
        byte[] buffer = uftMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
        try {
            multicastSocket.send(packet);
        } catch (IOException e) {
            logger.warning("Cannot send reseau.packet : " + e.getMessage());
        }
    }

    /**
     * Stop le serveur
     */
    public void stop() {
        serverState = ServerState.STOPPED;
        enableLoop = false;
        try {
            multicastSocket.leaveGroup(group);
        } catch (IOException e) {
            logger.severe("Cannot leave the group :  " + e.getMessage());
        }
        multicastSocket.close();
        udpThread.interrupt();
        logger.info("Server stop !");
    }

    /**
     * Obtient l'etat du serveur
     *
     * @return l'etat du serveur
     */
    public ServerState getServerState() {
        return serverState;
    }
}
