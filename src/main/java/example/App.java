package example;

import NF.Core;
import UI.GUI;

public class App {

	public static void main(String[] args) {
		lancer(args);
	}
	
	public static Core lancer (String[] args) {
		long id = (long)(1000000 * Math.random());
		Core c = new Core(id);
		GUI.setId(id);
		GUI.lancement(args, c);
		return c;
	}

}
