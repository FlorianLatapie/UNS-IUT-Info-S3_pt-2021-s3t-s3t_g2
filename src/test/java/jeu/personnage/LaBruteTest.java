package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import jeu.*;

class LaBruteTest {
    @Test
    void test() {
        Color c = Color.BLUE;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(c, nom);
        Personnage p1 = new LaBrute(j1);
        assertEquals(5, p1.getPoint());
        assertEquals(2, p1.getNbrZretenu());
        assertEquals(1, p1.getNbrVoixPourVoter());
    }
}