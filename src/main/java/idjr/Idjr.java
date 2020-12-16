package idjr;

import idjr.ihmidjr.event.Initializer;
import idjr.ihmidjr.event.JeuListener;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;
import reseau.type.CarteType;
import reseau.type.ConnexionType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.TypeJoueur;
import reseau.type.TypePartie;
import reseau.type.VoteType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Idjr {
	/* Parametre Idjr */
	private boolean estLieuChoisi = false;
	private List<PartieInfo> listOfServer;
	private PartieInfo current;
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
	private boolean estFini;
	private List<CarteType> listeCarte;
	private List<PionCouleur> listePion;
	private HashMap<Couleur, String> listeJoueursInitiale;
	private List<Couleur> couleurJoueursPresent;
	private List<Couleur> joueurEnVie;
	private VoteType voteType;
	private int pionChoisi;
	private int lieuChoisi;
	private CarteType carteChoisi;
	private Couleur couleurChoisi;
	private CarteType carteUtiliser;
	private Couleur voteChoisi;
	private boolean isContinue;
	private String etat;
	Initializer initializer;

	/* Parametre Temporaire */
	private List<Integer> pionAPos;

	public Idjr() throws IOException {
		initBot();
		initReseau();
	}

	private void initBot() {
		listeJoueursInitiale = new HashMap<>();
		this.typeJoueur = TypeJoueur.JR;
		this.connexionType = ConnexionType.CLIENT;
		this.listOfServer = new ArrayList<>();
		this.pionAPos = new ArrayList<>();
		this.lieuOuvert = new ArrayList<>();
		this.listeCarte = new ArrayList<>();
		this.couleurJoueursPresent = new ArrayList<>();
		this.listePion = new ArrayList<>();
		this.envie = true;
		this.estFini = false;
		this.joueurEnVie = new ArrayList<>();
	}

	private void initReseau() throws IOException {
		TraitementPaquetTcp traitementPaquetTcp = new TraitementPaquetTcp(this);
		TraitementPaquetUdp traitementPaquetUdp = new TraitementPaquetUdp(this);
		ControleurReseau.initConnexion(traitementPaquetTcp, traitementPaquetUdp,connexionType, ReseauOutils.getLocalIp());
	}

	public void estPartieConnecte(String nom) {
		Thread thread = new Thread(() -> {
			listOfServers();
			for (PartieInfo partieInfo : listOfServer) {
				if (partieInfo.getIdPartie().equals(nom)) {
					System.out.println("OK");
					current = partieInfo;
					ControleurReseau.demarrerClientTcp(partieInfo.getIp());
					initializer.partieValide(nom);
					return;
				}
			}
		});
		thread.start();
	}

	public void listOfServers() {
		listOfServer.clear();

		// TODO QUE MIXTE
		// QUE 6
		String message = ControleurReseau.construirePaquetUdp("RP", TypePartie.MIXTE, 5);
		ControleurReseau.envoyerUdp(message);

		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		initializer.partie(listOfServer);
	}

	public void rejoindrePartie(String nomp) {
		setIpPp(current.getIp());
		setPortPp(current.getPort());
		if (initializer != null)
			initializer.nomPartie(current.getIdPartie());
		String messageTcp = ControleurReseau.construirePaquetTcp("DCP", nom, typeJoueur, current.getIdPartie());
		ThreadOutils.asyncTask("rejoindrePartie", () -> {
			ControleurReseau.envoyerTcp(messageTcp);
			ControleurReseau.attendreTcp("ACP");
			String message1 = ControleurReseau.getMessageTcp("ACP");
			setJoueurId((String) ControleurReseau.getValueTcp("ACP", message1, 2));
		});
	}

	public Initializer getInitializer() {
		return initializer;
	}

	private boolean voteChoisiBool = false;

	public boolean voteDisponible() {
		return voteChoisiBool;
	}

	public Couleur getVoteChoisi() {
		return voteChoisi;
	}

	public void voteChoisi(boolean etat) {
		voteChoisiBool = etat;
	}

	public void setVoteChoisi(Couleur voteChoisi) {
		this.voteChoisi = voteChoisi;
	}

	private boolean estUtiliserCarteChoisi = false;

	public void choisirUtiliserCarte(CarteType carteUtiliser) {
		this.carteUtiliser = carteUtiliser;
	}

	public boolean utiliserCarteDisponible() {
		return estUtiliserCarteChoisi;
	}

	public CarteType getUtiliserCarteChosi() {
		return carteUtiliser;
	}

	public void utiliserCarteChoisi(boolean etat) {
		estUtiliserCarteChoisi = etat;
	}

	public int getPionChoisi() {
		return pionChoisi;
	}

	public void setPionChoisi(int pionChoisi) {
		this.pionChoisi = pionChoisi;
	}

	public void pionChoisi(boolean t) {
		estPionChoisi = t;
	}

	public void setEtatChoisi(String etat) {
		this.etat = etat;
	}

	public String getEtatChoisi() {
		return etat;
	}

	public boolean pionDisponible() {
		return estPionChoisi;
	}

	private boolean estPionChoisi = false;

	public CarteType getCarteChoisi() {
		return carteChoisi;
	}

	public void setCarteChoisi(CarteType carte) {
		this.carteChoisi = carte;
	}

	public void carteChoisi(boolean t) {
		estCarteChoisi = t;
	}

	public boolean carteDisponible() {
		return estCarteChoisi;
	}

	private boolean estCarteChoisi = false;

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

	public void addPartie(PartieInfo p) {
		listOfServer.add(p);
	}

	public synchronized void stop() {
		ControleurReseau.arreter();
	}

	public void setCouleurJoueurs(List<Couleur> couleurJoueurs) {
		this.couleurJoueursPresent = couleurJoueurs;
	}

	public void arreter() {
		ControleurReseau.arreter();
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

	public List<CarteType> getListeCarte() {
		return listeCarte;
	}

	public void addCarte(CarteType n) {
		listeCarte.add(n);
		initializer.updateCarte();
	}

	public void setListeCarte(List<CarteType> listeCarte) {
		this.listeCarte = listeCarte;
	}

	public List<PionCouleur> getListePion() {
		return listePion;
	}

	public void setListePion(List<PionCouleur> listePion) {
		this.listePion = listePion;
	}

	public List<Couleur> couleurJoueurPresent() {
		return couleurJoueursPresent;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public List<Couleur> getJoueurEnVie() {
		return joueurEnVie;
	}

	public void setJoueurEnVie(List<Couleur> joueurEnVie) {
		this.joueurEnVie = joueurEnVie;
	}

	public Map<Couleur, String> getListeJoueursInitiale() {
		return listeJoueursInitiale;
	}

	public void putListeJoueursInitiale(Couleur c, String nom) {
		this.listeJoueursInitiale.put(c, nom);
	}

	public void setCouleurChoisi(Couleur selectedCouleur) {
		this.couleurChoisi = selectedCouleur;
	}

	public Couleur getCouleurChoisi() {
		return couleurChoisi;
	}

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}
}
