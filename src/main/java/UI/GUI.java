package UI;
import NF.Core;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class GUI extends Application {
	
	private static Core core;
	private static long id;
	
	private Button joinUDP,sendUDPM;
	private TextField tf;
	private TextArea ta;
	private CheckBox cb;

	public static void lancement(String[] args, Core c) {
		core = c;
		GUI.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Demo Java Network");
		primaryStage.setWidth(1080);
		primaryStage.setHeight(720);
		
		
		BorderPane root = new BorderPane();
		
		HBox top = new HBox();
		top.setPrefHeight(50);
		top.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY,null)));
		top.setAlignment(Pos.CENTER);
		
		Label title = new Label("Demo Java Network - Server "+id);
		title.setFont(Font.font("Pristina", FontWeight.BOLD, 30));
		top.getChildren().add(title);
		
				
		HBox bottom = new HBox();
		bottom.setPrefHeight(50);
		bottom.setAlignment(Pos.CENTER_RIGHT);
		
				
		Button leave = new Button("Quitter");
		bottom.getChildren().add(leave);
		
		leave.setOnAction(
			event -> {
				Platform.runLater(new Runnable() {
					public void run() {
						core.exit();
					}
				});
			}
		);
				
		VBox center = new VBox();
		center.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,new CornerRadii(0), new BorderWidths(0), new Insets(10))));
		center.setSpacing(10);
		
		HBox serverJ = new HBox();
		serverJ.setSpacing(730);
		
		joinUDP = new Button("Joindre le groupe UDP");
		serverJ.getChildren().add(joinUDP);
		
		cb = new CheckBox("Ignorer les messages locaux");
		serverJ.getChildren().add(cb);
		
		HBox serverM = new HBox();
		
		tf = new TextField();
		serverM.getChildren().add(tf);
		
		sendUDPM = new Button("envoyer un message UDP");
		serverM.getChildren().add(sendUDPM);
		sendUDPM.setDisable(true);
		
		joinUDP.setOnAction(event -> {core.joinUDPMulticastGroup();});
		sendUDPM.setOnAction(event -> core.sendUDPMessage(tf.getText()));
		
		ta = new TextArea();
		ta.setEditable(false);
		ta.setPrefSize(1000, 500);
		
		core.setGUI(this);
		
		center.getChildren().add(serverJ);
		center.getChildren().add(serverM);
		center.getChildren().add(ta);
		
		root.setTop(top);
		root.setBottom(bottom);
		root.setCenter(center);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void setId(long i) {
		id = i;
	}

	public void setConnected() {
		sendUDPM.setDisable(false);
		joinUDP.setDisable(true);
	}

	public void setDisconnected() {
		sendUDPM.setDisable(true);
		joinUDP.setDisable(false);
	}

	public void displayLog(String s) {
		ta.appendText(s);
	}

	public boolean isLocalMsgSkip() {
		return cb.isSelected();
	}
}

