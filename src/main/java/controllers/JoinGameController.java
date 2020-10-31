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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class JoinGameController {

			double height;
			double width;
	
		    @FXML
		    private Button idGame1;

		    
		    @FXML
		    private Button idGame2;

		    @FXML
		    private Button idGame3;
		    
		    @FXML
		    private Button idGame4;
		    
		    @FXML
		    private Button joinButton;
		    
		    @FXML
		    private Button backButton;
		    
		    
		    
		    


		    @FXML
		    private void initialize() {
		    	idGame1.setText("idGame1");
		    	idGame2.setText("idGame2");
		    	idGame3.setText("idGame3");
		    	idGame4.setText("idGame4");
		    	joinButton.setText("Rejoindre");
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
		      
		      
		        else if(event.getSource() == joinButton) {
			        System.out.println("Rejoindre");
			        stage = (Stage) joinButton.getScene().getWindow();
			        height=stage.getHeight();
			        width=stage.getWidth();
		            root = FXMLLoader.load(getClass().getResource("/views/NotYetImplementedView.fxml"));

		        }
		        else {
		            stage = (Stage) joinButton.getScene().getWindow();
		            height=stage.getHeight();
			        width=stage.getWidth();
		            root = FXMLLoader.load(getClass().getResource("/views/JoinGameView.fxml"));
		        }

		        Scene scene = new Scene(root);
		        stage.setHeight(height);
		        stage.setWidth(width);
		        stage.setScene(scene);   
		        stage.show();
		    }
	    
	}

