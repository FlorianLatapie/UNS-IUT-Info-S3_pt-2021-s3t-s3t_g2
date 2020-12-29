package pp.reseau;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import reseau.socket.ControleurReseau;
import reseau.type.CarteType;
import reseau.type.VigileEtat;

/**
 * <h1>La classe ArriveZombieReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase d'arrivée des zombies
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class ArriveZombieReseau {

	/**
	 * Traitement du paquet PAZ du protocole reseau.
	 * 
	 * @param jeu        La partie courante
	 * @param ve         Etat du chef des vigiles
	 * @param partieID   L'identifiant de la partie en cours
	 * @param numeroTour Le numéro du tour courant
	 */
	public void debutArriveZombie(Partie jeu, VigileEtat ve, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("PAZ", jeu.getChefVIgile().getCouleur(), ve, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet AZLD du protocole reseau.
	 * 
	 * @param jeu La partie courante
	 */
	public void lanceDes(Partie jeu) {
		jeu.getChefVIgile().getConnection().attendreMessage("AZLD");
		jeu.getChefVIgile().getConnection().getMessage("AZLD");
	}

	/**
	 * Traitement du paquet AZLAZ du protocole reseau.
	 * 
	 * @param jeu        La partie courante
	 * @param lieuZombie Liste des lieux d'arrivés des zombies
	 * @param partieID   L'identifiant de la partie en cours
	 * @param numeroTour Le numéro du tour courant
	 */
	public void informeArriveZombie(Partie jeu, List<Integer> lieuZombie, String partieId, int numeroTour) {
		jeu.getChefVIgile().getConnection()
				.envoyer(ControleurReseau.construirePaquetTcp("AZLAZ", lieuZombie, partieId, numeroTour));
	}

	/**
	 * Traitement du paquet AZDCS du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void demanderCDS(Partie jeu, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurs().values())
			if (j.getCartes().contains(CarteType.CDS))
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZDCS", partieId, numeroTour));
	}

	/**
	 * Traitement du paquet AZRCS du protocole reseau.
	 * 
	 * @param jeu La partie courante.
	 * 
	 * @return Liste des joueurs ayant utilisé la carte caméra de sécurité.
	 */
	public List<Joueur> reponseCDS(Partie jeu) {
		List<Joueur> joueurCDS = new ArrayList<>();
		for (Joueur j : jeu.getJoueurs().values())
			if (j.getCartes().contains(CarteType.CDS)) {
				j.getConnection().attendreMessage("AZRCS");
				String mess = j.getConnection().getMessage("AZRCS");
				if ((CarteType) ControleurReseau.getValueTcp("AZRCS", mess, 1) != CarteType.NUL)
					joueurCDS.add(j);
			}
		return joueurCDS;
	}

	/**
	 * Traitement du paquet AZUCS du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param lieuZombie Liste des lieux d'arrivées des zombies.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informeArriveZombieCDS(Partie jeu, Joueur j, List<Integer> lieuZombie, String partieId,
			int numeroTour) {
		j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZUCS", lieuZombie, partieId, numeroTour));
	}

	/**
	 * Traitement du paquet AZICS du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param joueurCDS  liste des joueurs jouant une carte caméra de sécurité.
	 * @param lieuZombie Liste des lieux d'arrivés des zombies.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
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
