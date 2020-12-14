package pp.ihm;

import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pp.ihm.langues.International;

public class DataControl {

	public static enum ApplicationPane {
		ACCUEIL, AIDE, CREATION, JEU, OPTION, PAUSE, PLATEAU, CONFIG, REGLES, ACCESSIBILITE, ENDGAME, WAIT, COULEUR,
		CONFIG_BOT
	}

	public static enum ApplicationLangue {
		FRANCAIS, ENGLISH
	}

	public final static Locale localeEN = new Locale("en", "UK");
	public final static Locale localeFR = new Locale("fr", "FR");
	public final static String localMsgURL = "Ressources.Textes.messages";
	public static final String FOND = "pp/ihm/images/fond.png";
	public static final String PLATEAU = "pp/ihm/images/plateau.png";
	public static final String PLATEAU_NB = "pp/ihm/images/plateauNB.png";
	public static final String ICONE = "pp/ihm/images/desktop Icon.jpg";
	public static final String SCREEN = "pp/ihm/images/rotationEcran.png";
	public static final String JOUEUR_ATTENDU = "pp/ihm/images/joueurAttente.png";
	public static final String JOUEUR_CONNECTE = "pp/ihm/images/joueurConnecté.png";
	public static final String FERME1 = "pp/ihm/images/1Fermé.png";
	public static final String FERME2 = "pp/ihm/images/2Fermé.png";
	public static final String FERME3 = "pp/ihm/images/3Fermé.png";
	public static final String FERME5 = "pp/ihm/images/5Fermé.png";
	public static final String FERME6 = "pp/ihm/images/6Fermé.png";

	public static final ObservableList<Integer> nombreJoueur = FXCollections.observableArrayList(3, 4, 5, 6);
	public static final ObservableList<Integer> nombreBot = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6);
	public static final ObservableList<String> couleursJoueur = FXCollections.observableArrayList(
			International.trad("text.noir"), International.trad("text.vert"), International.trad("text.marron"),
			International.trad("text.rouge"), International.trad("text.bleu"), International.trad("text.jaune"));
	public static final ObservableList<String> difficulteBot = FXCollections.observableArrayList(
			International.trad("texte.valueFaible"), International.trad("texte.valueMoyen"),
			International.trad("texte.valueFort"));

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
