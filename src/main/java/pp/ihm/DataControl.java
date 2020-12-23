package pp.ihm;

import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pp.ihm.langues.International;
/**
 * 
 * @author florian
 * @author Remy 
 * @author Sebastien
 *
 */
public class DataControl {
	// auteurs  florian, remy 
	public static enum ApplicationPane {
		ACCUEIL, OPTION, PAUSE, PLATEAU, CONFIG, REGLES, ACCESSIBILITE, ENDGAME, WAIT, COULEUR, CONFIG_BOT
	}
	
	// auteur seb 
	public static enum ApplicationLangue {
		FRANCAIS, ENGLISH
	}

	public final static Locale localeEN = new Locale("en", "UK");
	public final static Locale localeFR = new Locale("fr", "FR");
	public final static String localMsgURL = "Ressources.Textes.messages";
	public static final String FOND = "Images/ImagesPP/fond.png"; // image modifiée par florian 
	public static final String PLATEAU = "Images/ImagesPP/plateau.png";
	public static final String PLATEAU_NB = "Images/ImagesPP/plateauNB.png";
	public static final String ICONE = "Images/ImagesPP/desktop Icon.jpg"; // image modifiée par florian
	public static final String SCREEN = "Images/ImagesPP/rotationEcran.png"; // image créée par remy 
	public static final String JOUEUR_ATTENDU = "Images/ImagesPP/joueurAttente.png"; // image libre de droits (freepik) modifiée par florian 
	public static final String JOUEUR_CONNECTE = "Images/ImagesPP/joueurConnecté.png"; // image libre de droits (freepik) modifiée par florian 
	
	
	public static final String FERME1 = "Images/ImagesPP/1Fermé.png";
	public static final String FERME2 = "Images/ImagesPP/2Fermé.png";
	public static final String FERME3 = "Images/ImagesPP/3Fermé.png";
	public static final String FERME5 = "Images/ImagesPP/5Fermé.png";
	public static final String FERME6 = "Images/ImagesPP/6Fermé.png";
	
	public static final String DOS_CARTE = "Images/ImagesPP/dosCarte.png";
	
	// Pions, tous les pions ont étés créés par florian 
	public static final String NOM_COULEUR = "Images/ImagesPP/Pions/SamplePion.png";
	
	public static final String TRUAND_B = "Images/ImagesPP/Pions/bleu1.png";
	public static final String BRUTE_B = "Images/ImagesPP/Pions/bleu2.png";
	public static final String BLONDE_B = "Images/ImagesPP/Pions/bleu3.png";
	public static final String FILLETTE_B = "Images/ImagesPP/Pions/bleu4.png";
	
	public static final String TRUAND_J = "Images/ImagesPP/Pions/jaune1.png";
	public static final String BRUTE_J = "Images/ImagesPP/Pions/jaune2.png";
	public static final String BLONDE_J= "Images/ImagesPP/Pions/jaune3.png";
	public static final String FILLETTE_J = "Images/ImagesPP/Pions/jaune4.png";
	
	public static final String TRUAND_M = "Images/ImagesPP/Pions/marron1.png";
	public static final String BRUTE_M = "Images/ImagesPP/Pions/marron2.png";
	public static final String BLONDE_M = "Images/ImagesPP/Pions/marron3.png";
	public static final String FILLETTE_M = "Images/ImagesPP/Pions/marron4.png";

	public static final String TRUAND_N = "Images/ImagesPP/Pions/noir1.png";
	public static final String BRUTE_N = "Images/ImagesPP/Pions/noir2.png";
	public static final String BLONDE_N = "Images/ImagesPP/Pions/noir3.png";
	public static final String FILLETTE_N = "Images/ImagesPP/Pions/noir4.png";

	public static final String TRUAND_R = "Images/ImagesPP/Pions/rouge1.png";
	public static final String BRUTE_R = "Images/ImagesPP/Pions/rouge2.png";
	public static final String BLONDE_R = "Images/ImagesPP/Pions/rouge3.png";
	public static final String FILLETTE_R = "Images/ImagesPP/Pions/rouge4.png";
	
	public static final String TRUAND_V = "Images/ImagesPP/Pions/vert1.png";
	public static final String BRUTE_V = "Images/ImagesPP/Pions/vert2.png";
	public static final String BLONDE_V = "Images/ImagesPP/Pions/vert3.png";
	public static final String FILLETTE_V = "Images/ImagesPP/Pions/vert4.png";
	
	// auteur remy 
	public static final String CSS = "pp/ihm/style/style.css";
	// redéfinition de NOM_COULEUR pour tester 
	//public static final String NOM_COULEUR = "Images/ImagesPP/Pions/marron1.png";

	//auteur florian 
	public static final ObservableList<Integer> nombreJoueur = FXCollections.observableArrayList(3, 4, 5, 6);
	public static final ObservableList<Integer> nombreBot = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6);
	public static final ObservableList<String> couleursJoueur = FXCollections.observableArrayList(
			International.trad("text.noir"), International.trad("text.vert"), International.trad("text.marron"),
			International.trad("text.rouge"), International.trad("text.bleu"), International.trad("text.jaune"));
	public static final ObservableList<String> difficulteBot = FXCollections.observableArrayList(
			International.trad("texte.valueFaible"), International.trad("texte.valueMoyen"),
			International.trad("texte.valueFort"));

	//auteur sebastien 
	public static Locale getLocale(ApplicationLangue langue) {
		switch (langue) {
		case ENGLISH:
			return localeEN;
		default:
			return localeFR;
		}
	}
	
	// auteur sebastien 
	public static ApplicationLangue getLangue(String l) {
		if (l.equals(ControleurLangue.get("texte.langue2"))) {//TODO opérateur ternaire ? 
			return ApplicationLangue.ENGLISH;
		}
		return ApplicationLangue.FRANCAIS;
	}
}
