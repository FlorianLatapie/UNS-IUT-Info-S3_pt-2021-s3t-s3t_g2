package jeu;

/**
 * The Class arme.
 * @author Alex, Remy, Florian
 * @version 0
 * @since 04/10/2020
 * @category carteAction
 */
public class Arme extends CarteAction {
	private String nom;
	private int nbZTués;
	
	
	
	
	public Arme(String nom, int nbZTués) {
		super("Arme");
		this.nom = nom;
		this.nbZTués = nbZTués;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getNbZTués() {
		return nbZTués;
	}
	public void setNbZTués(int nbZTués) {
		this.nbZTués = nbZTués;
	}
	
	
	

}

