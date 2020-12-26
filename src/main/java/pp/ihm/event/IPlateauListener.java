package pp.ihm.event;

import java.util.List;

import pp.Personnage;
import reseau.type.Couleur;

public interface IPlateauListener {
    void nbLieuZombie(int lieu, int val);

    void lieuFerme(int lieu, boolean val);

    void lieuOuvert(int lieu, boolean val);

    void nbPersoJoueur(int joueur, int persoNb);

    void nbCarteJoueur(int joueur, int carteNb);
    
    void nbPlaceLieu(int lieu, int val);

    void nomChefVigile(String joueur);

    void finPartie();

    void destionationPerso(int lieu, List<Personnage> destionationPerso);

    void fouilleCamion(String camion);

    void prevenirDeplacementVigile(String depvig);
    
    void electionChef(String message);

	void nomJoueur(int joueur, Couleur couleur, String val);

	void quiJoue(Couleur couleur);
	
	void suppQuiJoue();
	
	void choiCouleur(List<Couleur> couleurs);
	
	void nbCartePiocheActuel(int val);
}
