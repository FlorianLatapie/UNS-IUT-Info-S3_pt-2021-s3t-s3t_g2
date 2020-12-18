package idjr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import idjr.ihmidjr.langues.International;
import reseau.type.Couleur;
import reseau.type.PionCouleur;

public abstract class IdjrTools {
	private IdjrTools() {

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

	public static int getPionByValue(PionCouleur pc) {
		String tmp = "" + pc.name().charAt(1);

		return Integer.parseInt(tmp);
	}

	public static List<Integer> getPionsByValues(List<PionCouleur> pc) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (PionCouleur p : pc)
			tmp.add(getPionByValue(p));

		return tmp;
	}

	public static List<String> getLieuxByIndex(List<Integer> lieux) {
		HashMap<Integer, String> listeLieu = new HashMap<Integer, String>();
		listeLieu.put(1, International.trad("texte.lieu1"));
		listeLieu.put(2, International.trad("texte.lieu2"));
		listeLieu.put(3, International.trad("texte.lieu3"));
		listeLieu.put(4, International.trad("texte.lieu4"));
		listeLieu.put(5, International.trad("texte.lieu5") + " " + International.trad("texte.lieu5b"));
		listeLieu.put(6, International.trad("texte.lieu6"));

		List<String> list = new ArrayList<>();
		for (int i : lieux)
			list.add(listeLieu.get(i));

		return list;
	}
	
	public static String getLieuByIndex(Integer idLieu) {
		HashMap<Integer, String> listeLieu = new HashMap<Integer, String>();
		listeLieu.put(1, "Toilettes");
		listeLieu.put(2, "Cachou");
		listeLieu.put(3, "Megatoys");
		listeLieu.put(4, "Parking");
		listeLieu.put(5, "PC de securite");
		listeLieu.put(6, "Supermarche");
		return listeLieu.get(idLieu);
	}
	
	public static String getPionByIndex(Integer pionValue) {
		HashMap<Integer, String> listePion = new HashMap<Integer, String>();
		listePion.put(1, "Fillette");
		listePion.put(3, "Truand");
		listePion.put(5, "Brute");
		listePion.put(7, "Blonde");
		return listePion.get(pionValue);
	}

	public static String getVote() {
		// TODO Auto-generated method stub
		return null;
	}
}
