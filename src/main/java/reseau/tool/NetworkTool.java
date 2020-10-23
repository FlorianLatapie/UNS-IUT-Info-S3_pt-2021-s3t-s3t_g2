package reseau.tool;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <h1> Outils pour le réseau </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class NetworkTool {
    private NetworkTool() {
        throw new IllegalStateException("Utility class");
    }

    static final String URL_TEST = "1.1.1.1";

    /**
     * Recupere l'adresse ip utilisée pour rejoindre le reseau
     *
     * @return l'adresse ip de la bonne interface
     */
    public static InetAddress getAliveLocalIp() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(URL_TEST, 80));
            InetAddress tmp = socket.getLocalAddress();
            socket.close();
            return tmp;
        } catch (IOException e) {
            return null;
        }
    }

    public static List<InetAddress> getInterfaces() throws SocketException {
        List<InetAddress> inets = new ArrayList<>();
        Enumeration<NetworkInterface> gni = NetworkInterface.getNetworkInterfaces();
        while (gni.hasMoreElements()) {
            Enumeration<InetAddress> gnip = gni.nextElement().getInetAddresses();
            while (gnip.hasMoreElements())
                inets.add(gnip.nextElement());
        }

        return inets;
    }

    /**
     * Permet de savoir si le port est deja occupé
     *
     * @param port le port cible
     * @return si le port est deja occupé
     * @exception  IllegalArgumentException si le port se trouve en dehors [1, 65535]
     */
    public static boolean isBindSocket(int port) {
        if (port > 65535 || port < 1)
            throw new IllegalArgumentException("Une erreur peut se produire (max 65535 & min 1) : " + port);

        try {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    /**
     * Permet d'obtenir un port disponible
     *
     * @param startPort le port de départ (Inclus)
     * @param range     l'intervalle de recherche
     * @return un port disponible
     * @exception  IllegalArgumentException si le port se trouve en dehors [1, 65535]
     * @exception  IllegalArgumentException si pas de port disponible
     */
    public static int getPortSocket(int startPort, int range) {
        int tmpMax = startPort + range;
        if (tmpMax > 65535 || tmpMax < 1)
            throw new IllegalArgumentException("Une erreur peut se produire (max 65535 & min 1) : " + tmpMax);

        for (int i = startPort; i < tmpMax; i++)
            if (!isBindSocket(i))
                return i;

        throw new IllegalArgumentException("Pas de port disponible");
    }
}
