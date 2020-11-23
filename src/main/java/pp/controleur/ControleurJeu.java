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
			String m = nwm.getPacketsUdp().get("ACP").build(intPartieId, nwm.getIp().getHostAddress(), port, nomPartie,
					nbjtotal, nbjr, nbjv, status);
			nwm.getUdpConnexion().envoyer(m);

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
				e.printStackTrace();
			}
		}

		if (nwm.getUdpConnexion() != null)
			try {
				nwm.getUdpConnexion().arreter();
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

		String m = nwm.getPacketsTcp().get("IP").build(jeu.getJoueursNoms(), jeu.getJoueursCouleurs(), 0, 3, partieId);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		this.placementPersonnage();
		this.jeu.resultatChefVigile(jeu.getJoueurs().get(0));
		this.lieuZombie = arriveZombie();
		this.jeu.entreZombie(lieuZombie);

		m = nwm.getPacketsTcp().get("PIPZ").build(lieuZombie, partieId);
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

		String m = nwm.getPacketsTcp().get("IT").build(jeu.getChefVIgile().getCouleur(), getJoueursCouleurs(), partieId,
				numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		fouilleCamion();
		electionChefVigi();
		this.lieuZombie = arriveZombie();
		ArrayList<Integer> destination = new ArrayList<>();
		phasechoixDestination(destination);
		phaseDeplacementPerso(destination, lieuZombie);

		if (this.finJeu())
			return;

		jeu.entreZombie(lieuZombie);
		if (initializer != null)
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		jeu.fermerLieu();
		if (initializer != null)
			initializer.lieuFermeAll(new ArrayList<>(jeu.getLieux().values()));
		if (initializer != null)
			initializer.lieuOuvertAll(new ArrayList<>(jeu.getLieux().values()));

		if (finJeu())
			return;

		if (!attaqueZombie())
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
		// TODO TAILLE DE PIOCHE A 0
		String m = nwm.getPacketsTcp().get("PFC").build(getJoueursCouleurs(), 0, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		// TODO PREVENIR QUI FOUILLE LE CAMION=
		if (!jeu.getLieux().get(4).afficheJoueurSurLieu().isEmpty()) {
			Joueur j = jeu.voteJoueur(4);
			s += j + " fouille le camion!\n";
			s += "Le camion est vide.";
		} else {
			s += "Personne ne fouille le camion.";
		}

		// TODO CARTE NUL
		m = nwm.getPacketsTcp().get("RFC").build(CarteType.NUL, CarteType.NUL, CarteType.NUL, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);
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
		String m = nwm.getPacketsTcp().get("PECV").build(getPersosLieu(5), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		if (!jeu.getLieux().get(5).afficheJoueurSurLieu().isEmpty()) {
			Joueur j = jeu.voteJoueur(5);
			jeu.resultatChefVigile(j);
			out.println(j + " est le nouveau chef des vigiles!");
			jeu.setNewChef(true);

			m = nwm.getPacketsTcp().get("RECV").build(jeu.getChefVIgile().getCouleur(), partieId, numeroTour);
			for (Joueur joueur : jeu.getJoueurs().values())
				j.getConnection().envoyer(m);
			if (initializer != null)
				initializer.electionChef("Nouveau chef des vigiles : " + jeu.getChefVIgile());
		} else {
			out.println("Pas de nouveau chef des vigiles!");
			jeu.setNewChef(false);

			m = nwm.getPacketsTcp().get("RECV").build(Couleur.NUL, partieId, numeroTour);
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

		String m = nwm.getPacketsTcp().get("PAZ").build(jeu.getChefVIgile().getCouleur(), ve, partieId, numeroTour);
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

		// TODO AZLAZ lieuZombie
		out.println(jeu.getChefVIgile() + " , chef des vigiles, regarde les résulats de l'arrivé des Zombies:");
		for (Integer integer : lieuZombie) {
			out.println(jeu.getLieux().get(integer) + "-> Zombie + 1");
		}
		// l'afficher sur l'ecran du CV s'il y en a un et s'il a un perso sur le lieu 5
		// (PC)
		// regarder si un joueur utilise une carte camSecu et si oui l'afficher sur son
		// ecran et defausse la carte
		jeu.getChefVIgile().getConnection()
				.envoyer(nwm.getPacketsTcp().get("AZLAZ").build(lieuZombie, partieId, numeroTour));

		return lieuZombie;
	}

	private void phasechoixDestination(ArrayList<Integer> destination) throws InterruptedException {
		VigileEtat ve = jeu.getNewChef() ? VigileEtat.NE : VigileEtat.NUL;
		String m = nwm.getPacketsTcp().get("PCD").build(jeu.getChefVIgile().getCouleur(), ve, partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		if (jeu.getNewChef()) {
			jeu.getChefVIgile().getConnection().attendreMessage("CDDCV");

			m = jeu.getChefVIgile().getConnection().getMessage("CDDCV");
			int dest = (int) nwm.getPacketsTcp().get("CDDCV").getValue(m, 1);
			destination.add(dest);

			m = nwm.getPacketsTcp().get("CDCDV").build(jeu.getChefVIgile().getCouleur(), dest, partieId, numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				if (!(j.isChefDesVigiles() && ve == VigileEtat.NE))
					j.getConnection().envoyer(m);
			if (initializer != null)
				initializer.prevenirDeplacementVigile("Le chef des vigile (" + jeu.getChefVIgile().getCouleur()
						+ ") a choisi la detination :" + this.jeu.getLieux().get(dest));
		}

		int nb = nbjtotal - jeu.getNombreJoueurMort();
		nb += jeu.getNewChef() ? -1 : 0;

		out.println("mes" + nb);

		HashMap<String, Integer> idj = new HashMap<>();
		for (Joueur j : jeu.getJoueurs().values())
			if (j != jeu.getChefVIgile() && j.isEnVie())
				j.getConnection().attendreMessage("CDDJ");

		for (Joueur j : jeu.getJoueurs().values())
			if (j != jeu.getChefVIgile() && j.isEnVie()) {
				String rep = j.getConnection().getMessage("CDDJ");
				String id = (String) nwm.getPacketsTcp().get("CDDJ").getValue(rep, 4);
				int idJoueur = (int) nwm.getPacketsTcp().get("CDDJ").getValue(rep, 1);
				idj.put(id, idJoueur);
			}

		if (!jeu.getNewChef())
			for (Map.Entry<String, Integer> d : idj.entrySet())
				if (d.getKey().equals(jeu.getChefVIgile().getJoueurId()))
					destination.add(d.getValue());

		for (Joueur j : jeu.getJoueurs().values())
			if (!j.isChefDesVigiles() && j.isEnVie())
				for (Map.Entry<String, Integer> d : idj.entrySet())
					if (d.getKey().equals(j.getJoueurId()))
						destination.add(d.getValue());

		m = nwm.getPacketsTcp().get("CDFC").build(partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		for (Joueur j : this.jeu.getJoueurs().values()) {
			if (jmort.contains(j)) {

				m = nwm.getPacketsTcp().get("CDZVI").build(partieId, numeroTour);
				j.getConnection().envoyer(m);

				j.getConnection().attendreMessage("CDDZVJE");
				String rep = j.getConnection().getMessage("CDDZVJE");

				int dvz = (int) nwm.getPacketsTcp().get("CDDZVJE").getValue(rep, 1);

				jeu.getLieux().get(dvz).addZombie();
			}
		}

	}

	private void phaseDeplacementPerso(ArrayList<Integer> destination, ArrayList<Integer> zombie) {
		String m = nwm.getPacketsTcp().get("PDP").build(jeu.getChefVIgile().getCouleur(), destination, zombie,
				jeu.getLieuxFermes(), partieId, numeroTour);
		for (Joueur j : jeu.getJoueurs().values())
			j.getConnection().envoyer(m);

		int compteur = 0;
		for (int i = 0; i < jeu.getJoueurs().size(); i++) {
			if (jeu.getJoueurs().get(i).isChefDesVigiles()) {
				out.println(jeu.toString());

				m = nwm.getPacketsTcp().get("DPD").build(destination.get(compteur),
						jeu.getAllPersoPossible(jeu.getJoueurs().get(i)), partieId, numeroTour);
				jeu.getJoueurs().get(i).getConnection().envoyer(m);

				jeu.getJoueurs().get(i).getConnection().attendreMessage("DPR");
				String message = jeu.getJoueurs().get(i).getConnection().getMessage("DPR");

				int dest = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 1);
				int pion = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 2);
				jeu.deplacePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(pion), dest);

				if (initializer != null)
					initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
				if (finJeu())
					return;

				this.jeu.fermerLieu();
				compteur += 1;

				m = nwm.getPacketsTcp().get("DPI").build(jeu.getJoueurs().get(i).getCouleur(), dest, pion,
						CarteType.NUL, partieId, numeroTour);
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

				m = nwm.getPacketsTcp().get("DPD").build(destination.get(compteur),
						jeu.getAllPersoPossible(jeu.getJoueurs().get(i)), partieId, numeroTour);

				jeu.getJoueurs().get(i).getConnection().envoyer(m);

				jeu.getJoueurs().get(i).getConnection().attendreMessage("DPR");
				String message = jeu.getJoueurs().get(i).getConnection().getMessage("DPR");

				int dest = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 1);
				int pion = (int) nwm.getPacketsTcp().get("DPR").getValue(message, 2);
				jeu.deplacePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(pion), dest);

				if (initializer != null)
					initializer.forceLieuAll(new ArrayList<>(jeu.getLieux().values()));
				if (finJeu())
					return;

				this.jeu.fermerLieu();
				compteur += 1;

				m = nwm.getPacketsTcp().get("DPI").build(jeu.getJoueurs().get(i).getCouleur(), dest, pion,
						CarteType.NUL, partieId, numeroTour);
				for (Joueur j : jeu.getJoueurs().values())
					if (j != jeu.getJoueurs().get(i))
						j.getConnection().envoyer(m);
				if (initializer != null)
					initializer.destionationPersoAll(new ArrayList<>(jeu.getLieux().values()));

			}
		}
	}

	private boolean attaqueZombie() {
		List<Integer> nb = jeu.lastAttaqueZombie();
		if (initializer != null)
			initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
		String me = nwm.getPacketsTcp().get("PRAZ").build(nb.get(0), nb.get(1), jeu.getLieuxOuverts(),
				jeu.getNbZombieLieux(), jeu.getNbPionLieux(), partieId, numeroTour);
		for (Joueur joueur : jeu.getJoueurs().values())
			joueur.getConnection().envoyer(me);

		for (int i = 1; i < 7; i++) {
			if (jeu.getLieux().get(i).isOuvert()) {
				if (i == 4) {// si parking
					for (int j = 0; j < jeu.getLieux().get(i).getNbZombies(); j++) {
						String message = nwm.getPacketsTcp().get("RAZA").build(4,
								PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()),
								jeu.getLieux().get(i).getForce(), jeu.getLieux().get(i).getNbZombies(), partieId,
								numeroTour);
						for (Joueur joueur : jeu.getJoueurs().values())
							joueur.getConnection().envoyer(message);

						if (!jeu.getLieux().get(i).getPersonnage().isEmpty()) {
							System.out.println(jeu.toString());
							Joueur jou = jeu.voteJoueur(4);
							List<Integer> listePion = new ArrayList<>();
							for (Personnage p : jou.getPersonnages().values()) {
								if (p.getMonLieu() == jeu.getLieux().get(i)) {
									listePion.add(p.getPoint());
								}
							}

							String m = nwm.getPacketsTcp().get("RAZDS").build(i, listePion, partieId, numeroTour);

							jou.getConnection().envoyer(m);

							jou.getConnection().attendreMessage("RAZCS");
							String rep = jou.getConnection().getMessage("RAZCS");
							PionCouleur pionCou = (PionCouleur) nwm.getPacketsTcp().get("RAZCS").getValue(rep, 2);
							int pion = PpTools.getPionByValue(pionCou);

							jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
							if (initializer != null)
								initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
							jeu.getLieux().get(i).setNbZombies(jeu.getLieux().get(i).getNbZombies() - 1);

							m = nwm.getPacketsTcp().get("RAZIF").build(i, pionCou, jeu.getLieux().get(i).getNbZombies(),
									partieId, numeroTour);
							for (Joueur joueur : jeu.getJoueurs().values())
								joueur.getConnection().envoyer(m);
							if (initializer != null)
								initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));
						}
						if (this.finJeu())
							return false;

					}
				} else if (jeu.getLieux().get(i).estAttaquable()) {
					String message = nwm.getPacketsTcp().get("RAZA").build(4,
							PpTools.getPionsCouleurByPerso(jeu.getLieux().get(i).getPersonnage()),
							jeu.getLieux().get(i).getForce(), jeu.getLieux().get(i).getNbZombies(), partieId,
							numeroTour);
					for (Joueur joueur : jeu.getJoueurs().values())
						joueur.getConnection().envoyer(message);

					System.out.println(jeu.toString());
					Joueur jou = jeu.voteJoueur(jeu.getLieux().get(i).getNum());

					List<Integer> listePion = new ArrayList<>();
					for (Personnage p : jou.getPersonnages().values()) {
						if (p.getMonLieu() == jeu.getLieux().get(i)) {
							listePion.add(p.getPoint());
						}
					}

					String m = nwm.getPacketsTcp().get("RAZDS").build(i, listePion, partieId, numeroTour);

					jou.getConnection().envoyer(m);

					jou.getConnection().attendreMessage("RAZCS");
					String rep = jou.getConnection().getMessage("RAZCS");

					PionCouleur pionCou = (PionCouleur) nwm.getPacketsTcp().get("RAZCS").getValue(rep, 2);
					int pion = PpTools.getPionByValue(pionCou);

					jeu.sacrifie(jou, PpTools.valeurToIndex(pion));
					if (initializer != null)
						initializer.nbPersoJoueurAll(new ArrayList<>(jeu.getJoueurs().values()));
					jeu.getLieux().get(i).setNbZombies(0);

					m = nwm.getPacketsTcp().get("RAZIF").build(i, pionCou, jeu.getLieux().get(i).getNbZombies(),
							partieId, numeroTour);
					for (Joueur joueur : jeu.getJoueurs().values())
						joueur.getConnection().envoyer(m);
					if (initializer != null)
						initializer.nbZombiesLieuAll(new ArrayList<>(jeu.getLieux().values()));

				}
				if (this.finJeu())
					return false;
			}
		}

		return true;
	}

	private void placementPersonnage() {
		for (int n = 0; n < jeu.getJoueurs().get(0).getPersonnages().size(); n++) {
			for (int i = 0; i < jeu.getJoueurs().size(); i++) {

				String message = nwm.getPacketsTcp().get("PIIJ").build(jeu.nombrePlaceDisponible(),
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
				message = nwm.getPacketsTcp().get("PIRD").build(des, listePion, partieId);
				jeu.getJoueurs().get(i).getConnection().envoyer(message);

				jeu.getJoueurs().get(i).getConnection().attendreMessage("PICD");
				String rep = jeu.getJoueurs().get(i).getConnection().getMessage("PICD");

				// TODO
				int destEntre = (int) nwm.getPacketsTcp().get("PICD").getValue(rep, 1);
				int persEntre = (int) nwm.getPacketsTcp().get("PICD").getValue(rep, 2);
				jeu.placePerso(jeu.getJoueurs().get(i), PpTools.valeurToIndex(persEntre), destEntre);

				message = nwm.getPacketsTcp().get("PIIG").build(jeu.getJoueurs().get(i).getCouleur(), des, listePion,
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
	public boolean finJeu() {
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
		if ((lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4)) || (nbPerso <= 4 && jeu.getJoueurs().size() < 6) || (nbPerso <= 6 && jeu.getJoueurs().size() == 6)) {
			CondType cond;
			if (lieu.size() < 2 && lieu.get(0) != this.jeu.getLieux().get(4))
				cond = CondType.LIEUX;
			else
				cond = CondType.PION;

			for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
				if (this.jeu.getJoueurs().get(i).isEnVie()
						&& this.jeu.getJoueurs().get(i).getPersonnages().size() == 0) {
					this.jeu.getJoueurs().get(i).setEnVie(false);
				}
			}
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
			Joueur gagnantNotFair = vainqueur.get(new Random().nextInt(vainqueur.size()));
			String message = nwm.getPacketsTcp().get("FP").build(cond, gagnantNotFair.getCouleur(), partieId,
					numeroTour);
			for (Joueur j : jeu.getJoueurs().values())
				j.getConnection().envoyer(message);

			if (initializer != null)
				initializer.finPartie();
			if (initializer != null)
				initializer.getGagnant(gagnantNotFair);

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			status = Status.COMPLETE;

			try {
				nwm.getUdpConnexion().arreter();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				nwm.getTcpServeur().arreter();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		}
		return false;
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