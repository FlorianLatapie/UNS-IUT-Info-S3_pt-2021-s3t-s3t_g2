package bot;

import java.util.List;

import reseau.type.Couleur;
import reseau.type.PionCouleur;

public abstract class IdjrTools {
	private IdjrTools(){

    }

    public static Couleur getCouleurByName(String n, List<String> noms, List<Couleur> couleurs) {
        for (int i = 0; i < noms.size(); i++) {
            if (noms.get(i).equals(n))
                return couleurs.get(i);
        }

        return Couleur.NUL;
    }

    public static Couleur getCouleurByChar(PionCouleur pc) {
        for (Couleur c : Couleur.values())
            if (pc.name().charAt(0) == c.name().charAt(0))
                return c;

        return Couleur.NUL;
    }

    public static int getPionByValue(PionCouleur pc) {
        String tmp = "" + pc.name().charAt(1);

        return Integer.parseInt(tmp);
    }
}
