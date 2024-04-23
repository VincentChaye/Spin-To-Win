package spintowin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    
    void createPlayerFromRequestData(Joueur newPlayer) {
        try {
            // Convertir la java.util.Date en java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(newPlayer.getDateNaissance().getTime());
            
            String sql = "INSERT INTO joueur (pseudo, nom, prenom, date_naissance, credit, mot_de_passe_hash) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPlayer.getPseudo());
                pstmt.setString(2, newPlayer.getNom());
                pstmt.setString(3, newPlayer.getPrenom());
                pstmt.setDate(4, sqlDate);
                pstmt.setFloat(5, newPlayer.getCredit());
                pstmt.setString(6, newPlayer.getMot_de_passe_hash());

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le joueur a été inséré avec succès dans la base de données.");
                } else {
                    System.out.println("Échec de l'insertion du joueur dans la base de données.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        	    return joueurs;
        	} catch (SQLException e) {
        	    e.printStackTrace();
        	}
		return joueurs;

        
    }

    // Autres méthodes pour gérer les opérations de base de données ici...
}
