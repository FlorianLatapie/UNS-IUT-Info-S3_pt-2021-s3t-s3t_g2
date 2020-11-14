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
public abstract class ReseauOutils {
    private ReseauOutils() {
        throw new IllegalStateException("Utility class");
    }

    static final String URL_TEST = "1.1.1.1";

    /**
     * Recupere l'adresse ip utilisée pour rejoindre le reseau.
     *
     * @return L'adresse ip de la bonne interface
     */
    public static InetAddress getLocalIp() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(URL_TEST, 80));
            InetAddress ip = socket.getLocalAddress();
            socket.close();
            return ip;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Recupere toutes les interfaces du pc.
     *
     * @return La liste des interfaces
     */
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
     * Permet de savoir si le port est deja occupé.
     *
     * @param port Le port cible
     * @return Si le port est deja occupé
     * @exception  IllegalArgumentException Si le port se trouve en dehors [1, 65535]
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
     * Permet d'obtenir un port disponible.
     *
     * @param debutPort Le port de départ (Inclus)
     * @param intervalle     L'intervalle de recherche
     * @return Un port disponible
     * @exception  IllegalArgumentException Si le port se trouve en dehors [1, 65535]
     * @exception  IllegalArgumentException Si pas de port disponible
     */
    public static int getPortSocket(int debutPort, int intervalle) {
        int tmpMax = debutPort + intervalle;
        if (tmpMax > 65535 || tmpMax < 1)
            throw new IllegalArgumentException("Une erreur peut se produire (max 65535 & min 1) : " + tmpMax);

        for (int i = debutPort; i < tmpMax; i++)
            if (!isBindSocket(i))
                return i;

        throw new IllegalArgumentException("Pas de port disponible");
    }
}
