import java.io.IOException;
import java.net.URISyntaxException;

import idjr.Idjr;
import reseau.socket.NetWorkManager;
import reseau.socket.SideConnection;
import reseau.tool.NetworkTool;

public class MainConsole {
	static Idjr idjr;
	public static void main(String[] args) throws IOException, URISyntaxException {
		idjr = new Idjr();
		NetWorkManager nwm = new NetWorkManager(idjr);
		nwm.initConnection(SideConnection.CLIENT, NetworkTool.getAliveLocalIp());
	}
}
