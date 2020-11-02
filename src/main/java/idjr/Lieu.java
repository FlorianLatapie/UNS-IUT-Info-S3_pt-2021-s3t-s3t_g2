package idjr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Definit un lieu</h1>
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

	/**
	 * Initialise les diffÃ©rents lieux possibles et donne le nombre de places
	 * disponibles en fonction du numero de partie
	 *
	 * @param num le numÃ©ro de partie
	 */
	public Lieu(int num) {
		HashMap<Integer, String> listeLieu = new HashMap<Integer, String>();
		listeLieu.put(1, "Toilettes");
		listeLieu.put(2, "Cachou");
		listeLieu.put(3, "Megatoys");
		listeLieu.put(4, "Parking");
		listeLieu.put(5, "PC de sÃ©curitÃ©");
		listeLieu.put(6, "SupermarchÃ©");

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
	 * Ajoute un personnage dans un lieu
	 *
	 * @param p le personnage choisi
	 */
	public void addPersonnage(Personnage p) {
		if (this.isFull()) {
			throw new RuntimeException(name + " est full!");
		}
		this.personnage.add(p);
	}

	/**
	 * ajoute un zombie dans le lieu
	 */
	public void addZombie() {
		this.nbZombies += 1;
	}


	/**
	 * indique si le lieu est plein ou non
	 *
	 * @return true ou false
	 */
	public boolean isFull() {
		System.out.println(this.toString());
		if (personnage != null) {
			if (this.personnage.size() == this.nbPlaces) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return le numÃ©ro de partie
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @return si le lieu est ouvert ou non
	 */
	public boolean isOuvert() {
		return ouvert;
	}

	/**
	 * @param open attribut qui dÃ©finit si le lieu est ouvert ou non
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


	public int getNbPlaces() {
		return nbPlaces;
	}

	public String toString() {
		return name;
	}
}
