package pp.reseau;

import java.util.List;

import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.Couleur;
import reseau.type.VoteEtape;
import reseau.type.VoteType;

/**
 * <h1>La classe PlacementReseau</h1>. A pour rôle de traiter les paquets
 * réseaux des phases de vote
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class VoteReseau {

	/**
	 * Traitement du paquet IPV du protocole reseau.
	 * 
	 * @param jeu           La partie courante.
	 * @param tv            Type du vote.
	 * @param ve            Etape du vote.
	 * @param joueurPresent Liste des joueurs presents.
	 * @param joueurVotant  Liste des joueurs votants
	 * @param nbvoix        Liste des nombres de voix.
	 * @param partieID      L'identifiant de la partie en cours.
	 * @param numeroTour    Le numéro du tour courant.
	 */
	public void debutVote(Partie jeu, VoteType tv, VoteEtape ve, List<Couleur> joueurPresent,
			List<Couleur> joueurVotant, List<Integer> nbVoix, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values()) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("IPV", tv, ve, joueurPresent, joueurVotant,
					nbVoix, partieId, numeroTour));
		}
	}

	/**
	 * Traitement du paquet PVD du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param l          Lieu courant.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void demanderCarte(Partie jeu, Lieu l, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVD", partieId, numeroTour));
		}
	}

	/**
	 * Traitement du paquet PVC du protocole reseau.
	 * 
	 * @param j Joueur courant.
	 */
	public int carteJoue(Joueur j) {
		j.getConnection().attendreMessage("PVC");
		String m = j.getConnection().getMessage("PVC");
		return (int) ControleurReseau.getValueTcp("PVC", m, 1);
	}

	/**
	 * Traitement du paquet PVIC du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param numVote    Numéro du tour de vote.
	 * @param l          Lieu courant.
	 * @param nbMenace   Nombre de cartes menace jouer.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informeCarteJouee(Partie jeu, int numVote, Lieu l, Joueur j, int nbMenace, String partieId,
			int numeroTour) {
		for (Joueur jou : jeu.getJoueurs().values())
			jou.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVIC", j.getCouleur(), nbMenace,
					j.getNbVoix(l, numVote, nbMenace), partieId, numeroTour));
	}

	/**
	 * Traitement du paquet PVDV du protocole reseau.
	 * 
	 * @param j          Joueur courant.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void demanderVote(Joueur j, String partieId, int numeroTour) {
		j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVDV", partieId, numeroTour));
	}

	/**
	 * Traitement du paquet PVCV du protocole reseau.
	 * 
	 * @param j Joueur courant.
	 * 
	 * @return Couleur du joueur voté.
	 */
	public Couleur recupVote(Joueur j) {
		j.getConnection().attendreMessage("PVCV");
		String m = j.getConnection().getMessage("PVCV");
		return (Couleur) ControleurReseau.getValueTcp("PVCV", m, 1);
	}

	/**
	 * Traitement du paquet PVVC du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param l          Lieu courant.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void cloreVote(Partie jeu, Lieu l, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVVC", partieId, numeroTour));
		}
	}

	/**
	 * Traitement du paquet PVR du protocole reseau.
	 * 
	 * @param jeu                 La partie courante.
	 * @param couleurVote         Couleur du joueur élu.
	 * @param listeVoixRecu       Liste des voix recus.
	 * @param listeVotes          Liste des votes.
	 * @param listesJoueursVotant Liste des joueurs votant.
	 * @param partieID            L'identifiant de la partie en cours.
	 * @param numeroTour          Le numéro du tour courant.
	 */
	public void informerResultatVote(Partie jeu, Couleur couleurVote, List<Integer> listeVoixRecu,
			List<Couleur> listeVotes, List<Couleur> listeJoueursVotant, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values()) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVR", couleurVote, listeJoueursVotant,
					listeVotes, listeVoixRecu, partieId, numeroTour));
		}
	}
}
