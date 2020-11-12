package reseau.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reseau.tool.PacketTool;

import static java.lang.System.out;

public class TcpClient {
    private Socket socket = null;
    private DataOutputStream streamOut = null;
    private DataInputStream streamIn = null;
    private boolean isRunning;
    private NetWorkManager netWorkManager;
    private final List<String> messages;

    public TcpClient(NetWorkManager netWorkManager) throws IOException {
        this.messages = Collections.synchronizedList(new ArrayList());
        ;
        this.netWorkManager = netWorkManager;
        this.isRunning = true;
    }

    public boolean start(boolean canReceive, String serverName, int serverPort) throws IOException {

        out.println("Attente ...");
        boolean wait = true;
        while (wait)
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                socket = new Socket(serverName, serverPort);
                wait = false;
                out.println("Connected: " + socket);
            } catch (UnknownHostException uhe) {
                out.println("UnknownHostException: " + uhe.getMessage());
            } catch (IOException ioe) {
                out.println("IOException: " + ioe.getMessage());
            }

        if (socket == null)
            return false;

        streamOut = new DataOutputStream(socket.getOutputStream());
        streamIn = new DataInputStream(socket.getInputStream());

        if (canReceive)
            receive();

        return true;
    }

    public void send(String message) throws IOException {
        if (socket == null)
            return;

        streamOut.writeUTF(message);
        streamOut.flush();
        out.println("Send : " + message);
    }

    private void receive() throws IOException {
        String message = "";
        while (isRunning) {
            try {
                message = streamIn.readUTF();
            } catch (Exception e) {
                continue;
            }

            netWorkManager.getPacketHandlerTcp()
                    .traitement(netWorkManager.getPacketsTcp().get(PacketTool.getKeyFromMessage(message)), message);
            messages.add(message);
            out.println("Receive : " + message);
        }
    }

    public void stop() throws IOException {
        isRunning = false;

        if (streamOut != null)
            streamOut.close();

        if (streamIn != null)
            streamIn.close();

        if (socket != null)
            socket.close();

        out.println("Server stopped");
    }

    // REVOIR
    public boolean isReady() {
        if (socket == null)
            return false;
        return isRunning && !socket.isClosed();
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
        for (String m : messages) {
            if (PacketTool.getKeyFromMessage(m).equals(key)) {
                return true;
            }
        }

        return false;
    }
}
