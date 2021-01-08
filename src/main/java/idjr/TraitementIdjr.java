package idjr;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import idjr.ihmidjr.event.Evenement;
import idjr.ihmidjr.langues.International;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VoteType;

public class TraitementIdjr {

	public void initialiserPartie(Idjr core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
		Evenement.stopWait();
		Evenement.nomPhase(International.trad("text.placeperso"));
		List<String> noms = new ArrayList<>();
		List<Couleur> couleurs = new ArrayList<>();
		core.setJoueurEnVie((couleursT));
		for (Object o : nomsT)
			noms.add((String) o);
		for (Couleur o : couleursT)
			couleurs.add((Couleur) o);
		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
		Evenement.couleurJoueur(core.getCouleur());
		if (lieuferme.equals("2")) {
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
		for (int i = 0; i < noms.size(); i++)
			core.getListeJoueursInitiale().put(couleurs.get(i), noms.get(i));
	}

	public void lancerDes(Idjr core, List<?> pionT) {
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);
		core.setPionAPos(pion);
	}

	public int choisirDestPlacement(Idjr core, List<?> desT, List<?> destRestantT) {
		List<Integer> destRestant = new ArrayList<>();
		List<Integer> des = new ArrayList<>();
		for (Object o : desT)
			des.add((Integer) o);
		Evenement.desValeur(des);
		core.desVoteChoisi(false);
		while (!core.desVote())
			Thread.yield();
		core.desVoteChoisi(false);
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		Evenement.choisirLieu(destRestant);
		while (!core.lieuDisponible())
			Thread.yield();
		int dest = core.getLieuChoisi();
		core.lieuChoisi(false);
		return dest;
	}

	public int choisirPionPlacement(Idjr core) {
		Evenement.choisirPion(core.getPionAPos());
		while (!core.pionDisponible())
			Thread.yield();
		int pion = core.getPionChoisi();
		core.pionChoisi(false);
		return pion;
	}

	public void debutTour(Idjr core, List<Couleur> couleurs) {
		core.setJoueurEnVie(couleurs);
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}

	public int choixDest(Idjr core) {
		out.println("Entrez une destination");
		Evenement.choisirLieu(core.getLieuOuvert());
		while (!core.lieuDisponible())
			Thread.yield();
		int dest = core.getLieuChoisi();
		core.lieuChoisi(false);
		return dest;
	}

	public void debutDeplacemant(Idjr core, List<?> lieuxT) {
		Evenement.nomPhase(International.trad("text.phasedeplperso"));
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
	}

	public List<Object> pionADeplacer(Idjr core, int dest, HashMap<Integer, List<Integer>> listedp) {
		// TODO gérer carte sprint
		List<Object> listRenvoye = new ArrayList<>();
		List<Integer> listePion = new ArrayList<>();
		List<Integer> destPossible = new ArrayList<>();
		CarteType sprintJoue = CarteType.NUL;
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				destPossible.add(destPos);
		if (core.getListeCarte().contains(CarteType.SPR)) {
			// TODO choisir si utiliser carte sprint
			Evenement.choisirUtiliserCarte(CarteType.SPR);
			while (!core.utiliserCarteDisponible())
				Thread.yield();
			sprintJoue = core.getUtiliserCarteChosi();
			core.utiliserCarteChoisi(false);
			if (sprintJoue == null)
				sprintJoue = CarteType.NUL;
			if (sprintJoue == CarteType.SPR) {
				core.getListeCarte().remove(sprintJoue);
				// TODO choisir destination carte sprint parmis destPossible;
				Evenement.choisirLieu(core.getLieuOuvert());
				while (!core.lieuDisponible())
					Thread.yield();
				dest = core.getLieuChoisi();
				core.lieuChoisi(false);
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
			if (!listePion.isEmpty()) {
				Evenement.choisirPion(listePion);
				while (!core.pionDisponible())
					Thread.yield();
				pionAdep = core.getPionChoisi();
				core.pionChoisi(false);
			} else {
				// TODO Affiche PAS DE PERSO A DEPLACE
				pionAdep = new ArrayList<Integer>(listedp.keySet()).get(0);
			}
		} else {
			Evenement.choisirPion(listePion);
			while (!core.pionDisponible())
				Thread.yield();
			pionAdep = core.getPionChoisi();
			core.pionChoisi(false);
		}
		listRenvoye.add(dest);
		listRenvoye.add(pionAdep);
		listRenvoye.add(sprintJoue);
		return listRenvoye;
	}

	public void debutPhaseAttaque(Idjr core) {
		Evenement.nomPhase(International.trad("text.phaseresatazom"));
	}

	public void attaqueZombie(Idjr core, List<PionCouleur> l, int x,int zombie) {
		core.setNbZombieLieu(zombie);
		String nom = "";
		if (x == 1)
			nom = "Toilettes";
		if (x == 2)
			nom = "Cachou";
		if (x == 3)
			nom = "Megatoys";
		if (x == 4)
			nom = "Parking";
		if (x == 5)
			nom = "PC de sécurité";
		if (x == 6)
			nom = "Supermarché";
		Evenement.nomPhase(International.trad("text.phaseattaquelieu") + "" + nom);
		List<PionCouleur> ltemp = new ArrayList<>();
		for (PionCouleur pc : l) {
			if (IdjrTools.getCouleurByChar(pc) == core.getCouleur()) {
				ltemp.add(pc);
			}
		}
		core.setPoinSacrDispo(ltemp);
	}

	public PionCouleur choisirSacrifice(Idjr core, List<?> listPionT) {
		List<Integer> listPion = new ArrayList<>();
		for (Object o : listPionT)
			listPion.add((Integer) o);
		Evenement.sacrificeChange();
		Evenement.choisirPion(listPion);
		while (!core.pionDisponible())
			Thread.yield();
		int pionInt = core.getPionChoisi();
		core.pionChoisi(false);
		Evenement.deplacementChange();
		PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionInt);
		return pion;
	}

	public void finPartie(Idjr core, Couleur gagnant) {
		out.println("Le gagant est le joueur " + gagnant + " !");
		// getControleurReseau().arreter();
		Evenement.gagnant(core.getListeJoueursInitiale().get(gagnant));
		Evenement.fin();
		core.setEstFini(true);
	}

	public List<Object> listeCarteJouee(Idjr core, int n) {
		List<Object> listRenvoye = new ArrayList<>();
		List<CarteType> listeCarteJouee = new ArrayList<>();
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<CarteType> listeCarteUtilisable = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
		List<PionCouleur> personnagesCachable = core.getPoinSacrDispo();
		Random r = new Random();
		int indexCarteJouee;
		int nbCarteJouee = 0;
		for (CarteType carte : core.getListeCarte()) {
			if (n != 4) {
				if((n==6)&&(core.getNbZombieLieu()<4)) {
					if (carte.name() == "MAT") {
						listeCarteUtilisable.add(carte);
					}
				}
				
			}
			switch (carte.name()) {
			case "ACS":
			case "ATR":
			case "AGR":
			case "ARE":
			case "AHI":
			case "ABA":
				listeCarteUtilisable.add(carte);
				break;
			case "CAC":
				listeCarteCachette.add(carte);
				break;
			}
		}
		while (listeCarteCachette.size() > personnagesCachable.size())
			listeCarteCachette.remove(0);
		listeCarteUtilisable.addAll(listeCarteCachette);
		if (!listeCarteUtilisable.isEmpty()) {
			Evenement.choisirUtiliserCartes(listeCarteUtilisable);
			while (!core.utiliserCartesDisponible())
				Thread.yield();
			core.utiliserCartesChoisi(false);
			List<CarteType> tmpCarteType = core.getUtiliserCartesChosi();
			listeCarteJouee.addAll(tmpCarteType);
			core.getListeCarte().removeAll(tmpCarteType);
			Evenement.updateCarte();
			listeCarteUtilisable.removeAll(tmpCarteType);
		}

		int cartesCachette = 0;
		for (CarteType carte : listeCarteJouee)
			if (carte == CarteType.CAC)
				cartesCachette++;

		for (int i = 0; i < cartesCachette; i++) {
			Evenement.choisirPion(IdjrTools.getPionsByValues(personnagesCachable));
			while (!core.pionDisponible())
				Thread.yield();
			core.pionChoisi(false);
			PionCouleur tmp = PionCouleur.valueOf(core.getCouleur().name() + core.getPionChoisi());
			personnagesCachable.remove(tmp);
			listePionCache.add(tmp);
		}

		listRenvoye.add(listeCarteJouee);
		listRenvoye.add(IdjrTools.getPionsByValues(listePionCache));
		return listRenvoye;
	}

	public List<PionCouleur> listePionCache(Idjr core) {
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
		List<CarteType> carteJouees = new ArrayList<>();

		for (CarteType carte : core.getListeCarte()) {
			if (carte.name() == "CAC") {
				listeCarteCachette.add(carte);
			}
		}
		if (listeCarteCachette.isEmpty())
			return listePionCache;
		Random r = new Random();
		int nbrCartePossibleDejouer = listeCarteCachette.size();
		int nbrPion = core.getPoinSacrDispo().size();
		int nbrCarteJouee;
		// TODO choix nombre carte cachette jouée

		while (core.isContinue() && nbrCartePossibleDejouer > 0 && nbrPion > 0) {
			Evenement.choisirUtiliserCarte(CarteType.CAC);
			while (!core.utiliserCarteDisponible())
				Thread.yield();
			core.utiliserCarteChoisi(false);

			carteJouees.add(core.getUtiliserCarteChosi());
			nbrCartePossibleDejouer--;
			nbrPion--;
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		core.setContinue(true);

		return listePionCache;
	}

	public CarteType ReponseJoueurCourant(Idjr core) {
		// TODO choisir utilisé carte caméra sécuré
		CarteType RJ = CarteType.NUL;
		if (core.getListeCarte().contains(CarteType.CDS)) {
			Evenement.choisirUtiliserCarte(CarteType.CDS);
			while (!core.utiliserCarteDisponible())
				Thread.yield();
			core.utiliserCarteChoisi(false);

			RJ = core.getUtiliserCarteChosi();
			if (RJ == null)
				RJ = CarteType.NUL;

			if (RJ == CarteType.CDS)
				core.getListeCarte().remove(CarteType.CDS);
			Evenement.updateCarte();
		}

		return RJ;
	}

	public int IndiquerCarteJouees(Idjr core) {
		// TODO choix carte vote
		List<CarteType> carteMenJouees = new ArrayList<>();

		if (core.getListeCarte().isEmpty())
			return 0;
		CarteType carteSelec = CarteType.NUL;

		while (core.isContinue() && core.getListeCarte().contains(CarteType.MEN)) {
			Evenement.choisirUtiliserCarte(CarteType.MEN);
			while (!core.utiliserCarteDisponible())
				Thread.yield();

			core.utiliserCarteChoisi(false);
			carteSelec = core.getUtiliserCarteChosi();
			if (carteSelec == CarteType.MEN) {
				carteMenJouees.add(carteSelec);
				core.getListeCarte().remove(carteSelec);
				Evenement.updateCarte();
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		core.setContinue(true);

		return carteMenJouees.size();
	}

	public Couleur getRandom(Idjr core, VoteType vt) {
		// TODO choisir couleur qu'on souhaite voter en fonction du VoteType
		System.out.println("Joueur Présent:" + core.couleurJoueurPresent().size());

		Evenement.setVote(core.couleurJoueurPresent());
		while (!core.voteDisponible())
			Thread.yield();

		core.voteChoisi(false);

		return core.getVoteChoisi();
	}

	public Couleur getRandom2(Idjr core, VoteType vt) {
		// TODO choisir couleur a qui on veut donner une carte.
		Evenement.setVote(core.couleurJoueurPresent());
		while (!core.voteDisponible())
			Thread.yield();

		core.voteChoisi(false);

		return core.getVoteChoisi();

	}

	public List<Object> carteFouille(List<CarteType> listeCarte, Idjr idjr) {
		CarteType carteGarde = CarteType.NUL;
		CarteType carteOfferte = CarteType.NUL;
		CarteType carteDefausse = CarteType.NUL;
		Couleur couleur = Couleur.NUL;
		List<Object> carteChoisies = new ArrayList<Object>();
		List<Couleur> listJoueurDispo = idjr.getJoueurEnVie();
		listJoueurDispo.remove(idjr.getCouleur());
		if (listeCarte.size() == 3) {

			Evenement.choisirCarte(listeCarte, idjr.getJoueurEnVie(), true, false, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteGarde = idjr.getCarteChoisi();
			listeCarte.remove(carteGarde);
			idjr.addCarte(carteGarde);

			Evenement.choisirCarte(listeCarte, listJoueurDispo, false, true, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteOfferte = idjr.getCarteChoisi();
			listeCarte.remove(carteOfferte);

			Evenement.choisirCarte(listeCarte, idjr.getJoueurEnVie(), false, false, true, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);

			carteDefausse = idjr.getCarteChoisi();
			listeCarte.remove(carteDefausse);
			couleur = idjr.getCouleurChoisi();
		}
		if (listeCarte.size() == 2) {
			Evenement.choisirCarte(listeCarte, idjr.getJoueurEnVie(), true, false, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteGarde = idjr.getCarteChoisi();
			idjr.addCarte(carteGarde);
			listeCarte.remove(carteGarde);

			Evenement.choisirCarte(listeCarte, listJoueurDispo, false, true, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);

			carteOfferte = idjr.getCarteChoisi();
			listeCarte.remove(carteOfferte);

			couleur = idjr.getCouleurChoisi();
		}
		if (listeCarte.size() == 1) {
			Evenement.choisirCarte(listeCarte, idjr.getJoueurEnVie(), true, false, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteGarde = idjr.getCarteChoisi();
			idjr.addCarte(carteGarde);
		}
		carteChoisies.add(carteGarde);
		carteChoisies.add(carteOfferte);
		carteChoisies.add(carteDefausse);
		carteChoisies.add(couleur);

		return carteChoisies;
	}
}
