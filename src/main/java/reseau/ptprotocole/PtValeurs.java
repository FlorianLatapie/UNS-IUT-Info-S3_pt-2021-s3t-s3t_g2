package reseau.ptprotocole;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Permet de stocker un ensemble de paquets</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class PtValeurs {
	private final List<PtValeur> valeurs = new ArrayList<>();

	/**
	 * Permet d'ajouter des valeurs.
	 *
	 * @param value Valeur a rajouter
	 */
	public void ajouterValeur(PtValeur value) {
		valeurs.add(value);
	}

	/**
	 * Obtient les valeurs du paquet.
	 *
	 * @return Les valeurs du paquet
	 */
	public List<PtValeur> getValeurs() {
		return valeurs;
	}

	@Override
	public String toString() {
		StringBuilder tmp = new StringBuilder();
		for (PtValeur v : valeurs)
			tmp.append(v.toString());

		return "[Values: " + tmp + "]";
	}
}
