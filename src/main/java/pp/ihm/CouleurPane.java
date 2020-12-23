package pp.ihm;

import pp.Joueur;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.event.ICouleurListener;
import pp.ihm.langues.ITraduction;
import pp.ihm.langues.International;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import reseau.type.Couleur;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class CouleurPane extends StackPane implements ICouleurListener, ITraduction {
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.COULEUR;

	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	private int hBoutonMD = 60;
	private int lBoutonMD = 150;
	private int hauteurElemtents = 60;
	private int largeurTF = 300;
	private int largeurComboBox = 140;
	private int spacing = 20;

	private String nomPolice = "Segoe UI";
	private Font policeBouton = Font.font(nomPolice, FontWeight.BOLD, 27);
	private Font policeBoutonMD = Font.font(nomPolice, FontWeight.BOLD, 23);

	private String styleNom = "-fx-background-color: #1B1B1B; -fx-background-radius: 10px;";
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";

	private GaussianBlur flou = new GaussianBlur(30);
	private CornerRadii coin = new CornerRadii(15.0);

	Label nom1;
	Label nom2;
	Label nom3;
	Label nom4;
	Label nom5;
	Label nom6;

	HBox j1;
	HBox j2;
	HBox j3;
	HBox j4;
	HBox j5;
	HBox j6;

	Button monter1;
	Button monter2;
	Button monter3;
	Button monter4;
	Button monter5;
	Button monter6;

	Button descendre1;
	Button descendre2;
	Button descendre3;
	Button descendre4;
	Button descendre5;
	Button descendre6;

	ComboBox<String> couleur1;
	ComboBox<String> couleur2;
	ComboBox<String> couleur3;
	ComboBox<String> couleur4;
	ComboBox<String> couleur5;
	ComboBox<String> couleur6;

	int[] ordre;
	List<Joueur> joueurs;
	List<String> couleursChoix;

	Label titre1;
	Label infoVigile;
	Button bJouer;
	Button bRetour;

	public CouleurPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		// titre
		titre1 = new Label(
				International.trad("texte.titreCouleurPaneA") + "\n" + International.trad("texte.titreCouleurPaneB"));
		titre1.setTextAlignment(TextAlignment.CENTER);
		titre1.setFont(Font.font(nomPolice, FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		infoVigile = new Label(International.trad("texte.infoVigile"));
		infoVigile.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
		infoVigile.setTextFill(Color.WHITE);
		infoVigile.setPadding(new Insets(5, 10, 5, 10));

		////

		VBox vJoueurs = new VBox();

		j1 = new HBox();

		nom1 = new Label();
		nom1.setStyle(styleNom);
		nom1.setText(International.trad("texte.j1"));
		nom1.setFont(Font.font(nomPolice, FontWeight.BOLD, 23));
		nom1.setTextFill(Color.WHITE);
		nom1.setPrefSize(largeurTF, hauteurElemtents);
		nom1.setMinHeight(hauteurElemtents);
		nom1.setAlignment(Pos.CENTER);
		couleur1 = new ComboBox<>();
		couleur1.setMinHeight(hauteurElemtents);
		couleur1.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur1.getItems().addAll(DataControl.couleursJoueur);
		couleur1.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nom1.setStyle(getCouleur(couleur1.getValue()));
				if (ordre.length > 0)
					couleursChoix.set(ordre[0], newValue);
			}
		});
		monter1 = new Button(International.trad("bouton.monter"));
		monter1.setPrefSize(lBoutonMD, hBoutonMD);
		monter1.setMinSize(lBoutonMD, hBoutonMD);
		monter1.setFont(policeBoutonMD);
		monter1.setStyle(styleBoutons);
		monter1.setOnMouseEntered(event -> monter1.setStyle(styleBoutonsSouris));
		monter1.setOnMouseExited(event -> monter1.setStyle(styleBoutons));
		monter1.setDisable(true);
		descendre1 = new Button(International.trad("bouton.descendre"));
		descendre1.setPrefSize(lBoutonMD, hBoutonMD);
		descendre1.setMinSize(lBoutonMD, hBoutonMD);
		descendre1.setFont(policeBoutonMD);
		descendre1.setStyle(styleBoutons);
		descendre1.setOnMouseEntered(event -> descendre1.setStyle(styleBoutonsSouris));
		descendre1.setOnMouseExited(event -> descendre1.setStyle(styleBoutons));
		descendre1.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 0, 1);
			updateOrdre();
		});

		j1.setAlignment(Pos.CENTER);
		j1.setSpacing(spacing);
		j1.getChildren().addAll(nom1, couleur1, monter1, descendre1);
		j1.setDisable(false);

		///

		j2 = new HBox();

		nom2 = new Label();
		nom2.setStyle(styleNom);
		nom2.setText(International.trad("texte.j2"));
		nom2.setFont(Font.font(nomPolice, FontWeight.BOLD, 23));
		nom2.setTextFill(Color.WHITE);
		nom2.setPrefSize(largeurTF, hauteurElemtents);
		nom2.setMinHeight(hauteurElemtents);
		nom2.setAlignment(Pos.CENTER);
		couleur2 = new ComboBox<>();
		couleur2.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur2.setMinHeight(hauteurElemtents);
		couleur2.getItems().addAll(DataControl.couleursJoueur);
		couleur2.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nom2.setStyle(getCouleur(couleur2.getValue()));
				if (ordre.length > 1)
					couleursChoix.set(ordre[1], newValue);
			}
		});
		monter2 = new Button(International.trad("bouton.monter"));
		monter2.setPrefSize(lBoutonMD, hBoutonMD);
		monter2.setMinSize(lBoutonMD, hBoutonMD);
		monter2.setFont(policeBoutonMD);
		monter2.setStyle(styleBoutons);
		monter2.setOnMouseEntered(event -> monter2.setStyle(styleBoutonsSouris));
		monter2.setOnMouseExited(event -> monter2.setStyle(styleBoutons));
		monter2.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 1, 0);
			updateOrdre();
		});
		descendre2 = new Button(International.trad("bouton.descendre"));
		descendre2.setPrefSize(lBoutonMD, hBoutonMD);
		descendre2.setMinSize(lBoutonMD, hBoutonMD);
		descendre2.setFont(policeBoutonMD);
		descendre2.setStyle(styleBoutons);
		descendre2.setOnMouseEntered(event -> descendre2.setStyle(styleBoutonsSouris));
		descendre2.setOnMouseExited(event -> descendre2.setStyle(styleBoutons));
		descendre2.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 1, 2);
			updateOrdre();
		});

		j2.setAlignment(Pos.CENTER);
		j2.setSpacing(spacing);
		j2.getChildren().addAll(nom2, couleur2, monter2, descendre2);
		j2.setDisable(false);

		///

		j3 = new HBox();

		nom3 = new Label();
		nom3.setStyle(styleNom);
		nom3.setText(International.trad("texte.j3"));
		nom3.setFont(Font.font(nomPolice, FontWeight.BOLD, 23));
		nom3.setTextFill(Color.WHITE);
		nom3.setPrefSize(largeurTF, hauteurElemtents);
		nom3.setMinHeight(hauteurElemtents);
		nom3.setAlignment(Pos.CENTER);
		couleur3 = new ComboBox<>();
		couleur3.setMinHeight(hauteurElemtents);
		couleur3.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur3.getItems().addAll(DataControl.couleursJoueur);
		couleur3.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nom3.setStyle(getCouleur(couleur3.getValue()));
				if (ordre.length > 2)
					couleursChoix.set(ordre[2], newValue);
			}
		});
		monter3 = new Button(International.trad("bouton.monter"));
		monter3.setPrefSize(lBoutonMD, hBoutonMD);
		monter3.setMinSize(lBoutonMD, hBoutonMD);
		monter3.setFont(policeBoutonMD);
		monter3.setStyle(styleBoutons);
		monter3.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 2, 1);
			updateOrdre();
		});
		monter3.setOnMouseEntered(event -> monter3.setStyle(styleBoutonsSouris));
		monter3.setOnMouseExited(event -> monter3.setStyle(styleBoutons));
		descendre3 = new Button(International.trad("bouton.descendre"));
		descendre3.setPrefSize(lBoutonMD, hBoutonMD);
		descendre3.setMinSize(lBoutonMD, hBoutonMD);
		descendre3.setFont(policeBoutonMD);
		descendre3.setStyle(styleBoutons);
		descendre3.setOnMouseEntered(event -> descendre3.setStyle(styleBoutonsSouris));
		descendre3.setOnMouseExited(event -> descendre3.setStyle(styleBoutons));
		descendre3.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 2, 3);
			updateOrdre();
		});

		j3.setAlignment(Pos.CENTER);
		j3.setSpacing(spacing);
		j3.getChildren().addAll(nom3, couleur3, monter3, descendre3);
		j3.setDisable(false);

		///

		j4 = new HBox();

		nom4 = new Label();
		nom4.setStyle(styleNom);
		nom4.setText(International.trad("texte.j4"));
		nom4.setFont(Font.font(nomPolice, FontWeight.BOLD, 23));
		nom4.setTextFill(Color.WHITE);
		nom4.setPrefSize(largeurTF, hauteurElemtents);
		nom4.setMinHeight(hauteurElemtents);
		nom4.setAlignment(Pos.CENTER);
		couleur4 = new ComboBox<>();
		couleur4.setMinHeight(hauteurElemtents);
		couleur4.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur4.getItems().addAll(DataControl.couleursJoueur);
		couleur4.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nom4.setStyle(getCouleur(couleur4.getValue()));
				if (ordre.length > 3)
					couleursChoix.set(ordre[3], newValue);
			}
		});
		monter4 = new Button(International.trad("bouton.monter"));
		monter4.setPrefSize(lBoutonMD, hBoutonMD);
		monter4.setMinSize(lBoutonMD, hBoutonMD);
		monter4.setFont(policeBoutonMD);
		monter4.setStyle(styleBoutons);
		monter4.setOnMouseEntered(event -> monter4.setStyle(styleBoutonsSouris));
		monter4.setOnMouseExited(event -> monter4.setStyle(styleBoutons));
		monter4.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 3, 2);
			updateOrdre();
		});
		descendre4 = new Button(International.trad("bouton.descendre"));
		descendre4.setPrefSize(lBoutonMD, hBoutonMD);
		descendre4.setMinSize(lBoutonMD, hBoutonMD);
		descendre4.setFont(policeBoutonMD);
		descendre4.setStyle(styleBoutons);
		descendre4.setOnMouseEntered(event -> descendre4.setStyle(styleBoutonsSouris));
		descendre4.setOnMouseExited(event -> descendre4.setStyle(styleBoutons));
		descendre4.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 3, 4);
			updateOrdre();
		});

		j4.setAlignment(Pos.CENTER);
		j4.setSpacing(spacing);
		j4.getChildren().addAll(nom4, couleur4, monter4, descendre4);
		j4.setDisable(false);

		///

		j5 = new HBox();

		nom5 = new Label();
		nom5.setStyle(styleNom);
		nom5.setText(International.trad("texte.j5"));
		nom5.setFont(Font.font(nomPolice, FontWeight.BOLD, 23));
		nom5.setTextFill(Color.WHITE);
		nom5.setPrefSize(largeurTF, hauteurElemtents);
		nom5.setMinHeight(hauteurElemtents);
		nom5.setAlignment(Pos.CENTER);
		couleur5 = new ComboBox<>();
		couleur5.setMinHeight(hauteurElemtents);
		couleur5.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur5.getItems().addAll(DataControl.couleursJoueur);
		couleur5.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nom5.setStyle(getCouleur(couleur5.getValue()));
				if (ordre.length > 4)
					couleursChoix.set(ordre[4], newValue);
			}
		});
		monter5 = new Button(International.trad("bouton.monter"));
		monter5.setPrefSize(lBoutonMD, hBoutonMD);
		monter5.setMinSize(lBoutonMD, hBoutonMD);
		monter5.setFont(policeBoutonMD);
		monter5.setStyle(styleBoutons);
		monter5.setOnMouseEntered(event -> monter5.setStyle(styleBoutonsSouris));
		monter5.setOnMouseExited(event -> monter5.setStyle(styleBoutons));
		monter5.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 4, 3);
			updateOrdre();
		});
		descendre5 = new Button(International.trad("bouton.descendre"));
		descendre5.setPrefSize(lBoutonMD, hBoutonMD);
		descendre5.setMinSize(lBoutonMD, hBoutonMD);
		descendre5.setFont(policeBoutonMD);
		descendre5.setStyle(styleBoutons);
		descendre5.setOnMouseEntered(event -> descendre5.setStyle(styleBoutonsSouris));
		descendre5.setOnMouseExited(event -> descendre5.setStyle(styleBoutons));
		descendre5.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 4, 5);
			updateOrdre();
		});

		j5.setAlignment(Pos.CENTER);
		j5.setSpacing(spacing);
		j5.getChildren().addAll(nom5, couleur5, monter5, descendre5);
		j5.setDisable(false);

		///

		j6 = new HBox();

		nom6 = new Label();
		nom6.setStyle(styleNom);
		nom6.setText(International.trad("texte.j6"));
		nom6.setFont(Font.font(nomPolice, FontWeight.BOLD, 23));
		nom6.setTextFill(Color.WHITE);
		nom6.setPrefSize(largeurTF, hauteurElemtents);
		nom6.setMinHeight(hauteurElemtents);
		nom6.setAlignment(Pos.CENTER);
		couleur6 = new ComboBox<>();
		couleur6.setMinHeight(hauteurElemtents);
		couleur6.setPrefSize(largeurComboBox, hauteurElemtents);
		couleur6.getItems().addAll(DataControl.couleursJoueur);
		couleur6.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nom6.setStyle(getCouleur(couleur6.getValue()));
				if (ordre.length > 5)
					couleursChoix.set(ordre[5], newValue);
			}
		});
		monter6 = new Button(International.trad("bouton.monter"));
		monter6.setPrefSize(lBoutonMD, hBoutonMD);
		monter6.setMinSize(lBoutonMD, hBoutonMD);
		monter6.setFont(policeBoutonMD);
		monter6.setStyle(styleBoutons);
		monter6.setOnMouseEntered(event -> monter6.setStyle(styleBoutonsSouris));
		monter6.setOnMouseExited(event -> monter6.setStyle(styleBoutons));
		monter6.setOnAction(EventHandler -> {
			ordre = IhmOutils.echanger(ordre, 5, 4);
			updateOrdre();
		});
		descendre6 = new Button(International.trad("bouton.descendre"));
		descendre6.setPrefSize(lBoutonMD, hBoutonMD);
		descendre6.setMinSize(lBoutonMD, hBoutonMD);
		descendre6.setFont(policeBoutonMD);
		descendre6.setStyle(styleBoutons);
		descendre6.setOnMouseEntered(event -> descendre6.setStyle(styleBoutonsSouris));
		descendre6.setOnMouseExited(event -> descendre6.setStyle(styleBoutons));
		descendre6.setDisable(true);

		j6.setAlignment(Pos.CENTER);
		j6.setSpacing(spacing);
		j6.getChildren().addAll(nom6, couleur6, monter6, descendre6);

		///

		vJoueurs.setSpacing(14);
		vJoueurs.setAlignment(Pos.CENTER);
		vJoueurs.getChildren().addAll(infoVigile, j1, j2, j3, j4, j5, j6);

		VBox vbCenter = new VBox();
		vbCenter.setMargin(vJoueurs, new Insets(0, 0, 100, 0));
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
		bJouer.setOnAction(EventHandler -> {
			boolean isOk = IhmOutils.isAllUniqueColor(couleursChoix);
			if (isOk) {
				List<Couleur> cs = IhmOutils.comboStringToColorList(ordre, couleursChoix);
				core.getCj().setJoueurCouleur(cs, IhmOutils.reOrdre(ordre, joueurs));
				sc.setPaneOnTop(ApplicationPane.PLATEAU);
			}
		});

		bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setPrefSize(lBouton, hBouton);
		bRetour.setMinSize(lBouton, hBouton);
		bRetour.setFont(policeBouton);
		bRetour.setStyle(styleBoutons);

		bRetour.setOnMouseEntered(event -> bRetour.setStyle(styleBoutonsSouris));
		bRetour.setOnMouseExited(event -> {
			bRetour.setStyle(styleBoutons);
			core.getCj().stopThreads();
		});
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

		// boite du fond qui contient tout
		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(fond, centreMenu);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

	@Override
	public void joueurNoms(List<Joueur> joueurs) {
		Platform.runLater(() -> {
			Button[] buttons = { descendre1, descendre2, descendre3, descendre4, descendre5, descendre6 };
			List<ComboBox<String>> combos = new ArrayList<>();
			combos.add(couleur1);
			combos.add(couleur2);
			combos.add(couleur3);
			combos.add(couleur4);
			combos.add(couleur5);
			combos.add(couleur6);

			ordre = new int[joueurs.size()];
			for (int i = 0; i < ordre.length; i++)
				ordre[i] = i;
			couleursChoix = new ArrayList<>();
			for (int i = 0; i < joueurs.size(); i++)
				couleursChoix.add(combos.get(i).getValue());

			buttons[joueurs.size() - 1].setDisable(true);

			this.joueurs = joueurs;

			Label[] noms = { nom1, nom2, nom3, nom4, nom5, nom6 };

			for (int i = 0; i < noms.length; i++)
				noms[i].setText(joueurs.size() >= i + 1 ? joueurs.get(i).getNom() : "");

			nom1.setText(joueurs.size() >= 1 ? joueurs.get(0).getNom() : "");
			nom2.setText(joueurs.size() >= 2 ? joueurs.get(1).getNom() : "");
			nom3.setText(joueurs.size() >= 3 ? joueurs.get(2).getNom() : "");
			nom4.setText(joueurs.size() >= 4 ? joueurs.get(3).getNom() : "");
			nom5.setText(joueurs.size() >= 5 ? joueurs.get(4).getNom() : "");
			nom6.setText(joueurs.size() >= 6 ? joueurs.get(5).getNom() : "");
			setJoueurConfig(core.getNbJoueur());
		});
	}

	public void updateOrdre() {
		List<ComboBox<String>> combos = new ArrayList<>();
		combos.add(couleur1);
		combos.add(couleur2);
		combos.add(couleur3);
		combos.add(couleur4);
		combos.add(couleur5);
		combos.add(couleur6);

		nom1.setText(joueurs.size() >= 1 ? joueurs.get(ordre[0]).getNom() : "");
		nom2.setText(joueurs.size() >= 2 ? joueurs.get(ordre[1]).getNom() : "");
		nom3.setText(joueurs.size() >= 3 ? joueurs.get(ordre[2]).getNom() : "");
		nom4.setText(joueurs.size() >= 4 ? joueurs.get(ordre[3]).getNom() : "");
		nom5.setText(joueurs.size() >= 5 ? joueurs.get(ordre[4]).getNom() : "");
		nom6.setText(joueurs.size() >= 6 ? joueurs.get(ordre[5]).getNom() : "");

		couleur1.setValue(joueurs.size() >= 1 ? couleursChoix.get(ordre[0]) : "");
		couleur2.setValue(joueurs.size() >= 2 ? couleursChoix.get(ordre[1]) : "");
		couleur3.setValue(joueurs.size() >= 3 ? couleursChoix.get(ordre[2]) : "");
		couleur4.setValue(joueurs.size() >= 4 ? couleursChoix.get(ordre[3]) : "");
		couleur5.setValue(joueurs.size() >= 5 ? couleursChoix.get(ordre[4]) : "");
		couleur6.setValue(joueurs.size() >= 6 ? couleursChoix.get(ordre[5]) : "");
	}

	private void setJoueurConfig(int maxJr) {
		if (maxJr == 3) {

			j1.setVisible(true);
			j2.setVisible(true);
			j3.setVisible(true);
			j4.setVisible(false);
			j5.setVisible(false);
			j6.setVisible(false);
		} else if (maxJr == 4) {
			j1.setVisible(true);
			j2.setVisible(true);
			j3.setVisible(true);
			j4.setVisible(true);
			j5.setVisible(false);
			j6.setVisible(false);
		} else if (maxJr == 5) {

			j1.setVisible(true);
			j2.setVisible(true);
			j3.setVisible(true);
			j4.setVisible(true);
			j5.setVisible(true);
			j6.setVisible(false);
		} else {
			j1.setVisible(true);
			j2.setVisible(true);
			j3.setVisible(true);
			j4.setVisible(true);
			j5.setVisible(true);
			j6.setVisible(true);
		}
	}

	public static String getCouleur(String c) {
		String style = ";-fx-background-radius: 10px;";
		if (c == null)
			return "#1A1A1A";
		switch (c) {
		case "Bleu":
			return IhmOutils.bleu + style;
		case "Rouge":
			return IhmOutils.rouge + style;
		case "Vert":
			return IhmOutils.vert + style;
		case "Noir":
			return IhmOutils.noir + style;
		case "Jaune":
			return IhmOutils.jaune + style;
		case "Marron":
			return IhmOutils.marron + style;
		default:
			return "#1A1A1A";
		}
	}

	@Override
	public void traduire() {
		titre1.setText(
				International.trad("texte.titreCouleurPaneA") + "\n" + International.trad("texte.titreCouleurPaneB"));
		infoVigile.setText(International.trad("texte.infoVigile"));
		nom1.setText(International.trad("texte.j1"));
		monter1.setText(International.trad("bouton.monter"));
		descendre1.setText(International.trad("bouton.descendre"));
		nom2.setText(International.trad("texte.j2"));
		monter2.setText(International.trad("bouton.monter"));
		descendre2.setText(International.trad("bouton.descendre"));
		nom3.setText(International.trad("texte.j3"));
		monter3.setText(International.trad("bouton.monter"));
		descendre3.setText(International.trad("bouton.descendre"));
		nom4.setText(International.trad("texte.j4"));
		monter4.setText(International.trad("bouton.monter"));
		descendre4.setText(International.trad("bouton.descendre"));
		nom5.setText(International.trad("texte.j5"));
		monter5.setText(International.trad("bouton.monter"));
		descendre5.setText(International.trad("bouton.descendre"));
		nom6.setText(International.trad("texte.j6"));
		monter6.setText(International.trad("bouton.monter"));
		descendre6.setText(International.trad("bouton.descendre"));
		bJouer.setText(International.trad("bouton.jouer"));
		bRetour.setText(International.trad("bouton.retour"));

	}
}