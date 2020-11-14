package pp;

import pp.controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.Connexion;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.TypeJoueur;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.out;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<Connexion> {
	private ControleurJeu core;

	/**
	 * @param netWorkManager le jeu.controleur réseau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (ControleurJeu) core;
	}

	public void init(ControleurReseau controleurReseau) {
		setControleurReseau(controleurReseau);
	}

	/**
	 * Traitement des paquet TCP
	 *
	 * @param packet     le paquet du message
	 * @param message    le message sous forme de chaine de caractere
	 * @param connection le socket du paquet
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	public void traitement(Packet packet, String message, Connexion connection) {
		switch (packet.getKey()) {
		case "DCP":
			dcp(packet, message, connection);
			break;
		}
	}

	private void dcp(Packet packet, String message, Connexion connection) {
		switch (core.getStatus()) {
		case ATTENTE:
			String id = null;
			try {
				id = core.ajouterJoueur(InetAddress.getLocalHost(), 5555, (String) packet.getValue(message, 1),
						(TypeJoueur) packet.getValue(message, 2), connection);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			connection.envoyer(getControleurReseau().getPacketsTcp().get("ACP").build(core.getPartieId(), id));
			break;
		case ANNULEE:
		case COMPLETE:
		case TERMINE:
			connection.envoyer(getControleurReseau().getPacketsTcp().get("RCP").build(core.getPartieId()));
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + core.getStatus());
		}
	}

	@Override
	public void set(Object core) {
		this.core = (ControleurJeu) core;
	}
}
