package idjr;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteType;
import reseau.type.CondType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.RaisonType;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import static java.lang.System.out;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import idjr.ihmidjr.event.Initializer;

/**
 * <h1>Permet de gerer les Paquets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<TcpClient> {
	private Idjr core;
	private TraitementIdjr traitementI;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (Idjr) core;
		this.traitementI = new TraitementIdjr();
	}

	@Override
	public void init() {

	}

	/**
	 * Traitement des paquets TCP
	 *
	 * @param Paquet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Paquet Paquet, String message, TcpClient extra) {
		switch (Paquet.getCle()) {
		// TODO gérer paquet DC
		case "IP":
			initialiserPartie(Paquet, message);
			logIP(Paquet, message);
			break;
		case "DC":
			recupCarte(Paquet, message);
			logDC(Paquet, message);
			break;
		case "PIIJ":
			lancerDes(Paquet, message);// savoir comment return plusieur choses
			logPIIJ(Paquet, message);
			break;
		case "PIRD":
			choisirDestPion(Paquet, message);
			logPIRD(Paquet, message);
			break;
		case "PIIG":
			logPIIG(Paquet, message);
			break;
		case "IT":
			debutTour(Paquet, message);
			logIT(Paquet, message);
			break;
		case "PAZ":
			lanceDesChefVigil(Paquet, message);
			logPAZ(Paquet, message);
			break;
		case "PCD":
			choixDestVigil(Paquet, message);
			logPCD(Paquet, message);
			break;
		case "CDCDV":
			choisirDest(Paquet, message);
			logCDCDV(Paquet, message);
			break;
		case "CDZVI":
			destZombieVengeur(Paquet, message);
			logCDZVI(Paquet, message);
			break;
		case "PDP":
			debutDeplacemant(Paquet, message);
			logPDP(Paquet, message);
			break;
		case "DPD":
			deplacerPion(Paquet, message);
			logDPD(Paquet, message);
			break;
		case "DPI":
			logDPI(Paquet, message);
			break;
		case "PRAZ":
			debutPhaseAttaque(Paquet, message);
			logPRAZ(Paquet, message);
			break;
		case "RAZA":
			attaqueZombie(Paquet, message);
			logRAZA(Paquet, message);
			break;
		case "RAZDS":
			choisirSacrifice(Paquet, message);
			logRAZDS(Paquet, message);
			break;
		case "RAZIF":
			logRAZIF(Paquet, message);
			break;
		case "FP":
			finPartie(Paquet, message);
			logFP(Paquet, message);
			break;
		case "RAZDD":
			fournirActionsDefense(Paquet, message);
			logRAZDD(Paquet, message);
			break;
		case "PVDV":
			ChoisirQuiVoter(Paquet, message);
			logPVDV(Paquet, message);
			break;
		case "AZDCS":
			ReponseJoueurCourant(Paquet, message);
			logAZDCS(Paquet, message);
			break;
		case "PVD":
			IndiquerCarteJouees(Paquet, message);
			logPVD(Paquet, message);
			break;
		case "FCRC":
			recupCarte(Paquet, message);
			logFCRC(Paquet, message);
			break;
		case "PIPZ":
			placementzombie(Paquet, message);
			logPIPZ(Paquet, message);
			break;
		case "PFC":
			phaseFouilleCamion();
			logPFC(Paquet, message);
			break;
		case "FCLC":
			choixCarteFouille(Paquet, message);
			logFCLC(Paquet, message);
			break;
		case "RFC":
			logRFC(Paquet, message);
			break;
		case "PECV":
			phaseElectionChefVigile(Paquet, message);
			logPECV(Paquet, message);
			break;
		case "RECV":
			logRECV(Paquet, message);
			break;
		case "CDFC":
			logCDFC(Paquet, message);
			break;
		case "CDZVDI":
			arrivezombies(Paquet, message);
			logCDZVDI(Paquet, message);
			break;
		case "AZUCS":
		case "AZLAZ":
			arrivezombies(Paquet, message);
			logAZUCSvAZLAZ(Paquet, message);
			break;
		case "AZICS":
			logAZICS(Paquet, message);
			break;
		case "PVIC":
			logPVIC(Paquet, message);
			break;
		case "PVR":
			logPVR(Paquet, message);
			break;
		case "PVVC":
			logPVVC(Paquet, message);
			break;
		case "RAZPA":
			logRAZPA(Paquet, message);
			break;
		case "RAZID":
			logRAZID(Paquet, message);
			break;
		case "ACP":
			break;
		case "IPV":
			recupInfoVote(Paquet, message);
			logIPV(Paquet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", Paquet.getCle()));
		}
	}

	private void logPVR(Paquet paquet, String message) {
		String log;
		if (((Couleur) paquet.getValeur(message, 1)).equals(Couleur.NUL))
			log = "Le résultat du vte est une égalité donc aucun joueur n'est choisit.";
		else
			log = "Le joueur choisit est le joueur " + ((Couleur) paquet.getValeur(message, 1)).toString() + ".";
		Initializer.log(log);

	}

	private void logPVVC(Paquet paquet, String message) {
		String log = "Le vote est clos.";
		Initializer.log(log);

	}

	private void logPVIC(Paquet paquet, String message) {
		String log = "Le joueur " + ((Couleur) paquet.getValeur(message, 2)).toString() + " joue "
				+ (Integer) paquet.getValeur(message, 2) + " carte menace. Il a donc "
				+ (Integer) paquet.getValeur(message, 3) + " voix pour ce vote.";
		Initializer.log(log);

	}

	private void logRAZID(Paquet paquet, String message) {
		List<CarteType> carteJouee = (List<CarteType>) paquet.getValeur(message, 3);
		List<Integer> pionCachee = (List<Integer>) paquet.getValeur(message, 4);
		String log = "Le joueur " + ((Couleur) paquet.getValeur(message, 2)).toString() + " joue les cartes : ";
		for (int i = 0; i < carteJouee.size() - 1; i++)
			log += carteJouee.get(i).toString() + ", ";
		log += carteJouee.get(carteJouee.size() - 1).toString();

		log += " pour la défence du lieu " + IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";

		if (!pionCachee.isEmpty()) {
			log += "Les pions cachées sont ";
			for (int i = 0; i < pionCachee.size() - 1; i++)
				log += pionCachee.get(i).toString() + ", ";
			log += pionCachee.get(pionCachee.size() - 1).toString() + ".";
		} else
			log += "Il n'y a pas de pion cachée.";

		log += " Il reste " + (Integer) paquet.getValeur(message, 6) + " zombies sur le lieu.";
		Initializer.log(log);
	}

	private void logRAZPA(Paquet paquet, String message) {
		String log = "Il n'y a pas d'attaque sur le lieu "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1));
		if (((RaisonType) paquet.getValeur(message, 3)).equals(RaisonType.PION))
			log += " car il n'y a pas de personnage.";
		if (((RaisonType) paquet.getValeur(message, 3)).equals(RaisonType.ZOMBIE))
			log += " car il n'y a pas de zombie.";
		if (((RaisonType) paquet.getValeur(message, 3)).equals(RaisonType.FORCE))
			log += " car les zombies n'ont pas la force pour entrer dans le lieu.";
		Initializer.log(log);

	}

	private void logAZICS(Paquet paquet, String message) {
		String log = "Le joueur " + ((Couleur) paquet.getValeur(message, 2)).toString();
		if (!((CarteType) paquet.getValeur(message, 2)).equals(CarteType.CDS))
			log += " a joué une carte Caméra de sécurité.";
		else
			log += " n'a pas joué de carte Caméra de sécurité.";
		Initializer.log(log);
	}

	private void logCDZVDI(Paquet paquet, String message) {
		List<Integer> lieuArriveZombie = (List<Integer>) paquet.getValeur(message, 1);
		String log = " Un zombie vengeur arrive sur chacun de ces lieux ";
		for (int i = 0; i < lieuArriveZombie.size() - 1; i++)
			log += lieuArriveZombie.get(i) + ", ";
		log += lieuArriveZombie.get(lieuArriveZombie.size() - 1) + ".";
		Initializer.log(log);
	}

	private void logAZUCSvAZLAZ(Paquet paquet, String message) {
		List<Integer> lieuArriveZombie = (List<Integer>) paquet.getValeur(message, 1);
		String log = " Un zombie arrivera sur chacun de ces lieux  ";
		for (int i = 0; i < lieuArriveZombie.size() - 1; i++)
			log += lieuArriveZombie.get(i) + ", ";
		log += lieuArriveZombie.get(lieuArriveZombie.size() - 1) + ".";
		Initializer.log(log);
	}

	private void logIPV(Paquet paquet, String message) {
		String log = " Un vote de" + ((VoteType) paquet.getValeur(message, 1)).toString() + " commence.";
		Initializer.log(log);

	}

	private void logCDFC(Paquet paquet, String message) {
		Initializer.log("La phase de choix d'une destination est fini.");

	}

	private void logRECV(Paquet paquet, String message) {
		String log = " La phase d'élection du chef des vigiles est terminé." + "\n";
		if (!((Couleur) paquet.getValeur(message, 1)).equals(Couleur.NUL))
			log += "Le nouveau chef est " + core.getListeJoueursInitiale().get(((Couleur) paquet.getValeur(message, 1)))
					+ ".";
		else
			log += "Il n'y a pas de nouveau chef des vigiles.";

		log += "Il reste " + (Integer) paquet.getValeur(message, 2) + " cartes dans la pioche.";
		Initializer.log(log);
	}

	private void logPECV(Paquet paquet, String message) {
		String log = " La phase d'élection du chef des vigiles commence." + "\n"
				+ "Les joueurs présent sur le lieux sont :" + "\n";
		for (Couleur c : (List<Couleur>) paquet.getValeur(message, 1))
			log += core.getListeJoueursInitiale().get(c) + "\n";
		log += "Il reste " + (Integer) paquet.getValeur(message, 2) + " cartes dans la pioche.";
		Initializer.log(log);

	}

	private void logRFC(Paquet paquet, String message) {
		String log = " La phase de la fouille du camion est terminé." + "\n";
		if (!((Couleur) paquet.getValeur(message, 1)).equals(Couleur.NUL))
			log += "Le joueur " + ((Couleur) paquet.getValeur(message, 1)).toString()
					+ " fouille le camion et a gardé une carte" + "\n";
		else
			log += "Aucune carte n'a été gardé" + "\n";
		if (!((Couleur) paquet.getValeur(message, 2)).equals(Couleur.NUL))
			log += "Le joueur " + ((Couleur) paquet.getValeur(message, 1)).toString() + " a reçu une carte" + "\n";
		else
			log += "Aucune carte n'a été offerte" + "\n";
		if (!((CarteType) paquet.getValeur(message, 3)).equals(CarteType.NUL))
			log += "Une carte a été défaussé";
		else
			log += "Aucune carte n'a été défaussé";

		Initializer.log(log);

	}

	private void logFCLC(Paquet paquet, String message) {
		List<CarteType> listeJC = (List<CarteType>) paquet.getValeur(message, 1);
		String log = "Vous devez choisir parmi les cartes suivante : ";
		for (int i = 0; i < listeJC.size() - 1; i++)
			log += listeJC.get(i).toString() + ", ";
		log += listeJC.get(listeJC.size() - 1).toString();
		log += " laquelle vous gardez, laquelle vous donnez, a quel joueur vous la donnez et la quelle vous defaussez.";
		Initializer.log(log);

	}

	private void logPFC(Paquet paquet, String message) {
		String log = " La phase de la fouille du camion commence." + "\n" + "Les joueurs présent sur le lieux sont :"
				+ "\n";
		for (PionCouleur c : (List<PionCouleur>) paquet.getValeur(message, 1))
			log += core.getListeJoueursInitiale().get(IdjrTools.getCouleurByChar(c)) + "\n";
		log += "Il reste " + (Integer) paquet.getValeur(message, 2) + " cartes dans la pioche.";
		Initializer.log(log);

	}

	private void logPIPZ(Paquet paquet, String message) {
		List<Integer> lieuArriveZombie = (List<Integer>) paquet.getValeur(message, 1);
		String log = " Un zombie est arrivé sur chacun de ces lieux  ";
		for (int i = 0; i < lieuArriveZombie.size() - 1; i++)
			log += lieuArriveZombie.get(i) + ", ";
		log += lieuArriveZombie.get(lieuArriveZombie.size() - 1) + ".";
		Initializer.log(log);

	}

	private void logFCRC(Paquet paquet, String message) {
		String log = "Le joueur " + ((Couleur) paquet.getValeur(message, 2)).toString() + " vous donne la carte "
				+ ((CarteType) paquet.getValeur(message, 1)).toString() + ".";
		Initializer.log(log);

	}

	private void logPVD(Paquet paquet, String message) {
		String log = "Quels cartes voulez vous jouez ?";
		Initializer.log(log);

	}

	private void logAZDCS(Paquet paquet, String message) {
		String log = "Le PP vous demande si vous voulez jouer une carte caméra de sécurité.";
		Initializer.log(log);

	}

	private void logPVDV(Paquet paquet, String message) {
		String log = "Votez pour un joueur";
		Initializer.log(log);

	}

	private void logRAZDD(Paquet paquet, String message) {
		String log = "Voulez vous utiliser une carte Defense pour l'attaque du lieu "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";
		Initializer.log(log);

	}

	private void logFP(Paquet paquet, String message) {
		String log = "La partie est fini.";
		if (((CondType) paquet.getValeur(message, 1)).equals(CondType.LIEUX))
			log += " Tous les personnages sont dans le même lieux.";
		else if (((CondType) paquet.getValeur(message, 1)).equals(CondType.PION))
			log += " Le nombre minimal de pion a été atteint.";
		Initializer.log(log);
	}

	private void logRAZIF(Paquet paquet, String message) {
		String log = "Le pion "
				+ IdjrTools.getPionByIndex(IdjrTools.getPionByValue((PionCouleur) paquet.getValeur(message, 2)))
				+ " du joueur " + IdjrTools.getCouleurByChar((PionCouleur) paquet.getValeur(message, 2))
				+ " a été mangé sur le lieu " + IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ". ";
		log += "Il reste " + (Integer) paquet.getValeur(message, 3) + " zombies sur le lieu.";
		Initializer.log(log);

	}

	private void logPRAZ(Paquet paquet, String message) {
		String log = "";
		if ((Integer) paquet.getValeur(message, 1) != 0)
			log += "Le lieu " + IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + "attire un zombie.";
		if ((Integer) paquet.getValeur(message, 2) != 0)
			log += "Le lieu " + IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 2)) + "attire un zombie.";
		Initializer.log(log);
	}

	private void logRAZDS(Paquet paquet, String message) {
		String log = "Quel pion voulez vous sacrifier sur le lieu "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";
		Initializer.log(log);

	}

	private void logRAZA(Paquet paquet, String message) {
		String log = "Il y a une attaque sur le lieu "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";
		Initializer.log(log);
	}

	private void logDPI(Paquet paquet, String message) {
		String log = "Le joueur " + ((Couleur) paquet.getValeur(message, 1)).toString() + " a déplacé le pion "
				+ IdjrTools.getPionByIndex((Integer) paquet.getValeur(message, 3)) + " sur le lieu"
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 2));
		if (!((CarteType) paquet.getValeur(message, 4)).equals(CarteType.NUL))
			log += " en utilisant une carte Sprint.";
		else
			log += " sans utiliser de carte Sprint.";

		Initializer.log(log);

	}

	private void logDPD(Paquet paquet, String message) {
		String log = "Choissisez un pion a deplacé sur le lieu "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1))
				+ " ou jouez une carte Sprint pour choisir aussi un nouveau lieu de destination.";
		Initializer.log(log);

	}

	private void logPDP(Paquet paquet, String message) {
		Initializer.log("La phase de déplacement des personnage commence.");

	}

	private void logCDZVI(Paquet paquet, String message) {
		Initializer.log("Vous etes mort au tour precedent vous pouvez déposer un zombie vengeur sur un lieu");
	}

	private void logCDCDV(Paquet paquet, String message) {
		String log = "Le chef des vigiles (Joueur " + ((Couleur) paquet.getValeur(message, 1)).toString()
				+ ") a choisit le lieu " + IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 2))
				+ " comme destination.";
		Initializer.log(log);

	}

	private void logPCD(Paquet paquet, String message) {
		Initializer.log("La phase de choix d'une destination débute.");

	}

	private void logPAZ(Paquet paquet, String message) {
		Initializer.log("La phase d'arrivée des zombies débute.");

	}

	private void logIT(Paquet paquet, String message) {
		Initializer.log("Le tour n° " + (Integer) paquet.getValeur(message, 4) + " commence.");
	}

	private void logPIIG(Paquet paquet, String message) {
		String log = "Le joueur " + ((Couleur) paquet.getValeur(message, 1)).toString() + " a placé son pion "
				+ IdjrTools.getPionByIndex((Integer) paquet.getValeur(message, 5)) + " sur le lieu : "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 4)) + ".";
		Initializer.log(log);
	}

	private void logPIRD(Paquet paquet, String message) {
		List<Integer> lieuPossible = (List<Integer>) paquet.getValeur(message, 2);
		String log = "Les résultats des dés sont " + ((List<Integer>) paquet.getValeur(message, 1)).get(0) + " et "
				+ ((List<Integer>) paquet.getValeur(message, 1)).get(1) + ".\n";
		log += "Les possibilités de placement sont ";

		for (int i = 0; i < lieuPossible.size() - 1; i++)
			log += lieuPossible.get(i) + ", ";
		log += lieuPossible.get(lieuPossible.size() - 1);
		Initializer.log(log);
	}

	private void logPIIJ(Paquet paquet, String message) {
		
		String log =  "Il vous reste à placer :";
		for (Integer i : (List<Integer>) paquet.getValeur(message, 2)) {
			log += "\n" + "Votre " + IdjrTools.getPionByIndex(i) + ".";
		}
		Initializer.log(log);
	}

	private void logDC(Paquet paquet, String message) {
		Initializer.log("Vous avez reçu la carte : " + ((CarteType) paquet.getValeur(message, 1)).toString());
	}

	private void logIP(Paquet paquet, String message) {
		Initializer.log("La partie commence!");
	}

	private void arrivezombies(Paquet paquet, String message) {
		Initializer.desVigiles((List<Integer>) paquet.getValeur(message, 1));
	}

	private void placementzombie(Paquet paquet, String message) {
		Initializer.enleverDes();
	}

	private void debutPhaseAttaque(Paquet Paquet, String message) {
		traitementI.debutPhaseAttaque(core);
	}

	private void recupInfoVote(Paquet Paquet, String message) {
		List<Couleur> couleursJoueurs = (List<Couleur>) Paquet.getValeur(message, 3);
		core.setCouleurJoueurs(couleursJoueurs);
		core.setVoteType((VoteType) Paquet.getValeur(message, 1));
	}

	public void lancerDes(Paquet Paquet, String message) {
		traitementI.lancerDes(core, (List<?>) Paquet.getValeur(message, 2));
		String m1 = (String) Paquet.getValeur(message, 3);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PILD", m1, core.getJoueurId()));
	}

	public void choisirDestPion(Paquet Paquet, String message) {
		String m1 = (String) Paquet.getValeur(message, 3);
		Integer dest = traitementI.choisirDestPlacement(core, (List<?>) Paquet.getValeur(message, 1),
				(List<?>) Paquet.getValeur(message, 2));
		Integer pion = traitementI.choisirPionPlacement(core);
		String log = "Vous avez choisit de déplacer le pion " + IdjrTools.getPionByIndex(pion) + " sur le lieu "
				+ IdjrTools.getLieuByIndex(dest) + ".";
		Initializer.log(log);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PICD", dest, pion, m1, core.getJoueurId()));
	}

	public void ChoisirQuiVoter(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		System.out.print("ChoisirQuiVoter");
		Couleur choix = traitementI.getRandom(core, core.getVoteType());
		String log = "Vous avez voté pour le joueur " + choix.toString() + ".";
		Initializer.log(log);
		String messageTcp = ControleurReseau.construirePaquetTcp("PVCV", choix, (String) Paquet.getValeur(message, 1),
				(int) Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void recupCarte(Paquet Paquet, String message) {
		core.addCarte((CarteType) Paquet.getValeur(message, 1));
		// TODO déplacer (isoler réseaux) et mettre a jour liste carte
	}

	public void IndiquerCarteJouees(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		int nbCarteJouee = traitementI.IndiquerCarteJouees(core);
		String log = "Vous jouez " + nbCarteJouee + " cartes menace.";
		Initializer.log(log);

		String messageTcp = ControleurReseau.construirePaquetTcp("PVC", nbCarteJouee,
				/* , */ (String) Paquet.getValeur(message, 1), (int) Paquet.getValeur(message, 2),
				(String) core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	public void debutTour(Paquet Paquet, String message) {
		traitementI.debutTour(core, (List<Couleur>) Paquet.getValeur(message, 2));
	}

	private void fournirActionsDefense(Paquet Paquet, String message) {
		List<Object> listerenvoye = traitementI.listeCarteJouee(this.core, (int) Paquet.getValeur(message, 1));

		List<CarteType> carteJouee = (List<CarteType>) listerenvoye.get(0);
		List<Integer> pionCachee = (List<Integer>) listerenvoye.get(1);
		String log = "Vous jouez les cartes : ";
		for (int i = 0; i < carteJouee.size() - 1; i++)
			log += carteJouee.get(i).toString() + ", ";
		log += carteJouee.get(carteJouee.size() - 1).toString();

		log += " pour la défence du lieu " + IdjrTools.getLieuByIndex((Integer) Paquet.getValeur(message, 1)) + ".";

		if (!pionCachee.isEmpty()) {
			log += "Vous cachez les pions ";
			for (int i = 0; i < pionCachee.size() - 1; i++)
				log += pionCachee.get(i).toString() + ", ";
			log += pionCachee.get(pionCachee.size() - 1).toString() + ".";
		} else
			log += "Vous ne cachez pas de pion.";
		Initializer.log(log);

		String messageTCP = ControleurReseau.construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) Paquet.getValeur(message, 2), (int) Paquet.getValeur(message, 3), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTCP);
	}

	public void lanceDesChefVigil(Paquet packet, String message) {
		// TODO réorganiser
		Initializer.nomPhase("Phase d’arrivée des zombies");
		Couleur c1 = (Couleur) packet.getValeur(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValeur(message, 3);
			int m2 = (int) packet.getValeur(message, 4);
			String log = "En tant que chef des vigiles vous lancez 4 dés pour l'arrivée des zombies.";
			Initializer.log(log);
			String messageTcp = ControleurReseau.construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);

		}
	}

	private void choixCarteFouille(Paquet Paquet, String message) {
		List<Object> listeResultat = traitementI.carteFouille((List<CarteType>) Paquet.getValeur(message, 1), core);
		String log = "";
		if (!((CarteType) listeResultat.get(0)).equals(CarteType.NUL))
			log += "Vous choissisez de garder la carte " + ((CarteType) listeResultat.get(0)).toString() + ".";
		else
			log += "Vous choissisez de ne pas garder de carte.";
		if (!((CarteType) listeResultat.get(1)).equals(CarteType.NUL))
			log += "Vous choissisez de donner la carte " + ((CarteType) listeResultat.get(1)).toString() + " au joueur "
					+ ((Couleur) listeResultat.get(3)).toString() + ".";
		else
			log += "Vous choissisez de ne pas donner de carte.";
		if (!((CarteType) listeResultat.get(2)).equals(CarteType.NUL))
			log += "Vous choissisez de defausser la carte " + ((CarteType) listeResultat.get(2)).toString() + ".";
		else
			log += "Vous choissisez de ne pas defausser de carte.";
		Initializer.log(log);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("SCFC", (CarteType) listeResultat.get(0),
				(CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3), (CarteType) listeResultat.get(2),
				(String) Paquet.getValeur(message, 2), Paquet.getValeur(message, 3), core.getJoueurId()));

	}

	public void choixDestVigil(Paquet Paquet, String message) {
		// TODO réorganiser
		Initializer.nomPhase("Phase de choix d’une destination");
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			out.println("Entrez une destination");
			int dest = traitementI.choixDest(core);

			String log = "En tant que chef des vigiles vous avez choisit comme destination le lieu "
					+ IdjrTools.getLieuByIndex(dest) + ".";
			Initializer.log(log);

			String messageTcp = ControleurReseau.construirePaquetTcp("CDDCV", dest,
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		} else if (core.getCouleur() != (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {
			int dest = traitementI.choixDest(core);

			String log = "Vous avez choisit comme destination le lieu " + IdjrTools.getLieuByIndex(dest) + ".";
			Initializer.log(log);

			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", dest,
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void ReponseJoueurCourant(Paquet Paquet, String message) {
		CarteType RJ = traitementI.ReponseJoueurCourant(core);
		String log = "";
		if (RJ.equals(CarteType.NUL))
			log += "Vous choissisez de ne pas jouer de carte.";
		else
			log += "Vous choissisez de jouez la carte " + RJ.toString() + ".";
		Initializer.log(log);
		String IDP = (String) Paquet.getValeur(message, 1);
		int NT = (int) Paquet.getValeur(message, 2);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
	}

	public void choisirDest(Paquet Paquet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)) {
			return;
		} else {

			int dest = traitementI.choixDest(core);
			String log = "Vous choissisez le lieu " + IdjrTools.getLieuByIndex(dest) + " comme destination.";
			Initializer.log(log);

			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", dest,
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void destZombieVengeur(Paquet Paquet, String message) {
		out.println("Entrez une destination pour le zombie vengeur");
		int dest = traitementI.choixDest(core);
		String log = "Vous choissisez le lieu " + IdjrTools.getLieuByIndex(dest)
				+ " comme destination pour le zombie vengeur.";
		Initializer.log(log);
		String message1 = ControleurReseau.construirePaquetTcp("CDDZVJE", dest, Paquet.getValeur(message, 1),
				Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(message1);
	}

	public void debutDeplacemant(Paquet Paquet, String message) {
		traitementI.debutDeplacemant(core, (List<?>) Paquet.getValeur(message, 4));
	}

	public void deplacerPion(Paquet Paquet, String message) {
		List<Object> listRenvoye = traitementI.pionADeplacer(core, (int) Paquet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) Paquet.getValeur(message, 2));
		String log = "Vous choissisez de déplacer votre pion " + IdjrTools.getPionByIndex((Integer) listRenvoye.get(1))
				+ " sur le lieu" + IdjrTools.getLieuByIndex((Integer) listRenvoye.get(0));
		if (!((CarteType) listRenvoye.get(2)).equals(CarteType.NUL))
			log += " en utilisant une carte Sprint.";
		else
			log += " sans utiliser de carte Sprint.";
		Initializer.log(log);
		String messageTcp = ControleurReseau.construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) Paquet.getValeur(message, 3),
				(int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	public void attaqueZombie(Paquet Paquet, String message) {
		traitementI.attaqueZombie(core, (List<PionCouleur>) (Paquet.getValeur(message, 2)),
				(int) (Paquet.getValeur(message, 1)));
	}

	public void choisirSacrifice(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		PionCouleur sacrifice = traitementI.choisirSacrifice(core, (List<?>) Paquet.getValeur(message, 2));

		String log = "Vous avez choisit de sacrifier votre "
				+ IdjrTools.getPionByIndex(IdjrTools.getPionByValue(sacrifice)) + " sur le lieu "
				+ IdjrTools.getLieuByIndex((Integer) (int) Paquet.getValeur(message, 1)) + ".";
		Initializer.log(log);

		String messageTcp = ControleurReseau.construirePaquetTcp("RAZCS", (int) Paquet.getValeur(message, 1), sacrifice,
				(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void finPartie(Paquet Paquet, String message) {
		traitementI.finPartie(core, (Couleur) Paquet.getValeur(message, 2));
	}

	public void phaseFouilleCamion() {
		// TODO réorganiser
		Initializer.nomPhase("Phase de fouille du camion");

	}

	public void phaseElectionChefVigile(Paquet paquet, String message) {
		// TODO réorganiser
		Initializer.nomPhase("Phase d’élection du chef des vigiles");
	}

	public void initialiserPartie(Paquet Paquet, String message) {
		traitementI.initialiserPartie(this.core, (List<?>) Paquet.getValeur(message, 1),
				(List<Couleur>) Paquet.getValeur(message, 2), (String) Paquet.getValeur(message, 3));
	}

	@Override
	public void set(Object core) {
		this.core = (Idjr) core;
	}
}
