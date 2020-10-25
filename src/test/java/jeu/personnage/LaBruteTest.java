package jeu.personnage;
	
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import jeu.*;
public class LaBruteTest {
	@Test
    void test() {
        
        Color c = Color.BLUE;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(c, nom);
        Personnage p1 = new LaBrute(j1);
        assertEquals(p1.getPoint(),5);
        assertEquals(p1.getNbrZretenu(),2);
        assertEquals(p1.nbrVoixDeVote(),1);

    }
}
