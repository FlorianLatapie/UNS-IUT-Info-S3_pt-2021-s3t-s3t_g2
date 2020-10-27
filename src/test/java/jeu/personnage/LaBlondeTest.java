package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import jeu.Joueur;
import jeu.LaBlonde;
import reseau.type.Couleur;

class LaBlondeTest {
	@Test
	void test() throws UnknownHostException {
		Couleur c = Couleur.BLEU;
		String nom = "Joueur1";
		Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"), c,1050, nom);
		LaBlonde p1 = new LaBlonde(j1);

		assertEquals(7, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}