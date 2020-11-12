package pp.ihm;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class InterfacePrincipale extends Application {
	private StackPane root = new StackPane();
	private Node currentTopNode = null;
	private ScreenControl sControl = null;
	private Scene scene = new Scene(root);
	private static Core core;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.getIcons().add(new Image(DataControl.ICONE));
		sControl = new ScreenControl(this, core);
		int largeur = 1920;
		int hauteur = 1080;
		
		primaryStage.setTitle("PP - G2 - ZOMBIES la blonde la brute et le truand");

		primaryStage.setMaxWidth(largeur);
		primaryStage.setMaxHeight(hauteur);
		primaryStage.setMinWidth(largeur-20);
		primaryStage.setMinHeight(hauteur-50);
		
		primaryStage.setFullScreen(true);

		
		AttenteJoueurPane attenteJoueurPane = new AttenteJoueurPane(sControl, core);
		ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
		FinDePartiePane finDePartiePane = new FinDePartiePane(sControl, core);
		ConfigBotPane configBotPane = new ConfigBotPane(sControl, core);
		CouleurPane couleurPane = new CouleurPane(sControl, core);
		PlateauPane plateauPane = new PlateauPane(sControl, core);

		core.eventInit();
		core.getInitializer().addListenerAttente(attenteJoueurPane);
		core.getInitializer().addListenerPlateau(plateauPane);
		core.getInitializer().addListenerFin(finDePartiePane);
		
		root.getChildren().add(new AccessibilitePane(sControl));
		root.getChildren().add(new ReglesPane(sControl, core));
		root.getChildren().add(new OptionPane(sControl, core));
		root.getChildren().add(new PausePane(sControl, core));
		root.getChildren().add(attenteJoueurPane);
		root.getChildren().add(configPartiePane);
		root.getChildren().add(finDePartiePane);
		root.getChildren().add(configBotPane);
		root.getChildren().add(plateauPane);
		root.getChildren().add(couleurPane);


		root.getChildren().add(new AccueilPane(sControl, core));

		primaryStage.setScene(scene);
		primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::onClose);
		primaryStage.show();

	}

	public static void lancement(String[] args, Core c) {
		core = c;
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

	public void onClose(WindowEvent event) {
		if (core.getCj() != null)
			core.getCj().stopThreads();
		System.exit(0);
	}

}
