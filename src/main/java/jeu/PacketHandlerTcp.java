package jeu;

import controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.type.TypeJoueur;

import java.net.Socket;
import java.text.MessageFormat;
import java.util.Random;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerTcp {
    private final NetWorkManager nwm;
    private final ControleurJeu core;

    /**
     * @param netWorkManager le controleur réseau
     * @param core           coeur du jeu
     */
    public PacketHandlerTcp(NetWorkManager netWorkManager, Object core) {
        this.nwm = netWorkManager;
        this.core = (ControleurJeu) core;
    }

    /**
     * Traitement des paquet TCP
     *
     * @param packet  le paquet du message
     * @param message le message sous forme de chaine de caractere
     * @param socket  le socket du paquet
     * @return reponse au paquet
     * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
     */
    public String traitement(Packet packet, String message, Socket socket) {
        switch (packet.getKey()) {
            case "DCP":
                return dcp(packet, message, socket);
            default:
                throw new IllegalStateException(
                        MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    private String dcp(Packet packet, String message, Socket socket) {

        switch (core.getStatus()) {
            case ATTENTE:
                System.out.println(socket.getLocalPort());
                String id = core.ajouterJoueur(socket.getInetAddress(), socket.getPort(), (String) packet.getValue(message, 1), (TypeJoueur) packet.getValue(message, 2));
                return nwm.getPacketsTcp().get("ACP").build(core.getPartieId(), id);
            case ANNULEE:
            case COMPLETE:
            case TERMINE:
                return nwm.getPacketsTcp().get("RCP").build(core.getPartieId());
            default:
                throw new IllegalStateException("Unexpected value: " + core.getStatus());
        }
    }
}
