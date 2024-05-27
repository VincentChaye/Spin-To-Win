package WebGestion;

import spintowin.Joueur;
import java.util.Date; // Import de la classe Date

public class Main3 {
    public static void main(String[] args) {
        GestionSalons gestionSalons = new GestionSalons();
        
        // Ajouter des joueurs avec des informations spécifiques
        for (int i = 1; i <= 11; i++) { // Ajout de 11 joueurs
            Joueur joueur = new Joueur(i, "Pseudo" + i, "Nom" + i, "Prenom" + i, "email" + i + "@example.com", new Date(), 100); // Création d'un joueur avec des informations spécifiques
            gestionSalons.ajouterJoueur(joueur);
        }
        
        // Afficher les salons et les joueurs
        for (Salon salon : gestionSalons.getSalons()) {
            System.out.println("Salon ID: " + salon.getIdentifiantSalon());
            for (Joueur joueur : salon.getJoueurs()) {
                if (joueur != null) {
                    System.out.println(" - " + joueur.getNom());
                }
            }
        }

        // Retirer le joueur avec le pseudo "Nom1" du premier salon
        int identifiantSalon = 1; // Identifiant du premier salon
        Salon premierSalon = gestionSalons.getSalon(identifiantSalon);
        for (Joueur joueur : premierSalon.getJoueurs()) {
            if (joueur != null && joueur.getPseudo().equals("Nom1")) {
                gestionSalons.retirerJoueurDuSalon(identifiantSalon, joueur);
                break;
            }
        }

        // Afficher les salons et les joueurs après avoir retiré le joueur
        for (Salon salon : gestionSalons.getSalons()) {
            System.out.println("Salon ID: " + salon.getIdentifiantSalon());
            for (Joueur joueur : salon.getJoueurs()) {
                if (joueur != null) {
                    System.out.println(" - " + joueur.getNom());
                }
            }
        }
    }
}
