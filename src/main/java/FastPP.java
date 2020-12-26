import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pp.controleur.ControleurJeu;
import pp.ihm.event.EvenementStockage;
import reseau.socket.ControleurReseau;
import reseau.tool.ThreadOutils;
import reseau.type.Couleur;
import reseau.type.Statut;

public class FastPP {

	static ControleurJeu controleurJeu;

	public static void main(String[] args) {
		EvenementStockage.desactiverEvent(true);
		ThreadOutils.asyncTaskRepeat("FastPPCore", 1000, () -> {
			try {
				controleurJeu = new ControleurJeu("Test", 0, 3);
				controleurJeu.setPlacerJoueur(true);
				while (controleurJeu.getStatus() != Statut.COMPLETE)
					Thread.yield();

				List<Couleur> couleurs = new ArrayList<>();
				couleurs.add(Couleur.B);
				couleurs.add(Couleur.J);
				couleurs.add(Couleur.R);
				controleurJeu.setJoueurCouleur(couleurs, new ArrayList(controleurJeu.getJeu().getJoueurs().values()));
				ControleurReseau.arreterUdp();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
