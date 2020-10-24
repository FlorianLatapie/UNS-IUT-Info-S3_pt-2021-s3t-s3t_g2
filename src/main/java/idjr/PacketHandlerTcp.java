package idjr;

import reseau.packet.Packet;

import reseau.socket.NetWorkManager;
import reseau.type.*;

import java.net.Socket;
import java.text.MessageFormat;
import java.util.Scanner;

/**
 * <h1> Permet de gerer les packets </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerTcp {
    private final NetWorkManager nwm;
    private final Idjr core; //TODO Add the game manager (core)

    /**
     * @param netWorkManager le controleur réseau
     * @param core           coeur du jeu
     */
    public PacketHandlerTcp(NetWorkManager netWorkManager, Object core) {
        this.nwm = netWorkManager;
        this.core = (Idjr) core;//TODO Add the game manager (core)
    }

    /**
     * Traitement des paquets TCP
     *
     * @param packet  le paquet du message
     * @param message le message sous forme de chaine de caractere
     * @param socket le socket du paquet
     * @return reponse au paquet
     * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
     */
    public String traitement(Packet packet, String message, Socket socket) {
        switch (packet.getKey()) {
            case "DPD":
                return deplacerJoueur(packet,message);
            default:
                throw new IllegalStateException(MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }
    
    public String deplacerJoueur(Packet packet, String message) {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Entrez une destination");
    	int dest = sc.nextInt();
    	System.out.println("Entrez un pion");
    	PionType pion = PionType.valueOf(sc.nextLine());
    	System.out.println("Entrez une carte Sprint(si disponible 'SPR' sinon 'NUL')");
    	String carteSprint= sc.nextLine();
    	sc.close();
    
    	return nwm.getPacketsTcp().get("DPR").build(dest,pion,carteSprint,packet.getValue(message, 3),packet.getValue(message, 4),core.getJoueurId());
    	
    }
}
