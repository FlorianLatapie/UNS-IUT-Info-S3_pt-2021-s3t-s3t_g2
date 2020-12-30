package idjr.ihmidjr;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.IPleineEcranListener;
import idjr.ihmidjr.langues.ITraduction;
import idjr.ihmidjr.langues.International;
import idjr.ihmidjr.langues.Langues;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 
 * @author florian
 * @author tom 
 * @author sebastien 
 *
 */
public class OptionPane extends StackPane implements IPleineEcranListener, ITraduction {
	//auteur florian 
	private ScreenControl sControl = null;
	private Core core = null;
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private String styleTitre = "-fx-text-fill: #ff1c16";
	private final ApplicationPane paneName = ApplicationPane.OPTION;
	private int tailleCarreCentral = 700;
	private Font policeTitre = Font.font("Segoe UI", FontWeight.BOLD, 75);
	private Font policeBouton = Font.font("Segoe UI", FontWeight.BOLD, 33);
	private int hauteurElement = 60;

	Button bPleinEcran;
	Label titre;
	Button bFrancais;
	Button bEnglish;
	Button bRetour;

	public OptionPane(ScreenControl sc, Core c) {
		//auteur florian 
		core = c;
		sControl = sc;

		stackPane.setAlignment(Pos.CENTER);

		Rectangle rect = new Rectangle();
		rect.setWidth(tailleCarreCentral);
		rect.setHeight(tailleCarreCentral);
		rect.setArcHeight(30);
		rect.setArcWidth(30);
		rect.setOpacity(.3);

		VBox vbFond = new VBox();
		vbFond.setAlignment(Pos.CENTER);
		vbFond.setSpacing(20);
		vbFond.setEffect(flou);

		HBox hbBRetour = new HBox();
		hbBRetour.setAlignment(Pos.BOTTOM_LEFT);
		hbBRetour.setPrefSize(tailleCarreCentral, hauteurElement);
		hbBRetour.setMinSize(tailleCarreCentral, hauteurElement);
		hbBRetour.setMaxSize(tailleCarreCentral, hauteurElement);

		VBox vbCentral = new VBox();
		vbCentral.setAlignment(Pos.CENTER);
		vbCentral.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setMinSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		vbCentral.setPadding(new Insets(10));

		VBox vbTitre = new VBox();
		vbTitre.setAlignment(Pos.TOP_CENTER);
		vbTitre.setTranslateY(-40);

		HBox hbLang = new HBox();
		hbLang.setAlignment(Pos.CENTER);
		hbLang.setSpacing(10);

		VBox vbBoutons = new VBox();
		vbBoutons.setAlignment(Pos.CENTER);
		vbBoutons.setTranslateY(-40);
		vbBoutons.setSpacing(15);

		titre = new Label(International.trad("bouton.options"));
		titre.setStyle(styleTitre);
		titre.setFont(policeTitre);
		vbTitre.getChildren().add(titre);

		bFrancais = new Button(International.trad("texte.langue1"));
		bFrancais.setFont(policeBouton);
		bFrancais.setAlignment(Pos.CENTER);
		bFrancais.setPrefSize(245, hauteurElement);
		bFrancais.setStyle(styleBoutons);
		bFrancais.setOnAction(EventHandler -> sc.setPaneOnTop(ApplicationPane.OPTION));

		bFrancais.setOnMouseEntered(event -> {
			bFrancais.setStyle(styleBoutonsSouris);
		});
		bFrancais.setOnMouseExited(event -> {
			bFrancais.setStyle(styleBoutons);
		});
		bFrancais.setOnAction(EventHandler -> {
			core.changerLangue(Langues.FR);
		});

		bEnglish = new Button(International.trad("texte.langue2"));
		bEnglish.setFont(policeBouton);
		bEnglish.setAlignment(Pos.CENTER);
		bEnglish.setPrefSize(245, hauteurElement);
		bEnglish.setStyle(styleBoutons);
		bEnglish.setOnMouseEntered(event -> {
			bEnglish.setStyle(styleBoutonsSouris);
		});
		bEnglish.setOnMouseExited(event -> {
			bEnglish.setStyle(styleBoutons);
		});
		bEnglish.setOnAction(EventHandler -> {
			core.changerLangue(Langues.EN);
		});

		bPleinEcran = new Button(International.trad("bouton.pEcran"));
		bPleinEcran.setFont(policeBouton);
		bPleinEcran.setAlignment(Pos.CENTER);
		bPleinEcran.setPrefSize(500, hauteurElement);
		bPleinEcran.setStyle(styleBoutons);
		bPleinEcran.setOnMouseEntered(event -> bPleinEcran.setStyle(styleBoutonsSouris));
		bPleinEcran.setOnMouseExited(event -> bPleinEcran.setStyle(styleBoutons));
		bPleinEcran.setOnAction(EventHandler -> {
			Stage stage = (Stage) bPleinEcran.getScene().getWindow();
			if (core.getSauvegarderOptions().isEstPleineEcran()) {
				stage.setFullScreen(false);
				core.getSauvegarderOptions().setEstPleineEcran(false);
				bPleinEcran.setText(International.trad("bouton.pEcran"));
			} else {
				stage.setFullScreen(true);
				core.getSauvegarderOptions().setEstPleineEcran(true);
				bPleinEcran.setText(International.trad("bouton.fenetre"));
			}
		});

		bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setFont(policeBouton);
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(180, hauteurElement);
		bRetour.setStyle(styleBoutons);
		bRetour.setOnAction(EventHandler -> sc.setPaneOnTop(core.getPauseDepuis()));
		bRetour.setOnMouseEntered(event -> {
			bRetour.setStyle(styleBoutonsSouris);
		});
		bRetour.setOnMouseExited(event -> {
			bRetour.setStyle(styleBoutons);
		});

		hbBRetour.getChildren().add(bRetour);
		hbLang.getChildren().addAll(bFrancais, bEnglish);
		vbBoutons.getChildren().addAll(hbLang, bPleinEcran);
		vbBoutons.setMargin(vbTitre, new Insets(140));
		vbBoutons.setMargin(hbBRetour, new Insets(140, 0, 30, 15));

		vbCentral.getChildren().addAll(vbTitre, vbBoutons, hbBRetour);
		ImageView img = new ImageView(DataControl.FOND);
		vbFond.getChildren().add(img);

		stackPane.getChildren().addAll(vbFond, rect, vbCentral);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

	/**
	 * Met a jour le bouton du mode plein écran
	 * @author sebastien
	 */
	@Override
	public void updatePleineEcran() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!core.getSauvegarderOptions().isEstPleineEcran())
					bPleinEcran.setText(International.trad("bouton.pEcran"));
				else
					bPleinEcran.setText(International.trad("bouton.fenetre"));
			}
		});
	}

	/**
	 * Traduit les elements de ce pane
	 */
	@Override
	public void traduire() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				titre.setText(International.trad("bouton.options"));
				bFrancais.setText(International.trad("texte.langue1"));
				bEnglish.setText(International.trad("texte.langue2"));
				bPleinEcran.setText(International.trad("bouton.pEcran"));
				bRetour.setText(International.trad("bouton.retour"));
			}
		});
	}
}