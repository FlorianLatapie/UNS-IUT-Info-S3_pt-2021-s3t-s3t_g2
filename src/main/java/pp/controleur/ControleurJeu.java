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
	static Statut statut;
	private final List<String> tempPaquet;

	private List<Joueur> joueurs;
	private List<Integer> lieuZombie;
	private boolean couleurPret = false;
	static boolean isFinished = false;

	private ControleurFouilleCamion cfc;
	private ControleurElectionVigile cev;
	private ControleurArriveZombie caz;
	private ControleurChoixDestination ccd;
	private ControleurPlacementPersonnage cpp;
	private ControleurDeplacementPersonnage cdp;
	private ControleurFinPartie cfp;
	private ControleurAttaqueZombie catz;

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
		this.cev = new ControleurElectionVigile();
		this.caz = new ControleurArriveZombie();
		this.ccd = new ControleurChoixDestination();
		this.cpp = new ControleurPlacementPersonnage();
		this.cdp = new ControleurDeplacementPersonnage();
		this.cfp = new ControleurFinPartie();
		this.catz = new ControleurAttaqueZombie();

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
		cpp.placementPersonnage(jeu, partieId, numeroTour);
		this.jeu.resultatChefVigile(jeu.getJoueurs().get(0));
		this.lieuZombie = caz.lanceDes();
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
		cev.phaseElectionChefVigi(jeu, partieId, numeroTour);
		this.lieuZombie = caz.phaseArriveZombie(jeu, partieId, numeroTour);
		ArrayList<Integer> destination = new ArrayList<>();
		ccd.phasechoixDestination(jeu, destination, jmort, partieId, numeroTour);
		jeu.entreZombie(lieuZombie);
		cdp.phaseDeplacementPerso(jeu, destination, lieuZombie, partieId, numeroTour);
		if (isFinished)
			return;
		cfp.finJeu(jeu, partieId, numeroTour);
		if (isFinished)
			return;
		Initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		jeu.fermerLieu();
		Initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		Initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
		cfp.finJeu(jeu, partieId, numeroTour);
		if (isFinished)
			return;
		catz.phaseAttaqueZombie(jeu, partieId, numeroTour);
		if (isFinished)
			return;
		jmort.clear();
		jmort = jeu.getJoueursMort();
		numeroTour++;
		start();
	}

	/**
	 * @return le jeu
	 */
	public Partie getJeu() {
		return jeu;
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