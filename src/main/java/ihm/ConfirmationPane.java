package ihm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Remy
 *
 */
public class ConfirmationPane {

	static boolean reponse;
	private static int tailleFenetreL = 450;
	private static int tailleFenetreH = 150;

	public static boolean afficher(String titre, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(titre);
		window.setMinWidth(tailleFenetreL);
		window.setMinHeight(tailleFenetreH);

		Label label = new Label();
		label.setText(message);
		label.setStyle("-fx-text-fill: #DDDDDD");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		label.setPadding(new Insets(10));

		Button boutonOui = new Button("QUITTER");
		boutonOui.setPrefSize(200, 50);
		boutonOui.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 5px; -fx-text-fill: #ffffff");
		boutonOui.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		Button boutonNon = new Button("ANNULER");
		boutonNon.setPrefSize(200, 50);
		boutonNon.setStyle("-fx-background-color: #A9A9A9; -fx-background-radius: 5px; -fx-text-fill: #ffffff");
		boutonNon.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		boutonOui.setOnAction(e -> {
			reponse = true;
			
			window.close();

		});

		boutonNon.setOnAction(e -> {
			reponse = false;
			window.close();
		});

		HBox boutonHbox = new HBox(10);
		boutonHbox.getChildren().addAll(boutonNon, boutonOui);
		boutonHbox.setAlignment(Pos.CENTER);
		boutonHbox.setPadding(new Insets(10));

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.getChildren().add(label);
		layout.getChildren().add(boutonHbox);
		layout.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,CornerRadii.EMPTY,null)));
		Scene scene = new Scene(layout);

		window.setScene(scene);
		window.showAndWait();

		return reponse;

	}

}