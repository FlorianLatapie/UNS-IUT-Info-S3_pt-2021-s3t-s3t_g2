package jeu.ihm;

import jeu.ihm.DataControl.ApplicationPane;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.HashMap;

public class ScreenControl implements EventHandler<MouseEvent> {

	private final Core core;
	private InterfacePrincipale primary = null;
	private HashMap<ApplicationPane, Node> listNode = new HashMap<>();
	private boolean lock = false;
	protected static String anglePane = "bas";



	public void setAnglePane(String sens) {
		ScreenControl.anglePane = sens;
	}

	/**
	 * crée le jeu.controleur de dialogue de l'application
	 *
	 * @param i : l'interface principale de l'application
	 */
	public ScreenControl(InterfacePrincipale i, Core core) {
		this.core = core;
		primary = i;
		primary.getScene().addEventFilter(MouseEvent.ANY, this);
	}

	/**
	 * Enregistre les noeuds (écrans/panneau) qui seront pilotés par le jeu.controleur
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
	public void setRotatePane(Node n, String sens) {
		double angle = n.getRotate();
		this.setAnglePane(sens);
		switch (sens) {
		case ("haut"):
			if (angle != 180) {
				angle = 180;
			}
			break;
		case ("bas"):
			if (angle != 0) {
				angle = 0;
			}
			break;
		case ("gauche"):
			if (angle != 90) {
				angle = 90;
			}

			break;
		case ("droite"):
			if (angle != -90) {
				angle = -90;
			}
			break;
		}

		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(500));
		rotateTransition.setNode(n);
		rotateTransition.setToAngle(angle);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
	}
	
	public static double getAngle(String sens) {
		double currentAngle = 0;
		switch (sens) {
			case ("haut"):
				currentAngle = 180;
				break;
			case ("bas"):
				currentAngle = 0;
				break;
			case ("gauche"):
				currentAngle = 90;
				break;
			case ("droite"):
				currentAngle = -90;
				break;
			}
		return currentAngle;
	}

	/**
	 * Consomme les évènement souris qui ont lieu durant une animation pour
	 * permettre à celle-ci d'avoir lieu sans problème.
	 *
	 * @param event :un evènement souris
	 */
	@Override
	public void handle(MouseEvent event) {
		if (lock) {
			event.consume();
		}
	}
}
