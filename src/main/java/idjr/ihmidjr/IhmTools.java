package idjr.ihmidjr;

public interface IhmTools {

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
