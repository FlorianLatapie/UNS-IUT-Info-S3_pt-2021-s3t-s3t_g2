package bot;

import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.ConnexionType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.TypeJoueur;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import bot.partie.Joueur;
import bot.partie.Lieu;
import bot.partie.Partie;

public class Bot {
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
	private boolean estFini;
	private int compteurTour;
	private List<CarteType> listeCarte;
	private List<PionCouleur> listePion;
	private List<Couleur> couleurJoueursPresent;
	private List<Couleur> joueurEnVie;
	private HashMap<Couleur,Integer> joueursVotantPresent;
	private VoteType voteType;
	private Partie partie;
	private BotType botType;

	

	/* Parametre Temporaire */
	private List<Integer> pionAPos;

	public Bot(int delay,BotType botType) {
		this.delay = delay;
		this.botType=botType;
	}

	public void start() throws IOException {
		initBot();
		initReseau();

		while (!isEstFini()) {
			Thread.yield();
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
		ControleurReseau.initConnexion(traitementPaquetTcp,traitementPaquetUdp,connexionType, ReseauOutils.getLocalIp());
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

	public List<PionCouleur> getPionSacrDispo() {
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
	
	public void newTour() {
		this.compteurTour ++;
	}
	
	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public void initPartie(List<Couleur> couleurs) {
		partie = new Partie(couleurs);
	}

	public void initCarte(CarteType value) {
		partie.getJoueurs().get(couleur).getCartes().add(value);
		partie.getCartes().remove(value);
		for (Joueur j : partie.getJoueurs().values()) {
			if ((partie.getCartes().size() > 0) && (!j.getCouleur().equals(couleur))) {
				int rand = new Random().nextInt(partie.getCartes().size());
				if (rand == 0)
					rand = 0;
				CarteType c = partie.getCartes().get(rand);
				partie.getCartes().remove(c);
				partie.getJoueurs().get(j.getCouleur()).getCartes().add(c);
			}
		}
	}

	public void placePion(int dest, int pion) {
		partie.placePerso(couleur, pion, dest);
	}

	public void placePionCouleur(Couleur value, int dest, int pion) {
		partie.placePerso(value, pion, dest);

	}

	public void deplacePion(int dest, int pion) {
		partie.deplacePerso(couleur, pion, dest);
	}

	public void deplacePionCouleur(Couleur c, int dest, int pion) {
		partie.deplacePerso(c, pion, dest);
	}

	public void initZombie(List<Integer> listeIndexLieux) {

		partie.entreZombie(listeIndexLieux);
	}

	public void carteCamion(List<Object> carteChoisies) {
		CarteType c1 = (CarteType) carteChoisies.get(0);
		CarteType c2 = (CarteType) carteChoisies.get(1);
		CarteType c3 = (CarteType) carteChoisies.get(2);
		Couleur couleurJC = (Couleur) carteChoisies.get(3);
		if (partie.getCartes().contains(c1)) {
			partie.givecarte(couleur, c1);
		} else if (partie.getCartes().size() > 0) {
			int rand = new Random().nextInt(partie.getCartes().size());
			if (rand == 0)
				rand = 0;
			partie.givecarte(couleur, partie.getCartes().get(rand));
		}

		if (partie.getCartes().contains(c2)) {
			partie.givecarte(couleurJC, c2);
		} else if (partie.getCartes().size() > 0) {
			int rand = new Random().nextInt(partie.getCartes().size());
			if (rand == 0)
				rand = 0;
			partie.givecarte(couleurJC, partie.getCartes().get(rand));
		}
		if (partie.getCartes().contains(c3)) {
			partie.getCartes().remove(c3);
		} else if (partie.getCartes().size() > 0) {
			int rand = new Random().nextInt(partie.getCartes().size());
			if (rand == 0)
				rand = 0;
			partie.getCartes().remove(partie.getCartes().get(rand));
		}

	}

	public void resFouille(Couleur cjg, Couleur cjo, CarteEtat cd) {
		if (partie.getCartes().size() > 0) {
			int rand = new Random().nextInt(partie.getCartes().size());
			if (rand == 0)
				rand = 0;

			if (!(cjg.equals(Couleur.NUL)) && (!cjg.equals(couleur))) {
				partie.givecarte(cjg, partie.getCartes().get(rand));
			}
		}
		if (partie.getCartes().size() > 0) {
			int rand2 = new Random().nextInt(partie.getCartes().size());
			if (rand2 == 0)
				rand2 = 0;
			if (!(cjg.equals(Couleur.NUL)) && (!cjo.equals(couleur))) {
				partie.givecarte(cjo, partie.getCartes().get(rand2));
			}
		}
		if (partie.getCartes().size() > 0) {
			int rand3 = new Random().nextInt(partie.getCartes().size());
			if (rand3 == 0)
				rand3 = 0;
			if (cd.equals(CarteEtat.CD)) {
				partie.getCartes().remove(partie.getCartes().get(rand3));
			}
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

	public void arriveZombie(List<Integer> arr) {
		partie.entreZombie(arr);

	}

	public void joueCarte(Couleur value, CarteType carte) {
		if (partie.getJoueurs().get(value).getCartes().contains(carte)) {
			partie.getJoueurs().get(value).getCartes().remove(carte);
		} else if ((partie.getCartes().contains(carte))&& (partie.getJoueurs().get(value).getCartes().size() > 0))  {
			partie.getCartes().remove(carte);
			int rand = new Random().nextInt(partie.getJoueurs().get(value).getCartes().size());
			if (rand == 0)
				rand = 0;
			CarteType c = partie.getJoueurs().get(value).getCartes().get(rand);
			partie.getCartes().add(c);
			partie.getJoueurs().get(value).getCartes().remove(c);

		} else if (partie.getJoueurs().get(value).getCartes().size() > 0) {
			for (Couleur c : partie.getJoueursCouleurs()) {
				if (partie.getJoueurs().get(c).getCartes().contains(carte)) {
					partie.getJoueurs().get(c).getCartes().remove(carte);
					int rand = new Random().nextInt(partie.getJoueurs().get(value).getCartes().size());
					if (rand == 0)
						rand = 0;
					CarteType ca = partie.getJoueurs().get(value).getCartes().get(rand);
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

	public void deplPionJoueurCourant(Couleur couleur, Integer dest, Integer pion) {
		partie.deplacePerso(couleur, pion, dest);

	}

	public void setZombie(List<Integer> li, List<Integer> lz) {
		for (int i = 0; i < li.size(); i++) {
			partie.getLieux().get(li.get(i)).setNbZombies(lz.get(i));
		}
		for (Lieu l : partie.getLieux().values()) {
			if (!li.contains(l.getNum())) {
				l.setOuvert(false);
			}
		}

	}



	public void correctionZombie(Integer lieu, Integer nbz) {
		partie.setZombieSurLieu(lieu, nbz);

	}

	public void joueCartes(Couleur value, List<CarteType> value2) {
		for (CarteType c : value2) {
			joueCarte(value, c);
		}

	}

	public void setPersoCache(Integer lieu, List<Integer> lp) {
		partie.setPersoCache(lieu, lp.size());

	}

	public void resetPersoCache() {
		partie.resetPersoCache();

	}

	public void sacrifice(PionCouleur pc) {
		partie.sacrifie(IdjrTools.getCouleurByChar(pc), IdjrTools.getPionByValue(pc));
	}

	public String getEtatPartie() {
		return partie.getEtatPartie();
	}

	public BotType getBotType() {
		return botType;
	}

	public void setBotType(BotType botType) {
		this.botType = botType;
	}

	public HashMap<Couleur,Integer> getJoueursVotantPresent() {
		return joueursVotantPresent;
	}

	public void setJoueursVotantPresent(HashMap<Couleur,Integer> joueursVotant) {
		this.joueursVotantPresent = joueursVotant;
	}



}
