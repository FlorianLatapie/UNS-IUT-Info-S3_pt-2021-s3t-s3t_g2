package pp;

/**
 * <h1>La classe La fillette</h1>. A pour rôle de définir un Personnage.
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
	 * Instantie un nouveau Personnage LaFillette
	 * 
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
