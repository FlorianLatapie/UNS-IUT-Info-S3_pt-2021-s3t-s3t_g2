package pp.ihm;

import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.langues.International;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class ConfigBotPane extends StackPane {

	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.CONFIG_BOT;

	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	private int hauteurElemtents = 60;
	private int largeurTF = 100;
	private int largeurTexte = 100;
	private int spacing = 30;

	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
	private Font policeNom = Font.font("Segoe UI", 17);

	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private String styleLabelBot = "-fx-background-color: #4C4C4C; -fx-background-radius: 5px;";

	private GaussianBlur flou = new GaussianBlur(30);
	private CornerRadii coin = new CornerRadii(15.0);
	private CornerRadii coinfb = new CornerRadii(5.0);

	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));
	private String styleVBox = "-fx-border-color: black; -fx-border-insets: 5; -fx-border-width: 3;";
	private Insets botPadding = new Insets(0, 10, 0, 10);

	HBox bot1 = new HBox();
	HBox bot2 = new HBox();
	HBox bot3 = new HBox();
	HBox bot4 = new HBox();
	HBox bot5 = new HBox();
	HBox bot6 = new HBox();

	public ConfigBotPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		// titre
		Label titre1 = new Label("Configuration\ndes bots");// TODO
		titre1.setTextAlignment(TextAlignment.CENTER);
		titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		// texte
		/*
		 * Label desc = new Label("Choisissez un nombre de joueurs entre 3 et 6");
		 * desc.setFont(policeNom); desc.setMinHeight(hauteurElemtents);
		 * desc.setBackground(fondBlanc); desc.setPadding(botPadding);
		 */

		VBox vBots = new VBox();

		///

		bot1 = new HBox();

		Label bot1Texte = new Label("Bot1");
		bot1Texte.setAlignment(Pos.CENTER);
		bot1Texte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 23));
		bot1Texte.setTextFill(Color.WHITE);
		bot1Texte.setMinHeight(hauteurElemtents);
		bot1Texte.setStyle(styleLabelBot);
		bot1Texte.setPadding(botPadding);
		bot1Texte.setMinWidth(largeurTexte);

		ComboBox<String> diffBot1 = new ComboBox<>();
		diffBot1.setPadding(new Insets(7));
		diffBot1.setBlendMode(BlendMode.HARD_LIGHT);
		diffBot1.setItems(DataControl.difficulteBot);
		diffBot1.setValue(International.trad("texte.valueFaible"));
		diffBot1.setPrefSize(largeurTF, hauteurElemtents);
		diffBot1.setMinHeight(hauteurElemtents);		

		bot1.setAlignment(Pos.CENTER);
		bot1.setSpacing(spacing);
		bot1.getChildren().addAll(bot1Texte, diffBot1);
		bot1.setVisible(true);

		///

		bot2 = new HBox();

		Label bot2Texte = new Label("Bot2");
		bot2Texte.setAlignment(Pos.CENTER);
		bot2Texte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 23));
		bot2Texte.setTextFill(Color.WHITE);
		bot2Texte.setMinHeight(hauteurElemtents);		
		bot2Texte.setPadding(botPadding);
		bot2Texte.setStyle(styleLabelBot);
		bot2Texte.setMinWidth(largeurTexte);

		ComboBox<String> diffBot2 = new ComboBox<>();
		diffBot2.getItems().addAll(DataControl.difficulteBot);
		diffBot2.setBlendMode(BlendMode.HARD_LIGHT);
		diffBot2.setPadding(new Insets(7));
		diffBot2.setValue(International.trad("texte.valueFaible"));
		diffBot2.setPrefSize(largeurTF, hauteurElemtents);
		diffBot2.setMinHeight(hauteurElemtents);

		bot2.setAlignment(Pos.CENTER);
		bot2.setSpacing(spacing);
		bot2.getChildren().addAll(bot2Texte, diffBot2);
		bot2.setVisible(true);

		///

		bot3 = new HBox();

		Label bot3Texte = new Label("Bot3");
		bot3Texte.setAlignment(Pos.CENTER);
		bot3Texte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 23));
		bot3Texte.setTextFill(Color.WHITE);
		bot3Texte.setMinHeight(hauteurElemtents);		
		bot3Texte.setPadding(botPadding);
		bot3Texte.setStyle(styleLabelBot);
		bot3Texte.setMinWidth(largeurTexte);

		ComboBox<String> diffBot3 = new ComboBox<>();
		diffBot3.getItems().addAll(DataControl.difficulteBot);
		diffBot3.setBlendMode(BlendMode.HARD_LIGHT);
		diffBot3.setPadding(new Insets(7));
		diffBot3.setValue(International.trad("texte.valueFaible"));
		diffBot3.setPrefSize(largeurTF, hauteurElemtents);
		diffBot3.setMinHeight(hauteurElemtents);

		bot3.setAlignment(Pos.CENTER);
		bot3.setSpacing(spacing);
		bot3.getChildren().addAll(bot3Texte, diffBot3);
		bot3.setVisible(true);

		///

		bot4 = new HBox();

		Label bot4Texte = new Label("Bot4");
		bot4Texte.setAlignment(Pos.CENTER);
		bot4Texte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 23));
		bot4Texte.setTextFill(Color.WHITE);
		bot4Texte.setMinHeight(hauteurElemtents);
		bot4Texte.setStyle(styleLabelBot);
		bot4Texte.setPadding(botPadding);
		bot4Texte.setMinWidth(largeurTexte);

		ComboBox<String> diffBot4 = new ComboBox<>();
		diffBot4.getItems().addAll(DataControl.difficulteBot);
		diffBot4.setBlendMode(BlendMode.HARD_LIGHT);
		diffBot4.setPadding(new Insets(7));
		diffBot4.setValue(International.trad("texte.valueFaible"));
		diffBot4.setPrefSize(largeurTF, hauteurElemtents);
		diffBot4.setMinHeight(hauteurElemtents);

		bot4.setAlignment(Pos.CENTER);
		bot4.setSpacing(spacing);
		bot4.getChildren().addAll(bot4Texte, diffBot4);
		bot4.setVisible(true);

		///

		bot5 = new HBox();

		Label bot5Texte = new Label("Bot5");
		bot5Texte.setAlignment(Pos.CENTER);
		bot5Texte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 23));
		bot5Texte.setTextFill(Color.WHITE);
		bot5Texte.setMinHeight(hauteurElemtents);
		bot5Texte.setStyle(styleLabelBot);
		bot5Texte.setPadding(botPadding);
		bot5Texte.setMinWidth(largeurTexte);

		ComboBox<String> diffBot5 = new ComboBox<>();
		diffBot5.getItems().addAll(DataControl.difficulteBot);
		diffBot5.setBlendMode(BlendMode.HARD_LIGHT);
		diffBot5.setPadding(new Insets(7));
		diffBot5.setValue(International.trad("texte.valueFaible"));
		diffBot5.setPrefSize(largeurTF, hauteurElemtents);
		diffBot5.setMinHeight(hauteurElemtents);

		bot5.setAlignment(Pos.CENTER);
		bot5.setSpacing(spacing);
		bot5.getChildren().addAll(bot5Texte, diffBot5);
		bot5.setVisible(true);

		///

		bot6 = new HBox();

		Label bot6Texte = new Label("Bot6");
		bot6Texte.setAlignment(Pos.CENTER);
		bot6Texte.setFont(Font.font("Segoe UI", FontWeight.BOLD, 23));
		bot6Texte.setTextFill(Color.WHITE);
		bot6Texte.setMinHeight(hauteurElemtents);
		bot6Texte.setStyle(styleLabelBot);
		bot6Texte.setPadding(botPadding);
		bot6Texte.setMinWidth(largeurTexte);

		ComboBox<String> diffBot6 = new ComboBox<>();
		diffBot6.getItems().addAll(DataControl.difficulteBot);
		diffBot6.setBlendMode(BlendMode.HARD_LIGHT);
		diffBot6.setPadding(new Insets(7));
		diffBot6.setValue(International.trad("texte.valueFaible"));
		diffBot6.setPrefSize(largeurTF, hauteurElemtents);
		diffBot6.setMinHeight(hauteurElemtents);

		bot6.setAlignment(Pos.CENTER);
		bot6.setSpacing(spacing);
		bot6.getChildren().addAll(bot6Texte, diffBot6);
		bot6.setVisible(true);

		vBots.setSpacing(spacing / 2.0);
		vBots.getChildren().addAll(bot1, bot2, bot3, bot4, bot5, bot6);

		VBox vbCenter = new VBox();
		vbCenter.setMargin(vBots, new Insets(0, 0, 100, 0));
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setSpacing(spacing);
		vbCenter.getChildren().addAll(vBots);
		// boutons
		Button bJouer = new Button(International.trad("bouton.jouer"));
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> bJouer.setStyle(styleBoutonsSouris));
		bJouer.setOnMouseExited(event -> bJouer.setStyle(styleBoutons));
		bJouer.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.WAIT));

		Button bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setPrefSize(lBouton, hBouton);
		bRetour.setMinSize(lBouton, hBouton);
		bRetour.setFont(policeBouton);
		bRetour.setStyle(styleBoutons);

		bRetour.setOnMouseEntered(event -> bRetour.setStyle(styleBoutonsSouris));
		bRetour.setOnMouseExited(event -> bRetour.setStyle(styleBoutons));
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));

		// grille contenant les boutons du bas

		AnchorPane boutonsPanneau = new AnchorPane();
		boutonsPanneau.setLeftAnchor(bRetour, 0.0);
		boutonsPanneau.setRightAnchor(bJouer, 0.0);
		boutonsPanneau.getChildren().addAll(bRetour, bJouer);

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

		// boite du fond qui contient le fond et les autres boites
		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(fond, centreMenu, bEcranDroite, bEcranHaut, bEcranGauche, bEcranBas);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}
}