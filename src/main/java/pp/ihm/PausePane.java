
package pp.ihm;

import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.langues.International;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PausePane extends StackPane {

	private ScreenControl sControl = null;
	private Core core; 
	private final ApplicationPane paneName = ApplicationPane.PAUSE;

	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	
	private GaussianBlur flou = new GaussianBlur(30);
	

	public PausePane(ScreenControl sc, Core c) {
		core = c; 
		sControl = sc;

		Rectangle rect = new Rectangle();
		rect.setStroke(Color.RED);
		rect.setStrokeWidth(2);
		rect.setWidth(700);
		rect.setHeight(600);
		rect.setX(100);
		rect.setY(100);
		rect.setOpacity(.3);

		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);

		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.CENTER);
		vbCentral.setPrefSize(700, 600);
		vbCentral.setMinSize(700, 600);
		vbCentral.setMaxSize(700, 600);
		
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
		
		///
		
		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.CENTER);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		Label titre = new Label(International.trad("text.titerPause"));
		titre.setStyle("-fx-text-fill: #ff1c16");
		titre.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		vbTitre.getChildren().add(titre);
		vbTitre.setMargin(vbBoutons, new Insets(70));

		Button bOption = new Button(International.trad("bouton.options"));
		bOption.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bOption.setAlignment(Pos.CENTER);
		bOption.setPrefSize(500, 60);
		bOption.setStyle(styleBoutons);
		bOption.setOnAction(EventHandler -> {
			core.setPauseDepuis(paneName);
			sc.setPaneOnTop(ApplicationPane.OPTION);
		});

		bOption.setOnMouseEntered(event -> bOption.setStyle(styleBoutonsSouris));
		bOption.setOnMouseExited(event -> bOption.setStyle(styleBoutons));

		Button bRegles = new Button(International.trad("text.titreRegle"));
		bRegles.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRegles.setAlignment(Pos.CENTER);
		bRegles.setPrefSize(500, 60);
		bRegles.setStyle(styleBoutons);
		bRegles.setOnMouseEntered(event -> bRegles.setStyle(styleBoutonsSouris));
		bRegles.setOnMouseExited(event -> bRegles.setStyle(styleBoutons));
		bRegles.setOnAction(EventHandler -> {
			core.setReglesDepuis(paneName);
			sc.setPaneOnTop(ApplicationPane.REGLES);
		});

		Button bRecommencer = new Button(International.trad("text.recommencer"));
		bRecommencer.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRecommencer.setAlignment(Pos.CENTER);
		bRecommencer.setPrefSize(500, 60);
		bRecommencer.setStyle(styleBoutons);
		bRecommencer.setOnMouseEntered(event -> bRecommencer.setStyle(styleBoutonsSouris));
		bRecommencer.setOnMouseExited(event -> bRecommencer.setStyle(styleBoutons));

		Button bQuitter = new Button(International.trad("bouton.quitter"));
		bQuitter.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bQuitter.setAlignment(Pos.CENTER);
		bQuitter.setPrefSize(500, 60);
		bQuitter.setStyle(styleBoutons);
		bQuitter.setOnAction(actionEvent -> Platform.exit());

		bQuitter.setOnMouseEntered(event -> bQuitter.setStyle(styleBoutonsSouris));
		bQuitter.setOnMouseExited(event -> bQuitter.setStyle(styleBoutons));

		bQuitter.setOnAction(event -> {
			boolean resultat = ConfirmationPane.afficher(International.trad("texte.confirmationTitre"),
					International.trad("texte.confirmationL1"),International.trad("texte.confirmationL2"));
			if (resultat)
				Platform.exit();
		});

		Button bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(500, 60);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PLATEAU));
		bRetour.setOnMouseEntered(event -> bRetour.setStyle(styleBoutonsSouris));
		bRetour.setOnMouseExited(event -> bRetour.setStyle(styleBoutons));

		vbBoutons.getChildren().addAll(bOption, bRegles, bRecommencer, bRetour, bQuitter);
		vbBoutons.setMargin(bRegles, new Insets(10));
		vbBoutons.setMargin(bRetour, new Insets(10));

		vbCentral.getChildren().addAll(vbTitre, vbBoutons);

		ImageView img = new ImageView(DataControl.FOND);
		vbFond.getChildren().add(img);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(vbFond, bEcranHaut, bEcranGauche, bEcranDroite, bEcranBas, rect, vbCentral);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
