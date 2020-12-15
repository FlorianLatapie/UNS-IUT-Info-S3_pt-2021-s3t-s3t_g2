package idjr;

import java.net.InetAddress;

import reseau.type.Statut;
import reseau.type.TypeJoueur;

public class PartieInfo {
	private InetAddress ip;
	private int port;
	private String idPartie;
	private TypeJoueur type;
	private int nbjr;
	private int nbjb;
	private int nbjrMax;
	private int nbjbMax;
	private Statut statut;

	public PartieInfo(InetAddress ip, int port, String idPartie, TypeJoueur type, int nbjr, int nbjb, int nbjrMax,
			int nbjbMax, Statut statut) {
		this.ip = ip;
		this.port = port;
		this.idPartie = idPartie;
		this.type = type;
		this.nbjr = nbjr;
		this.nbjb = nbjb;
		this.nbjrMax = nbjrMax;
		this.nbjbMax = nbjbMax;
		this.statut = statut;
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

	@Override
	public String toString() {
		return "[" + statut.name() + "] " + idPartie + " " + statut.name() + " " + nbjr + "/" + nbjrMax + " joueurs réels " + nbjb
				+ "/" + nbjbMax + " joueurs virtuels";
	}
}