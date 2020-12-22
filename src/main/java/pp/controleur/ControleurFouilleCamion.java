package pp.controleur;

import java.util.List;

import pp.Joueur;
import pp.Partie;
import pp.ihm.Core;
import pp.ihm.event.EvenementStockage;
import pp.ihm.event.Initializer;
import pp.reseau.FouilleCamionReseau;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.VoteType;

public class ControleurFouilleCamion {
	private FouilleCamionReseau fr;
	private ControleurVote cVote;

	public ControleurFouilleCamion() {
		fr = new FouilleCamionReseau();
		cVote = new ControleurVote();
	}

	public void phaseFouilleCamion(Partie jeu, String partieId, int numeroTour) {
		String s = "";
		fr.debutPhaseFouille(jeu, partieId, numeroTour);
		Joueur j;
		if (isFouillable(jeu)) {
			j = joueurFouille(jeu, partieId, numeroTour);
			if (j != null) {
				s = "Phase fouille camion";
				fr.envoyerCarte(jeu, j, partieId, numeroTour);
				List<Object> l = fr.choixCarte(jeu, j);
				traitementResultatFouille(jeu, j, (CarteType) l.get(0), (CarteType) l.get(1), (Couleur) l.get(2),
						(CarteType) l.get(3));
				fr.receptionnerCarte(jeu.getJoueurCouleur((Couleur) l.get(2)), (CarteType) l.get(1), (Couleur) l.get(2),
						partieId, numeroTour);
				fr.finFouilleCamion(jeu, j.getCouleur(), (Couleur) l.get(2), etatCarteDefausse((CarteType) l.get(3)),
						partieId, numeroTour);
			}
			if (s == "") {
				s = "Pezrsonne fouille le Camion";
				fr.finFouilleCamion(jeu, Couleur.NUL, Couleur.NUL, CarteEtat.NUL, partieId, numeroTour);
			}
			Initializer.fouilleCamion(s);
			while (!EvenementStockage.isPopupAccepter())
				Thread.yield();
			EvenementStockage.setPopupAccepter(false);
		}
	}

	public Boolean isFouillable(Partie jeu) {
		return !jeu.getJoueurSurLieu(jeu.getLieux().get(4)).isEmpty() && !jeu.getCartes().isEmpty();
	}

	public Joueur joueurFouille(Partie jeu, String partieId, int numeroTour) {
		Joueur j;
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(4)).size() == 1)
			return jeu.getJoueurSurLieu(jeu.getLieux().get(4)).get(0);
		return cVote.phaseVote(jeu, jeu.getLieux().get(4), VoteType.FDC, partieId, numeroTour);
	}

	public void traitementResultatFouille(Partie jeu, Joueur j, CarteType carte1, CarteType carte2, Couleur couleur,
			CarteType carte3) {
		if (carte1 != CarteType.NUL)
			j.getCartes().add(carte1);
		if (carte2 != CarteType.NUL)
			jeu.getJoueurCouleur(couleur).getCartes().add(carte2);
		if (carte3 != CarteType.NUL)
			jeu.getCartes().add(carte3);
	}

	public CarteEtat etatCarteDefausse(CarteType ct) {
		CarteEtat ce = CarteEtat.NUL;
		if (ct != null)
			ce = CarteEtat.CD;
		return ce;
	}
}
