package pp.reseau;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.Couleur;

/**
 * <h1>La classe DeplacementReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase de deplacement de personnages
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ElectionChefVigileReseau {

	/**
	 * Traitement du paquet PECV du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void debutElectionChefVigile(Partie jeu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PECV", jeu.getPersosLieu(5), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet RECV du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param c          Couleur du nouveau chef des vigiles.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerElectionChefVigile(Partie jeu, Couleur c, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RECV", c, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

}