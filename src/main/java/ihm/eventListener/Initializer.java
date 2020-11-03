package ihm.eventListener;

import jeu.Joueur;
import jeu.Lieu;
import jeu.Personnage;

import java.util.ArrayList;
import java.util.List;

public class Initializer {
    private List<PlateauListener> listeners = new ArrayList<>();

    public void addListener(PlateauListener toAdd) {
        listeners.add(toAdd);
    }

    public void nbZombiesLieuAll(List<Lieu> lieux) {
        for (Lieu l : lieux)
            nbZombiesLieu(l.getNum(), l.getNbZombies());
    }

    private void nbZombiesLieu(int lieu, int val) {
        for (PlateauListener hl : listeners)
            hl.nbLieuZombie(lieu, val);
    }

    public void nomJoueurAll(List<Joueur> joueurs) {
        for (Joueur l : joueurs)
            nomJoueur(l.getJoueurIdint(), l.getNom());
    }

    private void nomJoueur(int joueur, String val) {
        for (PlateauListener hl : listeners)
            hl.nomJoueur(joueur, val);
    }

    public void lieuFermeAll(List<Lieu> lieux) {
        for (Lieu l : lieux)
            lieuFerme(l.getNum(), !l.isOuvert());
    }

    private void lieuFerme(int lieu, boolean val) {
        for (PlateauListener hl : listeners)
            hl.lieuFerme(lieu, val);
    }

    public void lieuOuvertAll(List<Lieu> lieux) {
        for (Lieu l : lieux)
            lieuOuvert(l.getNum(), l.isOuvert());
    }

    private void lieuOuvert(int lieu, boolean val) {
        for (PlateauListener hl : listeners)
            hl.lieuOuvert(lieu, val);
    }

    public void nbPersoJoueurAll(List<Joueur> joueurs) {
        for (Joueur j : joueurs)
            nbPersoJoueur(j.getJoueurIdint(), j.getPersonnages().size());
    }

    private void nbPersoJoueur(int joueur, int persoNb) {
        for (PlateauListener hl : listeners)
            hl.nbPersoJoueur(joueur, persoNb);
    }

    public void nbCarteJoueurAll(List<Joueur> joueurs) {
        for (Joueur l : joueurs)
            nbCarteJoueur(l.getJoueurIdint(), l.getCartes().size());
    }

    private void nbCarteJoueur(int joueur, int carteNb) {
        for (PlateauListener hl : listeners)
            hl.nbCarteJoueur(joueur, carteNb);
    }

    public void forceLieuAll(List<Lieu> lieux) {
        for (Lieu l : lieux) {
            int i = 0;
            for (Personnage p : l.getPersonnage())
                i += p.getPoint();
            forceLieu(l.getNum(), i);
        }
    }

    private void forceLieu(int lieu, int val) {
        for (PlateauListener hl : listeners)
            hl.forceLieu(lieu, val);
    }

    public void finPartie() {
        for (PlateauListener hl : listeners)
            hl.finPartie();
    }
}
