package idjr;

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

public class TraitementIdjr {

	public void initialiserPartie(Idjr core, List<?> nomsT, List<Couleur> couleursT, int lieuferme) {
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
		if (lieuferme == 2) {
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

	public List<Integer> pionADeplacer(Idjr core, int dest, HashMap<Integer, List<Integer>> listedp) {
		// TODO gérer carte sprint
		List<Integer> destEtPion = new ArrayList<>();
		List<Integer> listePion = new ArrayList<>();
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
		destEtPion.add(dest);
		destEtPion.add(pionAdep);
		return destEtPion;
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

	public List<CarteType> listeCarteJouee(Idjr core, int n) {
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
		if (listeCarteUtilisable.isEmpty())
			return listeCarteJouee;
		//TODO choisir carte parmis carte utilisable
		int nbCarteJouee = r.nextInt(listeCarteUtilisable.size());
		for (int i = 0; i < nbCarteJouee; i++) {
			indexCarteJouee = r.nextInt(listeCarteUtilisable.size());
			listeCarteJouee.add(listeCarteUtilisable.get(indexCarteJouee));
			core.getListeCarte().remove(listeCarteUtilisable.get(indexCarteJouee));
			//TODO Mettre a jour liste carte
			listeCarteUtilisable.remove(indexCarteJouee);
		}

		return listeCarteJouee;
	}

	public List<PionCouleur> listePionCache(Idjr core) {
		//TODO modifier car faux (cachette tiré 2 fois) voir méthode listeCarteJouee
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
		//TODO choix nombre carte cachette jouée
		if (nbrCartePossibleDejouer > nbrPion) {
			nbrCarteJouee = r.nextInt(nbrPion); 
		} else {
			nbrCarteJouee = r.nextInt(nbrCartePossibleDejouer);
		}

		for (int i = 0; i < nbrCarteJouee; i++) {
			listePionCache.add(core.getListePion().get(i));
		}

		return listePionCache;
	}

	public CarteType ReponseJoueurCourant(Idjr core) {
		//TODO Choix utiliser carte caméra de sécurité
		CarteType[] RJListe = CarteType.values();
		int rand = new Random().nextInt(1);
		CarteType RJ = RJListe[rand];
		return RJ;
	}

	public int IndiquerCarteJouees(Idjr core) {
		//TODO choix carte vote
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
		//TODO choisir couleur qu'on souhaite voter en fonction du VoteType
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

	public Couleur getRandom2(Idjr core, VoteType vt) {
		//TODO choisir couleur a qui on veut donner une carte.
		int rand = new Random().nextInt(core.getJoueurEnVie().size());
		return core.getJoueurEnVie().get(rand);

	}

	public List<Object> carteFouille(List<CarteType> listeCarte, Idjr idjr) {
		CarteType carteGarde = CarteType.NUL;
		CarteType carteOfferte = CarteType.NUL;
		CarteType carteDefausse = CarteType.NUL;
		Couleur couleur = Couleur.NUL;
		List<Object> carteChoisies = new ArrayList<Object>();
		if (listeCarte.size() == 3) {
			carteGarde = listeCarte.get(0);
			idjr.addCarte(carteGarde);
			//TODO mettre a jour liste carte
			carteOfferte = listeCarte.get(1);
			carteDefausse = listeCarte.get(2);
			System.out.print("carte fouille");
			couleur = getRandom2(idjr, VoteType.FDC);
		}
		if (listeCarte.size() == 2) {
			carteGarde = listeCarte.get(0);
			idjr.addCarte(carteGarde);
			//TODO mettre a jour liste carte
			System.out.print("carte fouille");
			System.out.print("4" + idjr.couleurJoueurPresent().size());
			couleur = getRandom2(idjr, VoteType.FDC);
		}
		if (listeCarte.size() == 1) {
			carteGarde = listeCarte.get(0);
			idjr.addCarte(carteGarde);
			//TODO mettre a jour liste carte
		}
		carteChoisies.add(carteGarde);
		carteChoisies.add(carteOfferte);
		carteChoisies.add(carteDefausse);
		carteChoisies.add(couleur);

		return carteChoisies;
	}
}
