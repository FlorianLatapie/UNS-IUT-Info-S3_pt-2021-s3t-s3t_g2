package pp.reseau;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.VigileEtat;

/**
 * <h1>La classe ChoixDestinationReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase d'attaque de choix d'une destination
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ChoixDestinationReseau {

	/**
	 * Traitement du paquet PCD du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param ve         Etat du vigile.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void debuterPhaseChoixDestination(Partie jeu, VigileEtat ve, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PCD", jeu.getChefVIgile().getCouleur(), ve, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet CDDCV du protocole reseau.
	 * 
	 * @param jeu La partie courante.
	 * 
	 * @return Destination choisie par le chef des vigiles.
	 */
	public int indiqueChoixDestChef(Partie jeu) {
		jeu.getChefVIgile().getConnection().attendreMessage("CDDCV");
		String m = jeu.getChefVIgile().getConnection().getMessage("CDDCV");
		return (int) ControleurReseau.getValueTcp("CDDCV", m, 1);
	}

	/**
	 * Traitement du paquet CDCDV du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param dest       Destination choisie par le chef des vigiles.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerDestChefVigile(Partie jeu, int dest, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("CDCDV", jeu.getChefVIgile().getCouleur(), dest, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			if (!(j.isChefDesVigiles()))
				j.getConnection().envoyer(m);
	}
	
	/**
	 * Traitement du paquet CDDJ du protocole reseau.
	 * 
	 * @param jeu La partie courante.
	 */
	public void attendreChoixDestination(Partie jeu) {
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().attendreMessage("CDDJ");
	}

	/**
	 * Traitement du paquet CDDJ du protocole reseau.
	 * 
	 * @param j Joueur courant
	 * 
	 * @return Destination choisie par le joueur courant.
	 */
	public int indiqueChoixDest(Joueur j) {
		String rep = j.getConnection().getMessage("CDDJ");
		return (int) ControleurReseau.getValueTcp("CDDJ", rep, 1);
	}

	/**
	 * Traitement du paquet CDFC du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void cloreChoiXDest(Partie jeu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("CDFC", partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet CDZVI du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void choisirDestZombieVenger(Joueur j, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("CDZVI", partieId, numeroTour);
		j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet CDDZVJE du protocole reseau.
	 * 
	 * @param j Joueur courant.
	 * 
	 * @return Destination choisie pour le zombie vengeur.
	 */
	public int indiquerChoixDestZombieVenger(Joueur j) {
		j.getConnection().attendreMessage("CDDZVJE");
		String rep = j.getConnection().getMessage("CDDZVJE");
		return (int) ControleurReseau.getValueTcp("CDDZVJE", rep, 1);
	}

	/**
	 * Traitement du paquet CDZVDI du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param j          Joueur courant.
	 * @param dvz        Destination zombie vengeur.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerChoixDestZombieVenger(Partie jeu, Joueur j, int dvz, String partieId, int numeroTour) {
		for (Joueur j2 : jeu.getJoueurs().values()) {
			j2.getConnection()
					.envoyer(ControleurReseau.construirePaquetTcp("CDZVDI", j.getCouleur(), dvz, partieId, numeroTour));
		}
	}

}
