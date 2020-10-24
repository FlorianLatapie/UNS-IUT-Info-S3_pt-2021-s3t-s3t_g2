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
	//déclaration des attributs nécessaires pour les getters setters et constructeurs
	private int num;
	private int nbPlaces;
	private int nbZombies;
	public String name;
	private boolean ouvert;
	private ArrayList<Personnage> personnage;

	// Getter Setter
	
	/**
	 * @return le numéro de partie
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @return le nombre de places disponibles
	 */
	public int getNbPlaces() {
		return nbPlaces;
	}

	/**
	 * @return le nombre de zombies présents dans un lieu
	 */
	public int getNbZombies() {
		return nbZombies;
	}

	/**
	 * @return si le lieu est ouvert ou non
	 */
	public boolean isOuvert() {
		return ouvert;
	}

	/**
	 * @param open attribut qui définit si le lieu est ouvert ou non
	 */
	public void setOuvert(boolean open) {
		this.ouvert = open;
	}

	/**
	 * @return la liste des personnages
	 */
	public List<Personnage> getPersonnage() {
		return personnage;
	}

	/**
	 * @return la liste des personnages "la blonde"
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
	 * @param personnage la liste des personnages
	 */
	public void setPersonnage(ArrayList<Personnage> personnage) {
		if (personnage.size() > this.nbPlaces) {
			/*si la taille du personnage est trop grande par rapport au nombre de places alors
			il ne peut pas être créé !
			*/
			throw new RuntimeException(this.toString() + " est full!");
		}
		this.personnage = personnage;
	}

	/**
	 * @param p le personnage choisi
	 */
	public void addPersonnage(Personnage p) {
		//ajoute un personnage dans un lieu
		if (this.isFull()) {
			//si le lieu est plein on ne peut pas ajouter de personnage
			throw new RuntimeException(this.toString() + " est full!");
		}
		this.personnage.add(p);
	}

	// Constructeurs
	
	/**
	 * @param num le numéro de partie
	 */
	public Lieu(int num) {
		HashMap<Integer, String> listeLieu = new HashMap<Integer, String>();
		//declaration des différents lieus possibles
		listeLieu.put(1, "Toilettes");
		listeLieu.put(2, "Cachou");
		listeLieu.put(3, "Megatoys");
		listeLieu.put(4, "Parking");
		listeLieu.put(5, "PC de sécurité");
		listeLieu.put(6, "Supermarché");

		this.name = listeLieu.get(num);
		//donne le nombre de places disponibles en fonction du numero de partie
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
		//affiche les différents personnages qui se trouvent sur un lieu
		ArrayList<Joueur> n = new ArrayList<Joueur>();
		for (int i = 0; i < personnage.size(); i++) {
			if (!(n.contains(personnage.get(i).getJoueur()))) {
				//si le lieu ne contient deja pas le même personnage
				n.add(personnage.get(i).getJoueur());
			}
		}
		return n;
	}

	//le lieu doit contenir des personnages pour être attaqué
	public boolean estAttaquable() {
		if (this.ouvert) {//le lieu doit etre ouvert pour être attaquable
			if (this.num == 4 && this.nbZombies > 0 && this.personnage.size() > 0) { //si parking
				return true;
			}
			if (this.num == 6 && this.nbZombies >= 4 && this.personnage.size() > 0) { //si supermarché
				return true;
			}
			int force = 0;
			for (int a = 0; a < this.personnage.size(); a++) {
				if (personnage.get(a).getType() == TypePersonnage.BRUTE) {
					//si le personnage est une brute alors sa force augmente de 2
					force += 2;
				} else { //sinon de 1
					force += 1;
				}
			}
			if (force > 0 && force <= this.nbZombies) {
				/*la force du joueur doit etre suffisante face aux zombies pour que le lieu 
				soit attaquable
				*/
				return true;
			}
			return false;
		}
		return false;
		
	}

	public void addZombie() {
		//ajoute un zombie dans le lieu
		this.nbZombies += 1;
	}

	public void addZombie(int i) {
		//ajoute un ou plusieurs zombies dans le lieu
		this.nbZombies += i;
	}

	public String toString() {
		return this.name;
	}

	public boolean isFull() {
		//indique que le lieu est plein
		if (personnage != null) {
			if (this.personnage.size() == this.nbPlaces) {
				//si les personnages remplissent toutes les places c'est plein !
				return true;
			}
		}
		return false;
	}

	/**
	 * @param nbZombies nombre de zombies
	 */
	public void setNbZombies(int nbZombies) {
		this.nbZombies = nbZombies;
	}
}
