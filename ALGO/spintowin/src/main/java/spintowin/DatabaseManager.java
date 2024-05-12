package spintowin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                        rs.getString("email"),
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
                        rs.getString("email"),
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
    
    
    
    public static Joueur verifieMotDePasse(String joueurPseudo, String MotDePasse) {
        Joueur newPlayer = null;
        String motDePasseCrypte = crypterMotDePasse(MotDePasse);
        String sql = "SELECT * FROM joueur WHERE pseudo = ? AND mot_de_passe_hash = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
               pstmt.setString(1, joueurPseudo);
               pstmt.setString(2, motDePasseCrypte); 
               ResultSet rs = pstmt.executeQuery();
               if (rs.next()) {
                   newPlayer = new Joueur(
                           rs.getInt("id"),
                           rs.getString("pseudo"),
                           rs.getString("nom"),
                           rs.getString("prenom"),
                           rs.getString("email"),
                           rs.getDate("date_naissance"),
                           rs.getFloat("credit")
                          
                   );
               }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPlayer;
    }

    
    
    void createPlayerFromRequestData(Joueur newPlayer) {
        try {
            // Convertir la java.util.Date en java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(newPlayer.getDateNaissance().getTime());

            String motDePasseCrypte = crypterMotDePasse(newPlayer.getMot_de_passe_hash());
            if (motDePasseCrypte != null) {
                String sql = "INSERT INTO joueur (pseudo, nom, prenom,email, date_naissance, credit, mot_de_passe_hash) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, newPlayer.getPseudo());
                    pstmt.setString(2, newPlayer.getNom());
                    pstmt.setString(3, newPlayer.getPrenom());
                    pstmt.setString(4, newPlayer.getEmail());
                    pstmt.setDate(5, sqlDate);
                    pstmt.setFloat(6, newPlayer.getCredit());
                    pstmt.setString(7, motDePasseCrypte); // Utilisez le mot de passe crypté

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Le joueur a été inséré avec succès dans la base de données.");
                    } else {
                        System.out.println("Échec de l'insertion du joueur dans la base de données.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String crypterMotDePasse(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(motDePasse.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Gérer les exceptions liées à l'algorithme de hachage ici
            e.printStackTrace();
            return null;
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
        	                rs.getString("email"),
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

    
    
    public static List<String> getAllPseudo() {
        List<String> pseudos = new ArrayList<>();
        String sql = "SELECT pseudo FROM joueur";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pseudo = rs.getString("pseudo");
                pseudos.add(pseudo);
            }
            return pseudos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pseudos;
    }

    
    // Autres méthodes pour gérer les opérations de base de données ici...
}
