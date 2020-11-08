package pp;

import pp.controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.type.TypeJoueur;

import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.out;

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
     * @param netWorkManager le jeu.controleur réseau
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
            case "AZLD":
                return azld(packet, message, socket);
            case "CDDCV":
                return cddcv(packet, message, socket);
            case "CDDJ":
                return cddj(packet, message, socket);
            default:
                throw new IllegalStateException(
                        MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    private String cddj(Packet packet, String message, Socket socket) {
        out.println("I received a CDDJ");
        core.getTempPaquet().add(message);
        core.addCddj();
        return "";
    }

    private String cddcv(Packet packet, String message, Socket socket) {
        core.getTempPaquet().add(message);
        core.setChoixDestinationVigile(true);
        return "";
    }

    private String azld(Packet packet, String message, Socket socket) {
        int z1 = new Random().nextInt(6) + 1;
        int z2 = new Random().nextInt(6) + 1;
        int z3 = new Random().nextInt(6) + 1;
        int z4 = new Random().nextInt(6) + 1;
        ArrayList<Integer> lieuZombie = new ArrayList<>();
        lieuZombie.add(z1);
        lieuZombie.add(z2);
        lieuZombie.add(z3);
        lieuZombie.add(z4);
        core.setLieuZombie(lieuZombie);
        //TODO AZLAZ lieuZombie
        out.println(
                core.getJeu().getChefVIgile() + " , chef des vigiles, regarde les résulats de l'arrivé des Zombies:");
        for (Integer integer : lieuZombie) {
            out.println(core.getJeu().getLieux().get(integer) + "-> Zombie + 1");
        }
        // l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5
        // (PC)
        // regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son
        // ecran et defausse la carte
        core.setArriveZombiePacket(true);
        return nwm.getPacketsTcp().get("AZLAZ").build(lieuZombie, core.getPartieId(), core.getNumeroTour());
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
