package jeu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>La classe joueur
 * <h1>
 * 
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @version 0.1
 * @since 05/10/2020
 */

public class Joueur {
	private Color couleur;
	private boolean enVie;
	private HashMap<Integer, Personnage> personnages;
	private ArrayList<CarteAction> cartes;
	private boolean chefDesVigiles;
	private String nom;

	/**
	 * Constructeur du joueur
	 * 
	 * @param couleur la couleur du joueur
	 * @param nom     le nom du joueur
	 */
	public Joueur(Color couleur, String nom) {
		this.couleur = couleur;
		enVie = true;
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
		this.nom = nom;
	}

	/**
	 * Donne la couleur du joueur
	 * 
	 * @return la couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur du joueur
	 * 
	 * @param couleur
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * 
	 * @return la liste de personnages
	 */
	public Map<Integer, Personnage> getPersonnages() {
		return personnages;
	}

	/**
	 * Modifie la liste de personnages
	 * 
	 * @param personnages
	 */
	public void setPersonnages(HashMap<Integer, Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * Donne la liste de cartes
	 * 
	 * @return cartes
	 */
	public List<CarteAction> getCartes() {
		return cartes;
	}

	/**
	 * Donne le statut en vie
	 * 
	 * @return enVie
	 */
	public boolean isEnVie() {
		return enVie;
	}

	/**
	 * Modifie le statut en vie
	 * 
	 * @param enVie
	 */
	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	/**
	 * Donne le statut chef des vigiles
	 * 
	 * @return chefDesVigiles
	 */
	public boolean isChefDesVigiles() {
		return chefDesVigiles;
	}

	/**
	 * Modifie le statut chef des vigiles
	 * 
	 * @param chefDesVigiles
	 */
	public void setChefDesVigiles(boolean chefDesVigiles) {
		this.chefDesVigiles = chefDesVigiles;
	}

	/**
	 * Donne le nom
	 * 
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Supprime le personnage choisi
	 * 
	 * @param cle du perso a supr
	 */
	public void removePerso(Integer choix) {
		personnages.remove(choix);
	}

	/**
	 * Permet l'affichage d'un joueur par son nom
	 * 
	 * @return le nom du joueur
	 */
	@Override
	public String toString() {
		return this.nom;
	}
}