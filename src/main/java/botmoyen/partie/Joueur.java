package botmoyen.partie;

import reseau.type.CarteType;
import reseau.type.Couleur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Les infos d'un joueur</h1>
 *
 * @author Alex Devauchelle
 * @version 0.1
 * @since 05/10/2020
 */
public class Joueur   {

	private Couleur couleur;
	private boolean enVie;
	private HashMap<Integer, Personnage> personnages;
	private ArrayList<CarteType> cartes;
	private boolean chefDesVigiles;
	

	public Joueur(Couleur c) {	
		couleur =c;
		enVie = true;
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
	}
	
	

	
	public Joueur(Couleur couleur, boolean enVie, HashMap<Integer, Personnage> personnages, boolean chefDesVigiles) {
		super();
		this.couleur = couleur;
		this.enVie = enVie;
		this.personnages = personnages;
		this.chefDesVigiles = chefDesVigiles;
		this.cartes= new ArrayList<CarteType>();
	}




	/**
	 * @return la couleur
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur du joueur
	 *
	 * @param couleur la couleur cible
	 */
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	/**
	 * @return la liste de personnages
	 */
	public Map<Integer, Personnage> getPersonnages() {
		return personnages;
	}

	/**
	 * Modifie la liste de personnages
	 *
	 * @param personnages le personnage cible
	 */
	public void setPersonnages(HashMap<Integer, Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * @return les cartes actions du joueur
	 */
	public List<CarteType> getCartes() {
		return cartes;
	}

	/**
	 * @return si le joueur est en vie
	 */
	public boolean isEnVie() {
		return enVie;
	}

	/**
	 * @param enVie definit si le joueur est en vie
	 */
	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	/**
	 * @return si le joueur courant est le chef des vigile
	 */
	public boolean isChefDesVigiles() {
		return chefDesVigiles;
	}

	/**
	 * @param chefDesVigiles définit si le joueur courant est le chef des vigiles
	 */
	public void setChefDesVigiles(boolean chefDesVigiles) {
		this.chefDesVigiles = chefDesVigiles;
	}


	@Override
	public String toString() {
		return "Joueur [couleur=" + couleur + ", enVie=" + enVie + ", personnages=" + personnages + ", cartes=" + cartes
				+ "]";
	}


	public Joueur copyOf() {
		HashMap<Integer, Personnage> personnagesCopy = new HashMap<Integer, Personnage>();
		for (Personnage p : personnages.values())
			personnagesCopy.put(p.getPoint(), p.copyOf());
		return new Joueur( couleur,  enVie, personnagesCopy,  chefDesVigiles);
	}
	
	


}