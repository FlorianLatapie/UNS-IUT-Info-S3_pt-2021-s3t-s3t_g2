package idjr.ihmidjr;

import idjr.ihmidjr.DataControl.ApplicationPane;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * 
 * @author florian 
 * @author sebastien 
 *
 */
public class ScreenControl implements EventHandler<MouseEvent> {

	private final Core core;
	private InterfacePrincipale primary = null;
	private HashMap<ApplicationPane, Node> listNode = new HashMap<>();
	private boolean lock = false;

	/**
	 * crée le controleur de dialogue de l'application
	 * 
	 * @param i : l'interface principale de l'application
	 */
	public ScreenControl(InterfacePrincipale i, Core core) {
		this.core = core;
		primary = i;
		primary.getScene().addEventFilter(MouseEvent.ANY, this);
	}

	/**
	 * Enregistre les noeuds (écrans/panneau) qui seront pilotés par le controleur
	 * de dialogue
	 *
	 * @param s : le nom du noeud, c'est à dire de l'écran (en réalité panneau)
	 * @param n : une référence sur le noeud
	 */
	public void registerNode(ApplicationPane s, Node n) {
		listNode.put(s, n);

	}

	/**
	 * Affiche le conteneur (noeud/écran/panneau) en paramètre
	 *
	 * @param s : le nom du panneau (écran/noeud) que l'on souhaite afficher
	 */
	public void setPaneOnTop(ApplicationPane s) {
		if (listNode.containsKey(s)) {
			primary.setOnTop(listNode.get(s));
		}

	}

	/**
	 * Consomme les évènement souris qui ont lieu durant une animation pour
	 * permettre à celle-ci d'avoir lieu sans problème.
	 * @author sebastien
	 * @param event :un evènement souris
	 */
	@Override
	public void handle(MouseEvent event) {
		if (lock) {
			event.consume();
		}
	}
}