import java.io.IOException;
import java.net.URISyntaxException;

import botfaible.Idjr;
import botfaible.TraitementPaquetTcp;
import botfaible.TraitementPaquetUdp;
import reseau.socket.ConnexionType;
import reseau.socket.ControleurReseau;
import reseau.tool.ReseauOutils;

public class ConsoleBot {
	static Idjr idjr;
	public static void main(String[] args) throws IOException, URISyntaxException {
		idjr = new Idjr();
		TraitementPaquetTcp packetHandlerTcp = new TraitementPaquetTcp(idjr);
		TraitementPaquetUdp packetHandlerUdp = new TraitementPaquetUdp(idjr);
		ControleurReseau nwm = new ControleurReseau(packetHandlerTcp, packetHandlerUdp);
		nwm.initConnexion(ConnexionType.CLIENT, ReseauOutils.getLocalIp());
	}
}
