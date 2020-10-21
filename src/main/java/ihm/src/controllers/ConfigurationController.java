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
    private Label label;

    @FXML
    private Button quitButton;

    @FXML
    private Button menuButton;

    @FXML
    private void initialize() {
        label.setText("Config");
        quitButton.setText("Quitter");
        menuButton.setText("Menu");
    }

    @FXML
    private void clickedButton (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == quitButton) {
            System.exit(0);
        }

        else {
            stage = (Stage) menuButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/MenuView.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
