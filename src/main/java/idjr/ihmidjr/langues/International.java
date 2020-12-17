package idjr.ihmidjr.langues;

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
	private static String CHEMIN_LANGUE = "langues_IDJR.messages";

	static {
		langue = Locale.FRENCH;
		resourceBundle = ResourceBundle.getBundle(CHEMIN_LANGUE, langue);
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
	 * Permet de traduire la phrase cible avec des arguments
	 *
	 * @param phrase     la phrase à traduire
	 * @param argStrings la liste des arguments custom
	 * @return la traduction de la phrase
	 */

	public static String trad(String phrase, String... argStrings) {
		if (!resourceBundle.containsKey(phrase))
			throw new IllegalArgumentException("La traduction " + phrase + " n'existe pas");
		System.out.println(phrase);

		String tmp = resourceBundle.getString(phrase);
		StringBuilder t = new StringBuilder(tmp);
		int i = 0;
		while (tmp.contains("{" + i + "}")) {
			int startIndex = t.indexOf("{");
			int stopIndex = t.indexOf("}") + 1;
			t = t.replace(startIndex, stopIndex, argStrings[i]);
			System.out.println(t);
			i++;
		}

		return t.toString();
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
