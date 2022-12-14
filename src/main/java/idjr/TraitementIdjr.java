package idjr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import idjr.ihmidjr.event.Evenement;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VoteType;

public class TraitementIdjr {

	public void initialiserPartie(Idjr core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
		Evenement.stopWait();
		Evenement.nomPhases("text.placeperso");
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
		List<Integer> lieuDispo = new ArrayList<>();
		int premierpion =0;
		lieuDispo.addAll(core.getLieuOuvert());
		boolean memendroit = true;
		int v1 = 0;
		for(int pion : core.getPersolieu().keySet()) {
			if (premierpion == 0) {
				premierpion = pion;
			}
		}
		for (int dest : core.getPersolieu().values()) {
			if(v1 == 0)
				v1 = dest;
			if (dest != v1)
				memendroit = false;
		}
		if (memendroit)
			lieuDispo.remove(core.getPersolieu().get(premierpion));

		Evenement.choisirLieu(lieuDispo);
		while (!core.lieuDisponible())
			Thread.yield();
		int dest = core.getLieuChoisi();
		core.lieuChoisi(false);
		return dest;
	}

	public void debutDeplacemant(Idjr core, List<?> lieuxT) {
		Evenement.nomPhases("text.phasedeplperso");
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
	}

	public List<Object> pionADeplacer(Idjr core, int dest, HashMap<Integer, List<Integer>> listedp) {
		int destinit = dest;
		List<Object> listRenvoye = new ArrayList<>();
		List<Integer> listePion = new ArrayList<>();
		List<Integer> destPossible = new ArrayList<>();
		CarteType sprintJoue = CarteType.NUL;
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				destPossible.add(destPos);
		if (core.getListeCarte().contains(CarteType.SPR)) {
			Evenement.choisirUtiliserCarte(CarteType.SPR);
			while (!core.utiliserCarteDisponible())
				Thread.yield();
			sprintJoue = core.getUtiliserCarteChosi();
			core.utiliserCarteChoisi(false);
			if (sprintJoue == null)
				sprintJoue = CarteType.NUL;
			if (sprintJoue == CarteType.SPR) {
				core.getListeCarte().remove(sprintJoue);
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
			for (int i : core.getPersolieu().keySet())
				if (core.getPersolieu().get(i) != destinit)
					listePion.add(i);
			if (!listePion.isEmpty()) {
				Evenement.choisirPion(listePion);
				while (!core.pionDisponible())
					Thread.yield();
				pionAdep = core.getPionChoisi();
				core.pionChoisi(false);
			} else {
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
		Evenement.nomPhases("text.phaseresatazom");
	}

	public void attaqueZombie(Idjr core, List<PionCouleur> l, int x, int zombie) {
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
			nom = "PC de s??curit??";
		if (x == 6)
			nom = "Supermarch??";
		Evenement.nomPhases("text.phaseattaquelieu", nom);
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
		for (CarteType carte : core.getListeCarte()) {
			if (n != 4) {
				if ((n == 6) && (core.getNbZombieLieu() < 4)) {
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

	public CarteType ReponseJoueurCourant(Idjr core) {
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
		List<CarteType> menPossede = new ArrayList<>();
		List<CarteType> carteSelec = new ArrayList<>();

		for (CarteType ct : core.getListeCarte())
			if (ct == CarteType.MEN)
				menPossede.add(ct);

		if (core.getListeCarte().isEmpty() || menPossede.isEmpty())
			return 0;

		Evenement.choisirUtiliserCartes(menPossede);
		while (!core.utiliserCartesDisponible())
			Thread.yield();

		core.utiliserCartesChoisi(false);
		carteSelec = core.getUtiliserCartesChosi();

		core.getListeCarte().removeAll(carteSelec);
		Evenement.updateCarte();
		try {
			Thread.sleep(500);
		} catch (Exception e) {
		}
		core.setContinue(true);

		return carteSelec.size();
	}

	public Couleur getRandom(Idjr core, VoteType vt) {
		Evenement.setVote(core.couleurJoueurPresent());
		while (!core.voteDisponible())
			Thread.yield();

		core.voteChoisi(false);

		return core.getVoteChoisi();
	}

	public Couleur getRandom2(Idjr core, VoteType vt) {
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
