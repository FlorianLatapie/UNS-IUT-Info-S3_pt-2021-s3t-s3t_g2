package bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import reseau.type.Couleur;
import reseau.type.PionCouleur;

public abstract class BotOutils {
	private BotOutils() {

	}

	public static Couleur getCouleurByName(String n, List<String> noms, List<Couleur> couleurs) {
		for (int i = 0; i < noms.size(); i++) {
			if (noms.get(i).equals(n))
				return couleurs.get(i);
		}

		return Couleur.NUL;
	}

	public static Couleur getCouleurByChar(PionCouleur pc) {
		for (Couleur c : Couleur.values())
			if (pc.name().charAt(0) == c.name().charAt(0))
				return c;

		return Couleur.NUL;
	}

	public static List<Integer> getPionsByValues(List<PionCouleur> pc) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (PionCouleur p : pc)
			tmp.add(getPionByValue(p));

		return tmp;
	}

	public static int getPionByValue(PionCouleur pc) {
		String tmp = "" + pc.name().charAt(1);

		return Integer.parseInt(tmp);
	}

	public static String getNom(BotType botType) {
		String prenoms = "Ressources/Prenoms/prenomsFR.txt";
		Scanner scanneurPrenom;
		String nomDuJoueur = "Bot " + botType.typeString() + " ";
		try {
			scanneurPrenom = new Scanner(new File(prenoms));
			StringBuilder fichierLu = new StringBuilder();
			while (scanneurPrenom.hasNext()) {
				fichierLu.append(scanneurPrenom.nextLine() + "\n");
			}
			String[] tableauPrenom = fichierLu.toString().split("\n");
			int choix = new Random().nextInt(tableauPrenom.length);
			System.out.println(nomDuJoueur + tableauPrenom[choix]);
			nomDuJoueur += tableauPrenom[choix];
			scanneurPrenom.close();
		} catch (FileNotFoundException e) {
			nomDuJoueur += new Random().nextInt(9999);
			System.out.println("Erreur de nom");
		}

		return nomDuJoueur;
	}
}
