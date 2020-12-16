package reseau.socket;

import reseau.paquet.Paquet;

/**
 * <h1>Patron pour un traitement de paquet les paquet</h1>
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public abstract class TraitementPaquet<T> {
	public abstract void init();

	public abstract void set(Object core);

	public abstract void traitement(Paquet packet, String message, T extra);

}