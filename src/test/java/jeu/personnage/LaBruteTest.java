package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import jeu.*;
import reseau.type.Couleur;

class LaBruteTest {
	@Test
	void test() {
		Couleur c = Couleur.BLEU;
		String nom = "Joueur1";
		Joueur j1 = new Joueur("P0", "127.0.0.1",  c,1030, nom);
		Personnage p1 = new LaBrute(j1);
		assertEquals(5, p1.getPoint());
		assertEquals(2, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}