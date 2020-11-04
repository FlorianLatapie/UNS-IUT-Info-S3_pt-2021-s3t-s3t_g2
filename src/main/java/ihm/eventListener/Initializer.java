package ihm.eventListener;

import jeu.Joueur;
import jeu.Lieu;
import jeu.Personnage;

import java.util.ArrayList;
import java.util.List;

public class Initializer {
    private List<PlateauListener> listenerspl = new ArrayList<>();
    private List<AttenteListener> listenersal = new ArrayList<>();

    public void addListenerPlateau(PlateauListener toAdd) {
        listenerspl.add(toAdd);
    }

    public void addListenerAttente(AttenteListener toAdd) {
        listenersal.add(toAdd);
    }

    public void nbZombiesLieuAll(List<Lieu> lieux) {
        for (Lieu l : lieux)
            nbZombiesLieu(l.getNum(), l.getNbZombies());
    }

    private void nbZombiesLieu(int lieu, int val) {
        for (PlateauListener pl : listenerspl)
            pl.nbLieuZombie(lieu, val);
    }

    public void nomJoueurAll(List<Joueur> joueurs) {
        for (Joueur l : joueurs)
            nomJoueur(l.getJoueurIdint(), l.getNom());
    }

    private void nomJoueur(int joueur, String val) {
        for (PlateauListener pl : listenerspl)
            pl.nomJoueur(joueur, val);
    }

    public void lieuFermeAll(List<Lieu> lieux) {
        for (Lieu l : lieux)
            lieuFerme(l.getNum(), !l.isOuvert());
    }

    private void lieuFerme(int lieu, boolean val) {
        for (PlateauListener pl : listenerspl)
            pl.lieuFerme(lieu, val);
    }

    public void lieuOuvertAll(List<Lieu> lieux) {
        for (Lieu l : lieux)
            lieuOuvert(l.getNum(), l.isOuvert());
    }

    private void lieuOuvert(int lieu, boolean val) {
        for (PlateauListener pl : listenerspl)
            pl.lieuOuvert(lieu, val);
    }

    public void nbPersoJoueurAll(List<Joueur> joueurs) {
        for (Joueur j : joueurs)
            nbPersoJoueur(j.getJoueurIdint(), j.getPersonnages().size());
    }

    private void nbPersoJoueur(int joueur, int persoNb) {
        for (PlateauListener pl : listenerspl)
            pl.nbPersoJoueur(joueur, persoNb);
    }

    public void nbCarteJoueurAll(List<Joueur> joueurs) {
        for (Joueur l : joueurs)
            nbCarteJoueur(l.getJoueurIdint(), l.getCartes().size());
    }

    private void nbCarteJoueur(int joueur, int carteNb) {
        for (PlateauListener pl : listenerspl)
            pl.nbCarteJoueur(joueur, carteNb);
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
        for (PlateauListener pl : listenerspl)
            pl.forceLieu(lieu, val);
    }

    public void finPartie() {
        for (PlateauListener pl : listenerspl)
            pl.finPartie();
    }

    public void joueurPret() {
        for (AttenteListener al : listenersal)
            al.joueurPret();
    }
}
