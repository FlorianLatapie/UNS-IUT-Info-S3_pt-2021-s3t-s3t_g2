package idjr;

import reseau.type.TypeJoueur;

public class Idjr {
	private String numPartie;
	private String joueurId;
	private String couleur;
	private TypeJoueur typeJoueur;

	public Idjr() {
		this.typeJoueur = TypeJoueur.JOUEUR;
	}

	public TypeJoueur getTypeJoueur() {
		return typeJoueur;
	}

	public String getCouleur() {
		return couleur;
	}

	public String getJoueurId() {
		return joueurId;
	}

	public String getNumPartie() {
		return numPartie;
	}

}
