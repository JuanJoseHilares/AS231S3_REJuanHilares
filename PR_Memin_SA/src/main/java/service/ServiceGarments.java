package service;

import db.AccessDB;
import model.Garments;

import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceGarments {


    // LISTAR los datos
    public List<Garments> getAllGarments() {
        List<Garments> garments = new ArrayList<>();
        String SQL_ALL_GARMENTS = "SELECT id, code, name, brand, category, price, state FROM garments WHERE state = 'A'";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL_GARMENTS)) {

            while (rs.next()) {
                Garments garment = new Garments();
                garment.setId(rs.getInt("id"));
                garment.setCode(rs.getString("code"));
                garment.setName(rs.getString("name"));
                garment.setBrand(rs.getString("brand"));
                garment.setCategory(rs.getString("category"));
                garment.setPrice(rs.getBigDecimal("price"));
                garment.setState(rs.getString("state"));
                garments.add(garment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return garments;
    }

    public List<Garments> getGarmentsOption() {
        List<Garments> garmentList = new ArrayList<>();
        String SQL_ACTIVE_GARMENTS = "SELECT id, name, price FROM garments WHERE state = 'A'";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ACTIVE_GARMENTS)) {

            while (rs.next()) {
                Garments garment = new Garments();
                garment.setId(rs.getInt("id"));
                garment.setName(rs.getString("name"));
                // Redondear el precio a dos decimales
                garment.setPrice(rs.getBigDecimal("price").setScale(2, RoundingMode.HALF_UP));
                garmentList.add(garment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return garmentList;
    }

    // Método para obtener prendas ordenadas por nombre
    public List<Garments> getAllGarmentsSortedByName() {
        List<Garments> garments = new ArrayList<>();
        String SQL_ALL_GARMENTS_SORTED = "SELECT id, code, name, brand, category, price, state FROM garments WHERE state = 'A' ORDER BY name ASC";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL_GARMENTS_SORTED)) {

            while (rs.next()) {
                Garments garment = new Garments();
                garment.setId(rs.getInt("id"));
                garment.setCode(rs.getString("code"));
                garment.setName(rs.getString("name"));
                garment.setBrand(rs.getString("brand"));
                garment.setCategory(rs.getString("category"));
                garment.setPrice(rs.getBigDecimal("price"));
                garment.setState(rs.getString("state"));
                garments.add(garment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return garments;
    }

    public List<Garments> searchGarments(String code, String name, String brand) {
        List<Garments> garments = new ArrayList<>();
        StringBuilder SQL_SEARCH_GARMENTS = new StringBuilder("SELECT id, code, name, brand, category, price, state FROM garments WHERE state = 'A'");

        List<String> conditions = new ArrayList<>();
        if (code != null && !code.isEmpty()) {
            conditions.add("code LIKE ?");
        }
        if (name != null && !name.isEmpty()) {
            conditions.add("name LIKE ?");
        }
        if (brand != null && !brand.isEmpty()) {
            conditions.add("brand LIKE ?");
        }

        if (!conditions.isEmpty()) {
            SQL_SEARCH_GARMENTS.append(" AND ").append(String.join(" AND ", conditions));
        }

        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SEARCH_GARMENTS.toString())) {

            int index = 1;
            if (code != null && !code.isEmpty()) {
                pstmt.setString(index++, "%" + code + "%");
            }
            if (name != null && !name.isEmpty()) {
                pstmt.setString(index++, "%" + name + "%");
            }
            if (brand != null && !brand.isEmpty()) {
                pstmt.setString(index++, "%" + brand + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Garments garment = new Garments();
                garment.setId(rs.getInt("id"));
                garment.setCode(rs.getString("code"));
                garment.setName(rs.getString("name"));
                garment.setBrand(rs.getString("brand"));
                garment.setCategory(rs.getString("category"));
                garment.setPrice(rs.getBigDecimal("price"));
                garment.setState(rs.getString("state"));
                garments.add(garment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return garments;
    }

    // LISTAR los datos
    public List<Garments> getAllGarmentsMaster() {
        List<Garments> garments = new ArrayList<>();
        String SQL_ALL_GARMENTS = "SELECT id, code, name, brand, category, price, state FROM garments";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL_GARMENTS)) {

            while (rs.next()) {
                Garments garment = new Garments();
                garment.setId(rs.getInt("id"));
                garment.setCode(rs.getString("code"));
                garment.setName(rs.getString("name"));
                garment.setBrand(rs.getString("brand"));
                garment.setCategory(rs.getString("category"));
                garment.setPrice(rs.getBigDecimal("price"));
                garment.setState(rs.getString("state"));
                garments.add(garment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return garments;
    }

    // Método para restaurar un dato
    public void restoreGarment(int id) {
        String SQL_RESTORE_GARMENT = "UPDATE garments SET state = 'A' WHERE id = ?";
        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_RESTORE_GARMENT)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Garments> getInactiveGarments() {
        List<Garments> garments = new ArrayList<>();
        String SQL_INACTIVE_GARMENTS = "SELECT id, code, name, brand, category, price, state FROM garments WHERE state = 'I'";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_INACTIVE_GARMENTS)) {

            while (rs.next()) {
                Garments garment = new Garments();
                garment.setId(rs.getInt("id"));
                garment.setCode(rs.getString("code"));
                garment.setName(rs.getString("name"));
                garment.setBrand(rs.getString("brand"));
                garment.setCategory(rs.getString("category"));
                garment.setPrice(rs.getBigDecimal("price"));
                garment.setState(rs.getString("state"));
                garments.add(garment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return garments;
    }

    // Borrado logico
    public void DeleteGarment(int id) {
        String SQL_ELIMINAR_GARMENT = "UPDATE garments SET state = 'I' WHERE id = ?";
        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_ELIMINAR_GARMENT)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Método para registrar un nuevo garment
    public void createGarment(Garments garment) {
        String SQL_CREATE_GARMENT = "INSERT INTO garments (code, name, brand, category, price, state) VALUES (?, ?, ?, ?, ?, 'A')";
        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE_GARMENT)) {
            pstmt.setString(1, garment.getCode());
            pstmt.setString(2, garment.getName());
            pstmt.setString(3, garment.getBrand());
            pstmt.setString(4, garment.getCategory());
            pstmt.setBigDecimal(5, garment.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar un garment
    public void updateGarment(Garments garment) {
        String SQL_UPDATE_GARMENT = "UPDATE garments SET code = ?, name = ?, brand = ?, category = ?, price = ? WHERE id = ?";
        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_GARMENT)) {
            pstmt.setString(1, garment.getCode());
            pstmt.setString(2, garment.getName());
            pstmt.setString(3, garment.getBrand());
            pstmt.setString(4, garment.getCategory());
            pstmt.setBigDecimal(5, garment.getPrice());
            pstmt.setInt(6, garment.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
