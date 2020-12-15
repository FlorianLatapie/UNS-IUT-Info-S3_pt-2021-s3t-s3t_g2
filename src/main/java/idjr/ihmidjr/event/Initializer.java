package idjr.ihmidjr.event;

import java.util.ArrayList;
import java.util.List;

import idjr.PartieInfo;
import reseau.type.CarteType;
import reseau.type.Couleur;

public class Initializer {
	private final List<JeuListener> listenersjl = new ArrayList<>();
	private final List<FinListener> listenersfl = new ArrayList<>();
	private final List<ConfigListener> listenerscl = new ArrayList<>();
	private final List<AttenteListener> listenersal = new ArrayList<>();

	public void addListenerJeu(JeuListener toAdd) {
		listenersjl.add(toAdd);
	}

	public void addListenerFin(FinListener toAdd) {
		listenersfl.add(toAdd);
	}

	public void addListenerConfig(ConfigListener toAdd) {
		listenerscl.add(toAdd);
	}

	public void addListenerAttente(AttenteListener toAdd) {
		listenersal.add(toAdd);
	}

	public void partieValide(String nom) {
		for (ConfigListener cl : listenerscl)
			cl.partieValide(nom);
	}

	public void choisirPion(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.choisirPion(list);
	}

	public void choisirLieu(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.choisirLieu(list);
	}

	public void desValeur(List<Integer> list) {
		for (JeuListener jl : listenersjl)
			jl.desValeur(list);
	}

	public void nomJoueur(String nom) {
		for (JeuListener jl : listenersjl)
			jl.nomJoueur(nom);
	}

	public void nomPhase(String nom) {
		for (JeuListener jl : listenersjl)
			jl.nomPhase(nom);
	}

	public void stopWait() {
		for (AttenteListener al : listenersal)
			al.stopWait();
	}

	public void nomPartie(String nom) {
		for (AttenteListener al : listenersal)
			al.nomPartie(nom);
	}

	public void desVigiles(List<String> list) {
		for (JeuListener jl : listenersjl)
			jl.desVigiles(list);
	}

	public void fin() {
		for (JeuListener jl : listenersjl)
			jl.fin();
	}

	public void gagnant(String nom) {
		for (FinListener jl : listenersfl)
			jl.gagnant(nom);
	}

	public void couleurJoueur(Couleur couleur) {
		for (JeuListener jl : listenersjl)
			jl.couleurJoueur(couleur);
	}

	public void sacrificeChange() {
		for (JeuListener jl : listenersjl)
			jl.sacrificeChange();
	}

	public void deplacementChange() {
		for (JeuListener jl : listenersjl)
			jl.deplacementChange();
	}

	public void choisirCarte(List<CarteType> listeCartes, List<Couleur> listeCouleurJoueurVivant, boolean garder,
			boolean donner, boolean defausser, boolean utiliser) {
		for (JeuListener jl : listenersjl)
			jl.choisirCarte(listeCartes, listeCouleurJoueurVivant, garder, donner, defausser, utiliser);
	}

	public void nomJoueurs(List<String> listeNomJoueur) {
		for (JeuListener jl : listenersjl)
			jl.nomJoueurs(listeNomJoueur);
	}

	public void choisirUtiliserCarte() {
		for (JeuListener jl : listenersjl)
			jl.choisirUtiliserCarte();
	}

	public void choisirUtiliserCarte(CarteType carteType) {
		for (JeuListener jl : listenersjl)
			jl.choisirUtiliserCarte(carteType);
	}

	public void choisirUtiliserCarte(List<CarteType> carteTypes) {
		for (JeuListener jl : listenersjl)
			jl.choisirUtiliserCarte(carteTypes);
	}

	public void updateCarte() {
		for (JeuListener jl : listenersjl)
			jl.updateCarte();
	}

	public void setVote(List<Couleur> joueur) {
		for (JeuListener jl : listenersjl)
			jl.setVote(joueur);
	}

	public void partie(List<PartieInfo> partieInfo) {
		for (ConfigListener cl : listenerscl)
			cl.partie(partieInfo);
	}

	public void log(String action) {
		for (JeuListener jl : listenersjl)
			jl.log(action);
	}

	public void enleverDes() {
		for (JeuListener jl : listenersjl)
			jl.enleverDes();
	}
}
