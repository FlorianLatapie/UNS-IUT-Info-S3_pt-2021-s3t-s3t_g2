package reseau.tool;

import org.junit.jupiter.api.Test;
import reseau.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PtOutilsTest {

	@Test
	void convertParamToString() {
		assertEquals("Test", PtOutils.convertParamToString("Test", String.class.getSimpleName()));
		assertNotEquals("Test", PtOutils.convertParamToString("Tes", String.class.getSimpleName()));

		assertEquals("0", PtOutils.convertParamToString(0, int.class.getSimpleName()));
		assertEquals("1009", PtOutils.convertParamToString(1009, int.class.getSimpleName()));
		assertNotEquals("0", PtOutils.convertParamToString(-2, int.class.getSimpleName()));

		assertEquals("ECD", PtOutils.convertParamToString(VoteType.ECD, VoteType.class.getSimpleName()));
		assertEquals("MPZ", PtOutils.convertParamToString(VoteType.MPZ, VoteType.class.getSimpleName()));
		assertNotEquals("MPL", PtOutils.convertParamToString(VoteType.MPZ, VoteType.class.getSimpleName()));

		assertEquals("PRE", PtOutils.convertParamToString(VoteEtape.PRE, VoteEtape.class.getSimpleName()));
		assertEquals("SEC", PtOutils.convertParamToString(VoteEtape.SEC, VoteEtape.class.getSimpleName()));
		assertNotEquals("MPL", PtOutils.convertParamToString(VoteEtape.PRE, VoteEtape.class.getSimpleName()));

		assertEquals("NE", PtOutils.convertParamToString(VigileEtat.NE, VigileEtat.class.getSimpleName()));
		assertEquals("NUL", PtOutils.convertParamToString(VigileEtat.NUL, VigileEtat.class.getSimpleName()));
		assertNotEquals("NEA", PtOutils.convertParamToString(VigileEtat.NUL, VigileEtat.class.getSimpleName()));

		assertEquals("JR", PtOutils.convertParamToString(TypeJoueur.JR, TypeJoueur.class.getSimpleName()));
		assertEquals("BOT", PtOutils.convertParamToString(TypeJoueur.BOT, TypeJoueur.class.getSimpleName()));
		assertNotEquals("NUL", PtOutils.convertParamToString(TypeJoueur.BOT, TypeJoueur.class.getSimpleName()));

		assertEquals("FORCE", PtOutils.convertParamToString(RaisonType.FORCE, RaisonType.class.getSimpleName()));
		assertEquals("PION", PtOutils.convertParamToString(RaisonType.PION, RaisonType.class.getSimpleName()));
		assertNotEquals("NUL", PtOutils.convertParamToString(RaisonType.ZOMBIE, RaisonType.class.getSimpleName()));

		assertEquals("B1", PtOutils.convertParamToString(PionCouleur.B1, PionCouleur.class.getSimpleName()));
		assertEquals("J7", PtOutils.convertParamToString(PionCouleur.J7, PionCouleur.class.getSimpleName()));
		assertNotEquals("HJ", PtOutils.convertParamToString(PionCouleur.B3, PionCouleur.class.getSimpleName()));

		assertEquals("N", PtOutils.convertParamToString(Couleur.N, Couleur.class.getSimpleName()));
		assertEquals("M", PtOutils.convertParamToString(Couleur.M, Couleur.class.getSimpleName()));
		assertEquals("NUL", PtOutils.convertParamToString(Couleur.NUL, Couleur.class.getSimpleName()));
		assertNotEquals("J", PtOutils.convertParamToString(Couleur.V, Couleur.class.getSimpleName()));

		assertEquals("LIEUX", PtOutils.convertParamToString(CondType.LIEUX, CondType.class.getSimpleName()));
		assertEquals("PION", PtOutils.convertParamToString(CondType.PION, CondType.class.getSimpleName()));
		assertNotEquals("NON", PtOutils.convertParamToString(CondType.PION, CondType.class.getSimpleName()));

		assertEquals("ARE", PtOutils.convertParamToString(CarteType.ARE, CarteType.class.getSimpleName()));
		assertEquals("CDS", PtOutils.convertParamToString(CarteType.CDS, CarteType.class.getSimpleName()));
		assertNotEquals("OUI", PtOutils.convertParamToString(CarteType.MAT, CarteType.class.getSimpleName()));

		assertEquals("JRU", PtOutils.convertParamToString(TypePartie.JRU, TypePartie.class.getSimpleName()));
		assertEquals("BOTU", PtOutils.convertParamToString(TypePartie.BOTU, TypePartie.class.getSimpleName()));
		assertNotEquals("OUI", PtOutils.convertParamToString(TypePartie.MIXTE, TypePartie.class.getSimpleName()));

		assertEquals("ANNULEE", PtOutils.convertParamToString(Statut.ANNULEE, Statut.class.getSimpleName()));
		assertEquals("ATTENTE", PtOutils.convertParamToString(Statut.ATTENTE, Statut.class.getSimpleName()));
		assertNotEquals("Ah", PtOutils.convertParamToString(Statut.COMPLETE, Statut.class.getSimpleName()));

		assertEquals("CD", PtOutils.convertParamToString(CarteEtat.CD, CarteEtat.class.getSimpleName()));
		assertEquals("NUL", PtOutils.convertParamToString(CarteEtat.NUL, CarteEtat.class.getSimpleName()));
		assertNotEquals("NUL", PtOutils.convertParamToString(CarteEtat.CD, CarteEtat.class.getSimpleName()));

		HashMap<Integer, List<Integer>> actual = new HashMap<>();
		List<Integer> blonde = new ArrayList<>();
		blonde.add(5);
		actual.put(7, blonde);
		List<Integer> brute = new ArrayList<>();
		brute.add(2);
		actual.put(5, brute);
		List<Integer> truand = new ArrayList<>();
		truand.add(3);
		truand.add(5);
		actual.put(3, truand);
		List<Integer> filette = new ArrayList<>();
		filette.add(-1);
		actual.put(1, filette);
		String expected = "BLONDE:5;BRUTE:2;FILETTE:-1;TRUAND:3,5";
		// Assertions.assertEquals(expected, PtTool.convertParamToString(actual,
		// "HashMap<Integer,List<Integer>>"));
		assertNotEquals("az", PtOutils.convertParamToString(actual, "HashMap<Integer,List<Integer>>"));

		List<CarteType> carteTypes = new ArrayList<>();
		carteTypes.add(CarteType.ABA);
		carteTypes.add(CarteType.ACS);
		expected = "ABA,ACS";
		assertEquals(expected, PtOutils.convertParamToString(carteTypes, "List<CarteType>"));
		assertNotEquals("az", PtOutils.convertParamToString(carteTypes, "List<CarteType>"));

		List<Couleur> couleurs = new ArrayList<>();
		couleurs.add(Couleur.R);
		couleurs.add(Couleur.N);
		expected = "R,N";
		assertEquals(expected, PtOutils.convertParamToString(couleurs, "List<Couleur>"));
		assertNotEquals("az", PtOutils.convertParamToString(couleurs, "List<Couleur>"));

		List<PionCouleur> pionCouleurs = new ArrayList<>();
		pionCouleurs.add(PionCouleur.B7);
		pionCouleurs.add(PionCouleur.M3);
		expected = "B7,M3";
		assertEquals(expected, PtOutils.convertParamToString(pionCouleurs, "List<PionCouleur>"));
		assertNotEquals("az", PtOutils.convertParamToString(pionCouleurs, "List<PionCouleur>"));

		List<Integer> integers = new ArrayList<>();
		integers.add(5);
		integers.add(0);
		expected = "5,0";
		assertEquals(expected, PtOutils.convertParamToString(integers, "List<Integer>"));
		assertNotEquals("az", PtOutils.convertParamToString(integers, "List<Integer>"));

		List<String> strings = new ArrayList<>();
		strings.add("5");
		strings.add("Lol");
		expected = "5,Lol";
		assertEquals(expected, PtOutils.convertParamToString(strings, "List<String>"));
		assertNotEquals("az", PtOutils.convertParamToString(strings, "List<String>"));

		assertNull(PtOutils.convertParamToString(strings, Character.class.getSimpleName()));
		assertNull(PtOutils.convertParamToString(strings, "Oui"));
	}

	@Test
	void convertStringToObject() {
		assertEquals("Test", PtOutils.convertStringToObject("Test", String.class.getSimpleName()));
		assertNotEquals("Test", PtOutils.convertStringToObject("Tes", String.class.getSimpleName()));

		assertEquals(0, PtOutils.convertStringToObject("0", int.class.getSimpleName()));
		assertEquals(1009, PtOutils.convertStringToObject("1009", int.class.getSimpleName()));
		assertNotEquals(0, PtOutils.convertStringToObject("-2", int.class.getSimpleName()));

		assertEquals(VoteType.ECD, PtOutils.convertStringToObject("ECD", VoteType.class.getSimpleName()));
		assertEquals(VoteType.MPZ, PtOutils.convertStringToObject("MPZ", VoteType.class.getSimpleName()));
		assertNotEquals(VoteType.FDC, PtOutils.convertStringToObject("MPZ", VoteType.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", VoteType.class.getSimpleName()));

		assertEquals(VoteEtape.PRE, PtOutils.convertStringToObject("PRE", VoteEtape.class.getSimpleName()));
		assertEquals(VoteEtape.SEC, PtOutils.convertStringToObject("SEC", VoteEtape.class.getSimpleName()));
		assertNotEquals(VoteEtape.SEC, PtOutils.convertStringToObject("PRE", VoteEtape.class.getSimpleName()));

		assertNull(PtOutils.convertStringToObject("MPZPM", VoteEtape.class.getSimpleName()));

		assertEquals(VigileEtat.NE, PtOutils.convertStringToObject("NE", VigileEtat.class.getSimpleName()));
		assertEquals(VigileEtat.NUL, PtOutils.convertStringToObject("NUL", VigileEtat.class.getSimpleName()));
		assertNotEquals(VigileEtat.NE, PtOutils.convertStringToObject("NUL", VigileEtat.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", VigileEtat.class.getSimpleName()));

		assertEquals(TypeJoueur.JR, PtOutils.convertStringToObject("JR", TypeJoueur.class.getSimpleName()));
		assertEquals(TypeJoueur.BOT, PtOutils.convertStringToObject("BOT", TypeJoueur.class.getSimpleName()));
		assertNotEquals(TypeJoueur.JR, PtOutils.convertStringToObject("BOT", TypeJoueur.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", TypeJoueur.class.getSimpleName()));

		assertEquals(RaisonType.FORCE, PtOutils.convertStringToObject("FORCE", RaisonType.class.getSimpleName()));
		assertEquals(RaisonType.PION, PtOutils.convertStringToObject("PION", RaisonType.class.getSimpleName()));
		assertNotEquals(RaisonType.PION, PtOutils.convertStringToObject("ZOMBIE", RaisonType.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", RaisonType.class.getSimpleName()));

		assertEquals(PionCouleur.B5, PtOutils.convertStringToObject("B5", PionCouleur.class.getSimpleName()));
		assertEquals(PionCouleur.M5, PtOutils.convertStringToObject("M5", PionCouleur.class.getSimpleName()));
		assertNotEquals(PionCouleur.B3, PtOutils.convertStringToObject("BF", PionCouleur.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", PionCouleur.class.getSimpleName()));

		assertEquals(Couleur.N, PtOutils.convertStringToObject("N", Couleur.class.getSimpleName()));
		assertEquals(Couleur.M, PtOutils.convertStringToObject("M", Couleur.class.getSimpleName()));
		assertNotEquals(Couleur.J, PtOutils.convertStringToObject("V", Couleur.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", Couleur.class.getSimpleName()));

		assertEquals(CondType.LIEUX, PtOutils.convertStringToObject("LIEUX", CondType.class.getSimpleName()));
		assertEquals(CondType.PION, PtOutils.convertStringToObject("PION", CondType.class.getSimpleName()));
		assertNotEquals(CondType.LIEUX, PtOutils.convertStringToObject("PION", CondType.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", CondType.class.getSimpleName()));

		assertEquals(CarteType.ARE, PtOutils.convertStringToObject("ARE", CarteType.class.getSimpleName()));
		assertEquals(CarteType.CDS, PtOutils.convertStringToObject("CDS", CarteType.class.getSimpleName()));
		assertNotEquals(CarteType.ACS, PtOutils.convertStringToObject("MAT", CarteType.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", CarteType.class.getSimpleName()));

		assertEquals(TypePartie.JRU, PtOutils.convertStringToObject("JRU", TypePartie.class.getSimpleName()));
		assertEquals(TypePartie.BOTU, PtOutils.convertStringToObject("BOTU", TypePartie.class.getSimpleName()));
		assertNotEquals(TypePartie.BOTU, PtOutils.convertStringToObject("MIXTE", TypePartie.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", TypePartie.class.getSimpleName()));

		assertEquals(Statut.ANNULEE, PtOutils.convertStringToObject("ANNULEE", Statut.class.getSimpleName()));
		assertEquals(Statut.ATTENTE, PtOutils.convertStringToObject("ATTENTE", Statut.class.getSimpleName()));
		assertNotEquals(Statut.ATTENTE, PtOutils.convertStringToObject("COMPLETE", Statut.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", Statut.class.getSimpleName()));

		assertEquals(CarteEtat.CD, PtOutils.convertStringToObject("CD", CarteEtat.class.getSimpleName()));
		assertEquals(CarteEtat.NUL, PtOutils.convertStringToObject("NUL", CarteEtat.class.getSimpleName()));
		assertNotEquals(CarteEtat.NUL, PtOutils.convertStringToObject("CD", CarteEtat.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject("MPZPM", CarteEtat.class.getSimpleName()));

		HashMap<Integer, List<Integer>> actual = new HashMap<>();
		List<Integer> blonde = new ArrayList<>();
		blonde.add(5);
		actual.put(7, blonde);
		List<Integer> brute = new ArrayList<>();
		brute.add(2);
		actual.put(5, brute);
		List<Integer> truand = new ArrayList<>();
		truand.add(3);
		truand.add(5);
		actual.put(3, truand);
		List<Integer> filette = new ArrayList<>();
		filette.add(-1);
		actual.put(1, filette);
		String expected = "BLONDE:5;BRUTE:2;FILETTE:-1;TRUAND:3,5";
		// Assertions.assertEquals(actual, PtTool.convertStringToObject(expected,
		// "HashMap<Integer,List<Integer>>"));
		// assertNotEquals(actual, PtTool.convertStringToObject(expected,
		// "HashMap<Integer,List<Integer>>"));

		List<CarteType> carteTypes = new ArrayList<>();
		carteTypes.add(CarteType.ABA);
		carteTypes.add(CarteType.ACS);
		expected = "ABA,ACS";
		assertEquals(carteTypes, PtOutils.convertStringToObject(expected, "List<CarteType>"));
		assertNotEquals(carteTypes, PtOutils.convertStringToObject("az", "List<CarteType>"));

		List<Couleur> couleurs = new ArrayList<>();
		couleurs.add(Couleur.R);
		couleurs.add(Couleur.N);
		expected = "R,N";
		assertEquals(couleurs, PtOutils.convertStringToObject(expected, "List<Couleur>"));
		assertNotEquals(couleurs, PtOutils.convertStringToObject("azza", "List<Couleur>"));

		List<PionCouleur> pionCouleurs = new ArrayList<>();
		pionCouleurs.add(PionCouleur.N5);
		pionCouleurs.add(PionCouleur.J3);
		expected = "N5,J3";
		assertEquals(pionCouleurs, PtOutils.convertStringToObject(expected, "List<PionCouleur>"));
		assertNotEquals(pionCouleurs, PtOutils.convertStringToObject("azza", "List<PionCouleur>"));

		List<Integer> integers = new ArrayList<>();
		integers.add(5);
		integers.add(0);
		expected = "5,0";
		assertEquals(expected, PtOutils.convertParamToString(integers, "List<Integer>"));
		assertNotEquals("az", PtOutils.convertParamToString(integers, "List<Integer>"));

		List<String> strings = new ArrayList<>();
		strings.add("Euh");
		strings.add("Oui");
		expected = "Euh,Oui";
		assertEquals(expected, PtOutils.convertParamToString(strings, "List<String>"));
		assertNotEquals("az", PtOutils.convertParamToString(strings, "List<String>"));

		assertNull(PtOutils.convertStringToObject("Lol", Character.class.getSimpleName()));
		assertNull(PtOutils.convertStringToObject(expected, "Oui"));
	}
}