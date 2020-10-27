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
        assertEquals("Test", PtTool.convertParamToString("Test", String.class.getSimpleName()));
        assertNotEquals("Test", PtTool.convertParamToString("Tes", String.class.getSimpleName()));

        assertEquals("0", PtTool.convertParamToString(0, int.class.getSimpleName()));
        assertEquals("1009", PtTool.convertParamToString(1009, int.class.getSimpleName()));
        assertNotEquals("0", PtTool.convertParamToString(-2, int.class.getSimpleName()));

        assertEquals("ECD", PtTool.convertParamToString(VoteType.ECD, VoteType.class.getSimpleName()));
        assertEquals("MPZ", PtTool.convertParamToString(VoteType.MPZ, VoteType.class.getSimpleName()));
        assertNotEquals("MPL", PtTool.convertParamToString(VoteType.MPZ, VoteType.class.getSimpleName()));

        assertEquals("PRE", PtTool.convertParamToString(VoteEtape.PRE, VoteEtape.class.getSimpleName()));
        assertEquals("SEC", PtTool.convertParamToString(VoteEtape.SEC, VoteEtape.class.getSimpleName()));
        assertNotEquals("MPL", PtTool.convertParamToString(VoteEtape.PRE, VoteEtape.class.getSimpleName()));

        assertEquals("NE", PtTool.convertParamToString(VigileEtat.NE, VigileEtat.class.getSimpleName()));
        assertEquals("NUL", PtTool.convertParamToString(VigileEtat.NUL, VigileEtat.class.getSimpleName()));
        assertNotEquals("NEA", PtTool.convertParamToString(VigileEtat.NUL, VigileEtat.class.getSimpleName()));

        assertEquals("JOUEUR", PtTool.convertParamToString(TypeJoueur.JOUEUR, TypeJoueur.class.getSimpleName()));
        assertEquals("BOT", PtTool.convertParamToString(TypeJoueur.BOT, TypeJoueur.class.getSimpleName()));
        assertNotEquals("NUL", PtTool.convertParamToString(TypeJoueur.BOT, TypeJoueur.class.getSimpleName()));

        assertEquals("FORCE", PtTool.convertParamToString(ReasonType.FORCE, ReasonType.class.getSimpleName()));
        assertEquals("PION", PtTool.convertParamToString(ReasonType.PION, ReasonType.class.getSimpleName()));
        assertNotEquals("NUL", PtTool.convertParamToString(ReasonType.ZOMBIE, ReasonType.class.getSimpleName()));

        assertEquals("BLONDE", PtTool.convertParamToString(PionType.BLONDE, PionType.class.getSimpleName()));
        assertEquals("FILETTE", PtTool.convertParamToString(PionType.FILETTE, PionType.class.getSimpleName()));
        assertNotEquals("azfdsf", PtTool.convertParamToString(PionType.BRUTE, PionType.class.getSimpleName()));

        assertEquals("BBR", PtTool.convertParamToString(PionCouleur.BBR, PionCouleur.class.getSimpleName()));
        assertEquals("JBR", PtTool.convertParamToString(PionCouleur.JBR, PionCouleur.class.getSimpleName()));
        assertNotEquals("HJ", PtTool.convertParamToString(PionCouleur.BF, PionCouleur.class.getSimpleName()));

        assertEquals("NOIR", PtTool.convertParamToString(Couleur.NOIR, Couleur.class.getSimpleName()));
        assertEquals("MARRON", PtTool.convertParamToString(Couleur.MARRON, Couleur.class.getSimpleName()));
        assertNotEquals("JAUNE", PtTool.convertParamToString(Couleur.VERT, Couleur.class.getSimpleName()));

        assertEquals("LIEUX", PtTool.convertParamToString(CondType.LIEUX, CondType.class.getSimpleName()));
        assertEquals("PION", PtTool.convertParamToString(CondType.PION, CondType.class.getSimpleName()));
        assertNotEquals("NON", PtTool.convertParamToString(CondType.PION, CondType.class.getSimpleName()));

        assertEquals("ARE", PtTool.convertParamToString(CarteType.ARE, CarteType.class.getSimpleName()));
        assertEquals("CDS", PtTool.convertParamToString(CarteType.CDS, CarteType.class.getSimpleName()));
        assertNotEquals("OUI", PtTool.convertParamToString(CarteType.MAT, CarteType.class.getSimpleName()));

        assertEquals("JRU", PtTool.convertParamToString(TypePartie.JRU, TypePartie.class.getSimpleName()));
        assertEquals("BOTU", PtTool.convertParamToString(TypePartie.BOTU, TypePartie.class.getSimpleName()));
        assertNotEquals("OUI", PtTool.convertParamToString(TypePartie.MIXTE, TypePartie.class.getSimpleName()));

        assertEquals("ANNULEE", PtTool.convertParamToString(Status.ANNULEE, Status.class.getSimpleName()));
        assertEquals("ATTENTE", PtTool.convertParamToString(Status.ATTENTE, Status.class.getSimpleName()));
        assertNotEquals("Ah", PtTool.convertParamToString(Status.COMPLETE, Status.class.getSimpleName()));

        HashMap<PionType, List<Integer>> actual = new HashMap<>();
        List<Integer> blonde = new ArrayList<>();
        blonde.add(5);
        actual.put(PionType.BLONDE, blonde);
        List<Integer> brute = new ArrayList<>();
        brute.add(2);
        actual.put(PionType.BRUTE, brute);
        List<Integer> truand = new ArrayList<>();
        truand.add(3);
        truand.add(5);
        actual.put(PionType.TRUAND, truand);
        List<Integer> filette = new ArrayList<>();
        filette.add(-1);
        actual.put(PionType.FILETTE, filette);
        String expected = "BLONDE:5;BRUTE:2;FILETTE:-1;TRUAND:3,5";
        //Assertions.assertEquals(expected, PtTool.convertParamToString(actual, "HashMap<PionType,List<Integer>>"));
        assertNotEquals("az", PtTool.convertParamToString(actual, "HashMap<PionType,List<Integer>>"));

        List<CarteType> carteTypes = new ArrayList<>();
        carteTypes.add(CarteType.ABA);
        carteTypes.add(CarteType.ACS);
        expected = "ABA,ACS";
        assertEquals(expected, PtTool.convertParamToString(carteTypes, "List<CarteType>"));
        assertNotEquals("az", PtTool.convertParamToString(carteTypes, "List<CarteType>"));

        List<PionType> pionTypes = new ArrayList<>();
        pionTypes.add(PionType.FILETTE);
        pionTypes.add(PionType.TRUAND);
        expected = "FILETTE,TRUAND";
        assertEquals(expected, PtTool.convertParamToString(pionTypes, "List<PionType>"));
        assertNotEquals("az", PtTool.convertParamToString(pionTypes, "List<PionType>"));

        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(Couleur.ROUGE);
        couleurs.add(Couleur.NOIR);
        expected = "ROUGE,NOIR";
        assertEquals(expected, PtTool.convertParamToString(couleurs, "List<Couleur>"));
        assertNotEquals("az", PtTool.convertParamToString(couleurs, "List<Couleur>"));

        List<PionCouleur> pionCouleurs = new ArrayList<>();
        pionCouleurs.add(PionCouleur.BBR);
        pionCouleurs.add(PionCouleur.JT);
        expected = "BBR,JT";
        assertEquals(expected, PtTool.convertParamToString(pionCouleurs, "List<PionCouleur>"));
        assertNotEquals("az", PtTool.convertParamToString(pionCouleurs, "List<PionCouleur>"));

        List<Integer> integers = new ArrayList<>();
        integers.add(5);
        integers.add(0);
        expected = "5,0";
        assertEquals(expected, PtTool.convertParamToString(integers, "List<Integer>"));
        assertNotEquals("az", PtTool.convertParamToString(integers, "List<Integer>"));

        List<String> strings = new ArrayList<>();
        strings.add("5");
        strings.add("Lol");
        expected = "5,Lol";
        assertEquals(expected, PtTool.convertParamToString(strings, "List<String>"));
        assertNotEquals("az", PtTool.convertParamToString(strings, "List<String>"));

        assertNull(PtTool.convertParamToString(strings, Character.class.getSimpleName()));
        assertNull(PtTool.convertParamToString(strings, "Oui"));
    }

    @Test
    void convertStringToObject() {
        assertEquals("Test", PtTool.convertStringToObject("Test", String.class.getSimpleName()));
        assertNotEquals("Test", PtTool.convertStringToObject("Tes", String.class.getSimpleName()));

        assertEquals(0, PtTool.convertStringToObject("0", int.class.getSimpleName()));
        assertEquals(1009, PtTool.convertStringToObject("1009", int.class.getSimpleName()));
        assertNotEquals(0, PtTool.convertStringToObject("-2", int.class.getSimpleName()));

        assertEquals(VoteType.ECD, PtTool.convertStringToObject("ECD", VoteType.class.getSimpleName()));
        assertEquals(VoteType.MPZ, PtTool.convertStringToObject("MPZ", VoteType.class.getSimpleName()));
        assertNotEquals(VoteType.FDC, PtTool.convertStringToObject("MPZ", VoteType.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", VoteType.class.getSimpleName()));

        assertEquals(VoteEtape.PRE, PtTool.convertStringToObject("PRE", VoteEtape.class.getSimpleName()));
        assertEquals(VoteEtape.SEC, PtTool.convertStringToObject("SEC", VoteEtape.class.getSimpleName()));
        assertNotEquals(VoteEtape.SEC, PtTool.convertStringToObject("PRE", VoteEtape.class.getSimpleName()));

        assertNull(PtTool.convertStringToObject("MPZPM", VoteEtape.class.getSimpleName()));

        assertEquals(VigileEtat.NE, PtTool.convertStringToObject("NE", VigileEtat.class.getSimpleName()));
        assertEquals(VigileEtat.NUL, PtTool.convertStringToObject("NUL", VigileEtat.class.getSimpleName()));
        assertNotEquals(VigileEtat.NE, PtTool.convertStringToObject("NUL", VigileEtat.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", VigileEtat.class.getSimpleName()));

        assertEquals(TypeJoueur.JOUEUR, PtTool.convertStringToObject("JOUEUR", TypeJoueur.class.getSimpleName()));
        assertEquals(TypeJoueur.BOT, PtTool.convertStringToObject("BOT", TypeJoueur.class.getSimpleName()));
        assertNotEquals(TypeJoueur.JOUEUR, PtTool.convertStringToObject("BOT", TypeJoueur.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", TypeJoueur.class.getSimpleName()));

        assertEquals(ReasonType.FORCE, PtTool.convertStringToObject("FORCE", ReasonType.class.getSimpleName()));
        assertEquals(ReasonType.PION, PtTool.convertStringToObject("PION", ReasonType.class.getSimpleName()));
        assertNotEquals(ReasonType.PION, PtTool.convertStringToObject("ZOMBIE", ReasonType.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", ReasonType.class.getSimpleName()));

        assertEquals(PionType.BLONDE, PtTool.convertStringToObject("BLONDE", PionType.class.getSimpleName()));
        assertEquals(PionType.FILETTE, PtTool.convertStringToObject("FILETTE", PionType.class.getSimpleName()));
        assertNotEquals(PionType.BLONDE, PtTool.convertStringToObject("BRUTE", PionType.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", PionType.class.getSimpleName()));

        assertEquals(PionCouleur.BBR, PtTool.convertStringToObject("BBR", PionCouleur.class.getSimpleName()));
        assertEquals(PionCouleur.JBR, PtTool.convertStringToObject("JBR", PionCouleur.class.getSimpleName()));
        assertNotEquals(PionCouleur.BT, PtTool.convertStringToObject("BF", PionCouleur.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", PionCouleur.class.getSimpleName()));

        assertEquals(Couleur.NOIR, PtTool.convertStringToObject("NOIR", Couleur.class.getSimpleName()));
        assertEquals(Couleur.MARRON, PtTool.convertStringToObject("MARRON", Couleur.class.getSimpleName()));
        assertNotEquals(Couleur.JAUNE, PtTool.convertStringToObject("VERT", Couleur.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", Couleur.class.getSimpleName()));

        assertEquals(CondType.LIEUX, PtTool.convertStringToObject("LIEUX", CondType.class.getSimpleName()));
        assertEquals(CondType.PION, PtTool.convertStringToObject("PION", CondType.class.getSimpleName()));
        assertNotEquals(CondType.LIEUX, PtTool.convertStringToObject("PION", CondType.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", CondType.class.getSimpleName()));

        assertEquals(CarteType.ARE, PtTool.convertStringToObject("ARE", CarteType.class.getSimpleName()));
        assertEquals(CarteType.CDS, PtTool.convertStringToObject("CDS", CarteType.class.getSimpleName()));
        assertNotEquals(CarteType.ACS, PtTool.convertStringToObject("MAT", CarteType.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", CarteType.class.getSimpleName()));

        assertEquals(TypePartie.JRU, PtTool.convertStringToObject("JRU", TypePartie.class.getSimpleName()));
        assertEquals(TypePartie.BOTU, PtTool.convertStringToObject("BOTU", TypePartie.class.getSimpleName()));
        assertNotEquals(TypePartie.BOTU, PtTool.convertStringToObject("MIXTE", TypePartie.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", TypePartie.class.getSimpleName()));

        assertEquals(Status.ANNULEE, PtTool.convertStringToObject("ANNULEE", Status.class.getSimpleName()));
        assertEquals(Status.ATTENTE, PtTool.convertStringToObject("ATTENTE", Status.class.getSimpleName()));
        assertNotEquals(Status.ATTENTE, PtTool.convertStringToObject("COMPLETE", Status.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject("MPZPM", Status.class.getSimpleName()));

        HashMap<PionType, List<Integer>> actual = new HashMap<>();
        List<Integer> blonde = new ArrayList<>();
        blonde.add(5);
        actual.put(PionType.BLONDE, blonde);
        List<Integer> brute = new ArrayList<>();
        brute.add(2);
        actual.put(PionType.BRUTE, brute);
        List<Integer> truand = new ArrayList<>();
        truand.add(3);
        truand.add(5);
        actual.put(PionType.TRUAND, truand);
        List<Integer> filette = new ArrayList<>();
        filette.add(-1);
        actual.put(PionType.FILETTE, filette);
        String expected = "BLONDE:5;BRUTE:2;FILETTE:-1;TRUAND:3,5";
        //Assertions.assertEquals(actual, PtTool.convertStringToObject(expected, "HashMap<PionType,List<Integer>>"));
        //assertNotEquals(actual, PtTool.convertStringToObject(expected, "HashMap<PionType,List<Integer>>"));

        List<CarteType> carteTypes = new ArrayList<>();
        carteTypes.add(CarteType.ABA);
        carteTypes.add(CarteType.ACS);
        expected = "ABA,ACS";
        assertEquals(carteTypes, PtTool.convertStringToObject(expected, "List<CarteType>"));
        assertNotEquals(carteTypes, PtTool.convertStringToObject("az", "List<CarteType>"));

        List<PionType> pionTypes = new ArrayList<>();
        pionTypes.add(PionType.FILETTE);
        pionTypes.add(PionType.TRUAND);
        expected = "FILETTE,TRUAND";
        assertEquals(pionTypes, PtTool.convertStringToObject(expected, "List<PionType>"));
        assertNotEquals(pionTypes, PtTool.convertStringToObject("az", "List<PionType>"));

        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(Couleur.ROUGE);
        couleurs.add(Couleur.NOIR);
        expected = "ROUGE,NOIR";
        assertEquals(couleurs, PtTool.convertStringToObject(expected, "List<Couleur>"));
        assertNotEquals(couleurs, PtTool.convertStringToObject("azza", "List<Couleur>"));

        List<PionCouleur> pionCouleurs = new ArrayList<>();
        pionCouleurs.add(PionCouleur.BBR);
        pionCouleurs.add(PionCouleur.JT);
        expected = "BBR,JT";
        assertEquals(pionCouleurs, PtTool.convertStringToObject(expected, "List<PionCouleur>"));
        assertNotEquals(pionCouleurs, PtTool.convertStringToObject("azza", "List<PionCouleur>"));

        List<Integer> integers = new ArrayList<>();
        integers.add(5);
        integers.add(0);
        expected = "5,0";
        assertEquals(expected, PtTool.convertParamToString(integers, "List<Integer>"));
        assertNotEquals("az", PtTool.convertParamToString(integers, "List<Integer>"));

        List<String> strings = new ArrayList<>();
        strings.add("Euh");
        strings.add("Oui");
        expected = "Euh,Oui";
        assertEquals(expected, PtTool.convertParamToString(strings, "List<String>"));
        assertNotEquals("az", PtTool.convertParamToString(strings, "List<String>"));

        assertNull(PtTool.convertStringToObject("Lol", Character.class.getSimpleName()));
        assertNull(PtTool.convertStringToObject(expected, "Oui"));
    }
}