package pp.reseau;

import java.util.List;

import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.Couleur;
import reseau.type.VoteEtape;
import reseau.type.VoteType;

public class VoteReseau {

	public void debutVote(Partie jeu, VoteType tv, VoteEtape ve, List<Couleur> joueurPresent,
			List<Couleur> joueurVotant, List<Integer> nbVoix, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values()) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("IPV", tv, ve, joueurPresent, joueurVotant,
					nbVoix, partieId, numeroTour));
		}
	}

	public void demanderCarte(Partie jeu, Lieu l, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVD", partieId, numeroTour));
		}
	}

	public int carteJoue(Joueur j) {
		j.getConnection().attendreMessage("PVC");
		String m = j.getConnection().getMessage("PVC");
		return (int) ControleurReseau.getValueTcp("PVC", m, 1);
	}

	public void informeCarteJouee(Lieu l, Joueur j, int nbMateriel, String partieId, int numeroTour) {
		j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVIC", j.getCouleur(), nbMateriel,
				j.getNbVoix(l, 1, nbMateriel), partieId, numeroTour));
	}

	public void demanderVote(Partie jeu, Lieu l, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVDV", partieId, numeroTour));
		}
	}

	public Couleur recupVote(Joueur j) {
		j.getConnection().attendreMessage("PVCV");
		String m = j.getConnection().getMessage("PVCV");
		return (Couleur) ControleurReseau.getValueTcp("PVCV", m, 1);
	}

	public void cloreVote(Partie jeu, Lieu l, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(l)) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVVC", partieId, numeroTour));
		}
	}

	public void informerResultatVote(Partie jeu, Couleur couleurVote, List<Integer> listeVoixRecu,
			List<Couleur> listeVotes, List<Couleur> listeJoueursVotant, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values()) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("PVR", couleurVote, listeJoueursVotant,
					listeVotes, listeVoixRecu, partieId, numeroTour));
		}
	}
}
