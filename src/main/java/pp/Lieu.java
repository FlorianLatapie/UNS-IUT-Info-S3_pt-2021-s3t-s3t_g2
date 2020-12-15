package pp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe lieu</h1>. A pour rôle de définir un lieu.
 *
 * @author Alex
 * @version 1
 * @since 04/10/2020
 */
public class Lieu {

	/** Numéro de la partie où se trouve le lieu. */
	private int num;

	/** Entier correspondant au nombre de places d'un lieu. */
	private int nbPlaces;

	/** Entier correspondant au nombre de zombie sur un lieu. */
	private int nbZombies;

	/** Chaine de caractère correspondant au nom d'un lieu. */
	public String name;

	/** Booléen indiquant si un lieu est ouvert ou non. */
	private boolean ouvert;

	/** Collection des personnages présents sur un lieu. */
	private ArrayList<Personnage> personnage;

	/**
	 * Initialise les différents lieux possibles et donne le nombre de places
	 * disponibles pour chaque lieu en fonction du numéro de partie.
	 *
	 * @param num Le numéro de partie
	 */
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

	/**
	 * Récupère les instances de "blonde" sur un lieu.
	 *
	 * @return La liste des personnages "la blonde"
	 */
	public List<Personnage> getBlonde() {
		ArrayList<Personnage> retour = new ArrayList<Personnage>();
		for (int i = 0; i < this.personnage.size(); i++) {
			if (this.personnage.get(i) instanceof LaBlonde) {
				retour.add(this.personnage.get(i));
			}
		}
		return retour;
	}

	/**
	 * Ajoute un personnage dans un lieu.
	 *
	 * @param p Le personnage choisi
	 */
	public void addPersonnage(Personnage p) {
		if (this.isFull()) {
			throw new RuntimeException(this.toString() + " est full!");
		}
		this.personnage.add(p);
	}

	/**
	 * Indique si un lieu est attaquable ou non Le lieu doit contenir des
	 * personnages pour être attaqué.
	 *
	 * @return Si le lieu est attaquable
	 */
	public boolean estAttaquable() {
		if (this.ouvert) {
			if (this.num == 4 && this.nbZombies > 0 && this.personnage.size() > 0) {
				return true;
			}
			if (this.num == 6 && this.nbZombies >= 4 && this.personnage.size() > 0) {
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
			if (force > 0 && force <= this.nbZombies) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Indique si un lieu est attaquable ou non Le lieu doit contenir des
	 * personnages pour être attaqué.
	 * 
	 * @param nbCarteMateriel
	 * @return Si le lieu est attaquable
	 */
	public boolean estAttaquable(int nbCarteMateriel) {
		List<Personnage> personnagePresent = new ArrayList<>();
		for (Personnage personnage : this.personnage) {
			if (!personnage.isEstCache()) {
				personnagePresent.add(personnage);
			}
		}
		if (this.ouvert) {
			if (this.num == 4 && this.nbZombies > 0 && personnagePresent.size() > 0) {
				return true;
			}
			if (this.num == 6 && this.nbZombies >= 4 && personnagePresent.size() > 0) {
				return true;
			}
			if (personnagePresent.size() == 0) {
				return false;
			}
			int force = 0;
			for (int a = 0; a < personnage.size(); a++) {
				if (personnage.get(a).getType() == TypePersonnage.BRUTE) {
					force += 2;
				} else {
					force += 1;
				}
			}
			if (force > 0 && force + nbCarteMateriel <= this.nbZombies) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Calcule la force totale de tous les personnages présent sur un lieu.
	 *
	 * @return force
	 */
	public int getForce() {
		int force = 0;
		for (int a = 0; a < this.personnage.size(); a++) {
			if (personnage.get(a).getType() == TypePersonnage.BRUTE) {
				force += 2;
			} else {
				force += 1;
			}
		}

		return force;
	}

	/**
	 * Ajoute un zombie dans le lieu.
	 */
	public void addZombie() {
		this.nbZombies += 1;
	}

	/**
	 * Gère l'affichage du nom d'un lieu.
	 *
	 * @return name
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Indique si le lieu est plein ou non.
	 *
	 * @return Si le lieu est plein
	 */
	public boolean isFull() {
		if (personnage != null) {
			if (this.personnage.size() == this.nbPlaces) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Modifie le nombre de zombies présents sur un lieu.
	 *
	 * @param nbZombies the new nb zombies
	 */
	public void setNbZombies(int nbZombies) {
		if (nbZombies < 0)
			this.nbZombies = 0;
		else
			this.nbZombies = nbZombies;
	}

	/**
	 * Récupère le numéro de partie associé à un lieu.
	 *
	 * @return num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Récupère le nombre de places disponibles sur un lieu.
	 *
	 * @return nbPlaces
	 */
	public int getNbPlaces() {
		return nbPlaces;
	}

	/**
	 * Récupère le nombre de zombies présents sur un lieu.
	 *
	 * @return nbZombies
	 */
	public int getNbZombies() {
		return nbZombies;
	}

	/**
	 * Récupère l'état ouvert ou fermé d'un lieu.
	 *
	 * @return ouvert
	 */
	public boolean isOuvert() {
		return ouvert;
	}

	/**
	 * Modifie l'état ouvert ou fermé.
	 *
	 * @param open Attribut qui définit si le lieu est ouvert ou non
	 */
	public void setOuvert(boolean open) {
		this.ouvert = open;
	}

	/**
	 * Récupère la liste des personnages sur un lieu.
	 *
	 * @return personnage
	 */
	public List<Personnage> getPersonnage() {
		return personnage;
	}
}
