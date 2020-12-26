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

	/**
	 * Instancie le Contrtoleur de la fin de partie.
	 */
	public ControleurFinPartie() {
		fjr = new FinJeuReseau();
	}

	/**
	 * Vérifie et execute la fin d'une partie.
	 *
	 * @param cj         Le controleur de la partie courante.
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
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

	/**
	 * Met a jour le jeu et créé des liste pour pouvoir vérifier la fin de partie.
	 *
	 * @param jeu La partie courante.
	 * 
	 * @return Une liste d'une liste des lieux contenants un personnage et d'un
	 *         entier represantant le nombre de personnages en vie.
	 */
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

	/**
	 * Execute la phase de deplacment des personnages.
	 *
	 * @param jeu     La partie courante.
	 * @param lieu    Liste des lieux contenant un personnage.
	 * @param nbPerso Nombre de personnage encore en vie.
	 * 
	 * @return C'est la fin du jeu.
	 */
	private Boolean condFinJeu(Partie jeu, List<Lieu> lieu, int nbPerso) {
		return (lieu.size() < 2 && lieu.get(0) != jeu.getLieux().get(4))
				|| (nbPerso <= 4 && jeu.getJoueurs().size() < 6) || (nbPerso <= 6 && jeu.getJoueurs().size() == 6);
	}

	/**
	 * Definie la condition de fin de partie.
	 *
	 * @param jeu     La partie courante.
	 * @param lieu    Liste des lieux contenant un personnage.
	 * @param nbPerso Nombre de personnage encore en vie.
	 * 
	 * @return Condition fin de partie.
	 */
	private CondType definirCondFin(Partie jeu, List<Lieu> lieu) {
		CondType cond;
		if (lieu.size() < 2 && lieu.get(0) != jeu.getLieux().get(4))
			cond = CondType.LIEUX;
		else
			cond = CondType.PION;
		return cond;
	}

	/**
	 * Definie le vainqeur de la partie.
	 *
	 * @param jeu La partie courante.
	 * 
	 * @return Joueur vainqueur de la partie
	 */
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
