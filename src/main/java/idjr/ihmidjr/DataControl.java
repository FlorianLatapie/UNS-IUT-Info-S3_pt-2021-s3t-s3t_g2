package idjr.ihmidjr;

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
	public static final String FOND = "idjr/ihmidjr/images/fond.png";
	public static final String PLATEAU = "idjr/ihmidjr/images/plateau.png";
	public static final String ICONE = "idjr/ihmidjr/images/desktop Icon.jpg";
	
	public static final String NOIR = "idjr/ihmidjr/images/noir.png";
	public static final String VERT = "idjr/ihmidjr/images/vert.png";
	public static final String MARRON = "idjr/ihmidjr/images/marron.png";
	public static final String ROUGE = "idjr/ihmidjr/images/rouge.png";
	public static final String BLEU = "idjr/ihmidjr/images/bleu.png";
	public static final String JAUNE = "idjr/ihmidjr/images/jaune.png";
	
		
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