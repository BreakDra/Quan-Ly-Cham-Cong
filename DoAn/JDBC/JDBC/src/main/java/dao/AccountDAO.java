package dao;

import model.Account;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account login(String username, String password) {
        String sql = "SELECT accountID, username, password, role, active " +
                "FROM account WHERE username=? AND password=? AND active=TRUE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account(
                            rs.getInt("accountID"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                    a.setActive(rs.getBoolean("active"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Lấy account theo ID
    public Account getById(int accountId) {
        String sql = "SELECT accountID, username, password, role, active FROM account WHERE accountID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account(
                            rs.getInt("accountID"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                    a.setActive(rs.getBoolean("active"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật account (username, password, role, active)
    public boolean update(Account acc) {
        String sql = "UPDATE account SET username=?, password=?, role=?, active=? WHERE accountID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.setString(3, acc.getRole());
            ps.setBoolean(4, acc.isActive());
            ps.setInt(5, acc.getAccountID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean insert(Account a) {
        String sql = "INSERT INTO account(employeeID, username, password, role, active) VALUES (?,?,?,?,FALSE)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getEmployeeID());
            ps.setString(2, a.getUsername());
            ps.setString(3, a.getPassword());
            ps.setString(4, a.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // --Hàm check trùng username với đăng ký --
    public boolean usernameExists(String username) {
        String sql = "SELECT * FROM account WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return true; // tránh trùng lặp
        }
    }

    public int createAccount(Account acc) {
        String sql = "INSERT INTO account(username, password, role, active) VALUES (?, ?, ?, FALSE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.setString(3, acc.getRole());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Account> getPendingAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT accountID, username, role, active FROM account WHERE active=FALSE";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountID(rs.getInt("accountID"));
                acc.setUsername(rs.getString("username"));
                acc.setRole(rs.getString("role"));
                acc.setActive(rs.getBoolean("active"));
                list.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean approve(int accountID) {
        String sql = "UPDATE account SET active=TRUE WHERE accountID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Admin hủy account
    public boolean reject(int accountID) {
        String sql = "DELETE FROM account WHERE accountID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
