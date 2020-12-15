package botmoyen;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import botmoyen.MCTS.MCTSBotMoyen;
import botmoyen.partie.Lieu;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VoteType;

public class TraitementBot {

	public void initialiserPartie(BotMoyen core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
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
		core.initPartie(couleurs);
	}

	public void lancerDes(BotMoyen core, List<?> pionT) {
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);
		core.setPionAPos(pion);
	}

	public int choisirDestPlacement(List<?> destRestantT) {
		List<Integer> destRestant = new ArrayList<>();
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		int dest = 0;
		if (!destRestant.isEmpty())
			dest = destRestant.get(new Random().nextInt(destRestant.size()));
		return dest;
	}

	public int choisirPionPlacement(BotMoyen core) {
		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		return pion;
	}

	public void debutTour(BotMoyen core, List<Couleur> couleurs) {
		core.newTour();
		core.setJoueurEnVie(couleurs);
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}

	public int choixDest(BotMoyen core) {
		out.println(core.getEtatPartie());
		out.println("Entrez une destination");
		int dest = 0;
		if (core.getCompteurTour()%2==1) 
			dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
		else 
			dest = MCTSBotMoyen.getChoisDest(core.getPartie(),core.getCouleur());
		return dest;
	}
	
	public int choixBestDest (BotMoyen core) {
		out.println("Entrez une destination");
		int dest = 0;
		int difference =-20;
		for(int i =0;i<core.getLieuOuvert().size();i++) {
			if(core.getPartie().getLieux().get(i).getPersonnage().size()-core.getPartie().getLieux().get(i).getNbZombies()>difference)
				dest = core.getPartie().getLieux().get(i).getNum();
			if(core.getPartie().getLieux().get(i).getPersonnage().size()-core.getPartie().getLieux().get(i).getNbZombies()==difference) {
				if(core.getPartie().getLieux().get(i).getNum()==5) {
					dest = core.getPartie().getLieux().get(i).getNum();
				}
			}
		
		}
		return dest;
	}

	public int choixBestZDest(BotMoyen core) {
		out.println("Entrez une destination");
		int dest = 0;
		int lieuMin = 20;
		for (int i = 0; i < core.getLieuOuvert().size(); i++) {
			if (core.getPartie().getLieux().get(i).getPersonnage().size() < lieuMin)
				dest = core.getPartie().getLieux().get(i).getNum();

		}
		return dest;
	}

	public void debutDeplacemant(BotMoyen core, List<?> lieuxT) {
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
		core.setLieuxFerme(lieux);
	}

	public List<Object> pionADeplacer(BotMoyen core, int dest, HashMap<Integer, List<Integer>> listedp) {
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

	public void attaqueZombie(BotMoyen core, List<PionCouleur> l, List<PionCouleur> ltemp) {
		for (PionCouleur pc : l) {
			if (IdjrTools.getCouleurByChar(pc) == core.getCouleur()) {
				ltemp.add(pc);
			}
		}
		core.setPoinSacrDispo(ltemp);
	}

	public int choixBestDest3 (BotMoyen core) {
		out.println("Entrez une destination");
		int dest = 0;
		int difference =-20;
		for(int i =0;i<core.getLieuOuvert().size();i++) {
			if(core.getPartie().getLieux().get(i).getPersonnage().size()-core.getPartie().getLieux().get(i).getNbZombies()>difference)
				dest = core.getPartie().getLieux().get(i).getNum();
			if(core.getPartie().getLieux().get(i).getPersonnage().size()-core.getPartie().getLieux().get(i).getNbZombies()==difference) {
				if(core.getPartie().getLieux().get(i).getNum()==5) {
					dest = core.getPartie().getLieux().get(i).getNum();
				}
			}
		
		}
		return dest;
	}
	public PionCouleur choisirSacrifice(BotMoyen core, List<?> listPionT) {
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

	public void finPartie(BotMoyen core, Couleur gagnant) {
		out.println("Le gagant est le joueur " + gagnant + " !");
		core.setEstFini(true);
		// getControleurReseau().arreter();
	}

	public List<Object> listeCarteJouee(BotMoyen core, int n) {
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
		listRenvoye.add(listePionCache);
		return listRenvoye;
	}
	
	public List<Object> BestlisteCarteJouee(BotMoyen core, int n) {
		List<Object> listRenvoye = new ArrayList<>();
		List<CarteType> listeCarteJouee = new ArrayList<>();
		List<CarteType> listeCarteUtilisable = new ArrayList<>();
	
		
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
		
		int carteAJouer = 0;
		if (!listeCarteUtilisable.isEmpty()) {
			for(int i=0; i<6;i++) {
			if(core.getPartie().getLieux().get(n).getPersonnage().get(i).getJoueur().getCouleur()== core.getCouleur()) {
				listeCarteJouee.add(listeCarteUtilisable.get(carteAJouer));
				core.getListeCarte().remove(listeCarteUtilisable.get(carteAJouer));
				listeCarteUtilisable.remove(carteAJouer);
				
				
			}
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

	public List<PionCouleur> listePionCache(BotMoyen core) {
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
		for (CarteType carte : core.getListeCarte())
			if (carte.equals(CarteType.CAC))
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

	public CarteType ReponseJoueurCourant(BotMoyen core) {
		if ((core.getListeCarte().contains(CarteType.CDS)) && (new Random().nextInt(1) == 1)) {
			core.getListeCarte().remove(CarteType.CDS);
			return CarteType.CDS;
		}
		return CarteType.NUL;
	}

	public int IndiquerCarteJouees(BotMoyen core) {
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

	public Couleur getRandom(BotMoyen core, VoteType vt) {
		if (vt == VoteType.MPZ) {
			System.out.println("Joueur Présent:" + core.couleurJoueurPresent().size());
			core.couleurJoueurPresent().remove(core.getCouleur());
			return core.couleurJoueurPresent().get(new Random().nextInt(core.couleurJoueurPresent().size()));
		}
		return core.couleurJoueurPresent().get(new Random().nextInt(core.couleurJoueurPresent().size()));
	}

	public Couleur getRandom2(BotMoyen core, VoteType vt) {
		return core.getJoueurEnVie().get(new Random().nextInt(core.getJoueurEnVie().size()));
	}

	public List<Object> carteFouille(List<CarteType> listeCarte, BotMoyen bot) {
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

}
