package reseau.socket;

import reseau.tool.PaquetOutils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

/**
 * <h1>Permet de gerer les connections du serveur individuellement</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class Connexion implements Runnable {
    private final ControleurReseau controleurReseau;
    private final Socket socketCourant;
    private DataInputStream fluxEntre;
    private DataOutputStream fluxSortie;

    private final boolean estLancer;
    private final List<String> messagesTampon;

    /**
     * @param socket           Socket a gerer
     * @param controleurReseau Le controleur reseau depuis le socket est issue
     */
    public Connexion(Socket socket, ControleurReseau controleurReseau) {
        this.estLancer = true;
        this.socketCourant = socket;
        this.controleurReseau = controleurReseau;
        this.messagesTampon = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Permet d'ouvrir la connexion.
     *
     * @throws IOException Si les flux n'arrive a s'ouvrir
     */
    private void ouvrir() throws IOException {
        fluxEntre = new DataInputStream(new BufferedInputStream(socketCourant.getInputStream()));
        fluxSortie = new DataOutputStream(new BufferedOutputStream(socketCourant.getOutputStream()));
    }

    /**
     * Permet de fermer la connexion.
     *
     * @throws IOException Si les flux ou le socket n'a pas reussi a se terminer
     */
    public void fermer() throws IOException {
        if (socketCourant != null)
            socketCourant.close();
        if (fluxEntre != null)
            fluxEntre.close();
        if (fluxSortie != null)
            fluxSortie.close();
    }

    /**
     * Permet d'envoyer un message a un client.
     *
     * @param message Le message a envoyer
     */
    public void envoyer(String message) {
        try {
            this.fluxSortie.writeUTF(message);
            fluxSortie.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Send : " + message);
    }

    /**
     * Permet de recevoir des messages. Bloque l'execution du thread.
     *
     * @throws IOException Si la lecture du message a échoué
     */
    private void reception() throws IOException {
        while (estLancer) {
            String message = fluxEntre.readUTF();
            String cle = PaquetOutils.getCleMessage(message);
            controleurReseau.getTraitementPaquetTcp().traitement(
                    controleurReseau.getPacketsTcp().get(cle), message, this);
            messagesTampon.add(message);
            out.println("Receive : " + message);
        }
    }


    /**
     * Démarre la connexion dans un thread.
     */
    @Override
    public void run() {
        try {
            ouvrir();
            reception();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterMessage(String message) {
        messagesTampon.add(message);
    }

    /**
     * Permet d'obtenir la premiere occurrence du message du mot-clé correspond.
     *
     * @param cle Le mot-clé du message a obtenir
     * @return Le message du mot-clé correspond
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
     * Permet de savoir si un type de message est contenu dans la mémoire tampon. Le type du message est identifié par une clé.
     *
     * @param cle Mot-clé du message
     * @return Si le mot clé est contenu dans la mémoire tampon
     */
    private boolean contient(String cle) {
        synchronized (messagesTampon) {
            for (String message : messagesTampon)
                if (PaquetOutils.getCleMessage(message).equals(cle))
                    return true;
        }

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
