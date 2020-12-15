package botmoyen.MCTS;

import java.util.LinkedList;

import MCTS.Base.Action;
import MCTS.Base.Player;
import MCTS.Base.State;
import botmoyen.partie.ControleurPartie;
import botmoyen.partie.Joueur;
import botmoyen.partie.Lieu;
import botmoyen.partie.Partie;
import botmoyen.partie.Personnage;
import reseau.type.Couleur;

public class PartieState extends State {
	private Partie partie;
	private Couleur couleur;

	public PartieState(Partie partie, Couleur couleur) {
		super();
		this.partie = partie;
		this.couleur = couleur;
	}

	public String toString() {
		return partie.getEtatPartie();
	}

	@Override
	public LinkedList<Action> getPossibleActions() {
		LinkedList<Action> possibleActions = new LinkedList<Action>();
		for (Integer idLieu : partie.getLieuxOuverts()) {
			boolean destValide = false;
			for (Personnage perso : partie.getJoueurs().get(couleur).getPersonnages().values())
				if (perso.getMonLieu() != idLieu)
					destValide = true;
			if (destValide)
				possibleActions.add(new PartieAction(idLieu));
		}
		return possibleActions;
	}

	@Override
	public boolean isTerminal() {
		if (partie.getJoueurs().get(couleur).getPersonnages().isEmpty())
			return true;
		
		return partie.estFini();
	}

	@Override
	public State takeAction(Action arg0) {
		PartieAction pa = (PartieAction) arg0;
		ControleurPartie cp = new ControleurPartie(partie,couleur);
		try {
			cp.iterate(pa.getDestination());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PartieState(cp.getPartie(), couleur);
	}

	@Override
	public boolean equals(Object obj) {
		PartieState pmsa = (PartieState) obj;
		Boolean equal = true;

		for (Lieu l : partie.getLieux().values()) {
			for (Lieu l2 : pmsa.getPartie().getLieux().values())
				if ((l.getNum() == l2.getNum())
						&& ((!l.getPersonnage().equals(l2.getPersonnage())) || (l.getNbZombies() != l2.getNbZombies())))
					equal = false;
		}

		for (Joueur j : partie.getJoueurs().values()) {
			for (Joueur j2 : pmsa.getPartie().getJoueurs().values())
				if ((j.getCouleur().equals(j2.getCouleur())) && (j.getCartes().size() == j2.getCartes().size()))
					equal = false;
		}
		return equal;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	@Override
	public double getReward(Player arg0) {
		if (partie.getJoueurs().get(couleur).getPersonnages().isEmpty())
			return -2;
		else if (partie.isEgalite())
			return 0;
		else if (partie.getGagnant().equals(couleur))
			return 1;
		return -1;
	}

}
