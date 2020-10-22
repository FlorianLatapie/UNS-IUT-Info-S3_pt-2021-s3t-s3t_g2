package jeu;

import java.util.ArrayList;

/**
 * <h1> La classe jeu </h1>
 * 
 * 
 * @author Alex le dieu
 * @version 0.1
 * @since 04/10/2020
 */
public class Jeu {
	ArrayList<Lieu> lieux;
	ArrayList<Joueur> joueurs;
	ArrayList<CarteAction> cartes;

	public Jeu(ArrayList<Joueur> listeJoueursInitiale) {
		lieux = new ArrayList<>();
		joueurs = new ArrayList<>();
		cartes = new ArrayList<>();
//		initCarte();
		for (int i = 0; i < listeJoueursInitiale.size(); i++)
			joueurs.add(listeJoueursInitiale.get(i));
		initJoueurs();
		initLieu();

	}
	
	
	/**
	 * Initialise les cartes du jeu
	 * 
	 * 
	 */
	
	
	/*
	private void initCarte() {

		Materiel materiel1 = new Materiel();
		Menace menace1 = new Menace();
		Sprint sprint1 = new Sprint();
		CameraDeSecurite camSecu1 = new CameraDeSecurite();
		Materiel materiel2 = new Materiel();
		Menace menace2 = new Menace();
		Sprint sprint2 = new Sprint();
		CameraDeSecurite camSecu2 = new CameraDeSecurite();
		Materiel materiel3 = new Materiel();
		Menace menace3 = new Menace();
		Sprint sprint3 = new Sprint();
		CameraDeSecurite camSecu3 = new CameraDeSecurite();
		Cachette cachette1 = new Cachette();
		Cachette cachette2 = new Cachette();
		Cachette cachette3 = new Cachette();
		Arme bate = new Arme();
		Arme grenade = new Arme();
		Arme tronconeuse = new Arme();
    	Arme fusil = new Arme();
		Arme revolver = new Arme();
		Arme hache = new Arme();
		cartes.add(hache);
		cartes.add(revolver);
		cartes.add(tronconeuse);
		cartes.add(fusil);
		cartes.add(grenade);
		cartes.add(bate);
		cartes.add(sprint1);
		cartes.add(sprint2);
		cartes.add(sprint3);
		cartes.add(menace1);
		cartes.add(menace2);
		cartes.add(menace3);
		cartes.add(materiel1);
		cartes.add(materiel2);
		cartes.add(materiel3);
		cartes.add(cachette1);
		cartes.add(cachette2);
		cartes.add(cachette3);
		cartes.add(camSecu1);
		cartes.add(camSecu2);
		cartes.add(camSecu3);

	}
	*/

	/**
	 * Initialise les lieux du jeu
	 * 
	 * 
	 */
	private void initLieu() {

		Lieu parking = new Lieu(4);
		Lieu PCsecu = new Lieu(5);
		Lieu supermarche = new Lieu(6);
		Lieu megatoys = new Lieu(3);
		Lieu cachou = new Lieu(2);
		Lieu toilettes = new Lieu(1);
		if (joueurs.size() < 4)
			cachou.setOuvert(false);
		lieux.add(supermarche);
		lieux.add(PCsecu);
		lieux.add(toilettes);
		lieux.add(megatoys);
		lieux.add(cachou );
		lieux.add(parking);

	}

	
	
	/**
	 * Initialise les joueurs du jeu
	 * 
	 * 
	 */
	private void initJoueurs() {
		if (joueurs.size() < 4)
			for (int i = 0; i < joueurs.size(); i++) {
				joueurs.get(i).getPersonnages().add(new LaBlonde(joueurs.get(i)));
				joueurs.get(i).getPersonnages().add(new LaBrute(joueurs.get(i)));
				joueurs.get(i).getPersonnages().add(new LeTruand(joueurs.get(i)));
				joueurs.get(i).getPersonnages().add(new LaFillette(joueurs.get(i)));
			}
		else
			for (int i = 0; i < joueurs.size(); i++) {
				joueurs.get(i).getPersonnages().add(new LaBlonde(joueurs.get(i)));
				joueurs.get(i).getPersonnages().add(new LaBrute(joueurs.get(i)));
				joueurs.get(i).getPersonnages().add(new LeTruand(joueurs.get(i)));
			}
	}

	
	/**
	 * Applique les résultats de la fouille du camion : attribut la carte piocheur au piocheur, la carte receveur au receveur et sort les 3 cartes du jeu
	 * @param piocheur le piocheur
	 * @param receveur le receveur
	 * @param cartePiocheur la carte que le piocheur a choisi de garder
	 * @param carteReceveur la carte que le piocheur a choisi de donner au receveur
	 * @param carteDefause la carte que le piocheur n'a pas attribuer
	 */
	public void fouilleCamion(Joueur piocheur, Joueur receveur , CarteAction cartePiocheur , CarteAction carteReceveur , CarteAction carteDefause) {
		for (int i = 0 ; i < joueurs.size(); i++) {
			if (joueurs.get(i).equals(piocheur))
				joueurs.get(i).getCartes().add(cartePiocheur);
			if (joueurs.get(i).equals(receveur))
				joueurs.get(i).getCartes().add(carteReceveur);
		}
		cartes.remove(cartePiocheur);
		cartes.remove(carteDefause);
		cartes.remove(carteReceveur);
	}
	
	/**
	 * Applique le nouveau chef des vigiles si un chef est élu
	 * @param nouveauChef le joueurs élu en tant que chef des vigiles
	 */
	public void resultatChefVigile(Joueur nouveauChef) {
		for (int i = 0 ; i < joueurs.size(); i++) {
			if (joueurs.get(i).equals(nouveauChef))
				joueurs.get(i).setChefDesVigiles(true);
			else
				joueurs.get(i).setChefDesVigiles(false);
		}
			
	}
	
	/**
	 * Supprime le chef des vigiles 
	 */
	public void resultatChefVigile() {
		for (int i = 0 ; i < joueurs.size(); i++) {
			joueurs.get(i).setChefDesVigiles(false);
		}
			
	}
	
	
	/**
	 * Lance l'ajout des zombies dans les lieux du jeu
	 * @param listeInt id des lieux où les nouveaux zombie arrivent
	 */
	public void entreZombie(ArrayList<Integer> listeInt) {
		for (int i = 0; i<4 ;i++) {
			lieux.get(listeInt.get(i)).addZombie();
		}	
	}
	
	
	/**
	 * Test si le jeu est fini
	 * @return true si le jeu est fini sinon false
	 */
	public boolean estFini() {
		//TO DO
		return false;
	}
	
	/**
	 * Donne le joueur gagnant
	 * @return le joueur gagnant
	 */
	public Joueur getWinner() {
		//TO DO
		return null;
	}
	
	/**
	 * Donne la liste des joueurs
	 * @return la liste des joueurs
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Donne la liste des lieux
	 * @return la liste des lieux
	 */
	public ArrayList<Lieu> getLieux() {
		return lieux;
	}



	/**
	 * Donne la liste des cartes
	 * @return la liste des cartes
	 */
	public ArrayList<CarteAction> getCartes() {
		return cartes;
	}


	/**
	 * Donne la liste des zombies par lieux
	 * @return la liste des zombies par lieux
	 */
	public ArrayList<Integer> getZombies() {
		ArrayList<Integer> listeNbrZombie = new ArrayList<>();
		for (int i = 0; i< lieux.size(); i++)
			listeNbrZombie.add(lieux.get(i).getNbZombies());	
		return listeNbrZombie;
	}



	/**
	 * Applique la derniere attaque des zombies donc ajoute un zombie au lieu qui a le plus de blondes et un zombie au lieu qui a le plus de personnages
	 * 
	 */
	public void lastAttaqueZombie() {
		// TO DO
		
	}


	/**
	 *Deplace le personnage du joueur au lieu choisi
	 * @param joueur le joueur dont le personnage est déplacé
	 * @param choixPerso le personnage qui est déplacé
	 * @param dest le lieu de destination du personnage
	 */
	public void deplacePerso(Joueur joueur, Personnage choixPerso, Integer dest) {
		for (int i = 0 ; i < joueurs.size() ; i++ ) {
			if (joueurs.get(i).equals(joueur))
				for (int j=0 ; j<joueurs.get(i).getPersonnages().size();j++)
					joueurs.get(i).getPersonnages().get(j).changerDeLieux(lieux.get(dest));
			
		}

		
	}


	/**
	 * Supprime le personnage du jeu 
	 * @param personnage le personnage qui va être supprimé du jeu
	 */
	public void sacrifie(Personnage personnage) {
		personnage.getJoueur().getPersonnages().remove(personnage);
		personnage.getMonLieu().getPersonnage().remove(personnage);
		
		
	}
	
	
	
	

}
