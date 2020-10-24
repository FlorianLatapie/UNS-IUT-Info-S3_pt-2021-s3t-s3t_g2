package controleur;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import jeu.*;

/**
 * The Class controleurJeu.
 * 
 * @author Alex
 * @version 0
 * @since 04/10/2020
 */
public class ControleurJeu {
	private String partieId; // "P" + Numero de la partie Ex : P9458
	private int intPartieId;
	private String nomPartie; // Nom de la partie
	private int nbjtotal; // Nombre de joueurs total
	private int nbjr; // Nombre de joueurs réel max
	private int nbjv; // Nombre de joueurs virtuel max
	private int nbjractuel; // Nombre de joueurs réel actuellement connecté
	private int nbjvactuel; // Nombre de joueurs virtuel actuellement connecté
	private Scanner sc;
	private String retourLigne = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	private Jeu jeu;
	// private InterfaceGrahique ig = new InterfaceGrahique();
	// private GestionnaireReseau rg = new GestionnaireReseau();

	public ControleurJeu() throws Exception {
		this.intPartieId = new Random().nextInt(10000000);
		this.partieId = "P" + intPartieId;

		sc = new Scanner(System.in);

		jeu = new Jeu(this.createJoeur());
		this.placementPersonnage();
		this.jeu.afficheJeu();
		this.start();
	}

	public void start() throws Exception {
		fouilleCamion();
		electionChefVigi();
		ArrayList<Integer> lieuZombie = new ArrayList<>();
		lieuZombie = arriveZombie();
		ArrayList<Integer> destination = new ArrayList<>();
		destination = choixDestination();
		jeu.entreZombie(lieuZombie);
		if (this.finJeu()) {
			return;
		}
		deplacementPerso(destination);
		if (this.finJeu()) {
			return;
		}
		attaqueZombie();
		if (this.finJeu()) {
			return;
		}
		mortJoueur();
		start();
	}

	private void fouilleCamion() {
		if (jeu.getLieux().get(4).afficheJoueurSurLieu().size() > 0) {
			Joueur j = voteJoueur(4);
			System.out.println(j + " fouille le camion!");
			System.out.println("Le camion est vide.");
			System.out.println(this.retourLigne);
		}
	}

	public Jeu getJeu() {
		return jeu;
	}

	private void electionChefVigi() {
		if (jeu.getLieux().get(5).afficheJoueurSurLieu().size() > 0) {
			Joueur j = voteJoueur(5);
			jeu.resultatChefVigile(j);
			System.out.println(j + " est le nouveau chef des vigiles!");
			System.out.println(this.retourLigne);
		}
		System.out.println("Pas de nouveau chef des vigiles!");
	}

	// attention le chef des vigiles peu voir le résultat que s'il a été elu le tour
	// meme
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
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isChefDesVigiles()) {
				System.out.println(
						jeu.getJoueurs().get(i) + " , chef des vigiles, regarde les résulats de l'arrivé des Zombies:");
				for (int a = 0; a < lieuZombie.size(); a++) {
					System.out.println(this.jeu.getLieux().get(lieuZombie.get(a)) + "-> Zombie + 1");
				}
			}
		}
		// l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5
		// (PC)
		// regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son
		// ecran et defausse la carte
		System.out.println(this.retourLigne);
		return lieuZombie;
	}

	private ArrayList<Integer> choixDestination() {
		ArrayList<Integer> resultat = new ArrayList<>();
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			this.jeu.afficheJeu();
			int lieu;
			ArrayList<Integer> dest = new ArrayList<>();
			if (jeu.getJoueurs().get(i).isEnVie()) {
				System.out.println(jeu.getJoueurs().get(i) + " choisis une destination (un numéro):");
				for (int a = 1; a < 7; a++) {
					for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
						if (jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu() != jeu.getLieux().get(a)
								&& jeu.getLieux().get(a).isOuvert() && !dest.contains(a)) {
							dest.add(a);
							System.out.println(a + "\t" + jeu.getLieux().get(a));
						}
					}
				}
				// lieu = sc.nextInt();
				lieu = new Random().nextInt(6) + 1; // temporaire
				System.out.println(lieu); // temporaire
				while (!dest.contains(lieu)) {
					System.out.println();
					System.out.println("Numéro incorect !\n");
					System.out.println(jeu.getJoueurs().get(i) + " choisis une destination (un numéro):");
					for (int a = 0; a < dest.size(); a++) {
						System.out.println(dest.get(a) + "\t" + jeu.getLieux().get(dest.get(a)));
					}
					// lieu = sc.nextInt();
					lieu = new Random().nextInt(6) + 1; // temporaire
					System.out.println(lieu); // temporaire
				}
				resultat.add(lieu);
			}
			System.out.println(this.retourLigne);
		}

		return resultat;
	}

	private void deplacementPerso(ArrayList<Integer> destination) {
		// affiche les choix de Destination
		// ig.afficheDestination(destination);
		// affiche les nouveaux zombies
		// ig.afficheZombie(jeu.getZombies());
		// carte SPRINT NOT TO DO
		int compteur = 0;
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			this.jeu.afficheJeu();
			int choixPerso;
			ArrayList<Integer> pers = new ArrayList<>();
			if (jeu.getJoueurs().get(i).isEnVie()) {
				if (!jeu.getLieux().get(destination.get(compteur)).isFull()
						&& jeu.getLieux().get(destination.get(compteur)).isOuvert()) {
					System.out.println(jeu.getJoueurs().get(i) + " choisit un personage a déplacer à "
							+ jeu.getLieux().get(destination.get(compteur)));
				} else {
					System.out.println(
							jeu.getJoueurs().get(i) + " choisit un personage a déplacer à " + jeu.getLieux().get(4));
				}
				for (Integer a : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
					if (jeu.getJoueurs().get(i).getPersonnages().get(a).getMonLieu() != jeu.getLieux()
							.get(destination.get(compteur))) {
						pers.add(a);
						System.out.println(a + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(a));
					}
				}
				// choixPerso = sc.nextInt();
				choixPerso = new Random().nextInt(3); // temporaire
				System.out.println(choixPerso); // temporaire
				while (!pers.contains(choixPerso)) {
					System.out.println();
					System.out.println("Numéro Incorect!\n");
					System.out.println(jeu.getJoueurs().get(i) + " choisit un personage a déplacer à "
							+ jeu.getLieux().get(destination.get(compteur)));
					for (int a = 0; a < pers.size(); a++) {
						System.out.println(
								pers.get(a) + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(pers.get(a)));
					}
					// choixPerso = sc.nextInt();
					choixPerso = new Random().nextInt(3); // temporaire
					System.out.println(choixPerso); // temporaire
				}
				if (!jeu.getLieux().get(destination.get(compteur)).isFull()
						&& jeu.getLieux().get(destination.get(compteur)).isOuvert()) {
					jeu.deplacePerso(jeu.getJoueurs().get(i), choixPerso, destination.get(compteur));
				} else {
					jeu.deplacePerso(jeu.getJoueurs().get(i), choixPerso, 4);
				}
				compteur += 1;
			}
			System.out.println(this.retourLigne);
		}

	}

	// faux: on ne vote pas pour un personnage mais pour un joueur qui choisi un
	// perso a sacrifier
	// A modifier
	private void attaqueZombie() {
		jeu.lastAttaqueZombie();
		for (int i = 1; i < 7; i++) {
			if (jeu.getLieux().get(i).isOuvert()) {
				if (i == 4) {// si parking
					for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
						if (jeu.getLieux().get(i).getPersonnage().size() > 0) {
							jeu.afficheJeu();
							Joueur jou = this.voteJoueur(4);
							System.out
									.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
							ArrayList<Integer> num = new ArrayList<>();
							int persEntre;
							for (Integer b : jou.getPersonnages().keySet()) {
								if (jeu.getLieux().get(i).getPersonnage().contains(jou.getPersonnages().get(b))) {
									num.add(b);
									System.out.println(b + "     " + jou.getPersonnages().get(b));
								}
							}
							// persEntre = sc.nextInt();
							persEntre = new Random().nextInt(3); // temporaire
							System.out.println(persEntre); // temporaire
							while (!num.contains(persEntre)) {
								jeu.afficheJeu();
								System.out.println();
								System.out.println("Numéro incorect !\n");
								System.out.println(
										jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
								for (int b = 0; b < num.size(); b++) {
									System.out.println(num.get(b) + "\t" + jou.getPersonnages().get(num.get(b)));
								}
								// persEntre = sc.nextInt();
								persEntre = new Random().nextInt(3); // temporaire
								System.out.println(persEntre); // temporaire
							}
							jeu.sacrifie(jou, persEntre);
							System.out.println(this.retourLigne);
							jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);
						}
					}
				} else if (jeu.getLieux().get(i).estAttaquable()) {
					if (jeu.getLieux().get(i).estAttaquable()) {
						jeu.afficheJeu();
						Joueur jou = this.voteJoueur(jeu.getLieux().get(i).getNum());
						System.out.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
						ArrayList<Integer> num = new ArrayList<>();
						int persEntre;
						for (Integer b : jou.getPersonnages().keySet()) {
							if (jeu.getLieux().get(i).getPersonnage().contains(jou.getPersonnages().get(b))) {
								num.add(b);
								System.out.println(b + "     " + jou.getPersonnages().get(b));
							}
						}
						// persEntre = sc.nextInt();
						persEntre = new Random().nextInt(3); // temporaire
						System.out.println(persEntre); // temporaire
						while (!num.contains(persEntre)) {
							jeu.afficheJeu();
							System.out.println();
							System.out.println("Numéro incorect !\n");
							System.out
									.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
							for (int b = 0; b < num.size(); b++) {
								System.out.println(num.get(b) + "\t" + jou.getPersonnages().get(num.get(b)));
							}
							// persEntre = sc.nextInt();
							persEntre = new Random().nextInt(3); // temporaire
							System.out.println(persEntre); // temporaire
						}
						jeu.sacrifie(jou, persEntre);
						System.out.println(this.retourLigne);
					}
					jeu.getLieux().get(i).setNbZombies(0);
				}

			}
		}
		System.out.println(this.retourLigne);
	}

	private Joueur voteJoueur(int lieu) {
		// gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).afficheJoueurSurLieu().size());
		return jeu.getLieux().get(lieu).afficheJoueurSurLieu().get(rnd);
	}

	private ArrayList<Joueur> createJoeur() {
		System.out.println("Saissisez le nb de joueur réel :");
		// nbjr = sc.nextInt();
		nbjr = 5; // temporaire
		System.out.println(nbjr);
		while (nbjr > 5 || nbjr < 0) {
			System.out.println("nb de joueur réel incorrect");
			System.out.println("Saissisez le nb de joueur réel :");
			nbjr = sc.nextInt();
		}
		nbjv = 5 - nbjr;
		HashMap<Integer, Color> listeCouleur = new HashMap<Integer, Color>();
		listeCouleur.put(1, Color.BLACK);
		listeCouleur.put(2, Color.RED);
		listeCouleur.put(3, Color.BLUE);
		listeCouleur.put(4, Color.GREEN);
		listeCouleur.put(5, Color.YELLOW);
		listeCouleur.put(6, Color.ORANGE);
		HashMap<String, String> niveauBot = new HashMap<String, String>();
		niveauBot.put("1", "facile");
		niveauBot.put("2", "moyen");
		niveauBot.put("3", "difficile");
		ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();
		for (int i = 0; i < nbjr; i++) {
			System.out.println("==Couleur Disponible==\n");
			System.out.println(listeCouleur);
			System.out.println("Joueur " + i + " choissisez votre couleur :\n");
			// int choix = sc.nextInt();
			int choix = new Random().nextInt(6) + 1; // temporaire
			System.out.println(choix);
			while (!listeCouleur.containsKey(choix)) {
				System.out.println("Saissie non valide, recommencer:");
				// choix = sc.nextInt();
				choix = new Random().nextInt(6); // temporaire
				System.out.println(choix);
			}
			listeJoueur.add(new Joueur(listeCouleur.get(choix), "joueur " + i));
			listeCouleur.remove(choix);
		}
		for (int j = 0; j < nbjv; j++) {
			System.out.println(niveauBot);
			System.out.println("Choissisez le niveau du joueur virtuel " + j + " :\n");
			String choix = sc.nextLine();
			while ((!niveauBot.containsKey(choix)) || (!niveauBot.containsValue(choix))) {
				System.out.println("Saissie non valide, recommencer:");
				choix = sc.nextLine();
			}
			Integer[] keys = (Integer[]) listeCouleur.keySet().toArray();
			int i = (int) ((double) Math.random() * keys.length);
			Integer randomKey = keys[i];
			if (choix.equals("1") || choix.equals("facile")) {
				// listeJoueur.add(new botFacile(listeCouleur.get(randomKey)));
			} else if (choix.equals("2") || choix.equals("moyen")) {
				// listeJoueur.add(new botMoyen(listeCouleur.get(randomKey)));
			} else if (choix.equals("3") || choix.equals("difficile")) {
				// listeJoueur.add(new botDifficile(listeCouleur.get(randomKey)));
			}
		}
		System.out.println(this.retourLigne);
		return listeJoueur;
	}

	private void placementPersonnage() {
		for (int a = 0; a < jeu.getJoueurs().get(0).getPersonnages().size(); a++) {
			for (int i = 0; i < jeu.getJoueurs().size(); i++) {
				jeu.afficheJeu();
				System.out.println();
				System.out.println("Lancement des dés.");
				int x = new Random().nextInt(6) + 1;
				int y = new Random().nextInt(6) + 1;
				System.out.println("Résultat du lancement :");
				int destEntre;
				if (jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
					System.out.println(x + "\t" + jeu.getLieux().get(x) + ": Complet");
					System.out.println(y + "\t" + jeu.getLieux().get(y) + ": Complet");
					ArrayList<Integer> pos = new ArrayList<>();
					System.out.println("Joueur " + i + " choisit un numéro:");
					for (int b = 1; b < 7; b++) {
						if (!this.jeu.getLieux().get(b).isFull() && this.jeu.getLieux().get(b).isOuvert()) {
							pos.add(b);
							System.out.println(b + "\t" + jeu.getLieux().get(b) + ": Pas Complet");
						}
					}
					// destEntre = sc.nextInt();
					destEntre = new Random().nextInt(4) + 1; // temporaire
					System.out.println(destEntre); // temporaire
					while (!pos.contains(destEntre)) {
						System.out.println();
						System.out.println("Numéro incorect !\n");
						System.out.println("Joueur " + i + " choisit un numéro:");
						for (int b = 0; b < pos.size(); b++) {
							System.out.println(pos.get(b) + "\t" + jeu.getLieux().get(pos.get(b)) + ": Complet");
						}
						// destEntre = sc.nextInt();
						destEntre = new Random().nextInt(4) + 1; // temporaire
						System.out.println(destEntre); // temporaire
					}
				} else if (jeu.getLieux().get(x).isFull() && !jeu.getLieux().get(y).isFull()) {
					System.out.println(x + "\t" + jeu.getLieux().get(x) + ": Complet");
					System.out.println(y + "\t" + jeu.getLieux().get(y) + ": Pas Complet");
					destEntre = y;
				} else if (!jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
					System.out.println(x + "\t" + jeu.getLieux().get(x) + ": Pas Complet");
					System.out.println(y + "\t" + jeu.getLieux().get(y) + ": Complet");
					destEntre = x;
				} else {
					System.out.println(x + "\t" + jeu.getLieux().get(x) + ": Pas Complet");
					System.out.println(y + "\t" + jeu.getLieux().get(y) + ": Pas Complet");
					System.out.println("Joueur " + i + " choisit un numéro:");
					// destEntre = sc.nextInt();
					destEntre = new Random().nextInt(7) + 1; // temporaire
					System.out.println(destEntre); // temporaire
					while (destEntre != x && destEntre != y) {
						System.out.println();
						System.out.println("Numéro incorrect!\n");
						System.out.println(x + "\t" + jeu.getLieux().get(x) + ": Pas Complet");
						System.out.println(y + "\t" + jeu.getLieux().get(y) + ": Pas Complet");
						System.out.println("Joueur " + i + " choisit un numéro:");
						// destEntre = sc.nextInt();
						destEntre = new Random().nextInt(7) + 1; // temporaire
						System.out.println(destEntre); // temporaire
					}
				}
				System.out.println("Joueur " + i + " choisit un personage a placer à " + jeu.getLieux().get(destEntre));
				ArrayList<Integer> num = new ArrayList<>();
				for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
					if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
						num.add(b);
						System.out.println(b + "     " + jeu.getJoueurs().get(i).getPersonnages().get(b));
					}
				}
				// int persEntre = sc.nextInt();
				int persEntre = new Random().nextInt(3); // temporaire
				System.out.println(persEntre); // temporaire
				while (!num.contains(persEntre)) {
					System.out.println();
					System.out.println("Numéro incorect !\n");
					System.out.println(
							"Joueur " + i + " choisit un personage a déplacer à " + jeu.getLieux().get(destEntre));
					for (int b = 0; b < num.size(); b++) {
						System.out
								.println(num.get(b) + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(num.get(b)));
					}
					// persEntre = sc.nextInt();
					persEntre = new Random().nextInt(3); // temporaire
					System.out.println(persEntre); // temporaire
				}
				jeu.placePerso(jeu.getJoueurs().get(i), persEntre, destEntre);
				System.out.println(this.retourLigne);
			}
		}
	}

	public void mortJoueur() {
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie() && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
				this.jeu.getJoueurs().get(i).setEnVie(false);
				System.out.println(this.jeu.getJoueurs().get(i) + " est mort!");
				int dest;
				ArrayList<Integer> num = new ArrayList<>();
				System.out.println();
				System.out.println(this.jeu.getJoueurs().get(i) + " choisis un lieu ou ajouter un Zombie:");
				for (int j = 1; j < 7; j++) {
					if (this.jeu.getLieux().get(j).isOuvert()) {
						num.add(j);
						System.out.println(j + "\t" + this.jeu.getLieux().get(j));
					}
				}
				// dest = sc.nextInt();
				dest = new Random().nextInt(6) + 1; // temporaire
				System.out.println(dest); // temporaire
				while (!num.contains(dest)) {
					System.out.println();
					System.out.println("Numéro incorect !\n");
					System.out.println(this.jeu.getJoueurs().get(i) + " choisis un lieu ou ajouter un Zombie:");
					for (int j = 0; j < num.size(); j++) {
						System.out.println(num.get(j) + "\t" + this.jeu.getLieux().get(num.get(j)));
					}
					// dest = sc.nextInt();
					dest = new Random().nextInt(6) + 1; // temporaire
					System.out.println(dest); // temporaire
				}
				this.jeu.getLieux().get(dest).addZombie();
			}
		}
		System.out.println(this.retourLigne);
	}

	public boolean finJeu() {
		ArrayList<Lieu> lieu = new ArrayList<>();
		int nbPerso = 0;
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie()) {
				nbPerso += this.jeu.getJoueurs().get(i).getPersonnages().size();
				for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
					System.out.println(
							this.jeu.getJoueurs().get(i) + "" + this.jeu.getJoueurs().get(i).getPersonnages().get(j));
					if (!lieu.contains(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu())) {
						lieu.add(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu());
					}
				}
			}
		}
		if ((lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4)) || nbPerso <= 4) {
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
					System.out.println(" Point " + this.jeu.getJoueurs().get(i) + ": " + point);
				} else {
					System.out.println(" Point " + this.jeu.getJoueurs().get(i) + "(mort): " + point);
				}
			}
			if (vainqueur.size() == 1) {
				System.out.println(">>>>> " + vainqueur.get(0) + " a gangné ! <<<<<");
			} else {
				String s = ">>>>> ";
				for (int a = 0; a < vainqueur.size(); a++) {
					s += "  " + vainqueur.get(a);
				}
				s += " sont vainqeurs à égalité! <<<<<";
				System.out.println(s);
			}
			return true;
		}
		return false;

	}

	public String getPartieId() {
		return partieId;
	}

	public int getIntPartieId() {
		return intPartieId;
	}

	public String getNomPartie() {
		return nomPartie;
	}

	public int getNbjtotal() {
		return nbjtotal;
	}

	public int getNbjr() {
		return nbjr;
	}

	public int getNbjv() {
		return nbjv;
	}

	public int getNbjractuel() {
		return nbjractuel;
	}

	public int getNbjvactuel() {
		return nbjvactuel;
	}

}
