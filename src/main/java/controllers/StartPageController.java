package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartPageController {

    @FXML
    private Button quitButton;
    
    @FXML
    private Button optionsButton;

    @FXML
    private Button regleButton;
    
    @FXML
    private Button playButton;


    @FXML
    private void initialize() {
        quitButton.setText("Quitter");
        optionsButton.setText("Options");
        regleButton.setText("Règle");
        playButton.setText("Jouer");
    }

    @FXML
    private void clickedButton2 (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == quitButton) {
            System.exit(0);
        }
        

        else {
            stage = (Stage) optionsButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/MenuView.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
