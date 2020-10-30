import java.util.ArrayList;
import java.util.Random;

import controleur.ControleurJeu;
import reseau.tool.ThreadTool;
import reseau.type.Couleur;

public class MainConsole {

    public static void main(String[] args) throws Exception {
        Random rd;
        ControleurJeu controleur = new ControleurJeu("Test", 5, 0);
        ArrayList<Couleur> couleursRestante = new ArrayList<>();
        rd = new Random();
        couleursRestante.add(Couleur.NOIR);
        couleursRestante.add(Couleur.ROUGE);
        couleursRestante.add(Couleur.JAUNE);
        couleursRestante.add(Couleur.BLEU);
        couleursRestante.add(Couleur.MARRON);
        couleursRestante.add(Couleur.VERT);
        ThreadTool.asyncTask(() -> {
            while (!controleur.joueurConnect())
                ;
            for (int i = 0; i < controleur.getJeu().getJoueurs().size(); i++) {
                Couleur couleur = couleursRestante.get(rd.nextInt(couleursRestante.size()));
                controleur.getJeu().getJoueurs().get(i).setCouleur(couleur);
                couleursRestante.remove(couleur);
            }
            controleur.demarerJeu();
        });

    }

}
