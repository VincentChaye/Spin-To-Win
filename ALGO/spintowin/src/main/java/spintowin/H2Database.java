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
                        + " id SERIAL PRIMARY KEY,"
                        + " pseudo VARCHAR(255) UNIQUE,"
                        + " nom VARCHAR(255),"
                        + " prenom VARCHAR(255),"
                        + " email VARCHAR(255),"
                        + " date_naissance DATE,"
                        + " credit FLOAT,"
                        + " mot_de_passe_hash VARCHAR(255)"
                        + ")");
            }

            // Insertion de données de test dans la table joueur
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("INSERT INTO joueur (pseudo, nom, prenom, date_naissance, credit, mot_de_passe_hash) VALUES "
                        + "('joueur1', 'Doe', 'John', '1990-01-01', 1000.0, 'hash1'), "
                        + "('joueur2', 'Smith', 'Jane', '1992-05-15', 1500.0, 'hash2'), "
                        + "('joueur3', 'Johnson', 'Michael', '1988-11-30', 800.0, 'hash3'), "
                        + "('joueur4', 'Williams', 'Emily', '1995-03-22', 1200.0, 'hash4'), "
                        + "('joueur5', 'Brown', 'Jessica', '1993-07-10', 900.0, 'hash5'), "
                        + "('joueur6', 'Jones', 'David', '1985-09-18', 2000.0, 'hash6'), "
                        + "('joueur7', 'Garcia', 'Maria', '1998-02-28', 700.0, 'hash7'), "
                        + "('joueur8', 'Martinez', 'Daniel', '1991-06-12', 1100.0, 'hash8'), "
                        + "('joueur9', 'Hernandez', 'Sophia', '1987-04-05', 1300.0, 'hash9'), "
                        + "('joueur10', 'Lopez', 'William', '1996-08-25', 1800.0, 'hash10')");
            } catch (SQLException e) {
                e.printStackTrace();
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
