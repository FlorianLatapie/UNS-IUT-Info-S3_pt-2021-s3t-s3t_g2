package pp.controleur;

import pp.*;
import pp.ihm.event.Evenement;
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
 * <h1>La classe controleurJeu</h1> A pour rôle de gérer une partie
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
	private boolean couleurPret;

	public boolean isPlacerJoueur() {
		return placerJoueur;
	}

	public void setPlacerJoueur(boolean placerJoueur) {
		this.placerJoueur = placerJoueur;
	}

	private boolean placerJoueur;
	private boolean isFinished;
	private boolean vite;

	private ControleurFouilleCamion cfc;
	private ControleurElectionVigile cev;
	private ControleurArriveZombie caz;
	private ControleurChoixDestination ccd;
	private ControleurPlacementPersonnage cpp;
	private ControleurDeplacementPersonnage cdp;
	private ControleurFinPartie cfp;
	private ControleurAttaqueZombie catz;

	public ControleurJeu(String nom, int njr, int njv, boolean vite) throws IOException {
		if (njr + njv > 6 || njr + njv < 3)
			throw new IllegalArgumentException("Mauvais nombre de joueur");
		this.isFinished = false;
		this.couleurPret = false;
		this.placerJoueur = false;
		this.vite = vite;
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
		Evenement.nomPartie(partieId, nomPartie);
		initPartie();
	}

	private void updateValues() {
		Evenement.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Evenement.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		Evenement.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
		Evenement.nomJoueurs(new ArrayList<>(jeu.getJoueurs().values()));
		Evenement.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.nbCartePiocheActuel(jeu.getCartes().size());
		Evenement.nbPlaceLieuAll(new ArrayList<>(jeu.getLieux().values()));
	}

	private synchronized void initPartie() {
		coreThread = ThreadOutils.asyncTask("initPartie", () -> {
			String m = ControleurReseau.construirePaquetUdp("ACP", intPartieId,
					ControleurReseau.getIp().getHostAddress(), port, nomPartie, nbjtotal, nbjr, nbjv, statut);
			ControleurReseau.envoyerUdp(m);

			while (joueurs.size() != this.nbjtotal)
				Thread.yield();

			Evenement.joueurPret();
			joueurs.get(0).setChefDesVigiles(true);
			jeu = new Partie(joueurs);
			statut = Statut.COMPLETE;

			Evenement.nomJoueurs(new ArrayList<>(jeu.getJoueurs().values()));

			while (!couleurPret)
				Thread.yield();

			Evenement.choiCouleur(getJoueursCouleurs());

			while (!placerJoueur)
				Thread.yield();

			Evenement.nomJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));

			updateValues();

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
		joueurs.get(0).setChefDesVigiles(true);
		jeu = new Partie(joueursOrdre);
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
		ControleurReseau.initConnexion(tcp, udp, ConnexionType.SERVEUR, ip, vite);
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
		Evenement.updateJoueurs(joueurs, nbjtotal);

		return joueur.getJoueurId();
	}

	private int getNewJoueurId() {
		int tmp = nextJoueurId;
		nextJoueurId++;
		return tmp;
	}

	/**
	 * Distribue la premiere carte a chaque joueur.
	 */
	public void distribuerCarte() {
		for (Joueur j : jeu.getJoueurs().values()) {
			CarteType a = jeu.getCartes().get(0);
			j.getCartes().add(a);
			jeu.getCartes().remove(0);
			j.getConnection().envoyer(ControleurReseau.construirePaquetTcp("DC", a, partieId));
		}
		Evenement.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
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
		cdp.phaseDeplacementPerso(this, jeu, destination, lieuZombie, partieId, numeroTour);
		if (isFinished)
			return;
		cfp.finJeu(this, jeu, partieId, numeroTour);
		if (isFinished)
			return;
		Evenement.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		jeu.fermerLieu();
		Evenement.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		Evenement.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
		cfp.finJeu(this, jeu, partieId, numeroTour);
		if (isFinished)
			return;
		catz.phaseAttaqueZombie(this, jeu, partieId, numeroTour);
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

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

}