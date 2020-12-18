package idjr.ihmidjr;

import java.util.ArrayList;
import java.util.List;

import idjr.PartieInfo;
import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.IConfigListener;
import idjr.ihmidjr.langues.ITraduction;
import idjr.ihmidjr.langues.International;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import reseau.type.Statut;
import reseau.type.TypePartie;

/**
 * The Class ConfigPartiePane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class ConfigPartiePane extends StackPane implements IConfigListener, ITraduction {
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.CONFIG;
	private int tailleCarreCentral = 800; // l'interface est sur un stackPane
	private int hBouton = 75;
	private int lBouton = 150;
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
	private CornerRadii coin = new CornerRadii(15.0);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private int hauteurElemtents = 60;

	ListView<Label> listView;
	List<PartieInfo> partieActuelle = new ArrayList<>();
	private Insets botPadding = new Insets(10);
	ObservableList<Label> liste = FXCollections.observableArrayList();

	ComboBox<String> cbtypePartie;
	ComboBox<Integer> cbnbJr;
	ComboBox<String> cbStatutPartie;

	Label titre1;
	Label desc;
	TextField nomP;
	Button bRefresh;
	Button bPbConnexion;
	Button bJouer;
	Button bRetour;

	public ConfigPartiePane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);
		// titre
		titre1 = new Label(
				International.trad("texte.titreConfigPartieA") + "\n" + International.trad("texte.titreConfigPartieB"));
		titre1.setTextAlignment(TextAlignment.CENTER);
		titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		////

		desc = new Label(International.trad("texte.idPartie"));
		desc.setAlignment(Pos.CENTER);
		desc.setFont(policeBouton);
		desc.setTextFill(Color.WHITE);
		desc.setMinHeight(hauteurElemtents);
		desc.setPadding(botPadding);

		HBox hComboTri = new HBox();
		cbtypePartie = new ComboBox<>();
		cbtypePartie.getItems().addAll(TypePartie.BOTU.name(), TypePartie.JRU.name(), TypePartie.MIXTE.name());
		cbtypePartie.setValue(TypePartie.MIXTE.name());// TODO
		cbtypePartie.setStyle("-fx-text-fill: white;");
		cbtypePartie.setPrefSize(200, 63);
		cbtypePartie.setMinHeight(63);
		cbnbJr = new ComboBox<>();
		cbnbJr.getItems().addAll(3, 4, 5, 6);
		cbnbJr.setValue(6);// TODO
		cbnbJr.setStyle("-fx-text-fill: white;");
		cbnbJr.setPadding(new Insets(0, 0, 0, 20));
		cbnbJr.setPrefSize(100, 63);
		cbnbJr.setMinHeight(63);
		cbStatutPartie = new ComboBox<>();
		cbStatutPartie.getItems().addAll(Statut.ATTENTE.name(), Statut.COMPLETE.name(), Statut.TERMINEE.name());
		cbStatutPartie.setValue(Statut.ATTENTE.name());// TODO
		cbStatutPartie.setStyle("-fx-text-fill: white;");
		cbStatutPartie.setPrefSize(200, 63);
		cbStatutPartie.setMinHeight(63);

		hComboTri.getChildren().addAll(cbtypePartie, cbnbJr, cbStatutPartie);
		hComboTri.setAlignment(Pos.CENTER);
		hComboTri.setSpacing(20);

		nomP = new TextField();
		nomP.setText(International.trad("texte.Partie"));
		nomP.setFont(policeBouton);
		nomP.setStyle(
				"-fx-background-color: #1A1A1A; -fx-border-color: white; -fx-border-width: 1; -fx-text-fill: white;");
		nomP.setPrefSize(400, hauteurElemtents);
		nomP.setMinSize(400, hauteurElemtents);
		nomP.setMaxSize(400, hauteurElemtents);

		VBox partie = new VBox();
		partie.setAlignment(Pos.CENTER);
		partie.getChildren().addAll(desc, hComboTri);
		partie.setPadding(new Insets(10));
		partie.setSpacing(17);
		partie.setPrefWidth(220);
		partie.setMaxWidth(600);

		// To Creating a Observable List
		ObservableList<Label> liste = FXCollections.observableArrayList();

		// Create a ListView
		listView = new ListView<Label>(liste);

		// Only allowed to select single row in the ListView.
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.setPrefWidth(600);
		listView.setMaxWidth(600);
		listView.setEditable(false);
		listView.setStyle(
				"-fx-background-color: white;-fx-control-inner-background: #1A1A1A ; -fx-control-inner-background-alt: derive(-fx-control-inner-background, 15%);");

		bRefresh = new Button(International.trad("bouton.rafraichir"));
		bRefresh.setPrefSize(lBouton + 30.0, hBouton);
		bRefresh.setMinSize(lBouton + 30.0, hBouton);
		bRefresh.setFont(policeBouton);
		bRefresh.setStyle(styleBoutons);

		bRefresh.setOnMouseEntered(event -> bRefresh.setStyle(styleBoutonsSouris));
		bRefresh.setOnMouseExited(event -> bRefresh.setStyle(styleBoutons));

		bRefresh.setOnAction(EventHandler -> core.getIdjr().listOfServers(cbnbJr.getValue(),
				TypePartie.valueOf(cbtypePartie.getValue()), Statut.valueOf(cbStatutPartie.getValue())));

		bPbConnexion = new Button(
				International.trad("texte.pbConnexionA") + "\n" + International.trad("texte.pbConnexionB"));
		bPbConnexion.setTextAlignment(TextAlignment.CENTER);
		bPbConnexion.setPrefSize(120, 50);
		bPbConnexion.setMinSize(120, 50);
		bPbConnexion.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
		bPbConnexion.setStyle(styleBoutons);
		bPbConnexion.setOnMouseEntered(event -> bPbConnexion.setStyle(styleBoutonsSouris));
		bPbConnexion.setOnMouseExited(event -> bPbConnexion.setStyle(styleBoutons));
		bPbConnexion.setTranslateX(240);

		VBox vbCenter = new VBox();
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setSpacing(10);
		vbCenter.setPrefHeight(500);
		vbCenter.setMinHeight(500);
		vbCenter.setMaxHeight(500);
		vbCenter.getChildren().addAll(partie, listView, bPbConnexion);

		// boutons
		bJouer = new Button(International.trad("bouton.jouer"));
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(styleBoutons);

		bJouer.setOnMouseEntered(event -> bJouer.setStyle(styleBoutonsSouris));
		bJouer.setOnMouseExited(event -> bJouer.setStyle(styleBoutons));
		bJouer.setOnAction(EventHandler -> {
			int index = listView.getSelectionModel().getSelectedIndex();
			if (index == -1)
				return;
			if (partieActuelle.size() <= index)
				return;

			PartieInfo partieInfo = partieActuelle.get(index);
			if (partieInfo == null)
				return;
			core.getIdjr().estPartieConnecte(partieInfo.getIdPartie(), cbnbJr.getValue(),
					TypePartie.valueOf(cbtypePartie.getValue()), Statut.valueOf(cbStatutPartie.getValue()));
		});
		nomP.textProperty().addListener((obs, oldText, newText) -> bJouer.setDisable(nomP.getText().isEmpty()));

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
		boutonsPanneau.setLeftAnchor(bRefresh, 300.0);
		boutonsPanneau.setRightAnchor(bJouer, 0.0);
		boutonsPanneau.getChildren().addAll(bRetour, bRefresh, bJouer);
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
		vbCenter.setTranslateY(-60);

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
	public void partieValide(String id) {
		Platform.runLater(() -> {
			core.getIdjr().rejoindrePartie(id);
			sControl.setPaneOnTop(ApplicationPane.WAIT);
		});
	}

	@Override
	public void partie(List<PartieInfo> partieInfo) {
		Platform.runLater(() -> {
			listView.getItems().clear();
			if (partieInfo.isEmpty()) {
				partieActuelle = null;
				Label res = new Label(International.trad("texte.dispoPartie"));
				res.setAlignment(Pos.CENTER);
				res.setTextFill(Color.WHITE);
				res.setFont(policeBouton);
				listView.getItems().add(res);
			} else {
				partieActuelle = partieInfo;
				for (PartieInfo partieInfo2 : partieInfo) {
					Label res = new Label(partieInfo2.toString());
					res.setAlignment(Pos.CENTER);
					res.setTextFill(Color.WHITE);
					res.setFont(policeBouton);
					listView.getItems().add(res);
				}
			}
		});
	}

	@Override
	public void traduire() {
		titre1.setText(
				International.trad("texte.titreConfigPartieA") + "\n" + International.trad("texte.titreConfigPartieB"));
		desc.setText(International.trad("texte.idPartie"));
		nomP.setText(International.trad("texte.Partie"));
		bRefresh.setText(International.trad("bouton.rafraichir"));
		bPbConnexion
				.setText(International.trad("texte.pbConnexionA") + "\n" + International.trad("texte.pbConnexionB"));

		bJouer.setText(International.trad("bouton.jouer"));
		bRetour.setText(International.trad("bouton.retour"));

	}
}
