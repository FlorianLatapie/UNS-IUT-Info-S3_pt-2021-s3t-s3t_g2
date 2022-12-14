package pp.reseau;

import java.util.ArrayList;
import java.util.List;
import pp.Joueur;
import pp.Partie;
import pp.ihm.event.Evenement;
import reseau.socket.ControleurReseau;
import reseau.type.CarteType;

/**
 * <h1>La classe DeplacementReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase de deplacement de personnages
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class DeplacementReseau {

	/**
	 * Traitement du paquet PDP du protocole reseau
	 * 
	 * @param jeu         La partie courante.
	 * @param destiantion Destination choisie par les joueurs.
	 * @param zombie      Lieux d'arrivéd des zombies.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	public void debutPhaseDeplacement(Partie jeu, List<Integer> destination, List<Integer> zombie, String partieId,
			int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PDP", jeu.getChefVIgile().getCouleur(), destination, zombie,
				jeu.getLieuxFermes(), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet DPD du protocole reseau.
	 * 
	 * @param jeu         La partie courante.
	 * @param j           Joueur courant.
	 * @param destination Destination choisie par le joueur courant.
	 * @param partieID    L'identifiant de la partie en cours.
	 * @param numeroTour  Le numéro du tour courant.
	 */
	public void demanderDeplacements(Partie jeu, Joueur j, int destination, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("DPD", destination, jeu.getAllPersoPossible(j), partieId,
				numeroTour);
		j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet DPR du protocole reseau.
	 * 
	 * @param jeu La partie courante.
	 * @param j   Joueur courant.
	 */
	public List<Object> recupDeplacemnt(Partie jeu, Joueur j) {
		List<Object> li = new ArrayList<>();
		j.getConnection().attendreMessage("DPR");
		String message = j.getConnection().getMessage("DPR");
		if (ControleurReseau.getValueTcp("DPR", message, 3) == CarteType.SPR)
			j.getCartes().remove(CarteType.SPR);
		Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		li.add(ControleurReseau.getValueTcp("DPR", message, 1));
		li.add(ControleurReseau.getValueTcp("DPR", message, 2));
		li.add(ControleurReseau.getValueTcp("DPR", message, 3));
		return li;
	}

	/**
	 * Traitement du paquet DPI du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param j          Joueur courant.
	 * @param dest       Destination choisie par le joueur courant.
	 * @param pion       Pion choisie par le joueur Courant.
	 * @param c          Type de carte utilisé (Sprint).
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerDeplacment(Partie jeu, Joueur j, int dest, int pion, CarteType c, String partieId,
			int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("DPI", j.getCouleur(), dest, pion, c, partieId, numeroTour);
		for (Joueur j2 : jeu.getJoueurs().values())
			if (j2 != j)
				j2.getConnection().envoyer(m);
	}
}
