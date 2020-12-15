package reseau.tool;

/**
 * <h1>Outils pour les threads</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public interface ThreadOutils {
	/**
	 * Permet d'effectuer plusieurs taches de facon asynchrone.
	 *
	 * @param nom Le nom a attribuer
	 * @param task Les taches a effectuer
	 * @return Le thread crée
	 */
	 static Thread asyncTask(String nom ,Runnable... task) {
		Thread t = new Thread(() -> {
			for (Runnable runnable : task)
				runnable.run();
		},nom);
		t.start();

		return t;
	}

	 static Thread asyncTaskRepeat(int repeat, Runnable task) {
		Thread t = new Thread(() -> {
			for (int i = 0; i < repeat; i++)
				task.run();
		});
		t.start();

		return t;
	}
}
