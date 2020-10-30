package java;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/StartPage.fxml"));
		
		 Scene scene = new Scene(fxmlLoader.load());
		 
		 primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
   public static void main(String[] args) {
       launch(args);
   }
}
