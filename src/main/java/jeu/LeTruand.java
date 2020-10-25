package jeu;

/**
 * <h1> Le personnage "Le truand" </h1>
 *
 * @version 0.1
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @since 04/10/2020
 */
public class LeTruand extends Personnage {

    /**
     * @param joueur le joueur cible
     */
    public LeTruand(Joueur joueur) {
        super(joueur, TypePersonnage.TRUAND);
        super.point = 3;
        super.nbrZretenu = 1;
        super.nbrVoixPourVoter = 2;
    }

    /**
     * @return le nom du personnage
     */
    @Override
    public String toString() {
        return "Le Truand";
    }

}
