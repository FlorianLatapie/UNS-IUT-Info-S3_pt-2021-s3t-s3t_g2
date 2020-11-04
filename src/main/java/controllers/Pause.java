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

	double height;
	double width;
		
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
            height=stage.getHeight();
	        width=stage.getWidth();
            root = FXMLLoader.load(getClass().getResource("/views/StartPage.fxml"));
        }
        
        if(event.getSource() == Options) {
        	stage = (Stage) Options.getScene().getWindow();
            height=stage.getHeight();
	        width=stage.getWidth();
            root = FXMLLoader.load(getClass().getResource("/views/OptionsView.fxml"));
        }

        if(event.getSource() == Regles_du_jeu) {
        	stage = (Stage) Regles_du_jeu.getScene().getWindow();
            height=stage.getHeight();
	        width=stage.getWidth();
            root = FXMLLoader.load(getClass().getResource("/views/RegleView.fxml"));
        }

        if(event.getSource() == Re) {
        	stage = (Stage) Re.getScene().getWindow();
            height=stage.getHeight();
	        width=stage.getWidth();
            root = FXMLLoader.load(getClass().getResource("/views/JoinGameView.fxml"));
        }

        

        else {
            stage = (Stage) Retour.getScene().getWindow();
            System.exit(0);
        }

        Scene scene = new Scene(root);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setScene(scene);   
        stage.show();
    }
}
