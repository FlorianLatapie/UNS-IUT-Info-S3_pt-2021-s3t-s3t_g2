package reseau.tool;

import reseau.socket.TcpClientSocket;

import java.net.InetAddress;
import java.util.concurrent.*;

/**
 * <h1> Outils pour les threads </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class ThreadTool {
    private ThreadTool() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Permet d'effectuer plusieurs taches de facon asynchrone
     *
     * @param task les taches a effectuer
     * @return le thread crée
     */
    public static Thread asyncTask(Runnable... task) {
        Thread t = new Thread(() -> {
            for (Runnable runnable : task)
                runnable.run();
        });
        t.start();

        return t;
    }

    /**
     * Envoie une requete TCP de facon asynchrone
     *
     * @param ip      l'ip de destination
     * @param port    le port destination
     * @param message le message a envoyé
     * @return le message de retour
     */
    public static String taskPacketTcp(InetAddress ip, int port, String message) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> TcpClientSocket.connect(ip, port, message,null,0);
        Future<String> future = executorService.submit(callable);
        String p = "";
        try {
            p = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        return p;
    }

    /**
     * Envoie une requete TCP de facon asynchrone
     *
     * @param ip      l'ip de destination serveur
     * @param port    le port destination serveur
     * @param message le message a envoyé
     * @param ip1      l'ip du serveur TCP client
     * @param port1    port du serveur TCP client
     * @return le message de retour
     */
    public static String taskPacketTcp(InetAddress ip, int port, String message, InetAddress ip1, int port1) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> TcpClientSocket.connect(ip, port, message, ip1, port1);
        Future<String> future = executorService.submit(callable);
        String p = "";
        try {
            p = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        return p;
    }
}
