package pp.ihm;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pp.ihm.event.Evenement;
import pp.ihm.langues.International;
import pp.ihm.langues.Langues;
/**
 * 
 * @author florian
 * @author remy 
 * @author sebastien 
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
		//auteur florian 
		primaryStage.getIcons().add(new Image(DataControl.ICONE));
		sControl = new ScreenControl(this, core);
		int largeur = 1920;
		int hauteur = 1080;

		primaryStage.setTitle("PP - G2 - ZOMBIES la blonde la brute et le truand");

		primaryStage.setMaxWidth(largeur);
		primaryStage.setMaxHeight(hauteur);
		primaryStage.setMinWidth(largeur - 20.0);
		primaryStage.setMinHeight(hauteur - 50.0);

		primaryStage.setFullScreen(core.getSauvegarderOptions().isEstPleinEcran());

		AttenteJoueurPane attenteJoueurPane = new AttenteJoueurPane(sControl, core);
		ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
		FinDePartiePane finDePartiePane = new FinDePartiePane(sControl, core);
		ConfigBotPane configBotPane = new ConfigBotPane(sControl, core);
		CouleurPane couleurPane = new CouleurPane(sControl, core);
		PlateauPane plateauPane = new PlateauPane(sControl, core);
		OptionPane optionPane = new OptionPane(sControl, core);
		AccessibilitePane accessibilitePane = new AccessibilitePane(sControl);
		ReglesPane reglesPane = new ReglesPane(sControl, core);
		PausePane pausePane = new PausePane(sControl, core);
		AccueilPane accueilPane = new AccueilPane(sControl, core);

		//auteur florian 
		Evenement.addListener(attenteJoueurPane);
		Evenement.addListener(plateauPane);
		Evenement.addListener(finDePartiePane);
		Evenement.addListener(couleurPane);
		Evenement.addListener(optionPane);

		Evenement.updatePleineEcran();

		International.ajouterPane(accessibilitePane);
		International.ajouterPane(reglesPane);
		International.ajouterPane(optionPane);
		International.ajouterPane(pausePane);
		International.ajouterPane(attenteJoueurPane);
		International.ajouterPane(configPartiePane);
		International.ajouterPane(finDePartiePane);
		International.ajouterPane(configBotPane);
		International.ajouterPane(plateauPane);
		International.ajouterPane(couleurPane);
		International.ajouterPane(accueilPane);
		
		International.changerLangue(core.getSauvegarderOptions().getLangues());
		
		//auteur remy 
		scene.getStylesheets().add(DataControl.CSS);
		
		//auteur floria n
		root.getChildren().add(accessibilitePane);
		root.getChildren().add(reglesPane);
		root.getChildren().add(optionPane);
		root.getChildren().add(pausePane);
		root.getChildren().add(attenteJoueurPane);
		root.getChildren().add(configPartiePane);
		root.getChildren().add(finDePartiePane);
		root.getChildren().add(configBotPane);
		root.getChildren().add(plateauPane);
		root.getChildren().add(couleurPane);

		root.getChildren().add(accueilPane);

		primaryStage.setScene(scene);
		primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::onClose);
		primaryStage.show();

	}
	/**
	 * @author florian
	 * @param args arguments de lancement
	 * @param c core 
	 */
	public static void lancement(String[] args, Core c) {
		core = c;
		InterfacePrincipale.launch(args);
	}
	/**
	 * @author florian
	 * @param n panneau a afficher 
	 */
	public void setOnTop(Node n) {
		if (currentTopNode != null)
			currentTopNode.setVisible(false);
		n.setVisible(true);
		currentTopNode = n;
	}

	public Scene getScene() {
		return scene;
	}
	
	/**
	 * @author Sebastien
	 * @param event quand la fenetre demande la fermeture 
	 */
	public void onClose(WindowEvent event) {
		if (core.getCj() != null)
			core.getCj().stopThreads();
		System.exit(0);
	}
}
