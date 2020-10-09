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
		Personnage br = new LaBrute(this);
		Personnage tr = new LeTruand(this);
		Personnage bl = new LaBlonde(this);
		Personnage fi = new LaFillette(this);
		personnages.add(br);
		personnages.add(tr);
		personnages.add(bl);
		personnages.add(fi);
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
	 * @return enVie
	 */
	public boolean estEnVie() {
		return enVie;
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
	 * @return chefDesVigiles
	 */
	public boolean estChefDesVigiles() {
		return chefDesVigiles;
	}

	/**
	 * 
	 * @param personnage
	 */
	public void supprimerPersonnage(Personnage personnage) {
		this.personnages.remove(personnage);
	}

	/**
	 * 
	 * @param carte
	 */
	public void ajouterCarte(CarteAction carte) {
		this.cartes.add(carte);
	}
}
