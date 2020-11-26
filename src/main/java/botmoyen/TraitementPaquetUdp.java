package botmoyen;

import reseau.packet.Packet;
import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.tool.ThreadOutils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetUdp extends TraitementPaquet<DatagramPacket> {
	private BotMoyen core;// TODO Add the game manager (core)

	/**
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetUdp(Object core) {
		this.core = (BotMoyen) core;// TODO Add the game manager (core)
	}

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
	public void traitement(Packet packet, String message, DatagramPacket datagramPacket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {
			
		}
		switch (packet.getKey()) {
		case "ACP":
			acp(packet, message);
			break;
		case "AMP":
			amp(packet, message);
			break;
		case "IP":
			ip(packet, message);
			break;
		case "RP":
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	public void acp(Packet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
		InetAddress address = null;
		try {
			address = InetAddress.getByName((String) packet.getValue(message, 2));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		core.setIpPp(address);
		core.setPortPp((int) packet.getValue(message, 3));

		System.out.println(
				MessageFormat.format("Une nouvelle partie vient d''etre trouvÃ© !\n{0}", packet.getValue(message, 1)));
		System.out.println("Voulez-vous rejoindre cette partie ? (K)");
		Scanner sc = new Scanner(System.in);
		// String rep = new Scanner(System.in).nextLine();
		String rep = "K";
		if (rep.equals("K")) {
			System.out.println("Entrez votre nom !");
			// String nomdujoueur = sc.nextLine();
			String nomdujoueur = "BOT" + new Random().nextInt(9999);
			core.setNom(nomdujoueur);
			String messageTcp = getControleurReseau().construirePaquetTcp("DCP",nomdujoueur,
					core.getTypeJoueur(), "P" + (int) packet.getValue(message, 1));
			ThreadOutils.asyncTask(() -> {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}

					getControleurReseau().getTcpClient().envoyer(messageTcp);

				getControleurReseau().getTcpClient().attendreMessage("ACP");
			});

		}
		sc.close();
	}

	public void amp(Packet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;

		System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", packet.getValue(message, 1)));
	}

	public void ip(Packet packet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;

		System.out.println("Informations sur la partie !");
	}

	@Override
	public void set(Object core) {
		this.core = (BotMoyen)core;
	}
}
