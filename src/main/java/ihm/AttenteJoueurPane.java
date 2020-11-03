package ihm;

import ihm.DataControl.ApplicationPane;
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
public class AttenteJoueurPane extends StackPane {
    // private ControleurJeu cj = new ControleurJeu(); // mettre ne paramètres les
    // joueurs

    private ScreenControl sControl = null;
    private Core core = null;
    private final ApplicationPane paneName = ApplicationPane.WAIT;
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

    private Font policeNom = Font.font("Segoe UI", 37);
    private int hauteurElemtents = 60;
    private int spacing = 30;
    private CornerRadii coinfb = new CornerRadii(5.0);
    private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

    private Insets botPadding = new Insets(0, 10, 0, 10);

    public AttenteJoueurPane(ScreenControl sc, Core c) {
        core = c;
        sControl = sc;
        stackPane.setAlignment(Pos.CENTER);
        // titre
        Label titre1 = new Label("Connexion \n\ten cours");
        titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
        titre1.setTextFill(Color.BLACK);

        VBox titre = new VBox(titre1);
        titre.setAlignment(Pos.CENTER);
        titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
        titre.setPrefWidth(730);
        titre.setMinWidth(730);

        ////

        Label desc = new Label("Attente de la connexion\nde tous les joueurs ...");
        desc.setFont(policeNom);
        desc.setMinHeight(hauteurElemtents);
        desc.setBackground(fondBlanc);
        desc.setPadding(botPadding);

        
        VBox vbCenter = new VBox();
        vbCenter.setAlignment(Pos.CENTER);
        vbCenter.setSpacing(spacing);
        vbCenter.getChildren().addAll(desc);
      

        // boutons
        Button bJouer = new Button("ATTENDEZ");
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
            sc.setPaneOnTop(ApplicationPane.COULEUR);
        });
        //TODO a dé-commenter : bJouer.setDisable(true);

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
        bRetour.setOnAction(EventHandler -> {
        	//TODO arreter la création de la partie 
        	sc.setPaneOnTop(ApplicationPane.ACCUEIL);
        });

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