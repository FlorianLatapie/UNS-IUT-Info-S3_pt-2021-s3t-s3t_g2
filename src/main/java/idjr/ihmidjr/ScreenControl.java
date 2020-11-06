package idjr.ihmidjr;

import idjr.ihmidjr.DataControl.ApplicationPane;
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
    //private MemoryGame mg;
    private boolean lock = false;


    /**
     * crée le controleur de dialogue de l'application
     *
     * @param i : l'interface principale de l'application
     */
    public ScreenControl(InterfacePrincipale i, Core core) {
        this.core = core;
        primary = i;
        //mg = new MemoryGame(this);
        primary.getScene().addEventFilter(MouseEvent.ANY, this);
    }

    /**
     * Enregistre les noeuds (écrans/panneau) qui seront pilotés par le controleur de dialogue
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
     * Informe le nf du theme selectionné et affiche l'information dans l'interface
     *
     * @param l : une référence vers le libéllé indiquand le theme
     */
    public void setTheme(Labeled l) {
        //mg.setTheme(DataControl.getTheme(l.getText()));
        //((OptionPane)listNode.get(ApplicationPane.OPTION)).setTheme(l);
    }

    /**
     * Informe le nf de la langue selectionnée et affiche l'information dans l'interface
     *
     * @param l : une référence vers le libéllé indiquand la langue
     */
    public void setLangue(Labeled l) {
        //mg.setLangue(DataControl.getLangue(l.getText()));
        //((OptionPane)listNode.get(ApplicationPane.OPTION)).setLangue(l);
        //I18N.setLocale(DataControl.getLocale(DataControl.getLangue(l.getText()))); //Phase 2 : à décommenter quand on travaillera sur l'internationalisation
    }
    
    public void rotatePane(Node n , String sens) {
    	double angle = n.getRotate();
    	switch(sens) {
    		case("haut"):
    			if (angle != 180)
    				angle = 180;
    			break;
    		case("bas"):
    			if (angle != 0)
    				angle = 0;
    			break;
    		case("gauche"):
    			if (angle != 90)
    				angle = 90;
    			break;
    		case("droite"):
    			if (angle != -90)
    				angle = -90;
    			break;
    	}
		
    	RotateTransition rotateTransition = new RotateTransition();
    	rotateTransition.setDuration(Duration.millis(500));
    	rotateTransition.setNode(n);
    	rotateTransition.setToAngle(angle);
    	rotateTransition.setAutoReverse(false);
    	rotateTransition.play();
    }

    /**
     * Consomme les évènement souris qui ont lieu durant une animation pour permettre à celle-ci d'avoir lieu sans problème.
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