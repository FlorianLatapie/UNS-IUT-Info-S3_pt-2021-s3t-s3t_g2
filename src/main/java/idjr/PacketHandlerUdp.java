package idjr;

import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.socket.TcpClientSocket;
import reseau.type.Status;
import reseau.type.TypeJoueur;
import reseau.type.TypePartie;

import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

import com.sun.javafx.tk.DummyToolkit;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerUdp {
    private final NetWorkManager nwm;
    private final Idjr core;// TODO Add the game manager (core)

    /**
     * @param netWorkManager le controleur réseau
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
            case "IP":
                ip(packet, message);
                break;
            default:
                throw new IllegalStateException(
                        MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    public void acp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;

        System.out.println(
                MessageFormat.format("Une nouvelle partie vient d''etre trouvé !\n{0}", packet.getValue(message, 1)));
        System.out.println("Voulez-vous rejoindre cette partie ? (K)");
        Scanner sc = new Scanner(System.in);
        //String rep = new Scanner(System.in).nextLine();
        String rep = "K";
        if (rep.equals("K")) {
            System.out.println("Entrez votre nom !");
            //String nomdujoueur = sc.nextLine();
            String nomdujoueur = "Joueur " + new Random().nextInt(99999);

            String messageTcp = nwm.getPacketsTcp().get("DCP").build(nomdujoueur, core.getTypeJoueur(),
                    "P" + (int) packet.getValue(message, 1));
            TcpClientSocket.connect((String) packet.getValue(message, 2), (int) packet.getValue(message, 3),
                    messageTcp);
        }
        sc.close();
    }

    public void amp(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;

        System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", packet.getValue(message, 1)));
    }

    public void ip(Packet packet, String message) {
        if (SideConnection.CLIENT != nwm.getSideConnection())
            return;

        System.out.println("Informations sur la partie !");
    }
}
