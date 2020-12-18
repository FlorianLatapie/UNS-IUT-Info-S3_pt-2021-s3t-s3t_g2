
package idjr.ihmidjr;

import idjr.ihmidjr.DataControl.ApplicationPane;
import idjr.ihmidjr.event.IAttenteListener;
import idjr.ihmidjr.langues.ITraduction;
import idjr.ihmidjr.langues.International;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	private CornerRadii coin = new CornerRadii(15.0);
	private StackPane stackPane = new StackPane();
	private GaussianBlur flou = new GaussianBlur(30);
	private String styleVBox = "-fx-border-color: black; -fx-border-insets: -3; -fx-border-width: 3";
	
	private String nomPolice = "Segoe UI";
	private Font policeNom = Font.font(nomPolice, FontWeight.BOLD, 33);
	private int hauteurElemtents = 60;
	private int spacing = 30;
	private int tailleCercle = 55;
	private Insets padding = new Insets(0, 10, 0, 10);
	Label lIDPartie;

	Label titre1;
	Label desc;

	public AttenteJoueurPane(ScreenControl sc, Core c) {
		core = c;
		sControl = sc;
		stackPane.setAlignment(Pos.CENTER);

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

		lIDPartie = new Label();
		lIDPartie.setStyle("-fx-border-color: black; -fx-border-insets: -3; -fx-border-width: 3");
		lIDPartie.setFont(policeNom);
		lIDPartie.setTextFill(Color.WHITE);
		lIDPartie.setMinHeight(hauteurElemtents);
		lIDPartie.setPadding(padding);

		desc = new Label(International.trad("texte.attenteJoueur"));
		desc.setFont(Font.font(nomPolice, FontWeight.BOLD, 27));
		desc.setTextFill(Color.WHITE);
		desc.setPadding(new Insets(7));

		VBox vbDescPartie = new VBox();
		vbDescPartie.setAlignment(Pos.CENTER);
		vbDescPartie.getChildren().addAll(lIDPartie, desc);

		// les cercles passent en rouge quand le joueur à rejoint
		Circle cercle1 = new Circle();
		cercle1.setRadius(tailleCercle);
		cercle1.setFill(null);
		cercle1.setStroke(Color.WHITE);
		cercle1.setStrokeWidth(7);

		Circle cercle2 = new Circle();
		cercle2.setRadius(tailleCercle);
		cercle2.setFill(null);
		cercle2.setStroke(Color.WHITE);
		cercle2.setStrokeWidth(7);

		Circle cercle3 = new Circle();
		cercle3.setRadius(tailleCercle);
		cercle3.setFill(null);
		cercle3.setStroke(Color.WHITE);
		cercle3.setStrokeWidth(7);

		Circle cercle4 = new Circle();
		cercle4.setRadius(tailleCercle);
		cercle4.setFill(null);
		cercle4.setStroke(Color.WHITE);
		cercle4.setStrokeWidth(7);

		Circle cercle5 = new Circle();
		cercle5.setRadius(tailleCercle);
		cercle5.setFill(null);
		cercle5.setStroke(Color.WHITE);
		cercle5.setStrokeWidth(7);

		Circle cercle6 = new Circle();
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

		// image fond
		ImageView imgFond = new ImageView(DataControl.FOND);

		// carre central qui contient tous les éléments (boutons et titre)
		BorderPane centreMenu = new BorderPane();
		centreMenu.setMinSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setPrefSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setMaxSize(tailleCarreCentral, tailleCarreCentral);
		centreMenu.setAlignment(titre, Pos.CENTER);
		centreMenu.setTop(titre);
		centreMenu.setCenter(vbCenter);

		// boite du fond qui contient tout
		HBox fond = new HBox();
		fond.setAlignment(Pos.CENTER);
		fond.setPrefWidth(100);
		fond.setPrefHeight(100);
		fond.setEffect(flou);
		fond.getChildren().add(imgFond);

		stackPane.getChildren().addAll(fond, centreMenu);
		stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));

		this.getChildren().add(stackPane);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);

	}
	/*
	 * @Override public void joueurPret() {
	 * sControl.setPaneOnTop(ApplicationPane.COULEUR); }
	 * 
	 * @Override public void nomPartie(String nom) { lIDPartie.setText(nom); }
	 */

	@Override
	public void stopWait() {
		Platform.runLater(() -> sControl.setPaneOnTop(ApplicationPane.JEU));
	}

	@Override
	public void nomPartie(String nom) {
		Platform.runLater(() -> lIDPartie.setText(nom));
	}

	@Override
	public void traduire() {
		titre1.setText(International.trad("texte.titreAttenteA") + "\n" + International.trad("texte.titreAttenteB"));
		desc.setText(International.trad("texte.attenteJoueur"));

	}
}
