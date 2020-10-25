package jeu;

/**
 * The Class laBlonde.
 * @authors Leo Theo Yanis Kevin Vincent
 * @version 0.1
 * @since 04/10/2020
 * @category personnage
 */
public class LaBlonde extends Personnage {
	
	/**
	 * Instancie un un personnage de type LaBlonde
	 * @param Joueur         joueur
	 */
	public LaBlonde(Joueur joueur) {
		super(joueur, TypePersonnage.BLONDE);
		super.point=7;
		super.nbrZretenu=1;
		super.nbrVoixPourVoter=1;
	}

	/**
	 * Renvoie une chaine de caractère
	 */
	@Override
	public String toString() {
		return "La Blonde";
	}


}
