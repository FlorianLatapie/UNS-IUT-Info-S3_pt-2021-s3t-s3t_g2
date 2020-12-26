package pp.reseau;

import java.util.ArrayList;
import java.util.List;
import pp.Joueur;
import pp.Lieu;
import pp.Partie;
import pp.PpTools;
import reseau.socket.ControleurReseau;
import reseau.type.CarteType;
import reseau.type.PionCouleur;
import reseau.type.RaisonType;

/**
 * <h1>La classe AttaqueZombieReseau</h1>. A pour rôle de traiter les paquets
 * réseaux de la phase d'attaque des zombies
 *
 * @author Aurelien
 * @version 1
 * @since 12/12/2020
 */
public class AttaqueZombieReseau {

	/**
	 * Traitement du paquet PRAZ du protocole reseau.
	 * 
	 * @param jeu        La partie courante
	 * @param nb         Liste des 2 lieux attirants un zombie en plus
	 * @param partieID   L'identifiant de la partie en cours
	 * @param numeroTour Le numéro du tour courant
	 */
	public void debuteAttaque(Partie jeu, List<Integer> nb, String partieId, int numeroTour) {
		String me = ControleurReseau.construirePaquetTcp("PRAZ", nb.get(0), nb.get(1), jeu.getLieuxOuverts(),
				jeu.getNbZombieLieux(), jeu.getNbPionLieux(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(me);
	}

	/**
	 * Traitement du paquet RAZPA du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param r          Raison qu'il n'y est pas d'attaque.
	 * @param lieu       Lieu courant ou il n'y a pas d'attaque.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void indiquerPasAttaque(Partie jeu, RaisonType r, Lieu lieu, String partieId, int numeroTour) {
		String message = ControleurReseau.construirePaquetTcp("RAZPA", lieu.getNum(),
				PpTools.getPionsCouleurByPerso(lieu.getPersonnage()), r, lieu.getNbZombies(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(message);
	}

	/**
	 * Traitement du paquet RAZA du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param lieu       Lieu courant ou il y a une attaque.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void indiquerAttaque(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		String message = ControleurReseau.construirePaquetTcp("RAZA", lieu.getNum(),
				PpTools.getPionsCouleurByPerso(lieu.getPersonnage()), lieu.getForce(), lieu.getNbZombies(), partieId,
				numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(message);
	}

	/**
	 * Traitement du paquet RAZDD du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param lieu       Lieu courant ou il y a une attaque
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void demanderDefense(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(lieu))
			j.getConnection()
					.envoyer(ControleurReseau.construirePaquetTcp("RAZDD", lieu.getNum(), partieId, numeroTour));
	}

	/**
	 * Traitement du paquet RAZRD du protocole reseau.
	 * 
	 * @param j Joueur courant.
	 *
	 * @return Liste d'une liste des carte joués et d'une liste des pion cachés
	 */
	public List<Object> recupDefense(Joueur j) {
		List<Object> lo = new ArrayList<>();
		j.getConnection().attendreMessage("RAZRD");
		String m = j.getConnection().getMessage("RAZRD");
		lo.add(ControleurReseau.getValueTcp("RAZRD", m, 1));
		lo.add(ControleurReseau.getValueTcp("RAZRD", m, 2));
		return lo;
	}

	/**
	 * Traitement du paquet RAZID du protocole reseau.
	 * 
	 * @param jeu             La partie courante.
	 * @param lieu            Lieu courant ou il y a une attaque.
	 * @param persoCacheTemp  List des personnage caché sur le lieu courant.
	 * @param nbCarteMateriel Nombre de cartes matériel joué.
	 * @param j               Joueur courant.
	 * @param carte           Liste des cartes joués par le joueur courant.
	 * @param partieID        L'identifiant de la partie en cours.
	 * @param numeroTour      Le numéro du tour courant.
	 */
	public void informerDefense(Partie jeu, Lieu lieu, List<Integer> persoCacheTemp, int nbCarteMateriel, Joueur j,
			List<CarteType> carte, String partieId, int numeroTour) {
		for (Joueur jou : jeu.getJoueurs().values()) {
			jou.getConnection()
					.envoyer(ControleurReseau.construirePaquetTcp("RAZID", lieu.getNum(), j.getCouleur(), carte,
							persoCacheTemp, lieu.getForce() + nbCarteMateriel, lieu.getNbZombies(), partieId,
							numeroTour));
		}
	}

	/**
	 * Traitement du paquet RAZDS du protocole reseau.
	 * 
	 * @param listePion  Liste des pion sacrifiable.
	 * @param jou        Joueur courant.
	 * @param lieu       lieu courant ou il y a une attaque.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void demanderSacrifice(List<Integer> listePion, Joueur jou, Lieu lieu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RAZDS", lieu.getNum(), listePion, partieId, numeroTour);
		jou.getConnection().envoyer(m);
	}

	/**
	 * Traitement du paquet RAZCS du protocole reseau.
	 * 
	 * @param jou Joueur courant.
	 * 
	 * @return Le pion sacrifié par le joueur courant.
	 */
	public PionCouleur recupSacrifice(Joueur jou) {
		jou.getConnection().attendreMessage("RAZCS");
		String rep = jou.getConnection().getMessage("RAZCS");
		return ((PionCouleur) ControleurReseau.getValueTcp("RAZCS", rep, 2));
	}

	/**
	 * Traitement du paquet RAZIF du protocole reseau.
	 * 
	 * @param jeu        La partie courante.
	 * @param lieu       Lieu courant ou il y a une attaque.
	 * @param pioncou    Pion sacrifié par le joueur courant.
	 * @param partieID   L'identifiant de la partie en cours.
	 * @param numeroTour Le numéro du tour courant.
	 */
	public void informerSacrifice(Partie jeu, Lieu lieu, PionCouleur pionCou, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RAZIF", lieu.getNum(), pionCou, lieu.getNbZombies(), partieId,
				numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(m);
	}
}
