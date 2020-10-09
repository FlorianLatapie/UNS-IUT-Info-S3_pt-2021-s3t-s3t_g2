package controleur;

import java.util.ArrayList;
import java.util.Random;

import jeu.*;

/**
 * The Class controleurJeu.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 */
public class ControleurJeu {
	
	private Jeu jeu;
	private InterfaceGrahique ig = new InterfaceGrahique();
	private GestionnaireReseau rg = new GestionnaireReseau();

	public Controleur() throws ClassNotFoundException, IOException{
		jeu = new Jeu(ig.configJoueurs());
		
	}
	
	public void start() throws Exception {
		if (jeu.estFini()) {
			ig.Fini(jeu.getWinner());
		}
		else {
			fouilleCamion();
			electionChefVigi();
			ArrayList<Integer> lieuZombie = new ArrayList<>();
			lieuZombie = arriveZombie();
			ArrayList<Integer> destination = new ArrayList<>();
			destination = choixDestination();
			deplacementPerso(destination , lieuZombie);
			attaqueFinalZombie();
			start();
		}
	}

	


	private void fouilleCamion() {
		//NOT TO DO
	}
	
	
	private void electionChefVigi() {
		jeu.resultatChefVigile(vote(5));
	}

	private ArrayList<Integer> arriveZombie() {
		int z1 = new Random().nextInt(7);
		int z2 = new Random().nextInt(7);
		int z3 = new Random().nextInt(7);
		int z4 = new Random().nextInt(7);
		ArrayList<Integer> lieuZombie = new ArrayList<>();
		lieuZombie.add(z1);
		lieuZombie.add(z2);
		lieuZombie.add(z3);
		lieuZombie.add(z4);
		//l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5 (PC)
		//regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son ecran et defausse la carte
		return lieuZombie;
	}
	

	private ArrayList<Integer> choixDestination() {
		//si nouveau chef il choisit en premier et affiche son choix
		//les joueurs choissisent le lieu de destination
		
		
		
		
		ArrayList<Integer> resultat = new ArrayList<>();
		return resultat;
		
	}
	
	
	private void deplacementPerso(ArrayList<Integer> destination,ArrayList<Integer> lieuZombie) {
		//affiche les choix de Destination
		ig.afficheDestination(destination);
		
		//affiche les nouveaux zombies
		jeu.entreZombie(lieuZombie);
		ig.afficheZombie(jeu.getZombies());		
		
		//carte SPRINT NOT TO DO
		
		//chaque joueur choisi un personnage a deplacer
		for (int i = 0 ; i < jeu.getJoueurs().size();i++) {
			if (jeu.getJoueurs().get(i).estVivant()) {
				//choisit un perso 
				jeu.deplacePerso(jeu.getJoueurs().get(i),choixPerso)
			}
		}
			
		
		
		
	}



	private void attaqueFinalZombie() {
		jeu.lastAttaqueZombie();
		for (int i=0;i<jeu.getLieux().size();i++) {
			if (jeu.getLieux().get(i).ouvert()) {
				if (jeu.getLieux().get(i).getNum()==4) {//si parking
					for (int i=0;i<jeu.getLieux().get(i).getNbrZombie();i++) {
						jeu.sacrifie(vote(jeu.getLieux().get(i).getNum()));
					}
				}
				else if (jeu.getLieux().get(i).estAttaquable()) {
					//les joueurs sur le lieu peuvent utiliser leur cartes NOT TO DO
					if (jeu.getLieux().get(i).estAttaquable()) {
						jeu.sacrifie(vote(jeu.getLieux().get(i).getNum()));
					}
					
				}
					
			}
		}
	}

	
	//NOT TO DO
	private Joueur vote(int lieu) {
		//gestion des cartes NOT TO DO
		int rnd = new Random().nextInt(jeu.getLieux().get(lieu).getJoueurs().size());
	    return jeu.getLieux().get(lieu).getJoueurs().get(rnd);
		
	}
	

}
