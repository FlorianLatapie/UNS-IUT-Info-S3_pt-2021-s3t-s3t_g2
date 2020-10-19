package ihm;

import ihm.ScreenControl;
import ihm.DataControl.ApplicationPane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
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
		
		/*//Phase 1 : construire l'interface
		HBox hbtop = new HBox(); 
		VBox vbdroite = new VBox(); 
		VBox vbgauche = new VBox(); 
		VBox vbcentre = new VBox(); 
				
		Label titre = new Label("Memory");
		
		titre.setFont(Font.font("Pristina", FontWeight.BOLD, 45));
		hbtop.getChildren().add(titre);
		hbtop.setAlignment(Pos.CENTER);
		
		Button bNewPartie = new Button ("Nouvelle partie");
		vbcentre.getChildren().add(bNewPartie);
		bNewPartie.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.CREATION));
		
		Button bHelp = new Button ("Aide");
		vbcentre.getChildren().add(bHelp);
		bHelp.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.AIDE));
		
		Button bOption = new Button ("Options");
		vbcentre.getChildren().add(bOption);
		bOption.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));
		
		vbcentre.setAlignment(Pos.CENTER);
		
		Button bQuitter = new Button ("Quitter");
		vbdroite.getChildren().add(bQuitter);
		vbdroite.setAlignment(Pos.BOTTOM_RIGHT);
		vbdroite.setPrefWidth(100);
		
		vbgauche.setPrefWidth(100);
		
		bQuitter.setOnAction(actionEvent -> Platform.exit());
		
		//Phase 2 : l'action sur le bouton Nouvelle Partie, affiche l'écran de création d'une partie (message à destination du controleur de dialogue)
				
		//Phase 2 : l'action dur le bouton Aide, aafiche l'écran d'aide (message à destination du controleur de dialogue)
		
		//Phase 2 : l'action sur le bouton Options , aafiche l'écran de réglage des options (message à destination du controleur de dialogue)
		
		this.setTop(hbtop);
		this.setRight(vbdroite);
		this.setLeft(vbgauche);
		this.setCenter(vbcentre);*/
		
		Label titre = new Label("ZOMBIES la blonde la brute et le truand");
		titre.setFont(Font.font("Segoe UI", 50));
		
		Button Jouer = new Button("Jouer");
		Button Options = new Button("Options");
		Button Regles = new Button("Règles");
		Button Quitter = new Button("Quitter");
		
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
		centreMenu.getChildren().addAll(titre,Jouer,Options,Regles,Quitter);
		
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