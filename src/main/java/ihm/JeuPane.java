package ihm;

import ihm.DataControl.ApplicationPane;
import ihm.ScreenControl;
import ihm.event.JeuListener;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;

public class JeuPane extends StackPane implements JeuListener {

	private ScreenControl sControl = null;
	private Core core = null;
	// définition des variable pour la suite du pane
	private int tailleCarreCentral = 600; // l'interface est sur un stackPane qui peut tourner avec des crans de 90
											// degrés
	private int hBouton = 100;
	private int lBouton = 200;
	private int marge = tailleCarreCentral / 25;
	private Insets margeBoutons = new Insets(marge, marge, marge, marge);
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);
	private Font policeBoutonDe = Font.font("Segoe UI", FontWeight.BOLD, 40);
	private CornerRadii coin = new CornerRadii(15.0);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleDe = " -fx-background-color:#FFFFFF; -fx-background-radius: 3px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleVBox = "-fx-border-color: black; -fx-border-insets: 5; -fx-border-width: 3;";
	private CornerRadii coinfb = new CornerRadii(5.0);
	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));
	private Background fondNoir = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null));
	
	private int largBouton = 155;
	private int hautBouton = 70;

	private Font policeNom = Font.font("Segoe UI", 17);
	private int largeurTF = 100;
	private int hauteurElemtents = 60;
	private final ApplicationPane paneName = ApplicationPane.JEU;

	public JeuPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);
		
		Label nomJoueur = new Label("Nom du joueur");
		nomJoueur.setAlignment(Pos.CENTER);
		nomJoueur.setTranslateY(-400);
		nomJoueur.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
		nomJoueur.setTextFill(Color.BLACK);
		//////////////////////////////////////////
		//TODO afficher une image dedans 
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

		Button joueur1 = new Button("Joueur1");
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

		Button joueur2 = new Button("Joueur2");
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

		Button joueur3 = new Button("Joueur3");
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

		Button joueur4 = new Button("Joueur4");
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

		Button joueur5 = new Button("Joueur5");
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
        //vbTitre.setMaxSize(700, 80);
        vbTitre.setBackground(fondNoir);
        //vbTitre.setOpacity(.5);
        vbTitre.getChildren().addAll(titreVote, titreQuestion);

		vote.setTop(vbTitre);
		vote.setCenter(vbVoteCentre);
		vote.setDisable(false);

		//////////////////////////////////////////////////////////

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
		//vbTitre1.setMaxSize(300, 50);

		Label labDeplPers = new Label("Déplacement des personnages");
		labDeplPers.setAlignment(Pos.TOP_CENTER);
		labDeplPers.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		labDeplPers.setTextFill(Color.WHITE);

		VBox vbPersonnage = new VBox();
		vbPersonnage.setMinWidth(350);
		vbPersonnage.setMaxWidth(350);
		vbPersonnage.setAlignment(Pos.TOP_CENTER);
		//vbPersonnage.setStyle(styleVBox);

		HBox hbHaut = new HBox();
		hbHaut.setAlignment(Pos.TOP_CENTER);
		hbHaut.setSpacing(5);
		hbHaut.setPadding(new Insets(5));
		HBox hbBas = new HBox();
		hbBas.setAlignment(Pos.TOP_CENTER);
		hbBas.setSpacing(5);
		hbBas.setPadding(new Insets(5));

		Button bBlonde = new Button("La Blonde");
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

		Button bBrute = new Button("La Brute");
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

		Button bTruand = new Button("Le Truand");
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

		Button bFillette = new Button("La Fillette");
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
		//vbTitre2.setMaxSize(300, 50);

		Label labDeplLieux = new Label("Destination");
		labDeplLieux.setAlignment(Pos.TOP_CENTER);
		labDeplLieux.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		labDeplLieux.setTextFill(Color.WHITE);
		
		HBox hbLieux = new HBox();
		hbLieux.setMinWidth(350);
		hbLieux.setMaxWidth(350);
		hbLieux.setAlignment(Pos.TOP_CENTER);
		//hbLieux.setStyle(styleVBox);

		VBox vbGauche = new VBox();
		vbGauche.setAlignment(Pos.CENTER_LEFT);
		vbGauche.setSpacing(5);
		vbGauche.setPadding(new Insets(5));
		
		VBox vbDroite = new VBox();
		vbDroite.setAlignment(Pos.CENTER_RIGHT);
		vbDroite.setSpacing(5);
		vbDroite.setPadding(new Insets(5));

		Button bToilettes = new Button("Toilettes");
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

		Button bCachou = new Button("Cachou");
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

		Button bMegatoys = new Button("Megatoys");
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

		Button bParking = new Button("Parking");
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
		
		Button bPCSecu = new Button("PC\nSécurité");
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
		
		Button bSuperMarche = new Button("Supermarché");
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
		vbDroite.getChildren().addAll(bCachou, bParking,bSuperMarche );

		vbTitre2.getChildren().add(labDeplLieux);
		vbTitre2.setBackground(fondNoir);
		
		
		hbLieux.getChildren().addAll(vbGauche, vbDroite);
		vbDeplLieux.getChildren().addAll(vbTitre2, hbLieux);
		
		vbDeplCentre.getChildren().addAll(vbDeplPers, vbDeplLieux);
		vbDeplCentre.setDisable(false);
		/////
		
		VBox des = new VBox();
		des.setTranslateX(790);
		des.setTranslateY(-100);
		des.setAlignment(Pos.CENTER);
		des.setStyle(styleVBox);
		//des.setMaxSize(100,250);
		//des.setMinSize(100,250);
		des.setMaxSize(120,275);
		Label de1 = new Label("6");
		de1.setBackground(fondBlanc);
		de1.setAlignment(Pos.CENTER);
		de1.setMinSize(100,100);
		//de1.setStyle(styleDe);
		de1.setTextFill(Color.BLACK);
		de1.setFont(policeBoutonDe);
		Label de2 = new Label("6");
		de2.setBackground(fondBlanc);
		de2.setAlignment(Pos.CENTER);
		de2.setMinSize(100,100);
		//de2.setStyle(styleDe);
		de2.setTextFill(Color.BLACK);
		de2.setFont(policeBoutonDe);
		
		Button lancer = new Button("Lancer");
		lancer.setStyle(styleBoutons);
		lancer.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
		lancer.setMinSize(100,70);
		lancer.setOnMouseEntered(event -> {
			lancer.setStyle(styleBoutonsSouris);
		});
		lancer.setOnMouseExited(event -> {
			lancer.setStyle(styleBoutons);
		});
		
		des.setPadding(new Insets(10));
		des.setSpacing(10);
		des.getChildren().addAll(de1,de2,lancer);
		des.setDisable(false); //TODO ne pas oublier de le retier 
		
		/////
		ImageView imgFond = new ImageView(DataControl.BLEU);
		/////
		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);
		
		stackPane.getChildren().addAll(fond,rectVigile, nomJoueur, vbCentral, vote, vbDeplCentre,des);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
		//stackPane.setOpacity(.8);

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
