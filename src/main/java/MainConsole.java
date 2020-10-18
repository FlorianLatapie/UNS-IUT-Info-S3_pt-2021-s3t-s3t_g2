import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import controleur.ControleurJeu;
import jeu.Personnage;

public class MainConsole {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		ControleurJeu Controleur = new ControleurJeu();
		
		Scanner sc = new Scanner(System.in);
		
		
		
		
		for (int i = 1; i <= Controleur.getJeu().getJoueurs().size(); i++) {
			ArrayList <Personnage> p = Controleur.getJeu().getJoueurs().get(i).getPersonnages();
			for (int a = 0; a < Controleur.getJeu().getJoueurs().get(i).getPersonnages().size(); a++ ) {
				System.out.println("Lancement des dées.");
				int x = new Random().nextInt(6); 
				int y = new Random().nextInt(6); 
				System.out.println("Résultat du lancement :");
				System.out.println(x + "     "  + Controleur.getJeu().getLieux().get(x));
				System.out.println(y + "     "  + Controleur.getJeu().getLieux().get(y));
				System.out.println("Choisir un numéro:");
				int dest = sc.nextInt();
				while (dest != x && dest != y) {
					System.out.println("Numéro incorrect!\n");
					System.out.println(x + "     "  + Controleur.getJeu().getLieux().get(x));
					System.out.println(y + "     "  + Controleur.getJeu().getLieux().get(y));
					System.out.println("Choisir un numéro:");
					dest = sc.nextInt();
				}
				System.out.println("Choisir un personage a déplacer à " + Controleur.getJeu().getLieux().get(dest));
				ArrayList <Integer> num = new ArrayList<>();
				for (int b = 0; b < Controleur.getJeu().getJoueurs().get(i).getPersonnages().size(); b++ ) {
					num.add(b);
					System.out.println( b + "     "  +  Controleur.getJeu().getJoueurs().get(i).getPersonnages().get(b));
				}
				System.out.println();
				int pers = sc.nextInt();
				while (!num.contains(pers)){
					System.out.println("Numéro incorect !\n");
					System.out.println("Choisir un personage a déplacer à " + Controleur.getJeu().getLieux().get(dest));
					for (int b = 0; b < Controleur.getJeu().getJoueurs().get(i).getPersonnages().size(); b++ ) {
						System.out.println( b + "     "  +  Controleur.getJeu().getJoueurs().get(i).getPersonnages().get(b));
					}
					pers = sc.nextInt();
					System.out.println();
				}
				p.remove(pers);
			}
		}
		
		
		
		
	}

}
