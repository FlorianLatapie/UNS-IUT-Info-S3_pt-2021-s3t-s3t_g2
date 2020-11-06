package idjr;

import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.socket.TcpClientSocket;
import reseau.tool.ThreadTool;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class PacketHandlerUdp {
    private final NetWorkManager nwm;
    private final Idjr core;// TODO Add the game manager (core)

    /**
     * @param netWorkManager le controleur rÃ©seau
     * @param core           coeur du jeu
     */
    public PacketHandlerUdp(NetWorkManager netWorkManager, Object core) {
        this.nwm = netWorkManager;
        this.core = (Idjr) core;// TODO Add the game manager (core)
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
            case "ACP":
                acp(packet, message);
                break;
            case "AMP":
                amp(packet, message);
                break;
            case "RP":
                rp(packet, message);
                break;
            default:
                throw new IllegalStateException(
                        MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    private void rp(Packet packet, String message) {
        if (SideConnection.SERVER != nwm.getSideConnection())
            return;
    }

    public void acp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;
    }

    public void amp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;
        String partie = (String)packet.getValue(message,1);
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName((String)packet.getValue(message,2));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int port = (int)packet.getValue(message,3);
        PartieInfo partieInfo = new PartieInfo(ip, port, partie, core.getTypeJoueur());
        core.addPartie(partieInfo);

        System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", packet.getValue(message, 1)));
    }
}
