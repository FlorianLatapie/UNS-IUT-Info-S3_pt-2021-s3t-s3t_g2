package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe La brute</h1>. A pour rôle de définir un Personnage.
 *
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 0.1
 * @since 04/10/2020
 */
public class LaBrute extends Personnage {

	/**
	 * Instantie un nouveau Personnage LaBrute
	 * 
	 * @param joueur le joueur cible
	 */
	public LaBrute(Joueur joueur) {
		super(joueur, TypePersonnage.BRUTE);
		super.point = 5;
		super.nbrZretenu = 2;
	}

	/**
	 * @return le String La Brute
	 */
	@Override
	public String toString() {
		return "La Brute";
	}

}
