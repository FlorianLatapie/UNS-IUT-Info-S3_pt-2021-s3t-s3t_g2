package jeu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class lieu.
 * 
 * @author Alex
 * @version 0
 * @since 04/10/2020
 */
public class Lieu {
	private int num;
	private int nbPlaces;
	private int nbZombies;
	public String name;
	private boolean ouvert;
	private ArrayList<Personnage> personnage;

	// Getter Setter
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

	public ArrayList<Personnage> getBlonde() {
		ArrayList retour = new ArrayList();
		for (int i = 0; i < this.personnage.size(); i++) {
			if (this.personnage.get(i) instanceof LaBlonde) {
				retour.add(this.personnage.get(i));
			}
		}
		return retour;
	}

	public void setPersonnage(ArrayList<Personnage> personnage) {
		this.personnage = personnage;
	}

	// Constructeurs
	public Lieu(int num) {
		HashMap<Integer, String> listeLieu = new HashMap<Integer, String>();
		listeLieu.put(1, "Toillets");
		listeLieu.put(2, "Cachou");
		listeLieu.put(3, "Megatoys");
		listeLieu.put(4, "Parking");
		listeLieu.put(5, "PC de sécurité");
		listeLieu.put(6, "Supermarché");

		this.name = listeLieu.get(num);

		this.num = num;
		if (this.num == 1 || this.num == 5) {
			this.nbPlaces = 3;
		}
		if (this.num == 2 || this.num == 3) {
			this.nbPlaces = 4;
		}
		if (this.num == 4) {
			this.nbPlaces = 24;
		}
		if (this.num == 6) {
			this.nbPlaces = 6;
		}
		this.ouvert = true;
	}

	// methodes
	public ArrayList<Joueur> afficheJoueurSurLieu() {
		ArrayList<Joueur> n = new ArrayList<Joueur>();
		for (int i = 0; i < personnage.size(); i++) {
			if (!(n.contains(personnage.get(i).getJoueur()))) {
				n.add(personnage.get(i).getJoueur());
			}
		}
		return n;
	}

	public boolean estAttaquable() {
		int force = 0;
		for (int a = 0; a < this.personnage.size(); a++) {
			if (personnage.get(a).getType() == TypePersonnage.BRUTE) {
				force += 2;
			} else {
				force += 1;
			}
		}
		if (force > this.nbZombies) {
			return false;
		}
		return true;
	}

	public void addZombie() {
		this.nbZombies += 1;
	}
	
	public void addZombie(int i) {
		this.nbZombies += i;
	}

	public String toString() {
		return this.name;
	}

	public boolean isFull() {
		if (this.personnage != null) {
			if (this.personnage.size() < this.nbPlaces) {
				return true;
			}
		}
		return false;
	}
}
