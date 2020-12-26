package pp.ihm.event;

public abstract class EvenementStockage {
	private static boolean popup_Accepter = false;
	private static boolean desactiver = false;

	public static boolean isPopupAccepter() {
		return desactiver ? true : popup_Accepter;
	}

	public static void setPopupAccepter(boolean popupAccepter) {
		popup_Accepter = popupAccepter;
	}

	public static void desactiverEvent(boolean etat) {
		desactiver = etat;
	}
}
