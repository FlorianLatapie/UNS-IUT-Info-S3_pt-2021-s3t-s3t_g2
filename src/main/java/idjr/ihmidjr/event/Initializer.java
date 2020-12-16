package idjr.ihmidjr.event;

import java.util.ArrayList;
import java.util.List;

import idjr.PartieInfo;
import reseau.type.CarteType;
import reseau.type.Couleur;

public abstract class Initializer {
	private static final List<JeuListener> listenersjl = new ArrayList<>();
	private static final List<FinListener> listenersfl = new ArrayList<>();
	private static final List<ConfigListener> listenerscl = new ArrayList<>();
	private static final List<AttenteListener> listenersal = new ArrayList<>();

	public static void addListenerJeu(JeuListener toAdd) {
		listenersjl.add(toAdd);
	}

	public static void addListenerFin(FinListener toAdd) {
		listenersfl.add(toAdd);
	}

	public static void addListenerConfig(ConfigListener toAdd) {
		listenerscl.add(toAdd);
	}

	public static void addListenerAttente(AttenteListener toAdd) {
		listenersal.add(toAdd);
	}

	public static void partieValide(String nom) {
		for (ConfigListener cl : listenerscl)
			cl.partieValide(nom);
	}

	public static void choisirPion(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.choisirPion(list);
	}

	public static void choisirLieu(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.choisirLieu(list);
	}

	public static void desValeur(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.desValeur(list);
	}

	public static void nomJoueur(String nom) {
		for (JeuListener jl : listenersjl)
			jl.nomJoueur(nom);
	}

	public static void nomPhase(String nom) {
		for (JeuListener jl : listenersjl)
			jl.nomPhase(nom);
	}

	public static void stopWait() {
		for (AttenteListener al : listenersal)
			al.stopWait();
	}

	public static void nomPartie(String nom) {
		for (AttenteListener al : listenersal)
			al.nomPartie(nom);
	}

	public static void desVigiles(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.desVigiles(list);
	}

	public static void fin() {
		for (JeuListener jl : listenersjl)
			jl.fin();
	}

	public static void gagnant(String nom) {
		for (FinListener jl : listenersfl)
			jl.gagnant(nom);
	}

	public static void couleurJoueur(Couleur couleur) {
		for (JeuListener jl : listenersjl)
			jl.couleurJoueur(couleur);
	}

	public static void sacrificeChange() {
		for (JeuListener jl : listenersjl)
			jl.sacrificeChange();
	}

	public static void deplacementChange() {
		for (JeuListener jl : listenersjl)
			jl.deplacementChange();
	}

	public static void choisirCarte(List<CarteType> listeCartes, List<Couleur> listeCouleurJoueurVivant, boolean garder,
			boolean donner, boolean defausser, boolean utiliser) {
		for (JeuListener jl : listenersjl)
			jl.choisirCarte(listeCartes, listeCouleurJoueurVivant, garder, donner, defausser, utiliser);
	}

	public static void nomJoueurs(List<String> listeNomJoueur) {
		for (JeuListener jl : listenersjl)
			jl.nomJoueurs(listeNomJoueur);
	}

	public static void choisirUtiliserCarte() {
		for (JeuListener jl : listenersjl)
			jl.choisirUtiliserCarte();
	}

	public static void choisirUtiliserCarte(CarteType carteType) {
		for (JeuListener jl : listenersjl)
			jl.choisirUtiliserCarte(carteType);
	}

	public static void choisirUtiliserCarte(List<CarteType> carteTypes) {
		for (JeuListener jl : listenersjl)
			jl.choisirUtiliserCarte(carteTypes);
	}

	public static void updateCarte() {
		for (JeuListener jl : listenersjl)
			jl.updateCarte();
	}

	public static void setVote(List<Couleur> joueur) {
		for (JeuListener jl : listenersjl)
			jl.setVote(joueur);
	}

	public static void partie(List<PartieInfo> partieInfo) {
		for (ConfigListener cl : listenerscl)
			cl.partie(partieInfo);
	}

	public static void log(String action) {
		for (JeuListener jl : listenersjl)
			jl.log(action);
	}

	public static void enleverDes() {
		for (JeuListener jl : listenersjl)
			jl.enleverDes();
	}
}
