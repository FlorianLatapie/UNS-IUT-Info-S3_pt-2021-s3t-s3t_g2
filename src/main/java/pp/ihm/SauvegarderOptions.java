package pp.ihm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import pp.ihm.event.Initializer;
import pp.ihm.langues.Langues;
/**
 * 
 * @author Sebastien 
 *
 */
public class SauvegarderOptions implements Serializable {

	private static final long serialVersionUID = 3409175594291680627L;

	private Langues langues;
	private boolean estPleinEcran;
	private int angleRotation;

	private static String CHEMIN = "options.opts";

	/**
	 * Sauvegarde les options dans le chemin en attribut 
	 */
	public SauvegarderOptions() {
		File opt = new File(CHEMIN);
		if (!opt.exists()) {
			langues = Langues.FR;
			estPleinEcran = true;
			angleRotation = 0;
			sauvegarder();
		} else {
			SauvegarderOptions so = null;
			try {
				FileInputStream fis = new FileInputStream(CHEMIN);
				ObjectInputStream ois = new ObjectInputStream(fis);
				so = (SauvegarderOptions) ois.readObject();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			langues = so.getLangues();
			estPleinEcran = so.isEstPleinEcran();
			angleRotation = so.getAngleRotation();
		}
	}

	/**
	 * Sauvegarde les options dans le chemin en attribut 
	 */
	public void sauvegarder() {
		FileOutputStream fioust;
		try {
			fioust = new FileOutputStream(CHEMIN);
			ObjectOutputStream oboust = new ObjectOutputStream(fioust);
			oboust.writeObject(this);
			oboust.flush();
			oboust.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Langues getLangues() {
		return langues;
	}

	public void setLangues(Langues langues) {
		this.langues = langues;
		sauvegarder();
	}

	public int getAngleRotation() {
		return angleRotation;
	}

	public void setAngleRotation(int angleRotation) {
		this.angleRotation = angleRotation;
		sauvegarder();
	}

	public boolean isEstPleinEcran() {
		return estPleinEcran;
	}

	public void setEstPleinEcran(boolean estPleinEcran) {
		this.estPleinEcran = estPleinEcran;
		sauvegarder();
	}
}
