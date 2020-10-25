package jeu.personnage;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LaFiletteTest {
	@Test
	void test() {
		Color c = Color.BLUE;
		String nom = "Joueur1";
		Joueur j1 = new Joueur(c, nom);
		LaFillette p1 = new LaFillette(j1);
		assertEquals(1, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}