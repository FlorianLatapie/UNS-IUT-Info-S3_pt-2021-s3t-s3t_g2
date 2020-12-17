package bot;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import java.text.MessageFormat;
import java.util.*;

import bot.partie.Joueur;

import static java.lang.System.out;

import java.net.Socket;

/**
 * <h1>Permet de gerer les paquets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<TcpClient> {
	private Bot core;
	private TraitementBot traitementB;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (Bot) core;
		this.traitementB = new TraitementBot();
	}

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
	public void traitement(Paquet paquet, String message, TcpClient socket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (paquet.getCle()) {
		case "IP":
			traitementIP(paquet, message);
			break;
		case "DC":
			traitementFCRC(paquet, message);
			break;
		case "PIIJ":
			traitementPIIJ(paquet, message);
			break;
		case "PIRD":
			traitementPIDR(paquet, message);
			break;
		case "PIIG":
			traitementPIIG(paquet, message);
			break;
		case "IT":
			traitementIT(paquet, message);
			break;
		case "PAZ":
			traitementPAZ(paquet, message);
			break;
		case "PCD":
			traitementPCD(paquet, message);
			break;
		case "CDCDV":
			traitementCDCDV(paquet, message);
			break;
		case "CDZVI":
			traitementCDZVI(paquet, message);
			break;
		case "PDP":
			traitementPDP(paquet, message);
			break;
		case "DPD":
			traitementDPD(paquet, message);
			break;
		case "DPI":
			traitementDPI(paquet, message);
			break;
		case "PRAZ":
			traitementPRAZ(paquet, message);
			break;
		case "RAZA":
			traitementRAZA(paquet, message);
			break;
		case "RAZDS":
			traitementRAZDS(paquet, message);
			break;
		case "RAZIF":
			traitementRAZIF(paquet, message);
			break;
		case "FP":
			traitementFP(paquet, message);
			break;
		case "ACP":
			traitementACP(paquet, message);
			break;
		case "RAZDD":
			traitementRAZDD(paquet, message);
			break;
		case "PVDV":
			traitementPVDV(paquet, message);
			break;
		case "AZDCS":
			traitementAZDCS(paquet, message);
			break;
		case "PVD":
			traitementPVD(paquet, message);
			break;
		case "FCLC":
			traitementFCLC(paquet, message);
			break;
		case "FCRC":
			traitementFCRC(paquet, message);
			break;

		case "PIPZ":
			traitementPIPZ(paquet, message);
			break;
		case "PFC":
			traitementPFC(paquet, message);
			break;
		case "RFC":
			traitementRFC(paquet, message);
			break;
		case "PECV":
			traitementPECV(paquet, message);
			break;
		case "RECV":
			traitementRECV(paquet, message);
			break;
		case "CDFC":
			traitementCDFC(paquet, message);
			break;
		case "AZLAZ":
			traitementAZLAZ(paquet, message);
			break;
		case "AZUCS":
			traitementAZUCS(paquet, message);
			break;
		case "AZICS":
			traitementAZICS(paquet, message);
			break;
		case "CDZVDI":
			traitementCDZVDI(paquet, message);
			break;
		case "PVIC":
			traitementPVIC(paquet, message);
			break;
		case "PVR":
			traitementPVR(paquet, message);
			break;
		case "PVVC":
			traitementPVVC(paquet, message);
			break;
		case "RAZPA":
			traitementRAZPA(paquet, message);
			break;
		case "RAZID":
			traitementRAZID(paquet, message);
			break;
		case "IPV":
			traitementIPV(paquet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n'y a pas de traitement possible pour {0}", paquet.getCle()));
		}
	}



	private void traitementPVVC(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementPVR(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementPVIC(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementAZUCS(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementCDFC(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementPECV(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementPFC(Paquet paquet, String message) {
		// TODO Auto-generated method stub

	}

	private void traitementRAZIF(Paquet paquet, String message) {
		core.sacrifice((PionCouleur) paquet.getValeur(message, 2));
		core.correctionZombie((Integer) paquet.getValeur(message, 1), (Integer) paquet.getValeur(message, 3));
		
		System.out.println("resSacrifice : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementRAZID(Paquet paquet, String message) {
		core.joueCartes((Couleur) paquet.getValeur(message, 2), (List<CarteType>) paquet.getValeur(message, 3));
		core.correctionZombie((Integer) paquet.getValeur(message, 1), (Integer) paquet.getValeur(message, 6));
		core.setPersoCache((Integer) paquet.getValeur(message, 1), (List<Integer>) paquet.getValeur(message, 3));
		
		System.out.println("joueCarteDEF : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementRAZPA(Paquet paquet, String message) {
		core.correctionZombie((Integer) paquet.getValeur(message, 1), (Integer) paquet.getValeur(message, 4));

		System.out.println("traitementRAZPA  : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPRAZ(Paquet paquet, String message) {
		core.setZombie((List<Integer>) paquet.getValeur(message, 3), (List<Integer>) paquet.getValeur(message, 4));
		
		System.out.println("resoAttaqueZombie phase de résolution de l’attaque des zombies, pas d’attaque : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementDPI(Paquet paquet, String message) {
		core.deplPionJoueurCourant((Couleur) paquet.getValeur(message, 1), (Integer) paquet.getValeur(message, 2),
				(Integer) paquet.getValeur(message, 3));
		if (((CarteType) paquet.getValeur(message, 4)).equals(CarteType.SPR))
			core.joueCarte((Couleur) paquet.getValeur(message, 1), CarteType.SPR);
		
		System.out.println("traitementDPI  phase de déplacement des personnages : information aux joueurs : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementCDZVDI(Paquet paquet, String message) {
		core.arriveSoloZombie((Integer) paquet.getValeur(message, 2));
		
		System.out.println("arriveSoloZombie : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementAZICS(Paquet paquet, String message) {
		if ((((CarteType) paquet.getValeur(message, 2)).equals(CarteType.CDS))
				&& (!((Couleur) paquet.getValeur(message, 1)).equals(core.getCouleur()))) {
			core.joueCarte((Couleur) paquet.getValeur(message, 1), CarteType.CDS);
		}
		
		System.out.println("joueCarteCDS : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementAZLAZ(Paquet paquet, String message) {
		core.arriveZombie((List<Integer>) paquet.getValeur(message, 1));
		
		System.out.println("arriveZombie : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementRECV(Paquet paquet, String message) {
		core.resVigile((Couleur) paquet.getValeur(message, 1));
		
		System.out.println("resVigile : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementRFC(Paquet paquet, String message) {
		core.resFouille((Couleur) paquet.getValeur(message, 1), (Couleur) paquet.getValeur(message, 2),
				(CarteEtat) paquet.getValeur(message, 3));
		
		System.out.println("resFouille : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementPIPZ(Paquet paquet, String message) {
		core.initZombie((List<Integer>) paquet.getValeur(message, 1));
		
		System.out.println("placeZombie : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementPIIG(Paquet paquet, String message) {
		core.placePionCouleur((Couleur) paquet.getValeur(message, 1), (Integer) paquet.getValeur(message, 4),
				(Integer) paquet.getValeur(message, 5));
		
		System.out.println("placementPerso : \n");
		System.out.println(core.getEtatPartie());

	}

	private void traitementFCRC(Paquet paquet, String message) {
		core.getListeCarte().add((CarteType) paquet.getValeur(message, 1));
		core.initCarte((CarteType) paquet.getValeur(message, 1));
		core.recupCarte((CarteType) paquet.getValeur(message, 1));
		
		System.out.println("recupCarte : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementIPV(Paquet paquet, String message) {
		List<Couleur> joueursCouleurs = (List<Couleur>) paquet.getValeur(message, 4);
		List<Integer> joueursValeurVote = (List<Integer>) paquet.getValeur(message, 5);
		core.setCouleurJoueurs(joueursCouleurs);
		core.setVoteType((VoteType) paquet.getValeur(message, 1));
		HashMap<Couleur,Integer> joueursVotant= new HashMap<Couleur,Integer>();
		for (int i = 0 ; i<joueursCouleurs.size();i++ )
			joueursVotant.put(joueursCouleurs.get(i), joueursValeurVote.get(i));
		core.setJoueursVotant(joueursVotant);
		System.out.println("recupInfoVote : \n");
		System.out.println(core.getEtatPartie());
	}

	public void traitementAZDCS(Paquet paquet, String message) {

		ControleurReseau
				.envoyerTcp(ControleurReseau.construirePaquetTcp("AZRCS", traitementB.traitementAZDCS(core),
						(String) paquet.getValeur(message, 1), (int) paquet.getValeur(message, 2), core.getJoueurId()));

		System.out.println("ReponseJoueurCourant : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPVDV(Paquet paquet, String message) {
		out.println(paquet.getDocs());

		ControleurReseau.envoyerTcp(
				ControleurReseau.construirePaquetTcp("PVCV", traitementB.traitementPVDV(core, core.getVoteType()),
						(String) paquet.getValeur(message, 1), (int) paquet.getValeur(message, 2), core.getJoueurId()));

		System.out.println("ChoisirQuiVoter : \n");
		System.out.println(core.getEtatPartie());
	}
	
	private void traitementRAZA(Paquet paquet, String message) {
		traitementB.traitementRAZA(core, (List<PionCouleur>) (paquet.getValeur(message, 2)));
		core.correctionZombie((Integer) paquet.getValeur(message, 1), (Integer) paquet.getValeur(message, 4));
	}

	private void traitementRAZDD(Paquet paquet, String message) {
		List<Object> listerenvoye = traitementB.traitementRAZDD(this.core, (int) paquet.getValeur(message, 1));

		ControleurReseau
				.envoyerTcp(ControleurReseau.construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
						(String) paquet.getValeur(message, 2), (int) paquet.getValeur(message, 3), core.getJoueurId()));

		System.out.println("fournirActionsDefense : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementACP(Paquet paquet, String message) {
		System.out.println((String) paquet.getValeur(message, 2));

		core.setJoueurId((String) paquet.getValeur(message, 2));
	}

	private void traitementIP(Paquet paquet, String message) {
		traitementB.traitementIP(this.core, (List<?>) paquet.getValeur(message, 1),
				(List<Couleur>) paquet.getValeur(message, 2), (String) paquet.getValeur(message, 3));
	}

	private void traitementPIIJ(Paquet paquet, String message) {
		traitementB.traitementPIIJ(core, (List<?>) paquet.getValeur(message, 2));

		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PILD", (String) paquet.getValeur(message, 3),
				core.getJoueurId()));
		System.out.println("lancerDes : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPIDR(Paquet paquet, String message) {
		List<Integer> choix = new ArrayList<>();
		choix = traitementB.traitementPIDR(core, (List<?>) paquet.getValeur(message, 2));
		int dest = choix.get(0);
		int pion = choix.get(1);

		core.placePion(dest, pion);

		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PICD", dest, pion,
				(String) paquet.getValeur(message, 3), core.getJoueurId()));

		System.out.println("choisirDestPlacement : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementIT(Paquet paquet, String message) {
		traitementB.traitementIT(core, (List<Couleur>) paquet.getValeur(message, 2));
		core.resetPersoCache();

		System.out.println("debutTour : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPAZ(Paquet paquet, String message) {
		Couleur c1 = (Couleur) paquet.getValeur(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) paquet.getValeur(message, 3);
			int m2 = (int) paquet.getValeur(message, 4);
			ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("AZLD", m1, m2, core.getJoueurId()));
		}

		core.NewChef((VigileEtat) paquet.getValeur(message, 2));

		System.out.println("lanceDesChefVigil : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPCD(Paquet paquet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) paquet.getValeur(message, 1)
				&& (VigileEtat) paquet.getValeur(message, 2) == VigileEtat.NE) {

			ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("CDDCV", traitementB.traitementPCD(core),
					(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId()));

		} else if (core.getCouleur() != (Couleur) paquet.getValeur(message, 1)
				&& (VigileEtat) paquet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {

			ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("CDDJ", traitementB.traitementPCD(core),
					(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId()));
		}
		System.out.println("choixDestVigil : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementCDCDV(Paquet paquet, String message) {
		if (!core.getEnvie())
			return;

		if (core.getCouleur() == (Couleur) paquet.getValeur(message, 1)) {
			return;

		} else {

			ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("CDDJ", traitementB.traitementCDCDV(core),
					(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId()));
		}
		System.out.println("choisirDest : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementCDZVI(Paquet paquet, String message) {

		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("CDDZVJE", traitementB.traitementCDZVI(core),
				paquet.getValeur(message, 1), paquet.getValeur(message, 2), core.getJoueurId()));

		System.out.println("destZombieVengeur : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPDP(Paquet paquet, String message) {
		traitementB.traitementPDP(core, (List<?>) paquet.getValeur(message, 4));
	}

	private void traitementDPD(Paquet paquet, String message) {
		List<Object> listRenvoye = traitementB.traitementDPD(core, (int) paquet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) paquet.getValeur(message, 2));

		System.out.println("deplacerPion avant dpl: {" + core.getCouleur() + ", " + (Integer) listRenvoye.get(0) + ", "
				+ (Integer) listRenvoye.get(1) + "}\n");
		System.out.println(core.getEtatPartie());

		core.deplPionJoueurCourant(core.getCouleur(), (Integer) listRenvoye.get(0), (Integer) listRenvoye.get(1));

		if (((CarteType) listRenvoye.get(2)).equals(CarteType.SPR))
			core.joueCarte(core.getCouleur(), CarteType.SPR);

		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) paquet.getValeur(message, 3),
				(int) paquet.getValeur(message, 4), core.getJoueurId()));

		System.out.println("deplacerPion apres dpl: \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementRAZDS(Paquet paquet, String message) {
		out.println(paquet.getDocs());

		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("RAZCS", (int) paquet.getValeur(message, 1),
				traitementB.traitementRAZDS(core, (List<?>) paquet.getValeur(message, 2)),
				(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId()));

		System.out.println("choisirSacrifice : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementFP(Paquet paquet, String message) {
		traitementB.traitementFP(core, (Couleur) paquet.getValeur(message, 2));

		System.out.println("finPartie : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementPVD(Paquet paquet, String message) {
		out.println(paquet.getDocs());

		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PVC", traitementB.traitementPVD(core),
				(String) paquet.getValeur(message, 1), (int) paquet.getValeur(message, 2), core.getJoueurId()));

		System.out.println("IndiquerCarteJouees : \n");
		System.out.println(core.getEtatPartie());
	}

	private void traitementFCLC(Paquet paquet, String message) {
		List<Object> listeResultat = traitementB.traitementFCLC((List<CarteType>) paquet.getValeur(message, 1), core);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("SCFC", (CarteType) listeResultat.get(0),
				(CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3), (CarteType) listeResultat.get(2),
				(String) paquet.getValeur(message, 2), paquet.getValeur(message, 3), core.getJoueurId()));

		System.out.println("choixCarteFouille : \n");
		System.out.println(core.getEtatPartie());

	}

	@Override
	public void set(Object core) {
		this.core = (Bot) core;
	}
}
