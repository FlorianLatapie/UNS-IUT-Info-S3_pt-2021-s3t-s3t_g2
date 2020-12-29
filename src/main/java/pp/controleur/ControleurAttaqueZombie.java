package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import pp.Personnage;
import pp.PpTools;
import pp.ihm.event.Evenement;
import pp.reseau.AttaqueZombieReseau;
import reseau.type.CarteType;
import reseau.type.PionCouleur;
import reseau.type.RaisonType;
import reseau.type.VoteType;

/**
 * <h1>La classe ControleurAttaqueZombie</h1>. A pour rôle de gérer la phase
 * d'attaque des zombies
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurAttaqueZombie {
	ControleurVote cv;
	ControleurFinPartie cfp;
	AttaqueZombieReseau azr;

	/**
	 * Instancie le Contrtoleur de la phase d'attaque des zombies.
	 */
	public ControleurAttaqueZombie() {
		cv = new ControleurVote();
		cfp = new ControleurFinPartie();
		azr = new AttaqueZombieReseau();
	}

	/**
	 * Execute la phase d'attaque des zombies.
	 *
	 * @param cj         Le controleur de la partie courante.
	 * @param jeu        La partie en cours.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void phaseAttaqueZombie(ControleurJeu cj, Partie jeu, String partieId, int numeroTour) {
		List<Integer> nb = jeu.lastAttaqueZombie();
		Evenement.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		azr.debuteAttaque(jeu, nb, partieId, numeroTour);
		for (Lieu lieu : jeu.getLieux().values()) {
			if (lieuAttaquableReseau(jeu, lieu, partieId, numeroTour)) {
				List<Object> defense = defenseAttaqueZombie(jeu, lieu, partieId, numeroTour);
				if (lieu.estAttaquable((int) defense.get(1))) {
					if (lieu.getNum() == 4) {
						while (lieu.getNbZombies() != 0 && !lieu.getPersonnage().isEmpty()) {
							attaqueZombie(jeu, lieu, partieId, numeroTour);
							lieu.setNbZombies(lieu.getNbZombies() - 1);
							cfp.finJeu(cj, jeu, partieId, numeroTour);
							if (cj.isFinished())
								return;
						}
					} else {
						attaqueZombie(jeu, lieu, partieId, numeroTour);
						lieu.setNbZombies(0);
						cfp.finJeu(cj, jeu, partieId, numeroTour);
						if (cj.isFinished())
							return;
					}
				}
			}
		}
		reinitJoueurCache(jeu);
	}

	/**
	 * Réinitialise la list des personnages cachées.
	 *
	 * @param jeu La partie courante.
	 */
	private void reinitJoueurCache(Partie jeu) {
		for (Joueur j : jeu.getJoueurs().values())
			for (Personnage p : j.getPersonnages().values())
				p.setEstCache(false);
	}

	/**
	 * Traite la defense des joueurs presents sur le lieu de l'attaque.
	 *
	 * @param jeu        La partie courante.
	 * @param lieu       Lieu courant de l'attaque.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 *
	 * @return La liste des lieux d'arrivés des zombies.
	 */
	private List<Object> defenseAttaqueZombie(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		List<Object> defense = new ArrayList<>();
		List<Personnage> persoCache = new ArrayList<>();
		int nbCarteMateriel = 0;
		azr.demanderDefense(jeu, lieu, partieId, numeroTour);
		azr.attendreDefense(jeu.getJoueurSurLieu(lieu));
		for (Joueur j : jeu.getJoueurSurLieu(lieu)) {
			List<Object> lo = azr.recupDefense(j);
			List<Integer> persoCacheTemp = (List<Integer>) lo.get(1);
			for (Personnage p : j.getPersonnages().values())
				if (persoCacheTemp.contains(p.getPoint()))
					p.setEstCache(true);
			List<CarteType> carte = (List<CarteType>) lo.get(0);
			for (CarteType c : carte) {
				if (c == CarteType.MAT)
					nbCarteMateriel += 1;
				else if (c == CarteType.ACS || c == CarteType.ATR || c == CarteType.AGR)
					lieu.setNbZombies(lieu.getNbZombies() - 2);
				else if (c == CarteType.ARE || c == CarteType.AHI || c == CarteType.ABA)
					lieu.setNbZombies(lieu.getNbZombies() - 1);
				j.getCartes().remove(c);
			}
			azr.informerDefense(jeu, lieu, persoCacheTemp, nbCarteMateriel, j, carte, partieId, numeroTour);
		}
		Evenement.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		defense.add(persoCache);
		defense.add(nbCarteMateriel);
		return defense;
	}

	/**
	 * Definie si un lieu est attaquable et les raisons qu'il ne soit pas
	 * attaquable.
	 *
	 * @param jeu        La partie courante.
	 * @param lieu       Lieu courant.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 *
	 * @return Le lieu est attaquable.
	 */
	private boolean lieuAttaquableReseau(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		if (lieu.isOuvert() && !lieu.getPersonnage().isEmpty())
			if (lieu.getNbZombies() > 0)
				if (lieu.estAttaquable()) {
					azr.indiquerAttaque(jeu, lieu, partieId, numeroTour);
					return true;
				} else
					azr.indiquerPasAttaque(jeu, RaisonType.FORCE, lieu, partieId, numeroTour);
			else
				azr.indiquerPasAttaque(jeu, RaisonType.ZOMBIE, lieu, partieId, numeroTour);
		else
			azr.indiquerPasAttaque(jeu, RaisonType.PION, lieu, partieId, numeroTour);
		return false;
	}

	/**
	 * Execute l'attaque d'un lieu par les zombies.
	 *
	 * @param jeu        La partie courante.
	 * @param lieu       Lieu courant de l'attaque.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	private void attaqueZombie(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		Joueur jou;
		if (jeu.getJoueurSurLieu(lieu).size() == 1)
			jou = jeu.getJoueurSurLieu(lieu).get(0);
		else
			jou = cv.phaseVote(jeu, lieu, VoteType.MPZ, partieId, numeroTour);
		List<Integer> listePion = new ArrayList<>();
		for (Personnage p : jou.getPersonnages().values())
			if (lieu.getPersonnage().contains(p))
				listePion.add(p.getPoint());
		azr.demanderSacrifice(listePion, jou, lieu, partieId, numeroTour);
		PionCouleur pionCou = azr.recupSacrifice(jou);
		int pion = PpTools.getPionByValue(pionCou);
		jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
		Evenement.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Evenement.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
		azr.informerSacrifice(jeu, lieu, pionCou, partieId, numeroTour);
		Evenement.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
	}

}
