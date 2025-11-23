package dao;

import model.Account;
import util.DBConnection;

import java.sql.*;

public class AccountDAO {

    public Account login(String username, String password) {
        String sql = "SELECT * FROM account WHERE username=? AND password=? AND active=1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account a = new Account();
                a.setAccountID(rs.getInt("accountID"));
                a.setEmployeeID(rs.getInt("employeeID"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setRole(rs.getString("role"));
                a.setActive(rs.getBoolean("active"));
                return a;
            }

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Account a) {
        String sql = "INSERT INTO account(employeeID, username, password, role) VALUES (?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getEmployeeID());
            ps.setString(2, a.getUsername());
            ps.setString(3, a.getPassword());
            ps.setString(4, a.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
