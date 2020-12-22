package pp.ihm.event;

public abstract class EvenementStockage {
	private static boolean popup_Accepter = false;

	public static boolean isPopupAccepter() {
		return popup_Accepter;
	}

	public static void setPopupAccepter(boolean popupAccepter) {
		popup_Accepter = popupAccepter;
	}
}
