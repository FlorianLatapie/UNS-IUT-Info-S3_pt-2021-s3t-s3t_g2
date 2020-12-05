package idjr;

import idjr.ihmidjr.event.Initializer;
import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;
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
	ControleurReseau nwm;
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
		TraitementPaquetTcp traitementPaquetTcp = new TraitementPaquetTcp(this);
		TraitementPaquetUdp traitementPaquetUdp = new TraitementPaquetUdp(this);
		nwm = new ControleurReseau(traitementPaquetTcp, traitementPaquetUdp);
		nwm.initConnexion(ConnexionType.CLIENT, ReseauOutils.getLocalIp());
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
		String message = nwm.construirePaquetUdp("RP", TypePartie.MIXTE, 5);
		nwm.envoyerUdp(message);

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
		String messageTcp = nwm.construirePaquetTcp("DCP",nom, typeJoueur, current.getIdPartie());
		ThreadOutils.asyncTask(() -> {
			nwm.getTcpClient().envoyer(messageTcp);

			nwm.getTcpClient().attendreMessage("ACP");
			String message1 = nwm.getTcpClient().getMessage("ACP");
			setJoueurId((String) nwm.getPaquetTcp("ACP").getValue(message1, 2));
		});
	}

	public synchronized void stop() {
		nwm.arreter();
	}
}
