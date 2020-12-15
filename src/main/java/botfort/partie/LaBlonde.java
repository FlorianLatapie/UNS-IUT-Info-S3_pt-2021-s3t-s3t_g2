package botfort.partie;

/**
 * <h1>La classe La blonde</h1>. A pour rôle de définir un Personnage.
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
	 * Instantie un nouveau Personnage LaBlonde
	 * 
	 * @param joueur le joueur cible
	 */
	public LaBlonde(Joueur joueur) {
		super(joueur, TypePersonnage.BLONDE);
		super.point = 7;
		super.nbrZretenu = 1;
		super.nbrVoixPourVoter = 1;
	}

	/**
	 * @return le nom du personnage
	 */
	@Override
	public String toString() {
		return "La Blonde";
	}
}