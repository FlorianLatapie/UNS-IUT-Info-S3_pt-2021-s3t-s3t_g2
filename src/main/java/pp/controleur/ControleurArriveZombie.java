package pp.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pp.Joueur;
import pp.Partie;
import pp.ihm.event.Evenement;
import pp.reseau.ArriveZombieReseau;
import reseau.type.CarteType;
import reseau.type.VigileEtat;

/**
 * <h1>La classe ControleurArriveZombie</h1>. A pour rôle de gérer la phase
 * d'arrivée des zombies
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ControleurArriveZombie {
	private ArriveZombieReseau azr;

	public ControleurArriveZombie() {
		azr = new ArriveZombieReseau();
	}

	public List<Integer> phaseArriveZombie(Partie jeu, String partieId, int numeroTour) {
		List<Integer> lieuZombie = new ArrayList<>();
		List<Joueur> joueurCDS = new ArrayList<>();
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		azr.debutArriveZombie(jeu, ve, partieId, numeroTour);
		azr.lanceDes(jeu);
		lieuZombie = lanceDes();
		azr.debutArriveZombie(jeu, ve, partieId, numeroTour);
		azr.demanderCDS(jeu, partieId, numeroTour);
		joueurCDS = azr.reponseCDS(jeu);
		for (Joueur j : joueurCDS) {
			azr.informeArriveZombieCDS(jeu, j, lieuZombie, partieId, numeroTour);
			j.getCartes().remove(CarteType.CDS);
		}
		Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		azr.informerUtilisationCDS(jeu, joueurCDS, lieuZombie, partieId, numeroTour);
		return lieuZombie;
	}

	public List<Integer> lanceDes() {
		List<Integer> lieuZombie = new ArrayList<>();
		int z1 = new Random().nextInt(6) + 1;
		int z2 = new Random().nextInt(6) + 1;
		int z3 = new Random().nextInt(6) + 1;
		int z4 = new Random().nextInt(6) + 1;
		lieuZombie.add(z1);
		lieuZombie.add(z2);
		lieuZombie.add(z3);
		lieuZombie.add(z4);
		return lieuZombie;
	}

}
