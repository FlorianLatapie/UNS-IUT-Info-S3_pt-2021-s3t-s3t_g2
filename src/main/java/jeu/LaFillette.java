package jeu;

/**
 * <h1>Le personnage "La fillette"</h1>
 *
 * @version 0.1
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @since 04/10/2020
 */
public class LaFillette extends Personnage {

	/**
	 * @param joueur le joueur cible
	 */
	public LaFillette(Joueur joueur) {
		super(joueur, TypePersonnage.FILLETTE);
		super.point = 1;
		super.nbrZretenu = 1;
		super.nbrVoixPourVoter = 1;
	}

	/**
	 * @return le nom du personnage
	 */
	@Override
	public String toString() {
		return "La Fillette";
	}

}
