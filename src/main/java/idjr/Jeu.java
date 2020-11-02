package idjr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * <h1>Le plateau de jeu</h1>
 *
 * @author Alex
 * @author Aurélien
 * @version 0.1
 * @since 04/10/2020
 */
public class Jeu {
	private HashMap<Integer, Lieu> lieux;
	private HashMap<Integer, Joueur> joueurs;

	/**
	 * @param listeJoueursInitiale La liste des joueurs initiaux
	 */
	public Jeu() {
		lieux = new HashMap<>();
		joueurs = new HashMap<>();
		initLieu();
	}

	/**
	 * Initialise les lieux du jeu
	 */
	private void initLieu() {
		Lieu toilettes = new Lieu(1);
		Lieu cachou = new Lieu(2);
		Lieu megatoys = new Lieu(3);
		Lieu parking = new Lieu(4);
		Lieu pCsecu = new Lieu(5);
		Lieu supermarche = new Lieu(6);

		if (joueurs.size() < 4)
			cachou.setOuvert(false);

		lieux.put(1, toilettes);
		lieux.put(2, cachou);
		lieux.put(3, megatoys);
		lieux.put(4, parking);
		lieux.put(5, pCsecu);
		lieux.put(6, supermarche);
	}

	public HashMap<Integer, Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Initialise les joueurs du jeu
	 */
	public void initJoueurs(List<Joueur> listeJoueursInitiale) {
		for (int i = 0; i < listeJoueursInitiale.size(); i++)
			joueurs.put(i, listeJoueursInitiale.get(i));
		if (joueurs.size() < 4)
			for (int i = 0; i < joueurs.size(); i++) {
				HashMap<Integer, Personnage> dp = new HashMap<>();
				dp.put(7, new Personnage(joueurs.get(i)));
				dp.put(5, new Personnage(joueurs.get(i)));
				dp.put(3, new Personnage(joueurs.get(i)));
				dp.put(1, new Personnage(joueurs.get(i)));
				joueurs.get(i).setPersonnages(dp);
			}
		else
			for (int i = 0; i < joueurs.size(); i++) {
				HashMap<Integer, Personnage> dp = new HashMap<>();
				dp.put(7, new Personnage(joueurs.get(i)));
				dp.put(5, new Personnage(joueurs.get(i)));
				dp.put(3, new Personnage(joueurs.get(i)));
				joueurs.get(i).setPersonnages(dp);
			}
	}

	/**
	 * Lance l'ajout des zombies dans les lieux du jeu et ferme si nécessaire les
	 * lieux
	 *
	 * @param listeInt id des lieux où les nouveaux zombie arrivent
	 */
	public void entreZombie(List<Integer> listeInt) {
		for (int i = 0; i < listeInt.size(); i++) {
			lieux.get(listeInt.get(i)).addZombie();
		}
	}

	/**
	 * Deplace le personnage du joueur au lieu choisi
	 *
	 * @param joueur     le joueur dont le personnage est déplacé
	 * @param choixPerso le personnage qui est déplacé
	 * @param dest       le lieu de destination du personnage
	 */
	public void deplacePerso(Joueur joueur, Integer choixPerso, Integer dest) {
		for (int i = 0; i < joueurs.size(); i++) {
			if (joueurs.get(i).equals(joueur)) {
				int l = joueurs.get(i).getPersonnages().get(choixPerso).getMonLieu().getNum();
				joueurs.get(i).getPersonnages().get(choixPerso).changerDeLieux(lieux.get(dest));
				this.lieux.get(dest).addPersonnage(joueurs.get(i).getPersonnages().get(choixPerso));
				this.lieux.get(l).getPersonnage().remove(joueurs.get(i).getPersonnages().get(choixPerso));
			}
		}

	}

	/**
	 * Place le personnage du joueur au lieu choisi
	 *
	 * @param joueur     le joueur dont le personnage est placé
	 * @param choixPerso le personnage qui est déplacé
	 * @param dest       le lieu de destination du personnage
	 */
	public void placePerso(Joueur joueur, Integer choixPerso, Integer dest) {
		for (int i = 0; i < joueurs.size(); i++) {
			if (joueurs.get(i).equals(joueur)) {
				joueurs.get(i).getPersonnages().get(choixPerso).changerDeLieux(lieux.get(dest));
				this.lieux.get(dest).addPersonnage(joueurs.get(i).getPersonnages().get(choixPerso));
			}
		}

	}

	/**
	 * Supprime le personnage du jeu
	 *
	 * @param joueur     le joueur possedant le personnage sacrifié
	 * @param choixPerso le personnage qui va être sacrifié
	 */
	public void sacrifie(Joueur joueur, Integer choixPerso) {
		for (int i = 0; i < joueurs.size(); i++) {
			if (joueurs.get(i).equals(joueur)) {
				int l = joueurs.get(i).getPersonnages().get(choixPerso).getMonLieu().getNum();
				this.lieux.get(l).getPersonnage().remove(joueurs.get(i).getPersonnages().get(choixPerso));
				joueurs.get(i).getPersonnages().remove(choixPerso);
			}
		}
	}

	/**
	 * Ferme les lieux a envahie
	 */
	public void fermerLieu() {
		for (int i = 1; i < 7; i++) {
			if (i != 4 && this.lieux.get(i).getNbZombies() >= 8 && this.lieux.get(i).getPersonnage().isEmpty()) {
				this.lieux.get(i).setOuvert(false);
				this.lieux.get(i).setNbZombies(0);
			}
		}
	}

	public void fermerLieu(List<Integer> l) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) != 4 && this.lieux.get(l.get(i)).getNbZombies() >= 8 && this.lieux.get(l.get(i)).getPersonnage().isEmpty()){
				this.lieux.get(l.get(i)).setOuvert(false);
				this.lieux.get(l.get(i)).setNbZombies(0);
			}
		}
	}

	public List<Integer> pionChoixDispo(Joueur j, int dest){
		List<Integer> l = new ArrayList<>();
		for(Entry<Integer, Personnage> p: j.getPersonnages().entrySet()) {
			if (p.getValue().getMonLieu().getNum() != dest) {
				l.add(p.getKey());
			}
		}
		return l;
	}

	public List<Integer> pionSacrDispo(Joueur j, int dest){
		List<Integer> l = new ArrayList<>();
		for(Entry<Integer, Personnage> p: j.getPersonnages().entrySet()) {
			if (p.getValue().getMonLieu().getNum() == dest) {
				l.add(p.getKey());
			}
		}
		return l;
	}


	private boolean toutPers(Joueur j, int a) {
		for (Personnage i : j.getPersonnages().values()) {
			if (i.getMonLieu().getNum() != a) {
				return false;
			}
		}
		return true;
	}

	public HashMap<Integer, Lieu> getLieux() {
		return lieux;
	}

	public List<Integer> choixLieudispo(Joueur j) {
		List<Integer> lieux = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			if (!this.lieux.get(i).isFull() && this.lieux.get(i).isOuvert() && !toutPers(j, i)) {
				lieux.add(i);
			}
		}
		return lieux;
	}

	public List<Integer> choixLieudispo() {
		List<Integer> lieux = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			if (!this.lieux.get(i).isFull() && this.lieux.get(i).isOuvert()) {
				lieux.add(i);
			}
		}
		return lieux;
	}

	public List<Integer> getLieuxOuvert(){
		List<Integer> l = new ArrayList<>();
		for (Lieu lieu: this.lieux.values()) {
			if (lieu.isOuvert()) {
				l.add(lieu.getNum());
			}
		}
		return l;
	}

	/**
	 * Obtient l'etat du jeu
	 *
	 * @return le plateau de jeu
	 */
	public String afficheJeu() {
		StringBuilder tmp = new StringBuilder();
		for (int i = 1; i < 7; i++) {
			tmp.append(this.lieux.get(i)).append(":\n");
			for (int a = 0; a < this.lieux.get(i).getPersonnage().size(); a++) {
				tmp.append("(").append(this.lieux.get(i).getPersonnage().get(a).getJoueur()).append(") ")
						.append(this.lieux.get(i).getPersonnage().get(a)).append("\n");
			}
			tmp.append("Nombre de place-> ").append(this.lieux.get(i).getNbPlaces()).append("\n");
			tmp.append("Nombre de Zombie-> ").append(this.lieux.get(i).getNbZombies()).append("\n\n");
		}

		return tmp.toString();
	}
}
