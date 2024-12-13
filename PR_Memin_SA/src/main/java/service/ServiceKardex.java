package service;

import db.AccessDB;
import model.Kardex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceKardex {

    public List<Kardex> getAllKardex() {
        List<Kardex> kardexList = new ArrayList<>();
        String SQL_ALL_KARDEX = "SELECT k.id, g.name AS product_name, k.registration_date, k.month, k.motion, k.amount, k.unit_cost, k.total_cost, k.state " +
                "FROM kardex k " +
                "JOIN garments g ON k.products_id = g.id " +
                "WHERE k.state = 'A'";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL_KARDEX)) {

            while (rs.next()) {
                Kardex kardex = new Kardex();
                kardex.setId(rs.getInt("id"));
                kardex.setRegistration_date(rs.getDate("registration_date"));
                kardex.setMonth(rs.getString("month"));
                kardex.setMotion(rs.getString("motion"));
                kardex.setAmount(rs.getInt("amount"));
                kardex.setUnit_cost(rs.getBigDecimal("unit_cost"));
                kardex.setTotal_cost(rs.getBigDecimal("total_cost"));
                kardex.setState(rs.getString("state"));
                kardex.setProductName(rs.getString("product_name"));
                kardexList.add(kardex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kardexList;
    }

    public List<Kardex> getAllKardexSortedByProductName() {
        List<Kardex> kardexList = new ArrayList<>();
        String SQL_ALL_KARDEX_SORTED = "SELECT k.id, g.name AS product_name, k.registration_date, k.month, k.motion, k.amount, k.unit_cost, k.total_cost, k.state " +
                "FROM kardex k " +
                "JOIN garments g ON k.products_id = g.id " +
                "WHERE k.state = 'A' ORDER BY g.name ASC"; // Order by product name

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL_KARDEX_SORTED)) {

            while (rs.next()) {
                Kardex kardex = new Kardex();
                kardex.setId(rs.getInt("id"));
                kardex.setRegistration_date(rs.getDate("registration_date"));
                kardex.setMonth(rs.getString("month"));
                kardex.setMotion(rs.getString("motion"));
                kardex.setAmount(rs.getInt("amount"));
                kardex.setUnit_cost(rs.getBigDecimal("unit_cost"));
                kardex.setTotal_cost(rs.getBigDecimal("total_cost"));
                kardex.setState(rs.getString("state"));
                kardex.setProductName(rs.getString("product_name"));
                kardexList.add(kardex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kardexList;
    }

    public List<Kardex> getKardexByOptionalParams(Date registrationDate, String brand, String motion) {
        List<Kardex> kardexList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT k.id, g.name AS product_name, k.registration_date, k.month, k.motion, k.amount, k.unit_cost, k.total_cost, k.state " +
                "FROM kardex k " +
                "JOIN garments g ON k.products_id = g.id WHERE k.state = 'A'");

        List<Object> parameters = new ArrayList<>();

        if (registrationDate != null) {
            sql.append(" AND k.registration_date = ?");
            parameters.add(registrationDate);
        }
        if (brand != null && !brand.isEmpty()) {
            sql.append(" AND g.brand = ?");
            parameters.add(brand);
        }
        if (motion != null && !motion.isEmpty()) {
            sql.append(" AND k.motion = ?");
            parameters.add(motion);
        }

        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Kardex kardex = new Kardex();
                kardex.setId(rs.getInt("id"));
                kardex.setRegistration_date(rs.getDate("registration_date"));
                kardex.setMonth(rs.getString("month"));
                kardex.setMotion(rs.getString("motion"));
                kardex.setAmount(rs.getInt("amount"));
                kardex.setUnit_cost(rs.getBigDecimal("unit_cost"));
                kardex.setTotal_cost(rs.getBigDecimal("total_cost"));
                kardex.setState(rs.getString("state"));
                kardex.setProductName(rs.getString("product_name"));
                kardexList.add(kardex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kardexList;
    }

    public List<Kardex> getAllKardexMaster() {
        List<Kardex> kardexList = new ArrayList<>();
        String SQL_ALL_KARDEXMASTER = "SELECT k.id, g.name AS product_name, k.registration_date, k.month, k.motion, k.amount, k.unit_cost, k.total_cost, k.state " +
                "FROM kardex k " +
                "JOIN garments g ON k.products_id = g.id";
        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL_KARDEXMASTER)) {

            while (rs.next()) {
                Kardex kardex = new Kardex();
                kardex.setId(rs.getInt("id"));
                kardex.setRegistration_date(rs.getDate("registration_date"));
                kardex.setMonth(rs.getString("month"));
                kardex.setMotion(rs.getString("motion"));
                kardex.setAmount(rs.getInt("amount"));
                kardex.setUnit_cost(rs.getBigDecimal("unit_cost"));
                kardex.setTotal_cost(rs.getBigDecimal("total_cost"));
                kardex.setState(rs.getString("state"));
                kardex.setProductName(rs.getString("product_name"));
                kardexList.add(kardex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kardexList;
    }

    public void updateKardex(Kardex kardex) {
        String SQL_UPDATE_KARDEX = "UPDATE kardex SET products_id = ?, registration_date = ?, month = ?, motion = ?, amount = ?, unit_cost = ?, total_cost = ? WHERE id = ?";

        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_KARDEX)) {

            pstmt.setInt(1, kardex.getProducts_id());
            pstmt.setDate(2, kardex.getRegistration_date());
            pstmt.setString(3, kardex.getMonth());
            pstmt.setString(4, kardex.getMotion());
            pstmt.setInt(5, kardex.getAmount());
            pstmt.setBigDecimal(6, kardex.getUnit_cost());
            pstmt.setBigDecimal(7, kardex.getTotal_cost());
            pstmt.setInt(8, kardex.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteKardex(int id) {
        String SQL_DELETE_KARDEX = "UPDATE kardex SET state = 'I' WHERE id = ?";

        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_KARDEX)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Kardex> getDeletedKardex() {
        List<Kardex> deletedKardexList = new ArrayList<>();
        String SQL_DELETED_KARDEX = "SELECT k.id, g.name AS product_name, k.registration_date, k.month, k.motion, k.amount, k.unit_cost, k.total_cost, k.state " +
                "FROM kardex k " +
                "JOIN garments g ON k.products_id = g.id " +
                "WHERE k.state = 'I'";

        try (Connection conn = AccessDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_DELETED_KARDEX)) {

            while (rs.next()) {
                Kardex kardex = new Kardex();
                kardex.setId(rs.getInt("id"));
                kardex.setRegistration_date(rs.getDate("registration_date"));
                kardex.setMonth(rs.getString("month"));
                kardex.setMotion(rs.getString("motion"));
                kardex.setAmount(rs.getInt("amount"));
                kardex.setUnit_cost(rs.getBigDecimal("unit_cost"));
                kardex.setTotal_cost(rs.getBigDecimal("total_cost"));
                kardex.setState(rs.getString("state"));
                kardex.setProductName(rs.getString("product_name"));
                deletedKardexList.add(kardex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deletedKardexList;
    }

    public void restoreKardex(int id) {
        String SQL_RESTORE_KARDEX = "UPDATE kardex SET state = 'A' WHERE id = ?";

        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_RESTORE_KARDEX)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createKardex(Kardex kardex) {
        String SQL_INSERT_KARDEX = "INSERT INTO kardex (products_id, registration_date, month, motion, amount, unit_cost, total_cost, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = AccessDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT_KARDEX)) {

            pstmt.setInt(1, kardex.getProducts_id());
            pstmt.setDate(2, kardex.getRegistration_date());
            pstmt.setString(3, kardex.getMonth());
            pstmt.setString(4, kardex.getMotion());
            pstmt.setInt(5, kardex.getAmount());
            pstmt.setBigDecimal(6, kardex.getUnit_cost());
            pstmt.setBigDecimal(7, kardex.getTotal_cost());
            pstmt.setString(8, kardex.getState());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
