package spintowin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "password";

    // Méthode pour établir une connexion à la base de données
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Méthode pour récupérer un joueur par son ID
    public static Joueur getJoueurById(int joueurId) {
        Joueur joueur = null;
        String sql = "SELECT * FROM joueur WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, joueurId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        rs.getFloat("credit"),
                        rs.getString("mot_de_passe_hash")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueur;
    }
    
    public static Joueur getJoueurByName(String joueurPseudo) {
        Joueur joueur = null;
        String sql = "SELECT * FROM joueur WHERE pseudo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, joueurPseudo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        rs.getFloat("credit"),
                        rs.getString("mot_de_passe_hash")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueur;
    }

    
    private void createPlayerFromRequestData(String requestData)  {
        Map<String, String> formData = new HashMap<>();
        String[] pairs = requestData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = URLDecoder.decode(keyValue[0], "UTF-8");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            formData.put(key, value);
        }

        // Maintenant, vous pouvez utiliser les données du formulaire pour créer un objet Joueur
        // Par exemple :
        String playerName = formData.get("name");
        String playerPseudo = formData.get("pseudo");
        // ...
    }
    
    // Méthode pour récupérer tous les joueurs
    public static List<Joueur> getAllJoueurs() {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Joueur joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        rs.getFloat("credit"),
                        rs.getString("mot_de_passe_hash")
                );
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueurs;
    }

    // Autres méthodes pour gérer les opérations de base de données ici...
}
