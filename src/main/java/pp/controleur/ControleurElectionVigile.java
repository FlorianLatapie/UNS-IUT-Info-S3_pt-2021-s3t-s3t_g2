package pp.controleur;

import pp.Joueur;
import pp.Partie;

public class ControleurElectionVigile {

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
