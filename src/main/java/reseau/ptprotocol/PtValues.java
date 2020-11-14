package reseau.ptprotocol;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1> Permet de stocker un ensemble de paquets </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PtValues {
    private final List<PtValue> values = new ArrayList<>();

    /**
     * Permet d'ajouter des valeurs.
     *
     * @param value Valeur a rajouter
     */
    public void ajouterValeur(PtValue value) {
        values.add(value);
    }

    /**
     * Obtient les valeurs du paquet.
     *
     * @return Les valeurs du paquet
     */
    public List<PtValue> getValeurs() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (PtValue v : values)
            tmp.append(v.toString());

        return "[Values: " + tmp + "]";
    }
}
