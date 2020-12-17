package pp.reseau;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.CarteType;
import reseau.type.VigileEtat;

public class ArriveZombieReseau {

	public void debutArriveZombie(Partie jeu, VigileEtat ve, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PAZ", jeu.getChefVIgile().getCouleur(), ve, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	public void lanceDes(Partie jeu) {
		jeu.getChefVIgile().getConnection().attendreMessage("AZLD");
		jeu.getChefVIgile().getConnection().getMessage("AZLD");
	}

	public void informeArriveZombie(Partie jeu, List<Integer> lieuZombie, String partieId, int numeroTour) {
		jeu.getChefVIgile().getConnection()
				.envoyer(ControleurReseau.construirePaquetTcp("AZLAZ", lieuZombie, partieId, numeroTour));
	}

	public void demanderCDS(Partie jeu, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values())
			if (j.getCartes().contains(CarteType.CDS))
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZDCS", partieId, numeroTour));
	}

	public List<Joueur> reponseCDS(Partie jeu) {
		List<Joueur> joueurCDS = new ArrayList();
		for (Joueur j : jeu.getJoueurs().values())
			if (j.getCartes().contains(CarteType.CDS)) {
				j.getConnection().attendreMessage("AZRCS");
				String mess = j.getConnection().getMessage("AZRCS");
				if ((CarteType) ControleurReseau.getValueTcp("AZRCS", mess, 1) != CarteType.NUL)
					joueurCDS.add(j);
			}
		return joueurCDS;
	}

	public void informeArriveZombieCDS(Partie jeu, Joueur j, List<Integer> lieuZombie, String partieId,
			int numeroTour) {
		j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZUCS", lieuZombie, partieId, numeroTour));
	}

	public void informerUtilisationCDS(Partie jeu, List<Joueur> joueurCDS, List<Integer> lieuZombie, String partieId,
			int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values()) {
			if (joueurCDS.contains(j))
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZICS", j.getCouleur(), CarteType.CDS,
						partieId, numeroTour));
			else
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZICS", j.getCouleur(), CarteType.NUL,
						partieId, numeroTour));
		}
	}

}
