package traitement;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import idjr.Joueur;
import idjr.ihmidjr.Core;
import reseau.packet.Packet;
import reseau.socket.ControleurReseau;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;

public class Traitement {

	public ArrayList<Joueur> initialiserPartie(Packet packet, String message, Controleur core) {
		List<?> nomsT = (List<?>) packet.getValue(message, 1);
		List<?> couleursT = (List<?>) packet.getValue(message, 2);
		int lieuferme = (int) packet.getValue(message, 3);
		List<String> noms = new ArrayList<>();
		List<Couleur> couleurs = new ArrayList<>();
		for (Object o : nomsT)
			noms.add((String) o);
		for (Object o : couleursT)
			couleurs.add((Couleur) o);
		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
		if (lieuferme == 2) {
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
		ArrayList<Joueur> listeJoueursInitiale = new ArrayList<>();
		for (int i = 0; i < noms.size(); i++)
			listeJoueursInitiale.add(new Joueur(noms.get(i), couleurs.get(i)));

		return listeJoueursInitiale;
	}

	public void lancerDes(Packet packet, String message, Controleur core, ControleurReseau controleurReseau) {
		List<?> pionT = (List<?>) packet.getValue(message, 2);
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);
		core.setPionAPos(pion);
		String m1 = (String) packet.getValue(message, 3);
		controleurReseau.getTcpClient().envoyer(controleurReseau.construirePaquetTcp("PILD", m1, core.getJoueurId()));
	}

	public List<Integer> choisirDestPlacementInit(Packet packet, String message, Controleur core,
			ControleurReseau controleurReseau) {
		List<?> destRestantT = (List<?>) packet.getValue(message, 2);
		List<Integer> destRestant = new ArrayList<>();
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		return destRestant;
	}

	public void choisirDestPlacement(Packet packet, String message, Controleur core, ControleurReseau controleurReseau,
			int valDest, int valPion) {

		String m1 = (String) packet.getValue(message, 3);
		controleurReseau.getTcpClient()
				.envoyer(controleurReseau.construirePaquetTcp("PICD", valDest, valPion, m1, core.getJoueurId()));
	}

	public void debutTour(Packet packet, String message, Controleur core) {
		List<Couleur> couleurs = (List<Couleur>) packet.getValue(message, 2);
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}

	public boolean lanceDesChefVigil(Packet packet, String message, Controleur core,
			ControleurReseau controleurReseau) {
		Couleur c1 = (Couleur) packet.getValue(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValue(message, 3);
			int m2 = (int) packet.getValue(message, 4);
			String messageTcp = controleurReseau.construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			controleurReseau.getTcpClient().envoyer(messageTcp);
			return true;
		}

		return false;
	}

	public boolean choixDestVigilInit(Controleur core, Packet packet, String message) {
		if (!core.getEnvie())
			return false;

		if (core.getCouleur() != (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE)
			return false;

		return true;
	}

	public void choixDestVigil(Controleur core, Packet packet, String message, ControleurReseau controleurReseau,
			int dest) {

		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			String messageTcp = controleurReseau.construirePaquetTcp("CDDCV", dest,
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
			controleurReseau.getTcpClient().envoyer(messageTcp);
		} else {
			String messageTcp = controleurReseau.construirePaquetTcp("CDDJ", dest, (String) packet.getValue(message, 3),
					(int) packet.getValue(message, 4), core.getJoueurId());
			controleurReseau.getTcpClient().envoyer(messageTcp);
		}
	}

	public List<Integer> pionADeplacer(int dest, HashMap<Integer, List<Integer>> listedp) {
		List<Integer> destEtPion = new ArrayList<>();
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
		destEtPion.add(dest);
		destEtPion.add(pionAdep);
		return destEtPion;
	}

	public boolean choisirDestInit(Packet packet, String message, Controleur core) {
		if (!core.getEnvie())
			return false;

		if (core.getCouleur() == (Couleur) packet.getValue(message, 1))
			return false;

		return true;
	}

	public void choisirDest(Packet packet, String message, ControleurReseau controleurReseau, Controleur core,
			int dest) {
		String messageTcp = controleurReseau.construirePaquetTcp("CDDJ", dest, (String) packet.getValue(message, 3),
				(int) packet.getValue(message, 4), core.getJoueurId());
		controleurReseau.getTcpClient().envoyer(messageTcp);
	}

	public void destZombieVengeur(Packet packet, String message, ControleurReseau controleurReseau, Controleur core,
			int dest) {
		String message1 = controleurReseau.construirePaquetTcp("CDDZVJE", dest, packet.getValue(message, 1),
				packet.getValue(message, 2), core.getJoueurId());
		controleurReseau.getTcpClient().envoyer(message1);
	}

	public void debutDeplacemant(Controleur core, Packet packet, String message) {
		List<?> lieuxT = (List<?>) packet.getValue(message, 4);
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
	}

	public void deplacerPion(Packet packet, String message, ControleurReseau controleurReseau, Controleur core) {
		List<Integer> destEtPion = pionADeplacer((int) packet.getValue(message, 1),
				(HashMap<Integer, List<Integer>>) packet.getValue(message, 2));
		CarteType carte = CarteType.NUL;
		String messageTcp = controleurReseau.construirePaquetTcp("DPR", destEtPion.get(0), destEtPion.get(1), carte,
				(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
		controleurReseau.getTcpClient().envoyer(messageTcp);
	}

	public List<Integer> choisirSacrificeInit(Packet packet, String message) {
		List<?> listPionT = (List<?>) packet.getValue(message, 2);
		List<Integer> listPion = new ArrayList<>();
		for (Object o : listPionT)
			listPion.add((Integer) o);

		return listPion;
	}

	public void choisirSacrifice(Packet packet, String message, Controleur core, ControleurReseau controleurReseau,
			int pionTemp) {
		PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionTemp);
		String messageTcp = controleurReseau.construirePaquetTcp("RAZCS", (int) packet.getValue(message, 1), pion,
				(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
		controleurReseau.getTcpClient().envoyer(messageTcp);
	}

	public void finPartie(Packet packet, String message, Controleur core) {
		Couleur gagnant = (Couleur) packet.getValue(message, 2);
		out.println("Le gagant est le joueur " + gagnant + " !");
		core.setEstFini(true);
		// getControleurReseau().arreter();
	}

}
