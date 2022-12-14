package pp.ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import pp.Personnage;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.event.EvenementStockage;
import pp.ihm.event.IPlateauListener;
import pp.ihm.langues.ITraduction;
import pp.ihm.langues.International;
import reseau.type.Couleur;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * 
 * @author florian
 * @author remy
 * @author sebastien
 * @author tom
 *
 */
public class PlateauPane extends StackPane implements IPlateauListener, ITraduction {
	// auteur florian
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.PLATEAU; // nom du pane

	private final int margeP = 24;
	private final Insets margeBoutonPause = new Insets(margeP, margeP, margeP, margeP);
	private final int lhBoutonPause = 80;

	private String nomPolice = "Segoe UI";
	private final Font policeBoutonPause = Font.font(nomPolice, FontWeight.BOLD, 34);

	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 50px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 50px;";
	private String styleBoutonInfo = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff;";
	private String styleBoutonInfoSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private String styleChefVigile = "-fx-background-color: #2D2D2D; -fx-border-color: whitesmoke; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 10px;";
	private String styleInfo = " -fx-background-color:#1A1A1A; -fx-background-radius: 20px; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 20px;";

	private final int margeJ = 20;
	private final Insets margeTexteJoueur = new Insets(margeJ, 10, margeJ, 10);
	private final Insets insetJGauche = new Insets(0, 500, 0, 115);
	private final Insets insetJDroit = new Insets(0, 115, 0, 500);
	private final int tailleVBoxJoueur = 210;

	private final int taillePlateau = 1080;
	private final double opaciteFondLieu = .7;
	private final double arrondiFondLieu = 30;

	private final int margeL = 10;
	private final Insets margeLieu = new Insets(margeL, margeL, margeL, margeL);
	private final int tailleFont = 18;

	private final Font fontInfo = Font.font("Segoe UI", FontWeight.BOLD, tailleFont);

	private String tmpColor = " -fx-background-color:#000000; -fx-text-fill: #FF2626";
	private int largBouton = 155;
	private int hautBouton = 70;

	List<Couleur> couleurs1;
	int o = 0;

	List<VBox> vboxs = new ArrayList<>();
	List<Integer> index = new ArrayList<>();

	List<Integer> persos = new ArrayList<>();
	List<Integer> cartess = new ArrayList<>();
	boolean estChoisirPlace = true;
	String currentCDV = "";
	int currentPioche = 1;

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
	ImageView l4p10;
	ImageView l4p11;
	ImageView l4p12;
	ImageView l4p13;
	ImageView l4p14;
	ImageView l4p15;
	ImageView l4p16;
	ImageView l4p17;
	ImageView l4p18;

	BorderPane bPileCarte;
	ImageView imgPileCarte;
	Label lPileCarte1;
	Label lPileCarte2;
	Label lPileCarte3;
	Label lPileCarte4;

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

	BorderPane b1;
	BorderPane b2;
	BorderPane b3;
	BorderPane b4;
	BorderPane b5;
	BorderPane b6;

	Rectangle fondB1;
	Rectangle fondB2;
	Rectangle fondB3;
	Rectangle fondB4;
	Rectangle fondB5;
	Rectangle fondB6;

	public PlateauPane(ScreenControl sc, Core c) {
		// auteur florian
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

		j1 = new VBox();
		nbPerso1 = new Label("## personnages");
		nbPerso1.setFont(Font.font("Segoe UI", 20));
		nbPerso1.setTextFill(Color.BLACK);
		nbPerso1.setStyle(tmpColor);
		nbCartes1 = new Label("## de cartes");
		nbCartes1.setFont(Font.font("Segoe UI", 20));
		nbCartes1.setTextFill(Color.BLACK);
		nbCartes1.setStyle(tmpColor);
		nomJoueur1 = new Label(International.trad("texte.nomJoueur1"));
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
		nomJoueur2 = new Label(International.trad("texte.nomJoueur2"));
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
		nomJoueur3 = new Label(International.trad("texte.nomJoueur3"));
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
		nomJoueur4 = new Label(International.trad("texte.nomJoueur4"));
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
		nomJoueur5 = new Label(International.trad("texte.nomJoueur5"));
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
		nomJoueur6 = new Label(International.trad("texte.nomJoueur6"));
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
		hGauche.setTranslateY(150);

		/////////////////////////////////////////////////////
		aPlateau.setMinSize(taillePlateau, taillePlateau);
		aPlateau.setPrefSize(taillePlateau, taillePlateau);
		aPlateau.setMaxSize(taillePlateau, taillePlateau);

		VBox vbRight1 = new VBox();
		vbRight1.setAlignment(Pos.TOP_CENTER);
		vbRight1.setSpacing(30);

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

		b1 = new BorderPane();
		b1.setPadding(new Insets(5));
		b1.setPrefSize(320, 210);
		b1.setMaxSize(320, 210);
		// vbRight1.setBackground(new Background(new BackgroundFill(Color.BLACK,
		// CornerRadii.EMPTY, null)));

		vbRight1.getChildren().addAll(nbPlace1, nbZombies1);

		b1.setCenter(joueursPresents1);
		b1.setRight(vbRight1);
		// b1.setOpacity(.9);

		b1.setRotate(128);

		AnchorPane.setTopAnchor(b1, 755.0);
		AnchorPane.setLeftAnchor(b1, 630.0);

		fondB1 = new Rectangle();
		fondB1.setWidth(330);
		fondB1.setHeight(215);
		fondB1.setArcHeight(arrondiFondLieu);
		fondB1.setArcWidth(arrondiFondLieu);
		fondB1.setOpacity(opaciteFondLieu);
		fondB1.setRotate(128);
		AnchorPane.setTopAnchor(fondB1, 755.0);
		AnchorPane.setLeftAnchor(fondB1, 630.0);

		////
		// auteur remy
		VBox vbRight2 = new VBox();
		vbRight2.setAlignment(Pos.TOP_CENTER);
		vbRight2.setSpacing(30);

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

		b2 = new BorderPane();
		b2.setPadding(new Insets(5));
		b2.setPrefSize(320, 215);
		b2.setMaxSize(320, 215);
		// vbRight2.setBackground(new Background(new BackgroundFill(Color.BLACK,
		// CornerRadii.EMPTY, null)));

		vbRight2.getChildren().addAll(nbPlace2, nbZombies2);

		b2.setCenter(joueursPresents2);
		b2.setRight(vbRight2);
		// b2.setOpacity(.9);
		b2.setRotate(-133);

		AnchorPane.setTopAnchor(b2, 725.0);
		AnchorPane.setLeftAnchor(b2, 70.0);

		fondB2 = new Rectangle();
		fondB2.setWidth(330);
		fondB2.setHeight(215);
		fondB2.setArcHeight(arrondiFondLieu);
		fondB2.setArcWidth(arrondiFondLieu);
		fondB2.setOpacity(opaciteFondLieu);
		fondB2.setRotate(-133);
		AnchorPane.setTopAnchor(fondB2, 725.0);
		AnchorPane.setLeftAnchor(fondB2, 70.0);

		///
		// auteur florian
		VBox vbRight3 = new VBox();
		vbRight3.setAlignment(Pos.TOP_CENTER);
		vbRight3.setSpacing(30);

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

		b3 = new BorderPane();
		b3.setPadding(new Insets(5));
		b3.setPrefSize(325, 210);
		b3.setMaxSize(325, 210);
		// vbRight3.setBackground(new Background(new BackgroundFill(Color.BLACK,
		// CornerRadii.EMPTY, null)));

		vbRight3.getChildren().addAll(nbPlace3, nbZombies3);

		b3.setCenter(joueursPresents3);
		b3.setRight(vbRight3);
		// b3.setOpacity(.9);

		b3.setRotate(-62);

		AnchorPane.setTopAnchor(b3, 260.0);
		AnchorPane.setLeftAnchor(b3, 25.0);

		fondB3 = new Rectangle();
		fondB3.setWidth(330);
		fondB3.setHeight(215);
		fondB3.setArcHeight(arrondiFondLieu);
		fondB3.setArcWidth(arrondiFondLieu);
		fondB3.setOpacity(opaciteFondLieu);
		fondB3.setRotate(-62);
		AnchorPane.setTopAnchor(fondB3, 260.0);
		AnchorPane.setLeftAnchor(fondB3, 25.0);

		///
		VBox vbRight4 = new VBox();
		vbRight4.setAlignment(Pos.TOP_CENTER);
		vbRight4.setSpacing(30);

		VBox joueursPresents4 = new VBox();
		joueursPresents4.setAlignment(Pos.CENTER);
		HBox joueursPresents4L1 = new HBox();
		HBox joueursPresents4L2 = new HBox();
		HBox joueursPresents4L3 = new HBox();
		HBox joueursPresents4L4 = new HBox();
		HBox joueursPresents4L5 = new HBox();

		double carteScale = 0.7;

		l4p1 = new ImageView(DataControl.NOM_COULEUR);
		l4p1.setScaleX(carteScale);
		l4p1.setScaleY(carteScale);
		l4p2 = new ImageView(DataControl.NOM_COULEUR);
		l4p2.setScaleX(carteScale);
		l4p2.setScaleY(carteScale);
		l4p3 = new ImageView(DataControl.NOM_COULEUR);
		l4p3.setScaleX(carteScale);
		l4p3.setScaleY(carteScale);
		l4p4 = new ImageView(DataControl.NOM_COULEUR);
		l4p4.setScaleX(carteScale);
		l4p4.setScaleY(carteScale);
		l4p5 = new ImageView(DataControl.NOM_COULEUR);
		l4p5.setScaleX(carteScale);
		l4p5.setScaleY(carteScale);
		l4p6 = new ImageView(DataControl.NOM_COULEUR);
		l4p6.setScaleX(carteScale);
		l4p6.setScaleY(carteScale);
		l4p7 = new ImageView(DataControl.NOM_COULEUR);
		l4p7.setScaleX(carteScale);
		l4p7.setScaleY(carteScale);
		l4p8 = new ImageView(DataControl.NOM_COULEUR);
		l4p8.setScaleX(carteScale);
		l4p8.setScaleY(carteScale);
		l4p9 = new ImageView(DataControl.NOM_COULEUR);
		l4p9.setScaleX(carteScale);
		l4p9.setScaleY(carteScale);
		l4p10 = new ImageView(DataControl.NOM_COULEUR);
		l4p10.setScaleX(carteScale);
		l4p10.setScaleY(carteScale);
		l4p11 = new ImageView(DataControl.NOM_COULEUR);
		l4p11.setScaleX(carteScale);
		l4p11.setScaleY(carteScale);
		l4p12 = new ImageView(DataControl.NOM_COULEUR);
		l4p12.setScaleX(carteScale);
		l4p12.setScaleY(carteScale);
		l4p13 = new ImageView(DataControl.NOM_COULEUR);
		l4p13.setScaleX(carteScale);
		l4p13.setScaleY(carteScale);
		l4p14 = new ImageView(DataControl.NOM_COULEUR);
		l4p14.setScaleX(carteScale);
		l4p14.setScaleY(carteScale);
		l4p15 = new ImageView(DataControl.NOM_COULEUR);
		l4p15.setScaleX(carteScale);
		l4p15.setScaleY(carteScale);
		l4p16 = new ImageView(DataControl.NOM_COULEUR);
		l4p16.setScaleX(carteScale);
		l4p16.setScaleY(carteScale);
		l4p17 = new ImageView(DataControl.NOM_COULEUR);
		l4p17.setScaleX(carteScale);
		l4p17.setScaleY(carteScale);
		l4p18 = new ImageView(DataControl.NOM_COULEUR);
		l4p18.setScaleX(carteScale);
		l4p18.setScaleY(carteScale);

		int carteSpacing = -20;
		joueursPresents4L1.getChildren().addAll(l4p1, l4p2, l4p3, l4p4);
		joueursPresents4L1.setSpacing(carteSpacing);
		joueursPresents4L2.getChildren().addAll(l4p5, l4p6, l4p7, l4p8);
		joueursPresents4L2.setSpacing(carteSpacing);
		joueursPresents4L3.getChildren().addAll(l4p9, l4p10, l4p11, l4p12);
		joueursPresents4L3.setSpacing(carteSpacing);
		joueursPresents4L4.getChildren().addAll(l4p13, l4p14, l4p15, l4p16);
		joueursPresents4L4.setSpacing(carteSpacing);
		joueursPresents4L5.getChildren().addAll(l4p17, l4p18);
		joueursPresents4L5.setSpacing(carteSpacing);

		joueursPresents4.getChildren().addAll(joueursPresents4L1, joueursPresents4L2, joueursPresents4L3,
				joueursPresents4L4, joueursPresents4L5);
		joueursPresents4.setSpacing(carteSpacing);
		joueursPresents4.setPrefSize(170, 100);

		nbZombies4 = new Label(International.trad("text.nbZombie"));
		nbZombies4.setFont(fontInfo);
		nbZombies4.setTextFill(Color.GREEN);

		nbPlace4 = new Label("## / ???");
		nbPlace4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace4.setTextFill(Color.WHITE);

		b4 = new BorderPane();
		b4.setPadding(new Insets(5));
		b4.setPrefSize(270, 210);
		b4.setMaxSize(270, 210);
		// vbRight4.setBackground(new Background(new BackgroundFill(Color.BLACK,
		// CornerRadii.EMPTY, null)));

		vbRight4.getChildren().addAll(nbPlace4, nbZombies4);

		b4.setCenter(joueursPresents4);
		b4.setRight(vbRight4);
		b4.setRotate(10);
		// b4.setOpacity(.9);

		AnchorPane.setTopAnchor(b4, 440.0);
		AnchorPane.setLeftAnchor(b4, 365.0);

		fondB4 = new Rectangle();
		fondB4.setWidth(330);
		fondB4.setHeight(280);
		fondB4.setArcHeight(arrondiFondLieu);
		fondB4.setArcWidth(arrondiFondLieu);
		fondB4.setOpacity(opaciteFondLieu);
		fondB4.setRotate(10);
		AnchorPane.setTopAnchor(fondB4, 440.0);
		AnchorPane.setLeftAnchor(fondB4, 365.0);

		///
		// auteur remy
		VBox vbRight5 = new VBox();
		vbRight5.setAlignment(Pos.TOP_CENTER);
		vbRight5.setSpacing(30);

		nbPlace5 = new Label("## / 3");
		nbPlace5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		nbPlace5.setTextFill(Color.WHITE);

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

		b5 = new BorderPane();
		b5.setPadding(new Insets(5));
		b5.setPrefSize(325, 215);
		b5.setMaxSize(325, 215);
		// vbRight5.setBackground(new Background(new BackgroundFill(Color.BLACK,
		// CornerRadii.EMPTY, null)));

		vbRight5.getChildren().addAll(nbPlace5, nbZombies5);

		b5.setCenter(joueursPresents5);
		b5.setRight(vbRight5);
		// b5.setOpacity(.9);

		b5.setRotate(4);

		AnchorPane.setTopAnchor(b5, 77.5);
		AnchorPane.setLeftAnchor(b5, 408.5);

		fondB5 = new Rectangle();
		fondB5.setWidth(330);
		fondB5.setHeight(215);
		fondB5.setArcHeight(arrondiFondLieu);
		fondB5.setArcWidth(arrondiFondLieu);
		fondB5.setOpacity(opaciteFondLieu);
		fondB5.setRotate(4);
		AnchorPane.setTopAnchor(fondB5, 77.0);
		AnchorPane.setLeftAnchor(fondB5, 408.0);

		///

		VBox vbRight6 = new VBox();
		vbRight6.setAlignment(Pos.TOP_CENTER);
		vbRight6.setSpacing(30);

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

		b6 = new BorderPane();
		b6.setPrefSize(310, 215);
		b6.setMaxSize(310, 215);
		b6.setPadding(new Insets(5));
		// vbRight6.setBackground(new Background(new BackgroundFill(Color.BLACK,
		// CornerRadii.EMPTY, null)));

		vbRight6.getChildren().addAll(nbPlace6, nbZombies6);

		b6.setCenter(joueursPresents6);
		b6.setRight(vbRight6);

		b6.setRotate(53);

		AnchorPane.setTopAnchor(b6, 320.0);
		AnchorPane.setLeftAnchor(b6, 747.5);

		fondB6 = new Rectangle();
		fondB6.setWidth(330);
		fondB6.setHeight(215);
		fondB6.setArcHeight(arrondiFondLieu);
		fondB6.setArcWidth(arrondiFondLieu);
		fondB6.setOpacity(opaciteFondLieu);
		fondB6.setRotate(53);
		AnchorPane.setTopAnchor(fondB6, 320.0);
		AnchorPane.setLeftAnchor(fondB6, 747.0);

		/////
		// auteur florian
		lChefVigile = new Label();
		lChefVigile.setText("XXXXXXXXX est le chef des vigiles"); // do not translate, do not remove (c'est du
																	// sample text pour l'ihm)
		lChefVigile.setStyle(styleChefVigile);
		lChefVigile.setTextFill(Color.WHITE);
		lChefVigile.setFont(fontInfo);
		lChefVigile.setPadding(margeLieu);
		lChefVigile.setTranslateY(515);

		lChefVigile2 = new Label();
		lChefVigile2.setText("XXXXXXXXX est le chef des vigiles"); // do not translate, do not remove (c'est
																	// du sample text pour l'ihm)
		lChefVigile2.setStyle(styleChefVigile);
		lChefVigile2.setTextFill(Color.WHITE);
		lChefVigile2.setFont(fontInfo);
		lChefVigile2.setPadding(margeLieu);
		lChefVigile2.setRotate(180);
		lChefVigile2.setTranslateY(-515);

		lChefVigile3 = new Label();
		lChefVigile3.setText("XXXXXXXXX est le chef des vigiles"); // do not translate, do not remove (c'est
																	// du sample text pour l'ihm)
		lChefVigile3.setStyle(styleChefVigile);
		lChefVigile3.setTextFill(Color.WHITE);
		lChefVigile3.setFont(fontInfo);
		lChefVigile3.setPadding(margeLieu);
		lChefVigile3.setRotate(90);
		lChefVigile3.setTranslateX(-575);

		lChefVigile4 = new Label();
		lChefVigile4.setText("XXXXXXXXX est le chef des vigiles"); // do not translate, do not remove (c'est
																	// du sample text pour l'ihm)
		lChefVigile4.setStyle(styleChefVigile);
		lChefVigile4.setTextFill(Color.WHITE);
		lChefVigile4.setFont(fontInfo);
		lChefVigile4.setPadding(margeLieu);
		lChefVigile4.setRotate(-90);
		lChefVigile4.setTranslateX(575);

		////

		aPlateau.getChildren().addAll(fondB1, fondB2, fondB3, fondB4, fondB5, fondB6, b1, b2, b3, b4, b5, b6);
		// auteur remy
		notifInfo = new AnchorPane();
		notifInfo.setMinSize(1920, 1080);

		info1 = new BorderPane();
		info1.setPrefSize(700, 200);
		info1.setMaxSize(700, 200);
		info1.setPadding(new Insets(10));
		info1.setStyle(styleInfo);

		bQuitterInfo1 = new Button("Ok");
		bQuitterInfo1.setAlignment(Pos.CENTER);
		bQuitterInfo1.setStyle(styleBoutonInfo);
		bQuitterInfo1.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo1.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo1.setOnAction(event -> notifInfo.setVisible(false));
		bQuitterInfo1.setOnMouseEntered(event -> bQuitterInfo1.setStyle(styleBoutonInfoSouris));
		bQuitterInfo1.setOnMouseExited(event -> bQuitterInfo1.setStyle(styleBoutonInfo));

		VBox vTitreInfo1 = new VBox();
		vTitreInfo1.setAlignment(Pos.CENTER);
		vTitreInfo1.setPadding(new Insets(20));
		titreInfo1 = new Label(
				International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
		titreInfo1.setTextAlignment(TextAlignment.CENTER);
		titreInfo1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo1.setTextFill(Color.WHITE);
		vTitreInfo1.getChildren().addAll(titreInfo1);

		VBox vInfo1 = new VBox();
		vInfo1.setAlignment(Pos.CENTER);
		lInfo1 = new Label(International.trad("text.lInfo"));
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
		info2.setStyle(styleInfo);

		bQuitterInfo2 = new Button("Ok");
		bQuitterInfo2.setAlignment(Pos.CENTER);
		bQuitterInfo2.setStyle(styleBoutonInfo);
		bQuitterInfo2.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo2.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo2.setOnAction(event -> notifInfo.setVisible(false));
		bQuitterInfo2.setOnMouseEntered(event -> bQuitterInfo2.setStyle(styleBoutonInfoSouris));
		bQuitterInfo2.setOnMouseExited(event -> bQuitterInfo2.setStyle(styleBoutonInfo));

		VBox vTitreInfo2 = new VBox();
		vTitreInfo2.setAlignment(Pos.CENTER);
		vTitreInfo2.setPadding(new Insets(20));
		titreInfo2 = new Label(
				International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
		titreInfo2.setTextAlignment(TextAlignment.CENTER);
		titreInfo2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo2.setTextFill(Color.WHITE);
		vTitreInfo2.getChildren().addAll(titreInfo2);

		VBox vInfo2 = new VBox();
		vInfo2.setAlignment(Pos.CENTER);
		lInfo2 = new Label(International.trad("text.lInfo"));
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
		info3.setStyle(styleInfo);

		bQuitterInfo3 = new Button("Ok");
		bQuitterInfo3.setAlignment(Pos.CENTER);
		bQuitterInfo3.setStyle(styleBoutonInfo);
		bQuitterInfo3.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo3.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo3.setOnAction(event -> notifInfo.setVisible(false));
		bQuitterInfo3.setOnMouseEntered(event -> bQuitterInfo3.setStyle(styleBoutonInfoSouris));
		bQuitterInfo3.setOnMouseExited(event -> bQuitterInfo3.setStyle(styleBoutonInfo));

		VBox vTitreInfo3 = new VBox();
		vTitreInfo3.setAlignment(Pos.CENTER);
		vTitreInfo3.setPadding(new Insets(20));
		titreInfo3 = new Label(
				International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
		titreInfo3.setTextAlignment(TextAlignment.CENTER);
		titreInfo3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo3.setTextFill(Color.WHITE);
		vTitreInfo3.getChildren().addAll(titreInfo3);

		VBox vInfo3 = new VBox();
		vInfo3.setAlignment(Pos.CENTER);
		lInfo3 = new Label(International.trad("text.lInfo"));
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
		info4.setStyle(styleInfo);

		bQuitterInfo4 = new Button("Ok");
		bQuitterInfo4.setAlignment(Pos.CENTER);
		bQuitterInfo4.setStyle(styleBoutonInfo);
		bQuitterInfo4.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo4.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo4.setOnAction(event -> notifInfo.setVisible(false));
		bQuitterInfo4.setOnMouseEntered(event -> bQuitterInfo4.setStyle(styleBoutonInfoSouris));
		bQuitterInfo4.setOnMouseExited(event -> bQuitterInfo4.setStyle(styleBoutonInfo));

		VBox vTitreInfo4 = new VBox();
		vTitreInfo4.setAlignment(Pos.CENTER);
		vTitreInfo4.setPadding(new Insets(20));
		titreInfo4 = new Label(
				International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
		titreInfo4.setTextAlignment(TextAlignment.CENTER);
		titreInfo4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo4.setTextFill(Color.WHITE);
		vTitreInfo4.getChildren().addAll(titreInfo4);

		VBox vInfo4 = new VBox();
		vInfo4.setAlignment(Pos.CENTER);
		lInfo4 = new Label(International.trad("text.lInfo"));
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

		///////////////////////////////////////

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

		bPileCarte = new BorderPane();
		bPileCarte.setMinSize(280, 280);
		bPileCarte.setPrefSize(280, 280);
		bPileCarte.setMaxSize(280, 280);
		bPileCarte.setTranslateX(-780);
		bPileCarte.setTranslateY(-140);

		imgPileCarte = new ImageView(DataControl.DOS_CARTE);

		lPileCarte1 = new Label("Nombre de carte : ##/##");
		lPileCarte1.setFont(fontInfo);
		lPileCarte1.setTextFill(Color.WHITE);

		lPileCarte2 = new Label("Nombre de carte : ##/##");
		lPileCarte2.setFont(fontInfo);
		lPileCarte2.setTextFill(Color.WHITE);
		lPileCarte2.setRotate(90);
		lPileCarte2.setTranslateX(-90);

		lPileCarte3 = new Label("Nombre de carte : ##/##");
		lPileCarte3.setFont(fontInfo);
		lPileCarte3.setTextFill(Color.WHITE);
		lPileCarte3.setRotate(180);

		lPileCarte4 = new Label("Nombre de carte : ##/##");
		lPileCarte4.setFont(fontInfo);
		lPileCarte4.setTextFill(Color.WHITE);
		lPileCarte4.setMinSize(210, 20);
		lPileCarte4.setPrefSize(210, 20);
		lPileCarte4.setMaxSize(210, 20);
		lPileCarte4.setRotate(-90);
		lPileCarte4.setTranslateX(90);

		BorderPane.setAlignment(imgPileCarte, Pos.CENTER);
		BorderPane.setAlignment(lPileCarte1, Pos.CENTER);
		BorderPane.setAlignment(lPileCarte2, Pos.CENTER);
		BorderPane.setAlignment(lPileCarte3, Pos.CENTER);
		BorderPane.setAlignment(lPileCarte4, Pos.CENTER_RIGHT);
		bPileCarte.setCenter(imgPileCarte);
		bPileCarte.setBottom(lPileCarte1);
		bPileCarte.setLeft(lPileCarte2);
		bPileCarte.setTop(lPileCarte3);
		bPileCarte.setRight(lPileCarte4);

		this.setStyle(" -fx-background-color:#151515;"); // TODO ?? d??placer

		// auteur sebastien

		choixCouleurReset();

		enleverPlateau();

		estChoisirPlace = true;
		persos.clear();
		cartess.clear();
		for (int i = 0; i < 6; i++) {
			persos.add(0);
			cartess.add(0);
		}

		// auteur florian
		this.getChildren().addAll(imgFond, bPileCarte, borderJoueurs, aPlateau, lChefVigile, lChefVigile2, lChefVigile3,
				lChefVigile4, imgCarteFerme1, imgCarteFerme2, imgCarteFerme3, imgCarteFerme5, imgCarteFerme6,
				notifInfo);
		this.setMinSize(1920, 1080);
		this.setPrefSize(1920, 1080);
		this.setMaxSize(1920, 1080);

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}

	/**
	 * Met a jour le nombre de zombies dans le lieu
	 * 
	 * @param lieu le lieu cible
	 * @param val  le nombre de zombies actuel dans le lieu
	 */
	@Override
	public void nbLieuZombie(int lieu, int val) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = val + " " + International.trad("text.nbZombie");
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
				default:
					throw new IllegalArgumentException("Impossible de rajouter des zombies dans le lieu " + lieu);
				}
			}
		});
	}

	/**
	 * Met a jour le nombre de place dans le lieu
	 * 
	 * @param lieu le lieu cible
	 * @param val  le nombre de place actuel dans le lieu
	 */
	@Override
	public void nbPlaceLieu(int lieu, int val) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				switch (lieu) {
				case 1:
					nbPlace1.setText(val + " / 3");
					break;
				case 2:
					nbPlace2.setText(val + " / 4");
					break;
				case 3:
					nbPlace3.setText(val + " / 4");
					break;
				case 4:
					nbPlace4.setText(val + " / ???");
					break;
				case 5:
					nbPlace5.setText(val + " / 3");
					break;
				case 6:
					nbPlace6.setText(val + " / 6");
					break;
				default:
					throw new IllegalArgumentException("Impossible de rajouter des zombies dans le lieu " + lieu);
				}
			}
		});
	}

	/**
	 * Met a jour les joueurs sur le plateau
	 * 
	 * @param joueur  le joueur cible
	 * @param couleur la couleur du joueur
	 * @param val     le nom du joueur
	 */
	@Override
	public void nomJoueur(int joueur, Couleur couleur, String val) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("Joueur " + joueur + " " + couleur.nomEntier() + " " + val);
				switch (index.get(joueur)) {
				case 0:
					nomJoueur1.setText(val);
					nbPerso1.setStyle(IhmOutils.color(couleur));
					nbCartes1.setStyle(IhmOutils.color(couleur));
					nomJoueur1.setStyle(IhmOutils.color(couleur));
					j1.setStyle(IhmOutils.color(couleur));
					j1.setVisible(true);
					j1.setDisable(false);
					break;
				case 1:
					nomJoueur2.setText(val);
					nbPerso2.setStyle(IhmOutils.color(couleur));
					nbCartes2.setStyle(IhmOutils.color(couleur));
					nomJoueur2.setStyle(IhmOutils.color(couleur));
					j2.setStyle(IhmOutils.color(couleur));
					j2.setVisible(true);
					j2.setDisable(false);
					break;
				case 2:
					nomJoueur3.setText(val);
					nbPerso3.setStyle(IhmOutils.color(couleur));
					nbCartes3.setStyle(IhmOutils.color(couleur));
					nomJoueur3.setStyle(IhmOutils.color(couleur));
					j3.setStyle(IhmOutils.color(couleur));
					j3.setVisible(true);
					j3.setDisable(false);
					break;
				case 3:
					nomJoueur4.setText(val);
					nbPerso4.setStyle(IhmOutils.color(couleur));
					nbCartes4.setStyle(IhmOutils.color(couleur));
					nomJoueur4.setStyle(IhmOutils.color(couleur));
					j4.setStyle(IhmOutils.color(couleur));
					j4.setVisible(true);
					j4.setDisable(false);
					break;
				case 4:
					nomJoueur5.setText(val);
					nbPerso5.setStyle(IhmOutils.color(couleur));
					nbCartes5.setStyle(IhmOutils.color(couleur));
					nomJoueur5.setStyle(IhmOutils.color(couleur));
					j5.setStyle(IhmOutils.color(couleur));
					j5.setVisible(true);
					j5.setDisable(false);
					break;
				case 5:
					nomJoueur6.setText(val);
					nbPerso6.setStyle(IhmOutils.color(couleur));
					nbCartes6.setStyle(IhmOutils.color(couleur));
					nomJoueur6.setStyle(IhmOutils.color(couleur));
					j6.setStyle(IhmOutils.color(couleur));
					j6.setVisible(true);
					j6.setDisable(false);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + joueur);
				}
			}
		});
	}

	/**
	 * Met a jour les lieux ferm??
	 * 
	 * @param lieu le lieu cible
	 * @param val  si c'est ouvert
	 */
	@Override
	public void lieuFerme(int lieu, boolean val) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!val)
					return;
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
			}
		});
	}

	/**
	 * Met a jour les lieux ouverts
	 * 
	 * @param lieu le lieu cible
	 * @param val  si c'est ferm??
	 */
	@Override
	public void lieuOuvert(int lieu, boolean val) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!val)
					return;
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
					// c'est le parking il est forc??ment ouvert
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
			}
		});
	}

	/**
	 * Met a jour le nombre de personnages d'un joueur
	 * 
	 * @param joueur le joueur cible
	 * @param perso  le nombre de personnages
	 */
	@Override
	public void nbPersoJoueur(int joueur, int perso) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = perso + " " + International.trad("text.perso.num");
				switch (index.get(joueur)) {
				case 0:
					persos.set(0, perso);
					nbPerso1.setText(tmp);
					break;
				case 1:
					persos.set(1, perso);
					nbPerso2.setText(tmp);
					break;
				case 2:
					persos.set(2, perso);
					nbPerso3.setText(tmp);
					break;
				case 3:
					persos.set(3, perso);
					nbPerso4.setText(tmp);
					break;
				case 4:
					persos.set(4, perso);
					nbPerso5.setText(tmp);
					break;
				case 5:
					persos.set(5, perso);
					nbPerso6.setText(tmp);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + joueur);
				}
			}
		});
	}

	/**
	 * Met a jour le nombre de carte d'un joueur
	 * 
	 * @param joueur le joueur cible
	 * @param cartes le nombre de carte
	 */
	@Override
	public void nbCarteJoueur(int joueur, int cartes) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = cartes + " " + International.trad("text.cartes");

				switch (index.get(joueur)) {
				case 0:
					cartess.set(0, cartes);
					nbCartes1.setText(tmp);
					break;
				case 1:
					cartess.set(1, cartes);
					nbCartes2.setText(tmp);
					break;
				case 2:
					cartess.set(2, cartes);
					nbCartes3.setText(tmp);
					break;
				case 3:
					cartess.set(3, cartes);
					nbCartes4.setText(tmp);
					break;
				case 4:
					cartess.set(4, cartes);
					nbCartes5.setText(tmp);
					break;
				case 5:
					cartess.set(5, cartes);
					nbCartes6.setText(tmp);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + joueur);
				}
			}
		});
	}

	/**
	 * Met a jour le nom du chef des vigiles
	 * 
	 * @param joueur le joueur chef des vigiles
	 */
	@Override
	public void nomChefVigile(String joueur) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentCDV = joueur;
				String tmp = joueur + " " + International.trad("text.chefVigile");
				lChefVigile.setText(tmp);
				lChefVigile2.setText(tmp);
				lChefVigile3.setText(tmp);
				lChefVigile4.setText(tmp);
			}
		});
	}

	/**
	 * Passage du l'affichage de fin
	 */
	@Override
	public void finPartie() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				sControl.setPaneOnTop(ApplicationPane.ENDGAME);
				enleverPlateau();
				choixCouleurReset();
				estChoisirPlace = true;
				persos.clear();
				cartess.clear();
				for (int i = 0; i < 6; i++) {
					persos.add(0);
					cartess.add(0);
				}
				currentCDV = "";
			}
		});
	}

	/**
	 * Met a jour les personnages dans les lieux
	 * 
	 * @param lieu le lieu cible
	 * @param p    les personnages du lieu
	 */
	@Override
	public void destionationPerso(int lieu, List<Personnage> p) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				switch (lieu) {
				case 1:
					ImageView[] l1 = { l1p1, l1p2, l1p3 };
					setPersonnageLieu(lieu, p, l1);
					break;
				case 2:
					ImageView[] l2 = { l2p1, l2p2, l2p3, l2p4 };
					setPersonnageLieu(lieu, p, l2);
					break;
				case 3:
					ImageView[] l3 = { l3p1, l3p2, l3p3, l3p4 };
					setPersonnageLieu(lieu, p, l3);
					break;
				case 4:
					ImageView[] l4 = { l4p1, l4p2, l4p3, l4p4, l4p5, l4p6, l4p7, l4p8, l4p9, l4p10, l4p11, l4p12, l4p13,
							l4p14, l4p15, l4p16, l4p17, l4p18 };
					setPersonnageLieu(lieu, p, l4);
					break;
				case 5:
					ImageView[] l5 = { l5p1, l5p2, l5p3 };
					setPersonnageLieu(lieu, p, l5);
					break;
				case 6:
					ImageView[] l6 = { l6p1, l6p2, l6p3, l6p4, l6p5, l6p6 };
					setPersonnageLieu(lieu, p, l6);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + lieu);
				}
			}
		});
	}

	/**
	 * Affiche une popup pour la fouille du camion
	 * 
	 * @param camion le message a afficher
	 */
	@Override
	public void fouilleCamion(String camion) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				popup(International.trad("text.fouilleCamion"), camion, false);
			}
		});
	}

	/**
	 * Affiche une popup pour pr??venir du d??placement du chef des vigiles
	 * 
	 * @param depvig le message a afficher
	 */
	@Override
	public void prevenirDeplacementVigile(String depvig) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				popup(International.trad("text.deplChefVigile3"), depvig, false);
			}
		});
	}

	/**
	 * Affiche une popup pour l'election du chef des vigiles
	 * 
	 * @param message le message a afficher
	 */
	@Override
	public void electionChef(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				popup(International.trad("text.elecChefVigile"), message, false);
			}
		});
	}

	/**
	 * Permet d'afficher des popups
	 * 
	 * @param titre    titre de l'information
	 * @param message  message de l'information
	 * @param okFictif change le mode du bouton (continue ou attendre)
	 */
	public void popup(String titre, String message, boolean okFictif) {
		titreInfo1.setText(titre);
		titreInfo2.setText(titre);
		titreInfo3.setText(titre);
		titreInfo4.setText(titre);
		lInfo1.setText(message);
		lInfo2.setText(message);
		lInfo3.setText(message);
		lInfo4.setText(message);
		FadeTransition transi = new FadeTransition(Duration.millis(500), notifInfo);
		transi.setFromValue(0.0);
		transi.setToValue(1.0);
		transi.setCycleCount(1);
		transi.play();
		notifInfo.setVisible(true);
		Button[] buttons = { bQuitterInfo1, bQuitterInfo2, bQuitterInfo3, bQuitterInfo4 };

		for (Button button : buttons) {
			if (okFictif) {
				button.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						imgFond.setEffect(null);
						borderJoueurs.setEffect(null);
						aPlateau.setEffect(null);
						notifInfo.setVisible(false);
					}
				});
			} else {
				button.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						EvenementStockage.setPopupAccepter(true);
					}
				});
			}
		}
	}

	/**
	 * Permet d'afficher les personnages dans un lieu
	 * 
	 * @param lieu       lieu auquel il faut appliquer le pion
	 * @param p          la liste des pions du lieu
	 * @param imageViews liste des images qui peuvent changer
	 */
	public void setPersonnageLieu(int lieu, List<Personnage> p, ImageView[] imageViews) {
		for (int i = 0; i < imageViews.length; i++) {
			if (i < p.size()) {
				imageViews[i].setVisible(true);
				imageViews[i].setImage(new Image(
						IhmOutils.convertVersImagePerso(p.get(i).getJoueur().getCouleur(), p.get(i).getType())));
			} else {
				imageViews[i].setVisible(false);
			}
		}
	}

	/**
	 * Traduit les elements de ce pane
	 */
	@Override
	public void traduire() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				nbZombies1.setText(International.trad("text.nbZombie"));
				nbZombies2.setText(International.trad("text.nbZombie"));
				nbZombies3.setText(International.trad("text.nbZombie"));
				nbZombies4.setText(International.trad("text.nbZombie"));
				nbZombies5.setText(International.trad("text.nbZombie"));
				nbZombies6.setText(International.trad("text.nbZombie"));
				// nomJoueur1.setText(International.trad("texte.nomJoueur1"));
				// nomJoueur2.setText(International.trad("texte.nomJoueur2"));
				// nomJoueur3.setText(International.trad("texte.nomJoueur3"));
				// nomJoueur4.setText(International.trad("texte.nomJoueur4"));
				// nomJoueur5.setText(International.trad("texte.nomJoueur5"));
				// nomJoueur6.setText(International.trad("texte.nomJoueur6"));
				// bQuitterInfo1.setText(International.trad("bouton.quitter"));
				// bQuitterInfo2.setText(International.trad("bouton.quitter"));
				// bQuitterInfo3.setText(International.trad("bouton.quitter"));
				// bQuitterInfo4.setText(International.trad("bouton.quitter"));
				titreInfo1.setText(
						International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
				titreInfo2.setText(
						International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
				titreInfo3.setText(
						International.trad("text.deplChefVigile1") + "\n" + International.trad("text.deplChefVigile2"));
				lInfo1.setText(International.trad("text.lInfo"));
				lInfo2.setText(International.trad("text.lInfo"));
				lInfo3.setText(International.trad("text.lInfo"));
				lInfo4.setText(International.trad("text.lInfo"));

				if (estChoisirPlace) {
					nomJoueur1.setStyle(tmpColor);
					nomJoueur1.setText(International.trad("text.choisir"));
					nbPerso1.setText("");
					nbCartes1.setText("");
					nomJoueur2.setStyle(tmpColor);
					nomJoueur2.setText(International.trad("text.choisir"));
					nbPerso2.setText("");
					nbCartes2.setText("");
					nomJoueur3.setStyle(tmpColor);
					nomJoueur3.setText(International.trad("text.choisir"));
					nbPerso3.setText("");
					nbCartes3.setText("");
					nomJoueur4.setStyle(tmpColor);
					nomJoueur4.setText(International.trad("text.choisir"));
					nbPerso4.setText("");
					nbCartes4.setText("");
					nomJoueur5.setStyle(tmpColor);
					nomJoueur5.setText(International.trad("text.choisir"));
					nbPerso5.setText("");
					nbCartes5.setText("");
					nomJoueur6.setStyle(tmpColor);
					nomJoueur6.setText(International.trad("text.choisir"));
					nbPerso6.setText("");
					nbCartes6.setText("");
				} else {
					nbPerso1.setText(persos.get(0) + " " + International.trad("text.perso.num"));
					nbPerso2.setText(persos.get(1) + " " + International.trad("text.perso.num"));
					nbPerso3.setText(persos.get(2) + " " + International.trad("text.perso.num"));
					nbPerso4.setText(persos.get(3) + " " + International.trad("text.perso.num"));
					nbPerso5.setText(persos.get(4) + " " + International.trad("text.perso.num"));
					nbPerso6.setText(persos.get(5) + " " + International.trad("text.perso.num"));
					nbCartes1.setText(cartess.get(0) + " " + International.trad("text.cartes"));
					nbCartes2.setText(cartess.get(1) + " " + International.trad("text.cartes"));
					nbCartes3.setText(cartess.get(2) + " " + International.trad("text.cartes"));
					nbCartes4.setText(cartess.get(3) + " " + International.trad("text.cartes"));
					nbCartes5.setText(cartess.get(4) + " " + International.trad("text.cartes"));
					nbCartes6.setText(cartess.get(5) + " " + International.trad("text.cartes"));
				}
				if (currentCDV != "")
					nomChefVigile(currentCDV);
				if (currentPioche != -1) 
					nbCartePiocheActuel(currentPioche);
			}
		});
	}

	/**
	 * Permet de savoir qui joue sur le plateau
	 * 
	 * @param le joueur qui joue
	 */
	@Override
	public void quiJoue(Couleur joueur) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < vboxs.size(); i++) {
					vboxs.get(i).setStyle(IhmOutils.color(couleurs1.get(i)));
				}

				int i = couleurs1.indexOf(joueur);
				vboxs.get(i).setStyle(IhmOutils.color(couleurs1.get(i))
						+ "; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3;");
			}
		});
	}

	/**
	 * Permet de connaitre les couleurs a placer
	 * 
	 * @param la liste des couleurs a placer
	 */
	@Override
	public void choiCouleur(List<Couleur> couleurs) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				couleurs1 = new ArrayList<>(couleurs);
				o = 0;
				getProchaineCouleur();
			}
		});
	}

	/**
	 * Permet de lancer la proc??dure pour que le joueur d'une couleur puisse choisir
	 * sa place.
	 */
	private void getProchaineCouleur() {
		System.out.println(o);
		System.out.println(couleurs1.get(o));
		System.out.println(couleurs1 == null);
		popup(International.trad("text.choisistable"), International.trad("text.thatis") + ""
				+ IhmOutils.getCouleurTrad(couleurs1.get(o)) + " " + International.trad("text.choisistablea"), true);
		VBox[] j = { j1, j2, j3, j4, j5, j6 };
		Label[] l = { nomJoueur1, nomJoueur2, nomJoueur3, nomJoueur4, nomJoueur5, nomJoueur6 };

		int vboxIndex = 0;
		for (VBox vBox : j) {
			int tmp = vboxIndex;
			if (!vboxs.contains(vBox)) {
				vBox.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						l[tmp].setText(International.trad("text.dejachoisis"));
						vboxs.add(vBox);
						index.add(tmp);
						if (o == couleurs1.size()) {
							estChoisirPlace = false;
							o = 0;
							resetCadreJoueur();
							visibleCadreInutile();
							afficherPlateau();
							core.getCj().setPlacerJoueur(true);
						} else
							getProchaineCouleur();
					}
				});
			} else {
				vBox.setDisable(true);
				vBox.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {

					}
				});
			}

			vboxIndex++;
		}
		o++;
	}

	/**
	 * Rend les boutons des cadres (joueur) non cliquable
	 */
	private void resetCadreJoueur() {
		VBox[] j = { j1, j2, j3, j4, j5, j6 };
		for (VBox vBox : j) {
			vBox.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {

				}
			});
		}
	}

	/**
	 * Enleve les vbox reli??s au carte inutile
	 */
	private void visibleCadreInutile() {
		VBox[] j = { j1, j2, j3, j4, j5, j6 };
		for (VBox vBox : j) {
			if (!vboxs.contains(vBox)) {
				vBox.setVisible(false);
			}
		}
	}

	/**
	 * Affiche le plateau du jeu
	 */
	private void afficherPlateau() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				lChefVigile.setVisible(true);
				lChefVigile2.setVisible(true);
				lChefVigile3.setVisible(true);
				lChefVigile4.setVisible(true);

				fondB1.setVisible(true);
				fondB2.setVisible(true);
				fondB3.setVisible(true);
				fondB4.setVisible(true);
				fondB5.setVisible(true);
				fondB6.setVisible(true);
				b1.setVisible(true);
				b2.setVisible(true);
				b3.setVisible(true);
				b4.setVisible(true);
				b5.setVisible(true);
				b6.setVisible(true);
				bPileCarte.setVisible(true);
			}
		});
	}

	/**
	 * Eneleve le plateau du jeu
	 */
	private void enleverPlateau() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				lChefVigile.setVisible(false);
				lChefVigile2.setVisible(false);
				lChefVigile3.setVisible(false);
				lChefVigile4.setVisible(false);

				fondB1.setVisible(false);
				fondB2.setVisible(false);
				fondB3.setVisible(false);
				fondB4.setVisible(false);
				fondB5.setVisible(false);
				fondB6.setVisible(false);
				b1.setVisible(false);
				b2.setVisible(false);
				b3.setVisible(false);
				b4.setVisible(false);
				b5.setVisible(false);
				b6.setVisible(false);
				bPileCarte.setVisible(false);
			}
		});
	}

	private void choixCouleurReset() {
		j1.setVisible(true);
		j2.setVisible(true);
		j3.setVisible(true);
		j4.setVisible(true);
		j5.setVisible(true);
		j6.setVisible(true);
		j1.setDisable(false);
		j2.setDisable(false);
		j3.setDisable(false);
		j4.setDisable(false);
		j5.setDisable(false);
		j6.setDisable(false);
		j1.setStyle(tmpColor);
		j2.setStyle(tmpColor);
		j3.setStyle(tmpColor);
		j4.setStyle(tmpColor);
		j5.setStyle(tmpColor);
		j6.setStyle(tmpColor);

		nomJoueur1.setStyle(tmpColor);
		nomJoueur1.setText(International.trad("text.choisir"));
		nbPerso1.setText("");
		nbCartes1.setText("");
		nomJoueur2.setStyle(tmpColor);
		nomJoueur2.setText(International.trad("text.choisir"));
		nbPerso2.setText("");
		nbCartes2.setText("");
		nomJoueur3.setStyle(tmpColor);
		nomJoueur3.setText(International.trad("text.choisir"));
		nbPerso3.setText("");
		nbCartes3.setText("");
		nomJoueur4.setStyle(tmpColor);
		nomJoueur4.setText(International.trad("text.choisir"));
		nbPerso4.setText("");
		nbCartes4.setText("");
		nomJoueur5.setStyle(tmpColor);
		nomJoueur5.setText(International.trad("text.choisir"));
		nbPerso5.setText("");
		nbCartes5.setText("");
		nomJoueur6.setStyle(tmpColor);
		nomJoueur6.setText(International.trad("text.choisir"));
		nbPerso6.setText("");
		nbCartes6.setText("");

		vboxs.clear();
		index.clear();
	}

	/**
	 * Met a jour le nombre actuel de carte dans la pioche
	 * 
	 * @param val le nombre de carte dans la pioche
	 */
	@Override
	public void nbCartePiocheActuel(int val) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentPioche = val;
				lPileCarte1.setText(val + " " + International.trad("text.pioche"));
				lPileCarte2.setText(val + " " + International.trad("text.pioche"));
				lPileCarte3.setText(val + " " + International.trad("text.pioche"));
				lPileCarte4.setText(val + " " + International.trad("text.pioche"));
			}
		});
	}

	@Override
	public void suppQuiJoue() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < vboxs.size(); i++) {
					vboxs.get(i).setStyle(IhmOutils.color(couleurs1.get(i)));
				}
			}
		});
	}
}
