package pp.ihm;

import pp.controleur.ControleurJeu;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.eventListener.Initializer;

public class Core {
    private ControleurJeu cj = null;
    private int nbJoueur = 5;
    private int nbBot = 4;
    private String nomPartie = "partieParDéfaut";


    private Initializer initializer;

    public void eventInit() {
        this.initializer = new Initializer();
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

    public ControleurJeu getCj() {
        return cj;
    }

    public void setCj(ControleurJeu cj) {
        this.cj = cj;
    }

    public int getNbJoueurReel() {
        return nbJoueur - nbBot;
    }

    public Initializer getInitializer() {
        return initializer;
    }
}
