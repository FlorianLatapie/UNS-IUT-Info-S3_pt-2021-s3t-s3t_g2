package idjr;

import java.net.InetAddress;

import reseau.type.TypeJoueur;

public class PartieInfo {
	private InetAddress ip;
	private int port;
	private String idPartie;
	private TypeJoueur type;
	
	public PartieInfo(InetAddress ip, int port, String idPartie, TypeJoueur type) {
		this.ip = ip;
		this.port = port;
		this.idPartie = idPartie;
		this.type = type;
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
}
