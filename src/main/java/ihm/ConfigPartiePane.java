package ihm;

import ihm.DataControl.ApplicationPane;
import ihm.event.ConfigListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class ConfigPartiePane extends StackPane implements ConfigListener {
    // private ControleurJeu cj = new ControleurJeu(); // mettre ne paramètres les
    // joueurs

    private ScreenControl sControl = null;
    private Core core = null;
    private final ApplicationPane paneName = ApplicationPane.CONFIG;
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

    private Font policeNom = Font.font("Segoe UI", 25);
    private int hauteurElemtents = 60;
    private int largeurTF = 200;
    private int largeurTexte = 220;
    private int spacing = 30;
    private CornerRadii coinfb = new CornerRadii(5.0);
    private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

    private Insets botPadding = new Insets(10);

    public ConfigPartiePane(ScreenControl sc, Core c) {
        core = c;
        sControl = sc;
        stackPane.setAlignment(Pos.CENTER);
        // titre
        Label titre1 = new Label("Rejoindre\n\tune partie");
        titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
        titre1.setTextFill(Color.BLACK);

        VBox titre = new VBox(titre1);
        titre.setAlignment(Pos.CENTER);
        titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
        titre.setPrefWidth(730);
        titre.setMinWidth(730);

        ////

        Label desc = new Label("Entrez le nom de la partie");
        desc.setAlignment(Pos.CENTER);
        desc.setFont(policeNom);
        desc.setMinHeight(hauteurElemtents);
        //desc.setBackground(fondBlanc);
        desc.setPadding(botPadding);

        TextField nomP = new TextField();
        nomP.setText("Partie");
        nomP.setFont(policeNom);
        nomP.setPrefSize(largeurTF, hauteurElemtents);
        nomP.setMinHeight(hauteurElemtents);
        
        VBox partie = new VBox();
        partie.getChildren().addAll(desc, nomP);
        partie.setBackground(fondBlanc);
        partie.setPadding(new Insets(10));
        partie.setPrefWidth(220);
        partie.setMaxWidth(400);
        
        VBox vbCenter = new VBox();
        vbCenter.setAlignment(Pos.CENTER);
        //vbCenter.setSpacing(spacing);
        //vbCenter.setBackground(fondBlanc);
        vbCenter.getChildren().addAll(partie);


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
            //TODO
            //sc.setPaneOnTop(ApplicationPane.WAIT);
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