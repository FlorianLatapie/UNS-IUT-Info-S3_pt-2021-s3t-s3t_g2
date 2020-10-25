package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.Joueur;
import jeu.LaBlonde;

class LaBlondeTest {
	@Test
	void test() {
		Color c = Color.BLUE;
		String nom = "Joueur1";
		Joueur j1 = new Joueur(c, nom);
		LaBlonde p1 = new LaBlonde(j1);

		assertEquals(7, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}