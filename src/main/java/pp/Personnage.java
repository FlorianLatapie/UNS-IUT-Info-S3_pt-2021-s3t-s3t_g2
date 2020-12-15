package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>Le personnage</h1>
 *
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 1
 * @since 04/10/2020
 */
public abstract class Personnage {

	/** Lieu actuel où se trouve le personnage. */
	protected Lieu monLieu;

	/** Joueur possédant le personnage. */
	private Joueur joueur;

	/** Type de personnage du personnage. */
	private TypePersonnage type;

	/** Nombre de points que vaut le personnage. */
	protected int point;

	/** Nombre de zombies que le personnage peut retenir. */
	protected int nbrZretenu = 1;

	/** Booléen indiquant si le personnage est en vie ou non. */
	protected boolean estVivant;

	/** Booléen indiquant si le personnage est caché ou non. */
	protected boolean estCache;

	/**
	 * Créer un nouveau personnage qui prendra part à une partie.
	 * 
	 * @param joueur Le joueur possédant le personnage
	 * @param type   Le type du personnage
	 */
	public Personnage(Joueur joueur, TypePersonnage type) {
		this.type = type;
		this.joueur = joueur;
	}

	/**
	 * Récupère le statut caché ou non du personnage.
	 * 
	 * @return estCache Si le personnage est caché ou non
	 */
	public boolean isEstCache() {
		return estCache;
	}

	/**
	 * Modifie le statut caché du personnage.
	 *
	 * @param estCache Définit si le personnage est caché ou non
	 */
	public void setEstCache(boolean estCache) {
		this.estCache = estCache;
	}

	/**
	 * Récupère le nombre de point que vaut le personnage.
	 * 
	 * @return point Le nombre de point que vaut le personnage
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * Récupère le nombre de zombie retenus par le personnage.
	 * 
	 * @return nbrZretenu Le nombre de zombies que le personnage peut retenir
	 */
	public int getNbrZretenu() {
		return nbrZretenu;
	}

	/**
	 * Définit le nouveau lieu où le personnage vient d'arriver.
	 *
	 * @param newLieu Nouveau lieu où se trouve le personnage
	 */
	public void changerDeLieux(Lieu newLieu) {
		monLieu = newLieu;
	}

	/**
	 * Récupère le lieu où se trouve le personnage.
	 * 
	 * @return monLieu Le lieu dans lequel se trouve actuellement le personnage
	 */
	public Lieu getMonLieu() {
		return monLieu;
	}

	/**
	 * Récupère le joueur qui possède le personnage.
	 * 
	 * @return joueur Le joueur possédant le personnage
	 */
	public Joueur getJoueur() {
		return this.joueur;
	}

	/**
	 * Récupère le type de personnage du personnage.
	 * 
	 * @return type Le type du personnage
	 */
	public TypePersonnage getType() {
		return type;
	}

	/**
	 * Gère l'affichage du personnage.
	 */
	public abstract String toString();
}
