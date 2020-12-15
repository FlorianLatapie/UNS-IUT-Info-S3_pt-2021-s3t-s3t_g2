package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe La blonde</h1>.
 * 
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 0.1
 * @since 04/10/2020
 */
public class LaBlonde extends Personnage {

	/**
	 * Instantie une nouvelle blonde.
	 * 
	 * @param joueur Le joueur cible
	 */
	public LaBlonde(Joueur joueur) {
		super(joueur, TypePersonnage.BLONDE);
		super.point = 7;
		super.nbrZretenu = 1;
	}

	/**
	 * Gère l'affichage de la blonde.
	 * 
	 * @return Le nom du personnage
	 */
	@Override
	public String toString() {
		return "La Blonde";
	}
}
