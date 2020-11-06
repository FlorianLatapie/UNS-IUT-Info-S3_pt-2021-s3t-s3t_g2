package botfaible;

import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.TcpClientSocket;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;

import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.out;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class PacketHandlerTcp {
    private final NetWorkManager nwm;
    private final Idjr core;

    /**
     * @param netWorkManager le controleur rÃ©seau
     * @param core           coeur du jeu
     */
    public PacketHandlerTcp(NetWorkManager netWorkManager, Object core) {
        this.nwm = netWorkManager;
        this.core = (Idjr) core;
    }

    /**
     * Traitement des paquets TCP
     *
     * @param packet  le paquet du message
     * @param message le message sous forme de chaine de caractere
     * @return reponse au paquet
     * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
     */
    public String traitement(Packet packet, String message) {
        switch (packet.getKey()) {
            case "IP":
                return initialiserPartie(packet, message);
            case "PIIJ":
                return lancerDes(packet, message);// savoir comment return plusieur choses
            case "PIRD":
                return choisirDestPion(packet, message);
            case "PIIG":
                return joueurPlacement(packet, message);
            case "IT":
                return debutTour(packet, message);
            case "PAZ":
                return lanceDesChefVigil(packet, message);
            case "PCD":
                return choixDestVigil(packet, message);
            case "CDCDV":
                return choisirDest(packet, message);
            case "CDZVI":
                return destZombieVengeur(packet, message);
            case "PDP":
                return debutDeplacemant(packet, message);
            case "DPD":
                return deplacerPion(packet, message);
            case "DPI":
                return tousDeplacment(packet, message);
            case "PRAZ":
                return attaqueZombie(packet, message);
            case "RAZDS":
                return choisirSacrifice(packet, message);
            case "RAZIF":
                return tousSacrifice(packet, message);
            case "FP":
                return finPartie(packet, message);

            case "PIPZ":
            case "PFC":
            case "RFC":
            case "PECV":
            case "RECV":
            case "CDFC":
                return "";
            default:
                throw new IllegalStateException(
                        MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
        }
    }

    public String initialiserPartie(Packet packet, String message) {
        List<?> nomsT = (List<?>) packet.getValue(message, 1);
        List<String> noms = new ArrayList<>();
        for (Object o : nomsT)
            noms.add((String) o);

        List<?> couleursT = (List<?>) packet.getValue(message, 2);
        List<Couleur> couleurs = new ArrayList<>();
        for (Object o : couleursT)
            couleurs.add((Couleur) o);

        core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
        ArrayList<Joueur> listeJoueursInitiale = new ArrayList<>();

        for (int i = 0; i < noms.size(); i++)
            listeJoueursInitiale.add(new Joueur(noms.get(i), couleurs.get(i)));

        core.getJeu().initJoueurs(listeJoueursInitiale);

        return "";
    }

    public String lancerDes(Packet packet, String message) {
        List<?> pionT = (List<?>) packet.getValue(message, 2);
        List<Integer> pion = new ArrayList<>();
        for (Object o : pionT)
            pion.add((Integer) o);

        core.setPionAPos(pion);

        String m1 = (String) packet.getValue(message, 3);

        return nwm.getPacketsTcp().get("PILD").build(m1, core.getJoueurId());
    }

    public String choisirDestPion(Packet packet, String message) {
        List<?> destRestantT = (List<?>) packet.getValue(message, 2);
        List<Integer> destRestant = new ArrayList<>();
        for (Object o : destRestantT)
            destRestant.add((Integer) o);

        int pion = 0;
        if (!core.getPionAPos().isEmpty())
            pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));

        int dest = 0;
        if (!destRestant.isEmpty())
            dest = destRestant.get(new Random().nextInt(destRestant.size()));

        core.getJeu().placePerso(core.getMoi(), pion, dest);
        String m1 = (String) packet.getValue(message, 3);
        return nwm.getPacketsTcp().get("PICD").build(dest, pion, m1,
                core.getJoueurId());
    }

    public String joueurPlacement(Packet packet, String message) {
        Couleur c = (Couleur) packet.getValue(message, 1);
        int dest = (int) packet.getValue(message, 4);
        int pion = (int) packet.getValue(message, 5);

        core.getJeu().placePerso(core.getJoueur(c), pion, dest);
        return "";
    }

    public String debutTour(Packet packet, String message) {
        List<?> lT = (List<?>) packet.getValue(message, 2);
        List<Couleur> l = new ArrayList<>();
        for (Object o : lT)
            l.add((Couleur) o);

        for (Joueur j : core.getJeu().getJoueurs().values())
            if (!l.contains(j.getCouleur()))
                j.setEnVie(false);

        return "";
    }

    public String lanceDesChefVigil(Packet packet, String message) {
        Couleur c1 = (Couleur) packet.getValue(message, 1);
        if (core.getCouleur() == c1) {
            String m1 = (String) packet.getValue(message, 3);
            int m2 = (int) packet.getValue(message, 4);
            String messageTcp = nwm.getPacketsTcp().get("AZLD").build(m1,
                    m2, core.getJoueurId());
            TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
        }

        return "";
    }

    public String choixDestVigil(Packet packet, String message) {
        if (!core.getMoi().isEnVie())
            return "";

        if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
                && (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {

            out.println("Entrez une destination");
            int dest = 0;

            if (!core.getJeu().choixLieudispo(core.getMoi()).isEmpty())
                dest = core.getJeu().choixLieudispo(core.getMoi())
                        .get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));

            String messageTcp = nwm.getPacketsTcp().get("CDDCV").build(dest, (String) packet.getValue(message, 3),
                    (int) packet.getValue(message, 4), core.getJoueurId());
            TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);

        } else if (core.getCouleur() != (Couleur) packet.getValue(message, 1)
                && (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
            return "";
        } else {
            out.println("Entrez une destination");
            int dest = 0;
            if (!core.getJeu().choixLieudispo(core.getMoi()).isEmpty())
                dest = core.getJeu().choixLieudispo(core.getMoi())
                        .get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));

            String messageTcp = nwm.getPacketsTcp().get("CDDJ").build(dest, (String) packet.getValue(message, 3),
                    (int) packet.getValue(message, 4), core.getJoueurId());

            TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
        }

        return "";
    }

    public String choisirDest(Packet packet, String message) {
        if (!core.getMoi().isEnVie())
            return "";

        if (core.getCouleur() == (Couleur) packet.getValue(message, 1)) {
            return "";
        } else {
            out.println("Entrez une destination");
            int dest = 0;
            if (!core.getJeu().choixLieudispo(core.getMoi()).isEmpty())
                dest = core.getJeu().choixLieudispo(core.getMoi())
                        .get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));
            out.println("Entrez un pion (Piontype)");

            String messageTcp = nwm.getPacketsTcp().get("CDDJ").build(dest, (String) packet.getValue(message, 3),
                    (int) packet.getValue(message, 4), core.getJoueurId());

            TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
        }

        return "";
    }

    public String destZombieVengeur(Packet packet, String message) {
        out.println("Entrez une destination pour le zombie vengeur");
        int destZomb = 0;
        if (!core.getJeu().choixLieudispo().isEmpty())
            destZomb = core.getJeu().choixLieudispo().get(new Random().nextInt(core.getJeu().choixLieudispo().size()));

        return nwm.getPacketsTcp().get("CDDZVJE").build(destZomb, packet.getValue(message, 1),
                packet.getValue(message, 2), core.getJoueurId());
    }

    public String debutDeplacemant(Packet packet, String message) {
        List<?> lieuxT = (List<?>) packet.getValue(message, 4);
        List<Integer> lieux = new ArrayList<>();
        for (Object o : lieuxT)
            lieux.add((Integer) o);
        core.getJeu().fermerLieu(lieux);

        return "";
    }

    public String deplacerPion(Packet packet, String message) {
        int dest = (int) packet.getValue(message, 1);
        out.println("First dest " + dest);
        if (core.getJeu().getLieux().get(dest).isFull())
            dest = 4;
        out.println("Second dest " + dest);
        HashMap<Integer, List<Integer>> listedp = (HashMap<Integer, List<Integer>>) packet.getValue(message, 2);
        List<Integer> listeDest = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
            for (int destPos : dp.getValue())
                if (destPos == dest)
                    listeDest.add(dp.getKey());

        List<Personnage> list = new ArrayList<>(core.getMoi().getPersonnages().values());
        int pionAdep = list.get(new Random().nextInt(list.size())).getNum();
        if (!listeDest.isEmpty())
            pionAdep = listeDest.get(new Random().nextInt(listeDest.size()));

        out.println("Entrez un pion (Piontype)");
        out.println("Entrez une carte Sprint(si disponible 'SPR' sinon 'NUL')");
        CarteType carte = CarteType.NUL;
        core.getJeu().deplacePerso(core.getMoi(), pionAdep, dest);
        return nwm.getPacketsTcp().get("DPR").build(dest, pionAdep, carte, (String) packet.getValue(message, 3),
                (int) packet.getValue(message, 4), core.getJoueurId());

    }

    public String tousDeplacment(Packet packet, String message) {
        Couleur c = (Couleur) packet.getValue(message, 1);
        int dest = (int) packet.getValue(message, 2);
        int p = (int) packet.getValue(message, 3);
        core.getJeu().deplacePerso(core.getJoueur(c), p, dest);

        return "";
    }

    public String attaqueZombie(Packet packet, String message) {
        if ((int) packet.getValue(message, 1) != 0)
            core.getJeu().getLieux().get((int) packet.getValue(message, 1)).addZombie();

        if ((int) packet.getValue(message, 2) != 0)
            core.getJeu().getLieux().get((int) packet.getValue(message, 2)).addZombie();

        return "";
    }

    public String choisirSacrifice(Packet packet, String message) {
        out.println("Entrez un pion (PionCouleur)");
        int pionInt = 0;

        if (!core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).isEmpty())
            pionInt = core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).get(new Random()
                    .nextInt(core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).size()));

        String pionTemp = core.getCouleur().name().charAt(0) + "" + pionInt;
        PionCouleur pion = PionCouleur.valueOf(pionTemp);
        return nwm.getPacketsTcp().get("RAZCS").build(packet.getValue(message, 1), pion, packet.getValue(message, 2),
                packet.getValue(message, 3), core.getJoueurId());
    }

    public String tousSacrifice(Packet packet, String message) {
        PionCouleur pionTemp = (PionCouleur) packet.getValue(message, 2);
        Couleur pionCouleur = IdjrTools.getCouleurByChar(pionTemp);
        int pionInt = IdjrTools.getPionByValue(pionTemp);
        core.getJeu().sacrifie(core.getJoueur(pionCouleur), pionInt);
        return "";
    }

    private String finPartie(Packet packet, String message) {
        Couleur gagnant = (Couleur) packet.getValue(message, 2);
        out.println("Le gagant est " + core.getJoueur(gagnant).getNom() + " !");
        nwm.getTcpServerSocket().stopUntilFinished();
        nwm.getUdpSocket().stop();
        return "";
    }
}
