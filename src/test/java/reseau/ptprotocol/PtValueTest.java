package reseau.ptprotocol;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PtValueTest {
    static PtValue ptValue;

    @BeforeAll
    static void setUp() {
        ptValue = new PtValue("nombre","int","Nombre de stylo");
    }

    @Test
    void getName() {
        assertEquals("nombre",ptValue.getName());
    }

    @Test
    void getType() {
        assertEquals("int",ptValue.getType());
    }

    @Test
    void getDoc() {
        assertEquals("Nombre de stylo",ptValue.getDoc());
    }

    @Test
    void testToString() {
        assertEquals("[Name: nombre \tType: int \t\tDoc: Nombre de stylo]",ptValue.toString());
    }
}