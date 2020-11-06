package idjr;

import ihmidjr.event.Initializer;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.socket.TcpClientSocket;
import reseau.tool.NetworkTool;
import reseau.tool.ThreadTool;
import reseau.type.Couleur;
import reseau.type.TypeJoueur;
import reseau.type.TypePartie;

import java.io.IOException;
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
	NetWorkManager nwm;
	Initializer initializer;
	/* Parametre Temporaire */
	private List<Integer> pionAPos;

	public Initializer getInitializer() {
		return initializer;
	}

	private List<PartieInfo> listOfServer;
	private PartieInfo current;

	/*                       */
	private int pionChoisi;

	public int getPionChoisi() {
		return pionChoisi;
	}

	public void setPionChoisi(int pionChoisi) {
		this.pionChoisi = pionChoisi;
	}

	public void pionChoisi(boolean t) {
		estPionChoisi = t;
	}

	public boolean pionDisponible() {
		return estPionChoisi;
	}

	private boolean estPionChoisi = false;

	/*                       */

	/*                       */
	private int lieuChoisi;

	public int getLieuChoisi() {
		return lieuChoisi;
	}

	public void setLieuChoisi(int lieuChoisi) {
		this.lieuChoisi = lieuChoisi;
	}

	public void lieuChoisi(boolean t) {
		estLieuChoisi = t;
	}

	public boolean lieuDisponible() {
		return estLieuChoisi;
	}

	private boolean estLieuChoisi = false;

	/*                       */

	/* Core */
	private final Jeu jeu = new Jeu();

	public Idjr(Initializer initializer) throws IOException {
		this.initializer = initializer;
		this.typeJoueur = TypeJoueur.JR;
		this.pionAPos = new ArrayList<>();
		this.listOfServer = new ArrayList<>();
		initReseau();
	}

	private void initReseau() throws IOException {
		nwm = new NetWorkManager(this);
		nwm.initConnection(SideConnection.CLIENT, NetworkTool.getAliveLocalIp());
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

	public void estPartieConnecte(String nom) {
		Thread thread = new Thread(() -> {
			listOfServers();
			for (PartieInfo partieInfo : listOfServer) {
				if (partieInfo.getIdPartie().equals(nom)) {
					System.out.println("OK");
					current = partieInfo;
					initializer.partieValide(nom);
					return;
				}
			}
		});

		thread.start();
	}

	private void listOfServers() {
		listOfServer.clear();

		// TODO QUE MIXTE
		// QUE 6
		String message = nwm.getPacketsUdp().get("RP").build(TypePartie.MIXTE, 5);
		nwm.getUdpSocket().sendPacket(message);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addPartie(PartieInfo p) {
		listOfServer.add(p);
	}

	public void rejoindrePartie(String nomp) {
		setIpPp(current.getIp());
		setPortPp(current.getPort());
		if (initializer != null)
			initializer.nomPartie(current.getIdPartie());
		String messageTcp = nwm.getPacketsTcp().get("DCP").build(nom, typeJoueur, current.getIdPartie());
		ThreadTool.asyncTask(() -> {
			String message1 = TcpClientSocket.connect(ipPp, portPp, messageTcp, nwm.getAddress(), nwm.getTcpPort());
			setJoueurId((String) nwm.getPacketsTcp().get("ACP").getValue(message1, 2));
		}, () -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			nwm.stopBind();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			nwm.startServerTCP();
		});
	}
	
	public synchronized void stop() {
		if (nwm != null) {
			if (nwm.getTcpServerSocket() != null)
				nwm.getTcpServerSocket().stop();
			if (nwm.getUdpSocket() != null)
				nwm.getUdpSocket().stop();
		}
	}
}
