package ihm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        	boolean resultat = ConfirmationPane.afficher("Quitter le jeu",
					"Êtes-vous sûr de vouloir quitter le jeu ? \nSi vous quittez, la partie en cours sera perdue.");
			if (resultat)
				Platform.exit();
        });
        sControl = new ScreenControl(this, core);
        int largeur = 1920;
        int hauteur = 1080;
        primaryStage.setTitle("IDJR - G2 - ZOMBIES la blonde la brute et le truand");

        primaryStage.setMaxWidth(largeur);
        primaryStage.setMaxHeight(hauteur);
        primaryStage.setWidth(largeur-100);
        primaryStage.setHeight(hauteur-100);
        primaryStage.setMinWidth(1800);
        primaryStage.setMinHeight(960);

        // on passe en plein écran
        //primaryStage.setFullScreen(true);

        /*PlateauPane plateauPane = new PlateauPane(sControl, core);
        ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
        AttenteJoueurPane attenteJoueurPane = new AttenteJoueurPane(sControl, core);
        CouleurPane couleurPane = new CouleurPane(sControl, core);
        FinDePartiePane finDePartiePane = new FinDePartiePane(sControl, core);

        core.eventInit();
        core.getInitializer().addListenerPlateau(plateauPane);
        core.getInitializer().addListenerAttente(attenteJoueurPane);
        core.getInitializer().addListenerFin(finDePartiePane);
        root.getChildren().add(new PausePane(sControl, core));
        root.getChildren().add(new ReglesPane(sControl, core));
        root.getChildren().add(plateauPane);
        
        root.getChildren().add(new AccessibilitePane(sControl));
        root.getChildren().add(configPartiePane);
        root.getChildren().add(attenteJoueurPane);
        root.getChildren().add(couleurPane);
        root.getChildren().add(finDePartiePane);*/
        ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
        FinDePartiePane finDePartiePane = new FinDePartiePane(sControl, core);
        root.getChildren().add(configPartiePane);
        root.getChildren().add(finDePartiePane);
        
        root.getChildren().add(new OptionPane(sControl, core));
        root.getChildren().add(new ReglesPane(sControl, core));
        root.getChildren().add(new JeuPane(sControl, core));
        root.getChildren().add(new AccueilPane(sControl, core));

        primaryStage.setScene(scene);

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

}