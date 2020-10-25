package jeu;

/**
 * <h1> Le personnage "La brute" </h1>
 *
 * @version 0.1
 * @author Leo
 * @author Theo
 * @author Yanis
 * @author Kevin
 * @author Vincent
 * @since 04/10/2020
 */
public class LaBrute extends Personnage {

    /**
     * @param joueur le joueur cible
     */
    public LaBrute(Joueur joueur) {
        super(joueur, TypePersonnage.BRUTE);
        super.point = 5;
        super.nbrZretenu = 2;
        super.nbrVoixPourVoter = 1;
    }

    /**
     * @return le nom du personnage
     */
    @Override
    public String toString() {
        return "La Brute";
    }

}
