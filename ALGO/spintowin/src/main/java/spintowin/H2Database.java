package spintowin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Database {
    public static void main(String[] args) {
       
        Connection conn = null;
        try {
            // Connexion à la base de données H2 (en mémoire)
            conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "password");

            // Création de la base de données
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE SCHEMA IF NOT EXISTS testdb");
            }
            
            // Création de la table joueur
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS joueur ("
                        + "id INT PRIMARY KEY,"
                        + "pseudo VARCHAR(255),"
                        + "date_naissance DATE,"
                        + "credit FLOAT"
                        + ")");
            }

            // Insertion de données de test dans la table joueur
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("INSERT INTO joueur (id, pseudo, date_naissance, credit) VALUES (1, 'Joueur1', '2000-01-01', 100.0)");
                stmt.executeUpdate("INSERT INTO joueur (id, pseudo, date_naissance, credit) VALUES (2, 'Joueur2', '1995-05-20', 150.0)");
            }

            // Exécution d'une requête de sélection
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM joueur");

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Pseudo: " + rs.getString("pseudo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
