package idjr;

/**
 * <h1>Le personnage</h1>
 *
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 0.1
 * @since 04/10/2020
 */
public class Personnage {
	protected Lieu monLieu;// position actuel du personnage
	private Joueur joueur;


	/**
	 * @param joueur le joueur possedant le personnage
	 * @param type   le type du personnage
	 */
	public Personnage(Joueur joueur) {
		this.joueur = joueur;
	}

	
	/**
	 * Definis un nouveau lieu pour le personnage
	 *
	 * @param newLieu nouveau lieu
	 */
	public void changerDeLieux(Lieu newLieu) {
		monLieu = newLieu;
	}

	/**
	 * @return le lieu dans lequel se trouve actuellement le joueur
	 */
	public Lieu getMonLieu() {
		return monLieu;
	}

	/**
	 * @return Renvoie le joueur possédant le personnage
	 */
	public Joueur getJoueur() {
		return this.joueur;
	}
}
