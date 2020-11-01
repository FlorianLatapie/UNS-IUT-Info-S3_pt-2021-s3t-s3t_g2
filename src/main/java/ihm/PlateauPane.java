package ihm;

import java.util.Map;

import ihm.DataControl.ApplicationPane;
import ihm.eventListener.LieuxListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jeu.Lieu;

public class PlateauPane extends StackPane implements LieuxListener{
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.PLATEAU; // nom du pane
	
	private int margeP = 24;
	private Insets margeBoutonPause = new Insets(margeP, margeP, margeP, margeP);
	private int lhBoutonPause = 80;
	private String coinBoutons = " -fx-background-radius: 5px";
	private Font policeBoutonPause = Font.font("Segoe UI", FontWeight.BOLD, 33);
	
	private int margeJ = 20;
	private Insets margeTexteJoueur = new Insets(margeJ, 10,margeJ,10);
	private Insets insetJGauche =new Insets(0,500,0,115);
	private Insets insetJDroit = new Insets(0,115,0,500);
	private int tailleVBoxJoueur = 210;
	
	private int taillePlateau = 1080;
	
	private int margeL = 10;
	private Insets margeLieu = new Insets(margeL,margeL,margeL,margeL);
	private int tailleFont = 15;
	private Font fontInfo = Font.font("Segoe UI", tailleFont);
	private Font fontTitre = Font.font("Segoe UI", FontWeight.BOLD, tailleFont);
	
	public PlateauPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;
		
		HBox hHaut = new HBox(); 
		HBox hDroite = new HBox(); 
		HBox hBas = new HBox(); 
		HBox hGauche = new HBox(); 
		//StackPane sBase = new StackPane(); 
		BorderPane borderJoueurs = new BorderPane();
		AnchorPane aPlateau = new AnchorPane(); 
		
		///////////////////////////////////////////
		Button bPause1 = new Button("| |"); 
		bPause1.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause1.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause1.setStyle(coinBoutons);
		bPause1.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause1.setFont(policeBoutonPause);
		hBas.setMargin(bPause1, margeBoutonPause);
		
		Button bPause2 = new Button("| |"); 
		bPause2.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause2.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause2.setStyle(coinBoutons);
		bPause2.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause2.setFont(policeBoutonPause);
		hBas.setMargin(bPause2, margeBoutonPause);
		
		VBox j1 = new VBox();
		Label nbPerso1 = new Label("## personnages");
		nbPerso1.setFont(Font.font("Segoe UI", 20));
		nbPerso1.setTextFill(Color.BLACK);
		Label nbCartes1 = new Label("## de cartes");
		nbCartes1.setFont(Font.font("Segoe UI", 20));
		nbCartes1.setTextFill(Color.BLACK);
		Label nomJoueur1 = new Label("nom Joueur1");
		nomJoueur1.setFont(Font.font("Segoe UI", 20));
		nomJoueur1.setTextFill(Color.BLACK);
		
		hBas.setMargin(j1, insetJGauche);
		j1.setAlignment(Pos.CENTER);
		j1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		j1.setMargin(nbPerso1, margeTexteJoueur);
		j1.setMargin(nbCartes1, margeTexteJoueur);
		j1.setMargin(nomJoueur1, margeTexteJoueur);
		j1.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j1.getChildren().addAll(nbPerso1,nbCartes1,nomJoueur1);
		
		VBox j2 = new VBox();
		Label nbPerso2 = new Label("## personnages");
		nbPerso2.setFont(Font.font("Segoe UI", 20));
		nbPerso2.setTextFill(Color.BLACK);
		Label nbCartes2 = new Label("## de cartes");
		nbCartes2.setFont(Font.font("Segoe UI", 20));
		nbCartes2.setTextFill(Color.BLACK);
		Label nomJoueur2 = new Label("Nom Joueur 2");
		nomJoueur2.setFont(Font.font("Segoe UI", 20));
		nomJoueur2.setTextFill(Color.BLACK);
		
		hBas.setMargin(j2, insetJDroit);
		j2.setAlignment(Pos.CENTER);
		j2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		j2.setMargin(nbPerso2, margeTexteJoueur);
		j2.setMargin(nbCartes2, margeTexteJoueur);
		j2.setMargin(nomJoueur2, margeTexteJoueur);
		j2.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j2.getChildren().addAll(nbPerso2,nbCartes2,nomJoueur2);
		
		
		hBas.getChildren().addAll(bPause1,j1,j2,bPause2);		
		hBas.setAlignment(Pos.BOTTOM_CENTER);
		
/////////////////////////////////////////////////////
		
		Button bPause3 = new Button("| |"); 
		bPause3.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause3.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause3.setStyle(coinBoutons);
		bPause3.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause3.setFont(policeBoutonPause);
		hHaut.setMargin(bPause3, margeBoutonPause);
		
		Button bPause4 = new Button("| |"); 
		bPause4.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause4.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause4.setStyle(coinBoutons);
		bPause4.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		bPause4.setFont(policeBoutonPause);
		hHaut.setMargin(bPause4, margeBoutonPause);
		
		VBox j3 = new VBox();
		Label nbPerso3 = new Label("## personnages");
		nbPerso3.setFont(Font.font("Segoe UI", 20));
		nbPerso3.setTextFill(Color.BLACK);
		Label nbCartes3 = new Label("## de cartes");
		nbCartes3.setFont(Font.font("Segoe UI", 20));
		nbCartes3.setTextFill(Color.BLACK);
		Label nomJoueur3 = new Label("Nom Joueur 3");
		nomJoueur3.setFont(Font.font("Segoe UI", 20));
		nomJoueur3.setTextFill(Color.BLACK);
		
		hHaut.setMargin(j3, insetJGauche);
		j3.setAlignment(Pos.CENTER);
		j3.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		j3.setMargin(nbPerso3, margeTexteJoueur);
		j3.setMargin(nbCartes3, margeTexteJoueur);
		j3.setMargin(nomJoueur3, margeTexteJoueur);
		j3.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j3.getChildren().addAll(nbPerso3,nbCartes3,nomJoueur3);
		
		VBox j4 = new VBox();
		Label nbPerso4 = new Label("## personnages");
		nbPerso4.setFont(Font.font("Segoe UI", 20));
		nbPerso4.setTextFill(Color.BLACK);
		Label nbCartes4 = new Label("## de cartes");
		nbCartes4.setFont(Font.font("Segoe UI", 20));
		nbCartes4.setTextFill(Color.BLACK);
		Label nomJoueur4 = new Label("Nom Joueur 4");
		nomJoueur4.setFont(Font.font("Segoe UI", 20));
		nomJoueur4.setTextFill(Color.BLACK);
		
		hHaut.setMargin(j4, insetJDroit);
		j4.setAlignment(Pos.CENTER);
		j4.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		j4.setMargin(nbPerso4, margeTexteJoueur);
		j4.setMargin(nbCartes4, margeTexteJoueur);
		j4.setMargin(nomJoueur4, margeTexteJoueur);
		j4.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j4.getChildren().addAll(nbPerso4,nbCartes4,nomJoueur4);
		
		
		hHaut.getChildren().addAll(bPause3,j3,j4,bPause4);		
		hHaut.setAlignment(Pos.BOTTOM_CENTER);
		hHaut.setRotate(180);
		
		////////////////////////////////////////////////
		
		VBox j5 = new VBox();
		Label nbPerso5 = new Label("## personnages");
		nbPerso5.setFont(Font.font("Segoe UI", 20));
		nbPerso5.setTextFill(Color.BLACK);
		Label nbCartes5 = new Label("## de cartes");
		nbCartes5.setFont(Font.font("Segoe UI", 20));
		nbCartes5.setTextFill(Color.BLACK);
		Label nomJoueur5 = new Label("Nom Joueur 5");
		nomJoueur5.setFont(Font.font("Segoe UI", 20));
		nomJoueur5.setTextFill(Color.BLACK);
		
		j5.setAlignment(Pos.CENTER);
		j5.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		j5.setMargin(nbPerso5, margeTexteJoueur);
		j5.setMargin(nbCartes5, margeTexteJoueur);
		j5.setMargin(nomJoueur5, margeTexteJoueur);
		j5.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j5.setPrefSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j5.setMaxSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j5.getChildren().addAll(nbPerso5,nbCartes5,nomJoueur5);
		j5.setRotate(270);
		
		hDroite.getChildren().addAll(j5);		
		hDroite.setAlignment(Pos.CENTER_LEFT);
		
		
		
		///////////////////////
		/*
		// obsolete 
		VBox j6 = new VBox();
		Label nbPerso6 = new Label("## personnages");
		nbPerso6.setFont(Font.font("Segoe UI", 20));
		nbPerso6.setTextFill(Color.BLACK);
		Label nbCartes6 = new Label("## de cartes");
		nbCartes6.setFont(Font.font("Segoe UI", 20));
		nbCartes6.setTextFill(Color.BLACK);
		Label nomJoueur6 = new Label("Nom Joueur 1");
		nomJoueur6.setFont(Font.font("Segoe UI", 20));
		nomJoueur6.setTextFill(Color.BLACK);
		
		j6.setAlignment(Pos.CENTER);
		j6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		j6.setMargin(nbPerso6, margeTexteJoueur);
		j6.setMargin(nbCartes6, margeTexteJoueur);
		j6.setMargin(nomJoueur6, margeTexteJoueur);
		j6.setMinSize(tailleVBoxJoueur, tailleVBoxJoueur);
		j6.getChildren().addAll(nbPerso6,nbCartes6,nomJoueur6);
		
		
		hGauche.getChildren().addAll(j6);		
		hGauche.setAlignment(Pos.BOTTOM_CENTER);
		hGauche.setRotate(90);*/
		
		/////////////////////////////////////////////////////
		aPlateau.setMinSize(taillePlateau, taillePlateau);
		aPlateau.setPrefSize(taillePlateau, taillePlateau);
		aPlateau.setMaxSize(taillePlateau, taillePlateau);
		
		Label estBarricade1 = new Label("Barricadé\n"+"Oui/Non");
		estBarricade1.setFont(fontInfo);
		estBarricade1.setTextFill(Color.BLACK);
		
		Label titre1 = new Label("Toilettes");
		titre1.setFont(fontTitre);
		titre1.setTextFill(Color.BLACK);
		
		Label estFerme1 = new Label("Fermé\n"+"Oui/Non");
		estFerme1.setFont(fontInfo);
		estFerme1.setTextFill(Color.BLACK);
		
		Label force1 = new Label("Force\ntotale de\nl'équipe :\n"+"##");
		force1.setFont(fontInfo);
		force1.setTextFill(Color.BLACK);
		
		GridPane joueursPresents1 = new GridPane(); 
		joueursPresents1.setPrefSize(200, 200);
		joueursPresents1.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, null)));
		//todo 
		
		Label nbZombies1 = new Label("## de\nzombies");
		nbZombies1.setFont(fontInfo);
		nbZombies1.setTextFill(Color.BLACK);
		
		
		GridPane g1 = new GridPane(); 
		g1.add(estBarricade1, 0, 0);
		g1.add(titre1, 1, 0);
		g1.add(estFerme1, 2, 0);
		
		g1.add(force1, 0, 1);
		g1.add(joueursPresents1, 1, 1);
		g1.add(nbZombies1, 2, 1);
		
		g1.setAlignment(Pos.CENTER);
		g1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		
		g1.setMargin(estBarricade1, margeLieu);
		g1.setMargin(titre1, margeLieu);
		g1.setMargin(estFerme1, margeLieu);
		g1.setMargin(force1, margeLieu);
		g1.setMargin(joueursPresents1, margeLieu);
		g1.setMargin(nbZombies1, margeLieu);
		
		g1.setRotate(134.0);
		
		aPlateau.setTopAnchor(g1, 700.0);
		aPlateau.setLeftAnchor(g1, 600.0);
		
			
		////
		
		Label estBarricade2 = new Label("Barricadé\n"+"Oui/Non");
		estBarricade2.setFont(fontInfo);
		estBarricade2.setTextFill(Color.BLACK);
		
		Label titre2 = new Label("Cachou");
		titre2.setFont(fontTitre);
		titre2.setTextFill(Color.BLACK);
		
		Label estFerme2 = new Label("Fermé\n"+"Oui/Non");
		estFerme2.setFont(fontInfo);
		estFerme2.setTextFill(Color.BLACK);
		
		Label force2 = new Label("Force\ntotale de\nl'équipe :\n"+"##");
		force2.setFont(fontInfo);
		force2.setTextFill(Color.BLACK);
		
		GridPane joueursPresents2 = new GridPane(); 
		joueursPresents2.setPrefSize(200, 200);
		joueursPresents2.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, null)));
		//todo 
		
		Label nbZombies2 = new Label("## de\nzombies");
		nbZombies2.setFont(fontInfo);
		nbZombies2.setTextFill(Color.BLACK);
		
		
		GridPane g2 = new GridPane(); 
		g2.add(estBarricade2, 0, 0);
		g2.add(titre2, 1, 0);
		g2.add(estFerme2, 2, 0);
		
		g2.add(force2, 0, 2);
		g2.add(joueursPresents2, 1, 2);
		g2.add(nbZombies2, 2, 2);
		
		g2.setAlignment(Pos.CENTER);
		g2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		
		g2.setMargin(estBarricade2, margeLieu);
		g2.setMargin(titre2, margeLieu);
		g2.setMargin(estFerme2, margeLieu);
		g2.setMargin(force2, margeLieu);
		g2.setMargin(joueursPresents2, margeLieu);
		g2.setMargin(nbZombies2, margeLieu);
		
		g2.setRotate(226.0);
		
		aPlateau.setTopAnchor(g2, 700.0);
		aPlateau.setLeftAnchor(g2, 10.0);
		
		///
		
		Label estBarricade3 = new Label("Barricadé\n"+"Oui/Non");
		estBarricade3.setFont(fontInfo);
		estBarricade3.setTextFill(Color.BLACK);
		
		Label titre3 = new Label("Megatoys");
		titre3.setFont(fontTitre);
		titre3.setTextFill(Color.BLACK);
		
		Label estFerme3 = new Label("Fermé\n"+"Oui/Non");
		estFerme3.setFont(fontInfo);
		estFerme3.setTextFill(Color.BLACK);
		
		Label force3 = new Label("Force\ntotale de\nl'équipe :\n"+"##");
		force3.setFont(fontInfo);
		force3.setTextFill(Color.BLACK);
		
		GridPane joueursPresents3 = new GridPane(); 
		joueursPresents3.setPrefSize(200, 200);
		joueursPresents3.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, null)));
		//todo 
		
		Label nbZombies3 = new Label("## de\nzombies");
		nbZombies3.setFont(fontInfo);
		nbZombies3.setTextFill(Color.BLACK);
		
		
		GridPane g3 = new GridPane(); 
		g3.add(estBarricade3, 0, 0);
		g3.add(titre3, 1, 0);
		g3.add(estFerme3, 2, 0);
		
		g3.add(force3, 0, 2);
		g3.add(joueursPresents3, 1, 2);
		g3.add(nbZombies3, 2, 2);
		
		g3.setAlignment(Pos.CENTER);
		g3.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		
		g3.setMargin(estBarricade3, margeLieu);
		g3.setMargin(titre3, margeLieu);
		g3.setMargin(estFerme3, margeLieu);
		g3.setMargin(force3, margeLieu);
		g3.setMargin(joueursPresents3, margeLieu);
		g3.setMargin(nbZombies3, margeLieu);
		
		g3.setRotate(293.0);
		
		aPlateau.setTopAnchor(g3, 200.0);
		aPlateau.setLeftAnchor(g3, 20.0);
		
		///
		Label estBarricade4 = new Label("Barricadé\n"+"Oui/Non");
		estBarricade4.setFont(fontInfo);
		estBarricade4.setTextFill(Color.BLACK);
		
		Label titre4 = new Label("Parking");
		titre4.setFont(fontTitre);
		titre4.setTextFill(Color.BLACK);
		
		Label estFerme4 = new Label("Fermé\n"+"Oui/Non");
		estFerme4.setFont(fontInfo);
		estFerme4.setTextFill(Color.BLACK);
		
		Label force4 = new Label("Force\ntotale de\nl'équipe :\n"+"##");
		force4.setFont(fontInfo);
		force4.setTextFill(Color.BLACK);
		
		GridPane joueursPresents4 = new GridPane(); 
		joueursPresents4.setPrefSize(200, 200);
		joueursPresents4.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, null)));
		//todo 
		
		Label nbZombies4 = new Label("## de\nzombies");
		nbZombies4.setFont(fontInfo);
		nbZombies4.setTextFill(Color.BLACK);
		
		
		GridPane g4 = new GridPane(); 
		g4.add(estBarricade4, 0, 0);
		g4.add(titre4, 1, 0);
		g4.add(estFerme4, 2, 0);
		
		g4.add(force4, 0, 2);
		g4.add(joueursPresents4, 1, 2);
		g4.add(nbZombies4, 2, 2);
		
		g4.setAlignment(Pos.CENTER);
		g4.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		
		g4.setMargin(estBarricade4, margeLieu);
		g4.setMargin(titre4, margeLieu);
		g4.setMargin(estFerme4, margeLieu);
		g4.setMargin(force4, margeLieu);
		g4.setMargin(joueursPresents4, margeLieu);
		g4.setMargin(nbZombies4, margeLieu);
		
		g4.setRotate(0.0);
		
		aPlateau.setTopAnchor(g4, 400.0);
		aPlateau.setLeftAnchor(g4, 350.0);
		
		///
		
		Label estBarricade5 = new Label("Barricadé\n"+"Oui/Non");
		estBarricade5.setFont(fontInfo);
		estBarricade5.setTextFill(Color.BLACK);
		
		Label titre5 = new Label("PC de Sécurité");
		titre5.setFont(fontTitre);
		titre5.setTextFill(Color.BLACK);
		
		Label estFerme5 = new Label("Fermé\n"+"Oui/Non");
		estFerme5.setFont(fontInfo);
		estFerme5.setTextFill(Color.BLACK);
		
		Label force5 = new Label("Force\ntotale de\nl'équipe :\n"+"##");
		force5.setFont(fontInfo);
		force5.setTextFill(Color.BLACK);
		
		GridPane joueursPresents5 = new GridPane(); 
		joueursPresents5.setPrefSize(200, 200);
		joueursPresents5.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, null)));
		//todo 
		
		Label nbZombies5 = new Label("## de\nzombies");
		nbZombies5.setFont(fontInfo);
		nbZombies5.setTextFill(Color.BLACK);
		
		
		GridPane g5 = new GridPane(); 
		g5.add(estBarricade5, 0, 0);
		g5.add(titre5, 1, 0);
		g5.add(estFerme5, 2, 0);
		
		g5.add(force5, 0, 2);
		g5.add(joueursPresents5, 1, 2);
		g5.add(nbZombies5, 2, 2);
		
		g5.setAlignment(Pos.CENTER);
		g5.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		
		g5.setMargin(estBarricade5, margeLieu);
		g5.setMargin(titre5, margeLieu);
		g5.setMargin(estFerme5, margeLieu);
		g5.setMargin(force5, margeLieu);
		g5.setMargin(joueursPresents5, margeLieu);
		g5.setMargin(nbZombies5, margeLieu);
		
		g5.setRotate(0.0);
		
		aPlateau.setTopAnchor(g5, 50.0);
		aPlateau.setLeftAnchor(g5, 350.0);
		
		///
		
		Label estBarricade6 = new Label("Barricadé\n"+"Oui/Non");
		estBarricade6.setFont(fontInfo);
		estBarricade6.setTextFill(Color.BLACK);
		
		Label titre6 = new Label("Supermarché");
		titre6.setFont(fontTitre);
		titre6.setTextFill(Color.BLACK);
		
		Label estFerme6 = new Label("Fermé\n"+"Oui/Non");
		estFerme6.setFont(fontInfo);
		estFerme6.setTextFill(Color.BLACK);
		
		Label force6 = new Label("Force\ntotale de\nl'équipe :\n"+"##");
		force6.setFont(fontInfo);
		force6.setTextFill(Color.BLACK);
		
		GridPane joueursPresents6 = new GridPane(); 
		joueursPresents6.setPrefSize(200, 200);
		joueursPresents6.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, null)));
		//TODO 
		
		Label nbZombies6 = new Label("## de\nzombies");
		nbZombies6.setFont(fontInfo);
		nbZombies6.setTextFill(Color.BLACK);
		
		
		GridPane g6 = new GridPane(); 
		g6.add(estBarricade6, 0, 0);
		g6.add(titre6, 1, 0);
		g6.add(estFerme6, 2, 0);
		
		g6.add(force6, 0, 2);
		g6.add(joueursPresents6, 1, 2);
		g6.add(nbZombies6, 2, 2);
		
		g6.setAlignment(Pos.CENTER);
		g6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, null)));
		
		g6.setMargin(estBarricade6, margeLieu);
		g6.setMargin(titre6, margeLieu);
		g6.setMargin(estFerme6, margeLieu);
		g6.setMargin(force6, margeLieu);
		g6.setMargin(joueursPresents6, margeLieu);
		g6.setMargin(nbZombies6, margeLieu);
		
		g6.setRotate(62.0);
		
		aPlateau.setTopAnchor(g6, 300.0);
		aPlateau.setLeftAnchor(g6, 700.0);
		
		aPlateau.getChildren().addAll(g1,g2,g3,g4,g5,g6);
		
		//aPlateau.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
		
		////////////////////////////////////////////////////
		borderJoueurs.setTop(hHaut);
		borderJoueurs.setBottom(hBas);
		borderJoueurs.setLeft(hGauche);
		borderJoueurs.setRight(hDroite);
		
		borderJoueurs.setMinSize(1920,1080);
		borderJoueurs.setPrefSize(1920, 1080);
		borderJoueurs.setMaxSize(1920,1080);
		
		ImageView imgFond = new ImageView(DataControl.PLATEAU);
		imgFond.setScaleX(0.4362);
		imgFond.setScaleY(0.4362);
		
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
		
		this.getChildren().addAll(imgFond,borderJoueurs,aPlateau);
		
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
		

		
	}

	@Override
	public void lieuxChanged(Map<Integer, Lieu> lieux) {
		// TODO modifier l'affichage en recuperant les element dans lieux
		
	}
}
