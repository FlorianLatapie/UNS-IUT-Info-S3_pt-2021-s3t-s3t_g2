package pp.ihm;

import pp.controleur.ControleurJeu;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.langues.International;
import pp.ihm.langues.Langues;

/**
 * 
 * @author florian
 * @author Sebastien
 *
 */

public class Core {
	//auteur florian
	private ControleurJeu cj = null;
	private int nbJoueur = 5;
	private int nbBot = 4;
	private final SauvegarderOptions sauvegarderOptions;

	/**
	 * @author Sebastien
	 * constructeur par défaut 
	 */
	public Core() {
		sauvegarderOptions = new SauvegarderOptions();
	}
	
	//auteur florian
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

	//auteur sebastien
	public SauvegarderOptions getSauvegarderOptions() {
		return sauvegarderOptions;
	}
	
	/**
	 * @author Sebastien
	 * applique la langue entrée en paramètre à l'interface graphique et sauvegarde l'information dans les options 
	 * @param langue langue que l'on veut utiliser pour linterface graphique 
	 */
	public void changerLangue(Langues langue) {
		International.changerLangue(langue);
		getSauvegarderOptions().setLangues(langue);
	}
}
