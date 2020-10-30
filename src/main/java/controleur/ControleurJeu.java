package controleur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.*;

import jeu.*;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.tool.NetworkTool;
import reseau.tool.ThreadTool;
import reseau.type.Couleur;
import reseau.type.Status;

import static java.lang.System.out;

/**
 * <h1>La classe controleurJeu</h1>
 *
 * @author Alex
 * @author Aurelien
 * @version 0
 * @since 04/10/2020
 */

public class ControleurJeu {
    private final String partieId; // "P" + Numero de la partie Ex : P9458
    private final int intPartieId;
    private String nomPartie; // Nom de la partie
    private int nbjtotal; // Nombre de joueurs total
    private int nbjr; // Nombre de joueurs réel max
    private int nbjv; // Nombre de joueurs virtuel max
    private int nbjractuel; // Nombre de joueurs réel actuellement connecté
    private int nbjvactuel; // Nombre de joueurs virtuel actuellement connecté
    private final Scanner sc;
    private static final String RETOUR_LIGNE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    private Jeu jeu;
    private int port;
    private int nextJoueurId;
    private Random rd;

    private List<Couleur> couleursRestante;

    public Status getStatus() {
        return status;
    }

    private Status status;

    final NetWorkManager nwm;

    ArrayList<Joueur> joueurs;

    // TODO INTERROMPRE PARTIE

    public ControleurJeu(String nom, int njr, int njv) throws IOException {
        if (njr + njv > 6 || njr + njv < 3)
            throw new IllegalArgumentException("Mauvais nombre de joueur");
        this.nomPartie = nom;
        this.nbjv = njv;
        this.nbjr = njr;
        this.nbjtotal = nbjv + nbjr;
        couleursRestante = new ArrayList<>();
        joueurs = new ArrayList<>();
        rd = new Random();
        couleursRestante.add(Couleur.NOIR);
        couleursRestante.add(Couleur.ROUGE);
        couleursRestante.add(Couleur.JAUNE);
        couleursRestante.add(Couleur.BLEU);
        couleursRestante.add(Couleur.MARRON);
        couleursRestante.add(Couleur.VERT);
        nwm = new NetWorkManager(this);
        status = Status.ATTENTE;
        initReseau();
        this.port = nwm.getTcpPort();
        this.intPartieId = new Random().nextInt(10000000);
        this.partieId = "P" + intPartieId;
        sc = new Scanner(System.in);
        ThreadTool.asyncTask(() -> {
            String m = nwm.getPacketsUdp().get("ACP").build(intPartieId, nwm.getAddress().getHostAddress(), port,
                    nomPartie, nbjtotal, nbjr, nbjv, status);
            nwm.getUdpSocket().sendPacket(m);
            // TODO vérifier jr et jv nombre
            while (joueurs.size() != this.nbjtotal) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            status = Status.COMPLETE;
            jeu = new Jeu(joueurs);
        });
    }

    public boolean joueurConnect() {
        return joueurs.size() == this.nbjtotal && jeu != null;
    }

    public void setCouleurJoueur(ArrayList<Couleur> couleur) {
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            jeu.getJoueurs().get(i).setCouleur(couleur.get(i));
        }
    }

    public void demarerJeu() {
        ThreadTool.asyncTask(() -> {
            // TODO UN OU PLUSIEURS LIEUX FERME
            // TODO 3 ou 4 PION
            out.println(jeu.afficheJeu());
            //PASS ON UDP
            //String m = nwm.getPacketsUdp().get("IP").build(getJoueursListe(), getCouleurJoueursListe(), 0, 3, partieId);
            //nwm.getUdpSocket().sendPacket(m);

            // TODO ATTENDRE TOUS LES JOUEURS
            // TODO (COMPLETE ACP)
            this.placementPersonnage();
            this.jeu.resultatChefVigile(jeu.getJoueurs().get(0));
            ArrayList<Integer> lieuZombie = arriveZombie();
            this.jeu.entreZombie(lieuZombie);
            out.println(jeu.afficheJeu());
            out.println("YES");
            this.start();
        });
    }

    private void initReseau() throws SocketException {
        InetAddress ip = NetworkTool.getAliveLocalIp();
        if (ip == null) {
            List<InetAddress> ips = NetworkTool.getInterfaces();
            int i = 0;
            for (InetAddress ia : ips) {
                System.out.println(i + " " + ia.getHostAddress());
                i++;
            }

            int val = new Scanner(System.in).nextInt();
            ip = ips.get(val);
        }

        try {
            nwm.initConnection(SideConnection.SERVER, ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ip.getHostAddress());
        System.out.println(nwm.getTcpPort());
    }

    public String ajouterJoueur(InetAddress ip, int port, String nom) {
        Joueur joueur = new Joueur(getNewJoueurId(), ip, port, nom);
        joueurs.add(joueur);
        return joueur.getJoueurId();
    }

    private int getNewJoueurId() {
        int tmp = nextJoueurId;
        nextJoueurId++;
        return tmp;
    }

    private List<String> getJoueursListe() {
        // TODO BONNE ORDRE ? VIGILE
        List<String> noms = new ArrayList<>();
        for (Joueur j : joueurs)
            noms.add(j.getNom());

        return noms;
    }

    private List<Couleur> getCouleurJoueursListe() {
        // TODO BONNE ORDRE ? VIGILE
        List<Couleur> couleurs = new ArrayList<>();
        for (Joueur j : joueurs)
            couleurs.add(j.getCouleur());

        return couleurs;
    }

    /**
     * Execute le déroulement d'une parite
     */
    public void start() {
        fouilleCamion();
        electionChefVigi();
        ArrayList<Integer> lieuZombie = arriveZombie();
        ArrayList<Integer> destination = new ArrayList<>();
        String s = "";
        s = phasechoixDestination(destination);
        phaseDeplacementPerso(destination, s);
        jeu.entreZombie(lieuZombie);
        jeu.fermerLieu();
        if (this.finJeu())
            return;
        jeu.fermerLieu();
        if (!attaqueZombie())
            return;

        mortJoueur();
        start();
    }

    /**
     * Affiche le joueur qui fouille le camion
     */
    private void fouilleCamion() {
        // TODO PREVENIR QUI FOUILLE LE CAMION
        if (!jeu.getLieux().get(4).afficheJoueurSurLieu().isEmpty()) {
            Joueur j = voteJoueur(4);
            out.println(j + " fouille le camion!");
            out.println("Le camion est vide.");
            out.println(RETOUR_LIGNE);
        }
    }

    /**
     * @return le jeu
     */
    public Jeu getJeu() {
        return jeu;
    }

    /**
     * Affiche et met a jour le nouveau chef des vigiles
     */
    private void electionChefVigi() {
        // TODO PREVENIR QUI EST LE CHEF DES VIGILES
        if (!jeu.getLieux().get(5).afficheJoueurSurLieu().isEmpty()) {
            Joueur j = voteJoueur(5);
            jeu.resultatChefVigile(j);
            out.println(j + " est le nouveau chef des vigiles!");
            jeu.setNewChef(true);
        } else {
            out.println("Pas de nouveau chef des vigiles!");
            jeu.setNewChef(false);
        }

        out.println(RETOUR_LIGNE);
    }

    /**
     * Definie l'arrivé des zombies et l'affiche pour le chef des vigiles
     *
     * @return liste des numéro des lieux d'arrivé des zomibie
     */
    private ArrayList<Integer> arriveZombie() {
        int z1 = new Random().nextInt(6) + 1;
        int z2 = new Random().nextInt(6) + 1;
        int z3 = new Random().nextInt(6) + 1;
        int z4 = new Random().nextInt(6) + 1;
        ArrayList<Integer> lieuZombie = new ArrayList<>();
        lieuZombie.add(z1);
        lieuZombie.add(z2);
        lieuZombie.add(z3);
        lieuZombie.add(z4);
        for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
            if (this.jeu.getJoueurs().get(i).isChefDesVigiles()) {
                out.println(
                        jeu.getJoueurs().get(i) + " , chef des vigiles, regarde les résulats de l'arrivé des Zombies:");
                for (Integer integer : lieuZombie) {
                    out.println(this.jeu.getLieux().get(integer) + "-> Zombie + 1");
                }
            }
        }
        // l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5
        // (PC)
        // regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son
        // ecran et defausse la carte
        out.println(RETOUR_LIGNE);
        // TODO LE CHEF DES VIGILES CONAISSE LE LIEU D4ARRIVE DES ZOMBIES
        return lieuZombie;
    }

    private String phasechoixDestination(ArrayList<Integer> destination) {
        String s = "";
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            if (jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
                System.out.println(jeu.afficheJeu());
                int x = choixDestination(i);
                destination.add(x);
                s += "Destination " + jeu.getJoueurs().get(i) + " (CDV) : " + jeu.getLieux().get(x) + "\n";
                if (jeu.getNewChef() == true) {
                    out.println(s);
                }
                // TODO CHEF DES VIGILES CHOISI DEST
                // TODO AFFICHER QUE SI IL A ETAIT ELU CE TOUR SI
            }
        }
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            if (!jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
                System.out.println(jeu.afficheJeu());
                int x = choixDestination(i);
                destination.add(x);
                s += "Destination " + jeu.getJoueurs().get(i) + " : " + jeu.getLieux().get(x) + "\n";
                out.println();
                // TODO TOUS LES JOUEURS LEUR DEST SECRETEMENT EN MEME TEMPS
            }
        }
        return s;
    }

    private int choixDestination(int joueur) {
        int lieu = 0;
        ArrayList<Integer> dest = new ArrayList<>();
        out.println(jeu.getJoueurs().get(joueur) + " choisis une destination (un numéro):");
        for (int a = 1; a < 7; a++) {
            for (Integer j : this.jeu.getJoueurs().get(joueur).getPersonnages().keySet()) {
                if (jeu.getJoueurs().get(joueur).getPersonnages().get(j).getMonLieu() != jeu.getLieux().get(a)
                        && jeu.getLieux().get(a).isOuvert() && !dest.contains(a)) {
                    dest.add(a);
                    out.println(a + "\t" + jeu.getLieux().get(a));
                }
            }
        }
        // TODO RECEVOIR ET ENVOYER DEST
        // lieu = sc.nextInt();
        lieu = new Random().nextInt(6) + 1; // temporaire
        out.println(lieu); // temporaire
        while (!dest.contains(lieu)) {
            out.println(RETOUR_LIGNE);
            System.out.println(jeu.afficheJeu());
            out.println();
            out.println("Numéro incorect !\n");
            out.println(jeu.getJoueurs().get(joueur) + " choisis une destination (un numéro):");
            for (Integer integer : dest) {
                out.println(integer + "\t" + jeu.getLieux().get(integer));
            }
            // lieu = sc.nextInt();
            lieu = new Random().nextInt(6) + 1; // temporaire
            out.println(lieu); // temporaire
        }
        out.println(RETOUR_LIGNE);
        return lieu;
    }

    private void phaseDeplacementPerso(ArrayList<Integer> destination, String s) {
        int compteur = 0;
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            if (jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
                out.println(jeu.afficheJeu());
                out.println(s);
                if (!deplacementPerso(jeu.getJoueurs().get(i), destination.get(compteur))) {
                    return;
                }
                compteur += 1;
            }
        }
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            if (!jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
                out.println(jeu.afficheJeu());
                out.println(s);
                if (!deplacementPerso(jeu.getJoueurs().get(i), destination.get(compteur))) {
                    return;
                }
                compteur += 1;
            }
        }
    }

    private boolean deplacementPerso(Joueur joueur, int destination) {
        int choixPerso;
        String s = "";
        ArrayList<Integer> pers = new ArrayList<>();
        out.println();
        for (Integer a : joueur.getPersonnages().keySet()) {
            if (joueur.getPersonnages().get(a).getMonLieu() != jeu.getLieux().get(destination)) {
                pers.add(a);
                s += a + "\t" + joueur.getPersonnages().get(a) + "\n";
            }
        }
        if (pers.size() == 1) {
            if (jeu.getLieux().get(destination).isFull() || !jeu.getLieux().get(destination).isOuvert()) {
                destination = 4;
                out.println(joueur + " personage a déplacer à " + jeu.getLieux().get(destination) + ": "
                        + joueur.getPersonnages().get(pers.get(0)));
            } else {
                out.println(joueur + " personage a déplacer à " + jeu.getLieux().get(destination) + ": "
                        + joueur.getPersonnages().get(pers.get(0)));
            }
            choixPerso = pers.get(0);
        } else {
            affichageConsoleDeplacment(joueur, destination);
            out.println(s);
            // TODO ENVOIE LA LISTE DES CHOIS PERSO (PERS)
            // choixPerso = sc.nextInt();
            choixPerso = new Random().nextInt(3); // temporaire
            out.println(choixPerso); // temporaire
            while (!pers.contains(choixPerso)) {
                out.println(RETOUR_LIGNE);
                System.out.println(jeu.afficheJeu());
                out.println();
                out.println("Numéro Incorect!\n");
                out.println(joueur + " choisit un personage a déplacer à " + jeu.getLieux().get(destination));
                for (Integer per : pers) {
                    out.println(per + "\t" + joueur.getPersonnages().get(per));
                }
                // choixPerso = sc.nextInt();
                choixPerso = new Random().nextInt(3); // temporaire
                out.println(choixPerso); // temporaire
            }
        }
        if (!jeu.getLieux().get(destination).isFull() && jeu.getLieux().get(destination).isOuvert()) {
            jeu.deplacePerso(joueur, choixPerso, destination);
        } else {
            jeu.deplacePerso(joueur, choixPerso, 4);
        }

        out.println(RETOUR_LIGNE);
        return !this.finJeu();
    }

    private void affichageConsoleDeplacment(Joueur joueur, int destination) {
        if (!jeu.getLieux().get(destination).isFull() && jeu.getLieux().get(destination).isOuvert()) {
            out.println(MessageFormat.format("{0} choisit un personage a déplacer à {1}", joueur,
                    jeu.getLieux().get(destination)));
        } else {
            if (!jeu.getLieux().get(destination).isOuvert()) {
                destination = 4;
                out.println(jeu.getLieux().get(destination) + " est fermé.");
            } else if (jeu.getLieux().get(destination).isFull()) {
                out.println(jeu.getLieux().get(destination) + " est complet.");
            }
            destination = 4;
            out.println(joueur + " choisit un personage a déplacer à " + jeu.getLieux().get(4));
        }
    }

    private boolean attaqueZombie() {
        jeu.lastAttaqueZombie();
        for (int i = 1; i < 7; i++) {
            if (jeu.getLieux().get(i).isOuvert()) {
                if (i == 4) {// si parking
                    for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
                        if (!jeu.getLieux().get(i).getPersonnage().isEmpty()) {
                            System.out.println(jeu.afficheJeu());
                            Joueur jou = this.voteJoueur(4);
                            String s = "";
                            ArrayList<Integer> num = new ArrayList<>();
                            int persEntre;
                            for (Integer b : jou.getPersonnages().keySet()) {
                                if (jeu.getLieux().get(i).getPersonnage().contains(jou.getPersonnages().get(b))) {
                                    num.add(b);
                                    s += MessageFormat.format("{0}\t{1}\n", b, jou.getPersonnages().get(b));
                                }
                            }
                            if (num.size() == 1) {
                                out.println(jou + " personage sacrifier a " + jeu.getLieux().get(4) + ": "
                                        + jou.getPersonnages().get(num.get(0)));
                                persEntre = num.get(0);
                            } else {
                                out.println(MessageFormat.format("{0} choisis un numéro a sacrifier a {1}: ", jou,
                                        jeu.getLieux().get(i)));
                                out.println(s);
                                // TODO ENVOYER LA LISTE DES PERSO QUI SOUHAITE SACRFIE (NUM)
                                // persEntre = sc.nextInt();
                                persEntre = new Random().nextInt(3); // temporaire
                                out.println(persEntre); // temporaire
                                while (!num.contains(persEntre)) {
                                    out.println(RETOUR_LIGNE);
                                    System.out.println(jeu.afficheJeu());
                                    out.println();
                                    out.println("Numéro incorect !\n");
                                    out.println(MessageFormat.format("{0} choisis un numéro a sacrifier a {1}: ", jou,
                                            jeu.getLieux().get(i)));
                                    for (int b = 0; b < num.size(); b++) {
                                        out.println(num.get(b) + "\t" + jou.getPersonnages().get(num.get(b)));
                                    }
                                    // persEntre = sc.nextInt();
                                    persEntre = new Random().nextInt(3); // temporaire
                                    out.println(persEntre); // temporaire
                                }
                            }
                            jeu.sacrifie(jou, persEntre);
                            out.println(RETOUR_LIGNE);
                            jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);
                        }
                        if (this.finJeu()) {
                            return false;
                        }
                    }
                } else if (jeu.getLieux().get(i).estAttaquable()) {
                    String s = "";
                    System.out.println(jeu.afficheJeu());
                    Joueur jou = this.voteJoueur(jeu.getLieux().get(i).getNum());

                    ArrayList<Integer> num = new ArrayList<>();
                    int persEntre;
                    for (Integer b : jou.getPersonnages().keySet()) {
                        if (jeu.getLieux().get(i).getPersonnage().contains(jou.getPersonnages().get(b))) {
                            num.add(b);
                            s += b + "     " + jou.getPersonnages().get(b) + "\n";
                            out.println();
                        }
                    }
                    if (num.size() == 1) {
                        out.println(jou + " personage sacrifier a " + jeu.getLieux().get(i) + ": "
                                + jou.getPersonnages().get(num.get(0)));
                        persEntre = num.get(0);
                    } else {
                        out.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
                        out.println(s);
                        // TODO ENVOYER LA LISTE DES PETSO QUI PEUT SACRIFIE (NUM)
                        // persEntre = sc.nextInt();
                        persEntre = new Random().nextInt(3); // temporaire
                        out.println(persEntre); // temporaire
                        while (!num.contains(persEntre)) {
                            out.println(RETOUR_LIGNE);
                            System.out.println(jeu.afficheJeu());
                            out.println();
                            out.println("Numéro incorect !\n");
                            out.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
                            for (int b = 0; b < num.size(); b++) {
                                out.println(num.get(b) + "\t" + jou.getPersonnages().get(num.get(b)));
                            }
                            // persEntre = sc.nextInt();
                            persEntre = new Random().nextInt(3); // temporaire
                            out.println(persEntre); // temporaire
                        }
                    }
                    jeu.sacrifie(jou, persEntre);
                    out.println(RETOUR_LIGNE);
                }
                jeu.getLieux().get(i).setNbZombies(0);
                if (this.finJeu()) {
                    return false;
                }

            }
        }
        out.println(RETOUR_LIGNE);
        return true;
    }

    private Joueur voteJoueur(int lieu) {
        // gestion des cartes NOT TO DO
        int rnd = new Random().nextInt(jeu.getLieux().get(lieu).afficheJoueurSurLieu().size());
        return jeu.getLieux().get(lieu).afficheJoueurSurLieu().get(rnd);
    }

    private void placementPersonnage() {
        for (int n = 0; n < jeu.getJoueurs().get(0).getPersonnages().size(); n++) {
            for (int i = 0; i < jeu.getJoueurs().size(); i++) {

                String message = nwm.getPacketsTcp().get("PIIJ").build(nbPlace(), persoPlace(jeu.getJoueurs().get(i)),
                        partieId);
                ThreadTool.taskPacketTcp(jeu.getJoueurs().get(i).getIp(), jeu.getJoueurs().get(i).getPort(), message);

                out.println(jeu.afficheJeu());
                out.println();
                out.println("Lancement des dés.");
                int x = rd.nextInt(6) + 1;
                int y = rd.nextInt(6) + 1;
                out.println("Résultat du lancement :");
                List<Integer> des = new ArrayList<>();
                des.add(x);
                des.add(y);

                message = nwm.getPacketsTcp().get("PIRD").build(des, placementDest(x, y, i), partieId);
                String rep = ThreadTool.taskPacketTcp(jeu.getJoueurs().get(i).getIp(),
                        jeu.getJoueurs().get(i).getPort(), message);
                // TODO

                int destEntre = (int) nwm.getPacketsTcp().get("PICD").getValue(rep, 1);
                int persEntre = placementPerso(i, destEntre);
                jeu.placePerso(jeu.getJoueurs().get(i), persEntre, destEntre);
                out.println(RETOUR_LIGNE);
            }
        }
    }

    private int nbPlace() {
        int tmp = 0;
        for (Lieu l : jeu.getLieux().values())
            for (Personnage ignored : l.getPersonnage())
                tmp++;

        return tmp;
    }

    private List<Integer> persoPlace(Joueur joueur) {
        List<Integer> tmp = new ArrayList<>();
        for (int b = 0; b < joueur.getPersonnages().size(); b++) {
            if (joueur.getPersonnages().get(b).getMonLieu() == null) {
                tmp.add(b);
            }
        }
        return tmp;
    }

    private boolean estPlace(Joueur joueur, TypePersonnage type) {
        for (Lieu l : jeu.getLieux().values())
            for (Personnage p : l.getPersonnage())
                if (p.getJoueur() == joueur && p.getType() == type)
                    return true;

        return false;
    }

    private List<Integer> placementDest(int x, int y, int i) {
        List<Integer> posi = new ArrayList<>();
        if (jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
            for (Lieu l : jeu.getLieux().values())
                posi.add(l.getNum());
            // TODO CHOIX AU JOUEUR OU C PAS FULL SAUF 1 POSSOBILITE

            // TODO CHOIX DE LA POSITION
            // TODO POS = NB LIEUX POSSIBLE
        } else if (jeu.getLieux().get(x).isFull() && !jeu.getLieux().get(y).isFull()) {
            posi.add(y);
        } else if (!jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
            posi.add(x);
        } else {
            posi.add(x);
            posi.add(y);
        }

        return posi;
    }

    private int placementPerso(int i, int destEntre) {
        out.println();
        String s = "";
        ArrayList<Integer> num = new ArrayList<>();
        for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
            if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
                num.add(b);
                s += b + "     " + jeu.getJoueurs().get(i).getPersonnages().get(b) + "\n";
            }
        }
        if (num.size() == 1) {
            return num.get(0);
        }
        out.println("Joueur " + i + " choisit un personage a placer à " + jeu.getLieux().get(destEntre));
        out.println(s);
        // int persEntre = sc.nextInt();
        int persEntre = new Random().nextInt(3); // temporaire
        out.println(persEntre); // temporaire
        while (!num.contains(persEntre)) {
            out.println(RETOUR_LIGNE);
            System.out.println(jeu.afficheJeu());
            out.println();
            out.println("Numéro incorect !\n");
            out.println("Joueur " + i + " choisit un personage a placer à " + jeu.getLieux().get(destEntre));
            for (Integer integer : num) {
                out.println(integer + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(integer));
            }
            // persEntre = sc.nextInt();
            persEntre = new Random().nextInt(3); // temporaire
            out.println(persEntre); // temporaire
        }

        // TODO NUM liste des perso a cette endroit

        return persEntre;
    }

    /**
     *
     */
    public void mortJoueur() {
        for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
            if (this.jeu.getJoueurs().get(i).isEnVie() && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
                this.jeu.getJoueurs().get(i).setEnVie(false);
                out.println(this.jeu.getJoueurs().get(i) + " est mort!");
                int dest;
                ArrayList<Integer> num = new ArrayList<>();
                out.println();
                out.println(this.jeu.getJoueurs().get(i) + " choisis un lieu ou ajouter un Zombie:");
                for (int j = 1; j < 7; j++) {
                    if (this.jeu.getLieux().get(j).isOuvert()) {
                        num.add(j);
                        out.println(j + "\t" + this.jeu.getLieux().get(j));
                    }
                }
                // TODO ENVOYER LIEUX POSSIBLE POUR METTRE ZOMBIE (NUM)
                // dest = sc.nextInt();
                dest = new Random().nextInt(6) + 1; // temporaire
                out.println(dest); // temporaire
                while (!num.contains(dest)) {
                    out.println(RETOUR_LIGNE);
                    System.out.println(jeu.afficheJeu());
                    out.println();
                    out.println("Numéro incorect !\n");
                    out.println(this.jeu.getJoueurs().get(i) + " choisis un lieu ou ajouter un Zombie:");
                    for (Integer integer : num) {
                        out.println(integer + "\t" + this.jeu.getLieux().get(integer));
                    }
                    // dest = sc.nextInt();
                    dest = new Random().nextInt(6) + 1; // temporaire
                    out.println(dest); // temporaire
                }
                this.jeu.getLieux().get(dest).addZombie();
            }
        }
        out.println(RETOUR_LIGNE);
    }

    /**
     * Detecte et affiche la fin du jeu
     *
     * @return si c'est la fin du jeu
     */
    public boolean finJeu() {
        ArrayList<Lieu> lieu = new ArrayList<>();
        int nbPerso = 0;
        for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
            if (this.jeu.getJoueurs().get(i).isEnVie()) {
                nbPerso += this.jeu.getJoueurs().get(i).getPersonnages().size();
                for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
                    if (!lieu.contains(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu())) {
                        lieu.add(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu());
                    }
                }
            }
        }
        if ((lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4)) || nbPerso <= 4) {
            for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
                if (this.jeu.getJoueurs().get(i).isEnVie()
                        && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
                    this.jeu.getJoueurs().get(i).setEnVie(false);
                }
            }
            System.out.println(jeu.afficheJeu());
            out.println();
            int pointVainqueur = 0;
            ArrayList<Joueur> vainqueur = new ArrayList<>();
            for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
                int point = 0;
                if (this.jeu.getJoueurs().get(i).isEnVie()) {
                    for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
                        point += this.jeu.getJoueurs().get(i).getPersonnages().get(j).getPoint();
                    }
                    if (point > pointVainqueur) {
                        pointVainqueur = point;
                        vainqueur.clear();
                        vainqueur.add(jeu.getJoueurs().get(i));
                    } else if (point == pointVainqueur) {
                        vainqueur.add(jeu.getJoueurs().get(i));
                    }
                    out.println(" Point " + this.jeu.getJoueurs().get(i) + ": " + point);
                } else {
                    out.println(" Point " + this.jeu.getJoueurs().get(i) + "(mort): " + point);
                }
                out.println();
            }
            if (vainqueur.size() == 1) {
                out.println(">>>>> " + vainqueur.get(0) + " a gagné ! <<<<<");
            } else {
                StringBuilder s = new StringBuilder(">>>>> ");
                for (Joueur joueur : vainqueur) {
                    s.append("  ").append(joueur);
                }
                s.append(" sont vainqeurs à égalité! <<<<<");
                out.println(s);
            }
            return true;
        }
        return false;

    }

    public String getPartieId() {
        return partieId;
    }

    public int getIntPartieId() {
        return intPartieId;
    }

    public String getNomPartie() {
        return nomPartie;
    }

    public int getNbjtotal() {
        return nbjtotal;
    }

    public int getNbjr() {
        return nbjr;
    }

    public int getNbjv() {
        return nbjv;
    }

    public int getNbjractuel() {
        return nbjractuel;
    }

    public int getNbjvactuel() {
        return nbjvactuel;
    }
}