package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import pp.Joueur;
import pp.Partie;
import pp.PpTools;
import pp.ihm.event.Initializer;
import pp.reseau.DeplacementReseau;
import reseau.type.CarteType;

public class ControleurDeplacementPersonnage {
	private DeplacementReseau dr;
	private ControleurFinPartie cfp;

	public ControleurDeplacementPersonnage() {
		dr = new DeplacementReseau();
		cfp = new ControleurFinPartie();
	}

	public void phaseDeplacementPerso(ControleurJeu cj,Partie jeu, List<Integer> destination, List<Integer> zombie, String partieId,
			int numeroTour) {
		dr.debutPhaseDeplacement(jeu, destination, zombie, partieId, numeroTour);
		int compteur = 0;
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.isEnVie() && j.isChefDesVigiles()) {
				deplacePerso(cj, jeu, compteur, j, destination, partieId, numeroTour);
				compteur ++;
			}
			Initializer.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
		}
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.isEnVie() && !(j.isChefDesVigiles())) {
				deplacePerso(cj, jeu, compteur, j, destination, partieId, numeroTour);
				compteur ++;
			}
			Initializer.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
		}
	}

	private void deplacePerso(ControleurJeu cj, Partie jeu, Integer compteur, Joueur j, List<Integer> destination, String partieId,
			int numeroTour) {
		dr.demanderDeplacements(jeu, j, destination.get(compteur), partieId, numeroTour);
		List<Object> recupDeplacement = dr.recupDeplacemnt(jeu, j);
		jeu.deplacePerso(j, PpTools.valeurToIndex((int) recupDeplacement.get(1)), (int) recupDeplacement.get(0));
		cfp.finJeu(cj, jeu, partieId, numeroTour);
		if (cj.isFinished())
			return;
		jeu.fermerLieu();
		dr.informerDeplacment(jeu, j, (int) recupDeplacement.get(0), (int) recupDeplacement.get(1),
				(CarteType) recupDeplacement.get(2), partieId, numeroTour);
		Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
	}

}
