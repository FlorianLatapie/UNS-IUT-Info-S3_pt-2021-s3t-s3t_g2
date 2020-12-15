package pp;

import reseau.type.TypePersonnage;

/**
 * <h1>La classe La fillette</h1>.
 * 
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @version 0.1
 * @since 04/10/2020
 */
public class LaFillette extends Personnage {

	/**
	 * Instantie une nouvelle fillette.
	 * 
	 * @param joueur Le joueur cible
	 */
	public LaFillette(Joueur joueur) {
		super(joueur, TypePersonnage.FILLETTE);
		super.point = 1;
		super.nbrZretenu = 1;
	}

	/**
	 * Gère l'affichage de la fillette.
	 * 
	 * @return Le nom du personnage
	 */
	@Override
	public String toString() {
		return "La Fillette";
	}
}
