package pp.ihm;

import javafx.scene.control.ComboBox;
import reseau.type.Couleur;

import java.util.ArrayList;
import java.util.List;

public abstract class IhmTools {
	/**
	 * si toutes les couleurs choisies sont différentes cela renvoie vrai 
	 * @param max nombre total de couleurs 
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
	 * @param max nombre total de couleurs 
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

		//a-z A-Z À-ÿ  -  0-9  '-' _-_
		int[][] intervalle = { { 65, 90 }, { 97, 122 }, { 192, 255 }, { 32, 32 }, { 48, 57 }, { 39, 39 }, { 95, 95 } };
		int[] exclure = { 215, 247 };

		boolean result = true;
		for (int i = 0; i < nom.length(); i++) {
			boolean tmp = false;
			for (int j = 0; j < intervalle.length; j++) {
				if (intervalle[j][0] <= (int) nom.charAt(i) && intervalle[j][1] >= (int) nom.charAt(i)) {
					boolean t = true;
					for (int u : exclure) {
						if (u== (int) nom.charAt(i))
							t &= false;
					}
					tmp |= true && t;
				}	
			}
			result &= tmp;
		}

		return result;
	}
}
