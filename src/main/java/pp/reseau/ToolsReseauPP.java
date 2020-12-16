package pp.reseau;

import java.util.ArrayList;
import java.util.List;

import pp.Joueur;
import pp.Partie;
import reseau.type.Couleur;

public class ToolsReseauPP {
	
	public List<Couleur> getJoueursCouleurs(Partie jeu){
		List<Couleur> lc = new ArrayList<>();
		lc.add(jeu.getChefVIgile().getCouleur());
		for (Joueur j : jeu.getJoueurs().values())
			if (j != jeu.getChefVIgile() && j.isEnVie())
				lc.add(j.getCouleur());
		return lc;
	}
}
