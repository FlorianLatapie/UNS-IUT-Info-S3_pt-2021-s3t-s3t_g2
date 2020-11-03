package ihm;

import javafx.application.Application;
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
        sControl = new ScreenControl(this, core);
        int largeur = 1920;
        int hauteur = 1080;
        primaryStage.setTitle("G2 - ZOMBIES la blonde la brute et le truand");

        primaryStage.setMaxWidth(largeur);
        primaryStage.setMaxHeight(hauteur);
        // primaryStage.setMinWidth(largeur-20);
        // primaryStage.setMinHeight(hauteur-80);

        // on passe en plein écran
        primaryStage.setFullScreen(true);

        PausePane pausePane = new PausePane(sControl, core);
        ReglesPane reglesPane = new ReglesPane(sControl, core);
        PlateauPane plateauPane = new PlateauPane(sControl, core);
        OptionPane optionPane = new OptionPane(sControl, core);
        AccessibilitePane accessibilitePane = new AccessibilitePane(sControl);
        ConfigPartiePane configPartiePane = new ConfigPartiePane(sControl, core);
        core.eventInit();
        core.getInitializer().addListener(plateauPane);
        root.getChildren().add(pausePane);
        root.getChildren().add(reglesPane);
        root.getChildren().add(plateauPane);
        root.getChildren().add(optionPane);
        root.getChildren().add(accessibilitePane);
        root.getChildren().add(configPartiePane);

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
