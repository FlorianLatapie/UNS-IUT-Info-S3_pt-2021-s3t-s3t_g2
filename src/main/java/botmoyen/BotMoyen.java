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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import botmoyen.partie.ControleurPartie;
import botmoyen.partie.Joueur;
import botmoyen.partie.Lieu;
import botmoyen.partie.Partie;

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

	public void placePion(int dest, int pion) {
		partie.placePerso(couleur, pion, dest);
	}
	public void deplacePion(int dest, int pion) {
		partie.deplacePerso(couleur, pion, dest);
	}
	
	public void deplacePionCouleur(Couleur c,int dest, int pion) {
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

	public void arriveZombie(String value) {
		List<Integer> arr = new ArrayList<Integer>();
		for (String s : value.split(","))
			arr.add(Integer.parseInt(s));
		partie.entreZombie(arr);
		
	}

	public void joueCarte(Couleur value,CarteType carte) {
		if (partie.getJoueurs().get(value).getCartes().contains(carte)) {
			partie.getJoueurs().get(value).getCartes().remove( carte);
		}
		else if (partie.getCartes().contains(carte)) {
				partie.getCartes().remove(carte);
				CarteType c = partie.getJoueurs().get(value).getCartes().get(new Random().nextInt(partie.getJoueurs().get(value).getCartes().size()));
				partie.getCartes().add(c);
				partie.getJoueurs().get(value).getCartes().remove(c);		
			
		}
		else {
			for (Couleur c : partie.getJoueursCouleurs()) {
				if (partie.getJoueurs().get(c).getCartes().contains(carte)) {
					partie.getJoueurs().get(c).getCartes().remove(carte);
					CarteType ca = partie.getJoueurs().get(value).getCartes().get(new Random().nextInt(partie.getJoueurs().get(value).getCartes().size()));
					partie.getJoueurs().get(c).getCartes().add(ca);
					partie.getJoueurs().get(value).getCartes().remove(ca);
					break;
				}
			}
		}
		
	}

	public void arriveSoloZombie(Integer value) {
		List<Integer> arr = new ArrayList<Integer>();
		arr.add(value);
		partie.entreZombie(arr);
		
	}

	public void setLieuxFerme(List<Integer> lieux) {
		for (Integer i : lieux) {
			partie.getLieux().get(i).setOuvert(false);
		}
		
	}

	public void deplPionJoueurCourant(Couleur value, Integer dest, Integer pion) {
		partie.deplacePerso(value, pion, dest);
		
	}

	public void setZombie(String listeL, String listeZ) {
		String[] s = listeL.split(",");
		String[] z = listeZ.split(",");
		List<Integer> li = new ArrayList<Integer>();
		for (int i =0 ;i<s.length;i++) {
			partie.getLieux().get(Integer.parseInt(s[i])).setNbZombies(Integer.parseInt(z[i]));
			li.add(Integer.parseInt(z[i]));
		}
		for (Lieu l : partie.getLieux().values()) {
			if (!li.contains(l.getNum())) {
				l.setOuvert(false);
			}
		}
		
	}

	public void recupInfoPerso(Integer value, List<Object> value2,Integer lieu) {
		List<Couleur> lc = new ArrayList<Couleur>();
		List<Integer> li = new ArrayList<Integer>();
		for (int i =0;i<value2.size();i=i+2) {
			lc.add((Couleur)value2.get(i));
			li.add((Integer)value2.get(i+1));
		}
		for (int i = 0 ; i<lc.size();i++) {
			partie.deplacePerso(lc.get(i), li.get(i), lieu);
		}
		
	}

	public void corectionZombie(Integer lieu, Integer nbz) {
		partie.setZombieSurLieu(lieu,nbz);
		
	}

	public void joueCartes(Couleur value, List<CarteType> value2) {
		for (CarteType c : value2) {
			joueCarte(value, c);
		}
		
	}

	public void setPersoCache(Integer lieu, List<Integer> lp) {
		partie.setPersoCache(lieu,lp.size());
		
	}

	public void resetPersoCache() {
		partie.resetPersoCache();
		
	}

	public void sacrifice(PionCouleur value) {
		switch (value) {    
			case N7:
				partie.sacrifie(Couleur.NOIR, 7);
				break;
			case N5:   
				partie.sacrifie(Couleur.NOIR, 5);
				break;
			case N3:
				partie.sacrifie(Couleur.NOIR, 3);
				break;
			case N1:
				partie.sacrifie(Couleur.NOIR, 1);
				break;
			case V7:
				partie.sacrifie(Couleur.VERT, 7);
				break;
			case V5:
				partie.sacrifie(Couleur.VERT, 5);
				break;
			case V3:
				partie.sacrifie(Couleur.VERT, 3);
				break;
			case V1:
				partie.sacrifie(Couleur.VERT, 1);
				break;
			case B7:
				partie.sacrifie(Couleur.BLEU, 7);
				break;
			case B5:
				partie.sacrifie(Couleur.BLEU, 5);
				break;
			case B3: 
				partie.sacrifie(Couleur.BLEU, 3);
				break;
			case B1:
				partie.sacrifie(Couleur.BLEU, 1);
				break;
			case R7:  
				partie.sacrifie(Couleur.ROUGE, 7);
				break;
			case R5: 
				partie.sacrifie(Couleur.ROUGE, 5);
				break;
			case R3: 
				partie.sacrifie(Couleur.ROUGE, 3);
				break;
			case R1:
				partie.sacrifie(Couleur.ROUGE, 1);
				break;
			case J7: 
				partie.sacrifie(Couleur.JAUNE, 7);
				break;
			case J5:
				partie.sacrifie(Couleur.JAUNE, 5);
				break;
			case J3:  
				partie.sacrifie(Couleur.JAUNE, 3);
				break;
			case J1:
				partie.sacrifie(Couleur.JAUNE, 1);
				break;
			case M7:
				partie.sacrifie(Couleur.MARRON, 7);
				break;
			case M5:
				partie.sacrifie(Couleur.MARRON, 5);
				break;
			case M3:  
				partie.sacrifie(Couleur.MARRON, 3);
				break;
			case M1:
				partie.sacrifie(Couleur.MARRON, 1);
				break;
			default:
				break;
				
		}
			
		
	}

	public String getEtatPartie() {
		return partie.getEtatPartie();
	}

	
	
	
	
}
