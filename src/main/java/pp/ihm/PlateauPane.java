package pp.ihm;

import java.util.Timer;
import java.util.TimerTask;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.eventListener.PlateauListener;
import pp.ihm.langues.International;
import reseau.type.Couleur;
import javafx.animation.TranslateTransition;
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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

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
	private String styleBoutonInfo = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff;";
	private String styleBoutonInfoSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private final int margeJ = 20;
	private final Insets margeTexteJoueur = new Insets(margeJ, 10, margeJ, 10);
	private final Insets insetJGauche = new Insets(0, 500, 0, 115);
	private final Insets insetJDroit = new Insets(0, 115, 0, 500);
	private final int tailleVBoxJoueur = 210;

	private final int taillePlateau = 1080;

	private final int margeL = 10;
	private final Insets margeLieu = new Insets(margeL, margeL, margeL, margeL);
	private final int tailleFont = 18;

	private GaussianBlur flou = new GaussianBlur(20);

	private final Font fontInfo = Font.font("Segoe UI", FontWeight.BOLD, tailleFont);
	private final Font fontPerso = Font.font("Segoe UI", FontWeight.BOLD, 12);

	private CornerRadii coinfb = new CornerRadii(5.0);
	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));
	private Background fondNoir = new Background(new BackgroundFill(Color.BLACK, coinfb, null));
	private String tmpColor = " -fx-background-color:#000000; -fx-text-fill: #FF2626"; // TODO si cette couleur est
																						// affichée en partie, il y a
																						// une erreur dans l'event
	private String vert = " -fx-background-color:#5EB137; -fx-text-fill: #000000";
	private String rouge = " -fx-background-color:#F30101; -fx-text-fill: #000000";
	private String marron = " -fx-background-color:#6C3505; -fx-text-fill: #000000";
	private String jaune = " -fx-background-color:#E9B902; -fx-text-fill: #000000";
	private String bleu = " -fx-background-color:#008CDA; -fx-text-fill: #ffffff";
	private String noir = " -fx-background-color:#000000; -fx-text-fill: #ffffff";

	private int largBouton = 155;
	private int hautBouton = 70;

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

	/*
	 * Label afficheJoueursLieu1; Label afficheJoueursLieu2; Label
	 * afficheJoueursLieu3; Label afficheJoueursLieu4; Label afficheJoueursLieu5;
	 * Label afficheJoueursLieu6;
	 */

	BorderPane info1;
	Label titreInfo1;
	Label lInfo1;

	BorderPane info2;
	Label titreInfo2;
	Label lInfo2;

	BorderPane info3;
	Label titreInfo3;
	Label lInfo3;

	BorderPane info4;
	Label titreInfo4;
	Label lInfo4;

	Label lChefVigile;
	Label lChefVigile2;
	Label lChefVigile3;
	Label lChefVigile4;

	Button bQuitterInfo1;
	Button bQuitterInfo2;
	Button bQuitterInfo3;
	Button bQuitterInfo4;

	AnchorPane notifInfo;
	AnchorPane aPlateau;
	ImageView imgFond;
	BorderPane borderJoueurs;

	ImageView imgCarteFerme1;
	ImageView imgCarteFerme2;
	ImageView imgCarteFerme3;
	ImageView imgCarteFerme5;
	ImageView imgCarteFerme6;
	Timer myTimer;

	ImageView l1p1;
	ImageView l1p2;
	ImageView l1p3;

	ImageView l2p1;
	ImageView l2p2;
	ImageView l2p3;
	ImageView l2p4;

	ImageView l3p1;
	ImageView l3p2;
	ImageView l3p3;
	ImageView l3p4;

	ImageView l4p1;
	ImageView l4p2;
	ImageView l4p3;
	ImageView l4p4;
	ImageView l4p5;
	ImageView l4p6;
	ImageView l4p7;
	ImageView l4p8;
	ImageView l4p9;

	ImageView l5p1;
	ImageView l5p2;
	ImageView l5p3;

	ImageView l6p1;
	ImageView l6p2;
	ImageView l6p3;
	ImageView l6p4;
	ImageView l6p5;
	ImageView l6p6;

	Label nbPlace1;
	Label nbPlace2;
	Label nbPlace3;
	Label nbPlace4;
	Label nbPlace5;
	Label nbPlace6;

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

		j1 = new VBox(); // TODO j'ai ajouté les couleurs en ligne 53 à 58 il faut les appliquer avec des
							// event sur j1,nbPerso1,nbCartes1,nomJoueur1 etc
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
		vbRight1.setAlignment(Pos.TOP_CENTER);
		vbRight1.setSpacing(30);

		force1 = new Label(International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB")
				+ "\n\n" + "##");
		force1.setFont(fontInfo);
		force1.setTextFill(Color.RED);
		force1.setTextAlignment(TextAlignment.CENTER);

		VBox joueursPresents1 = new VBox();
		joueursPresents1.setAlignment(Pos.CENTER);
		HBox joueursPresents1L1 = new HBox();
		joueursPresents1L1.setAlignment(Pos.CENTER);
		joueursPresents1L1.setSpacing(10);
		HBox joueursPresents1L2 = new HBox();
		joueursPresents1L2.setAlignment(Pos.CENTER);
		joueursPresents1L2.setSpacing(10);
		l1p1 = new ImageView(DataControl.NOM_COULEUR);
		l1p2 = new ImageView(DataControl.NOM_COULEUR);
		l1p3 = new ImageView(DataControl.NOM_COULEUR);

		joueursPresents1L1.getChildren().addAll(l1p1, l1p2);
		joueursPresents1L2.getChildren().addAll(l1p3);
		joueursPresents1.getChildren().addAll(joueursPresents1L1, joueursPresents1L2);
		joueursPresents1.setPrefSize(170, 200);

		nbZombies1 = new Label(International.trad("text.nbZombie"));
		nbZombies1.setFont(fontInfo);
		nbZombies1.setTextFill(Color.GREEN);
		
		nbPlace1 = new Label("## / 3");
		nbPlace1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace1.setTextFill(Color.WHITE);

		BorderPane b1 = new BorderPane();
		b1.setPadding(new Insets(5));
		b1.setPrefSize(320, 210);
		b1.setMaxSize(320, 210);
		b1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight1.getChildren().addAll(nbPlace1,force1, nbZombies1);

		b1.setCenter(joueursPresents1);
		b1.setRight(vbRight1);
		b1.setOpacity(.9);

		b1.setRotate(128);

		AnchorPane.setTopAnchor(b1, 755.0);
		AnchorPane.setLeftAnchor(b1, 630.0);

		////

		VBox vbRight2 = new VBox();
		vbRight2.setAlignment(Pos.TOP_CENTER);
		vbRight2.setSpacing(30);

		force2 = new Label(International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB")
				+ "\n\n" + "##");
		force2.setFont(fontInfo);
		force2.setTextFill(Color.RED);
		force2.setTextAlignment(TextAlignment.CENTER);

		VBox joueursPresents2 = new VBox();
		joueursPresents2.setAlignment(Pos.CENTER);
		HBox joueursPresents2L1 = new HBox();
		joueursPresents2L1.setAlignment(Pos.CENTER);
		joueursPresents2L1.setSpacing(10);
		HBox joueursPresents2L2 = new HBox();
		joueursPresents2L2.setAlignment(Pos.CENTER);
		joueursPresents2L2.setSpacing(10);
		l2p1 = new ImageView(DataControl.NOM_COULEUR);
		l2p2 = new ImageView(DataControl.NOM_COULEUR);
		l2p3 = new ImageView(DataControl.NOM_COULEUR);
		l2p4 = new ImageView(DataControl.NOM_COULEUR);

		joueursPresents2L1.getChildren().addAll(l2p1, l2p2);
		joueursPresents2L2.getChildren().addAll(l2p3, l2p4);
		joueursPresents2.getChildren().addAll(/* afficheJoueursLieu2 = new Label(), */ joueursPresents2L1,
				joueursPresents2L2);
		joueursPresents2.setSpacing(5);
		joueursPresents2.setPrefSize(170, 100);

		nbZombies2 = new Label(International.trad("text.nbZombie"));
		nbZombies2.setFont(fontInfo);
		nbZombies2.setTextFill(Color.GREEN);
		
		nbPlace2 = new Label("## / 4");
		nbPlace2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace2.setTextFill(Color.WHITE);

		BorderPane b2 = new BorderPane();
		b2.setPadding(new Insets(5));
		b2.setPrefSize(320, 215);
		b2.setMaxSize(320, 215);
		b2.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight2.getChildren().addAll(nbPlace2, force2, nbZombies2);

		b2.setCenter(joueursPresents2);
		b2.setRight(vbRight2);
		b2.setOpacity(.9);
		b2.setRotate(-133);

		AnchorPane.setTopAnchor(b2, 725.0);
		AnchorPane.setLeftAnchor(b2, 70.0);

		///

		VBox vbRight3 = new VBox();
		vbRight3.setAlignment(Pos.TOP_CENTER);
		vbRight3.setSpacing(30);

		force3 = new Label(International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB")
				+ "\n\n" + "##");
		force3.setFont(fontInfo);
		force3.setTextFill(Color.RED);
		force3.setTextAlignment(TextAlignment.CENTER);

		VBox joueursPresents3 = new VBox();
		joueursPresents3.setAlignment(Pos.CENTER);
		HBox joueursPresents3L1 = new HBox();
		joueursPresents3L1.setAlignment(Pos.CENTER);
		joueursPresents3L1.setSpacing(10);
		HBox joueursPresents3L2 = new HBox();
		joueursPresents3L2.setAlignment(Pos.CENTER);
		joueursPresents3L2.setSpacing(10);
		l3p1 = new ImageView(DataControl.NOM_COULEUR);
		l3p2 = new ImageView(DataControl.NOM_COULEUR);
		l3p3 = new ImageView(DataControl.NOM_COULEUR);
		l3p4 = new ImageView(DataControl.NOM_COULEUR);

		joueursPresents3L1.getChildren().addAll(l3p1, l3p2);
		joueursPresents3L2.getChildren().addAll(l3p3, l3p4);
		joueursPresents3.getChildren().addAll(/* afficheJoueursLieu3 = new Label(), */joueursPresents3L1,
				joueursPresents3L2);
		joueursPresents3.setSpacing(5);
		joueursPresents3.setPrefSize(170, 100);
		
		
		nbPlace3 = new Label("## / 4");
		nbPlace3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace3.setTextFill(Color.WHITE);

		nbZombies3 = new Label(International.trad("text.nbZombie"));
		nbZombies3.setFont(fontInfo);
		nbZombies3.setTextFill(Color.GREEN);

		BorderPane b3 = new BorderPane();
		b3.setPadding(new Insets(5));
		b3.setPrefSize(325, 210);
		b3.setMaxSize(325, 210);
		b3.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight3.getChildren().addAll(nbPlace3,force3, nbZombies3);

		b3.setCenter(joueursPresents3);
		b3.setRight(vbRight3);
		b3.setOpacity(.9);

		b3.setRotate(-62);

		AnchorPane.setTopAnchor(b3, 260.0);
		AnchorPane.setLeftAnchor(b3, 25.0);

		///

		VBox vbRight4 = new VBox();
		vbRight4.setAlignment(Pos.TOP_CENTER);
		vbRight4.setSpacing(30);

		force4 = new Label(International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB")
				+ "\n\n" + "##");
		force4.setFont(fontInfo);
		force4.setTextFill(Color.RED);
		force4.setTextAlignment(TextAlignment.CENTER);

		VBox joueursPresents4 = new VBox();
		joueursPresents4.setAlignment(Pos.CENTER);
		HBox joueursPresents4L1 = new HBox();
		HBox joueursPresents4L2 = new HBox();
		HBox joueursPresents4L3 = new HBox();
		l4p1 = new ImageView(DataControl.NOM_COULEUR);
		l4p2 = new ImageView(DataControl.NOM_COULEUR);
		l4p3 = new ImageView(DataControl.NOM_COULEUR);
		l4p4 = new ImageView(DataControl.NOM_COULEUR);
		l4p5 = new ImageView(DataControl.NOM_COULEUR);
		l4p6 = new ImageView(DataControl.NOM_COULEUR);
		l4p7 = new ImageView(DataControl.NOM_COULEUR);
		l4p8 = new ImageView(DataControl.NOM_COULEUR);
		l4p9 = new ImageView(DataControl.NOM_COULEUR);

		joueursPresents4L1.getChildren().addAll(l4p1, l4p2, l4p3);
		joueursPresents4L2.getChildren().addAll(l4p4, l4p5, l4p6);
		joueursPresents4L3.getChildren().addAll(l4p7, l4p8, l4p9);
		joueursPresents4.getChildren().addAll(/* afficheJoueursLieu4 = new Label(), */joueursPresents4L1,
				joueursPresents4L2, joueursPresents4L3);
		joueursPresents4.setPrefSize(170, 100);

		nbZombies4 = new Label(International.trad("text.nbZombie"));
		nbZombies4.setFont(fontInfo);
		nbZombies4.setTextFill(Color.GREEN);
		
		nbPlace4 = new Label("## / ∞");
		nbPlace4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace4.setTextFill(Color.WHITE);

		BorderPane b4 = new BorderPane();
		b4.setPadding(new Insets(5));
		b4.setPrefSize(270, 210);
		b4.setMaxSize(270, 210);
		b4.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight4.getChildren().addAll(nbPlace4,force4, nbZombies4);

		b4.setCenter(joueursPresents4);
		b4.setRight(vbRight4);
		b4.setOpacity(.9);

		b4.setRotate(11);

		AnchorPane.setTopAnchor(b4, 450.0);
		AnchorPane.setLeftAnchor(b4, 385.0);

		///

		VBox vbRight5 = new VBox();
		vbRight5.setAlignment(Pos.TOP_CENTER);
		vbRight5.setSpacing(30);

		nbPlace5 = new Label("## / 3");
		nbPlace5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace5.setTextFill(Color.WHITE);

		force5 = new Label(International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB")
				+ "\n\n" + "##");
		force5.setFont(fontInfo);
		force5.setTextFill(Color.RED);
		force5.setTextAlignment(TextAlignment.CENTER);

		VBox joueursPresents5 = new VBox();
		joueursPresents5.setAlignment(Pos.CENTER);
		HBox joueursPresents5L1 = new HBox();
		joueursPresents5L1.setAlignment(Pos.CENTER);
		joueursPresents5L1.setSpacing(10);
		HBox joueursPresents5L2 = new HBox();
		joueursPresents5L2.setAlignment(Pos.CENTER);
		joueursPresents5L2.setSpacing(10);
		l5p1 = new ImageView(DataControl.NOM_COULEUR);
		l5p2 = new ImageView(DataControl.NOM_COULEUR);
		l5p3 = new ImageView(DataControl.NOM_COULEUR);

		joueursPresents5L1.getChildren().addAll(l5p1, l5p2);
		joueursPresents5L2.getChildren().add(l5p3);
		joueursPresents5.getChildren().addAll(joueursPresents5L1, joueursPresents5L2);
		joueursPresents5.setPrefSize(170, 100);

		nbZombies5 = new Label(International.trad("text.nbZombie"));
		nbZombies5.setFont(fontInfo);
		nbZombies5.setTextFill(Color.GREEN);

		BorderPane b5 = new BorderPane();
		b5.setPadding(new Insets(5));
		b5.setPrefSize(325, 215);
		b5.setMaxSize(325, 215);
		b5.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		vbRight5.getChildren().addAll(nbPlace5, force5, nbZombies5);

		b5.setCenter(joueursPresents5);
		b5.setRight(vbRight5);
		b5.setOpacity(.9);

		b5.setRotate(4);

		AnchorPane.setTopAnchor(b5, 77.5);
		AnchorPane.setLeftAnchor(b5, 408.5);

		///

		VBox vbRight6 = new VBox();
		vbRight6.setAlignment(Pos.TOP_CENTER);
		vbRight6.setSpacing(30);

		force6 = new Label(International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB")
				+ "\n\n" + "##");
		force6.setFont(fontInfo);
		force6.setTextFill(Color.RED);
		force6.setTextAlignment(TextAlignment.CENTER);

		VBox joueursPresents6 = new VBox();
		joueursPresents6.setAlignment(Pos.CENTER);
		HBox joueursPresents6L1 = new HBox();
		HBox joueursPresents6L2 = new HBox();
		l6p1 = new ImageView(DataControl.NOM_COULEUR);
		l6p2 = new ImageView(DataControl.NOM_COULEUR);
		l6p3 = new ImageView(DataControl.NOM_COULEUR);
		l6p4 = new ImageView(DataControl.NOM_COULEUR);
		l6p5 = new ImageView(DataControl.NOM_COULEUR);
		l6p6 = new ImageView(DataControl.NOM_COULEUR);

		joueursPresents6L1.getChildren().addAll(l6p1, l6p2, l6p3);
		joueursPresents6L2.getChildren().addAll(l6p4, l6p5, l6p6);
		joueursPresents6.getChildren().addAll(/* afficheJoueursLieu5 = new Label(), */joueursPresents6L1,
				joueursPresents6L2);
		joueursPresents6.setSpacing(20);
		joueursPresents6.setPrefSize(170, 100);

		nbZombies6 = new Label(International.trad("text.nbZombie"));
		nbZombies6.setFont(fontInfo);
		nbZombies6.setTextFill(Color.GREEN);
		
		nbPlace6 = new Label("## / 6");
		nbPlace6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace6.setTextFill(Color.WHITE);

		BorderPane b6 = new BorderPane();
		b6.setPrefSize(310, 215);
		b6.setMaxSize(310, 215);
		b6.setPadding(new Insets(5));
		b6.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
		b6.setOpacity(.9);

		vbRight6.getChildren().addAll(nbPlace6,force6, nbZombies6);

		b6.setCenter(joueursPresents6);
		b6.setRight(vbRight6);

		b6.setRotate(53);

		AnchorPane.setTopAnchor(b6, 320.0);
		AnchorPane.setLeftAnchor(b6, 747.5);

		/////

		lChefVigile = new Label();
		lChefVigile.setText("XXXXXXX" + " est le chef des vigiles"); // TODO
		lChefVigile.setBackground(fondBlanc);
		lChefVigile.setFont(fontInfo);
		lChefVigile.setPadding(margeLieu);
		AnchorPane.setTopAnchor(lChefVigile, 750.0);
		AnchorPane.setLeftAnchor(lChefVigile, 375.0);

		lChefVigile2 = new Label();
		lChefVigile2.setText("XXXXXXX" + " est le chef des vigiles");// TODO
		lChefVigile2.setBackground(fondBlanc);
		lChefVigile2.setFont(fontInfo);
		lChefVigile2.setPadding(margeLieu);
		lChefVigile2.setRotate(180);
		AnchorPane.setTopAnchor(lChefVigile2, 350.0);
		AnchorPane.setRightAnchor(lChefVigile2, 425.0);

		lChefVigile3 = new Label();
		lChefVigile3.setText("XXXXXXX" + " est le chef des vigiles"); // TODO
		lChefVigile3.setBackground(fondBlanc);
		lChefVigile3.setFont(fontInfo);
		lChefVigile3.setPadding(margeLieu);
		lChefVigile3.setRotate(90);
		AnchorPane.setTopAnchor(lChefVigile3, 550.0);
		AnchorPane.setLeftAnchor(lChefVigile3, 180.0);

		lChefVigile4 = new Label();
		lChefVigile4.setText("XXXXXXX" + " est le chef des vigiles");// TODO
		lChefVigile4.setBackground(fondBlanc);
		lChefVigile4.setFont(fontInfo);
		lChefVigile4.setPadding(margeLieu);
		lChefVigile4.setRotate(-90);
		AnchorPane.setTopAnchor(lChefVigile4, 550.0);
		AnchorPane.setLeftAnchor(lChefVigile4, 575.0);

		////

		aPlateau.getChildren().addAll(b1, b2, b3, b4, b5, b6, lChefVigile, lChefVigile2, lChefVigile3, lChefVigile4);

		notifInfo = new AnchorPane();
		notifInfo.setMinSize(1920, 1080);

		info1 = new BorderPane();
		info1.setPrefSize(700, 200);
		info1.setMaxSize(700, 200);
		info1.setPadding(new Insets(10));
		info1.setStyle(
				" -fx-background-color:#1A1A1A; -fx-background-radius: 20px; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 20px;");

		bQuitterInfo1 = new Button("QUITTER");
		bQuitterInfo1.setAlignment(Pos.CENTER);
		bQuitterInfo1.setStyle(styleBoutonInfo);
		bQuitterInfo1.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo1.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo1.setOnAction(event -> {
			notifInfo.setVisible(false);
		});
		bQuitterInfo1.setOnMouseEntered(event -> {
			bQuitterInfo1.setStyle(styleBoutonInfoSouris);
		});
		bQuitterInfo1.setOnMouseExited(event -> {
			bQuitterInfo1.setStyle(styleBoutonInfo);
		});

		VBox vTitreInfo1 = new VBox();
		vTitreInfo1.setAlignment(Pos.CENTER);
		vTitreInfo1.setPadding(new Insets(20));
		titreInfo1 = new Label("Déplacement d'un pion du chef \ndes vigiles");
		titreInfo1.setTextAlignment(TextAlignment.CENTER);
		titreInfo1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo1.setTextFill(Color.WHITE);
		vTitreInfo1.getChildren().addAll(titreInfo1);

		VBox vInfo1 = new VBox();
		vInfo1.setAlignment(Pos.CENTER);
		lInfo1 = new Label("Voici l'information que vous voulez savoir");
		lInfo1.setTextAlignment(TextAlignment.CENTER);
		lInfo1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		lInfo1.setTextFill(Color.WHITE);
		vInfo1.getChildren().addAll(lInfo1);

		info1.setMargin(vInfo1, new Insets(-30, 0, 0, 0));
		info1.setTop(vTitreInfo1);
		info1.setCenter(vInfo1);
		info1.setBottom(bQuitterInfo1);
		info1.setVisible(true);
		notifInfo.setBottomAnchor(info1, 206.0);
		notifInfo.setLeftAnchor(info1, 605.0);

		info2 = new BorderPane();
		info2.setPrefSize(700, 200);
		info2.setMaxSize(700, 200);
		info2.setPadding(new Insets(10));
		info2.setStyle(
				" -fx-background-color:#1A1A1A; -fx-background-radius: 20px; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 20px;");

		bQuitterInfo2 = new Button("QUITTER");
		bQuitterInfo2.setAlignment(Pos.CENTER);
		bQuitterInfo2.setStyle(styleBoutonInfo);
		bQuitterInfo2.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo2.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo2.setOnAction(event -> {
			notifInfo.setVisible(false);
		});
		bQuitterInfo2.setOnMouseEntered(event -> {
			bQuitterInfo2.setStyle(styleBoutonInfoSouris);
		});
		bQuitterInfo2.setOnMouseExited(event -> {
			bQuitterInfo2.setStyle(styleBoutonInfo);
		});

		VBox vTitreInfo2 = new VBox();
		vTitreInfo2.setAlignment(Pos.CENTER);
		vTitreInfo2.setPadding(new Insets(20));
		titreInfo2 = new Label("Déplacement d'un pion du chef \ndes vigiles");
		titreInfo2.setTextAlignment(TextAlignment.CENTER);
		titreInfo2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo2.setTextFill(Color.WHITE);
		vTitreInfo2.getChildren().addAll(titreInfo2);

		VBox vInfo2 = new VBox();
		vInfo2.setAlignment(Pos.CENTER);
		lInfo2 = new Label("Voici l'information que vous voulez savoir");
		lInfo2.setTextAlignment(TextAlignment.CENTER);
		lInfo2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		lInfo2.setTextFill(Color.WHITE);
		vInfo2.getChildren().addAll(lInfo2);

		info2.setMargin(vInfo2, new Insets(-30, 0, 0, 0));
		info2.setRotate(90);
		info2.setTop(vTitreInfo2);
		info2.setCenter(vInfo2);
		info2.setBottom(bQuitterInfo2);
		notifInfo.setTopAnchor(info2, 436.0);

		info3 = new BorderPane();
		info3.setPrefSize(700, 200);
		info3.setMaxSize(700, 200);
		info3.setPadding(new Insets(10));
		info3.setStyle(
				" -fx-background-color:#1A1A1A; -fx-background-radius: 20px; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 20px;");

		bQuitterInfo3 = new Button("QUITTER");
		bQuitterInfo3.setAlignment(Pos.CENTER);
		bQuitterInfo3.setStyle(styleBoutonInfo);
		bQuitterInfo3.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo3.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo3.setOnAction(event -> {
			notifInfo.setVisible(false);
		});
		bQuitterInfo3.setOnMouseEntered(event -> {
			bQuitterInfo3.setStyle(styleBoutonInfoSouris);
		});
		bQuitterInfo3.setOnMouseExited(event -> {
			bQuitterInfo3.setStyle(styleBoutonInfo);
		});

		VBox vTitreInfo3 = new VBox();
		vTitreInfo3.setAlignment(Pos.CENTER);
		vTitreInfo3.setPadding(new Insets(20));
		titreInfo3 = new Label("Déplacement d'un pion du chef \ndes vigiles");
		titreInfo3.setTextAlignment(TextAlignment.CENTER);
		titreInfo3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo3.setTextFill(Color.WHITE);
		vTitreInfo3.getChildren().addAll(titreInfo3);

		VBox vInfo3 = new VBox();
		vInfo3.setAlignment(Pos.CENTER);
		lInfo3 = new Label("Voici l'information que vous voulez savoir");
		lInfo3.setTextAlignment(TextAlignment.CENTER);
		lInfo3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		lInfo3.setTextFill(Color.WHITE);
		vInfo3.getChildren().addAll(lInfo3);

		info3.setMargin(vInfo3, new Insets(-30, 0, 0, 0));
		info3.setRotate(180);
		info3.setTop(vTitreInfo3);
		info3.setCenter(vInfo3);
		info3.setBottom(bQuitterInfo3);
		notifInfo.setTopAnchor(info3, 206.0);
		notifInfo.setLeftAnchor(info3, 605.0);

		info4 = new BorderPane();
		info4.setPrefSize(700, 200);
		info4.setMaxSize(700, 200);
		info4.setPadding(new Insets(10));
		info4.setStyle(
				" -fx-background-color:#1A1A1A; -fx-background-radius: 20px; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 20px;");

		bQuitterInfo4 = new Button("QUITTER");
		bQuitterInfo4.setAlignment(Pos.CENTER);
		bQuitterInfo4.setStyle(styleBoutonInfo);
		bQuitterInfo4.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo4.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo4.setOnAction(event -> {
			notifInfo.setVisible(false);
		});
		bQuitterInfo4.setOnMouseEntered(event -> {
			bQuitterInfo4.setStyle(styleBoutonInfoSouris);
		});
		bQuitterInfo4.setOnMouseExited(event -> {
			bQuitterInfo4.setStyle(styleBoutonInfo);
		});

		VBox vTitreInfo4 = new VBox();
		vTitreInfo4.setAlignment(Pos.CENTER);
		vTitreInfo4.setPadding(new Insets(20));
		titreInfo4 = new Label("Déplacement d'un pion du chef \ndes vigiles");
		titreInfo4.setTextAlignment(TextAlignment.CENTER);
		titreInfo4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo4.setTextFill(Color.WHITE);
		vTitreInfo4.getChildren().addAll(titreInfo4);

		VBox vInfo4 = new VBox();
		vInfo4.setAlignment(Pos.CENTER);
		lInfo4 = new Label("Voici l'information que vous voulez savoir");
		lInfo4.setTextAlignment(TextAlignment.CENTER);
		lInfo4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		lInfo4.setTextFill(Color.WHITE);
		vInfo4.getChildren().addAll(lInfo4);

		info4.setMargin(vInfo4, new Insets(-30, 0, 0, 0));
		info4.setRotate(270);
		info4.setTop(vTitreInfo4);
		info4.setCenter(vInfo4);
		info4.setBottom(bQuitterInfo4);
		notifInfo.setTopAnchor(info4, 436.0);
		notifInfo.setRightAnchor(info4, 0.0);

		notifInfo.getChildren().addAll(info1, info2, info3, info4);
		notifInfo.setVisible(false);

		borderJoueurs.setTop(hHaut);
		borderJoueurs.setBottom(hBas);
		borderJoueurs.setLeft(hGauche);
		borderJoueurs.setRight(hDroite);

		borderJoueurs.setMinSize(1920, 1080);
		borderJoueurs.setPrefSize(1920, 1080);
		borderJoueurs.setMaxSize(1920, 1080);

		imgFond = new ImageView(DataControl.PLATEAU_NB);
		imgFond.setScaleX(0.4362);
		imgFond.setScaleY(0.4362);

		/*
		 * afficheJoueursLieu1.setTextFill(Color.WHITE);
		 * afficheJoueursLieu2.setTextFill(Color.WHITE);
		 * afficheJoueursLieu3.setTextFill(Color.WHITE);
		 * afficheJoueursLieu4.setTextFill(Color.WHITE);
		 * afficheJoueursLieu5.setTextFill(Color.WHITE);
		 * afficheJoueursLieu6.setTextFill(Color.WHITE);
		 * afficheJoueursLieu1.setFont(fontPerso);
		 * afficheJoueursLieu2.setFont(fontPerso);
		 * afficheJoueursLieu3.setFont(fontPerso);
		 * afficheJoueursLieu4.setFont(fontPerso);
		 * afficheJoueursLieu5.setFont(fontPerso);
		 * afficheJoueursLieu6.setFont(fontPerso);
		 */

		imgCarteFerme1 = new ImageView(DataControl.FERME1);
		imgCarteFerme1.setRotate(128);
		imgCarteFerme1.setTranslateX(250);
		imgCarteFerme1.setTranslateY(320);

		imgCarteFerme2 = new ImageView(DataControl.FERME2);
		imgCarteFerme2.setRotate(-133);
		imgCarteFerme2.setTranslateX(-310);
		imgCarteFerme2.setTranslateY(295);

		imgCarteFerme3 = new ImageView(DataControl.FERME3);
		imgCarteFerme3.setRotate(-62);
		imgCarteFerme3.setTranslateX(-350);
		imgCarteFerme3.setTranslateY(-180);

		imgCarteFerme5 = new ImageView(DataControl.FERME5);
		imgCarteFerme5.setRotate(4);
		imgCarteFerme5.setTranslateX(30);
		imgCarteFerme5.setTranslateY(-350);

		imgCarteFerme6 = new ImageView(DataControl.FERME6);
		imgCarteFerme6.setRotate(53);
		imgCarteFerme6.setTranslateX(360);
		imgCarteFerme6.setTranslateY(-110);

		imgCarteFerme1.setVisible(false);
		imgCarteFerme2.setVisible(false);
		imgCarteFerme3.setVisible(false);
		imgCarteFerme5.setVisible(false);
		imgCarteFerme6.setVisible(false);

		this.setStyle(" -fx-background-color:#151515;");

		j1.setVisible(false);
		j2.setVisible(false);
		j3.setVisible(false);
		j4.setVisible(false);
		j5.setVisible(false);
		j6.setVisible(false);

		this.getChildren().addAll(imgFond, borderJoueurs, aPlateau, imgCarteFerme1, imgCarteFerme2, imgCarteFerme3,
				imgCarteFerme5, imgCarteFerme6, notifInfo);
		this.setMinSize(1920, 1080);
		this.setPrefSize(1920, 1080);
		this.setMaxSize(1920, 1080);

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}

	@Override
	public synchronized void nbLieuZombie(int lieu, int val) {
		String tmp = val + " " + International.trad("text.nbZombie");
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
	public void nomJoueur(int joueur, Couleur couleur, String val) {
		// String tmp = val;
		Platform.runLater(() -> {
			switch (joueur) {
			case 0:
				nomJoueur1.setText(val);
				nbPerso1.setStyle(color(couleur));
				nbCartes1.setStyle(color(couleur));
				nomJoueur1.setStyle(color(couleur));
				j1.setStyle(color(couleur));
				j1.setVisible(true);
				break;
			case 1:
				nomJoueur2.setText(val);
				nbPerso2.setStyle(color(couleur));
				nbCartes2.setStyle(color(couleur));
				nomJoueur2.setStyle(color(couleur));
				j2.setStyle(color(couleur));
				j2.setVisible(true);
				break;
			case 2:
				nomJoueur3.setText(val);
				nbPerso3.setStyle(color(couleur));
				nbCartes3.setStyle(color(couleur));
				nomJoueur3.setStyle(color(couleur));
				j3.setStyle(color(couleur));
				j3.setVisible(true);
				break;
			case 3:
				nomJoueur4.setText(val);
				nbPerso4.setStyle(color(couleur));
				nbCartes4.setStyle(color(couleur));
				nomJoueur4.setStyle(color(couleur));
				j4.setStyle(color(couleur));
				j4.setVisible(true);
				break;
			case 4:
				nomJoueur5.setText(val);
				nbPerso5.setStyle(color(couleur));
				nbCartes5.setStyle(color(couleur));
				nomJoueur5.setStyle(color(couleur));
				j5.setStyle(color(couleur));
				j5.setVisible(true);
				break;
			case 5:
				nomJoueur6.setText(val);
				nbPerso6.setStyle(color(couleur));
				nbCartes6.setStyle(color(couleur));
				nomJoueur6.setStyle(color(couleur));
				j6.setStyle(color(couleur));
				j6.setVisible(true);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + joueur);
			}
		});
	}

	public String color(Couleur couleur) {
		switch (couleur) {
		case B:
			return bleu;
		case R:
			return rouge;
		case V:
			return vert;
		case N:
			return noir;
		case J:
			return jaune;
		case M:
			return marron;
		default:
			System.err.println("couleur " + couleur + "inconnue"); // TODO en faire une exception
			break;
		}

		return null;
	}

	@Override
	public void lieuFerme(int lieu, boolean val) {
		if (!val)
			return;
		Platform.runLater(() -> {
			switch (lieu) {
			case 1:
				imgCarteFerme1.setVisible(true);
				break;
			case 2:
				imgCarteFerme2.setVisible(true);

				break;
			case 3:
				imgCarteFerme3.setVisible(true);

				break;
			case 5:
				imgCarteFerme5.setVisible(true);
				break;
			case 6:
				imgCarteFerme6.setVisible(true);
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
		Platform.runLater(() -> {
			switch (lieu) {
			case 1:
				imgCarteFerme1.setVisible(false);
				break;
			case 2:
				imgCarteFerme2.setVisible(false);
				break;
			case 3:
				imgCarteFerme3.setVisible(false);
				break;
			case 4:
				// c'est le parking il est forcément ouvert
				break;
			case 5:
				imgCarteFerme5.setVisible(false);
				break;
			case 6:
				imgCarteFerme6.setVisible(false);
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
			case 5:
				nbPerso6.setText(tmp);
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
			case 5:
				nbCartes6.setText(tmp);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + joueur);
			}
		});
	}

	@Override
	public void forceLieu(int lieu, int force) {
		String tmp = International.trad("text.forceEquipeA") + "\n" + International.trad("text.forceEquipeB") + "\n\n"
				+ force;
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
		String tmp = joueur + International.trad("text.chefVigile");
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
		// TODO prendre le string le déouper et trouver quel perso il faut afficher et
		// non afficher
		Platform.runLater(() -> {
			switch (lieu) {
			case 1:
				System.out.println("TODO afficher les images maintenant"); // TODO
				// afficheJoueursLieu1.setText(nomPersosCouleur);
				break;
			case 2:
				System.out.println("TODO afficher les images maintenant"); // TODO
				// afficheJoueursLieu2.setText(nomPersosCouleur);
				break;
			case 3:
				System.out.println("TODO afficher les images maintenant"); // TODO
				// afficheJoueursLieu3.setText(nomPersosCouleur);
				break;
			case 4:
				System.out.println("TODO afficher les images maintenant"); // TODO
				// afficheJoueursLieu4.setText(nomPersosCouleur);
				break;
			case 5:
				System.out.println("TODO afficher les images maintenant"); // TODO
				// afficheJoueursLieu5.setText(nomPersosCouleur);
				break;
			case 6:
				System.out.println("TODO afficher les images maintenant"); // TODO
				// afficheJoueursLieu6.setText(nomPersosCouleur);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + lieu);
			}
		});
	}

	@Override
	public void fouilleCamion(String camion) {
		Platform.runLater(() -> {
			titreInfo1.setText("Fouille du camion");
			titreInfo2.setText("Fouille du camion");
			titreInfo3.setText("Fouille du camion");
			titreInfo4.setText("Fouille du camion");

			lInfo1.setText(camion);
			lInfo2.setText(camion);
			lInfo3.setText(camion);
			lInfo4.setText(camion);
			TranslateTransition transi = new TranslateTransition(Duration.millis(1400), notifInfo);
			transi.setFromY(-1000f);
			transi.setToY(0f);
			transi.setCycleCount((int) 2f);
			transi.setAutoReverse(false);
			transi.play();
			notifInfo.setVisible(true);
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
					notifInfo.setVisible(false);
				}
			}, 5000);
		});
	}

	@Override
	public void prevenirDeplacementVigile(String depvig) {
		Platform.runLater(() -> {
			titreInfo1.setText("Déplacement d'un pion du chef des vigiles");
			titreInfo2.setText("Déplacement d'un pion du chef des vigiles");
			titreInfo3.setText("Déplacement d'un pion du chef des vigiles");
			titreInfo4.setText("Déplacement d'un pion du chef des vigiles");
			lInfo1.setText(depvig);
			lInfo2.setText(depvig);
			lInfo3.setText(depvig);
			lInfo4.setText(depvig);
			TranslateTransition transi = new TranslateTransition(Duration.millis(1400), notifInfo);
			transi.setFromY(-1000f);
			transi.setToY(0f);
			transi.setCycleCount((int) 2f);
			transi.setAutoReverse(false);
			transi.play();
			notifInfo.setVisible(true);
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
					notifInfo.setVisible(false);
				}
			}, 5000);
		});
	}

	@Override
	public void electionChef(String message) {
		Platform.runLater(() -> {
			titreInfo1.setText("Election du chef des vigiles");
			titreInfo2.setText("Election du chef des vigiles");
			titreInfo3.setText("Election du chef des vigiles");
			titreInfo4.setText("Election du chef des vigiles");
			lInfo1.setText(message);
			lInfo2.setText(message);
			lInfo3.setText(message);
			lInfo4.setText(message);
			TranslateTransition transi = new TranslateTransition(Duration.millis(1400), notifInfo);
			transi.setFromY(-1000f);
			transi.setToY(0f);
			transi.setCycleCount((int) 2f);
			transi.setAutoReverse(false);
			transi.play();
			notifInfo.setVisible(true);
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
					notifInfo.setVisible(false);
				}
			}, 5000);
		});
	}
}
