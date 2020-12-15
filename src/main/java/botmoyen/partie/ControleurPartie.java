package botmoyen.partie;

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
		phasechoixDestination(destination, dest);
		partie.entreZombie(lieuZombie);
		phaseDeplacementPerso(destination, lieuZombie);
		if (partie.estFini())
			return;
		partie.fermerLieu();
		if (partie.estFini())
			return;
		phaseAttaqueZombie();
		if (partie.estFini())
			return;
		jmort.clear();
		jmort = partie.getJoueursMort();
		electionChefVigi();
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
		if (!partie.getLieux().get(5).getJoueurs().isEmpty()) {
			if (partie.getLieux().get(5).getJoueurs().size() == 1)
				j = partie.getLieux().get(5).getJoueurs().get(0);
			else
				j = phaseVote(partie.getLieux().get(5), VoteType.ECD);
			if (j != null) {
				partie.resultatChefVigile(j);
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
		int dest;
		if (partie.getNewChef()) {
			if (partie.getChefVIgile().isEnVie()) {
				if (partie.getChefVIgile().getCouleur().equals(couleurBotCourant))
					dest = destinationBot;
				else {
					List<Integer> possibleActions = new ArrayList<Integer>();
					for (Integer idLieu : partie.getLieuxOuverts()) {
						boolean destValide = false;
						for (Personnage perso : partie.getChefVIgile().getPersonnages().values())
							if (perso.getMonLieu().getNum() != idLieu)
								destValide = true;
						if (destValide)
							possibleActions.add(idLieu);
					}
					Collections.shuffle(possibleActions);
					dest = possibleActions.get(0);
				}
				destination.put(partie.getChefVIgile().getCouleur(), dest);

				for (Joueur j : partie.getJoueurs().values()) {
					if (j.isEnVie()) {
						List<Integer> possibleActions = new ArrayList<Integer>();
						for (Integer idLieu : partie.getLieuxOuverts()) {
							boolean destValide = false;
							for (Personnage perso : j.getPersonnages().values())
								if (perso.getMonLieu().getNum() != idLieu)
									destValide = true;
							if (destValide)
								possibleActions.add(idLieu);
						}
						Collections.shuffle(possibleActions);
						destination.put(j.getCouleur(), possibleActions.get(0));
					}
				}
			}
		} else {
			for (Joueur j : partie.getJoueurs().values()) {
				if (j.isEnVie()) {
					if (j.getCouleur().equals(couleurBotCourant)) {
						destination.put(couleurBotCourant, destinationBot);
					} else {
						List<Integer> possibleActions = new ArrayList<Integer>();
						for (Integer idLieu : partie.getLieuxOuverts()) {
							boolean destValide = false;
							for (Personnage perso : j.getPersonnages().values())
								if (perso.getMonLieu().getNum() != idLieu)
									destValide = true;
							if (destValide)
								possibleActions.add(idLieu);
						}
						Collections.shuffle(possibleActions);
						destination.put(j.getCouleur(), possibleActions.get(0));
					}
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
				for (Personnage p : j.getPersonnages().values())
					if ((p.estVivant) && (p.getMonLieu().getNum() != destination.get(j.getCouleur())))
						pions.add(p.getPoint());
				Collections.shuffle(pions);
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
		Joueur jou;
		if (partie.getLieux().get(i).getJoueurs().size() == 1)
			jou = partie.getLieux().get(i).getJoueurs().get(0);
		else
			jou = phaseVote(partie.getLieux().get(i), VoteType.MPZ);
		List<Integer> listePion = new ArrayList<>();
		for (Personnage p : jou.getPersonnages().values()) {
			if (partie.getLieux().get(i).getPersonnage().contains(p)) {
				listePion.add(p.getPoint());
			}
		}
		Collections.shuffle(listePion);
		partie.sacrifie(jou.getCouleur(), listePion.get(0));
	}

	public Joueur phaseVote(Lieu l, VoteType tv) {
		if (tv.equals(VoteType.ECD)) {
			int res = new Random().nextInt(l.getJoueurs().size()+1);
			if (res == l.getJoueurs().size())
				return null;
			return l.getJoueurs().get(res);
		}
		
		return l.getJoueurs().get(new Random().nextInt(l.getJoueurs().size()));
	}

	public void setLieuZombie(ArrayList<Integer> lieuZombie) {
		this.lieuZombie = lieuZombie;
	}
}