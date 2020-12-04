package pp.ihm;

import pp.controleur.ControleurJeu;
import pp.ihm.DataControl.ApplicationPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class ConfigPartiePane extends StackPane {

	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.CONFIG;

	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	private int hauteurElemtents = 60;
	private int largeurTF = 100;
	private int largeurTexte = 220;
	private int spacing = 30;

	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
	private Font policeNom = Font.font("Segoe UI", 17);

	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";

	private GaussianBlur flou = new GaussianBlur(30);
	private CornerRadii coin = new CornerRadii(15.0);
	private CornerRadii coinfb = new CornerRadii(5.0);

	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

	private Insets botPadding = new Insets(0, 10, 0, 10);

	public ConfigPartiePane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		// titre
		Label titre1 = new Label("Configuration de \nla partie");
		titre1.setTextAlignment(TextAlignment.CENTER);
		titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		// texte
		Label desc = new Label("Choisissez un nombre de joueurs entre 3 et 6");
		desc.setFont(Font.font("Segoe UI", 30));
		desc.setMinHeight(hauteurElemtents);
		desc.setBackground(new Background(new BackgroundFill(Color.WHITE, coin, null)));
		desc.setPadding(new Insets(0, 20, 0, 20));

		VBox vJoueurs = new VBox();
		HBox nomPartie = new HBox();

		Label nomPTexte = new Label("Identifiant de la partie");
		nomPTexte.setFont(policeNom);
		nomPTexte.setMinHeight(hauteurElemtents);
		nomPTexte.setBackground(fondBlanc);
		nomPTexte.setPadding(botPadding);
		nomPTexte.setMinWidth(largeurTexte);

		TextField nomP = new TextField();
		nomP.setText("Partie" + (int) (100 * Math.random()));
		nomP.setFont(policeNom);
		nomP.setPrefSize(largeurTF, hauteurElemtents);
		nomP.setMinHeight(hauteurElemtents);

		nomPartie.setAlignment(Pos.CENTER);
		nomPartie.setSpacing(spacing);
		nomPartie.getChildren().addAll(nomPTexte, nomP);
		nomPartie.setDisable(false);

		//
		HBox nbTotJr = new HBox();

		Label nbjrTexte = new Label("Nombre de joueurs : ");
		nbjrTexte.setFont(policeNom);
		nbjrTexte.setMinHeight(hauteurElemtents);
		nbjrTexte.setBackground(fondBlanc);
		nbjrTexte.setPadding(botPadding);
		nbjrTexte.setMinWidth(largeurTexte);

		ComboBox<Integer> nbJr = new ComboBox<Integer>();
		nbJr.getItems().addAll(DataControl.nombreJoueur);
		nbJr.setValue(5);
		nbJr.setPrefSize(largeurTF, hauteurElemtents);
		nbJr.setMinHeight(hauteurElemtents);

		nbTotJr.setAlignment(Pos.CENTER);
		nbTotJr.setSpacing(spacing);
		nbTotJr.getChildren().addAll(nbjrTexte, nbJr);
		nbTotJr.setDisable(false);

		///

		HBox nbTotBot = new HBox();

		Label nbBotTexte = new Label("Nombre de Bots : ");
		nbBotTexte.setFont(policeNom);
		nbBotTexte.setMinHeight(hauteurElemtents);
		nbBotTexte.setBackground(fondBlanc);
		nbBotTexte.setPadding(botPadding);
		nbBotTexte.setMinWidth(largeurTexte);

		ComboBox<Integer> nbBot = new ComboBox<>();
		nbBot.getItems().addAll(DataControl.nombreBot);
		nbBot.setValue(5);
		nbBot.setPrefSize(largeurTF, hauteurElemtents);
		nbBot.setMinHeight(hauteurElemtents);

		nbTotBot.setAlignment(Pos.CENTER);
		nbTotBot.setSpacing(spacing);
		nbTotBot.getChildren().addAll(nbBotTexte, nbBot);
		nbTotBot.setDisable(false);

		vJoueurs.setSpacing(spacing / 2.0);
		vJoueurs.getChildren().addAll(nomPartie, nbTotJr, nbTotBot);

		VBox vbCenter = new VBox();
		vbCenter.setMargin(vJoueurs, new Insets(0, 0, 100, 0));
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setSpacing(spacing);
		vbCenter.getChildren().addAll(desc, vJoueurs);

		// boutons
		Button bJouer = new Button("JOUER");
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> bJouer.setStyle(styleBoutonsSouris));
		bJouer.setOnMouseExited(event -> bJouer.setStyle(styleBoutons));
		bJouer.setOnAction(EventHandler -> {
			// TODO on lance la partie ici ou au pane suivant (config bot)
			if (nbJr.getValue() > 6 || nbJr.getValue() < 3) {
				core.setNbJoueur(5);
			} else {
				core.setNbJoueur(nbJr.getValue());
			}

			if (nbBot.getValue() > 6 || nbBot.getValue() < 0 || nbBot.getValue() > nbJr.getValue()) {
				core.setNbBot(core.getNbJoueur());
			} else {
				core.setNbBot(nbBot.getValue());
			}
			core.setNomPartie(nomP.getText());
			try {
				core.setCj(new ControleurJeu(core.getNomPartie(), core.getNbJoueurReel(), core.getNbBot(),
						core.getInitializer()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sc.setPaneOnTop(ApplicationPane.WAIT);
		});

		Button bRetour = new Button("RETOUR");
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