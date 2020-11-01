package ihm;

import ihm.DataControl.ApplicationPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AccessibilitePane extends StackPane{
	
	private ScreenControl sControl = null;
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private final ApplicationPane paneName = ApplicationPane.ACCESSIBILITE;

	public AccessibilitePane(ScreenControl sc) {

		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);
		
		Rectangle rect = new Rectangle();
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.LIGHTGRAY);
		rect.setWidth(700);
		rect.setHeight(600);
		rect.setX(100);
		rect.setY(100);
		rect.setOpacity(.8);
		
		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);
		
		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.CENTER);
		vbCentral.setPrefSize(700, 600);
		vbCentral.setMinSize(700, 600);
		vbCentral.setMaxSize(700, 600);
		
		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.CENTER);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		
		VBox vbCheckBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		
		VBox vbRetour = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		vbRetour.setPadding(new Insets(10));
		
		HBox hbDaltonisme = new HBox();
		hbDaltonisme.setAlignment(Pos.CENTER);

		Label titre = new Label("Accessibilité");
		titre.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		vbTitre.getChildren().add(titre);
		vbTitre.setMargin(vbBoutons, new Insets(10));
		
		
		Label titreVbBoutons = new Label("Daltonisme");
		titreVbBoutons.setFont(Font.font("Arial", 40));
		vbBoutons.getChildren().add(titreVbBoutons);
		vbBoutons.setMargin(vbCheckBoutons, new Insets(20));

		Label fr = new Label("Français");
		Label en = new Label("English");

		fr.setFont(Font.font("Arial", 20));
		fr.setAlignment(Pos.CENTER);

		en.setFont(Font.font("Arial", 20));
		en.setAlignment(Pos.CENTER);

		Button bDeuteranopie = new Button("Deutéranopie");
		bDeuteranopie.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		bDeuteranopie.setAlignment(Pos.CENTER);
		bDeuteranopie.setPrefSize(1500, 80);
		
		Button bProtanopie = new Button("Protanopie");
		bProtanopie.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		bProtanopie.setAlignment(Pos.CENTER);
		bProtanopie.setPrefSize(1500, 80);

		Button bTritanopie = new Button("Tritanopie");
		bTritanopie.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		bTritanopie.setAlignment(Pos.CENTER);
		bTritanopie.setPrefSize(1500, 80);
		
		CheckBox bHematophobie = new CheckBox("Hématophobie");
		bHematophobie.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bHematophobie.setAlignment(Pos.CENTER_LEFT);
		bHematophobie.setPrefSize(500, 60);
		
		CheckBox bAudio = new CheckBox("Audio description");
		bAudio.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bAudio.setAlignment(Pos.CENTER_LEFT);
		bAudio.setPrefSize(500, 60);

		Button bRetour = new Button("Retour");
		bRetour.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(120, 60);
		bRetour.setPadding(new Insets(10));
		//bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTIONS));

		hbDaltonisme.getChildren().addAll(bDeuteranopie, bProtanopie, bTritanopie);
		vbBoutons.getChildren().add(hbDaltonisme);
		vbRetour.getChildren().add(bRetour);
		vbCheckBoutons.getChildren().addAll(bHematophobie, bAudio);

		vbCentral.getChildren().addAll(vbTitre, vbBoutons, vbCheckBoutons, vbRetour);

		ImageView img = new ImageView("http://tof.canardpc.com/view/3bd6756b-7865-4945-b524-426f6cd21e6c.jpg");
		vbFond.getChildren().add(img);
		
		stackPane.getChildren().addAll(vbFond, rect, vbCentral);

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}
}
