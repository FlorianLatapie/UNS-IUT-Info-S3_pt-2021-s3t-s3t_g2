package idjr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import idjr.ihmidjr.langues.International;
import reseau.type.Couleur;
import reseau.type.PionCouleur;
import reseau.type.Statut;

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
		listeLieu.put(1, International.trad("texte.lieu1"));
		listeLieu.put(2, International.trad("texte.lieu2"));
		listeLieu.put(3, International.trad("texte.lieu3"));
		listeLieu.put(4, International.trad("texte.lieu4"));
		listeLieu.put(5, International.trad("texte.lieu5") + " " + International.trad("texte.lieu5b"));
		listeLieu.put(6, International.trad("texte.lieu6"));
		return listeLieu.get(idLieu);
	}

	public static String getPionByIndex(Integer pionValue) {
		HashMap<Integer, String> listePion = new HashMap<Integer, String>();
		listePion.put(1, International.trad("texte.Fillette"));
		listePion.put(3, International.trad("texte.Truand"));
		listePion.put(5, International.trad("texte.Brute"));
		listePion.put(7, International.trad("texte.Blonde"));
		return listePion.get(pionValue);
	}

	public static String getStatutTrad(Statut statut) {
		switch (statut) {
		case ANNULEE:
			return International.trad("text.statut.annulee");
		case ATTENTE:
			return International.trad("text.statut.attente");
		case COMPLETE:
			return International.trad("text.statut.complete");
		case TERMINEE:
			return International.trad("text.statut.terminee");

		default:
			return "";
		}
	}
}
