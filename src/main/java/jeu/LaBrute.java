package jeu;

/**
 * The Class LaBrute.
 * @authors Leo Theo Yanis Kevin Vincent
 * @version 0.1
 * @since 04/10/2020
 * @category personnage
 */
public class LaBrute extends Personnage {

	/**
	 * Instancie un un personnage de type LaBrute
	 * @param Joueur         joueur
	 */

	public LaBrute(Joueur joueur) {
		super(joueur, TypePersonnage.BRUTE);
		super.point=5;
		super.nbrZretenu=2;
		super.nbrVoixPourVoter=1;

	}
	/**
	 * Renvoie une chaine de caractère
	 */
	@Override
	public String toString() {
		return "La Brute";
	}
	
}
