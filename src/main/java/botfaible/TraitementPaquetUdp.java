package botfaible;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.tool.ThreadOutils;
import reseau.type.ConnexionType;

import java.io.File;
import java.io.FileNotFoundException;
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
	private BotFaible core;// TODO Add the game manager (core)

	/**
	 * @param core coeur du jeu
	 */
	public TraitementPaquetUdp(Object core) {
		this.core = (BotFaible) core;// TODO Add the game manager (core)
	}

	public void init() {
		
	}

	/**
	 * Traitement des paquet UDP
	 *
	 * @param packet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Paquet packet, String message, DatagramPacket datagramPacket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (packet.getCle()) {
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
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getCle()));
		}
	}

	public void acp(Paquet packet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;
		InetAddress address = null;
		try {
			System.out.println((String) packet.getValeur(message, 2));
			address = InetAddress.getByName((String) packet.getValeur(message, 2));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		core.setIpPp(address);
		core.setPortPp((int) packet.getValeur(message, 3));
		System.out.println(core.getIpPp());
		core.setPortPp(core.getPortPp());

		System.out.println(
				MessageFormat.format("Une nouvelle partie vient d''etre trouvÃ© !\n{0}", packet.getValeur(message, 1)));
		System.out.println("Voulez-vous rejoindre cette partie ? (K)");
		
		String prenoms = "Ressources/Prenoms/prenomsFR.txt";
		Scanner scanneurPrenom;
		String nomDuJoueur = "Bot Faible ";
		try {
			scanneurPrenom = new Scanner(new File(prenoms));
			StringBuilder fichierLu = new StringBuilder();
			while(scanneurPrenom.hasNext()) {
				fichierLu.append(scanneurPrenom.nextLine()+"\n");
			}
			String[] tableauPrenom = fichierLu.toString().split("\n");
			int choix= new Random().nextInt(tableauPrenom.length);
			System.out.println(nomDuJoueur + tableauPrenom[choix]);
			nomDuJoueur += tableauPrenom[choix];
			scanneurPrenom.close();
		} catch (FileNotFoundException e) {
			nomDuJoueur += new Random().nextInt(9999);
			System.out.println("bug");
			e.printStackTrace();
		}
		core.setNom(nomDuJoueur);
		ControleurReseau.demarrerClientTcp(core.getIpPp());

		ThreadOutils.asyncTask("acp", () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String messageTcp = ControleurReseau.construirePaquetTcp("DCP", core.getNom(), core.getTypeJoueur(),
					"P" + (int) packet.getValeur(message, 1));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ControleurReseau.envoyerTcp(messageTcp);
			ControleurReseau.attendreTcp("ACP");
		});

	}

	public void amp(Paquet packet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;

		System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", packet.getValeur(message, 1)));
	}

	public void ip(Paquet packet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;

		System.out.println("Informations sur la partie !");
	}

	@Override
	public void set(Object core) {
		this.core = (BotFaible) core;
	}
}
