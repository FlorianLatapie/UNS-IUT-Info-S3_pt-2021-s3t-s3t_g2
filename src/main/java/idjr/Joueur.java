package idjr;

import reseau.type.Couleur;

import java.util.HashMap;
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


    private Couleur couleur;
    private boolean enVie;
    private HashMap<Integer, Personnage> personnages;
    private final String nom;

    /**
     * @param joueurIdint
     * @param ip
     * @param port
     * @param nom         le nom du joueur
     */
    public Joueur(String nom, Couleur c) {
    	this.couleur = c;
        enVie = true;
        personnages = new HashMap<>();
        this.nom = nom;
    }

    /**
     * @return la couleur
     */
    public Couleur getCouleur() {
        return couleur;
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
     * @return nom du joueur
     */
    public String getNom() {
        return nom;
    }

}