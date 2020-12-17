package pp.ihm.event;

import java.util.List;

import pp.Joueur;

public interface IAttenteListener {
	void joueurPret();

	void nomPartie(String id, String nom);

	void updateJoueurs(List<Joueur> joueurs, int max);
}
