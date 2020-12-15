package pp.controleur;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import reseau.type.Couleur;
import reseau.type.VoteType;

public class ControleurVote {

	public List<Object> Tour1(Partie jeu, Lieu l, VoteType tv){
		List<Object> lo = new ArrayList<>();
		List<Couleur> listeJoueursPresent = new ArrayList<>();
		List<Integer> listeNbVoix = new ArrayList<>();
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			listeJoueursPresent.add(j.getCouleur());
		}
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			listeNbVoix.add(j.getNbVoix(l, 1, 0));
		}
		List<Couleur> listeJoueursVotant = listeJoueursPresent;
		System.out.println("Joueur Présent:" + listeJoueursPresent.size());
		lo.add(listeJoueursPresent);
		lo.add(listeJoueursVotant);
		lo.add(listeNbVoix);
		return lo;
	}
	
	public Couleur eluVote(List<Integer> listeVoixRecu, List<Couleur> listeJoueursPresent) {
		int i = 0;
		int nbVoteTemp = 0;
		Couleur couleurVote = Couleur.NUL;
		for (int nbVoix : listeVoixRecu) {
			if (nbVoix > nbVoteTemp) {
				couleurVote = listeJoueursPresent.get(i);
				nbVoteTemp = nbVoix;
			} else if (nbVoix == nbVoteTemp) {
				couleurVote = Couleur.NUL;
			}
			i++;
		}
		return couleurVote;
	}
	
	
	
}
