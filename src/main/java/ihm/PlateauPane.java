package ihm;

import ihm.DataControl.ApplicationPane;
import ihm.eventListener.PlateauListener;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static java.lang.System.out;

public class PlateauPane extends StackPane implements PlateauListener {
    private ScreenControl sControl = null;
    private Core core = null;
    private final ApplicationPane paneName = ApplicationPane.PLATEAU; // nom du pane

    private final int margeP = 24;
    private final Insets margeBoutonPause = new Insets(margeP, margeP, margeP, margeP);
    private final int lhBoutonPause = 80;
    private final String coinBoutons = " -fx-background-radius: 5px";
    private final Font policeBoutonPause = Font.font("Segoe UI", FontWeight.BOLD, 33);

    private final int margeJ = 20;
    private final Insets margeTexteJoueur = new Insets(margeJ, 10, margeJ, 10);
    private final Insets insetJGauche = new Insets(0, 500, 0, 115);
    private final Insets insetJDroit = new Insets(0, 115, 0, 500);
    private final int tailleVBoxJoueur = 210;

    private final int taillePlateau = 1080;

    private final int margeL = 10;
    private final Insets margeLieu = new Insets(margeL, margeL, margeL, margeL);
    private final int tailleFont = 18;
    private final Font fontInfo = Font.font("Segoe UI", FontWeight.BOLD, tailleFont);
    private final Font fontTitre = Font.font("Segoe UI", FontWeight.BOLD, tailleFont);

    Label nbZombies1;
    Label nbZombies2;
    Label nbZombies3;
    Label nbZombies4;
    Label nbZombies5;
    Label nbZombies6;

    Label nomJoueur1;
    Label nomJoueur2;
    Label nomJoueur3;
    Label nomJoueur4;
    Label nomJoueur5;
    Label nomJoueur6;

    Label estFerme1;
    Label estFerme2;
    Label estFerme3;
    Label estFerme4;
    Label estFerme5;
    Label estFerme6;

    Label force1;
    Label force2;
    Label force3;
    Label force4;
    Label force5;
    Label force6;

    Label nbPerso1;
    Label nbPerso2;
    Label nbPerso3;
    Label nbPerso4;
    Label nbPerso5;

    Label nbCartes1;
    Label nbCartes2;
    Label nbCartes3;
    Label nbCartes4;
    Label nbCartes5;

    public PlateauPane(ScreenControl sc, Core c) {
        core = c;
        sControl = sc;

        HBox hHaut = new HBox();
        HBox hDroite = new HBox();
        HBox hBas = new HBox();
        HBox hGauche = new HBox();
        // StackPane sBase = new StackPane();
        BorderPane borderJoueurs = new BorderPane();
        AnchorPane aPlateau = new AnchorPane();

        ///////////////////////////////////////////
        Button bPause1 = new Button("| |");
        bPause1.setPrefSize(lhBoutonPause, lhBoutonPause);
        bPause1.setMinSize(lhBoutonPause, lhBoutonPause);
        bPause1.setStyle(coinBoutons);
        bPause1.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
        bPause1.setFont(policeBoutonPause);
        HBox.setMargin(bPause1, margeBoutonPause);

        Button bPause2 = new Button("| |");
        bPause2.setPrefSize(lhBoutonPause, lhBoutonPause);
        bPause2.setMinSize(lhBoutonPause, lhBoutonPause);
        bPause2.setStyle(coinBoutons);
        bPause2.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
        bPause2.setFont(policeBoutonPause);
        HBox.setMargin(bPause2, margeBoutonPause);

        VBox j1 = new VBox();
        nbPerso1 = new Label("## personnages");
        nbPerso1.setFont(Font.font("Segoe UI", 20));
        nbPerso1.setTextFill(Color.BLACK);
        nbCartes1 = new Label("## de cartes");
        nbCartes1.setFont(Font.font("Segoe UI", 20));
        nbCartes1.setTextFill(Color.BLACK);
        nomJoueur1 = new Label("nom Joueur1");
        nomJoueur1.setFont(Font.font("Segoe UI", 20));
        nomJoueur1.setTextFill(Color.BLACK);

        HBox.setMargin(j1, insetJGauche);
        j1.setAlignment(Pos.CENTER);
        j1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
        VBox.setMargin(nbPerso1, margeTexteJoueur);
        VBox.setMargin(nbCartes1, margeTexteJoueur);
        VBox.setMargin(nomJoueur1, margeTexteJoueur);
        j1.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j1.getChildren().addAll(nbPerso1, nbCartes1, nomJoueur1);

        VBox j2 = new VBox();
        nbPerso2 = new Label("## personnages");
        nbPerso2.setFont(Font.font("Segoe UI", 20));
        nbPerso2.setTextFill(Color.BLACK);
        nbCartes2 = new Label("## de cartes");
        nbCartes2.setFont(Font.font("Segoe UI", 20));
        nbCartes2.setTextFill(Color.BLACK);
        nomJoueur2 = new Label("Nom Joueur 2");
        nomJoueur2.setFont(Font.font("Segoe UI", 20));
        nomJoueur2.setTextFill(Color.BLACK);

        HBox.setMargin(j2, insetJDroit);
        j2.setAlignment(Pos.CENTER);
        j2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
        VBox.setMargin(nbPerso2, margeTexteJoueur);
        VBox.setMargin(nbCartes2, margeTexteJoueur);
        VBox.setMargin(nomJoueur2, margeTexteJoueur);
        j2.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j2.getChildren().addAll(nbPerso2, nbCartes2, nomJoueur2);

        hBas.getChildren().addAll(bPause1, j1, j2, bPause2);
        hBas.setAlignment(Pos.BOTTOM_CENTER);

/////////////////////////////////////////////////////

        Button bPause3 = new Button("| |");
        bPause3.setPrefSize(lhBoutonPause, lhBoutonPause);
        bPause3.setMinSize(lhBoutonPause, lhBoutonPause);
        bPause3.setStyle(coinBoutons);
        bPause3.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
        bPause3.setFont(policeBoutonPause);
        HBox.setMargin(bPause3, margeBoutonPause);

        Button bPause4 = new Button("| |");
        bPause4.setPrefSize(lhBoutonPause, lhBoutonPause);
        bPause4.setMinSize(lhBoutonPause, lhBoutonPause);
        bPause4.setStyle(coinBoutons);
        bPause4.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
        bPause4.setFont(policeBoutonPause);
        HBox.setMargin(bPause4, margeBoutonPause);

        VBox j3 = new VBox();
        nbPerso3 = new Label("## personnages");
        nbPerso3.setFont(Font.font("Segoe UI", 20));
        nbPerso3.setTextFill(Color.BLACK);
        nbCartes3 = new Label("## de cartes");
        nbCartes3.setFont(Font.font("Segoe UI", 20));
        nbCartes3.setTextFill(Color.BLACK);
        nomJoueur3 = new Label("Nom Joueur 3");
        nomJoueur3.setFont(Font.font("Segoe UI", 20));
        nomJoueur3.setTextFill(Color.BLACK);

        HBox.setMargin(j3, insetJGauche);
        j3.setAlignment(Pos.CENTER);
        j3.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
        VBox.setMargin(nbPerso3, margeTexteJoueur);
        VBox.setMargin(nbCartes3, margeTexteJoueur);
        VBox.setMargin(nomJoueur3, margeTexteJoueur);
        j3.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j3.getChildren().addAll(nbPerso3, nbCartes3, nomJoueur3);

        VBox j4 = new VBox();
        nbPerso4 = new Label("## personnages");
        nbPerso4.setFont(Font.font("Segoe UI", 20));
        nbPerso4.setTextFill(Color.BLACK);
        nbCartes4 = new Label("## de cartes");
        nbCartes4.setFont(Font.font("Segoe UI", 20));
        nbCartes4.setTextFill(Color.BLACK);
        nomJoueur4 = new Label("Nom Joueur 4");
        nomJoueur4.setFont(Font.font("Segoe UI", 20));
        nomJoueur4.setTextFill(Color.BLACK);

        HBox.setMargin(j4, insetJDroit);
        j4.setAlignment(Pos.CENTER);
        j4.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
        VBox.setMargin(nbPerso4, margeTexteJoueur);
        VBox.setMargin(nbCartes4, margeTexteJoueur);
        VBox.setMargin(nomJoueur4, margeTexteJoueur);
        j4.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j4.getChildren().addAll(nbPerso4, nbCartes4, nomJoueur4);

        hHaut.getChildren().addAll(bPause3, j3, j4, bPause4);
        hHaut.setAlignment(Pos.BOTTOM_CENTER);
        hHaut.setRotate(180);

        ////////////////////////////////////////////////

        VBox j5 = new VBox();
        nbPerso5 = new Label("## personnages");
        nbPerso5.setFont(Font.font("Segoe UI", 20));
        nbPerso5.setTextFill(Color.BLACK);
        nbCartes5 = new Label("## de cartes");
        nbCartes5.setFont(Font.font("Segoe UI", 20));
        nbCartes5.setTextFill(Color.BLACK);
        nomJoueur5 = new Label("Nom Joueur 5");
        nomJoueur5.setFont(Font.font("Segoe UI", 20));
        nomJoueur5.setTextFill(Color.BLACK);

        j5.setAlignment(Pos.CENTER);
        j5.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
        VBox.setMargin(nbPerso5, margeTexteJoueur);
        VBox.setMargin(nbCartes5, margeTexteJoueur);
        VBox.setMargin(nomJoueur5, margeTexteJoueur);
        j5.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j5.setPrefSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j5.setMaxSize(tailleVBoxJoueur, tailleVBoxJoueur);
        j5.getChildren().addAll(nbPerso5, nbCartes5, nomJoueur5);
        j5.setRotate(270);

        hDroite.getChildren().addAll(j5);
        hDroite.setAlignment(Pos.CENTER_LEFT);

        ///////////////////////
        /*
         * // obsolete VBox j6 = new VBox(); Label nbPerso6 = new
         * Label("## personnages"); nbPerso6.setFont(Font.font("Segoe UI", 20));
         * nbPerso6.setTextFill(Color.BLACK); Label nbCartes6 = new
         * Label("## de cartes"); nbCartes6.setFont(Font.font("Segoe UI", 20));
         * nbCartes6.setTextFill(Color.BLACK); Label nomJoueur6 = new
         * Label("Nom Joueur 1"); nomJoueur6.setFont(Font.font("Segoe UI", 20));
         * nomJoueur6.setTextFill(Color.BLACK);
         *
         * j6.setAlignment(Pos.CENTER); j6.setBackground(new Background(new
         * BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
         * j6.setMargin(nbPerso6, margeTexteJoueur); j6.setMargin(nbCartes6,
         * margeTexteJoueur); j6.setMargin(nomJoueur6, margeTexteJoueur);
         * j6.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
         * j6.getChildren().addAll(nbPerso6,nbCartes6,nomJoueur6);
         *
         *
         * hGauche.getChildren().addAll(j6); hGauche.setAlignment(Pos.BOTTOM_CENTER);
         * hGauche.setRotate(90);
         */

        /////////////////////////////////////////////////////
        aPlateau.setMinSize(taillePlateau, taillePlateau);
        aPlateau.setPrefSize(taillePlateau, taillePlateau);
        aPlateau.setMaxSize(taillePlateau, taillePlateau);

        VBox vbRight1 = new VBox();
        HBox hbBot1 = new HBox();

        Label estBarricade1 = new Label("Barricadé");
        estBarricade1.setFont(fontInfo);
        estBarricade1.setTextFill(Color.LIGHTGREY);

        estFerme1 = new Label("Fermé");
        estFerme1.setFont(fontInfo);
        estFerme1.setTextFill(Color.LIGHTGREY);

        force1 = new Label("    Force\nde l'équipe\n" + "\n\t##");
        force1.setFont(fontInfo);
        force1.setTextFill(Color.RED);

        GridPane joueursPresents1 = new GridPane();
        joueursPresents1.setPrefSize(170, 200);

        nbZombies1 = new Label("Nb zombies");
        nbZombies1.setFont(fontInfo);
        nbZombies1.setTextFill(Color.RED);

        BorderPane b1 = new BorderPane();
        b1.setOpacity(.7);
        b1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        vbRight1.getChildren().addAll(estBarricade1, estFerme1, force1);
        hbBot1.getChildren().add(nbZombies1);

        b1.setCenter(joueursPresents1);
        b1.setRight(vbRight1);
        b1.setBottom(hbBot1);

        b1.setRotate(128);

        AnchorPane.setTopAnchor(b1, 725.0);
        AnchorPane.setLeftAnchor(b1, 670.0);

        ////

        VBox vbRight2 = new VBox();
        HBox hbBot2 = new HBox();

        Label estBarricade2 = new Label("Barricadé");
        estBarricade2.setFont(fontInfo);
        estBarricade2.setTextFill(Color.LIGHTGREY);

        estFerme2 = new Label("Fermé");
        estFerme2.setFont(fontInfo);
        estFerme2.setTextFill(Color.LIGHTGREY);

        force2 = new Label("    Force\nde l'équipe\n" + "\n\t##");
        force2.setFont(fontInfo);
        force2.setTextFill(Color.RED);

        GridPane joueursPresents2 = new GridPane();
        joueursPresents2.setPrefSize(170, 100);

        nbZombies2 = new Label("Nb zombies");
        nbZombies2.setFont(fontInfo);
        nbZombies2.setTextFill(Color.RED);

        BorderPane b2 = new BorderPane();
        b2.setOpacity(.7);
        b2.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        vbRight2.getChildren().addAll(estBarricade2, estFerme2, force2);
        hbBot2.getChildren().add(nbZombies2);

        b2.setCenter(joueursPresents2);
        b2.setRight(vbRight2);
        b2.setBottom(hbBot2);

        b2.setRotate(-133);

        AnchorPane.setTopAnchor(b2, 795.0);
        AnchorPane.setLeftAnchor(b2, 95.0);

        ///

        VBox vbRight3 = new VBox();
        HBox hbBot3 = new HBox();

        Label estBarricade3 = new Label("Barricadé");
        estBarricade3.setFont(fontInfo);
        estBarricade3.setTextFill(Color.LIGHTGREY);

        estFerme3 = new Label("Fermé");
        estFerme3.setFont(fontInfo);
        estFerme3.setTextFill(Color.LIGHTGREY);

        force3 = new Label("    Force\nde l'équipe\n" + "\n\t##");
        force3.setFont(fontInfo);
        force3.setTextFill(Color.RED);

        GridPane joueursPresents3 = new GridPane();
        joueursPresents3.setPrefSize(170, 100);

        nbZombies3 = new Label("Nb zombies");
        nbZombies3.setFont(fontInfo);
        nbZombies3.setTextFill(Color.RED);

        BorderPane b3 = new BorderPane();
        b3.setOpacity(.7);
        b3.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        vbRight3.getChildren().addAll(estBarricade3, estFerme3, force3);
        hbBot3.getChildren().add(nbZombies3);

        b3.setCenter(joueursPresents3);
        b3.setRight(vbRight3);
        b3.setBottom(hbBot3);

        b3.setRotate(-62);

        AnchorPane.setTopAnchor(b3, 300.0);
        AnchorPane.setLeftAnchor(b3, 15.0);

        ///

        VBox vbRight4 = new VBox();
        HBox hbBot4 = new HBox();

        Label estBarricade4 = new Label("Barricadé");
        estBarricade4.setFont(fontInfo);
        estBarricade4.setTextFill(Color.LIGHTGREY);

        estFerme4 = new Label("Fermé");
        estFerme4.setFont(fontInfo);
        estFerme4.setTextFill(Color.LIGHTGREY);

        force4 = new Label("    Force\nde l'équipe\n" + "\n\t##");
        force4.setFont(fontInfo);
        force4.setTextFill(Color.RED);

        GridPane joueursPresents4 = new GridPane();
        joueursPresents4.setPrefSize(170, 100);

        nbZombies4 = new Label("Nb zombies");
        nbZombies4.setFont(fontInfo);
        nbZombies4.setTextFill(Color.RED);

        BorderPane b4 = new BorderPane();
        b4.setOpacity(.7);
        b4.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        vbRight4.getChildren().addAll(estBarricade4, estFerme4, force4);
        hbBot4.getChildren().add(nbZombies4);

        b4.setCenter(joueursPresents4);
        b4.setRight(vbRight4);
        b4.setBottom(hbBot4);

        b4.setRotate(10);

        AnchorPane.setTopAnchor(b4, 455.0);
        AnchorPane.setLeftAnchor(b4, 395.0);

        ///

        VBox vbRight5 = new VBox();
        HBox hbBot5 = new HBox();

        Label estBarricade5 = new Label("Barricadé");
        estBarricade5.setFont(fontInfo);
        estBarricade5.setTextFill(Color.LIGHTGREY);

        estFerme5 = new Label("Fermé");
        estFerme5.setFont(fontInfo);
        estFerme5.setTextFill(Color.LIGHTGREY);

        force5 = new Label("    Force\nde l'équipe\n" + "\n\t##");
        force5.setFont(fontInfo);
        force5.setTextFill(Color.RED);

        GridPane joueursPresents5 = new GridPane();
        joueursPresents5.setPrefSize(170, 100);

        nbZombies5 = new Label("Nb zombies");
        nbZombies5.setFont(fontInfo);
        nbZombies5.setTextFill(Color.RED);

        BorderPane b5 = new BorderPane();
        b5.setOpacity(.7);
        b5.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        vbRight5.getChildren().addAll(estBarricade5, estFerme5, force5);
        hbBot5.getChildren().add(nbZombies5);

        b5.setCenter(joueursPresents5);
        b5.setRight(vbRight5);
        b5.setBottom(hbBot5);

        b5.setRotate(4);

        AnchorPane.setTopAnchor(b5, 77.5);
        AnchorPane.setLeftAnchor(b5, 408.5);

        ///

        VBox vbLeft6 = new VBox();
        HBox hbBot6 = new HBox();

        Label estBarricade6 = new Label("Barricadé");
        estBarricade6.setFont(fontInfo);
        estBarricade6.setTextFill(Color.LIGHTGREY);

        estFerme6 = new Label("Fermé");
        estFerme6.setFont(fontInfo);
        estFerme6.setTextFill(Color.LIGHTGREY);

        force6 = new Label("    Force\nde l'équipe\n" + "\n\t##");
        force6.setFont(fontInfo);
        force6.setTextFill(Color.RED);

        GridPane joueursPresents6 = new GridPane();
        joueursPresents6.setPrefSize(170, 100);

        nbZombies6 = new Label("Nb zombies");
        nbZombies6.setFont(fontInfo);
        nbZombies6.setTextFill(Color.RED);

        BorderPane b6 = new BorderPane();
        b6.setOpacity(.7);
        b6.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        vbLeft6.getChildren().addAll(estBarricade6, estFerme6, force6);
        hbBot6.getChildren().add(nbZombies6);

        b6.setCenter(joueursPresents6);
        b6.setLeft(vbLeft6);
        b6.setBottom(hbBot6);

        b6.setRotate(53);

        AnchorPane.setTopAnchor(b6, 313.5);
        AnchorPane.setLeftAnchor(b6, 778.5);

        aPlateau.getChildren().addAll(b1, b2, b3, b4, b5, b6);

        // aPlateau.setBackground(new Background(new BackgroundFill(Color.RED,
        // CornerRadii.EMPTY, null)));

        ////////////////////////////////////////////////////
        borderJoueurs.setTop(hHaut);
        borderJoueurs.setBottom(hBas);
        borderJoueurs.setLeft(hGauche);
        borderJoueurs.setRight(hDroite);

        borderJoueurs.setMinSize(1920, 1080);
        borderJoueurs.setPrefSize(1920, 1080);
        borderJoueurs.setMaxSize(1920, 1080);

        ImageView imgFond = new ImageView(DataControl.PLATEAU);
        imgFond.setScaleX(0.4362);
        imgFond.setScaleY(0.4362);

        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

        this.getChildren().addAll(imgFond, borderJoueurs, aPlateau);
        this.setMinSize(1920, 1080);
        this.setPrefSize(1920, 1080);
        this.setMaxSize(1920, 1080);

        sControl.registerNode(paneName, this);
        sControl.setPaneOnTop(paneName);

    }

    @Override
    public synchronized void nbLieuZombie(int lieu, int val) {
        String tmp = "Nb zombies : " + val;
        Platform.runLater(() -> {
            switch (lieu) {
                case 1:
                    nbZombies1.setText(tmp);
                    break;
                case 2:
                    nbZombies2.setText(tmp);
                    break;
                case 3:
                    nbZombies3.setText(tmp);
                    break;
                case 4:
                    nbZombies4.setText(tmp);
                    break;
                case 5:
                    nbZombies5.setText(tmp);
                    break;
                case 6:
                    nbZombies6.setText(tmp);
                    break;
            }
        });
    }

    @Override
    public void nomJoueur(int joueur, String val) {
        Platform.runLater(() -> {
            switch (joueur) {
                case 0:
                    nomJoueur1.setText(val);
                    break;
                case 1:
                    nomJoueur2.setText(val);
                    break;
                case 2:
                    nomJoueur3.setText(val);
                    break;
                case 3:
                    nomJoueur4.setText(val);
                    break;
                case 4:
                    nomJoueur5.setText(val);
                    break;
                case 5:
                    nomJoueur6.setText(val);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + joueur);
            }
        });
    }

    @Override
    public void lieuFerme(int lieu, boolean val) {
        if (!val)
            return;

        String tmp = "Fermé";
        Platform.runLater(() -> {
            switch (lieu) {
                case 1:
                    estFerme1.setText(tmp);
                    break;
                case 2:
                    estFerme2.setText(tmp);
                    break;
                case 3:
                    estFerme3.setText(tmp);
                    break;
                case 4:
                    estFerme4.setText(tmp);
                    break;
                case 5:
                    estFerme5.setText(tmp);
                    break;
                case 6:
                    estFerme6.setText(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lieu);
            }
        });
    }

    @Override
    public void lieuOuvert(int lieu, boolean val) {
        if (!val)
            return;

        String tmp = "Ouvert";
        Platform.runLater(() -> {
            switch (lieu) {
                case 1:
                    estFerme1.setText(tmp);
                    break;
                case 2:
                    estFerme2.setText(tmp);
                    break;
                case 3:
                    estFerme3.setText(tmp);
                    break;
                case 4:
                    estFerme4.setText(tmp);
                    break;
                case 5:
                    estFerme5.setText(tmp);
                    break;
                case 6:
                    estFerme6.setText(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lieu);
            }
        });
    }

    @Override
    public void nbPersoJoueur(int joueur, int perso) {
        String tmp = perso + " personnages";
        Platform.runLater(() -> {
            switch (joueur) {
                case 0:
                    nbPerso1.setText(tmp);
                    break;
                case 1:
                    nbPerso2.setText(tmp);
                    break;
                case 2:
                    nbPerso3.setText(tmp);
                    break;
                case 3:
                    nbPerso4.setText(tmp);
                    break;
                case 4:
                    nbPerso5.setText(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + joueur);
            }
        });
    }

    @Override
    public void nbCarteJoueur(int joueur, int cartes) {
        String tmp = cartes + " cartes";
        Platform.runLater(() -> {
            switch (joueur) {
                case 0:
                    nbCartes1.setText(tmp);
                    break;
                case 1:
                    nbCartes2.setText(tmp);
                    break;
                case 2:
                    nbCartes3.setText(tmp);
                    break;
                case 3:
                    nbCartes4.setText(tmp);
                    break;
                case 4:
                    nbCartes5.setText(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + joueur);
            }
        });
    }

    @Override
    public void forceLieu(int lieu, int force) {
        String tmp = "    Force\nde l'équipe\n" + "\n\t" + force;
        Platform.runLater(() -> {
            switch (lieu) {
                case 1:
                    force1.setText(tmp);
                    break;
                case 2:
                    force2.setText(tmp);
                    break;
                case 3:
                    force3.setText(tmp);
                    break;
                case 4:
                    force4.setText(tmp);
                    break;
                case 5:
                    force5.setText(tmp);
                    break;
                case 6:
                    force6.setText(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lieu);
            }
        });
    }

    @Override
    public void finPartie() {
        out.println("FIN");
        sControl.setPaneOnTop(ApplicationPane.ACCUEIL);
    }
}
