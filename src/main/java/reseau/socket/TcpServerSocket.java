package reseau.socket;

import reseau.tool.PtTool;
import reseau.tool.ThreadTool;
import reseau.type.ServerState;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Serveur TCP</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class TcpServerSocket {
	private static final Logger logger = Logger.getLogger(TcpServerSocket.class.getName());

	private ServerSocket serverSocket;
	private boolean enableLoop;
	private Thread tcpServerThread;
	private ServerState serverState;
	private final NetWorkManager netWorkManager;
	private boolean stop = false;

	/**
	 * Initialise le serveur TCP
	 *
	 * @param netWorkManager le gestionnaire réseau
	 */
	public TcpServerSocket(NetWorkManager netWorkManager) {
		this.netWorkManager = netWorkManager;
		serverState = ServerState.STOPPED;
		enableLoop = false;
		tcpServerThread = null;
	}

	/**
	 * Demarre un serveur TCP
	 *
	 * @param ip   c'est l'ip du serveur
	 * @param port port du serveur
	 */
	public void start(InetAddress ip, int port) {
		if (serverState != ServerState.STOPPED) {
			logger.log(Level.WARNING, "Server on server state : {}", serverState);
			return;
		}

		serverState = ServerState.INIT;
		try {
			serverSocket = new ServerSocket(port, 1, ip);
			logger.log(Level.FINEST, "Server on port {}", port);
		} catch (IOException e) {
			logger.severe("Server cannot start : " + e.getMessage());
			return;
		}
		enableLoop = true;
		tcpServerThread = ThreadTool.asyncTask(this::startAsync);
		logger.finest("Server on thread : " + tcpServerThread.getId());
	}

	/**
	 * Demarrage du serveur de facon asynchrone
	 */
	private void startAsync() {
		if (serverState != ServerState.INIT) {
			logger.log(Level.WARNING, "Server on server state : {0}", serverState);
			return;
		}

		logger.info("Server started !");
		while (enableLoop) {
			try {
				Socket socket = serverSocket.accept();
				socket.setTcpNoDelay(false);
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				String message = (String) inputStream.readObject();
				logger.log(Level.INFO, "Message received : {0}", message);

				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				String response = process(socket, message);
				if (response != null) {
					outputStream.writeObject(response);
					logger.log(Level.INFO, "Message sent : {0}", response);
				}

				inputStream.close();
				outputStream.close();
				socket.close();
				if (stop)
					stop();
			} catch (IOException | ClassNotFoundException e) {
				logger.warning("Cannot received or send message !");
			}
		}
	}

	/**
	 * Traitement du message
	 *
	 * @param socket  socket du message
	 * @param message message a traite
	 * @return renvoie une reponse
	 */
	private String process(Socket socket, String message) {
		return netWorkManager.getPacketHandlerTcp().traitement(PtTool.strToPacketTcp(message, netWorkManager), message );
	}

	/**
	 * Stop le serveur
	 */
	public void stop() {
		if (serverSocket == null)
			return;
		serverState = ServerState.STOPPED;
		enableLoop = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			logger.warning("Cannot close connection : " + e.getMessage());
		}
		tcpServerThread.interrupt();
		logger.info("Server stopped !");
	}

	/**
	 * Stop le serveur
	 */
	public void stopUntilFinished() {
		this.stop = true;
	}
}
