package ihm;

import ihm.ScreenControl;
import ihm.DataControl.ApplicationPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

/**
 * The Class AccueilPane.
 * 
 * @author Florian
 * @version 0.1
 * @since 04/10/2020
 */
public class AccueilPane extends BorderPane { 
	//ScreenControl permet de gérer les variables globales comme la langue ou le volume 
	private ScreenControl sControl = null;
	private final ApplicationPane paneName = ApplicationPane.ACCUEIL; //nom du pane 
	//définition des variable pour la suite du pane 
	private int tailleCarreCentral = 600; //l'interface est sur un stackPane qui peut trouner avec des crans dd 90 degrés 
	private int hBouton = 100;
	private int lBouton = 200;
	private int marge = tailleCarreCentral / 25;
	private Insets margeBoutons = new Insets(marge, marge, marge, marge);
	private Font policeBouton = Font.font("Segoe UI", tailleCarreCentral / 17);
	private CornerRadii coin = new CornerRadii(15.0);
	private String coinBoutons = " -fx-background-radius: 20px";

	public AccueilPane(ScreenControl sc) {

		sControl = sc;
		
		// titre 
		Label titre1 = new Label("ZOMBIES");
		titre1.setFont(Font.font("Segoe UI", 60));
		titre1.setTextFill(Color.BLACK);
		
		Label titre2 = new Label("LA BLONDE LA BRUTE ET LE TRUAND");
		titre2.setFont(Font.font("Segoe UI", 25));
		titre2.setTextFill(Color.BLACK);
		
		VBox titre = new VBox(titre1,titre2);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		
		
		// boutons 
		Button bJouer = new Button("Jouer");
		bJouer.setPrefSize(lBouton, hBouton);
		bJouer.setMinSize(lBouton, hBouton);
		bJouer.setFont(policeBouton);
		bJouer.setStyle(coinBoutons);

		Button bOptions = new Button("Options");
		bOptions.setPrefSize(lBouton, hBouton);
		bOptions.setMinSize(lBouton, hBouton);
		bOptions.setFont(policeBouton);
		bOptions.setStyle(coinBoutons);
		
		Button bRegles = new Button("Règles");
		bRegles.setPrefSize(lBouton, hBouton);
		bRegles.setMinSize(lBouton, hBouton);
		bRegles.setFont(policeBouton);
		bRegles.setStyle(coinBoutons);

		Button bQuitter = new Button("Quitter");
		bQuitter.setPrefSize(lBouton, hBouton);
		bQuitter.setMinSize(lBouton, hBouton);
		bQuitter.setFont(policeBouton);
		bQuitter.setStyle(coinBoutons);

		// Jouer.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.JOUER));
		bOptions.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));
		// Regles.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.Regles));
		bQuitter.setOnAction(event -> {
			Platform.runLater(new Runnable() {
				public void run() {
					Platform.exit();
				}
			});
		});

		
		// grille contenant les 4 boutons 
		GridPane grilleBoutons = new GridPane();
		grilleBoutons.setAlignment(Pos.CENTER);
		grilleBoutons.add(bJouer, 0, 0);
		grilleBoutons.add(bOptions, 0, 1);
		grilleBoutons.add(bRegles, 1, 0);
		grilleBoutons.add(bQuitter, 1, 1);

		grilleBoutons.setMargin(bJouer, margeBoutons);
		grilleBoutons.setMargin(bOptions, margeBoutons);
		grilleBoutons.setMargin(bRegles, margeBoutons);
		grilleBoutons.setMargin(bQuitter, margeBoutons);
		
		// image fond
		//ImageView imgFond = new ImageView("../existe pas.extension");
		
		
		// carre central qui contient tous les éléments (bouton et titre) 
		VBox centreMenu = new VBox();
		//centreMenu.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,null)));
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setAlignment(Pos.CENTER);
		centreMenu.setMargin(titre, new Insets(0, 0, 100, 0));
		//titre.setPadding(margeBoutons);
		centreMenu.getChildren().addAll(titre, grilleBoutons);
		
		// rotation de l'interface 
		//centreMenu.setRotate(90);
		
		// boite du fond qui contient tout 
		HBox fond = new HBox(centreMenu);
		fond.setAlignment(Pos.CENTER);
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,null)));
		//fond.getChildren().add(imgFond);
		this.setCenter(fond);

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}

}