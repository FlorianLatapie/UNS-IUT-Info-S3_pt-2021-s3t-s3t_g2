package jeu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private HashMap<Integer,Personnage> personnages;
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
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
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
	public Map<Integer,Personnage> getPersonnages() {
		return personnages;
	}

	public void setPersonnages(HashMap<Integer, Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * @return cartes
	 */
	public List<CarteAction> getCartes() {
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
	
	/**
	 * Supprime le personnage choisi
	 * @param cle du perso a supr
	 */
	public void removePerso(Integer choix) {
		personnages.remove(choix);
	}

	@Override
	public String toString() {
		return this.nom;
	}
}