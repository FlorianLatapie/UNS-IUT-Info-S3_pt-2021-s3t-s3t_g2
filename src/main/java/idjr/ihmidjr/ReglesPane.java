package idjr.ihmidjr;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.langues.ITraduction;
import idjr.ihmidjr.langues.International;
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
 * @since 01/11/2020
 */
public class ReglesPane extends StackPane implements ITraduction {

	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.REGLES;
	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	private int marge = tailleCarreCentral / 25;
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 27);
	private CornerRadii coin = new CornerRadii(15.0);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);

	private Font policeNom = Font.font("Segoe UI", 17);
	private CornerRadii coinfb = new CornerRadii(5.0);
	private Background fondBlanc = new Background(new BackgroundFill(Color.WHITE, coinfb, null));

	Label titre1;
	Label desc1;
	Button bRetour;

	public ReglesPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

		// titre
		titre1 = new Label(International.trad("text.titreRegle"));
		titre1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(730);
		titre.setMinWidth(730);

		Rectangle rect = new Rectangle();
		// rect.setStroke(Color.RED);
		rect.setStrokeWidth(2);
		rect.setWidth(tailleCarreCentral);
		rect.setHeight(tailleCarreCentral - 154);
		rect.setArcHeight(30);
		rect.setArcWidth(30);
		rect.setOpacity(.3);

		desc1 = new Label();
		desc1.setText(International.trad("texte.pourQuiA") + "\n" + International.trad("texte.pourQuiB") + "\n\n"
				+ International.trad("texte.histoireA") + "\n" + International.trad("texte.histoireB") + "\n"
				+ International.trad("texte.histoireC") + "\n" + International.trad("texte.histoireD") + "\n"
				+ International.trad("texte.histoireE") + "\n" + International.trad("texte.histoireF") + "\n"
				+ International.trad("texte.histoireG") + "\n\n" + International.trad("texte.but") + "\n"
				+ International.trad("texte.butExplicationA") + "\n" + International.trad("texte.butExplicationB")
				+ "\n" + International.trad("texte.butExplicationC") + "\n"
				+ International.trad("texte.butExplicationD") + "\n" + International.trad("texte.butExplicationE")
				+ "\n" + International.trad("texte.butExplicationF") + "\n\n" + International.trad("texte.finDePartieA")
				+ "\n" + International.trad("texte.finDePartieB") + "\n" + International.trad("texte.FDPsit1A") + "\n"
				+ International.trad("texte.FDPsit1B") + "\n" + International.trad("texte.FDPsit2A") + "\n"
				+ International.trad("texte.FDPsit2B") + "\n" + International.trad("texte.FDPfinA") + "\n"
				+ International.trad("texte.FDPfinB") + "\n" + International.trad("texte.FDPfinC") + "\n");
		desc1.setFont(policeNom);
		desc1.setTextFill(Color.WHITE);
		desc1.setPadding(new Insets(5, 10, 5, 10));
		// desc1.setBackground(fondBlanc);

		VBox vbCenter = new VBox();
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.getChildren().addAll(desc1);

		// bouton
		bRetour = new Button(International.trad("bouton.retour"));
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
		// centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));
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

		stackPane.getChildren().addAll(fond, rect, centreMenu);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}

	@Override
	public void traduire() {
		titre1.setText(International.trad("text.titreRegle"));
		desc1.setText(International.trad("texte.pourQuiA") + "\n" + International.trad("texte.pourQuiB") + "\n\n"
				+ International.trad("texte.histoireA") + "\n" + International.trad("texte.histoireB") + "\n"
				+ International.trad("texte.histoireC") + "\n" + International.trad("texte.histoireD") + "\n"
				+ International.trad("texte.histoireE") + "\n" + International.trad("texte.histoireF") + "\n"
				+ International.trad("texte.histoireG") + "\n\n" + International.trad("texte.but") + "\n"
				+ International.trad("texte.butExplicationA") + "\n" + International.trad("texte.butExplicationB")
				+ "\n" + International.trad("texte.butExplicationC") + "\n"
				+ International.trad("texte.butExplicationD") + "\n" + International.trad("texte.butExplicationE")
				+ "\n" + International.trad("texte.butExplicationF") + "\n\n" + International.trad("texte.finDePartieA")
				+ "\n" + International.trad("texte.finDePartieB") + "\n" + International.trad("texte.FDPsit1A") + "\n"
				+ International.trad("texte.FDPsit1B") + "\n" + International.trad("texte.FDPsit2A") + "\n"
				+ International.trad("texte.FDPsit2B") + "\n" + International.trad("texte.FDPfinA") + "\n"
				+ International.trad("texte.FDPfinB") + "\n" + International.trad("texte.FDPfinC") + "\n");
		bRetour.setText(International.trad("bouton.retour"));

	}

}