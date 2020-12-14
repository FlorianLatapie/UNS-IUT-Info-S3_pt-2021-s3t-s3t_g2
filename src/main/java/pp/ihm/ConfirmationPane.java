/**
 * 
 */
package pp.ihm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pp.ihm.langues.International;

/**
 * @author Remy
 *
 */
public class ConfirmationPane {

	static boolean reponse;
	private static int tailleFenetreL = 450;
	private static int tailleFenetreH = 150;
	private static String nomPolice = "Segoe UI";
	
	/**
	 * affiche une fenêtre de confirmation avec un titre et un message personnalisé
	 * @param titre titre de la fenetre
	 * @param message message affiché dans la fenetre 
	 * @return
	 */
	public static boolean afficher(String titre, String message) {
		
		//nouvelle fenetre
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(titre);
		window.setMinWidth(tailleFenetreL);
		window.setMinHeight(tailleFenetreH);
		
		//titre
		Label label = new Label();
		label.setText(message);
		label.setStyle("-fx-text-fill: #DDDDDD");
		label.setFont(Font.font(nomPolice, FontWeight.BOLD, 20));
		label.setPadding(new Insets(10));

		//boutons
		Button boutonOui = new Button(International.trad("bouton.quitter"));
		boutonOui.setPrefSize(200, 50);
		boutonOui.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 5px; -fx-text-fill: #ffffff");
		boutonOui.setFont(Font.font(nomPolice, FontWeight.BOLD, 27));

		Button boutonNon = new Button(International.trad("bouton.annuler"));
		boutonNon.setPrefSize(200, 50);
		boutonNon.setStyle("-fx-background-color: #A9A9A9; -fx-background-radius: 5px; -fx-text-fill: #ffffff");
		boutonNon.setFont(Font.font(nomPolice, FontWeight.BOLD, 27));

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
		layout.setStyle(" -fx-background-color: #1F1F1F;");
		Scene scene = new Scene(layout);

		window.setScene(scene);
		window.showAndWait();

		return reponse;
	}
}
