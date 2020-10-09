package jeu;

/**
 * The Class laBlonde.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 * @category personnage
 */
public class LaBlonde extends Personnage {
	
	public LaBlonde(Joueur joueur) {
		super(joueur, TypePersonnage.BLONDE);
		super.point=7;
		super.nbrZretenu=1;
		super.nbrVoixPourVoter=1;
	}


}
