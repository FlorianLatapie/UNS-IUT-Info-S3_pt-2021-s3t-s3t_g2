package pp.ihm;

import pp.controleur.ControleurJeu;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.event.Initializer;
import pp.ihm.langues.ITraduction;
import pp.ihm.langues.International;
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
import java.util.ArrayList;
import java.util.Random;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @author Remy 
 * @author Sebastien
 * @author Tom
 * 
 * @version 1.0
 * @since 26/10/2020
 */
public class ConfigPartiePane extends StackPane implements ITraduction {
	//auteur florian
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.CONFIG;

	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	private int hauteurElemtents = 60;
	private int largeurTexte = 220;
	private int spacing = 10;

	private String nomPolice = "Segoe UI";
	private Font policeTexte = Font.font(nomPolice, FontWeight.BOLD, 30);
	private Font policeBouton = Font.font(nomPolice, FontWeight.BOLD, 27);
	private Font policeNom = Font.font(nomPolice, 17);

	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private String styleVBox = "-fx-border-color: black; -fx-border-insets: 5; -fx-border-width: 3;";
	private GaussianBlur flou = new GaussianBlur(30);
	private CornerRadii coin = new CornerRadii(15.0);

	private Insets botPadding = new Insets(10);

	Label titre1;
	Label desc;
	Label nomPTexte;
	Label nbjrTexte;
	Label infoNomPartie;
	Label nbBotTexte;
	Button bJouer;
	Button bRetour;

	public ConfigPartiePane(ScreenControl sc, Core c) {
		//auteur florian
		core = c;
		sControl = sc;

		// titre
		titre1 = new Label(
				International.trad("texte.titreConfigPartieA") + "\n" + International.trad("texte.titreConfigPartieB"));
		titre1.setTextAlignment(TextAlignment.CENTER);
		titre1.setFont(Font.font(nomPolice, FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		// texte
		desc = new Label(International.trad("texte.descriptionConfigPartie"));
		desc.setFont(policeTexte);
		desc.setTextFill(Color.WHITE);
		desc.setMinHeight(hauteurElemtents);

		VBox vJoueurs = new VBox();
		vJoueurs.setAlignment(Pos.CENTER);
		vJoueurs.setPadding(botPadding);
		HBox nomPartie = new HBox();

		nomPTexte = new Label(International.trad("texte.idPartie"));
		nomPTexte.setFont(policeTexte);
		nomPTexte.setTextFill(Color.WHITE);
		nomPTexte.setStyle(styleVBox);
		nomPTexte.setMinHeight(hauteurElemtents);
		nomPTexte.setPadding(new Insets(10, 10, 10, 10));
		nomPTexte.setMinWidth(largeurTexte + 200.0);

		infoNomPartie = new Label();
		infoNomPartie.setTextAlignment(TextAlignment.JUSTIFY);
		infoNomPartie.setText(International.trad("texte.infoNom1") + "\n" + International.trad("texte.infoNom2") + "\n"
				+ International.trad("texte.infoNom3") + " " + International.trad("texte.infoNom4"));
		infoNomPartie.setMinHeight(100);
		infoNomPartie.setPrefSize(580, 100);
		infoNomPartie.setFont(policeNom);
		infoNomPartie.setPadding(new Insets(5, 10, 5, 10));
		infoNomPartie.setTextFill(Color.WHITE);
		//auteur remy
		TextField nomP = new TextField();
		nomP.setText("Partie" + (new Random().nextInt(100)));
		nomP.setFont(policeTexte);
		nomP.setStyle(
				"-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 3;");
		nomP.setBackground(null);
		nomP.setAlignment(Pos.CENTER);
		nomP.setPrefSize(160, 63);
		nomP.setMinHeight(63);

		nomPartie.setAlignment(Pos.CENTER);
		nomPartie.setSpacing(spacing);
		nomPartie.getChildren().addAll(nomPTexte, nomP);
		nomPartie.setDisable(false);

		//auteur florian
		HBox nbTotJr = new HBox();

		nbjrTexte = new Label(International.trad("texte.nbdeJr"));
		nbjrTexte.setFont(policeTexte);
		nbjrTexte.setTextFill(Color.WHITE);
		nbjrTexte.setStyle(styleVBox);
		nbjrTexte.setMinHeight(hauteurElemtents);
		nbjrTexte.setPadding(new Insets(10, 20, 10, 10));
		nbjrTexte.setMinWidth(420);
		nbjrTexte.setPrefWidth(420);
		
		ComboBox<Integer> nbJr = new ComboBox<>();
		nbJr.getItems().addAll(DataControl.nombreJoueur);
		nbJr.setValue(5);
		nbJr.setStyle("-fx-text-fill: white;");
		nbJr.setPadding(new Insets(0, 0, 0, 40));
		nbJr.setPrefSize(160, 63);
		nbJr.setMinHeight(63);

		nbTotJr.setAlignment(Pos.CENTER);
		nbTotJr.setSpacing(spacing);
		nbTotJr.getChildren().addAll(nbjrTexte, nbJr);
		nbTotJr.setDisable(false);

		///

		HBox nbTotBot = new HBox();

		nbBotTexte = new Label(International.trad("texte.nbdeBot"));
		nbBotTexte.setFont(policeTexte);
		nbBotTexte.setTextFill(Color.WHITE);
		nbBotTexte.setAlignment(Pos.CENTER_LEFT);
		nbBotTexte.setStyle(styleVBox);
		nbBotTexte.setMinHeight(hauteurElemtents);
		nbBotTexte.setPadding(new Insets(10, 10, 10, 10));
		nbBotTexte.setMinWidth(largeurTexte + 200.0);

		ComboBox<Integer> nbBot = new ComboBox<>();
		nbBot.getItems().addAll(DataControl.nombreBot);
		nbBot.setValue(5);
		nbBot.setPadding(new Insets(0, 0, 0, 40));
		nbBot.setPrefSize(160, 63);
		nbBot.setMinHeight(63);

		nbTotBot.setAlignment(Pos.CENTER);
		nbTotBot.setSpacing(spacing);
		nbTotBot.getChildren().addAll(nbBotTexte, nbBot);
		nbTotBot.setDisable(false);

		vJoueurs.setSpacing(spacing / 2.0);
		vJoueurs.getChildren().addAll(desc, infoNomPartie, nomPartie, nbTotJr, nbTotBot);

		VBox vbCenter = new VBox();
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setSpacing(spacing);
		vbCenter.getChildren().addAll(vJoueurs);

		// boutons
		bJouer = new Button(International.trad("bouton.jouer"));
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> bJouer.setStyle(styleBoutonsSouris));
		bJouer.setOnMouseExited(event -> bJouer.setStyle(styleBoutons));
		//auteur Sebastien
		bJouer.setOnAction(EventHandler -> {
			if (!IhmOutils.nomEstValide(nomP.getText()))
				return;

			if (nbJr.getValue() > 6 || nbJr.getValue() < 3)
				return;

			if (nbBot.getValue() > 6 || nbBot.getValue() < 0)
				return;

			if (nbBot.getValue() > nbJr.getValue())
				return;

			core.setNbJoueur(nbJr.getValue());
			core.setNbBot(nbBot.getValue());

			try {
				core.setCj(new ControleurJeu(nomP.getText(), core.getNbJoueurReel(), core.getNbBot()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Initializer.updateJoueurs(new ArrayList<>(), core.getCj().getNbjtotal());
			sc.setPaneOnTop(ApplicationPane.WAIT);
		});
		//auteur florian
		bRetour = new Button(International.trad("bouton.retour"));
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

		centreMenu.setAlignment(titre, Pos.CENTER);

		centreMenu.setTop(titre);
		centreMenu.setCenter(vbCenter);
		centreMenu.setBottom(boutonsPanneau);
		
		//auteur Remy
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

		//auteur florian
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

	@Override
	public void traduire() {
		titre1.setText(
				International.trad("texte.titreConfigPartieA") + "\n" + International.trad("texte.titreConfigPartieB"));
		desc.setText(International.trad("texte.descriptionConfigPartie"));
		nomPTexte.setText(International.trad("texte.idPartie"));
		nbjrTexte.setText(International.trad("texte.nbdeJr"));
		infoNomPartie.setText(International.trad("texte.infoNom1") + "\n" + International.trad("texte.infoNom2") + "\n"
				+ International.trad("texte.infoNom3") + "\n" + International.trad("texte.infoNom4"));
		nbBotTexte.setText(International.trad("texte.nbdeBot"));
		bJouer.setText(International.trad("bouton.jouer"));
		bRetour.setText(International.trad("bouton.retour"));
	}
}
