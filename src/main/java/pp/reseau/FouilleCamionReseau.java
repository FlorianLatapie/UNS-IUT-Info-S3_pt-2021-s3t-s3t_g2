package pp.reseau;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import pp.PpTools;
import reseau.socket.ControleurReseau;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;

/**
 * <h1>La classe FouilleCamionReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase de fouille du camion
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class FouilleCamionReseau {

	/**
	 * Traitement du paquet PFC du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void debutPhaseFouille(Partie jeu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PFC",
				PpTools.getPionsCouleurByPerso(jeu.getLieux().get(4).getPersonnage()), jeu.getCartes().size(), partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet FCLC du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param j          Joueur courant.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void envoyerCarte(Partie jeu, Joueur j, String partieId, int numeroTour) {
		j.getConnection()
				.envoyer(ControleurReseau.construirePaquetTcp("FCLC", jeu.tirerCartes(j), partieId, numeroTour));
	}

	/**
	 * Traitement du paquet SCFC du protocole reseau.
	 * 
	 * @param jeu La partie courante.
	 * @param j   Joueur courant.
	 * 
	 * @return Liste de la carte gardée par le joueur, la carte offerte à une autre
	 *         joueur, la couleur du joueur recevant la carte offerte et la carte
	 *         défaussée.
	 */
	public List<Object> choixCarte(Partie jeu, Joueur j) {
		List<Object> lo = new ArrayList<>();
		j.getConnection().attendreMessage("SCFC");
		String mess = j.getConnection().getMessage("SCFC");
		lo.add(ControleurReseau.getValueTcp("SCFC", mess, 1));
		lo.add(ControleurReseau.getValueTcp("SCFC", mess, 2));
		lo.add(ControleurReseau.getValueTcp("SCFC", mess, 3));
		lo.add(ControleurReseau.getValueTcp("SCFC", mess, 4));
		return lo;
	}

	/**
	 * Traitement du paquet FCRC du protocole reseau.
	 * 
	 * @param j          Joueur courant.
	 * @param ct         Carte offerte.
	 * @param c          Couleur du joueur recevant la carte offerte.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void receptionnerCarte(Joueur j, CarteType ct, Couleur c, String partieId, int numeroTour) {
		j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("FCRC", ct, c, partieId, numeroTour));
	}

	/**
	 * Traitement du paquet RFC du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param c1         Couleur du joueur ayant gardé une carte
	 * @param c2         Couleur du joueur ayant reçu la carte offerte
	 * @param ce         Etat de la carte défaussé.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void finFouilleCamion(Partie jeu, Couleur c1, Couleur c2, CarteEtat ce, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RFC", c1, c2, ce, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

}
