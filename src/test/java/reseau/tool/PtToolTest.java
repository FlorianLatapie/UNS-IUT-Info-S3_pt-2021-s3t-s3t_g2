package reseau.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reseau.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PtToolTest {

    @Test
    void convertParamToString() {
        Assertions.assertEquals("Test", PtTool.convertParamToString("Test", String.class.getSimpleName()));
        Assertions.assertNotEquals("Test", PtTool.convertParamToString("Tes", String.class.getSimpleName()));

        Assertions.assertEquals("0", PtTool.convertParamToString(0, int.class.getSimpleName()));
        Assertions.assertEquals("-1", PtTool.convertParamToString(-1, int.class.getSimpleName()));
        Assertions.assertNotEquals("0", PtTool.convertParamToString(-2, int.class.getSimpleName()));

        Assertions.assertEquals("ECD", PtTool.convertParamToString(VoteType.ECD, VoteType.class.getSimpleName()));
        Assertions.assertEquals("MPZ", PtTool.convertParamToString(VoteType.MPZ, VoteType.class.getSimpleName()));
        Assertions.assertNotEquals("MPL", PtTool.convertParamToString(VoteType.MPZ, VoteType.class.getSimpleName()));

        Assertions.assertEquals("PRE", PtTool.convertParamToString(VoteEtape.PRE, VoteEtape.class.getSimpleName()));
        Assertions.assertEquals("SEC", PtTool.convertParamToString(VoteEtape.SEC, VoteEtape.class.getSimpleName()));
        Assertions.assertNotEquals("MPL", PtTool.convertParamToString(VoteEtape.PRE, VoteEtape.class.getSimpleName()));

        Assertions.assertEquals("NE", PtTool.convertParamToString(VigileEtat.NE, VigileEtat.class.getSimpleName()));
        Assertions.assertEquals("NUL", PtTool.convertParamToString(VigileEtat.NUL, VigileEtat.class.getSimpleName()));
        Assertions.assertNotEquals("NEA", PtTool.convertParamToString(VigileEtat.NUL, VigileEtat.class.getSimpleName()));

        Assertions.assertEquals("JOUEUR", PtTool.convertParamToString(TypeJoueur.JOUEUR, TypeJoueur.class.getSimpleName()));
        Assertions.assertEquals("BOT", PtTool.convertParamToString(TypeJoueur.BOT, TypeJoueur.class.getSimpleName()));
        Assertions.assertNotEquals("NUL", PtTool.convertParamToString(TypeJoueur.BOT, TypeJoueur.class.getSimpleName()));

        Assertions.assertEquals("FORCE", PtTool.convertParamToString(ReasonType.FORCE, ReasonType.class.getSimpleName()));
        Assertions.assertEquals("PION", PtTool.convertParamToString(ReasonType.PION, ReasonType.class.getSimpleName()));
        Assertions.assertNotEquals("NUL", PtTool.convertParamToString(ReasonType.ZOMBIE, ReasonType.class.getSimpleName()));

        Assertions.assertEquals("BLONDE", PtTool.convertParamToString(PionType.BLONDE, PionType.class.getSimpleName()));
        Assertions.assertEquals("FILETTE", PtTool.convertParamToString(PionType.FILETTE, PionType.class.getSimpleName()));
        Assertions.assertNotEquals("azfdsf", PtTool.convertParamToString(PionType.BRUTE, PionType.class.getSimpleName()));

        Assertions.assertEquals("BBR", PtTool.convertParamToString(PionCouleur.BBR, PionCouleur.class.getSimpleName()));
        Assertions.assertEquals("JBR", PtTool.convertParamToString(PionCouleur.JBR, PionCouleur.class.getSimpleName()));
        Assertions.assertNotEquals("HJ", PtTool.convertParamToString(PionCouleur.BF, PionCouleur.class.getSimpleName()));

        Assertions.assertEquals("NOIR", PtTool.convertParamToString(Couleur.NOIR, Couleur.class.getSimpleName()));
        Assertions.assertEquals("MARRON", PtTool.convertParamToString(Couleur.MARRON, Couleur.class.getSimpleName()));
        Assertions.assertNotEquals("JAUNE", PtTool.convertParamToString(Couleur.VERT, Couleur.class.getSimpleName()));

        Assertions.assertEquals("LIEUX", PtTool.convertParamToString(CondType.LIEUX, CondType.class.getSimpleName()));
        Assertions.assertEquals("PION", PtTool.convertParamToString(CondType.PION, CondType.class.getSimpleName()));
        Assertions.assertNotEquals("NON", PtTool.convertParamToString(CondType.PION, CondType.class.getSimpleName()));

        Assertions.assertEquals("ARE", PtTool.convertParamToString(CarteType.ARE, CarteType.class.getSimpleName()));
        Assertions.assertEquals("CDS", PtTool.convertParamToString(CarteType.CDS, CarteType.class.getSimpleName()));
        Assertions.assertNotEquals("OUI", PtTool.convertParamToString(CarteType.MAT, CarteType.class.getSimpleName()));

        Assertions.assertEquals("JRU", PtTool.convertParamToString(TypePartie.JRU, TypePartie.class.getSimpleName()));
        Assertions.assertEquals("BOTU", PtTool.convertParamToString(TypePartie.BOTU, TypePartie.class.getSimpleName()));
        Assertions.assertNotEquals("OUI", PtTool.convertParamToString(TypePartie.MIXTE, TypePartie.class.getSimpleName()));

        Assertions.assertEquals("ANNULEE", PtTool.convertParamToString(Status.ANNULEE, Status.class.getSimpleName()));
        Assertions.assertEquals("ATTENTE", PtTool.convertParamToString(Status.ATTENTE, Status.class.getSimpleName()));
        Assertions.assertNotEquals("Ah", PtTool.convertParamToString(Status.COMPLETE, Status.class.getSimpleName()));

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
        Assertions.assertEquals(expected, PtTool.convertParamToString(actual, "HashMap<PionType,List<Integer>>"));
        Assertions.assertNotEquals("az", PtTool.convertParamToString(actual, "HashMap<PionType,List<Integer>>"));

        List<CarteType> carteTypes = new ArrayList<>();
        carteTypes.add(CarteType.ABA);
        carteTypes.add(CarteType.ACS);
        expected = "ABA,ACS";
        Assertions.assertEquals(expected, PtTool.convertParamToString(carteTypes, "List<CarteType>"));
        Assertions.assertNotEquals("az", PtTool.convertParamToString(carteTypes, "List<CarteType>"));

        List<PionType> pionTypes = new ArrayList<>();
        pionTypes.add(PionType.FILETTE);
        pionTypes.add(PionType.TRUAND);
        expected = "FILETTE,TRUAND";
        Assertions.assertEquals(expected, PtTool.convertParamToString(pionTypes, "List<PionType>"));
        Assertions.assertNotEquals("az", PtTool.convertParamToString(pionTypes, "List<PionType>"));

        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(Couleur.ROUGE);
        couleurs.add(Couleur.NOIR);
        expected = "ROUGE,NOIR";
        Assertions.assertEquals(expected, PtTool.convertParamToString(couleurs, "List<Couleur>"));
        Assertions.assertNotEquals("az", PtTool.convertParamToString(couleurs, "List<Couleur>"));

        List<PionCouleur> pionCouleurs = new ArrayList<>();
        pionCouleurs.add(PionCouleur.BBR);
        pionCouleurs.add(PionCouleur.JT);
        expected = "BBR,JT";
        Assertions.assertEquals(expected, PtTool.convertParamToString(pionCouleurs, "List<PionCouleur>"));
        Assertions.assertNotEquals("az", PtTool.convertParamToString(pionCouleurs, "List<PionCouleur>"));

        List<Integer> integers = new ArrayList<>();
        integers.add(-5);
        integers.add(0);
        expected = "-5,0";
        Assertions.assertEquals(expected, PtTool.convertParamToString(integers, "List<Integer>"));
        Assertions.assertNotEquals("az", PtTool.convertParamToString(integers, "List<Integer>"));

        Assertions.assertEquals(null, PtTool.convertParamToString(integers, Character.class.getSimpleName()));
        Assertions.assertEquals(null, PtTool.convertParamToString(integers, "Oui"));
    }

    @Test
    void convertStringToObject() {
    }
}