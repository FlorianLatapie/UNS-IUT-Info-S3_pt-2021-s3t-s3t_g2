package ihm;

import ihm.DataControl.ApplicationPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import reseau.type.Couleur;

import java.util.List;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class CouleurPane extends StackPane {
    // private ControleurJeu cj = new ControleurJeu(); // mettre ne paramètres les
    // joueurs

    private ScreenControl sControl = null;
    private Core core = null;
    private final ApplicationPane paneName = ApplicationPane.COULEUR;
    // définition des variable pour la suite du pane
    private int tailleCarreCentral = 800; // l'interface est sur un stackPane qui peut tourner avec des crans de 90
    // degrés
    private int hBouton = 75;
    private int lBouton = 150;
    //private int marge = tailleCarreCentral / 25;
    //private Insets margeBoutons = new Insets(marge, marge, marge, marge);
    private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
    private CornerRadii coin = new CornerRadii(15.0);
    private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
    private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
    private StackPane stackPane = new StackPane();
    private GaussianBlur flou = new GaussianBlur(30);

    private Font policeNom = Font.font("Segoe UI", 17);
    private int hauteurElemtents = 60;
    private int largeurTF = 100;
    private int largeurComboBox = 220;
    private int spacing = 30;
    private CornerRadii coinfb = new CornerRadii(5.0);
    private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

    private Insets botPadding = new Insets(0, 10, 0, 10);

    public CouleurPane(ScreenControl sc, Core c) {
        core = c;
        sControl = sc;
        stackPane.setAlignment(Pos.CENTER);
        // titre
        Label titre1 = new Label("Choisissez vos \n\tcouleurs");
        titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
        titre1.setTextFill(Color.BLACK);

        VBox titre = new VBox(titre1);
        titre.setAlignment(Pos.CENTER);
        titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
        titre.setPrefWidth(730);
        titre.setMinWidth(730);

        ////

        /*Label desc = new Label("Choisissez un nombre de joueurs entre 3 et 6");
        desc.setFont(policeNom);
        desc.setMinHeight(hauteurElemtents);
        desc.setBackground(fondBlanc);
        desc.setPadding(botPadding);
*/
        VBox vJoueurs = new VBox();

        HBox j1 = new HBox();

        Label nom1 = new Label();
        nom1.setBackground(fondBlanc);
        nom1.setText("Joueur 1");
        nom1.setFont(policeNom);
        nom1.setPrefSize(largeurTF, hauteurElemtents);
        nom1.setMinHeight(hauteurElemtents);
        nom1.setAlignment(Pos.CENTER);
        ComboBox<String> couleur1 = new ComboBox<String>();
        couleur1.setMinHeight(hauteurElemtents);
        couleur1.setPrefSize(largeurComboBox, hauteurElemtents);
        couleur1.getItems().addAll(DataControl.couleursJoueur);

        j1.setAlignment(Pos.CENTER);
        j1.setSpacing(spacing);
        j1.getChildren().addAll(nom1, couleur1);
        j1.setDisable(false);

        ///

        HBox j2 = new HBox();

        Label nom2 = new Label();
        nom2.setBackground(fondBlanc);
        nom2.setText("Joueur 2");
        nom2.setFont(policeNom);
        nom2.setPrefSize(largeurTF, hauteurElemtents);
        nom2.setMinHeight(hauteurElemtents);
        nom2.setAlignment(Pos.CENTER);
        ComboBox<String> couleur2 = new ComboBox<String>();
        couleur2.setPrefSize(largeurComboBox, hauteurElemtents);
        couleur2.setMinHeight(hauteurElemtents);
        couleur2.getItems().addAll(DataControl.couleursJoueur);

        j2.setAlignment(Pos.CENTER);
        j2.setSpacing(spacing);
        j2.getChildren().addAll(nom2, couleur2);
        j2.setDisable(false);

        ///

        HBox j3 = new HBox();

        Label nom3 = new Label();
        nom3.setBackground(fondBlanc);
        nom3.setText("Joueur 3");
        nom3.setFont(policeNom);
        nom3.setPrefSize(largeurTF, hauteurElemtents);
        nom3.setMinHeight(hauteurElemtents);
        nom3.setAlignment(Pos.CENTER);
        ComboBox<String> couleur3 = new ComboBox<String>();
        couleur3.setMinHeight(hauteurElemtents);
        couleur3.setPrefSize(largeurComboBox, hauteurElemtents);
        couleur3.getItems().addAll(DataControl.couleursJoueur);

        j3.setAlignment(Pos.CENTER);
        j3.setSpacing(spacing);
        j3.getChildren().addAll(nom3, couleur3);
        j3.setDisable(false);

        ///

        HBox j4 = new HBox();

        Label nom4 = new Label();
        nom4.setBackground(fondBlanc);
        nom4.setText("Joueur 4");
        nom4.setFont(policeNom);
        nom4.setPrefSize(largeurTF, hauteurElemtents);
        nom4.setMinHeight(hauteurElemtents);
        nom4.setAlignment(Pos.CENTER);
        ComboBox<String> couleur4 = new ComboBox<String>();
        couleur4.setMinHeight(hauteurElemtents);
        couleur4.setPrefSize(largeurComboBox, hauteurElemtents);
        couleur4.getItems().addAll(DataControl.couleursJoueur);

        j4.setAlignment(Pos.CENTER);
        j4.setSpacing(spacing);
        j4.getChildren().addAll(nom4, couleur4);
        j4.setDisable(false);

        ///

        HBox j5 = new HBox();

        Label nom5 = new Label();
        nom5.setBackground(fondBlanc);
        nom5.setText("Joueur 5");
        nom5.setFont(policeNom);
        nom5.setPrefSize(largeurTF, hauteurElemtents);
        nom5.setMinHeight(hauteurElemtents);
        nom5.setAlignment(Pos.CENTER);
        ComboBox<String> couleur5 = new ComboBox<String>();
        couleur5.setMinHeight(hauteurElemtents);
        couleur5.setPrefSize(largeurComboBox, hauteurElemtents);
        couleur5.getItems().addAll(DataControl.couleursJoueur);

        j5.setAlignment(Pos.CENTER);
        j5.setSpacing(spacing);
        j5.getChildren().addAll(nom5, couleur5);
        j5.setDisable(false);

        ///

        HBox j6 = new HBox();

        Label nom6 = new Label();
        nom6.setBackground(fondBlanc);
        nom6.setText("Joueur 6");
        nom6.setFont(policeNom);
        nom6.setPrefSize(largeurTF, hauteurElemtents);
        nom6.setMinHeight(hauteurElemtents);
        nom6.setAlignment(Pos.CENTER);
        ComboBox<String> couleur6 = new ComboBox<String>();
        couleur6.setMinHeight(hauteurElemtents);
        couleur6.setPrefSize(largeurComboBox, hauteurElemtents);
        couleur6.getItems().addAll(DataControl.couleursJoueur);

        j6.setAlignment(Pos.CENTER);
        j6.setSpacing(spacing);
        j6.getChildren().addAll(nom6, couleur6);
        j6.setDisable(true);

        ///

        vJoueurs.setSpacing(14);
        vJoueurs.getChildren().addAll(j1, j2, j3, j4, j5, j6);

        // vJoueurs.setBackground(new Background(new
        // BackgroundFill(Color.BLUE,CornerRadii.EMPTY,null)));

        VBox vbCenter = new VBox();
        vbCenter.setMargin(vJoueurs, new Insets(0, 0, 100, 0));
        vbCenter.setAlignment(Pos.CENTER);
        vbCenter.setSpacing(spacing);
        vbCenter.getChildren().addAll(vJoueurs);


        // boutons
        Button bJouer = new Button("JOUER");
        bJouer.setPrefSize(lBouton, hBouton);
        bJouer.setMinSize(lBouton, hBouton);
        bJouer.setFont(policeBouton);
        bJouer.setStyle(styleBoutons);

        bJouer.setOnMouseEntered(event -> {

            bJouer.setStyle(styleBoutonsSouris);
        });
        bJouer.setOnMouseExited(event -> {
            bJouer.setStyle(styleBoutons);
        });
        bJouer.setOnAction(EventHandler -> {
            boolean isOk = IhmTools.isAllUniqueColor(core.getNbJoueur(), couleur1, couleur2, couleur3, couleur4, couleur5, couleur6);
            if (isOk) {
                List<Couleur> cs = IhmTools.comboStringToColorList(core.getNbJoueur(), couleur1, couleur2, couleur3, couleur4, couleur5, couleur6);
                core.getCj().setJoueurCouleur(cs);
                sc.setPaneOnTop(ApplicationPane.PLATEAU);
            }
        });

        Button bRetour = new Button("RETOUR");
        bRetour.setPrefSize(lBouton, hBouton);
        bRetour.setMinSize(lBouton, hBouton);
        bRetour.setFont(policeBouton);
        bRetour.setStyle(styleBoutons);

        bRetour.setOnMouseEntered(event -> {
            bRetour.setStyle(styleBoutonsSouris);
        });
        bRetour.setOnMouseExited(event -> {
            core.getCj().stopThreads();
            bRetour.setStyle(styleBoutons);
        });
        bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

        // grille contenant les boutons du bas
        AnchorPane boutonsPanneau = new AnchorPane();
        boutonsPanneau.setLeftAnchor(bRetour, 0.0);
        boutonsPanneau.setRightAnchor(bJouer, 0.0);
        boutonsPanneau.getChildren().addAll(bRetour, bJouer);


        // image fond
        ImageView imgFond = new ImageView(DataControl.FOND);
        // carre central qui contient tous les éléments (boutons et titre)
        BorderPane centreMenu = new BorderPane();
        // centreMenu.setBackground(new Background(new
        // BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,null)));
        centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
        centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
        centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
        centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));

        centreMenu.setAlignment(titre, Pos.CENTER);

        centreMenu.setTop(titre);
        centreMenu.setCenter(vbCenter);
        centreMenu.setBottom(boutonsPanneau);

        // rotation de l'interface
        // centreMenu.setRotate(90);

        // boite du fond qui contient tout
        HBox fond = new HBox();
        fond.setAlignment(Pos.CENTER);
        fond.setPrefWidth(100);
        fond.setPrefHeight(100);
        fond.setEffect(flou);
        fond.getChildren().add(imgFond);

        stackPane.getChildren().addAll(fond, centreMenu);
        stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        this.getChildren().add(stackPane);
        sControl.registerNode(paneName, this);
        sControl.setPaneOnTop(paneName);

    }
}