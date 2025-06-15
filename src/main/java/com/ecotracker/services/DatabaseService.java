package com.ecotracker.services;

import com.ecotracker.models.Activitat;
import com.ecotracker.utils.Config;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static final String URL = Config.getDbUrl();
    private static final String USER = Config.getDbUser();
    private static final String PASSWORD = Config.getDbPassword();

    public DatabaseService() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS activitats (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nom VARCHAR(100) NOT NULL,
                data DATE NOT NULL,
                categoria VARCHAR(50) NOT NULL,
                descripcio TEXT,
                co2_estalviat DECIMAL(10,2) NOT NULL
            )
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveActivitat(Activitat activitat) {
        String sql = "INSERT INTO activitats (nom, data, categoria, descripcio, co2_estalviat) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, activitat.getNom());
            pstmt.setDate(2, Date.valueOf(activitat.getData()));
            pstmt.setString(3, activitat.getCategoria());
            pstmt.setString(4, activitat.getDescripcio());
            pstmt.setDouble(5, activitat.getCo2Estalviat());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    activitat.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Activitat> getAllActivitats() {
        List<Activitat> activitats = new ArrayList<>();
        String sql = "SELECT * FROM activitats ORDER BY data DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Activitat activitat = new Activitat();
                activitat.setId(rs.getInt("id"));
                activitat.setNom(rs.getString("nom"));
                activitat.setData(rs.getDate("data").toLocalDate());
                activitat.setCategoria(rs.getString("categoria"));
                activitat.setDescripcio(rs.getString("descripcio"));
                activitat.setCo2Estalviat(rs.getDouble("co2_estalviat"));
                activitats.add(activitat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activitats;
    }

    public void deleteActivitat(int id) {
        String sql = "DELETE FROM activitats WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTotalCo2Estalviat() {
        String sql = "SELECT SUM(co2_estalviat) as total FROM activitats";
        double total = 0.0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
} 