package jeu.personnage;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.Joueur;
import jeu.LaBlonde;
public class LaBlondeTest {
	
	@Test
    void test() {
		 Color c = Color.BLUE;
	        String nom = "Joueur1";
	        Joueur j1 = new Joueur(c, nom);
	        LaBlonde p1 = new LaBlonde(j1);
	       
        assertEquals(p1.getPoint(),7);
        assertEquals(p1.getNbrZretenu(),1);
        assertEquals(p1.getNbrVoixPourVoter(),1);

    }

}

