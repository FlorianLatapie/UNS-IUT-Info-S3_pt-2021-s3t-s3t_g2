package ihm.eventListener;

import java.util.Map;

import jeu.Lieu;

public interface LieuxListener {
	void lieuxChanged(Map<Integer,Lieu> lieux);
}
