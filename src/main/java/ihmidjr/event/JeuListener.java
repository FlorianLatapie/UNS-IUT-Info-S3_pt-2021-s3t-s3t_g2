package ihmidjr.event;

import java.util.List;

import reseau.type.Couleur;

public interface JeuListener {
	void choisirPion(List<Integer> list);
	
	void choisirLieu(List<Integer> list);
	
	void desValeur(List<Integer> list);
	
	void nomJoueur(String nom);
	
	void nomPhase(String nom);
	
	void desVigiles(List<String> list);
	
	void fin();
	
	void couleurJoueur(Couleur couleur);

	void sacrificeChange();

	void deplacementChange();
}
