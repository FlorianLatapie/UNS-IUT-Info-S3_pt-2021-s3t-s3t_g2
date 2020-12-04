package reseau.socket;

import reseau.packet.Packet;

/**
 * <h1>Patron pour un traitement de paquet les paquet</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class TraitementPaquet<T> {
	private ControleurReseau controleurReseau;

	public abstract void init(ControleurReseau netWorkManager);

	public abstract void set(Object core);

	public abstract void traitement(Packet packet, String message, T extra);

	public ControleurReseau getControleurReseau() {
		return controleurReseau;
	}

	/**
	 * Permet d'affecter un controleur réseau au traitement de paquet.
	 *
	 * @param controleurReseau Le controleur réseau associé
	 */
	public void setControleurReseau(ControleurReseau controleurReseau) {
		this.controleurReseau = controleurReseau;
	}

}