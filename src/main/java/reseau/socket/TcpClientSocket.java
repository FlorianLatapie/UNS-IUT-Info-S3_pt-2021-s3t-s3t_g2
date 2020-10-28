package reseau.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1> Client TCP </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class TcpClientSocket {
    private static final Logger logger = Logger.getLogger(TcpClientSocket.class.getName());

    private TcpClientSocket() {
        throw new IllegalStateException("Utility class");
    }

    static InetAddress host;

    /**
     * Se connecte au seveur et envoie un message
     *
     * @param ip      l'ip du serveur TCP serveur
     * @param port    port du serveur TCP serveur
     * @param message le message a envoyer
     * @param ip1     l'ip du serveur TCP client
     * @param port1   port du serveur TCP client
     * @return reponse au paquet
     */
    public static String connect(String ip, int port, String message, String ip1, int port1) {
        if (ip1 == null)
            return connect(ip, port, message, null, 0);
        try {
            return connect(InetAddress.getByName(ip), port, message, InetAddress.getByName(ip1), port1);
        } catch (UnknownHostException e) {
            logger.warning("Cannot find ip : " + e.getMessage());
        }

        return "";
    }

    /**
     * Se connecte au seveur et envoie un message
     * Il réessaye d'envoyer le paquer tout les 50ms
     *
     * @param inetAddress l'ip du serveur TCP
     * @param port        port du serveur TCP
     * @param message     le message a envoyer
     * @param ip1         l'ip du serveur TCP client
     * @param port1       port du serveur TCP client
     * @return reponse au paquet
     */
    public static String connect(InetAddress inetAddress, int port, String message, InetAddress ip1, int port1) {
        logger.finest("Client started !");

        String r = "";
        boolean error = false;
        int retry = 0;
        while (!error) {
            try {
                ObjectOutputStream outputStream;
                ObjectInputStream inputStream;
                Socket socket;
                if (ip1 == null)
                    socket = new Socket(inetAddress, port);
                else
                    socket = new Socket(inetAddress, port, ip1, port1);
                socket.setTcpNoDelay(false);
                logger.log(Level.FINEST, "Client on port : {}", port);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                logger.log(Level.INFO, "Message sent : {0}", message);
                outputStream.writeObject(message);
                inputStream = new ObjectInputStream(socket.getInputStream());
                r = (String) inputStream.readObject();
                logger.log(Level.INFO, "Message received : {0}", r);
                inputStream.close();
                outputStream.close();
                error = true;
            } catch (IOException | ClassNotFoundException e) {
                if (retry == 3)
                    break;
                logger.warning(MessageFormat.format("Cannot connect to the server : {0}", e.getMessage()));
                logger.warning(MessageFormat.format("Retry # : {0}", retry));
                retry++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        return r;
    }
}
