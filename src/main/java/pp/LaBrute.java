package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe La brute</h1>.
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
	 * Instantie une nouvelle brute.
	 * 
	 * @param joueur Le joueur cible
	 */
	public LaBrute(Joueur joueur) {
		super(joueur, TypePersonnage.BRUTE);
		super.point = 5;
		super.nbrZretenu = 2;
	}

	/**
	 * Gère l'affichage de la brute.
	 * 
	 * @return Le nom du personnage
	 */
	@Override
	public String toString() {
		return "La Brute";
	}

}
