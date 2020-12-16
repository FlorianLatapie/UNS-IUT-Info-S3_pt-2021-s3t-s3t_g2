package bot;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import bot.MCTS.MCTSBotMoyen;
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
			if (IdjrTools.getCouleurByChar(pc) == core.getCouleur()) {
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
		} else
			return null;
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



	protected static List<Object> carteFouilleIntelligent(List<CarteType> listeCarte, Bot core) {
		// TODO Auto-generated method stub
		return null;
	}

	protected static int IndiquerCarteJoueesIntelligent(Bot core) {
		// TODO Auto-generated method stub
		return 0;
	}

	protected static List<Object> pionADeplacerIntelligent(Bot core, int dest, HashMap<Integer, List<Integer>> listedp) {
		// TODO Auto-generated method stub
		return null;
	}



	protected static Integer choisirPionPlacementIntelligent(Bot core) {
		// TODO Auto-generated method stub
		return null;
	}

	protected static Integer choisirDestPlacementIntelligent(List<?> destRestanteT) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected static Couleur voteIntelligent(Bot core, VoteType vt) {
		// TODO Auto-generated method stub
		return null;
	}
}
