package idjr.ihmidjr;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.JeuListener;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import reseau.type.CarteType;
import reseau.type.Couleur;

public class JeuPane extends StackPane implements JeuListener {

	private ScreenControl sControl = null;
	private Core core = null;
	private int hBouton = 100;
	private int lBouton = 200;
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);
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
	private int hBoutonCamion = 70;
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
	Button bChoixUtiliser;

	Label de1;
	Label de2;

	Label nomJoueur;

	ImageView imgFond;

	Label labDeplPers;

	ImageView imgCarte1;
	ImageView imgCarte2;
	ImageView imgCarte3;

	BorderPane fouilleCamion;

	CarteType selectedCarte;
	Couleur selectedCouleur;
	HBox hboxBoutonJoueur;

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
		rectVigile.setStroke(Color.BLACK);
		rectVigile.setStrokeWidth(3);
		rectVigile.setWidth(200);
		rectVigile.setHeight(120);

		//////////////////////////////////////////
		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.BOTTOM_CENTER);
		vbCentral.setTranslateY(340);
		vbCentral.setPrefSize(1800, 250);
		vbCentral.setMinSize(1800, 250);
		vbCentral.setMaxSize(1800, 250);
		vbCentral.setStyle(styleVBox);

		BorderPane vote = new BorderPane();
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

		joueur1 = new Button("Joueur1");
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

		joueur2 = new Button("Joueur2");
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

		joueur3 = new Button("Joueur3");
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

		joueur4 = new Button("Joueur4");
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

		joueur5 = new Button("Joueur5");
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

		Label titreQuestion = new Label("Pour qui voulez vous voter ?");
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
		vote.setDisable(false);
		vote.setVisible(false);

		///
		fouilleCamion = new BorderPane();
		fouilleCamion.setPrefSize(850, 720);
		fouilleCamion.setMinSize(850, 720);
		fouilleCamion.setMaxSize(850, 720);
		fouilleCamion.setStyle("-fx-border-color: black; -fx-border-insets: 5; -fx-border-width: 3;");

		VBox vbChoixCarteCentre = new VBox();
		vbChoixCarteCentre.setAlignment(Pos.CENTER);
		vbChoixCarteCentre.setPrefSize(850, 700);
		vbChoixCarteCentre.setMinSize(850, 700);
		vbChoixCarteCentre.setMaxSize(850, 700);
		// vbChoixCarteCentre.setStyle(styleVBox);

		VBox vbCarte = new VBox();
		vbCarte.setAlignment(Pos.CENTER);
		vbCarte.setPrefSize(780, 300);
		vbCarte.setMinSize(780, 300);
		vbCarte.setMaxSize(780, 300);
		// vbCarte.setStyle("-fx-border-color: green; -fx-border-insets: 5;
		// -fx-border-width: 3;");

		VBox vbChoix = new VBox();
		vbChoix.setAlignment(Pos.CENTER);
		vbChoix.setStyle(styleVBox);

		HBox hboxImgCarte = new HBox();
		hboxImgCarte.setAlignment(Pos.CENTER);
		hboxImgCarte.setPrefSize(780, 255);
		hboxImgCarte.setMinSize(780, 255);
		hboxImgCarte.setMaxSize(780, 255);
		hboxImgCarte.setSpacing(-385);
		// hboxImgCarte.setStyle("-fx-border-color: red; -fx-border-insets: 5;
		// -fx-border-width: 3;");

		HBox hboxBoutonCarte = new HBox();
		hboxBoutonCarte.setAlignment(Pos.TOP_CENTER);
		hboxBoutonCarte.setSpacing(20);
		hboxBoutonCarte.setPrefHeight(90);
		hboxBoutonCarte.setPadding(new Insets(10));

		HBox hboxBoutonChoix = new HBox();
		hboxBoutonChoix.setAlignment(Pos.TOP_CENTER);
		hboxBoutonChoix.setSpacing(20);
		hboxBoutonChoix.setPadding(new Insets(20));

		hboxBoutonJoueur = new HBox();
		hboxBoutonJoueur.setAlignment(Pos.TOP_CENTER);
		hboxBoutonJoueur.setSpacing(10);
		hboxBoutonJoueur.setPadding(new Insets(20));
		hboxBoutonJoueur.setVisible(false);
		//
		imgCarte1 = new ImageView(DataControl.CARTE_BATTE);
		imgCarte1.setScaleX(0.4);
		imgCarte1.setScaleY(0.4);
		imgCarte2 = new ImageView(DataControl.CARTE_BATTE);
		imgCarte2.setScaleX(0.4);
		imgCarte2.setScaleY(0.4);
		imgCarte3 = new ImageView(DataControl.CARTE_BATTE);
		imgCarte3.setScaleX(0.4);
		imgCarte3.setScaleY(0.4);

		hboxImgCarte.getChildren().addAll(imgCarte1, imgCarte2, imgCarte3);

		//
		bCarte1 = new Button("Carte 1");
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

		bCarte2 = new Button("Carte 2");
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

		bCarte3 = new Button("Carte 3");
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

		bChoixUtiliser = new Button("Utiliser");
		bChoixUtiliser.setAlignment(Pos.CENTER);
		bChoixUtiliser.setStyle(styleBoutons);
		bChoixUtiliser.setPrefSize(lBoutonCamion, hBoutonCamion);
		bChoixUtiliser.setMinSize(lBoutonCamion, hBoutonCamion);
		bChoixUtiliser.setFont(policeBoutonC);
		bChoixUtiliser.setOnMouseEntered(event -> {
			bChoixUtiliser.setStyle(styleBoutonsSouris);
		});
		bChoixUtiliser.setOnMouseExited(event -> {
			bChoixUtiliser.setStyle(styleBoutons);
		});
		bChoixUtiliser.setOnAction(EventHandler -> {
			if (selectedCarte != null) {
				core.getIdjr().setCarteChoisi(selectedCarte);
				core.getIdjr().carteChoisi(true);
				core.getIdjr().setEtatChoisi("Utiliser");
				cartePanelReset();
			}
		});

		bChoixGarder = new Button("Garder");
		bChoixGarder.setDisable(true);
		bChoixGarder.setAlignment(Pos.CENTER);
		bChoixGarder.setStyle(styleBoutons);
		bChoixGarder.setPrefSize(lBoutonCamion, hBoutonCamion);
		bChoixGarder.setMinSize(lBoutonCamion, hBoutonCamion);
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
				core.getIdjr().setEtatChoisi("Garder");
				cartePanelReset();
			}
		});

		bChoixDonner = new Button("Donner");
		bChoixDonner.setDisable(true);
		bChoixDonner.setAlignment(Pos.CENTER);
		bChoixDonner.setStyle(styleBoutons);
		bChoixDonner.setPrefSize(lBoutonCamion, hBoutonCamion);
		bChoixDonner.setMinSize(lBoutonCamion, hBoutonCamion);
		bChoixDonner.setFont(policeBoutonC);
		bChoixDonner.setOnMouseEntered(event -> {
			bChoixDonner.setStyle(styleBoutonsSouris);
		});
		bChoixDonner.setOnMouseExited(event -> {
			bChoixDonner.setStyle(styleBoutons);
		});
		bChoixDonner.setOnAction(EventHandler -> {
			hboxBoutonJoueur.setVisible(true);
		});

		bChoixDefausser = new Button("Défausser");
		bChoixDefausser.setDisable(true);
		bChoixDefausser.setAlignment(Pos.CENTER);
		bChoixDefausser.setStyle(styleBoutons);
		bChoixDefausser.setPrefSize(lBoutonCamion, hBoutonCamion);
		bChoixDefausser.setMinSize(lBoutonCamion, hBoutonCamion);
		bChoixDefausser.setFont(policeBouton);
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
				core.getIdjr().setEtatChoisi("Defausser");
				cartePanelReset();
			}
		});

		hboxBoutonChoix.getChildren().addAll(bChoixUtiliser, bChoixGarder, bChoixDonner, bChoixDefausser);

		joueur1c = new Button("Joueur1");
		joueur1c.setAlignment(Pos.CENTER);
		joueur1c.setStyle(styleBoutons);
		joueur1c.setPrefSize(lBoutonCamion2, hBoutonCamion);
		joueur1c.setMinSize(lBoutonCamion2, hBoutonCamion);
		joueur1c.setFont(policeBoutonC);
		joueur1c.setOnMouseEntered(event -> {
			joueur1c.setStyle(styleBoutonsSouris);
		});
		joueur1c.setOnMouseExited(event -> {
			joueur1c.setStyle(styleBoutons);
		});

		joueur2c = new Button("Joueur2");
		joueur2c.setAlignment(Pos.CENTER);
		joueur2c.setStyle(styleBoutons);
		joueur2c.setPrefSize(lBoutonCamion2, hBoutonCamion);
		joueur2c.setMinSize(lBoutonCamion2, hBoutonCamion);
		joueur2c.setFont(policeBoutonC);
		joueur2c.setOnMouseEntered(event -> {
			joueur2c.setStyle(styleBoutonsSouris);
		});
		joueur2c.setOnMouseExited(event -> {
			joueur2c.setStyle(styleBoutons);
		});

		joueur3c = new Button("Joueur3");
		joueur3c.setAlignment(Pos.CENTER);
		joueur3c.setStyle(styleBoutons);
		joueur3c.setPrefSize(lBoutonCamion2, hBoutonCamion);
		joueur3c.setMinSize(lBoutonCamion2, hBoutonCamion);
		joueur3c.setFont(policeBoutonC);
		joueur3c.setOnMouseEntered(event -> {
			joueur3c.setStyle(styleBoutonsSouris);
		});
		joueur3c.setOnMouseExited(event -> {
			joueur3c.setStyle(styleBoutons);
		});

		joueur4c = new Button("Joueur4");
		joueur4c.setAlignment(Pos.CENTER);
		joueur4c.setStyle(styleBoutons);
		joueur4c.setPrefSize(lBoutonCamion2, hBoutonCamion);
		joueur4c.setMinSize(lBoutonCamion2, hBoutonCamion);
		joueur4c.setFont(policeBoutonC);
		joueur4c.setOnMouseEntered(event -> {
			joueur4c.setStyle(styleBoutonsSouris);
		});
		joueur4c.setOnMouseExited(event -> {
			joueur4c.setStyle(styleBoutons);
		});

		joueur5c = new Button("Joueur5");
		joueur5c.setAlignment(Pos.CENTER);
		joueur5c.setStyle(styleBoutons);
		joueur5c.setPrefSize(lBoutonCamion2, hBoutonCamion);
		joueur5c.setMinSize(lBoutonCamion2, hBoutonCamion);
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

		Label titreFouille = new Label("Fouille du camion");
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
		fouilleCamion.setDisable(false);
		fouilleCamion.setVisible(true);

		/*
		 * fond.setEffect(flou); TODO A mettre quand le popup fouille camion est activé
		 * rectVigile.setEffect(flou); nomJoueur.setEffect(flou);
		 * vbCentral.setEffect(flou); vbDeplCentre.setEffect(flou); des.setEffect(flou);
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

		labDeplPers = new Label("Déplacement des personnages");
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

		bBlonde = new Button("La Blonde");
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

		bBrute = new Button("La Brute");
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

		bTruand = new Button("Le Truand");
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

		bFillette = new Button("La Fillette");
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

		Label labDeplLieux = new Label("Destination");
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

		bToilettes = new Button("Toilettes");
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

		bCachou = new Button("Cachou");
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

		bMegatoys = new Button("Megatoys");
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

		bParking = new Button("Parking");
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

		bPCSecu = new Button("PC Sécurité");
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

		bSuperMarche = new Button("Supermarché");
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

		VBox des = new VBox();
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

		Label titrede = new Label("DÉS");
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
		info.setBackground(fondBlanc);
		info.setVisible(false);

		VBox vTitreInfo = new VBox();
		vTitreInfo.setAlignment(Pos.CENTER);
		vTitreInfo.setPadding(new Insets(20));
		titreInfo = new Label("Titre info");
		titreInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		vTitreInfo.getChildren().addAll(titreInfo);

		VBox vInfo = new VBox();
		vInfo.setAlignment(Pos.TOP_LEFT);
		lInfo = new Label("information");
		lInfo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
		vInfo.getChildren().addAll(lInfo);

		info.setMargin(vInfo, new Insets(50, 20, 0, 20));
		info.setTop(vTitreInfo);
		info.setCenter(vInfo);
		info.setVisible(true);

		imgFond = new ImageView(DataControl.BLEU);

		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		stackPane.getChildren().addAll(fond, rectVigile, nomJoueur, phasePartie, vbCentral, /* vote, */ vbDeplCentre,
				des, fouilleCamion, info);
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
	public void desVigiles(List<String> list) {
		Platform.runLater(() -> {
			titreInfo.setText("Zombies");
			lInfo.setText("Des zombies ont été placé dans " + list.get(0) + ", " + list.get(1) + ", " + list.get(2)
					+ " et " + list.get(3));
			info.setVisible(true);

			Timer myTimer = new Timer();
			myTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					info.setVisible(false);
				}
			}, 5000);
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
			case BLEU:
				imgFond.setImage(new Image(DataControl.BLEU));
				break;
			case ROUGE:
				imgFond.setImage(new Image(DataControl.ROUGE));
				break;
			case VERT:
				imgFond.setImage(new Image(DataControl.VERT));
				break;
			case JAUNE:
				imgFond.setImage(new Image(DataControl.JAUNE));
				break;
			case MARRON:
				imgFond.setImage(new Image(DataControl.MARRON));
				break;
			case NOIR:
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

	@Override
	public void choisirCarte(CarteType carte, List<Couleur> listeCouleurJoueurVivant, boolean garder, boolean donner,
			boolean defausser, boolean utiliser) {
		Platform.runLater(() -> {
			fouilleCamion.setVisible(true);
			imgCarte1.setImage(new Image(convertCarte(carte)));
			fouilleCamion.setVisible(true);
			bCarte1.setDisable(false);
			bCarte1.setOnAction(EventHandler -> {
				selectedCarte = carte;
				bChoixUtiliser.setDisable(!utiliser);
				bChoixGarder.setDisable(!garder);
				bChoixDonner.setDisable(!donner);
				bChoixDefausser.setDisable(!defausser);
			});
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
		for (int i = 0; i < listeCouleurJoueurVivant.size(); i++) {
			joueursButton[i].setDisable(false);
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
		}
	}

	@Override
	public void choisirCarte(List<CarteType> listeCartes, List<Couleur> listeCouleurJoueurVivant, boolean garder,
			boolean donner, boolean defausser, boolean utiliser) {
		Platform.runLater(() -> {
			fouilleCamion.setVisible(true);
			if (listeCartes.size() <= 1) {
				imgCarte1.setImage(new Image(convertCarte(listeCartes.get(0))));
				bCarte1.setDisable(false);
				bCarte1.setOnAction(EventHandler -> {
					selectedCarte = listeCartes.get(0);
					bChoixUtiliser.setDisable(!utiliser);
					bChoixGarder.setDisable(!garder);
					bChoixDonner.setDisable(!donner);
					bChoixDefausser.setDisable(!defausser);
				});
			}
			if (listeCartes.size() <= 2) {
				imgCarte2.setImage(new Image(convertCarte(listeCartes.get(1))));
				bCarte2.setDisable(false);
				bCarte2.setOnAction(EventHandler -> {
					selectedCarte = listeCartes.get(1);
					bChoixUtiliser.setDisable(!utiliser);
					bChoixGarder.setDisable(!garder);
					bChoixDonner.setDisable(!donner);
					bChoixDefausser.setDisable(!defausser);
				});
			}
			if (listeCartes.size() <= 3) {
				imgCarte3.setImage(new Image(convertCarte(listeCartes.get(2))));
				bCarte3.setDisable(false);
				bCarte3.setOnAction(EventHandler -> {
					selectedCarte = listeCartes.get(2);
					bChoixUtiliser.setDisable(!utiliser);
					bChoixGarder.setDisable(!garder);
					bChoixDonner.setDisable(!donner);
					bChoixDefausser.setDisable(!defausser);
				});
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
		bChoixUtiliser.setDisable(true);
		selectedCarte = null;
		selectedCouleur = null;
	}
}
