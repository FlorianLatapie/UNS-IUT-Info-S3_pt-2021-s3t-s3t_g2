package botfaible;

import reseau.type.Couleur;
import reseau.type.PionCouleur;
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
    private List<PionCouleur> poinSacrDispo;
    private Boolean envie;
    private List<Integer> lieuOuvert;

    public List<Integer> getLieuOuvert() {
		return lieuOuvert;
	}

	public void setLieuOuvert(List<Integer> lieuOuvert) {
		this.lieuOuvert = lieuOuvert;
	}

	public Boolean getEnvie() {
		return envie;
	}

	public void setEnvie(Boolean envie) {
		this.envie = envie;
	}

	public List<PionCouleur> getPoinSacrDispo() {
		return poinSacrDispo;
	}

	public void setPoinSacrDispo(List<PionCouleur> list) {
		this.poinSacrDispo = list;
	}

	/* Parametre Temporaire */
    private List<Integer> pionAPos;


    public Idjr() {
        this.typeJoueur = TypeJoueur.BOT;
        this.pionAPos = new ArrayList<>();
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
