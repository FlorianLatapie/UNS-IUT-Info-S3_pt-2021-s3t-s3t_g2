package jeu.joueur;

import static org.junit.jupiter.api.Assertions.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import jeu.Joueur;
import jeu.LaBlonde;
import jeu.Personnage;
import reseau.type.Couleur;

/**
 * Class JoueurTest
 * 
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @since 24/10/2020
 */

class JoueurTest {
	Couleur c = Couleur.BLEU;
	String nom = "Joueur1";
	Joueur j = new Joueur(0, InetAddress.getByName("127.0.0.1"),1024, nom);
	LaBlonde b = new LaBlonde(j);

	JoueurTest() throws UnknownHostException {
	}

	/**
	 * Test du contructeur d'un joueur
	 */
	@Test
	void testJoueurConstructor() {
		assertNotNull(j);
	}

	/**
	 * Tests des getters et des setters de la classe Joueur
	 */
	@Test
	void testJoueurGettersAndSetters() {
		j.setCouleur(c);
		HashMap<Integer, Personnage> p = new HashMap<>();
		p.put(0, b);

		/**
		 * Vérification des getters suite à la création d'un joueur
		 */
		assertSame(j.getCouleur(), c);
		assertTrue(j.isEnVie());
		assertNotNull(j.getPersonnages());
		assertNotNull(j.getCartes());
		assertSame(j.getNom(), nom);

		/**
		 * Modifications de la couleur
		 */
		assertSame(j.getCouleur(), c);
		j.setCouleur(Couleur.ROUGE);
		assertNotSame(j.getCouleur(), c);
			assertSame(j.getCouleur(), Couleur.ROUGE);

		/**
		 * Modification du statut enVie
		 */
		assertTrue(j.isEnVie());
		j.setEnVie(false);
		assertFalse(j.isEnVie());
		j.setEnVie(true);
		assertTrue(j.isEnVie());

		/**
		 * Modification de la liste des personnages
		 */
		assertEquals(0, j.getPersonnages().size());
		j.setPersonnages(p);
		assertEquals(1, j.getPersonnages().size());

		/**
		 * Modification du statut chef des vigiles
		 */
		j.setChefDesVigiles(true);
		assertTrue(j.isChefDesVigiles());
		j.setChefDesVigiles(false);
		assertFalse(j.isChefDesVigiles());
	}

	/**
	 * Test du toString() d'un joueur
	 */
	@Test
	void testJoueurToString() {
		assertSame(j.toString(), j.getNom());
	}
}