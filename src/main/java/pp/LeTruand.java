package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe Le truand</h1>. A pour rôle de définir un Personnage.
 * 
 * 
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 0.1
 * @since 04/10/2020
 */
public class LeTruand extends Personnage {

	/**
	 * Instantie un nouveau Personnage LeTruand
	 * 
	 * @param joueur le joueur cible
	 */
	public LeTruand(Joueur joueur) {
		super(joueur, TypePersonnage.TRUAND);
		super.point = 3;
		super.nbrZretenu = 1;
	}

	/**
	 * Retourne le nom "Le Truand"
	 * 
	 * @return le nom du personnage
	 */
	@Override
	public String toString() {
		return "Le Truand";
	}

}
