package jeu.personnage;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.*;
public class LeTruandTest {
	@Test
    void test() {

		 Color c = Color.BLUE;
	        String nom = "Joueur1";
	        Joueur j1 = new Joueur(c, nom);
	        LeTruand p1 = new LeTruand(j1);
        assertEquals(p1.getPoint(),3);
        assertEquals(p1.getNbrZretenu(),1);
        assertEquals(p1.nbrVoixDeVote(),2);

    }

}

