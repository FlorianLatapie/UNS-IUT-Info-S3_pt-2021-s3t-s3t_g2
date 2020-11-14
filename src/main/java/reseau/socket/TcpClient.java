package reseau.socket;

import reseau.tool.PaquetOutils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static java.util.Collections.synchronizedList;

/**
 * <h1>Permet de gerer un serveur TCP du coté client</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class TcpClient implements Runnable {
    private final ControleurReseau controleurReseau;
    private Socket socket;
    private DataOutputStream fluxSortie;
    private DataInputStream fluxEntre;
    private InetAddress ip;
    private int port;

    private boolean estLancer;
    private final List<String> messagesTampon;

    /**
     * @param controleurReseau Le controleur reseau du client
     * @param ip   L'ip du serveur TCP cible
     * @param port Le port du serveur TCP cible
     */
    public TcpClient(ControleurReseau controleurReseau, InetAddress ip, int port) {
        this.messagesTampon = synchronizedList(new ArrayList<String>());
        this.controleurReseau = controleurReseau;
        this.estLancer = true;
        this.ip = ip;
        this.port = port;
    }

    /**
     * Permet d'ouvrir la connexion.
     *
     * @return Si la connexion a été ouverte corretement
     * @throws IOException Si les flux de s'ouvre pas correctement
     */
    private boolean ouvrir(InetAddress ip, int port) throws IOException {
        out.println("Attente ...");
        boolean attendre = true;
        while (attendre)
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socket = new Socket(ip, port);
                attendre = false;
                out.println("Connected: " + socket);
            } catch (UnknownHostException uhe) {
                out.println("Pas de connexion !");
            } catch (IOException ioe) {
                out.println("Pas de connexion !");
            }

        if (socket == null)
            return false;

        fluxSortie = new DataOutputStream(socket.getOutputStream());
        fluxEntre = new DataInputStream(socket.getInputStream());

        reception();

        return true;
    }

    @Override
    public void run() {
        try {
            ouvrir(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reception();
    }

    /**
     * Permet d'envoyer un message au serveur.
     *
     * @param message Le message a a envoyer
     */
    public void envoyer(String message)  {
        if (socket == null)
            return;

        try {
            fluxSortie.writeUTF(message);
            fluxSortie.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Send : " + message);
    }

    /**
     * Permet de recevoir des messages du client. Bloque l'execution de thread.
     */
    private void reception() {
        String message;
        while (estLancer) {
            try {
                message = fluxEntre.readUTF();
            } catch (Exception e) {
                continue;
            }

            String cle = PaquetOutils.getCleMessage(message);
            controleurReseau.getTraitementPaquetTcp().traitement(
                    controleurReseau.getPacketsTcp().get(cle), message, socket);
            messagesTampon.add(message);
            out.println("Receive : " + message);
        }
    }

    /**
     * Arrete le serveur.
     *
     * @throws IOException Si les flux ou si le socket ne se ferme pas correctement
     */
    public void stop() throws IOException {
        estLancer = false;

        if (fluxSortie != null)
            fluxSortie.close();

        if (fluxEntre != null)
            fluxEntre.close();

        if (socket != null)
            socket.close();

        out.println("Server stopped");
    }

    /**
     * Permet d'ajouter un message dans la mémoire tampon.
     *
     * @param message Le message a ajouter
     */
    public void ajouterMessage(String message) {
        messagesTampon.add(message);
    }

    /**
     * Attend le message du mot-clé correspondant.
     *
     * @param cle La cle du message
     * @return Le message du mot-clé correspondant
     */
    public String getMessage(String cle) {
        for (String message : messagesTampon)
            if (PaquetOutils.getCleMessage(message).equals(cle)) {
                messagesTampon.remove(message);
                return message;
            }

        return null;
    }

    /**
     * Permet de savoir si le message est contenu dans la mémoire tampon.
     *
     * @param cle La clé du message cible
     * @return Si le message est contenu
     */
    private boolean contient(String cle) {
        for (String message : messagesTampon)
            if (PaquetOutils.getCleMessage(message).equals(cle))
                return true;

        return false;
    }

    /**
     * Bloque l'execution du thread tant que le message n'a pas été recu.
     *
     * @param cle Mot-clé du message
     */
    public void attendreMessage(String cle) {
        while (!contient(cle))
            ;
    }
}
