package spintowin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
public class SimpleHttpServer1 {
    private static HttpServer server; // Ajout d'un champ statique pour stocker l'instance du serveur

    public static void main(String[] args) throws IOException {
        // Créez le serveur HTTP sur le port 8000
        server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Définissez les gestionnaires de requêtes pour les différents chemins
        server.createContext("/resource1", new Resource1Handler());
        server.createContext("/resource2", new Resource2Handler());
        server.createContext("/player", new PlayerHandler());
        server.createContext("/player/name", new PlayerHandlerName());
        server.createContext("/player/new", new PlayerHandlerNew());
        server.createContext("/player/pseudo", new PlayerHandlerAllPseudo());
        server.createContext("/player/auth", new PlayerHandlerAuth());

        // Activez le CORS globalement
        server.setExecutor(null); // Utilisation d'un gestionnaire d'exécution null pour un démarrage par défaut

        // Démarrer le serveur
        server.start();

        System.out.println("Server started on port 8000");
    }

    // Méthode statique pour obtenir l'instance du serveur
    public static HttpServer getServer() {
        return server;
    }
}

// Request handler for the path "/resource1"
class Resource1Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Received request for /resource1");
        
        String response = "Babam Babam";
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(200, response.getBytes().length);
            os.write(response.getBytes());
        }
    }
}

// Request handler for the path "/resource2"
class Resource2Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        String[] parts = requestPath.split("/");

        // Check if the request path has the expected format
        if (parts.length != 3 || !parts[1].equals("resource2")) {
            exchange.sendResponseHeaders(404, 0); // Send a 404 response if the path is not correct
            return;
        }

        try {
            // Extract the number from the URL part after /resource2/
            int number = Integer.parseInt(parts[2]);
            
            // Calculate the double of the number
            int result = number * 2;
            
            // Prepare the response
            String response = String.valueOf(result);
            
            // Send the response
            try (OutputStream os = exchange.getResponseBody()) {
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os.write(response.getBytes());
            }
        } catch (NumberFormatException e) {
            // Send a 400 response if the number is not valid
            exchange.sendResponseHeaders(400, 0);
        }
    }
}
class PlayerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extracting the ID from the request path
        String requestPath = exchange.getRequestURI().getPath();
        String[] parts = requestPath.split("/");

        // Check if the request path has the expected format
        if (parts.length != 3 || !parts[1].equals("player")) {
            exchange.sendResponseHeaders(404, 0); // Send a 404 response if the path is not correct
            return;
        }

        try {
            // Extract the player ID from the URL part after /player/
            int playerId = Integer.parseInt(parts[2]);
            
            // Call getJoueurById to retrieve player details
            Joueur playerDetails = DatabaseManager.getJoueurById(playerId);
            
            // Check if the player exists
            if (playerDetails != null) {
                // Convert playerDetails to a string representation
                String playerDetailsString = playerDetails.toString();
                
                // Send the player details as response
                byte[] responseBytes = playerDetailsString.getBytes();
                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } else {
                // Send a 404 response if the player does not exist
                exchange.sendResponseHeaders(404, 0);
            }
        } catch (NumberFormatException e) {
            // Send a 400 response if the ID is not valid
            exchange.sendResponseHeaders(400, 0);
        }
    }}

class PlayerHandlerName implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extraction du pseudo à partir du chemin de la requête
        String requestPath = exchange.getRequestURI().getPath();
        String[] parts = requestPath.split("/");

        // Vérifier si le chemin de la requête a le format attendu
        if (parts.length != 4 || !parts[1].equals("player") || !parts[2].equals("name")) {
            exchange.sendResponseHeaders(404, 0); // Envoyer une réponse 404 si le chemin n'est pas correct
            return;
        }

        try {
            // Extract the player pseudo from the URL part after /player/name/
            String joueurPseudo = parts[3];
            
            // Appeler getJoueurByName pour récupérer les détails du joueur
            Joueur playerDetails = DatabaseManager.getJoueurByName(joueurPseudo);
            
            // Vérifier si le joueur existe
            if (playerDetails != null) {
                // Convertir playerDetails en une représentation sous forme de chaîne de caractères
                String playerDetailsString = playerDetails.toString();
                
                // Envoyer les détails du joueur en tant que réponse
                byte[] responseBytes = playerDetailsString.getBytes();
                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } else {
                // Envoyer une réponse 404 si le joueur n'existe pas
                exchange.sendResponseHeaders(404, 0);
            }
        } catch (NumberFormatException e) {
            // Envoyer une réponse 400 si l'ID n'est pas valide
            exchange.sendResponseHeaders(400, 0);
        }
    }
}

class PlayerHandlerNew implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Vérifier si la méthode HTTP est PUT
        if (!exchange.getRequestMethod().equalsIgnoreCase("PUT")) {
            exchange.sendResponseHeaders(405, 0); // Envoyer une réponse 405 (Method Not Allowed) si la méthode n'est pas PUT
            return;
        }

        try {
            // Lire le corps de la requête pour obtenir les données du joueur
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }

            // Convertir le JSON en objet Joueur
            ObjectMapper objectMapper = new ObjectMapper();
            Joueur newPlayer = objectMapper.readValue(requestBody.toString(), Joueur.class);

            // Appeler la fonction pour créer le joueur en base de données
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.createPlayerFromRequestData(newPlayer);

            // Envoyer une réponse 201 (Created)
            exchange.sendResponseHeaders(201, 0);
        } catch (Exception e) {
            // En cas d'erreur, envoyer une réponse 500 (Internal Server Error)
            exchange.sendResponseHeaders(500, 0);
        }
    }
}
    class PlayerHandlerAllPseudo implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Extraction du pseudo à partir du chemin de la requête
        	System.out.println("Allpseudo");

        	String requestPath = exchange.getRequestURI().getPath();
            String[] parts = requestPath.split("/");

            if (parts.length < 3 || !parts[1].equals("player") || !parts[2].equals("pseudo")) {
                exchange.sendResponseHeaders(404, 0); // Envoyer une réponse 404 si le chemin n'est pas correct
                System.out.println("Allpseudoloupe");
                return;
            }


            try {
                // Appeler getAllPseudo pour récupérer la liste des pseudonymes
                List<String> playerPseudos = DatabaseManager.getAllPseudo();

                // Convertir la liste de pseudonymes en JSON
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayNode pseudoArray = objectMapper.createArrayNode();
                for (String pseudo : playerPseudos) {
                    pseudoArray.add(pseudo);
                }
                String jsonResponse = objectMapper.writeValueAsString(pseudoArray);

                // Envoyer les pseudonymes en tant que réponse JSON
                byte[] responseBytes = jsonResponse.getBytes();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } catch (NumberFormatException e) {
                // Envoyer une réponse 400 si l'ID n'est pas valide
                exchange.sendResponseHeaders(400, 0);
            }
        }
        }
        
        class PlayerHandlerAuth implements HttpHandler {
        	@Override
        	public void handle(HttpExchange exchange) throws IOException {
        	    if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
        	        Utils.setCorsHeaders(exchange);
        	        exchange.sendResponseHeaders(200, -1);
        	        return;
        	    }
        	    Utils.setCorsHeaders(exchange);
        	    System.out.println("Authenticating player...");


                // Récupérer les données du corps de la requête
                InputStream requestBody = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                StringBuilder requestBodyBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBodyBuilder.append(line);
                }
                String requestBodyString = requestBodyBuilder.toString();

                // Analyser les données JSON du corps de la requête
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode requestBodyJson = objectMapper.readTree(requestBodyString);
                String pseudo = requestBodyJson.get("pseudo").asText();
                String motDePasse = requestBodyJson.get("motDePasse").asText();

                // Vérifier l'authenticité du joueur dans la base de données
                Joueur isAuthenticated = DatabaseManager.verifieMotDePasse(pseudo, motDePasse);

                if (isAuthenticated != null) {
                	ObjectMapper objectMapper1 = new ObjectMapper();
                	String jsonResponse = objectMapper1.writeValueAsString(isAuthenticated);

                	// Envoyer la réponse
                	exchange.getResponseHeaders().set("Content-Type", "application/json");
                	byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
                	exchange.sendResponseHeaders(200, responseBytes.length);
                	try (OutputStream os = exchange.getResponseBody()) {
                	    os.write(responseBytes);
                	}
                } else {
                    // Renvoyer une réponse d'erreur si l'authentification échoue
                    String jsonResponse = "{\"message\": \"Authentication failed. Invalid pseudo or motDePasse.\"}";

                    // Envoyer la réponse
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(401, jsonResponse.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(jsonResponse.getBytes());
                    }
                
            }
        }
        
            
            static class Utils {
                public static void setCorsHeaders(HttpExchange exchange) {
                    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                    exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
                }
            }

        
    }
    
    
    


	



