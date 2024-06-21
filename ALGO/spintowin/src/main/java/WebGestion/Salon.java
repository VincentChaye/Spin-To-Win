
package WebGestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.java_websocket.WebSocket;

public class Salon {
    private static int count = 0;
    private int numero;
    private List<WebSocket> joueurs;
    private HashMap< WebSocket, String> chat;

    public Salon() {
        this.numero = ++count;
        this.joueurs = new ArrayList<>();
    }

    public boolean ajouterJoueur(WebSocket joueur) {
        if (joueurs.size() < 10) {
            joueurs.add(joueur);
            return true;
        }
        return false;
    }

    public void retirerJoueur(WebSocket joueur) {
        joueurs.remove(joueur);
    }

    public int getNumero() {
        return numero;
    }

    // Méthode pour accéder à la liste des joueurs
    public List<WebSocket> getJoueurs() {
        return joueurs;
    }

    // Méthode pour envoyer un message à tous les joueurs du salon
    public void broadcast(String message) {
        for (WebSocket joueur : joueurs) {
            joueur.send(message);
        }
    }
}