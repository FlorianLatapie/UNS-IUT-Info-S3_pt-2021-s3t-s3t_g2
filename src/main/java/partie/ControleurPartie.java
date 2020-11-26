package partie;

import reseau.socket.Connexion;
import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;
import reseau.type.*;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.util.*;

import static java.lang.System.out;

/**
 * <h1>La classe controleurJeu</h1>
 *
 * @author Alex
 * @version 0
 * @since 04/10/2020
 */

public class ControleurPartie {
	/* Réglage de la partie */

	private final int nbjtotal; // Nombre de joueurs total
	private int numeroTour = 1;
	private Partie jeu;
	private List<Joueur> jmort;
	private Status status;
	private final ArrayList<Joueur> joueurs;
	private ArrayList<Integer> lieuZombie;
	private boolean couleurPret = false;
	private boolean isFinished = false;
	private final Random rd = new Random();

	public ControleurPartie(int nbjt) {
		jmort = new ArrayList<>();
		nbjtotal = nbjt;
		lieuZombie = new ArrayList<>();
		joueurs = new ArrayList<>();
		initPartie();
	}

	private void initPartie() {
		while (joueurs.size() != this.nbjtotal)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				return;
			}
		status = Status.COMPLETE;
		joueurs.get(0).setChefDesVigiles(true);
		jeu = new Partie(joueurs);
		while (!couleurPret)
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				return;
			}
		try {
			demarerJeu();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void setJoueurCouleur(List<Couleur> couleurs) {
		this.couleurPret = true;
		for (int i = 0; i < joueurs.size(); i++)
			joueurs.get(i).setCouleur(couleurs.get(i));
	}

	private List<Couleur> getJoueursCouleurs() {
		List<Couleur> lc = new ArrayList<>();
		lc.add(jeu.getChefVIgile().getCouleur());
		for (Joueur j : jeu.getJoueurs().values())
			if (j != jeu.getChefVIgile() && j.isEnVie())
				lc.add(j.getCouleur());
		return lc;
	}

	private void demarerJeu() throws InterruptedException {
		// TODO 3 ou 4 PION | UN OU PLUSIEURS LIEUX FERME
		placementPersonnage();
		jeu.resultatChefVigile(jeu.getJoueurs().get(0));
		lieuZombie = arriveZombie();
		jeu.entreZombie(lieuZombie);
		start();
	}

	/**
	 * Execute le déroulement d'une partie
	 */
	public void start() throws InterruptedException {
		fouilleCamion();
		electionChefVigi();
		this.lieuZombie = arriveZombie();
		ArrayList<Integer> destination = new ArrayList<>();
		phasechoixDestination(destination);
		jeu.entreZombie(lieuZombie);
		phaseDeplacementPerso(destination, lieuZombie);
		if (isFinished)
			return;
		finJeu();
		jeu.fermerLieu();
		if (isFinished)
			return;
		attaqueZombie();
		if (isFinished)
			return;
		jmort.clear();
		jmort = jeu.getJoueursMort();
		numeroTour++;
		start();
	}

	/**
	 * Affiche le joueur qui fouille le camion
	 */
	private void fouilleCamion() {
		if (!jeu.getLieux().get(4).afficheJoueurSurLieu().isEmpty()) {
			Joueur j = jeu.voteJoueur(4);
		} else {

		}
		// TODO CARTE NUL
	}

	private List<PionCouleur> getPersosLieu(int i) {
		List<PionCouleur> pc = new ArrayList<>();
		Lieu l = jeu.getLieux().get(i);
		for (Personnage p : l.getPersonnage())
			pc.add(PionCouleur.valueOf(p.getJoueur().getCouleur().toString().substring(0, 1) + p.getPoint()));
		return pc;
	}

	/**
	 * @return le jeu
	 */
	public Partie getJeu() {
		return jeu;
	}

	/**
	 * Affiche et met à jour le nouveau chef des vigiles
	 */
	private void electionChefVigi() {
		if (!jeu.getLieux().get(5).afficheJoueurSurLieu().isEmpty()) {
			Joueur j = jeu.voteJoueur(5);
			jeu.resultatChefVigile(j);
			jeu.setNewChef(true);

		} else {
			jeu.setNewChef(false);
		}
	}

	/**
	 * Definit l'arrivée des zombies et l'affiche pour le chef des vigiles
	 *
	 * @return liste des numéro des lieux d'arriveé des zombies
	 */
	private ArrayList<Integer> arriveZombie() {
		int z1 = new Random().nextInt(6) + 1;
		int z2 = new Random().nextInt(6) + 1;
		int z3 = new Random().nextInt(6) + 1;
		int z4 = new Random().nextInt(6) + 1;
		ArrayList<Integer> lieuZombie = new ArrayList<>();
		lieuZombie.add(z1);
		lieuZombie.add(z2);
		lieuZombie.add(z3);
		lieuZombie.add(z4);
		return lieuZombie;
	}

	private void phasechoixDestination(ArrayList<Integer> destination) throws InterruptedException {

		if (jeu.getNewChef()) {
			int dest = 0; // a modifie
			destination.add(dest);
			HashMap<String, Integer> idj = new HashMap<>();
			for (Joueur j : jeu.getJoueurs().values())
				if (j != jeu.getChefVIgile() && j.isEnVie()) {
//					String id = ;
//					int idJoueur = ;
//					idj.put(id, idJoueur);
				}
			for (Joueur j : jeu.getJoueurs().values())
				if (!j.isChefDesVigiles() && j.isEnVie())
					for (Map.Entry<String, Integer> d : idj.entrySet())
						if (d.getKey().equals(j.getJoueurId()))
							destination.add(d.getValue());
		} else {
			HashMap<String, Integer> idj = new HashMap<>();

			for (Joueur j : jeu.getJoueurs().values())
				if (j.isEnVie()) {
//					String id = ;
//					int idJoueur = ;
//					idj.put(id, idJoueur);
				}
			for (Map.Entry<String, Integer> d : idj.entrySet())
				if (d.getKey().equals(jeu.getChefVIgile().getJoueurId()))
					destination.add(d.getValue());
			for (Joueur j : jeu.getJoueurs().values())
				if (!j.isChefDesVigiles() && j.isEnVie())
					for (Map.Entry<String, Integer> d : idj.entrySet())
						if (d.getKey().equals(j.getJoueurId()))
							destination.add(d.getValue());
		}
		for (Joueur j : this.jeu.getJoueurs().values()) {
			if (jmort.contains(j)) {
//				int dvz = (int) nwm.getValueTcp("CDDZVJE", rep, 1);
//				jeu.getLieux().get(dvz).addZombie();
			}
		}

	}

	private void phaseDeplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> zombie) {

		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			if (jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
//				int dest = (int) nwm.getValueTcp("DPR", message, 1);
//				int pion = (int) nwm.getValueTcp("DPR", message, 2);
//				jeu.deplacePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(pion), dest);
				finJeu();
				if (isFinished)
					return;
				jeu.fermerLieu();

			}
		}
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			if (!jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
//				int dest = (int) nwm.getValueTcp("DPR", message, 1);
//				int pion = (int) nwm.getValueTcp("DPR", message, 2);
//				jeu.deplacePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(pion), dest);
				finJeu();
				if (isFinished)
					return;
				this.jeu.fermerLieu();
			}
		}
	}

	private void attaqueZombie() {
//		List<Integer> nb = jeu.lastAttaqueZombie();
		for (int i = 1; i < 7; i++) {
			if (jeu.getLieux().get(i).isOuvert()) {
				if (i == 4) {// si parking
					for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
						if (!jeu.getLieux().get(i).getPersonnage().isEmpty()) {
							Joueur jou = jeu.voteJoueur(4);
							List<Integer> listePion = new ArrayList<>();
							for (Personnage p : jou.getPersonnages().values()) {
								if (p.getMonLieu() == jeu.getLieux().get(i)) {
									listePion.add(p.getPoint());
								}
							}
//							PionCouleur pionCou = (PionCouleur) nwm.getValueTcp("RAZCS", rep, 2);
//							int pion = PpTools.getPionByValue(pionCou);
//							jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
//							jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);
						}
						this.finJeu();
						if (isFinished)
							return;
					}
				} else if (jeu.getLieux().get(i).estAttaquable()) {
					Joueur jou = jeu.voteJoueur(jeu.getLieux().get(i).getNum());
					List<Integer> listePion = new ArrayList<>();
					for (Personnage p : jou.getPersonnages().values()) {
						if (p.getMonLieu() == jeu.getLieux().get(i)) {
							listePion.add(p.getPoint());
						}
					}
//					PionCouleur pionCou = (PionCouleur) nwm.getValueTcp("RAZCS", rep, 2);
//					int pion = PpTools.getPionByValue(pionCou);
//					jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
//					jeu.getLieux().get(i).setNbZombies(0);
				}
				this.finJeu();
				if (isFinished)
					return;
			}
		}
	}

	private void placementPersonnage() {
		for (int n = 0; n < jeu.getJoueurs().get(0).getPersonnages().size(); n++) {
			for (int i = 0; i < jeu.getJoueurs().size(); i++) {
				int x = rd.nextInt(6) + 1;
				int y = rd.nextInt(6) + 1;
				List<Integer> des = new ArrayList<>();
				des.add(x);
				des.add(y);
//				int destEntre = (int) nwm.getValueTcp("PICD", rep, 1);
//				int persEntre = (int) nwm.getValueTcp("PICD", rep, 2);
//				jeu.placePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(persEntre), destEntre);
			}
		}
	}

	/**
	 * Detecte et affiche la fin du jeu
	 *
	 * @return si c'est la fin du jeu
	 */
	public void finJeu() {
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie() && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
				this.jeu.getJoueurs().get(i).setEnVie(false);
			}
		}
		ArrayList<Lieu> lieu = new ArrayList<>();
		int nbPerso = 0;
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie()) {
				nbPerso += this.jeu.getJoueurs().get(i).getPersonnages().size();
				for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
					if (!lieu.contains(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu())) {
						lieu.add(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu());
					}
				}
			}
		}
		if ((lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4))
				|| (nbPerso <= 4 && jeu.getJoueurs().size() < 6) || (nbPerso <= 6 && jeu.getJoueurs().size() == 6)) {
			int pointVainqueur = 0;
			ArrayList<Joueur> vainqueur = new ArrayList<>();
			for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
				int point = 0;
				if (this.jeu.getJoueurs().get(i).isEnVie()) {
					for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
						point += this.jeu.getJoueurs().get(i).getPersonnages().get(j).getPoint();
					}
					if (point > pointVainqueur) {
						pointVainqueur = point;
						vainqueur.clear();
						vainqueur.add(jeu.getJoueurs().get(i));
					} else if (point == pointVainqueur) {
						vainqueur.add(jeu.getJoueurs().get(i));
					}
				}
			}
			isFinished = true;
			// Joueur gagnantNotFair = vainqueur.get(new
			// Random().nextInt(vainqueur.size()));
			status = Status.COMPLETE;
		}
	}



	public int getNbjtotal() {
		return nbjtotal;
	}


	public int getNumeroTour() {
		return numeroTour;
	}

	public Status getStatus() {
		return status;
	}

	public void setLieuZombie(ArrayList<Integer> lieuZombie) {
		this.lieuZombie = lieuZombie;
	}
}