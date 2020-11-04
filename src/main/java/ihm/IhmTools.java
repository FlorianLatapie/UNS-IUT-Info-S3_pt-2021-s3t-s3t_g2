package ihm;

import javafx.scene.control.ComboBox;
import reseau.type.Couleur;

import java.util.ArrayList;
import java.util.List;

public abstract class IhmTools {

    public static boolean isAllUniqueColor(int max, ComboBox<String>... couleurs) {
        for (int i = 0; i < max; i++)
            for (int y = 0; y < max; y++)
                if (i != y)
                    if (couleurs[i].getValue() == couleurs[y].getValue())
                        return false;

        return true;
    }

    public static List<Couleur> comboStringToColorList(int max, ComboBox<String>... couleurs) {
        List<Couleur> cs = new ArrayList<>();
        for (int i = 0; i < max; i++)
            cs.add(Couleur.valueOf(couleurs[i].getValue().toUpperCase()));


        return cs;
    }
}
