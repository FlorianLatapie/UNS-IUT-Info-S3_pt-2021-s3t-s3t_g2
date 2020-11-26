package pp.ihm;

import java.util.Timer;
import java.util.TimerTask;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.eventListener.PlateauListener;
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

public class PlateauPane extends StackPane implements PlateauListener {
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.PLATEAU; // nom du pane

	private final int margeP = 24;
	private final Insets margeBoutonPause = new Insets(margeP, margeP, margeP, margeP);
	private final int lhBoutonPause = 80;
	private final Font policeBoutonPause = Font.font("Segoe UI", FontWeight.BOLD, 34);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 50px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 50px;";

	private final int margeJ = 20;
	private final Insets margeTexteJoueur = new Insets(margeJ, 10, margeJ, 10);
	private final Insets insetJGauche = new Insets(0, 500, 0, 115);
	private final Insets insetJDroit = new Insets(0, 115, 0, 500);
	private final int tailleVBoxJoueur = 210;

	private final int taillePlateau = 1080;

	private final int margeL = 10;
	private final Insets margeLieu = new Insets(margeL, margeL, margeL, margeL);
	private final int tailleFont = 18;
	
	private GaussianBlur flou = new GaussianBlur(30);
	
	private final Font fontInfo = Font.font("Segoe UI", FontWeight.BOLD, tailleFont);
	private final Font fontPerso = Font.font("Segoe UI", FontWeight.BOLD, 12);

	private CornerRadii coinfb = new CornerRadii(5.0);
	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));
	private Background fondNoir = new Background(new BackgroundFill(Color.BLACK, coinfb, null));
	private String tmpColor = " -fx-background-color:#000000; -fx-text-fill: #FF2626"; //TODO si cette couleur est affichée en partie, il y a une erreur dans l'event 
	private String vert = " -fx-background-color:#5EB137; -fx-text-fill: #000000";
	private String rouge = " -fx-background-color:#F30101; -fx-text-fill: #000000";
	private String marron = " -fx-background-color:#6C3505; -fx-text-fill: #000000";
	private String jaune = " -fx-background-color:#E9B902; -fx-text-fill: #000000";
	private String bleu = " -fx-background-color:#008CDA; -fx-text-fill: #ffffff";
	private String noir = " -fx-background-color:#000000; -fx-text-fill: #ffffff";

	Label nbZombies1;
	Label nbZombies2;
	Label nbZombies3;
	Label nbZombies4;
	Label nbZombies5;
	Label nbZombies6;

	VBox j1;
	VBox j2;
	VBox j3;
	VBox j4;
	VBox j5;
	VBox j6;
	
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
	Label nbPerso6;

	Label nbCartes1;
	Label nbCartes2;
	Label nbCartes3;
	Label nbCartes4;
	Label nbCartes5;
	Label nbCartes6;

	Label afficheJoueursLieu1;
	Label afficheJoueursLieu2;
	Label afficheJoueursLieu3;
	Label afficheJoueursLieu4;
	Label afficheJoueursLieu5;
	Label afficheJoueursLieu6;

	BorderPane info;

	Label titreInfo;
	Label lInfo;
	
	Label lChefVigile;
	Label lChefVigile2;
	Label lChefVigile3;
	Label lChefVigile4;
	
	AnchorPane aPlateau;
	ImageView imgFond;
	BorderPane borderJoueurs;	
	
	Timer myTimer;

	public PlateauPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		HBox hHaut = new HBox();
		HBox hDroite = new HBox();
		HBox hBas = new HBox();
		HBox hGauche = new HBox();
		borderJoueurs = new BorderPane();
		aPlateau = new AnchorPane();

		///////////////////////////////////////////
		Button bPause1 = new Button("| |");
		bPause1.setAlignment(Pos.CENTER);
		bPause1.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause1.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause1.setStyle(styleBoutons);
		bPause1.setOnMouseEntered(event -> bPause1.setStyle(styleBoutonsSouris));
		bPause1.setOnMouseExited(event -> bPause1.setStyle(styleBoutons));
		bPause1.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause1.setFont(policeBoutonPause);
		HBox.setMargin(bPause1, margeBoutonPause);

		Button bPause2 = new Button("| |");
		bPause2.setAlignment(Pos.CENTER);
		bPause2.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause2.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause2.setStyle(styleBoutons);
		bPause2.setOnMouseEntered(event -> bPause2.setStyle(styleBoutonsSouris));
		bPause2.setOnMouseExited(event -> bPause2.setStyle(styleBoutons));
		bPause2.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause2.setFont(policeBoutonPause);
		HBox.setMargin(bPause2, margeBoutonPause);

		j1 = new VBox(); //TODO j'ai ajouté les couleurs en ligne 53 à 58 il faut les appliquer avec des event sur j1,nbPerso1,nbCartes1,nomJoueur1 etc 
		nbPerso1 = new Label("## personnages");
		nbPerso1.setFont(Font.font("Segoe UI", 20));
		nbPerso1.setTextFill(Color.BLACK);
		nbPerso1.setStyle(tmpColor);
		nbCartes1 = new Label("## de cartes");
		nbCartes1.setFont(Font.font("Segoe UI", 20));
		nbCartes1.setTextFill(Color.BLACK);
		nbCartes1.setStyle(tmpColor);
		nomJoueur1 = new Label("nom Joueur1");
		nomJoueur1.setFont(Font.font("Segoe UI", 20));
		nomJoueur1.setTextFill(Color.BLACK);
		nomJoueur1.setStyle(tmpColor);

		HBox.setMargin(j1, insetJGauche);
		j1.setAlignment(Pos.CENTER);
		j1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		VBox.setMargin(nbPerso1, margeTexteJoueur);
		VBox.setMargin(nbCartes1, margeTexteJoueur);
		VBox.setMargin(nomJoueur1, margeTexteJoueur);
		j1.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j1.setStyle(tmpColor);
		j1.getChildren().addAll(nbPerso1, nbCartes1, nomJoueur1);

		j2 = new VBox();
		nbPerso2 = new Label("## personnages");
		nbPerso2.setFont(Font.font("Segoe UI", 20));
		nbPerso2.setTextFill(Color.BLACK);
		nbPerso2.setStyle(tmpColor);
		nbCartes2 = new Label("## de cartes");
		nbCartes2.setFont(Font.font("Segoe UI", 20));
		nbCartes2.setTextFill(Color.BLACK);
		nbCartes2.setStyle(tmpColor);
		nomJoueur2 = new Label("Nom Joueur 2");
		nomJoueur2.setFont(Font.font("Segoe UI", 20));
		nomJoueur2.setTextFill(Color.BLACK);
		nomJoueur2.setStyle(tmpColor);

		HBox.setMargin(j2, insetJDroit);
		j2.setAlignment(Pos.CENTER);
		j2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		VBox.setMargin(nbPerso2, margeTexteJoueur);
		VBox.setMargin(nbCartes2, margeTexteJoueur);
		VBox.setMargin(nomJoueur2, margeTexteJoueur);
		j2.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j2.getChildren().addAll(nbPerso2, nbCartes2, nomJoueur2);
		j2.setStyle(tmpColor);

		hBas.getChildren().addAll(bPause1, j1, j2, bPause2);
		hBas.setAlignment(Pos.BOTTOM_CENTER);

		/////////////////////////////////////////////////////

		Button bPause3 = new Button("| |");
		bPause3.setAlignment(Pos.CENTER);
		bPause3.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause3.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause3.setStyle(styleBoutons);
		bPause3.setOnMouseEntered(event -> bPause3.setStyle(styleBoutonsSouris));
		bPause3.setOnMouseExited(event -> bPause3.setStyle(styleBoutons));
		bPause3.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause3.setFont(policeBoutonPause);
		HBox.setMargin(bPause3, margeBoutonPause);

		Button bPause4 = new Button("| |");
		bPause4.setAlignment(Pos.CENTER);
		bPause4.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause4.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause4.setStyle(styleBoutons);
		bPause4.setOnMouseEntered(event -> bPause4.setStyle(styleBoutonsSouris));
		bPause4.setOnMouseExited(event -> bPause4.setStyle(styleBoutons));
		bPause4.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause4.setFont(policeBoutonPause);
		HBox.setMargin(bPause4, margeBoutonPause);

		j3 = new VBox();
		nbPerso3 = new Label("## personnages");
		nbPerso3.setFont(Font.font("Segoe UI", 20));
		nbPerso3.setTextFill(Color.BLACK);
		nbPerso3.setStyle(tmpColor);
		nbCartes3 = new Label("## de cartes");
		nbCartes3.setFont(Font.font("Segoe UI", 20));
		nbCartes3.setTextFill(Color.BLACK);
		nbCartes3.setStyle(tmpColor);
		nomJoueur3 = new Label("Nom Joueur 3");
		nomJoueur3.setFont(Font.font("Segoe UI", 20));
		nomJoueur3.setTextFill(Color.BLACK);
		nomJoueur3.setStyle(tmpColor);

		HBox.setMargin(j3, insetJGauche);
		j3.setAlignment(Pos.CENTER);
		j3.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		VBox.setMargin(nbPerso3, margeTexteJoueur);
		VBox.setMargin(nbCartes3, margeTexteJoueur);
		VBox.setMargin(nomJoueur3, margeTexteJoueur);
		j3.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j3.getChildren().addAll(nbPerso3, nbCartes3, nomJoueur3);
		j3.setStyle(tmpColor);

		j4 = new VBox();
		nbPerso4 = new Label("## personnages");
		nbPerso4.setFont(Font.font("Segoe UI", 20));
		nbPerso4.setTextFill(Color.BLACK);
		nbPerso4.setStyle(tmpColor);
		nbCartes4 = new Label("## de cartes");
		nbCartes4.setFont(Font.font("Segoe UI", 20));
		nbCartes4.setTextFill(Color.BLACK);
		nbCartes4.setStyle(tmpColor);
		nomJoueur4 = new Label("Nom Joueur 4");
		nomJoueur4.setFont(Font.font("Segoe UI", 20));
		nomJoueur4.setTextFill(Color.BLACK);
		nomJoueur4.setStyle(tmpColor);

		HBox.setMargin(j4, insetJDroit);
		j4.setAlignment(Pos.CENTER);
		j4.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		VBox.setMargin(nbPerso4, margeTexteJoueur);
		VBox.setMargin(nbCartes4, margeTexteJoueur);
		VBox.setMargin(nomJoueur4, margeTexteJoueur);
		j4.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j4.getChildren().addAll(nbPerso4, nbCartes4, nomJoueur4);
		j4.setStyle(tmpColor);

		hHaut.getChildren().addAll(bPause3, j3, j4, bPause4);
		hHaut.setAlignment(Pos.BOTTOM_CENTER);
		hHaut.setRotate(180);

		j5 = new VBox();
		nbPerso5 = new Label("## personnages");
		nbPerso5.setFont(Font.font("Segoe UI", 20));
		nbPerso5.setTextFill(Color.BLACK);
		nbPerso5.setStyle(tmpColor);
		nbCartes5 = new Label("## de cartes");
		nbCartes5.setFont(Font.font("Segoe UI", 20));
		nbCartes5.setTextFill(Color.BLACK);
		nbCartes5.setStyle(tmpColor);
		nomJoueur5 = new Label("Nom Joueur 5");
		nomJoueur5.setFont(Font.font("Segoe UI", 20));
		nomJoueur5.setTextFill(Color.BLACK);
		nomJoueur5.setStyle(tmpColor);

		j5.setAlignment(Pos.CENTER);
		j5.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		VBox.setMargin(nbPerso5, margeTexteJoueur);
		VBox.setMargin(nbCartes5, margeTexteJoueur);
		VBox.setMargin(nomJoueur5, margeTexteJoueur);
		j5.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j5.setPrefSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j5.setMaxSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j5.getChildren().addAll(nbPerso5, nbCartes5, nomJoueur5);
		j5.setStyle(tmpColor);
		j5.setRotate(270);

		hDroite.getChildren().addAll(j5);
		hDroite.setAlignment(Pos.CENTER_LEFT);
		
		j6 = new VBox();
		nbPerso6 = new Label("## personnages");
		nbPerso6.setFont(Font.font("Segoe UI", 20));
		nbPerso6.setTextFill(Color.BLACK);
		nbPerso6.setStyle(tmpColor);
		nbCartes6 = new Label("## de cartes");
		nbCartes6.setFont(Font.font("Segoe UI", 20));
		nbCartes6.setTextFill(Color.BLACK);
		nbCartes6.setStyle(tmpColor);
		nomJoueur6 = new Label("Nom Joueur 6");
		nomJoueur6.setFont(Font.font("Segoe UI", 20));
		nomJoueur6.setTextFill(Color.BLACK);
		nomJoueur6.setStyle(tmpColor);

		j6.setAlignment(Pos.CENTER);
		j6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		VBox.setMargin(nbPerso6, margeTexteJoueur);
		VBox.setMargin(nbCartes6, margeTexteJoueur);
		VBox.setMargin(nomJoueur6, margeTexteJoueur);
		j6.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j6.setPrefSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j6.setMaxSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j6.getChildren().addAll(nbPerso6, nbCartes6, nomJoueur6);
		j6.setStyle(tmpColor);
		j6.setRotate(90);

		hGauche.getChildren().addAll(j6);
		hGauche.setAlignment(Pos.CENTER_LEFT);


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

		VBox joueursPresents1 = new VBox();
		joueursPresents1.getChildren().addAll(afficheJoueursLieu1 = new Label());
		joueursPresents1.setPrefSize(170, 200);

		nbZombies1 = new Label("Nb zombies");
		nbZombies1.setFont(fontInfo);
		nbZombies1.setTextFill(Color.RED);

		BorderPane b1 = new BorderPane();
		b1.setPadding(new Insets(5));
		b1.setPrefSize(320,	210);
		b1.setMaxSize(320,	210);
		b1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight1.getChildren().addAll(estBarricade1, estFerme1, force1);
		hbBot1.getChildren().add(nbZombies1);

		b1.setCenter(joueursPresents1);
		b1.setRight(vbRight1);
		b1.setBottom(hbBot1);
		b1.setOpacity(.9);
		
		b1.setRotate(128);

		AnchorPane.setTopAnchor(b1, 755.0);
		AnchorPane.setLeftAnchor(b1, 630.0);

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

		VBox joueursPresents2 = new VBox();
		joueursPresents2.getChildren().addAll(afficheJoueursLieu2 = new Label());
		joueursPresents2.setPrefSize(170, 100);

		nbZombies2 = new Label("Nb zombies");
		nbZombies2.setFont(fontInfo);
		nbZombies2.setTextFill(Color.RED);

		BorderPane b2 = new BorderPane();
		b2.setPadding(new Insets(5));
		b2.setPrefSize(320,	215);
		b2.setMaxSize(320,	215);
		b2.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight2.getChildren().addAll(estBarricade2, estFerme2, force2);
		hbBot2.getChildren().add(nbZombies2);

		b2.setCenter(joueursPresents2);
		b2.setRight(vbRight2);
		b2.setBottom(hbBot2);
		b2.setOpacity(.9);

		b2.setRotate(-133);

		AnchorPane.setTopAnchor(b2, 725.0);
		AnchorPane.setLeftAnchor(b2, 70.0);

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

		VBox joueursPresents3 = new VBox();
		joueursPresents3.getChildren().addAll(afficheJoueursLieu3 = new Label());
		joueursPresents3.setPrefSize(170, 100);

		nbZombies3 = new Label("Nb zombies");
		nbZombies3.setFont(fontInfo);
		nbZombies3.setTextFill(Color.RED);

		BorderPane b3 = new BorderPane();
		b3.setPadding(new Insets(5));
		b3.setPrefSize(325,	210);
		b3.setMaxSize(325,	210);
		b3.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight3.getChildren().addAll(estBarricade3, estFerme3, force3);
		hbBot3.getChildren().add(nbZombies3);

		b3.setCenter(joueursPresents3);
		b3.setRight(vbRight3);
		b3.setBottom(hbBot3);
		b3.setOpacity(.9);


		b3.setRotate(-62);

		AnchorPane.setTopAnchor(b3, 260.0);
		AnchorPane.setLeftAnchor(b3, 25.0);

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

		VBox joueursPresents4 = new VBox();
		joueursPresents4.getChildren().addAll(afficheJoueursLieu4 = new Label());
		joueursPresents4.setPrefSize(170, 100);

		nbZombies4 = new Label("Nb zombies");
		nbZombies4.setFont(fontInfo);
		nbZombies4.setTextFill(Color.RED);

		BorderPane b4 = new BorderPane();
		b4.setPadding(new Insets(5));
		b4.setPrefSize(270,	210);
		b4.setMaxSize(270,	210);
		b4.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight4.getChildren().addAll(estBarricade4, estFerme4, force4);
		hbBot4.getChildren().add(nbZombies4);

		b4.setCenter(joueursPresents4);
		b4.setRight(vbRight4);
		b4.setBottom(hbBot4);
		b4.setOpacity(.9);

		b4.setRotate(11);

		AnchorPane.setTopAnchor(b4, 450.0);
		AnchorPane.setLeftAnchor(b4, 385.0);

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

		VBox joueursPresents5 = new VBox();
		joueursPresents5.getChildren().addAll(afficheJoueursLieu5 = new Label());
		joueursPresents5.setPrefSize(170, 100);

		nbZombies5 = new Label("Nb zombies");
		nbZombies5.setFont(fontInfo);
		nbZombies5.setTextFill(Color.RED);

		BorderPane b5 = new BorderPane();
		b5.setPadding(new Insets(5));
		b5.setPrefSize(325,	215);
		b5.setMaxSize(325,	215);
		b5.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight5.getChildren().addAll(estBarricade5, estFerme5, force5);
		hbBot5.getChildren().add(nbZombies5);

		b5.setCenter(joueursPresents5);
		b5.setRight(vbRight5);
		b5.setBottom(hbBot5);
		b5.setOpacity(.9);

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

		VBox joueursPresents6 = new VBox();
		joueursPresents6.getChildren().addAll(afficheJoueursLieu6 = new Label());
		joueursPresents6.setPrefSize(170, 100);

		nbZombies6 = new Label("Nb zombies");
		nbZombies6.setFont(fontInfo);
		nbZombies6.setTextFill(Color.RED);

		BorderPane b6 = new BorderPane();
		b6.setPrefSize(310,	215);
		b6.setMaxSize(310,	215);
		b6.setPadding(new Insets(5));
		b6.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
		b6.setOpacity(.9);

		vbLeft6.getChildren().addAll(estBarricade6, estFerme6, force6);
		hbBot6.getChildren().add(nbZombies6);

		b6.setCenter(joueursPresents6);
		b6.setLeft(vbLeft6);
		b6.setBottom(hbBot6);

		b6.setRotate(53);
		
		AnchorPane.setTopAnchor(b6, 320.0);
		AnchorPane.setLeftAnchor(b6, 747.5);
		
		/////
		
		lChefVigile = new Label();
		lChefVigile.setText("XXXXXXX" + " est le chef des vigiles");
		lChefVigile.setBackground(fondBlanc);
		lChefVigile.setFont(fontInfo);
		lChefVigile.setPadding(margeLieu);
		AnchorPane.setTopAnchor(lChefVigile, 750.0);
		AnchorPane.setLeftAnchor(lChefVigile, 375.0);

		lChefVigile2 = new Label();
		lChefVigile2.setText("XXXXXXX" + " est le chef des vigiles");
		lChefVigile2.setBackground(fondBlanc);
		lChefVigile2.setFont(fontInfo);
		lChefVigile2.setPadding(margeLieu);
		lChefVigile2.setRotate(180);
		AnchorPane.setTopAnchor(lChefVigile2, 350.0);
		AnchorPane.setRightAnchor(lChefVigile2, 425.0);
		
		lChefVigile3 = new Label();
		lChefVigile3.setText("XXXXXXX" + " est le chef des vigiles");
		lChefVigile3.setBackground(fondBlanc);
		lChefVigile3.setFont(fontInfo);
		lChefVigile3.setPadding(margeLieu);
		lChefVigile3.setRotate(90);
		AnchorPane.setTopAnchor(lChefVigile3, 550.0);
		AnchorPane.setLeftAnchor(lChefVigile3, 180.0);
		
		lChefVigile4 = new Label();
		lChefVigile4.setText("XXXXXXX" + " est le chef des vigiles");
		lChefVigile4.setBackground(fondBlanc);
		lChefVigile4.setFont(fontInfo);
		lChefVigile4.setPadding(margeLieu);
		lChefVigile4.setRotate(-90);
		AnchorPane.setTopAnchor(lChefVigile4, 550.0);
		AnchorPane.setLeftAnchor(lChefVigile4, 575.0);
		
		////
		
		aPlateau.getChildren().addAll(b1, b2, b3, b4, b5, b6, lChefVigile, lChefVigile2,lChefVigile3,lChefVigile4);
		info = new BorderPane();
		info.setPrefSize(450, 200);
		info.setMaxSize(450, 200);
		info.setStyle(" -fx-background-color:#1A1A1A; -fx-background-radius: 20px;");		

		VBox vTitreInfo = new VBox();
		vTitreInfo.setAlignment(Pos.CENTER);
		vTitreInfo.setPadding(new Insets(20));
		titreInfo = new Label("Titre info");
		titreInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		titreInfo.setTextFill(Color.WHITE);
		vTitreInfo.getChildren().addAll(titreInfo);

		VBox vInfo = new VBox();
		vInfo.setAlignment(Pos.TOP_LEFT);
		lInfo = new Label("information");
		lInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
		lInfo.setTextFill(Color.WHITE);
		vInfo.getChildren().addAll(lInfo);

		info.setMargin(vInfo, new Insets(50, 20, 0, 20));
		info.setTop(vTitreInfo);
		info.setCenter(vInfo);
		info.setVisible(false);
		
		borderJoueurs.setTop(hHaut);
		borderJoueurs.setBottom(hBas);
		borderJoueurs.setLeft(hGauche);
		borderJoueurs.setRight(hDroite);

		borderJoueurs.setMinSize(1920, 1080);
		borderJoueurs.setPrefSize(1920, 1080);
		borderJoueurs.setMaxSize(1920, 1080);

		imgFond = new ImageView(DataControl.PLATEAU);
		imgFond.setScaleX(0.4362);
		imgFond.setScaleY(0.4362);

		afficheJoueursLieu1.setTextFill(Color.WHITE);
		afficheJoueursLieu2.setTextFill(Color.WHITE);
		afficheJoueursLieu3.setTextFill(Color.WHITE);
		afficheJoueursLieu4.setTextFill(Color.WHITE);
		afficheJoueursLieu5.setTextFill(Color.WHITE);
		afficheJoueursLieu6.setTextFill(Color.WHITE);
		afficheJoueursLieu1.setFont(fontPerso);
		afficheJoueursLieu2.setFont(fontPerso);
		afficheJoueursLieu3.setFont(fontPerso);
		afficheJoueursLieu4.setFont(fontPerso);
		afficheJoueursLieu5.setFont(fontPerso);
		afficheJoueursLieu6.setFont(fontPerso);

		this.setStyle(" -fx-background-color:#151515;");
		
		this.getChildren().addAll(imgFond, borderJoueurs, aPlateau, info);
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
	public void nomJoueur(int joueur, String couleur, String val) {
		String tmp = val + " " + couleur;
		Platform.runLater(() -> {
			switch (joueur) {
			case 0:
				nomJoueur1.setText(tmp);
				break;
			case 1:
				nomJoueur2.setText(tmp);
				break;
			case 2:
				nomJoueur3.setText(tmp);
				break;
			case 3:
				nomJoueur4.setText(tmp);
				break;
			case 4:
				nomJoueur5.setText(tmp);
				break;
			case 5:
				nomJoueur6.setText(tmp);
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
	public void nomChefVigile(String joueur) {
		String tmp = joueur + " est le chef des vigiles";
		Platform.runLater(() -> {
			lChefVigile.setText(tmp);
			lChefVigile2.setText(tmp);
			lChefVigile3.setText(tmp);
			lChefVigile4.setText(tmp);
		});
	}

	@Override
	public void finPartie() {
		sControl.setPaneOnTop(ApplicationPane.ENDGAME);
	}

	@Override
	public void destionationPerso(int lieu, String nomPersosCouleur) {
		Platform.runLater(() -> {
			switch (lieu) {
			case 1:
				afficheJoueursLieu1.setText(nomPersosCouleur);
				break;
			case 2:
				afficheJoueursLieu2.setText(nomPersosCouleur);
				break;
			case 3:
				afficheJoueursLieu3.setText(nomPersosCouleur);
				break;
			case 4:
				afficheJoueursLieu4.setText(nomPersosCouleur);
				break;
			case 5:
				afficheJoueursLieu5.setText(nomPersosCouleur);
				break;
			case 6:
				afficheJoueursLieu6.setText(nomPersosCouleur);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + lieu);
			}
		});
	}

	@Override
	public void fouilleCamion(String camion) {
		Platform.runLater(() -> {
			titreInfo.setText("Fouille du camion");
			lInfo.setText(camion);
			imgFond.setEffect(flou);
			borderJoueurs.setEffect(flou);
			aPlateau.setEffect(flou);
			info.setVisible(true);
			if (myTimer != null) {
				myTimer.cancel();
			}
			myTimer = new Timer();
			myTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					imgFond.setEffect(null);
					borderJoueurs.setEffect(null);
					aPlateau.setEffect(null);
					info.setVisible(false);
				}
			}, 5000);
		});
	}

	@Override
	public void prevenirDeplacementVigile(String depvig) {
		Platform.runLater(() -> {
			titreInfo.setText("Déplacement d'un pion du chef des vigiles");
			lInfo.setText(depvig);
			imgFond.setEffect(flou);
			borderJoueurs.setEffect(flou);
			aPlateau.setEffect(flou);
			info.setVisible(true);
			if (myTimer != null) {
				myTimer.cancel();
			}
			myTimer = new Timer();
			myTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					imgFond.setEffect(null);
					borderJoueurs.setEffect(null);
					aPlateau.setEffect(null);
					info.setVisible(false);
				}
			}, 5000);
		});
	}

	@Override
	public void electionChef(String message) {
		Platform.runLater(() -> {
			titreInfo.setText("Election du chef des vigiles");
			lInfo.setText(message);
			imgFond.setEffect(flou);
			borderJoueurs.setEffect(flou);
			aPlateau.setEffect(flou);
			info.setVisible(true);
			if (myTimer != null) {
				myTimer.cancel();
			}

			myTimer = new Timer();
			myTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					imgFond.setEffect(null);
					borderJoueurs.setEffect(null);
					aPlateau.setEffect(null);
					info.setVisible(false);
				}
			}, 5000);
		});
	}
}
