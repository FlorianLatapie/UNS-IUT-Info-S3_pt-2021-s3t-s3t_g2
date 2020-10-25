package jeu;

/**
 * The Class LeTruand.
 * @authors Leo Theo Yanis Kevin Vincent
 * @version 0.1
 * @since 04/10/2020
 * @category personnage
 */
public class LeTruand extends Personnage {

	/**
	 * Instancie un un personnage de type LeTruand
	 * @param Joueur         joueur
	 */
	public LeTruand(Joueur joueur) {
		super(joueur, TypePersonnage.TRUAND);
		super.point=3;
		super.nbrZretenu=1;
		super.nbrVoixPourVoter=2;
	}

	/**
	 * Renvoie une chaine de caractère
	 */
	@Override
	public String toString() {
		return "Le Truand";
	}
	
}
