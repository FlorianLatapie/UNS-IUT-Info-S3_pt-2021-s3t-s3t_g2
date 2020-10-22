package jeu;

import java.util.ArrayList;
import java.util.HashMap;

import jeu.Joueur;
import jeu.Lieu;
import jeu.Personnage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LieuTest {

	@Test
	void testLieu_Constructor() {
		String nameTest = "Parking";
		int numTest = 4;
		int nbPlacesTest = 24;
		int nbZombiesTest = 3;
		ArrayList<Personnage> characters = new ArrayList<Personnage>();
		
		Lieu place = new Lieu(numTest);
		Assertions.assertNotNull(place);
		ArrayList<Personnage> perso = place.getPersonnage(); 
		ArrayList<Joueur> n = new ArrayList<Joueur>();
	
		Assertions.assertTrue(place.estAttaquable());
		Assertions.assertTrue(place.isOuvert());
		Assertions.assertTrue(place.isFull());
}

	
}