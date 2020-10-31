package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegleViewController {
	
	double height;
	double width;
    
    @FXML
    private Button backButton;
    
   


    @FXML
    private void initialize() {
 
    	backButton.setText("Retour");


    }
    
    
    @FXML
    private void clickedButton (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == backButton) {
        	System.out.println("retour");
	        stage = (Stage) backButton.getScene().getWindow();
	        height=stage.getHeight();
	        width=stage.getWidth();
            root = FXMLLoader.load(getClass().getResource("/views/StartPage.fxml"));
           
        }
        
        Scene scene = new Scene(root);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setScene(scene);   
        stage.show();
    }

}
