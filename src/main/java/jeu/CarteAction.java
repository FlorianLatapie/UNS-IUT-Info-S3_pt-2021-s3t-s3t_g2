package jeu;

/**
 * The Class carteAction.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 */
public abstract class CarteAction {
	private boolean enJeu;
	private String nomCarte;

	public CarteAction(boolean enJeu, String nomCarte) {
		this.enJeu = enJeu;
		this.nomCarte = nomCarte;
	}
	
	public CarteAction(String nomCarte) {
		this.nomCarte = nomCarte;
	}

	public String getNomCarte() {
		return nomCarte;
	}

	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}

	public boolean estEnJeu() {
		return enJeu;
	}

	public void setEnJeu(boolean enJeu) {
		this.enJeu = enJeu;
	}
}
