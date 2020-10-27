package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jeu.*;
import reseau.type.Couleur;

class LeTruandTest {
	@Test
	void test() {
		Couleur c = Couleur.BLEU;
		String nom = "Joueur1";
		Joueur j1 = new Joueur("P0", "127.0.0.1", c,1030, nom);
		LeTruand p1 = new LeTruand(j1);
		assertEquals(3, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(2, p1.getNbrVoixPourVoter());
	}
}