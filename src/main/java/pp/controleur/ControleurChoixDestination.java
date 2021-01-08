package pp.controleur;

import java.util.List;
import pp.Joueur;
import pp.Partie;
import pp.ihm.event.EvenementStockage;
import pp.ihm.langues.International;
import pp.ihm.IhmOutils;
import pp.ihm.event.Evenement;
import pp.reseau.ChoixDestinationReseau;
import reseau.type.VigileEtat;

/**
 * <h1>La classe ControleurChoixDestination</h1>. A pour rôle de gérer la phase
 * de choix de destination.
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurChoixDestination {
	private ChoixDestinationReseau cdr;

	/**
	 * Instancie le Contrtoleur de la phase de choix d'une destination..
	 */
	public ControleurChoixDestination() {
		cdr = new ChoixDestinationReseau();
	}

	/**
	 * Execute la phase de choix d'une destination.
	 *
	 * @param jeu         La partie courante.
	 * @param destination Liste des lieux destination des joueurs en vie.
	 * @param jmort       La list des joueurs morst ce tour.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	public void phasechoixDestination(Partie jeu, List<Integer> destination, List<Joueur> jmort, String partieId,
			int numeroTour) {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		cdr.debuterPhaseChoixDestination(jeu, ve, partieId, numeroTour);
		if (jeu.getNewChef())
			nouveauChef(jeu, destination, partieId, numeroTour);
		else
			ancienChef(jeu, destination);
		cdr.cloreChoiXDest(jeu, partieId, numeroTour);
		zombieVengeur(jeu, jmort, partieId, numeroTour);
	}

	/**
	 * Execute la phase de choix d'une destination en cas de nouveau chef des
	 * vigiles elu ce tour.
	 *
	 * @param jeu         La partie courante.
	 * @param destination Liste des lieux d'arrivés des zombies.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	public void nouveauChef(Partie jeu, List<Integer> destination, String partieId, int numeroTour) {
		Evenement.quiJoue(jeu.getChefVIgile().getCouleur());
		int dest = cdr.indiqueChoixDestChef(jeu);
		destination.add(dest);
		Evenement.suppQuiJoue();
		cdr.informerDestChefVigile(jeu, dest, partieId, numeroTour);
		Evenement.prevenirDeplacementVigile(
				International.trad("text.prevenira") + IhmOutils.getCouleurTrad(jeu.getChefVIgile().getCouleur())
						+ International.trad("text.prevenirb") + jeu.getLieux().get(dest));
		while (!EvenementStockage.isPopupAccepter())
			Thread.yield();
		EvenementStockage.setPopupAccepter(false);
		cdr.attendreChoixDestinationNewChef(jeu);
		for (Joueur j : jeu.getJoueurs().values())
			if (!j.isChefDesVigiles() && j.isEnVie()) {
				destination.add(cdr.indiqueChoixDest(j));
			}
	}

	/**
	 * Execute la phase de choix d'une destination lorsqu'aucun nouveau chef des
	 * vigiles n'est elu ce tour.
	 *
	 * @param jeu         La partie courante.
	 * @param destination Liste des lieux d'arrivés des zombies.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	public void ancienChef(Partie jeu, List<Integer> destination) {
		cdr.attendreChoixDestinationOldChef(jeu);
		for (Joueur j : jeu.getJoueurs().values())
			if (j.isChefDesVigiles() && j.isEnVie())
				destination.add(cdr.indiqueChoixDest(j));
		for (Joueur j : jeu.getJoueurs().values())
			if (!j.isChefDesVigiles() && j.isEnVie())
				destination.add(cdr.indiqueChoixDest(j));
	}

	/**
	 * Execute la phase de choix d'une destination pour un zombie vengeur.
	 *
	 * @param jeu        La partie courante
	 * @param jmort      La list des joueurs morst ce tour
	 * @param partieID   L'identifient de la partie en cours
	 * @param numeroTour Le numéro du tour courant
	 */
	public void zombieVengeur(Partie jeu, List<Joueur> jmort, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values())
			if (jmort.contains(j)) {
				Evenement.quiJoue(j.getCouleur());
				cdr.choisirDestZombieVenger(j, partieId, numeroTour);
				int dvz = cdr.indiquerChoixDestZombieVenger(j);
				jeu.getLieux().get(dvz).addZombie();
				cdr.informerChoixDestZombieVenger(jeu, j, dvz, partieId, numeroTour);
			}
	}

}
