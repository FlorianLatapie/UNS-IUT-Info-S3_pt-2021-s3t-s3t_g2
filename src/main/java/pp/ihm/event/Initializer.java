package pp.ihm.event;

import pp.Joueur;
import pp.Lieu;
import pp.Personnage;
import reseau.type.Couleur;

import java.util.ArrayList;
import java.util.List;

public abstract class Initializer {
	private static List<IPlateauListener> listenerspl = new ArrayList<>();
	private static List<IAttenteListener> listenersal = new ArrayList<>();
	private static List<IFinListener> listenersfl = new ArrayList<>();
	private static List<ICouleurListener> listenerscl = new ArrayList<>();
	private static List<IRotationListener> listenersrl = new ArrayList<>();
	private static List<IPleineEcranListener> listenerspel = new ArrayList<>();

	public static void addListener(IPlateauListener toAdd) {
		listenerspl.add(toAdd);
	}

	public static void addListener(IAttenteListener toAdd) {
		listenersal.add(toAdd);
	}

	public static void addListener(IFinListener toAdd) {
		listenersfl.add(toAdd);
	}

	public static void addListener(ICouleurListener toAdd) {
		listenerscl.add(toAdd);
	}

	public static void addListener(IRotationListener toAdd) {
		listenersrl.add(toAdd);
	}

	public static void addListener(IPleineEcranListener toAdd) {
		listenerspel.add(toAdd);
	}

	public static void nbZombiesLieuAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			nbZombiesLieu(l.getNum(), l.getNbZombies());
	}

	private static void nbZombiesLieu(int lieu, int val) {
		for (IPlateauListener pl : listenerspl)
			pl.nbLieuZombie(lieu, val);
	}

	public static void nomJoueurAll(List<Joueur> joueurs) {
		for (int i = 0; i < joueurs.size(); i++) {
			nomJoueur(i, joueurs.get(i).getCouleur(), joueurs.get(i).getNom());
		}
	}

	private static void nomJoueur(int joueur, Couleur couleur, String val) {
		for (IPlateauListener pl : listenerspl)
			pl.nomJoueur(joueur, couleur, val);
	}

	public static void lieuFermeAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			lieuFerme(l.getNum(), !l.isOuvert());
	}

	private static void lieuFerme(int lieu, boolean val) {
		for (IPlateauListener pl : listenerspl)
			pl.lieuFerme(lieu, val);
	}

	public static void lieuOuvertAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			lieuOuvert(l.getNum(), l.isOuvert());
	}

	private static void lieuOuvert(int lieu, boolean val) {
		for (IPlateauListener pl : listenerspl)
			pl.lieuOuvert(lieu, val);
	}

	public static void nbPersoJoueurAll(List<Joueur> joueurs) {
		for (Joueur j : joueurs)
			nbPersoJoueur(j.getJoueurIdint(), j.getPersonnages().size());
	}

	private static void nbPersoJoueur(int joueur, int persoNb) {
		for (IPlateauListener pl : listenerspl)
			pl.nbPersoJoueur(joueur, persoNb);
	}

	public static void nbCarteJoueurAll(List<Joueur> joueurs) {
		for (Joueur l : joueurs)
			nbCarteJoueur(l.getJoueurIdint(), l.getCartes().size());
	}

	private static void nbCarteJoueur(int joueur, int carteNb) {
		for (IPlateauListener pl : listenerspl)
			pl.nbCarteJoueur(joueur, carteNb);
	}

	public static void finPartie() {
		for (IPlateauListener pl : listenerspl)
			pl.finPartie();
	}

	public static void joueurPret() {
		for (IAttenteListener al : listenersal)
			al.joueurPret();
	}

	public static void getGagnant(Joueur joueur) {
		for (IFinListener fl : listenersfl)
			fl.getGagnant(joueur.getNom());
	}

	public static void nomChefVigileAll(List<Joueur> joueurs) {
		for (Joueur l : joueurs)
			if (l.isChefDesVigiles())
				nomChefVigile(l.getNom());
	}

	private static void nomChefVigile(String joueur) {
		for (IPlateauListener pl : listenerspl)
			pl.nomChefVigile(joueur);
	}

	public static void destionationPersoAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			destionationPerso(l.getNum(), l.getPersonnage());
	}

	private static void destionationPerso(int lieu, List<Personnage> personnages) {
		for (IPlateauListener pl : listenerspl)
			pl.destionationPerso(lieu, personnages);
	}

	public static void fouilleCamion(String camion) {
		for (IPlateauListener pl : listenerspl)
			pl.fouilleCamion(camion);
	}

	public static void electionChef(String message) {
		for (IPlateauListener pl : listenerspl)
			pl.electionChef(message);
	}

	public static void prevenirDeplacementVigile(String depvig) {
		for (IPlateauListener pl : listenerspl)
			pl.prevenirDeplacementVigile(depvig);
	}

	public static void nomPartie(String id, String nom) {
		for (IAttenteListener al : listenersal)
			al.nomPartie(id, nom);
	}

	public static void nomJoueurs(List<Joueur> joueurs) {
		for (ICouleurListener cl : listenerscl)
			cl.joueurNoms(joueurs);
	}

	public static void rotation(int angle) {
		for (IRotationListener cl : listenersrl)
			cl.rotation(angle);
	}

	public static void updatePleineEcran() {
		for (IPleineEcranListener pel : listenerspel)
			pel.updatePleineEcran();
	}

	public static void updateJoueurs(List<Joueur> joueurs, int max) {
		for (IAttenteListener al : listenersal)
			al.updateJoueurs(joueurs, max);
	}

	public static void quiJoue(int val) {
		for (IPlateauListener pl : listenerspl)
			pl.quiJoue(val);
	}
	
	public static void nbPlaceLieuAll(List<Lieu> lieux) {
		for (IPlateauListener pl : listenerspl)
			for (Lieu lieu2 : lieux) {
				nbPlaceLieu(lieu2.getNum(),lieu2.getPersonnage().size());
			}
	}
	
	private static void nbPlaceLieu(int lieu, int val) {
		for (IPlateauListener pl : listenerspl)
			pl.nbPlaceLieu(lieu, val);
	}
}
