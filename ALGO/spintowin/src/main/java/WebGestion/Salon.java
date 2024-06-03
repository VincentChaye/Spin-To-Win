package WebGestion;

import java.util.ArrayList;
import java.util.List;
import com.corundumstudio.socketio.SocketIOClient;
import spintowin.Joueur;

public class Salon {
    private int identifiantSalon;
    private List<SocketIOClient> joueurs;
    private List<String> chatMessages;

    public Salon(int identifiantSalon) {
        this.identifiantSalon = identifiantSalon;
        this.joueurs = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }

    public int getIdentifiantSalon() {
        return identifiantSalon;
    }

    public List<SocketIOClient> getJoueurs() {
        return joueurs;
    }

    public void ajouterJoueur(SocketIOClient joueur) {
        joueurs.add(joueur);
        joueur.sendEvent("message", "Vous avez rejoint le salon " + identifiantSalon);
    }

    public void retirerJoueur(SocketIOClient joueur) {
        joueurs.remove(joueur);
        joueur.sendEvent("message", "Vous avez quitté le salon " + identifiantSalon);
    }

    public boolean estDansSalon(SocketIOClient joueur) {
        return joueurs.contains(joueur);
    }

    public void ajouterMessage(String message) {
        chatMessages.add(message);
        // Envoyer le message à tous les joueurs dans le salon
        for (SocketIOClient joueur : joueurs) {
            joueur.sendEvent("chatMessage", message);
        }
    }

    public List<String> getChatMessages() {
        return chatMessages;
    }
}
