package pp.ihm;

import pp.controleur.ControleurJeu;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.event.Initializer;
import pp.ihm.langues.International;
import pp.ihm.langues.Langues;

public class Core {
	private ControleurJeu cj = null;
	private int nbJoueur = 5;
	private int nbBot = 4;
	private final SauvegarderOptions sauvegarderOptions;

	public Core() {
		sauvegarderOptions = new SauvegarderOptions();
	}
	
	

	private ApplicationPane pauseDepuis = ApplicationPane.ACCUEIL;
	private ApplicationPane reglesDepuis = ApplicationPane.ACCUEIL;

	public ApplicationPane getReglesDepuis() {
		return reglesDepuis;
	}

	public void setReglesDepuis(ApplicationPane reglesDepuis) {
		this.reglesDepuis = reglesDepuis;
	}

	public ApplicationPane getPauseDepuis() {
		return pauseDepuis;
	}

	public void setPauseDepuis(ApplicationPane pauseDepuis) {
		this.pauseDepuis = pauseDepuis;
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
		return nbJoueur - nbBot;
	}

	public SauvegarderOptions getSauvegarderOptions() {
		return sauvegarderOptions;
	}
	
	public void changerLangue(Langues l) {
		International.changerLangue(l);
		getSauvegarderOptions().setLangues(l);
	}
}
