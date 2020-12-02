package pp;

import reseau.socket.Connexion;
import reseau.type.CarteType;
import reseau.type.Couleur;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Les infos d'un joueur</h1>
 *
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @version 0.1
 * @since 05/10/2020
 */
public class Joueur {
	private final String joueurId;
	private final int joueurIdint;
	private final InetAddress ip;
	private final int port;
	private Couleur couleur;
	private boolean enVie;
	private HashMap<Integer, Personnage> personnages;
	private final ArrayList<CarteType> cartes;
	private boolean chefDesVigiles;
	private final String nom;
	private final Connexion connection;

	public Joueur(int joueurIdint, InetAddress ip, int port, String nom, Connexion connection) {
		this.connection = connection;
		this.joueurIdint = joueurIdint;
		this.joueurId = "J" + joueurIdint;
		this.ip = ip;
		this.port = port;
		enVie = true;
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
		this.nom = nom;
	}

	public Joueur(int joueurIdint, InetAddress ip, int port, String nom) {
		this.connection = null;
		this.joueurIdint = joueurIdint;
		this.joueurId = "J" + joueurIdint;
		this.ip = ip;
		this.port = port;
		enVie = true;
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
		this.nom = nom;
	}
	
	public String getJoueurId() {
		return joueurId;
	}

	public int getJoueurIdint() {
		return joueurIdint;
	}

	public int getNbVoix(Lieu l, int numeroVote, int nbCarteMenace) {
		int nbVoix = 0;
		for(Personnage p : this.personnages.values()) {
			if (p.getMonLieu() == l) {
				if (p.getType() == TypePersonnage.TRUAND) {
					nbVoix += 2;
				}else {
					nbVoix += 1;
				}
			}
		}
		if (nbVoix == 0 && numeroVote == 2)
			nbVoix = 1;
		nbVoix += nbCarteMenace;
		return nbVoix;
	}
	

	/**
	 * @return la couleur
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur du joueur
	 *
	 * @param couleur la couleur cible
	 */
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	/**
	 * @return la liste de personnages
	 */
	public Map<Integer, Personnage> getPersonnages() {
		return personnages;
	}

	/**
	 * Modifie la liste de personnages
	 *
	 * @param personnages le personnage cible
	 */
	public void setPersonnages(HashMap<Integer, Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * @return les cartes actions du joueur
	 */
	public List<CarteType> getCartes() {
		return cartes;
	}

	/**
	 * @return si le joueur est en vie
	 */
	public boolean isEnVie() {
		return enVie;
	}

	/**
	 * @param enVie definit si le joueur est en vie
	 */
	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	/**
	 * @return si le joueur courant est le chef des vigile
	 */
	public boolean isChefDesVigiles() {
		return chefDesVigiles;
	}

	/**
	 * @param chefDesVigiles définit si le joueur courant est le chef des vigiles
	 */
	public void setChefDesVigiles(boolean chefDesVigiles) {
		this.chefDesVigiles = chefDesVigiles;
	}

	/**
	 * @return nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return le nom du joueur
	 */
	@Override
	public String toString() {
		return this.nom;
	}

	public InetAddress getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public Connexion getConnection() {
		return connection;
	}
}