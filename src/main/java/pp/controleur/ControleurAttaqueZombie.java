package pp.controleur;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import pp.Personnage;
import pp.PpTools;
import pp.ihm.event.Initializer;
import pp.reseau.AttaqueZombieReseau;
import reseau.socket.ControleurReseau;
import reseau.type.CarteType;
import reseau.type.PionCouleur;
import reseau.type.RaisonType;
import reseau.type.VoteType;
import sun.util.logging.resources.logging;

public class ControleurAttaqueZombie {
	ControleurVote cv;
	ControleurFinPartie cfp;
	AttaqueZombieReseau azr;

	public ControleurAttaqueZombie() {
		cv = new ControleurVote();
		cfp = new ControleurFinPartie();
		azr = new AttaqueZombieReseau();
	}

	public void phaseAttaqueZombie(ControleurJeu cj, Partie jeu, String partieId, int numeroTour) {
		List<Integer> nb = jeu.lastAttaqueZombie();
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
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

	private void reinitJoueurCache(Partie jeu) {
		for (Joueur j : jeu.getJoueurs().values())
			for (Personnage p : j.getPersonnages().values())
				p.setEstCache(false);
	}

	private List<Object> defenseAttaqueZombie(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		List<Object> defense = new ArrayList<>();
		List<Personnage> persoCache = new ArrayList<>();
		int nbCarteMateriel = 0;
		int nbCarteCachette = 0;
		azr.demanderDefense(jeu, lieu, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurSurLieu(lieu)) {
			List<Object> lo = azr.recupDefense(j);
			List<Integer> persoCacheTemp = (List<Integer>) lo.get(1);
			for(Personnage p : j.getPersonnages().values())
				if(persoCacheTemp.contains(p.getPoint()))
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
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		defense.add(persoCache);
		defense.add(nbCarteMateriel);
		return defense;
	}

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
		Initializer.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
		azr.informerSacrifice(jeu, lieu, pionCou, partieId, numeroTour);
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
	}

}
