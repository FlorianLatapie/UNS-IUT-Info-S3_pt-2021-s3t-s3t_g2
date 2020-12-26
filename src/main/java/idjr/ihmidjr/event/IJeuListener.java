package idjr.ihmidjr.event;

import java.util.List;

import reseau.type.CarteType;
import reseau.type.Couleur;

public interface IJeuListener {
	void choisirPion(List<Integer> list);

	void choisirLieu(List<Integer> list);

	void desValeur(List<Integer> list);

	void nomJoueur(String nom);

	void nomPhase(String nom);

	void desVigiles(List<Integer> list);

	void fin();

	void couleurJoueur(Couleur couleur);

	void sacrificeChange();

	void deplacementChange();

	void choisirCarte(List<CarteType> listeCartes, List<Couleur> listeCouleurJoueurVivant, boolean garder,
			boolean donner, boolean defausser);

	void nomJoueurs(List<String> listeNomJoueur);

	void choisirUtiliserCarte(CarteType carteType);

	void choisirUtiliserCarte(List<CarteType> carteTypes);

	void updateCarte();

	void setVote(List<Couleur> listeCouleurJoueur);

	void log(String action);

	void enleverDes();

	void choisirUtiliserCartes(List<CarteType> carteTypes);
}
