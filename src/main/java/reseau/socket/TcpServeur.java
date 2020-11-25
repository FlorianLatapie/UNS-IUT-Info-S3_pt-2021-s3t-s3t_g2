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
 * @version 1.0
 */
public class TcpServeur implements Runnable {
	private ControleurReseau netWorkManager;
	private ServerSocket serveurSocket;

	private final List<Connexion> connexions;
	private boolean estLancer;
	private int port;

	private Logger logger;

	/**
	 * @param controleurReseau Le controleur reseau associé
	 * @param port             Le port du serveur TCP
	 */
	public TcpServeur(ControleurReseau controleurReseau, int port) {
		this.port = port;
		this.connexions = new ArrayList<>();
		this.estLancer = true;
		this.netWorkManager = controleurReseau;
		this.logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Démarre le serveur dans un nouveau thread.
	 */
	@Override
	public void run() {
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
			Connexion connection = new Connexion(sock, netWorkManager);
			new Thread(connection).start();
			connexions.add(connection);
		}
		System.out.println("Ended TCP Serveur");
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
		estLancer = false;
		for (Connexion connexion : connexions)
			connexion.arreter();

		if (serveurSocket != null) {
			System.out.println("TCP SERVEUR CLIENT §§§§§§");
			serveurSocket.close();
		}
	}

	/**
	 * Obtient les connexions ouvertes.
	 *
	 * @return Les connexions ouvertes
	 */
	public List<Connexion> getConnexions() {
		return connexions;
	}
}
