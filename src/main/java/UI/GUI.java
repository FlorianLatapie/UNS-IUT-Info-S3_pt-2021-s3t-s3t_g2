package UI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class GUI extends Application {

	private static long id;
	
	private Button joinUDP,sendUDPM;
	private TextField tf;
	private TextArea ta;
	private CheckBox cb;

	public static void lancement(String[] args) {
		//core = c;
		GUI.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		int largeur = 1920;
		int hauteur = 1080;
		primaryStage.setTitle("G2 - ZOMBIES la blonde la brute et le truand");
		
		primaryStage.setMaxWidth(largeur);
		primaryStage.setMaxHeight(hauteur);
		primaryStage.setMinWidth(largeur-20);
		primaryStage.setMinHeight(hauteur-80);
		
		primaryStage.setFullScreen(true);
		
		
		BorderPane root = new BorderPane();
		/*
		HBox top = new HBox();
		top.setPrefHeight(hauteur/4);
		top.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY,null)));
		top.setAlignment(Pos.CENTER);
		
		Label title = new Label("ZOMBIES la blonde la brute et le truand");
		title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 100));
		top.getChildren().add(title);*/
		
		Rectangle Rect = new Rectangle(300,300); 
		Label t = new Label("sample text");
		t.setFont(Font.font("Segoe UI", 50));
		
		StackPane sp = new StackPane(Rect, t);
		sp.setPrefWidth(100);
		sp.setPrefHeight(100);
		sp.setBackground(new Background(new BackgroundFill(Color.ORANGE,CornerRadii.EMPTY,null)));
		
		root.setCenter(sp);
		
		//root.setTop(top);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void displayLog(String s) {
		ta.appendText(s);
	}
}

