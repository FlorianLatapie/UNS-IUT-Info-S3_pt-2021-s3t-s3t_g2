package pp.ihm.langues;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <h1>Permet de gérer la traduction</h1>
 *
 * @author Tom Gontero
 * @version 1.0
 */
public abstract class International {
	private static Locale langue;
	private static ResourceBundle resourceBundle;
	private static String CHEMIN_LANGUE = "messages_";

	static {
		langue = Locale.FRENCH;
		resourceBundle = ResourceBundle.getBundle(langue.getDisplayLanguage(), langue);
	}

	/**
	 * Permet de changer la langue
	 *
	 * @param l la langue cible
	 */
	public static void changerLangue(Locale l) {
		langue = l;
	}

	/**
	 * Permet de traduire la phrase cible
	 *
	 * @param phrase la phrase à traduire
	 * @return la traduction de la phrase
	 */
	public static String trad(String phrase) {
		if (!resourceBundle.containsKey(phrase))
			throw new IllegalArgumentException("La traduction " + phrase + " n'existe pas");
		return resourceBundle.getString(phrase);
	}

	/**
	 * Permet d'obtenir la langue actuelle
	 *
	 * @return la langue actuelle
	 */
	public static String getActualLangue() {
		return langue.getDisplayLanguage();
	}

}
