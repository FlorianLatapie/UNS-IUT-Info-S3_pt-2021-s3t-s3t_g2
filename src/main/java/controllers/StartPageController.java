package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class StartPageController {

		double height;
		double width;
	
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
	    private void clickedButton (ActionEvent event) throws Exception {
	        Stage stage = null;
	        Parent root = null;

	        if(event.getSource() == quitButton) {
		        System.exit(0);
		        
	        }
	        
	        if(event.getSource() == optionsButton) {
		        System.out.println("Options!");
		        stage = (Stage) optionsButton.getScene().getWindow();
		        height=stage.getHeight();
		        width=stage.getWidth();
	            root = FXMLLoader.load(getClass().getResource("/views/OptionView.fxml"));
	        }
	        if(event.getSource() == regleButton) {
		        System.out.println("Règle");
		        stage = (Stage) regleButton.getScene().getWindow();
		        height=stage.getHeight();
		        width=stage.getWidth();
	            root = FXMLLoader.load(getClass().getResource("/views/NotYetImplementedView.fxml"));
	        }
	        if(event.getSource() == playButton) {
		        System.out.println("Jouer");
		        stage = (Stage) playButton.getScene().getWindow();
		        height=stage.getHeight();
		        width=stage.getWidth();
	            root = FXMLLoader.load(getClass().getResource("/views/JoinGameView.fxml"));

	        }
	     
	        /*else {
	            stage = (Stage) optionsButton.getScene().getWindow();
	            root = FXMLLoader.load(getClass().getResource("/views/MenuView.fxml"));
	        }*/

	        Scene scene = new Scene(root);
	        stage.setHeight(height);
	        stage.setWidth(width);
	        stage.setScene(scene);
	        stage.show();
	    }
    
}