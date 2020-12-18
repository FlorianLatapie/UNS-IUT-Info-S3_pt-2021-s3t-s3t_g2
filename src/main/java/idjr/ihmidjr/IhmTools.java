package idjr.ihmidjr;

import reseau.type.Couleur;

public interface IhmTools {
	String vert = " -fx-background-color:#5EB137; -fx-text-fill: #000000";
	String rouge = " -fx-background-color:#F30101; -fx-text-fill: #000000";
	String marron = " -fx-background-color:#6C3505; -fx-text-fill: #000000";
	String jaune = " -fx-background-color:#E9B902; -fx-text-fill: #000000";
	String bleu = " -fx-background-color:#008CDA; -fx-text-fill: #ffffff";
	String noir = " -fx-background-color:#000000; -fx-text-fill: #ffffff";

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
}
