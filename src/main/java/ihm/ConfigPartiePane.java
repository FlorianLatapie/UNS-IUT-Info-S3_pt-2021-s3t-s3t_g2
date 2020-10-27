package ihm;

import ihm.ScreenControl;
import controleur.ControleurJeu;
import ihm.DataControl.ApplicationPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The Class AccueilPane.
 * 
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class ConfigPartiePane extends StackPane {
	// private ControleurJeu cj = new ControleurJeu(); // mettre ne paramètres les
	// joueurs

	private ScreenControl sControl = null;
	private final ApplicationPane paneName = ApplicationPane.CONFIG;
	// définition des variable pour la suite du pane
	private int tailleCarreCentral = 800; // l'interface est sur un stackPane qui peut tourner avec des crans de 90
											// degrés
	private int hBouton = 75;
	private int lBouton = 150;
	private int marge = tailleCarreCentral / 25;
	private Insets margeBoutons = new Insets(marge, marge, marge, marge);
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
	private CornerRadii coin = new CornerRadii(15.0);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);

	private Font policeNom = Font.font("Segoe UI", 17);
	private int hauteurElemtents = 60;
	private int largeurTF = 250;
	private int largeurComboBox= 250;
	private int spacing = 30;
	private CornerRadii coinfb = new CornerRadii(5.0);
	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

	private Insets botPadding = new Insets(0, 10, 0, 10);

	public ConfigPartiePane(ScreenControl sc) {

		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

		// titre
		Label titre1 = new Label("Configuration de \n\tla partie");
		titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

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
		bJouer.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PLATEAU));

		Button bRetour = new Button("RETOUR");
		bRetour.setPrefSize(lBouton, hBouton);
		bRetour.setMinSize(lBouton, hBouton);
		bRetour.setFont(policeBouton);
		bRetour.setStyle(styleBoutons);

		bRetour.setOnMouseEntered(event -> {
			bRetour.setStyle(styleBoutonsSouris);
		});
		bRetour.setOnMouseExited(event -> {
			bRetour.setStyle(styleBoutons);
		});
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

		// grille contenant les boutons du bas
		AnchorPane boutonsPanneau = new AnchorPane();
		boutonsPanneau.setLeftAnchor(bRetour, 0.0);
		boutonsPanneau.setRightAnchor(bJouer, 0.0);
		boutonsPanneau.getChildren().addAll(bRetour, bJouer);

		// configuration des joueurs

		// ------------------Menu joueur---------------
		VBox vJoueurs = new VBox();
		// vJoueurs.setAlignment(Pos.CENTER);

		

		// --box joueur--

		HBox j1 = new HBox();

		TextField nom1 = new TextField();
		nom1.setText("Joueur 1");
		nom1.setFont(policeNom);
		nom1.setPrefSize(largeurTF, hauteurElemtents);
		nom1.setMinHeight(hauteurElemtents);
		ComboBox<String> couleur1 = new ComboBox<String>();
		couleur1.setMinHeight(hauteurElemtents);
		couleur1.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur1.getItems().addAll(DataControl.couleursJoueur);

		HBox hBot1 = new HBox();
		Label lBot1 = new Label("Bot ");
		lBot1.setFont(policeNom);
		lBot1.setMinHeight(hauteurElemtents);
		CheckBox estBot1 = new CheckBox();
		estBot1.setMinHeight(hauteurElemtents);
		estBot1.setSelected(false);

		hBot1.setBackground(fondBlanc);
		hBot1.setSpacing(spacing);
		hBot1.setPadding(botPadding);
		hBot1.getChildren().addAll(lBot1, estBot1);

		j1.setAlignment(Pos.CENTER);
		j1.setSpacing(spacing);
		j1.getChildren().addAll(nom1, couleur1, hBot1);
		j1.setDisable(false);

		///

		HBox j2 = new HBox();

		TextField nom2 = new TextField();
		nom2.setText("Joueur 2");
		nom2.setFont(policeNom);
		nom2.setPrefSize(largeurTF, hauteurElemtents);
		nom2.setMinHeight(hauteurElemtents);
		ComboBox<String> couleur2 = new ComboBox<String>();
		couleur2.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur2.setMinHeight(hauteurElemtents);
		couleur2.getItems().addAll(DataControl.couleursJoueur);

		HBox hBot2 = new HBox();
		Label lBot2 = new Label("Bot ");
		lBot2.setFont(policeNom);
		lBot2.setMinHeight(hauteurElemtents);
		CheckBox estBot2 = new CheckBox();
		estBot2.setMinHeight(hauteurElemtents);
		estBot2.setSelected(false);

		hBot2.setBackground(fondBlanc);
		hBot2.setSpacing(spacing);
		hBot2.setPadding(botPadding);
		hBot2.getChildren().addAll(lBot2, estBot2);

		j2.setAlignment(Pos.CENTER);
		j2.setSpacing(spacing);
		j2.getChildren().addAll(nom2, couleur2, hBot2);
		j2.setDisable(false);

		///

		HBox j3 = new HBox();

		TextField nom3 = new TextField();
		nom3.setText("Joueur 3");
		nom3.setFont(policeNom);
		nom3.setPrefSize(largeurTF, hauteurElemtents);
		nom3.setMinHeight(hauteurElemtents);
		ComboBox<String> couleur3 = new ComboBox<String>();
		couleur3.setMinHeight(hauteurElemtents);
		couleur3.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur3.getItems().addAll(DataControl.couleursJoueur);

		HBox hBot3 = new HBox();
		Label lBot3 = new Label("Bot ");
		lBot3.setFont(policeNom);
		lBot3.setMinHeight(hauteurElemtents);
		CheckBox estBot3 = new CheckBox();
		estBot3.setMinHeight(hauteurElemtents);
		estBot3.setSelected(false);

		hBot3.setBackground(fondBlanc);
		hBot3.setSpacing(spacing);
		hBot3.setPadding(botPadding);
		hBot3.getChildren().addAll(lBot3, estBot3);

		j3.setAlignment(Pos.CENTER);
		j3.setSpacing(spacing);
		j3.getChildren().addAll(nom3, couleur3, hBot3);
		j3.setDisable(false);

		///

		HBox j4 = new HBox();

		TextField nom4 = new TextField();
		nom4.setText("Joueur 4");
		nom4.setFont(policeNom);
		nom4.setPrefSize(largeurTF, hauteurElemtents);
		nom4.setMinHeight(hauteurElemtents);
		ComboBox<String> couleur4 = new ComboBox<String>();
		couleur4.setMinHeight(hauteurElemtents);
		couleur4.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur4.getItems().addAll(DataControl.couleursJoueur);

		HBox hBot4 = new HBox();
		Label lBot4 = new Label("Bot ");
		lBot4.setFont(policeNom);
		lBot4.setMinHeight(hauteurElemtents);
		CheckBox estBot4 = new CheckBox();
		estBot4.setMinHeight(hauteurElemtents);
		estBot4.setSelected(false);

		hBot4.setBackground(fondBlanc);
		hBot4.setSpacing(spacing);
		hBot4.setPadding(botPadding);
		hBot4.getChildren().addAll(lBot4, estBot4);

		j4.setAlignment(Pos.CENTER);
		j4.setSpacing(spacing);
		j4.getChildren().addAll(nom4, couleur4, hBot4);
		j4.setDisable(false);

		///

		HBox j5 = new HBox();

		TextField nom5 = new TextField();
		nom5.setText("Joueur 5");
		nom5.setFont(policeNom);
		nom5.setPrefSize(largeurTF, hauteurElemtents);
		nom5.setMinHeight(hauteurElemtents);
		ComboBox<String> couleur5 = new ComboBox<String>();
		couleur5.setMinHeight(hauteurElemtents);
		couleur5.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur5.getItems().addAll(DataControl.couleursJoueur);

		HBox hBot5 = new HBox();
		Label lBot5 = new Label("Bot ");
		lBot5.setFont(policeNom);
		lBot5.setMinHeight(hauteurElemtents);
		CheckBox estBot5 = new CheckBox();
		estBot5.setMinHeight(hauteurElemtents);
		estBot5.setSelected(false);

		hBot5.setBackground(fondBlanc);
		hBot5.setSpacing(spacing);
		hBot5.setPadding(botPadding);
		hBot5.getChildren().addAll(lBot5, estBot5);

		j5.setAlignment(Pos.CENTER);
		j5.setSpacing(spacing);
		j5.getChildren().addAll(nom5, couleur5, hBot5);
		j5.setDisable(false);

		///

		HBox j6 = new HBox();

		TextField nom6 = new TextField();
		nom6.setText("Joueur 6");
		nom6.setFont(policeNom);
		nom6.setPrefSize(largeurTF, hauteurElemtents);
		nom6.setMinHeight(hauteurElemtents);
		ComboBox<String> couleur6 = new ComboBox<String>();
		couleur6.setMinHeight(hauteurElemtents);
		couleur6.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur6.getItems().addAll(DataControl.couleursJoueur);

		HBox hBot6 = new HBox();
		Label lBot6 = new Label("Bot ");
		lBot6.setFont(policeNom);
		lBot6.setMinHeight(hauteurElemtents);
		CheckBox estBot6 = new CheckBox();
		estBot6.setMinHeight(hauteurElemtents);
		estBot6.setSelected(false);

		hBot6.setBackground(fondBlanc);
		hBot6.setSpacing(spacing);
		hBot6.setPadding(botPadding);
		hBot6.getChildren().addAll(lBot6, estBot6);

		j6.setAlignment(Pos.CENTER);
		j6.setSpacing(spacing);
		j6.getChildren().addAll(nom6, couleur6, hBot6);
		j6.setDisable(true);

		///

		vJoueurs.setSpacing(14);
		vJoueurs.getChildren().addAll(j1, j2, j3, j4, j5, j6);
		// vJoueurs.setBackground(new Background(new
		// BackgroundFill(Color.BLUE,CornerRadii.EMPTY,null)));

		VBox vbCenter = new VBox();
		vbCenter.setMargin(vJoueurs, new Insets(0, 0, 100, 0));
		vbCenter.getChildren().addAll(vJoueurs);

		// image fond
		ImageView imgFond = new ImageView(DataControl.PLATEAU);
		// carre central qui contient tous les éléments (boutons et titre)
		BorderPane centreMenu = new BorderPane();
		// centreMenu.setBackground(new Background(new
		// BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,null)));
		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));

		centreMenu.setAlignment(titre, Pos.CENTER);

		centreMenu.setTop(titre);
		centreMenu.setCenter(vbCenter);
		centreMenu.setBottom(boutonsPanneau);

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
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}
}