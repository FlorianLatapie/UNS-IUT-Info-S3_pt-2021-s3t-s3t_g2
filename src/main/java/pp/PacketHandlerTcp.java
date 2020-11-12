package pp;

import pp.controleur.ControleurJeu;
import reseau.packet.Packet;
import reseau.socket.Connection;
import reseau.socket.NetWorkManager;
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
public class PacketHandlerTcp {
	private final NetWorkManager nwm;
	private final ControleurJeu core;

	/**
	 * @param netWorkManager le jeu.controleur réseau
	 * @param core           coeur du jeu
	 */
	public PacketHandlerTcp(NetWorkManager netWorkManager, Object core) {
		this.nwm = netWorkManager;
		this.core = (ControleurJeu) core;
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
	public void traitement(Packet packet, String message) {
		switch (packet.getKey()) {
		case "DCP":
			break;
		//default:
			//throw new IllegalStateException(
					//MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
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
	public void traitement(Packet packet, String message, Connection connection) {
		switch (packet.getKey()) {
		case "DCP":
			dcp(packet, message, connection);
			break;
		//default:
			//throw new IllegalStateException(
					//MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	private void dcp(Packet packet, String message, Connection connection) {
		switch (core.getStatus()) {
		case ATTENTE:
			String id = null;
			try {
				id = core.ajouterJoueur(InetAddress.getLocalHost(), 5555, (String) packet.getValue(message, 1),
						(TypeJoueur) packet.getValue(message, 2), connection);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			connection.send(nwm.getPacketsTcp().get("ACP").build(core.getPartieId(), id));
			break;
		case ANNULEE:
		case COMPLETE:
		case TERMINE:
			connection.send(nwm.getPacketsTcp().get("RCP").build(core.getPartieId()));
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + core.getStatus());
		}
	}
}
