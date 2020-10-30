package jeu.personnage;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import jeu.*;
import reseau.type.Couleur;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LaFiletteTest {
	@Test
	void test() throws UnknownHostException {
		Couleur c = Couleur.BLEU;
		String nom = "Joueur1";
		Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"),1030, nom);
		LaFillette p1 = new LaFillette(j1);
		assertEquals(1, p1.getPoint());
		assertEquals(1, p1.getNbrZretenu());
		assertEquals(1, p1.getNbrVoixPourVoter());
	}
}