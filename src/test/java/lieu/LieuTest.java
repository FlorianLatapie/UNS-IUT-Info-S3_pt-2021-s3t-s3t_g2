package lieu;

import java.util.ArrayList;
import java.util.HashMap;

import jeu.Joueur;
import jeu.Lieu;
import jeu.Personnage;

import org.junit.Assert;
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
		Assert.assertNotNull(place);
		ArrayList<Personnage> perso = place.getPersonnage(); 
		ArrayList<Joueur> n = new ArrayList<Joueur>();
	
		Assert.assertTrue(place.estAttaquable());
		Assert.assertTrue(place.isOuvert());
		Assert.assertTrue(place.isFull());
}

	
}