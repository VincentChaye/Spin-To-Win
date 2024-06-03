package spintowin;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import java.util.Timer;
import java.util.TimerTask;

public class SocketServer {

    private static int counter = 0;

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3000);

        final SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            System.out.println("Client connected: " + client.getSessionId());
        });

        server.addDisconnectListener(client -> {
            System.out.println("Client disconnected: " + client.getSessionId());
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter++;
                server.getBroadcastOperations().sendEvent("number", counter);
            }
        }, 0, 10000);

        server.start();
        System.out.println("Server is running on port 3000");

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}
