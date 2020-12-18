package pp.ihm;

import pp.Joueur;
import pp.ihm.DataControl.ApplicationPane;
import pp.ihm.event.IAttenteListener;
import pp.ihm.langues.ITraduction;
import pp.ihm.langues.International;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * The Class AccueilPane.
 *
 * @author Florian
 * @version 0.1
 * @since 26/10/2020
 */
public class AttenteJoueurPane extends StackPane implements IAttenteListener, ITraduction {
	private ScreenControl sControl = null;
	private Core core = null;
	private final ApplicationPane paneName = ApplicationPane.WAIT;

	private int tailleCarreCentral = 800;
	private int hBouton = 75;
	private int lBouton = 150;
	private int hauteurElemtents = 60;
	private int spacing = 30;
	private int tailleCercle = 55;

	private String nomPolice = "Segoe UI";
	private Font policeBouton = Font.font(nomPolice, FontWeight.BOLD, 27);
	private Font policeNom = Font.font(nomPolice, FontWeight.BOLD, 33);

	private String styleBoutonsSouris = "-fx-background-color:#ff0000;  -fx-text-fill:#000000; -fx-background-radius: 15px;";
	private String styleBoutons = " -fx-background-color:#000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
	private String styleVBox = "-fx-border-color: black; -fx-border-insets: -3; -fx-border-width: 3";

	private CornerRadii coin = new CornerRadii(15.0);
	private GaussianBlur flou = new GaussianBlur(30);
	private Insets padding = new Insets(0, 10, 0, 10);

	Label lIDPartie;

	Circle cercle1;
	Circle cercle2;
	Circle cercle3;
	Circle cercle4;
	Circle cercle5;
	Circle cercle6;

	Label titre1;
	Label desc;
	Button bRetour;

	public AttenteJoueurPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;

		// titre
		titre1 = new Label(
				International.trad("texte.titreAttenteA") + "\n" + International.trad("texte.titreAttenteB"));
		titre1.setTextAlignment(TextAlignment.CENTER);
		titre1.setFont(Font.font(nomPolice, FontWeight.BOLD, 80));
		titre1.setTextFill(Color.BLACK);

		VBox titre = new VBox(titre1);
		titre.setAlignment(Pos.CENTER);
		titre.setBackground(new Background(new BackgroundFill(Color.RED, coin, null)));
		titre.setPrefWidth(740);
		titre.setMinWidth(740);

		// identifiant de partie
		lIDPartie = new Label();
		lIDPartie.setStyle("-fx-border-color: black; -fx-border-insets: -3; -fx-border-width: 3");
		lIDPartie.setFont(policeNom);
		lIDPartie.setTextFill(Color.WHITE);
		lIDPartie.setMinHeight(hauteurElemtents);
		lIDPartie.setPadding(padding);

		desc = new Label(International.trad("texte.attenteJoueur"));
		desc.setFont(policeNom);
		desc.setTextFill(Color.WHITE);
		desc.setPadding(new Insets(7));

		VBox vbDescPartie = new VBox();
		vbDescPartie.setAlignment(Pos.CENTER);
		vbDescPartie.getChildren().addAll(lIDPartie, desc);

		// les cercles passent en rouge quand le joueur à rejoint TODO
		cercle1 = new Circle();
		cercle1.setRadius(tailleCercle);
		cercle1.setFill(null);
		cercle1.setStroke(Color.WHITE);
		cercle1.setStrokeWidth(7);

		cercle2 = new Circle();
		cercle2.setRadius(tailleCercle);
		cercle2.setFill(null);
		cercle2.setStroke(Color.WHITE);
		cercle2.setStrokeWidth(7);

		cercle3 = new Circle();
		cercle3.setRadius(tailleCercle);
		cercle3.setFill(null);
		cercle3.setStroke(Color.WHITE);
		cercle3.setStrokeWidth(7);

		cercle4 = new Circle();
		cercle4.setRadius(tailleCercle);
		cercle4.setFill(null);
		cercle4.setStroke(Color.WHITE);
		cercle4.setStrokeWidth(7);

		cercle5 = new Circle();
		cercle5.setRadius(tailleCercle);
		cercle5.setFill(null);
		cercle5.setStroke(Color.WHITE);
		cercle5.setStrokeWidth(7);

		cercle6 = new Circle();
		cercle6.setRadius(tailleCercle);
		cercle6.setFill(null);
		cercle6.setStroke(Color.WHITE);
		cercle6.setStrokeWidth(7);

		HBox hbJoueurPret = new HBox();
		hbJoueurPret.setAlignment(Pos.CENTER);
		hbJoueurPret.setStyle(styleVBox);
		hbJoueurPret.setPadding(new Insets(20));
		hbJoueurPret.setSpacing(12);
		hbJoueurPret.getChildren().addAll(cercle1, cercle2, cercle3, cercle4, cercle5, cercle6);

		VBox vbWait = new VBox();
		vbWait.setStyle(styleVBox);
		vbWait.setAlignment(Pos.CENTER);
		vbWait.setPrefSize(700, 200);
		vbWait.setMaxSize(700, 200);
		vbWait.setMargin(lIDPartie, new Insets(8));
		vbWait.getChildren().addAll(vbDescPartie, hbJoueurPret);

		VBox vbCenter = new VBox();
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setSpacing(spacing);
		vbCenter.getChildren().addAll(vbWait);

		// boutons
		bRetour = new Button(International.trad("bouton.retour"));
		bRetour.setAlignment(Pos.CENTER);
		bRetour.setPrefSize(lBouton, hBouton);
		bRetour.setMinSize(lBouton, hBouton);
		bRetour.setFont(policeBouton);
		bRetour.setStyle(styleBoutons);

		bRetour.setOnMouseEntered(event -> bRetour.setStyle(styleBoutonsSouris));
		bRetour.setOnMouseExited(event -> bRetour.setStyle(styleBoutons));
		bRetour.setOnAction(EventHandler -> {
			core.getCj().stopThreads();
			sc.setPaneOnTop(ApplicationPane.ACCUEIL);
		});

		// grille contenant les boutons du bas
		AnchorPane boutonsPanneau = new AnchorPane();
		boutonsPanneau.setLeftAnchor(bRetour, 0.0);
		boutonsPanneau.getChildren().addAll(bRetour);

		// image fond
		ImageView imgFond = new ImageView(DataControl.FOND);
		BorderPane centreMenu = new BorderPane();
		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);

		centreMenu.setAlignment(titre, Pos.CENTER);

		centreMenu.setTop(titre);
		centreMenu.setCenter(vbCenter);
		centreMenu.setBottom(boutonsPanneau);

		// boite du fond qui contient le fond et les autres boites
		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(fond, centreMenu);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}

	@Override
	/**
	 * Dès que les joueurs sont prets, le pane change vers le choix des couleurs de
	 * chaque joueur
	 */
	public void joueurPret() {
		sControl.setPaneOnTop(ApplicationPane.COULEUR);
	}

	@Override
	/**
	 * applique le nom de la partie sur le label lIDPartie
	 * 
	 * @param nom le nom de la partie
	 */
	public void nomPartie(String id, String nom) {
		lIDPartie.setText(nom + " - " + id);
	}

	@Override
	public void updateJoueurs(List<Joueur> joueurs, int max) {
		Circle[] circles = { cercle1, cercle2, cercle3, cercle4, cercle5, cercle6 };
		for (int i = 0; i < circles.length; i++) {
			if (i < max) {
				if (i < joueurs.size())
					circles[i].setFill(Color.GREEN);
				else
					circles[i].setFill(null);

				circles[i].setVisible(true);
			} else
				circles[i].setVisible(false);
		}
	}

	@Override
	public void traduire() {
		titre1.setText(International.trad("texte.titreAttenteA") + "\n" + International.trad("texte.titreAttenteB"));
		desc.setText(International.trad("texte.attenteJoueur"));
		bRetour.setText(International.trad("bouton.retour"));

	}

}