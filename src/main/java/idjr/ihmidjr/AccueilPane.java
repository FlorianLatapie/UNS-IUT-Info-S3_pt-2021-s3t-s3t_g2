package idjr.ihmidjr;

import java.util.Random;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.Initializer;
import idjr.ihmidjr.langues.ITraduction;
import idjr.ihmidjr.langues.International;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
import javafx.util.Duration;
import reseau.type.Statut;
import reseau.type.TypePartie;

/**
 * The Class AccueilPane.
 * 
 * @author Florian
 * @version 0.1
 * @since 04/10/2020
 */
public class AccueilPane extends StackPane implements ITraduction {

	// définition des variable pour la suite du pane
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
	private String styleQuestion = " -fx-background-color:#000000; -fx-background-radius: 50px; -fx-text-fill: #ffffff";
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private Font policeNom = Font.font(nomPolice, 17);

	private int largeurTF = 600;
	private int hauteurElemtents = 60;

	TextField nomjoueur;
	Tooltip infoNomJoueur;
	Button bOptions;
	Button bRegles;
	Button bQuitter;

	Label titre1;
	Button bJouer;
	Label titre2;

	public AccueilPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

		// titre
		titre1 = new Label(International.trad("texte.preTitre"));
		titre1.setFont(Font.font(nomPolice, FontWeight.BOLD, 160));
		titre1.setTextFill(Color.BLACK);

		titre2 = new Label(International.trad("texte.titreIDJR"));
		titre2.setFont(Font.font(nomPolice, 35));
		titre2.setTextFill(Color.BLACK);
		titre2.setPadding(new Insets(0, 0, 20, 0));

		Label titre3 = new Label("IDJR");
		titre3.setFont(Font.font(nomPolice, 35));
		titre3.setTextFill(Color.BLACK);
		titre3.setPadding(new Insets(0, 0, 20, 0));

		VBox titre = new VBox(titre1, titre2, titre3);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(800);
		titre.setMinWidth(800);

		// nom du joueur

		HBox HNomJoueur = new HBox();
		HNomJoueur.setAlignment(Pos.CENTER);
		HNomJoueur.setSpacing(10);

		infoNomJoueur = new Tooltip((International.trad("texte.infoNom1") + "\n" + International.trad("texte.infoNom2")
				+ "\n" + International.trad("texte.infoNom3") + "\n" + International.trad("texte.infoNom4")));

		infoNomJoueur.setFont(policeNom);
		infoNomJoueur.setShowDelay(Duration.seconds(1));
		infoNomJoueur.setHideDelay(Duration.seconds(20));
		
		nomjoueur = new TextField();
		nomjoueur.setText("JoueurSansNom" + new Random().nextInt(100));
		nomjoueur.setText(core.getSauvegarderOptions().getNom());
		nomjoueur.setStyle(
				"-fx-background-color: #1A1A1A; -fx-text-fill: white; -fx-border-color: white;  -fx-border-width: 1;");
		nomjoueur.setFont(policeNom);
		nomjoueur.setPrefSize(largeurTF, hauteurElemtents);
		nomjoueur.setMinHeight(hauteurElemtents);

		Label iconeQuestion = new Label("?");
		iconeQuestion.setAlignment(Pos.CENTER);
		iconeQuestion.setPrefSize(hauteurElemtents, hauteurElemtents);
		iconeQuestion.setMinSize(hauteurElemtents, hauteurElemtents);
		iconeQuestion.setStyle(styleQuestion);
		iconeQuestion.setFont(policeBouton);
		iconeQuestion.setTooltip(infoNomJoueur);
		iconeQuestion.tooltipProperty();

		HNomJoueur.getChildren().addAll(nomjoueur, iconeQuestion);

		// boutons
		bJouer = new Button(International.trad("bouton.jouer"));
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> bJouer.setStyle(styleBoutonsSouris));
		bJouer.setOnMouseExited(event -> bJouer.setStyle(styleBoutons));
		nomjoueur.textProperty().addListener((obs, oldText, newText) -> bJouer.setDisable(!(nomjoueur.getText().length() < 32 && IhmTools.nomEstValide(nomjoueur.getText()))));
		bJouer.setOnAction(EventHandler -> {
			if (nomjoueur.getText().length() < 20 && IhmTools.nomEstValide(nomjoueur.getText())) {
				core.getIdjr().setNom(nomjoueur.getText());
				Initializer.nomJoueur(core.getIdjr().getNom());
				core.getIdjr().listOfServers(6, TypePartie.MIXTE, Statut.ATTENTE);
				core.getSauvegarderOptions().setNom(nomjoueur.getText());
				sc.setPaneOnTop(ApplicationPane.CONFIG);
			}
		});

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
					International.trad("texte.confirmationL1") + "\n" + International.trad("texte.confirmationL2"));
			if (resultat) {
				if (core.getIdjr() != null)
					core.getIdjr().stop();
				Platform.exit();
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
		VBox centreMenu = new VBox();
		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setAlignment(Pos.CENTER);
		centreMenu.setMargin(titre, new Insets(0, 0, 50, 0));
		centreMenu.setSpacing(20);
		centreMenu.getChildren().addAll(titre, HNomJoueur, grilleBoutons);

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

	@Override
	public void traduire() {
		titre1.setText(International.trad("texte.preTitre"));
		infoNomJoueur.setText((International.trad("texte.infoNom1") + "\n" + International.trad("texte.infoNom2") + "\n"
				+ International.trad("texte.infoNom3") + "\n" + International.trad("texte.infoNom4")));
		bOptions.setText(International.trad("bouton.options"));
		bRegles.setText(International.trad("bouton.regles"));
		bJouer.setText(International.trad("bouton.jouer"));
		bQuitter.setText(International.trad("bouton.quitter"));
		titre2.setText(International.trad("texte.titreIDJR"));

	}

}