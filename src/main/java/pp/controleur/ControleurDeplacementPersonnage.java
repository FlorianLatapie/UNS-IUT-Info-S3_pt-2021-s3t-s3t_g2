package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import pp.Joueur;
import pp.Partie;
import pp.PpTools;
import pp.ihm.event.Evenement;
import pp.reseau.DeplacementReseau;
import reseau.type.CarteType;

/**
 * <h1>La classe ControleurDeplacementPersonnage</h1>. A pour rôle de gérer la
 * phase de déplacment des personnages
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurDeplacementPersonnage {
	private DeplacementReseau dr;
	private ControleurFinPartie cfp;

	/**
	 * Instancie le Contrtoleur de la phase de choix de deplacment des personnages.
	 */
	public ControleurDeplacementPersonnage() {
		dr = new DeplacementReseau();
		cfp = new ControleurFinPartie();
	}

	/**
	 * Execute la phase de deplacment des personnages.
	 *
	 * @param cj          Le controleur de la partie courante.
	 * @param jeu         La partie courante.
	 * @param destination Liste des destinations des joueurs en vie.
	 * @param zombie      Liste des lieux d'arrivés des zombies.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	public void phaseDeplacementPerso(ControleurJeu cj, Partie jeu, List<Integer> destination, List<Integer> zombie,
			String partieId, int numeroTour) {
		dr.debutPhaseDeplacement(jeu, destination, zombie, partieId, numeroTour);
		int compteur = 0;
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.isEnVie() && j.isChefDesVigiles()) {
				Evenement.quiJoue(j.getCouleur());
				deplacePerso(cj, jeu, compteur, j, destination, partieId, numeroTour);
				compteur++;
				Evenement.suppQuiJoue();
			}
			Evenement.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
		}
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.isEnVie() && !(j.isChefDesVigiles())) {
				Evenement.quiJoue(j.getCouleur());
				deplacePerso(cj, jeu, compteur, j, destination, partieId, numeroTour);
				compteur++;
				Evenement.suppQuiJoue();
			}
			Evenement.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
		}
	}

	/**
	 * Execute le deplacment d'un personnage.
	 *
	 * @param cj          Le controleur de la partie courante.
	 * @param jeu         La partie courante.
	 * @param compteur    Indice dans la liste destination.
	 * @param destination Liste des lieux destination des joueurs en vie.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	private void deplacePerso(ControleurJeu cj, Partie jeu, Integer compteur, Joueur j, List<Integer> destination,
			String partieId, int numeroTour) {
		dr.demanderDeplacements(jeu, j, destination.get(compteur), partieId, numeroTour);
		List<Object> recupDeplacement = dr.recupDeplacemnt(jeu, j);
		jeu.deplacePerso(j, PpTools.valeurToIndex((int) recupDeplacement.get(1)), (int) recupDeplacement.get(0));
		cfp.finJeu(cj, jeu, partieId, numeroTour);
		if (cj.isFinished())
			return;
		jeu.fermerLieu();
		dr.informerDeplacment(jeu, j, (int) recupDeplacement.get(0), (int) recupDeplacement.get(1),
				(CarteType) recupDeplacement.get(2), partieId, numeroTour);
		Evenement.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
	}

}
