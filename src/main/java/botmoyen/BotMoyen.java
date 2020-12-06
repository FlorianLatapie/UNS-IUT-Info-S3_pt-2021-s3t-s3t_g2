package botmoyen;

import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.TypeJoueur;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import partie.ControleurPartie;
import partie.Joueur;
import partie.Partie;

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
	private int compteurTour;
	private List<CarteType> listeCarte;
	private List<PionCouleur> listePion;
	private List<Couleur> couleurJoueursPresent;
	private List<Couleur> joueurEnVie;
	private VoteType voteType;
	private Partie partie;

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

	public void setCouleurJoueurs(List<Couleur> couleurJoueurs) {
		this.couleurJoueursPresent = couleurJoueurs;
	}

	private void initBot() {
		this.typeJoueur = TypeJoueur.BOT;
		this.connexionType = ConnexionType.CLIENT;
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

	public List<CarteType> getListeCarte() {
		return listeCarte;
	}

	public void addCarte(CarteType n) {
		listeCarte.add(n);
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

	public int getCompteurTour() {
		return compteurTour;
	}

	public void setCompteurTour(int compteurTour) {
		this.compteurTour = compteurTour;
	}
	
	public void initPartie(List<Couleur> couleurs) {
		partie = new Partie(couleurs);
	}

	public void initCarte(CarteType value) {
		partie.getJoueurs().get(couleur).getCartes().add(value);
		partie.getCartes().remove(value);
		for ( Joueur j : partie.getJoueurs().values()) {
			if (!j.getCouleur().equals(couleur)) {
				CarteType c =partie.getCartes().get(new Random().nextInt(partie.getCartes().size()));
				partie.getCartes().remove(c);
				partie.getJoueurs().get(j.getCouleur()).getCartes().add(c);
			}
		}
	}

	public void deplPion(int dest, int pion) {
		partie.deplacePerso(couleur, pion, dest);
	}
	
	public void placPion(Couleur c,int dest, int pion) {
		partie.deplacePerso(c, pion, dest);
	}
	
	public void initZombie(String s) {
		List<Integer> listeIndexLieux = new ArrayList<Integer>();
		for (String a : s.split(",")) {
			listeIndexLieux.add(Integer.parseInt(a));
		}
		partie.entreZombie(listeIndexLieux);
	}

	public void carteCamion(List<Object> carteChoisies) {
		CarteType c1=(CarteType)carteChoisies.get(0);
		CarteType c2=(CarteType)carteChoisies.get(2);
		CarteType c3=(CarteType)carteChoisies.get(3);
		Couleur couleurJC=(Couleur)carteChoisies.get(4);
		if (partie.getCartes().contains(c1)) {
			partie.givecarte(couleur, c1);
		}
		else {
			partie.givecarte(couleur, partie.getCartes().get(new Random().nextInt(partie.getCartes().size())));
		}

		if (partie.getCartes().contains(c2)) {
			partie.givecarte(couleurJC, c2);
		}
		else {
			partie.givecarte(couleurJC, partie.getCartes().get(new Random().nextInt(partie.getCartes().size())));
		}
		if (partie.getCartes().contains(c3)) {
			partie.getCartes().remove(c3);
		}
		else {
			partie.getCartes().remove(partie.getCartes().get(new Random().nextInt(partie.getCartes().size())));
		}
		
	}

	public void resFouille(Couleur cjg, Couleur cjo, CarteEtat cd) {
		if (!cjg.equals(couleur)) {
			partie.givecarte(cjg, partie.getCartes().get(new Random().nextInt(partie.getCartes().size())));
		}
		if (!cjo.equals(couleur)) {
			partie.givecarte(cjo, partie.getCartes().get(new Random().nextInt(partie.getCartes().size())));
		}
		if (cd.equals(CarteEtat.CD)) {
			partie.getCartes().remove(partie.getCartes().get(new Random().nextInt(partie.getCartes().size())));
		}
		
	}

	public void recupCarte(CarteType value) {
		partie.givecarte(couleur, value);
		
	}

	public void resVigile(Couleur value) {
		partie.setChef(value);
		
	}

	public void NewChef(VigileEtat value) {
		if (value.equals(VigileEtat.NE))
			partie.setNewChef(true);
		else
			partie.setNewChef(false);
		
	}
	
	
	
}
