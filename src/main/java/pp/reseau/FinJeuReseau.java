package pp.reseau;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.CondType;

public class FinJeuReseau {
	
	public void indiqueFinPartie(Partie jeu, Joueur vainqueur, CondType cond, String partieId, int numeroTour) {
		String message = ControleurReseau.construirePaquetTcp("FP", cond, vainqueur.getCouleur(), partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(message);
	}

}
