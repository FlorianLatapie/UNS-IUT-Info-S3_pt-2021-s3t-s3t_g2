package bot.partie;


import reseau.type.Couleur;

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
public class Personnage{

	protected int monidLieu;// position actuel du personnage
	protected Couleur couleur;
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
	public Personnage(Joueur joueur,int point,int nbZretenu, int nbrVoixPourVoter) {
		this.point=point;
		this.nbrZretenu = nbZretenu;
		this.nbrVoixPourVoter = nbrVoixPourVoter;
		this.couleur = joueur.getCouleur();
	}
	

	public Personnage(int monidLieu, Couleur couleur, int point, int nbrZretenu,
			int nbrVoixPourVoter, int nbrElection, boolean estVivant, boolean estCache) {
		super();
		this.monidLieu = monidLieu;
		this.couleur = couleur;
		this.point = point;
		this.nbrZretenu = nbrZretenu;
		this.nbrVoixPourVoter = nbrVoixPourVoter;
		this.nbrElection = nbrElection;
		this.estVivant = estVivant;
		this.estCache = estCache;
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
	 * @return le nombre de point du personnage
	 */
	public int getPoint() {
		return point;
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
	public void changerDeLieux(int newidLieu) {
		monidLieu = newidLieu;
	}

	/**
	 * @return le lieu dans lequel se trouve actuellement le joueur
	 */
	public int getMonLieu() {
		return monidLieu;
	}

	/**
	 * @return Renvoie le joueur possédant le personnage
	 */
	public Couleur getCouleur() {
		return this.couleur;
	}



	public String toString() {
		if (point==7)
			return "La Blonde";
		if (point==5)
			return "La Brute";
		if (point==3)
			return "Le Truand";
		if (point==1)
			return "La Fillette";
		return "Inconnu";
		
	}
	
	public Personnage copyOf() {
		return new Personnage( monidLieu,  couleur,  point,  nbrZretenu, nbrVoixPourVoter,  nbrElection,  estVivant,  estCache);
	}
	


}
