package botmoyen;

import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.TypeJoueur;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class BotMoyen {
	/* Parametre Idjr */
	private String nom;
	private String numPartie;
	private String joueurId;
	private Couleur couleur;
	private TypeJoueur typeJoueur;
	private ConnexionType connexionType;
	private InetAddress ipPp;
	private int portPp;
	private List<PionCouleur> poinSacrDispo;
	private boolean envie;
	private List<Integer> lieuOuvert;
	private int delay;
	private ControleurReseau nwm;
	private boolean estFini;

	/* Parametre Temporaire */
	private List<Integer> pionAPos;

	public BotMoyen(int delay) {
		this.delay = delay;
	}
	
	public void start() throws IOException {
		initBot();
		initReseau();
		
		while (!isEstFini()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		arreter();
	}

	private void initBot() {
		this.typeJoueur = TypeJoueur.BOT;
		this.connexionType = ConnexionType.CLIENT;
		this.pionAPos = new ArrayList<>();
		this.lieuOuvert = new ArrayList<>();
		this.envie = true;
		this.estFini = false;
	}

	private void initReseau() throws IOException {
		TraitementPaquetUdp traitementPaquetUdp = new TraitementPaquetUdp(this);
		TraitementPaquetTcp traitementPaquetTcp = new TraitementPaquetTcp(this);
		nwm = new ControleurReseau(traitementPaquetTcp, traitementPaquetUdp);
		nwm.initConnexion(connexionType, ReseauOutils.getLocalIp());
	}
	
	public void arreter() {
			nwm.arreter();
	}

	public int getDelay() {
		return delay;
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

	public List<Integer> getLieuOuvert() {
		return lieuOuvert;
	}

	public void setLieuOuvert(List<Integer> lieuOuvert) {
		this.lieuOuvert = lieuOuvert;
	}

	public boolean getEnvie() {
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

	public void setEstFini(boolean estFini) {
		this.estFini = estFini;
	}

	public boolean isEstFini() {
		return estFini;
	}
}
