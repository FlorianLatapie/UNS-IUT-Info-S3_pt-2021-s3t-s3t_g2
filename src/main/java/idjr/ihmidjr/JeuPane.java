package idjr.ihmidjr;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.JeuListener;
import idjr.ihmidjr.langues.International;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import reseau.type.CarteType;
import reseau.type.Couleur;

public class JeuPane extends StackPane implements JeuListener {

	private ScreenControl sControl = null;
	private Core core = null;
	private int hBouton = 100;
	private int lBouton = 200;
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);
	private Font policeLog = Font.font("Segoe UI", FontWeight.BOLD, 15);
	private Font policeBoutonC = Font.font("Segoe UI", FontWeight.BOLD, 28);
	private Font policeBoutonDe = Font.font("Segoe UI", FontWeight.BOLD, 40);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleVBox = "-fx-border-color: black; -fx-border-insets: 5; -fx-border-width: 3;";
	private CornerRadii coinfb = new CornerRadii(5.0);
	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));
	private Background fondNoir = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null));
	private int largBouton = 155;
	private int hautBouton = 70;
	private int lBoutonCamion = 180;
	private int hBoutonDeck = 60;
	private int lBoutonCamion2 = 145;
	private final ApplicationPane paneName = ApplicationPane.JEU;

	Label phasePartie;

	BorderPane info;
	Label titreInfo;
	Label lInfo;

	Button bToilettes;
	Button bCachou;
	Button bMegatoys;
	Button bParking;
	Button bPCSecu;
	Button bSuperMarche;

	Button bBlonde;
	Button bBrute;
	Button bTruand;
	Button bFillette;

	Button joueur1;
	Button joueur2;
	Button joueur3;
	Button joueur4;
	Button joueur5;

	Button joueur1c;
	Button joueur2c;
	Button joueur3c;
	Button joueur4c;
	Button joueur5c;

	Button bCarte1;
	Button bCarte2;
	Button bCarte3;

	Button bChoixGarder;
	Button bChoixDonner;
	Button bChoixDefausser;

	Label de1;
	Label de2;

	Label nomJoueur;

	ImageView imgFond;

	Label labDeplPers;

	ImageView imgCarte1;
	ImageView imgCarte2;
	ImageView imgCarte3;

	ImageView imgDeCarte1;
	ImageView imgDeCarte2;
	ImageView imgDeCarte3;
	ImageView imgDeCarte4;
	ImageView imgDeCarte5;
	ImageView imgDeCarte6;
	ImageView imgDeCarte7;
	ImageView imgDeCarte8;
	ImageView imgDeCarte9;

	Button bDeCarte1;
	Button bDeCarte2;
	Button bDeCarte3;
	Button bDeCarte4;
	Button bDeCarte5;
	Button bDeCarte6;
	Button bDeCarte7;
	Button bDeCarte8;
	Button bDeCarte9;
	Button bPasserCarte;

	VBox carte1;
	VBox carte2;
	VBox carte3;
	VBox carte4;
	VBox carte5;
	VBox carte6;
	VBox carte7;
	VBox carte8;
	VBox carte9;
	VBox passerCarte;

	BorderPane fouilleCamion;
	BorderPane vote;

	Button bLog;
	ListView<Label> log;

	Button bQuitterInfo;

	CarteType selectedCarte;
	Couleur selectedCouleur;
	HBox hboxBoutonJoueur;

	HBox infoZombie;
	Label linfoZombie;

	VBox des;

	public JeuPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);

		nomJoueur = new Label("Nom du joueur");
		nomJoueur.setAlignment(Pos.CENTER);
		nomJoueur.setTranslateY(-400);
		nomJoueur.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
		nomJoueur.setTextFill(Color.BLACK);

		phasePartie = new Label("Phase de la partie");
		phasePartie.setAlignment(Pos.CENTER);
		phasePartie.setTranslateY(-325);
		phasePartie.setFont(Font.font("Segoe UI", 50));
		phasePartie.setTextFill(Color.BLACK);

		//////////////////////////////////////////
		// TODO afficher une image dedans
		Rectangle rectVigile = new Rectangle();
		rectVigile.setTranslateX(793);
		rectVigile.setTranslateY(150);
		rectVigile.setFill(null);
		// rectVigile.setStroke(Color.BLACK);
		rectVigile.setStrokeWidth(3);
		rectVigile.setWidth(100);
		rectVigile.setHeight(80);

		rectVigile.setFill(new ImagePattern(new Image(DataControl.BADGE_VIGILE)));

		rectVigile.setVisible(false);// TODO event pour l'afficher au bon moment

		//////////////////////////////////////////
		HBox hbCartes = new HBox();
		hbCartes.setAlignment(Pos.CENTER);
		hbCartes.setTranslateY(340);
		hbCartes.setPrefSize(1800, 250);
		hbCartes.setMinSize(1800, 250);
		hbCartes.setMaxSize(1800, 250);
		hbCartes.setStyle(styleVBox);
		// hbCartes.setPadding(new Insets(0,10,0,10));
		hbCartes.setSpacing(20);

		carte1 = new VBox();
		carte1.setAlignment(Pos.CENTER);
		carte1.setSpacing(10);

		imgDeCarte1 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte1.setFitHeight(150);
		imgDeCarte1.setFitWidth(150);

		bDeCarte1 = new Button("Utiliser"); // TODO Event
		bDeCarte1.setAlignment(Pos.CENTER);
		bDeCarte1.setStyle(styleBoutons);
		bDeCarte1.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte1.setMinSize(largBouton, hBoutonDeck);
		bDeCarte1.setFont(policeBoutonC);
		bDeCarte1.setOnMouseEntered(event -> {
			bDeCarte1.setStyle(styleBoutonsSouris);
		});
		bDeCarte1.setOnMouseExited(event -> {
			bDeCarte1.setStyle(styleBoutons);
		});

		carte1.getChildren().addAll(imgDeCarte1, bDeCarte1);

		carte2 = new VBox();
		carte2.setAlignment(Pos.CENTER);
		carte2.setSpacing(10);

		imgDeCarte2 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte2.setFitHeight(150);
		imgDeCarte2.setFitWidth(150);

		bDeCarte2 = new Button("Utiliser"); // TODO Event
		bDeCarte2.setAlignment(Pos.CENTER);
		bDeCarte2.setStyle(styleBoutons);
		bDeCarte2.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte2.setMinSize(largBouton, hBoutonDeck);
		bDeCarte2.setFont(policeBoutonC);
		bDeCarte2.setOnMouseEntered(event -> {
			bDeCarte2.setStyle(styleBoutonsSouris);
		});
		bDeCarte2.setOnMouseExited(event -> {
			bDeCarte2.setStyle(styleBoutons);
		});

		carte2.getChildren().addAll(imgDeCarte2, bDeCarte2);

		carte3 = new VBox();
		carte3.setAlignment(Pos.CENTER);
		carte3.setSpacing(10);

		imgDeCarte3 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte3.setFitHeight(150);
		imgDeCarte3.setFitWidth(150);

		bDeCarte3 = new Button("Utiliser"); // TODO Event
		bDeCarte3.setAlignment(Pos.CENTER);
		bDeCarte3.setStyle(styleBoutons);
		bDeCarte3.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte3.setMinSize(largBouton, hBoutonDeck);
		bDeCarte3.setFont(policeBoutonC);
		bDeCarte3.setOnMouseEntered(event -> {
			bDeCarte3.setStyle(styleBoutonsSouris);
		});
		bDeCarte3.setOnMouseExited(event -> {
			bDeCarte3.setStyle(styleBoutons);
		});

		carte3.getChildren().addAll(imgDeCarte3, bDeCarte3);

		carte4 = new VBox();
		carte4.setAlignment(Pos.CENTER);
		carte4.setSpacing(10);

		imgDeCarte4 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte4.setFitHeight(150);
		imgDeCarte4.setFitWidth(150);

		bDeCarte4 = new Button("Utiliser"); // TODO Event
		bDeCarte4.setAlignment(Pos.CENTER);
		bDeCarte4.setStyle(styleBoutons);
		bDeCarte4.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte4.setMinSize(largBouton, hBoutonDeck);
		bDeCarte4.setFont(policeBoutonC);
		bDeCarte4.setOnMouseEntered(event -> {
			bDeCarte4.setStyle(styleBoutonsSouris);
		});
		bDeCarte4.setOnMouseExited(event -> {
			bDeCarte4.setStyle(styleBoutons);
		});

		carte4.getChildren().addAll(imgDeCarte4, bDeCarte4);

		carte5 = new VBox();
		carte5.setAlignment(Pos.CENTER);
		carte5.setSpacing(10);

		imgDeCarte5 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte5.setFitHeight(150);
		imgDeCarte5.setFitWidth(150);

		bDeCarte5 = new Button("Utiliser"); // TODO Event
		bDeCarte5.setAlignment(Pos.CENTER);
		bDeCarte5.setStyle(styleBoutons);
		bDeCarte5.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte5.setMinSize(largBouton, hBoutonDeck);
		bDeCarte5.setFont(policeBoutonC);
		bDeCarte5.setOnMouseEntered(event -> {
			bDeCarte5.setStyle(styleBoutonsSouris);
		});
		bDeCarte5.setOnMouseExited(event -> {
			bDeCarte5.setStyle(styleBoutons);
		});

		carte5.getChildren().addAll(imgDeCarte5, bDeCarte5);

		carte6 = new VBox();
		carte6.setAlignment(Pos.CENTER);
		carte6.setSpacing(10);

		imgDeCarte6 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte6.setFitHeight(150);
		imgDeCarte6.setFitWidth(150);

		bDeCarte6 = new Button("Utiliser"); // TODO Event
		bDeCarte6.setAlignment(Pos.CENTER);
		bDeCarte6.setStyle(styleBoutons);
		bDeCarte6.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte6.setMinSize(largBouton, hBoutonDeck);
		bDeCarte6.setFont(policeBoutonC);
		bDeCarte6.setOnMouseEntered(event -> {
			bDeCarte6.setStyle(styleBoutonsSouris);
		});
		bDeCarte6.setOnMouseExited(event -> {
			bDeCarte6.setStyle(styleBoutons);
		});

		carte6.getChildren().addAll(imgDeCarte6, bDeCarte6);

		carte7 = new VBox();
		carte7.setAlignment(Pos.CENTER);
		carte7.setSpacing(10);

		imgDeCarte7 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte7.setFitHeight(150);
		imgDeCarte7.setFitWidth(150);

		bDeCarte7 = new Button("Utiliser"); // TODO Event
		bDeCarte7.setAlignment(Pos.CENTER);
		bDeCarte7.setStyle(styleBoutons);
		bDeCarte7.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte7.setMinSize(largBouton, hBoutonDeck);
		bDeCarte7.setFont(policeBoutonC);
		bDeCarte7.setOnMouseEntered(event -> {
			bDeCarte7.setStyle(styleBoutonsSouris);
		});
		bDeCarte7.setOnMouseExited(event -> {
			bDeCarte7.setStyle(styleBoutons);
		});

		carte7.getChildren().addAll(imgDeCarte7, bDeCarte7);

		carte8 = new VBox();
		carte8.setAlignment(Pos.CENTER);
		carte8.setSpacing(10);

		imgDeCarte8 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte8.setFitHeight(150);
		imgDeCarte8.setFitWidth(150);

		bDeCarte8 = new Button("Utiliser"); // TODO Event
		bDeCarte8.setAlignment(Pos.CENTER);
		bDeCarte8.setStyle(styleBoutons);
		bDeCarte8.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte8.setMinSize(largBouton, hBoutonDeck);
		bDeCarte8.setFont(policeBoutonC);
		bDeCarte8.setOnMouseEntered(event -> {
			bDeCarte8.setStyle(styleBoutonsSouris);
		});
		bDeCarte8.setOnMouseExited(event -> {
			bDeCarte8.setStyle(styleBoutons);
		});

		carte8.getChildren().addAll(imgDeCarte8, bDeCarte8);

		carte9 = new VBox();
		carte9.setAlignment(Pos.CENTER);
		carte9.setSpacing(10);

		imgDeCarte9 = new ImageView(DataControl.CARTE_CS);
		imgDeCarte9.setFitHeight(150);
		imgDeCarte9.setFitWidth(150);

		bDeCarte9 = new Button("Utiliser"); // TODO Event
		bDeCarte9.setAlignment(Pos.CENTER);
		bDeCarte9.setStyle(styleBoutons);
		bDeCarte9.setPrefSize(largBouton, hBoutonDeck);
		bDeCarte9.setMinSize(largBouton, hBoutonDeck);
		bDeCarte9.setFont(policeBoutonC);
		bDeCarte9.setOnMouseEntered(event -> {
			bDeCarte9.setStyle(styleBoutonsSouris);
		});
		bDeCarte9.setOnMouseExited(event -> {
			bDeCarte9.setStyle(styleBoutons);
		});

		carte9.getChildren().addAll(imgDeCarte9, bDeCarte9);

		passerCarte = new VBox();
		passerCarte.setAlignment(Pos.CENTER);
		passerCarte.setSpacing(10);

		bPasserCarte = new Button("Passer\n\n Skip"); // TODO Event && rename field
		bPasserCarte.setAlignment(Pos.CENTER);
		bPasserCarte.setStyle(styleBoutons);
		bPasserCarte.setPrefSize(largBouton, largBouton);
		bPasserCarte.setMinSize(largBouton, largBouton);
		bPasserCarte.setFont(policeBoutonC);
		bPasserCarte.setOnMouseEntered(event -> {
			bPasserCarte.setStyle(styleBoutonsSouris);
		});
		bPasserCarte.setOnMouseExited(event -> {
			bPasserCarte.setStyle(styleBoutons);
		});

		passerCarte.getChildren().addAll(bPasserCarte);

		hbCartes.getChildren().addAll(carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9,
				passerCarte);

		//////////////////////////////////////////

		vote = new BorderPane();
		vote.setPrefSize(756, 376);
		vote.setMinSize(756, 376);
		vote.setMaxSize(756, 376);
		vote.setStyle(styleVBox);

		VBox vbVoteCentre = new VBox();
		vbVoteCentre.setAlignment(Pos.CENTER);
		vbVoteCentre.setStyle(styleVBox);

		HBox hbJoueurHaut = new HBox();
		hbJoueurHaut.setAlignment(Pos.TOP_CENTER);
		hbJoueurHaut.setSpacing(50);
		hbJoueurHaut.setPadding(new Insets(20));

		HBox hbJoueurBas = new HBox();
		hbJoueurBas.setAlignment(Pos.BOTTOM_CENTER);
		hbJoueurBas.setSpacing(50);
		hbJoueurBas.setPadding(new Insets(20));

		joueur1 = new Button(International.trad("texte.j1"));
		joueur1.setAlignment(Pos.CENTER);
		joueur1.setStyle(styleBoutons);
		joueur1.setPrefSize(lBouton, hBouton);
		joueur1.setMinSize(lBouton, hBouton);
		joueur1.setFont(policeBouton);
		joueur1.setOnMouseEntered(event -> {
			joueur1.setStyle(styleBoutonsSouris);
		});
		joueur1.setOnMouseExited(event -> {
			joueur1.setStyle(styleBoutons);
		});

		joueur2 = new Button(International.trad("texte.j2"));
		joueur2.setAlignment(Pos.CENTER);
		joueur2.setStyle(styleBoutons);
		joueur2.setPrefSize(lBouton, hBouton);
		joueur2.setMinSize(lBouton, hBouton);
		joueur2.setFont(policeBouton);
		joueur2.setOnMouseEntered(event -> {
			joueur2.setStyle(styleBoutonsSouris);
		});
		joueur2.setOnMouseExited(event -> {
			joueur2.setStyle(styleBoutons);
		});

		joueur3 = new Button(International.trad("texte.j3"));
		joueur3.setAlignment(Pos.CENTER);
		joueur3.setStyle(styleBoutons);
		joueur3.setPrefSize(lBouton, hBouton);
		joueur3.setMinSize(lBouton, hBouton);
		joueur3.setFont(policeBouton);
		joueur3.setOnMouseEntered(event -> {
			joueur3.setStyle(styleBoutonsSouris);
		});
		joueur3.setOnMouseExited(event -> {
			joueur3.setStyle(styleBoutons);
		});

		joueur4 = new Button(International.trad("texte.j4"));
		joueur4.setAlignment(Pos.CENTER);
		joueur4.setStyle(styleBoutons);
		joueur4.setPrefSize(lBouton, hBouton);
		joueur4.setMinSize(lBouton, hBouton);
		joueur4.setFont(policeBouton);
		joueur4.setOnMouseEntered(event -> {
			joueur4.setStyle(styleBoutonsSouris);
		});
		joueur4.setOnMouseExited(event -> {
			joueur4.setStyle(styleBoutons);
		});

		joueur5 = new Button(International.trad("texte.j5"));
		joueur5.setAlignment(Pos.CENTER);
		joueur5.setStyle(styleBoutons);
		joueur5.setPrefSize(lBouton, hBouton);
		joueur5.setMinSize(lBouton, hBouton);
		joueur5.setFont(policeBouton);
		joueur5.setOnMouseEntered(event -> {
			joueur5.setStyle(styleBoutonsSouris);
		});
		joueur5.setOnMouseExited(event -> {
			joueur5.setStyle(styleBoutons);
		});
		hbJoueurHaut.getChildren().addAll(joueur1, joueur2, joueur3);
		hbJoueurBas.getChildren().addAll(joueur4, joueur5);

		vbVoteCentre.getChildren().addAll(hbJoueurHaut, hbJoueurBas);

		Label titreVote = new Label("Vote");
		titreVote.setAlignment(Pos.CENTER);
		titreVote.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreVote.setTextFill(Color.WHITE);

		Label titreQuestion = new Label(International.trad("texte.qVote"));
		titreQuestion.setAlignment(Pos.CENTER);
		titreQuestion.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreQuestion.setTextFill(Color.WHITE);

		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.CENTER);
		vbTitre.setPrefHeight(80);
		vbTitre.setBackground(fondNoir);
		vbTitre.getChildren().addAll(titreVote, titreQuestion);

		vote.setTop(vbTitre);
		vote.setCenter(vbVoteCentre);
		// vote.setDisable(false);
		// vote.setVisible(false);

		///
		fouilleCamion = new BorderPane();
		fouilleCamion.setPrefSize(850, 550);
		fouilleCamion.setMinSize(850, 550);
		fouilleCamion.setMaxSize(850, 550);
		fouilleCamion.setStyle("-fx-border-color: black; -fx-border-insets: 5; -fx-border-width: 3;");

		VBox vbChoixCarteCentre = new VBox();
		vbChoixCarteCentre.setAlignment(Pos.CENTER);
		vbChoixCarteCentre.setPrefSize(850, 600);
		vbChoixCarteCentre.setMinSize(850, 600);
		vbChoixCarteCentre.setMaxSize(850, 600);

		VBox vbCarte = new VBox();
		vbCarte.setAlignment(Pos.CENTER);
		vbCarte.setPrefSize(780, 300);
		vbCarte.setMinSize(780, 300);
		vbCarte.setMaxSize(780, 300);

		VBox vbChoix = new VBox();
		vbChoix.setAlignment(Pos.CENTER);
		fouilleCamion.setAlignment(vbChoixCarteCentre, Pos.TOP_CENTER);

		HBox hboxImgCarte = new HBox();
		hboxImgCarte.setAlignment(Pos.CENTER);
		hboxImgCarte.setPrefSize(780, 255);
		hboxImgCarte.setMinSize(780, 255);
		hboxImgCarte.setMaxSize(780, 255);
		hboxImgCarte.setSpacing(10);
		// hboxImgCarte.setStyle("-fx-border-color: red; -fx-border-insets: 5;
		// -fx-border-width: 3;");

		HBox hboxBoutonCarte = new HBox();
		hboxBoutonCarte.setAlignment(Pos.CENTER);
		hboxBoutonCarte.setSpacing(20);
		hboxBoutonCarte.setPrefHeight(90);
		hboxBoutonCarte.setPadding(new Insets(10));

		HBox hboxBoutonChoix = new HBox();
		hboxBoutonChoix.setAlignment(Pos.CENTER);
		hboxBoutonChoix.setSpacing(20);
		hboxBoutonChoix.setPadding(new Insets(20));

		hboxBoutonJoueur = new HBox();
		hboxBoutonJoueur.setAlignment(Pos.CENTER);
		hboxBoutonJoueur.setSpacing(10);
		hboxBoutonJoueur.setPadding(new Insets(10));
		hboxBoutonJoueur.setVisible(false);
		//
		imgCarte1 = new ImageView(DataControl.CARTE_BATTE);
		imgCarte2 = new ImageView(DataControl.CARTE_BATTE);
		imgCarte3 = new ImageView(DataControl.CARTE_BATTE);

		hboxImgCarte.getChildren().addAll(imgCarte1, imgCarte2, imgCarte3);

		//
		bCarte1 = new Button(International.trad("texte.c1"));
		bCarte1.setAlignment(Pos.CENTER);
		bCarte1.setStyle(styleBoutons);
		bCarte1.setPrefSize(250, 60);
		bCarte1.setMinSize(250, 60);
		bCarte1.setFont(policeBoutonC);
		bCarte1.setOnMouseEntered(event -> {
			bCarte1.setStyle(styleBoutonsSouris);
		});
		bCarte1.setOnMouseExited(event -> {
			bCarte1.setStyle(styleBoutons);
		});

		bCarte2 = new Button(International.trad("texte.c2"));
		bCarte2.setAlignment(Pos.CENTER);
		bCarte2.setStyle(styleBoutons);
		bCarte2.setPrefSize(250, 60);
		bCarte2.setMinSize(250, 60);
		bCarte2.setFont(policeBoutonC);
		bCarte2.setOnMouseEntered(event -> {
			bCarte2.setStyle(styleBoutonsSouris);
		});
		bCarte2.setOnMouseExited(event -> {
			bCarte2.setStyle(styleBoutons);
		});

		bCarte3 = new Button(International.trad("texte.c3"));
		bCarte3.setAlignment(Pos.CENTER);
		bCarte3.setStyle(styleBoutons);
		bCarte3.setPrefSize(250, 60);
		bCarte3.setMinSize(250, 60);
		bCarte3.setFont(policeBoutonC);
		bCarte3.setOnMouseEntered(event -> {
			bCarte3.setStyle(styleBoutonsSouris);
		});
		bCarte3.setOnMouseExited(event -> {
			bCarte3.setStyle(styleBoutons);
		});

		hboxBoutonCarte.getChildren().addAll(bCarte1, bCarte2, bCarte3);
		vbCarte.getChildren().addAll(hboxImgCarte, hboxBoutonCarte);
		vbCarte.setMargin(vbChoix, new Insets(10));

		bChoixGarder = new Button(International.trad("bouton.garder"));
		bChoixGarder.setDisable(true);
		bChoixGarder.setAlignment(Pos.CENTER);
		bChoixGarder.setStyle(styleBoutons);
		bChoixGarder.setPrefSize(lBoutonCamion, hautBouton);
		bChoixGarder.setMinSize(lBoutonCamion, hautBouton);
		bChoixGarder.setFont(policeBoutonC);
		bChoixGarder.setOnMouseEntered(event -> {
			bChoixGarder.setStyle(styleBoutonsSouris);
		});
		bChoixGarder.setOnMouseExited(event -> {
			bChoixGarder.setStyle(styleBoutons);
		});
		bChoixGarder.setOnAction(EventHandler -> {
			if (selectedCarte != null) {
				core.getIdjr().setCarteChoisi(selectedCarte);
				core.getIdjr().carteChoisi(true);
				core.getIdjr().setEtatChoisi(International.trad("bouton.garder"));
				cartePanelReset();
			}
		});
		bChoixDonner = new Button(International.trad("bouton.donner"));
		bChoixDonner.setDisable(true);
		bChoixDonner.setAlignment(Pos.CENTER);
		bChoixDonner.setStyle(styleBoutons);
		bChoixDonner.setPrefSize(lBoutonCamion, hautBouton);
		bChoixDonner.setMinSize(lBoutonCamion, hautBouton);
		bChoixDonner.setFont(policeBoutonC);
		bChoixDonner.setOnMouseEntered(event -> {
			bChoixDonner.setStyle(styleBoutonsSouris);
		});
		bChoixDonner.setOnMouseExited(event -> {
			bChoixDonner.setStyle(styleBoutons);
		});
		bChoixDonner.setOnAction(EventHandler -> {
			fouilleCamion.setPrefSize(850, 650);
			fouilleCamion.setMinSize(850, 650);
			fouilleCamion.setMaxSize(850, 650);
			hboxBoutonJoueur.setVisible(true);
		});

		bChoixDefausser = new Button(International.trad("bouton.defausser"));
		bChoixDefausser.setDisable(true);
		bChoixDefausser.setAlignment(Pos.CENTER);
		bChoixDefausser.setStyle(styleBoutons);
		bChoixDefausser.setPrefSize(lBoutonCamion, hautBouton);
		bChoixDefausser.setMinSize(lBoutonCamion, hautBouton);
		bChoixDefausser.setFont(policeBoutonC);
		bChoixDefausser.setOnMouseEntered(event -> {
			bChoixDefausser.setStyle(styleBoutonsSouris);
		});
		bChoixDefausser.setOnMouseExited(event -> {
			bChoixDefausser.setStyle(styleBoutons);
		});
		bChoixDefausser.setOnAction(EventHandler -> {
			if (selectedCarte != null) {
				core.getIdjr().setCarteChoisi(selectedCarte);
				core.getIdjr().carteChoisi(true);
				core.getIdjr().setEtatChoisi(International.trad("bouton.defausser"));
				cartePanelReset();
			}
		});

		hboxBoutonChoix.getChildren().addAll(bChoixGarder, bChoixDonner, bChoixDefausser);

		joueur1c = new Button(International.trad("texte.j1"));
		joueur1c.setAlignment(Pos.CENTER);
		joueur1c.setStyle(styleBoutons);
		joueur1c.setPrefSize(lBoutonCamion2, hautBouton);
		joueur1c.setMinSize(lBoutonCamion2, hautBouton);
		joueur1c.setFont(policeBoutonC);
		joueur1c.setOnMouseEntered(event -> {
			joueur1c.setStyle(styleBoutonsSouris);
		});
		joueur1c.setOnMouseExited(event -> {
			joueur1c.setStyle(styleBoutons);
		});

		joueur2c = new Button(International.trad("texte.j2"));
		joueur2c.setAlignment(Pos.CENTER);
		joueur2c.setStyle(styleBoutons);
		joueur2c.setPrefSize(lBoutonCamion2, hautBouton);
		joueur2c.setMinSize(lBoutonCamion2, hautBouton);
		joueur2c.setFont(policeBoutonC);
		joueur2c.setOnMouseEntered(event -> {
			joueur2c.setStyle(styleBoutonsSouris);
		});
		joueur2c.setOnMouseExited(event -> {
			joueur2c.setStyle(styleBoutons);
		});

		joueur3c = new Button(International.trad("texte.j3"));
		joueur3c.setAlignment(Pos.CENTER);
		joueur3c.setStyle(styleBoutons);
		joueur3c.setPrefSize(lBoutonCamion2, hautBouton);
		joueur3c.setMinSize(lBoutonCamion2, hautBouton);
		joueur3c.setFont(policeBoutonC);
		joueur3c.setOnMouseEntered(event -> {
			joueur3c.setStyle(styleBoutonsSouris);
		});
		joueur3c.setOnMouseExited(event -> {
			joueur3c.setStyle(styleBoutons);
		});

		joueur4c = new Button(International.trad("texte.j4"));
		joueur4c.setAlignment(Pos.CENTER);
		joueur4c.setStyle(styleBoutons);
		joueur4c.setPrefSize(lBoutonCamion2, hautBouton);
		joueur4c.setMinSize(lBoutonCamion2, hautBouton);
		joueur4c.setFont(policeBoutonC);
		joueur4c.setOnMouseEntered(event -> {
			joueur4c.setStyle(styleBoutonsSouris);
		});
		joueur4c.setOnMouseExited(event -> {
			joueur4c.setStyle(styleBoutons);
		});

		joueur5c = new Button(International.trad("texte.j5"));
		joueur5c.setAlignment(Pos.CENTER);
		joueur5c.setStyle(styleBoutons);
		joueur5c.setPrefSize(lBoutonCamion2, hautBouton);
		joueur5c.setMinSize(lBoutonCamion2, hautBouton);
		joueur5c.setFont(policeBoutonC);
		joueur5c.setOnMouseEntered(event -> {
			joueur5c.setStyle(styleBoutonsSouris);
		});
		joueur5c.setOnMouseExited(event -> {
			joueur5c.setStyle(styleBoutons);
		});
		hboxBoutonJoueur.getChildren().addAll(joueur1c, joueur2c, joueur3c, joueur4c, joueur5c);
		vbChoix.getChildren().addAll(hboxBoutonChoix, hboxBoutonJoueur);
		vbChoixCarteCentre.getChildren().addAll(vbCarte, vbChoix);

		Label titreFouille = new Label(International.trad("text.fouilleCamion"));
		titreFouille.setAlignment(Pos.CENTER);
		titreFouille.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreFouille.setTextFill(Color.WHITE);

		Label titreQuestionCarte = new Label("Sélectionnez les cartes et faite vos choix"); // TODO ajouter ca dans le
																							// fichier de langues
		titreQuestionCarte.setAlignment(Pos.CENTER);
		titreQuestionCarte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreQuestionCarte.setTextFill(Color.WHITE);

		VBox vbTitreCamion = new VBox();
		vbTitreCamion.setAlignment(Pos.CENTER);
		vbTitreCamion.setPrefHeight(80);
		vbTitreCamion.setBackground(fondNoir);
		vbTitreCamion.getChildren().addAll(titreFouille, titreQuestionCarte);
		// vbTitreCamion.setStyle("-fx-border-color: yellow; -fx-border-insets: 5;
		// -fx-border-width: 3;");

		fouilleCamion.setTop(vbTitreCamion);
		fouilleCamion.setCenter(vbChoixCarteCentre);

		/*
		 * fond.setEffect(flou); TODO A mettre quand le popup fouille camion est activé
		 * rectVigile.setEffect(flou); nomJoueur.setEffect(flou);
		 * hbCartes.setEffect(flou); vbDeplCentre.setEffect(flou); des.setEffect(flou);
		 */

		///

		VBox vbDeplCentre = new VBox();
		vbDeplCentre.setAlignment(Pos.CENTER_LEFT);
		vbDeplCentre.setTranslateX(-700);
		vbDeplCentre.setTranslateY(-70);
		vbDeplCentre.setPrefSize(300, 500);
		vbDeplCentre.setMaxSize(300, 500);
		vbDeplCentre.setStyle(styleVBox);

		VBox vbDeplPers = new VBox();
		vbDeplPers.setAlignment(Pos.CENTER);
		vbDeplPers.setPrefSize(300, 500);
		vbDeplPers.setMaxSize(300, 500);
		vbDeplPers.setStyle(styleVBox);

		VBox vbTitre1 = new VBox();
		vbTitre1.setAlignment(Pos.CENTER);
		vbTitre1.setPrefHeight(50);
		vbTitre1.setMinHeight(50);
		vbTitre1.setMaxHeight(50);

		labDeplPers = new Label(International.trad("text.deplPersonnages"));
		labDeplPers.setAlignment(Pos.TOP_CENTER);
		labDeplPers.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		labDeplPers.setTextFill(Color.WHITE);

		VBox vbPersonnage = new VBox();
		vbPersonnage.setMinWidth(350);
		vbPersonnage.setMaxWidth(350);
		vbPersonnage.setAlignment(Pos.TOP_CENTER);

		HBox hbHaut = new HBox();
		hbHaut.setAlignment(Pos.TOP_CENTER);
		hbHaut.setSpacing(5);
		hbHaut.setPadding(new Insets(5));
		HBox hbBas = new HBox();
		hbBas.setAlignment(Pos.TOP_CENTER);
		hbBas.setSpacing(5);
		hbBas.setPadding(new Insets(5));

		bBlonde = new Button(International.trad("bouton.laBlonde"));
		bBlonde.setAlignment(Pos.CENTER);
		bBlonde.setStyle(styleBoutons);
		bBlonde.setPrefSize(largBouton, hautBouton);
		bBlonde.setMinSize(largBouton, hautBouton);
		bBlonde.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bBlonde.setOnMouseEntered(event -> {
			bBlonde.setStyle(styleBoutonsSouris);
		});
		bBlonde.setOnMouseExited(event -> {
			bBlonde.setStyle(styleBoutons);
		});

		bBrute = new Button(International.trad("bouton.laBrute"));
		bBrute.setAlignment(Pos.CENTER);
		bBrute.setStyle(styleBoutons);
		bBrute.setPrefSize(largBouton, hautBouton);
		bBrute.setMinSize(largBouton, hautBouton);
		bBrute.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bBrute.setOnMouseEntered(event -> {
			bBrute.setStyle(styleBoutonsSouris);
		});
		bBrute.setOnMouseExited(event -> {
			bBrute.setStyle(styleBoutons);
		});

		bTruand = new Button(International.trad("bouton.leTruand"));
		bTruand.setAlignment(Pos.CENTER);
		bTruand.setStyle(styleBoutons);
		bTruand.setPrefSize(largBouton, hautBouton);
		bTruand.setMinSize(largBouton, hautBouton);
		bTruand.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bTruand.setOnMouseEntered(event -> {
			bTruand.setStyle(styleBoutonsSouris);
		});
		bTruand.setOnMouseExited(event -> {
			bTruand.setStyle(styleBoutons);
		});

		bFillette = new Button(International.trad("bouton.laFillette"));
		bFillette.setAlignment(Pos.CENTER);
		bFillette.setStyle(styleBoutons);
		bFillette.setPrefSize(largBouton, hautBouton);
		bFillette.setMinSize(largBouton, hautBouton);
		bFillette.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bFillette.setOnMouseEntered(event -> {
			bFillette.setStyle(styleBoutonsSouris);
		});
		bFillette.setOnMouseExited(event -> {
			bFillette.setStyle(styleBoutons);
		});

		hbHaut.getChildren().addAll(bBlonde, bBrute);
		hbBas.getChildren().addAll(bTruand, bFillette);

		vbTitre1.getChildren().add(labDeplPers);
		vbTitre1.setBackground(fondNoir);
		vbPersonnage.getChildren().addAll(hbHaut, hbBas);
		vbDeplPers.getChildren().addAll(vbTitre1, vbPersonnage);

		/////////////////////////////////////////////////////////

		VBox vbDeplLieux = new VBox();
		vbDeplLieux.setAlignment(Pos.CENTER);
		vbDeplLieux.setPrefSize(300, 500);
		vbDeplLieux.setMaxSize(300, 500);
		vbDeplLieux.setStyle(styleVBox);

		VBox vbTitre2 = new VBox();
		vbTitre2.setAlignment(Pos.CENTER);
		vbTitre2.setPrefHeight(50);
		vbTitre2.setMinHeight(50);
		vbTitre2.setMaxHeight(50);

		Label labDeplLieux = new Label(International.trad("texte.destination"));
		labDeplLieux.setAlignment(Pos.TOP_CENTER);
		labDeplLieux.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		labDeplLieux.setTextFill(Color.WHITE);

		HBox hbLieux = new HBox();
		hbLieux.setMinWidth(350);
		hbLieux.setMaxWidth(350);
		hbLieux.setAlignment(Pos.TOP_CENTER);

		VBox vbGauche = new VBox();
		vbGauche.setAlignment(Pos.CENTER_LEFT);
		vbGauche.setSpacing(5);
		vbGauche.setPadding(new Insets(5));

		VBox vbDroite = new VBox();
		vbDroite.setAlignment(Pos.CENTER_RIGHT);
		vbDroite.setSpacing(5);
		vbDroite.setPadding(new Insets(5));

		bToilettes = new Button(International.trad("texte.lieu1"));
		bToilettes.setAlignment(Pos.CENTER);
		bToilettes.setStyle(styleBoutons);
		bToilettes.setPrefSize(largBouton, hautBouton);
		bToilettes.setMinSize(largBouton, hautBouton);
		bToilettes.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bToilettes.setOnMouseEntered(event -> {
			bToilettes.setStyle(styleBoutonsSouris);
		});
		bToilettes.setOnMouseExited(event -> {
			bToilettes.setStyle(styleBoutons);
		});

		bCachou = new Button(International.trad("texte.lieu2"));
		bCachou.setAlignment(Pos.CENTER);
		bCachou.setStyle(styleBoutons);
		bCachou.setPrefSize(largBouton, hautBouton);
		bCachou.setMinSize(largBouton, hautBouton);
		bCachou.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bCachou.setOnMouseEntered(event -> {
			bCachou.setStyle(styleBoutonsSouris);
		});
		bCachou.setOnMouseExited(event -> {
			bCachou.setStyle(styleBoutons);
		});

		bMegatoys = new Button(International.trad("texte.lieu3"));
		bMegatoys.setAlignment(Pos.CENTER);
		bMegatoys.setStyle(styleBoutons);
		bMegatoys.setPrefSize(largBouton, hautBouton);
		bMegatoys.setMinSize(largBouton, hautBouton);
		bMegatoys.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bMegatoys.setOnMouseEntered(event -> {
			bMegatoys.setStyle(styleBoutonsSouris);
		});
		bMegatoys.setOnMouseExited(event -> {
			bMegatoys.setStyle(styleBoutons);
		});

		bParking = new Button(International.trad("texte.lieu4"));
		bParking.setAlignment(Pos.CENTER);
		bParking.setStyle(styleBoutons);
		bParking.setPrefSize(largBouton, hautBouton);
		bParking.setMinSize(largBouton, hautBouton);
		bParking.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bParking.setOnMouseEntered(event -> {
			bParking.setStyle(styleBoutonsSouris);
		});
		bParking.setOnMouseExited(event -> {
			bParking.setStyle(styleBoutons);
		});

		bPCSecu = new Button(International.trad("texte.lieu5")+"\n"+International.trad("texte.lieu5b"));
		bPCSecu.setTextAlignment(TextAlignment.CENTER);
		bPCSecu.setAlignment(Pos.CENTER);
		bPCSecu.setStyle(styleBoutons);
		bPCSecu.setPrefSize(largBouton, hautBouton);
		bPCSecu.setMinSize(largBouton, hautBouton);
		bPCSecu.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bPCSecu.setOnMouseEntered(event -> {
			bPCSecu.setStyle(styleBoutonsSouris);
		});
		bPCSecu.setOnMouseExited(event -> {
			bPCSecu.setStyle(styleBoutons);
		});

		bSuperMarche = new Button(International.trad("texte.lieu6"));
		bSuperMarche.setAlignment(Pos.CENTER);
		bSuperMarche.setStyle(styleBoutons);
		bSuperMarche.setPrefSize(largBouton, hautBouton);
		bSuperMarche.setMinSize(largBouton, hautBouton);
		bSuperMarche.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		bSuperMarche.setOnMouseEntered(event -> {
			bSuperMarche.setStyle(styleBoutonsSouris);
		});
		bSuperMarche.setOnMouseExited(event -> {
			bSuperMarche.setStyle(styleBoutons);
		});

		vbGauche.getChildren().addAll(bToilettes, bMegatoys, bPCSecu);
		vbDroite.getChildren().addAll(bCachou, bParking, bSuperMarche);

		vbTitre2.getChildren().add(labDeplLieux);
		vbTitre2.setBackground(fondNoir);

		hbLieux.getChildren().addAll(vbGauche, vbDroite);
		vbDeplLieux.getChildren().addAll(vbTitre2, hbLieux);

		vbDeplCentre.getChildren().addAll(vbDeplPers, vbDeplLieux);
		vbDeplCentre.setDisable(false);

		des = new VBox();
		des.setTranslateX(790);
		des.setTranslateY(-100);
		des.setAlignment(Pos.CENTER);
		des.setStyle(styleVBox);
		des.setMaxSize(120, 275);

		de1 = new Label("0");
		de1.setBackground(fondBlanc);
		de1.setAlignment(Pos.CENTER);
		de1.setMinSize(100, 100);
		de1.setTextFill(Color.BLACK);
		de1.setFont(policeBoutonDe);

		de2 = new Label("0");
		de2.setBackground(fondBlanc);
		de2.setAlignment(Pos.CENTER);
		de2.setMinSize(100, 100);
		de2.setTextFill(Color.BLACK);
		de2.setFont(policeBoutonDe);

		Label titrede = new Label(International.trad("texte.des"));
		titrede.setMinSize(100, 50);
		titrede.setAlignment(Pos.CENTER);
		titrede.setTextFill(Color.WHITE);
		titrede.setFont(policeBouton);
		titrede.setStyle("-fx-background-radius: 3px;-fx-background-color:#000000;");

		des.setPadding(new Insets(10));
		des.setSpacing(10);
		des.getChildren().addAll(de1, de2, titrede);
		des.setDisable(false); // TODO ne pas oublier de le retier

		info = new BorderPane();
		info.setPrefSize(1000, 200);
		info.setMaxSize(1000, 200);
		info.setPadding(new Insets(10));
		info.setStyle(
				" -fx-background-color:#1A1A1A; -fx-background-radius: 20px; -fx-border-color: red; -fx-border-insets: -3; -fx-border-width: 3; -fx-border-radius: 20px;");
		info.setVisible(false);

		bQuitterInfo = new Button(International.trad("bouton.quitter"));
		bQuitterInfo.setAlignment(Pos.CENTER);
		bQuitterInfo.setStyle(styleBoutons);
		bQuitterInfo.setPrefSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo.setMinSize(largBouton * .7, hautBouton * .7);
		bQuitterInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		bQuitterInfo.setOnAction(event -> {
			info.setVisible(false);
		});
		bQuitterInfo.setOnMouseEntered(event -> {
			bQuitterInfo.setStyle(styleBoutonsSouris);
		});
		bQuitterInfo.setOnMouseExited(event -> {
			bQuitterInfo.setStyle(styleBoutons);
		});

		VBox vTitreInfo = new VBox();
		vTitreInfo.setAlignment(Pos.CENTER);
		vTitreInfo.setPadding(new Insets(20));
		titreInfo = new Label("Information");
		titreInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
		titreInfo.setTextFill(Color.WHITE);
		vTitreInfo.getChildren().addAll(titreInfo);

		VBox vInfo = new VBox();
		vInfo.setAlignment(Pos.CENTER);
		lInfo = new Label("Voici l'information que vous voulez savoir");
		lInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		lInfo.setTextFill(Color.WHITE);
		vInfo.getChildren().addAll(lInfo);

		info.setMargin(vInfo, new Insets(-30, 0, 0, 0));
		info.setTop(vTitreInfo);
		info.setCenter(vInfo);
		info.setBottom(bQuitterInfo);
		info.setVisible(false);

		imgFond = new ImageView(DataControl.BLEU);

		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		log = new ListView<>();
		log.setStyle(
				"-fx-background-color: red; -fx-control-inner-background: #1A1A1A ; -fx-control-inner-background-alt: derive(-fx-control-inner-background, 15%);");
		log.setEditable(true);
		log.setMinSize(600, 800);
		log.setMaxSize(600, 800);
		log.setPrefSize(600, 800);
		log.setVisible(false);

		bLog = new Button("LOG");
		bLog.setAlignment(Pos.CENTER);
		bLog.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
		bLog.setStyle(styleBoutons);
		bLog.setPrefSize(largBouton, hautBouton);
		bLog.setMinSize(largBouton, hautBouton);
		bLog.setOnAction(event -> {
			if (!log.isVisible())
				log.setVisible(true);
			else
				log.setVisible(false);
		});
		bLog.setOnMouseEntered(event -> {
			bLog.setStyle(styleBoutonsSouris);
		});
		bLog.setOnMouseExited(event -> {
			bLog.setStyle(styleBoutons);
		});
		bLog.setTranslateX(790);
		bLog.setTranslateY(-350);

		Label lo = new Label(International.trad("texte.nomPerso") + International.trad("texte.coulPerso")
				+ International.trad(("texte.depLieu")));
		lo.setFont(policeLog);
		updateLog(log, lo);

		infoZombie = new HBox(); // TODO .setVisible() aux bons moments
		infoZombie.setPrefSize(450, 70);
		infoZombie.setMinSize(450, 70);
		infoZombie.setMaxSize(450, 70);
		infoZombie.setTranslateX(-675);
		infoZombie.setTranslateY(-400);
		infoZombie.setAlignment(Pos.CENTER);
		infoZombie.setStyle(styleVBox);

		linfoZombie = new Label("Des zombies arriveront dans les lieux X, X, X, X"); // TODO afficher ca dans un event
		linfoZombie.setFont(Font.font("Segoe UI", 20));
		linfoZombie.setTextFill(Color.BLACK);

		infoZombie.getChildren().addAll(linfoZombie);

		stackPane.getChildren().addAll(fond, rectVigile, nomJoueur, phasePartie, hbCartes, vote, vbDeplCentre, des,
				infoZombie, fouilleCamion, info, log, bLog);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

		bBlonde.setOnAction(EventHandler -> {
			core.getIdjr().setPionChoisi(7);
			core.getIdjr().pionChoisi(true);
			bBlonde.setDisable(true);
			bBrute.setDisable(true);
			bTruand.setDisable(true);
			bFillette.setDisable(true);
		});
		bBrute.setOnAction(EventHandler -> {
			core.getIdjr().setPionChoisi(5);
			core.getIdjr().pionChoisi(true);
			bBlonde.setDisable(true);
			bBrute.setDisable(true);
			bTruand.setDisable(true);
			bFillette.setDisable(true);
		});
		bTruand.setOnAction(EventHandler -> {
			core.getIdjr().setPionChoisi(3);
			core.getIdjr().pionChoisi(true);
			bBlonde.setDisable(true);
			bBrute.setDisable(true);
			bTruand.setDisable(true);
			bFillette.setDisable(true);
		});
		bFillette.setOnAction(EventHandler -> {
			core.getIdjr().setPionChoisi(1);
			core.getIdjr().pionChoisi(true);
			bBlonde.setDisable(true);
			bBrute.setDisable(true);
			bTruand.setDisable(true);
			bFillette.setDisable(true);
		});

		bToilettes.setOnAction(EventHandler -> {
			core.getIdjr().setLieuChoisi(1);
			core.getIdjr().lieuChoisi(true);
			bToilettes.setDisable(true);
			bCachou.setDisable(true);
			bMegatoys.setDisable(true);
			bParking.setDisable(true);
			bPCSecu.setDisable(true);
			bSuperMarche.setDisable(true);
		});
		bCachou.setOnAction(EventHandler -> {
			core.getIdjr().setLieuChoisi(2);
			core.getIdjr().lieuChoisi(true);
			bToilettes.setDisable(true);
			bCachou.setDisable(true);
			bMegatoys.setDisable(true);
			bParking.setDisable(true);
			bPCSecu.setDisable(true);
			bSuperMarche.setDisable(true);
		});
		bMegatoys.setOnAction(EventHandler -> {
			core.getIdjr().setLieuChoisi(3);
			core.getIdjr().lieuChoisi(true);
			bToilettes.setDisable(true);
			bCachou.setDisable(true);
			bMegatoys.setDisable(true);
			bParking.setDisable(true);
			bPCSecu.setDisable(true);
			bSuperMarche.setDisable(true);
		});
		bParking.setOnAction(EventHandler -> {
			core.getIdjr().setLieuChoisi(4);
			core.getIdjr().lieuChoisi(true);
			bToilettes.setDisable(true);
			bCachou.setDisable(true);
			bMegatoys.setDisable(true);
			bParking.setDisable(true);
			bPCSecu.setDisable(true);
			bSuperMarche.setDisable(true);
		});
		bPCSecu.setOnAction(EventHandler -> {
			core.getIdjr().setLieuChoisi(5);
			core.getIdjr().lieuChoisi(true);
			bToilettes.setDisable(true);
			bCachou.setDisable(true);
			bMegatoys.setDisable(true);
			bParking.setDisable(true);
			bPCSecu.setDisable(true);
			bSuperMarche.setDisable(true);
		});
		bSuperMarche.setOnAction(EventHandler -> {
			core.getIdjr().setLieuChoisi(6);
			core.getIdjr().lieuChoisi(true);
			bToilettes.setDisable(true);
			bCachou.setDisable(true);
			bMegatoys.setDisable(true);
			bParking.setDisable(true);
			bPCSecu.setDisable(true);
			bSuperMarche.setDisable(true);
		});

		bBlonde.setDisable(true);
		bBrute.setDisable(true);
		bTruand.setDisable(true);
		bFillette.setDisable(true);

		bToilettes.setDisable(true);
		bCachou.setDisable(true);
		bMegatoys.setDisable(true);
		bParking.setDisable(true);
		bPCSecu.setDisable(true);
		bSuperMarche.setDisable(true);

		info.setVisible(false);

		joueur1.setDisable(true);
		joueur2.setDisable(true);
		joueur3.setDisable(true);
		joueur4.setDisable(true);
		joueur5.setDisable(true);
		cartePanelReset();
		fouilleCamion.setVisible(false);

		ImageView[] imgViews = { imgDeCarte1, imgDeCarte2, imgDeCarte3, imgDeCarte4, imgDeCarte5, imgDeCarte6,
				imgDeCarte7, imgDeCarte8, imgDeCarte9 };
		Button[] buttons = { bDeCarte1, bDeCarte2, bDeCarte3, bDeCarte4, bDeCarte5, bDeCarte6, bDeCarte7, bDeCarte8,
				bDeCarte9, bPasserCarte };
		VBox[] carteVbox = { carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9, passerCarte };

		for (VBox vBox : carteVbox)
			vBox.setVisible(false);
		for (ImageView imageView : imgViews)
			imageView.setDisable(true);
		for (Button button : buttons)
			button.setDisable(true);

		vote.setVisible(false);
	}

	//TODO

	public ListView<Label> updateLog(ListView<Label> list, Label l) {
		list.getItems().add(list.getItems().size(), l);
		list.scrollTo(list.getItems().size() - 1);
		return list;
	}

	@Override
	public void choisirPion(List<Integer> list) {
		Platform.runLater(() -> {
			for (Integer integer : list) {
				switch (integer) {
				case 7:
					bBlonde.setDisable(false);
					break;
				case 5:
					bBrute.setDisable(false);
					break;
				case 3:
					bTruand.setDisable(false);
					break;
				case 1:
					bFillette.setDisable(false);
					break;
				default:
					break;
				}
			}
		});

	}

	@Override
	public void choisirLieu(List<Integer> list) {
		Platform.runLater(() -> {
			for (Integer integer : list) {
				switch (integer) {
				case 1:
					bToilettes.setDisable(false);
					break;
				case 2:
					bCachou.setDisable(false);
					break;
				case 3:
					bMegatoys.setDisable(false);
					break;
				case 4:
					bParking.setDisable(false);
					break;
				case 5:
					bPCSecu.setDisable(false);
					break;
				case 6:
					bSuperMarche.setDisable(false);
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void desValeur(List<Integer> list) {
		Platform.runLater(() -> {
			String de1Int = list.get(0).toString();
			String de2Int = list.get(1).toString();
			de1.setText(de1Int);
			de2.setText(de2Int);
		});
	}

	@Override
	public void nomJoueur(String nom) {
		Platform.runLater(() -> {
			nomJoueur.setText(nom);
		});
	}

	@Override
	public void nomPhase(String nom) {
		Platform.runLater(() -> {
			phasePartie.setText(nom);
		});
	}

	@Override
	public void desVigiles(List<Integer> list) {
		Platform.runLater(() -> {
			linfoZombie.setText("Des zombies ont été placé dans les lieu numero" + list.get(0) + ", " + list.get(1) + ", " + list.get(2)
					+ " et " + list.get(3));
		});
	}

	@Override
	public void fin() {
		Platform.runLater(() -> {
			sControl.setPaneOnTop(ApplicationPane.ENDGAME);
		});
	}

	@Override
	public void couleurJoueur(Couleur couleur) {
		Platform.runLater(() -> {
			switch (couleur) {
			case B:
				imgFond.setImage(new Image(DataControl.BLEU));
				break;
			case R:
				imgFond.setImage(new Image(DataControl.ROUGE));
				break;
			case V:
				imgFond.setImage(new Image(DataControl.VERT));
				break;
			case J:
				imgFond.setImage(new Image(DataControl.JAUNE));
				break;
			case M:
				imgFond.setImage(new Image(DataControl.MARRON));
				break;
			case N:
				imgFond.setImage(new Image(DataControl.NOIR));
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void sacrificeChange() {
		Platform.runLater(() -> {
			labDeplPers.setText("Sacrifice d'un personnage");
		});
	}

	@Override
	public void deplacementChange() {
		Platform.runLater(() -> {
			labDeplPers.setText("Déplacement des personnages");
		});
	}

	public String convertCarte(CarteType carteType) {
		switch (carteType) {
		case ABA:
			return DataControl.CARTE_BATTE;
		case CAC:
			return DataControl.CARTE_CACHETTE;
		case CDS:
			return DataControl.CARTE_CAMERA;
		case ACS:
			return DataControl.CARTE_CS;
		case AGR:
			return DataControl.CARTE_GRENADE;
		case AHI:
			return DataControl.CARTE_HACHE;
		case MAT:
			return DataControl.CARTE_MATERIEL;
		case MEN:
			return DataControl.CARTE_MENACE;
		case ARE:
			return DataControl.CARTE_REVOLVER;
		case SPR:
			return DataControl.CARTE_SPRINT;
		case ATR:
			return DataControl.CARTE_TRONCENNEUSE;
		}

		return null;
	}

	public CarteType convertCarte(String dataControl) {
		switch (dataControl) {
		case DataControl.CARTE_BATTE:
			return CarteType.ABA;
		case DataControl.CARTE_CACHETTE:
			return CarteType.CAC;
		case DataControl.CARTE_CAMERA:
			return CarteType.CDS;
		case DataControl.CARTE_CS:
			return CarteType.ACS;
		case DataControl.CARTE_GRENADE:
			return CarteType.AGR;
		case DataControl.CARTE_HACHE:
			return CarteType.AHI;
		case DataControl.CARTE_MATERIEL:
			return CarteType.MAT;
		case DataControl.CARTE_MENACE:
			return CarteType.MEN;
		case DataControl.CARTE_REVOLVER:
			return CarteType.ARE;
		case DataControl.CARTE_SPRINT:
			return CarteType.SPR;
		case DataControl.CARTE_TRONCENNEUSE:
			return CarteType.ATR;
		}

		return CarteType.NUL;
	}

	public void setCarteOfferte(List<Couleur> listeCouleurJoueurVivant) {
		Button[] joueursButton = { joueur1c, joueur2c, joueur3c, joueur4c, joueur5c };
		for (int i = 0; i < joueursButton.length; i++) {
			if (listeCouleurJoueurVivant.size() > i) {
				joueursButton[i].setDisable(false);
				joueursButton[i].setText(listeCouleurJoueurVivant.get(i).nomEntier());
				Couleur tmpCouleur = listeCouleurJoueurVivant.get(i);
				joueursButton[i].setOnAction(EventHandler -> {
					selectedCouleur = tmpCouleur;
					if (selectedCouleur != null && selectedCarte != null) {
						core.getIdjr().setCarteChoisi(selectedCarte);
						core.getIdjr().setCouleurChoisi(selectedCouleur);
						core.getIdjr().setEtatChoisi("Donner");
						core.getIdjr().carteChoisi(true);
						cartePanelReset();
					}
				});
			} else {
				joueursButton[i].setText("");
			}
		}
	}

	@Override
	public void choisirCarte(List<CarteType> listeCartes, List<Couleur> listeCouleurJoueurVivant, boolean garder,
			boolean donner, boolean defausser, boolean utiliser) {
		Platform.runLater(() -> {
			Background selecBackground = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, null));
			Background deselecBackground = fondNoir;
			vote.setVisible(false);
			System.out.println(listeCartes.size());
			System.out.println(listeCouleurJoueurVivant.size());
			fouilleCamion.setVisible(true);
			if (listeCartes.size() >= 1) {
				imgCarte1.setImage(new Image(convertCarte(listeCartes.get(0))));
				bCarte1.setDisable(false);
				bCarte1.setText("Selectionner");
				bCarte1.setOnAction(EventHandler -> {
					selectedCarte = listeCartes.get(0);
					bChoixGarder.setDisable(!garder);
					bChoixDonner.setDisable(!donner);
					bChoixDefausser.setDisable(!defausser);
					bCarte1.setBackground(selecBackground);
					bCarte2.setBackground(fondNoir);
					bCarte3.setBackground(fondNoir);
				});
			} else {
				imgCarte1.setDisable(true);
				imgCarte1.setImage(new Image(DataControl.CARTE_VIDE));
			}
			if (listeCartes.size() >= 2) {
				imgCarte2.setImage(new Image(convertCarte(listeCartes.get(1))));
				bCarte2.setDisable(false);
				bCarte2.setText("Selectionner");
				bCarte2.setOnAction(EventHandler -> {
					selectedCarte = listeCartes.get(1);
					bChoixGarder.setDisable(!garder);
					bChoixDonner.setDisable(!donner);
					bChoixDefausser.setDisable(!defausser);
					bCarte1.setBackground(fondNoir);
					bCarte2.setBackground(selecBackground);
					bCarte3.setBackground(fondNoir);
				});
			} else {
				imgCarte2.setDisable(true);
				imgCarte2.setImage(new Image(DataControl.CARTE_VIDE));
			}
			if (listeCartes.size() >= 3) {
				imgCarte3.setImage(new Image(convertCarte(listeCartes.get(2))));
				bCarte3.setDisable(false);
				bCarte3.setText("Selectionner");
				bCarte3.setOnAction(EventHandler -> {
					selectedCarte = listeCartes.get(2);
					bChoixGarder.setDisable(!garder);
					bChoixDonner.setDisable(!donner);
					bChoixDefausser.setDisable(!defausser);
					bCarte1.setBackground(fondNoir);
					bCarte2.setBackground(fondNoir);
					bCarte3.setBackground(selecBackground);
				});
			} else {
				imgCarte3.setDisable(true);
				imgCarte3.setImage(new Image(DataControl.CARTE_VIDE));
			}
			if (listeCouleurJoueurVivant != null)
				setCarteOfferte(listeCouleurJoueurVivant);
		});

	}

	@Override
	public void nomJoueurs(List<String> listeNomJoueur) {
		Platform.runLater(() -> {
			Button[] joueursButton = { joueur1, joueur2, joueur3, joueur4, joueur5 };
			for (int i = 0; i < listeNomJoueur.size(); i++)
				joueursButton[i].setText(listeNomJoueur.get(i));
		});
	}

	public void cartePanelReset() {
		fouilleCamion.setVisible(false);
		hboxBoutonJoueur.setVisible(false);
		joueur1c.setDisable(true);
		joueur2c.setDisable(true);
		joueur3c.setDisable(true);
		joueur4c.setDisable(true);
		joueur5c.setDisable(true);
		bCarte1.setDisable(true);
		bCarte2.setDisable(true);
		bCarte3.setDisable(true);
		bChoixGarder.setDisable(true);
		bChoixDonner.setDisable(true);
		bChoixDefausser.setDisable(true);
		selectedCarte = null;
		selectedCouleur = null;
	}

	@Override
	public void updateCarte() {
		Platform.runLater(() -> {
			ImageView[] imgViews = { imgDeCarte1, imgDeCarte2, imgDeCarte3, imgDeCarte4, imgDeCarte5, imgDeCarte6,
					imgDeCarte7, imgDeCarte8, imgDeCarte9 };
			Button[] buttons = { bDeCarte1, bDeCarte2, bDeCarte3, bDeCarte4, bDeCarte5, bDeCarte6, bDeCarte7, bDeCarte8,
					bDeCarte9 };
			VBox[] carteVbox = { carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9 };
			for (int i = 0; i < 9; i++) {
				if (i < core.getIdjr().getListeCarte().size()) {
					imgViews[i].setImage(new Image(convertCarte(core.getIdjr().getListeCarte().get(i))));
					carteVbox[i].setVisible(true);
					imgViews[i].setDisable(false);
					CarteType type = core.getIdjr().getListeCarte().get(i);
					buttons[i].setOnAction(EventHandler -> {
						core.getIdjr().choisirUtiliserCarte(type);
						core.getIdjr().utiliserCarteChoisi(true);
						resetUtiliserCarte();
					});
				} else {
					imgViews[i].setImage(new Image(DataControl.CARTE_VIDE));
					carteVbox[i].setVisible(false);
					imgViews[i].setDisable(true);
				}
			}
		});
	}

	public void resetUtiliserCarte() {
		Button[] buttons = { bDeCarte1, bDeCarte2, bDeCarte3, bDeCarte4, bDeCarte5, bDeCarte6, bDeCarte7, bDeCarte8,
				bDeCarte9, bPasserCarte };
		for (int i = 0; i < 10; i++) {
			if (i < core.getIdjr().getListeCarte().size()) {
				buttons[i].setDisable(true);
			}
		}
		passerCarte.setVisible(false);
		passerCarte.setDisable(true);
		bPasserCarte.setDisable(true);
	}

	public void resetVoteCarte() {
		vote.setVisible(false);
		Button[] buttons = { joueur1, joueur2, joueur3, joueur4, joueur5 };
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setDisable(true);
		}
	}

	@Override
	public void setVote(List<Couleur> listeCouleurJoueur) {
		Platform.runLater(() -> {
			vote.setVisible(true);
			fouilleCamion.setVisible(false);
			Button[] buttons = { joueur1, joueur2, joueur3, joueur4, joueur5 };
			for (int i = 0; i < buttons.length; i++) {
				if (listeCouleurJoueur.size() > i) {
					buttons[i].setDisable(false);
					buttons[i].setText(listeCouleurJoueur.get(i).nomEntier());
					Couleur cible = listeCouleurJoueur.get(i);
					buttons[i].setOnAction(EventHandler -> {
						core.getIdjr().setVoteChoisi(cible);
						core.getIdjr().voteChoisi(true);
						resetVoteCarte();
					});
				} else {
					buttons[i].setText("");
				}
			}
		});
	}

	@Override
	public void choisirUtiliserCarte() {
		Platform.runLater(() -> {
			Button[] buttons = { bDeCarte1, bDeCarte2, bDeCarte3, bDeCarte4, bDeCarte5, bDeCarte6, bDeCarte7, bDeCarte8,
					bDeCarte9 };
			for (int i = 0; i < 9; i++) {
				if (i < core.getIdjr().getListeCarte().size()) {
					buttons[i].setDisable(false);
				}
			}

			passerCarte.setVisible(true);
			passerCarte.setDisable(false);
			bPasserCarte.setDisable(false);
			bPasserCarte.setOnAction(EventHandler -> {
				core.getIdjr().setContinue(false);
				core.getIdjr().utiliserCarteChoisi(true);
				resetUtiliserCarte();
			});
		});
	}

	@Override
	public void choisirUtiliserCarte(CarteType carteType) {
		Platform.runLater(() -> {
			Button[] buttons = { bDeCarte1, bDeCarte2, bDeCarte3, bDeCarte4, bDeCarte5, bDeCarte6, bDeCarte7, bDeCarte8,
					bDeCarte9 };
			for (int i = 0; i < 9; i++) {
				if (i < core.getIdjr().getListeCarte().size()) {
					if (carteType == core.getIdjr().getListeCarte().get(i))
						buttons[i].setDisable(false);
				}
			}

			passerCarte.setVisible(true);
			passerCarte.setDisable(false);
			bPasserCarte.setDisable(false);
			bPasserCarte.setOnAction(EventHandler -> {
				core.getIdjr().setContinue(false);
				core.getIdjr().utiliserCarteChoisi(true);
				resetUtiliserCarte();
			});
		});
	}

	@Override
	public void choisirUtiliserCarte(List<CarteType> carteTypes) {
		Platform.runLater(() -> {
			Button[] buttons = { bDeCarte1, bDeCarte2, bDeCarte3, bDeCarte4, bDeCarte5, bDeCarte6, bDeCarte7, bDeCarte8,
					bDeCarte9 };
			for (int i = 0; i < 9; i++) {
				if (i < core.getIdjr().getListeCarte().size()) {
					for (CarteType carteType2 : carteTypes) {
						if (carteType2 == core.getIdjr().getListeCarte().get(i))
							buttons[i].setDisable(false);
					}
				}
			}
			passerCarte.setVisible(true);
			passerCarte.setDisable(false);
			bPasserCarte.setDisable(false);
			bPasserCarte.setOnAction(EventHandler -> {
				core.getIdjr().setContinue(false);
				core.getIdjr().utiliserCarteChoisi(true);
				resetUtiliserCarte();
			});
		});
	}

	@Override
	public void log(String action) {
		updateLog(log, new Label(action));
	}

	@Override
	public void enleverDes() {
		des.setVisible(false);
	}
}
