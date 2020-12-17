package idjr.ihmidjr;

import idjr.Idjr;
import idjr.ihmidjr.event.Initializer;
import idjr.ihmidjr.langues.International;
import javafx.application.Application;
import javafx.application.Platform;
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
		primaryStage.setOnCloseRequest((e) -> {
			boolean resultat = ConfirmationPane.afficher(International.trad("texte.confirmationTitre"),
					International.trad("texte.confirmationL1"), International.trad("texte.confirmationL2"));
			if (resultat)
				Platform.exit();
		});
		sControl = new ScreenControl(this, core);
		int largeur = 1920;
		int hauteur = 1080;
		primaryStage.setTitle("IDJR - G2 - ZOMBIES la blonde la brute et le truand");
		primaryStage.setMaxWidth(largeur);
		primaryStage.setMaxHeight(hauteur);
		primaryStage.setWidth(largeur - 100);
		primaryStage.setHeight(hauteur - 100);
		primaryStage.setMinWidth(1800);
		primaryStage.setMinHeight(960);
		
		primaryStage.setFullScreen(core.getSauvegarderOptions().isEstPleineEcran());

		ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
		FinDePartiePane finDePartiePane = new FinDePartiePane(sControl, core);
		AttenteJoueurPane attenteJoueurPane = new AttenteJoueurPane(sControl, core);
		JeuPane jeuPane = new JeuPane(sControl, core);
		OptionPane optionPane = new OptionPane(sControl, core);

		root.getChildren().add(configPartiePane);
		root.getChildren().add(finDePartiePane);
		root.getChildren().add(jeuPane);

		core.setIdjr(new Idjr());
		Initializer.addListener(configPartiePane);
		Initializer.addListener(jeuPane);
		Initializer.addListener(finDePartiePane);
		Initializer.addListener(attenteJoueurPane);
		Initializer.addListener(optionPane);
		
		/* Ajouter les panes dont il y de la rotation */
		//Initializer.addListenerAttente(PANE);
		
		Initializer.updatePleineEcran();
		
		root.getChildren().add(optionPane);
		root.getChildren().add(new ReglesPane(sControl, core));
		root.getChildren().add(attenteJoueurPane);
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
		core.getIdjr().stop();
	}

}