package reseau.ptprotocole;

/**
 * <h1>Permet de stocker les informations du protocol</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class PtProtocole {
	private final String seq;
	private final String nomClasse;
	private final String doc;
	private final String motCle;
	private final String protocol;
	private final PtValeurs ptValeurs;

	/**
	 * @param seq       Numero unique du paquet
	 * @param nomClasse Nom abrégé du packet
	 * @param doc       Documentation globale du paquet
	 * @param motCle    Mot-clé unique par le type de protocol
	 * @param protocol  Nom du protocol
	 * @param ptValeurs Les valeurs du paquet
	 */
	public PtProtocole(String seq, String nomClasse, String doc, String motCle, String protocol, PtValeurs ptValeurs) {
		this.seq = seq;
		this.nomClasse = nomClasse;
		this.doc = doc;
		this.motCle = motCle;
		this.protocol = protocol;
		this.ptValeurs = ptValeurs;
	}

	/**
	 * Permet d'obtenir le nombre de parametre.
	 *
	 * @return Le nombre de parametre
	 */
	public int taille() {
		return ptValeurs.getValeurs().size();
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
		return nomClasse;
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
		return motCle;
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
	public PtValeurs getValeurs() {
		return ptValeurs;
	}

	/**
	 * Obtient les noms des parametres.
	 *
	 * @return Les noms des parametres
	 */
	public String[] toNoms() {
		String[] tmp = new String[ptValeurs.getValeurs().size()];
		for (int i = 0; i < tmp.length; i++)
			tmp[i] = ptValeurs.getValeurs().get(i).getNom();

		return tmp;
	}

	/**
	 * Obtient les types des parametres.
	 *
	 * @return Les types des parametres
	 */
	public String[] toTypes() {
		String[] tmp = new String[ptValeurs.getValeurs().size()];
		for (int i = 0; i < tmp.length; i++)
			tmp[i] = ptValeurs.getValeurs().get(i).getType();

		return tmp;
	}

	/**
	 * Obtient les documentations des parametres.
	 *
	 * @return Les documentations des parametres
	 */
	public String[] toDocs() {
		String[] tmp = new String[ptValeurs.getValeurs().size()];
		for (int i = 0; i < tmp.length; i++)
			tmp[i] = ptValeurs.getValeurs().get(i).getDoc();

		return tmp;
	}

	@Override
	public String toString() {
		return "[Seq: " + seq + " ClassName: " + nomClasse + " Doc: " + doc + " Keyword: " + motCle + " Protocol: "
				+ protocol + " Values: " + ptValeurs.toString() + "]";
	}
}
