package controleur;

import java.awt.Color;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import jeu.*;
import reseau.socket.TcpClientSocket;

import static java.lang.System.out;

/**
 * <h1>La classe controleurJeu</h1>
 *
 * @author Alex
 * @author Aurelien
 * @version 0
 * @since 04/10/2020
 */

public class ControleurJeu {
	private final String partieId; // "P" + Numero de la partie Ex : P9458
	private final int intPartieId;
	private String nomPartie; // Nom de la partie
	private int nbjtotal; // Nombre de joueurs total
	private int nbjr; // Nombre de joueurs réel max
	private int nbjv; // Nombre de joueurs virtuel max
	private int nbjractuel; // Nombre de joueurs réel actuellement connecté
	private int nbjvactuel; // Nombre de joueurs virtuel actuellement connecté
	private final Scanner sc;
	private static final String RETOUR_LIGNE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	private final Jeu jeu;

	public ControleurJeu() {
		this.intPartieId = new Random().nextInt(10000000);
		this.partieId = "P" + intPartieId;
		sc = new Scanner(System.in);

		jeu = new Jeu(this.createJoeur());
		this.placementPersonnage();
		System.out.println(jeu.afficheJeu());
		this.start();
	}

	/**
	 * Execute le déroulement d'une parite
	 */
	public void start() {
		fouilleCamion();
		electionChefVigi();
		ArrayList<Integer> lieuZombie = arriveZombie();
		ArrayList<Integer> destination = choixDestination();
		jeu.entreZombie(lieuZombie);
		jeu.fermerLieu();

		if (this.finJeu())
			return;

		if (!deplacementPerso(destination))
			return;
		jeu.fermerLieu();

		if (!attaqueZombie())
			return;

		mortJoueur();
		start();
	}

	/**
	 * Affiche le joueur qui fouille le camion
	 */
	private void fouilleCamion() {
		if (!jeu.getLieux().get(4).afficheJoueurSurLieu().isEmpty()) {
			Joueur j = voteJoueur(4);
			out.println(j + " fouille le camion!");
			out.println("Le camion est vide.");
			out.println(RETOUR_LIGNE);
		}
	}

	/**
	 * @return le jeu
	 */
	public Jeu getJeu() {
		return jeu;
	}

	/**
	 * Affiche et met a jour le nouveau chef des vigiles
	 */
	private void electionChefVigi() {
		if (!jeu.getLieux().get(5).afficheJoueurSurLieu().isEmpty()) {
			Joueur j = voteJoueur(5);
			jeu.resultatChefVigile(j);
			out.println(j + " est le nouveau chef des vigiles!");
		} else {
			out.println("Pas de nouveau chef des vigiles!");
		}

		out.println(RETOUR_LIGNE);
	}

	/**
	 * Definie l'arrivé des zombies et l'affiche pour le chef des vigiles
	 *
	 * @return liste des numéro des lieux d'arrivé des zomibie
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
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isChefDesVigiles()) {
				out.println(
						jeu.getJoueurs().get(i) + " , chef des vigiles, regarde les résulats de l'arrivé des Zombies:");
				for (Integer integer : lieuZombie) {
					out.println(this.jeu.getLieux().get(integer) + "-> Zombie + 1");
				}
			}
		}
		// l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5
		// (PC)
		// regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son
		// ecran et defausse la carte
		out.println(RETOUR_LIGNE);
		return lieuZombie;
	}

	/**
	 * @return
	 */
	private ArrayList<Integer> choixDestination() {
		ArrayList<Integer> resultat = new ArrayList<>();
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			System.out.println(jeu.afficheJeu());
			int lieu;
			ArrayList<Integer> dest = new ArrayList<>();
			if (jeu.getJoueurs().get(i).isEnVie()) {
				out.println(jeu.getJoueurs().get(i) + " choisis une destination (un numéro):");
				for (int a = 1; a < 7; a++) {
					for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
						if (jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu() != jeu.getLieux().get(a)
								&& jeu.getLieux().get(a).isOuvert() && !dest.contains(a)) {
							dest.add(a);
							out.println(a + "\t" + jeu.getLieux().get(a));
						}
					}
				}
				// lieu = sc.nextInt();
				lieu = new Random().nextInt(6) + 1; // temporaire
				out.println(lieu); // temporaire
				while (!dest.contains(lieu)) {
					out.println(RETOUR_LIGNE);
					System.out.println(jeu.afficheJeu());
					out.println();
					out.println("Numéro incorect !\n");
					out.println(jeu.getJoueurs().get(i) + " choisis une destination (un numéro):");
					for (Integer integer : dest) {
						out.println(integer + "\t" + jeu.getLieux().get(integer));
					}
					// lieu = sc.nextInt();
					lieu = new Random().nextInt(6) + 1; // temporaire
					out.println(lieu); // temporaire
				}
				resultat.add(lieu);
			}
			out.println(RETOUR_LIGNE);
		}

		return resultat;
	}

	/**
	 * @param destination
	 * @return
	 */
	private boolean deplacementPerso(ArrayList<Integer> destination) {
		// ig.afficheDestination(destination);
		// ig.afficheZombie(jeu.getZombies());
		// carte SPRINT NOT TO DO
		int compteur = 0;
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			int choixPerso;
			ArrayList<Integer> pers = new ArrayList<>();
			if (jeu.getJoueurs().get(i).isEnVie()) {
				System.out.println(jeu.afficheJeu());
				int compt = 0;
				for (int j = 0; j < jeu.getJoueurs().size(); j++) {
					if (jeu.getJoueurs().get(j).isEnVie()) {
						out.println("Destination " + jeu.getJoueurs().get(j) + ": "
								+ jeu.getLieux().get(destination.get(compt)));
						compt += 1;
					}
				}
				out.println();
				if (!jeu.getLieux().get(destination.get(compteur)).isFull()
						&& jeu.getLieux().get(destination.get(compteur)).isOuvert()) {
					out.println(MessageFormat.format("{0} choisit un personage a déplacer à {1}",
							jeu.getJoueurs().get(i), jeu.getLieux().get(destination.get(compteur))));
				} else {
					if (!jeu.getLieux().get(destination.get(compteur)).isOuvert()) {
						out.println(jeu.getLieux().get(destination.get(compteur)) + " est fermé.");
					} else if (jeu.getLieux().get(destination.get(compteur)).isFull()) {
						out.println(jeu.getLieux().get(destination.get(compteur)) + " est complet.");
					}
					out.println(
							jeu.getJoueurs().get(i) + " choisit un personage a déplacer à " + jeu.getLieux().get(4));
				}
				for (Integer a : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
					if (jeu.getJoueurs().get(i).getPersonnages().get(a).getMonLieu() != jeu.getLieux()
							.get(destination.get(compteur))) {
						pers.add(a);
						out.println(a + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(a));
					}
				}
				// choixPerso = sc.nextInt();
				choixPerso = new Random().nextInt(3); // temporaire
				out.println(choixPerso); // temporaire
				while (!pers.contains(choixPerso)) {
					out.println(RETOUR_LIGNE);
					System.out.println(jeu.afficheJeu());
					int compt1 = 0;
					for (int j = 0; j < jeu.getJoueurs().size(); j++) {
						if (jeu.getJoueurs().get(j).isEnVie()) {
							out.println("Destination " + jeu.getJoueurs().get(j) + ": "
									+ jeu.getLieux().get(destination.get(compt1)));
							compt1 += 1;
						}
					}
					out.println();
					out.println("Numéro Incorect!\n");
					out.println(jeu.getJoueurs().get(i) + " choisit un personage a déplacer à "
							+ jeu.getLieux().get(destination.get(compteur)));
					for (Integer per : pers) {
						out.println(per + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(per));
					}
					// choixPerso = sc.nextInt();
					choixPerso = new Random().nextInt(3); // temporaire
					out.println(choixPerso); // temporaire
				}
				if (!jeu.getLieux().get(destination.get(compteur)).isFull()
						&& jeu.getLieux().get(destination.get(compteur)).isOuvert()) {
					jeu.deplacePerso(jeu.getJoueurs().get(i), choixPerso, destination.get(compteur));
				} else {
					jeu.deplacePerso(jeu.getJoueurs().get(i), choixPerso, 4);
				}
				compteur += 1;
			}
			out.println(RETOUR_LIGNE);
			if (this.finJeu()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return false si fin du jeu, sinon true
	 */
	private boolean attaqueZombie() {
		jeu.lastAttaqueZombie();
		for (int i = 1; i < 7; i++) {
			if (jeu.getLieux().get(i).isOuvert()) {
				if (i == 4) {// si parking
					for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
						if (!jeu.getLieux().get(i).getPersonnage().isEmpty()) {
							System.out.println(jeu.afficheJeu());
							Joueur jou = this.voteJoueur(4);
							out.println(MessageFormat.format("{0} choisis un numéro a sacrifier a {1}: ", jou,
									jeu.getLieux().get(i)));
							ArrayList<Integer> num = new ArrayList<>();
							int persEntre;
							for (Integer b : jou.getPersonnages().keySet()) {
								if (jeu.getLieux().get(i).getPersonnage().contains(jou.getPersonnages().get(b))) {
									num.add(b);
									out.println(MessageFormat.format("{0}\t{1}", b, jou.getPersonnages().get(b)));
								}
							}
							// persEntre = sc.nextInt();
							persEntre = new Random().nextInt(3); // temporaire
							out.println(persEntre); // temporaire
							while (!num.contains(persEntre)) {
								out.println(RETOUR_LIGNE);
								System.out.println(jeu.afficheJeu());
								out.println();
								out.println("Numéro incorect !\n");
								out.println(MessageFormat.format("{0} choisis un numéro a sacrifier a {1}: ", jou,
										jeu.getLieux().get(i)));
								for (int b = 0; b < num.size(); b++) {
									out.println(num.get(b) + "\t" + jou.getPersonnages().get(num.get(b)));
								}
								// persEntre = sc.nextInt();
								persEntre = new Random().nextInt(3); // temporaire
								out.println(persEntre); // temporaire
							}
							jeu.sacrifie(jou, persEntre);
							out.println(RETOUR_LIGNE);
							jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);
						}
						if (this.finJeu()) {
							return false;
						}
					}
				} else if (jeu.getLieux().get(i).estAttaquable()) {
					System.out.println(jeu.afficheJeu());
					Joueur jou = this.voteJoueur(jeu.getLieux().get(i).getNum());
					out.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
					ArrayList<Integer> num = new ArrayList<>();
					int persEntre;
					for (Integer b : jou.getPersonnages().keySet()) {
						if (jeu.getLieux().get(i).getPersonnage().contains(jou.getPersonnages().get(b))) {
							num.add(b);
							out.println(b + "     " + jou.getPersonnages().get(b));
						}
					}
					// persEntre = sc.nextInt();
					persEntre = new Random().nextInt(3); // temporaire
					out.println(persEntre); // temporaire
					while (!num.contains(persEntre)) {
						out.println(RETOUR_LIGNE);
						System.out.println(jeu.afficheJeu());
						out.println();
						out.println("Numéro incorect !\n");
						out.println(jou + " choisis un numéro a sacrifier a " + jeu.getLieux().get(i) + ": ");
						for (int b = 0; b < num.size(); b++) {
							out.println(num.get(b) + "\t" + jou.getPersonnages().get(num.get(b)));
						}
						// persEntre = sc.nextInt();
						persEntre = new Random().nextInt(3); // temporaire
						out.println(persEntre); // temporaire
					}
					jeu.sacrifie(jou, persEntre);
					out.println(RETOUR_LIGNE);
				}
				jeu.getLieux().get(i).setNbZombies(0);
				if (this.finJeu()) {
					return false;
				}

			}
		}
		out.println(RETOUR_LIGNE);
		return true;
	}

	/**
	 * @param lieu
	 * @return
	 */
	private Joueur voteJoueur(int lieu) {
		// gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).afficheJoueurSurLieu().size());
		return jeu.getLieux().get(lieu).afficheJoueurSurLieu().get(rnd);
	}

	/**
	 * @return
	 */
	private ArrayList<Joueur> createJoeur() {
		out.println("Saissisez le nb de joueur réel :");
		// nbjr = sc.nextInt();
		nbjr = 5; // temporaire
		out.println(nbjr);
		while (nbjr > 5 || nbjr < 0) {
			out.println("nb de joueur réel incorrect");
			out.println("Saissisez le nb de joueur réel :");
			nbjr = sc.nextInt();
		}
		nbjv = 5 - nbjr;
		HashMap<Integer, Color> listeCouleur = new HashMap<>();
		listeCouleur.put(1, Color.BLACK);
		listeCouleur.put(2, Color.RED);
		listeCouleur.put(3, Color.BLUE);
		listeCouleur.put(4, Color.GREEN);
		listeCouleur.put(5, Color.YELLOW);
		listeCouleur.put(6, Color.ORANGE);
		HashMap<String, String> niveauBot = new HashMap<>();
		niveauBot.put("1", "facile");
		niveauBot.put("2", "moyen");
		niveauBot.put("3", "difficile");
		ArrayList<Joueur> listeJoueur = new ArrayList<>();
		for (int i = 0; i < nbjr; i++) {
			out.println("==Couleur Disponible==\n");
			out.println(listeCouleur);
			out.println(MessageFormat.format("Joueur {0} choissisez votre couleur :\n", i));
			// int choix = sc.nextInt();
			int choix = new Random().nextInt(6) + 1; // temporaire
			out.println(choix);
			while (!listeCouleur.containsKey(choix)) {
				out.println("Saissie non valide, recommencer:");
				// choix = sc.nextInt();
				choix = new Random().nextInt(6); // temporaire
				out.println(choix);
			}
			listeJoueur.add(new Joueur(listeCouleur.get(choix), "joueur " + i));
			listeCouleur.remove(choix);
		}
		for (int j = 0; j < nbjv; j++) {
			out.println(niveauBot);
			out.println("Choissisez le niveau du joueur virtuel " + j + " :\n");
			String choix = sc.nextLine();
			while ((!niveauBot.containsKey(choix)) || (!niveauBot.containsValue(choix))) {
				out.println("Saissie non valide, recommencer:");
				choix = sc.nextLine();
			}
			/*
			 * Integer[] keys = (Integer[]) listeCouleur.keySet().toArray(); int i = (int)
			 * (Math.random() * keys.length); Integer randomKey = keys[i]; if
			 * (choix.equals("1") || choix.equals("facile")) { // listeJoueur.add(new
			 * botFacile(listeCouleur.get(randomKey))); } else if (choix.equals("2") ||
			 * choix.equals("moyen")) { // listeJoueur.add(new
			 * botMoyen(listeCouleur.get(randomKey))); } else if (choix.equals("3") ||
			 * choix.equals("difficile")) { // listeJoueur.add(new
			 * botDifficile(listeCouleur.get(randomKey))); }
			 */
		}
		out.println(RETOUR_LIGNE);
		return listeJoueur;
	}

	/**
	 *
	 */
	private void placementPersonnage() {
		for (int a = 0; a < jeu.getJoueurs().get(0).getPersonnages().size(); a++) {
			for (int i = 0; i < jeu.getJoueurs().size(); i++) {
				System.out.println(jeu.afficheJeu());
				out.println();
				out.println("Lancement des dés.");
				int x = new Random().nextInt(6) + 1;
				int y = new Random().nextInt(6) + 1;
				out.println("Résultat du lancement :");
				int destEntre;
				if (jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
					out.println(MessageFormat.format("{0}\t{1}: Complet", x, jeu.getLieux().get(x)));
					out.println(MessageFormat.format("{0}\t{1}: Complet", y, jeu.getLieux().get(y)));
					ArrayList<Integer> pos = new ArrayList<>();
					out.println();
					out.println(MessageFormat.format("Joueur {0} choisit un numéro:", i));
					for (int b = 1; b < 7; b++) {
						if (!this.jeu.getLieux().get(b).isFull() && this.jeu.getLieux().get(b).isOuvert()) {
							pos.add(b);
							out.println(b + "\t" + jeu.getLieux().get(b) + ": Pas Complet");
						}
					}
					// destEntre = sc.nextInt();
					destEntre = new Random().nextInt(4) + 1; // temporaire
					out.println(destEntre); // temporaire
					while (!pos.contains(destEntre)) {
						out.println();
						out.println("Numéro incorect !\n");
						out.println(MessageFormat.format("Joueur {0} choisit un numéro:", i));
						for (Integer po : pos) {
							out.println(MessageFormat.format("{0}\t{1}: Complet", po, jeu.getLieux().get(po)));
						}
						// destEntre = sc.nextInt();
						destEntre = new Random().nextInt(4) + 1; // temporaire
						out.println(destEntre); // temporaire
					}
				} else if (jeu.getLieux().get(x).isFull() && !jeu.getLieux().get(y).isFull()) {
					out.println(MessageFormat.format("{0}\t{1}: Complet", x, jeu.getLieux().get(x)));
					out.println(MessageFormat.format("{0}\t{1}: Pas Complet", y, jeu.getLieux().get(y)));
					destEntre = y;
				} else if (!jeu.getLieux().get(x).isFull() && jeu.getLieux().get(y).isFull()) {
					out.println(MessageFormat.format("{0}\t{1}: Pas Complet", x, jeu.getLieux().get(x)));
					out.println(MessageFormat.format("{0}\t{1}: Complet", y, jeu.getLieux().get(y)));
					destEntre = x;
				} else {
					out.println(MessageFormat.format("{0}\t{1}: Pas Complet", x, jeu.getLieux().get(x)));
					out.println(MessageFormat.format("{0}\t{1}: Pas Complet", y, jeu.getLieux().get(y)));
					out.println(MessageFormat.format("Joueur {0} choisit un numéro:", i));
					// destEntre = sc.nextInt();
					destEntre = new Random().nextInt(6) + 1; // temporaire
					out.println(destEntre); // temporaire
					while (destEntre != x && destEntre != y) {
						out.println(RETOUR_LIGNE);
						System.out.println(jeu.afficheJeu());
						out.println();
						out.println("Numéro incorrect!\n");
						out.println(MessageFormat.format("{0}\t{1}: Pas Complet", x, jeu.getLieux().get(x)));
						out.println(MessageFormat.format("{0}\t{1}: Pas Complet", y, jeu.getLieux().get(y)));
						out.println(MessageFormat.format("Joueur {0} choisit un numéro:", i));
						// destEntre = sc.nextInt();
						destEntre = new Random().nextInt(6) + 1; // temporaire
						out.println(destEntre); // temporaire
					}
				}
				out.println();
				out.println("Joueur " + i + " choisit un personage a placer à " + jeu.getLieux().get(destEntre));
				ArrayList<Integer> num = new ArrayList<>();
				for (int b = 0; b < jeu.getJoueurs().get(i).getPersonnages().size(); b++) {
					if (jeu.getJoueurs().get(i).getPersonnages().get(b).getMonLieu() == null) {
						num.add(b);
						out.println(b + "     " + jeu.getJoueurs().get(i).getPersonnages().get(b));
					}
				}
				// int persEntre = sc.nextInt();
				int persEntre = new Random().nextInt(3); // temporaire
				out.println(persEntre); // temporaire
				while (!num.contains(persEntre)) {
					out.println(RETOUR_LIGNE);
					System.out.println(jeu.afficheJeu());
					out.println();
					out.println("Numéro incorect !\n");
					out.println("Joueur " + i + " choisit un personage a placer à " + jeu.getLieux().get(destEntre));
					for (Integer integer : num) {
						out.println(integer + "\t" + jeu.getJoueurs().get(i).getPersonnages().get(integer));
					}
					// persEntre = sc.nextInt();
					persEntre = new Random().nextInt(3); // temporaire
					out.println(persEntre); // temporaire
				}
				jeu.placePerso(jeu.getJoueurs().get(i), persEntre, destEntre);
				out.println(RETOUR_LIGNE);
			}
		}
	}

	/**
	 *
	 */
	public void mortJoueur() {
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie() && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
				this.jeu.getJoueurs().get(i).setEnVie(false);
				out.println(this.jeu.getJoueurs().get(i) + " est mort!");
				int dest;
				ArrayList<Integer> num = new ArrayList<>();
				out.println();
				out.println(this.jeu.getJoueurs().get(i) + " choisis un lieu ou ajouter un Zombie:");
				for (int j = 1; j < 7; j++) {
					if (this.jeu.getLieux().get(j).isOuvert()) {
						num.add(j);
						out.println(j + "\t" + this.jeu.getLieux().get(j));
					}
				}
				// dest = sc.nextInt();
				dest = new Random().nextInt(6) + 1; // temporaire
				out.println(dest); // temporaire
				while (!num.contains(dest)) {
					out.println(RETOUR_LIGNE);
					System.out.println(jeu.afficheJeu());
					out.println();
					out.println("Numéro incorect !\n");
					out.println(this.jeu.getJoueurs().get(i) + " choisis un lieu ou ajouter un Zombie:");
					for (Integer integer : num) {
						out.println(integer + "\t" + this.jeu.getLieux().get(integer));
					}
					// dest = sc.nextInt();
					dest = new Random().nextInt(6) + 1; // temporaire
					out.println(dest); // temporaire
				}
				this.jeu.getLieux().get(dest).addZombie();
			}
		}
		out.println(RETOUR_LIGNE);
	}

	/**
	 * Detecte et affiche la fin du jeu
	 *
	 * @return si c'est la fin du jeu
	 */
	public boolean finJeu() {
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
		if ((lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4)) || nbPerso <= 4) {
			for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
				if (this.jeu.getJoueurs().get(i).isEnVie()
						&& this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
					this.jeu.getJoueurs().get(i).setEnVie(false);
				}
			}
			System.out.println(jeu.afficheJeu());
			out.println();
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
					out.println(" Point " + this.jeu.getJoueurs().get(i) + ": " + point);
				} else {
					out.println(" Point " + this.jeu.getJoueurs().get(i) + "(mort): " + point);
				}
				out.println();
			}
			if (vainqueur.size() == 1) {
				out.println(">>>>> " + vainqueur.get(0) + " a gagné ! <<<<<");
			} else {
				StringBuilder s = new StringBuilder(">>>>> ");
				for (Joueur joueur : vainqueur) {
					s.append("  ").append(joueur);
				}
				s.append(" sont vainqeurs à égalité! <<<<<");
				out.println(s);
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