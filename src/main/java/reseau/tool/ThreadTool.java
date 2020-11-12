package reseau.tool;

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
}
