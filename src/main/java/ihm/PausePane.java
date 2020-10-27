
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

public class PausePane extends StackPane {

	private ScreenControl sControl = null;
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private final ApplicationPane paneName = ApplicationPane.PAUSE;

	public PausePane(ScreenControl sc) {

		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);
		
		Rectangle rect = new Rectangle();
		rect.setStroke(Color.BLACK);
		rect.setWidth(700);
		rect.setHeight(600);
		rect.setX(100);
		rect.setY(100);		
		rect.setOpacity(.6);

		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);
		

		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.CENTER);
		vbCentral.setPrefSize(700, 600);
		vbCentral.setMinSize(700, 600);
		vbCentral.setMaxSize(700, 600);
		
		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.CENTER);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		Label titre = new Label("Pause");
		titre.setStyle("-fx-text-fill: #ff0000");
		titre.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		vbTitre.getChildren().add(titre);
		vbTitre.setMargin(vbBoutons, new Insets(70));

		Button bOption = new Button("Options");
		bOption.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bOption.setAlignment(Pos.CENTER);
		bOption.setPrefSize(500, 60);
		bOption.setStyle(styleBoutons);
		bOption.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));
		
		bOption.setOnMouseEntered(event -> {
			bOption.setStyle(styleBoutonsSouris);
		});
		bOption.setOnMouseExited(event -> {
			bOption.setStyle(styleBoutons);
		});

		Button bRegles = new Button("Règles du jeu");
		bRegles.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRegles.setAlignment(Pos.CENTER);
		bRegles.setPrefSize(500, 60);
		bRegles.setStyle(styleBoutons);
		bRegles.setOnMouseEntered(event -> {
			bRegles.setStyle(styleBoutonsSouris);
		});
		bRegles.setOnMouseExited(event -> {
			bRegles.setStyle(styleBoutons);
		});

		Button bRecommencer = new Button("Recommencer");
		bRecommencer.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRecommencer.setAlignment(Pos.CENTER);
		bRecommencer.setPrefSize(500, 60);
		bRecommencer.setStyle(styleBoutons);
		bRecommencer.setOnMouseEntered(event -> {
			bRecommencer.setStyle(styleBoutonsSouris);
		});
		bRecommencer.setOnMouseExited(event -> {
			bRecommencer.setStyle(styleBoutons);
		});

		Button bQuitter = new Button("Quitter");
		bQuitter.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bQuitter.setAlignment(Pos.CENTER);
		bQuitter.setPrefSize(500, 60);
		bQuitter.setStyle(styleBoutons);
		bQuitter.setOnAction(actionEvent -> Platform.exit());
		bQuitter.setOnMouseEntered(event -> {
			bQuitter.setStyle(styleBoutonsSouris);
		});
		bQuitter.setOnMouseExited(event -> {
			bQuitter.setStyle(styleBoutons);
		});
		
		bQuitter.setOnAction(event -> {
			boolean resultat = ConfirmationPane.afficher("Quitter le jeu",
					"Êtes-vous sûr de vouloir quitter le jeu ? \nSi vous quittez, la partie en cours sera perdue.");
			if (resultat)
				Platform.exit();
		});

		Button bRetour = new Button("Retour");
		bRetour.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(500, 60);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PLATEAU));

		vbBoutons.getChildren().add(bOption);
		vbBoutons.getChildren().add(bRegles);
		vbBoutons.getChildren().add(bRecommencer);
		vbBoutons.getChildren().add(bRetour);
		vbBoutons.getChildren().add(bQuitter);
		vbBoutons.setMargin(bRegles, new Insets(10));
		vbBoutons.setMargin(bRetour, new Insets(10));

		vbCentral.getChildren().addAll(vbTitre, vbBoutons);

		ImageView img = new ImageView(DataControl.PLATEAU);
		vbFond.getChildren().add(img);
		
		stackPane.getChildren().addAll(vbFond, rect, vbCentral);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
