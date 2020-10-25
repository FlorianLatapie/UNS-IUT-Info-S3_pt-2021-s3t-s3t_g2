package jeu.personnage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.*;

class LeTruandTest {
    @Test
    void test() {
        Color c = Color.BLUE;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(c, nom);
        LeTruand p1 = new LeTruand(j1);
        assertEquals(3, p1.getPoint());
        assertEquals(1, p1.getNbrZretenu());
        assertEquals(2, p1.getNbrVoixPourVoter());
    }
}