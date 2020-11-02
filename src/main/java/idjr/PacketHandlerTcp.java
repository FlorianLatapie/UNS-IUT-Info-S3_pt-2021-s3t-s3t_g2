package idjr;

import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import reseau.packet.Packet;
import reseau.socket.NetWorkManager;
import reseau.socket.TcpClientSocket;
import reseau.tool.ThreadTool;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class PacketHandlerTcp {
	private final NetWorkManager nwm;
	private final Idjr core; // TODO Add the game manager (core)

	/**
	 * @param netWorkManager le controleur rÃ©seau
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

			case "IP":
				return initialiserPartie(packet, message);
			case "PIIJ":
				return lancerDes(packet, message);// savoir comment return plusieur choses
			case "PIRD":
				return choisirDestPion(packet, message);
			case "PIIG":
				return joueurDeplacement(packet, message);
			case "PAZ":
				return lanceDesChefVigil(packet, message);
			case "PCD":
				return choixDestVigil(packet, message);
			case "CDCDV":
				return choisirDest(packet, message);
			case "CDZVI":
				return destZombieVengeur(packet, message);
			case "PDP":
				return debutDeplacemant(packet, message);
			case "DPD":
				return deplacerPion(packet, message);
			case "DPI":
				return tousDeplacment(packet, message);
			case "PRAZ":
				return attaqueZombie(packet, message);
			case "RAZDS":
				return choisirSacrifice(packet, message);
			case "RAZIF":
				return tousSacrifice(packet, message);

			case "PIPZ":
			case "IT":
			case "PFC":
			case "RFC":
			case "PECV":
			case "RECV":
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
		ArrayList<Joueur> listeJoueursInitiale = new ArrayList();
		for (int i = 0; i < noms.size(); i++) {
			listeJoueursInitiale.add(new Joueur(noms.get(i), couleurs.get(i)));
		}
		core.getJeu().initJoueurs(listeJoueursInitiale);
		return "";
	}

	public String lancerDes(Packet packet, String message) {
		System.out.println((String) packet.getValue(message, 3));
		System.out.println(core.getJoueurId());
		core.setPionAPos((List<Integer>) packet.getValue(message, 2));
		return nwm.getPacketsTcp().get("PILD").build((String) packet.getValue(message, 3), core.getJoueurId());
	}

	public String choisirDestPion(Packet packet, String message) {
		List<Integer> destRestant = (List<Integer>) packet.getValue(message, 2);

		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez une destination");
		// int dest = sc.nextInt();

		int pion = 0;
		if (core.getPionAPos().size() != 0)
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));

		System.out.println("Entrez un pion (Piontype)");
		int dest = 0;
		if (destRestant.size() != 0)
			dest = destRestant.get(new Random().nextInt(destRestant.size()));
		System.out.println(dest);
		System.out.println(pion);
		System.out.println(core.getMoi());
		sc.close();

		core.getJeu().placePerso(core.getMoi(), pion, dest);
		System.out.println(core.getJeu().afficheJeu());
		return nwm.getPacketsTcp().get("PICD").build(dest, pion, (String) packet.getValue(message, 3),
				(String) core.getJoueurId());
	}

	public String joueurDeplacement(Packet packet, String message) {
		Couleur c = (Couleur) packet.getValue(message, 1);
		int dest = (int) packet.getValue(message, 4);
		int pion = (int) packet.getValue(message, 5);
		System.out.println(dest);
		System.out.println(pion);
		core.getJeu().placePerso(core.getJoueur(c), pion, dest);
		return "";
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
			int dest = 0;
			// TODO BORDEL IL Y AVAIT PAS TOUT RANDOM
			if (core.getJeu().choixLieudispo(core.getMoi()).size() != 0)
				dest = core.getJeu().choixLieudispo(core.getMoi())
						.get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));
			String messageTcp = nwm.getPacketsTcp().get("CDDCV").build(dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());
			TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
		} else if (!(core.getCouleur() == (Couleur) packet.getValue(message, 1))
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			return "";
		} else {
			System.out.println("Entrez une destination");
			int dest = 0;
			if (!(core.getJeu().choixLieudispo(core.getMoi()).size() == 0))
				dest = core.getJeu().choixLieudispo(core.getMoi())
						.get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));

			String messageTcp = nwm.getPacketsTcp().get("CDDJ").build(dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());
			TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
		}

		System.out.println(core.getJeu().afficheJeu());
		return "";
	}

	public String choisirDest(Packet packet, String message) {
		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)) {
			return "";
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrez une destination");
			int dest = 0;
			if (!(core.getJeu().choixLieudispo(core.getMoi()).size() == 0))
				dest = core.getJeu().choixLieudispo(core.getMoi())
						.get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));
			System.out.println("Entrez un pion (Piontype)");
			// PionType pion = PionType.valueOf(sc.nextLine());
			sc.close();
			String messageTcp = nwm.getPacketsTcp().get("CDDJ").build(dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());
			TcpClientSocket.connect(core.getIpPp(), core.getPortPp(), messageTcp, null, 0);
		}

		System.out.println(core.getJeu().afficheJeu());
		return "";
	}

	public String destZombieVengeur(Packet packet, String message) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez une destination pour le zombie vengeur");
		int destZomb = 0;
		if (!(core.getJeu().choixLieudispo().size() == 0))
			destZomb = core.getJeu().choixLieudispo().get(new Random().nextInt(core.getJeu().choixLieudispo().size()));
		sc.close();
		return nwm.getPacketsTcp().get("CDDZVJE").build(destZomb, packet.getValue(message, 1),
				packet.getValue(message, 2), core.getJoueurId());

	}

	public String debutDeplacemant(Packet packet, String message) {
		core.getJeu().entreZombie((List<Integer>) packet.getValue(message, 3));
		core.getJeu().fermerLieu((List<Integer>) packet.getValue(message, 4));
		return "";
	}

	public String deplacerPion(Packet packet, String message) {
		Scanner sc = new Scanner(System.in);
		int dest = (int) packet.getValue(message, 1);
		if (core.getJeu().getLieux().get(dest).isFull())
			dest = 4;
		HashMap<Integer, List<Integer>> listedp = (HashMap<Integer, List<Integer>>) packet.getValue(message, 2);
		List<Integer> listeDest = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				if (destPos == dest)
					listeDest.add(dp.getKey());

		int pionAdep = listeDest.get(new Random().nextInt(listeDest.size()));

		System.out.println("Entrez un pion (Piontype)");
		System.out.println("Entrez une carte Sprint(si disponible 'SPR' sinon 'NUL')");
		CarteType carte = CarteType.NUL;
		core.getJeu().deplacePerso(core.getMoi(), pionAdep, dest);
		core.getJeu().fermerLieu();
		return nwm.getPacketsTcp().get("DPR").build(dest, pionAdep, carte, (String) packet.getValue(message, 3),
				(int) packet.getValue(message, 4), core.getJoueurId());

	}

	public String tousDeplacment(Packet packet, String message) {
		Couleur c = (Couleur) packet.getValue(message, 1);
		int dest = (int) packet.getValue(message, 2);
		int p = (int) packet.getValue(message, 3);
		core.getJeu().deplacePerso(core.getJoueur(c), p, dest);
		core.getJeu().fermerLieu();
		return "";
	}

	public String attaqueZombie(Packet packet, String message) {
		if ((int) packet.getValue(message, 1) != 0)
			core.getJeu().getLieux().get((int) packet.getValue(message, 1)).addZombie();
		if ((int) packet.getValue(message, 2) != 0)
			core.getJeu().getLieux().get((int) packet.getValue(message, 2)).addZombie();
		core.getJeu().fermerLieu();
		return "";
	}

	public String choisirSacrifice(Packet packet, String message) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez un pion (PionCouleur)");
		int pionInt = 0;
		if (core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).size() != 0)
			pionInt = core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).get(new Random()
					.nextInt(core.getJeu().pionChoixDispo(core.getMoi(), (int) packet.getValue(message, 1)).size()));
		String pionTemp = core.getCouleur().name().charAt(0) + "" + pionInt;
		PionCouleur pion = PionCouleur.valueOf(pionTemp);
		return nwm.getPacketsTcp().get("RAZCS").build(packet.getValue(message, 1), pion, packet.getValue(message, 2),
				packet.getValue(message, 3), core.getJoueurId());
	}

	public String tousSacrifice(Packet packet, String message) {
		PionCouleur pionTemp = PionCouleur.valueOf((String) packet.getValue(message, 2));
		Couleur pionCouleur = IdjrTools.getCouleurByChar(pionTemp);
		int pionInt = IdjrTools.getPionByValue(pionTemp);
		core.getJeu().sacrifie(core.getJoueur(pionCouleur), pionInt);
		core.getJeu().getLieux().get((int) packet.getValue(message, 1)).setNbZombies((int) packet.getValue(message, 3));
		core.getJeu().fermerLieu();
		return "";
	}

}
