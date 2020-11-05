package ihm.event;

import java.util.ArrayList;
import java.util.List;

public class Initializer {
    private final List<JeuListener> listenersjl = new ArrayList<>();
    private final List<FinListener> listenersfl = new ArrayList<>();
    private final List<ConfigListener> listenerscl = new ArrayList<>();

    public void addListenerJeu(JeuListener toAdd) {
        listenersjl.add(toAdd);
    }

    public void addListenerFin(FinListener toAdd) {
        listenersfl.add(toAdd);
    }

    public void addListenerConfig(ConfigListener toAdd) {
        listenerscl.add(toAdd);
    }

    private void test(int lieu, int val) {
        //for (PlateauListener pl : listenerspl)
          //  pl.nbLieuZombie(lieu, val);
    }
}
