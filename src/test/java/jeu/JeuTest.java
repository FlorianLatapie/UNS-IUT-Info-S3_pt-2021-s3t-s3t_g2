package jeu;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class JeuTest {
	Joueur j1 = new Joueur(Color.red, "Bob");
	Joueur j2 = new Joueur(Color.blue, "Lea");

	List<Joueur> lj = new ArrayList<>(Arrays.asList(j1, j2));

	Jeu j = new Jeu(lj);

	@Test
	void testJeu_Constructor() {
		HashMap<Integer, Joueur> dj = new HashMap<>();
		dj.put(0, j1);
		dj.put(1, j2);
		assertEquals(j.getJoueurs(), dj);
	}

	@Test
	void testJeu_Initializer() {
		assertTrue(j.getLieux().containsKey(1));
		assertTrue(j.getLieux().containsKey(6));
		assertEquals(6, j.getLieux().size());
		// assertEquals(30,j.getCartes().size());
		assertEquals(j.getJoueurs().get(0).getNom(), j1.getNom());
		assertEquals(j.getJoueurs().get(1).getNom(), j2.getNom());
		assertEquals(2, j.getJoueurs().size());
	}

	@Test
	void testJeu_PlusieurJoueur() {
		HashMap<Integer, Joueur> dj = new HashMap<>();
		Joueur j3 = new Joueur(Color.green, "Jo");
		Joueur j4 = new Joueur(Color.orange, "Louis");
		Joueur j5 = new Joueur(Color.cyan, "Julie");
		List<Joueur> lj2 = new ArrayList<>(Arrays.asList(j1, j2, j3, j4, j5));
		Jeu jeu2 = new Jeu(lj2);
		dj.put(0, j1);
		dj.put(1, j2);
		dj.put(2, j3);
		dj.put(3, j4);
		dj.put(4, j5);
		assertEquals(jeu2.getJoueurs(), dj);
		j.afficheJeu();
	}

	@Test
	void testJeu_FouilleCamion() {
		j.fouilleCamion(null, null, null, null, null);
		// assertEquals(j.getCartes().size(),27);
		assertEquals(j.getCartes().size(), 0);
	}

	@Test
	void testJeu_ResultatChefVigile() {
		j.resultatChefVigile(j1);
		j.afficheJeu();
		assertTrue(j.getJoueurs().get(0).isChefDesVigiles());
		assertFalse(j.getJoueurs().get(1).isChefDesVigiles());
		j.resultatChefVigile();
		assertFalse(j.getJoueurs().get(0).isChefDesVigiles());
		assertFalse(j.getJoueurs().get(1).isChefDesVigiles());
	}

	@Test
	void testJeu_EntreZombie() {
		assertEquals(0, j.getLieux().get(1).getNbZombies());
		j.entreZombie(new ArrayList<Integer>(Arrays.asList(1, 2, 2)));
		assertEquals(1, j.getLieux().get(1).getNbZombies());
		assertEquals(2, j.getLieux().get(2).getNbZombies());

	}

	@Test
	void testJeu_LastAttaqueZombie() {

		j.getLieux().get(2).addPersonnage(j.getJoueurs().get(0).getPersonnages().get(0));
		j.lastAttaqueZombie();
		assertEquals(2, j.getLieux().get(2).getNbZombies());
		j.getLieux().get(1).addPersonnage(j.getJoueurs().get(0).getPersonnages().get(0));
		j.lastAttaqueZombie();
		assertEquals(0, j.getLieux().get(1).getNbZombies());
	}

	@Test
	void testJeu_DeplacePerso() {
		j.placePerso(j.getJoueurs().get(0), 0, 2);
		assertEquals(1, j.getLieux().get(2).getPersonnage().size());
		j.deplacePerso(j.getJoueurs().get(0), 0, 1);
		assertEquals(0, j.getLieux().get(2).getPersonnage().size());
		assertEquals(1, j.getLieux().get(1).getPersonnage().size());
	}

	@Test
	void testJeu_Sacrifie() {
		j.placePerso(j.getJoueurs().get(0), 0, 2);
		j.placePerso(j.getJoueurs().get(0), 1, 2);
		j.afficheJeu();
		assertEquals(4, j.getJoueurs().get(0).getPersonnages().size());
		assertEquals(2, j.getLieux().get(2).getPersonnage().size());
		j.sacrifie(j.getJoueurs().get(0), 0);
		assertEquals(3, j.getJoueurs().get(0).getPersonnages().size());
		assertEquals(1, j.getLieux().get(2).getPersonnage().size());
		j.sacrifie(j.getJoueurs().get(0), 1);
		assertEquals(2, j.getJoueurs().get(0).getPersonnages().size());
		assertEquals(0, j.getLieux().get(2).getPersonnage().size());
	}

	@Test
	void testJeu_FermerLieu() {
		assertTrue(j.getLieux().get(1).isOuvert());
		j.getLieux().get(1).setNbZombies(10);
		assertTrue(j.getLieux().get(1).isOuvert());
		j.fermerLieu();
		assertFalse(j.getLieux().get(1).isOuvert());
	}

}
