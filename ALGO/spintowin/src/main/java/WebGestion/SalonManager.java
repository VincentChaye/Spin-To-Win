package WebGestion;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import java.util.HashMap;
import java.util.Map;

public class SalonManager {
    private Map<Integer, Salon> salons;
    private int dernierIdSalon = 0;

    public SalonManager() {
        this.salons = new HashMap<>();
    }

    public Salon rejoindreSalon(SocketIOClient client) {
        // Rechercher un salon disponible
        Salon salonDisponible = null;
        for (Salon salon : salons.values()) {
            if (salon.getNbJoueurEnLigne() < 10) {
                salonDisponible = salon;
                break;
            }
        }

        // Créer un nouveau salon si aucun salon n'est disponible
        if (salonDisponible == null) {
            dernierIdSalon++;
            salonDisponible = new Salon(dernierIdSalon);
            salons.put(dernierIdSalon, salonDisponible);
        }

        // Ajouter le joueur au salon
        salonDisponible.ajouterJoueur(client);
        return salonDisponible;
    }

    public void quitterSalon(SocketIOClient client) {
        // Trouver le salon auquel le joueur est associé
        for (Salon salon : salons.values()) {
            if (salon.estDansSalon(client)) {
                salon.retirerJoueur(client);
                return;
            }
        }
    }
}
