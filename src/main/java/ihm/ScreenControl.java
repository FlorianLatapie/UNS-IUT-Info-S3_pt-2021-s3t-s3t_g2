package ihm;

import java.util.HashMap;
import java.util.List;

import ihm.DataControl.ApplicationPane;
import ihm.InterfacePrincipale;
//import ihm.JeuPane;
//import ihm.OptionPane;
import javafx.application.Application;
import javafx.event.EventHandler; 
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

public class ScreenControl implements EventHandler<MouseEvent>{

	private InterfacePrincipale primary = null;
	private HashMap<ApplicationPane, Node> listNode = new HashMap<>();
	//private MemoryGame mg;
	private boolean lock = false;


	/**
	 * crée le controleur de dialogue de l'application
	 * @param i : l'interface principale de l'application
	 */
	public ScreenControl(InterfacePrincipale i) {
		primary = i;
		//mg = new MemoryGame(this);
		primary.getScene().addEventFilter(MouseEvent.ANY, this);
	}

	/**
	 * Enregistre les noeuds (écrans/panneau) qui seront pilotés par le controleur de dialogue
	 * @param s : le nom du noeud, c'est à dire de l'écran (en réalité panneau)
	 * @param n : une référence sur le noeud
	 */
	public void registerNode(ApplicationPane s, Node n) {
		listNode.put(s, n);

	}

	/**
	 * Affiche le conteneur (noeud/écran/panneau) en paramètre
	 * @param s : le nom du panneau (écran/noeud) que l'on souhaite afficher
	 */
	public void setPaneOnTop(ApplicationPane s) {
		if (listNode.containsKey(s))
			primary.setOnTop(listNode.get(s));
	}

	
	/**
	 * Informe le nf du theme selectionné et affiche l'information dans l'interface
	 * @param l : une référence vers le libéllé indiquand le theme
	 */
	public void setTheme(Labeled l) {
		//mg.setTheme(DataControl.getTheme(l.getText()));
		//((OptionPane)listNode.get(ApplicationPane.OPTION)).setTheme(l);
	}

	/**
	 * Informe le nf de la langue selectionnée et affiche l'information dans l'interface
	 * @param l : une référence vers le libéllé indiquand la langue
	 */
	public void setLangue(Labeled l) {
		//mg.setLangue(DataControl.getLangue(l.getText()));
		//((OptionPane)listNode.get(ApplicationPane.OPTION)).setLangue(l);
		//I18N.setLocale(DataControl.getLocale(DataControl.getLangue(l.getText()))); //Phase 2 : à décommenter quand on travaillera sur l'internationalisation
	}

	/**
	 * Informe le nf du volume des effets sonores et affiche l'information dans l'interface
	 * @param v : la valeur du volume en 0 et 100
	 */
	public void setSonVolume(int v) {
		//mg.setEffectVolume(v);
		//((OptionPane)listNode.get(ApplicationPane.OPTION)).setSonVolume(v);
	}
	
	/**
	 * Retourne la valeur du volume des effets sonores
	 * @return un entier entre 0 et 100
	 */
	/*public int getSonVolume() {
		return mg.getEffectVolume();	
	}*/
	
	/**
	 * Retourne le theme sélectionné
	 * @return le theme sélectionné appartenant à l'énumération ApplicationTheme
	 */
	/*public ApplicationTheme getTheme() {
		return mg.getTheme();
	}*/


	/**
	 * Consomme les évènement souris qui ont lieu durant une animation pour permettre à celle-ci d'avoir lieu sans problème. 
	 * @param event :un evènement souris
	 */
	@Override
	public void handle(MouseEvent event) {
		if (lock) {
			event.consume();
		}
	}
}
