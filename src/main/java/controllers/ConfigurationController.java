package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfigurationController {

    @FXML
    private Button playbutton;
    
    @FXML
    private Button backbutton;




    @FXML
    private void initialize() {
        playbutton.setText("Jouer");
        backbutton.setText("Retour");

    }

    @FXML
    private void clickedButton (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == backbutton) {
        	stage = (Stage) backbutton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/JoinGameView.fxml"));
        }
        

        if(event.getSource() == playbutton) {
        	stage = (Stage) playbutton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/NotYetImplementedView.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
