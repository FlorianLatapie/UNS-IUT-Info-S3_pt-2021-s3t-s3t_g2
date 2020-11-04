package ihm;

import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataControl {

	public static enum ApplicationPane {
		ACCUEIL, AIDE, CREATION, JEU, OPTION, PAUSE, PLATEAU, CONFIG, REGLES, ACCESSIBILITE, ENDGAME, WAIT, COULEUR
	}

	public static enum ApplicationLangue {
		FRANCAIS, ENGLISH
	}

	public final static Locale localeEN = new Locale("en", "UK");
	public final static Locale localeFR = new Locale("fr", "FR");
	public final static String localMsgURL = "Ressources.Textes.messages";
	public static final String FOND = "ihm/fond.png";
	public static final String PLATEAU = "ihm/plateau.png";
	public static final String ICONE = "ihm/desktop Icon.jpg";
	public static final String SCREEN = "ihm/rotateScreen.png";
	
	public static final ObservableList<Integer> nombreJoueur = FXCollections.observableArrayList(3,4,5,6);
	public static final ObservableList<Integer> nombreBot = FXCollections.observableArrayList(0,1,2,3,4,5,6);
	
	public static final ObservableList<String> couleursJoueur = FXCollections.observableArrayList("Noir", "Vert",
			"Marron", "Rouge", "Bleu", "Jaune");


	public static Locale getLocale(ApplicationLangue l) {
		switch (l) {
		case ENGLISH:
			return localeEN;
		default:
			return localeFR;
		}
	}

	public static ApplicationLangue getLangue(String l) {
		if (l.equals(ControleurLangue.get("texte.langue2"))) {
			return ApplicationLangue.ENGLISH;
		}
		return ApplicationLangue.FRANCAIS;
	}
}
