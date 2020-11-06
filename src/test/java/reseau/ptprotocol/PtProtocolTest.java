package reseau.ptprotocol;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PtProtocolTest {
    static PtProtocol ptProtocol;
    static PtValues ptValues;

    @BeforeAll
    static void setUp() {
        ptValues = new PtValues();
        ptValues.addValue(new PtValue("Lol", "int", "La verite"));
        ptProtocol = new PtProtocol("0", "LaFuite", "Ajoute la pluie", "PL", "UDP", ptValues);
    }

    @Test
    void valueCount() {
        assertEquals(1, ptProtocol.valueCount());
    }

    @Test
    void getSeq() {
        assertEquals("0", ptProtocol.getSeq());
    }

    @Test
    void getClassName() {
        assertEquals("LaFuite", ptProtocol.getClassName());
    }

    @Test
    void getKeyword() {
        assertEquals("PL", ptProtocol.getKeyword());
    }

    @Test
    void getProtocol() {
        assertEquals("UDP", ptProtocol.getProtocol());
    }

    @Test
    void getValues() {
        assertEquals(ptValues, ptProtocol.getValues());
    }

    @Test
    void toNameArray() {
        String[] str = {"Lol"};
        assertEquals(1, ptProtocol.toNameArray().length);
        assertEquals(str[0], ptProtocol.toNameArray()[0]);
    }

    @Test
    void toTypeArray() {
        String[] str = {"int"};
        assertEquals(1, ptProtocol.toTypeArray().length);
        assertEquals(str[0], ptProtocol.toTypeArray()[0]);
    }

    @Test
    void toDocArray() {
        String[] str = {"La verite"};
        assertEquals(1, ptProtocol.toDocArray().length);
        assertEquals(str[0], ptProtocol.toDocArray()[0]);
    }
}