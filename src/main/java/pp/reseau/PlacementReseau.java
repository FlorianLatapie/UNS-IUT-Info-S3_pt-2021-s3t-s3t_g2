package pp.reseau;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;

/**
 * <h1>La classe PlacementReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase de placement des personnages
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class PlacementReseau {

	/**
	 * Traitement du paquet PIIJ du protocole reseau.
	 * 
	 * @param jeu      La partie courante.
	 * @param j        Joueur courant.
	 * @param partieID L'identifiant de la partie en cours.
	 */
	public void informerJoueur(Partie jeu, Joueur j, String partieId) {
		String message = ControleurReseau.construirePaquetTcp("PIIJ", jeu.nombrePlaceDisponible(),
				jeu.getPersonnageAPlace(j), partieId);
		j.getConnection().envoyer(message);
	}

	/**
	 * Traitement du paquet PILD du protocole reseau.
	 * 
	 * @param j Joueur courant.
	 */
	public void attendreLancement(Joueur j) {
		j.getConnection().attendreMessage("PILD");
	}

	/**
	 * Traitement du paquet PIRD du protocole reseau.
	 * 
	 * @param j          Joueur courant.
	 * @param des        Resultat des dès.
	 * @param listePion  Pion restant a placer.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerResultatDes(Joueur j, List<Integer> des, List<Integer> listePion, String partieId) {
		String message = ControleurReseau.construirePaquetTcp("PIRD", des, listePion, partieId);
		j.getConnection().envoyer(message);
	}

	/**
	 * Traitement du paquet PICD du protocole reseau.
	 * 
	 * @param j Joueur courant.
	 * 
	 * @return Liste de la destination et du personnage choisie.
	 */
	public List<Integer> choisirDest(Joueur j) {
		List<Integer> a = new ArrayList<>();
		j.getConnection().attendreMessage("PICD");
		String rep = j.getConnection().getMessage("PICD");
		a.add((int) ControleurReseau.getValueTcp("PICD", rep, 1));
		a.add((int) ControleurReseau.getValueTcp("PICD", rep, 2));
		return a;
	}

	/**
	 * Traitement du paquet PIIG du protocole reseau.
	 * 
	 * @param j          Joueur courant.
	 * @param listePion  Pion restant a placer.
	 * @param des        Resultat des dès.
	 * @param destEntre  Destinatrion choisie pour le placement.
	 * @param persEntre  Personnage choisie pour le placement.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerPlacement(Partie jeu, Joueur j, List<Integer> listePion, List<Integer> des, int destEntre,
			int persEntre, String partieId) {
		String message = ControleurReseau.construirePaquetTcp("PIIG", j.getCouleur(), des, listePion, destEntre,
				persEntre, partieId);
		for (Joueur j2 : jeu.getJoueurs().values())
			if (j2 != j)
				j2.getConnection().envoyer(message);
	}

}
