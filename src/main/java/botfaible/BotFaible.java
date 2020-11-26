package botfaible;

import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.TypeJoueur;
import traitement.Controleur;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class BotFaible extends Controleur {
	/* Parametre Idjr */
	private String numPartie;
	private String joueurId;
	private TypeJoueur typeJoueur;
	private ConnexionType connexionType;
	private InetAddress ipPp;
	private int portPp;
	private List<PionCouleur> poinSacrDispo;
	private int delay;
	private ControleurReseau nwm;

	/* Parametre Temporaire */
	private List<Integer> pionAPos;

	public BotFaible(int delay) {
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

	public void setJoueurId(String joueurId) {
		this.joueurId = joueurId;
	}

	public TypeJoueur getTypeJoueur() {
		return typeJoueur;
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

	public List<PionCouleur> getPoinSacrDispo() {
		return poinSacrDispo;
	}

	public void setPoinSacrDispo(List<PionCouleur> list) {
		this.poinSacrDispo = list;
	}
}
