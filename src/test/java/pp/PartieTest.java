package pp;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class PartieTest {
	Joueur j1 = new Joueur(0, InetAddress.getByName("127.0.0.1"), 1025, "Bob");
	Joueur j2 = new Joueur(1, InetAddress.getByName("127.0.0.1"), 1024, "Lea");
	List<Joueur> lj = new ArrayList<>(Arrays.asList(j1, j2));
	Partie j = new Partie(lj);

	PartieTest() throws UnknownHostException {
	}

	@Test
	void testPartie_Constructor() {
		HashMap<Integer, Joueur> dj = new HashMap<>();
		dj.put(0, j1);
		dj.put(1, j2);
		assertEquals(j.getJoueurs(), dj);
	}

	@Test
	void testPartie_Initializer() {
		assertTrue(j.getLieux().containsKey(1));
		assertTrue(j.getLieux().containsKey(6));
		assertEquals(6, j.getLieux().size());
		// assertEquals(30,j.getCartes().size());
		assertEquals(j.getJoueurs().get(0).getNom(), j1.getNom());
		assertEquals(j.getJoueurs().get(1).getNom(), j2.getNom());
		assertEquals(2, j.getJoueurs().size());
	}

	@Test
	void testPartie_PlusieurJoueur() throws UnknownHostException {
		HashMap<Integer, Joueur> dj = new HashMap<>();
		Joueur j3 = new Joueur(2, InetAddress.getByName("127.0.0.1"), 1024, "Jo");
		Joueur j4 = new Joueur(3, InetAddress.getByName("127.0.0.1"), 1025, "Louis");
		Joueur j5 = new Joueur(4, InetAddress.getByName("127.0.0.1"), 1026, "Julie");
		List<Joueur> lj2 = new ArrayList<>(Arrays.asList(j1, j2, j3, j4, j5));
		Partie jeu2 = new Partie(lj2);
		dj.put(0, j1);
		dj.put(1, j2);
		dj.put(2, j3);
		dj.put(3, j4);
		dj.put(4, j5);
		assertEquals(jeu2.getJoueurs(), dj);
		j.getEtatPartie();
	}

	@Test
	void testPartie_FouilleCamion() {
		// assertEquals(j.getCartes().size(),27);
		assertEquals(j.getCartes().size(), 0);
	}

	@Test
	void testPartie_ResultatChefVigile() {
		j.resultatChefVigile(j1);
		j.getEtatPartie();
		assertTrue(j.getJoueurs().get(0).isChefDesVigiles());
		assertFalse(j.getJoueurs().get(1).isChefDesVigiles());
		j.resultatChefVigile();
		assertFalse(j.getJoueurs().get(0).isChefDesVigiles());
		assertFalse(j.getJoueurs().get(1).isChefDesVigiles());
	}

	@Test
	void testPartie_EntreZombie() {
		assertEquals(0, j.getLieux().get(1).getNbZombies());
		j.entreZombie(new ArrayList<Integer>(Arrays.asList(1, 2, 2)));
		assertEquals(1, j.getLieux().get(1).getNbZombies());
		assertEquals(2, j.getLieux().get(2).getNbZombies());

	}

	@Test
	void testPartie_LastAttaqueZombie() {

		j.getLieux().get(2).addPersonnage(j.getJoueurs().get(0).getPersonnages().get(0));
		j.lastAttaqueZombie();
		assertEquals(2, j.getLieux().get(2).getNbZombies());
		j.getLieux().get(1).addPersonnage(j.getJoueurs().get(0).getPersonnages().get(0));
		j.lastAttaqueZombie();
		assertEquals(0, j.getLieux().get(1).getNbZombies());
	}

	@Test
	void testPartie_DeplacePerso() {
		j.placePerso(j.getJoueurs().get(0), 0, 2);
		assertEquals(1, j.getLieux().get(2).getPersonnage().size());
		j.deplacePerso(j.getJoueurs().get(0), 0, 1);
		assertEquals(0, j.getLieux().get(2).getPersonnage().size());
		assertEquals(1, j.getLieux().get(1).getPersonnage().size());
	}

	@Test
	void testPartie_Sacrifie() {
		j.placePerso(j.getJoueurs().get(0), 0, 2);
		j.placePerso(j.getJoueurs().get(0), 1, 2);
		j.getEtatPartie();
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
	void testPartie_FermerLieu() {
		assertTrue(j.getLieux().get(1).isOuvert());
		j.getLieux().get(1).setNbZombies(10);
		assertTrue(j.getLieux().get(1).isOuvert());
		j.fermerLieu();
		assertFalse(j.getLieux().get(1).isOuvert());
	}

}
