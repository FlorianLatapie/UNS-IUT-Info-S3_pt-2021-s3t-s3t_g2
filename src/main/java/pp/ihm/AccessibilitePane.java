package pp.ihm;

import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.langues.ITraduction;
import pp.ihm.langues.International;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AccessibilitePane extends StackPane implements ITraduction {

	private ScreenControl sControl = null;
	private StackPane stackPane = new StackPane();
	private final ApplicationPane paneName = ApplicationPane.ACCESSIBILITE;

	private int tailleCarreCentral = 700;

	private GaussianBlur flou = new GaussianBlur(30);

	private String styleTitre = "-fx-text-fill: #ff1c16";
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleTexte = " -fx-background-color:#000000; -fx-text-fill: #ffffff; -fx-background-radius: 5px;";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";

	private String nomPolice = "Segoe UI";
	private Font policeTitre = Font.font(nomPolice, FontWeight.BOLD, 75);
	private Font policeBouton = Font.font(nomPolice, FontWeight.BOLD, 27);
	private Font policeBoutonRetour = Font.font(nomPolice, FontWeight.BOLD, 33);

	private int hauteurElement = 60;
	private int largeurBouton = 220;
	Label titre;
	Label titreVbBoutons;
	Button bDeuteranopie;
	Button bProtanopie;
	Button bTritanopie;
	Button bRetour;

	public AccessibilitePane(ScreenControl sc) {

		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

		Rectangle rect = new Rectangle();
		rect.setWidth(tailleCarreCentral);
		rect.setHeight(tailleCarreCentral);
		rect.setArcHeight(30);
		rect.setArcWidth(30);
		rect.setOpacity(.3);

		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);

		BorderPane bpCentral = new BorderPane();
		bpCentral.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		bpCentral.setMinSize(tailleCarreCentral, tailleCarreCentral);
		bpCentral.setMaxSize(tailleCarreCentral, tailleCarreCentral);

		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.CENTER);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);

		VBox vbCheckBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);

		VBox vbRetour = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		vbRetour.setPadding(new Insets(10));

		titre = new Label(International.trad("texte.titreAcc"));
		titre.setStyle(styleTitre);
		titre.setFont(policeTitre);
		titre.setTranslateY(-30);
		vbTitre.getChildren().add(titre);
		vbTitre.setMargin(vbBoutons, new Insets(10));

		// daltonisme
		HBox hbDaltonisme = new HBox();
		hbDaltonisme.setSpacing(10);
		hbDaltonisme.setAlignment(Pos.CENTER);

		titreVbBoutons = new Label(International.trad("texte.dalto"));
		titreVbBoutons.setFont(policeBouton);
		titreVbBoutons.setTextFill(Color.RED);
		titreVbBoutons.setPadding(new Insets(10));
		vbBoutons.getChildren().add(titreVbBoutons);
		vbBoutons.setMargin(vbCheckBoutons, new Insets(20));

		// boutons de différents daltonismes
		bDeuteranopie = new Button(International.trad("texte.deuteranopie"));
		bDeuteranopie.setFont(policeBouton);
		bDeuteranopie.setAlignment(Pos.CENTER);
		bDeuteranopie.setStyle(styleBoutons);
		bDeuteranopie.setPrefSize(largeurBouton, hauteurElement);
		bDeuteranopie.setOnMouseEntered(event -> bDeuteranopie.setStyle(styleBoutonsSouris));
		bDeuteranopie.setOnMouseExited(event -> bDeuteranopie.setStyle(styleBoutons));

		bProtanopie = new Button(International.trad("texte.protanopie"));
		bProtanopie.setFont(policeBouton);
		bProtanopie.setAlignment(Pos.CENTER);
		bProtanopie.setPrefSize(largeurBouton, hauteurElement);
		bProtanopie.setStyle(styleBoutons);
		bProtanopie.setOnMouseEntered(event -> bProtanopie.setStyle(styleBoutonsSouris));
		bProtanopie.setOnMouseExited(event -> bProtanopie.setStyle(styleBoutons));

		bTritanopie = new Button(International.trad("texte.tritanopie"));
		bTritanopie.setFont(policeBouton);
		bTritanopie.setAlignment(Pos.CENTER);
		bTritanopie.setPrefSize(largeurBouton, hauteurElement);
		bTritanopie.setStyle(styleBoutons);
		bTritanopie.setOnMouseEntered(event -> bTritanopie.setStyle(styleBoutonsSouris));
		bTritanopie.setOnMouseExited(event -> bTritanopie.setStyle(styleBoutons));
		hbDaltonisme.getChildren().addAll(bDeuteranopie, bProtanopie, bTritanopie);

		vbBoutons.setSpacing(20);
		vbBoutons.getChildren().add(hbDaltonisme);

		// checkboxes
		/*
		 * CheckBox bHematophobie = new
		 * CheckBox(International.trad("texte.hematophobie"));
		 * bHematophobie.setStyle(styleTexte); bHematophobie.setPadding(new Insets(10));
		 * bHematophobie.setFont(policeBouton);
		 * bHematophobie.setAlignment(Pos.CENTER_LEFT);
		 * 
		 * CheckBox bAudio = new CheckBox(International.trad("texte.audioDesc"));
		 * bAudio.setStyle(styleTexte); bAudio.setPadding(new Insets(10));
		 * bAudio.setFont(policeBouton); bAudio.setAlignment(Pos.CENTER_LEFT);
		 */

		bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setFont(policeBoutonRetour);
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(180, hauteurElement);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));
		bRetour.setOnMouseEntered(event -> bRetour.setStyle(styleBoutonsSouris));
		bRetour.setOnMouseExited(event -> bRetour.setStyle(styleBoutons));

		vbRetour.getChildren().add(bRetour);

		/*
		 * vbCheckBoutons.setSpacing(20);
		 * vbCheckBoutons.getChildren().addAll(bHematophobie, bAudio);
		 */

		// centre

		VBox vbCentre = new VBox();
		vbCentre.setSpacing(20);
		vbCentre.setMargin(titre, new Insets(100, 0, 0, 0));
		vbCentre.getChildren().addAll(vbTitre, vbBoutons/* , vbCheckBoutons */);
		bpCentral.setCenter(vbCentre);
		bpCentral.setBottom(vbRetour);

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
		bEcranHaut.setOnAction(EventHandler -> sc.setRotatePane(rect, bpCentral, "haut"));

		Button bEcranBas = new Button();
		bEcranBas.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranBas.setAlignment(Pos.CENTER);
		bEcranBas.setTranslateY(490);
		bEcranBas.setPrefSize(80, 80);
		bEcranBas.setGraphic(img2);
		bEcranBas.setOnAction(EventHandler -> sc.setRotatePane(rect, bpCentral, "bas"));

		Button bEcranGauche = new Button();
		bEcranGauche.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranGauche.setAlignment(Pos.CENTER);
		bEcranGauche.setTranslateX(-910);
		bEcranGauche.setPrefSize(80, 80);
		bEcranGauche.setRotate(90);
		bEcranGauche.setGraphic(img3);
		bEcranGauche.setOnAction(EventHandler -> sc.setRotatePane(rect, bpCentral, "gauche"));

		Button bEcranDroite = new Button();
		bEcranDroite.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranDroite.setAlignment(Pos.CENTER);
		bEcranDroite.setTranslateX(910);
		bEcranDroite.setRotate(-90);
		bEcranDroite.setPrefSize(80, 80);
		bEcranDroite.setGraphic(img4);
		bEcranDroite.setOnAction(EventHandler -> sc.setRotatePane(rect, bpCentral, "droite"));

		// boite du fond qui contient le fond et les autres boites
		ImageView img = new ImageView(DataControl.FOND);
		vbFond.getChildren().add(img);

		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
		stackPane.getChildren().addAll(vbFond, rect, bpCentral, bEcranHaut, bEcranGauche, bEcranBas, bEcranDroite);

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

	@Override
	public void traduire() {
		titre.setText(International.trad("texte.titreAcc"));
		titreVbBoutons.setText(International.trad("texte.dalto"));
		bDeuteranopie.setText(International.trad("texte.deuteranopie"));
		bProtanopie.setText(International.trad("texte.protanopie"));
		bTritanopie.setText(International.trad("texte.tritanopie"));
		bRetour.setText(International.trad("bouton.retour"));
	}
}
