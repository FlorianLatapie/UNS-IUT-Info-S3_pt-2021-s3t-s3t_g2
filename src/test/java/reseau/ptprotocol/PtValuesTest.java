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
        ptValues.addValue(ptValue);
        ptValues.addValue(ptValue1);
    }

    @Test
    void addValue() {
        assertEquals(2, ptValues.getValues().size());
        PtValue ptValue = new PtValue("typeDuJoueur", "TypeJoueur", "Le type du joueur");
        ptValues.addValue(ptValue);
        assertEquals(3, ptValues.getValues().size());
    }

    @Test
    void getValues() {
        assertEquals(2, ptValues.getValues().size());
        assertEquals(ptValue, ptValues.getValues().get(0));
        assertEquals(ptValue1, ptValues.getValues().get(1));
    }

    @Test
    void testToString() {
        String expected = "[Values: " + ptValue.toString() + ptValue1.toString() + "]";
        assertEquals(expected, ptValues.toString());
    }
}