package reseau.tool;

import reseau.socket.ControleurReseau;

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
	 * @param nom  Le nom a attribuer
	 * @param task Les taches a effectuer
	 * @return Le thread crée
	 */
	static Thread asyncTask(String nom, Runnable... task) {
		Thread t = new Thread(() -> {
			for (Runnable runnable : task)
				runnable.run();
		}, nom);
		t.start();

		return t;
	}

	static Thread asyncTaskRepeat(String nom, int repeat, Runnable task) {
		Thread t = new Thread(() -> {
			for (int i = 0; i < repeat; i++)
				task.run();
		}, nom);
		t.start();

		return t;
	}

	static Thread asyncTaskInfinite(String nom, Runnable task) {
		Thread t = new Thread(() -> {
			while (true)
				task.run();
		}, nom);
		t.start();

		return t;
	}

	static void attendre() {
		if (!ControleurReseau.isVite())
			Thread.yield();
	}
}
