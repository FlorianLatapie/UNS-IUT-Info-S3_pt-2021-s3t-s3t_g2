package pp.reseau;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;

public class PlacementReseau {

	public void informerJoueur(Partie jeu, Joueur j, String partieId) {
		String message = ControleurReseau.construirePaquetTcp("PIIJ", jeu.nombrePlaceDisponible(),
				jeu.getPersonnageAPlace(j), partieId);
		j.getConnection().envoyer(message);
	}

	public void attendreLancement(Joueur j){
		j.getConnection().attendreMessage("PILD");
	}
	
	public void informerResultatDes(Joueur j, List<Integer> des, List<Integer> listePion, String partieId) {
		String message = ControleurReseau.construirePaquetTcp("PIRD", des, listePion, partieId);
		j.getConnection().envoyer(message);
	}

	public List<Integer> choisirDest(Joueur j) {
		List<Integer> a = new ArrayList<>();
		j.getConnection().attendreMessage("PICD");
		String rep = j.getConnection().getMessage("PICD");
		a.add((int) ControleurReseau.getValueTcp("PICD", rep, 1));
		a.add((int) ControleurReseau.getValueTcp("PICD", rep, 2));
		return a;
	}

	public void informerPlacement(Partie jeu, Joueur j, List<Integer> listePion, List<Integer> des, int destEntre,
			int persEntre, String partieId) {
		String message = ControleurReseau.construirePaquetTcp("PIIG", j.getCouleur(), des, listePion, destEntre,
				persEntre, partieId);
		for (Joueur j2 : jeu.getJoueurs().values())
			if (j2 != j)
				j2.getConnection().envoyer(message);
	}

}
