package jeu;

import controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.type.Status;
import reseau.type.TypePartie;

import java.text.MessageFormat;

/**
 * <h1> Permet de gerer les packets </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerUdp {
    private final NetWorkManager nwm;
    private final ControleurJeu core;

    /**
     * @param netWorkManager le controleur réseau
     * @param core           coeur du jeu
     */
    public PacketHandlerUdp(NetWorkManager netWorkManager, Object core) {
        this.nwm = netWorkManager;
        this.core = (ControleurJeu) core;
    }

    /**
     * Traitement des paquet UDP
     *
     * @param packet  le paquet du message
     * @param message le message sous forme de chaine de caractere
     * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
     */
    public void traitement(Packet packet, String message) {
        switch (packet.getKey()) {
            case "RP":
                rp(packet, message);
                break;
            case "ACP":
                acp(packet, message);
                break;
            case "AMP":
                amp(packet, message);
                break;
            default:
                throw new IllegalStateException(MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    public void rp(Packet packet, String message) {
        if (SideConnection.SERVER != nwm.getSideConnection())
            return;

        TypePartie typePartie = (TypePartie) packet.getValue(message, 1);

        String m = nwm.getPacketsUdp().get("AMP").build(
                nwm.getPartieId(), nwm.getAddress().getHostAddress(), nwm.getTcpPort(), nwm.nomPartie, nwm.nbjtotal,
                nwm.nbjr, nwm.nbjv, nwm.nbjractuel, nwm.nbjvactuel, Status.ATTENTE);
        switch (typePartie) {
            case JRU:
                if (nwm.nbjr == 6)
                    nwm.getUdpSocket().sendPacket(m);
                break;
            case BOTU:
                if (nwm.nbjv == 6)
                    nwm.getUdpSocket().sendPacket(m);
                break;
            case MIXTE:
            default:
                nwm.getUdpSocket().sendPacket(m);
        }
    }

    public void acp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;

        System.out.println(MessageFormat.format("Une nouvelle partie vient d''etre trouvé !\n{0}",
                packet.getValue(message, 1)));
    }

    public void amp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;

        System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", packet.getValue(message, 1)));
    }
}
