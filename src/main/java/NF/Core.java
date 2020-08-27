package NF;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import UI.GUI;
import javafx.application.Platform;


public class Core {
	
	private String ipGroup ="224.7.7.7";
	private int portGroup = 7777;
	private InetAddress group;
	private DatagramSocket socketServer = null;
	private GUI gui = null;
	private long id=0;
	private boolean isConnected = false;
	
	public Core (long i) {
		id = i;
	}
	
	public void setGUI(GUI g) {
		gui = g;
	}
	
	public  void joinUDPMulticastGroup() {
		joinUDPMulticastGroup(ipGroup, portGroup);
	}
	

	public  void joinUDPMulticastGroup(String ip, int p) {
		if (socketServer != null) {
			socketServer.close();
		}
		try {
			socketServer = new DatagramSocket(); // socket de sortie (emission de message)
			group = InetAddress.getByName(ip);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Thread t = new Thread(new ServerUDPThread(this, gui, group, portGroup, id));
	    t.start(); 
	}

	public void sendUDPMessage(String message) {
		byte[] msg=null;
		DatagramPacket packet=null;
		try {
			msg = (""+id+" -> "+message).getBytes("UTF-8");
			packet = new DatagramPacket(msg, msg.length, group, portGroup);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			socketServer.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		if (isConnected) {
			sendUDPMessage("--QUIT--");
			socketServer.close();
		}
		Platform.exit();
	}

	public void setConnected(boolean b) {
		isConnected = b;	
	}
	
}
