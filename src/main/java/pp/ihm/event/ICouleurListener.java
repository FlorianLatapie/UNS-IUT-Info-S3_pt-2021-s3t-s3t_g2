package pp.ihm.event;

import java.util.List;

import pp.Joueur;
/**
 * 
 * @author Sebastien
 *
 */
public interface ICouleurListener {
	/**
	 * @author Sebastien
	 * permet d'appliquer la couleur au joueur selon celle qui est choisie par la combo box
	 * @param joueurs liste des joueurs auquels il faut changer leur couleur 
	 */
	void joueurNoms(List<Joueur> joueurs);
}
