package idjr.ihmidjr;

import idjr.Idjr;
import idjr.ihmidjr.event.Evenement;
import idjr.ihmidjr.langues.International;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * 
 * @author florian
 *
 */
public class InterfacePrincipale extends Application {
	//auteur florian 
	private StackPane root = new StackPane();
	private Node currentTopNode = null;
	private ScreenControl sControl = null;
	private Scene scene = new Scene(root);
	private static Core core;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.getIcons().add(new Image(DataControl.ICONE));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				boolean resultat = ConfirmationPane.afficher(International.trad("texte.confirmationTitre"),
						International.trad("texte.confirmationL1") + "\n" + International.trad("texte.confirmationL2"));
				if (resultat)
					Platform.exit();
				else
					event.consume();
			}
		});
		sControl = new ScreenControl(this, core);
		int largeur = 1920;
		int hauteur = 1080;
		primaryStage.setTitle("IDJR - G2 - ZOMBIES la blonde la brute et le truand");
		primaryStage.setMaxWidth(largeur);
		primaryStage.setMaxHeight(hauteur);
		primaryStage.setWidth(largeur - 100.0);
		primaryStage.setHeight(hauteur - 100.0);
		primaryStage.setMinWidth(1800);
		primaryStage.setMinHeight(960);

		primaryStage.setFullScreen(core.getSauvegarderOptions().isEstPleineEcran());

		scene.getStylesheets().add(DataControl.CSS);

		ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
		FinDePartiePane finDePartiePane = new FinDePartiePane(sControl, core);
		JeuPane jeuPane = new JeuPane(sControl, core);
		OptionPane optionPane = new OptionPane(sControl, core);
		ReglesPane reglesPane = new ReglesPane(sControl, core);
		ConfirmationPane confirmationPane = new ConfirmationPane();
		AttenteJoueurPane attenteJoueurPane = new AttenteJoueurPane(sControl, core);
		AccueilPane accueilPane = new AccueilPane(sControl, core);

		root.getChildren().add(configPartiePane);
		root.getChildren().add(finDePartiePane);
		root.getChildren().add(jeuPane);
		root.getChildren().add(optionPane);
		root.getChildren().add(reglesPane);
		root.getChildren().add(attenteJoueurPane);
		root.getChildren().add(accueilPane);

		core.setIdjr(new Idjr());
		Evenement.addListener(configPartiePane);
		Evenement.addListener(jeuPane);
		Evenement.addListener(finDePartiePane);
		Evenement.addListener(attenteJoueurPane);
		Evenement.addListener(optionPane);

		Evenement.updatePleinEcran();

		International.ajouterPane(accueilPane);
		International.ajouterPane(reglesPane);
		International.ajouterPane(optionPane);
		International.ajouterPane(jeuPane);
		International.ajouterPane(attenteJoueurPane);
		International.ajouterPane(configPartiePane);
		International.ajouterPane(finDePartiePane);
		International.ajouterPane(confirmationPane);

		International.changerLangue(core.getSauvegarderOptions().getLangues());

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