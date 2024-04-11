package spintowin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Database {
    public static void main(String[] args) {
        try {
            // Connexion à la base de données H2 (en mémoire)
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
            
            // Création d'une table de test
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(255))");
            }
            
            // Insertion de données de test
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("INSERT INTO test_table (id, name) VALUES (1, 'Test')");
            }
            
            // Exécution d'une requête de sélection
            try (Statement stmt = conn.createStatement()) {
                var rs = stmt.executeQuery("SELECT * FROM test_table");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }
            }
            
            // Fermeture de la connexion
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
