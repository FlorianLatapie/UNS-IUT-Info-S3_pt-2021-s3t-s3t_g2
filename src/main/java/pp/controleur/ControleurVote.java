package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import pp.ihm.event.Evenement;
import pp.reseau.VoteReseau;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.VoteEtape;
import reseau.type.VoteType;

/**
 * <h1>La classe ControleurVote</h1>. A pour rôle de gérer les phases de vote
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurVote {
	private VoteReseau vr;

	/**
	 * Instancie le Controleur de la phase de vote.
	 */
	public ControleurVote() {
		vr = new VoteReseau();
	}

	/**
	 * Execute la phase de vote
	 *
	 * @param jeu        La partie courante.
	 * @param l          Le lieu du vote.
	 * @param tv         Le type du vote.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 * 
	 * @return Le joueur élu.
	 */
	public Joueur phaseVote(Partie jeu, Lieu l, VoteType tv, String partieId, int numeroTour) {
		if (jeu.getJoueurSurLieu(l).size() == 1) {
			return jeu.getJoueurSurLieu(l).get(0);
		}
		Joueur joueur = phaseVoteTour(jeu, l, tv, VoteEtape.PRE, partieId, numeroTour);
		if (joueur != null)
			return joueur;
		joueur = phaseVoteTour(jeu, l, tv, VoteEtape.SEC, partieId, numeroTour);
		if (joueur != null)
			return joueur;
		if (tv == VoteType.MPZ)
			return jeu.getJoueurSurLieu(l).get(new Random().nextInt(jeu.getJoueurSurLieu(l).size()));
		return null;
	}

	/**
	 * Execute un tour de la phase de vote.
	 *
	 * @param jeu        La partie courante.
	 * @param l          Le lieu du vote.
	 * @param tv         Le type du vote.
	 * @param ve         Etape du vote.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 * 
	 * @return le joueur élu ce tour.
	 */
	public Joueur phaseVoteTour(Partie jeu, Lieu l, VoteType tv, VoteEtape ve, String partieId, int numeroTour) {
		List<Object> lo = infoVote(jeu, l, tv, ve, partieId, numeroTour);
		List<Couleur> joueursVotant = (List<Couleur>) lo.get(0);
		List<Integer> nbVoix = (List<Integer>) lo.get(1);
		List<Couleur> votes = new ArrayList<>();
		List<Integer> voixRecu = initVoixRecu(ve, jeu, l);
		vr.demanderCarte(jeu, l, partieId, numeroTour);
		vr.attendreCarte(jeu, l);
		traitementMenace(ve, jeu, nbVoix, l, partieId, numeroTour);
		demanderVote(jeu, l, ve, partieId, numeroTour);
		voixRecu = traitementVotes(jeu, l, ve, votes, voixRecu, nbVoix);
		vr.cloreVote(jeu, l, partieId, numeroTour);
		Couleur couleurVote = eluVote(voixRecu, joueursVotant);
		vr.informerResultatVote(jeu, couleurVote, voixRecu, votes, joueursVotant, partieId, numeroTour);
		if (couleurVote == Couleur.NUL)
			return null;
		return jeu.getJoueurCouleur(couleurVote);
	}

	/**
	 * Definie les listes necessaires au vote.
	 *
	 * @param jeu        La partie courante.
	 * @param l          Le lieu du vote.
	 * @param tv         Le type du vote.
	 * @param ve         Etape du vote.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 * 
	 * @return Liste d'une liste des joueurs votants et d'une liste des voix de
	 *         chaque joueur.
	 */
	public List<Object> infoVote(Partie jeu, Lieu l, VoteType tv, VoteEtape ve, String partieId, int numeroTour) {
		List<Object> lo = new ArrayList<>();
		List<Couleur> joueursPresent = new ArrayList<>();
		List<Integer> nbVoix = new ArrayList<>();
		List<Couleur> joueursVotant = new ArrayList<>();
		for (Joueur j : jeu.getJoueurSurLieu(l))
			joueursPresent.add(j.getCouleur());
		if (ve == VoteEtape.PRE) {
			for (Joueur j : jeu.getJoueurSurLieu(l))
				nbVoix.add(j.getNbVoix(l, 1, 0));
			joueursVotant = joueursPresent;
			vr.debutVote(jeu, tv, VoteEtape.PRE, joueursPresent, joueursVotant, nbVoix, partieId, numeroTour);
		} else if (ve == VoteEtape.SEC) {
			for (Joueur j : jeu.getJoueurs().values())
				nbVoix.add(j.getNbVoix(l, 2, 0));
			for (Joueur j : jeu.getJoueurs().values())
				joueursVotant.add(j.getCouleur());
			vr.debutVote(jeu, tv, VoteEtape.SEC, joueursPresent, joueursVotant, nbVoix, partieId, numeroTour);
		}
		lo.add(joueursVotant);
		lo.add(nbVoix);
		return lo;
	}

	/**
	 * Initialise les voix recus par tout les joueurs present au lieu de l'attaque.
	 *
	 * @param ve  Etape du vote.
	 * @param jeu La partie courante.
	 * @param l   Le lieu du vote.
	 * 
	 * @return Liste des voix recus par tout les joueurs presents.
	 */
	public List<Integer> initVoixRecu(VoteEtape ve, Partie jeu, Lieu l) {
		List<Integer> voixRecu = new ArrayList<>();
		if (ve == VoteEtape.PRE)
			while (voixRecu.size() < jeu.getJoueurSurLieu(l).size())
				voixRecu.add(0);
		else if (ve == VoteEtape.SEC)
			while (voixRecu.size() < jeu.getJoueurs().size())
				voixRecu.add(0);
		return voixRecu;
	}

	/**
	 * Initialise les voix recus par tout les joueurs present au lieu de l'attaque.
	 *
	 * @param ve         Etape du vote.
	 * @param jeu        La partie courante.
	 * @param nbVoix     Liste du nombre de voix de chaque joueur.
	 * @param l          Le lieu du vote.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void traitementMenace(VoteEtape ve, Partie jeu, List<Integer> nbVoix, Lieu l, String partieId,
			int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			if (ve == VoteEtape.PRE) {
				int nbMenace = vr.carteJoue(j);
				for (int i = 0; i < nbMenace; i++)
					j.getCartes().remove(CarteType.MEN);
				Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
				nbVoix.add(j.getNbVoix(l, 1, nbMenace));
				vr.informeCarteJouee(jeu, 1, l, j, nbMenace, partieId, numeroTour);
			} else if (ve == VoteEtape.SEC) {
				int nbMenace = vr.carteJoue(j);
				for (int i = 0; i < nbMenace; i++)
					j.getCartes().remove(CarteType.MEN);
				Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
				int a = 0;
				for (Integer i : jeu.getJoueurs().keySet())
					if (jeu.getJoueurs().get(i) == j)
						a = i;
				nbVoix.remove(a);
				nbVoix.add(a, j.getNbVoix(l, 2, nbMenace));
				vr.informeCarteJouee(jeu, 2, l, j, nbMenace, partieId, numeroTour);
			}
		}
	}

	/**
	 * Deamnde aux joueurs de voter.
	 *
	 * @param jeu        La partie courante.
	 * @param l          Le lieu du vote.
	 * @param ve         Etape du vote.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void demanderVote(Partie jeu, Lieu l, VoteEtape ve, String partieId, int numeroTour) {
		if (ve == VoteEtape.PRE)
			for (Joueur j : jeu.getJoueurSurLieu(l))
				vr.demanderVote(j, partieId, numeroTour);
		else if (ve == VoteEtape.SEC)
			for (Joueur j : jeu.getJoueurs().values())
				vr.demanderVote(j, partieId, numeroTour);
	}

	/**
	 * Demande aux joueurs de voter.
	 *
	 * @param jeu      La partie courante.
	 * @param l        Le lieu du vote.
	 * @param ve       Etape du vote.
	 * @param votes    Liste des votes.
	 * @param voixRecu Liste des voix recus.
	 * @param nbVoix   liste des nombres de voix.
	 * 
	 * @return Liste des voix recu.
	 */
	public List<Integer> traitementVotes(Partie jeu, Lieu l, VoteEtape ve, List<Couleur> votes, List<Integer> voixRecu,
			List<Integer> nbVoix) {
		int i = 0;
		if (ve == VoteEtape.PRE) {
			vr.attendreVote(jeu, jeu.getJoueurSurLieu(l));
			for (Joueur j : jeu.getJoueurSurLieu(l)) {
				Couleur couleurVote = vr.recupVote(j);
				votes.add(couleurVote);
				int a = 0;
				for (Joueur jou : jeu.getJoueurSurLieu(l)) {
					if (jou.getCouleur() == couleurVote)
						voixRecu.set(a, voixRecu.get(a) + nbVoix.get(i));
					a++;
				}
				i++;
			}
		} else if (ve == VoteEtape.SEC) {
			vr.attendreVote(jeu, jeu.getJoueurs().values());
			for (Joueur j : jeu.getJoueurs().values()) {
				Couleur couleurVote = vr.recupVote(j);
				votes.add(couleurVote);
				int a = 0;
				for (Joueur jou : jeu.getJoueurs().values()) {
					if (jou.getCouleur() == couleurVote)
						voixRecu.set(a, voixRecu.get(a) + nbVoix.get(i));
					a++;
				}
				i++;
			}
		}
		return voixRecu;
	}

	/**
	 * Definie le joueur elu.
	 *
	 * @param listeVoixRecu       Liste des voix recus.
	 * @param listeJoueursPresent Liste des joueurs present.
	 * 
	 * @return La couleur du joueur elu.
	 */
	public Couleur eluVote(List<Integer> listeVoixRecu, List<Couleur> listeJoueursPresent) {
		int i = 0;
		int nbVoteTemp = 0;
		Couleur couleurVote = Couleur.NUL;
		for (int nbVoix : listeVoixRecu) {
			if (nbVoix > nbVoteTemp) {
				couleurVote = listeJoueursPresent.get(i);
				nbVoteTemp = nbVoix;
			} else if (nbVoix == nbVoteTemp) {
				couleurVote = Couleur.NUL;
			}
			i++;
		}
		return couleurVote;
	}

}
