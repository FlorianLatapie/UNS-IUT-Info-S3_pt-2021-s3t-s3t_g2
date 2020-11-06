package reseau.ptprotocol;

/**
 * <h1> Permet de stocker les informations du protocol </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class PtProtocol {
    private final String seq;
    private final String className;
    private final String doc;
    private final String keyword;
    private final String protocol;
    private final PtValues values;

    /**
     * @param seq       numero unique du paquet
     * @param className nom abrégé du packet
     * @param doc       documentation globale du paquet
     * @param keyword   mot-clé unique par le type de protocol
     * @param protocol  nom du protocol
     * @param values    les valeurs du paquet
     */
    public PtProtocol(String seq, String className, String doc, String keyword, String protocol, PtValues values) {
        this.seq = seq;
        this.className = className;
        this.doc = doc;
        this.keyword = keyword;
        this.protocol = protocol;
        this.values = values;
    }

    /**
     * Permet d'obtenir le nombre de parametre
     *
     * @return le nombre de parametre
     */
    public int valueCount() {
        return values.getValues().size();
    }

    /**
     * Obtient le numero unique du paquet
     *
     * @return le numero unique du paquet
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Obtient le nom abrégé du paquet
     *
     * @return le nom abrégé du paquet
     */
    public String getClassName() {
        return className;
    }

    /**
     * Obtient la documentation globale
     *
     * @return la documentation globale
     */
    public String getDoc() {
        return doc;
    }

    /**
     * Obtient le mot-clé
     *
     * @return le mot-clé
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Obtient le type du protocol
     *
     * @return le type du protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Obtient les valeurs du packet
     *
     * @return les valeurs du packet
     */
    public PtValues getValues() {
        return values;
    }

    /**
     * Obtient les noms des parametres
     *
     * @return les noms des parametres
     */
    public String[] toNameArray() {
        String[] tmp = new String[values.getValues().size()];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = values.getValues().get(i).getName();

        return tmp;
    }

    /**
     * Obtient les types des parametres
     *
     * @return les types des parametres
     */
    public String[] toTypeArray() {
        String[] tmp = new String[values.getValues().size()];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = values.getValues().get(i).getType();

        return tmp;
    }

    /**
     * Obtient les documentations des parametres
     *
     * @return les documentations des parametres
     */
    public String[] toDocArray() {
        String[] tmp = new String[values.getValues().size()];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = values.getValues().get(i).getDoc();

        return tmp;
    }

    @Override
    public String toString() {
        return "[Seq: " + seq + " ClassName: " + className + " Doc: " + doc + " Keyword: " + keyword + " Protocol: " + protocol + " Values: " + values.toString() + "]";
    }
}
