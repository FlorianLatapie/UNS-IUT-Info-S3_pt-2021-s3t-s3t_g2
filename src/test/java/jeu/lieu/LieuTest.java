package jeu.lieu;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jeu.Joueur;
import jeu.LaBlonde;
import jeu.LaBrute;
import jeu.LaFillette;
import jeu.Lieu;
import reseau.type.Couleur;

class LieuTest {

    @Test
    void test_Lieu_Constructor() {
        int numTest = 4;
        Lieu place = new Lieu(numTest);
        assertNotNull(place);
        Lieu place2 = new Lieu(1);
        assertNotNull(place2);
        Lieu place3 = new Lieu(2);
        assertNotNull(place3);
        Lieu place4 = new Lieu(3);
        assertNotNull(place4);
        Lieu place5 = new Lieu(5);
        assertNotNull(place5);
        Lieu place6 = new Lieu(6);
        assertNotNull(place6);

    }

    @Test
    void test_Lieu_Getters() {
        int numTest = 4;

        Lieu place = new Lieu(numTest);
        Assertions.assertNotNull(place);
        assertEquals(place.getNum(), numTest);
        assertEquals(place.getNbPlaces(), 24);
        assertEquals(place.getNbZombies(), 0);
        assertNotNull(place.getPersonnage());

    }

    @Test
    void test_Lieu_Setters() {
        int numTest = 3;
        Lieu place = new Lieu(numTest);
        assertTrue(place.isOuvert());
        place.setOuvert(false);
        assertFalse(place.isOuvert());
        assertThrows(RuntimeException.class, () -> {
            LaBlonde maBlonde = new LaBlonde(new Joueur("P0", "127.0.0.1", Couleur.NOIR, 1024, "popol"));
            LaBrute maBrute = new LaBrute(new Joueur("P1", "127.0.0.1", Couleur.NOIR, 1025, "tata lili"));
            LaFillette maFillette = new LaFillette(new Joueur("P2", "127.0.0.1", Couleur.NOIR, 1026, "toutou"));
            LaFillette maFillette1 = new LaFillette(new Joueur("P3", "127.0.0.1", Couleur.NOIR, 1027, "toutou"));
            LaBrute maBrute1 = new LaBrute(new Joueur("P4", "127.0.0.1", Couleur.NOIR, 1028, "op"));
            place.addPersonnage(maFillette);
            place.addPersonnage(maBrute);
            place.addPersonnage(maBlonde);
            place.addPersonnage(maFillette1);
            place.addPersonnage(maBrute1);
        });
    }

    @Test
    void test_getBlonde() {
        int numTest = 4;
        Lieu place = new Lieu(numTest);
        LaBlonde maBlonde = new LaBlonde(new Joueur("P5", "127.0.0.1", Couleur.NOIR, 1029, "blonde"));
        place.addPersonnage(maBlonde);
        assertEquals(place.getBlonde().size(), 1);

    }

    @Test
    void test_getJoueurSurLieu() {
        int numTest = 4;
        Lieu place = new Lieu(numTest);
        Joueur Bob = new Joueur("P6", "127.0.0.1", Couleur.NOIR,1030, "toto");
        LaBlonde maBlonde = new LaBlonde(Bob);
        place.addPersonnage(maBlonde);
        assertTrue(place.afficheJoueurSurLieu().add(Bob));
    }

    @Test
    void test_estAttaquable() {
        int numTest = 1;
        Lieu place = new Lieu(numTest);
        Joueur Bob = new Joueur("P7", "127.0.0.1",  Couleur.NOIR, 1030,"titi");
        LaBlonde maBlonde = new LaBlonde(Bob);
        place.addPersonnage(maBlonde);
        assertFalse(place.estAttaquable());
        LaBrute maBrute = new LaBrute(Bob);
        place.addPersonnage(maBrute);
        place.setNbZombies(place.getNbPlaces() + 4);
        assertTrue(place.estAttaquable());
    }

    @Test
    void test_addZombie() {
        int numTest = 4;
        Lieu place = new Lieu(numTest);
        place.addZombie();
        assertEquals(place.getNbZombies(), 1);

    }

    @Test
    void test_addZombie2() {
        int numTest = 4;
        Lieu place = new Lieu(numTest);
        place.setNbZombies(2);
        assertEquals(place.getNbZombies(), 2);
    }

    @Test
    void test_toString() {
        int numTest = 4;
        String nameTest = "Parking";
        Lieu place = new Lieu(numTest);
        assertEquals(place.toString(), nameTest);
    }

    @Test
    void test_isFull() {
        int numTest = 3;
        Lieu place = new Lieu(numTest);
        LaBlonde maBlonde = new LaBlonde(new Joueur("P8", "127.0.0.1", Couleur.NOIR, 1030, "popol"));
        LaBrute maBrute = new LaBrute(new Joueur("P9", "127.0.0.1", Couleur.NOIR, 1031, "tata lili"));
        LaFillette maFillette = new LaFillette(new Joueur("P10", "127.0.0.1", Couleur.NOIR, 1032, "toutou"));
        LaFillette maFillette1 = new LaFillette(new Joueur("P11", "127.0.0.1", Couleur.NOIR, 1033, "toutou"));
        place.addPersonnage(maBlonde);
        place.addPersonnage(maBrute);
        place.addPersonnage(maFillette);
        assertFalse(place.isFull());
        place.addPersonnage(maFillette1);
        assertTrue(place.isFull());
        assertThrows(RuntimeException.class, () -> {
            LaBlonde maBlondeError = new LaBlonde(new Joueur("P12", "127.0.0.1", Couleur.NOIR, 1034, "yep"));
            place.addPersonnage(maBlondeError);
        });

    }
}
