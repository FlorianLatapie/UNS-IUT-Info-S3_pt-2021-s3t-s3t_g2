package idjr;

import idjr.ihmidjr.event.Evenement;
import idjr.ihmidjr.event.IJeuListener;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;
import reseau.type.CarteType;
import reseau.type.ConnexionType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.Statut;
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
	private List<CarteType> cartesUtiliser;
	private List<Object> resultatFouille;
	private Couleur voteChoisi;
	private boolean isContinue;
	private String etat;

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
		ControleurReseau.initConnexion(traitementPaquetTcp, traitementPaquetUdp, connexionType,
				ReseauOutils.getLocalIp(), false);
	}

	public void estPartieConnecte(String nom, int maxJr, TypePartie typePartie, Statut statut) {
		Thread thread = new Thread(() -> {
			listOfServers(maxJr, typePartie, statut);
			for (PartieInfo partieInfo : listOfServer) {
				if (partieInfo.getIdPartie().equals(nom)) {
					System.out.println("OK");
					current = partieInfo;
					ControleurReseau.demarrerClientTcp(partieInfo.getIp(), partieInfo.getPort());
					Evenement.partieValide(nom);
					return;
				}
			}
		});
		thread.start();
	}

	public void listOfServers(int maxJr, TypePartie typePartie, Statut statut) {
		listOfServer.clear();

		String message = ControleurReseau.construirePaquetUdp("RP", typePartie, maxJr);
		ControleurReseau.envoyerUdp(message);

		try {
			Thread.sleep(350);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<PartieInfo> partieInfos = listOfServer;

		for (PartieInfo partieInfo : partieInfos) {
			if (partieInfo.getTypeP() != typePartie)
				listOfServer.remove(partieInfo);

			if (partieInfo.getNbJoueurTotalMax() > maxJr)
				listOfServer.remove(partieInfo);
		}

		Evenement.partie(listOfServer);
	}

	public void rejoindrePartie(String nomp) {
		setIpPp(current.getIp());
		setPortPp(current.getPort());
		Evenement.nomPartie(current.getIdPartie());
		String messageTcp = ControleurReseau.construirePaquetTcp("DCP", nom, typeJoueur, current.getIdPartie());
		ThreadOutils.asyncTask("rejoindrePartie", () -> {
			ControleurReseau.envoyerTcp(messageTcp);
		});
	}

	private boolean desVote = false;

	public boolean desVote() {
		return desVote;
	}

	public void desVoteChoisi(boolean etat) {
		desVote = etat;
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

	private boolean estUtiliserCartesChoisi = false;

	public void choisirUtiliserCartes(List<CarteType> cartesUtiliser) {
		this.cartesUtiliser = cartesUtiliser;
	}

	public boolean utiliserCartesDisponible() {
		return estUtiliserCartesChoisi;
	}

	public List<CarteType> getUtiliserCartesChosi() {
		return cartesUtiliser;
	}

	public void utiliserCartesChoisi(boolean etat) {
		estUtiliserCartesChoisi = etat;
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
		Evenement.updateCarte();
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

	public List<Object> getResultatFouille() {
		return resultatFouille;
	}

	public void setResultatFouille(List<Object> resultatFouille) {
		this.resultatFouille = resultatFouille;
	}
	
	
}
