package idjr;

import java.net.Socket;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.TcpClientSocket;
import reseau.tool.ThreadTool;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.PionType;
import reseau.type.VigileEtat;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PacketHandlerTcp {
	private final NetWorkManager nwm;
	private final Idjr core; // TODO Add the game manager (core)

	/**
	 * @param netWorkManager le controleur réseau
	 * @param core           coeur du jeu
	 */
	public PacketHandlerTcp(NetWorkManager netWorkManager, Object core) {
		this.nwm = netWorkManager;
		this.core = (Idjr) core;// TODO Add the game manager (core)
	}

	/**
	 * Traitement des paquets TCP
	 *
	 * @param packet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @param socket  le socket du paquet
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	public String traitement(Packet packet, String message, Socket socket) {
		switch (packet.getKey()) {
			case "DPD":
				return deplacerPion(packet, message);
			case "PIIJ":
				return lancerDes(packet, message);// savoir comment return plusieur choses
			case "PIRD":
				return choisirDestPion(packet, message);
			case "PAZ":
				return lanceDesChefVigil(packet, message);
			case "PCD":
				return choixDestVigil(packet, message);
			case "CDCDV":
				return choisirDest(packet, message);
			case "CDZVI":
				return DestZombieVengeur(packet, message);
			case "RAZDS":
				return choisirSacrifice(packet, message);
			case "IP":
				return initialiserPartie(packet, message);

			case "PIPZ":
			case "IT":
			case "PFC":
			case "RFC":
			case "PECV":
			case "RECV":
			case "PIIG":
			case "CDFC":
				return "";

			// TODO voir CDDJ
			default:
				throw new IllegalStateException(
						MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	@SuppressWarnings("unchecked")
	public String initialiserPartie(Packet packet, String message) {
		List<String> noms = (List<String>) packet.getValue(message, 1);
		for (String string : noms)
			System.out.println(string);
		List<Couleur> couleurs = (List<Couleur>) packet.getValue(message, 2);
		for (Couleur string : couleurs)
			System.out.println(string);
		System.out.println(core.getNom());
		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
		return "";
	}

	public String deplacerPion(Packet packet, String message) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez une destination");
		int dest = sc.nextInt();
		System.out.println("Entrez un pion (Piontype)");
		PionType pion = PionType.valueOf(sc.nextLine());
		System.out.println("Entrez une carte Sprint(si disponible 'SPR' sinon 'NUL')");
		String carteSprint = sc.nextLine();
		sc.close();

		return nwm.getPacketsTcp().get("DPR").build(dest, pion, carteSprint, packet.getValue(message, 3),
				packet.getValue(message, 4), core.getJoueurId());

	}

	public String lancerDes(Packet packet, String message) {
		System.out.println((String) packet.getValue(message, 3));
		System.out.println(core.getJoueurId());
		core.setPionAPos((List<Integer>) packet.getValue(message, 2));
		return nwm.getPacketsTcp().get("PILD").build((String) packet.getValue(message, 3), core.getJoueurId());
	}

	public String choisirDestPion(Packet packet, String message) {
		List<Integer> pionsRestant = (List<Integer>) packet.getValue(message, 2);

		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez une destination");
		// int dest = sc.nextInt();
		int dest = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		System.out.println("Entrez un pion (Piontype)");
		int pion = pionsRestant.get(new Random().nextInt(pionsRestant.size()));
		System.out.println(dest);
		System.out.println(pion);
		sc.close();
		return nwm.getPacketsTcp().get("PICD").build(pion, dest, (String) packet.getValue(message, 3),
				(String) core.getJoueurId());
	}

	public String lanceDesChefVigil(Packet packet, String message) {
		System.out.println(core.getCouleur());
		System.out.println((Couleur) packet.getValue(message, 1));
		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)) {
			String messageTcp = nwm.getPacketsTcp().get("AZLD").build((String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), (String) core.getJoueurId());
			TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
			System.out.println("YAY");
		}
		return "";
	}

	public String choixDestVigil(Packet packet, String message) {
		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			System.out.println("Entrez une destination");
			int dest = new Random().nextInt(6) + 1;

			String messageTcp = nwm.getPacketsTcp().get("CDDCV").build(dest, (String)packet.getValue(message, 3),
					(int)packet.getValue(message, 4), core.getJoueurId());
			TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);

		}
		else {
			System.out.println("Entrez une destination");
			int dest = new Random().nextInt(6) + 1;

			String messageTcp = nwm.getPacketsTcp().get("CDDJ").build(dest, (String)packet.getValue(message, 3),
					(int)packet.getValue(message, 4), core.getJoueurId());
			TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
		}

		return "";
	}

	public String choisirDest(Packet packet, String message) {
		if (core.getCouleur() == packet.getValue(message, 1)) {
			return null;
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrez une destination");
			int dest = sc.nextInt();
			System.out.println("Entrez un pion (Piontype)");
			PionType pion = PionType.valueOf(sc.nextLine());
			sc.close();
			return nwm.getPacketsTcp().get("CDDJ").build(dest, packet.getValue(message, 3), packet.getValue(message, 4),
					core.getJoueurId());
		}
	}

	public String DestZombieVengeur(Packet packet, String message) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez une destination pour le zombie vengeur");
		int destZomb = sc.nextInt();
		sc.close();
		return nwm.getPacketsTcp().get("CDDZVJE").build(destZomb, packet.getValue(message, 1),
				packet.getValue(message, 2), core.getJoueurId());

	}

	public String choisirSacrifice(Packet packet, String message) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez un pion (PionCouleur)");
		PionCouleur pion = PionCouleur.valueOf(sc.nextLine());
		return nwm.getPacketsTcp().get("RAZCS").build(packet.getValue(message, 1), pion, packet.getValue(message, 2),
				packet.getValue(message, 3), core.getJoueurId());
	}

}
