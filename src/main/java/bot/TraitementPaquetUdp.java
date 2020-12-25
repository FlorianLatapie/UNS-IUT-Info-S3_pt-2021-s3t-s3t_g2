package bot;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.tool.ThreadOutils;
import reseau.type.ConnexionType;
import reseau.type.Statut;

import java.io.File;
import java.io.FileNotFoundException;
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
	private Bot core;// TODO Add the game manager (core)

	/**
	 * @param core coeur du jeu
	 */
	public TraitementPaquetUdp(Object core) {
		this.core = (Bot) core;// TODO Add the game manager (core)
	}

	public void init() {
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
		case "RP":
			break;
		default:
			throw new IllegalStateException("[UDP] Il n''y a pas de traitement possible pour " + paquet.getCle());
		}
	}

	public void acp(Paquet paquet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;
		
		if (core.getBotMode() != BotMode.Automatique)
			return;
		
		InetAddress address = null;
		try {
			address = InetAddress.getByName((String) paquet.getValeur(message, 2));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		core.connecter(address, (int) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 1));
	}

	public void amp(Paquet paquet, String message) {
		if (ConnexionType.CLIENT != ControleurReseau.getConnexionType())
			return;
		String partie = (String) paquet.getValeur(message, 1);
		InetAddress ip = null;
		try {
			ip = InetAddress.getByName((String) paquet.getValeur(message, 2));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int port = (int) paquet.getValeur(message, 3);
		String nom = (String) paquet.getValeur(message, 4);
		int nbjr = (int) paquet.getValeur(message, 8);
		int nbjrMax = (int) paquet.getValeur(message, 6);
		int nbjb = (int) paquet.getValeur(message, 9);
		int nbjbMax = (int) paquet.getValeur(message, 7);
		Statut stat = (Statut) paquet.getValeur(message, 10);
		PartieInfo partieInfo = new PartieInfo(ip, port, partie, core.getTypeJoueur(), nbjr, nbjb, nbjrMax, nbjbMax,
				stat,nom);
		core.ajouterPartie(partieInfo);

		System.out.println(MessageFormat.format("Mise a jour d''une partie !\n{0}", paquet.getValeur(message, 1)));
	}

	@Override
	public void set(Object core) {
		this.core = (Bot) core;
	}
}
