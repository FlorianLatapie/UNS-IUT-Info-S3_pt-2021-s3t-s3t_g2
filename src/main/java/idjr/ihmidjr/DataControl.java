package idjr.ihmidjr;

import java.util.Locale;

import idjr.ihmidjr.langues.International;
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
	public static final String CSS = "idjr/ihmidjr/style/style.css";

	
	public static final String FOND = "Images/ImagesIDJR/fond.png";
	public static final String PLATEAU = "Images/ImagesIDJR/plateau.png";
	public static final String ICONE = "Images/ImagesIDJR/desktopIcon.jpg";
	public static final String NOIR = "Images/ImagesIDJR/noir.png";
	public static final String VERT = "Images/ImagesIDJR/vert.png";
	public static final String MARRON = "Images/ImagesIDJR/marron.png";
	public static final String ROUGE = "Images/ImagesIDJR/rouge.png";
	public static final String BLEU = "Images/ImagesIDJR/bleu.png";
	public static final String JAUNE = "Images/ImagesIDJR/jaune.png";

	public static final String BADGE_VIGILE = "Images/ImagesIDJR/badge vigile.png";

	// Cartes
	public static final String CARTE_BATTE = "Images/ImagesIDJR/Cartes/Cartes_Armes/batte.png";
	public static final String CARTE_CS = "Images/ImagesIDJR/Cartes/Cartes_Armes/Canon Scié.png";
	public static final String CARTE_GRENADE = "Images/ImagesIDJR/Cartes/Cartes_Armes/grenade.png";
	public static final String CARTE_HACHE = "Images/ImagesIDJR/Cartes/Cartes_Armes/hache.png";
	public static final String CARTE_REVOLVER = "Images/ImagesIDJR/Cartes/Cartes_Armes/revolver.png";
	public static final String CARTE_TRONCENNEUSE = "Images/ImagesIDJR/Cartes/Cartes_Armes/tronconneuse.png";

	// Autres artes
	public static final String CARTE_CACHETTE = "Images/ImagesIDJR/Cartes/Cartes_Autres/cachette.png";
	public static final String CARTE_CAMERA = "Images/ImagesIDJR/Cartes/Cartes_Autres/camera.png";
	public static final String CARTE_MATERIEL = "Images/ImagesIDJR/Cartes/Cartes_Autres/matériel.png";
	public static final String CARTE_MENACE = "Images/ImagesIDJR/Cartes/Cartes_Autres/menace.png";
	public static final String CARTE_SPRINT = "Images/ImagesIDJR/Cartes/Cartes_Autres/sprint.png";

	// Autre autre carte 
	public static final String CARTE_VIDE = "Images/ImagesIDJR/carte_vide.png";
	public static final String DOS_CARTE = "Images/ImagesIDJR/Cartes_Autres/dosCarte.png";

	public static final ObservableList<Integer> nombreJoueur = FXCollections.observableArrayList(3, 4, 5, 6);
	public static final ObservableList<Integer> nombreBot = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6);
	public static final ObservableList<String> typePartie = FXCollections.observableArrayList("seb mets","les types","dans le","datacontrol");//TODO
	public static final ObservableList<String> statutPartie = FXCollections.observableArrayList("seb mets","les types","dans le","datacontrol");//TODO
	public static final ObservableList<String> couleursJoueur = FXCollections.observableArrayList(
			International.trad("text.noir"), International.trad("text.vert"), International.trad("text.marron"),
			International.trad("text.rouge"), International.trad("text.bleu"), International.trad("text.jaune"));

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