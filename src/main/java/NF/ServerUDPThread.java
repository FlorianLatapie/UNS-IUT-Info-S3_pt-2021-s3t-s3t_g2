package NF;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import UI.GUI;
import javafx.application.Platform;


public class ServerUDPThread implements Runnable {
	
	private int portGroup;
	private InetAddress group;
	private long id=0;
	private GUI gui;
	private Core core;

	public ServerUDPThread(Core c, GUI gu, InetAddress g, int p,long i) {
		portGroup = p;
		group = g;
		id = i;
		gui = gu;
		core = c;
	}

	@Override
	public void run() {
		byte[] buffer=new byte[1024];
	    MulticastSocket socket;
		try {
			socket = new MulticastSocket(portGroup); //socket de reception
			socket.joinGroup(group);
			core.setConnected(true);
			Platform.runLater(new Runnable() {
			    @Override public void run() {
			    	gui.setConnected();
			    }
			});
			gui.displayLog("[Message Local de "+id+"] Attente d'un message Multicast ...\n");
		    while(true){
		        DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		        socket.receive(packet);
		        String msg=new String(packet.getData(),packet.getOffset(),packet.getLength(),"UTF-8");
		        if (!gui.isLocalMsgSkip() || !msg.startsWith(""+id+" ->")) {
		        	gui.displayLog("[Message Multicast re�u par "+id+"] Reception du message de "+msg+"\n");
		        }
		        if(msg.contains(""+id+" -> --QUIT--")) {
		        	gui.displayLog("[Message local de "+id+"] Message de fin d'�coute d�tect�\n");
		            break;
		        }
		    }
		    socket.leaveGroup(group);
		    socket.close();
		    core.setConnected(false);
		    Platform.runLater(new Runnable() {
			    @Override public void run() {
			    	gui.setDisconnected();
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
