package reseau.ptprotocole;

/**
 * <h1>Permet de stocker les parametres des paquets</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class PtValeur {
	private final String nom;
	private final String type;
	private final String doc;

	/**
	 * @param nom  Nom du parametre
	 * @param type Type du parametre
	 * @param doc  Doc du parametre
	 */
	public PtValeur(String nom, String type, String doc) {
		this.nom = nom;
		this.type = type;
		this.doc = doc;
	}

	/**
	 * Obtient le nom du parametre.
	 *
	 * @return Le nom du parametre
	 */
	public String getNom() {
		return nom;
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
		return "[Name: " + nom + " \tType: " + type + " \t\tDoc: " + doc + "]";
	}
}
