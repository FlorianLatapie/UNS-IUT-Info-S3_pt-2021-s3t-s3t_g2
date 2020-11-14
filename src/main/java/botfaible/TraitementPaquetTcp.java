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
			choisirDestPion(packet, message);
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
			attaqueZombie(packet, message);
			break;
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
		System.out.println((String)packet.getValue(message, 2));
		
		core.setJoueurId((String)packet.getValue(message, 2));
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
		ArrayList<Joueur> listeJoueursInitiale = new ArrayList<>();

		for (int i = 0; i < noms.size(); i++)
			listeJoueursInitiale.add(new Joueur(noms.get(i), couleurs.get(i)));

		core.getJeu().initJoueurs(listeJoueursInitiale);
	}

	public void lancerDes(Packet packet, String message) {
		List<?> pionT = (List<?>) packet.getValue(message, 2);
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);

		core.setPionAPos(pion);

		String m1 = (String) packet.getValue(message, 3);
			getControleurReseau().getTcpClient().envoyer(getControleurReseau().getPacketsTcp().get("PILD").build(m1, core.getJoueurId()));
	}

	public void choisirDestPion(Packet packet, String message) {
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

		core.getJeu().placePerso(core.getMoi(), pion, dest);
		String m1 = (String) packet.getValue(message, 3);
			getControleurReseau().getTcpClient().envoyer(getControleurReseau().getPacketsTcp().get("PICD").build(dest, pion, m1, core.getJoueurId()));
	}

	public void joueurPlacement(Packet packet, String message) {
		Couleur c = (Couleur) packet.getValue(message, 1);
		int dest = (int) packet.getValue(message, 4);
		int pion = (int) packet.getValue(message, 5);

		core.getJeu().placePerso(core.getJoueur(c), pion, dest);
	}

	public void debutTour(Packet packet, String message) {
		List<?> lT = (List<?>) packet.getValue(message, 2);
		List<Couleur> l = new ArrayList<>();
		for (Object o : lT)
			l.add((Couleur) o);

		for (Joueur j : core.getJeu().getJoueurs().values())
			if (!l.contains(j.getCouleur()))
				j.setEnVie(false);

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
		if (!core.getMoi().isEnVie())
			return;

		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {

			out.println("Entrez une destination");
			int dest = 0;

			if (!core.getJeu().choixLieudispo(core.getMoi()).isEmpty())
				dest = core.getJeu().choixLieudispo(core.getMoi())
						.get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));

			String messageTcp = getControleurReseau().getPacketsTcp().get("CDDCV").build(dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());
			
				getControleurReseau().getTcpClient().envoyer(messageTcp);
		} else if (core.getCouleur() != (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			return;
		} else {
			out.println("Entrez une destination");
			int dest = 0;
			if (!core.getJeu().choixLieudispo(core.getMoi()).isEmpty())
				dest = core.getJeu().choixLieudispo(core.getMoi())
						.get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));

			String messageTcp = getControleurReseau().getPacketsTcp().get("CDDJ").build(dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());

				getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void choisirDest(Packet packet, String message) {
		if (!core.getMoi().isEnVie())
			return;

		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)) {
			return;
		} else {
			out.println("Entrez une destination");
			int dest = 0;
			if (!core.getJeu().choixLieudispo(core.getMoi()).isEmpty())
				dest = core.getJeu().choixLieudispo(core.getMoi())
						.get(new Random().nextInt(core.getJeu().choixLieudispo(core.getMoi()).size()));
			out.println("Entrez un pion (Piontype)");

			String messageTcp = getControleurReseau().getPacketsTcp().get("CDDJ").build(dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());

				getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void destZombieVengeur(Packet packet, String message) {
		out.println("Entrez une destination pour le zombie vengeur");
		int destZomb = 0;
		if (!core.getJeu().choixLieudispo().isEmpty())
			destZomb = core.getJeu().choixLieudispo().get(new Random().nextInt(core.getJeu().choixLieudispo().size()));

		String message1 = getControleurReseau().getPacketsTcp().get("CDDZVJE").build(destZomb, packet.getValue(message, 1),
				packet.getValue(message, 2), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(message1);
	}

	public void debutDeplacemant(Packet packet, String message) {
		List<?> lieuxT = (List<?>) packet.getValue(message, 4);
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		core.getJeu().fermerLieu(lieux);
	}

	public void deplacerPion(Packet packet, String message) {
		int dest = (int) packet.getValue(message, 1);
		out.println("First dest " + dest);
		if (core.getJeu().getLieux().get(dest).isFull())
			dest = 4;
		out.println("Second dest " + dest);
		HashMap<Integer, List<Integer>> listedp = (HashMap<Integer, List<Integer>>) packet.getValue(message, 2);
		List<Integer> listeDest = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				if (destPos == dest)
					listeDest.add(dp.getKey());

		List<Personnage> list = new ArrayList<>(core.getMoi().getPersonnages().values());
		int pionAdep = list.get(new Random().nextInt(list.size())).getNum();
		if (!listeDest.isEmpty())
			pionAdep = listeDest.get(new Random().nextInt(listeDest.size()));

		out.println("Entrez un pion (Piontype)");
		out.println("Entrez une carte Sprint(si disponible 'SPR' sinon 'NUL')");
		CarteType carte = CarteType.NUL;
		core.getJeu().deplacePerso(core.getMoi(), pionAdep, dest);
		String messageTcp = getControleurReseau().getPacketsTcp().get("DPR").build(dest, pionAdep, carte,
				(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());

			getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	public void tousDeplacment(Packet packet, String message) {
		Couleur c = (Couleur) packet.getValue(message, 1);
		int dest = (int) packet.getValue(message, 2);
		int p = (int) packet.getValue(message, 3);
		core.getJeu().deplacePerso(core.getJoueur(c), p, dest);
	}

	public void attaqueZombie(Packet packet, String message) {
		if ((int) packet.getValue(message, 1) != 0)
			core.getJeu().getLieux().get((int) packet.getValue(message, 1)).addZombie();

		if ((int) packet.getValue(message, 2) != 0)
			core.getJeu().getLieux().get((int) packet.getValue(message, 2)).addZombie();
	}

	public void choisirSacrifice(Packet packet, String message) {
		out.println("Entrez un pion (PionCouleur)");
		int pionInt = 0;

		if (!core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).isEmpty())
			pionInt = core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).get(new Random()
					.nextInt(core.getJeu().pionSacrDispo(core.getMoi(), (int) packet.getValue(message, 1)).size()));

		String pionTemp = core.getCouleur().name().charAt(0) + "" + pionInt;
		PionCouleur pion = PionCouleur.valueOf(pionTemp);
		String messageTcp = getControleurReseau().getPacketsTcp().get("RAZCS").build(packet.getValue(message, 1), pion,
				packet.getValue(message, 2), packet.getValue(message, 3), core.getJoueurId());

			getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	public void tousSacrifice(Packet packet, String message) {
		PionCouleur pionTemp = (PionCouleur) packet.getValue(message, 2);
		Couleur pionCouleur = IdjrTools.getCouleurByChar(pionTemp);
		int pionInt = IdjrTools.getPionByValue(pionTemp);
		core.getJeu().sacrifie(core.getJoueur(pionCouleur), pionInt);
	}

	private void finPartie(Packet packet, String message) {
		Couleur gagnant = (Couleur) packet.getValue(message, 2);
		out.println("Le gagant est " + core.getJoueur(gagnant).getNom() + " !");
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
		this.core = (Idjr)core;
	}
}
