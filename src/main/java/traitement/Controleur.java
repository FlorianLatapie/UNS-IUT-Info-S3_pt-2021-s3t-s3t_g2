package traitement;

import java.util.ArrayList;
import java.util.List;

import reseau.type.Couleur;

public abstract class Controleur {
	private String nom;
	private Couleur couleur;
	private boolean envie;
	private String joueurId;
	
	private List<Integer> lieuOuvert;
	private List<Integer> pionAPos;
	
	private boolean estFini;
	
	public Controleur() {
		this.lieuOuvert = new ArrayList<>();
		this.envie = true;
		this.estFini = false;
	}
	
	public List<Integer> getPionAPos() {
		return pionAPos;
	}

	public void setPionAPos(List<Integer> pionAPos) {
		this.pionAPos = pionAPos;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Integer> getLieuOuvert() {
		return lieuOuvert;
	}

	public void setLieuOuvert(List<Integer> lieuOuvert) {
		this.lieuOuvert = lieuOuvert;
	}

	public boolean getEnvie() {
		return envie;
	}

	public void setEnvie(Boolean envie) {
		this.envie = envie;
	}

	public void setEstFini(boolean estFini) {
		this.estFini = estFini;
	}

	public boolean isEstFini() {
		return estFini;
	}

	public String getJoueurId() {
		return joueurId;
	}

	public void setJoueurId(String joueurId) {
		this.joueurId = joueurId;
	}
}
