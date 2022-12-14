package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import pp.PpTools;
import pp.ihm.event.Evenement;
import pp.reseau.PlacementReseau;

/**
 * <h1>La classe ControleurPlacementPersonnage</h1>. A pour rôle de gérer la
 * phase de placement des personnages
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurPlacementPersonnage {
	private PlacementReseau pr;
	private Random rd;

	/**
	 * Instancie le Controleur de la phase de placement des personnages.
	 */
	public ControleurPlacementPersonnage() {
		pr = new PlacementReseau();
		rd = new Random();
	}

	/**
	 * Execute la phase de placment des personnages.
	 *
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void placementPersonnage(Partie jeu, String partieId, int numeroTour) {
		for (int n = 0; n < jeu.getJoueurs().get(0).getPersonnages().size(); n++) {
			for (Joueur j : jeu.getJoueurs().values()) {
				Evenement.quiJoue(j.getCouleur());
				pr.informerJoueur(jeu, j, partieId);
				int x = jeu.getLieuxOuverts().get(rd.nextInt(jeu.getLieuxOuverts().size()));
				int y = jeu.getLieuxOuverts().get(rd.nextInt(jeu.getLieuxOuverts().size()));
				List<Integer> des = new ArrayList<>();
				des.add(x);
				des.add(y);
				List<Integer> listePion = getDestinationPossible(jeu, x, y);
				pr.attendreLancement(j);
				pr.informerResultatDes(j, des, listePion, partieId);
				List<Integer> choisirDest = pr.choisirDest(j);
				jeu.placePerso(j, PpTools.valeurToIndex(choisirDest.get(1)), choisirDest.get(0));
				pr.informerPlacement(jeu, j, des, listePion, choisirDest.get(0), choisirDest.get(1), partieId);
				Evenement.suppQuiJoue();
				Evenement.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
				Evenement.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
			}
		}
	}

	/**
	 * Retourne la liste des lieux valides au lancement des dès pour le placement.
	 *
	 * @param destination1 résultat du 1er dé.
	 * @param destination2 résultat du 2ème dé.
	 * 
	 * @return la liste des index des destination valides.
	 */
	private List<Integer> getDestinationPossible(Partie jeu, int destination1, int destination2) {
		List<Integer> destinationPossible = new ArrayList<>();
		if (jeu.getLieux().get(destination1).isFull() && jeu.getLieux().get(destination2).isFull()) {
			for (Lieu l : jeu.getLieux().values())
				if (!l.isFull() && l.isOuvert())
					destinationPossible.add(l.getNum());
		} else if (jeu.getLieux().get(destination1).isFull() && !jeu.getLieux().get(destination2).isFull())
			destinationPossible.add(destination2);
		else if (!jeu.getLieux().get(destination1).isFull() && jeu.getLieux().get(destination2).isFull())
			destinationPossible.add(destination1);
		else {
			destinationPossible.add(destination1);
			destinationPossible.add(destination2);
		}
		return destinationPossible;
	}

}
