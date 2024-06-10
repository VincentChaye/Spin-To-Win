package WebGestion;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import spintowin.RandomNumber;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SalonWebSocketServer extends WebSocketServer {
    private List<Salon> salons;
    private int etatPartie;

    public SalonWebSocketServer(InetSocketAddress address) {
        super(address);
        this.salons = new ArrayList<>();
        this.etatPartie = 1;

        // Planifier le changement d'état de la partie
        Timer etatTimer = new Timer();
        etatTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                changerEtatPartie();
            }
        }, 0, 30000); // 30 secondes
    }

    private void changerEtatPartie() {
        if (etatPartie == 1) {
            etatPartie = 2;
            System.out.println("Etat de la partie changé à 2");
            generateAndSendNumbers();
            // Replanifier le changement d'état à 1 après 15 secondes
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    etatPartie = 1;
                    System.out.println("Etat de la partie changé à 1");
                    envoyerEtatPartie();
                }
            }, 8000); // 15 secondes
        }
    }

    private void generateAndSendNumbers() {
        for (Salon salon : salons) {
            int randomNum = RandomNumber.generateNumber();
            System.out.println("Nouveau numéro de salon généré pour le salon " + salon.getNumero() + " : " + randomNum);

            // Envoyer le numéro aléatoire, l'identifiant du salon et l'état de la partie aux clients connectés
            String salonData = "{\"NbAlea\": " + randomNum + ", \"salonId\": " + salon.getNumero() + ", \"etatPartie\": " + etatPartie + "}";
            salon.broadcast(salonData); // Méthode pour envoyer des données à tous les clients du salon
        }
    }

    private void envoyerEtatPartie() {
        String etatData = "{\"etatPartie\": " + etatPartie + "}";
        for (Salon salon : salons) {
            int salonId = salon.getNumero();
            String data = "{\"salonId\": " + salonId + ", \"etatPartie\": " + etatPartie + "}";
            salon.broadcast(data); // Envoyer l'état de la partie et le numéro du salon à tous les clients
        }
    }

    @Override
    public void onStart() {
        System.out.println("Serveur WebSocket démarré sur le port: " + getPort());
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Nouvelle connexion de: " + conn.getRemoteSocketAddress());

        // Trouver un salon avec de la place ou créer un nouveau salon
        Salon salonDisponible = trouverSalonDisponible();
        if (salonDisponible == null) {
            salonDisponible = creerNouveauSalon();
        }

        // Ajouter le joueur au salon
        boolean ajoutReussi = salonDisponible.ajouterJoueur(conn);
        if (ajoutReussi) {
            System.out.println("Joueur ajouté au salon numéro " + salonDisponible.getNumero());
            envoyerNumeroSalon(conn, salonDisponible.getNumero());
        } else {
            System.out.println("Le salon est plein.");
            // Gérer le cas où tous les salons sont pleins
        }
    }

    private Salon trouverSalonDisponible() {
        for (Salon salon : salons) {
            if (salon.getJoueurs().size() < 10) {
                return salon;
            }
        }
        return null;
    }

    private Salon creerNouveauSalon() {
        Salon nouveauSalon = new Salon();
        salons.add(nouveauSalon);
        return nouveauSalon;
    }

    private void envoyerNumeroSalon(WebSocket conn, int numeroSalon) {
        conn.send("Vous êtes dans le salon numéro " + numeroSalon);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Connexion fermée par " + (remote ? "le client" : "le serveur") + ": " + reason);

        // Retirer le joueur de tous les salons
        for (Salon salon : salons) {
            salon.retirerJoueur(conn);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message reçu: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress(8888);
        SalonWebSocketServer server = new SalonWebSocketServer(socketAddress); // Utiliser le port 8888
        server.start();
    }
}
