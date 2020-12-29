package pp.reseau;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.CondType;

/**
 * <h1>La classe FinJeuReseau</h1>. A pour rôle de traiter les paquets réseaux
 * d'une fin de partie
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class FinJeuReseau {

	/**
	 * Traitement du paquet FP du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param joueur     Joueur vainqueur.
	 * @param cond       Condition de fin de partie.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void indiqueFinPartie(Partie jeu, Joueur vainqueur, CondType cond, String partieId, int numeroTour) {
		String message = ControleurReseau.construirePaquetTcp("FP", cond, vainqueur.getCouleur(), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(message);
	}

}
