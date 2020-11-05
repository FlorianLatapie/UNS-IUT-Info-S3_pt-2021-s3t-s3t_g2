package jeu;

import reseau.type.PionCouleur;

public abstract class PpTools {

    public static int getPionByValue(PionCouleur pc) {
        String tmp = "" + pc.name().charAt(1);

        return Integer.parseInt(tmp);
    }

    public static int valeurToIndex(int valeur) {
        switch (valeur) {
            case 7:
                return 0;
            case 5:
                return 1;
            case 3:
                return 2;
            case 1:
                return 3;
            default:
                throw new IllegalStateException("Unexpected value: " + valeur);
        }
    }
}
