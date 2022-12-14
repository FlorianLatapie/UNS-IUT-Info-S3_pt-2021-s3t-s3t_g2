package bot.partie;

import reseau.type.CarteType;
import reseau.type.Couleur;

import java.util.*;

import static java.lang.System.out;

/**
 * <h1>La classe partie</h1>.
 *
 * @author Alex
 * @author Aurélien
 * @version 1
 * @since 04/10/2020
 */
public class Partie {

	/** Dictionnaire des lieux identifiés par un entier. */
	HashMap<Integer, Lieu> lieux;

	/** Dictionnaire des joueurs identifiés par un entier. */
	HashMap<Couleur, Joueur> joueurs;

	/** Liste des cartes. */
	List<CarteType> cartes;

	/** Booléen indiquant si un nouveauChef à été élu. */
	boolean nouveauChef;

	/** Couleur indiquant le gagnant */
	Couleur gagnant;

	/** Booléen indiquant si il y a eu une egalite. */
	boolean egalite;

	/**
	 * Instantie une nouvelle partie.
	 *
	 * @param listeJoueursInitiale La liste des joueurs initiaux
	 */
	public Partie(List<Couleur> couleurs) {
		lieux = new HashMap<>();
		joueurs = new HashMap<>();
		cartes = new ArrayList<>();
		nouveauChef = false;
		initCarte();
		for (int i = 0; i < couleurs.size(); i++)
			joueurs.put(couleurs.get(i), new Joueur(couleurs.get(i)));
		initJoueurs();
		initLieu();
	}

	public Partie(HashMap<Integer, Lieu> lieux, HashMap<Couleur, Joueur> joueurs, boolean nouveauChef, Couleur gagnant,
			boolean egalite) {
		super();
		this.lieux = lieux;
		this.joueurs = joueurs;
		this.nouveauChef = nouveauChef;
		this.gagnant = gagnant;
		this.egalite = egalite;
	}

	/**
	 * Initialise les cartes du
	 */
	private void initCarte() {

		for (CarteType c : CarteType.values())
			cartes.add(c);
		cartes.add(CarteType.SPR);
		cartes.add(CarteType.SPR);
		cartes.add(CarteType.MEN);
		cartes.add(CarteType.MEN);
		cartes.add(CarteType.MAT);
		cartes.add(CarteType.MAT);
		cartes.add(CarteType.CAC);
		cartes.add(CarteType.CAC);
		cartes.add(CarteType.CDS);
		cartes.add(CarteType.CDS);

		Collections.shuffle(cartes);

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
		cachou.setOuvert(joueurs.size() >= 4);
		lieux.put(1, toilettes);
		lieux.put(2, cachou);
		lieux.put(3, megatoys);
		lieux.put(4, parking);
		lieux.put(5, pCsecu);
		lieux.put(6, supermarche);
	}

	/**
	 * Initialise les joueurs du
	 */
	private void initJoueurs() {
		if (joueurs.size() < 4)
			for (Joueur j : joueurs.values()) {
				HashMap<Integer, Personnage> dp = new HashMap<>();
				dp.put(7, new Personnage(j, 7, 1, 1));
				dp.put(5, new Personnage(j, 5, 2, 1));
				dp.put(3, new Personnage(j, 3, 1, 2));
				dp.put(1, new Personnage(j, 1, 1, 1));
				j.setPersonnages(dp);
			}
		else
			for (Joueur j : joueurs.values()) {
				HashMap<Integer, Personnage> dp = new HashMap<>();
				dp.put(7, new Personnage(j, 7, 1, 1));
				dp.put(5, new Personnage(j, 5, 2, 1));
				dp.put(3, new Personnage(j, 3, 1, 2));
				j.setPersonnages(dp);
			}
	}

	/**
	 * 
	 * 
	 * @param lieu the lieu
	 * @return the joueur
	 */
	// TODO
	public Joueur voteJoueur(int lieu) {
		// gestion des cartes
		List<Couleur> couleurs = lieux.get(lieu).afficheJoueurSurLieu();
		ArrayList<Joueur> joueurList = new ArrayList<>();
		for (Couleur c : couleurs)
			for (Joueur j : joueurs.values())
				if (j.getCouleur().equals(c))
					joueurList.add(j);
		int rnd = new Random().nextInt(joueurList.size());
		return joueurList.get(rnd);
	}

	/**
	 * Applique les résultats de la fouille du camion : attribut la carte piocheur
	 * au piocheur, la carte receveur au receveur et sort les 3 cartes du
	 *
	 * @param piocheur      le piocheur
	 * @param receveur      le receveur
	 * @param cartePiocheur la carte que le piocheur a choisi de garder
	 * @param carteReceveur la carte que le piocheur a choisi de donner au receveur
	 * @param carteDefause  la carte que le piocheur n'a pas attribuer
	 */
	// TODO
	public void fouilleCamion(Joueur piocheur, Joueur receveur, CarteType cartePiocheur, CarteType carteReceveur,
			CarteType carteDefause) {
		/*
		 * for (int i = 0; i < joueurs.size(); i++) { if
		 * (joueurs.get(i).equals(piocheur))
		 * joueurs.get(i).getCartes().add(cartePiocheur); if
		 * (joueurs.get(i).equals(receveur))
		 * joueurs.get(i).getCartes().add(carteReceveur); }
		 * cartes.remove(cartePiocheur); cartes.remove(carteDefause);
		 * cartes.remove(carteReceveur);
		 */
	}

	/**
	 * Retourne la liste des index des destinations valides. Une destination valide
	 * est un lieu sur lequel, il est possible de placer un personnage. Pour chaque
	 * résulatat de dé, si le résultat du dé est valide il est ajouté a la liste. Si
	 * les résultat des dés ne sont pas valides alors toutes les destinations
	 * valides de la partie sont ajouté à la liste. Puis la liste est renvoyée.
	 *
	 * @param destination1 résultat du 1er dé
	 * @param destination2 résultat du 2ème dé
	 * @return la liste des index des destination valides
	 */
	public List<Integer> getDestinationPossible(int destination1, int destination2) {
		List<Integer> destinationPossible = new ArrayList<>();
		if (lieux.get(destination1).isFull() && lieux.get(destination2).isFull()) {
			for (Lieu l : lieux.values())
				if (!l.isFull() && l.isOuvert())
					destinationPossible.add(l.getNum());
		} else if (lieux.get(destination1).isFull() && !lieux.get(destination2).isFull()) {
			destinationPossible.add(destination2);
		} else if (!lieux.get(destination1).isFull() && lieux.get(destination2).isFull()) {
			destinationPossible.add(destination1);
		} else {
			destinationPossible.add(destination1);
			destinationPossible.add(destination2);
		}

		return destinationPossible;
	}

	/**
	 * Retourne le nombre de place disponible dans l'ensemble des lieux de la
	 * partie.
	 *
	 * @return le nombre de place disponible dans l'ensemble des lieux de la partie.
	 */
	public int nombrePlaceDisponible() {
		int nbrPlaceDispo = 0;
		for (Lieu l : lieux.values())
			nbrPlaceDispo += l.getPersonnage().size();
		return nbrPlaceDispo;
	}

	/**
	 * Retourne la liste des points des personnages non placés du joueur en
	 * parametre.
	 *
	 * @param joueur Le joueur cible
	 * @return la liste des personnages non placés du joueur en parametre.
	 */
	public List<Integer> getPersonnageAPlace(Joueur joueur) {
		List<Integer> persoNonPlace = new ArrayList<>();
		for (Personnage personnage : joueur.getPersonnages().values()) {
			if (personnage.getMonLieu() == 0) {
				persoNonPlace.add(personnage.getPoint());
			}
		}
		return persoNonPlace;
	}

	/**
	 * Retourne un dictionnaire(Map) qui pour chaque personnage du joueur passe en
	 * parametre associe une liste d'entier qui correspond aux index des lieux
	 * disponibles
	 *
	 * @param joueur Le joueur cible
	 * @return un dictionnaire(Map) qui pour chaque personnage du joueur passe en
	 *         parametre associe une liste d'entier qui correspond aux index des
	 *         lieux disponibles
	 */
	public Map<Integer, List<Integer>> getAllPersoPossible(Joueur joueur) {
		HashMap<Integer, List<Integer>> allPersoPossible = new HashMap<>();
		for (Personnage personnage : joueur.getPersonnages().values()) {
			List<Integer> destTmp = getPersoDestPossible(personnage);
			if (!destTmp.isEmpty())
				allPersoPossible.put(personnage.getPoint(), destTmp);
		}
		return allPersoPossible;
	}

	/**
	 * Retourne la liste des lieux dans lesquel le personnage passe en parametre
	 * peut se deplacer.
	 *
	 * @param personnage Le personnage cible
	 * @return la liste des lieux dans lequel le personnage passe en paraletre peut
	 *         se deplacer.
	 */
	public List<Integer> getPersoDestPossible(Personnage personnage) {
		List<Integer> destinations = new ArrayList<>();
		for (Lieu l : lieux.values())
			if (personnage.getMonLieu() != l.getNum() && l.isOuvert() && !l.isFull())
				destinations.add(l.getNum());
		return destinations;
	}

	/**
	 * Affecte le grade chef des vigiles au joueur passe en parametre et supprime ce
	 * grade de tout les autres joueurs.
	 *
	 * @param nouveauChef le joueur élu en tant que chef des vigiles
	 */
	public void resultatChefVigile(Joueur nouveauChef) {
		for (Joueur joueur : joueurs.values()) {
			joueur.setChefDesVigiles(joueur.equals(nouveauChef));
		}
	}

	/**
	 * Supprime le grade de chef des vigiles à tous les joueurs.
	 */
	public void resultatChefVigile() {
		for (Joueur joueur : joueurs.values()) {
			joueur.setChefDesVigiles(false);
		}
	}

	/**
	 * Retourne la liste des index des lieux fermés
	 *
	 * @return la liste des index des lieux fermés
	 */
	public List<Integer> getLieuxFermes() {
		List<Integer> lieuxFerme = new ArrayList<>();
		for (Lieu lieu : lieux.values())
			if (!lieu.isOuvert())
				lieuxFerme.add(lieu.getNum());
		return lieuxFerme;
	}

	/**
	 * Retourne la liste des index des lieux ouverts
	 *
	 * @return la liste des index des lieux ouverts
	 */
	public List<Integer> getLieuxOuverts() {
		List<Integer> lieuxOuvert = new ArrayList<>();
		for (Lieu lieu : lieux.values())
			if (lieu.isOuvert())
				lieuxOuvert.add(lieu.getNum());
		return lieuxOuvert;
	}

	/**
	 * Retourne le chef des vigiles s'il y en a un sinon retourne null.
	 *
	 * @return le chef des vigiles s'il y en a un sinon retourne null.
	 */
	public Joueur getChefVIgile() {
		for (Joueur joueur : this.getJoueurs().values()) {
			if (joueur.isChefDesVigiles()) {
				return joueur;
			}
		}
		return null;
	}

	/**
	 * Ajoute les zombies dans les lieux dont les index sont dans la liste passe en
	 * parametre et ferme les lieux qui doivent l'être.
	 *
	 * @param listeIndexLieux Liste des index des lieux où les nouveaux zombies sont
	 *                        ajoutés.
	 */
	public void entreZombie(List<Integer> listeIndexLieux) {
		for (Integer index : listeIndexLieux) {
			lieux.get(index).addZombie();
		}
	}

	/**
	 * Retourne la liste des nombres de zombies des lieux ouverts
	 *
	 * @return la liste des nombres de zombies des lieux ouverts
	 */
	public List<Integer> getNbZombieLieux() {
		List<Integer> nbZombieLieux = new ArrayList<>();
		for (Lieu lieu : lieux.values())
			if (lieu.isOuvert())
				nbZombieLieux.add(lieu.getNbZombies());
		return nbZombieLieux;
	}

	/**
	 * Retourne la liste des nombres de poins des lieux.
	 *
	 * @return la liste des nombres de poins des lieux.
	 */
	public List<Integer> getNbPionLieux() {
		List<Integer> nbPionLieux = new ArrayList<>();
		for (Lieu lieu : lieux.values())
			if (lieu.isOuvert())
				nbPionLieux.add(lieu.getPersonnage().size());
		return nbPionLieux;
	}

	/**
	 * Ajoute un zombie au lieu qui a le plus de blondes et un zombie au lieu qui a
	 * le plus de personnages. Puis retourne la liste des index des lieux où ont été
	 * ajouté les zombies
	 *
	 * @return la liste des index des lieux où ont été ajouté les zombies
	 */
	public List<Integer> lastAttaqueZombie() {
		List<Integer> nb = new ArrayList<>();
		int idLieuP = 1;
		int idLieuB = 1;
		boolean maxP = true;
		boolean maxB = true;
		for (int i = 2; i < 7; i++) {
			if (lieux.get(i).getPersonnage().size() > lieux.get(idLieuP).getPersonnage().size()) {
				idLieuP = i;
			} else if (lieux.get(i).getPersonnage().size() == lieux.get(idLieuP).getPersonnage().size())
				maxP = false;
			if (lieux.get(i).getBlonde().size() > lieux.get(idLieuB).getBlonde().size()) {
				idLieuB = i;
			} else if (lieux.get(i).getBlonde().size() == lieux.get(idLieuB).getBlonde().size())
				maxB = false;
		}
		if (maxP) {
			lieux.get(idLieuP).addZombie();
			nb.add(idLieuP);
		} else
			nb.add(0);
		if (maxB) {
			lieux.get(idLieuB).addZombie();
			nb.add(idLieuB);
		} else
			nb.add(0);
		return nb;
	}

	/**
	 * Deplace le personnage du joueur au lieu choisi.
	 *
	 * @param joueur    le joueur dont le personnage est déplacé
	 * @param choixPion le personnage qui est déplacé
	 * @param dest      le lieu de destination du personnage
	 */
	public void deplacePerso(Couleur couleur, Integer choixPion, Integer destination) {
		for (Joueur joueur : joueurs.values()) {
			if (joueur.getCouleur().equals(couleur)) {
				int dest = destination;
				if (lieux.get(dest).isFull())
					dest = 4;
				System.out.println(choixPion);
				System.out.println(joueur);
				System.out.println(joueur.getPersonnages());
				System.out.println(joueur.getPersonnages().get(choixPion));
				System.out.println(joueur.getPersonnages().get(choixPion).getMonLieu());
				int idPosActuel = joueur.getPersonnages().get(choixPion).getMonLieu();
				joueur.getPersonnages().get(choixPion).changerDeLieux(dest);
				lieux.get(idPosActuel).getPersonnage().remove(joueur.getPersonnages().get(choixPion));
				lieux.get(dest).addPersonnage(joueur.getPersonnages().get(choixPion));

			}
		}
	}

	/**
	 * Place le personnage du joueur au lieu choisi.
	 *
	 * @param couleur    le joueur dont le personnage est placé
	 * @param choixPerso le personnage qui est déplacé
	 * @param dest       le lieu de destination du personnage
	 */
	public void placePerso(Couleur couleur, Integer choixPerso, Integer dest) {
		for (Joueur j : joueurs.values()) {
			if (j.getCouleur().equals(couleur)) {
				j.getPersonnages().get(choixPerso).changerDeLieux(dest);
				this.lieux.get(dest).addPersonnage(j.getPersonnages().get(choixPerso));
			}
		}
	}

	/**
	 * Supprime le personnage de la partie.
	 *
	 * @param joueur     le joueur possedant le personnage sacrifié
	 * @param choixPerso le personnage qui va être sacrifié
	 */
	public void sacrifie(Couleur couleur, Integer choixPerso) {
		lieux.get(joueurs.get(couleur).getPersonnages().get(choixPerso).getMonLieu()).getPersonnage()
				.remove(joueurs.get(couleur).getPersonnages().get(choixPerso));
		joueurs.get(couleur).getPersonnages().remove(choixPerso);
	}

	/**
	 * Ferme les lieux qui ont plus de 8, pas de personnage sauf si c'est le
	 * parking.
	 */
	public void fermerLieu() {
		for (Lieu lieu : lieux.values()) {
			if (lieu.getNum() != 4 && lieu.getNbZombies() >= 8 && lieu.getPersonnage().isEmpty()) {
				lieu.setOuvert(false);
				lieu.setNbZombies(0);
			}
		}
	}

	/**
	 * Retourne l'etat de la partie sous la forme d'un string.
	 *
	 * @return l'etat de la partie sous la forme d'un string.
	 */
	public String getEtatPartie() { // TODO ajouter les cartes
		StringBuilder etatPartie = new StringBuilder();
		for (Joueur joueur : joueurs.values()) {
			if (joueur.isChefDesVigiles()) {
				etatPartie.append(">>> ").append(joueur).append(" est le chef des Vigiles <<<\n");
			}
		}
		for (Lieu lieu : lieux.values()) {
			etatPartie.append(lieu).append(":\n");
			for (Personnage personnage : lieu.getPersonnage()) {
				etatPartie.append("(").append(joueurs.get(personnage.getCouleur())).append(") ").append(personnage)
						.append("\n");
			}
			etatPartie.append("Nombre de place-> ").append(lieu.getNbPlaces()).append("\n");
			etatPartie.append("Nombre de Zombie-> ").append(lieu.getNbZombies()).append("\n\n");
		}
		return etatPartie.toString();
	}

	/**
	 * Retourne le nombre de joueur mort.
	 *
	 * @return nombre de joueur mort
	 */
	public int getNombreJoueurMort() {
		int nbJoueurMort = 0;
		for (Joueur joueur : joueurs.values())
			if (!joueur.isEnVie())
				nbJoueurMort++;
		return nbJoueurMort;
	}

	/**
	 * Retourne la liste des couleurs des joueurs.
	 *
	 * @return la liste des couleurs des joueurs.
	 */
	public List<Couleur> getJoueursCouleurs() {
		List<Couleur> couleurs = new ArrayList<>();
		for (Joueur joueur : joueurs.values())
			couleurs.add(joueur.getCouleur());
		return couleurs;
	}

	/**
	 * Retourne le dictionnaire des joueurs.
	 *
	 * @return le dictionnaire des joueurs.
	 */
	public Map<Couleur, Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Actualise les joueurs morts : Si un joueur en vie n'a pas plus de personnage,
	 * il meurt. Puis retourne la liste des joueurs mort.
	 *
	 * @return liste des joueurs mort
	 */
	public List<Joueur> getJoueursMort() {
		List<Joueur> joueursMort = new ArrayList<Joueur>();
		for (Joueur j : joueurs.values()) {
			if (j.isEnVie() && j.getPersonnages().size() == 0) {
				j.setEnVie(false);
				joueursMort.add(j);
				out.println(j + " est mort!");
			}
		}
		return joueursMort;
	}

	/**
	 * Retourne la liste des lieux.
	 *
	 * @return la liste des lieux
	 */
	public Map<Integer, Lieu> getLieux() {
		return lieux;
	}

	/**
	 * Retourne nouveauChef.
	 *
	 * @return nouveauChef.
	 */
	public boolean getNewChef() {
		return nouveauChef;
	}

	/**
	 * Definie Chef des vigiles.
	 *
	 * @param c la couleur du nouceau chef.
	 */
	public void setChef(Couleur c) {
		for (Joueur j : joueurs.values()) {
			if (j.getCouleur().equals(c)) {
				j.setChefDesVigiles(true);
			} else {
				j.setChefDesVigiles(false);
			}
		}
	}

	/**
	 * Definie nouveauChef.
	 *
	 * @param nouveauChef l'état de nouveauChef à appliqué.
	 */
	public void setNewChef(boolean nouveauChef) {
		this.nouveauChef = nouveauChef;
	}

	public void setZombieSurLieu(Integer lieu, Integer nbz) {
		lieux.get(lieu).setNbZombies(nbz);
	}

	public void setPersoCache(Integer lieu, int size) {
		lieux.get(lieu).setNbPersoCache(size);
	}

	public void resetPersoCache() {
		for (Lieu l : lieux.values())
			l.setNbPersoCache(0);

	}

	public boolean estFini() {
		boolean isFinished = false;
		for (Couleur i : getJoueursCouleurs())
			if (getJoueurs().get(i).isEnVie() && getJoueurs().get(i).getPersonnages().size() == 0)
				getJoueurs().get(i).setEnVie(false);
		ArrayList<Integer> lieu = new ArrayList<>();
		int nbPerso = 0;
		for (Couleur i : getJoueursCouleurs())
			if (getJoueurs().get(i).isEnVie()) {
				nbPerso += getJoueurs().get(i).getPersonnages().size();
				for (Integer j : getJoueurs().get(i).getPersonnages().keySet())
					if (!lieu.contains(getJoueurs().get(i).getPersonnages().get(j).getMonLieu()))
						lieu.add(getJoueurs().get(i).getPersonnages().get(j).getMonLieu());
			}
		if ((lieu.size() < 2 && lieu.get(0) != 4) || (nbPerso <= 4 && getJoueurs().size() < 6)
				|| (nbPerso <= 6 && getJoueurs().size() == 6)) {
			int pointVainqueur = 0;
			ArrayList<Joueur> vainqueur = new ArrayList<>();
			for (Couleur i : getJoueursCouleurs()) {
				int point = 0;
				if (getJoueurs().get(i).isEnVie()) {
					for (Integer j : getJoueurs().get(i).getPersonnages().keySet())
						point += getJoueurs().get(i).getPersonnages().get(j).getPoint();
					if (point > pointVainqueur) {
						pointVainqueur = point;
						vainqueur.clear();
						vainqueur.add(getJoueurs().get(i));
					} else if (point == pointVainqueur)
						vainqueur.add(getJoueurs().get(i));
				}
			}
			isFinished = true;
			gagnant = vainqueur.get(new Random().nextInt(vainqueur.size())).getCouleur();
		}
		return isFinished;
	}

	public Couleur getGagnant() {
		return gagnant;
	}

	public boolean isEgalite() {
		return egalite;
	}

	public Partie copyOf() {
		HashMap<Integer, Lieu> lieuxCopy = new HashMap<Integer, Lieu>();
		HashMap<Couleur, Joueur> joueursCopy = new HashMap<Couleur, Joueur>();
		for (Lieu lieu : lieux.values()) {
			lieuxCopy.put(lieu.getNum(), lieu.copyOf());

		}

		for (Joueur joueur : joueurs.values()) {
			joueursCopy.put(joueur.getCouleur(), joueur.copyOf());

		}
		Partie partieCopy = new Partie(lieuxCopy, joueursCopy, nouveauChef, gagnant, egalite);
		for (Joueur joueur : partieCopy.getJoueurs().values())
			if (joueur.isEnVie())
				for (Personnage personnage : joueur.getPersonnages().values())
					partieCopy.placePerso(joueur.getCouleur(), personnage.getPoint(), personnage.getMonLieu());

		return partieCopy;

	}

	public ArrayList<Joueur> getJoueursSurLieu(Integer numlieu) {
		List<Couleur> couleurs = lieux.get(numlieu).afficheJoueurSurLieu();
		ArrayList<Joueur> joueurList = new ArrayList<>();
		for (Couleur c : couleurs)
			for (Joueur j : joueurs.values())
				if (j.getCouleur().equals(c))
					joueurList.add(j);
		return joueurList;
	}

	/*
	 * Retourne la liste des cartes
	 *
	 * @return la liste des cartes
	 */
	public List<CarteType> getCartes() {
		return cartes;
	}

	public void givecarte(Couleur c, CarteType carte) {
		joueurs.get(c).getCartes().add(carte);
		cartes.remove(carte);
	}

}
