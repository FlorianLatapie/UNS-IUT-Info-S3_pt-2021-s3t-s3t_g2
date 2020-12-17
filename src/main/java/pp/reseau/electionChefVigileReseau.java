package pp.reseau;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.Couleur;

public class electionChefVigileReseau {
	
	public void debutElectionChefVigile(Partie jeu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PECV", jeu.getPersosLieu(5), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}
	
	public void informerElectionChefVigile(Partie jeu, Couleur c, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RECV", c, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

}
