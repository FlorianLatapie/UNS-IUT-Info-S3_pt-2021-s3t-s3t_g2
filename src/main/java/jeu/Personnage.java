package jeu;

/**
 * <h1> Le personnage  </h1>
 *
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 0.1
 * @since 04/10/2020
 */
public abstract class Personnage {
    protected Lieu monLieu;// position actuel du personnage
    private Joueur joueur;
    private TypePersonnage type;
    protected int point;
    protected int nbrZretenu = 1;
    protected int nbrVoixPourVoter = 1;
    protected int nbrElection;
    protected boolean estVivant;
    protected boolean estCache;


    /**
     * @param joueur le joueur possedant le personnage
     * @param type   le type du personnage
     */
    public Personnage(Joueur joueur, TypePersonnage type) {
        this.type = type;
        this.joueur = joueur;
    }

    /**
     * Renvoie true si le personnage est vivant, sinon false
     *
     * @return si le personnage est vivant
     */
    public boolean isEstVivant() {
        return estVivant;
    }

    /**
     * Définis en true ou false le paramétre EstVivant du personnage
     *
     * @param estVivant nouvel etat du jouer, s'il est en vie
     */
    public void setEstVivant(boolean estVivant) {
        this.estVivant = estVivant;
    }

    /**
     * @return si le personnage est caché, sinon false
     */
    public boolean isEstCache() {
        return estCache;
    }

    /**
     * Définis par true ou false le paramétre EstCache du personnage
     *
     * @param estCache si le personnage est caché
     */
    public void setEstCache(boolean estCache) {
        this.estCache = estCache;
    }

    /**
     * @return le nombre de vote donné à ce personnage à la suite d'un vote
     */
    public int getNbrElection() {
        return nbrElection;
    }

    /**
     * Définis le nombre de vote donné à ce personnage à la suite d'un vote
     *
     * @param nbrElection le nombre de vote
     */
    public void setNbrElection(int nbrElection) {
        this.nbrElection = nbrElection;
    }

    /**
     * @return le nombre de point du personnage
     */
    public int getPoint() {
        return point;
    }

    /**
     * Modifie le nombre de point du personnage
     *
     * @param point le point du personnage
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * @return le nombre de Zombie que le personnage peut retenir
     */
    public int getNbrZretenu() {
        return nbrZretenu;
    }

    /**
     * @return le nombre de voix que le personnage peut utiliser durant un vote
     */
    public int getNbrVoixPourVoter() {
        return nbrVoixPourVoter;
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

    /**
     * Définis le joueur qui posséde le personnage
     *
     * @param joueur le joueur cible
     */
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    /**
     * @return le type du personnage
     */
    public TypePersonnage getType() {
        return type;
    }

    public abstract String toString();
}
