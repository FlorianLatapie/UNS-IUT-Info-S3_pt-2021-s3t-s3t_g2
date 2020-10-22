package ihm;

import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataControl {

	public static enum ApplicationPane {
		ACCUEIL, AIDE, CREATION, JEU, OPTION, PAUSE, PLATEAU
	}

	public static enum ApplicationLangue {
		FRANCAIS, ENGLISH
	}

	public final static Locale localeEN = new Locale("en", "UK");
	public final static Locale localeFR = new Locale("fr", "FR");
	public final static String localMsgURL = "Ressources.Textes.messages";

	// public final static int effectVolume = 50;

	// public static final String VOLUMEON = "./Ressources/Icones/MusiqueOn.png";
	// public static final String VOLUMEOFF = "./Ressources/Icones/MusiqueOff.png";

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
