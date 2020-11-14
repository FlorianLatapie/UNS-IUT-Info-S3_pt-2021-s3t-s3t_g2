package pp;

import pp.controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.TypePartie;

import java.net.DatagramPacket;
import java.text.MessageFormat;

/**
 * <h1>Permet de gerer les packets</h1>
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
	public void traitement(Packet packet, String message, DatagramPacket datagram) {
		switch (packet.getKey()) {
		case "RP":
			rp(packet, message);
			break;
		case "ACP":
			acp(packet, message);
			break;
		case "AMP":
			amp(packet, message);
			break;
		case "IP":
			ip(packet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	@Override
	public void init(ControleurReseau controleurReseau) {
		this.setControleurReseau(controleurReseau);
	}

	public void rp(Packet packet, String message) {
		if (ConnexionType.SERVER != getControleurReseau().getConnexionType())
			return;

		TypePartie typePartie = (TypePartie) packet.getValue(message, 1);

		String m = getControleurReseau().getPacketsUdp().get("AMP").build(core.getPartieId(),
				getControleurReseau().getIp().getHostAddress(), getControleurReseau().getTcpPort(), core.getNomPartie(),
				core.getNbjtotal(), core.getNbjr(), core.getNbjv(), core.getNbjractuel(), core.getNbjvactuel(),
				core.getStatus());
		switch (typePartie) {
		case JRU:
			if (core.getNbjr() == 6)
				getControleurReseau().getUdpConnexion().envoyer(m);
			break;
		case BOTU:
			if (core.getNbjv() == 6)
				getControleurReseau().getUdpConnexion().envoyer(m);
			break;
		case MIXTE:
		default:
			getControleurReseau().getUdpConnexion().envoyer(m);
		}
	}

	public void acp(Packet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
	}

	public void amp(Packet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
	}

	public void ip(Packet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
	}

	@Override
	public void set(Object core) {
		this.core = (ControleurJeu) core;
	}
}
