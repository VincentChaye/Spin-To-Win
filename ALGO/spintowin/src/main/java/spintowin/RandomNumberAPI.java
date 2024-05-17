package spintowin;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class RandomNumberAPI extends WebSocketServer {
    private int randomNumber;

    public RandomNumberAPI(InetSocketAddress address) {
        super(address);
        // Planifier la génération du nombre aléatoire toutes les 10 secondes
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::generateRandomNumber, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void onStart() {
        // Vous pouvez ajouter des actions spécifiques au démarrage du serveur ici
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // Pas d'action spécifique lorsqu'un client se connecte
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // Pas d'action spécifique lorsqu'un client se déconnecte
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Pas d'action spécifique lorsqu'un message est reçu
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(36); // Générer un nombre entre 0 et 35
        System.out.println("Nouveau nombre aléatoire : " + randomNumber);
    }

    public static void main(String[] args) {
        RandomNumberAPI server = new RandomNumberAPI(new InetSocketAddress(8888)); // Utiliser le port 8888
        server.start();
    }
}

