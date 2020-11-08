
package pp.ihm;

import pp.ihm.DataControl.ApplicationPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OptionPane extends StackPane {

	private ScreenControl sControl = null;
	private Core core = null;
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private String styleTitre = "-fx-text-fill: #ff1c16";
	private final ApplicationPane paneName = ApplicationPane.OPTION;

	private int tailleCarreCentral = 700;
	private Font policeTitre = Font.font("Segoe UI", FontWeight.BOLD, 75);
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);

	private int hauteurElement = 60;

	public OptionPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);

		Rectangle rect = new Rectangle();
		rect.setStroke(Color.RED);
		rect.setStrokeWidth(2);
		rect.setWidth(tailleCarreCentral);
		rect.setHeight(tailleCarreCentral);
		rect.setOpacity(.3);

		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);

		HBox hbBRetour = new HBox();
		hbBRetour.setAlignment(Pos.BOTTOM_LEFT);
		hbBRetour.setPrefSize(tailleCarreCentral, hauteurElement);
		hbBRetour.setMinSize(tailleCarreCentral, hauteurElement);
		hbBRetour.setMaxSize(tailleCarreCentral, hauteurElement);

		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.CENTER);
		vbCentral.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setMinSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setPadding(new Insets(10));

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
		bEcranHaut.setOnAction(EventHandler -> sc.setRotatePane(vbCentral, "haut"));

		Button bEcranBas = new Button();
		bEcranBas.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranBas.setAlignment(Pos.CENTER);
		bEcranBas.setTranslateY(490);
		bEcranBas.setPrefSize(80, 80);
		bEcranBas.setGraphic(img2);
		bEcranBas.setOnAction(EventHandler -> sc.setRotatePane(vbCentral, "bas"));

		Button bEcranGauche = new Button();
		bEcranGauche.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranGauche.setAlignment(Pos.CENTER);
		bEcranGauche.setTranslateX(-910);
		bEcranGauche.setPrefSize(80, 80);
		bEcranGauche.setRotate(90);
		bEcranGauche.setGraphic(img3);
		bEcranGauche.setOnAction(EventHandler -> sc.setRotatePane(vbCentral, "gauche"));

		Button bEcranDroite = new Button();
		bEcranDroite.setBackground(new Background(new BackgroundFill(null, CornerRadii.EMPTY, null)));
		bEcranDroite.setAlignment(Pos.CENTER);
		bEcranDroite.setTranslateX(910);
		bEcranDroite.setRotate(-90);
		bEcranDroite.setPrefSize(80, 80);
		bEcranDroite.setGraphic(img4);
		bEcranDroite.setOnAction(EventHandler -> sc.setRotatePane(vbCentral, "droite"));

		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.TOP_CENTER);
		vbTitre.setTranslateY(-40);

		HBox hbLang = new HBox();
		hbLang.setAlignment(Pos.CENTER);
		hbLang.setSpacing(10);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		vbBoutons.setTranslateY(-40);
		vbBoutons.setSpacing(15);

		Label titre = new Label("OPTIONS");
		titre.setStyle(styleTitre);
		titre.setFont(policeTitre);
		vbTitre.getChildren().add(titre);

		Button bFrancais = new Button("Français");
		bFrancais.setFont(policeBouton);
		bFrancais.setAlignment(Pos.CENTER);
		bFrancais.setPrefSize(245, hauteurElement);
		bFrancais.setStyle(styleBoutons);
		bFrancais.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));

		bFrancais.setOnMouseEntered(event -> {
			bFrancais.setStyle(styleBoutonsSouris);
		});
		bFrancais.setOnMouseExited(event -> {
			bFrancais.setStyle(styleBoutons);
		});

		Button bEnglish = new Button("English");
		bEnglish.setFont(policeBouton);
		bEnglish.setAlignment(Pos.CENTER);
		bEnglish.setPrefSize(245, hauteurElement);
		bEnglish.setStyle(styleBoutons);
		bEnglish.setOnMouseEntered(event -> {
			bEnglish.setStyle(styleBoutonsSouris);
		});
		bEnglish.setOnMouseExited(event -> {
			bEnglish.setStyle(styleBoutons);
		});

		Button bAcc = new Button("Accessibilité");
		bAcc.setFont(policeBouton);
		bAcc.setAlignment(Pos.CENTER);
		bAcc.setPrefSize(500, hauteurElement);
		bAcc.setStyle(styleBoutons);
		bAcc.setOnMouseEntered(event -> {
			bAcc.setStyle(styleBoutonsSouris);
		});
		bAcc.setOnMouseExited(event -> {
			bAcc.setStyle(styleBoutons);
		});
		bAcc.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCESSIBILITE));		

		Button bRetour = new Button("RETOUR");
		bRetour.setFont(policeBouton);
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(180, hauteurElement);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnAction(EventHandler -> {
			sc.setPaneOnTop(core.getPauseDepuis());
		});
		bRetour.setOnMouseEntered(event -> {
			bRetour.setStyle(styleBoutonsSouris);
		});
		bRetour.setOnMouseExited(event -> {
			bRetour.setStyle(styleBoutons);
		});

		hbBRetour.getChildren().add(bRetour);
		hbLang.getChildren().add(bFrancais);
		hbLang.getChildren().add(bEnglish);
		vbBoutons.getChildren().addAll(hbLang, bAcc);
		vbBoutons.setMargin(vbTitre, new Insets(140));
		vbBoutons.setMargin(hbBRetour, new Insets(140, 0, 30, 15));

		vbCentral.getChildren().addAll(vbTitre, vbBoutons, hbBRetour);
		ImageView img = new ImageView(DataControl.FOND);
		vbFond.getChildren().add(img);
		

		stackPane.getChildren().addAll(vbFond, bEcranHaut, bEcranBas, bEcranGauche, bEcranDroite, rect, vbCentral);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
