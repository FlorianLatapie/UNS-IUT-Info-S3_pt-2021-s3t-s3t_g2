package bot;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import bot.MCTS.MCTSBotMoyen;
import bot.partie.Lieu;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VoteType;

public class TraitementIntelligent {

	/**
	 * Choix de destination
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return La destination choisie
	 */

	protected static int choixDestIntelligent(Bot core) {
		out.println(core.getEtatPartie());
		out.println("Entrez une destination");
		return MCTSBotMoyen.getChoisDest(core.getPartie().copyOf(), core.getCouleur());
	}

	/**
	 * Choix de destination intelligente du zombie vengeur
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return La destination choisie
	 */

	protected static int choixZDestIntelligent(Bot core) {
		out.println("Entrez une destination");
		int dest = 0;
		int lieuMin = 20;
		for (int i = 0; i < core.getLieuOuvert().size(); i++) {
			if (core.getPartie().getLieux().get(i).getPersonnage().size() < lieuMin)
				dest = core.getPartie().getLieux().get(i).getNum();

		}
		return dest;
	}

	/**
	 * Attaque des zombies
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une liste de pionCouleur
	 * @param Une autre liste de PionCouleur (temporaire)
	 * @return la liste des personnage sacrifiable après l'attaque
	 */

	protected static void attaqueZombie(Bot core, List<PionCouleur> l) {
		List<PionCouleur> ltemp = new ArrayList<PionCouleur>();
		for (PionCouleur pc : l) {
			if (BotOutils.getCouleurByChar(pc) == core.getCouleur()) {
				ltemp.add(pc);
			}
		}
		core.setPoinSacrDispo(ltemp);
	}

	/**
	 * Choix du personnage a sacrifier
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param une liste d'integer correspondant aux pions
	 * @return Un pion a sacrifier
	 */

	protected static PionCouleur choisirBestSacrificeIntelligent(Bot core, List<?> listPionT) {
		int bestPion = 20;
		if (!listPionT.isEmpty()) {
			List<Integer> listPion = new ArrayList<>();
			for (Object o : listPionT)
				listPion.add((Integer) o);
			for (int i = 0; i < listPion.size(); i++) {
				if (listPion.get(i) < bestPion) {
					bestPion = listPion.get(i);
				}
			}
			return PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + bestPion);
		} else {
			System.out.println("erreur listePionT vide!!!!!!!!!");
			return null;
		}
	}

	/**
	 * Choix intelligent des cartes a jouer pendant l'attaque zombies
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param un  integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */

	protected static List<Object> listeCarteJoueeIntelligent(Bot core, int n) {
		List<Object> listRenvoye = new ArrayList<>();
		List<CarteType> listeCarteJouee = new ArrayList<>();
		List<CarteType> listeCarteUtilisable = new ArrayList<>();

		for (CarteType carte : core.getListeCarte()) {
			if (n != 4) {
				switch (carte.name()) {
				case "ACS":
				case "ATR":
				case "AGR":
				case "ARE":
				case "AHI":
				case "ABA":
				case "MAT":
					listeCarteUtilisable.add(carte);
					break;
				default:
					break;
				}
			}
		}
		int forcePerso = core.getPartie().getLieux().get(n).getForce();
		int nbZombie = core.getPartie().getLieux().get(n).getNbZombies();
		for (CarteType carteAJouer : listeCarteUtilisable) {
			switch (carteAJouer.name()) {
			case "ACS":
			case "ATR":
			case "AGR":
				if (nbZombie >= forcePerso) {
					listeCarteJouee.add(carteAJouer);
					core.getListeCarte().remove(carteAJouer);
					nbZombie = nbZombie - 2;
				}
				break;
			case "ARE":
			case "AHI":
			case "ABA":
				if (nbZombie >= forcePerso) {
					listeCarteJouee.add(carteAJouer);
					core.getListeCarte().remove(carteAJouer);
					nbZombie--;
				}
				break;
			default:
				break;
			}
		}
		List<PionCouleur> listePionCache = new ArrayList<>();
		for (PionCouleur pion : core.getPionSacrDispo())
			if (listeCarteUtilisable.contains(CarteType.CAC)) {
				listeCarteJouee.add(CarteType.CAC);
				core.getListeCarte().remove(CarteType.CAC);
				listeCarteUtilisable.remove(CarteType.CAC);
				listePionCache.add(pion);
			}
		listRenvoye.add(listeCarteJouee);
		listRenvoye.add(listePionCache);
		return listRenvoye;
	}

	/**
	 * Liste des pions cachés
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return la liste des pions cachés
	 */
	protected static List<PionCouleur> listePionCache(Bot core) {
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
		for (CarteType carte : core.getListeCarte())
			if (carte.equals(CarteType.CAC))
				listeCarteCachette.add(carte);
		if (listeCarteCachette.isEmpty())
			return listePionCache;

		int nbrCartePossibleDejouer = listeCarteCachette.size();
		int nbrPion = core.getPionSacrDispo().size();
		int nbrCarteJouee;
		if (nbrCartePossibleDejouer > nbrPion)
			nbrCarteJouee = new Random().nextInt(nbrPion);
		else
			nbrCarteJouee = new Random().nextInt(nbrCartePossibleDejouer);
		for (int i = 0; i < nbrCarteJouee; i++)
			listePionCache.add(core.getPionSacrDispo().get(i));
		return listePionCache;
	}

	/**
	 * Meilleure Reponse du joueur(bot) courant
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @return Un type de carte
	 */
	protected static CarteType reponseJoueurCourantIntelligent(Bot core) {
		if (core.getListeCarte().contains(CarteType.CDS)) {
			core.getListeCarte().remove(CarteType.CDS);
			return CarteType.CDS;
		}
		return CarteType.NUL;
	}

	/**
	 * Choix carte fouille intelligente
	 *
	 * @param Une liste de type de carte
	 * @param Un  bot pour lequel le traitement s'effectue
	 * @return La liste des carte gardée/donnée/choisie
	 */

	protected static List<Object> carteFouilleIntelligent(List<CarteType> listeCarte, Bot core) {
		// TODO faire la version intelligente
		CarteType carteGarde = CarteType.NUL;
		CarteType carteOfferte = CarteType.NUL;
		CarteType carteDefausse = CarteType.NUL;
		Couleur couleur = Couleur.NUL;
		List<Object> carteChoisies = new ArrayList<Object>();
		if (listeCarte.size() == 3) {
			carteGarde = listeCarte.get(0);
			core.addCarte(carteGarde);
			carteOfferte = listeCarte.get(1);
			carteDefausse = listeCarte.get(2);
			System.out.print("carte fouille");
			couleur = getRandom2(core, VoteType.FDC);
		}
		if (listeCarte.size() == 2) {
			carteGarde = listeCarte.get(0);
			core.addCarte(carteGarde);
			System.out.print("carte fouille");
			System.out.print("4" + core.couleurJoueurPresent().size());
			couleur = getRandom2(core, VoteType.FDC);
		}
		if (listeCarte.size() == 1) {
			carteGarde = listeCarte.get(0);
			core.addCarte(carteGarde);
		}
		carteChoisies.add(carteGarde);
		carteChoisies.add(carteOfferte);
		carteChoisies.add(carteDefausse);
		carteChoisies.add(couleur);
		core.carteCamion(carteChoisies);

		return carteChoisies;
	}

	protected static int IndiquerCarteJoueesIntelligent(Bot core) {
		if ((core.getListeCarte().isEmpty()) || !core.getVoteType().equals(VoteType.MPZ))
			return 0;

		CarteType carteMenace = CarteType.MEN;
		int nbrCarteMen = 0;
		for (CarteType carte : core.getListeCarte())
			if (carte.name().equals(carteMenace.name()))
				nbrCarteMen++;
		if (nbrCarteMen == 0)
			return 0;

		int maxDiff = 0;
		for (Couleur c : core.getJoueursVotantPresent().keySet()) {
			int diff = core.getJoueursVotantPresent().get(core.getCouleur()) - core.getJoueursVotantPresent().get(c);
			if (diff > maxDiff)
				maxDiff = diff;
		}

		if (maxDiff >= nbrCarteMen)
			return nbrCarteMen;
		else
			return maxDiff;
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
	protected static List<Object> pionADeplacerIntelligent(Bot core, int dest,
			HashMap<Integer, List<Integer>> listedp) {
		List<Object> listRenvoye = new ArrayList<>();
		List<Integer> listePion = new ArrayList<>();
		List<Integer> destPossible = new ArrayList<>();
		List<Integer> destPossibleNonAttaquable = new ArrayList<>();
		CarteType sprintJoue = CarteType.NUL;
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				destPossible.add(destPos);
		if ((core.getListeCarte().contains(CarteType.SPR)) && (core.getPartie().getLieux().get(dest).estAttaquable())) {
			dest = destPossible.get(new Random().nextInt(destPossible.size()));
			sprintJoue = CarteType.SPR;
			for (int i : destPossible) {
				if (!core.getPartie().getLieux().get(i).estAttaquable())
					destPossibleNonAttaquable.add(i);
			}
			if (!destPossibleNonAttaquable.isEmpty())
				dest = destPossibleNonAttaquable.get(new Random().nextInt(destPossibleNonAttaquable.size()));
		}
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				if (destPos == dest)
					listePion.add(dp.getKey());
		int pionAdep = 0;
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
		} else {
			System.out.println("liste pion not mpty : listepion = " + listePion);
			pionAdep = listePion.get(new Random().nextInt(listePion.size()));
			listePion.sort(null);
			if (core.getPartie().getLieux().get(dest).estAttaquable())
				pionAdep = listePion.get(0);
			else
				for (Integer pion : listePion)
					if (core.getPartie().getLieux().get(core.getPartie().getJoueurs().get(core.getCouleur())
							.getPersonnages().get(pion).getMonLieu()).estAttaquable())
						pionAdep = pion;
		}
		listRenvoye.add(dest);
		listRenvoye.add(pionAdep);
		listRenvoye.add(sprintJoue);
		return listRenvoye;
	}

	protected static Integer choisirPionPlacementIntelligent(Bot core) {
		// TODO faire la version intelligente
		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		return pion;
	}

	/**
	 * Choix de la destination intelligente
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une liste qui correspond a la liste de lieux
	 * @return la destination choisie
	 */

	protected static Integer choisirDestPlacementIntelligent(Bot core, List<?> destRestantT) {
		List<Integer> destRestant = new ArrayList<>();
		int nbZombie = 100;
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		int dest = 0;
		if (!destRestant.isEmpty())
			for (int i = 0; i < destRestant.size(); i++) {
				for (Lieu l : core.getPartie().getLieux().values()) {
					if ((destRestant.get(i) == l.getNum()) && (l.getNbZombies() < nbZombie)) {
						nbZombie = l.getNbZombies();
						dest = l.getNum();
					}
				}
			}
		return dest;
	}

	/**
	 * Vote intelligent
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Un  type de vote
	 * @return la couleur du joueur pour lequel on vote
	 */

	protected static Couleur voteIntelligent(Bot core, VoteType vt) {
		// TODO can be upgrade
		if (vt == VoteType.MPZ) {
			System.out.println("Joueur Présent:" + core.couleurJoueurPresent().size());
			core.couleurJoueurPresent().remove(core.getCouleur());
			return core.couleurJoueurPresent().get(new Random().nextInt(core.couleurJoueurPresent().size()));
		}
		return core.getCouleur();
	}

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
}
