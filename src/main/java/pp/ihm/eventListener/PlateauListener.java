package pp.ihm.eventListener;

import java.util.List;

import pp.Personnage;
import reseau.type.CarteType;
import reseau.type.Couleur;

public interface PlateauListener {
    void nbLieuZombie(int lieu, int val);

    void lieuFerme(int lieu, boolean val);

    void lieuOuvert(int lieu, boolean val);

    void nbPersoJoueur(int joueur, int persoNb);

    void nbCarteJoueur(int joueur, int carteNb);

    //void forceLieu(int lieu, int force);

    void nomChefVigile(String joueur);

    void finPartie();

    void destionationPerso(int lieu, List<Personnage> destionationPerso);

    void fouilleCamion(String camion);

    void prevenirDeplacementVigile(String depvig);
    
    void electionChef(String message);

	void nomJoueur(int joueur, Couleur couleur, String val);
}
