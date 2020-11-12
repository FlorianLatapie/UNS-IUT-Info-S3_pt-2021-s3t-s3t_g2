package reseau.socket;

import reseau.tool.PacketTool;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

public class Connection implements Runnable {
    private Socket socket;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;
    private final boolean isRunning;
    private NetWorkManager netWorkManager;
    private final List<String> messages;

    public Connection(Socket socket, NetWorkManager netWorkManager) {
        isRunning = true;
        this.socket = socket;
        this.netWorkManager = netWorkManager;
        this.messages = Collections.synchronizedList(new ArrayList<>());

    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void stop() throws IOException {
        if (socket != null)
            socket.close();
        if (streamIn != null)
            streamIn.close();
        if (streamOut != null)
            streamOut.close();
    }

    public void send(String message) {
        try {
            streamOut.writeUTF(message);
            streamOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Send : " + message);
    }

    public void receive() throws IOException {
        while (isRunning) {
            String message = streamIn.readUTF();
            netWorkManager.getPacketHandlerTcp().traitement(
                    netWorkManager.getPacketsTcp().get(PacketTool.getKeyFromMessage(message)), message, this);
            messages.add(message);
            out.println("Receive : " + message);
        }
    }

    @Override
    public void run() {
        try {
            open();
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String msg) {
        messages.add(msg);
    }

    public String getMessage(String key) {
        for (String m : messages) {
            if (PacketTool.getKeyFromMessage(m).equals(key)) {
                String tmp = m;
                messages.remove(m);
                return tmp;
            }
        }

        return "";
    }

    public boolean isMessage(String key) {
        synchronized (messages) {
            for (String m : messages) {
                if (PacketTool.getKeyFromMessage(m).equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }
}
