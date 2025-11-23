package dao;

import model.Department;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setDepartmentID(rs.getString("departmentID"));
                d.setDepartmentName(rs.getString("departmentName"));
                d.setPhone(rs.getString("phone"));
                d.setNote(rs.getString("note"));
                list.add(d);
            }

        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Department d) {
        String sql = "INSERT INTO department(departmentID, departmentName, phone, note) VALUES (?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getDepartmentID());
            ps.setString(2, d.getDepartmentName());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getNote());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
