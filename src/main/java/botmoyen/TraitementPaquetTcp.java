package botmoyen;

import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
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
 * <h1>Permet de gerer les paquets</h1>
 *
 * @author SÃ©bastien AglaÃ©
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<TcpClient> {
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
	 * @param Paquet  le paquet du message
	 * @param message le message sous forme de chaine de caractere
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	public void traitement(Paquet paquet, String message, TcpClient socket) {
		try {
			Thread.sleep(core.getDelay());
		} catch (InterruptedException e) {

		}
		switch (paquet.getCle()) {
		case "IP":
			initialiserPartie(paquet, message);
			break;
		case "DC":
			recupCarte(paquet, message);
			break;
		case "PIIJ":
			lancerDes(paquet, message);
			break;
		case "PIRD":
			choisirDestPlacement(paquet, message);
			break;
		case "PIIG":
			placementPerso(paquet, message);
			break;
		case "IT":
			debutTour(paquet, message);
			break;
		case "PAZ":
			lanceDesChefVigil(paquet, message);
			break;
		case "PCD":
			choixDestVigil(paquet, message);
			break;
		case "CDCDV":
			choisirDest(paquet, message);
			break;
		case "CDZVI":
			destZombieVengeur(paquet, message);
			break;
		case "PDP":
			debutDeplacemant(paquet, message);
			break;
		case "DPD":
			deplacerPion(paquet, message);
			break;
		case "DPI":
			deplacerPionJoueurCourant(paquet, message);
			break;
		case "PRAZ":
			resoAttaqueZombie(paquet, message);
			break;
		case "RAZA":
			recupInfoPerso(paquet, message);
			attaqueZombie(paquet, message);
			break;
		case "RAZDS":
			choisirSacrifice(paquet, message);
			break;
		case "RAZIF":
			resSacrifice(paquet, message);
			break;
		case "FP":
			finPartie(paquet, message);
			break;
		case "ACP":
			accepter(paquet, message);
			break;
		case "RAZDD":
			fournirActionsDefense(paquet, message);
			break;
		case "PVDV":
			ChoisirQuiVoter(paquet, message);
			break;
		case "AZDCS":
			ReponseJoueurCourant(paquet, message);
			break;
		case "PVD":
			IndiquerCarteJouees(paquet, message);
			break;
		case "FCLC":
			choixCarteFouille(paquet, message);
			break;
		case "FCRC":
			recupCarte(paquet, message);
			break;

		case "PIPZ":
			placeZombie(paquet, message);
			break;
		case "PFC":
			break;
		case "RFC":
			resFouille(paquet, message);
			break;
		case "PECV":
			break;
		case "RECV":
			resVigile(paquet, message);
			break;
		case "CDFC":
			break;
		case "AZLAZ":
			arriveZombie(paquet, message);
			break;
		case "AZUCS":
			arriveZombie(paquet, message);
			break;
			
		case "AZICS":
			joueCarteCDS(paquet, message);
			break;
		case "CDZVDI":
			arriveSoloZombie(paquet, message);
			break;
		case "PVIC":
			break;
		case "PVR":
			break;
		case "PVVC":
			break;
		case "RAZPA":
			recupInfoPerso(paquet, message);
			break;
		case "RAZID":
			joueCarteDEF(paquet, message);
			break;
		case "IPV":
			recupInfoVote(paquet, message);
			break;
		default:
			throw new IllegalStateException(
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", paquet.getCle()));
		}
	}

	private void resSacrifice(Paquet paquet, String message) {
		core.sacrifice((PionCouleur)paquet.getValeur(message, 2));
		core.corectionZombie((Integer)paquet.getValeur(message, 1), (Integer)paquet.getValeur(message, 3));
		System.out.println("resSacrifice : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void joueCarteDEF(Paquet paquet, String message) {
		core.joueCartes((Couleur)paquet.getValeur(message, 2),(List<CarteType>)paquet.getValeur(message, 3) );
		core.corectionZombie((Integer)paquet.getValeur(message, 1), (Integer)paquet.getValeur(message, 6));
		core.setPersoCache((Integer)paquet.getValeur(message, 1),(List<Integer>)paquet.getValeur(message, 3));
		System.out.println("joueCarteDEF : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void recupInfoPerso(Paquet paquet, String message) {
		core.recupInfoPerso((List<PionCouleur>)paquet.getValeur(message, 2),(Integer)paquet.getValeur(message, 1)); 
		core.corectionZombie((Integer)paquet.getValeur(message, 1),(Integer)paquet.getValeur(message, 4));
		System.out.println("recupInfoPerso : \n");
		System.out.println(core.getEtatPartie());
	}

	private void resoAttaqueZombie(Paquet paquet, String message) {
		core.setZombie((List<Integer>)paquet.getValeur(message, 3),(List<Integer>)paquet.getValeur(message, 4));
		System.out.println("resoAttaqueZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void deplacerPionJoueurCourant(Paquet paquet, String message) {
		core.deplPionJoueurCourant((Couleur)paquet.getValeur(message, 1),(Integer)paquet.getValeur(message, 2), (Integer)paquet.getValeur(message, 3));
		if (((CarteType)paquet.getValeur(message, 4)).equals(CarteType.SPR))
			core.joueCarte((Couleur)paquet.getValeur(message, 1),CarteType.SPR);
		System.out.println("deplacerPionJoueurCourant : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void arriveSoloZombie(Paquet paquet, String message) {
		core.arriveSoloZombie((Integer) paquet.getValeur(message, 2));
		System.out.println("arriveSoloZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void joueCarteCDS(Paquet paquet, String message) {
		if ((((CarteType)paquet.getValeur(message, 2)).equals(CarteType.CDS)  )&&(!((Couleur)paquet.getValeur(message, 1)).equals(core.getCouleur()))) {
			 core.joueCarte((Couleur)paquet.getValeur(message, 1),CarteType.CDS);
		}
		System.out.println("joueCarteCDS : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void arriveZombie(Paquet paquet, String message) {
		core.arriveZombie((List<Integer>) paquet.getValeur(message, 1));
		System.out.println("arriveZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void resVigile(Paquet paquet, String message) {
		core.resVigile((Couleur) paquet.getValeur(message, 1));
		System.out.println("resVigile : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void resFouille(Paquet paquet, String message) {
		core.resFouille((Couleur) paquet.getValeur(message, 1),(Couleur) paquet.getValeur(message, 2),(CarteEtat) paquet.getValeur(message, 3));
		System.out.println("resFouille : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void placeZombie(Paquet paquet, String message) {
		core.initZombie((List<Integer>) paquet.getValeur(message, 1) );
		System.out.println("placeZombie : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void placementPerso(Paquet paquet, String message) {
		core.placePionCouleur((Couleur) paquet.getValeur(message, 1),(Integer) paquet.getValeur(message, 4),(Integer)paquet.getValeur(message, 5) );
		System.out.println("placementPerso : \n");
		System.out.println(core.getEtatPartie());
		
	}

	private void recupCarte(Paquet paquet, String message) {
		core.getListeCarte().add((CarteType) paquet.getValeur(message, 1));
		core.initCarte((CarteType) paquet.getValeur(message, 1));
		core.recupCarte((CarteType) paquet.getValeur(message, 1));
		System.out.println("recupCarte : \n");
		System.out.println(core.getEtatPartie());
	}

	private void recupInfoVote(Paquet paquet, String message) {
		List<Couleur> couleursJoueurs = (List<Couleur>) paquet.getValeur(message, 3);
		core.setCouleurJoueurs(couleursJoueurs);
		core.setVoteType((VoteType) paquet.getValeur(message, 1));
		System.out.println("recupInfoVote : \n");
		System.out.println(core.getEtatPartie());
	}

	public void ReponseJoueurCourant(Paquet paquet, String message) {
		CarteType RJ = traitementB.ReponseJoueurCourant(core);
		String IDP = (String) paquet.getValeur(message, 1);
		int NT = (int) paquet.getValeur(message, 2);
		getControleurReseau()
				.envoyerTcp(getControleurReseau().construirePaquetTcp("AZRCS", RJ, IDP, NT, core.getJoueurId()));
		System.out.println("ReponseJoueurCourant : \n");
		System.out.println(core.getEtatPartie());
	}

	public void ChoisirQuiVoter(Paquet paquet, String message) {
		out.println(paquet.getDocs());
		System.out.print("ChoisirQuiVoter");
		String messageTcp = getControleurReseau().construirePaquetTcp("PVCV",
				traitementB.getRandom(core, core.getVoteType()), (String) paquet.getValeur(message, 1),
				(int) paquet.getValeur(message, 2), core.getJoueurId());
		getControleurReseau().envoyerTcp(messageTcp);
		System.out.println("ChoisirQuiVoter : \n");
		System.out.println(core.getEtatPartie());
	}

	private void fournirActionsDefense(Paquet paquet, String message) {
		List<Object> listerenvoye = traitementB.listeCarteJouee(this.core, (int) paquet.getValeur(message, 1));
		String messageTCP = getControleurReseau().construirePaquetTcp("RAZRD", listerenvoye.get(0), listerenvoye.get(1),
				(String) paquet.getValeur(message, 2), (int) paquet.getValeur(message, 3), core.getJoueurId());
		getControleurReseau().envoyerTcp(messageTCP);
		System.out.println("fournirActionsDefense : \n");
		System.out.println(core.getEtatPartie());
	}

	public void accepter(Paquet paquet, String message) {
		System.out.println((String) paquet.getValeur(message, 2));

		core.setJoueurId((String) paquet.getValeur(message, 2));
	}

	public void initialiserPartie(Paquet paquet, String message) {
		traitementB.initialiserPartie(this.core, (List<?>) paquet.getValeur(message, 1),
				(List<Couleur>) paquet.getValeur(message, 2), (String) paquet.getValeur(message, 3));
	}

	public void lancerDes(Paquet paquet, String message) {
		traitementB.lancerDes(core, (List<?>) paquet.getValeur(message, 2));
		String m1 = (String) paquet.getValeur(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PILD", m1, core.getJoueurId()));
		System.out.println("lancerDes : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choisirDestPlacement(Paquet paquet, String message) {
		String m1 = (String) paquet.getValeur(message, 3);
		int dest = traitementB.choisirDestPlacement((List<?>) paquet.getValeur(message, 2));
		int pion = traitementB.choisirPionPlacement(core);
		core.placePion(dest, pion);
		getControleurReseau().envoyerTcp(getControleurReseau().construirePaquetTcp("PICD", dest,pion, m1, core.getJoueurId()));
		System.out.println("choisirDestPlacement : \n");
		System.out.println(core.getEtatPartie());
	}

	public void debutTour(Paquet paquet, String message) {
		traitementB.debutTour(core, (List<Couleur>) paquet.getValeur(message, 2));
		core.resetPersoCache();
		System.out.println("debutTour : \n");
		System.out.println(core.getEtatPartie());
	}

	public void lanceDesChefVigil(Paquet paquet, String message) {
		Couleur c1 = (Couleur) paquet.getValeur(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) paquet.getValeur(message, 3);
			int m2 = (int) paquet.getValeur(message, 4);
			String messageTcp = getControleurReseau().construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			getControleurReseau().envoyerTcp(messageTcp);
		}
		
		core.NewChef((VigileEtat)paquet.getValeur(message, 2));
		System.out.println("lanceDesChefVigil : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choixDestVigil(Paquet paquet, String message) {
		if (!core.getEnvie())
			return;
		if (core.getCouleur() == (Couleur) paquet.getValeur(message, 1)
				&& (VigileEtat) paquet.getValeur(message, 2) == VigileEtat.NE) {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDCV", traitementB.choixDest(core),
					(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId());
			getControleurReseau().envoyerTcp(messageTcp);
		} else if (core.getCouleur() != (Couleur) paquet.getValeur(message, 1)
				&& (VigileEtat) paquet.getValeur(message, 2) == VigileEtat.NE) {
			return;
		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId());
			getControleurReseau().envoyerTcp(messageTcp);
		}
		System.out.println("choixDestVigil : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choisirDest(Paquet paquet, String message) {
		if (!core.getEnvie())
			return;

		if (core.getCouleur() == (Couleur) paquet.getValeur(message, 1)) {
			return;

		} else {
			String messageTcp = getControleurReseau().construirePaquetTcp("CDDJ", traitementB.choixDest(core),
					(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId());
			getControleurReseau().envoyerTcp(messageTcp);
		}
		System.out.println("choisirDest : \n");
		System.out.println(core.getEtatPartie());
	}

	public void destZombieVengeur(Paquet paquet, String message) {
		String message1 = getControleurReseau().construirePaquetTcp("CDDZVJE", traitementB.choixBestDest(core),
				paquet.getValeur(message, 1), paquet.getValeur(message, 2), core.getJoueurId());
		getControleurReseau().envoyerTcp(message1);
		System.out.println("destZombieVengeur : \n");
		System.out.println(core.getEtatPartie());
	}

	public void debutDeplacemant(Paquet paquet, String message) {
		traitementB.debutDeplacemant(core, (List<?>) paquet.getValeur(message, 4));
	}

	public void deplacerPion(Paquet paquet, String message) {
		List<Object> listRenvoye = traitementB.pionADeplacer(core, (int) paquet.getValeur(message, 1),
				(HashMap<Integer, List<Integer>>) paquet.getValeur(message, 2));
		System.out.println("deplacerPion avant dpl: {"+core.getCouleur()+ ", "+(Integer) listRenvoye.get(0)+ ", "+(Integer)listRenvoye.get(1)+"}\n");
		System.out.println(core.getEtatPartie());
		core.deplPionJoueurCourant(core.getCouleur(),(Integer) listRenvoye.get(0), (Integer)listRenvoye.get(1));
		if (((CarteType) listRenvoye.get(2)).equals(CarteType.SPR))
			core.joueCarte(core.getCouleur(),CarteType.SPR);
		
		String messageTcp = getControleurReseau().construirePaquetTcp("DPR", (Integer) listRenvoye.get(0),
				listRenvoye.get(1), listRenvoye.get(2), (String) paquet.getValeur(message, 3),
				(int) paquet.getValeur(message, 4), core.getJoueurId());
		getControleurReseau().envoyerTcp(messageTcp);
		
		System.out.println("deplacerPion apres dpl: \n");
		System.out.println(core.getEtatPartie());
	}

	public void attaqueZombie(Paquet paquet, String message) {
		traitementB.attaqueZombie(core, (List<PionCouleur>) (paquet.getValeur(message, 2)), new ArrayList<>());
		System.out.println("attaqueZombie : \n");
		System.out.println(core.getEtatPartie());
	}

	public void choisirSacrifice(Paquet paquet, String message) {
		out.println(paquet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("RAZCS", (int) paquet.getValeur(message, 1),
				traitementB.choisirSacrifice(core, (List<?>) paquet.getValeur(message, 2)),
				(String) paquet.getValeur(message, 3), (int) paquet.getValeur(message, 4), core.getJoueurId());
		getControleurReseau().envoyerTcp(messageTcp);
		System.out.println("choisirSacrifice : \n");
		System.out.println(core.getEtatPartie());
	}

	private void finPartie(Paquet paquet, String message) {
		traitementB.finPartie(core, (Couleur) paquet.getValeur(message, 2));
		System.out.println("finPartie : \n");
		System.out.println(core.getEtatPartie());
	}

	public void IndiquerCarteJouees(Paquet paquet, String message) {
		out.println(paquet.getDocs());
		String messageTcp = getControleurReseau().construirePaquetTcp("PVC", traitementB.IndiquerCarteJouees(core),
				/* , */ (String) paquet.getValeur(message, 1), (int) paquet.getValeur(message, 2),
				(String) core.getJoueurId());
		getControleurReseau().envoyerTcp(messageTcp);
		System.out.println("IndiquerCarteJouees : \n");
		System.out.println(core.getEtatPartie());
	}

	private void choixCarteFouille(Paquet paquet, String message) {
		List<Object> listeResultat = traitementB.carteFouille((List<CarteType>) paquet.getValeur(message, 1), core);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("SCFC", (CarteType) listeResultat.get(0),
						(CarteType) listeResultat.get(1), (Couleur) listeResultat.get(3),
						(CarteType) listeResultat.get(2), (String) paquet.getValeur(message, 2),
						paquet.getValeur(message, 3), core.getJoueurId()));
		System.out.println("choixCarteFouille : \n");
		System.out.println(core.getEtatPartie());

	}

	@Override
	public void set(Object core) {
		this.core = (BotMoyen) core;
	}
}
