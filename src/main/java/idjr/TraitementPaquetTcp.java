package idjr;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import static java.lang.System.out;

import java.net.Socket;
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
			break;
		case "DC":
			recupCarte(Paquet, message);
			break;
		case "PIIJ":
			lancerDes(Paquet, message);// savoir comment return plusieur choses
			break;
		case "PIRD":
			choisirDestPion(Paquet, message);
			break;
		case "PIIG":
			break;
		case "IT":
			debutTour(Paquet, message);
			break;
		case "PAZ":
			lanceDesChefVigil(Paquet, message);
			break;
		case "PCD":
			choixDestVigil(Paquet, message);
			break;
		case "CDCDV":
			choisirDest(Paquet, message);
			break;
		case "CDZVI":
			destZombieVengeur(Paquet, message);
			break;
		case "PDP":
			debutDeplacemant(Paquet, message);
			break;
		case "DPD":
			deplacerPion(Paquet, message);
			break;
		case "DPI":
			break;
		case "PRAZ":
			debutPhaseAttaque(Paquet, message);
			break;
		case "RAZA":
			attaqueZombie(Paquet, message);
			break;
		case "RAZDS":
			choisirSacrifice(Paquet, message);
			break;
		case "RAZIF":
			break;
		case "FP":
			finPartie(Paquet, message);
			break;
		case "RAZDD":
			fournirActionsDefense(Paquet, message);
			break;
		case "PVDV":
			ChoisirQuiVoter(Paquet, message);
			break;
		case "AZDCS":
			ReponseJoueurCourant(Paquet, message);
			break;
		case "PVD":
			IndiquerCarteJouees(Paquet, message);
			break;
		case "FCRC":
			recupCarte(Paquet, message);
			break;
		case "PIPZ":
			placementzombie(Paquet, message);
			break;
		case "PFC":
			phaseFouilleCamion();
			break;
		case "FCLC":
			choixCarteFouille(Paquet, message);
			break;
		case "RFC":
			break;
		case "PECV":
			phaseElectionChefVigile(Paquet, message);
			break;
		case "RECV":
			break;
		case "CDFC":
			break;

		case "CDZVDI":
		case "AZUCS":
		case "AZLAZ":
			arrivezombies(Paquet, message);
		case "AZICS":
		case "PVIC":
		case "PVR":
		case "PVVC":
		case "RAZPA":
		case "RAZID":
		case "ACP":
			break;
		case "IPV":
			recupInfoVote(Paquet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", Paquet.getCle()));
		}
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
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PICD",
				traitementI.choisirDestPlacement(core, (List<?>) Paquet.getValeur(message, 1),
						(List<?>) Paquet.getValeur(message, 2)),
				traitementI.choisirPionPlacement(core), m1, core.getJoueurId()));
	}

	public void ChoisirQuiVoter(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		System.out.print("ChoisirQuiVoter");
		String messageTcp = ControleurReseau.construirePaquetTcp("PVCV",
				traitementI.getRandom(core, core.getVoteType()), (String) Paquet.getValeur(message, 1),
				(int) Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void recupCarte(Paquet Paquet, String message) {
		core.addCarte((CarteType) Paquet.getValeur(message, 1));
		// TODO déplacer (isoler réseaux) et mettre a jour liste carte
	}

	public void IndiquerCarteJouees(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		String messageTcp = ControleurReseau.construirePaquetTcp("PVC", traitementI.IndiquerCarteJouees(core),
				/* , */ (String) Paquet.getValeur(message, 1), (int) Paquet.getValeur(message, 2),
				(String) core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	public void debutTour(Paquet Paquet, String message) {
		traitementI.debutTour(core, (List<Couleur>) Paquet.getValeur(message, 2));
	}

	private void fournirActionsDefense(Paquet Paquet, String message) {
		List<Object> listerenvoye = traitementI.listeCarteJouee(this.core, (int) Paquet.getValeur(message, 1));
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
			String messageTcp = ControleurReseau.construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);

		}
	}

	private void choixCarteFouille(Paquet Paquet, String message) {
		List<Object> listeResultat = traitementI.carteFouille((List<CarteType>) Paquet.getValeur(message, 1), core);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("SCFC",
				(CarteType) listeResultat.get(0), (CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3),
				(CarteType) listeResultat.get(2), (String) Paquet.getValeur(message, 2), Paquet.getValeur(message, 3),
				core.getJoueurId()));

	}

	public void choixDestVigil(Paquet Paquet, String message) {
		// TODO réorganiser
			Initializer.nomPhase("Phase de choix d’une destination");
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			out.println("Entrez une destination");
			String messageTcp = ControleurReseau.construirePaquetTcp("CDDCV", traitementI.choixDest(core),
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		} else if (core.getCouleur() != (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {
			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", traitementI.choixDest(core),
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void ReponseJoueurCourant(Paquet Paquet, String message) {
		CarteType RJ = traitementI.ReponseJoueurCourant(core);
		String IDP = (String) Paquet.getValeur(message, 1);
		int NT = (int) Paquet.getValeur(message, 2);
		ControleurReseau
				.envoyerTcp(ControleurReseau.construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
	}

	public void choisirDest(Paquet Paquet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)) {
			return;
		} else {
			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", traitementI.choixDest(core),
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void destZombieVengeur(Paquet Paquet, String message) {
		out.println("Entrez une destination pour le zombie vengeur");
		String message1 = ControleurReseau.construirePaquetTcp("CDDZVJE", traitementI.choixDest(core),
				Paquet.getValeur(message, 1), Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(message1);
	}

	public void debutDeplacemant(Paquet Paquet, String message) {
		traitementI.debutDeplacemant(core, (List<?>) Paquet.getValeur(message, 4));
	}

	public void deplacerPion(Paquet Paquet, String message) {
		List<Object> listRenvoye = traitementI.pionADeplacer(core, (int) Paquet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) Paquet.getValeur(message, 2));
		String messageTcp = ControleurReseau.construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) Paquet.getValeur(message, 3),
				(int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	public void attaqueZombie(Paquet Paquet, String message) {
		traitementI.attaqueZombie(core, (List<PionCouleur>)(Paquet.getValeur(message, 2)), (int)(Paquet.getValeur(message, 1)));
	}

	public void choisirSacrifice(Paquet Paquet, String message) {
		out.println(Paquet.getDocs());
		String messageTcp = ControleurReseau.construirePaquetTcp("RAZCS", (int) Paquet.getValeur(message, 1),
				traitementI.choisirSacrifice(core, (List<?>) Paquet.getValeur(message, 2)),
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
