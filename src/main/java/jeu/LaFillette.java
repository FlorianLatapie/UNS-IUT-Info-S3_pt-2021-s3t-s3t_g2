package jeu;

/**
 * The Class laFillette.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 * @category personnage
 */
public class LaFillette extends Personnage {

	public LaFillette(Joueur joueur) {
		super(joueur, TypePersonnage.FILLETTE);
		super.point=1;
		super.nbrZretenu=1;
		super.nbrVoixPourVoter=1;
	}

	@Override
	public String toString() {
		return "La Fillette";
	}
	
	
}
