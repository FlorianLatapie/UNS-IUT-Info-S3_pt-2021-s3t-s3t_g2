package idjr.ihmidjr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import idjr.ihmidjr.langues.Langues;

public class SauvegarderOptions implements Serializable {

	private static final long serialVersionUID = 3409175594291680627L;

	private Langues langues;
	private boolean estPleineEcran;
	private int angleRotation;
	private String nom;

	private static String CHEMIN = "options1.opts";

	public SauvegarderOptions() {
		File opt = new File(CHEMIN);
		if (!opt.exists()) {
			langues = Langues.FR;
			estPleineEcran = true;
			angleRotation = 0;
			nom = "";
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
			estPleineEcran = so.isEstPleineEcran();
			angleRotation = so.getAngleRotation();
			nom = so.getNom();
		}
	}

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

	public boolean isEstPleineEcran() {
		return estPleineEcran;
	}

	public void setEstPleineEcran(boolean estPleineEcran) {
		this.estPleineEcran = estPleineEcran;
		sauvegarder();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
		sauvegarder();
	}
}
