package pp.controleur;

import pp.*;
import pp.ihm.event.Initializer;
import reseau.socket.ControleurReseau;
import reseau.socket.TcpClient;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;
import reseau.type.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import static java.lang.System.out;

/**
 * <h1>La classe controleurJeu</h1>
 *
 * @author Alex
 * @author Aurelien
 * @author Sebastien
 * @version 0
 * @since 04/10/2020
 */

public class ControleurJeu {
	/* Réglage de la partie */
	private final String partieId; // "P" + Numero de la partie Ex : P9458
	private final int intPartieId;
	private final String nomPartie; // Nom de la partie
	private final int nbjtotal; // Nombre de joueurs total
	private final int nbjr; // Nombre de joueurs réels max
	private final int nbjv; // Nombre de joueurs virtuels max
	private int nbjractuel; // Nombre de joueurs réels actuellement connectés
	private int nbjvactuel; // Nombre de joueurs virtuels actuellement connectés
	private final int port;
	private int nextJoueurId;
	private int numeroTour = 1;

	// private final ControleurReseau ControleurReseau;
	private Partie jeu;
	private Thread coreThread;
	private List<Joueur> jmort;
	private Statut statut;
	private final List<String> tempPaquet;

	private List<Joueur> joueurs;
	private ArrayList<Integer> lieuZombie;
	private boolean couleurPret = false;
	private boolean isFinished = false;

	private ControleurFouilleCamion cfc;
	private ControleurVote cVote;
	private ControleurElectionVigile cev;

	private final Random rd = new Random();

	public ControleurJeu(String nom, int njr, int njv) throws IOException {
		if (njr + njv > 6 || njr + njv < 3)
			throw new IllegalArgumentException("Mauvais nombre de joueur");
		this.jmort = new ArrayList<>();
		this.tempPaquet = new ArrayList<>();
		this.nomPartie = nom;
		this.nbjv = njv;
		this.nbjr = njr;
		this.nbjtotal = nbjv + nbjr;
		this.lieuZombie = new ArrayList<>();
		this.joueurs = new ArrayList<>();
		this.cfc = new ControleurFouilleCamion();
		this.cVote = new ControleurVote();
		this.cev = new ControleurElectionVigile();

		this.statut = Statut.ATTENTE;
		initReseau();
		this.port = ControleurReseau.getTcpPort();
		this.intPartieId = new Random().nextInt(10000000);
		this.partieId = "P" + intPartieId;
		Initializer.nomPartie(partieId, nomPartie);
		initPartie();
	}

	private void updateValues() {
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Initializer.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
		Initializer.nomJoueurs(new ArrayList<>(jeu.getJoueurs().values()));
		Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
	}

	private synchronized void initPartie() {
		coreThread = ThreadOutils.asyncTask("initPartie", () -> {
			String m = ControleurReseau.construirePaquetUdp("ACP", intPartieId,
					ControleurReseau.getIp().getHostAddress(), port, nomPartie, nbjtotal, nbjr, nbjv, statut);
			ControleurReseau.envoyerUdp(m);

			while (joueurs.size() != this.nbjtotal)
				Thread.yield();

			Initializer.joueurPret();
			statut = Statut.COMPLETE;
			joueurs.get(0).setChefDesVigiles(true);
			jeu = new Partie(joueurs);
			updateValues();

			while (!couleurPret)
				Thread.yield();

			Initializer.nomJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
			try {
				demarerJeu();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public void stopThreads() {
		ControleurReseau.arreter();
		if (ControleurReseau.getTcpServeur() != null) {
			try {
				ControleurReseau.getTcpServeur().arreter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			ControleurReseau.arreterUdp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (coreThread != null)
			coreThread.interrupt();
	}

	public void setJoueurCouleur(List<Couleur> couleurs, List<Joueur> joueursOrdre) {
		this.couleurPret = true;
		joueurs = joueursOrdre;
		for (int i = 0; i < joueurs.size(); i++)
			joueurs.get(i).setCouleur(couleurs.get(i));
	}

	private List<Couleur> getJoueursCouleurs() {
		List<Couleur> lc = new ArrayList<>();
		lc.add(jeu.getChefVIgile().getCouleur());
		for (Joueur j : jeu.getJoueurs().values())
			if (j != jeu.getChefVIgile() && j.isEnVie())
				lc.add(j.getCouleur());
		return lc;
	}

	private void demarerJeu() throws InterruptedException {
		// TODO 3 ou 4 PION | UN OU PLUSIEURS LIEUX FERME
		out.println(jeu.toString());
		String m = ControleurReseau.construirePaquetTcp("IP", jeu.getJoueursNoms(), jeu.getJoueursCouleurs(),
				jeu.getLieux().get(2).isOuvert() ? " " : "2", 3, partieId);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		distribuerCarte();
		this.placementPersonnage();
		this.jeu.resultatChefVigile(jeu.getJoueurs().get(0));
		this.lieuZombie = arriveZombie();
		this.jeu.entreZombie(lieuZombie);
		m = ControleurReseau.construirePaquetTcp("PIPZ", lieuZombie, partieId);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		out.println(jeu.toString());
		this.start();
	}

	private void initReseau() throws IOException {
		TraitementPaquetTcp tcp = new TraitementPaquetTcp(this);
		TraitementPaquetUdp udp = new TraitementPaquetUdp(this);
		InetAddress ip = ReseauOutils.getLocalIp();
		if (ip == null) {
			List<InetAddress> ips = ReseauOutils.getInterfaces();
			int i = 0;
			for (InetAddress ia : ips) {
				System.out.println(i + " " + ia.getHostAddress());
				i++;
			}
			int val = new Scanner(System.in).nextInt();
			ip = ips.get(val);
		}
		ControleurReseau.initConnexion(tcp, udp, ConnexionType.SERVEUR, ip);
	}

	public String ajouterJoueur(String nom, TypeJoueur typeJoueur, TcpClient connection) {
		Joueur joueur = new Joueur(getNewJoueurId(), nom, connection);
		if (typeJoueur == TypeJoueur.JR)
			nbjractuel++;
		else if (typeJoueur == TypeJoueur.BOT)
			nbjvactuel++;

		synchronized (joueurs) {
			joueurs.add(joueur);
		}
		Initializer.updateJoueurs(joueurs, nbjtotal);

		return joueur.getJoueurId();
	}

	private int getNewJoueurId() {
		int tmp = nextJoueurId;
		nextJoueurId++;
		return tmp;
	}

	public void distribuerCarte() {
		for (Joueur j : jeu.getJoueurs().values()) {
			CarteType a = jeu.getCartes().get(0);
			j.getCartes().add(a);
			jeu.getCartes().remove(0);
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("DC", a, partieId));
		}
	}

	/**
	 * Execute le déroulement d'une partie
	 */
	public void start() throws InterruptedException {
		updateValues();
		String m = ControleurReseau.construirePaquetTcp("IT", jeu.getChefVIgile().getCouleur(), getJoueursCouleurs(),
				partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		cfc.phaseFouilleCamion(jeu, partieId, numeroTour);
		electionChefVigi();
		this.lieuZombie = arriveZombie();
		ArrayList<Integer> destination = new ArrayList<>();
		phasechoixDestination(destination);
		jeu.entreZombie(lieuZombie);
		phaseDeplacementPerso(destination, lieuZombie);
		if (isFinished)
			return;
		finJeu();
		if (isFinished)
			return;
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		jeu.fermerLieu();
		Initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
		finJeu();
		if (isFinished)
			return;
		phaseAttaqueZombie();
		if (isFinished)
			return;
		jmort.clear();
		jmort = jeu.getJoueursMort();
		numeroTour++;
		start();
	}

	private List<PionCouleur> getPersosLieu(int i) {
		List<PionCouleur> pc = new ArrayList<>();
		Lieu l = jeu.getLieux().get(i);
		for (Personnage p : l.getPersonnage())
			pc.add(PionCouleur.valueOf(p.getJoueur().getCouleur().toString().substring(0, 1) + p.getPoint()));

		return pc;
	}

	/**
	 * @return le jeu
	 */
	public Partie getJeu() {
		return jeu;
	}

	/**
	 * Affiche et met à jour le nouveau chef des vigiles
	 */
	private void electionChefVigi() {
		String m = ControleurReseau.construirePaquetTcp("PECV", getPersosLieu(5), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		Joueur j;
		if (cev.isElectableBoolean(jeu)) {
			j = cev.joueurElection(jeu);
			if (j == null)
				j = cVote.phaseVote(jeu, jeu.getLieux().get(5), VoteType.ECD, partieId, numeroTour);
			if (j != null) {
				cev.newVigile(jeu, j);
				m = ControleurReseau.construirePaquetTcp("RECV", jeu.getChefVIgile().getCouleur(), partieId,
						numeroTour);
				for (Joueur joueur : jeu.getJoueurs().values())
					j.getConnection().envoyer(m);
				Initializer.electionChef("Nouveau chef des vigiles : " + jeu.getChefVIgile());
			} else {
				jeu.setNewChef(false);
				m = ControleurReseau.construirePaquetTcp("RECV", Couleur.NUL, partieId, numeroTour);
				for (Joueur j2 : jeu.getJoueurs().values())
					j2.getConnection().envoyer(m);
				Initializer.electionChef("Il n'y a pas de nouveau chef des vigiles");
			}
		} else {
			cev.noNewVigile(jeu);
			m = ControleurReseau.construirePaquetTcp("RECV", Couleur.NUL, partieId, numeroTour);
			for (Joueur j2 : jeu.getJoueurs().values())
				j2.getConnection().envoyer(m);
			Initializer.electionChef("Il n'y a pas de nouveau chef des vigiles");
		}

	}

	/**
	 * Definit l'arrivée des zombies et l'affiche pour le chef des vigiles
	 *
	 * @return liste des numéro des lieux d'arriveé des zombies
	 */
	private ArrayList<Integer> arriveZombie() {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		String m = ControleurReseau.construirePaquetTcp("PAZ", jeu.getChefVIgile().getCouleur(), ve, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		jeu.getChefVIgile().getConnection().attendreMessage("AZLD");
		jeu.getChefVIgile().getConnection().getMessage("AZLD");
		int z1 = new Random().nextInt(6) + 1;
		int z2 = new Random().nextInt(6) + 1;
		int z3 = new Random().nextInt(6) + 1;
		int z4 = new Random().nextInt(6) + 1;
		ArrayList<Integer> lieuZombie = new ArrayList<>();
		lieuZombie.add(z1);
		lieuZombie.add(z2);
		lieuZombie.add(z3);
		lieuZombie.add(z4);
		for (Integer integer : lieuZombie) {
			out.println(jeu.getLieux().get(integer) + "-> Zombie + 1");
		}
		jeu.getChefVIgile().getConnection()
				.envoyer(ControleurReseau.construirePaquetTcp("AZLAZ", lieuZombie, partieId, numeroTour));
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.getCartes().contains(CarteType.CDS)) {
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZDCS", partieId, numeroTour));
			}
		}
		List<Joueur> joueurCDS = new ArrayList();
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.getCartes().contains(CarteType.CDS)) {
				j.getConnection().attendreMessage("AZRCS");
				String mess = j.getConnection().getMessage("AZRCS");
				if ((CarteType) ControleurReseau.getValueTcp("AZRCS", mess, 1) != CarteType.NUL) {
					joueurCDS.add(j);
				}
			}
		}
		for (Joueur j : joueurCDS) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZUCS", lieuZombie, partieId, numeroTour));
			j.getCartes().remove(CarteType.CDS);
		}
		for (Joueur j : jeu.getJoueurs().values()) {
			if (joueurCDS.contains(j))
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZICS", j.getCouleur(), CarteType.CDS,
						partieId, numeroTour));
			else
				j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("AZICS", j.getCouleur(), CarteType.NUL,
						partieId, numeroTour));
		}
		return lieuZombie;
	}

	private void phasechoixDestination(ArrayList<Integer> destination) throws InterruptedException {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		String m = ControleurReseau.construirePaquetTcp("PCD", jeu.getChefVIgile().getCouleur(), ve, partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		if (jeu.getNewChef()) {
			jeu.getChefVIgile().getConnection().attendreMessage("CDDCV");
			m = jeu.getChefVIgile().getConnection().getMessage("CDDCV");
			int dest = (int) ControleurReseau.getValueTcp("CDDCV", m, 1);
			destination.add(dest);
			m = ControleurReseau.construirePaquetTcp("CDCDV", jeu.getChefVIgile().getCouleur(), dest, partieId,
					numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				if (!(j.isChefDesVigiles()) && ve == VigileEtat.NE)
					j.getConnection().envoyer(m);
			Initializer.prevenirDeplacementVigile("Le chef des vigile (" + jeu.getChefVIgile().getCouleur()
					+ ") a choisi la detination :" + this.jeu.getLieux().get(dest));
			HashMap<String, Integer> idj = new HashMap<>();
			for (Joueur j : jeu.getJoueurs().values())
				if (j != jeu.getChefVIgile() && j.isEnVie())
					j.getConnection().attendreMessage("CDDJ");
			for (Joueur j : jeu.getJoueurs().values())
				if (j != jeu.getChefVIgile() && j.isEnVie()) {
					String rep = j.getConnection().getMessage("CDDJ");
					String id = (String) ControleurReseau.getValueTcp("CDDJ", rep, 4);
					int idJoueur = (int) ControleurReseau.getValueTcp("CDDJ", rep, 1);
					idj.put(id, idJoueur);
				}
			for (Joueur j : jeu.getJoueurs().values())
				if (!j.isChefDesVigiles() && j.isEnVie())
					for (Map.Entry<String, Integer> d : idj.entrySet())
						if (d.getKey().equals(j.getJoueurId()))
							destination.add(d.getValue());
		} else {
			HashMap<String, Integer> idj = new HashMap<>();
			for (Joueur j : jeu.getJoueurs().values())
				if (j.isEnVie())
					j.getConnection().attendreMessage("CDDJ");

			for (Joueur j : jeu.getJoueurs().values())
				if (j.isEnVie()) {
					String rep = j.getConnection().getMessage("CDDJ");
					String id = (String) ControleurReseau.getValueTcp("CDDJ", rep, 4);
					int idJoueur = (int) ControleurReseau.getValueTcp("CDDJ", rep, 1);
					idj.put(id, idJoueur);
				}

			for (Map.Entry<String, Integer> d : idj.entrySet())
				if (d.getKey().equals(jeu.getChefVIgile().getJoueurId()))
					destination.add(d.getValue());

			for (Joueur j : jeu.getJoueurs().values())
				if (!j.isChefDesVigiles() && j.isEnVie())
					for (Map.Entry<String, Integer> d : idj.entrySet())
						if (d.getKey().equals(j.getJoueurId()))
							destination.add(d.getValue());
		}
		m = ControleurReseau.construirePaquetTcp("CDFC", partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		for (Joueur j : this.jeu.getJoueurs().values()) {
			if (jmort.contains(j)) {
				m = ControleurReseau.construirePaquetTcp("CDZVI", partieId, numeroTour);
				j.getConnection().envoyer(m);
				j.getConnection().attendreMessage("CDDZVJE");
				String rep = j.getConnection().getMessage("CDDZVJE");
				int dvz = (int) ControleurReseau.getValueTcp("CDDZVJE", rep, 1);
				jeu.getLieux().get(dvz).addZombie();
				for (Joueur j2 : this.jeu.getJoueurs().values()) {
					j.getConnection().envoyer(
							ControleurReseau.construirePaquetTcp("CDZVDI", j.getCouleur(), dvz, partieId, numeroTour));
				}
			}
		}
	}

	private void phaseDeplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> zombie) {
		String m = ControleurReseau.construirePaquetTcp("PDP", jeu.getChefVIgile().getCouleur(), destination, zombie,
				jeu.getLieuxFermes(), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		int compteur = 0;
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.isEnVie() && j.isChefDesVigiles()) {
				out.println(jeu.toString());
				m = ControleurReseau.construirePaquetTcp("DPD", destination.get(compteur), jeu.getAllPersoPossible(j),
						partieId, numeroTour);
				j.getConnection().envoyer(m);
				j.getConnection().attendreMessage("DPR");
				String message = j.getConnection().getMessage("DPR");
				if (ControleurReseau.getValueTcp("DPR", message, 3) == CarteType.SPR) {
					j.getCartes().remove(CarteType.SPR);
				}
				int dest = (int) ControleurReseau.getValueTcp("DPR", message, 1);
				int pion = (int) ControleurReseau.getValueTcp("DPR", message, 2);
				jeu.deplacePerso(j, PpTools.valeurToIndex(pion), dest);
				finJeu();
				if (isFinished)
					return;
				this.jeu.fermerLieu();
				compteur += 1;
				m = ControleurReseau.construirePaquetTcp("DPI", j.getCouleur(), dest, pion,
						ControleurReseau.getValueTcp("DPR", message, 3), partieId, numeroTour);
				for (Joueur j2 : jeu.getJoueurs().values())
					if (j2 != j)
						j2.getConnection().envoyer(m);
				Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
			}
		}
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.isEnVie() && !(j.isChefDesVigiles())) {
				out.println(jeu.toString());
				m = ControleurReseau.construirePaquetTcp("DPD", destination.get(compteur), jeu.getAllPersoPossible(j),
						partieId, numeroTour);
				j.getConnection().envoyer(m);
				j.getConnection().attendreMessage("DPR");
				String message = j.getConnection().getMessage("DPR");
				if (ControleurReseau.getValueTcp("DPR", message, 3) == CarteType.SPR) {
					j.getCartes().remove(CarteType.SPR);
				}
				int dest = (int) ControleurReseau.getValueTcp("DPR", message, 1);
				int pion = (int) ControleurReseau.getValueTcp("DPR", message, 2);
				jeu.deplacePerso(j, PpTools.valeurToIndex(pion), dest);
				finJeu();
				if (isFinished)
					return;
				this.jeu.fermerLieu();
				compteur += 1;
				m = ControleurReseau.construirePaquetTcp("DPI", j.getCouleur(), dest, pion,
						ControleurReseau.getValueTcp("DPR", message, 3), partieId, numeroTour);
				for (Joueur j2 : jeu.getJoueurs().values())
					if (j2 != j)
						j2.getConnection().envoyer(m);
				Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
			}
		}

	}

	private void phaseAttaqueZombie() {
		List<Integer> nb = jeu.lastAttaqueZombie();
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		String me = ControleurReseau.construirePaquetTcp("PRAZ", nb.get(0), nb.get(1), jeu.getLieuxOuverts(),
				jeu.getNbZombieLieux(), jeu.getNbPionLieux(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(me);
		for (int i = 1; i < 7; i++) {
			if (lieuAttaquableReseau(i)) {
				List<Object> defense = défenseAttaqueZombie(i);
				if (jeu.getLieux().get(i).estAttaquable((int) defense.get(1))) {
					if (i == 4) {// si parking
						while (jeu.getLieux().get(i).getNbZombies() != 0
								&& !jeu.getLieux().get(i).getPersonnage().isEmpty()) {
							attaqueZombie(i);
							jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);
							this.finJeu();
							if (isFinished)
								return;
						}
					} else {
						attaqueZombie(i);
						jeu.getLieux().get(i).setNbZombies(0);
						this.finJeu();
						if (isFinished)
							return;
					}
				}
			}
		}
		reinitJoueurCache();
	}

	private void reinitJoueurCache() {
		for (Joueur j : jeu.getJoueurs().values()) {
			for (Personnage p : j.getPersonnages().values()) {
				p.setEstCache(false);
			}
		}
	}

	private List<Object> défenseAttaqueZombie(int i) {
		List<Object> defense = new ArrayList<>();
		List<Personnage> persoCache = new ArrayList<>();
		int nbCarteMatériel = 0;
		int nbCarteCachette = 0;
		for (Joueur j : jeu.getJoueurSurLieu(jeu.getLieux().get(i))) {
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("RAZDD", i, partieId, numeroTour));
		}
		for (Joueur j : jeu.getJoueurSurLieu(jeu.getLieux().get(i))) {
			List<Integer> persoCacheTemp = new ArrayList<>();
			j.getConnection().attendreMessage("RAZRD");
			String m = j.getConnection().getMessage("RAZRD");
			List<CarteType> l = (List<CarteType>) ControleurReseau.getValueTcp("RAZRD", m, 1);
			for (CarteType c : l) {
				if (c == CarteType.MAT) {
					nbCarteMatériel += 1;
				} else if (c == CarteType.ACS || c == CarteType.ATR || c == CarteType.AGR) {
					jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 2);
				} else if (c == CarteType.ARE || c == CarteType.AHI || c == CarteType.ABA) {
					jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);
				}
				j.getCartes().remove(c);
			}
			for (int num : (List<Integer>) ControleurReseau.getValueTcp("RAZRD", m, 2)) {
				persoCacheTemp.add(num);
				persoCache.add(j.getPersonnages().get(PpTools.valeurToIndex(num)));
				j.getPersonnages().get(PpTools.valeurToIndex(num)).setEstCache(true);
			}
			for (Joueur jou : jeu.getJoueurs().values()) {
				jou.getConnection()
						.envoyer(ControleurReseau.construirePaquetTcp("RAZID", i, j.getCouleur(), l, persoCacheTemp,
								jeu.getLieux().get(i).getForce() + nbCarteMatériel,
								jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour));
			}
		}
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		defense.add(persoCache);
		defense.add(nbCarteMatériel);
		return defense;
	}

	private boolean lieuAttaquableReseau(int i) {
		if (jeu.getLieux().get(i).isOuvert() && !jeu.getLieux().get(i).getPersonnage().isEmpty()) {
			if (jeu.getLieux().get(i).getNbZombies() > 0) {
				if (jeu.getLieux().get(i).estAttaquable()) {
					String message = ControleurReseau.construirePaquetTcp("RAZA", i,
							PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()),
							jeu.getLieux().get(i).getForce(), jeu.getLieux().get(i).getNbZombies(), partieId,
							numeroTour);
					for (Joueur joueur : jeu.getJoueurs().values())
						joueur.getConnection().envoyer(message);
					return true;
				} else {
					String message = ControleurReseau.construirePaquetTcp("RAZPA", 4,
							PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()), RaisonType.FORCE,
							jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
					for (Joueur joueur : jeu.getJoueurs().values())
						joueur.getConnection().envoyer(message);
				}
			} else {
				String message = ControleurReseau.construirePaquetTcp("RAZPA", 4,
						PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()), RaisonType.ZOMBIE,
						jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
				for (Joueur joueur : jeu.getJoueurs().values())
					joueur.getConnection().envoyer(message);
			}
		} else {
			String message = ControleurReseau.construirePaquetTcp("RAZPA", 4,
					PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()), RaisonType.PION,
					jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
			for (Joueur joueur : jeu.getJoueurs().values())
				joueur.getConnection().envoyer(message);
		}
		return false;
	}

	private void attaqueZombie(int i) {
		System.out.println(jeu.toString());
		Joueur jou;
		if (jeu.getJoueurSurLieu(jeu.getLieux().get(i)).size() == 1)
			jou = jeu.getJoueurSurLieu(jeu.getLieux().get(i)).get(0);
		else
			jou = cVote.phaseVote(jeu, jeu.getLieux().get(i), VoteType.MPZ, partieId, numeroTour);
		List<Integer> listePion = new ArrayList<>();
		for (Personnage p : jou.getPersonnages().values()) {
			if (jeu.getLieux().get(i).getPersonnage().contains(p)) {
				listePion.add(p.getPoint());
			}
		}
		String m = ControleurReseau.construirePaquetTcp("RAZDS", i, listePion, partieId, numeroTour);
		jou.getConnection().envoyer(m);
		jou.getConnection().attendreMessage("RAZCS");
		String rep = jou.getConnection().getMessage("RAZCS");
		PionCouleur pionCou = (PionCouleur) ControleurReseau.getValueTcp("RAZCS", rep, 2);
		int pion = PpTools.getPionByValue(pionCou);
		jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
		Initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
		m = ControleurReseau.construirePaquetTcp("RAZIF", i, pionCou, jeu.getLieux().get(i).getNbZombies(), partieId,
				numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(m);
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
	}

	private void placementPersonnage() {
		for (int n = 0; n < jeu.getJoueurs().get(0).getPersonnages().size(); n++) {
			for (int i = 0; i < jeu.getJoueurs().size(); i++) {
				String message = ControleurReseau.construirePaquetTcp("PIIJ", jeu.nombrePlaceDisponible(),
						jeu.getPersonnageAPlace(jeu.getJoueurs().get(i)), partieId);
				jeu.getJoueurs().get(i).getConnection().envoyer(message);
				out.println(jeu.toString());
				out.println();
				out.println("Lancement des dés.");
				int x = jeu.getLieuxOuverts().get(rd.nextInt(jeu.getLieuxOuverts().size()));
				int y = jeu.getLieuxOuverts().get(rd.nextInt(jeu.getLieuxOuverts().size()));
				out.println("Résultat du lancement :");
				List<Integer> des = new ArrayList<>();
				des.add(x);
				des.add(y);
				List<Integer> listePion = jeu.getDestinationPossible(x, y);
				message = ControleurReseau.construirePaquetTcp("PIRD", des, listePion, partieId);
				jeu.getJoueurs().get(i).getConnection().envoyer(message);
				jeu.getJoueurs().get(i).getConnection().attendreMessage("PICD");
				String rep = jeu.getJoueurs().get(i).getConnection().getMessage("PICD");
				int destEntre = (int) ControleurReseau.getValueTcp("PICD", rep, 1);
				int persEntre = (int) ControleurReseau.getValueTcp("PICD", rep, 2);
				jeu.placePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(persEntre), destEntre);
				message = ControleurReseau.construirePaquetTcp("PIIG", jeu.getJoueurs().get(i).getCouleur(), des,
						listePion, destEntre, persEntre, partieId);
				for (Joueur j : jeu.getJoueurs().values())
					if (j != jeu.getJoueurs().get(i))
						j.getConnection().envoyer(message);
			}
			Initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
		}
	}

	/**
	 * Detecte et affiche la fin du jeu
	 *
	 * @return si c'est la fin du jeu
	 */
	public void finJeu() {
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie() && this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
				this.jeu.getJoueurs().get(i).setEnVie(false);
			}
		}
		ArrayList<Lieu> lieu = new ArrayList<>();
		int nbPerso = 0;
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i).isEnVie()) {
				nbPerso += this.jeu.getJoueurs().get(i).getPersonnages().size();
				for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
					if (!lieu.contains(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu())) {
						lieu.add(this.jeu.getJoueurs().get(i).getPersonnages().get(j).getMonLieu());
					}
				}
			}
		}
		if ((lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4))
				|| (nbPerso <= 4 && jeu.getJoueurs().size() < 6) || (nbPerso <= 6 && jeu.getJoueurs().size() == 6)) {
			CondType cond;
			if (lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4))
				cond = CondType.LIEUX;
			else
				cond = CondType.PION;
			System.out.println(jeu.toString());
			out.println();
			int pointVainqueur = 0;
			ArrayList<Joueur> vainqueur = new ArrayList<>();
			for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
				int point = 0;
				if (this.jeu.getJoueurs().get(i).isEnVie()) {
					for (Integer j : this.jeu.getJoueurs().get(i).getPersonnages().keySet()) {
						point += this.jeu.getJoueurs().get(i).getPersonnages().get(j).getPoint();
					}
					if (point > pointVainqueur) {
						pointVainqueur = point;
						vainqueur.clear();
						vainqueur.add(jeu.getJoueurs().get(i));
					} else if (point == pointVainqueur) {
						vainqueur.add(jeu.getJoueurs().get(i));
					}
					out.println(" Point " + this.jeu.getJoueurs().get(i) + ": " + point);
				} else {
					out.println(" Point " + this.jeu.getJoueurs().get(i) + "(mort): " + point);
				}
				out.println();
			}
			if (vainqueur.size() == 1) {
				out.println(">>>>> " + vainqueur.get(0) + " a gagné ! <<<<<");
			} else {
				StringBuilder s = new StringBuilder(">>>>> ");
				for (Joueur joueur : vainqueur) {
					s.append("  ").append(joueur);
				}
				s.append(" sont vainqueurs à égalité! <<<<<");
			}
			isFinished = true;
			Joueur gagnantNotFair = vainqueur.get(new Random().nextInt(vainqueur.size()));
			String message = ControleurReseau.construirePaquetTcp("FP", cond, gagnantNotFair.getCouleur(), partieId,
					numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				j.getConnection().envoyer(message);
			Initializer.finPartie();
			Initializer.getGagnant(gagnantNotFair);
			statut = Statut.COMPLETE;
			try {
				ControleurReseau.getTcpServeur().arreter();
				ControleurReseau.arreterUdp();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean estPleine() {
		return joueurs.size() == this.nbjtotal;
	}

	public String getPartieId() {
		return partieId;
	}

	public String getNomPartie() {
		return nomPartie;
	}

	public int getNbjtotal() {
		return nbjtotal;
	}

	public int getNbjr() {
		return nbjr;
	}

	public int getNbjv() {
		return nbjv;
	}

	public int getNbjractuel() {
		return nbjractuel;
	}

	public int getNbjvactuel() {
		return nbjvactuel;
	}

	public List<String> getTempPaquet() {
		return tempPaquet;
	}

	public int getNumeroTour() {
		return numeroTour;
	}

	public Statut getStatus() {
		return statut;
	}

	public void setLieuZombie(ArrayList<Integer> lieuZombie) {
		this.lieuZombie = lieuZombie;
	}
}