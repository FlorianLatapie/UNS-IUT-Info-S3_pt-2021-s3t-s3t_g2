package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import pp.Joueur;
import pp.Partie;
import pp.ihm.event.EvenementStockage;
import pp.ihm.event.Evenement;
import pp.reseau.ChoixDestinationReseau;
import reseau.type.VigileEtat;

/**
 * <h1>La classe ControleurChoixDestination</h1>. A pour rôle de gérer la phase
 * de choix de destination
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurChoixDestination {
	private ChoixDestinationReseau cdr;

	public ControleurChoixDestination() {
		cdr = new ChoixDestinationReseau();
	}

	public void phasechoixDestination(Partie jeu, ArrayList<Integer> destination, List<Joueur> jmort, String partieId,
			int numeroTour) {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		cdr.debuterPhaseChoixDestination(jeu, ve, partieId, numeroTour);
		if (jeu.getNewChef())
			nouveauChef(jeu, destination, partieId, numeroTour);
		else
			ancienChef(jeu, destination, partieId, numeroTour);
		cdr.cloreChoiXDest(jeu, partieId, numeroTour);
		zombieVenger(jeu, jmort, partieId, numeroTour);
	}

	public void nouveauChef(Partie jeu, ArrayList<Integer> destination, String partieId, int numeroTour) {
		int dest = cdr.indiqueChoixDestChef(jeu);
		destination.add(dest);
		cdr.informerDestChefVigile(jeu, dest, partieId, numeroTour);
		Evenement.prevenirDeplacementVigile("Le chef des vigile (" + jeu.getChefVIgile().getCouleur()
				+ ") a choisi la detination :" + jeu.getLieux().get(dest));
		while (!EvenementStockage.isPopupAccepter())
			Thread.yield();
		EvenementStockage.setPopupAccepter(false);
		for (Joueur j : jeu.getJoueurs().values())
			if (!j.isChefDesVigiles() && j.isEnVie())
				destination.add(cdr.indiqueChoixDest(j));
	}

	public void ancienChef(Partie jeu, ArrayList<Integer> destination, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values())
			if (j.isChefDesVigiles() && j.isEnVie())
				destination.add(cdr.indiqueChoixDest(j));
		for (Joueur j : jeu.getJoueurs().values())
			if (!j.isChefDesVigiles() && j.isEnVie())
				destination.add(cdr.indiqueChoixDest(j));
	}

	public void zombieVenger(Partie jeu, List<Joueur> jmort, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values())
			if (jmort.contains(j)) {
				cdr.choisirDestZombieVenger(j, partieId, numeroTour);
				int dvz = cdr.indiquerChoixDestZombieVenger(j);
				jeu.getLieux().get(dvz).addZombie();
				cdr.informerChoixDestZombieVenger(jeu, j, dvz, partieId, numeroTour);
			}
	}

}
