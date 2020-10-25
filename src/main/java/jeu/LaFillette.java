package jeu;

/**
 * The Class LaFillette.
 * @authors Leo Theo Yanis Kevin Vincent
 * @version 0.1
 * @since 04/10/2020
 * @category personnage
 */
public class LaFillette extends Personnage {

	/**
	 * Instancie un un personnage de type LaFillette
	 * @param Joueur         joueur
	 */
	public LaFillette(Joueur joueur) {
		super(joueur, TypePersonnage.FILLETTE);
		super.point=1;
		super.nbrZretenu=1;
		super.nbrVoixPourVoter=1;
	}
	/**
	 * Renvoie une chaine de caractère
	 */
	@Override
	public String toString() {
		return "La Fillette";
	}
	
	
}
