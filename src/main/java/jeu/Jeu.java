package jeu;

import temp.CarteAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ihm.eventListener.LieuxListener;

/**
 * <h1>Le plateau de jeu</h1>
 *
 * @author Alex
 * @author Aurélien
 * @version 0.1
 * @since 04/10/2020
 */
public class Jeu {
    HashMap<Integer, Lieu> lieux;
    HashMap<Integer, Joueur> joueurs;
    ArrayList<CarteAction> cartes;
    boolean newChef;
    private List<LieuxListener> listeners = new ArrayList<LieuxListener>();

    public void addListener(LieuxListener toAdd) {
        listeners.add(toAdd);
    }

    public void lieuxChanged() {
        // Notify everybody that may be interested
        for (LieuxListener hl : listeners)
            hl.lieuxChanged(lieux);
    }

    public boolean getNewChef() {
        return newChef;
    }

    public void setNewChef(Boolean newChef) {
        this.newChef = newChef;
    }

    /**
     * @param listeJoueursInitiale La liste des joueurs initiaux
     */
    public Jeu(List<Joueur> listeJoueursInitiale) {
        lieux = new HashMap<>();
        joueurs = new HashMap<>();
        cartes = new ArrayList<>();
        newChef = false;
//		initCarte();
        for (int i = 0; i < listeJoueursInitiale.size(); i++)
            joueurs.put(i, listeJoueursInitiale.get(i));
        initJoueurs();
        initLieu();
    }

    public int getNbMort() {
        int i = 0;
        for (Joueur j : joueurs.values())
            if (!j.isEnVie())
                i++;

        return i;
    }

    /**
     * Initialise les cartes du jeu
     */

    /*
     * private void initCarte() {
     *
     * Materiel materiel1 = new Materiel(); Menace menace1 = new Menace(); Sprint
     * sprint1 = new Sprint(); CameraDeSecurite camSecu1 = new CameraDeSecurite();
     * Materiel materiel2 = new Materiel(); Menace menace2 = new Menace(); Sprint
     * sprint2 = new Sprint(); CameraDeSecurite camSecu2 = new CameraDeSecurite();
     * Materiel materiel3 = new Materiel(); Menace menace3 = new Menace(); Sprint
     * sprint3 = new Sprint(); CameraDeSecurite camSecu3 = new CameraDeSecurite();
     * Cachette cachette1 = new Cachette(); Cachette cachette2 = new Cachette();
     * Cachette cachette3 = new Cachette(); Arme bate = new Arme(); Arme grenade =
     * new Arme(); Arme tronconeuse = new Arme(); Arme fusil = new Arme(); Arme
     * revolver = new Arme(); Arme hache = new Arme(); cartes.add(hache);
     * cartes.add(revolver); cartes.add(tronconeuse); cartes.add(fusil);
     * cartes.add(grenade); cartes.add(bate); cartes.add(sprint1);
     * cartes.add(sprint2); cartes.add(sprint3); cartes.add(menace1);
     * cartes.add(menace2); cartes.add(menace3); cartes.add(materiel1);
     * cartes.add(materiel2); cartes.add(materiel3); cartes.add(cachette1);
     * cartes.add(cachette2); cartes.add(cachette3); cartes.add(camSecu1);
     * cartes.add(camSecu2); cartes.add(camSecu3);
     *
     * }
     */

    /**
     * Initialise les lieux du jeu
     */
    private void initLieu() {
        Lieu toilettes = new Lieu(1);
        Lieu cachou = new Lieu(2);
        Lieu megatoys = new Lieu(3);
        Lieu parking = new Lieu(4);
        Lieu pCsecu = new Lieu(5);
        Lieu supermarche = new Lieu(6);

        if (joueurs.size() < 4)
            cachou.setOuvert(false);

        lieux.put(1, toilettes);
        lieux.put(2, cachou);
        lieux.put(3, megatoys);
        lieux.put(4, parking);
        lieux.put(5, pCsecu);
        lieux.put(6, supermarche);
    }

    /**
     * Initialise les joueurs du jeu
     */
    private void initJoueurs() {
        if (joueurs.size() < 4)
            for (int i = 0; i < joueurs.size(); i++) {
                HashMap<Integer, Personnage> dp = new HashMap<>();
                dp.put(0, new LaBlonde(joueurs.get(i)));
                dp.put(1, new LaBrute(joueurs.get(i)));
                dp.put(2, new LeTruand(joueurs.get(i)));
                dp.put(3, new LaFillette(joueurs.get(i)));
                joueurs.get(i).setPersonnages(dp);
            }
        else
            for (int i = 0; i < joueurs.size(); i++) {
                HashMap<Integer, Personnage> dp = new HashMap<>();
                dp.put(0, new LaBlonde(joueurs.get(i)));
                dp.put(1, new LaBrute(joueurs.get(i)));
                dp.put(2, new LeTruand(joueurs.get(i)));
                joueurs.get(i).setPersonnages(dp);
            }
    }

    /**
     * Applique les résultats de la fouille du camion : attribut la carte piocheur
     * au piocheur, la carte receveur au receveur et sort les 3 cartes du jeu
     *
     * @param piocheur      le piocheur
     * @param receveur      le receveur
     * @param cartePiocheur la carte que le piocheur a choisi de garder
     * @param carteReceveur la carte que le piocheur a choisi de donner au receveur
     * @param carteDefause  la carte que le piocheur n'a pas attribuer
     */
    public void fouilleCamion(Joueur piocheur, Joueur receveur, CarteAction cartePiocheur, CarteAction carteReceveur,
                              CarteAction carteDefause) {
        /*
         * for (int i = 0; i < joueurs.size(); i++) { if
         * (joueurs.get(i).equals(piocheur))
         * joueurs.get(i).getCartes().add(cartePiocheur); if
         * (joueurs.get(i).equals(receveur))
         * joueurs.get(i).getCartes().add(carteReceveur); }
         * cartes.remove(cartePiocheur); cartes.remove(carteDefause);
         * cartes.remove(carteReceveur);
         */
    }

    /**
     * Applique le nouveau chef des vigiles si un chef est élu
     *
     * @param nouveauChef le joueurs élu en tant que chef des vigiles
     */
    public void resultatChefVigile(Joueur nouveauChef) {
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).equals(nouveauChef))
                joueurs.get(i).setChefDesVigiles(true);
            else
                joueurs.get(i).setChefDesVigiles(false);
        }
    }

    /**
     * Supprime le chef des vigiles
     */
    public void resultatChefVigile() {
        for (int i = 0; i < joueurs.size(); i++) {
            joueurs.get(i).setChefDesVigiles(false);
        }
    }

    public List<Integer> getLieuxFermes() {
        List<Integer> tmp = new ArrayList<>();
        for (Lieu l : lieux.values())
            tmp.add(l.getNum());

        return tmp;
    }

    /**
     * retourne le chef des vigiles
     */
    public Joueur getChefVIgile() {
        for (Joueur joueur : this.getJoueurs().values()) {
            if (joueur.isChefDesVigiles()) {
                return joueur;
            }
        }
        return null;
    }

    /**
     * Lance l'ajout des zombies dans les lieux du jeu et ferme si nécessaire les
     * lieux
     *
     * @param listeInt id des lieux où les nouveaux zombie arrivent
     */
    public void entreZombie(List<Integer> listeInt) {
        for (int i = 0; i < listeInt.size(); i++) {
            lieux.get(listeInt.get(i)).addZombie();
        }
        lieuxChanged();
    }

    /**
     * Applique la derniere attaque des zombies donc ajoute un zombie au lieu qui a
     * le plus de blondes et un zombie au lieu qui a le plus de personnages
     */
    public void lastAttaqueZombie() {
        int idLieuP = 1;
        int idLieuB = 1;
        boolean maxP = true;
        boolean maxB = true;
        for (int i = 2; i < 7; i++) {
            if (lieux.get(i).getPersonnage().size() > lieux.get(idLieuP).getPersonnage().size()) {
                idLieuP = i;
            } else if (lieux.get(i).getPersonnage().size() == lieux.get(idLieuP).getPersonnage().size())
                maxP = false;
        }

        for (int i = 2; i < 7; i++) {
            if (lieux.get(i).getBlonde().size() > lieux.get(idLieuB).getBlonde().size()) {
                idLieuB = i;
            } else if (lieux.get(i).getBlonde().size() == lieux.get(idLieuB).getBlonde().size())
                maxB = false;
        }
        if (maxP)
            lieux.get(idLieuP).addZombie();
        if (maxB)
            lieux.get(idLieuB).addZombie();

        lieuxChanged();
    }

    /**
     * Deplace le personnage du joueur au lieu choisi
     *
     * @param joueur     le joueur dont le personnage est déplacé
     * @param choixPerso le personnage qui est déplacé
     * @param dest       le lieu de destination du personnage
     */
    public void deplacePerso(Joueur joueur, Integer choixPerso, Integer dest) {
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).equals(joueur)) {
                int l = joueurs.get(i).getPersonnages().get(choixPerso).getMonLieu().getNum();
                joueurs.get(i).getPersonnages().get(choixPerso).changerDeLieux(lieux.get(dest));
                this.lieux.get(dest).addPersonnage(joueurs.get(i).getPersonnages().get(choixPerso));
                this.lieux.get(l).getPersonnage().remove(joueurs.get(i).getPersonnages().get(choixPerso));
            }
        }
        lieuxChanged();

    }

    /**
     * Place le personnage du joueur au lieu choisi
     *
     * @param joueur     le joueur dont le personnage est placé
     * @param choixPerso le personnage qui est déplacé
     * @param dest       le lieu de destination du personnage
     */
    public void placePerso(Joueur joueur, Integer choixPerso, Integer dest) {
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).equals(joueur)) {
                joueurs.get(i).getPersonnages().get(choixPerso).changerDeLieux(lieux.get(dest));
                this.lieux.get(dest).addPersonnage(joueurs.get(i).getPersonnages().get(choixPerso));
            }
        }
        lieuxChanged();

    }

    /**
     * Supprime le personnage du jeu
     *
     * @param joueur     le joueur possedant le personnage sacrifié
     * @param choixPerso le personnage qui va être sacrifié
     */
    public void sacrifie(Joueur joueur, Integer choixPerso) {
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).equals(joueur)) {
                int l = joueurs.get(i).getPersonnages().get(choixPerso).getMonLieu().getNum();
                this.lieux.get(l).getPersonnage().remove(joueurs.get(i).getPersonnages().get(choixPerso));
                joueurs.get(i).getPersonnages().remove(choixPerso);
            }
        }
        lieuxChanged();
    }

    /**
     * Ferme les lieux a envahie
     */
    public void fermerLieu() {
        for (int i = 1; i < 7; i++) {
            if (i != 4 && this.lieux.get(i).getNbZombies() >= 8 && this.lieux.get(i).getPersonnage().isEmpty()) {
                this.lieux.get(i).setOuvert(false);
                this.lieux.get(i).setNbZombies(0);
            }
        }
        lieuxChanged();
    }

    /**
     * Obtient l'etat du jeu
     *
     * @return le plateau de jeu
     */
    public String afficheJeu() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < this.joueurs.size(); i++) {
            if (this.joueurs.get(i).isChefDesVigiles()) {
                tmp.append(">>> ").append(this.joueurs.get(i)).append(" est le chef des Vigiles <<<\n");
            }
        }
        for (int i = 1; i < 7; i++) {
            tmp.append(this.lieux.get(i)).append(":\n");
            for (int a = 0; a < this.lieux.get(i).getPersonnage().size(); a++) {
                tmp.append("(").append(this.lieux.get(i).getPersonnage().get(a).getJoueur()).append(") ")
                        .append(this.lieux.get(i).getPersonnage().get(a)).append("\n");
            }
            tmp.append("Nombre de place-> ").append(this.lieux.get(i).getNbPlaces()).append("\n");
            tmp.append("Nombre de Zombie-> ").append(this.lieux.get(i).getNbZombies()).append("\n\n");
        }

        return tmp.toString();
    }

    /**
     * Donne la liste des joueurs
     *
     * @return la liste des joueurs
     */
    public Map<Integer, Joueur> getJoueurs() {
        return joueurs;
    }

    /**
     * Donne la liste des lieux
     *
     * @return la liste des lieux
     */
    public Map<Integer, Lieu> getLieux() {
        return lieux;
    }

    /*
     * Donne la liste des cartes
     *
     * @return la liste des cartes
     */
    public List<CarteAction> getCartes() {
        return cartes;
    }
}
