package pp.controleur;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import pp.ihm.event.EvenementStockage;
import pp.ihm.langues.International;
import pp.ihm.event.Evenement;
import pp.reseau.FouilleCamionReseau;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.VoteType;

/**
 * <h1>La classe ControleurFouilleCamion</h1>. A pour rôle de gérer la phase de
 * fouille du camion
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurFouilleCamion {
	private FouilleCamionReseau fr;
	private ControleurVote cVote;

	/**
	 * Instancie le Contrtoleur de la phase de fouille du camion.
	 */
	public ControleurFouilleCamion() {
		fr = new FouilleCamionReseau();
		cVote = new ControleurVote();
	}

	/**
	 * Execute la phase de fouille du camion.
	 *
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void phaseFouilleCamion(Partie jeu, String partieId, int numeroTour) {
		String s = "";
		fr.debutPhaseFouille(jeu, partieId, numeroTour);
		Joueur j;
		if (isFouillable(jeu)) {
			j = joueurFouille(jeu, partieId, numeroTour);
			if (j != null) {
				Evenement.quiJoue(j.getCouleur());
				s = j + " (" + j.getCouleur() + ") " + International.trad("text.fouilleLeCamion");
				Evenement.fouilleCamion(s);
				while (!EvenementStockage.isPopupAccepter())
					Thread.yield();
				EvenementStockage.setPopupAccepter(false);
				fr.envoyerCarte(jeu, j, partieId, numeroTour);
				Evenement.nbCartePiocheActuel(jeu.getCartes().size());
				List<Object> l = fr.choixCarte(jeu, j);
				traitementResultatFouille(jeu, j, (CarteType) l.get(0), (CarteType) l.get(1), (Couleur) l.get(2),
						(CarteType) l.get(3));
				fr.receptionnerCarte(jeu.getJoueurCouleur((Couleur) l.get(2)), (CarteType) l.get(1), (Couleur) l.get(2),
						partieId, numeroTour);
				Evenement.nbCartePiocheActuel(jeu.getCartes().size());
				fr.finFouilleCamion(jeu, j.getCouleur(), (Couleur) l.get(2), etatCarteDefausse((CarteType) l.get(3)),
						partieId, numeroTour);
				Evenement.suppQuiJoue();
				if ((Couleur) l.get(2) != null) {
					String s1 = jeu.getJoueurCouleur((Couleur) l.get(2)) + " (" + (Couleur) l.get(2) + ") "
							+ International.trad("text.recuCarte");
					Evenement.fouilleCamion(s1);
					while (!EvenementStockage.isPopupAccepter())
						Thread.yield();
					EvenementStockage.setPopupAccepter(false);
				}
			} else {
				s = International.trad("text.pCamion");
				fr.finFouilleCamion(jeu, Couleur.NUL, Couleur.NUL, CarteEtat.NUL, partieId, numeroTour);
				Evenement.fouilleCamion(s);
				while (!EvenementStockage.isPopupAccepter())
					Thread.yield();
				EvenementStockage.setPopupAccepter(false);
			}
		} else {
			s = International.trad("text.pCamion");
			fr.finFouilleCamion(jeu, Couleur.NUL, Couleur.NUL, CarteEtat.NUL, partieId, numeroTour);
			Evenement.fouilleCamion(s);
			while (!EvenementStockage.isPopupAccepter())
				Thread.yield();
			EvenementStockage.setPopupAccepter(false);
		}

	}

	/**
	 * Vérifie si une fouille du camion est possible.
	 *
	 * @param jeu La partie en cours.
	 * 
	 * @return On peut faire une fouille du camion.
	 */
	private Boolean isFouillable(Partie jeu) {
		return !(jeu.getLieux().get(4).getPersonnage().isEmpty()) && !(jeu.getCartes().isEmpty());
	}

	/**
	 * Definie le joueur elu pour fouiller le camion.
	 *
	 * @param jeu        La partie en cours.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 * 
	 * @return le joueur élu pour fouiller le camion.
	 */
	private Joueur joueurFouille(Partie jeu, String partieId, int numeroTour) {
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(4)).size() == 1)
			return jeu.getJoueurSurLieu(jeu.getLieux().get(4)).get(0);
		return cVote.phaseVote(jeu, jeu.getLieux().get(4), VoteType.FDC, partieId, numeroTour);
	}

	/**
	 * Traite le résultat de la fouille.
	 *
	 * @param jeu        La partie en cours.
	 * @param carte1     La carte gardé par le fouilleur.
	 * @param carte2     La carte donné par le fouilleur.
	 * @param couleur    La couleur du joueur recevant la carte donné.
	 * @param carte3     La carte defaussé.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	private void traitementResultatFouille(Partie jeu, Joueur j, CarteType carte1, CarteType carte2, Couleur couleur,
			CarteType carte3) {
		if (carte1 != CarteType.NUL)
			j.getCartes().add(carte1);
		if (carte2 != CarteType.NUL)
			jeu.getJoueurCouleur(couleur).getCartes().add(carte2);
		if (carte3 != CarteType.NUL)
			jeu.getCartes().add(carte3);
		Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
	}

	/**
	 * Definie l'état de la carte défaussé.
	 *
	 * @param ct La carte deffaussé.
	 * 
	 * @return Etat de la carte défaussé.
	 */
	private CarteEtat etatCarteDefausse(CarteType ct) {
		CarteEtat ce = CarteEtat.NUL;
		if (ct != null)
			ce = CarteEtat.CD;
		return ce;
	}
}
