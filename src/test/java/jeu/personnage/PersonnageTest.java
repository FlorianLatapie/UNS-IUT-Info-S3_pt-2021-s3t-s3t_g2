package jeu.personnage;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import jeu.*;
import reseau.type.Couleur;

class PersonnageTest {
    @Test
    void test_test_Personnage() throws UnknownHostException {
        Couleur c = Couleur.BLEU;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"), c,1030, nom);
        LaBlonde p1 = new LaBlonde(j1);


        p1.setEstCache(true);
        assertTrue(p1.isEstCache());

        assertEquals(7, p1.getPoint());

        assertEquals(1, p1.getNbrZretenu());

        assertEquals(1, p1.getNbrVoixPourVoter());

        Lieu newLieu = new Lieu(4);
        p1.changerDeLieux(newLieu);

        assertEquals(p1.getMonLieu(), newLieu);

        Joueur j2 = new Joueur(1, InetAddress.getByName("127.0.0.1"), c,1030, nom);
        assertEquals(p1.getJoueur(), j1);

        assertEquals(TypePersonnage.BLONDE, p1.getType());
    }
}
