package jeu;

import java.util.ArrayList;

/**
 * The Class lieu.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 */
public class Lieu {
	private int num;
	private int nbPlaces;
	private int nbZombies;
	private boolean ouvert;
	private ArrayList<Personnage> personnage;
	
	//Getter Setter
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNbPlaces() {
		return nbPlaces;
	}
	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}
	public int getNbZombies() {
		return nbZombies;
	}
	public void setNbZombies(int nbZombies) {
		this.nbZombies = nbZombies;
	}
	public boolean isOuvert() {
		return ouvert;
	}
	public void setOuvert(boolean open) {
		this.ouvert = open;
	}
	public ArrayList<Personnage> getPersonnage() {
		return personnage;
	}
	public void setPersonnage(ArrayList<Personnage> personnage) {
		this.personnage = personnage;
	}
	
	//Constructeurs
	public Lieu(int num) {
		this.num = num;
		if(this.num == 1 || this.num==5) {
			this.nbPlaces=3;
		}
		if(this.num == 2 || this.num == 3) {
			this.nbPlaces=4;
		}
		if(this.num == 4) {
			this.nbPlaces=24;
		}
		if(this.num == 6) {
			this.nbPlaces=6;
		}
		this.ouvert=true;
	}
	
	//methodes
	public ArrayList<Joueur> afficheJoueurSurLieu(){
		ArrayList<Joueur> n = new ArrayList<Joueur>();
		for(int i=0; i<personnage.size();i++) {
			if(!(n.contains(personnage.get(i).getJoueur()))){
				n.add(personnage.get(i).getJoueur());
			}
		}
		return n;
	}
		
	
	public boolean zombieEntre() {
		int force = 0;
		for(int a=0; a<this.personnage.size(); a++) {
			if(personnage.get(a).getType() == TypePersonnage.BRUTE) {
				force += 2;
			}
			else {
				force += 1;
			}
		}
		if (force > this.nbZombies){
			return false;
		}
		return true;
	}

	public void addZombie(){
		this.nbZombies += 1;
	}

}
