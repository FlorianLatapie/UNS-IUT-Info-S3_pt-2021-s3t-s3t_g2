package pp.controleur;

import pp.Joueur;
import pp.Partie;
import pp.ihm.event.EvenementStockage;
import pp.ihm.event.Evenement;
import pp.reseau.ElectionChefVigileReseau;
import reseau.type.Couleur;
import reseau.type.VoteType;

/**
 * <h1>La classe ControleurElectionVigile</h1>. A pour rôle de gérer la phase d'élection du chef des vigiles
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurElectionVigile {
	private ControleurVote cVote;
	private ElectionChefVigileReseau ecvr;
	
	public ControleurElectionVigile() {
		ecvr = new ElectionChefVigileReseau();
		cVote = new ControleurVote();
	}
	
	public void phaseElectionChefVigi(Partie jeu, String partieId, int numeroTour) {
		ecvr.debutElectionChefVigile(jeu, partieId, numeroTour);
		Joueur j;
		if (isElectableBoolean(jeu)) {
			j = joueurElection(jeu, partieId, numeroTour);
			if (j != null) {
				newVigile(jeu, j);
				ecvr.informerElectionChefVigile(jeu, jeu.getChefVIgile().getCouleur(), partieId, numeroTour);
				Evenement.electionChef("Nouveau chef des vigiles : " + jeu.getChefVIgile());
				while (!EvenementStockage.isPopupAccepter())
					Thread.yield();
				EvenementStockage.setPopupAccepter(false);
			} else {
				jeu.setNewChef(false);
				ecvr.informerElectionChefVigile(jeu, Couleur.NUL, partieId, numeroTour);
				Evenement.electionChef("Il n'y a pas de nouveau chef des vigiles");
				while (!EvenementStockage.isPopupAccepter())
					Thread.yield();
				EvenementStockage.setPopupAccepter(false);
			}
		} else {
			noNewVigile(jeu);
			ecvr.informerElectionChefVigile(jeu, Couleur.NUL, partieId, numeroTour);
			Evenement.electionChef("Il n'y a pas de nouveau chef des vigiles");
			while (!EvenementStockage.isPopupAccepter())
				Thread.yield();
			EvenementStockage.setPopupAccepter(false);
		}
	}

	public Boolean isElectableBoolean(Partie jeu) {
		return !jeu.getJoueurSurLieu(jeu.getLieux().get(5)).isEmpty();
	}

	public Joueur joueurElection(Partie jeu, String partieId, int numeroTour) {
		//TODO modifier
		Joueur j = null;
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(5)).size() == 1)
			j = jeu.getJoueurSurLieu(jeu.getLieux().get(5)).get(0);
		if (j == null)
			j = cVote.phaseVote(jeu, jeu.getLieux().get(5), VoteType.ECD, partieId, numeroTour);
		return j;
	}
	
	public void newVigile(Partie jeu, Joueur j) {
		jeu.resultatChefVigile(j);
		jeu.setNewChef(true);
	}
	
	public void noNewVigile(Partie jeu) {
		jeu.setNewChef(false);
	}
	
}
