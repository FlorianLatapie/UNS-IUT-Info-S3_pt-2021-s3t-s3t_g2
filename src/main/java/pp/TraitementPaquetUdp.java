package pp;

import pp.controleur.ControleurJeu;
import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.ConnexionType;
import reseau.type.TypePartie;

import java.net.DatagramPacket;
import java.text.MessageFormat;

/**
 * <h1>Permet de gérer les packets UDP</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class TraitementPaquetUdp extends TraitementPaquet<DatagramPacket> {
	private ControleurJeu core;

	/**
	 * @param netWorkManager le jeu.controleur réseau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetUdp(Object core) {
		this.core = (ControleurJeu) core;
	}

	/**
	 * Traitement des paquet UDP
	 *
	 * @param packet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Paquet packet, String message, DatagramPacket datagram) {
		switch (packet.getCle()) {
		case "RP":
			rp(packet, message);
			break;
		case "ACP":
			acp(packet, message);
			break;
		case "AMP":
			amp(packet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getCle()));
		}
	}

	@Override
	public void init() {
		
	}

	public void rp(Paquet packet, String message) {
		if (ConnexionType.SERVEUR != ControleurReseau.getConnexionType())
			return;

		TypePartie typePartie = (TypePartie) packet.getValeur(message, 1);

		String m = ControleurReseau.construirePaquetUdp("AMP",core.getPartieId(),
				ControleurReseau.getIp().getHostAddress(), ControleurReseau.getTcpPort(), core.getNomPartie(),
				core.getNbjtotal(), core.getNbjr(), core.getNbjv(), core.getNbjractuel(), core.getNbjvactuel(),
				core.getStatus());
		
		switch (typePartie) {
		case JRU:
			if (core.getNbjr() == core.getNbjtotal())
				ControleurReseau.envoyerUdp(m);
			break;
		case BOTU:
			if (core.getNbjv() == core.getNbjtotal())
				ControleurReseau.envoyerUdp(m);
			break;
		case MIXTE:
		default:
			ControleurReseau.envoyerUdp(m);
		}
	}

	public void acp(Paquet packet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;
	}

	public void amp(Paquet packet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;
	}

	public void ip(Paquet packet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;
	}

	@Override
	public void set(Object core) {
		this.core = (ControleurJeu) core;
	}
}
