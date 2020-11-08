package pp.temp;

/**
 * <h1>Crée une carte action arme</h1>
 *
 * @author Alex
 * @author Remy
 * @author Florian
 * @version 0
 * @since 04/10/2020
 */
public class Arme extends CarteAction {
	private String nom;
	private int nbZTues;

	public Arme(String nom, int nbZTues) {
		super("Arme");
		this.nom = nom;
		this.nbZTues = nbZTues;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbZTues() {
		return nbZTues;
	}

	public void setNbZTues(int nbZTues) {
		this.nbZTues = nbZTues;
	}
}