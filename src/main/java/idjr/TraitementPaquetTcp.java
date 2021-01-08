package idjr;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.CondType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.RaisonType;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import static java.lang.System.out;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import idjr.ihmidjr.IhmTools;
import idjr.ihmidjr.event.Evenement;
import idjr.ihmidjr.langues.International;

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
			// logPIIJ(Paquet, message);
			break;
		case "PIRD":
			choisirDestPion(Paquet, message);
			// logPIRD(Paquet, message);
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
			//logFP(Paquet, message);
			break;
		case "RAZDD":
			fournirActionsDefense(Paquet, message);
			//logRAZDD(Paquet, message);
			break;
		case "PVDV":
			ChoisirQuiVoter(Paquet, message);
			// logPVDV(Paquet, message);
			break;
		case "AZDCS":
			ReponseJoueurCourant(Paquet, message);
			logAZDCS(Paquet, message);
			break;
		case "PVD":
			IndiquerCarteJouees(Paquet, message);
			// logPVD(Paquet, message);
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
			logFCLC1(Paquet, message);
			// logFCLC2(core.getResultatFouille());
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
			accepterPartie(Paquet, message);
			break;
		case "RCP":
			break;
		case "IPV":
			recupInfoVote(Paquet, message);
			logIPV(Paquet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n'y a pas de traitement possible pour {0}", Paquet.getCle()));
		}
	}

	private void accepterPartie(Paquet paquet, String message) {
		core.setJoueurId((String) paquet.getValeur(message, 2));
		Evenement.partieValider();
	}

	private void refuserPartie(Paquet paquet, String message) {
		// TODO erreur
	}

	private void logPVR(Paquet paquet, String message) {
		String log ="";
		if (((Couleur) paquet.getValeur(message, 1)).equals(Couleur.NUL))
			log = International.trad("texte.logPVR1");
		else {
			List<Couleur>listeJ = (List<Couleur>) paquet.getValeur(message, 2);
			List<Couleur>listeV = (List<Couleur>) paquet.getValeur(message, 3);
			for (int i= 0 ; i<listeJ.size();i++) {
				log += International.trad("texte.logPVR3") + " " + IhmTools.colorTrad(listeJ.get(i))  + " " + International.trad("texte.logPVR4")+ IhmTools.colorTrad(listeV.get(i)) +".\n";
			}
			
			log += International.trad("texte.logPVR2") + " " + ((Couleur) paquet.getValeur(message, 1)).toString() + ".";
		}
			
		Evenement.log(log);

	}

	private void logPVVC(Paquet paquet, String message) {
		String log = International.trad("texte.logPVVC");
		Evenement.log(log);

	}

	private void logPVIC(Paquet paquet, String message) {
		String log = International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 1)).toString()
				+ " " + International.trad("texte.logPVIC2") + " " + (Integer) paquet.getValeur(message, 2) + " "
				+ International.trad("texte.logPVIC3") + " " + (Integer) paquet.getValeur(message, 3) + " "
				+ International.trad("texte.logPVIC4");
		Evenement.log(log);

	}

	private void logRAZID(Paquet paquet, String message) {
		List<CarteType> carteJouee = (List<CarteType>) paquet.getValeur(message, 3);
		List<Integer> pionCachee = (List<Integer>) paquet.getValeur(message, 4);
		String log = International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 2)).toString()
				+ " ";

		if (!carteJouee.isEmpty()) {
			log += International.trad("texte.logRAZID1") + " : ";
			for (int i = 0; i < carteJouee.size() - 1; i++)
				log += carteJouee.get(i).toString() + ", ";
			log += carteJouee.get(carteJouee.size() - 1).toString();

		} else
			log += International.trad("texte.logRAZID7");
		log += " " + International.trad("texte.logRAZID2") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";
		if (!pionCachee.isEmpty()) {
			log += International.trad("texte.logRAZID3") + " ";
			for (int i = 0; i < pionCachee.size() - 1; i++)
				log += pionCachee.get(i).toString() + ", ";
			log += pionCachee.get(pionCachee.size() - 1).toString() + ".";
		} else
			log += International.trad("texte.logRAZID4");

		log += " " + International.trad("texte.logRAZID5") + " " + (Integer) paquet.getValeur(message, 6) + " "
				+ International.trad("texte.logRAZID6");
		Evenement.log(log);
	}

	private void logRAZPA(Paquet paquet, String message) {
		String log = International.trad("texte.logRAZID1") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1));
		if (((RaisonType) paquet.getValeur(message, 3)).equals(RaisonType.PION))
			log += " " + International.trad("texte.logRAZID2");
		if (((RaisonType) paquet.getValeur(message, 3)).equals(RaisonType.ZOMBIE))
			log += " " + International.trad("texte.logRAZID3");
		if (((RaisonType) paquet.getValeur(message, 3)).equals(RaisonType.FORCE))
			log += " " + International.trad("texte.logRAZID4");
		Evenement.log(log);

	}

	private void logAZICS(Paquet paquet, String message) {
		String log = International.trad("texte.logLejoueur") + " "
				+ ((CarteType) paquet.getValeur(message, 2)).toString();
		if (!((CarteType) paquet.getValeur(message, 2)).equals(CarteType.CDS))
			log += " " + International.trad("texte.logAZICS1");
		else
			log += " " + International.trad("texte.logAZICS2");
		Evenement.log(log);
	}

	private void logCDZVDI(Paquet paquet, String message) {
		List<Integer> lieuArriveZombie = (List<Integer>) paquet.getValeur(message, 1);
		String log = " " + International.trad("texte.logCDZVDI") + " ";
		for (int i = 0; i < lieuArriveZombie.size() - 1; i++)
			log += lieuArriveZombie.get(i) + ", ";
		log += lieuArriveZombie.get(lieuArriveZombie.size() - 1) + ".";
		Evenement.log(log);
	}

	private void logAZUCSvAZLAZ(Paquet paquet, String message) {
		List<Integer> lieuArriveZombie = (List<Integer>) paquet.getValeur(message, 1);
		String log = " " + International.trad("texte.logAZUCSvAZLAZ") + " ";
		for (int i = 0; i < lieuArriveZombie.size() - 1; i++)
			log += lieuArriveZombie.get(i) + ", ";
		log += lieuArriveZombie.get(lieuArriveZombie.size() - 1) + ".";
		Evenement.log(log);
	}

	private void logIPV(Paquet paquet, String message) {
		String log = " " + International.trad("texte.logIPV1") + " ("
				+ ((VoteType) paquet.getValeur(message, 1)).toString() + ") " + International.trad("texte.logIPV2");
		Evenement.log(log);
	}

	private void logCDFC(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logCDFC"));
	}

	private void logRECV(Paquet paquet, String message) {
		String log = " " + International.trad("texte.logRECV1") + "\n ";
		if (!((Couleur) paquet.getValeur(message, 1)).equals(Couleur.NUL))
			log += International.trad("texte.logRECV2") + " "
					+ core.getListeJoueursInitiale().get(((Couleur) paquet.getValeur(message, 1))) + ".";
		else
			log += International.trad("texte.logRECV3");
		Evenement.log(log);
	}

	private void logPECV(Paquet paquet, String message) {
		String log = " " + International.trad("texte.logPECV1");
		/*
		 * + "\n " + International.trad("texte.logPECV2") + "\n ";
		 * 
		 * List<Couleur> couleur = new ArrayList<>(); for (PionCouleur pc :
		 * (List<PionCouleur>) paquet.getValeur(message, 1)) if
		 * (!couleur.contains(IdjrTools.getCouleurByChar(pc)))
		 * couleur.add(IdjrTools.getCouleurByChar(pc)); for (Couleur c : couleur) log +=
		 * core.getListeJoueursInitiale().get(c) + "\n ";
		 */
		Evenement.log(log);

	}

	private void logRFC(Paquet paquet, String message) {
		String log = " " + International.trad("texte.logRFC1") + "\n ";
		if (!((Couleur) paquet.getValeur(message, 1)).equals(Couleur.NUL))
			log += International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 1)).toString()
					+ " " + International.trad("texte.logRFC2") + "\n ";
		else
			log += International.trad("texte.logRFC3") + "\n ";
		if (!((Couleur) paquet.getValeur(message, 2)).equals(Couleur.NUL))
			log += International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 2)).toString()
					+ " " + International.trad("texte.logRFC4") + "\n ";
		else
			log += International.trad("texte.logRFC5") + "\n ";
		if (!((CarteEtat) paquet.getValeur(message, 3)).equals(CarteEtat.NUL))
			log += International.trad("texte.logRFC6");
		else
			log += International.trad("texte.logRFC7");

		Evenement.log(log);

	}

	private void logFCLC1(Paquet paquet, String message) {
		List<CarteType> listeJC = (List<CarteType>) paquet.getValeur(message, 1);
		String log = International.trad("texte.logFCLC1") + " ";
		for (int i = 0; i < listeJC.size() - 1; i++)
			log += listeJC.get(i).toString() + ", ";
		log += listeJC.get(listeJC.size() - 1).toString();
//		log += " " + International.trad("texte.logFCLC2");
		Evenement.log(log);

	}

	private void logFCLC2(List<Object> listeResultat) {
		String log = "";
		if (!((CarteType) listeResultat.get(0)).equals(CarteType.NUL))
			log += International.trad("texte.logchoixCarteFouille1") + " "
					+ ((CarteType) listeResultat.get(0)).toString() + ".";
		else
			log += International.trad("texte.logchoixCarteFouille2");
		if (!((CarteType) listeResultat.get(1)).equals(CarteType.NUL))
			log += International.trad("texte.logchoixCarteFouille3") + " "
					+ ((CarteType) listeResultat.get(1)).toString() + " "
					+ International.trad("texte.logchoixCarteFouille4") + " "
					+ ((Couleur) listeResultat.get(3)).toString() + ".";
		else
			log += International.trad("texte.logchoixCarteFouille5");
		if (!((CarteType) listeResultat.get(2)).equals(CarteType.NUL))
			log += International.trad("texte.logchoixCarteFouille6") + " "
					+ ((CarteType) listeResultat.get(2)).toString() + ".";
		else
			log += International.trad("texte.logchoixCarteFouille7");
		Evenement.log(log);
	}

	private void logPFC(Paquet paquet, String message) {
		String log = " " + International.trad("texte.logPFC1");
		/*
		 * + "\n " + International.trad("texte.logPFC2") + "\n ";
		 *
		 * for (PionCouleur c : (List<PionCouleur>) paquet.getValeur(message, 1)) log +=
		 * core.getListeJoueursInitiale().get(IdjrTools.getCouleurByChar(c)) + "\n ";
		 * log += International.trad("texte.logPFC3") + " " + (Integer)
		 * paquet.getValeur(message, 2) + " " + International.trad("texte.logPFC4");
		 */
		Evenement.log(log);

	}

	private void logPIPZ(Paquet paquet, String message) {
		List<Integer> lieuArriveZombie = (List<Integer>) paquet.getValeur(message, 1);
		String log = " " + International.trad("texte.logPIPZ") + "  ";
		for (int i = 0; i < lieuArriveZombie.size() - 1; i++)
			log += lieuArriveZombie.get(i) + ", ";
		log += lieuArriveZombie.get(lieuArriveZombie.size() - 1) + ".";
		Evenement.log(log);

	}

	private void logFCRC(Paquet paquet, String message) {
		String log = International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 2)).toString()
				+ " " + International.trad("texte.logFCRC") + " "
				+ ((CarteType) paquet.getValeur(message, 1)).toString() + ".";
		Evenement.log(log);

	}

	private void logPVD(Paquet paquet, String message) {
		String log = International.trad("texte.logPVD");
		Evenement.log(log);

	}

	private void logAZDCS(Paquet paquet, String message) {
		String log = International.trad("texte.logAZDCS");
		Evenement.log(log);

	}

	private void logPVDV(Paquet paquet, String message) {
		String log = International.trad("texte.logPVDV");
		Evenement.log(log);

	}

	private void logRAZDD(Paquet paquet, String message) {
		String log = International.trad("texte.logRAZDD") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";
		Evenement.log(log);

	}

	private void logFP(Paquet paquet, String message) {
		String log = International.trad("texte.logFP1");
		if (((CondType) paquet.getValeur(message, 1)).equals(CondType.LIEUX))
			log += " " + International.trad("texte.logFP2");
		else if (((CondType) paquet.getValeur(message, 1)).equals(CondType.PION))
			log += " " + International.trad("texte.logFP3");
		Evenement.log(log);
	}

	private void logRAZIF(Paquet paquet, String message) {
		String log = International.trad("texte.logRAZIF1") + " "
				+ IdjrTools.getPionByIndex(IdjrTools.getPionByValue((PionCouleur) paquet.getValeur(message, 2))) + " "
				+ International.trad("texte.logRAZIF2") + " "
				+ IdjrTools.getCouleurByChar((PionCouleur) paquet.getValeur(message, 2)) + " "
				+ International.trad("texte.logRAZIF3") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ". ";
		log += International.trad("texte.logRAZIF4") + " " + (Integer) paquet.getValeur(message, 3) + " "
				+ International.trad("texte.logRAZIF5");
		Evenement.log(log);

	}

	private void logPRAZ(Paquet paquet, String message) {
		String log = "";
		if ((Integer) paquet.getValeur(message, 1) != 0)
			log += International.trad("texte.logPRAZ1") + " "
					+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + " "
					+ International.trad("texte.logPRAZ2");
		if ((Integer) paquet.getValeur(message, 2) != 0)
			log += International.trad("texte.logPRAZ1") + " "
					+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 2)) + " "
					+ International.trad("texte.logPRAZ2");
		Evenement.log(log);
	}

	private void logRAZDS(Paquet paquet, String message) {
		String log = International.trad("texte.logRAZDS") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + " ?";
		Evenement.log(log);

	}

	private void logRAZA(Paquet paquet, String message) {
		String log = International.trad("texte.logRAZA") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + ".";
		Evenement.log(log);
	}

	private void logDPI(Paquet paquet, String message) {
		String log = International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 1)).toString()
				+ " " + International.trad("texte.logDPI1") + " "
				+ IdjrTools.getPionByIndex((Integer) paquet.getValeur(message, 3)) + " "
				+ International.trad("texte.logDPI2")
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 2));
		if (!((CarteType) paquet.getValeur(message, 4)).equals(CarteType.NUL))
			log += " " + International.trad("texte.logDPI3");
		else
			log += " " + International.trad("texte.logDPI4");

		Evenement.log(log);

	}

	private void logDPD(Paquet paquet, String message) {
		String log = International.trad("texte.logDPD1") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 1)) + " "
				+ International.trad("texte.logDPD2");
		Evenement.log(log);

	}

	private void logPDP(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logPDP"));

	}

	private void logCDZVI(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logCDZVI"));
	}

	private void logCDCDV(Paquet paquet, String message) {
		String log = International.trad("texte.logCDCDV1") + " " + ((Couleur) paquet.getValeur(message, 1)).toString()
				+ International.trad("texte.logCDCDV2") + " "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 2)) + " "
				+ International.trad("texte.logCDCDV3");
		Evenement.log(log);

	}

	private void logPCD(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logPCD"));

	}

	private void logPAZ(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logPAZ"));

	}

	private void logIT(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logIT1") + " n° " + (Integer) paquet.getValeur(message, 4) + " "
				+ International.trad("texte.logIT2"));
	}

	private void logPIIG(Paquet paquet, String message) {
		String log = International.trad("texte.logLejoueur") + " " + ((Couleur) paquet.getValeur(message, 1)).toString()
				+ " " + International.trad("texte.logPIIG1") + " "
				+ IdjrTools.getPionByIndex((Integer) paquet.getValeur(message, 5)) + " "
				+ International.trad("texte.logPIIG2") + " : "
				+ IdjrTools.getLieuByIndex((Integer) paquet.getValeur(message, 4)) + ".";
		Evenement.log(log);
	}

	private void logPIRD(Paquet paquet, String message) {
		List<Integer> lieuPossible = (List<Integer>) paquet.getValeur(message, 2);
		String log = International.trad("texte.logPIRD1") + " " + ((List<Integer>) paquet.getValeur(message, 1)).get(0)
				+ " " + International.trad("texte.logPIRD2") + " "
				+ ((List<Integer>) paquet.getValeur(message, 1)).get(1) + ".\n ";
		log += International.trad("texte.logPIRD3") + " ";

		for (int i = 0; i < lieuPossible.size() - 1; i++)
			log += lieuPossible.get(i) + ", ";
		log += lieuPossible.get(lieuPossible.size() - 1);
		Evenement.log(log);
	}

	private void logPIIJ(Paquet paquet, String message) {

		String log = International.trad("texte.logPIIJ1");
		for (Integer i : (List<Integer>) paquet.getValeur(message, 2)) {
			log += "\n " + International.trad("texte.logPIIJ2") + " " + IdjrTools.getPionByIndex(i) + ".";
		}
		Evenement.log(log);
	}

	private void logDC(Paquet paquet, String message) {
		Evenement
				.log(International.trad("texte.logDC") + " : " + ((CarteType) paquet.getValeur(message, 1)).toString());
	}

	private void logIP(Paquet paquet, String message) {
		Evenement.log(International.trad("texte.logIP"));
	}

	private void arrivezombies(Paquet paquet, String message) {
		Evenement.desVigiles((List<Integer>) paquet.getValeur(message, 1));
	}

	private void placementzombie(Paquet paquet, String message) {
		Evenement.enleverDes();
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
		String log = International.trad("texte.logchoisirDestPion1") + " " + IdjrTools.getPionByIndex(pion) + " "
				+ International.trad("texte.logchoisirDestPion2") + " " + IdjrTools.getLieuByIndex(dest) + ".";
		Evenement.log(log);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PICD", dest, pion, m1, core.getJoueurId()));
	}

	public void ChoisirQuiVoter(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		System.out.print("ChoisirQuiVoter");
		Couleur choix = traitementI.getRandom(core, core.getVoteType());
		String log = International.trad("texte.logChoisirQuiVoter") + " " + choix.toString() + ".";
		Evenement.log(log);
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
		String log = International.trad("texte.logIndiquerCarteJouees1") + " " + nbCarteJouee + " "
				+ International.trad("texte.logIndiquerCarteJouees2");
		Evenement.log(log);

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
		String log = "";
		if (!carteJouee.isEmpty()) {
			log += International.trad("texte.logfournirActionsDefense1") + " : ";
			for (int i = 0; i < carteJouee.size() - 1; i++)
				log += carteJouee.get(i).toString() + ", ";

			log += carteJouee.get(carteJouee.size() - 1).toString();

			log += " " + International.trad("texte.logfournirActionsDefense2") + " "
					+ IdjrTools.getLieuByIndex((Integer) Paquet.getValeur(message, 1)) + ". ";
		} else
			log += International.trad("texte.logfournirActionsDefense5") + " "
					+ IdjrTools.getLieuByIndex((Integer) Paquet.getValeur(message, 1)) + ". ";
		if (!pionCachee.isEmpty()) {
			log += International.trad("texte.logfournirActionsDefense3") + " ";
			for (int i = 0; i < pionCachee.size() - 1; i++)
				log += pionCachee.get(i).toString() + ", ";
			log += pionCachee.get(pionCachee.size() - 1).toString() + ". ";
		} else
			log += International.trad("texte.logfournirActionsDefense4");
		Evenement.log(log);

		String messageTCP = ControleurReseau.construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) Paquet.getValeur(message, 2), (int) Paquet.getValeur(message, 3), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTCP);
	}

	public void lanceDesChefVigil(Paquet packet, String message) {
		// TODO réorganiser
		Evenement.nomPhase(International.trad("text.phaseariveezombies"));
		Couleur c1 = (Couleur) packet.getValeur(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValeur(message, 3);
			int m2 = (int) packet.getValeur(message, 4);
			String log = International.trad("texte.loglanceDesChefVigil");
			Evenement.log(log);
			String messageTcp = ControleurReseau.construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);

		}
	}

	private void choixCarteFouille(Paquet Paquet, String message) {
		core.setResultatFouille(traitementI.carteFouille((List<CarteType>) Paquet.getValeur(message, 1), core));
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("SCFC",
				(CarteType) core.getResultatFouille().get(0), (CarteType) core.getResultatFouille().get(1),
				(Couleur) core.getResultatFouille().get(3), (CarteType) core.getResultatFouille().get(2),
				(String) Paquet.getValeur(message, 2), Paquet.getValeur(message, 3), core.getJoueurId()));

	}

	public void choixDestVigil(Paquet Paquet, String message) {
		// TODO réorganiser
		Evenement.nomPhase(International.trad("text.phasechoixdesti"));
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			out.println("Entrez une destination");
			int dest = traitementI.choixDest(core);

			String log = International.trad("texte.logchoixDestVigil1") + " " + IdjrTools.getLieuByIndex(dest) + ".";
			Evenement.log(log);

			String messageTcp = ControleurReseau.construirePaquetTcp("CDDCV", dest,
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		} else if (core.getCouleur() != (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {
			int dest = traitementI.choixDest(core);

			String log = International.trad("texte.logchoixDestVigil2") + " " + IdjrTools.getLieuByIndex(dest) + ".";
			Evenement.log(log);

			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", dest,
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void ReponseJoueurCourant(Paquet Paquet, String message) {
		CarteType RJ = traitementI.ReponseJoueurCourant(core);
		String log = "";
		if (RJ.equals(CarteType.NUL))
			log += International.trad("texte.logReponseJoueurCourant1");
		else
			log += International.trad("texte.logReponseJoueurCourant2") + " " + RJ.toString() + ".";
		Evenement.log(log);
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
			String log = International.trad("texte.logchoisirDest1") + " " + IdjrTools.getLieuByIndex(dest) + " "
					+ International.trad("texte.logchoisirDest2");
			Evenement.log(log);

			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", dest,
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void destZombieVengeur(Paquet Paquet, String message) {
		out.println("Entrez une destination pour le zombie vengeur");
		int dest = traitementI.choixDest(core);
		String log = International.trad("texte.logdestZombieVengeur1") + " " + IdjrTools.getLieuByIndex(dest) + " "
				+ International.trad("texte.logdestZombieVengeur2");
		Evenement.log(log);
		String message1 = ControleurReseau.construirePaquetTcp("CDDZVJE", dest, Paquet.getValeur(message, 1),
				Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(message1);
	}

	public void debutDeplacemant(Paquet Paquet, String message) {
		Evenement.desEnlVigiles();
		traitementI.debutDeplacemant(core, (List<?>) Paquet.getValeur(message, 4));
	}

	public void deplacerPion(Paquet Paquet, String message) {
		List<Object> listRenvoye = traitementI.pionADeplacer(core, (int) Paquet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) Paquet.getValeur(message, 2));
		String log = International.trad("texte.logdeplacerPion1") + " "
				+ IdjrTools.getPionByIndex((Integer) listRenvoye.get(1)) + " "
				+ International.trad("texte.logdeplacerPion2") + IdjrTools.getLieuByIndex((Integer) listRenvoye.get(0));
		if (!((CarteType) listRenvoye.get(2)).equals(CarteType.NUL))
			log += " " + International.trad("texte.logdeplacerPion3");
		else
			log += " " + International.trad("texte.logdeplacerPion4");
		Evenement.log(log);
		String messageTcp = ControleurReseau.construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) Paquet.getValeur(message, 3),
				(int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	public void attaqueZombie(Paquet Paquet, String message) {
		traitementI.attaqueZombie(core, (List<PionCouleur>) (Paquet.getValeur(message, 2)),
				(int) (Paquet.getValeur(message, 1)),(int)(Paquet.getValeur(message, 4)));
	}

	public void choisirSacrifice(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		PionCouleur sacrifice = traitementI.choisirSacrifice(core, (List<?>) Paquet.getValeur(message, 2));

		String log = International.trad("texte.logchoisirSacrifice1") + " "
				+ IdjrTools.getPionByIndex(IdjrTools.getPionByValue(sacrifice)) + " "
				+ International.trad("texte.logchoisirSacrifice2") + " "
				+ IdjrTools.getLieuByIndex((Integer) (int) Paquet.getValeur(message, 1)) + ".";
		Evenement.log(log);

		String messageTcp = ControleurReseau.construirePaquetTcp("RAZCS", (int) Paquet.getValeur(message, 1), sacrifice,
				(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void finPartie(Paquet Paquet, String message) {
		traitementI.finPartie(core, (Couleur) Paquet.getValeur(message, 2));
	}

	public void phaseFouilleCamion() {
		// TODO réorganiser
		Evenement.nomPhase(International.trad("text.phasefouillecam"));

	}

	public void phaseElectionChefVigile(Paquet paquet, String message) {
		// TODO réorganiser
		Evenement.nomPhase(International.trad("text.phaseeleccdv"));
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
