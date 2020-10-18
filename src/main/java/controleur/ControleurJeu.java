package controleur;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	private Jeu jeu;
	// private InterfaceGrahique ig = new InterfaceGrahique();
	// private GestionnaireReseau rg = new GestionnaireReseau();

	public ControleurJeu() throws ClassNotFoundException, IOException {
		intPartieId = new Random().nextInt(10000000);
		partieId = "P" + intPartieId;
		
		sc = new Scanner(System.in);
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
			listeJoueur.add(new Joueur(listeCouleur.get(choix)));
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
		jeu = new Jeu(listeJoueur);
	}

	
	
	public void start() throws Exception {
		if (jeu.estFini()) {
			// ig.Fini(jeu.getWinner());
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

	
	
	public Joueur fouilleCamion() {
		return voteJoueur(4);
	}

	
	
	public Jeu getJeu() {
		return jeu;
	}

	
	
	public Joueur electionChefVigi() {
		Joueur j = voteJoueur(5);
		jeu.resultatChefVigile(j);
		return j;
	}

	
	
	public ArrayList<Integer> arriveZombie() {
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
	
	

	public ArrayList<Integer> choixDestination() {
		// si nouveau chef il choisit en premier et affiche son choix
		// les joueurs choissisent le lieu de destination

		ArrayList<Integer> resultat = new ArrayList<>();
		return resultat;

	}

	
	
	public void deplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> lieuZombie) {
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

	
	
	public void attaqueFinalZombie() {
		jeu.lastAttaqueZombie();
		for (int i = 0; i < jeu.getLieux().size(); i++) {
			if (jeu.getLieux().get(i).isOuvert()) {
				if (jeu.getLieux().get(i).getNum() == 4) {// si parking
					for (int j = 0; j < jeu.getLieux().get(j).getNbZombies(); j++) {
						jeu.sacrifie(votePerso(jeu.getLieux().get(j).getNum()));
					}
				} else if (jeu.getLieux().get(i).estAttaquable()) {
					// les joueurs sur le lieu peuvent utiliser leur cartes NOT TO DO
					if (jeu.getLieux().get(i).estAttaquable()) {
						jeu.sacrifie(votePerso(jeu.getLieux().get(i).getNum()));
						jeu.getLieux().get(i).setNbZombies(0);
					}

				}

			}
		}
	}

	
	
	// NOT TO DO
	public Personnage votePerso(int lieu) {
		// gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).getPersonnage().size());
		return jeu.getLieux().get(lieu).getPersonnage().get(rnd);
	}

	
	
	public Joueur voteJoueur(int lieu) {
		// gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).afficheJoueurSurLieu().size());
		return jeu.getLieux().get(lieu).afficheJoueurSurLieu().get(rnd);
	}

}
