package bot;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private void initialiserPartie(Bot core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
		List<String> noms = new ArrayList<>();
		List<Couleur> couleurs = new ArrayList<>();
		core.setJoueurEnVie((couleursT));
		for (Object o : nomsT)
			noms.add((String) o);
		for (Couleur o : couleursT)
			couleurs.add((Couleur) o);
		core.setCouleur(BotOutils.getCouleurByName(core.getNom(), noms, couleurs));
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
		core.initPartie(couleurs);
	}

	/**
	 * Lancement des dés
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une liste d'integer correspondant aux pions
	 */
	private void lancerDes(Bot core, List<?> pionT) {
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);
		core.setPionAPos(pion);
	}


	/**
	 * Debut du tour
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une liste de couleur correspondant aux joueurs
	 */

	private void debutTour(Bot core, List<Couleur> couleurs) {
		core.newTour();
		core.setJoueurEnVie(couleurs);
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}


	/**
	 * Debut du deplacement
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param une liste d'integer correspondant aux lieux ouvert
	 */

	private void debutDeplacemant(Bot core, List<?> lieuxT) {
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
		core.setLieuxFerme(lieux);
	}



	/**
	 * Attaque des zombies
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une liste de pionCouleur
	 * @param Une autre liste de PionCouleur (temporaire)
	 * @return la liste des personnage sacrifiable après l'attaque
	 */

	private void attaqueZombie(Bot core, List<PionCouleur> l, int n) {
		core.setNbZombieSurLieux(n);
		List<PionCouleur> ltemp = new ArrayList<PionCouleur>();
		for (PionCouleur pc : l) {
			if (BotOutils.getCouleurByChar(pc) == core.getCouleur()) {
				ltemp.add(pc);
			}
		}
		core.setPoinSacrDispo(ltemp);
	}


	/**
	 * Fin de la partie
	 *
	 * @param Bot pour lequel le traitement s'effectue
	 * @param Une couleur du joueur gagnant
	 */

	private void finPartie(Bot core, Couleur gagnant) {
		out.println("Le gagant est le joueur " + gagnant + " !");
		core.setEstFini(true);
		// getControleurReseau().arreter();
	}

	

	public CarteType traitementAZDCS(Bot core) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.reponseJoueurCourantAleatoire(core);
		else
			return TraitementIntelligent.reponseJoueurCourantIntelligent(core);
	}

	public Couleur traitementPVDV(Bot core, VoteType vt) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.voteAleatoire(core,vt);
		else
			return TraitementIntelligent.voteIntelligent(core,vt);
	}



	public List<Object> traitementRAZDD(Bot core, int n) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.listeCarteJoueeAleatoire(core, n);
		else
			return TraitementIntelligent.listeCarteJoueeIntelligent(core, n);
	}

	public void traitementIP(Bot core, List<?> nomsT, List<Couleur> couleursT, String lieuferme) {
		initialiserPartie(core, nomsT, couleursT, lieuferme);
	}

	public void traitementPIIJ(Bot core, List<?> pionT) {
		lancerDes(core, pionT);
	}

	public List<Integer> traitementPIDR(Bot core, List<?> destRestanteT) {
		List<Integer> choix = new ArrayList<>();
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| ((core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))) {
			choix.add(TraitementAleatoire.choisirDestPlacementAleatoire(destRestanteT));
			choix.add(TraitementAleatoire.choisirPionPlacementAleatoire(core));
		} else {
			choix.add(TraitementIntelligent.choisirDestPlacementIntelligent(core, destRestanteT));
			choix.add(TraitementIntelligent.choisirPionPlacementIntelligent(core));
		}
		return choix;
	}

	public void traitementIT(Bot core, List<Couleur> couleurs) {
		debutTour(core, couleurs);
	}

	public int traitementPCD(Bot core) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.choixDestAleatoire(core);
		else
			return TraitementIntelligent.choixDestIntelligent(core);
	}

	public int traitementCDCDV(Bot core) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.choixDestAleatoire(core);
		else
			return TraitementIntelligent.choixDestIntelligent(core);
	}

	public int traitementCDZVI(Bot core) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.choixZDestAleatoire(core);
		else
			return TraitementIntelligent.choixZDestIntelligent(core);

	}

	public void traitementPDP(Bot core, List<?> lieuxT) {
		debutDeplacemant(core, lieuxT);
	}

	public List<Object> traitementDPD(Bot core, int dest, HashMap<Integer, List<Integer>> listedp) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.pionADeplacerAleatoire(core, dest, listedp);
		else
			return TraitementIntelligent.pionADeplacerIntelligent(core, dest, listedp);

	}

	public void traitementRAZA(Bot core, List<PionCouleur> listelp,int zomb) {
		attaqueZombie(core, listelp,zomb);
	}

	public PionCouleur traitementRAZDS(Bot core, List<?> arg2) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.choisirSacrificeAleatoire(core, arg2);
		else
			return TraitementAleatoire.choisirSacrificeAleatoire(core, arg2);
	}

	public void traitementFP(Bot core, Couleur couleur) {
		finPartie(core, couleur);
	}

	public int traitementPVD(Bot core) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.IndiquerCarteJoueesAleatoire(core);
		else
			return TraitementIntelligent.IndiquerCarteJoueesIntelligent(core);

	}

	public List<Object> traitementFCLC(List<CarteType> listeCarte, Bot core) {
		if ((core.getBotType().equals(BotType.FAIBLE))
				|| (core.getBotType().equals(BotType.MOYEN)) && (core.getCompteurTour() % 2 == 1))
			return TraitementAleatoire.carteFouilleAleatoire(listeCarte, core);
		else
			return TraitementIntelligent.carteFouilleIntelligent(listeCarte, core);
	}
}
