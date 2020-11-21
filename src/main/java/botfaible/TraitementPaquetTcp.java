package botfaible;

import reseau.packet.Packet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;

import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.out;

import java.io.IOException;
import java.net.Socket;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<Socket> {
	private Idjr core;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (Idjr) core;
	}

	public void init(ControleurReseau netWorkManager) {
		super.setControleurReseau(netWorkManager);
	}

	/**
	 * Traitement des paquets TCP
	 *
	 * @param packet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	public void traitement(Packet packet, String message, Socket socket) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		switch (packet.getKey()) {
		case "IP":
			initialiserPartie(packet, message);
			break;
		case "PIIJ":
			lancerDes(packet, message);// savoir comment return plusieur choses
			break;
		case "PIRD":
			choisirDestPlacement(packet, message);
			break;
		case "PIIG":
			joueurPlacement(packet, message);
			break;
		case "IT":
			debutTour(packet, message);
			break;
		case "PAZ":
			lanceDesChefVigil(packet, message);
			break;
		case "PCD":
			choixDestVigil(packet, message);
			break;
		case "CDCDV":
			choisirDest(packet, message);
			break;
		case "CDZVI":
			destZombieVengeur(packet, message);
			break;
		case "PDP":
			debutDeplacemant(packet, message);
			break;
		case "DPD":
			deplacerPion(packet, message);
			break;
		case "DPI":
			tousDeplacment(packet, message);
			break;
		case "PRAZ":
			debutattaqueZombie(packet, message);
			break;
		case "RAZA":
			attaqueZombie(packet, message);
		case "RAZDS":
			choisirSacrifice(packet, message);
			break;
		case "RAZIF":
			tousSacrifice(packet, message);
			break;
		case "FP":
			finPartie(packet, message);
			break;

		case "ACP":
			accepter(packet, message);
			break;

		case "PIPZ":
		case "PFC":
		case "RFC":
		case "PECV":
		case "RECV":
		case "CDFC":
		case "AZLAZ":
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[UDP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	public void accepter(Packet packet, String message) {
		System.out.println((String) packet.getValue(message, 2));

		core.setJoueurId((String) packet.getValue(message, 2));
	}

	public void initialiserPartie(Packet packet, String message) {
		List<?> nomsT = (List<?>) packet.getValue(message, 1);
		List<String> noms = new ArrayList<>();
		for (Object o : nomsT)
			noms.add((String) o);

		List<?> couleursT = (List<?>) packet.getValue(message, 2);
		List<Couleur> couleurs = new ArrayList<>();
		for (Object o : couleursT)
			couleurs.add((Couleur) o);

		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));

		if ((int) packet.getValue(message, 3) == 2) {
			core.getLieuOuvert().add(1);
			core.getLieuOuvert().add(3);
			core.getLieuOuvert().add(4);
			core.getLieuOuvert().add(5);
			core.getLieuOuvert().add(6);
		} else {
			core.getLieuOuvert().add(1);
			core.getLieuOuvert().add(2);
			core.getLieuOuvert().add(3);
			core.getLieuOuvert().add(4);
			core.getLieuOuvert().add(5);
			core.getLieuOuvert().add(6);
		}

	}

	public void lancerDes(Packet packet, String message) {
		List<?> pionT = (List<?>) packet.getValue(message, 2);
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);

		core.setPionAPos(pion);

		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().getPacketsTcp().get("PILD").build(m1, core.getJoueurId()));
	}

	public void choisirDestPlacement(Packet packet, String message) {
		List<?> destRestantT = (List<?>) packet.getValue(message, 2);
		List<Integer> destRestant = new ArrayList<>();
		for (Object o : destRestantT)
			destRestant.add((Integer) o);

		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));

		int dest = 0;
		if (!destRestant.isEmpty())
			dest = destRestant.get(new Random().nextInt(destRestant.size()));

		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().getPacketsTcp().get("PICD").build(dest, pion, m1, core.getJoueurId()));
	}

	public void joueurPlacement(Packet packet, String message) {

	}

	public void debutTour(Packet packet, String message) {
		List<Couleur> couleurs = (List<Couleur>) packet.getValue(message, 2);
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}

	public void lanceDesChefVigil(Packet packet, String message) {
		Couleur c1 = (Couleur) packet.getValue(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValue(message, 3);
			int m2 = (int) packet.getValue(message, 4);
			String messageTcp = getControleurReseau().getPacketsTcp().get("AZLD").build(m1, m2, core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void choixDestVigil(Packet packet, String message) {
		if (!core.getEnvie())
			return;

		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {

			out.println("Entrez une destination");
			int dest = 0;

			dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));

			String messageTcp = getControleurReseau().getPacketsTcp().get("CDDCV").build(dest,
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());

			getControleurReseau().getTcpClient().envoyer(messageTcp);
		} else if (core.getCouleur() != (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			return;
		} else {
			out.println("Entrez une destination");
			int dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));

			String messageTcp = getControleurReseau().getPacketsTcp().get("CDDJ").build(dest,
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());

			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void choisirDest(Packet packet, String message) {
		if (!core.getEnvie())
			return;

		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)) {
			return;
		} else {
			out.println("Entrez une destination");
			int dest = 0;
			dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
			out.println("Entrez un pion (Piontype)");

			String messageTcp = getControleurReseau().getPacketsTcp().get("CDDJ").build(dest,
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());

			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void destZombieVengeur(Packet packet, String message) {
		out.println("Entrez une destination pour le zombie vengeur");
		int destZomb = 0;
		destZomb = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));

		String message1 = getControleurReseau().getPacketsTcp().get("CDDZVJE").build(destZomb,
				packet.getValue(message, 1), packet.getValue(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(message1);
	}

	public void debutDeplacemant(Packet packet, String message) {
		List<?> lieuxT = (List<?>) packet.getValue(message, 4);
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);

	}

	public void deplacerPion(Packet packet, String message) {
		int dest = (int) packet.getValue(message, 1);
		out.println("dest: " + dest);
		HashMap<Integer, List<Integer>> listedp = (HashMap<Integer, List<Integer>>) packet.getValue(message, 2);
		List<Integer> listePion = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				if (destPos == dest)
					listePion.add(dp.getKey());
		int pionAdep;
		if (listePion.isEmpty()) {
			dest = 4;
			for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
				for (int destPos : dp.getValue())
					if (destPos == dest)
						listePion.add(dp.getKey());
			if (!listePion.isEmpty())
				pionAdep = listePion.get(new Random().nextInt(listePion.size()));
			else
				pionAdep = new ArrayList<Integer>(listedp.keySet()).get(0);
		} else
			pionAdep = listePion.get(new Random().nextInt(listePion.size()));
		CarteType carte = CarteType.NUL;
		String messageTcp = getControleurReseau().getPacketsTcp().get("DPR").build(dest, pionAdep, carte,
				(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());

		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	public void tousDeplacment(Packet packet, String message) {

	}

	public void debutattaqueZombie(Packet packet, String message) {

	}

	public void attaqueZombie(Packet packet, String message) {
		List<PionCouleur> l = (List<PionCouleur>) (packet.getValue(message, 2));
		List<PionCouleur> ltemp = new ArrayList<>();
		for (PionCouleur pc : l) {
			if (IdjrTools.getCouleurByChar(pc) == core.getCouleur()) {
				ltemp.add(pc);
			}
		}
		core.setPoinSacrDispo(ltemp);
	}

	public void choisirSacrifice(Packet packet, String message) {
		out.println(getControleurReseau().getPacketsTcp().get("RAZDS").getDocs());
		
		List<Integer> listPion = (List<Integer>) packet.getValue(message, 2);
		int pionTemp = listPion.get(new Random().nextInt(listPion.size()));
		PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionTemp);

		String messageTcp = getControleurReseau().getPacketsTcp().get("RAZCS").build((int) packet.getValue(message, 1),
				pion, (String) packet.getValue(message, 3), (String) packet.getValue(message, 4), core.getJoueurId());

		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	public void tousSacrifice(Packet packet, String message) {

	}

	private void finPartie(Packet packet, String message) {
		Couleur gagnant = (Couleur) packet.getValue(message, 2);
		out.println("Le gagant est le joueur " + gagnant + " !");
		try {
			getControleurReseau().getTcpServeur().arreter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getControleurReseau().getUdpConnexion().arreter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void set(Object core) {
		this.core = (Idjr) core;
	}
}
