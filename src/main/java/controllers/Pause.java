package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Pause {

    @FXML
    private Button Options;

    @FXML
    private Button Regles_du_jeu;

    @FXML
    private Button Re;

    @FXML
    private Button Quitter;

    @FXML
    private Button Retour;


    @FXML
    private void initialize() {
        Options.setText("Options");
        Regles_du_jeu.setText("Regles");
        Re.setText("recommencer");
        Quitter.setText("Quitter");
        Retour.setText("retour");

    }

    @FXML
    private void clickedButton (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == Quitter) {
        	stage = (Stage) Quitter.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/StartPage.fxml"));
        }

        else if(event.getSource() == Re){
            stage = (Stage) Re.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/JoinGameView.fxml"));
        }
        
        else if(event.getSource() == Regles_du_jeu){
        }
        
        else if(event.getSource() == Retour){
        }
        
        else {
            stage = (Stage) Options.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/OptionsView.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
