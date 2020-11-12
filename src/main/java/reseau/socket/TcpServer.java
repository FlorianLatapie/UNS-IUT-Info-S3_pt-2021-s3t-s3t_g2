package reseau.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class TcpServer implements Runnable {
    private final List<Connection> connections;
    private ServerSocket ssock;
    private boolean isRunning;
    private NetWorkManager netWorkManager;

    public TcpServer(NetWorkManager netWorkManager) {
        connections = new ArrayList<>();
        isRunning = true;
        this.netWorkManager = netWorkManager;
    }

    @Override
    public void run() {
        try {
            ssock = new ServerSocket(5555);
        } catch (IOException e1) {
            out.println("Server stopped");
            return;
        }
        out.println("Server started !!!!");

        while (isRunning) {
            Socket sock;
            try {
                sock = ssock.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            out.println("Connected");
            Connection connection = new Connection(sock, netWorkManager);
            new Thread(connection).start();
            connections.add(connection);
        }

        try {
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        for (Connection connection : connections)
            connection.stop();
        isRunning = false;
        if (ssock != null)
            ssock.close();
    }

    public List<Connection> getConnections() {
        return connections;
    }
}
