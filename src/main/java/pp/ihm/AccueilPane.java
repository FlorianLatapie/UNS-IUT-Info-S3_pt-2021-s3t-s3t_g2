package pp.ihm;

import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.langues.ITraduction;
import pp.ihm.langues.International;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
public class AccueilPane extends StackPane implements ITraduction {

	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.ACCUEIL;

	private int tailleCarreCentral = 600;

	private int hBouton = 100;
	private int lBouton = 200;
	private int marge = tailleCarreCentral / 25;
	private Insets margeBoutons = new Insets(marge, marge, marge, marge);

	private String nomPolice = "Segoe UI";
	private Font policeBouton = Font.font(nomPolice, FontWeight.BOLD, 33);

	private CornerRadii coin = new CornerRadii(15.0);

	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";

	private GaussianBlur flou = new GaussianBlur(30);

	VBox centreMenu;
	Label titre1;
	Label titre2;
	Button bJouer;
	Button bOptions;
	Button bRegles;
	Button bQuitter;

	public AccueilPane(ScreenControl sc, Core c) {

		core = c;
		sControl = sc;

		// titre
		titre1 = new Label(International.trad("texte.preTitre"));
		titre1.setFont(Font.font(nomPolice, FontWeight.BOLD, 160));
		titre1.setTextFill(Color.BLACK);

		titre2 = new Label(International.trad("texte.titrePP"));
		titre2.setFont(Font.font(nomPolice, 35));
		titre2.setTextFill(Color.BLACK);
		titre2.setPadding(new Insets(0, 0, 20, 0));

		VBox titre = new VBox(titre1, titre2);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(800);
		titre.setMinWidth(800);

		// boutons
		bJouer = new Button(International.trad("bouton.jouer"));
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> bJouer.setStyle(styleBoutonsSouris));
		bJouer.setOnMouseExited(event -> bJouer.setStyle(styleBoutons));
		bJouer.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.CONFIG));

		bOptions = new Button(International.trad("bouton.options"));
		bOptions.setPrefSize(lBouton, hBouton);
		bOptions.setMinSize(lBouton, hBouton);
		bOptions.setFont(policeBouton);
		bOptions.setStyle(styleBoutons);

		bOptions.setOnMouseEntered(event -> bOptions.setStyle(styleBoutonsSouris));
		bOptions.setOnMouseExited(event -> bOptions.setStyle(styleBoutons));
		bOptions.setOnAction(EventHandler -> {
			core.setPauseDepuis(paneName);
			sc.setPaneOnTop(ApplicationPane.OPTION);
		});

		bRegles = new Button(International.trad("bouton.regles"));
		bRegles.setPrefSize(lBouton, hBouton);
		bRegles.setMinSize(lBouton, hBouton);
		bRegles.setFont(policeBouton);
		bRegles.setStyle(styleBoutons);

		bRegles.setOnMouseEntered(event -> bRegles.setStyle(styleBoutonsSouris));
		bRegles.setOnMouseExited(event -> bRegles.setStyle(styleBoutons));
		bRegles.setOnAction(EventHandler -> {
			core.setReglesDepuis(paneName);
			sc.setPaneOnTop(ApplicationPane.REGLES);
		});

		bQuitter = new Button(International.trad("bouton.quitter"));
		bQuitter.setPrefSize(lBouton, hBouton);
		bQuitter.setMinSize(lBouton, hBouton);
		bQuitter.setFont(policeBouton);
		bQuitter.setStyle(styleBoutons);

		bQuitter.setOnMouseEntered(event -> bQuitter.setStyle(styleBoutonsSouris));
		bQuitter.setOnMouseExited(event -> bQuitter.setStyle(styleBoutons));
		bQuitter.setOnAction(event -> {
			boolean resultat = ConfirmationPane.afficher(International.trad("texte.confirmationTitre"),
					International.trad("texte.confirmationL1")+"\n"+International.trad("texte.confirmationL2"));
			if (resultat) {
				Platform.exit();
				System.exit(0);
			}
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
		centreMenu = new VBox();

		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setAlignment(Pos.CENTER);
		centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));
		centreMenu.getChildren().addAll(titre, grilleBoutons);

		// Boutons de rotation d'écran
		ImageView img1 = new ImageView(DataControl.SCREEN);
		img1.setFitHeight(70);
		img1.setPreserveRatio(true);
		ImageView img2 = new ImageView(DataControl.SCREEN);
		img2.setFitHeight(70);
		img2.setPreserveRatio(true);
		ImageView img3 = new ImageView(DataControl.SCREEN);
		img3.setFitHeight(70);
		img3.setPreserveRatio(true);
		ImageView img4 = new ImageView(DataControl.SCREEN);
		img4.setFitHeight(70);
		img4.setPreserveRatio(true);

		Button bEcranHaut = new Button();
		bEcranHaut.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranHaut.setAlignment(Pos.CENTER);
		bEcranHaut.setTranslateY(-490);
		bEcranHaut.setPrefSize(80, 80);
		bEcranHaut.setRotate(180);
		bEcranHaut.setGraphic(img1);
		bEcranHaut.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "haut"));

		Button bEcranBas = new Button();
		bEcranBas.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranBas.setAlignment(Pos.CENTER);
		bEcranBas.setTranslateY(490);
		bEcranBas.setPrefSize(80, 80);
		bEcranBas.setGraphic(img2);
		bEcranBas.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "bas"));

		Button bEcranGauche = new Button();
		bEcranGauche.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranGauche.setAlignment(Pos.CENTER);
		bEcranGauche.setTranslateX(-910);
		bEcranGauche.setPrefSize(80, 80);
		bEcranGauche.setRotate(90);
		bEcranGauche.setGraphic(img3);
		bEcranGauche.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "gauche"));

		Button bEcranDroite = new Button();
		bEcranDroite.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranDroite.setAlignment(Pos.CENTER);
		bEcranDroite.setTranslateX(910);
		bEcranDroite.setRotate(-90);
		bEcranDroite.setPrefSize(80, 80);
		bEcranDroite.setGraphic(img4);
		bEcranDroite.setOnAction(EventHandler -> sc.setRotatePane(centreMenu, "droite"));

		// boite du fond qui contient l'image et les autres boites
		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		this.getChildren().addAll(fond, centreMenu, bEcranHaut, bEcranBas, bEcranGauche, bEcranDroite);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}
	/*
	 * public void changerAngle(double angle) { centreMenu.setRotate(angle); }
	 */

	@Override
	public void traduire() {
		titre1.setText(International.trad("texte.preTitre"));
		titre2.setText(International.trad("texte.titrePP"));
		bJouer.setText(International.trad("bouton.jouer"));
		bOptions.setText(International.trad("bouton.options"));
		bRegles.setText(International.trad("bouton.regles"));
		bQuitter.setText(International.trad("bouton.quitter"));

	}
}
