package reseau.tool;

import org.junit.jupiter.api.Test;
import reseau.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PtToolTest {

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

        assertEquals("FORCE", PtOutils.convertParamToString(ReasonType.FORCE, ReasonType.class.getSimpleName()));
        assertEquals("PION", PtOutils.convertParamToString(ReasonType.PION, ReasonType.class.getSimpleName()));
        assertNotEquals("NUL", PtOutils.convertParamToString(ReasonType.ZOMBIE, ReasonType.class.getSimpleName()));

        assertEquals("B1", PtOutils.convertParamToString(PionCouleur.B1, PionCouleur.class.getSimpleName()));
        assertEquals("J7", PtOutils.convertParamToString(PionCouleur.J7, PionCouleur.class.getSimpleName()));
        assertNotEquals("HJ", PtOutils.convertParamToString(PionCouleur.B3, PionCouleur.class.getSimpleName()));

        assertEquals("NOIR", PtOutils.convertParamToString(Couleur.NOIR, Couleur.class.getSimpleName()));
        assertEquals("MARRON", PtOutils.convertParamToString(Couleur.MARRON, Couleur.class.getSimpleName()));
        assertNotEquals("JAUNE", PtOutils.convertParamToString(Couleur.VERT, Couleur.class.getSimpleName()));

        assertEquals("LIEUX", PtOutils.convertParamToString(CondType.LIEUX, CondType.class.getSimpleName()));
        assertEquals("PION", PtOutils.convertParamToString(CondType.PION, CondType.class.getSimpleName()));
        assertNotEquals("NON", PtOutils.convertParamToString(CondType.PION, CondType.class.getSimpleName()));

        assertEquals("ARE", PtOutils.convertParamToString(CarteType.ARE, CarteType.class.getSimpleName()));
        assertEquals("CDS", PtOutils.convertParamToString(CarteType.CDS, CarteType.class.getSimpleName()));
        assertNotEquals("OUI", PtOutils.convertParamToString(CarteType.MAT, CarteType.class.getSimpleName()));

        assertEquals("JRU", PtOutils.convertParamToString(TypePartie.JRU, TypePartie.class.getSimpleName()));
        assertEquals("BOTU", PtOutils.convertParamToString(TypePartie.BOTU, TypePartie.class.getSimpleName()));
        assertNotEquals("OUI", PtOutils.convertParamToString(TypePartie.MIXTE, TypePartie.class.getSimpleName()));

        assertEquals("ANNULEE", PtOutils.convertParamToString(Status.ANNULEE, Status.class.getSimpleName()));
        assertEquals("ATTENTE", PtOutils.convertParamToString(Status.ATTENTE, Status.class.getSimpleName()));
        assertNotEquals("Ah", PtOutils.convertParamToString(Status.COMPLETE, Status.class.getSimpleName()));

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
        //Assertions.assertEquals(expected, PtTool.convertParamToString(actual, "HashMap<Integer,List<Integer>>"));
        assertNotEquals("az", PtOutils.convertParamToString(actual, "HashMap<Integer,List<Integer>>"));

        List<CarteType> carteTypes = new ArrayList<>();
        carteTypes.add(CarteType.ABA);
        carteTypes.add(CarteType.ACS);
        expected = "ABA,ACS";
        assertEquals(expected, PtOutils.convertParamToString(carteTypes, "List<CarteType>"));
        assertNotEquals("az", PtOutils.convertParamToString(carteTypes, "List<CarteType>"));

        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(Couleur.ROUGE);
        couleurs.add(Couleur.NOIR);
        expected = "ROUGE,NOIR";
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

        assertEquals(ReasonType.FORCE, PtOutils.convertStringToObject("FORCE", ReasonType.class.getSimpleName()));
        assertEquals(ReasonType.PION, PtOutils.convertStringToObject("PION", ReasonType.class.getSimpleName()));
        assertNotEquals(ReasonType.PION, PtOutils.convertStringToObject("ZOMBIE", ReasonType.class.getSimpleName()));
        assertNull(PtOutils.convertStringToObject("MPZPM", ReasonType.class.getSimpleName()));

        assertEquals(PionCouleur.B5, PtOutils.convertStringToObject("B5", PionCouleur.class.getSimpleName()));
        assertEquals(PionCouleur.M5, PtOutils.convertStringToObject("M5", PionCouleur.class.getSimpleName()));
        assertNotEquals(PionCouleur.B3, PtOutils.convertStringToObject("BF", PionCouleur.class.getSimpleName()));
        assertNull(PtOutils.convertStringToObject("MPZPM", PionCouleur.class.getSimpleName()));

        assertEquals(Couleur.NOIR, PtOutils.convertStringToObject("NOIR", Couleur.class.getSimpleName()));
        assertEquals(Couleur.MARRON, PtOutils.convertStringToObject("MARRON", Couleur.class.getSimpleName()));
        assertNotEquals(Couleur.JAUNE, PtOutils.convertStringToObject("VERT", Couleur.class.getSimpleName()));
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

        assertEquals(Status.ANNULEE, PtOutils.convertStringToObject("ANNULEE", Status.class.getSimpleName()));
        assertEquals(Status.ATTENTE, PtOutils.convertStringToObject("ATTENTE", Status.class.getSimpleName()));
        assertNotEquals(Status.ATTENTE, PtOutils.convertStringToObject("COMPLETE", Status.class.getSimpleName()));
        assertNull(PtOutils.convertStringToObject("MPZPM", Status.class.getSimpleName()));

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
        filette.add(1);
        actual.put(1, filette);
        String expected = "BLONDE:5;BRUTE:2;FILETTE:-1;TRUAND:3,5";
        //Assertions.assertEquals(actual, PtTool.convertStringToObject(expected, "HashMap<Integer,List<Integer>>"));
        //assertNotEquals(actual, PtTool.convertStringToObject(expected, "HashMap<Integer,List<Integer>>"));

        List<CarteType> carteTypes = new ArrayList<>();
        carteTypes.add(CarteType.ABA);
        carteTypes.add(CarteType.ACS);
        expected = "ABA,ACS";
        assertEquals(carteTypes, PtOutils.convertStringToObject(expected, "List<CarteType>"));
        assertNotEquals(carteTypes, PtOutils.convertStringToObject("az", "List<CarteType>"));

        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(Couleur.ROUGE);
        couleurs.add(Couleur.NOIR);
        expected = "ROUGE,NOIR";
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
