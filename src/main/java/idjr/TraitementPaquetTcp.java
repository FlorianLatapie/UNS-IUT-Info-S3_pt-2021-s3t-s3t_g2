package idjr;

import reseau.packet.Packet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;

import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.out;

import java.net.Socket;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<Socket> {
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
	public void init(ControleurReseau netWorkManager) {
		super.setControleurReseau(netWorkManager);
	}

	/**
	 * Traitement des paquets TCP
	 *
	 * @param packet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Packet packet, String message, Socket extra) {
		switch (packet.getKey()) {
		//TODO gérer paquet DC
		case "IP":
			initialiserPartie(packet, message);
			break;
		case "DC":
			recupCarte(packet, message);
			break;
		case "PIIJ":
			lancerDes(packet, message);// savoir comment return plusieur choses
			break;
		case "PIRD":
			choisirDestPion(packet, message);
			break;
		case "PIIG":
			break;
		case "IT":
			debutTour(packet, message);
			break;
		case "PAZ":
			lanceDesChefVigil(packet, message);
			break;
		case "PCD":
			choixDestVigil(packet, message);
			break;
		case "CDCDV":
			choisirDest(packet, message);
			break;
		case "CDZVI":
			destZombieVengeur(packet, message);
			break;
		case "PDP":
			debutDeplacemant(packet, message);
			break;
		case "DPD":
			deplacerPion(packet, message);
			break;
		case "DPI":
			break;
		case "PRAZ":
			debutPhaseAttaque(packet, message);
			break;
		case "RAZA":
			attaqueZombie(packet, message);
			break;
		case "RAZDS":
			choisirSacrifice(packet, message);
			break;
		case "RAZIF":
			break;
		case "FP":
			finPartie(packet, message);
			break;
		case "RAZDD":
			fournirActionsDefense(packet, message);
			break;
		case "PVDV":
			ChoisirQuiVoter(packet, message);
			break;
		case "AZDCS":
			ReponseJoueurCourant(packet, message);
			break;
		case "PVD":
			IndiquerCarteJouees(packet, message);
			break;
		case "FCRC":
			recupCarte(packet, message);
			break;
		case "PIPZ":
			break;
		case "PFC":
			phaseFouilleCamion();
			break;
		case "FCLC":
			choixCarteFouille(packet, message);
			break;
		case "RFC":
			break;
		case "PECV":
			phaseElectionChefVigile();
			break;
		case "RECV":
			break;
		case "CDFC":
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	private void debutPhaseAttaque(Packet packet, String message) {
		traitementI.debutPhaseAttaque(core);
	}

	public void lancerDes(Packet packet, String message) {
		traitementI.lancerDes(core, (List<?>) packet.getValue(message, 2));
		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PILD", m1, core.getJoueurId()));
	}

	public void choisirDestPion(Packet packet, String message) {
		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PICD",
						traitementI.choisirDestPlacement(core, (List<?>) packet.getValue(message, 1),
								(List<?>) packet.getValue(message, 2)),
						traitementI.choisirPionPlacement(core), m1, core.getJoueurId()));
	}

	public void ChoisirQuiVoter(Packet packet, String message) {
		out.println(packet.getDocs());
		System.out.print("ChoisirQuiVoter");
		String messageTcp = getControleurReseau().construirePaquetTcp("PVCV", traitementI.getRandom(core, core.getVoteType()),
				(String) packet.getValue(message, 1), (int) packet.getValue(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}
	
	private void recupCarte(Packet packet, String message) {
		core.getListeCarte().add((CarteType)packet.getValue(message, 1));
		//TODO déplacer (isoler réseaux) et mettre a jour liste carte
	}
	
	public void IndiquerCarteJouees(Packet packet, String message) {
		out.println(packet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("PVC", traitementI.IndiquerCarteJouees(core),
				/* , */ (String) packet.getValue(message, 1), (int) packet.getValue(message, 2),
				(String) core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}


	public void debutTour(Packet packet, String message) {
		traitementI.debutTour(core, (List<Couleur>) packet.getValue(message, 2));
	}
	
	private void fournirActionsDefense(Packet packet, String message) {
		List<Object> listerenvoye = traitementI.listeCarteJouee(this.core, (int) packet.getValue(message, 1));
		String messageTCP = getControleurReseau().construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) packet.getValue(message, 2), (int) packet.getValue(message, 3), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTCP);
	}

	public void lanceDesChefVigil(Packet packet, String message) {
		// TODO réorganiser
		if (core.getInitializer() != null)
			core.getInitializer().nomPhase("Phase d’arrivée des zombies");
		Couleur c1 = (Couleur) packet.getValue(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValue(message, 3);
			int m2 = (int) packet.getValue(message, 4);
			String messageTcp = getControleurReseau().construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
			getControleurReseau().getTcpClient().attendreMessage("AZLAZ");
			String rep = getControleurReseau().getTcpClient().getMessage("AZLAZ");
			List<?> lT = (List<?>) getControleurReseau().getPaquetTcp("AZLAZ").getValue(rep, 1);
			List<Integer> l = new ArrayList<>();
			for (Object o : lT)
				l.add((Integer) o);

			if (core.getInitializer() != null)
				core.getInitializer().desVigiles(IdjrTools.getLieuByIndex(l));
		}
	}
	
	private void choixCarteFouille(Packet packet, String message) {
		List<Object> listeResultat = traitementI.carteFouille((List<CarteType>) packet.getValue(message, 1), core);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("SCFC", (CarteType)listeResultat.get(0), (CarteType)listeResultat.get(1), (Couleur)listeResultat.get(3),
						(CarteType)listeResultat.get(2), (String) packet.getValue(message, 2), packet.getValue(message, 3),
						core.getJoueurId()));

	}

	public void choixDestVigil(Packet packet, String message) {
		// TODO réorganiser
		if (core.getInitializer() != null)
			core.getInitializer().nomPhase("Phase de choix d’une destination");
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			out.println("Entrez une destination");
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDCV", traitementI.choixDest(core),
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		} else if (core.getCouleur() != (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			return;
		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementI.choixDest(core),
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}
	
	public void ReponseJoueurCourant(Packet packet, String message) {
		CarteType RJ = traitementI.ReponseJoueurCourant(core);
		String IDP = (String) packet.getValue(message, 1);
		int NT = (int) packet.getValue(message, 2);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
	}

	public void choisirDest(Packet packet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)) {
			return;
		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementI.choixDest(core),
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void destZombieVengeur(Packet packet, String message) {
		out.println("Entrez une destination pour le zombie vengeur");
		String message1 = getControleurReseau().construirePaquetTcp("CDDZVJE", traitementI.choixDest(core),
				packet.getValue(message, 1), packet.getValue(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(message1);
	}

	public void debutDeplacemant(Packet packet, String message) {
		traitementI.debutDeplacemant(core, (List<?>) packet.getValue(message, 4));
	}

	public void deplacerPion(Packet packet, String message) {
		List<Object> listRenvoye = traitementI.pionADeplacer(core, (int) packet.getValue(message, 1),
				(HashMap<Integer, List<Integer>>) packet.getValue(message, 2));
		String messageTcp = getControleurReseau().construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) packet.getValue(message, 3),
				(int) packet.getValue(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	public void attaqueZombie(Packet packet, String message) {
		traitementI.attaqueZombie(core, (List<PionCouleur>) (packet.getValue(message, 2)));
	}

	public void choisirSacrifice(Packet packet, String message) {
		out.println(packet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("RAZCS", (int) packet.getValue(message, 1),
				traitementI.choisirSacrifice(core, (List<?>) packet.getValue(message, 2)),
				(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	private void finPartie(Packet packet, String message) {
		traitementI.finPartie(core, (Couleur) packet.getValue(message, 2));
	}

	public void phaseFouilleCamion() {
		// TODO réorganiser
		if (core.getInitializer() != null)
			core.getInitializer().nomPhase("Phase de fouille du camion");

	}

	public void phaseElectionChefVigile() {
		// TODO réorganiser
		if (core.getInitializer() != null)
			core.getInitializer().nomPhase("Phase d’élection du chef des vigiles");
	}

	public void initialiserPartie(Packet packet, String message) {
		traitementI.initialiserPartie(this.core, (List<?>) packet.getValue(message, 1),
				(List<Couleur>) packet.getValue(message, 2), (int) packet.getValue(message, 3));
	}

	@Override
	public void set(Object core) {
		this.core = (Idjr) core;
	}
}
