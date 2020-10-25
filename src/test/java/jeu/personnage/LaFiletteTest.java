package jeu.personnage;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.*;
public class LaFiletteTest {
	@Test
    void test() {
		Color c = Color.BLUE;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(c, nom);
        LaFillette p1 = new LaFillette(j1);
        assertEquals(p1.getPoint(),1);
        assertEquals(p1.getNbrZretenu(),1);
        assertEquals(p1.getNbrVoixPourVoter(),1);

    }

}
