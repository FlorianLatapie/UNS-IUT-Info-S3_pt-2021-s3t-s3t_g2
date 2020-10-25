package jeu.personnage;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;


import jeu.*;

public class PersonnageTest {

    @Test
    void test_test_Personnage() {
        Color c = Color.BLUE;
        String nom = "Joueur1";
        Joueur j1 = new Joueur(c, nom);
        LaBlonde p1 = new LaBlonde(j1);
        p1.setEstVivant(true);
        assertEquals(p1.isEstVivant(), true);
        
        p1.setEstCache(true);
        assertEquals(p1.isEstCache(),true);
        
        p1.setNbrElection(3);
        assertEquals(p1.getNbrElection(),3);
        
        p1.setPoint(2);
        assertEquals(p1.getPoint(),2);
        
        
        
        assertEquals(p1.getNbrZretenu(),1);
        
        
        assertEquals(p1.getNbrVoixPourVoter(),1);
        
        Lieu newLieu = new Lieu(4);
        p1.changerDeLieux(newLieu);
        
        assertEquals(p1.getMonLieu(),newLieu);
        
        Color c2 = Color.RED;
        String nom2 = "Joueur2";
        Joueur j2 = new Joueur(c, nom);
        assertEquals(p1.getJoueur(),j1);
        p1.setJoueur(j2);
        assertEquals(p1.getJoueur(),j2);
        
        assertEquals(p1.getType(),p1.getType());
    }

}
