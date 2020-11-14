package reseau.tool;

/**
 * <h1> Outils pour les threads </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class ThreadOutils {
    private ThreadOutils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Permet d'effectuer plusieurs taches de facon asynchrone.
     *
     * @param task Les taches a effectuer
     * @return Le thread crée
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
