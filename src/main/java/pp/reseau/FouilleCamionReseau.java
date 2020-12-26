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

	public void debutPhaseFouille(Partie jeu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PFC",
				PpTools.getPionsCouleurByPerso(jeu.getLieux().get(4).getPersonnage()), jeu.getCartes().size(), partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	public void envoyerCarte(Partie jeu, Joueur j, String partieId, int numeroTour) {
		j.getConnection()
				.envoyer(ControleurReseau.construirePaquetTcp("FCLC", jeu.tirerCartes(j), partieId, numeroTour));
	}

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

	public void receptionnerCarte(Joueur j, CarteType ct, Couleur c, String partieId, int numeroTour) {
		j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("FCRC", ct, c, partieId, numeroTour));
	}

	public void finFouilleCamion(Partie jeu, Couleur c1, Couleur c2, CarteEtat ce, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RFC", c1, c2, ce, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

}
