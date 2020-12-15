package botfaible;

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

public class TraitementBot {
	/**
	 * <h1>Permet de gerer les traitements tcp</h1>
	 *
	 * @author Baptiste chatelain
	 * @version 1.0
	 */
	public void initialiserPartie(BotFaible core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
		List<String> noms = new ArrayList<>();
		List<Couleur> couleurs = new ArrayList<>();
		core.setJoueurEnVie((couleursT));
		for (Object o : nomsT)
			noms.add((String) o);
		for (Couleur o : couleursT)
			couleurs.add((Couleur) o);
		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
		if (lieuferme == "2") {
			core.getLieuOuvert().add(1);
			core.getLieuOuvert().add(3);
			core.getLieuOuvert().add(4);
			core.getLieuOuvert().add(5);
			core.getLieuOuvert().add(6);
		} else {
			core.getLieuOuvert().add(1);
			core.getLieuOuvert().add(2);
			core.getLieuOuvert().add(3);
			core.getLieuOuvert().add(4);
			core.getLieuOuvert().add(5);
			core.getLieuOuvert().add(6);
		}
	}
	/**
	 * Lancement des dés
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param Une liste d'integer correspondant aux pions
	 */
	public void lancerDes(BotFaible core, List<?> pionT) {
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);
		core.setPionAPos(pion);
	}
	/**
	 * Choix du placement des personnage
	 * 
	 * @param Une liste d'integer correspondant destinations possibles
	 * @return la destination choisie
	 */
	public int choisirDestPlacement(List<?> destRestantT) {
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
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @return le pion a deplacer
	 */
	public int choisirPionPlacement(BotFaible core) {
		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		return pion;
	}
	/**
	 * Debut du tour
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param Une liste de couleur correspondant aux joueurs
	 */
	public void debutTour(BotFaible core, List<Couleur> couleurs) {
		core.setJoueurEnVie(couleurs);
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}
	/**
	 * Choix de destination random
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @return La destination choisie
	 */

	public int choixDest(BotFaible core) {
		out.println("Entrez une destination");
		int dest = 0;
		dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
		return dest;
	}
	/**
	 * Debut du deplacement
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param une liste d'integer correspondant aux lieux ouvert
	 */
	public void debutDeplacemant(BotFaible core, List<?> lieuxT) {
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
	}
	/**
	 * Choix du pion a deplacer
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param un integer correspond au numéro d'un lieu
	 * @param un dictionnaire d'integer/Liste d'integer correspondant a la liste des deplacements
	 * @return la liste des deplacements
	 */
	public List<Object> pionADeplacer(BotFaible core, int dest, HashMap<Integer, List<Integer>> listedp) {
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
	 * Attaque des zombies
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param Une liste de pionCouleur
	 * @param Une autre liste de PionCouleur (temporaire)
	 * @return la liste des personnage sacrifiable après l'attaque
	 */
	public void attaqueZombie(BotFaible core, List<PionCouleur> l, List<PionCouleur> ltemp) {
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
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param une liste d'integer correspondant aux pions
	 * @return Un pion a sacrifier
	 */
	public PionCouleur choisirSacrifice(BotFaible core, List<?> listPionT) {
		List<Integer> listPion = new ArrayList<>();
		for (Object o : listPionT)
			listPion.add((Integer) o);
		int pionTemp = listPion.get(new Random().nextInt(listPion.size()));
		PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionTemp);
		return pion;
	}
	/**
	 * Fin de la partie
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param Une couleur du joueur gagnant
	 */
	public void finPartie(BotFaible core, Couleur gagnant) {
		out.println("Le gagant est le joueur " + gagnant + " !");
		core.setEstFini(true);
		// getControleurReseau().arreter();
	}
	/**
	 * Choix random des cartes a jouer  pendant l'attaque zombies
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param un integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */
	public List<Object> listeCarteJouee(BotFaible core, int n) {
		List<Object> listRenvoye = new ArrayList<>();
		List<CarteType> listeCarteJouee = new ArrayList<>();
		List<CarteType> listeCarteUtilisable = new ArrayList<>();
		Random r = new Random();
		int indexCarteJouee;
		for (CarteType carte : core.getListeCarte()) {
			if (n != 4) {
				if (carte.name() == "MAT") {
					listeCarteUtilisable.add(carte);
				}
			}
			switch (carte.name()) {
			case "ACS":
				listeCarteUtilisable.add(carte);
				break;
			case "ATR":
				listeCarteUtilisable.add(carte);
				break;
			case "AGR":
				listeCarteUtilisable.add(carte);
				break;
			case "ARE":
				listeCarteUtilisable.add(carte);
				break;
			case "AHI":
				listeCarteUtilisable.add(carte);
				break;
			case "ABA":
				listeCarteUtilisable.add(carte);
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
		listRenvoye.add(IdjrTools.getPionsByValues(listePionCache));
		return listRenvoye;
	}

	/**
	 * Liste des pions cachés
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @return la liste des pions cachés
	 */
	public List<PionCouleur> listePionCache(BotFaible core) {
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
		for (CarteType carte : core.getListeCarte())
			if (carte.name() == "CAC")
				listeCarteCachette.add(carte);
		if (listeCarteCachette.isEmpty())
			return listePionCache;
		Random r = new Random();
		int nbrCartePossibleDejouer = listeCarteCachette.size();
		int nbrPion = core.getPoinSacrDispo().size();
		int nbrCarteJouee;
		if (nbrCartePossibleDejouer > nbrPion)
			nbrCarteJouee = r.nextInt(nbrPion);
		else
			nbrCarteJouee = r.nextInt(nbrCartePossibleDejouer);
		for (int i = 0; i < nbrCarteJouee; i++)
			listePionCache.add(core.getPoinSacrDispo().get(i));
		return listePionCache;
	}
	/**
	 * Reponse du joueur courant
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @return Un type de carte 
	 */
	public CarteType ReponseJoueurCourant(BotFaible core) {
		CarteType RJ = CarteType.NUL;
		if (core.getListeCarte().contains(CarteType.CDS))
			if ( new Random().nextInt(1) == 1 ) {
				RJ = CarteType.CDS;
				core.getListeCarte().remove(CarteType.CDS);
			}	
		return RJ;
	}
	/**
	 * Indiquer les cartes jouées
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @return le nombre de cartes jouées
	 */
	public int IndiquerCarteJouees(BotFaible core) {
		if (core.getListeCarte().isEmpty())
			return 0;

		CarteType carteMenace = CarteType.MEN;
		Random r = new Random();
		int nbrCarteMen = 0;
		for (CarteType carte : core.getListeCarte()) {
			if (carte.name() == carteMenace.name()) {
				nbrCarteMen++;
			}
		}
		if (nbrCarteMen == 0)
			return 0;

		int nbrCartemenaceReturn = r.nextInt(nbrCarteMen);
		return nbrCartemenaceReturn;
	}
	/**
	 * Retourne une couleur random parmis les joueurs présents sauf le joueur actuel
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param un integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */
	public Couleur getRandom(BotFaible core, VoteType vt) {
		int rand;
		if (vt == VoteType.MPZ) {
			System.out.println("Joueur Présent:" + core.couleurJoueurPresent().size());
			core.couleurJoueurPresent().remove(core.getCouleur());
			rand = new Random().nextInt(core.couleurJoueurPresent().size());
			return core.couleurJoueurPresent().get(rand);
		}
		rand = new Random().nextInt(core.couleurJoueurPresent().size());
		return core.couleurJoueurPresent().get(rand);

	}
	/**
	 * Retourne une couleur random parmis les joueurs présents 
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param un integer correspond au numéro d'un lieu
	 * @return la liste des carte jouées
	 */
	public Couleur getRandom2(BotFaible core, VoteType vt) {
		int rand = new Random().nextInt(core.getJoueurEnVie().size());
		return core.getJoueurEnVie().get(rand);

	}
	/**
	 * Choix de la gestions de cartes de la fouille random
	 *
	 * @param BotMoyen pour lequel le traitement s'effectue
	 * @param Une liste de carteType correspondant aux cartes récupérées
	 * @return la liste des carte choisies
	 */
	public List<Object> carteFouille(List<CarteType> listeCarte, BotFaible bot) {
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
			carteOfferte = listeCarte.get(1);
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

		return carteChoisies;
	}

}
