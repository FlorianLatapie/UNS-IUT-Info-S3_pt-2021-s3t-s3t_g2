import jeu.ihm.Core;
import jeu.ihm.InterfacePrincipale;

/**
 * Lance l'application et son interface graphique 
 * 
 * @author Florian
 * @version 0
 * @since 10/10/2020
 * @category (default package)
 */
public class AppPP {
	public static void main(String[] args) {
		Core c = new Core(); 
		InterfacePrincipale.lancement(args,c);
	}
}
