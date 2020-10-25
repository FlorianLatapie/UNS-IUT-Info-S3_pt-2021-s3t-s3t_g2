package jeu;

/**
 * <h1> Le personnage "La blonde" </h1>
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
