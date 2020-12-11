package pp.ihm;

import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.eventListener.FinListener;
import pp.ihm.langues.International;
import javafx.application.Platform;
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
    private ScreenControl sControl = null;
    private Core core = null;
    private final ApplicationPane paneName = ApplicationPane.ENDGAME;

    private int tailleCarreCentral = 800; 
    private int hBouton = 75;
    private int lBouton = 150;
    
    private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
    private Font policeNom = Font.font("Segoe UI", 17);
    
    
    private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
    private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
    
    private GaussianBlur flou = new GaussianBlur(30);
    private CornerRadii coin = new CornerRadii(15.0);
    private CornerRadii coinfb = new CornerRadii(5.0);
    
    private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

    private int valPadding = 10;
    private Insets padding = new Insets(valPadding, valPadding * 2.0, valPadding, valPadding * 2.0);

    Label gagnant1;
    Label gagnant2;
    Label gagnant3;
    Label gagnant4;

    public FinDePartiePane(ScreenControl sc, Core c) {
        core = c;
        sControl = sc;

        // titre
        Label titre1 = new Label("Fin de\nla partie");//TODO
        titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
        titre1.setTextFill(Color.BLACK);

        VBox titre = new VBox(titre1);
        titre.setAlignment(Pos.CENTER);
        titre.setMinWidth(400);
        titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));

        VBox vbCenter = new VBox();
        vbCenter.setAlignment(Pos.CENTER);
        vbCenter.getChildren().addAll(titre);

        //
        BorderPane bBas = new BorderPane();

        HBox bBasCentre = new HBox();
        gagnant1 = new Label("Le joueur " + "JOUEUR X" + " a gagné");//TODO
        gagnant1.setFont(policeNom);
        gagnant1.setBackground(fondBlanc);
        gagnant1.setPadding(padding);
        bBasCentre.setAlignment(Pos.CENTER);
        bBasCentre.getChildren().addAll(gagnant1);

        Button bRetour1 = new Button(International.trad("bouton.retour"));
        bRetour1.setPrefSize(lBouton, hBouton);
        bRetour1.setMinSize(lBouton, hBouton);
        bRetour1.setFont(policeBouton);
        bRetour1.setStyle(styleBoutons);


        bRetour1.setOnMouseEntered(event -> bRetour1.setStyle(styleBoutonsSouris));
        bRetour1.setOnMouseExited(event -> bRetour1.setStyle(styleBoutons));
        bRetour1.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

        Button bRetour2 = new Button(International.trad("bouton.retour"));
        bRetour2.setPrefSize(lBouton, hBouton);
        bRetour2.setMinSize(lBouton, hBouton);
        bRetour2.setFont(policeBouton);
        bRetour2.setStyle(styleBoutons);


        bRetour2.setOnMouseEntered(event -> bRetour2.setStyle(styleBoutonsSouris));
        bRetour2.setOnMouseExited(event -> bRetour2.setStyle(styleBoutons));
        bRetour2.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));
        bRetour2.setRotate(-90);

        bBas.setCenter(bBasCentre);
        bBas.setLeft(bRetour1);
        bBas.setRight(bRetour2);


        ///


        BorderPane bHaut = new BorderPane();

        HBox bHautCentre = new HBox();
        gagnant2 = new Label("Le joueur " + "JOUEUR X" + " a gagné");//TODO
        gagnant2.setFont(policeNom);
        gagnant2.setBackground(fondBlanc);
        gagnant2.setPadding(padding);
        bHautCentre.setAlignment(Pos.CENTER);
        bHautCentre.getChildren().addAll(gagnant2);

        Button bRetour3 = new Button(International.trad("bouton.retour"));
        bRetour3.setPrefSize(lBouton, hBouton);
        bRetour3.setMinSize(lBouton, hBouton);
        bRetour3.setFont(policeBouton);
        bRetour3.setStyle(styleBoutons);

        bRetour3.setOnMouseEntered(event -> bRetour3.setStyle(styleBoutonsSouris));
        bRetour3.setOnMouseExited(event -> bRetour3.setStyle(styleBoutons));
        bRetour3.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

        Button bRetour4 = new Button(International.trad("bouton.retour"));
        bRetour4.setPrefSize(lBouton, hBouton);
        bRetour4.setMinSize(lBouton, hBouton);
        bRetour4.setFont(policeBouton);
        bRetour4.setStyle(styleBoutons);


        bRetour4.setOnMouseEntered(event -> bRetour4.setStyle(styleBoutonsSouris));
        bRetour4.setOnMouseExited(event -> bRetour4.setStyle(styleBoutons));
        bRetour4.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));
        bRetour4.setRotate(-90);

        bHaut.setRotate(180);
        bHaut.setCenter(bHautCentre);
        bHaut.setLeft(bRetour3);
        bHaut.setRight(bRetour4);

        ///
        HBox hDroite = new HBox();
        gagnant3 = new Label("Le joueur " + "JOUEUR X" + " a gagné");//TODO
        gagnant3.setFont(policeNom);
        gagnant3.setBackground(fondBlanc);
        gagnant3.setPadding(padding);
        gagnant3.setRotate(-90);
        hDroite.setAlignment(Pos.CENTER);
        hDroite.getChildren().addAll(gagnant3);


        ///


        HBox hGauche = new HBox();
        gagnant4 = new Label("Le joueur " + "JOUEUR X" + " a gagné");//TODO
        gagnant4.setFont(policeNom);
        gagnant4.setBackground(fondBlanc);
        gagnant4.setPadding(padding);
        gagnant4.setRotate(90);
        hGauche.setAlignment(Pos.CENTER);
        hGauche.getChildren().addAll(gagnant4);
        //

        // image fond
        ImageView imgFond = new ImageView(DataControl.FOND);
        // carre central qui contient tous les éléments (boutons et titre)
        BorderPane centreMenu = new BorderPane();

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

        // Boutons de rotation d'écran 
        ImageView img1 = new ImageView(DataControl.SCREEN);
		img1.setFitHeight(70);
		img1.setPreserveRatio(true);
		ImageView img2 = new ImageView(DataControl.SCREEN);
		img2.setFitHeight(70);
		img2.setPreserveRatio(true);
		ImageView img3 = new ImageView(DataControl.SCREEN);
		img3.setFitHeight(70);
		img3.setPreserveRatio(true);
		ImageView img4 = new ImageView(DataControl.SCREEN);
		img4.setFitHeight(70);
		img4.setPreserveRatio(true);

		Button bEcranHaut = new Button();
		bEcranHaut.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranHaut.setAlignment(Pos.CENTER);
		bEcranHaut.setTranslateY(-490);
		bEcranHaut.setPrefSize(80, 80);
		bEcranHaut.setRotate(180);
		bEcranHaut.setGraphic(img1);
		bEcranHaut.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "haut"));

		Button bEcranBas = new Button();
		bEcranBas.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranBas.setAlignment(Pos.CENTER);
		bEcranBas.setTranslateY(490);
		bEcranBas.setPrefSize(80, 80);
		bEcranBas.setGraphic(img2);
		bEcranBas.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "bas"));

		Button bEcranGauche = new Button();
		bEcranGauche.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranGauche.setAlignment(Pos.CENTER);
		bEcranGauche.setTranslateX(-910);
		bEcranGauche.setPrefSize(80, 80);
		bEcranGauche.setRotate(90);
		bEcranGauche.setGraphic(img3);
		bEcranGauche.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "gauche"));

		Button bEcranDroite = new Button();
		bEcranDroite.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranDroite.setAlignment(Pos.CENTER);
		bEcranDroite.setTranslateX(910);
		bEcranDroite.setRotate(-90);
		bEcranDroite.setPrefSize(80, 80);
		bEcranDroite.setGraphic(img4);
		bEcranDroite.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "droite"));

		// boite du fond qui contient le fond et les autres boites 
        HBox fond = new HBox();
        fond.setAlignment(Pos.CENTER);
        fond.setEffect(flou);
        fond.getChildren().add(imgFond);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(fond, centreMenu, bEcranDroite, bEcranHaut, bEcranGauche, bEcranBas);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        sControl.registerNode(paneName, this);
        sControl.setPaneOnTop(paneName);

    }

    /*
     * affiche le nom du joueur sur l'écran 
     */
    @Override
    public void getGagnant(String nom) {
        String tmp = "Le joueur " + nom + " a gagné";//TODO
        Platform.runLater(() -> {
            gagnant1.setText(tmp);
            gagnant2.setText(tmp);
            gagnant3.setText(tmp);
            gagnant4.setText(tmp);
        });
    }
}