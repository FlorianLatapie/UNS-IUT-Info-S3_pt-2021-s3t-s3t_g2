package reseau.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

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

    /**
     * @param controleurReseau Le controleur reseau associé
     * @param port Le port du serveur TCP
     */
    public TcpServeur(ControleurReseau controleurReseau, int port) {
        this.port = port;
        this.connexions = new ArrayList<>();
        this.estLancer = true;
        this.netWorkManager = controleurReseau;
    }

    /**
     * Démarre le serveur dans un nouveau thread.
     */
    @Override
    public void run() {
        try {
            serveurSocket = new ServerSocket(port);
        } catch (IOException e1) {
            out.println("Server stopped");
            return;
        }
        out.println("Server started !!!!");

        while (estLancer) {
            Socket sock;
            try {
                sock = serveurSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            out.println("Connected");
            Connexion connection = new Connexion(sock, netWorkManager);
            new Thread(connection).start();
            connexions.add(connection);
        }

        try {
            arreter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'arreter le serveur.
     *
     * @throws IOException Si le serveur ne s'arrete pas correctement
     */
    public void arreter() throws IOException {
        for (Connexion connexion : connexions)
            connexion.fermer();

        estLancer = false;
        if (serveurSocket != null)
            serveurSocket.close();
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
