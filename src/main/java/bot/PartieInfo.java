package bot;

import java.net.InetAddress;

import reseau.type.Statut;
import reseau.type.TypeJoueur;
import reseau.type.TypePartie;

public class PartieInfo {
	private InetAddress ip;
	private int port;
	private String idPartie;
	private TypeJoueur type;
	private TypePartie typeP;
	private int nbjr;
	private int nbjb;
	private int nbjrMax;
	private int nbjbMax;
	private Statut statut;
	private String nom;

	public PartieInfo(InetAddress ip, int port, String idPartie, TypeJoueur type, int nbjr, int nbjb, int nbjrMax,
			int nbjbMax, Statut statut, String nom) {
		this.ip = ip;
		this.port = port;
		this.idPartie = idPartie;
		this.type = type;
		this.nbjr = nbjr;
		this.nbjb = nbjb;
		this.nbjrMax = nbjrMax;
		this.nbjbMax = nbjbMax;
		this.statut = statut;
		this.typeP = getTypePartie();
		this.nom = nom;
	}

	private TypePartie getTypePartie() {
		if (nbjbMax == 0)
			return TypePartie.BOTU;
		else if (nbjrMax == 0)
			return TypePartie.BOTU;
		else
			return TypePartie.MIXTE;
	}

	public InetAddress getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getIdPartie() {
		return idPartie;
	}

	public TypeJoueur getType() {
		return type;
	}

	public TypePartie getTypeP() {
		return typeP;
	}

	public int getNbJoueurTotalMax() {
		return nbjbMax + nbjrMax;
	}

	public int getNbJoueurTotal() {
		return nbjb + nbjr;
	}

	public Statut getStatut() {
		return statut;
	}
	
	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return "[" + statut.nomEntier() + "] " + nom + " " + nbjr + "/" + nbjrMax + " joueurs réels " + nbjb + "/"
				+ nbjbMax + " joueurs virtuels " + getNbJoueurTotal() + "/" + getNbJoueurTotalMax() + " joueurs total";
	}
}
