package joueur;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import jeu.Joueur;
import jeu.LaBlonde;
import jeu.Personnage;

/**
 * Class JoueurTest
 * 
 * @author Maxime Lecerf
 * @author Emeric Maximil
 * @author Jehan Berthe
 * @since 24/10/2020
 */

class JoueurTest {
	Color c = Color.BLUE;
	String nom = "Joueur1";
	Joueur j = new Joueur(c, nom);
	LaBlonde b = new LaBlonde(j);

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
		ArrayList<Personnage> p = new ArrayList<>();
		p.add(b);

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
		j.setCouleur(Color.RED);
		assertNotSame(j.getCouleur(), c);
		assertSame(j.getCouleur(), Color.RED);

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