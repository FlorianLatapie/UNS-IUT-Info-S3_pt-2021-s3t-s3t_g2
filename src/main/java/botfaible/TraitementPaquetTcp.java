package botfaible;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import java.text.MessageFormat;
import java.util.*;

import java.net.Socket;

/**
 * <h1>Permet de gerer les Paquets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<TcpClient> {
	private BotFaible core;
	private TraitementBot traitementB;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (BotFaible) core;
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
	public void traitement(Paquet Paquet, String message, TcpClient socket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (Paquet.getCle()) {
		case "IP":
			initialiserPartie(Paquet, message);
			break;
		case "DC":
			recupCarte(Paquet, message);
			break;
		case "PIIJ":
			lancerDes(Paquet, message);
			break;
		case "PIRD":
			choisirDestPlacement(Paquet, message);
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
		case "PRAZ":
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
		case "ACP":
			accepter(Paquet, message);
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
		case "FCLC":
			choixCarteFouille(Paquet, message);
			break;
		case "FCRC":
			recupCarte(Paquet, message);
			break;

		case "CDZVDI":
		case "AZUCS":
		case "PIPZ":
		case "PFC":
		case "RFC":
		case "PECV":
		case "RECV":
		case "CDFC":
		case "AZLAZ":
		case "AZICS":
		case "PVIC":
		case "PVR":
		case "PVVC":
		case "RAZPA":
		case "RAZID":
			break;
		case "IPV":
			recupInfoVote(Paquet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", Paquet.getCle()));
		}
	}

	private void recupCarte(Paquet Paquet, String message) {
		core.getListeCarte().add((CarteType) Paquet.getValeur(message, 1));
	}

	private void recupInfoVote(Paquet Paquet, String message) {
		List<Couleur> couleursJoueurs = (List<Couleur>) Paquet.getValeur(message, 3);
		core.setCouleurJoueurs(couleursJoueurs);
		core.setVoteType((VoteType) Paquet.getValeur(message, 1));
	}

	public void ReponseJoueurCourant(Paquet Paquet, String message) {
		CarteType RJ = traitementB.ReponseJoueurCourant(core);
		String IDP = (String) Paquet.getValeur(message, 1);
		int NT = (int) Paquet.getValeur(message, 2);
		ControleurReseau
				.envoyerTcp(ControleurReseau.construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
	}

	public void ChoisirQuiVoter(Paquet Paquet, String message) {
		System.out.print("ChoisirQuiVoter");
		String messageTcp = ControleurReseau.construirePaquetTcp("PVCV",
				traitementB.getRandom(core, core.getVoteType()), (String) Paquet.getValeur(message, 1),
				(int) Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void fournirActionsDefense(Paquet Paquet, String message) {
		List<Object> listerenvoye = traitementB.listeCarteJouee(this.core, (int) Paquet.getValeur(message, 1));
		String messageTCP = ControleurReseau.construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) Paquet.getValeur(message, 2), (int) Paquet.getValeur(message, 3), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTCP);
	}

	public void accepter(Paquet Paquet, String message) {
		System.out.println((String) Paquet.getValeur(message, 2));

		core.setJoueurId((String) Paquet.getValeur(message, 2));
	}

	public void initialiserPartie(Paquet Paquet, String message) {
		traitementB.initialiserPartie(this.core, (List<?>) Paquet.getValeur(message, 1),
				(List<Couleur>) Paquet.getValeur(message, 2), (String) Paquet.getValeur(message, 3));

	}

	public void lancerDes(Paquet Paquet, String message) {
		traitementB.lancerDes(core, (List<?>) Paquet.getValeur(message, 2));
		String m1 = (String) Paquet.getValeur(message, 3);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PILD", m1, core.getJoueurId()));
	}

	public void choisirDestPlacement(Paquet Paquet, String message) {
		String m1 = (String) Paquet.getValeur(message, 3);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("PICD",
				traitementB.choisirDestPlacement((List<?>) Paquet.getValeur(message, 2)),
				traitementB.choisirPionPlacement(core), m1, core.getJoueurId()));
	}

	public void debutTour(Paquet Paquet, String message) {
		traitementB.debutTour(core, (List<Couleur>) Paquet.getValeur(message, 2));
	}

	public void lanceDesChefVigil(Paquet Paquet, String message) {
		Couleur c1 = (Couleur) Paquet.getValeur(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) Paquet.getValeur(message, 3);
			int m2 = (int) Paquet.getValeur(message, 4);
			String messageTcp = ControleurReseau.construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void choixDestVigil(Paquet Paquet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			String messageTcp = ControleurReseau.construirePaquetTcp("CDDCV", traitementB.choixDest(core),
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		} else if (core.getCouleur() != (Couleur) Paquet.getValeur(message, 1)
				&& (VigileEtat) Paquet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {
			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void choisirDest(Paquet Paquet, String message) {
		if (!core.getEnvie())
			return;

		if (core.getCouleur() == (Couleur) Paquet.getValeur(message, 1)) {
			return;

		} else {
			String messageTcp = ControleurReseau.construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
			ControleurReseau.envoyerTcp(messageTcp);
		}
	}

	public void destZombieVengeur(Paquet Paquet, String message) {
		String message1 = ControleurReseau.construirePaquetTcp("CDDZVJE", traitementB.choixDest(core),
				Paquet.getValeur(message, 1), Paquet.getValeur(message, 2), core.getJoueurId());
		ControleurReseau.envoyerTcp(message1);
	}

	public void debutDeplacemant(Paquet Paquet, String message) {
		traitementB.debutDeplacemant(core, (List<?>) Paquet.getValeur(message, 4));
	}

	public void deplacerPion(Paquet Paquet, String message) {
		List<Object> listRenvoye = traitementB.pionADeplacer(core, (int) Paquet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) Paquet.getValeur(message, 2));
		String messageTcp = ControleurReseau.construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) Paquet.getValeur(message, 3),
				(int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	public void attaqueZombie(Paquet Paquet, String message) {
		traitementB.attaqueZombie(core, (List<PionCouleur>) (Paquet.getValeur(message, 2)), new ArrayList<>());
	}

	public void choisirSacrifice(Paquet Paquet, String message) {
		String messageTcp = ControleurReseau.construirePaquetTcp("RAZCS", (int) Paquet.getValeur(message, 1),
				traitementB.choisirSacrifice(core, (List<?>) Paquet.getValeur(message, 2)),
				(String) Paquet.getValeur(message, 3), (int) Paquet.getValeur(message, 4), core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void finPartie(Paquet Paquet, String message) {
		traitementB.finPartie(core, (Couleur) Paquet.getValeur(message, 2));
	}

	public void IndiquerCarteJouees(Paquet Paquet, String message) {
		String messageTcp = ControleurReseau.construirePaquetTcp("PVC", traitementB.IndiquerCarteJouees(core),
				/* , */ (String) Paquet.getValeur(message, 1), (int) Paquet.getValeur(message, 2),
				(String) core.getJoueurId());
		ControleurReseau.envoyerTcp(messageTcp);
	}

	private void choixCarteFouille(Paquet Paquet, String message) {
		List<Object> listeResultat = traitementB.carteFouille((List<CarteType>) Paquet.getValeur(message, 1), core);
		ControleurReseau.envoyerTcp(ControleurReseau.construirePaquetTcp("SCFC",
				(CarteType) listeResultat.get(0), (CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3),
				(CarteType) listeResultat.get(2), (String) Paquet.getValeur(message, 2), Paquet.getValeur(message, 3),
				core.getJoueurId()));

	}

	@Override
	public void set(Object core) {
		this.core = (BotFaible) core;
	}
}
