package jeu;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

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
		assertTrue(place.getPersonnage() != null);

	}

	@Test
	void test_Lieu_Setters() {
		int numTest = 3;
		Lieu place = new Lieu(numTest);
		assertTrue(place.isOuvert());
		place.setOuvert(false);
		assertFalse(place.isOuvert());
		assertThrows(RuntimeException.class, () -> {
			LaBlonde maBlonde = new LaBlonde(new Joueur(Color.BLACK, "popol"));
			LaBrute maBrute = new LaBrute(new Joueur(Color.BLACK, "tata lili"));
			LaFillette maFillette = new LaFillette(new Joueur(Color.BLACK, "toutou"));
			LaFillette maFillette1 = new LaFillette(new Joueur(Color.BLACK, "toutou"));
			LaBrute maBrute1 = new LaBrute(new Joueur(Color.BLACK, "op"));
			place.setPersonnage(
					new ArrayList<Personnage>(Arrays.asList(maFillette, maBrute, maBlonde, maFillette1, maBrute1)));
		});
	}

	@Test
	void test_getBlonde() {
		int numTest = 4;
		Lieu place = new Lieu(numTest);
		LaBlonde maBlonde = new LaBlonde(new Joueur(Color.BLACK, "blonde"));
		place.setPersonnage(new ArrayList<Personnage>(Arrays.asList(maBlonde)));
		assertEquals(place.getBlonde().size(), 1);

	}

	@Test
	void test_getJoueurSurLieu() {
		int numTest = 4;
		Lieu place = new Lieu(numTest);
		Joueur Bob = new Joueur(Color.BLACK, "toto");
		LaBlonde maBlonde = new LaBlonde(Bob);
		place.setPersonnage(new ArrayList<Personnage>(Arrays.asList(maBlonde)));
		assertTrue(place.afficheJoueurSurLieu().add(Bob));
	}

	@Test
	void test_estAttaquable() {
		int numTest = 4;
		Lieu place = new Lieu(numTest);
		Joueur Bob = new Joueur(Color.BLACK, "titi");
		LaBlonde maBlonde = new LaBlonde(Bob);
		place.setPersonnage(new ArrayList<Personnage>(Arrays.asList(maBlonde)));
		assertFalse(place.estAttaquable());
		LaBrute maBrute = new LaBrute(Bob);
		place.setPersonnage(new ArrayList<Personnage>(Arrays.asList(maBrute)));
		place.addZombie(4);
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
		place.addZombie(2);
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
		LaBlonde maBlonde = new LaBlonde(new Joueur(Color.BLACK, "popol"));
		LaBrute maBrute = new LaBrute(new Joueur(Color.BLACK, "tata lili"));
		LaFillette maFillette = new LaFillette(new Joueur(Color.BLACK, "toutou"));
		LaFillette maFillette1 = new LaFillette(new Joueur(Color.BLACK, "toutou"));
		place.setPersonnage(new ArrayList<Personnage>(Arrays.asList(maFillette, maBrute, maBlonde)));
		assertFalse(place.isFull());
		place.addPersonnage(maFillette1);
		assertTrue(place.isFull());
		assertThrows(RuntimeException.class, () -> {
			LaBlonde maBlondeError = new LaBlonde(new Joueur(Color.BLACK, "yep"));
			place.addPersonnage(maBlondeError);
		});

	}
}
