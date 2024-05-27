package WebGestion;

import java.util.ArrayList;
import java.util.List;
import spintowin.Joueur;

public class Salon {
    private int identifiantSalon;
    private Joueur[] joueurs;
    private int nbJoueurEnLigne;
    private List<String> chatMessages; // List to store chat messages

    public Salon(int identifiantSalon) {
        this.identifiantSalon = identifiantSalon;
        this.joueurs = new Joueur[10];
        this.nbJoueurEnLigne = 0;
        this.chatMessages = new ArrayList<>();
    }

    public int getIdentifiantSalon() {
        return identifiantSalon;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public int getNbJoueurEnLigne() {
        return nbJoueurEnLigne;
    }

    public void ajouterJoueur(Joueur joueur) {
        if (nbJoueurEnLigne < joueurs.length) {
            joueurs[nbJoueurEnLigne++] = joueur;
        } else {
            System.out.println("Le salon est plein.");
        }
    }

    public void retirerJoueur(Joueur joueur) {
        for (int i = 0; i < nbJoueurEnLigne; i++) {
            if (joueurs[i].equals(joueur)) {
                for (int j = i; j < nbJoueurEnLigne - 1; j++) {
                    joueurs[j] = joueurs[j + 1];
                }
                joueurs[nbJoueurEnLigne - 1] = null;
                nbJoueurEnLigne--;
                return;
            }
        }
        System.out.println("Le joueur n'est pas présent dans le salon.");
    }

    // Method to add chat message
    public void ajouterMessage(String message) {
        chatMessages.add(message);
    }

    // Method to get chat messages
    public List<String> getChatMessages() {
        return chatMessages;
    }
}
