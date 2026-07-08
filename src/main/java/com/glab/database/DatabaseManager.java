package com.glab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestionnaire de connexion et d'initialisation de la base SQLite locale.
 */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:glab_parc.db";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialiserBase() {
        String sql = "CREATE TABLE IF NOT EXISTS ressources ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "type TEXT NOT NULL,"
                + "designation TEXT NOT NULL,"
                + "emplacement TEXT NOT NULL,"
                + "quantite INTEGER NOT NULL,"
                + "attribut_specifique1 TEXT,"
                + "attribut_specifique2 TEXT,"
                + "attribut_specifique3 TEXT"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Base de données initialisée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur d'initialisation de la base : " + e.getMessage());
        }
    }
}
