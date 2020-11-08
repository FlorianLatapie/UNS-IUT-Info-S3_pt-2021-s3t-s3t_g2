package pp.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import pp.Joueur;
import pp.LaBlonde;

class LaBlondeTest {
	@Test
	void test() throws UnknownHostException {
		String nom = "Joueur1";
		Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"), 1050, nom);
		LaBlonde p1 = new LaBlonde(j1);

		assertEquals(7, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}