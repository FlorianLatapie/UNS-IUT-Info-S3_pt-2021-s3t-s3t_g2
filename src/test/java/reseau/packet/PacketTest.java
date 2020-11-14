package reseau.packet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reseau.ptprotocol.PtProtocol;
import reseau.ptprotocol.PtValue;
import reseau.ptprotocol.PtValues;

import static org.junit.jupiter.api.Assertions.*;

class PacketTest {
    static Packet packet;
    static PtProtocol ptProtocol;

    @BeforeAll
    static void setUp() {
        PtValues ptValues = new PtValues();
        PtValue ptValue1 = new PtValue("nombre", "int", "Salut a tous");
        ptValues.ajouterValeur(ptValue1);
        ptProtocol = new PtProtocol("0", "TestClasse", "Dit bonjour", "DB", "UDP", ptValues);

        packet = new Packet(ptProtocol);
    }

    @Test
    void build() {
        assertEquals("DB-0", packet.build(0));
        assertEquals("DB-1", packet.build(1));
        assertEquals("DB-900", packet.build(900));
        assertNotEquals("DB-0", packet.build(1000));
        assertThrows(IllegalArgumentException.class,
                () -> packet.build()
        );
        assertThrows(IllegalArgumentException.class,
                () -> packet.build("0", 1)
        );
        assertThrows(IllegalArgumentException.class,
                () -> packet.build("0")
        );
        assertThrows(NullPointerException.class,
                () -> packet.build(null)
        );
    }

    @Test
    void getValues() {
        Object[] values = packet.getValues("DB-1000");
        Assertions.assertEquals(1000, (int) values[1]);
        Assertions.assertEquals(2, values.length);
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValues("DB");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValues("DB-2-2-2-2");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValues("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValues("DB-az");
        });
    }

    @Test
    void getValue() {
        assertEquals(1000, (int) packet.getValue("DB-1000", 1));
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValue("DB-1000", 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValue("DB-1000", -10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValue("DB-1000", 1000);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValue("DB-1000", 1000);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packet.getValue("DB-na", 1);
        });
    }

    @Test
    void isPacket() {
        assertTrue(packet.isPacket("DB-45"));
        assertFalse(packet.isPacket("ACP-45-ECP-798-Salut!"));
        assertTrue(packet.isPacket("DB"));
        assertFalse(packet.isPacket("ACP"));
    }

    @Test
    void getKey() {
        assertEquals("DB", packet.getKey());
        assertNotEquals("db", packet.getKey());
    }

    @Test
    void toStringTest() {
        assertEquals(ptProtocol.toString(), packet.toString());
    }
}