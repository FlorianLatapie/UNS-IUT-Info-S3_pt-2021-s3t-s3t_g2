package pp.personnage;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import pp.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LaFiletteTest {
	@Test
	void test() throws UnknownHostException {
		String nom = "Joueur1";
		Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"),1030, nom);
		LaFillette p1 = new LaFillette(j1);
		assertEquals(1, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
	}
}