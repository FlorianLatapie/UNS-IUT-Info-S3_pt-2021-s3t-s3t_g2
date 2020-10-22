package ihm;

import ihm.DataControl.ApplicationPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PlateauPane extends BorderPane {
	private ScreenControl sControl = null;
	private final ApplicationPane paneName = ApplicationPane.PLATEAU; // nom du pane
	
	private int margeP = 24;
	private Insets margeBoutonPause = new Insets(margeP, margeP, margeP, margeP);
	private int lhBoutonPause = 80;
	
	private String coinBoutons = " -fx-background-radius: 5px";
	
	
	public PlateauPane(ScreenControl sc) {
		sControl = sc;
		
		HBox hHaut = new HBox(); 
		HBox hDroite = new HBox(); 
		HBox hBas = new HBox(); 
		HBox hGauche = new HBox(); 
		StackPane sCentre = new StackPane(); 
		
		Button bPause1 = new Button(); 
		bPause1.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause1.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause1.setStyle(coinBoutons);
		bPause1.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		hBas.setMargin(bPause1, margeBoutonPause);
		
		Button bPause2 = new Button(); 
		bPause2.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause2.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause2.setStyle(coinBoutons);
		bPause2.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		hBas.setMargin(bPause2, margeBoutonPause);
		
		VBox j1 = new VBox();
		Label nbPerso1 = new Label("## personnages l.52");
		nbPerso1.setFont(Font.font("Segoe UI", 20));
		nbPerso1.setTextFill(Color.BLACK);
		Label nbCartes1 = new Label("## de cartes l.55");
		nbCartes1.setFont(Font.font("Segoe UI", 20));
		nbCartes1.setTextFill(Color.BLACK);
		Label nomJoueur1 = new Label("Nom Joueur 1 l.58");
		nomJoueur1.setFont(Font.font("Segoe UI", 20));
		nomJoueur1.setTextFill(Color.BLACK);
		
		hBas.setMargin(j1, new Insets(0,500,0,118));
		j1.setAlignment(Pos.CENTER);
		j1.getChildren().addAll(nbPerso1,nbCartes1,nomJoueur1);
		
		VBox j2 = new VBox();
		Label nbPerso2 = new Label("## personnages l.67");
		nbPerso2.setFont(Font.font("Segoe UI", 20));
		nbPerso2.setTextFill(Color.BLACK);
		Label nbCartes2 = new Label("## de cartes l.70");
		nbCartes2.setFont(Font.font("Segoe UI", 20));
		nbCartes2.setTextFill(Color.BLACK);
		Label nomJoueur2 = new Label("Nom Joueur 1 l.73");
		nomJoueur2.setFont(Font.font("Segoe UI", 20));
		nomJoueur2.setTextFill(Color.BLACK);
		
		hBas.setMargin(j2, new Insets(0,118,0,500));
		j2.setAlignment(Pos.CENTER);
		j2.getChildren().addAll(nbPerso2,nbCartes2,nomJoueur2);
		
		
		hBas.getChildren().addAll(bPause1,j1,j2,bPause2);		
		hBas.setAlignment(Pos.BOTTOM_CENTER);
		
/////////////////////////////////////////////////////
		
		Button bPause3 = new Button(); 
		bPause3.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause3.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause3.setStyle(coinBoutons);
		bPause3.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		hHaut.setMargin(bPause3, margeBoutonPause);
		
		Button bPause4 = new Button(); 
		bPause4.setPrefSize(lhBoutonPause, lhBoutonPause);
		bPause4.setMinSize(lhBoutonPause, lhBoutonPause);
		bPause4.setStyle(coinBoutons);
		bPause4.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.PAUSE));
		hHaut.setMargin(bPause4, margeBoutonPause);
		
		VBox j3 = new VBox();
		Label nbPerso3 = new Label("## personnages l.102");
		nbPerso3.setFont(Font.font("Segoe UI", 20));
		nbPerso3.setTextFill(Color.BLACK);
		Label nbCartes3 = new Label("## de cartes l.105");
		nbCartes3.setFont(Font.font("Segoe UI", 20));
		nbCartes3.setTextFill(Color.BLACK);
		Label nomJoueur3 = new Label("Nom Joueur 1 l.108");
		nomJoueur3.setFont(Font.font("Segoe UI", 20));
		nomJoueur3.setTextFill(Color.BLACK);
		
		hHaut.setMargin(j3, new Insets(0,500,0,118));
		j3.setAlignment(Pos.CENTER);
		j3.getChildren().addAll(nbPerso3,nbCartes3,nomJoueur3);
		
		VBox j4 = new VBox();
		Label nbPerso4 = new Label("## personnages l.117");
		nbPerso4.setFont(Font.font("Segoe UI", 20));
		nbPerso4.setTextFill(Color.BLACK);
		Label nbCartes4 = new Label("## de cartes l.120");
		nbCartes4.setFont(Font.font("Segoe UI", 20));
		nbCartes4.setTextFill(Color.BLACK);
		Label nomJoueur4 = new Label("Nom Joueur 1 l.123");
		nomJoueur4.setFont(Font.font("Segoe UI", 20));
		nomJoueur4.setTextFill(Color.BLACK);
		
		hHaut.setMargin(j4, new Insets(0,118,0,500));
		j4.setAlignment(Pos.CENTER);
		j4.getChildren().addAll(nbPerso4,nbCartes4,nomJoueur4);
		
		
		hHaut.getChildren().addAll(bPause3,j3,j4,bPause4);		
		hHaut.setAlignment(Pos.BOTTOM_CENTER);
		hHaut.setRotate(180);
		
		////////////////////////////////////////////////
		
		VBox j5 = new VBox();
		Label nbPerso5 = new Label("## personnages l.137");
		nbPerso5.setFont(Font.font("Segoe UI", 20));
		nbPerso5.setTextFill(Color.BLACK);
		Label nbCartes5 = new Label("## de cartes l.140");
		nbCartes5.setFont(Font.font("Segoe UI", 20));
		nbCartes5.setTextFill(Color.BLACK);
		Label nomJoueur5 = new Label("Nom Joueur 1 l.143");
		nomJoueur5.setFont(Font.font("Segoe UI", 20));
		nomJoueur5.setTextFill(Color.BLACK);
		
		j5.setAlignment(Pos.CENTER);
		j5.getChildren().addAll(nbPerso5,nbCartes5,nomJoueur5);
		
		
		hDroite.getChildren().addAll(j5);		
		hDroite.setAlignment(Pos.BOTTOM_CENTER);
		hDroite.setRotate(270);
		
		
		///////////////////////
		/*VBox j6 = new VBox();
		Label nbPerso6 = new Label("## personnages l.157");
		nbPerso6.setFont(Font.font("Segoe UI", 20));
		nbPerso6.setTextFill(Color.BLACK);
		Label nbCartes6 = new Label("## de cartes l.161");
		nbCartes6.setFont(Font.font("Segoe UI", 20));
		nbCartes6.setTextFill(Color.BLACK);
		Label nomJoueur6 = new Label("Nom Joueur 1 l.164");
		nomJoueur6.setFont(Font.font("Segoe UI", 20));
		nomJoueur6.setTextFill(Color.BLACK);
		
		j6.setAlignment(Pos.CENTER);
		j6.getChildren().addAll(nbPerso6,nbCartes6,nomJoueur6);
		
		
		hGauche.getChildren().addAll(j6);		
		hGauche.setAlignment(Pos.BOTTOM_CENTER);
		hGauche.setRotate(90);*/
		
		
		this.setTop(hHaut);
		this.setBottom(hBas);
		this.setLeft(hGauche);
		this.setRight(hDroite);
		this.setCenter(sCentre);
		
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}
}
