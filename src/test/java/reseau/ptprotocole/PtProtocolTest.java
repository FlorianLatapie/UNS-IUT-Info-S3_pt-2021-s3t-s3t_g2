package reseau.ptprotocole;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PtProtocolTest {
	static PtProtocole ptProtocol;
	static PtValeurs ptValues;

	@BeforeAll
	static void setUp() {
		ptValues = new PtValeurs();
		ptValues.ajouterValeur(new PtValeur("Lol", "int", "La verite"));
		ptProtocol = new PtProtocole("0", "LaFuite", "Ajoute la pluie", "PL", "UDP", ptValues);
	}

	@Test
	void valueCount() {
		assertEquals(1, ptProtocol.taille());
	}

	@Test
	void getSeq() {
		assertEquals("0", ptProtocol.getSeq());
	}

	@Test
	void getClassName() {
		assertEquals("LaFuite", ptProtocol.getNomClasse());
	}

	@Test
	void getKeyword() {
		assertEquals("PL", ptProtocol.getMotCle());
	}

	@Test
	void getProtocol() {
		assertEquals("UDP", ptProtocol.getProtocole());
	}

	@Test
	void getValues() {
		assertEquals(ptValues, ptProtocol.getValeurs());
	}

	@Test
	void toNameArray() {
		String[] str = { "Lol" };
		assertEquals(1, ptProtocol.toNoms().length);
		assertEquals(str[0], ptProtocol.toNoms()[0]);
	}

	@Test
	void toTypeArray() {
		String[] str = { "int" };
		assertEquals(1, ptProtocol.toTypes().length);
		assertEquals(str[0], ptProtocol.toTypes()[0]);
	}

	@Test
	void toDocArray() {
		String[] str = { "La verite" };
		assertEquals(1, ptProtocol.toDocs().length);
		assertEquals(str[0], ptProtocol.toDocs()[0]);
	}
}