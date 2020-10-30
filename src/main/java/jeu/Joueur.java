package jeu;

import reseau.type.Couleur;
import temp.CarteAction;

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
    public String getJoueurId() {
        return joueurId;
    }

    private final String joueurId;

    public int getJoueurIdint() {
        return joueurIdint;
    }

    private final int joueurIdint;
    private final InetAddress ip;
    private final int port;

    private Couleur couleur;
    private boolean enVie;
    private HashMap<Integer, Personnage> personnages;
    private final ArrayList<CarteAction> cartes;
    private boolean chefDesVigiles;
    private final String nom;

    /**
     * @param joueurIdint
     * @param ip
     * @param port
     * @param nom         le nom du joueur
     */
    public Joueur(int joueurIdint, InetAddress ip, int port, String nom) {
        this.joueurIdint = joueurIdint;
        this.joueurId = "J" + joueurIdint;
        this.ip = ip;
        this.port = port;
        enVie = true;
        personnages = new HashMap<>();
        cartes = new ArrayList<>();
        this.nom = nom;
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
    public List<CarteAction> getCartes() {
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
}