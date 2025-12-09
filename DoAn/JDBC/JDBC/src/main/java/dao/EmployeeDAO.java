package dao;

import model.Employee;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public int findEmployeeIdByAccountId(int accountID) {
        String sql = "SELECT employeeID FROM employee WHERE accountID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("employeeID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


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

    public Employee getById(int employeeID) {
        String sql = "SELECT * FROM employee WHERE employeeID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmployeeID(rs.getInt("employeeID"));
                    emp.setAccountID(rs.getInt("accountID"));   // ✅ thêm dòng này ở đây
                    emp.setFullname(rs.getString("fullname"));
                    emp.setGender(rs.getString("gender"));
                    emp.setBirthdate(rs.getDate("birthdate"));
                    emp.setPhone(rs.getString("phone"));
                    emp.setEmail(rs.getString("email"));
                    emp.setAddress(rs.getString("address"));
                    emp.setHireDate(rs.getDate("hireDate"));
                    emp.setSalary(rs.getDouble("salary"));
                    emp.setImagePath(rs.getString("imagePath"));
                    emp.setDepartmentID(rs.getString("departmentID"));
                    emp.setPositionID(rs.getString("positionID"));
                    return emp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Employee e) {
        String sql = """
            INSERT INTO employee(accountID, fullname, gender, birthdate, phone, email,
             address, hireDate, salary, imagePath, departmentID, positionID)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getAccountID()); // ✅ thêm accountID
            ps.setString(2, e.getFullname());
            ps.setString(3, e.getGender());
            ps.setDate(4, new java.sql.Date(e.getBirthdate().getTime()));
            ps.setString(5, e.getPhone());
            ps.setString(6, e.getEmail());
            ps.setString(7, e.getAddress());
            ps.setDate(8, new java.sql.Date(e.getHireDate().getTime()));
            ps.setDouble(9, e.getSalary());
            ps.setString(10, e.getImagePath());
            ps.setString(11, e.getDepartmentID());
            ps.setString(12, e.getPositionID());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public boolean update(Employee e) {
        String sql = """
            UPDATE employee SET accountID=?, fullname=?, gender=?, birthdate=?, phone=?, email=?,
            address=?, hireDate=?, salary=?, imagePath=?, departmentID=?, positionID=?
            WHERE employeeID=?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getAccountID()); // ✅ cập nhật accountID
            ps.setString(2, e.getFullname());
            ps.setString(3, e.getGender());
            ps.setDate(4, new java.sql.Date(e.getBirthdate().getTime()));
            ps.setString(5, e.getPhone());
            ps.setString(6, e.getEmail());
            ps.setString(7, e.getAddress());
            ps.setDate(8, new java.sql.Date(e.getHireDate().getTime()));
            ps.setDouble(9, e.getSalary());
            ps.setString(10, e.getImagePath());
            ps.setString(11, e.getDepartmentID());
            ps.setString(12, e.getPositionID());
            ps.setInt(13, e.getEmployeeID());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(int employeeID) {
        try (Connection conn = DBConnection.getConnection()) {
            // Xóa attendance trước
            String sqlAttendance = "DELETE FROM attendance WHERE employeeID = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(sqlAttendance)) {
                ps1.setInt(1, employeeID);
                ps1.executeUpdate();
            }

            // Xóa update_request trước khi xóa employee
            String sqlUpdateRequest = "DELETE FROM update_request WHERE employeeID = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(sqlUpdateRequest)) {
                ps2.setInt(1, employeeID);
                ps2.executeUpdate();
            }

            // Cuối cùng xóa employee
            String sqlEmployee = "DELETE FROM employee WHERE employeeID = ?";
            try (PreparedStatement ps3 = conn.prepareStatement(sqlEmployee)) {
                ps3.setInt(1, employeeID);
                return ps3.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean createEmployeeForNewAccount(Employee emp) {
        String sql = "INSERT INTO employee(fullname, email, phone, birthdate, gender, address, departmentID, positionID, accountID, hireDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getFullname());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setDate(4, emp.getBirthdate());
            ps.setString(5, emp.getGender());
            ps.setString(6, emp.getAddress());
            ps.setString(7, emp.getDepartmentID());
            ps.setString(8, emp.getPositionID());
            ps.setInt(9, emp.getAccountID());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
