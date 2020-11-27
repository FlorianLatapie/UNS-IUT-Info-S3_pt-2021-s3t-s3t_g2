package pp.controleur;

import pp.ihm.eventListener.Initializer;
import pp.*;
import reseau.socket.Connexion;
import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;
import reseau.tool.ThreadOutils;
import reseau.type.*;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
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

	private final ControleurReseau nwm;
	private Partie jeu;
	private final Initializer initializer;
	private Thread coreThread;
	private List<Joueur> jmort;
	private Status status;
	private final List<String> tempPaquet;

	private final ArrayList<Joueur> joueurs;
	private ArrayList<Integer> lieuZombie;
	private boolean couleurPret = false;
	private boolean isFinished = false;

	private final Random rd = new Random();

	public ControleurJeu(String nom, int njr, int njv, Initializer initializer) throws IOException {
		if (njr + njv > 6 || njr + njv < 3)
			throw new IllegalArgumentException("Mauvais nombre de joueur");

		this.initializer = initializer;
		this.jmort = new ArrayList<>();
		this.tempPaquet = new ArrayList<>();
		this.nomPartie = nom;
		this.nbjv = njv;
		this.nbjr = njr;
		this.nbjtotal = nbjv + nbjr;
		this.lieuZombie = new ArrayList<>();
		this.joueurs = new ArrayList<>();
		TraitementPaquetTcp tcp = new TraitementPaquetTcp(this);
		TraitementPaquetUdp udp = new TraitementPaquetUdp(this);
		this.nwm = new ControleurReseau(tcp, udp);
		this.status = Status.ATTENTE;
		initReseau();
		this.port = nwm.getTcpPort();
		this.intPartieId = new Random().nextInt(10000000);
		this.partieId = "P" + intPartieId;
		if (initializer != null)
			initializer.nomPartie(partieId);
		initPartie();
	}

	private void updateValues() {
		if (initializer != null) {
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
			initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
			initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));
			initializer.nbCarteJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
			initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
			initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
			initializer.nomChefVigileAll(new ArrayList<>(jeu.getJoueurs().values()));
			initializer.nomJoueurs(new ArrayList<>(jeu.getJoueurs().values()));
		}
	}

	private synchronized void initPartie() {
		coreThread = ThreadOutils.asyncTask(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String m = nwm.construirePaquetUdp("ACP", intPartieId, nwm.getIp().getHostAddress(), port, nomPartie,
					nbjtotal, nbjr, nbjv, status);
			nwm.envoyerUdp(m);

			while (joueurs.size() != this.nbjtotal)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}

			if (initializer != null)
				initializer.joueurPret();

			status = Status.COMPLETE;
			joueurs.get(0).setChefDesVigiles(true);
			jeu = new Partie(joueurs);
			updateValues();

			while (!couleurPret)
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					return;
				}

			if (initializer != null)
				initializer.nomJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
			try {
				demarerJeu();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public void stopThreads() {
		nwm.arreter();

		if (nwm.getTcpServeur() != null) {
			try {
				nwm.getTcpServeur().arreter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			nwm.arreterUdp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (coreThread != null)
			coreThread.interrupt();
	}

	public void setJoueurCouleur(List<Couleur> couleurs) {
		this.couleurPret = true;
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

		String m = nwm.construirePaquetTcp("IP", jeu.getJoueursNoms(), jeu.getJoueursCouleurs(), 0, 3, partieId);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		this.placementPersonnage();
		this.jeu.resultatChefVigile(jeu.getJoueurs().get(0));
		this.lieuZombie = arriveZombie();
		this.jeu.entreZombie(lieuZombie);

		m = nwm.construirePaquetTcp("PIPZ", lieuZombie, partieId);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		out.println(jeu.toString());
		this.start();
	}

	private void initReseau() throws IOException {
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

		nwm.initConnexion(ConnexionType.SERVER, ip);
	}

	public String ajouterJoueur(InetAddress ip, int port, String nom, TypeJoueur typeJoueur, Connexion connection) {
		if (typeJoueur == TypeJoueur.JR && nbjractuel == nbjr)
			return null;
		if (typeJoueur == TypeJoueur.BOT && nbjvactuel == nbjv)
			return null;

		Joueur joueur = new Joueur(getNewJoueurId(), ip, port, nom, connection);
		if (typeJoueur == TypeJoueur.JR)
			nbjractuel++;
		else if (typeJoueur == TypeJoueur.BOT)
			nbjvactuel++;
		synchronized (joueurs) {
			joueurs.add(joueur);
		}

		return joueur.getJoueurId();
	}

	private int getNewJoueurId() {
		int tmp = nextJoueurId;
		nextJoueurId++;

		return tmp;
	}

	/**
	 * Execute le déroulement d'une partie
	 */
	public void start() throws InterruptedException {
		updateValues();

		String m = nwm.construirePaquetTcp("IT", jeu.getChefVIgile().getCouleur(), getJoueursCouleurs(), partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		fouilleCamion();
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

		if (initializer != null)
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		jeu.fermerLieu();
		if (initializer != null)
			initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		if (initializer != null)
			initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));

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

	/**
	 * Affiche le joueur qui fouille le camion
	 */
	private void fouilleCamion() {
		String s = new String();
		String m = nwm.construirePaquetTcp("PFC", getJoueursCouleurs(), jeu.getCartes().size(), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		if (!jeu.getLieux().get(4).getJoueurSurLieu().isEmpty() || jeu.getCartes().isEmpty()) {
			Joueur j = jeu.voteJoueur(4);
			s += j + " fouille le camion!\n";
			j.getConnection().envoyer(nwm.construirePaquetTcp("FCLC", jeu.tirerCartes(j), partieId, numeroTour));

			// TODO traité carte recu d'IDJR
			j.getConnection().attendreMessage("SCFC");
			String mess = j.getConnection().getMessage("SCFC");
			Couleur a1 = Couleur.NUL;
			Couleur a2 = Couleur.NUL;
			Couleur a3 = Couleur.NUL;
			if (nwm.getPaquetTcp("SCFC").getValue(mess, 1) != CarteType.NUL) {
				j.getCartes().add((CarteType) nwm.getPaquetTcp("SCFC").getValue(mess, 1));
				a1 = j.getCouleur();
			}
			if (nwm.getPaquetTcp("SCFC").getValue(mess, 2) != CarteType.NUL) {
				jeu.getJoueurCouleur((Couleur) nwm.getPaquetTcp("SCFC").getValue(mess, 3)).getCartes()
						.add((CarteType) nwm.getPaquetTcp("SCFC").getValue(mess, 2));
				a2 = (Couleur) nwm.getPaquetTcp("SCFC").getValue(mess, 3);
			}
			if (nwm.getPaquetTcp("SCFC").getValue(mess, 4) != CarteType.NUL) {
				jeu.getCartes().add((CarteType) nwm.getPaquetTcp("SCFC").getValue(mess, 4));
				// TODO a3 = NUL ou CD
			}

			m = nwm.construirePaquetTcp("RFC", a1, a2, a3, partieId, numeroTour);
			for (Joueur j2 : jeu.getJoueurs().values())
				j2.getConnection().envoyer(m);
		} else {
			s += "Personne ne fouille le camion.";
		}

		if (initializer != null)
			initializer.fouilleCamion(s);
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
		// TODO PREVENIR QUI EST LE CHEF DES VIGILES
		String m = nwm.construirePaquetTcp("PECV", getPersosLieu(5), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		if (!jeu.getLieux().get(5).getJoueurSurLieu().isEmpty()) {
			Joueur j = jeu.voteJoueur(5);
			jeu.resultatChefVigile(j);
			out.println(j + " est le nouveau chef des vigiles!");
			jeu.setNewChef(true);

			m = nwm.construirePaquetTcp("RECV", jeu.getChefVIgile().getCouleur(), partieId, numeroTour);
			for (Joueur joueur : jeu.getJoueurs().values())
				j.getConnection().envoyer(m);
			if (initializer != null)
				initializer.electionChef("Nouveau chef des vigiles : " + jeu.getChefVIgile());
		} else {
			out.println("Pas de nouveau chef des vigiles!");
			jeu.setNewChef(false);

			m = nwm.construirePaquetTcp("RECV", Couleur.NUL, partieId, numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				j.getConnection().envoyer(m);
			if (initializer != null)
				initializer.electionChef("Il n'y a pas de nouveau chef des vigiles");
		}

	}

	/**
	 * Definit l'arrivée des zombies et l'affiche pour le chef des vigiles
	 *
	 * @return liste des numéro des lieux d'arriveé des zombies
	 */
	private ArrayList<Integer> arriveZombie() {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;

		String m = nwm.construirePaquetTcp("PAZ", jeu.getChefVIgile().getCouleur(), ve, partieId, numeroTour);
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

		out.println(jeu.getChefVIgile() + " , chef des vigiles, regarde les résulats de l'arrivé des Zombies:");
		for (Integer integer : lieuZombie) {
			out.println(jeu.getLieux().get(integer) + "-> Zombie + 1");
		}

		jeu.getChefVIgile().getConnection().envoyer(nwm.construirePaquetTcp("AZLAZ", lieuZombie, partieId, numeroTour));

		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.getCartes().contains(CarteType.CDS)) {
				j.getConnection().envoyer(nwm.construirePaquetTcp("AZDCS", partieId, numeroTour));
			}
		}

		List<Joueur> joueurCDS = new ArrayList();
		for (Joueur j : jeu.getJoueurs().values()) {
			if (j.getCartes().contains(CarteType.CDS)) {
				j.getConnection().attendreMessage("AZRCS");
				String mess = j.getConnection().getMessage("AZRCS");
				if ((CarteType) nwm.getPaquetTcp("AZRCS").getValue(mess, 1) != CarteType.NUL) {
					joueurCDS.add(j);
				}
			}
		}

		for (Joueur j : joueurCDS) {
			j.getConnection().envoyer(nwm.construirePaquetTcp("AZUCS", lieuZombie, partieId, numeroTour));
			j.getCartes().remove(CarteType.CDS);
		}

		for (Joueur j : jeu.getJoueurs().values()) {
			if (joueurCDS.contains(j))
				j.getConnection()
						.envoyer(nwm.construirePaquetTcp("AZICS", j.getCouleur(), CarteType.CDS, partieId, numeroTour));
			else
				j.getConnection()
						.envoyer(nwm.construirePaquetTcp("AZICS", j.getCouleur(), CarteType.NUL, partieId, numeroTour));
		}

		return lieuZombie;
	}

	private void phasechoixDestination(ArrayList<Integer> destination) throws InterruptedException {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		String m = nwm.construirePaquetTcp("PCD", jeu.getChefVIgile().getCouleur(), ve, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		if (jeu.getNewChef()) {
			jeu.getChefVIgile().getConnection().attendreMessage("CDDCV");

			m = jeu.getChefVIgile().getConnection().getMessage("CDDCV");
			int dest = (int) nwm.getValueTcp("CDDCV", m, 1);
			destination.add(dest);

			m = nwm.construirePaquetTcp("CDCDV", jeu.getChefVIgile().getCouleur(), dest, partieId, numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				if (!(j.isChefDesVigiles()) && ve == VigileEtat.NE)
					j.getConnection().envoyer(m);
			if (initializer != null)
				initializer.prevenirDeplacementVigile("Le chef des vigile (" + jeu.getChefVIgile().getCouleur()
						+ ") a choisi la detination :" + this.jeu.getLieux().get(dest));

			HashMap<String, Integer> idj = new HashMap<>();
			for (Joueur j : jeu.getJoueurs().values())
				if (j != jeu.getChefVIgile() && j.isEnVie())
					j.getConnection().attendreMessage("CDDJ");

			for (Joueur j : jeu.getJoueurs().values())
				if (j != jeu.getChefVIgile() && j.isEnVie()) {
					String rep = j.getConnection().getMessage("CDDJ");
					String id = (String) nwm.getValueTcp("CDDJ", rep, 4);
					int idJoueur = (int) nwm.getValueTcp("CDDJ", rep, 1);
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
					String id = (String) nwm.getValueTcp("CDDJ", rep, 4);
					int idJoueur = (int) nwm.getValueTcp("CDDJ", rep, 1);
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

		m = nwm.construirePaquetTcp("CDFC", partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		for (Joueur j : this.jeu.getJoueurs().values()) {
			if (jmort.contains(j)) {

				m = nwm.construirePaquetTcp("CDZVI", partieId, numeroTour);
				j.getConnection().envoyer(m);

				j.getConnection().attendreMessage("CDDZVJE");
				String rep = j.getConnection().getMessage("CDDZVJE");

				int dvz = (int) nwm.getValueTcp("CDDZVJE", rep, 1);

				jeu.getLieux().get(dvz).addZombie();
			}
		}

	}

	private void phaseDeplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> zombie) {
		String m = nwm.construirePaquetTcp("PDP", jeu.getChefVIgile().getCouleur(), destination, zombie,
				jeu.getLieuxFermes(), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
		int compteur = 0;
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			if (jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
				out.println(jeu.toString());
				m = nwm.construirePaquetTcp("DPD", destination.get(compteur),
						jeu.getAllPersoPossible(jeu.getJoueurs().get(i)), partieId, numeroTour);
				jeu.getJoueurs().get(i).getConnection().envoyer(m);
				jeu.getJoueurs().get(i).getConnection().attendreMessage("DPR");
				String message = jeu.getJoueurs().get(i).getConnection().getMessage("DPR");
				if (nwm.getValueTcp("DPR", message, 3) == CarteType.SPR) {
					jeu.getJoueurs().get(i).getCartes().remove(CarteType.SPR);
				}
				int dest = (int) nwm.getValueTcp("DPR", message, 1);
				int pion = (int) nwm.getValueTcp("DPR", message, 2);
				jeu.deplacePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(pion), dest);
				if (initializer != null)
					initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
				finJeu();
				if (isFinished)
					return;
				this.jeu.fermerLieu();
				compteur += 1;
				m = nwm.construirePaquetTcp("DPI", jeu.getJoueurs().get(i).getCouleur(), dest, pion,
						nwm.getValueTcp("DPR", message, 3), partieId, numeroTour);
				for (Joueur j : jeu.getJoueurs().values())
					if (j != jeu.getJoueurs().get(i))
						j.getConnection().envoyer(m);
				if (initializer != null)
					initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
			}
		}
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			if (!jeu.getJoueurs().get(i).isChefDesVigiles() && jeu.getJoueurs().get(i).isEnVie()) {
				out.println(jeu.toString());
				m = nwm.construirePaquetTcp("DPD", destination.get(compteur),
						jeu.getAllPersoPossible(jeu.getJoueurs().get(i)), partieId, numeroTour);
				jeu.getJoueurs().get(i).getConnection().envoyer(m);
				jeu.getJoueurs().get(i).getConnection().attendreMessage("DPR");
				String message = jeu.getJoueurs().get(i).getConnection().getMessage("DPR");
				if (nwm.getValueTcp("DPR", message, 3) == CarteType.SPR) {
					jeu.getJoueurs().get(i).getCartes().remove(CarteType.SPR);
				}
				int dest = (int) nwm.getValueTcp("DPR", message, 1);
				int pion = (int) nwm.getValueTcp("DPR", message, 2);
				jeu.deplacePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(pion), dest);
				if (initializer != null)
					initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
				finJeu();
				if (isFinished)
					return;
				this.jeu.fermerLieu();
				compteur += 1;
				m = nwm.construirePaquetTcp("DPI", jeu.getJoueurs().get(i).getCouleur(), dest, pion,
						nwm.getValueTcp("DPR", message, 3), partieId, numeroTour);
				for (Joueur j : jeu.getJoueurs().values())
					if (j != jeu.getJoueurs().get(i))
						j.getConnection().envoyer(m);
				if (initializer != null)
					initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
			}
		}
	}

	private void phaseAttaqueZombie() {
		List<Integer> nb = jeu.lastAttaqueZombie();
		if (initializer != null)
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		String me = nwm.construirePaquetTcp("PRAZ", nb.get(0), nb.get(1), jeu.getLieuxOuverts(), jeu.getNbZombieLieux(),
				jeu.getNbPionLieux(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(me);

		for (int i = 1; i < 7; i++) {
			if (lieuAttaquableReseau(i)) {
				List<Object> defense = défenseAttaqueZombie(i);
				if (jeu.getLieux().get(i).estAttaquable((int) defense.get(1))) {
					if (i == 4) {// si parking
						while (jeu.getLieux().get(i).getNbZombies() != 0
								|| !jeu.getLieux().get(i).getPersonnage().isEmpty()) {
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
	}

	private List<Object> défenseAttaqueZombie(int i) {
		List<Object> defense = new ArrayList<>();
		List<Personnage> persoCache = new ArrayList<>();
		int nbCarteMatériel = 0;
		int nbCarteCachette = 0;
		for (Joueur j : jeu.getLieux().get(i).getJoueurSurLieu()) {
			j.getConnection().envoyer(nwm.construirePaquetTcp("RAZDD", i, partieId, numeroTour));
		}
		for (Joueur j : jeu.getLieux().get(i).getJoueurSurLieu()) {
			List<Integer> persoCacheTemp = new ArrayList<>();
			j.getConnection().attendreMessage("RAZRD");
			String m = j.getConnection().getMessage("RAZRD");
			List<CarteType> l = (List<CarteType>) nwm.getValueTcp("RAZRD", m, 1);
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
			for (int num : (List<Integer>) nwm.getValueTcp("RAZRD", m, 2)) {
				persoCacheTemp.add(num);
				persoCache.add(j.getPersonnages().get(PpTools.valeurToIndex(num)));
			}
			for (Joueur jou : jeu.getJoueurs().values()) {
				jou.getConnection()
						.envoyer(nwm.construirePaquetTcp("RAZID", i, j.getCouleur(), l, persoCacheTemp,
								jeu.getLieux().get(i).getForce() + nbCarteMatériel,
								jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour));
			}
		}
		if (initializer != null)
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		defense.add(persoCache);
		defense.add(nbCarteMatériel);
		return defense;
	}

	private boolean lieuAttaquableReseau(int i) {
		if (jeu.getLieux().get(i).isOuvert() && !jeu.getLieux().get(i).getPersonnage().isEmpty()) {
			if (jeu.getLieux().get(i).getNbZombies() > 0) {
				if (jeu.getLieux().get(i).estAttaquable()) {
					String message = nwm.construirePaquetTcp("RAZA", i,
							PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()),
							jeu.getLieux().get(i).getForce(), jeu.getLieux().get(i).getNbZombies(), partieId,
							numeroTour);
					for (Joueur joueur : jeu.getJoueurs().values())
						joueur.getConnection().envoyer(message);
					return true;
				} else {
					String message = nwm.construirePaquetTcp("RAZPA", 4,
							PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()), ReasonType.FORCE,
							jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
					for (Joueur joueur : jeu.getJoueurs().values())
						joueur.getConnection().envoyer(message);
				}
			} else {
				String message = nwm.construirePaquetTcp("RAZPA", 4,
						PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()), ReasonType.ZOMBIE,
						jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
				for (Joueur joueur : jeu.getJoueurs().values())
					joueur.getConnection().envoyer(message);
			}
		} else {
			String message = nwm.construirePaquetTcp("RAZPA", 4,
					PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()), ReasonType.PION,
					jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
			for (Joueur joueur : jeu.getJoueurs().values())
				joueur.getConnection().envoyer(message);
		}
		return false;
	}

	private void attaqueZombie(int i) {
		System.out.println(jeu.toString());
		Joueur jou = jeu.voteJoueur(jeu.getLieux().get(i).getNum());
		List<Integer> listePion = new ArrayList<>();
		for (Personnage p : jou.getPersonnages().values()) {
			if (p.getMonLieu() == jeu.getLieux().get(i)) {
				listePion.add(p.getPoint());
			}
		}
		String m = nwm.construirePaquetTcp("RAZDS", i, listePion, partieId, numeroTour);
		jou.getConnection().envoyer(m);
		jou.getConnection().attendreMessage("RAZCS");
		String rep = jou.getConnection().getMessage("RAZCS");
		PionCouleur pionCou = (PionCouleur) nwm.getValueTcp("RAZCS", rep, 2);
		int pion = PpTools.getPionByValue(pionCou);
		jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
		if (initializer != null)
			initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
		m = nwm.construirePaquetTcp("RAZIF", i, pionCou, jeu.getLieux().get(i).getNbZombies(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(m);
		if (initializer != null)
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
	}

	private void placementPersonnage() {
		for (int n = 0; n < jeu.getJoueurs().get(0).getPersonnages().size(); n++) {
			for (int i = 0; i < jeu.getJoueurs().size(); i++) {
				String message = nwm.construirePaquetTcp("PIIJ", jeu.nombrePlaceDisponible(),
						jeu.getPersonnageAPlace(jeu.getJoueurs().get(i)), partieId);
				jeu.getJoueurs().get(i).getConnection().envoyer(message);
				out.println(jeu.toString());
				out.println();
				out.println("Lancement des dés.");
				int x = rd.nextInt(6) + 1;
				int y = rd.nextInt(6) + 1;
				out.println("Résultat du lancement :");
				List<Integer> des = new ArrayList<>();
				des.add(x);
				des.add(y);
				List<Integer> listePion = jeu.getDestinationPossible(x, y);
				message = nwm.construirePaquetTcp("PIRD", des, listePion, partieId);
				jeu.getJoueurs().get(i).getConnection().envoyer(message);
				jeu.getJoueurs().get(i).getConnection().attendreMessage("PICD");
				String rep = jeu.getJoueurs().get(i).getConnection().getMessage("PICD");
				// TODO
				int destEntre = (int) nwm.getValueTcp("PICD", rep, 1);
				int persEntre = (int) nwm.getValueTcp("PICD", rep, 2);
				jeu.placePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(persEntre), destEntre);
				message = nwm.construirePaquetTcp("PIIG", jeu.getJoueurs().get(i).getCouleur(), des, listePion,
						destEntre, persEntre, partieId);
				for (Joueur j : jeu.getJoueurs().values())
					if (j != jeu.getJoueurs().get(i))
						j.getConnection().envoyer(message);
			}
			if (initializer != null)
				initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));
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
			String message = nwm.construirePaquetTcp("FP", cond, gagnantNotFair.getCouleur(), partieId, numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				j.getConnection().envoyer(message);

			if (initializer != null)
				initializer.finPartie();
			if (initializer != null)
				initializer.getGagnant(gagnantNotFair);

			status = Status.COMPLETE;

			try {
				nwm.getTcpServeur().arreter();
				nwm.arreterUdp();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public Status getStatus() {
		return status;
	}

	public void setLieuZombie(ArrayList<Integer> lieuZombie) {
		this.lieuZombie = lieuZombie;
	}
}