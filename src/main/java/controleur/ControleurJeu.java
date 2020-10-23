package controleur;

import java.awt.Color;
import java.io.IOException;
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

	public ControleurJeu() throws ClassNotFoundException, IOException {
		this.intPartieId = new Random().nextInt(10000000);
		this.partieId = "P" + intPartieId;

		sc = new Scanner(System.in);

		jeu = new Jeu(this.createJoeur());
		this.placementPersonnage();
		this.jeu.afficheJeu();
	}

	public void start() throws Exception {
		if (jeu.estFini()) {
		} else {
			fouilleCamion();
			electionChefVigi();
			ArrayList<Integer> lieuZombie = new ArrayList<>();
			lieuZombie = arriveZombie();
			ArrayList<Integer> destination = new ArrayList<>();
			destination = choixDestination();
			deplacementPerso(destination, lieuZombie);
			attaqueFinalZombie();
			start();
		}
	}

	private Joueur fouilleCamion() {
		return voteJoueur(4);
	}

	public Jeu getJeu() {
		return jeu;
	}

	private Joueur electionChefVigi() {
		Joueur j = voteJoueur(5);
		jeu.resultatChefVigile(j);
		return j;
	}

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
		// l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5
		// (PC)
		// regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son
		// ecran et defausse la carte
		return lieuZombie;
	}

	private ArrayList<Integer> choixDestination() {
		// si nouveau chef il choisit en premier et affiche son choix
		// les joueurs choissisent le lieu de destination

		ArrayList<Integer> resultat = new ArrayList<>();
		return resultat;

	}

	private void deplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> lieuZombie) {
		// affiche les choix de Destination
		// ig.afficheDestination(destination);

		// affiche les nouveaux zombies
		jeu.entreZombie(lieuZombie);
		// ig.afficheZombie(jeu.getZombies());

		// carte SPRINT NOT TO DO

		// chaque joueur choisi un personnage a deplacer
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			if (jeu.getJoueurs().get(i).isEnVie()) {
				// choisit un perso
				Personnage choixPerso = null; // TO UPDATE
				jeu.deplacePerso(jeu.getJoueurs().get(i), choixPerso, destination.get(i));
			}
		}
	}

	// faux: on ne vote pas pour un personnage mais pour un joueur qui choisi un
	// perso a sacrifier
	// A modifier
	private void attaqueFinalZombie() {
		jeu.lastAttaqueZombie();
		for (int i = 1; i < 7; i++) {
			if (jeu.getLieux().get(i).isOuvert()) {
				if (i == 4) {// si parking
					for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
						Joueur jou = this.voteJoueur(4);
						System.out.print(jou + " choisis un numéro a sacrifier:");
						ArrayList<Integer> num = new ArrayList<>();
						int persEntre;
						for (int b = 0; b < jou.getPersonnages().size(); b++) {
							num.add(b);
							System.out.println(b + "     " + jeu.getJoueurs().get(i).getPersonnages().get(b));
						}
						persEntre = sc.nextInt();
						while (!num.contains(persEntre)) {
							System.out.println();
							System.out.println("Numéro incorect !\n");
							System.out.println(j + " choisis un numéro a sacrifier:");
							for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
								if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
									System.out.println(b + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(b));
								}
							}
							persEntre = sc.nextInt();
						}
						jeu.sacrifie(jou.getPersonnages().get(persEntre));
					}
				} else if (jeu.getLieux().get(i).estAttaquable()) { // les joueurs sur le lieu peuvent utiliser leur
																	// cartes NOT TO DO
					if (jeu.getLieux().get(i).estAttaquable()) {
						Joueur jou = this.voteJoueur(4);
						System.out.print(jou + " choisis un numéro a sacrifier:");
						ArrayList<Integer> num = new ArrayList<>();
						int persEntre;
						for (int b = 0; b < jou.getPersonnages().size(); b++) {
							num.add(b);
							System.out.println(b + "     " + jeu.getJoueurs().get(i).getPersonnages().get(b));
						}
						persEntre = sc.nextInt();
						while (!num.contains(persEntre)) {
							System.out.println();
							System.out.println("Numéro incorect !\n");
							System.out.println(jou + " choisis un numéro a sacrifier:");
							for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
								if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
									System.out.println(b + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(b));
								}
							}
							persEntre = sc.nextInt();
						}
						jeu.sacrifie(jou.getPersonnages().get(persEntre));
					}

				}

			}
		}
	}

	// NOT TO DO
	private Personnage votePerso(int lieu) {
		// gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).getPersonnage().size());
		return jeu.getLieux().get(lieu).getPersonnage().get(rnd);
	}

	private Joueur voteJoueur(int lieu) {
		// gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).afficheJoueurSurLieu().size());
		return jeu.getLieux().get(lieu).afficheJoueurSurLieu().get(rnd);
	}

	private ArrayList<Joueur> createJoeur() {
		System.out.println("Saissisez le nb de joueur réel :");
		nbjr = sc.nextInt();

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
			int choix = sc.nextInt();
			while (!listeCouleur.containsKey(choix)) {
				System.out.println("Saissie non valide, recommencer:");
				choix = sc.nextInt();
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
						if (!this.jeu.getLieux().get(b).isFull()) {
							pos.add(b);
							System.out.println(b + "\t" + jeu.getLieux().get(b) + ": Pas Complet");
						}
					}
					destEntre = sc.nextInt();
					while (!pos.contains(destEntre)) {
						System.out.println();
						System.out.println("Numéro incorect !\n");
						System.out.println("Joueur " + i + " choisit un numéro:");
						for (int b = 1; b < 7; b++) {
							if (!this.jeu.getLieux().get(b).isFull()) {
								System.out.println(b + "\t" + jeu.getLieux().get(b) + ": Complet");
							}
						}
						destEntre = sc.nextInt();
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
					destEntre = sc.nextInt();
					while (destEntre != x && destEntre != y) {
						System.out.println();
						System.out.println("Numéro incorrect!\n");
						System.out.println(x + "\t" + jeu.getLieux().get(x) + ": Pas Complet");
						System.out.println(y + "\t" + jeu.getLieux().get(y) + ": Pas Complet");
						System.out.println("Joueur " + i + " choisit un numéro:");
						destEntre = sc.nextInt();
					}
				}
				System.out
						.println("Joueur " + i + " choisit un personage a déplacer à " + jeu.getLieux().get(destEntre));
				ArrayList<Integer> num = new ArrayList<>();
				for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
					if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
						num.add(b);
						System.out.println(b + "     " + jeu.getJoueurs().get(i).getPersonnages().get(b));
					}
				}
				int persEntre = sc.nextInt();
				while (!num.contains(persEntre)) {
					System.out.println();
					System.out.println("Numéro incorect !\n");
					System.out.println(
							"Joueur " + i + " choisit un personage a déplacer à " + jeu.getLieux().get(destEntre));
					for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
						if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
							System.out.println(b + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(b));
						}
					}
					persEntre = sc.nextInt();
				}
				jeu.getLieux().get(destEntre).addPersonnage(jeu.getJoueurs().get(i).getPersonnages().get(persEntre));
				jeu.getJoueurs().get(i).getPersonnages().get(persEntre).changerDeLieux(jeu.getLieux().get(destEntre));
				System.out.println(this.retourLigne);
			}
		}
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
