import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class SimpleHttpServer {
    
    public static void main(String[] args) throws IOException {
        // Créer un serveur HTTP sur le port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Définir le gestionnaire de requêtes pour le chemin "/resource1"
        server.createContext("/resource1", new Resource1Handler());
        
        // Définir le gestionnaire de requêtes pour le chemin "/resource2"
        server.createContext("/resource2", new Resource2Handler());
        
        // Démarrer le serveur
        server.start();
        
        System.out.println("Server started on port 8000");
    }
}

// Gestionnaire de requêtes pour le chemin "/resource1"
class Resource1Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Received request for /resource1");
        
        String response = "Babam Babam";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

class Resource2Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        String[] parts = requestPath.split("/");

        // Vérifie si le chemin de la requête a le format attendu
        if (parts.length != 3 || !parts[1].equals("resource2")) {
            exchange.sendResponseHeaders(404, 0); // Renvoie une réponse 404 si le chemin n'est pas correct
            return;
        }

        try {
            // Extrait le nombre de la partie de l'URL après /resource2/
            int number = Integer.parseInt(parts[2]);
            
            // Calcule le double du nombre
            int result = number * 2;
            
            // Prépare la réponse
            String response = String.valueOf(result);
            
            // Envoie la réponse
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (NumberFormatException e) {
            // Renvoie une réponse 400 si le nombre n'est pas valide
            exchange.sendResponseHeaders(400, 0);
            return;
        }
    }
}
