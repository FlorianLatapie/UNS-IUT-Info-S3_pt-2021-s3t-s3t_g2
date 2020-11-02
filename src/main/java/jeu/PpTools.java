package jeu;

import reseau.type.PionCouleur;

public abstract class PpTools {

    public static int getPionByValue(PionCouleur pc) {
        String tmp = "" + pc.name().charAt(1);

        return Integer.parseInt(tmp);
    }
}
