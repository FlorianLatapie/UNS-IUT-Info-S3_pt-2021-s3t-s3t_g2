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

	public void debuteAttaque(Partie jeu, List<Integer> nb, String partieId, int numeroTour) {
		String me = ControleurReseau.construirePaquetTcp("PRAZ", nb.get(0), nb.get(1), jeu.getLieuxOuverts(),
				jeu.getNbZombieLieux(), jeu.getNbPionLieux(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(me);
	}

	public void indiquerPasAttaque(Partie jeu, RaisonType r, Lieu lieu, String partieId, int numeroTour) {
		String message = ControleurReseau.construirePaquetTcp("RAZPA", lieu.getNum(),
				PpTools.getPionsCouleurByPerso(lieu.getPersonnage()), r, lieu.getNbZombies(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(message);
	}

	public void indiquerAttaque(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		String message = ControleurReseau.construirePaquetTcp("RAZA", lieu.getNum(),
				PpTools.getPionsCouleurByPerso(lieu.getPersonnage()), lieu.getForce(), lieu.getNbZombies(), partieId,
				numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(message);
	}

	public void demanderDefense(Partie jeu, Lieu lieu, String partieId, int numeroTour) {
		for (Joueur j : jeu.getJoueurSurLieu(lieu))
			j.getConnection()
					.envoyer(ControleurReseau.construirePaquetTcp("RAZDD", lieu.getNum(), partieId, numeroTour));
	}

	public List<Object> recupDefense(Joueur j) {
		List<Object> lo = new ArrayList<>();
		j.getConnection().attendreMessage("RAZRD");
		String m = j.getConnection().getMessage("RAZRD");
		lo.add(ControleurReseau.getValueTcp("RAZRD", m, 1));
		lo.add(ControleurReseau.getValueTcp("RAZRD", m, 2));
		return lo;
	}

	public void informerDefense(Partie jeu, Lieu lieu, List<Integer> persoCacheTemp, int nbCarteMateriel, Joueur j,
			List<CarteType> carte, String partieId, int numeroTour) {
		for (Joueur jou : jeu.getJoueurs().values()) {
			jou.getConnection()
					.envoyer(ControleurReseau.construirePaquetTcp("RAZID", lieu.getNum(), j.getCouleur(), carte,
							persoCacheTemp, lieu.getForce() + nbCarteMateriel, lieu.getNbZombies(), partieId,
							numeroTour));
		}
	}

	public void demanderSacrifice(List<Integer> listePion, Joueur jou, Lieu lieu, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RAZDS", lieu.getNum(), listePion, partieId, numeroTour);
		jou.getConnection().envoyer(m);
	}

	public PionCouleur recupSacrifice(Joueur jou) {
		jou.getConnection().attendreMessage("RAZCS");
		String rep = jou.getConnection().getMessage("RAZCS");
		return ((PionCouleur) ControleurReseau.getValueTcp("RAZCS", rep, 2));
	}

	public void informerSacrifice(Partie jeu, Lieu lieu, PionCouleur pionCou, String partieId, int numeroTour) {
		String m = ControleurReseau.construirePaquetTcp("RAZIF", lieu.getNum(), pionCou, lieu.getNbZombies(), partieId,
				numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(m);
	}
}
