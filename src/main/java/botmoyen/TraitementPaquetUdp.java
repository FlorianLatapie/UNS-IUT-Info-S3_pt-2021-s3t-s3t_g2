package botmoyen;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.tool.ThreadOutils;
import reseau.type.ConnexionType;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * <h1>Permet de gerer les paquets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetUdp extends TraitementPaquet<DatagramPacket> {
	private BotMoyen core;// TODO Add the game manager (core)

	/**
	 * @param core coeur du jeu
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
	 * @param Paquet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Paquet paquet, String message, DatagramPacket datagrampaquet) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (paquet.getCle()) {
		case "ACP":
			acp(paquet, message);
			break;
		case "AMP":
			amp(paquet, message);
			break;
		case "IP":
			ip(paquet, message);
			break;
		case "RP":
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", paquet.getCle()));
		}
	}

	public void acp(Paquet paquet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;
		InetAddress address = null;
		try {
			System.out.println((String) paquet.getValeur(message, 2));
			address = InetAddress.getByName((String) paquet.getValeur(message, 2));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		core.setIpPp(address);
		core.setPortPp((int) paquet.getValeur(message, 3));
		System.out.println(core.getIpPp());
		core.setPortPp(core.getPortPp());
		
		System.out.println(
				MessageFormat.format("Une nouvelle partie vient d''etre trouvÃ© !\n{0}", paquet.getValeur(message, 1)));
		String nomdujoueur = "BOT" + new Random().nextInt(9999);
		core.setNom(nomdujoueur);
		getControleurReseau().tcp(core.getIpPp());	
		
		ThreadOutils.asyncTask(() ->{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String messageTcp = getControleurReseau().construirePaquetTcp("DCP", nomdujoueur, core.getTypeJoueur(),
					"P" + (int) paquet.getValeur(message, 1));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getControleurReseau().getTcpClient().envoyer(messageTcp);
			getControleurReseau().getTcpClient().attendreMessage("ACP");
		});
		
		

	}

	public void amp(Paquet paquet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;

		System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", paquet.getValeur(message, 1)));
	}

	public void ip(Paquet paquet, String message) {
		if (ConnexionType.CLIENT != getControleurReseau().getConnexionType())
			return;

		System.out.println("Informations sur la partie !");
	}

	@Override
	public void set(Object core) {
		this.core = (BotMoyen)core;
	}
}
