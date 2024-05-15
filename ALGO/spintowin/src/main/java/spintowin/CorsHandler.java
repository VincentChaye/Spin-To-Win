package spintowin;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class CorsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Ajoutez les en-têtes CORS à toutes les réponses
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Traitez les demandes OPTIONS ici (requêtes de pré-vérification CORS)
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(200, -1); // Répondez avec succès sans corps pour les demandes OPTIONS
            return;
        }

        // Si ce n'est pas une demande OPTIONS, transmettez-la à d'autres gestionnaires de requêtes
        exchange.sendResponseHeaders(404, 0); // Répondez avec une erreur 404 si aucune correspondance de chemin n'est trouvée
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.close();
    }
}
