package jeu;

/**
 * The Class laBrute.
 * @author Alex
 * @version 0
 * @since 04/10/2020
 * @category personnage
 */
public class LaBrute extends Personnage {

	public LaBrute(Joueur joueur) {
		super(joueur, TypePersonnage.BRUTE);
		super.point=5;
		super.nbrZretenu=2;
		super.nbrVoixPourVoter=1;

	}

	@Override
	public String toSrting() {
		return "La Brute";
	}
	
}
