package spintowin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class SimpleHttpServer1 {
    
    public static void main(String[] args) throws IOException {
        // Create an HTTP server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Define the request handler for the path "/resource1"
        server.createContext("/resource1", new Resource1Handler());
        
        // Define the request handler for the path "/resource2"
        server.createContext("/resource2", new Resource2Handler());
        
        // Start the server
        server.start();
        
        System.out.println("Server started on port 8000");
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


