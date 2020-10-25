package jeu.personnage;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import jeu.*;

class PersonnageTest {
    @Test
    void test_test_Personnage() {
        Color c = Color.BLUE;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(c, nom);
        LaBlonde p1 = new LaBlonde(j1);
        p1.setEstVivant(true);
        assertTrue(p1.isEstVivant());

        p1.setEstCache(true);
        assertTrue(p1.isEstCache());

        p1.setNbrElection(3);
        assertEquals(3, p1.getNbrElection());

        p1.setPoint(2);
        assertEquals(2, p1.getPoint());

        assertEquals(1, p1.getNbrZretenu());

        assertEquals(1, p1.getNbrVoixPourVoter());

        Lieu newLieu = new Lieu(4);
        p1.changerDeLieux(newLieu);

        assertEquals(p1.getMonLieu(), newLieu);

        Joueur j2 = new Joueur(c, nom);
        assertEquals(p1.getJoueur(), j1);
        p1.setJoueur(j2);
        assertEquals(p1.getJoueur(), j2);

        assertEquals(p1.getType(), p1.getType());
    }
}
