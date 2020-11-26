package botfaible;

import reseau.packet.Packet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;
import traitement.Traitement;

import java.text.MessageFormat;
import java.util.*;
import java.net.Socket;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<Socket> {
	private BotFaible core;
	private Traitement traitementB;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (BotFaible) core;
		this.traitementB = new Traitement();
	}

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
	public void traitement(Packet packet, String message, Socket socket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (packet.getKey()) {
		case "IP":
			initialiserPartie(packet, message);
			break;
		case "PIIJ":
			lancerDes(packet, message);// savoir comment return plusieur choses
			break;
		case "PIRD":
			choisirDestPlacement(packet, message);
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
		case "PRAZ":
			break;
		case "RAZA":
			break;
		case "RAZDS":
			choisirSacrifice(packet, message);
			break;
		case "RAZIF":
			break;
		case "FP":
			finPartie(packet, message);
			break;
		case "ACP":
			accepter(packet, message);
			break;

		case "PIPZ":
		case "PFC":
		case "RFC":
		case "PECV":
		case "RECV":
		case "CDFC":
		case "AZLAZ":
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	public void accepter(Packet packet, String message) {
		core.setJoueurId((String) packet.getValue(message, 2));
	}

	public void initialiserPartie(Packet packet, String message) {
		traitementB.initialiserPartie(packet, message, core);
	}

	public void lancerDes(Packet packet, String message) {
		traitementB.lancerDes(packet, message, core, getControleurReseau());
	}

	// Entry 2 OK
	public void choisirDestPlacement(Packet packet, String message) {
		int dest = 0;
		List<Integer> destRestant = traitementB.choisirDestPlacementInit(packet, message, core, getControleurReseau());
		if (!destRestant.isEmpty())
			dest = destRestant.get(new Random().nextInt(destRestant.size()));

		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));

		traitementB.choisirDestPlacement(packet, message, core, getControleurReseau(), dest, pion);
	}

	public void debutTour(Packet packet, String message) {
		traitementB.debutTour(packet, message, core);
	}

	public void lanceDesChefVigil(Packet packet, String message) {
		traitementB.lanceDesChefVigil(packet, message, core, getControleurReseau());
	}

	// Entry 1 OK
	public void choixDestVigil(Packet packet, String message) {
		if (traitementB.choixDestVigilInit(core, packet, message)){
			int dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
			traitementB.choixDestVigil(core, packet, message, getControleurReseau(), dest);
		}
	}

	// Entry 1 OK
	public void choisirDest(Packet packet, String message) {
		if (traitementB.choisirDestInit(packet, message, core)) {
			int dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
			traitementB.choisirDest(packet, message, getControleurReseau(), core, dest);
		}
	}

	// Entry 1 OK
	public void destZombieVengeur(Packet packet, String message) {
		int dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
		traitementB.destZombieVengeur(packet, message, getControleurReseau(), core, dest);
	}

	public void debutDeplacemant(Packet packet, String message) {
		traitementB.debutDeplacemant(core, packet, message);
	}

	//Entry 1
	public void deplacerPion(Packet packet, String message) {
		traitementB.deplacerPion(packet, message, getControleurReseau(), core);
	}

	// Entry 1 OK
	public void choisirSacrifice(Packet packet, String message) {
		List<Integer> listPion = traitementB.choisirSacrificeInit(packet, message);
		int pionTemp = listPion.get(new Random().nextInt(listPion.size()));
		traitementB.choisirSacrifice(packet, message, core, getControleurReseau(), pionTemp);
	}

	private void finPartie(Packet packet, String message) {
		traitementB.finPartie(packet, message, core);
	}

	@Override
	public void set(Object core) {
		this.core = (BotFaible) core;
	}
}
