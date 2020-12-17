package pp.controleur;

import pp.Joueur;
import pp.Partie;
import pp.ihm.event.Initializer;
import pp.reseau.ToolsReseauPP;
import pp.reseau.electionChefVigileReseau;
import reseau.socket.ControleurReseau;
import reseau.type.Couleur;
import reseau.type.VoteType;

public class ControleurElectionVigile {
	private ControleurVote cVote;
	private electionChefVigileReseau ecvr;
	
	public ControleurElectionVigile() {
		ecvr = new electionChefVigileReseau();
		cVote = new ControleurVote();
	}
	
	public void phaseElectionChefVigi(Partie jeu, String partieId, int numeroTour) {
		ecvr.debutElectionChefVigile(jeu, partieId, numeroTour);
		Joueur j;
		if (isElectableBoolean(jeu)) {
			j = joueurElection(jeu);
			if (j == null)
				j = cVote.phaseVote(jeu, jeu.getLieux().get(5), VoteType.ECD, partieId, numeroTour);
			if (j != null) {
				newVigile(jeu, j);
				ecvr.informerElectionChefVigile(jeu, jeu.getChefVIgile().getCouleur(), partieId, numeroTour);
				Initializer.electionChef("Nouveau chef des vigiles : " + jeu.getChefVIgile());
			} else {
				jeu.setNewChef(false);
				ecvr.informerElectionChefVigile(jeu, Couleur.NUL, partieId, numeroTour);
				Initializer.electionChef("Il n'y a pas de nouveau chef des vigiles");
			}
		} else {
			noNewVigile(jeu);
			ecvr.informerElectionChefVigile(jeu, Couleur.NUL, partieId, numeroTour);
			Initializer.electionChef("Il n'y a pas de nouveau chef des vigiles");
		}
	}

	public Boolean isElectableBoolean(Partie jeu) {
		return !jeu.getJoueurSurLieu(jeu.getLieux().get(5)).isEmpty();
	}

	public Joueur joueurElection(Partie jeu) {
		Joueur j;
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(5)).size() == 1)
			j = jeu.getJoueurSurLieu(jeu.getLieux().get(5)).get(0);
		return null;
	}
	
	public void newVigile(Partie jeu, Joueur j) {
		jeu.resultatChefVigile(j);
		jeu.setNewChef(true);
	}
	
	public void noNewVigile(Partie jeu) {
		jeu.setNewChef(false);
	}
	
}
