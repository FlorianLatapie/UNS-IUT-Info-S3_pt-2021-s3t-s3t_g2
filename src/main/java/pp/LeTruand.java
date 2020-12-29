package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe Le truand</h1>.
 * 
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 1
 * @since 04/10/2020
 */
public class LeTruand extends Personnage {

	/**
	 * Instantie un nouveau truand.
	 * 
	 * @param joueur Le joueur cible
	 */
	public LeTruand(Joueur joueur) {
		super(joueur, TypePersonnage.TRUAND);
		super.point = 3;
		super.nbrZretenu = 1;
	}

	/**
	 * Gère l'affichage du truand.
	 * 
	 * @return Le nom du personnage
	 */
	@Override
	public String toString() {
		return "Le Truand";
	}
}
