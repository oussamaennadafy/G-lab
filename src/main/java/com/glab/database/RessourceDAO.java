package com.glab.database;

import com.glab.model.DocumentationTechnique;
import com.glab.model.EquipementAutomate;
import com.glab.model.EquipementDrone;
import com.glab.model.Ressource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Couche d'accès aux données pour les ressources du laboratoire.
 */
public class RessourceDAO {

    public void ajouter(Ressource ressource) {
        String sql = "INSERT INTO ressources(type, designation, emplacement, quantite, "
                + "attribut_specifique1, attribut_specifique2, attribut_specifique3) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            remplirPreparedStatement(pstmt, ressource);
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    ressource.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Impossible d'enregistrer la ressource en base : " + e.getMessage(), e);
        }
    }

    public void mettreAJour(Ressource ressource) {
        String sql = "UPDATE ressources SET type=?, designation=?, emplacement=?, quantite=?, "
                + "attribut_specifique1=?, attribut_specifique2=?, attribut_specifique3=? "
                + "WHERE id=?;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            remplirPreparedStatement(pstmt, ressource);
            pstmt.setInt(8, ressource.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de mettre à jour la ressource : " + e.getMessage(), e);
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM ressources WHERE id=?;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de supprimer la ressource : " + e.getMessage(), e);
        }
    }

    public List<Ressource> chargerToutesLesRessources() {
        List<Ressource> liste = new ArrayList<>();
        String sql = "SELECT * FROM ressources ORDER BY id;";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(construireRessource(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
        }

        return liste;
    }

    private void remplirPreparedStatement(PreparedStatement pstmt, Ressource ressource) throws SQLException {
        pstmt.setString(1, ressource.getType());
        pstmt.setString(2, ressource.getDesignation());
        pstmt.setString(3, ressource.getEmplacement());
        pstmt.setInt(4, ressource.getQuantite());

        if (ressource instanceof EquipementAutomate automate) {
            pstmt.setString(5, automate.getMarque());
            pstmt.setString(6, String.valueOf(automate.getNombreEntreesSorties()));
            pstmt.setString(7, automate.getProtocoleReseau());
        } else if (ressource instanceof EquipementDrone drone) {
            pstmt.setString(5, drone.getTypeCapteurEmbarque());
            pstmt.setString(6, String.valueOf(drone.getAutonomieVol()));
            pstmt.setString(7, String.valueOf(drone.getMasse()));
        } else if (ressource instanceof DocumentationTechnique doc) {
            pstmt.setString(5, doc.getAuteurConstructeur());
            pstmt.setString(6, String.valueOf(doc.getNombrePages()));
            pstmt.setString(7, doc.getLienManuelPdf());
        } else {
            pstmt.setString(5, null);
            pstmt.setString(6, null);
            pstmt.setString(7, null);
        }
    }

    private Ressource construireRessource(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        int id = rs.getInt("id");
        String designation = rs.getString("designation");
        String emplacement = rs.getString("emplacement");
        int quantite = rs.getInt("quantite");
        String spec1 = rs.getString("attribut_specifique1");
        String spec2 = rs.getString("attribut_specifique2");
        String spec3 = rs.getString("attribut_specifique3");

        return switch (type) {
            case "DRONE" -> new EquipementDrone(
                    id, designation, emplacement, quantite,
                    Double.parseDouble(spec3),
                    Integer.parseInt(spec2),
                    spec1
            );
            case "AUTOMATE" -> new EquipementAutomate(
                    id, designation, emplacement, quantite,
                    spec1,
                    Integer.parseInt(spec2),
                    spec3
            );
            case "DOCUMENTATION" -> new DocumentationTechnique(
                    id, designation, emplacement, quantite,
                    spec1,
                    Integer.parseInt(spec2),
                    spec3
            );
            default -> throw new SQLException("Type de ressource inconnu : " + type);
        };
    }
}
