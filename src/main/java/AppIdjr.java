import idjr.ihmidjr.Core;
import idjr.ihmidjr.InterfacePrincipale;

/**
 * Lance l'application et son interface graphique 
 * 
 * @author Florian
 * @version 0
 * @since 10/10/2020
 * @category (default package)
 */
public class AppIdjr {
	public static void main(String[] args) {
		Core c = new Core(); 
		InterfacePrincipale.lancement(args,c);
	}
}