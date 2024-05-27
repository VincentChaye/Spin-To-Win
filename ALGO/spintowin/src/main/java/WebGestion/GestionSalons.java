package WebGestion;


import java.util.ArrayList;
import java.util.List;

import spintowin.Joueur;

public class GestionSalons {
	private List<Salon> salons = new ArrayList<>();
	private int salonCounter = 1; // To assign unique IDs to salons

	public GestionSalons() {
		salons.add(new Salon(salonCounter++)); // Create the first salon
	}
	
	public Salon getSalon(int index) {
		if (index >= 0 && index < salons.size()) {
			return salons.get(index);
		} else {
			return null;
		}
	}

	public void ajouterJoueur(Joueur joueur) {
	    Salon dernierSalon = salons.get(salons.size() - 1);
	    if (dernierSalon.getNbJoueurEnLigne() < 10) {
	        dernierSalon.ajouterJoueur(joueur);
	    } else {
	        Salon nouveauSalon = new Salon(salonCounter++);
	        nouveauSalon.ajouterJoueur(joueur);
	        salons.add(nouveauSalon);
	    }
	}


	public void ajouterMessageDansSalon(int identifiantSalon, String message) {
	    for (Salon salon : salons) {
	        if (salon.getIdentifiantSalon() == identifiantSalon) {
	            salon.ajouterMessage(message);
	            break;
	        }
	    }
	}

	public void retirerJoueur(Salon idsalon,Joueur joueurs) {
	    for (int i = 0; i < salonCounter; i++) {
	        if (joueurs[i] != null && joueurs[i].equals(joueur)) {
	            // Shift elements to the left to fill the gap
	            for (int j = i; j < salonCounter - 1; j++) {
	                joueurs[j] = joueurs[j + 1];
	            }
	            joueurs[salonCounter - 1] = null; // Remove the last element
	            salonCounter--; // Decrement the player count
	            System.out.println("Le joueur " + joueur.getNom() + " a été retiré du salon.");
	            return;
	        }
	    }
	    System.out.println("Le joueur n'est pas présent dans ce salon.");
	}



	public List<Salon> getSalons() {
		return salons;
	}
}
