import java.io.IOException;
import java.net.URISyntaxException;

import botfaible.Idjr;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.tool.NetworkTool;

public class ConsoleBot {
	static Idjr idjr;
	public static void main(String[] args) throws IOException, URISyntaxException {
		idjr = new Idjr();
		NetWorkManager nwm = new NetWorkManager(idjr);
		nwm.initConnection(SideConnection.CLIENT, NetworkTool.getAliveLocalIp());
	}
}
