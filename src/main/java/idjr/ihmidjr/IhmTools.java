package idjr.ihmidjr;

import reseau.type.CarteType;
import reseau.type.Couleur;

public interface IhmTools {
	String vert = " -fx-background-color:#5EB137; -fx-text-fill: #000000";
	String rouge = " -fx-background-color:#F30101; -fx-text-fill: #000000";
	String marron = " -fx-background-color:#6C3505; -fx-text-fill: #000000";
	String jaune = " -fx-background-color:#E9B902; -fx-text-fill: #000000";
	String bleu = " -fx-background-color:#008CDA; -fx-text-fill: #ffffff";
	String noir = " -fx-background-color:#000000; -fx-text-fill: #ffffff";

	/**
	 * Permet de savoir si le nom est conforme
	 * 
	 * @param nom le nom cible
	 * @return si le nom est valide
	 */
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

	/**
	 * Convertit la couleur en style
	 * 
	 * @param couleur la couleur cible
	 * @return le style
	 */
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

	/**
	 * Convertit la carte en chemin (image)
	 * 
	 * @param carteType la carte cible
	 * @return le chemin de l'image
	 */
	public static String convertCarte(CarteType carteType) {
		switch (carteType) {
		case ABA:
			return DataControl.CARTE_BATTE;
		case CAC:
			return DataControl.CARTE_CACHETTE;
		case CDS:
			return DataControl.CARTE_CAMERA;
		case ACS:
			return DataControl.CARTE_CS;
		case AGR:
			return DataControl.CARTE_GRENADE;
		case AHI:
			return DataControl.CARTE_HACHE;
		case MAT:
			return DataControl.CARTE_MATERIEL;
		case MEN:
			return DataControl.CARTE_MENACE;
		case ARE:
			return DataControl.CARTE_REVOLVER;
		case SPR:
			return DataControl.CARTE_SPRINT;
		case ATR:
			return DataControl.CARTE_TRONCENNEUSE;
		}

		return null;
	}

	/**
	 * Covertit la carte chemin d'acces en cartetype
	 * 
	 * @param dataControl le chemin de l'image
	 * @return la carte
	 */
	public static CarteType convertCarte(String dataControl) {
		switch (dataControl) {
		case DataControl.CARTE_BATTE:
			return CarteType.ABA;
		case DataControl.CARTE_CACHETTE:
			return CarteType.CAC;
		case DataControl.CARTE_CAMERA:
			return CarteType.CDS;
		case DataControl.CARTE_CS:
			return CarteType.ACS;
		case DataControl.CARTE_GRENADE:
			return CarteType.AGR;
		case DataControl.CARTE_HACHE:
			return CarteType.AHI;
		case DataControl.CARTE_MATERIEL:
			return CarteType.MAT;
		case DataControl.CARTE_MENACE:
			return CarteType.MEN;
		case DataControl.CARTE_REVOLVER:
			return CarteType.ARE;
		case DataControl.CARTE_SPRINT:
			return CarteType.SPR;
		case DataControl.CARTE_TRONCENNEUSE:
			return CarteType.ATR;
		}

		return CarteType.NUL;
	}
}
