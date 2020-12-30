package idjr.ihmidjr;

import idjr.Idjr;
import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.Evenement;
import idjr.ihmidjr.langues.International;
import idjr.ihmidjr.langues.Langues;

/**
 * 
 * @author florian
 * @author sebastien
 *
 */
public class Core {
	//auteur florian 
	private int nbJoueur = 5;
	private int nbBot = 4;
	private String nomPartie = "partieParDéfaut";
	private Idjr idjr;
	private SauvegarderOptions sauvegarderOptions;

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

	public int getNbJoueurReel() {
		return nbJoueur - nbBot;
	}

	public void setIdjr(Idjr idjr) {
		this.idjr = idjr;
	}

	public Idjr getIdjr() {
		return idjr;
	}
	/**
	 * @author Sebastien
	 * @return les options actuelles du programme
	 */
	public SauvegarderOptions getSauvegarderOptions() {
		return sauvegarderOptions;
	}
	
	/**
	 * @author Sebastien
	 * @param langues nouvelle langue a appliquer
	 */
	public void changerLangue(Langues langues) {
		sauvegarderOptions.setLangues(langues);
		International.changerLangue(langues);
	}
}