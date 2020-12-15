package pp.controleur;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.VoteType;

public class ControleurFouilleCamion {
	
	public Boolean isFouillable(Partie jeu){
		return !jeu.getJoueurSurLieu(jeu.getLieux().get(4)).isEmpty() && !jeu.getCartes().isEmpty();
	}
	
	public Joueur joueurFouille(Partie jeu) {
		Joueur j;
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(4)).size() == 1)
			return jeu.getJoueurSurLieu(jeu.getLieux().get(4)).get(0);
		return null;
	}
	
	public List<Object> resultatFouillage(Partie jeu, Joueur j, CarteType carte1, CarteType carte2, Couleur couleur, CarteType carte3) {
		List<Object> lo = new ArrayList<>();
		Couleur a1 = Couleur.NUL;
		Couleur a2 = Couleur.NUL;
		CarteEtat a3 = CarteEtat.NUL;
		if (carte1 != CarteType.NUL) {
			j.getCartes().add(carte1);
			a1 = j.getCouleur();
		}
		if (carte2 != CarteType.NUL) {
			jeu.getJoueurCouleur(couleur).getCartes()
					.add(carte2);
			a2 = couleur;
		}
		if (carte3 != CarteType.NUL) {
			jeu.getCartes().add(carte3);
			a3 = CarteEtat.CD;
		}
		lo.add(a1);
		lo.add(a2);
		lo.add(a3);
		return lo;
	}
	
}
