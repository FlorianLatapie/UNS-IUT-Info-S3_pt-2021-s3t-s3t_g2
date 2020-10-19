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


public class OptionPane extends BorderPane { //ATTENTION : le type Pane est à changer par le type de votre choix.

	private ScreenControl sControl = null;
	private final ApplicationPane paneName = ApplicationPane.OPTION;


	public OptionPane(ScreenControl sc) {

		sControl = sc;
		
	
	
		
		//this.setTop(hbTop);
		//this.setLeft(vbLeft);
		//this.setRight(vbRight);
		//this.setCenter(vbCentral);
		sControl.registerNode(paneName, this);
		sControl.setPaneOnTop(paneName);
	}

}
