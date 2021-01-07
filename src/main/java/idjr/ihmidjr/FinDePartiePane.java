package idjr.ihmidjr;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.IFinListener;
import idjr.ihmidjr.langues.ITraduction;
import idjr.ihmidjr.langues.International;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * The Class AccueilPane.
 * 
 * @author Florian
 * @author Remy 
 * @author Sebastien 
 * @author Tom 
 * 
 * @version 0.1
 * @since 01/11/2020
 */
public class FinDePartiePane extends StackPane implements IFinListener, ITraduction {
	//auteur florian 
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.ENDGAME;
	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	
	private String nomPolice = "Segoe UI";
	private Font policeBouton = Font.font(nomPolice, FontWeight.BOLD, 27);

	private CornerRadii coin = new CornerRadii(15.0);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);

	private Font policeNom = Font.font(nomPolice, FontWeight.BOLD, 35);
	private String styleVBox = "-fx-border-color: #1A1A1A; -fx-border-insets: 5; -fx-border-width: 3;";
	private Font policeScoreBoard = Font.font(nomPolice, FontWeight.BOLD, 20);

	Label desc;
	/*Label nomJoueur1;
	Label score1;
	Label nbPersVivant1;
	Label nbZbTue1;
	Label nomJoueur2;
	Label score2;
	Label nbPersVivant2;
	Label nbZbTue2;
	Label nomJoueur3;
	Label score3;
	Label nbPersVivant3;
	Label nbZbTue3;
	Label nomJoueur4;
	Label score4;
	Label nbPersVivant4;
	Label nbZbTue4;
	Label nomJoueur5;
	Label score5;
	Label nbPersVivant5;
	Label nbZbTue5;
	Label nomJoueur6;
	Label score6;
	Label nbPersVivant6;
	Label nbZbTue6;*/

	Label titreJoueur;
	Label titreScore;
	Label titreNbPersVivant;
	Label titreNbZbTues;

	Label titre1;
	Button bRetour;

	public FinDePartiePane(ScreenControl sc, Core c) {
		//auteur florian
		core = c;
		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

		// titre
		titre1 = new Label(
				International.trad("text.titreFinDePartieA") + " " + International.trad("text.titreFinDePartieB"));
		titre1.setFont(Font.font(nomPolice, FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		desc = new Label("gagné/perdu");
		desc.setTextAlignment(TextAlignment.CENTER);
		desc.setTextFill(Color.WHITE);
		desc.setFont(policeNom);
		desc.setPadding(new Insets(20));
		//auteur remy 
		/*nomJoueur1 = new Label(International.trad("texte.j1"));
		nomJoueur1.setAlignment(Pos.CENTER);
		nomJoueur1.setFont(policeScoreBoard);
		nomJoueur1.setTextFill(Color.WHITESMOKE);
		score1 = new Label("30000");
		score1.setAlignment(Pos.CENTER);
		score1.setFont(policeScoreBoard);
		score1.setTextFill(Color.WHITESMOKE);
		nbPersVivant1 = new Label("5");
		nbPersVivant1.setAlignment(Pos.CENTER);
		nbPersVivant1.setFont(policeScoreBoard);
		nbPersVivant1.setTextFill(Color.WHITESMOKE);
		nbZbTue1 = new Label("0");
		nbZbTue1.setAlignment(Pos.CENTER);
		nbZbTue1.setFont(policeScoreBoard);
		nbZbTue1.setTextFill(Color.WHITESMOKE);

		nomJoueur2 = new Label(International.trad("texte.j2"));
		nomJoueur2.setAlignment(Pos.CENTER);
		nomJoueur2.setFont(policeScoreBoard);
		nomJoueur2.setTextFill(Color.WHITESMOKE);
		score2 = new Label("30000");
		score2.setAlignment(Pos.CENTER);
		score2.setFont(policeScoreBoard);
		score2.setTextFill(Color.WHITESMOKE);
		nbPersVivant2 = new Label("5");
		nbPersVivant2.setAlignment(Pos.CENTER);
		nbPersVivant2.setFont(policeScoreBoard);
		nbPersVivant2.setTextFill(Color.WHITESMOKE);
		nbZbTue2 = new Label("0");
		nbZbTue2.setAlignment(Pos.CENTER);
		nbZbTue2.setFont(policeScoreBoard);
		nbZbTue2.setTextFill(Color.WHITESMOKE);

		nomJoueur3 = new Label(International.trad("texte.j3"));
		nomJoueur3.setAlignment(Pos.CENTER);
		nomJoueur3.setFont(policeScoreBoard);
		nomJoueur3.setTextFill(Color.WHITESMOKE);
		score3 = new Label("30000");
		score3.setAlignment(Pos.CENTER);
		score3.setFont(policeScoreBoard);
		score3.setTextFill(Color.WHITESMOKE);
		nbPersVivant3 = new Label("5");
		nbPersVivant3.setAlignment(Pos.CENTER);
		nbPersVivant3.setFont(policeScoreBoard);
		nbPersVivant3.setTextFill(Color.WHITESMOKE);
		nbZbTue3 = new Label("0");
		nbZbTue3.setAlignment(Pos.CENTER);
		nbZbTue3.setFont(policeScoreBoard);
		nbZbTue3.setTextFill(Color.WHITESMOKE);

		nomJoueur4 = new Label(International.trad("texte.j4"));
		nomJoueur4.setAlignment(Pos.CENTER);
		nomJoueur4.setFont(policeScoreBoard);
		nomJoueur4.setTextFill(Color.WHITESMOKE);
		score4 = new Label("30000");
		score4.setAlignment(Pos.CENTER);
		score4.setFont(policeScoreBoard);
		score4.setTextFill(Color.WHITESMOKE);
		nbPersVivant4 = new Label("5");
		nbPersVivant4.setAlignment(Pos.CENTER);
		nbPersVivant4.setFont(policeScoreBoard);
		nbPersVivant4.setTextFill(Color.WHITESMOKE);
		nbZbTue4 = new Label("0");
		nbZbTue4.setAlignment(Pos.CENTER);
		nbZbTue4.setFont(policeScoreBoard);
		nbZbTue4.setTextFill(Color.WHITESMOKE);

		nomJoueur5 = new Label(International.trad("texte.j5"));
		nomJoueur5.setAlignment(Pos.CENTER);
		nomJoueur5.setFont(policeScoreBoard);
		nomJoueur5.setTextFill(Color.WHITESMOKE);
		score5 = new Label("30000");
		score5.setAlignment(Pos.CENTER);
		score5.setFont(policeScoreBoard);
		score5.setTextFill(Color.WHITESMOKE);
		nbPersVivant5 = new Label("5");
		nbPersVivant5.setAlignment(Pos.CENTER);
		nbPersVivant5.setFont(policeScoreBoard);
		nbPersVivant5.setTextFill(Color.WHITESMOKE);
		nbZbTue5 = new Label("0");
		nbZbTue5.setAlignment(Pos.CENTER);
		nbZbTue5.setFont(policeScoreBoard);
		nbZbTue5.setTextFill(Color.WHITESMOKE);

		nomJoueur6 = new Label(International.trad("texte.j6"));
		nomJoueur6.setAlignment(Pos.CENTER);
		nomJoueur6.setFont(policeScoreBoard);
		nomJoueur6.setTextFill(Color.WHITESMOKE);
		score6 = new Label("30000");
		score6.setAlignment(Pos.CENTER);
		score6.setFont(policeScoreBoard);
		score6.setTextFill(Color.WHITESMOKE);
		nbPersVivant6 = new Label("5");
		nbPersVivant6.setAlignment(Pos.CENTER);
		nbPersVivant6.setFont(policeScoreBoard);
		nbPersVivant6.setTextFill(Color.WHITESMOKE);
		nbZbTue6 = new Label("0");
		nbZbTue6.setAlignment(Pos.CENTER);
		nbZbTue6.setFont(policeScoreBoard);
		nbZbTue6.setTextFill(Color.WHITESMOKE);

		titreJoueur = new Label(International.trad("texte.nom"));
		titreJoueur.setAlignment(Pos.CENTER);
		titreJoueur.setFont(policeScoreBoard);
		titreJoueur.setTextFill(Color.RED);

		titreScore = new Label(International.trad("texte.score"));
		titreScore.setAlignment(Pos.CENTER);
		titreScore.setFont(policeScoreBoard);
		titreScore.setTextFill(Color.RED);

		titreNbPersVivant = new Label(International.trad("texte.alive"));
		titreNbPersVivant.setAlignment(Pos.CENTER);
		titreNbPersVivant.setFont(policeScoreBoard);
		titreNbPersVivant.setTextFill(Color.RED);

		titreNbZbTues = new Label(International.trad("texte.zTues"));
		titreNbZbTues.setAlignment(Pos.CENTER);
		titreNbZbTues.setFont(policeScoreBoard);
		titreNbZbTues.setTextFill(Color.RED);
		*/
		VBox vbCenter = new VBox();
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setPrefHeight(500);
		vbCenter.setMinHeight(500);
		vbCenter.setTranslateY(-50);
/*
		TilePane tile1 = new TilePane();
		tile1.setAlignment(Pos.CENTER_LEFT);
		tile1.setStyle(styleVBox);
		tile1.setPrefSize(700, 65);
		tile1.setMinSize(700, 65);
		tile1.setMaxSize(700, 65);
		tile1.setPadding(new Insets(30));
		tile1.setHgap(95);
		tile1.getChildren().addAll(nomJoueur1, score1, nbPersVivant1, nbZbTue1);

		TilePane tile2 = new TilePane();
		tile2.setAlignment(Pos.CENTER_LEFT);
		tile2.setStyle(styleVBox);
		tile2.setPrefSize(700, 65);
		tile2.setMinSize(700, 65);
		tile2.setMaxSize(700, 65);
		tile2.setPadding(new Insets(30));
		tile2.setHgap(95);
		tile2.getChildren().addAll(nomJoueur2, score2, nbPersVivant2, nbZbTue2);

		TilePane tile3 = new TilePane();
		tile3.setAlignment(Pos.CENTER_LEFT);
		tile3.setStyle(styleVBox);
		tile3.setPrefSize(700, 65);
		tile3.setMinSize(700, 65);
		tile3.setMaxSize(700, 65);
		tile3.setPadding(new Insets(30));
		tile3.setHgap(95);
		tile3.getChildren().addAll(nomJoueur3, score3, nbPersVivant3, nbZbTue3);

		TilePane tile4 = new TilePane();
		tile4.setAlignment(Pos.CENTER_LEFT);
		tile4.setStyle(styleVBox);
		tile4.setPrefSize(700, 65);
		tile4.setMinSize(700, 65);
		tile4.setMaxSize(700, 65);
		tile4.setPadding(new Insets(30));
		tile4.setHgap(95);
		tile4.getChildren().addAll(nomJoueur4, score4, nbPersVivant4, nbZbTue4);

		TilePane tile5 = new TilePane();
		tile5.setAlignment(Pos.CENTER_LEFT);
		tile5.setStyle(styleVBox);
		tile5.setPrefSize(700, 65);
		tile5.setMinSize(700, 65);
		tile5.setMaxSize(700, 65);
		tile5.setPadding(new Insets(30));
		tile5.setHgap(95);
		tile5.getChildren().addAll(nomJoueur5, score5, nbPersVivant5, nbZbTue5);

		TilePane tile6 = new TilePane();
		tile6.setAlignment(Pos.CENTER_LEFT);
		tile6.setStyle(styleVBox);
		tile6.setPrefSize(700, 65);
		tile6.setMinSize(700, 65);
		tile6.setMaxSize(700, 65);
		tile6.setPadding(new Insets(30));
		tile6.setHgap(95);
		tile6.getChildren().addAll(nomJoueur6, score6, nbPersVivant6, nbZbTue6);

		VBox vbScore = new VBox();
		vbScore.setAlignment(Pos.TOP_CENTER);
		vbScore.setSpacing(-13);
		vbScore.setPrefSize(700, 330);
		vbScore.setMinSize(700, 330);
		vbScore.setMaxSize(700, 330);

		TilePane tileTitreScore = new TilePane();
		tileTitreScore.setAlignment(Pos.CENTER_LEFT);
		tileTitreScore.setStyle("-fx-border-color: #1A1A1A; -fx-border-width: 3; -fx-background-color: black;");
		tileTitreScore.setOpacity(.7);
		tileTitreScore.setPrefSize(690, 60);
		tileTitreScore.setMinSize(690, 60);
		tileTitreScore.setMaxSize(690, 60);
		tileTitreScore.setHgap(40);
		tileTitreScore.setVgap(0);
		tileTitreScore.getChildren().addAll(titreJoueur, titreScore, titreNbPersVivant, titreNbZbTues);

		VBox vbScoreBoard = new VBox();
		vbScoreBoard.setAlignment(Pos.TOP_CENTER);
		vbScoreBoard.setSpacing(-7);
		vbScoreBoard.setPrefSize(700, 390);
		vbScoreBoard.setMinSize(700, 390);
		vbScoreBoard.setMaxSize(700, 390);
		vbScore.getChildren().addAll(tile1, tile2, tile3, tile4, tile5, tile6);
		vbScoreBoard.getChildren().addAll(tileTitreScore, vbScore);*/

		vbCenter.getChildren().addAll(desc/*, vbScoreBoard*/);

		// auteur florian 
		// bouton
		bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setPrefSize(lBouton, hBouton);
		bRetour.setMinSize(lBouton, hBouton);
		bRetour.setFont(policeBouton);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnMouseEntered(event -> bRetour.setStyle(styleBoutonsSouris));
		bRetour.setOnMouseExited(event -> bRetour.setStyle(styleBoutons));
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(core.getReglesDepuis()));

		// grille contenant les boutons du bas
		AnchorPane boutonsPanneau = new AnchorPane();
		boutonsPanneau.setLeftAnchor(bRetour, 0.0);
		boutonsPanneau.getChildren().addAll(bRetour);

		// image fond
		ImageView imgFond = new ImageView(DataControl.FOND);

		// carre central qui contient tous les éléments (boutons et titre)
		BorderPane centreMenu = new BorderPane();
		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));
		centreMenu.setAlignment(titre, Pos.CENTER);
		centreMenu.setTop(titre);
		centreMenu.setCenter(vbCenter);
		centreMenu.setBottom(boutonsPanneau);

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

	/**
	 * Affiche le gagnant
	 * @author sebastien 
	 * @param nom le nom du joueur
	 */
	@Override
	public void gagnant(String nom) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (nom.equals(core.getIdjr().getNom()))
					desc.setText(International.trad("text.gagne"));
				else
					desc.setText(International.trad("text.perd1") + "\n" + International.trad("text.perd2")+nom);
			}
		});
	}

	/**
	 * Traduit les elements de ce pane
	 */
	@Override
	public void traduire() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				titre1.setText(International.trad("text.titreFinDePartieA") + " "
						+ International.trad("text.titreFinDePartieB"));
				/*nomJoueur1.setText(International.trad("texte.j1"));
				nomJoueur2.setText(International.trad("texte.j2"));
				nomJoueur3.setText(International.trad("texte.j3"));
				nomJoueur4.setText(International.trad("texte.j4"));
				nomJoueur5.setText(International.trad("texte.j5"));
				nomJoueur6.setText(International.trad("texte.j6"));*/
				titreJoueur.setText(International.trad("texte.nom"));
				titreScore.setText(International.trad("texte.score"));
				titreNbPersVivant.setText(International.trad("texte.alive"));
				titreNbZbTues.setText(International.trad("texte.zTues"));
				bRetour.setText(International.trad("bouton.retour"));
			}
		});
	}
}