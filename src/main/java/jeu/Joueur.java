package jeu;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Class Joueur
 * 
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @version 1.0
 * @since 05/10/2020
 */

public class Joueur {
	private Color couleur;
	private boolean enVie;
	private ArrayList<Personnage> personnages;
	private ArrayList<CarteAction> cartes;
	private boolean chefDesVigiles;

	/**
	 * Constructeur Joueur
	 * 
	 * @param couleur
	 */
	public Joueur(Color couleur) {
		this.couleur = couleur;
		enVie = true;
		personnages = new ArrayList<Personnage>();
		cartes = new ArrayList<CarteAction>();
	}

	/**
	 * 
	 * @return couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * 
	 * @return personnages
	 */
	public ArrayList<Personnage> getPersonnages() {
		return personnages;
	}

	/**
	 * 
	 * @return cartes
	 */
	public ArrayList<CarteAction> getCartes() {
		return cartes;
	}

	/**
	 * 
	 * @return enVie
	 */
	public boolean isEnVie() {
		return enVie;
	}
	
	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	public void setPersonnages(ArrayList<Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * 
	 * @return chefDesVigiles
	 */
	public boolean isChefDesVigiles() {
		return chefDesVigiles;
	}

	public void setChefDesVigiles(boolean chefDesVigiles) {
		this.chefDesVigiles = chefDesVigiles;
	}

	

	
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

}
