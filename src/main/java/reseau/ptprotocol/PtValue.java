package reseau.ptprotocol;

/**
 * <h1> Permet de stocker les parametres des paquets </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PtValue {
    private final String name;
    private final String type;
    private final String doc;

    /**
     * @param name Nom du parametre
     * @param type Type du parametre
     * @param doc  Doc du parametre
     */
    public PtValue(String name, String type, String doc) {
        this.name = name;
        this.type = type;
        this.doc = doc;
    }

    /**
     * Obtient le nom du parametre.
     *
     * @return Le nom du parametre
     */
    public String getName() {
        return name;
    }

    /**
     * Obtient le type du parametre.
     *
     * @return Le type du parametre
     */
    public String getType() {
        return type;
    }

    /**
     * Obtient la documentation du parametre.
     *
     * @return La documentation du parametre
     */
    public String getDoc() {
        return doc;
    }

    @Override
    public String toString() {
        return "[Name: " + name + " \tType: " + type + " \t\tDoc: " + doc + "]";
    }
}
