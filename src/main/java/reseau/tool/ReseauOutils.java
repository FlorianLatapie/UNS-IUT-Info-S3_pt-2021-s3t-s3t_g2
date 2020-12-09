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
 * @version 2.0
 */
public interface ReseauOutils {
    static final String URL_TEST = "1.1.1.1";

    /**
     * Recupere l'adresse ip utilisée pour rejoindre le reseau.
     *
     * @return L'adresse ip de la bonne interface
     */
     static InetAddress getLocalIp() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(URL_TEST, 80));
            return socket.getLocalAddress();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Recupere toutes les interfaces du pc.
     *
     * @return La liste des interfaces
     */
     static List<InetAddress> getInterfaces() throws SocketException {
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
     static boolean isBindSocket(int port) {
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
     * @param finPort     Le port de fin (Inclus)
     * @return Un port disponible
     * @exception  IllegalArgumentException Si le port se trouve en dehors [1, 65535]
     * @exception  IllegalArgumentException Si pas de port disponible
     */
     static int getPortSocket(int debutPort, int finPort) {
        if (debutPort >= finPort)
            throw new IllegalArgumentException("Le port de fin doit etre entre 1 et 65535 : " + finPort);

        if (debutPort > 65535 || debutPort < 1)
            throw new IllegalArgumentException("Le port de début doit etre entre 1 et 65535 : " + debutPort);

        if (finPort > 65535)
            throw new IllegalArgumentException("Le port de fin doit etre entre 1 et 65535 : " + finPort);

        for (int i = debutPort; i < finPort; i++)
            if (!isBindSocket(i))
                return i;

        throw new IllegalArgumentException("Pas de port disponible");
    }
}
