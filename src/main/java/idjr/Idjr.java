package idjr;

import reseau.type.Couleur;
import reseau.type.TypeJoueur;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Idjr {
    /* Parametre Idjr */
    private String nom;
    private String numPartie;
    private String joueurId;
    private Couleur couleur;
    private final TypeJoueur typeJoueur;
    private InetAddress ipPp;
    private int portPp;

    /* Parametre Temporaire */
    private List<Integer> pionAPos;

    /* Core */
    private final Jeu jeu = new Jeu();

    public Idjr() {
        this.typeJoueur = TypeJoueur.JR;
        this.pionAPos = new ArrayList<>();
    }

    public Joueur getMoi() {
        for (Joueur j : jeu.getJoueurs().values()) {
            if (j.getCouleur() == couleur) {
                return j;
            }
        }

        return null;
    }

    public Joueur getJoueur(Couleur c) {
        for (Joueur j : jeu.getJoueurs().values()) {
            if (j.getCouleur() == c) {
                return j;
            }
        }

        return null;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public List<Integer> getPionAPos() {
        return pionAPos;
    }

    public void setPionAPos(List<Integer> pionAPos) {
        this.pionAPos = pionAPos;
    }

    public void setJoueurId(String joueurId) {
        this.joueurId = joueurId;
    }

    public TypeJoueur getTypeJoueur() {
        return typeJoueur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public String getJoueurId() {
        return joueurId;
    }

    public String getNumPartie() {
        return numPartie;
    }

    public InetAddress getIpPp() {
        return ipPp;
    }

    public void setIpPp(InetAddress ipPp) {
        this.ipPp = ipPp;
    }

    public int getPortPp() {
        return portPp;
    }

    public void setPortPp(int portPp) {
        this.portPp = portPp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
