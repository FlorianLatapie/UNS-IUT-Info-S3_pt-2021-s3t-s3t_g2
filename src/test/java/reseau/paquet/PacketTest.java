package reseau.paquet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reseau.ptprotocole.PtProtocole;
import reseau.ptprotocole.PtValeur;
import reseau.ptprotocole.PtValeurs;

import static org.junit.jupiter.api.Assertions.*;

class PacketTest {
	static Paquet packet;
	static PtProtocole ptProtocol;

	@BeforeAll
	static void setUp() {
		PtValeurs ptValues = new PtValeurs();
		PtValeur ptValue1 = new PtValeur("nombre", "int", "Salut a tous");
		ptValues.ajouterValeur(ptValue1);
		ptProtocol = new PtProtocole("0", "TestClasse", "Dit bonjour", "DB", "UDP", ptValues);

		packet = new Paquet(ptProtocol);
	}

	@Test
	void build() {
		assertEquals("DB-0", packet.construire(0));
		assertEquals("DB-1", packet.construire(1));
		assertEquals("DB-900", packet.construire(900));
		assertNotEquals("DB-0", packet.construire(1000));
		assertThrows(IllegalArgumentException.class, () -> packet.construire());
		assertThrows(IllegalArgumentException.class, () -> packet.construire("0", 1));
		assertThrows(IllegalArgumentException.class, () -> packet.construire("0"));
		assertThrows(NullPointerException.class, () -> packet.construire(null));
	}

	@Test
	void getValues() {
		Object[] values = packet.getValeurs("DB-1000");
		Assertions.assertEquals(1000, (int) values[1]);
		Assertions.assertEquals(2, values.length);
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeurs("DB");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeurs("DB-2-2-2-2");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeurs("");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeurs("DB-az");
		});
	}

	@Test
	void getValue() {
		assertEquals(1000, (int) packet.getValeur("DB-1000", 1));
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeur("DB-1000", 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeur("DB-1000", -10);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeur("DB-1000", 1000);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeur("DB-1000", 1000);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			packet.getValeur("DB-na", 1);
		});
	}

	@Test
	void isPacket() {
		assertTrue(packet.isPaquet("DB-45"));
		assertFalse(packet.isPaquet("ACP-45-ECP-798-Salut!"));
		assertTrue(packet.isPaquet("DB"));
		assertFalse(packet.isPaquet("ACP"));
	}

	@Test
	void getKey() {
		assertEquals("DB", packet.getCle());
		assertNotEquals("db", packet.getCle());
	}

	@Test
	void toStringTest() {
		assertEquals(ptProtocol.toString(), packet.toString());
	}
}