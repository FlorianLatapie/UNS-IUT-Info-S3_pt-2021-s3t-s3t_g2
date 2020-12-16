package pp.ihm;

import javafx.scene.control.ComboBox;
import reseau.type.Couleur;
import reseau.type.TypePersonnage;

import java.util.ArrayList;
import java.util.List;

public interface IhmOutils {
	String vert = " -fx-background-color:#5EB137; -fx-text-fill: #000000";
	String rouge = " -fx-background-color:#F30101; -fx-text-fill: #000000";
	String marron = " -fx-background-color:#6C3505; -fx-text-fill: #000000";
	String jaune = " -fx-background-color:#E9B902; -fx-text-fill: #000000";
	String bleu = " -fx-background-color:#008CDA; -fx-text-fill: #ffffff";
	String noir = " -fx-background-color:#000000; -fx-text-fill: #ffffff";

	/**
	 * si toutes les couleurs choisies sont différentes cela renvoie vrai
	 * 
	 * @param max      nombre total de couleurs
	 * @param couleurs couleurs
	 * @return l'unicité de chaque couleur
	 */
	public static boolean isAllUniqueColor(int max, ComboBox<String>... couleurs) {
		for (int i = 0; i < max; i++)
			for (int y = 0; y < max; y++)
				if (i != y)
					if (couleurs[i].getValue() == couleurs[y].getValue())
						return false;

		return true;
	}

	/**
	 * convertis une combobox en liste de couleur
	 * 
	 * @param max      nombre total de couleurs
	 * @param couleurs couleurs
	 * @return une liste de couleur
	 */
	public static List<Couleur> comboStringToColorList(int max, ComboBox<String>... couleurs) {
		List<Couleur> cs = new ArrayList<>();
		for (int i = 0; i < max; i++)
			cs.add(Couleur.valueOf(String.valueOf(couleurs[i].getValue().charAt(0)).toUpperCase()));

		return cs;
	}

	static boolean nomEstValide(String nom) {
		if (nom == null)
			return false;
		if (nom.isEmpty())
			return false;
		if (nom.isBlank())
			return false;
		if (nom.length() > 32)
			return false;
		if (nom.startsWith(" "))
			return false;
		if (nom.endsWith(" "))
			return false;

		// a-z A-Z À-ÿ - 0-9 '-' _-_
		int[][] intervalle = { { 65, 90 }, { 97, 122 }, { 192, 255 }, { 32, 32 }, { 48, 57 }, { 39, 39 }, { 95, 95 } };
		int[] exclure = { 215, 247 };

		boolean result = true;
		for (int i = 0; i < nom.length(); i++) {
			boolean tmp = false;
			for (int j = 0; j < intervalle.length; j++) {
				if (intervalle[j][0] <= (int) nom.charAt(i) && intervalle[j][1] >= (int) nom.charAt(i)) {
					boolean t = true;
					for (int u : exclure) {
						if (u == (int) nom.charAt(i))
							t &= false;
					}
					tmp |= true && t;
				}
			}
			result &= tmp;
		}

		return result;
	}

	public static String color(Couleur couleur) {
		switch (couleur) {
		case B:
			return bleu;
		case R:
			return rouge;
		case V:
			return vert;
		case N:
			return noir;
		case J:
			return jaune;
		case M:
			return marron;
		default:
			throw new IllegalArgumentException("Couleur inconnue");
		}
	}
	
	public static String convertVersImagePerso(Couleur couleur, TypePersonnage type) {
		switch (type) {
		case BLONDE:
			switch (couleur) {
			case M:
				return DataControl.BLONDE_M;
			case N:
				return DataControl.BLONDE_N;
			case V:
				return DataControl.BLONDE_V;
			case R:
				return DataControl.BLONDE_R;
			case J:
				return DataControl.BLONDE_J;
			case B:
				return DataControl.BLONDE_B;
			default:
				break;
			}
		case BRUTE:
			switch (couleur) {
			case M:
				return DataControl.BRUTE_M;
			case N:
				return DataControl.BRUTE_N;
			case V:
				return DataControl.BRUTE_V;
			case R:
				return DataControl.BRUTE_R;
			case J:
				return DataControl.BRUTE_J;
			case B:
				return DataControl.BRUTE_B;
			default:
				break;
			}
		case TRUAND:
			switch (couleur) {
			case M:
				return DataControl.TRUAND_M;
			case N:
				return DataControl.TRUAND_N;
			case V:
				return DataControl.TRUAND_V;
			case R:
				return DataControl.TRUAND_R;
			case J:
				return DataControl.TRUAND_J;
			case B:
				return DataControl.TRUAND_B;
			default:
				break;
			}
		case FILLETTE:
			switch (couleur) {
			case R:
				return DataControl.FILLETTE_R;
			case J:
				return DataControl.FILLETTE_J;
			case B:
				return DataControl.FILLETTE_B;
			case M:
				return DataControl.FILLETTE_M;
			case N:
				return DataControl.FILLETTE_N;
			case V:
				return DataControl.FILLETTE_V;
			default:
				break;
			}
		}
		return DataControl.NOM_COULEUR;
	}
}
