package idjr;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import idjr.ihmidjr.event.JeuListener;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VoteType;

public class TraitementIdjr {

	public void initialiserPartie(Idjr core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
		if (core.getInitializer() != null) {
			core.getInitializer().stopWait();
			core.getInitializer().nomPhase("Placement des personnages");
		}
		List<String> noms = new ArrayList<>();
		List<Couleur> couleurs = new ArrayList<>();
		core.setJoueurEnVie((couleursT));
		for (Object o : nomsT)
			noms.add((String) o);
		for (Couleur o : couleursT)
			couleurs.add((Couleur) o);
		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
		if (core.getInitializer() != null)
			core.getInitializer().couleurJoueur(core.getCouleur());
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
		core.getInitializer().desValeur(des);
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		core.getInitializer().choisirLieu(destRestant);
		while (!core.lieuDisponible())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		int dest = core.getLieuChoisi();
		core.lieuChoisi(false);
		return dest;
	}

	public int choisirPionPlacement(Idjr core) {
		core.getInitializer().choisirPion(core.getPionAPos());
		while (!core.pionDisponible())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
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
		core.getInitializer().choisirLieu(core.getLieuOuvert());
		while (!core.lieuDisponible())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		int dest = core.getLieuChoisi();
		core.lieuChoisi(false);
		return dest;
	}

	public void debutDeplacemant(Idjr core, List<?> lieuxT) {
		if (core.getInitializer() != null)
			core.getInitializer().nomPhase("Phase de déplacement des personnages");
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
			
			core.getInitializer().choisirUtiliserCarte();
			while (!core.utiliserCarteDisponible())
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
			sprintJoue = core.getUtiliserCarteChosi();
			core.utiliserCarteChoisi(false);
			// TODO choisir destination carte sprint parmis destPossible;
			core.getInitializer().choisirLieu(core.getLieuOuvert());
			while (!core.lieuDisponible())
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
			dest = core.getLieuChoisi();
			core.lieuChoisi(false);
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
				core.getInitializer().choisirPion(listePion);
				while (!core.pionDisponible())
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						// TODO: handle exception
					}
				pionAdep = core.getPionChoisi();
				core.pionChoisi(false);
			} else {
				// TODO Affiche PAS DE PERSO A DEPLACE
				pionAdep = new ArrayList<Integer>(listedp.keySet()).get(0);
			}
		} else {
			core.getInitializer().choisirPion(listePion);
			while (!core.pionDisponible())
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
			pionAdep = core.getPionChoisi();
			core.pionChoisi(false);
		}
		listRenvoye.add(dest);
		listRenvoye.add(pionAdep);
		listRenvoye.add(sprintJoue);
		return listRenvoye;
	}

	public void debutPhaseAttaque(Idjr core) {
		if (core.getInitializer() != null)
			core.getInitializer().nomPhase("Phase de résolution de l’attaque des zombies");
	}

	public void attaqueZombie(Idjr core, List<PionCouleur> l) {
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
		core.getInitializer().sacrificeChange();
		core.getInitializer().choisirPion(listPion);
		while (!core.pionDisponible())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		int pionInt = core.getPionChoisi();
		core.pionChoisi(false);
		core.getInitializer().deplacementChange();
		PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionInt);
		return pion;
	}

	public void finPartie(Idjr core, Couleur gagnant) {
		out.println("Le gagant est le joueur " + gagnant + " !");
		core.setEstFini(true);
		// getControleurReseau().arreter();
		if (core.getInitializer() != null) {
			core.getInitializer().fin();
			core.getInitializer().gagnant(core.getListeJoueursInitiale().get(gagnant));
		}
		// TODO ATTENTION A LA FIN PROGRAMME
	}

	public List<Object> listeCarteJouee(Idjr core, int n) {
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
			case "CAC":
				listeCarteUtilisable.add(carte);
				break;
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
			// TODO choisir carte parmis carte utilisable
			nbCarteJouee = r.nextInt(listeCarteUtilisable.size());
			for (int i = 0; i < nbCarteJouee; i++) {
				indexCarteJouee = r.nextInt(listeCarteUtilisable.size());
				listeCarteJouee.add(listeCarteUtilisable.get(indexCarteJouee));
				core.getListeCarte().remove(listeCarteUtilisable.get(indexCarteJouee));
				core.initializer.updateCarte();
				// TODO Mettre a jour liste carte
				listeCarteUtilisable.remove(indexCarteJouee);
			}
		}
		List<PionCouleur> listePionCache = listePionCache(core);
		int i = 0;
		while (i < listePionCache.size()) {
			listeCarteJouee.add(CarteType.CAC);
			core.getListeCarte().remove(CarteType.CAC);
			core.initializer.updateCarte();
			i++;
		}
		listRenvoye.add(listeCarteJouee);
		listRenvoye.add(listePionCache);
		return listRenvoye;
	}

	public List<PionCouleur> listePionCache(Idjr core) {
		List<CarteType> listeCarteCachette = new ArrayList<>();
		List<PionCouleur> listePionCache = new ArrayList<>();
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
		if (nbrCartePossibleDejouer > nbrPion)
			nbrCarteJouee = r.nextInt(nbrPion);
		else
			nbrCarteJouee = r.nextInt(nbrCartePossibleDejouer);
		for (int i = 0; i < nbrCarteJouee; i++)
			listePionCache.add(core.getListePion().get(i));
		return listePionCache;
	}

	public CarteType ReponseJoueurCourant(Idjr core) {
		// TODO choisir utilisé carte caméra sécuré
		CarteType RJ = CarteType.NUL;
		if (core.getListeCarte().contains(CarteType.CDS))
			if (new Random().nextInt(1) == 1) {
				RJ = CarteType.CDS;
				core.getListeCarte().remove(CarteType.CDS);
				core.initializer.updateCarte();
			}
		return RJ;
	}

	public int IndiquerCarteJouees(Idjr core) {
		// TODO choix carte vote
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

	public Couleur getRandom(Idjr core, VoteType vt) {
		// TODO choisir couleur qu'on souhaite voter en fonction du VoteType
		int rand;
		if (vt == VoteType.MPZ) {
			System.out.println("Joueur Présent:" + core.couleurJoueurPresent().size());
			core.couleurJoueurPresent().remove(core.getCouleur());

			core.getInitializer().setVote(core.couleurJoueurPresent());
			while (!core.voteDisponible())
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}

			core.voteChoisi(false);
			
			return core.getVoteChoisi();
		}
		
		core.getInitializer().setVote(core.couleurJoueurPresent());
		while (!core.voteDisponible())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}

		core.voteChoisi(false);
		
		return core.getVoteChoisi();
	}

	public Couleur getRandom2(Idjr core, VoteType vt) {
		// TODO choisir couleur a qui on veut donner une carte.
		core.getInitializer().setVote(core.couleurJoueurPresent());
		while (!core.voteDisponible())
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}

		core.voteChoisi(false);
		
		return core.getVoteChoisi();

	}

	public List<Object> carteFouille(List<CarteType> listeCarte, Idjr idjr) {
		CarteType carteGarde = CarteType.NUL;
		CarteType carteOfferte = CarteType.NUL;
		CarteType carteDefausse = CarteType.NUL;
		Couleur couleur = Couleur.NUL;
		List<Object> carteChoisies = new ArrayList<Object>();
		if (listeCarte.size() == 3) {

			idjr.getInitializer().choisirCarte(listeCarte, idjr.getJoueurEnVie(), true, false, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteGarde = idjr.getCarteChoisi();
			listeCarte.remove(carteGarde);
			idjr.addCarte(carteGarde);

			idjr.getInitializer().choisirCarte(listeCarte, idjr.getJoueurEnVie(), false, true, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteOfferte = idjr.getCarteChoisi();
			listeCarte.remove(carteOfferte);

			idjr.getInitializer().choisirCarte(listeCarte, idjr.getJoueurEnVie(), false, false, true, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);

			carteDefausse = idjr.getCarteChoisi();
			listeCarte.remove(carteDefausse);
			couleur = idjr.getCouleurChoisi();
		}
		if (listeCarte.size() == 2) {
			idjr.getInitializer().choisirCarte(listeCarte, idjr.getJoueurEnVie(), true, false, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);
			carteGarde = idjr.getCarteChoisi();
			idjr.addCarte(carteGarde);
			listeCarte.remove(carteGarde);

			idjr.getInitializer().choisirCarte(listeCarte, idjr.getJoueurEnVie(), false, true, false, true);
			while (!idjr.carteDisponible())
				Thread.yield();

			idjr.carteChoisi(false);

			carteOfferte = idjr.getCarteChoisi();
			listeCarte.remove(carteOfferte);

			couleur = idjr.getCouleurChoisi();
		}
		if (listeCarte.size() == 1) {
			idjr.getInitializer().choisirCarte(listeCarte, idjr.getJoueurEnVie(), true, false, false, true);
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
