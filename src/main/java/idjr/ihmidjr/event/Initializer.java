package idjr.ihmidjr.event;

import java.util.ArrayList;
import java.util.List;

import idjr.PartieInfo;
import reseau.type.CarteType;
import reseau.type.Couleur;

public abstract class Initializer {
	private static final List<IJeuListener> listenersjl = new ArrayList<>();
	private static final List<IFinListener> listenersfl = new ArrayList<>();
	private static final List<IConfigListener> listenerscl = new ArrayList<>();
	private static final List<IAttenteListener> listenersal = new ArrayList<>();
	private static final List<IRotationListener> listenersrl = new ArrayList<>();
	private static final List<IPleineEcranListener> listenerspel = new ArrayList<>();

	public static void addListener(IJeuListener toAdd) {
		listenersjl.add(toAdd);
	}

	public static void addListener(IFinListener toAdd) {
		listenersfl.add(toAdd);
	}

	public static void addListener(IConfigListener toAdd) {
		listenerscl.add(toAdd);
	}

	public static void addListener(IAttenteListener toAdd) {
		listenersal.add(toAdd);
	}
	
	public static void addListener(IRotationListener toAdd) {
		listenersrl.add(toAdd);
	}
	
	public static void addListener(IPleineEcranListener toAdd) {
		listenerspel.add(toAdd);
	}

	public static void partieValide(String nom) {
		for (IConfigListener cl : listenerscl)
			cl.partieValide(nom);
	}

	public static void choisirPion(List<Integer> list) {
		for (IJeuListener jl : listenersjl)
			jl.choisirPion(list);
	}

	public static void choisirLieu(List<Integer> list) {
		for (IJeuListener jl : listenersjl)
			jl.choisirLieu(list);
	}

	public static void desValeur(List<Integer> list) {
		for (IJeuListener jl : listenersjl)
			jl.desValeur(list);
	}

	public static void nomJoueur(String nom) {
		for (IJeuListener jl : listenersjl)
			jl.nomJoueur(nom);
	}

	public static void nomPhase(String nom) {
		for (IJeuListener jl : listenersjl)
			jl.nomPhase(nom);
	}

	public static void stopWait() {
		for (IAttenteListener al : listenersal)
			al.stopWait();
	}

	public static void nomPartie(String nom) {
		for (IAttenteListener al : listenersal)
			al.nomPartie(nom);
	}

	public static void desVigiles(List<Integer> list) {
		for (IJeuListener jl : listenersjl)
			jl.desVigiles(list);
	}

	public static void fin() {
		for (IJeuListener jl : listenersjl)
			jl.fin();
	}

	public static void gagnant(String nom) {
		for (IFinListener jl : listenersfl)
			jl.gagnant(nom);
	}

	public static void couleurJoueur(Couleur couleur) {
		for (IJeuListener jl : listenersjl)
			jl.couleurJoueur(couleur);
	}

	public static void sacrificeChange() {
		for (IJeuListener jl : listenersjl)
			jl.sacrificeChange();
	}

	public static void deplacementChange() {
		for (IJeuListener jl : listenersjl)
			jl.deplacementChange();
	}

	public static void choisirCarte(List<CarteType> listeCartes, List<Couleur> listeCouleurJoueurVivant, boolean garder,
			boolean donner, boolean defausser, boolean utiliser) {
		for (IJeuListener jl : listenersjl)
			jl.choisirCarte(listeCartes, listeCouleurJoueurVivant, garder, donner, defausser, utiliser);
	}

	public static void nomJoueurs(List<String> listeNomJoueur) {
		for (IJeuListener jl : listenersjl)
			jl.nomJoueurs(listeNomJoueur);
	}

	public static void choisirUtiliserCarte() {
		for (IJeuListener jl : listenersjl)
			jl.choisirUtiliserCarte();
	}

	public static void choisirUtiliserCarte(CarteType carteType) {
		for (IJeuListener jl : listenersjl)
			jl.choisirUtiliserCarte(carteType);
	}

	public static void choisirUtiliserCarte(List<CarteType> carteTypes) {
		for (IJeuListener jl : listenersjl)
			jl.choisirUtiliserCarte(carteTypes);
	}

	public static void updateCarte() {
		for (IJeuListener jl : listenersjl)
			jl.updateCarte();
	}

	public static void setVote(List<Couleur> joueur) {
		for (IJeuListener jl : listenersjl)
			jl.setVote(joueur);
	}

	public static void partie(List<PartieInfo> partieInfo) {
		for (IConfigListener cl : listenerscl)
			cl.partie(partieInfo);
	}

	public static void log(String action) {
		for (IJeuListener jl : listenersjl)
			jl.log(action);
	}

	public static void enleverDes() {
		for (IJeuListener jl : listenersjl)
			jl.enleverDes();
	}

	public static void choisirUtiliserCartes(List<CarteType> carteTypes) {
		for (IJeuListener jl : listenersjl)
			jl.choisirUtiliserCartes(carteTypes);
	}

	public static void updateRotation(int angle) {
		for (IRotationListener rl : listenersrl)
			rl.rotation(angle);
	}

	public static void updatePleineEcran() {
		for (IPleineEcranListener pel : listenerspel)
			pel.updatePleineEcran();
	}
}
