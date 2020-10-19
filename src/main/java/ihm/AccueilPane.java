package ihm;

import ihm.ScreenControl;
import ihm.DataControl.ApplicationPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class AccueilPane extends BorderPane { //ATTENTION : le type Pane est à changer par le type de votre choix.
	
	private ScreenControl sControl = null;
	private final ApplicationPane paneName = ApplicationPane.ACCUEIL;

	public AccueilPane(ScreenControl sc) {

		sControl = sc;
		Label titre = new Label("ZOMBIES la blonde la brute et le truand");
		titre.setFont(Font.font("Segoe UI", 30));
		
		Button Jouer = new Button("Jouer");
		Jouer.setPrefSize(100, 50);
		Button Options = new Button("Options");
		Options.setPrefSize(100, 50);
		Button Regles = new Button("Règles");
		Regles.setPrefSize(100, 50);
		Button Quitter = new Button("Quitter");
		Quitter.setPrefSize(100, 50);
		
		Options.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));
		
		Quitter.setOnAction(
				event -> {
					Platform.runLater(new Runnable() {
						public void run() {
							Platform.exit();
						}
					});
				}
			);
		
		VBox centreMenu = new VBox(); 
		centreMenu.setBackground(new Background(new BackgroundFill(Color.BLUE,CornerRadii.EMPTY,null)));
		centreMenu.setPrefSize(600, 600);
		centreMenu.setMaxSize(600, 600);
		centreMenu.setAlignment(Pos.CENTER);
		
		
		HBox fond = new HBox(centreMenu);
		fond.setAlignment(Pos.CENTER);
		
		GridPane grilleBoutons = new GridPane();
		grilleBoutons.setAlignment(Pos.CENTER);
		grilleBoutons.add(Jouer, 0, 0);
		grilleBoutons.add(Options,0 ,1);
		grilleBoutons.add(Regles, 1, 0);
		grilleBoutons.add(Quitter, 1, 1);
		
		centreMenu.setMargin(titre,new Insets(0,0,100,0));
		centreMenu.getChildren().addAll(titre,grilleBoutons);
		
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setAlignment(Pos.CENTER);
		fond.setBackground(new Background(new BackgroundFill(Color.ORANGE,CornerRadii.EMPTY,null)));
		//root.setTop(top);
		
		this.setCenter(fond);
		
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
		
		
	}

}