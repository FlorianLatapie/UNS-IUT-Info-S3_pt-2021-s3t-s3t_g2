package ihm;

import controleur.ControleurJeu;

public class Core {
	private ControleurJeu cj = null;
	private int nbJoueur = 5; 
	private int nbBot = 4; 
	private String nomPartie = "partieParDéfaut";

	public String getNomPartie() {
		return nomPartie;
	}

	public void setNomPartie(String nomPartie) {
		this.nomPartie = nomPartie;
	}

	public int getNbJoueur() {
		return nbJoueur;
	}

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public int getNbBot() {
		return nbBot;
	}

	public void setNbBot(int nbBot) {
		this.nbBot = nbBot;
	}

	public ControleurJeu getCj() {
		return cj;
	}

	public void setCj(ControleurJeu cj) {
		this.cj = cj;
	} 
	
	public int getNbJoueurReel() {
		return nbJoueur-nbBot;
	}
	
}
