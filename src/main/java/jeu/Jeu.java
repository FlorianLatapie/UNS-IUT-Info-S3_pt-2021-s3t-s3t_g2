package jeu;

import java.util.ArrayList;

/**
 * The Class jeu.
 * 
 * @author Alex
 * @version 0
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
		for (int i = 0; i < listeJoueursInitiale.size() - 1; i++)
			joueurs.add(listeJoueursInitiale.get(i));
		initJoueurs();
		initLieu();

	}
	
	
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
	
	
	public void resultatChefVigile(Joueur nouveauChef) {
		for (int i = 0 ; i < joueurs.size(); i++) {
			if (joueurs.get(i).equals(nouveauChef))
				joueurs.get(i).setChefDesVigiles(true);
			else
				joueurs.get(i).setChefDesVigiles(false);
		}
			
	}
	
	public void resultatChefVigile() {
		for (int i = 0 ; i < joueurs.size(); i++) {
			joueurs.get(i).setChefDesVigiles(false);
		}
			
	}
	
	
	public void entreZombie(ArrayList<Integer> listeInt) {
		for (int i = 0; i<4 ;i++) {
			lieux.get(listeInt.get(i)).setNbZombies(nbZombies);;
		}	
	}
	
	public void placementDesPersonnages(ArrayList<Integer> lieuDest, ArrayList<Integer> lieuDep) {
		for (int i = 0; i < joueurs.size(); i++)
			if (joueurs.get(i).isEnVie()) 
				joueurs.get(i).setPersonnageAtLieu(joueurs.get(i).getPersonnageAtLieu(lieuDep.get(i)), lieuDest.get(i));
	}
	
	public boolean estFini() {
		//TO DO
		return false;
	}
	
	public Joueur getWinner() {
		//TO DO
		return null;
	}
	
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public ArrayList<Lieu> getLieux() {
		return lieux;
	}



	public ArrayList<CarteAction> getCartes() {
		return cartes;
	}



	public ArrayList<Integer> getZombies() {
		ArrayList<Integer> listeNbrZombie = new ArrayList<>();
		for (int i = 0; i< lieux.size(); i++)
			listeNbrZombie.add(lieux.get(i).getNbZombies());	
		return listeNbrZombie;
	}



	public void lastAttaqueZombie() {
		// TO DO
		
	}
	
	
	
	

}
