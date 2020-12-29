package pp.ihm.langues;

import java.util.ArrayList;
import java.util.List;
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
	private static String CHEMIN_LANGUE = "langues.messages";
	private static List<ITraduction> traductionListener;

	static {
		langue = Locale.FRENCH;
		resourceBundle = ResourceBundle.getBundle(CHEMIN_LANGUE, langue);
		traductionListener = new ArrayList<>();
	}

	/**
	 * Permet de changer la langue
	 *
	 * @param l la langue cible
	 */
	public static void changerLangue(Langues l) {
		langue = convertirLangue(l);
		resourceBundle = ResourceBundle.getBundle(CHEMIN_LANGUE, langue);
		updateTraduction();
	}

	private static Locale convertirLangue(Langues l) {
		switch (l) {
		case EN:
			return Locale.ENGLISH;
		default:
			return Locale.FRENCH;
		}
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

	/**
	 * Permet d'ajouter la traduction cible
	 * 
	 * @param trad la traduction à ajouter
	 */

	public static void ajouterPane(ITraduction trad) {
		traductionListener.add(trad);
	}

	/**
	 * Permet de passer d'une langue à l'autre
	 * 
	 */

	private static void updateTraduction() {
		for (ITraduction iTraduction : traductionListener) {
			iTraduction.traduire();
		}
	}
}
