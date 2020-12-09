package idjr;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.ConnexionType;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetUdp extends TraitementPaquet<DatagramPacket> {
	private Idjr core;// TODO Add the game manager (core)

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetUdp(Object core) {
		this.core = (Idjr) core;// TODO Add the game manager (core)
	}

	@Override
	public void init(ControleurReseau netWorkManager) {
		setControleurReseau(netWorkManager);
	}

	/**
	 * Traitement des paquet UDP
	 *
	 * @param packet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Paquet packet, String message, DatagramPacket extra) {
		switch (packet.getCle()) {
		case "ACP":
			acp(packet, message);
			break;
		case "AMP":
			amp(packet, message);
			break;
		case "RP":
			rp(packet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getCle()));
		}
	}

	private void rp(Paquet packet, String message) {
		if (ConnexionType.SERVEUR != getControleurReseau().getConnexionType())
			return;
	}

	public void acp(Paquet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
	}

	public void amp(Paquet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
		String partie = (String) packet.getValeur(message, 1);
		InetAddress ip = null;
		try {
			ip = InetAddress.getByName((String) packet.getValeur(message, 2));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int port = (int) packet.getValeur(message, 3);
		PartieInfo partieInfo = new PartieInfo(ip, port, partie, core.getTypeJoueur());
		core.addPartie(partieInfo);

		System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", packet.getValeur(message, 1)));
	}

	@Override
	public void set(Object core) {
		this.core = (Idjr) core;
	}
}
