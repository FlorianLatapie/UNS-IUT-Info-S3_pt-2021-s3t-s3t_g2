package idjr;

import java.util.List;

import reseau.type.Couleur;

public abstract class IdjrTools {
    public static Couleur getCouleurByName(String n, List<String> noms, List<Couleur> couleurs) {
        for (int i = 0; i < noms.size(); i++) {
            System.out.println(noms.get(i) + " =? " + n);
            if (noms.get(i).equals(n))
                return couleurs.get(i);
        }

        return Couleur.NUL;
    }
}
