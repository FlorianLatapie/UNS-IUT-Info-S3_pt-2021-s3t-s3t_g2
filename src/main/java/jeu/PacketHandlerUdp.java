package jeu;

import jeu.controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.type.TypePartie;

import java.text.MessageFormat;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerUdp {
    private final NetWorkManager nwm;
    private final ControleurJeu core;

    /**
     * @param netWorkManager le jeu.controleur réseau
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
            case "IP":
                ip(packet, message);
                break;
            default:
                throw new IllegalStateException(
                        MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    public void rp(Packet packet, String message) {
        if (SideConnection.SERVER != nwm.getSideConnection())
            return;

        TypePartie typePartie = (TypePartie) packet.getValue(message, 1);

        String m = nwm.getPacketsUdp().get("AMP").build(core.getPartieId(), nwm.getAddress().getHostAddress(),
                nwm.getTcpPort(), core.getNomPartie(), core.getNbjtotal(), core.getNbjr(), core.getNbjv(), core.getNbjractuel(), core.getNbjvactuel(),
                core.getStatus());
        switch (typePartie) {
            case JRU:
                if (core.getNbjr() == 6)
                    nwm.getUdpSocket().sendPacket(m);
                break;
            case BOTU:
                if (core.getNbjv() == 6)
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
    }

    public void amp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;
    }

    public void ip(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;
    }
}
