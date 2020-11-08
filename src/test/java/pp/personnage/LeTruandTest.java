package pp.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pp.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

class LeTruandTest {
	@Test
	void test() throws UnknownHostException {
		String nom = "Joueur1";
		Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"), 1030, nom);
		LeTruand p1 = new LeTruand(j1);
		assertEquals(3, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(2, p1.getNbrVoixPourVoter());
	}
}