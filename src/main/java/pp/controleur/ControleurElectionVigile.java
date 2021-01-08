package pp.controleur;

import java.util.ArrayList;

import pp.Joueur;
import pp.Partie;
import pp.ihm.event.EvenementStockage;
import pp.ihm.langues.International;
import pp.ihm.event.Evenement;
import pp.reseau.ElectionChefVigileReseau;
import reseau.type.Couleur;
import reseau.type.VoteType;

/**
 * <h1>La classe ControleurElectionVigile</h1>. A pour rôle de gérer la phase
 * d'élection du chef des vigiles
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurElectionVigile {
	private ControleurVote cVote;
	private ElectionChefVigileReseau ecvr;

	/**
	 * Instancie le Contrtoleur de la phase d'election du chef des vigiles.
	 */
	public ControleurElectionVigile() {
		ecvr = new ElectionChefVigileReseau();
		cVote = new ControleurVote();
	}

	/**
	 * Execute la phase d'election du chef des vigiles.
	 *
	 * @param jeu        La partie en cours.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void phaseElectionChefVigi(Partie jeu, String partieId, int numeroTour) {
		ecvr.debutElectionChefVigile(jeu, partieId, numeroTour);
		Joueur j;
		if (isElectableBoolean(jeu)) {
			j = joueurElection(jeu, partieId, numeroTour);
			if (j != null) {
				newVigile(jeu, j);
				ecvr.informerElectionChefVigile(jeu, jeu.getChefVIgile().getCouleur(), partieId, numeroTour);
				Evenement.electionChef(International.trad("text.nouveauchef") + jeu.getChefVIgile());
				while (!EvenementStockage.isPopupAccepter())
					Thread.yield();
				EvenementStockage.setPopupAccepter(false);
			} else {
				jeu.setNewChef(false);
				ecvr.informerElectionChefVigile(jeu, Couleur.NUL, partieId, numeroTour);
				Evenement.electionChef(International.trad("text.nonnouveauchef"));
				while (!EvenementStockage.isPopupAccepter())
					Thread.yield();
				EvenementStockage.setPopupAccepter(false);
			}
		} else {
			noNewVigile(jeu);
			ecvr.informerElectionChefVigile(jeu, Couleur.NUL, partieId, numeroTour);
			Evenement.electionChef(International.trad("text.nonnouveauchef"));
			while (!EvenementStockage.isPopupAccepter())
				Thread.yield();
			EvenementStockage.setPopupAccepter(false);
		}
		Evenement.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
	}

	/**
	 * Vérifie si une election du chef des vigiles est possible.
	 *
	 * @param jeu La partie en cours.
	 * 
	 * @return On peut faire une élection du chef des vigiles.
	 */
	private Boolean isElectableBoolean(Partie jeu) {
		return !jeu.getJoueurSurLieu(jeu.getLieux().get(5)).isEmpty();
	}

	/**
	 * Definie le joueur elu pour devenir chef des vigiles.
	 *
	 * @param jeu        La partie en cours.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 * 
	 * @retrun le joueur élu chef des vigiles.
	 */
	public Joueur joueurElection(Partie jeu, String partieId, int numeroTour) {
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(5)).size() == 1)
			return jeu.getJoueurSurLieu(jeu.getLieux().get(5)).get(0);
		return cVote.phaseVote(jeu, jeu.getLieux().get(5), VoteType.ECD, partieId, numeroTour);
	}

	/**
	 * Met a jour la variable newChef, en cas de nouveau chef, de la classe Partie.
	 *
	 * @param cj     Le controleur de la partie courante.
	 * @param joueur Le joueur élu chef des vigiles ce tour.
	 */
	public void newVigile(Partie jeu, Joueur j) {
		jeu.resultatChefVigile(j);
		jeu.setNewChef(true);
	}

	/**
	 * Met a jour la variable newChef, quand il n'y a pas de nouveau chef, de la classe Partie.
	 *
	 * @param cj     Le controleur de la partie courante.
	 * @param joueur Le joueur élu chef des vigiles ce tour.
	 */
	public void noNewVigile(Partie jeu) {
		jeu.setNewChef(false);
	}

}
