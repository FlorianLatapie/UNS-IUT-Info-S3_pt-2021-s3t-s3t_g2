package pp;

import reseau.socket.TcpClient;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.TypePersonnage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Les infos d'un joueur</h1>. A pour rôle de définir un joueur
 *
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @version 1
 * @since 05/10/2020
 */
public class Joueur {

	/** Identifiant du joueur au format chaîne de caractères */
	private final String joueurId;

	/** Identifiant du joueur sous la forme d'un entier */
	private final int joueurIdint;
	
	/** Couleur en jeu d'un joueur */
	private Couleur couleur;

	/** Booléen indiquant si le joueur est en vie ou non */
	private boolean enVie;

	/** Dictionnaire des personnages du joueur */
	private HashMap<Integer, Personnage> personnages;

	/** Liste des cartes de jeu du joueur */
	private final ArrayList<CarteType> cartes;

	/** Booléen indiquant si le joueur est chef des vigiles ou non */
	private boolean chefDesVigiles;

	/** Nom du joueur */
	private final String nom;

	/** Connexion au client TCP du joueur */
	private final TcpClient connection;

	/**
	 * Créer un nouveau joueur, pouvant rejoindre une partie.
	 * 
	 * @param joueurIdint L'identifiant au format entier
	 * @param ip          L'adresse IP
	 * @param port        Le port de connexion au serveur TCP
	 * @param nom         Le nom
	 * @param connection  La connexion au client TCP
	 */
	public Joueur(int joueurIdint, String nom, TcpClient connection) {
		this.connection = connection;
		this.joueurIdint = joueurIdint;
		this.joueurId = "J" + joueurIdint;
		enVie = true;
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
		this.nom = nom;
	}

	/**
	 * Créer un nouveau joueur, pouvant rejoindre une partie.
	 * 
	 * @param joueurIdint L'identifiant au format entier
	 * @param ip          L'adresse IP
	 * @param port        Le port de connexion au serveur TCP
	 * @param nom         Le nom
	 */
	public Joueur(int joueurIdint, InetAddress ip, int port, String nom) {
		this.connection = null;
		this.joueurIdint = joueurIdint;
		this.joueurId = "J" + joueurIdint;
		enVie = true;
		personnages = new HashMap<>();
		cartes = new ArrayList<>();
		this.nom = nom;
	}

	/**
	 * Récupère l'identifiant sous forme de chaine de caractère du joueur.
	 * 
	 * @return joueurId L'identifiant du joueur
	 */
	public String getJoueurId() {
		return joueurId;
	}

	/**
	 * Récupère l'identifiant sous forme d'entier du joueur.
	 * 
	 * @return jouerIdint L'identifiant entier du joueur
	 */
	public int getJoueurIdint() {
		return joueurIdint;
	}

	/**
	 * Récupère la couleur du joueur.
	 * 
	 * @return la couleur
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur du joueur.
	 *
	 * @param couleur La couleur cible
	 */
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	/**
	 * Récupère la liste des personnages du joueur.
	 * 
	 * @return personnages La liste de personnages
	 */
	public Map<Integer, Personnage> getPersonnages() {
		return personnages;
	}

	/**
	 * Modifie la liste de personnages du joueur.
	 *
	 * @param personnages Le personnage cible
	 */
	public void setPersonnages(HashMap<Integer, Personnage> personnages) {
		this.personnages = personnages;
	}

	/**
	 * Récupère la liste des cartes du joueur.
	 * 
	 * @return cartes Les cartes actions du joueur
	 */
	public List<CarteType> getCartes() {
		return cartes;
	}

	/**
	 * Récupère le statut en vie ou non du joueur.
	 * 
	 * @return enVie Si le joueur est en vie ou non
	 */
	public boolean isEnVie() {
		return enVie;
	}

	/**
	 * Modifie le statut en vie du joueur.
	 * 
	 * @param enVie Définit si le joueur est en vie
	 */
	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	/**
	 * Récupère le statut chef des vigiles du joueur.
	 * 
	 * @return chefDesVigiles Si le joueur courant est le chef des vigiles
	 */
	public boolean isChefDesVigiles() {
		return chefDesVigiles;
	}

	/**
	 * Modifie le statu chef des vigiles du joueur.
	 * 
	 * @param chefDesVigiles Définit si le joueur courant est le chef des vigiles
	 */
	public void setChefDesVigiles(boolean chefDesVigiles) {
		this.chefDesVigiles = chefDesVigiles;
	}

	/**
	 * Récupère le nom du joueur
	 * 
	 * @return nom Le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Récupère la connexion au client TCP du joueur.
	 * 
	 * @return connection La connexion au client TCP du joueur
	 */
	public TcpClient getConnection() {
		return connection;
	}

	/**
	 * Gère l'affichage du nom du joueur
	 * 
	 * @return le nom du joueur
	 */
	@Override
	public String toString() {
		return this.nom;
	}

	/**
	 * Calcule le nombre de voix du joueur lors d'un vote.
	 * 
	 * @param l
	 * @param numeroVote
	 * @param nbCarteMenace
	 * @return nbVoix Le nombre de voix du joueur
	 */
	public int getNbVoix(Lieu l, int numeroVote, int nbCarteMenace) {
		int nbVoix = 0;
		for (Personnage p : this.personnages.values()) {
			if (l.getPersonnage().contains(p) && !p.estCache) {
				if (p.getType() == TypePersonnage.TRUAND) {
					nbVoix += 2;
				} else {
					nbVoix += 1;
				}
			}
		}
		if (nbVoix == 0 && numeroVote == 2)
			nbVoix = 1;
		nbVoix += nbCarteMenace;
		return nbVoix;
	}
}
