
package ihm;

import ihm.DataControl.ApplicationPane;
import ihm.ScreenControl;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
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

public class OptionPane extends StackPane {

	private ScreenControl sControl = null;
	private Core core = null;
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private final ApplicationPane paneName = ApplicationPane.OPTION;

	private int tailleCarreCentral = 700;
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);

	public OptionPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);

		Rectangle rect = new Rectangle();
		rect.setStroke(Color.RED);
		rect.setStrokeWidth(2);
		rect.setWidth(tailleCarreCentral);
		rect.setHeight(tailleCarreCentral);
		rect.setX(100);
		rect.setY(100);
		rect.setOpacity(.3);

		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);

		HBox hbBRetour = new HBox();
		hbBRetour.setAlignment(Pos.BOTTOM_LEFT);
		hbBRetour.setPrefSize(tailleCarreCentral, 60);
		hbBRetour.setMinSize(tailleCarreCentral, 60);
		hbBRetour.setMaxSize(tailleCarreCentral, 60);

		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.CENTER);
		vbCentral.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setMinSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setPadding(new Insets(10));

		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.TOP_CENTER);

		HBox hbLang = new HBox();
		hbLang.setAlignment(Pos.CENTER);
		hbLang.setSpacing(10);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		vbBoutons.setSpacing(15);

		Label titre = new Label("OPTIONS");
		titre.setStyle("-fx-text-fill: #ff1c16");
		titre.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		vbTitre.getChildren().add(titre);

		Button bFrancais = new Button("Français");
		bFrancais.setFont(policeBouton);
		bFrancais.setAlignment(Pos.CENTER);
		bFrancais.setPrefSize(245, 60);
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
		bEnglish.setPrefSize(245, 60);
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
		bAcc.setPrefSize(500, 60);
		bAcc.setStyle(styleBoutons);
		bAcc.setOnMouseEntered(event -> {
			bAcc.setStyle(styleBoutonsSouris);
		});
		bAcc.setOnMouseExited(event -> {
			bAcc.setStyle(styleBoutons);
		});

		Button bRetour = new Button("RETOUR");
		bRetour.setFont(policeBouton);
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(150, 60);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(core.getPauseDepuis()));
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

		// vbCentral.setRotate(90);
		stackPane.getChildren().addAll(vbFond, rect, vbCentral);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
