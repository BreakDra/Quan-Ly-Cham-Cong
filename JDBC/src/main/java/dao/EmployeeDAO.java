package dao;

import model.Employee;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeID(rs.getInt("employeeID"));
                e.setFullname(rs.getString("fullname"));
                e.setGender(rs.getString("gender"));
                e.setBirthdate(rs.getDate("birthdate"));
                e.setPhone(rs.getString("phone"));
                e.setEmail(rs.getString("email"));
                e.setAddress(rs.getString("address"));
                e.setHireDate(rs.getDate("hireDate"));
                e.setSalary(rs.getDouble("salary"));
                e.setImagePath(rs.getString("imagePath"));
                e.setDepartmentID(rs.getString("departmentID"));
                e.setPositionID(rs.getString("positionID"));
                list.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Employee getById(int id) {
        String sql = "SELECT * FROM employee WHERE employeeID=?";
        Employee e = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e = new Employee();
                e.setEmployeeID(rs.getInt("employeeID"));
                e.setFullname(rs.getString("fullname"));
                e.setGender(rs.getString("gender"));
                e.setBirthdate(rs.getDate("birthdate"));
                e.setPhone(rs.getString("phone"));
                e.setEmail(rs.getString("email"));
                e.setAddress(rs.getString("address"));
                e.setHireDate(rs.getDate("hireDate"));
                e.setSalary(rs.getDouble("salary"));
                e.setImagePath(rs.getString("imagePath"));
                e.setDepartmentID(rs.getString("departmentID"));
                e.setPositionID(rs.getString("positionID"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public boolean insert(Employee e) {
        String sql = """
                INSERT INTO employee(fullname, gender, birthdate, phone, email,
                 address, hireDate, salary, imagePath, departmentID, positionID)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getFullname());
            ps.setString(2, e.getGender());
            ps.setDate(3, new Date(e.getBirthdate().getTime()));
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getAddress());
            ps.setDate(7, new Date(e.getHireDate().getTime()));
            ps.setDouble(8, e.getSalary());
            ps.setString(9, e.getImagePath());
            ps.setString(10, e.getDepartmentID());
            ps.setString(11, e.getPositionID());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(Employee e) {
        String sql = """
                UPDATE employee SET fullname=?, gender=?, birthdate=?, phone=?, email=?,
                address=?, hireDate=?, salary=?, imagePath=?, departmentID=?, positionID=?
                WHERE employeeID=?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getFullname());
            ps.setString(2, e.getGender());
            ps.setDate(3, new Date(e.getBirthdate().getTime()));
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getAddress());
            ps.setDate(7, new Date(e.getHireDate().getTime()));
            ps.setDouble(8, e.getSalary());
            ps.setString(9, e.getImagePath());
            ps.setString(10, e.getDepartmentID());
            ps.setString(11, e.getPositionID());
            ps.setInt(12, e.getEmployeeID());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM employee WHERE employeeID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
