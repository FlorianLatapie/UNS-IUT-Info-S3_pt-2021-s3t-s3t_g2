
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
	private final ApplicationPane paneName = ApplicationPane.PAUSE;

	public PausePane(ScreenControl sc) {

		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);
		
		Rectangle rect = new Rectangle();
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.LIGHTGRAY);
		rect.setWidth(700);
		rect.setHeight(600);
		rect.setX(100);
		rect.setY(100);
		rect.setOpacity(.8);
		

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
		titre.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		vbTitre.getChildren().add(titre);
		vbTitre.setMargin(vbBoutons, new Insets(70));

		Label fr = new Label("Français");
		Label en = new Label("English");

		fr.setFont(Font.font("Arial", 20));

		fr.setAlignment(Pos.CENTER);

		en.setFont(Font.font("Arial", 20));
		en.setAlignment(Pos.CENTER);

		Button bOption = new Button("Options");
		bOption.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bOption.setAlignment(Pos.CENTER);
		bOption.setPrefSize(500, 60);

		Button bRegles = new Button("Règles du jeu");
		bRegles.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRegles.setAlignment(Pos.CENTER);
		bRegles.setPrefSize(500, 60);

		Button bRecommencer = new Button("Recommencer");
		bRecommencer.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRecommencer.setAlignment(Pos.CENTER);
		bRecommencer.setPrefSize(500, 60);

		Button bQuitter = new Button("Quitter");
		bQuitter.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bQuitter.setAlignment(Pos.CENTER);
		bQuitter.setPrefSize(500, 60);
		bQuitter.setOnAction(actionEvent -> Platform.exit());
		
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
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PLATEAU));

		vbBoutons.getChildren().add(bOption);
		vbBoutons.getChildren().add(bRegles);
		vbBoutons.getChildren().add(bRecommencer);
		vbBoutons.getChildren().add(bRetour);
		vbBoutons.getChildren().add(bQuitter);
		vbBoutons.setMargin(bRegles, new Insets(10));
		vbBoutons.setMargin(bRetour, new Insets(10));

		vbCentral.getChildren().addAll(vbTitre, vbBoutons);

		ImageView img = new ImageView("http://tof.canardpc.com/view/3bd6756b-7865-4945-b524-426f6cd21e6c.jpg");
		vbFond.getChildren().add(img);
		
		stackPane.getChildren().addAll(vbFond, rect, vbCentral);

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
