package ihm.eventListener;

public interface PlateauListener {
    void nbLieuZombie(int lieu, int val);

    void nomJoueur(int joueur, String val);

    void lieuFerme(int lieu, boolean val);

    void lieuOuvert(int lieu, boolean val);

    void nbPersoJoueur(int joueur, int persoNb);

    void nbCarteJoueur(int joueur, int carteNb);

    void forceLieu(int lieu, int force);

    void finPartie();
}
