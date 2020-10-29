package ihm;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InterfacePrincipale extends Application {
	private StackPane root = new StackPane();
	private Node currentTopNode = null;
	private ScreenControl sControl = null;
	private Scene scene = new Scene(root);

	@Override
	public void start(Stage primaryStage) throws Exception {
		sControl = new ScreenControl(this);
		int largeur = 1920;
		int hauteur = 1080;
		primaryStage.setTitle("G2 - ZOMBIES la blonde la brute et le truand");

		primaryStage.setMaxWidth(largeur);
		primaryStage.setMaxHeight(hauteur);
		// primaryStage.setMinWidth(largeur-20);
		// primaryStage.setMinHeight(hauteur-80);

		// on passe en plein écran
		primaryStage.setFullScreen(true);

		root.getChildren().add(new PausePane(sControl));
		root.getChildren().add(new PlateauPane(sControl));
		//root.getChildren().add(new OptionPane(sControl));
		root.getChildren().add(new ConfigPartiePane(sControl));
		root.getChildren().add(new AccueilPane(sControl));

		primaryStage.setScene(scene);

		primaryStage.show();

	}

	public static void lancement(String[] args) {
		InterfacePrincipale.launch(args);
	}

	public void setOnTop(Node n) {
		if (currentTopNode != null)
			currentTopNode.setVisible(false);
		n.setVisible(true);
		currentTopNode = n;
	}

	public Scene getScene() {
		return scene;
	}

}
