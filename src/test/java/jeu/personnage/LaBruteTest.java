package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import jeu.*;

class LaBruteTest {
	@Test
	void test() throws UnknownHostException {
		String nom = "Joueur1";
		Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"),  1030, nom);
		Personnage p1 = new LaBrute(j1);
		assertEquals(5, p1.getPoint());
		assertEquals(2, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}