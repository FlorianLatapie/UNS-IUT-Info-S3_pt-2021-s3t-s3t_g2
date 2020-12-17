package pp.reseau;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.VigileEtat;

public class ChoixDestinationReseau {

	public void debuterPhaseChoixDestination(Partie jeu, VigileEtat ve, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PCD", jeu.getChefVIgile().getCouleur(), ve, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	public int indiqueChoixDestChef(Partie jeu) {
		jeu.getChefVIgile().getConnection().attendreMessage("CDDCV");
		String m = jeu.getChefVIgile().getConnection().getMessage("CDDCV");
		return (int) ControleurReseau.getValueTcp("CDDCV", m, 1);
	}

	public void informerDestChefVigile(Partie jeu, int dest, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("CDCDV", jeu.getChefVIgile().getCouleur(), dest, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			if (!(j.isChefDesVigiles()))
				j.getConnection().envoyer(m);
	}

	public int indiqueChoixDest(Joueur j) {
		j.getConnection().attendreMessage("CDDJ");
		String rep = j.getConnection().getMessage("CDDJ");
		return (int)ControleurReseau.getValueTcp("CDDJ", rep, 1);
	}
	
	public void cloreChoiXDest(Partie jeu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("CDFC", partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}
	
	public void choisirDestZombieVenger(Joueur j, String partieId, int numeroTour){
		String m = ControleurReseau.construirePaquetTcp("CDZVI", partieId, numeroTour);
		j.getConnection().envoyer(m);
	}
	
	public int indiquerChoixDestZombieVenger(Joueur j){
		j.getConnection().attendreMessage("CDDZVJE");
		String rep = j.getConnection().getMessage("CDDZVJE");
		return (int) ControleurReseau.getValueTcp("CDDZVJE", rep, 1);
	}
	
	
	public void informerChoixDestZombieVenger(Partie jeu, Joueur j, int dvz, String partieId, int numeroTour) {
		for (Joueur j2 : jeu.getJoueurs().values()) {
			j2.getConnection().envoyer(
					ControleurReseau.construirePaquetTcp("CDZVDI", j.getCouleur(), dvz, partieId, numeroTour));
		}
	}
	

}
