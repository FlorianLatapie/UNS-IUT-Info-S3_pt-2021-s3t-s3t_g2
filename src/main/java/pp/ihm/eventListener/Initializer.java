package pp.ihm.eventListener;

import pp.Joueur;
import pp.Lieu;
import pp.Personnage;
import reseau.type.Couleur;

import java.util.ArrayList;
import java.util.List;

public class Initializer {
	private static List<PlateauListener> listenerspl = new ArrayList<>();
	private static List<AttenteListener> listenersal = new ArrayList<>();
	private static List<FinListener> listenersfl = new ArrayList<>();
	private static List<CouleurListener> listenerscl = new ArrayList<>();

	public static void addListener(PlateauListener toAdd) {
		listenerspl.add(toAdd);
	}

	public static void addListener(AttenteListener toAdd) {
		listenersal.add(toAdd);
	}

	public static void addListener(FinListener toAdd) {
		listenersfl.add(toAdd);
	}

	public static void addListener(CouleurListener toAdd) {
		listenerscl.add(toAdd);
	}

	public static void nbZombiesLieuAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			nbZombiesLieu(l.getNum(), l.getNbZombies());
	}

	private static void nbZombiesLieu(int lieu, int val) {
		for (PlateauListener pl : listenerspl)
			pl.nbLieuZombie(lieu, val);
	}

	public static void nomJoueurAll(List<Joueur> joueurs) {
		for (Joueur l : joueurs)
			nomJoueur(l.getJoueurIdint(), l.getCouleur(), l.getNom());
	}

	private static void nomJoueur(int joueur, Couleur couleur, String val) {
		for (PlateauListener pl : listenerspl)
			pl.nomJoueur(joueur, couleur, val);
	}

	public static void lieuFermeAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			lieuFerme(l.getNum(), !l.isOuvert());
	}

	private static void lieuFerme(int lieu, boolean val) {
		for (PlateauListener pl : listenerspl)
			pl.lieuFerme(lieu, val);
	}

	public static void lieuOuvertAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			lieuOuvert(l.getNum(), l.isOuvert());
	}

	private static void lieuOuvert(int lieu, boolean val) {
		for (PlateauListener pl : listenerspl)
			pl.lieuOuvert(lieu, val);
	}

	public static void nbPersoJoueurAll(List<Joueur> joueurs) {
		for (Joueur j : joueurs)
			nbPersoJoueur(j.getJoueurIdint(), j.getPersonnages().size());
	}

	private static void nbPersoJoueur(int joueur, int persoNb) {
		for (PlateauListener pl : listenerspl)
			pl.nbPersoJoueur(joueur, persoNb);
	}

	public static void nbCarteJoueurAll(List<Joueur> joueurs) {
		for (Joueur l : joueurs)
			nbCarteJoueur(l.getJoueurIdint(), l.getCartes().size());
	}

	private static void nbCarteJoueur(int joueur, int carteNb) {
		for (PlateauListener pl : listenerspl)
			pl.nbCarteJoueur(joueur, carteNb);
	}

	/*
	 * public void forceLieuAll(List<Lieu> lieux) { for (Lieu l : lieux)
	 * forceLieu(l.getNum(), l.getForce()); }
	 * 
	 * private void forceLieu(int lieu, int val) { for (PlateauListener pl :
	 * listenerspl) pl.forceLieu(lieu, val); }
	 */

	public static void finPartie() {
		for (PlateauListener pl : listenerspl)
			pl.finPartie();
	}

	public static void joueurPret() {
		for (AttenteListener al : listenersal)
			al.joueurPret();
	}

	public static void getGagnant(Joueur joueur) {
		for (FinListener fl : listenersfl)
			fl.getGagnant(joueur.getNom());
	}

	public static void nomChefVigileAll(List<Joueur> joueurs) {
		for (Joueur l : joueurs)
			if (l.isChefDesVigiles())
				nomChefVigile(l.getNom());
	}

	private static void nomChefVigile(String joueur) {
		for (PlateauListener pl : listenerspl)
			pl.nomChefVigile(joueur);
	}

	public static void destionationPersoAll(List<Lieu> lieux) {
		for (Lieu l : lieux)
			destionationPerso(l);
	}

	private static void destionationPerso(Lieu lieu) {
		String np = "";
		for (Personnage p : lieu.getPersonnage())
			np += p.getType().name() + " " + p.getJoueur().getCouleur().nomEntier() + "\n";
		for (PlateauListener pl : listenerspl)
			pl.destionationPerso(lieu.getNum(), np);
	}

	public static void fouilleCamion(String camion) {
		for (PlateauListener pl : listenerspl)
			pl.fouilleCamion(camion);
	}

	public static void electionChef(String message) {
		for (PlateauListener pl : listenerspl)
			pl.electionChef(message);
	}

	public static void prevenirDeplacementVigile(String depvig) {
		for (PlateauListener pl : listenerspl)
			pl.prevenirDeplacementVigile(depvig);
	}

	public static void nomPartie(String nom) {
		for (AttenteListener al : listenersal)
			al.nomPartie(nom);
	}

	public static void nomJoueurs(List<Joueur> joueurs) {
		for (CouleurListener cl : listenerscl)
			cl.joueurNoms(joueurs);
	}
}
