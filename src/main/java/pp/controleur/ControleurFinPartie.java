package pp.controleur;

import static java.lang.System.out;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import pp.ihm.event.Evenement;
import pp.reseau.FinJeuReseau;
import reseau.socket.ControleurReseau;
import reseau.type.CondType;
import reseau.type.Statut;

/**
 * <h1>La classe ControleurFinPartie</h1>. A pour rôle de gérer et de detecter
 * une fin de partie
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurFinPartie {
	private FinJeuReseau fjr;

	public ControleurFinPartie() {
		fjr = new FinJeuReseau();
	}

	public void finJeu(ControleurJeu cj, Partie jeu, String partieId, int numeroTour) {
		List<Object> lo = initFinPartie(jeu);
		List<Lieu> lieu = (List<Lieu>) lo.get(0);
		int nbPerso = (int) lo.get(1);
		if (condFinJeu(jeu, lieu, nbPerso)) {
			CondType cond = definirCondFin(jeu, lieu);
			Joueur vainqueur = definirVainqueur(jeu);
			cj.setFinished(true);
			fjr.indiqueFinPartie(jeu, vainqueur, cond, partieId, numeroTour);
			Evenement.finPartie();
			Evenement.getGagnant(vainqueur);
			ControleurJeu.statut = Statut.COMPLETE;
			try {
				ControleurReseau.getTcpServeur().arreter();
				ControleurReseau.arreterUdp();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private List<Object> initFinPartie(Partie jeu) {
		List<Object> lo = new ArrayList<>();
		for (int i = 0; i < jeu.getJoueurs().size(); i++)
			if (jeu.getJoueurs().get(i).isEnVie() && jeu.getJoueurs().get(i).getPersonnages().size() == 0)
				jeu.getJoueurs().get(i).setEnVie(false);
		List<Lieu> lieu = new ArrayList<>();
		int nbPerso = 0;
		for (int i = 0; i < jeu.getJoueurs().size(); i++)
			if (jeu.getJoueurs().get(i).isEnVie()) {
				nbPerso += jeu.getJoueurs().get(i).getPersonnages().size();
				for (Integer j : jeu.getJoueurs().get(i).getPersonnages().keySet())
					if (!lieu.contains(jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu()))
						lieu.add(jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu());
			}
		lo.add(lieu);
		lo.add(nbPerso);
		return lo;
	}

	private Boolean condFinJeu(Partie jeu, List<Lieu> lieu, int nbPerso) {
		return (lieu.size() < 2 && lieu.get(0) != jeu.getLieux().get(4))
				|| (nbPerso <= 4 && jeu.getJoueurs().size() < 6) || (nbPerso <= 6 && jeu.getJoueurs().size() == 6);
	}

	private CondType definirCondFin(Partie jeu, List<Lieu> lieu) {
		CondType cond;
		if (lieu.size() < 2 && lieu.get(0) != jeu.getLieux().get(4))
			cond = CondType.LIEUX;
		else
			cond = CondType.PION;
		return cond;
	}

	private Joueur definirVainqueur(Partie jeu) {
		int pointVainqueur = 0;
		Joueur vainqueur = null;
		for (Joueur j : jeu.getJoueurs().values()) {
			int point = 0;
			if (j.isEnVie()) {
				for (Integer p : j.getPersonnages().keySet())
					point += j.getPersonnages().get(p).getPoint();
				if (point > pointVainqueur) {
					pointVainqueur = point;
					vainqueur = j;
				} else if (point == pointVainqueur)
					if (j.getCartes().size() == vainqueur.getCartes().size()) {
						List<Joueur> vJ = new ArrayList<>();
						vJ.add(vainqueur);
						vJ.add(j);
						vainqueur = vJ.get(new Random().nextInt(vJ.size()));
					}
				out.println(" Point " + j + ": " + point);
			} else
				out.println(" Point " + j + "(mort): " + point);
		}
		return vainqueur;
	}

}
