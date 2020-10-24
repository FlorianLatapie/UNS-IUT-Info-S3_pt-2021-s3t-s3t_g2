package jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	public int getNbPlaces() {
		return nbPlaces;
	}

	public int getNbZombies() {
		return nbZombies;
	}

	public boolean isOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean open) {
		this.ouvert = open;
	}

	public List<Personnage> getPersonnage() {
		return personnage;
	}

	public List<Personnage> getBlonde() {
		ArrayList<Personnage> retour = new ArrayList<Personnage>();
		for (int i = 0; i < this.personnage.size(); i++) {
			if (this.personnage.get(i) instanceof LaBlonde) {
				retour.add(this.personnage.get(i));
			}
		}
		return retour;
	}

	public void setPersonnage(ArrayList<Personnage> personnage) {
		if (personnage.size() > this.nbPlaces) {
			throw new RuntimeException(this.toString() + " est full!");
		}
		this.personnage = personnage;
	}

	public void addPersonnage(Personnage p) {
		if (this.isFull()) {
			throw new RuntimeException(this.toString() + " est full!");
		}
		this.personnage.add(p);
	}

	// Constructeurs
	public Lieu(int num) {
		HashMap<Integer, String> listeLieu = new HashMap<Integer, String>();
		listeLieu.put(1, "Toilettes");
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
		personnage = new ArrayList<>();
	}

	// Méthodes
	public List<Joueur> afficheJoueurSurLieu() {
		ArrayList<Joueur> n = new ArrayList<Joueur>();
		for (int i = 0; i < personnage.size(); i++) {
			if (!(n.contains(personnage.get(i).getJoueur()))) {
				n.add(personnage.get(i).getJoueur());
			}
		}
		return n;
	}

	//le lieu doit contenir des prosnnages pour être attaqué
	public boolean estAttaquable() {
		if (this.ouvert) {
			if (this.num == 4 && this.nbZombies > 0 && this.personnage.size() > 0) { //si parking
				return true;
			}
			if (this.num == 6 && this.nbZombies >= 4 && this.personnage.size() > 0) { //si supermarché
				return true;
			}
			int force = 0;
			for (int a = 0; a < this.personnage.size(); a++) {
				if (personnage.get(a).getType() == TypePersonnage.BRUTE) {
					force += 2;
				} else {
					force += 1;
				}
			}
			if (this.nbZombies > 0 && force <= this.nbZombies) {
				return true;
			}
			return false;
		}
		return false;
		
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
		if (personnage != null) {
			if (this.personnage.size() == this.nbPlaces) {
				return true;
			}
		}
		return false;
	}

	public void setNbZombies(int nbZombies) {
		this.nbZombies = nbZombies;
	}
}
