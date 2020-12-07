package botmoyen;

import reseau.packet.Packet;
import reseau.socket.ControleurReseau;
import reseau.socket.TraitementPaquet;
import reseau.type.CarteEtat;
import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.VigileEtat;
import reseau.type.VoteType;

import java.text.MessageFormat;
import java.util.*;

import botmoyen.partie.Joueur;

import static java.lang.System.out;

import java.net.Socket;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<Socket> {
	private BotMoyen core;
	private TraitementBot traitementB;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (BotMoyen) core;
		this.traitementB = new TraitementBot();
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
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (packet.getKey()) {
		case "IP":
			initialiserPartie(packet, message);
			break;
		case "DC":
			recupCarte(packet, message);
			break;
		case "PIIJ":
			lancerDes(packet, message);
			break;
		case "PIRD":
			choisirDestPlacement(packet, message);
			break;
		case "PIIG":
			placementPerso(packet, message);
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
			deplacerPionJoueurCourant(packet, message);
			break;
		case "PRAZ":
			resoAttaqueZombie(packet, message);
			break;
		case "RAZA":
			recupInfoPerso(packet, message);
			attaqueZombie(packet, message);
			break;
		case "RAZDS":
			choisirSacrifice(packet, message);
			break;
		case "RAZIF":
			resSacrifice(packet, message);
			break;
		case "FP":
			finPartie(packet, message);
			break;
		case "ACP":
			accepter(packet, message);
			break;
		case "RAZDD":
			fournirActionsDefense(packet, message);
			break;
		case "PVDV":
			ChoisirQuiVoter(packet, message);
			break;
		case "AZDCS":
			ReponseJoueurCourant(packet, message);
			break;
		case "PVD":
			IndiquerCarteJouees(packet, message);
			break;
		case "FCLC":
			choixCarteFouille(packet, message);
			break;
		case "FCRC":
			recupCarte(packet, message);
			break;

		case "PIPZ":
			placeZombie(packet, message);
			break;
		case "PFC":
			break;
		case "RFC":
			resFouille(packet, message);
			break;
		case "PECV":
			break;
		case "RECV":
			resVigile(packet, message);
			break;
		case "CDFC":
			break;
		case "AZLAZ":
			arriveZombie(packet, message);
			break;
		case "AZUCS":
			arriveZombie(packet, message);
			break;
			
		case "AZICS":
			joueCarteCDS(packet, message);
			break;
		case "CDZVDI":
			arriveSoloZombie(packet, message);
			break;
		case "PVIC":
			break;
		case "PVR":
			break;
		case "PVVC":
			break;
		case "RAZPA":
			recupInfoPerso(packet, message);
			break;
		case "RAZID":
			joueCarteDEF(packet, message);
			break;
		case "IPV":
			recupInfoVote(packet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}

	private void resSacrifice(Packet packet, String message) {
		core.sacrifice((PionCouleur)packet.getValue(message, 1));
		core.corectionZombie((Integer)packet.getValue(message, 1), (Integer)packet.getValue(message, 3));
		
	}

	private void joueCarteDEF(Packet packet, String message) {
		core.joueCartes((Couleur)packet.getValue(message, 2),(List<CarteType>)packet.getValue(message, 3) );
		core.corectionZombie((Integer)packet.getValue(message, 1), (Integer)packet.getValue(message, 6));
		core.setPersoCache((Integer)packet.getValue(message, 1),(List<Integer>)packet.getValue(message, 3));
		
	}

	private void recupInfoPerso(Packet packet, String message) {
		core.recupInfoPerso((List<PionCouleur>)packet.getValue(message, 2),(Integer)packet.getValue(message, 1)); 
		core.corectionZombie((Integer)packet.getValue(message, 1),(Integer)packet.getValue(message, 4));
	}

	private void resoAttaqueZombie(Packet packet, String message) {
		core.setZombie((List<Integer>)packet.getValue(message, 3),(List<Integer>)packet.getValue(message, 4));
		
	}

	private void deplacerPionJoueurCourant(Packet packet, String message) {
		core.deplPionJoueurCourant((Couleur)packet.getValue(message, 1),(Integer)packet.getValue(message, 2), (Integer)packet.getValue(message, 3));
		if (((CarteType)packet.getValue(message, 4)).equals(CarteType.SPR))
			core.joueCarte((Couleur)packet.getValue(message, 1),CarteType.SPR);
		
	}

	private void arriveSoloZombie(Packet packet, String message) {
		core.arriveSoloZombie((Integer) packet.getValue(message, 2));
		
	}

	private void joueCarteCDS(Packet packet, String message) {
		if ((((CarteType)packet.getValue(message, 2)).equals(CarteType.CDS)  )&&(!((Couleur)packet.getValue(message, 1)).equals(core.getCouleur()))) {
			 core.joueCarte((Couleur)packet.getValue(message, 1),CarteType.CDS);
		}
		
	}

	private void arriveZombie(Packet packet, String message) {
		core.arriveZombie((List<Integer>) packet.getValue(message, 1));
		
	}

	private void resVigile(Packet packet, String message) {
		core.resVigile((Couleur) packet.getValue(message, 1));
		
	}

	private void resFouille(Packet packet, String message) {
		core.resFouille((Couleur) packet.getValue(message, 1),(Couleur) packet.getValue(message, 2),(CarteEtat) packet.getValue(message, 3));
		
	}

	private void placeZombie(Packet packet, String message) {
		core.initZombie((List<Integer>) packet.getValue(message, 1) );
		
	}

	private void placementPerso(Packet packet, String message) {
		core.placePionCouleur((Couleur) packet.getValue(message, 1),(Integer) packet.getValue(message, 4),(Integer)packet.getValue(message, 5) );
		
	}

	private void recupCarte(Packet packet, String message) {
		core.getListeCarte().add((CarteType) packet.getValue(message, 1));
		core.initCarte((CarteType) packet.getValue(message, 1));
		core.recupCarte((CarteType) packet.getValue(message, 1));
	}

	private void recupInfoVote(Packet packet, String message) {
		List<Couleur> couleursJoueurs = (List<Couleur>) packet.getValue(message, 3);
		core.setCouleurJoueurs(couleursJoueurs);
		core.setVoteType((VoteType) packet.getValue(message, 1));
	}

	public void ReponseJoueurCourant(Packet packet, String message) {
		CarteType RJ = traitementB.ReponseJoueurCourant(core);
		String IDP = (String) packet.getValue(message, 1);
		int NT = (int) packet.getValue(message, 2);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
	}

	public void ChoisirQuiVoter(Packet packet, String message) {
		out.println(packet.getDocs());
		System.out.print("ChoisirQuiVoter");
		String messageTcp = getControleurReseau().construirePaquetTcp("PVCV",
				traitementB.getRandom(core, core.getVoteType()), (String) packet.getValue(message, 1),
				(int) packet.getValue(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	private void fournirActionsDefense(Packet packet, String message) {
		List<Object> listerenvoye = traitementB.listeCarteJouee(this.core, (int) packet.getValue(message, 1));
		String messageTCP = getControleurReseau().construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) packet.getValue(message, 2), (int) packet.getValue(message, 3), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTCP);
	}

	public void accepter(Packet packet, String message) {
		System.out.println((String) packet.getValue(message, 2));

		core.setJoueurId((String) packet.getValue(message, 2));
	}

	public void initialiserPartie(Packet packet, String message) {
		traitementB.initialiserPartie(this.core, (List<?>) packet.getValue(message, 1),
				(List<Couleur>) packet.getValue(message, 2), (int) packet.getValue(message, 3));
	}

	public void lancerDes(Packet packet, String message) {
		traitementB.lancerDes(core, (List<?>) packet.getValue(message, 2));
		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PILD", m1, core.getJoueurId()));
	}

	public void choisirDestPlacement(Packet packet, String message) {
		String m1 = (String) packet.getValue(message, 3);
		int dest = traitementB.choisirDestPlacement((List<?>) packet.getValue(message, 2));
		int pion = traitementB.choisirPionPlacement(core);
		core.placePion(dest, pion);
		getControleurReseau().getTcpClient().envoyer(getControleurReseau().construirePaquetTcp("PICD", dest,pion, m1, core.getJoueurId()));
	}

	public void debutTour(Packet packet, String message) {
		traitementB.debutTour(core, (List<Couleur>) packet.getValue(message, 2));
		core.resetPersoCache();
		System.out.println(core.getEtatPartie());
		
	}

	public void lanceDesChefVigil(Packet packet, String message) {
		Couleur c1 = (Couleur) packet.getValue(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValue(message, 3);
			int m2 = (int) packet.getValue(message, 4);
			String messageTcp = getControleurReseau().construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
		
		core.NewChef((VigileEtat)packet.getValue(message, 2));
	}

	public void choixDestVigil(Packet packet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDCV", traitementB.choixDest(core),
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		} else if (core.getCouleur() != (Couleur) packet.getValue(message, 1)
				&& (VigileEtat) packet.getValue(message, 2) == VigileEtat.NE) {
			return;
		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementB.choixDest(core),
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
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
	}

	public void destZombieVengeur(Packet packet, String message) {
		String message1 = getControleurReseau().construirePaquetTcp("CDDZVJE", traitementB.choixDest(core),
				packet.getValue(message, 1), packet.getValue(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(message1);
	}

	public void debutDeplacemant(Packet packet, String message) {
		traitementB.debutDeplacemant(core, (List<?>) packet.getValue(message, 4));
	}

	public void deplacerPion(Packet packet, String message) {
		List<Object> listRenvoye = traitementB.pionADeplacer(core, (int) packet.getValue(message, 1),
				(HashMap<Integer, List<Integer>>) packet.getValue(message, 2));
		String messageTcp = getControleurReseau().construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) packet.getValue(message, 3),
				(int) packet.getValue(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	public void attaqueZombie(Packet packet, String message) {
		traitementB.attaqueZombie(core, (List<PionCouleur>) (packet.getValue(message, 2)), new ArrayList<>());
	}

	public void choisirSacrifice(Packet packet, String message) {
		out.println(packet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("RAZCS", (int) packet.getValue(message, 1),
				traitementB.choisirSacrifice(core, (List<?>) packet.getValue(message, 2)),
				(String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	private void finPartie(Packet packet, String message) {
		traitementB.finPartie(core, (Couleur) packet.getValue(message, 2));
	}

	public void IndiquerCarteJouees(Packet packet, String message) {
		out.println(packet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("PVC", traitementB.IndiquerCarteJouees(core),
				/* , */ (String) packet.getValue(message, 1), (int) packet.getValue(message, 2),
				(String) core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
	}

	private void choixCarteFouille(Packet packet, String message) {
		List<Object> listeResultat = traitementB.carteFouille((List<CarteType>) packet.getValue(message, 1), core);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("SCFC", (CarteType) listeResultat.get(0),
						(CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3),
						(CarteType) listeResultat.get(2), (String) packet.getValue(message, 2),
						packet.getValue(message, 3), core.getJoueurId()));

	}

	@Override
	public void set(Object core) {
		this.core = (BotMoyen) core;
	}
}
