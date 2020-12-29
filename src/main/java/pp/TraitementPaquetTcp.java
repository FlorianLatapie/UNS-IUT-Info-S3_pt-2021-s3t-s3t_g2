package pp;

import pp.controleur.ControleurJeu;
import reseau.paquet.Paquet;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.socket.TraitementPaquet;
import reseau.type.TypeJoueur;
import reseau.type.TypePartie;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.out;

/**
 * <h1>Permet de gérer les packets TCP</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class TraitementPaquetTcp extends TraitementPaquet<TcpClient> {
	private ControleurJeu core;

	/**
	 * @param core coeur du jeu
	 */
	public TraitementPaquetTcp(Object core) {
		this.core = (ControleurJeu) core;
	}

	@Override
	public void init() {

	}

	/**
	 * Traitement des paquet TCP
	 *
	 * @param packet     le paquet du message
	 * @param message    le message sous forme de chaine de caractere
	 * @param connection le socket du paquet
	 * @return reponse au paquet
	 * @throws IllegalStateException si il n'y a pas de traitement pour ce paquet
	 */
	@Override
	public void traitement(Paquet packet, String message, TcpClient connection) {
		switch (packet.getCle()) {
		case "DCP":
			dcp(packet, message, connection);
			break;
		}
	}

	private void dcp(Paquet packet, String message, TcpClient connection) {
		switch (core.getStatus()) {
		case ATTENTE:
			if (core.estPleine()) {
				connection.envoyer(ControleurReseau.construirePaquetTcp("RCP", core.getPartieId()));
			} else {
				TypeJoueur typeJoueur = (TypeJoueur) packet.getValeur(message, 2);
				if (core.getNbjractuel() != core.getNbjr() && TypeJoueur.JR == typeJoueur) {
					String id = core.ajouterJoueur((String) packet.getValeur(message, 1),
							(TypeJoueur) packet.getValeur(message, 2), connection);
					connection.envoyer(ControleurReseau.construirePaquetTcp("ACP", core.getPartieId(), id));
				} else if (core.getNbjvactuel() != core.getNbjv() && TypeJoueur.BOT == typeJoueur) {
					String id = core.ajouterJoueur((String) packet.getValeur(message, 1),
							(TypeJoueur) packet.getValeur(message, 2), connection);
					connection.envoyer(ControleurReseau.construirePaquetTcp("ACP", core.getPartieId(), id));
				} else {
					connection.envoyer(ControleurReseau.construirePaquetTcp("RCP", core.getPartieId()));
				}
			}
			break;
		case ANNULEE:
		case COMPLETE:
		case TERMINEE:
			connection.envoyer(ControleurReseau.construirePaquetTcp("RCP", core.getPartieId()));
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + core.getStatus());
		}
	}

	@Override
	public void set(Object core) {
		this.core = (ControleurJeu) core;
	}
}
