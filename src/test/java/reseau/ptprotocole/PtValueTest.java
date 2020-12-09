package reseau.ptprotocole;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PtValueTest {
	static PtValeur ptValue;

	@BeforeAll
	static void setUp() {
		ptValue = new PtValeur("nombre", "int", "Nombre de stylo");
	}

	@Test
	void getName() {
		assertEquals("nombre", ptValue.getNom());
	}

	@Test
	void getType() {
		assertEquals("int", ptValue.getType());
	}

	@Test
	void getDoc() {
		assertEquals("Nombre de stylo", ptValue.getDoc());
	}

	@Test
	void testToString() {
		assertEquals("[Name: nombre \tType: int \t\tDoc: Nombre de stylo]", ptValue.toString());
	}
}