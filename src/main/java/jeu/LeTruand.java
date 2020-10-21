package jeu;

/**
 * The Class laTruand.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 * @category personnage
 */
public class LeTruand extends Personnage {

	public LeTruand(Joueur joueur) {
		super(joueur, TypePersonnage.TRUAND);
		super.point=3;
		super.nbrZretenu=1;
		super.nbrVoixPourVoter=2;
	}

	@Override
	public String toString() {
		return "Le Truand";
	}
	
}
