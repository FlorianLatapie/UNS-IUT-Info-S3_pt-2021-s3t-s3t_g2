package bot;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VoteType;

public class TraitementAleatoire {
	
	/**
	 * Retourne une couleur random parmis les joueurs présents
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param un  integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */
	private static Couleur getRandom2(Bot core, VoteType vt) {
		return core.getJoueurEnVie().get(new Random().nextInt(core.getJoueurEnVie().size()));
	}
	

	
	/**
	 * Choix de la gestions de cartes de la fouille random
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une liste de carteType correspondant aux cartes récupérées
	 * @return la liste des carte choisies
	 */
	protected static List<Object> carteFouilleAleatoire(List<CarteType> listeCarte, Bot bot) {
		CarteType carteGarde = CarteType.NUL;
		CarteType carteOfferte = CarteType.NUL;
		CarteType carteDefausse = CarteType.NUL;
		Couleur couleur = Couleur.NUL;
		List<Object> carteChoisies = new ArrayList<Object>();
		if (listeCarte.size() == 3) {
			carteGarde = listeCarte.get(0);
			bot.addCarte(carteGarde);
			carteOfferte = listeCarte.get(1);
			carteDefausse = listeCarte.get(2);
			System.out.print("carte fouille");
			couleur = getRandom2(bot, VoteType.FDC);
		}
		if (listeCarte.size() == 2) {
			carteGarde = listeCarte.get(0);
			bot.addCarte(carteGarde);
			System.out.print("carte fouille");
			System.out.print("4" + bot.couleurJoueurPresent().size());
			couleur = getRandom2(bot, VoteType.FDC);
		}
		if (listeCarte.size() == 1) {
			carteGarde = listeCarte.get(0);
			bot.addCarte(carteGarde);
		}
		carteChoisies.add(carteGarde);
		carteChoisies.add(carteOfferte);
		carteChoisies.add(carteDefausse);
		carteChoisies.add(couleur);
		bot.carteCamion(carteChoisies);

		return carteChoisies;
	}
	
	protected static int choisirDestPlacementAleatoire(List<?> destRestantT) {
		List<Integer> destRestant = new ArrayList<>();
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		int dest = 0;
		if (!destRestant.isEmpty())
			dest = destRestant.get(new Random().nextInt(destRestant.size()));
		return dest;
	}

	/**
	 * Choix du pion a deplacer
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return le pion a deplacer
	 */

	protected static int choisirPionPlacementAleatoire(Bot core) {
		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		return pion;
	}


	/**
	 * Choix du pion a deplacer
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param un  integer correspond au numéro d'un lieu
	 * @param un  dictionnaire d'integer/Liste d'integer correspondant a la liste
	 *            des deplacements
	 * @return la liste des deplacements
	 */

	protected static List<Object> pionADeplacerAleatoire(Bot core, int dest, HashMap<Integer, List<Integer>> listedp) {
		List<Object> listRenvoye = new ArrayList<>();
		List<Integer> listePion = new ArrayList<>();
		List<Integer> destPossible = new ArrayList<>();
		CarteType sprintJoue = CarteType.NUL;
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				destPossible.add(destPos);
		if (core.getListeCarte().contains(CarteType.SPR)) {
			int i = new Random().nextInt(2);
			if (i == 1) {
				sprintJoue = CarteType.SPR;
				dest = destPossible.get(new Random().nextInt(destPossible.size()));
			}
		}
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				if (destPos == dest)
					listePion.add(dp.getKey());
		int pionAdep;
		if (listePion.isEmpty()) {
			dest = 4;
			for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
				for (int destPos : dp.getValue())
					if (destPos == dest)
						listePion.add(dp.getKey());
			if (!listePion.isEmpty())
				pionAdep = listePion.get(new Random().nextInt(listePion.size()));
			else
				pionAdep = new ArrayList<Integer>(listedp.keySet()).get(0);
		} else
			pionAdep = listePion.get(new Random().nextInt(listePion.size()));
		listRenvoye.add(dest);
		listRenvoye.add(pionAdep);
		listRenvoye.add(sprintJoue);
		return listRenvoye;
	}

	

	/**
	 * Choix du personnage a sacrifier
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param une liste d'integer correspondant aux pions
	 * @return Un pion a sacrifier
	 */

	protected static PionCouleur choisirSacrificeAleatoire(Bot core, List<?> listPionT) {
		if (listPionT.size() > 0) {
			List<Integer> listPion = new ArrayList<>();
			for (Object o : listPionT)
				listPion.add((Integer) o);
			int rand = new Random().nextInt(listPion.size());
			if (rand == 0)
				rand = 0;
			int pionTemp = listPion.get(rand);
			PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionTemp);
			return pion;
		} else
			return null;
	}

	

	

	/**
	 * Choix random des cartes a jouer pendant l'attaque zombies
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param un  integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */

	protected static List<Object> listeCarteJoueeAleatoire(Bot core, int n) {
		List<Object> listRenvoye = new ArrayList<>();
		List<CarteType> listeCarteJouee = new ArrayList<>();
		List<CarteType> listeCarteUtilisable = new ArrayList<>();
		Random r = new Random();
		int indexCarteJouee;
		for (CarteType carte : core.getListeCarte()) {
			if ((n != 4) && (carte.name().equals("MAT")))
				listeCarteUtilisable.add(carte);

			switch (carte.name()) {
			case "ACS":
			case "ATR":
			case "AGR":
			case "ARE":
			case "AHI":
			case "ABA":
				listeCarteUtilisable.add(carte);
				break;
			default:
				break;
			}
		}
		int nbCarteJouee = 0;
		if (!listeCarteUtilisable.isEmpty()) {
			nbCarteJouee = r.nextInt(listeCarteUtilisable.size());
			for (int i = 0; i < nbCarteJouee; i++) {
				indexCarteJouee = r.nextInt(listeCarteUtilisable.size());
				listeCarteJouee.add(listeCarteUtilisable.get(indexCarteJouee));
				core.getListeCarte().remove(listeCarteUtilisable.get(indexCarteJouee));
				listeCarteUtilisable.remove(indexCarteJouee);
			}
		}
		List<PionCouleur> listePionCache = listePionCache(core);
		int i = 0;
		while (i < listePionCache.size()) {
			listeCarteJouee.add(CarteType.CAC);
			core.getListeCarte().remove(CarteType.CAC);
			i++;
		}
		listRenvoye.add(listeCarteJouee);
		listRenvoye.add(listePionCache);
		return listRenvoye;
	}

	

	/**
	 * Reponse du joueur courant
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return Un type de carte
	 */

	protected static CarteType reponseJoueurCourantAleatoire(Bot core) {
		if ((core.getListeCarte().contains(CarteType.CDS)) && (new Random().nextInt(1) == 1)) {
			core.getListeCarte().remove(CarteType.CDS);
			return CarteType.CDS;
		}
		return CarteType.NUL;
	}

	

	/**
	 * Indiquer les cartes jouées
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return le nombre de cartes jouées
	 */

	protected static int IndiquerCarteJoueesAleatoire(Bot core) {
		if (core.getListeCarte().isEmpty())
			return 0;

		CarteType carteMenace = CarteType.MEN;
		int nbrCarteMen = 0;
		for (CarteType carte : core.getListeCarte())
			if (carte.name() == carteMenace.name())
				nbrCarteMen++;
		if (nbrCarteMen == 0)
			return 0;
		return new Random().nextInt(nbrCarteMen);
	}

	/**
	 * Retourne une couleur random parmis les joueurs présents sauf le joueur actuel
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param un  integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */

	protected static Couleur voteAleatoire(Bot core, VoteType vt) {
		if (vt == VoteType.MPZ) {
			System.out.println("Joueur Présent:" + core.couleurJoueurPresent().size());
			core.couleurJoueurPresent().remove(core.getCouleur());
			return core.couleurJoueurPresent().get(new Random().nextInt(core.couleurJoueurPresent().size()));
		}
		return core.couleurJoueurPresent().get(new Random().nextInt(core.couleurJoueurPresent().size()));
	}



	protected static int choixZDestAleatoire(Bot core) {
		out.println(core.getEtatPartie());
		out.println("Le bot choisit une destination pour le zombie vengeur");
		int dest = 0;
		dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
		return dest;
	}

	protected static int choixDestAleatoire(Bot core) {
		out.println(core.getEtatPartie());
		out.println("Le bot choisit une destination de placement");
		int dest = 0;
		dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
		return dest;
	}
	
	/**
	 * Liste des pions cachés
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return la liste des pions cachés
	 */
	private static List<PionCouleur> listePionCache(Bot core) {
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
		for (CarteType carte : core.getListeCarte())
			if (carte.equals(CarteType.CAC))
				listeCarteCachette.add(carte);
		if (listeCarteCachette.isEmpty())
			return listePionCache;
		Random r = new Random();
		int nbrCartePossibleDejouer = listeCarteCachette.size();
		int nbrPion = core.getPionSacrDispo().size();
		int nbrCarteJouee;
		if (nbrCartePossibleDejouer > nbrPion)
			nbrCarteJouee = r.nextInt(nbrPion);
		else
			nbrCarteJouee = r.nextInt(nbrCartePossibleDejouer);
		for (int i = 0; i < nbrCarteJouee; i++)
			listePionCache.add(core.getPionSacrDispo().get(i));
		return listePionCache;
	}


	

}
