package ihm;

import ihm.DataControl.ApplicationPane;
import ihm.eventListener.FinListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The Class AccueilPane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class FinDePartiePane extends StackPane implements FinListener {
    // private ControleurJeu cj = new ControleurJeu(); // mettre ne paramètres les
    // joueurs

    private ScreenControl sControl = null;
    private Core core = null;
    private final ApplicationPane paneName = ApplicationPane.ENDGAME;
    // définition des variable pour la suite du pane
    private int tailleCarreCentral = 800; // l'interface est sur un stackPane qui peut tourner avec des crans de 90
    // degrés
    private int hBouton = 75;
    private int lBouton = 150;
    private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
    private CornerRadii coin = new CornerRadii(15.0);
    private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
    private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
    private StackPane stackPane = new StackPane();
    private GaussianBlur flou = new GaussianBlur(30);

    private Font policeNom = Font.font("Segoe UI", 17);

    private CornerRadii coinfb = new CornerRadii(5.0);
    private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

    private int valPadding = 10;
    private Insets padding = new Insets(valPadding, valPadding * 2, valPadding, valPadding * 2);

    public FinDePartiePane(ScreenControl sc, Core c) {
        core = c;
        sControl = sc;
        stackPane.setAlignment(Pos.CENTER);

        // titre
        Label titre1 = new Label("Fin de\nla partie");
        titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
        titre1.setTextFill(Color.BLACK);

        VBox titre = new VBox(titre1);
        titre.setAlignment(Pos.CENTER);
        titre.setMinWidth(400);
        titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));

        VBox vbCenter = new VBox();
        vbCenter.setAlignment(Pos.CENTER);
        vbCenter.getChildren().addAll(titre);

        ///////////////////////////////////////////////////////////////////////////////////////
        BorderPane bBas = new BorderPane();

        HBox bBasCentre = new HBox();
        Label gagnant1 = new Label("Le joueur " + "JOUEUR X" + " a gagné");
        gagnant1.setFont(policeNom);
        gagnant1.setBackground(fondBlanc);
        gagnant1.setPadding(padding);
        bBasCentre.setAlignment(Pos.CENTER);
        bBasCentre.getChildren().addAll(gagnant1);

        Button bRetour1 = new Button("RETOUR");
        bRetour1.setPrefSize(lBouton, hBouton);
        bRetour1.setMinSize(lBouton, hBouton);
        bRetour1.setFont(policeBouton);
        bRetour1.setStyle(styleBoutons);


        bRetour1.setOnMouseEntered(event -> {
            bRetour1.setStyle(styleBoutonsSouris);
        });
        bRetour1.setOnMouseExited(event -> {
            bRetour1.setStyle(styleBoutons);
        });
        bRetour1.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

        Button bRetour2 = new Button("RETOUR");
        bRetour2.setPrefSize(lBouton, hBouton);
        bRetour2.setMinSize(lBouton, hBouton);
        bRetour2.setFont(policeBouton);
        bRetour2.setStyle(styleBoutons);


        bRetour2.setOnMouseEntered(event -> {
            bRetour2.setStyle(styleBoutonsSouris);
        });
        bRetour2.setOnMouseExited(event -> {
            bRetour2.setStyle(styleBoutons);
        });
        bRetour2.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));
        bRetour2.setRotate(-90);

        bBas.setCenter(bBasCentre);
        bBas.setLeft(bRetour1);
        bBas.setRight(bRetour2);


        ///


        BorderPane bHaut = new BorderPane();

        HBox bHautCentre = new HBox();
        Label gagnant2 = new Label("Le joueur " + "JOUEUR X" + " a gagné");
        gagnant2.setFont(policeNom);
        gagnant2.setBackground(fondBlanc);
        gagnant2.setPadding(padding);
        bHautCentre.setAlignment(Pos.CENTER);
        bHautCentre.getChildren().addAll(gagnant2);

        Button bRetour3 = new Button("RETOUR");
        bRetour3.setPrefSize(lBouton, hBouton);
        bRetour3.setMinSize(lBouton, hBouton);
        bRetour3.setFont(policeBouton);
        bRetour3.setStyle(styleBoutons);

        bRetour3.setOnMouseEntered(event -> {
            bRetour3.setStyle(styleBoutonsSouris);
        });
        bRetour3.setOnMouseExited(event -> {
            bRetour3.setStyle(styleBoutons);
        });
        bRetour3.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

        Button bRetour4 = new Button("RETOUR");
        bRetour4.setPrefSize(lBouton, hBouton);
        bRetour4.setMinSize(lBouton, hBouton);
        bRetour4.setFont(policeBouton);
        bRetour4.setStyle(styleBoutons);


        bRetour4.setOnMouseEntered(event -> {
            bRetour4.setStyle(styleBoutonsSouris);
        });
        bRetour4.setOnMouseExited(event -> {
            bRetour4.setStyle(styleBoutons);
        });
        bRetour4.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));
        bRetour4.setRotate(-90);

        bHaut.setRotate(180);
        bHaut.setCenter(bHautCentre);
        bHaut.setLeft(bRetour3);
        bHaut.setRight(bRetour4);

        ///
        HBox hDroite = new HBox();
        Label gagnant3 = new Label("Le joueur " + "JOUEUR X" + " a gagné");
        gagnant3.setFont(policeNom);
        gagnant3.setBackground(fondBlanc);
        gagnant3.setPadding(padding);
        gagnant3.setRotate(-90);
        hDroite.setAlignment(Pos.CENTER);
        hDroite.getChildren().addAll(gagnant3);


        ///


        HBox hGauche = new HBox();
        Label gagnant4 = new Label("Le joueur " + "JOUEUR X" + " a gagné");
        gagnant4.setFont(policeNom);
        gagnant4.setBackground(fondBlanc);
        gagnant4.setPadding(padding);
        gagnant4.setRotate(90);
        hGauche.setAlignment(Pos.CENTER);
        hGauche.getChildren().addAll(gagnant4);
        //////////////////////////////////////////////////////////////////////////////////////

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

        centreMenu.setCenter(vbCenter);
        centreMenu.setBottom(bBas);
        centreMenu.setTop(bHaut);
        centreMenu.setRight(hDroite);
        centreMenu.setLeft(hGauche);
        // rotation de l'interface
        // centreMenu.setRotate(90);

        // boite du fond qui contient tout
        HBox fond = new HBox();
        fond.setAlignment(Pos.CENTER);
        fond.setEffect(flou);
        fond.getChildren().add(imgFond);

        stackPane.getChildren().addAll(fond, centreMenu);

        //centreMenu.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));

        stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        this.getChildren().add(stackPane);
        sControl.registerNode(paneName, this);
        sControl.setPaneOnTop(paneName);

    }

    @Override
    public void getGagnant(String nom) {
        System.out.println("VAINQ " + nom);
    }
}