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
	private BotFaible core;
	private TraitementBot traitementB;

	/**
	 * @param netWorkManager le controleur rÃ©seau
	 * @param core           coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (BotFaible) core;
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
		case "PIIJ":
			lancerDes(packet, message);
			break;
		case "PIRD":
			choisirDestPlacement(packet, message);
			break;
		case "PIIG":
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
		case "PRAZ":
			break;
		case "RAZA":
			attaqueZombie(packet, message);
			break;
		case "RAZDS":
			choisirSacrifice(packet, message);
			break;
		case "RAZIF":
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
					MessageFormat.format("[TCP] Il n''y a pas de traitement possible pour {0}", packet.getKey()));
		}
	}
	
	public void ReponseJoueurCourant(Packet packet, String message) {
        CarteType RJ = traitementB.ReponseJoueurCourant(core);
        String IDP = (String) packet.getValue(message, 2);
        int NT= (int) packet.getValue(message, 3 );
        getControleurReseau().getTcpClient()
        .envoyer(getControleurReseau().construirePaquetTcp("AZRCS", RJ ,IDP, NT, core.getJoueurId()));   
    }
	
	 public void ChoisirQuiVoter(Packet packet, String message)
	    {
	        out.println(packet.getDocs());
	        String messageTcp =  getControleurReseau().construirePaquetTcp("PVCV", traitementB.getRandom(), 
	                (String) packet.getValue(message, 1),(int) packet.getValue(message, 2), core.getJoueurId());
	        getControleurReseau().getTcpClient().envoyer(messageTcp);
	        
	    }
	
	private void fournirActionsDefense(Packet packet, String message) {
		List<CarteType> listeCarteJouee = traitementB.listeCarteJouee(this.core, (int) packet.getValue(message, 1));
		List<PionCouleur> listePionCache = traitementB.listePionCache(this.core);
		
		String messageTCP = getControleurReseau().construirePaquetTcp("RAZRD", listeCarteJouee, 
				listePionCache, (String) packet.getValue(message, 3),
				(int) packet.getValue(message, 4), core.getJoueurId());
		getControleurReseau().getTcpClient().envoyer(messageTCP);
	}

	public void accepter(Packet packet, String message) {
		System.out.println((String) packet.getValue(message, 2));

		core.setJoueurId((String) packet.getValue(message, 2));
	}

	public void initialiserPartie(Packet packet, String message) {
		traitementB.initialiserPartie(this.core, (List<?>) packet.getValue(message, 1),
				(List<?>) packet.getValue(message, 2), (int) packet.getValue(message, 3));

	}

	public void lancerDes(Packet packet, String message) {
		traitementB.lancerDes(core, (List<?>) packet.getValue(message, 2));
		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PILD", m1, core.getJoueurId()));
	}

	public void choisirDestPlacement(Packet packet, String message) {
		String m1 = (String) packet.getValue(message, 3);
		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("PICD",
						traitementB.choisirDestPlacement((List<?>) packet.getValue(message, 2)),
						traitementB.choisirPionPlacement(core), m1, core.getJoueurId()));
	}

	public void debutTour(Packet packet, String message) {
		traitementB.debutTour(core, (List<Couleur>) packet.getValue(message, 2));
	}

	public void lanceDesChefVigil(Packet packet, String message) {
		Couleur c1 = (Couleur) packet.getValue(message, 1);
		if (core.getCouleur() == c1) {
			String m1 = (String) packet.getValue(message, 3);
			int m2 = (int) packet.getValue(message, 4);
			String messageTcp = getControleurReseau().construirePaquetTcp("AZLD", m1, m2, core.getJoueurId());
			getControleurReseau().getTcpClient().envoyer(messageTcp);
		}
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
		List<Integer> destEtPion = traitementB.pionADeplacer((int) packet.getValue(message, 1),
				(HashMap<Integer, List<Integer>>) packet.getValue(message, 2));
		CarteType carte = CarteType.NUL;
		String messageTcp = getControleurReseau().construirePaquetTcp("DPR", destEtPion.get(0), destEtPion.get(1),
				carte, (String) packet.getValue(message, 3), (int) packet.getValue(message, 4), core.getJoueurId());
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
        String messageTcp = getControleurReseau().construirePaquetTcp("PVC", (int)traitementB.IndiquerCarteJouees(core),
                /*,*/ (String)packet.getValue(message, 1), (int)packet.getValue(message, 2), (String)core.getJoueurId());
        getControleurReseau().getTcpClient().envoyer(messageTcp);
    }
	
	private void choixCarteFouille(Packet packet, String message) {
		List<CarteType> listecartes = (List<CarteType>) packet.getValue(message, 1);
		CarteType carteGarde = CarteType.NUL;
		CarteType carteOfferte = CarteType.NUL;
		CarteType carteDefausse = CarteType.NUL;
		Couleur couleur = Couleur.NUL;
		if (listecartes.size() == 3) {
			carteGarde = listecartes.get(0);
			carteOfferte = listecartes.get(1);
			carteDefausse = listecartes.get(2);
			couleur = traitementB.getRandom();
		}
		if (listecartes.size() == 2) {
			carteGarde = listecartes.get(0);
			carteOfferte = listecartes.get(1);
			couleur = traitementB.getRandom();
		}
		if (listecartes.size() == 1) {
			carteGarde = listecartes.get(0);
		}

		getControleurReseau().getTcpClient()
				.envoyer(getControleurReseau().construirePaquetTcp("SCFC", carteGarde, carteOfferte, couleur,
						carteDefausse, (String) packet.getValue(message, 2), packet.getValue(message, 3),
						core.getJoueurId()));

	}

	@Override
	public void set(Object core) {
		this.core = (BotFaible) core;
	}
}
