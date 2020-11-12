package jeu;

import reseau.packet.Packet;
import reseau.socket.Connection;
import reseau.socket.NetWorkManager;

import java.net.Socket;
import java.text.MessageFormat;

/**
 * <h1> Permet de gerer les packets </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerTcp {
    private final NetWorkManager nwm;
    private final Object core; //TODO Add the game manager (core)

    /**
     * @param netWorkManager le controleur réseau
     * @param core           coeur du jeu
     */
    public PacketHandlerTcp(NetWorkManager netWorkManager, Object core) {
        this.nwm = netWorkManager;
        this.core = core;//TODO Add the game manager (core)
    }

    /**
     * Traitement des paquet TCP
     *
     * @param packet  le paquet du message
     * @param message le message sous forme de chaine de caractere
     * @param connection le socket du paquet
     * @return reponse au paquet
     * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
     */
    public void traitement(Packet packet, String message, Connection connection) {
        switch (packet.getKey()) {
            case "DJ":
                 break;
            default:
                throw new IllegalStateException(MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    /**
     * Traitement des paquet TCP
     *
     * @param packet  le paquet du message
     * @param message le message sous forme de chaine de caractere
     * @return reponse au paquet
     * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
     */
    public void traitement(Packet packet, String message) {
        switch (packet.getKey()) {
            case "DJ":
                break;
            default:
                throw new IllegalStateException(MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }
}
