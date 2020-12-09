package reseau.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Permet de gerer un serveur TCP du coté Serveur</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class TcpServeur implements Runnable, IControleSocket {
	private final ControleurReseau controleurReseau;
	private ServerSocket serveurSocket;

	private final List<TcpClient> connexions;
	private boolean estLancer;
	private final int port;

	private final Logger logger;

	/**
	 * @param controleurReseau Le controleur reseau associé
	 * @param port             Le port du serveur TCP
	 */
	public TcpServeur(ControleurReseau controleurReseau, int port) {
		this.port = port;
		this.connexions = new ArrayList<>();
		this.estLancer = true;
		this.controleurReseau = controleurReseau;
		this.logger = Logger.getLogger(getClass().getName());
		logger.log(Level.INFO, "Le serveur TCP démarre sur : {0}",
				controleurReseau.getIp().getHostAddress() + " " + port);
	}

	/**
	 * Démarre le serveur dans un nouveau thread.
	 */
	@Override
	public void run() {
		logger.finest("Démarrage du serveur TCP");
		logger.log(Level.FINEST, "Serveur TCP sur l'ip {0}", controleurReseau.getIp().getHostAddress());
		logger.log(Level.FINEST, "Serveur TCP sur le port {1}", port);
		try {
			serveurSocket = new ServerSocket(port);
		} catch (IOException e1) {
			return;
		}

		while (estLancer) {
			Socket sock;
			try {
				sock = serveurSocket.accept();
			} catch (IOException e) {
				break;
			}
			TcpClient connection;
			new Thread(connection = new TcpClient(sock, controleurReseau)).start();
			connexions.add(connection);
		}
		if (estLancer)
			try {
				arreter();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Permet d'arreter le serveur.
	 *
	 * @throws IOException Si le serveur ne s'arrete pas correctement @throws
	 */
	public void arreter() throws IOException {
		logger.log(Level.INFO, "Arret du serveur TCP");
		estLancer = false;

		if (serveurSocket != null) {
			serveurSocket.close();
		}
		logger.log(Level.INFO, "Serveur TCP arreter");
	}

	/**
	 * Obtient les connexions ouvertes.
	 *
	 * @return Les connexions ouvertes
	 */
	public List<TcpClient> getConnexions() {
		return connexions;
	}
}
