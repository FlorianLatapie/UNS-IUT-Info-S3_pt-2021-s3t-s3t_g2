package bot.partie;

import reseau.type.*;

import java.util.*;

/**
 * <h1>La classe controleurJeu</h1>
 *
 * @author Alex
 * @author Aurelien
 * @author Sebastien
 * @version 0
 * @since 04/10/2020
 */

public class ControleurPartie {

	private List<Joueur> jmort;
	private final ArrayList<Joueur> joueurs;
	private ArrayList<Integer> lieuZombie;
	private Couleur couleurBotCourant;
	private Partie partie;

	public Partie getPartie() {
		return partie;
	}

	public ControleurPartie(Partie partie, Couleur c) {
		this.partie = partie;
		this.couleurBotCourant = c;
		this.jmort = partie.getJoueursMort();
		this.lieuZombie = new ArrayList<>();
		this.joueurs = new ArrayList<>();
		for (Joueur j : partie.getJoueurs().values())
			joueurs.add(j);

	}

	/**
	 * Execute le déroulement d'une partie
	 */
	public void iterate(Integer dest) throws InterruptedException {
		HashMap<Couleur, Integer> destination = new HashMap<>();
		System.out.println("phasechoixDestination : " + destination + dest);
		phasechoixDestination(destination, dest);
		System.out.println("entreZombie : " + lieuZombie);
		partie.entreZombie(lieuZombie);
		System.out.println("phaseDeplacementPerso : " + destination + lieuZombie);
		phaseDeplacementPerso(destination, lieuZombie);
		if (partie.estFini())
			return;
		System.out.println("fermerLieu");
		partie.fermerLieu();
		if (partie.estFini())
			return;
		System.out.println("phaseAttaqueZombie");
		phaseAttaqueZombie();
		if (partie.estFini())
			return;
		jmort.clear();
		jmort = partie.getJoueursMort();
		//System.out.println("electionChefVigi");
		//electionChefVigi();
		System.out.println("arriveZombie");
		this.lieuZombie = arriveZombie();
	}

	/**
	 * @return le partie
	 */
	public Partie getJeu() {
		return partie;
	}

	/**
	 * Affiche et met à jour le nouveau chef des vigiles
	 */
	private void electionChefVigi() {
		Joueur j;
		if (!partie.getJoueursSurLieu(5).isEmpty()) {
			if (partie.getJoueursSurLieu(5).size() == 1)
				j = partie.getJoueursSurLieu(5).get(0);
			else
				j = phaseVote(partie.getLieux().get(5), VoteType.ECD);
			if (j != null) {
				partie.setChef(j.getCouleur());
				partie.setNewChef(true);
			} else
				partie.setNewChef(false);
		} else
			partie.setNewChef(false);
	}

	/**
	 * Definit l'arrivée des zombies et l'affiche pour le chef des vigiles
	 *
	 * @return liste des numéro des lieux d'arriveé des zombies
	 */
	private ArrayList<Integer> arriveZombie() {
		ArrayList<Integer> lieuZombies = new ArrayList<>();
		lieuZombies.add(new Random().nextInt(6) + 1);
		lieuZombies.add(new Random().nextInt(6) + 1);
		lieuZombies.add(new Random().nextInt(6) + 1);
		lieuZombies.add(new Random().nextInt(6) + 1);
		return lieuZombies;
	}

	private void phasechoixDestination(HashMap<Couleur, Integer> destination, Integer destinationBot) {

		for (Joueur j : partie.getJoueurs().values()) {
			if (j.isEnVie()) {
				if (j.getCouleur().equals(couleurBotCourant)) {
					destination.put(couleurBotCourant, destinationBot);
				} else {
					List<Integer> possibleActions = new ArrayList<Integer>();
					for (Integer idLieu : partie.getLieuxOuverts()) {
						boolean destValide = false;
						for (Personnage perso : j.getPersonnages().values())
							if (perso.getMonLieu() != idLieu)
								destValide = true;
						if (destValide)
							possibleActions.add(idLieu);
					}
					Collections.shuffle(possibleActions);
					destination.put(j.getCouleur(), possibleActions.get(0));
				}
			}
		}

		partie.getLieux().get(partie.getLieuxOuverts().get(new Random().nextInt(partie.getLieuxOuverts().size())))
				.addZombie();
	}

	private void phaseDeplacementPerso(HashMap<Couleur, Integer> destination, ArrayList<Integer> zombie) {
		for (Joueur j : partie.getJoueurs().values()) {
			if (j.isEnVie()) {
				List<Integer> pions = new ArrayList<>();
				List<Integer> perso = new ArrayList<>();
				for (Personnage p : j.getPersonnages().values())
					if (p.getMonLieu() != destination.get(j.getCouleur()))
						pions.add(p.getPoint());
					else
						perso.add(p.getPoint());
				Collections.shuffle(pions);
				Collections.shuffle(perso);
				if (pions.isEmpty())
					partie.deplacePerso(j.getCouleur(), perso.get(0), 4);
				else
					partie.deplacePerso(j.getCouleur(), pions.get(0), destination.get(j.getCouleur()));
				if (partie.estFini())
					return;
				this.partie.fermerLieu();
			}
		}
	}

	private void phaseAttaqueZombie() {
		partie.lastAttaqueZombie();
		for (int i = 1; i < 7; i++)
			if (partie.getLieux().get(i).estAttaquable()) {
				if (i == 4) {// si parking
					while (partie.getLieux().get(i).getNbZombies() != 0
							&& !partie.getLieux().get(i).getPersonnage().isEmpty()) {
						attaqueZombie(i);
						partie.getLieux().get(i).setNbZombies(partie.getLieux().get(i).getNbZombies() - 1);
						if (partie.estFini())
							return;
					}
				} else {
					attaqueZombie(i);
					partie.getLieux().get(i).setNbZombies(0);
					if (partie.estFini())
						return;
				}
			}

	}

	private void attaqueZombie(int i) {
		Couleur couleur;
		if (partie.getJoueursSurLieu(i).size() == 1)
			couleur = partie.getJoueursSurLieu(i).get(0).getCouleur();
		else
			couleur = phaseVote(partie.getLieux().get(i), VoteType.MPZ).getCouleur();
		List<Integer> listePion = new ArrayList<>();
		for (Personnage p : partie.getJoueurs().get(couleur).getPersonnages().values()) {
			if (partie.getLieux().get(i).getPersonnage().contains(p)) {
				listePion.add(p.getPoint());
			}
		}
		Collections.shuffle(listePion);
		partie.sacrifie(couleur, listePion.get(0));
	}

	public Joueur phaseVote(Lieu l, VoteType tv) {
		if (tv.equals(VoteType.ECD)) {
			int res = new Random().nextInt(partie.getJoueursSurLieu(l.getNum()).size() + 1);
			if (res == partie.getJoueursSurLieu(l.getNum()).size())
				return null;
			return partie.getJoueursSurLieu(l.getNum()).get(res);
		}

		return partie.getJoueursSurLieu(l.getNum())
				.get(new Random().nextInt(partie.getJoueursSurLieu(l.getNum()).size()));
	}

	public void setLieuZombie(ArrayList<Integer> lieuZombie) {
		this.lieuZombie = lieuZombie;
	}
}