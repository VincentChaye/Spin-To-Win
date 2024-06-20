package WebGestion;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Salon {
    private static int count = 0;
    private int numero;
    private List<WebSocket> joueurs;
    private static LinkedList<String> chatMessages = new LinkedList<>(); // Messages de chat partagés par tous les salons

    public Salon() {
        this.numero = ++count;
        this.joueurs = new ArrayList<>();
    }

    public boolean ajouterJoueur(WebSocket joueur) {
        if (joueurs.size() < 5) { // Changer 10 à 5
            joueurs.add(joueur);
            return true;
        }
        return false;
    }
    
    public int nombreJoueurDansSalon() {
    	return joueurs.size();
    }
    public void retirerJoueur(WebSocket joueur) {
        joueurs.remove(joueur);
    }

    public int getNumero() {
        return numero;
    }

    public List<WebSocket> getJoueurs() {
        return joueurs;
    }

    public static LinkedList<String> getChatMessages() {
        return chatMessages;
    }

    public static void addChatMessage(String message) {
        if (chatMessages.size() >= 50) {
            chatMessages.removeFirst();
        }
        chatMessages.add(message);
    }

    public void broadcast(String message) {
        for (WebSocket joueur : joueurs) {
            joueur.send(message);
        }
    }
}
