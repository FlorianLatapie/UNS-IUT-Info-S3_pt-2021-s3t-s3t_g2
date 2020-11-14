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
     * @param seq       Numero unique du paquet
     * @param className Nom abrégé du packet
     * @param doc       Documentation globale du paquet
     * @param keyword   Mot-clé unique par le type de protocol
     * @param protocol  Nom du protocol
     * @param values    Les valeurs du paquet
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
     * Permet d'obtenir le nombre de parametre.
     *
     * @return Le nombre de parametre
     */
    public int taille() {
        return values.getValeurs().size();
    }

    /**
     * Obtient le numero unique du paquet.
     *
     * @return Le numero unique du paquet
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Obtient le nom abrégé du paquet.
     *
     * @return Le nom abrégé du paquet
     */
    public String getNomClasse() {
        return className;
    }

    /**
     * Obtient la documentation globale.
     *
     * @return La documentation globale
     */
    public String getDoc() {
        return doc;
    }

    /**
     * Obtient le mot-clé.
     *
     * @return Le mot-clé
     */
    public String getMotCle() {
        return keyword;
    }

    /**
     * Obtient le type du protocol.
     *
     * @return Le type du protocol
     */
    public String getProtocole() {
        return protocol;
    }

    /**
     * Obtient les valeurs du packet.
     *
     * @return Les valeurs du packet
     */
    public PtValues getValeurs() {
        return values;
    }

    /**
     * Obtient les noms des parametres.
     *
     * @return Les noms des parametres
     */
    public String[] toNameArray() {
        String[] tmp = new String[values.getValeurs().size()];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = values.getValeurs().get(i).getName();

        return tmp;
    }

    /**
     * Obtient les types des parametres.
     *
     * @return Les types des parametres
     */
    public String[] toTypeArray() {
        String[] tmp = new String[values.getValeurs().size()];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = values.getValeurs().get(i).getType();

        return tmp;
    }

    /**
     * Obtient les documentations des parametres.
     *
     * @return Les documentations des parametres
     */
    public String[] toDocArray() {
        String[] tmp = new String[values.getValeurs().size()];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = values.getValeurs().get(i).getDoc();

        return tmp;
    }

    @Override
    public String toString() {
        return "[Seq: " + seq + " ClassName: " + className + " Doc: " + doc + " Keyword: " + keyword + " Protocol: " + protocol + " Values: " + values.toString() + "]";
    }
}
