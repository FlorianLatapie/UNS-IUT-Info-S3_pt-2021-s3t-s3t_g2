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
		if ((core.getListeCarte().isEmpty())||!core.getVoteType().equals(VoteType.MPZ))
			return 0;

		CarteType carteMenace = CarteType.MEN;
		int nbrCarteMen = 0;
		for (CarteType carte : core.getListeCarte())
			if (carte.name().equals(carteMenace.name()))
				nbrCarteMen++;
		if (nbrCarteMen == 0)
			return 0;

		int maxDiff=0;
		for (Couleur c : core.getJoueursVotant().keySet()) {
			int diff =core.getJoueursVotant().get(core.getCouleur()) - core.getJoueursVotant().get(c) ;
			if (diff>maxDiff)
				maxDiff=diff;
		}
		
		if (maxDiff>=nbrCarteMen)
			return nbrCarteMen;
		else
			return maxDiff;
	}

	protected static List<Object> pionADeplacerIntelligent(Bot core, int dest, HashMap<Integer, List<Integer>> listedp) {
		int bestLieu =10;
		int LieuPionABouger;
		int nbCartesSprint=0;
		List<Integer> pionPossibles = new ArrayList<Integer>();
		int bestPionABouger = 0; int difference=0;
		List<Object> listARenvoyer= new ArrayList<Object>();
		for(int i = 0; i<core.getPartie().getLieuxOuverts().size();i++) {
			if(difference>(core.getPartie().getLieux().get(i).getForce()-core.getPartie().getLieux().get(i).getNbZombies())&&core.getPartie().getLieux().get(i).getJoueursCouleurs().contains(core.getCouleur())) {
				difference = (core.getPartie().getLieux().get(i).getForce()-core.getPartie().getLieux().get(i).getNbZombies());
				LieuPionABouger=core.getPartie().getLieux().get(i).getNum();
				for(int j = 0;j<core.getPartie().getLieux().get(i).getPersonnage().size();j++) {
					if(core.getPartie().getLieux().get(i).getPersonnage().get(j).getCouleur().equals(core.getCouleur())) {
						pionPossibles.add(core.getPartie().getLieux().get(i).getPersonnage().get(j).getPoint());
					}
				}
			}
			
		}
		for(int z=0;z<pionPossibles.size();z++) {
			if(pionPossibles.get(z)>bestPionABouger) {
				bestPionABouger = pionPossibles.get(z);
			}
		}
		for(int y=0;y<core.getListeCarte().size();y++) {
			if(core.getListeCarte().get(y)==CarteType.SPR) {
				nbCartesSprint++;
			}
		}
		
		listARenvoyer.add(dest);
		listARenvoyer.add(bestPionABouger);
		if(nbCartesSprint>0) {
			listARenvoyer.add(CarteType.SPR);
		}
		else {
			listARenvoyer.add(CarteType.NUL);
		}
		return listARenvoyer;
	}



	protected static Integer choisirPionPlacementIntelligent(Bot core) {
		// TODO faire la version intelligente
		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		return pion;
	}

	protected static Integer choisirDestPlacementIntelligent(List<?> destRestantT) {
		// TODO faire la version intelligente
		List<Integer> destRestant = new ArrayList<>();
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		int dest = 0;
		if (!destRestant.isEmpty())
			dest = destRestant.get(new Random().nextInt(destRestant.size()));
		return dest;
	}
	
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
