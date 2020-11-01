package ihm;

import ihm.ScreenControl;
import ihm.DataControl.ApplicationPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The Class AccueilPane.
 * 
 * @author Florian
 * @version 0.1
 * @since 04/10/2020
 */
public class AccueilPane extends StackPane {

	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.ACCUEIL;
	// définition des variable pour la suite du pane
	private int tailleCarreCentral = 600; // l'interface est sur un stackPane qui peut tourner avec des crans de 90
											// degrés
	private int hBouton = 100;
	private int lBouton = 200;
	private int marge = tailleCarreCentral / 25;
	private Insets margeBoutons = new Insets(marge, marge, marge, marge);
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);
	private CornerRadii coin = new CornerRadii(15.0);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);

	public AccueilPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

		// titre
		Label titre1 = new Label("ZOMBIES");
		titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 160));
		titre1.setTextFill(Color.BLACK);

		Label titre2 = new Label("LA BLONDE LA BRUTE ET LE TRUAND");
		titre2.setFont(Font.font("Segoe UI", 35));
		titre2.setTextFill(Color.BLACK);
		titre2.setPadding(new Insets(0, 0, 20, 0));

		VBox titre = new VBox(titre1, titre2);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(800);
		titre.setMinWidth(800);

		// boutons
		Button bJouer = new Button("JOUER");
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> {
			bJouer.setStyle(styleBoutonsSouris);
		});
		bJouer.setOnMouseExited(event -> {
			bJouer.setStyle(styleBoutons);
		});
		bJouer.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.CONFIG));

		Button bOptions = new Button("OPTIONS");
		bOptions.setPrefSize(lBouton, hBouton);
		bOptions.setMinSize(lBouton, hBouton);
		bOptions.setFont(policeBouton);
		bOptions.setStyle(styleBoutons);

		bOptions.setOnMouseEntered(event -> {
			bOptions.setStyle(styleBoutonsSouris);
		});
		bOptions.setOnMouseExited(event -> {
			bOptions.setStyle(styleBoutons);
		});
		bOptions.setOnAction(EventHandler -> {
			core.setPauseDepuis(paneName);
			sc.setPaneOnTop(ApplicationPane.OPTION);
		});

		Button bRegles = new Button("REGLES");
		bRegles.setPrefSize(lBouton, hBouton);
		bRegles.setMinSize(lBouton, hBouton);
		bRegles.setFont(policeBouton);
		bRegles.setStyle(styleBoutons);

		bRegles.setOnMouseEntered(event -> {
			bRegles.setStyle(styleBoutonsSouris);
		});
		bRegles.setOnMouseExited(event -> {
			bRegles.setStyle(styleBoutons);
		});
		bRegles.setOnAction(EventHandler -> {
			core.setReglesDepuis(paneName);
			sc.setPaneOnTop(ApplicationPane.REGLES);
		});

		Button bQuitter = new Button("QUITTER");
		bQuitter.setPrefSize(lBouton, hBouton);
		bQuitter.setMinSize(lBouton, hBouton);
		bQuitter.setFont(policeBouton);
		bQuitter.setStyle(styleBoutons);

		bQuitter.setOnMouseEntered(event -> {
			bQuitter.setStyle(styleBoutonsSouris);
		});
		bQuitter.setOnMouseExited(event -> {
			bQuitter.setStyle(styleBoutons);
		});
		bQuitter.setOnAction(event -> {
			boolean resultat = ConfirmationPane.afficher("Quitter le jeu",
					"Êtes-vous sûr de vouloir quitter le jeu ? \nSi vous quittez, la partie en cours sera perdue.");
			if (resultat)
				Platform.exit();
		});

		// grille contenant les 4 boutons
		GridPane grilleBoutons = new GridPane();
		grilleBoutons.setAlignment(Pos.CENTER);
		grilleBoutons.add(bJouer, 0, 0);
		grilleBoutons.add(bOptions, 0, 1);
		grilleBoutons.add(bRegles, 1, 0);
		grilleBoutons.add(bQuitter, 1, 1);

		grilleBoutons.setMargin(bJouer, margeBoutons);
		grilleBoutons.setMargin(bOptions, margeBoutons);
		grilleBoutons.setMargin(bRegles, margeBoutons);
		grilleBoutons.setMargin(bQuitter, margeBoutons);

		// image fond
		ImageView imgFond = new ImageView(DataControl.FOND);

		// carre central qui contient tous les éléments (boutons et titre)
		VBox centreMenu = new VBox();
		// centreMenu.setBackground(new Background(new
		// BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,null)));
		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setAlignment(Pos.CENTER);
		centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));
		// titre.setPadding(margeBoutons);
		centreMenu.getChildren().addAll(titre, grilleBoutons);

		// rotation de l'interface
		// centreMenu.setRotate(90);

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

}