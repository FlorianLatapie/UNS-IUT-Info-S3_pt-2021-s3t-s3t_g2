package jeu;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Class Joueur
 * 
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @since 05/10/2020
 */

public class Joueur {
	private Color couleur;
	private boolean enVie;
	private ArrayList<Personnage> personnages;
	private ArrayList<CarteAction> cartes;
	private boolean chefDesVigiles;
	private String nom;

	/**
	 * Constructeur Joueur
	 * 
	 * @param couleur
	 * @param nom
	 */
	public Joueur(Color couleur, String nom) {
		this.couleur = couleur;
		enVie = true;
		personnages = new ArrayList<Personnage>();
		cartes = new ArrayList<CarteAction>();
		this.nom = nom;
	}

	/**
	 * @return couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * @return personnages
	 */
	public ArrayList<Personnage> getPersonnages() {
		return personnages;
	}

	public void setPersonnages(ArrayList<Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * @return cartes
	 */
	public ArrayList<CarteAction> getCartes() {
		return cartes;
	}

	/**
	 * @return enVie
	 */
	public boolean isEnVie() {
		return enVie;
	}

	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	/**
	 * @return chefDesVigiles
	 */
	public boolean isChefDesVigiles() {
		return chefDesVigiles;
	}

	public void setChefDesVigiles(boolean chefDesVigiles) {
		this.chefDesVigiles = chefDesVigiles;
	}
	
	/**
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return this.nom;
	}
}