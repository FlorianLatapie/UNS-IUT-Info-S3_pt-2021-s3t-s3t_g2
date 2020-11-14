package reseau.ptprotocol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PtValuesTest {
    PtValues ptValues;
    PtValue ptValue;
    PtValue ptValue1;

    @BeforeEach
    void setUp() {
        ptValues = new PtValues();
        ptValue = new PtValue("nombreChocolat", "int", "C'est le nombre de chocolat !");
        ptValue1 = new PtValue("nomDuClub", "String", "Bah, c'est le nom du club !");
        ptValues.ajouterValeur(ptValue);
        ptValues.ajouterValeur(ptValue1);
    }

    @Test
    void addValue() {
        assertEquals(2, ptValues.getValeurs().size());
        PtValue ptValue = new PtValue("typeDuJoueur", "TypeJoueur", "Le type du joueur");
        ptValues.ajouterValeur(ptValue);
        assertEquals(3, ptValues.getValeurs().size());
    }

    @Test
    void getValues() {
        assertEquals(2, ptValues.getValeurs().size());
        assertEquals(ptValue, ptValues.getValeurs().get(0));
        assertEquals(ptValue1, ptValues.getValeurs().get(1));
    }

    @Test
    void testToString() {
        String expected = "[Values: " + ptValue.toString() + ptValue1.toString() + "]";
        assertEquals(expected, ptValues.toString());
    }
}