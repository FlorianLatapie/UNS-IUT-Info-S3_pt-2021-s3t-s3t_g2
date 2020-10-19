//Fichier modifié le 02/05/2020 par Blondin Remy G1
package ihm;

import ihm.DataControl.ApplicationPane;
import ihm.ScreenControl;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class PausePane extends BorderPane { 

	private ScreenControl sControl = null;
	private final ApplicationPane paneName = ApplicationPane.PAUSE;


	public PausePane(ScreenControl sc) {

		sControl = sc;
		
	
		//Phase 1 : construire l'interface
		VBox vbCentral = new VBox();
		VBox vbLeft = new VBox();
		VBox vbRight = new VBox();
		HBox hbTop = new HBox();
		
		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.TOP_CENTER);
		vbCentral.getChildren().add(vbTitre);
		
		VBox vbBouttons = new VBox();
		vbBouttons.setAlignment(Pos.TOP_CENTER);
		vbCentral.getChildren().add(vbBouttons);
		

		
		
		hbTop.setAlignment(Pos.CENTER);
		vbCentral.setAlignment(Pos.CENTER);
		vbRight.setAlignment(Pos.BOTTOM_RIGHT);
		vbLeft.setAlignment(Pos.BOTTOM_LEFT);
		
		

		vbCentral.setSpacing(20);
		vbCentral.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,null)));
			
		Label titre = new Label("Pause");
		titre.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		vbTitre.getChildren().add(titre);
		


		Label fr = new Label("Français");
		Label en = new Label("English");
		
		fr.setFont(Font.font("Arial", 20));
		
		fr.setAlignment(Pos.CENTER);
		
		en.setFont(Font.font("Arial", 20));
		en.setAlignment(Pos.CENTER);
		
		
		Button bOption = new Button("Options");
		bOption.setFont(Font.font("Arial",FontWeight.BOLD,20));
		bOption.setAlignment(Pos.CENTER);
		bOption.setPrefSize(500, 50);
		
		Button bRegles = new Button("Règles du jeu");
		bRegles.setFont(Font.font("Arial",FontWeight.BOLD,20));
		bRegles.setAlignment(Pos.CENTER);
		bRegles.setPrefSize(500, 50);
		
		Button bRecommencer = new Button("Recommencer");
		bRecommencer.setFont(Font.font("Arial",FontWeight.BOLD,20));
		bRecommencer.setAlignment(Pos.CENTER);
		bRecommencer.setPrefSize(500, 50);
		
		Button bQuitter = new Button("Quitter");
		bQuitter.setFont(Font.font("Arial",FontWeight.BOLD,20));
		bQuitter.setAlignment(Pos.CENTER);
		bQuitter.setPrefSize(500,50);
		
		Button bRetour = new Button("Retour");
		bRetour.setFont(Font.font("Arial",FontWeight.BOLD,20));
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(500, 50);
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.ACCUEIL));
		

		vbBouttons.getChildren().add(bOption);
		vbBouttons.getChildren().add(bRegles);
		vbBouttons.getChildren().add(bRecommencer);
		vbBouttons.getChildren().add(bQuitter);
		vbBouttons.getChildren().add(bRetour);
		
		vbBouttons.setMargin(bRegles, new Insets(10));
		vbBouttons.setMargin(bQuitter, new Insets(10));
		
		
		vbBouttons.setPadding(new Insets(10));
		
		
		bQuitter.setOnAction(actionEvent -> Platform.exit());
		
		this.setTop(hbTop);
		this.setLeft(vbLeft);
		this.setRight(vbRight);
		this.setCenter(vbCentral);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
