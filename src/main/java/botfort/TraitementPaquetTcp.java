package botfort;


import reseau.paquet.Paquet;
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

import botfort.partie.Joueur;

import static java.lang.System.out;

import java.net.Socket;

/**
 * <h1>Permet de gerer les packets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<Socket> {
	private BotFort core;
	private TraitementBot traitementB;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (BotFort) core;
		this.traitementB = new TraitementBot();
	}

	public void init(ControleurReseau netWorkManager) {
		super.setControleurReseau(netWorkManager);
	}

	/**
	 * Traitement des paquets TCP
	 *
	 * @param Paquet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	public void traitement(Paquet packet, String message, Socket socket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (packet.getCle()) {
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
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getCle()));
		}
	}

	private void resSacrifice(Paquet packet, String message) {
		core.sacrifice((PionCouleur)packet.getValeur(message, 2));
		core.corectionZombie((Integer)packet.getValeur(message, 1), (Integer)packet.getValeur(message, 3));
		System.out.println("resSacrifice : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void joueCarteDEF(Paquet packet, String message) {
		core.joueCartes((Couleur)packet.getValeur(message, 2),(List<CarteType>)packet.getValeur(message, 3) );
		core.corectionZombie((Integer)packet.getValeur(message, 1), (Integer)packet.getValeur(message, 6));
		core.setPersoCache((Integer)packet.getValeur(message, 1),(List<Integer>)packet.getValeur(message, 3));
		System.out.println("joueCarteDEF : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void recupInfoPerso(Paquet packet, String message) {
		core.recupInfoPerso((List<PionCouleur>)packet.getValeur(message, 2),(Integer)packet.getValeur(message, 1)); 
		core.corectionZombie((Integer)packet.getValeur(message, 1),(Integer)packet.getValeur(message, 4));
		System.out.println("recupInfoPerso : \n");
		System.out.println(core.getEtatPartie());
	}

	private void resoAttaqueZombie(Paquet packet, String message) {
		core.setZombie((List<Integer>)packet.getValeur(message, 3),(List<Integer>)packet.getValeur(message, 4));
		System.out.println("resoAttaqueZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void deplacerPionJoueurCourant(Paquet packet, String message) {
		core.deplPionJoueurCourant((Couleur)packet.getValeur(message, 1),(Integer)packet.getValeur(message, 2), (Integer)packet.getValeur(message, 3));
		if (((CarteType)packet.getValeur(message, 4)).equals(CarteType.SPR))
			core.joueCarte((Couleur)packet.getValeur(message, 1),CarteType.SPR);
		System.out.println("deplacerPionJoueurCourant : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void arriveSoloZombie(Paquet packet, String message) {
		core.arriveSoloZombie((Integer) packet.getValeur(message, 2));
		System.out.println("arriveSoloZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void joueCarteCDS(Paquet packet, String message) {
		if ((((CarteType)packet.getValeur(message, 2)).equals(CarteType.CDS)  )&&(!((Couleur)packet.getValeur(message, 1)).equals(core.getCouleur()))) {
			 core.joueCarte((Couleur)packet.getValeur(message, 1),CarteType.CDS);
		}
		System.out.println("joueCarteCDS : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void arriveZombie(Paquet packet, String message) {
		core.arriveZombie((List<Integer>) packet.getValeur(message, 1));
		System.out.println("arriveZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void resVigile(Paquet packet, String message) {
		core.resVigile((Couleur) packet.getValeur(message, 1));
		System.out.println("resVigile : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void resFouille(Paquet packet, String message) {
		core.resFouille((Couleur) packet.getValeur(message, 1),(Couleur) packet.getValeur(message, 2),(CarteEtat) packet.getValeur(message, 3));
		System.out.println("resFouille : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void placeZombie(Paquet packet, String message) {
		core.initZombie((List<Integer>) packet.getValeur(message, 1) );
		System.out.println("placeZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void placementPerso(Paquet packet, String message) {
		core.placePionCouleur((Couleur) packet.getValeur(message, 1),(Integer) packet.getValeur(message, 4),(Integer)packet.getValeur(message, 5) );
		System.out.println("placementPerso : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void recupCarte(Paquet packet, String message) {
		core.getListeCarte().add((CarteType) packet.getValeur(message, 1));
		core.initCarte((CarteType) packet.getValeur(message, 1));
		core.recupCarte((CarteType) packet.getValeur(message, 1));
		System.out.println("recupCarte : \n");
		System.out.println(core.getEtatPartie());
	}

	private void recupInfoVote(Paquet packet, String message) {
		List<Couleur> couleursJoueurs = (List<Couleur>) packet.getValeur(message, 3);
		core.setCouleurJoueurs(couleursJoueurs);
		core.setVoteType((VoteType) packet.getValeur(message, 1));
		System.out.println("recupInfoVote : \n");
		System.out.println(core.getEtatPartie());
	}

	public void ReponseJoueurCourant(Paquet packet, String message) {
		CarteType RJ = traitementB.ReponseJoueurCourant(core);
		String IDP = (String) packet.getValeur(message, 1);
		int NT = (int) packet.getValeur(message, 2);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
		System.out.println("ReponseJoueurCourant : \n");
		System.out.println(core.getEtatPartie());
	}

	public void ChoisirQuiVoter(Paquet packet, String message) {
		out.println(packet.getDocs());
		System.out.print("ChoisirQuiVoter");
		String messageTcp = getControleurReseau().construirePaquetTcp("PVCV",
				traitementB.getRandom(core, core.getVoteType()), (String) packet.getValeur(message, 1),
				(int) packet.getValeur(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
		System.out.println("ChoisirQuiVoter : \n");
		System.out.println(core.getEtatPartie());
	}

	private void fournirActionsDefense(Paquet packet, String message) {
		List<Object> listerenvoye = traitementB.listeCarteJouee(this.core, (int) packet.getValeur(message, 1));
		String messageTCP = getControleurReseau().construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) packet.getValeur(message, 2), (int) packet.getValeur(message, 3), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTCP);
		System.out.println("fournirActionsDefense : \n");
		System.out.println(core.getEtatPartie());
	}

	public void accepter(Paquet packet, String message) {
		System.out.println((String) packet.getValeur(message, 2));

		core.setJoueurId((String) packet.getValeur(message, 2));
	}

	public void initialiserPartie(Paquet packet, String message) {
		traitementB.initialiserPartie(this.core, (List<?>) packet.getValeur(message, 1),
				(List<Couleur>) packet.getValeur(message, 2), (int) packet.getValeur(message, 3));
	}

	public void lancerDes(Paquet packet, String message) {
		traitementB.lancerDes(core, (List<?>) packet.getValeur(message, 2));
		String m1 = (String) packet.getValeur(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PILD", m1, core.getJoueurId()));
		System.out.println("lancerDes : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choisirDestPlacement(Paquet packet, String message) {
		String m1 = (String) packet.getValeur(message, 3);
		int dest = traitementB.choisirDestPlacement((List<?>) packet.getValeur(message, 2));
		int pion = traitementB.choisirPionPlacement(core);
		core.placePion(dest, pion);
		getControleurReseau().getTcpClient().envoyer(getControleurReseau().construirePaquetTcp("PICD", dest,pion, m1, core.getJoueurId()));
		System.out.println("choisirDestPlacement : \n");
		System.out.println(core.getEtatPartie());
	}

	public void debutTour(Paquet packet, String message) {
		traitementB.debutTour(core, (List<Couleur>) packet.getValeur(message, 2));
		core.resetPersoCache();
		System.out.println("debutTour : \n");
		System.out.println(core.getEtatPartie());
	}

	public void lanceDesChefVigil(Paquet packet, String message) {
		Couleur c1 = (Couleur) packet.getValeur(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValeur(message, 3);
			int m2 = (int) packet.getValeur(message, 4);
			String messageTcp = getControleurReseau().construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
		
		core.NewChef((VigileEtat)packet.getValeur(message, 2));
		System.out.println("lanceDesChefVigil : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choixDestVigil(Paquet packet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) packet.getValeur(message, 1)
				&& (VigileEtat) packet.getValeur(message, 2) == VigileEtat.NE) {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDCV", traitementB.choixDest(core),
					(String) packet.getValeur(message, 3), (int) packet.getValeur(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		} else if (core.getCouleur() != (Couleur) packet.getValeur(message, 1)
				&& (VigileEtat) packet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) packet.getValeur(message, 3), (int) packet.getValeur(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
		System.out.println("choixDestVigil : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choisirDest(Paquet packet, String message) {
		if (!core.getEnvie())
			return;

		if (core.getCouleur() == (Couleur) packet.getValeur(message, 1)) {
			return;

		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) packet.getValeur(message, 3), (int) packet.getValeur(message, 4), core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
		System.out.println("choisirDest : \n");
		System.out.println(core.getEtatPartie());
	}

	public void destZombieVengeur(Paquet packet, String message) {
		String message1 = getControleurReseau().construirePaquetTcp("CDDZVJE", traitementB.choixBestDest(core),
				packet.getValeur(message, 1), packet.getValeur(message, 2), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(message1);
		System.out.println("destZombieVengeur : \n");
		System.out.println(core.getEtatPartie());
	}

	public void debutDeplacemant(Paquet packet, String message) {
		traitementB.debutDeplacemant(core, (List<?>) packet.getValeur(message, 4));
	}

	public void deplacerPion(Paquet packet, String message) {
		List<Object> listRenvoye = traitementB.pionADeplacer(core, (int) packet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) packet.getValeur(message, 2));
		System.out.println("deplacerPion avant dpl: {"+core.getCouleur()+ ", "+(Integer) listRenvoye.get(0)+ ", "+(Integer)listRenvoye.get(1)+"}\n");
		System.out.println(core.getEtatPartie());
		core.deplPionJoueurCourant(core.getCouleur(),(Integer) listRenvoye.get(0), (Integer)listRenvoye.get(1));
		if (((CarteType) listRenvoye.get(2)).equals(CarteType.SPR))
			core.joueCarte(core.getCouleur(),CarteType.SPR);
		
		String messageTcp = getControleurReseau().construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) packet.getValeur(message, 3),
				(int) packet.getValeur(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
		
		System.out.println("deplacerPion apres dpl: \n");
		System.out.println(core.getEtatPartie());
	}

	public void attaqueZombie(Paquet packet, String message) {
		traitementB.attaqueZombie(core, (List<PionCouleur>) (packet.getValeur(message, 2)), new ArrayList<>());
		System.out.println("attaqueZombie : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choisirSacrifice(Paquet packet, String message) {
		out.println(packet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("RAZCS", (int) packet.getValeur(message, 1),
				traitementB.choisirSacrifice(core, (List<?>) packet.getValeur(message, 2)),
				(String) packet.getValeur(message, 3), (int) packet.getValeur(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
		System.out.println("choisirSacrifice : \n");
		System.out.println(core.getEtatPartie());
	}

	private void finPartie(Paquet packet, String message) {
		traitementB.finPartie(core, (Couleur) packet.getValeur(message, 2));
		System.out.println("finPartie : \n");
		System.out.println(core.getEtatPartie());
	}

	public void IndiquerCarteJouees(Paquet packet, String message) {
		out.println(packet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("PVC", traitementB.IndiquerCarteJouees(core),
				/* , */ (String) packet.getValeur(message, 1), (int) packet.getValeur(message, 2),
				(String) core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTcp);
		System.out.println("IndiquerCarteJouees : \n");
		System.out.println(core.getEtatPartie());
	}

	private void choixCarteFouille(Paquet packet, String message) {
		List<Object> listeResultat = traitementB.carteFouille((List<CarteType>) packet.getValeur(message, 1), core);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("SCFC", (CarteType) listeResultat.get(0),
						(CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3),
						(CarteType) listeResultat.get(2), (String) packet.getValeur(message, 2),
						packet.getValeur(message, 3), core.getJoueurId()));
		System.out.println("choixCarteFouille : \n");
		System.out.println(core.getEtatPartie());

	}

	@Override
	public void set(Object core) {
		this.core = (BotFort) core;
	}
}
