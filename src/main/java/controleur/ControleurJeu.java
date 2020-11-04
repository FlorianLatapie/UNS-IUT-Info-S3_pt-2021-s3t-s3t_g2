package controleur;

import ihm.eventListener.Initializer;
import jeu.*;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.socket.TcpClientSocket;
import reseau.tool.NetworkTool;
import reseau.tool.PacketTool;
import reseau.tool.ThreadTool;
import reseau.type.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.*;

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
    /* Réglage de la partie */
    private final String partieId; // "P" + Numero de la partie Ex : P9458
    private final int intPartieId;
    private final String nomPartie; // Nom de la partie
    private final int nbjtotal; // Nombre de joueurs total
    private final int nbjr; // Nombre de joueurs réel max
    private final int nbjv; // Nombre de joueurs virtuel max
    private int nbjractuel; // Nombre de joueurs réel actuellement connecté
    private int nbjvactuel; // Nombre de joueurs virtuel actuellement connecté
    private final int port;
    private int nextJoueurId;

    private static final String RETOUR_LIGNE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    private Jeu jeu;
    private final List<Joueur> jmort;

    private boolean azld = false;
    private boolean cddcv = false;
    private int cddj = 0;

    private final List<String> tempPaquet;
    private Status status;

    final NetWorkManager nwm;

    ArrayList<Joueur> joueurs;

    ArrayList<Integer> lieuZombie;
    Initializer initializer;
    boolean couleurPret = false;
    Random rd = new Random();
    private int numeroTour = 1;

    public ControleurJeu(String nom, int njr, int njv, Initializer initializer) throws IOException {
        if (njr + njv > 6 || njr + njv < 3)
            throw new IllegalArgumentException("Mauvais nombre de joueur");
        this.initializer = initializer;
        this.jmort = new ArrayList<>();
        this.tempPaquet = new ArrayList<>();
        this.nomPartie = nom;
        this.nbjv = njv;
        this.nbjr = njr;
        this.nbjtotal = nbjv + nbjr;
        lieuZombie = new ArrayList<>();
        joueurs = new ArrayList<>();
        nwm = new NetWorkManager(this);
        status = Status.ATTENTE;
        initReseau();
        this.port = nwm.getTcpPort();
        this.intPartieId = new Random().nextInt(10000000);
        this.partieId = "P" + intPartieId;
        init();
    }

    private synchronized void init() {
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

            if (initializer != null) initializer.joueurPret();

            status = Status.COMPLETE;
            joueurs.get(0).setChefDesVigiles(true);
            jeu = new Jeu(joueurs);
            if (initializer != null) initializer.nomJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
            if (initializer != null) initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
            if (initializer != null) initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
            if (initializer != null) initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
            if (initializer != null) initializer.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
            if (initializer != null) initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
            if (initializer != null) initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
            if (initializer != null) initializer.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
            //WaitForColor
            while (!couleurPret) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            demarerJeu();
        });
    }

    public void setJoueurCouleur(List<Couleur> couleurs) {
        this.couleurPret = true;
        for (int i = 0; i < joueurs.size(); i++)
            joueurs.get(i).setCouleur(couleurs.get(i));
    }

    // TODO ENLEVER LES JOUEURS MORT ?? IT
    private List<Couleur> getJoueursCouleurs() {
        List<Couleur> lc = new ArrayList<>();
        lc.add(jeu.getChefVIgile().getCouleur());
        for (Joueur j : jeu.getJoueurs().values())
            if (j != jeu.getChefVIgile() && j.isEnVie())
                lc.add(j.getCouleur());

        return lc;
    }

    public boolean joueurConnect() {
        return joueurs.size() == this.nbjtotal && jeu != null;
    }

    private void demarerJeu() {
        // TODO UN OU PLUSIEURS LIEUX FERME
        // TODO 3 ou 4 PION | UN OU PLUSIEURS LIEUX FERME
        out.println(jeu.afficheJeu());

        String m = nwm.getPacketsTcp().get("IP").build(getJoueursListeNo(), getCouleurJoueursListeNo(), 0, 3, partieId);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);

        // TODO ATTENDRE TOUS LES JOUEURS
        // TODO (COMPLETE ACP)
        this.placementPersonnage();
        this.jeu.resultatChefVigile(jeu.getJoueurs().get(0));
        lieuZombie = arriveZombie();
        this.jeu.entreZombie(lieuZombie);
        m = nwm.getPacketsTcp().get("PIPZ").build(lieuZombie, partieId);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);

        out.println(jeu.afficheJeu());
        out.println("YES");
        this.start();
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

    public boolean estAccepte(TypeJoueur typeJoueur) {
        if (typeJoueur == TypeJoueur.JR && nbjractuel == nbjr)
            return false;
        if (typeJoueur == TypeJoueur.BOT && nbjvactuel == nbjv)
            return false;

        return true;
    }

    public String ajouterJoueur(InetAddress ip, int port, String nom, TypeJoueur typeJoueur) {
        if (typeJoueur == TypeJoueur.JR && nbjractuel == nbjr)
            return "";
        if (typeJoueur == TypeJoueur.BOT && nbjvactuel == nbjv)
            return "";

        Joueur joueur = new Joueur(getNewJoueurId(), ip, port, nom);
        if (typeJoueur == TypeJoueur.JR)
            nbjractuel++;
        else if (typeJoueur == TypeJoueur.BOT)
            nbjvactuel++;
        joueurs.add(joueur);
        return joueur.getJoueurId();
    }

    private int getNewJoueurId() {
        int tmp = nextJoueurId;
        nextJoueurId++;
        return tmp;
    }

    private List<String> getJoueursListeNo() {
        List<String> noms = new ArrayList<>();
        for (Joueur j : joueurs)
            noms.add(j.getNom());

        return noms;
    }

    private List<Couleur> getCouleurJoueursListeNo() {
        List<Couleur> couleurs = new ArrayList<>();
        for (Joueur j : joueurs)
            couleurs.add(j.getCouleur());

        return couleurs;
    }

    /**
     * Execute le déroulement d'une parite
     */
    public void start() {
    	if (initializer != null) initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
    	if (initializer != null) initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
    	if (initializer != null) initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
    	if (initializer != null) initializer.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
    	if (initializer != null) initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
    	if (initializer != null) initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
    	if (initializer != null) initializer.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
    	
        String m = nwm.getPacketsTcp().get("IT").build(jeu.getChefVIgile().getCouleur(), getJoueursCouleurs(), partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);

        fouilleCamion();
        electionChefVigi();
        lieuZombie = arriveZombie();
        ArrayList<Integer> destination = new ArrayList<>();
        phasechoixDestination(destination);
        phaseDeplacementPerso(destination, lieuZombie);
        if (this.finJeu())
            return;
        jeu.entreZombie(lieuZombie);
        if (initializer != null) initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
        jeu.fermerLieu();
    	if (initializer != null) initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
    	if (initializer != null) initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
        if (this.finJeu())
            return;
        if (!attaqueZombie())
            return;
        jmort.clear();
        mortJoueur();
        numeroTour++;
        start();
    }

    /**
     * Affiche le joueur qui fouille le camion
     */
    private void fouilleCamion() {
        //TODO TAILLE DE PIOCHE A 0
        String m = nwm.getPacketsTcp().get("PFC").build(getJoueursCouleurs(), 0, partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        // TODO PREVENIR QUI FOUILLE LE CAMION=
        if (!jeu.getLieux().get(4).afficheJoueurSurLieu().isEmpty()) {
            Joueur j = voteJoueur(4);
            out.println(j + " fouille le camion!");
            out.println("Le camion est vide.");
            out.println(RETOUR_LIGNE);
        }
        // TODO CARTE NUL
        m = nwm.getPacketsTcp().get("RFC").build(CarteType.NUL, CarteType.NUL, CarteType.NUL, partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
    }

    private List<PionCouleur> getPersosLieu(int i) {
        List<PionCouleur> pc = new ArrayList<>();
        Lieu l = jeu.getLieux().get(i);
        for (Personnage p : l.getPersonnage())
            pc.add(PionCouleur.valueOf(p.getJoueur().getCouleur().toString().substring(0, 1) + p.getPoint()));

        return pc;
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
        String m = nwm.getPacketsTcp().get("PECV").build(getPersosLieu(5), partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        if (!jeu.getLieux().get(5).afficheJoueurSurLieu().isEmpty()) {
            Joueur j = voteJoueur(5);
            jeu.resultatChefVigile(j);
            out.println(j + " est le nouveau chef des vigiles!");
            jeu.setNewChef(true);
            m = nwm.getPacketsTcp().get("RECV").build(jeu.getChefVIgile().getCouleur(), partieId, numeroTour);
            for (Joueur joueur : jeu.getJoueurs().values())
                TcpClientSocket.connect(joueur.getIp(), joueur.getPort(), m, null, 0);
        } else {
            out.println("Pas de nouveau chef des vigiles!");
            jeu.setNewChef(false);
            m = nwm.getPacketsTcp().get("RECV").build(Couleur.NUL, partieId, numeroTour);
            for (Joueur j : jeu.getJoueurs().values())
                TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        }
        if (initializer != null) initializer.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
        out.println(RETOUR_LIGNE);
    }

    /**
     * Definie l'arrivé des zombies et l'affiche pour le chef des vigiles
     *
     * @return liste des numéro des lieux d'arrivé des zomibie
     */
    private ArrayList<Integer> arriveZombie() {
        VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
        String m = nwm.getPacketsTcp().get("PAZ").build(jeu.getChefVIgile().getCouleur(), ve, partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        while (!azld)
            ;
        azld = false;
        return lieuZombie;
    }

    private void phasechoixDestination(ArrayList<Integer> destination) {
        VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
        String m = nwm.getPacketsTcp().get("PCD").build(jeu.getChefVIgile().getCouleur(), ve, partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        if (jeu.getNewChef() == true) {
            while (!cddcv) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cddcv = false;
            m = getPaquetTemp("CDDCV");
            int dest = (int) nwm.getPacketsTcp().get("CDDCV").getValue(m, 1);
            destination.add(dest);

            m = nwm.getPacketsTcp().get("CDCDV").build(jeu.getChefVIgile().getCouleur(), dest, partieId, numeroTour);
            for (Joueur j : jeu.getJoueurs().values())
                if (!(j.isChefDesVigiles() && ve == VigileEtat.NE))
                    TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        }

        int nb = nbjtotal - jeu.getNbMort();
        nb += jeu.getNewChef() ? -1 : 0;

        out.println("mes" + nb);
        while (!(cddj == nb)) {
            try {
                Thread.sleep(100);
                out.println(cddj);
                out.println(tempPaquet.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cddj = 0;

        out.println("Yep 123 " + nb);
        HashMap<String, Integer> idj = new HashMap<>();
        while (true) {
            String message = getPaquetTemp("CDDJ");
            if (message == "")
                break;
            out.println("Lo " + message);
            String id = (String) nwm.getPacketsTcp().get("CDDJ").getValue(message, 4);

            int idJoueur = (int) nwm.getPacketsTcp().get("CDDJ").getValue(message, 1);
            idj.put(id, idJoueur);
        }

        if (!jeu.getNewChef())
            for (Map.Entry<String, Integer> d : idj.entrySet())
                if (d.getKey().equals(jeu.getChefVIgile().getJoueurId()))
                    destination.add(d.getValue());

        for (Joueur j : jeu.getJoueurs().values())
            if (!j.isChefDesVigiles() && j.isEnVie())
                for (Map.Entry<String, Integer> d : idj.entrySet())
                    if (d.getKey().equals(j.getJoueurId()))
                        destination.add(d.getValue());

        System.out.println("PtR5");

        m = nwm.getPacketsTcp().get("CDFC").build(partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        for (Joueur j : this.jeu.getJoueurs().values()) {
            if (jmort.contains(j)) {
                m = nwm.getPacketsTcp().get("CDZVI").build(partieId, numeroTour);
                String rep = TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
                int dvz = (int) nwm.getPacketsTcp().get("CDDZVJE").getValue(rep, 1);
                this.jeu.getLieux().get(dvz).addZombie();
            }
        }
    }

    private String getPaquetTemp(String key) {
        for (String str : tempPaquet)
            if (PacketTool.getKeyFromMessage(str).equals(key)) {
                String tmp = str;
                tempPaquet.remove(str);
                return tmp;
            }

        return "";
    }

    private void phaseDeplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> zombie) {
        String m = nwm.getPacketsTcp().get("PDP").build(jeu.getChefVIgile().getCouleur(), destination, zombie, jeu.getLieuxFermes(), partieId, numeroTour);
        for (Joueur j : jeu.getJoueurs().values())
            TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
        int compteur = 0;
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            if (jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
                out.println(jeu.afficheJeu());

                m = nwm.getPacketsTcp().get("DPD").build(destination.get(compteur), jeu.allChoixPossible(jeu.getJoueurs().get(i)), partieId, numeroTour);
                String message = TcpClientSocket.connect(jeu.getJoueurs().get(i).getIp(), jeu.getJoueurs().get(i).getPort(), m, null, 0);
                int dest = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 1);
                int pion = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 2);
                this.jeu.deplacePerso(jeu.getJoueurs().get(i), valeurToIndex(pion), dest);
                if (initializer != null) initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
                if (finJeu()) {
                	return;
                }
                this.jeu.fermerLieu();
                compteur += 1;

                m = nwm.getPacketsTcp().get("DPI").build(jeu.getJoueurs().get(i).getCouleur(), dest, pion, CarteType.NUL, partieId, numeroTour);
                for (Joueur j : jeu.getJoueurs().values()) {
                    if (j != jeu.getJoueurs().get(i))
                        TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
                }
            }
        }
        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            if (!jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
                out.println(jeu.afficheJeu());

                m = nwm.getPacketsTcp().get("DPD").build(destination.get(compteur), jeu.allChoixPossible(jeu.getJoueurs().get(i)), partieId, numeroTour);
                String message = TcpClientSocket.connect(jeu.getJoueurs().get(i).getIp(), jeu.getJoueurs().get(i).getPort(), m, null, 0);
                int dest = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 1);
                int pion = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 2);
                this.jeu.deplacePerso(jeu.getJoueurs().get(i), valeurToIndex(pion), dest);
                if (initializer != null) initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
                if (finJeu()) {
                	return;
                }
                this.jeu.fermerLieu();
                compteur += 1;

                m = nwm.getPacketsTcp().get("DPI").build(jeu.getJoueurs().get(i).getCouleur(), dest, pion, CarteType.NUL, partieId, numeroTour);
                for (Joueur j : jeu.getJoueurs().values()) {
                    if (j != jeu.getJoueurs().get(i))
                        TcpClientSocket.connect(j.getIp(), j.getPort(), m, null, 0);
                }
            }
        }
    }

    //TODO AJOUTER MESSAGE STRING EN PARAMETRE
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
            //TODO affichageConsoleDeplacment(joueur, destination);
            out.println(s);
            // choixPerso = sc.nextInt();
            choixPerso = new Random().nextInt(4); // temporaire
            System.out.println(jeu.afficheJeu());
            out.println(choixPerso); // temporaire
            while (!pers.contains(choixPerso)) {
                out.println();
                out.println("Numéro Incorect!\n");
                out.println(joueur + " choisit un personage a déplacer à " + jeu.getLieux().get(destination));
                for (Integer per : pers) {
                    out.println(per + "\t" + joueur.getPersonnages().get(per));
                }
                // choixPerso = sc.nextInt();
                choixPerso = new Random().nextInt(4); // temporaire
                out.println(choixPerso); // temporaire
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //TODO choixPerso = la réponse DPR de DPD (PION); -> message
        out.println(destination);
        if (!jeu.getLieux().get(destination).isFull()) {
            if (jeu.getLieux().get(destination).isOuvert())
                jeu.deplacePerso(joueur, choixPerso, destination);
        } else {
            jeu.deplacePerso(joueur, choixPerso, 4);
        }

        out.println(RETOUR_LIGNE);
        return !this.finJeu();
    }

    private void affichageConsoleDeplacement(Joueur joueur, int destination) {
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
        List<Integer> nb = jeu.lastAttaqueZombie();

        String me = nwm.getPacketsTcp().get("PRAZ").build(nb.get(0), nb.get(1), jeu.getLieuxOuverts(), jeu.getNbZombieLieux(), jeu.getNbPionLieux(), partieId, numeroTour);
        for (Joueur joueur : jeu.getJoueurs().values())
            TcpClientSocket.connect(joueur.getIp(), joueur.getPort(), me, null, 0);

        for (int i = 1; i < 7; i++) {
            if (jeu.getLieux().get(i).isOuvert()) {
                if (i == 4) {// si parking
                    for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
                        if (!jeu.getLieux().get(i).getPersonnage().isEmpty()) {
                            System.out.println(jeu.afficheJeu());
                            Joueur jou = this.voteJoueur(4);
                            String m = nwm.getPacketsTcp().get("RAZDS").build(i, partieId, numeroTour);

                            String rep = TcpClientSocket.connect(jou.getIp(), jou.getPort(), m, null, 0);
                            PionCouleur pionCou = (PionCouleur) nwm.getPacketsTcp().get("RAZCS").getValue(rep, 2);
                            int pion = PpTools.getPionByValue(pionCou);

                            jeu.sacrifie(jou, valeurToIndex(pion));
                        	if (initializer != null) initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
                            out.println(RETOUR_LIGNE);
                            jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);

                            m = nwm.getPacketsTcp().get("RAZIF").build(i, pionCou, jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
                            for (Joueur joueur : jeu.getJoueurs().values())
                                TcpClientSocket.connect(joueur.getIp(), joueur.getPort(), m, null, 0);
                        }
                        if (this.finJeu()) {
                            return false;
                        }
                    }
                } else if (jeu.getLieux().get(i).estAttaquable()) {
                    String s = "";
                    System.out.println(jeu.afficheJeu());
                    Joueur jou = this.voteJoueur(jeu.getLieux().get(i).getNum());
                    String m = nwm.getPacketsTcp().get("RAZDS").build(i, partieId, numeroTour);
                    String rep = TcpClientSocket.connect(jou.getIp(), jou.getPort(), m, null, 0);

                    PionCouleur pionCou = (PionCouleur) nwm.getPacketsTcp().get("RAZCS").getValue(rep, 2);
                    int pion = PpTools.getPionByValue(pionCou);

                    jeu.sacrifie(jou, valeurToIndex(pion));
                	if (initializer != null) initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
                    out.println(RETOUR_LIGNE);
                    jeu.getLieux().get(i).setNbZombies(0);

                    m = nwm.getPacketsTcp().get("RAZIF").build(i, pionCou, jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
                    for (Joueur joueur : jeu.getJoueurs().values()) {
                        TcpClientSocket.connect(joueur.getIp(), joueur.getPort(), m, null, 0);
                    }
                }
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

                List<Integer> listePion = placementDest(x, y);
                message = nwm.getPacketsTcp().get("PIRD").build(des, listePion, partieId);
                String rep = ThreadTool.taskPacketTcp(jeu.getJoueurs().get(i).getIp(),
                        jeu.getJoueurs().get(i).getPort(), message);
                // TODO

                int destEntre = (int) nwm.getPacketsTcp().get("PICD").getValue(rep, 1);
                int persEntre = (int) nwm.getPacketsTcp().get("PICD").getValue(rep, 2);
                jeu.placePerso(jeu.getJoueurs().get(i), valeurToIndex(persEntre), destEntre);

                message = nwm.getPacketsTcp().get("PIIG").build(jeu.getJoueurs().get(i).getCouleur(), des, listePion, destEntre, persEntre, partieId);
                for (Joueur j : jeu.getJoueurs().values())
                    if (j != jeu.getJoueurs().get(i))
                        ThreadTool.taskPacketTcp(j.getIp(),
                                j.getPort(), message);

                out.println(RETOUR_LIGNE);
            }
        }
    }

    private int valeurToIndex(int valeur) {
        switch (valeur) {
            case 7:
                return 0;
            case 5:
                return 1;
            case 3:
                return 2;
            case 1:
                return 3;
            default:
                throw new IllegalStateException("Unexpected value: " + valeur);
        }
    }

    private int nbPlace() {
        int tmp = 0;
        for (Lieu l : jeu.getLieux().values())
            tmp += l.getPersonnage().size();

        return tmp;
    }

    private List<Integer> persoPlace(Joueur joueur) {
        List<Integer> tmp = new ArrayList<>();
        for (int b = 0; b < joueur.getPersonnages().size(); b++) {
            if (joueur.getPersonnages().get(b).getMonLieu() == null) {
                tmp.add(joueur.getPersonnages().get(b).getPoint());
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

    private List<Integer> placementDest(int x, int y) {
        List<Integer> posi = new ArrayList<>();
        if (jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
            for (Lieu l : jeu.getLieux().values())
                if (!l.isFull() && l.isOuvert())
                    posi.add(l.getNum());
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

        return persEntre;
    }

    /**
     *
     */
    public void mortJoueur() {
        for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
            if (this.jeu.getJoueurs().get(i).isEnVie() && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
                this.jeu.getJoueurs().get(i).setEnVie(false);
                jmort.add(this.jeu.getJoueurs().get(i));
                out.println(this.jeu.getJoueurs().get(i) + " est mort!");
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
            CondType cond;
            if (lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4))
                cond = CondType.LIEUX;
            else
                cond = CondType.PION;

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
            Joueur gagnantNotFair = vainqueur.get(new Random().nextInt(vainqueur.size()));
            String message = nwm.getPacketsTcp().get("FP").build(cond, gagnantNotFair.getCouleur(), partieId, numeroTour);
            for (Joueur j : jeu.getJoueurs().values())
                ThreadTool.taskPacketTcp(j.getIp(),
                        j.getPort(), message);

            if (initializer != null) initializer.finPartie();
            if (initializer != null) initializer.getGagnant(gagnantNotFair);
            nwm.getUdpSocket().stop();
            nwm.getTcpServerSocket().stop();

            return true;
        }
        return false;
    }

    public String getPartieId() {
        return partieId;
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

    public List<String> getTempPaquet() {
        return tempPaquet;
    }

    public void setAzld(boolean azld) {
        this.azld = azld;
    }

    public void setCddcv(boolean cddcv) {
        this.cddcv = cddcv;
    }

    public void addCddj() {
        this.cddj++;
    }

    public int getNumeroTour() {
        return numeroTour;
    }

    public Status getStatus() {
        return status;
    }

    public void setLieuZombie(ArrayList<Integer> lieuZombie) {
        this.lieuZombie = lieuZombie;
    }
}