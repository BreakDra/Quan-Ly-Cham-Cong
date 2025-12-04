package dao;

import model.UpdateRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateRequestDAO {
    public List<UpdateRequest> getPendingRequests(Connection conn) throws SQLException {
        List<UpdateRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM update_request WHERE status='PENDING'";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UpdateRequest r = new UpdateRequest();
                r.setRequestID(rs.getInt("requestID"));   // ✅ thêm dòng này
                r.setEmployeeID(rs.getInt("employeeID")); // ✅ thêm dòng này
                r.setFullname(rs.getString("fullname"));
                r.setEmail(rs.getString("email"));
                r.setPhone(rs.getString("phone"));
                r.setAddress(rs.getString("address"));
                r.setGender(rs.getString("gender"));
                r.setUsername(rs.getString("username"));
                r.setPassword(rs.getString("password"));
                r.setStatus(rs.getString("status"));      // ✅ thêm dòng này
                r.setRequestDate(rs.getTimestamp("requestDate")); // nếu có cột này

                list.add(r);
            }
        }
        return list;
    }

    public void approveRequest(int requestID, Connection conn) throws SQLException {
        String sqlGet = "SELECT * FROM update_request WHERE requestID=?";
        try (PreparedStatement ps = conn.prepareStatement(sqlGet)) {
            ps.setInt(1, requestID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int empID = rs.getInt("employeeID");
                    // cập nhật employee
                    String sqlUpdateEmp = "UPDATE employee SET fullname=?, email=?, phone=?, address=?, gender=? WHERE employeeID=?";
                    try (PreparedStatement ps2 = conn.prepareStatement(sqlUpdateEmp)) {
                        ps2.setString(1, rs.getString("fullname"));
                        ps2.setString(2, rs.getString("email"));
                        ps2.setString(3, rs.getString("phone"));
                        ps2.setString(4, rs.getString("address"));
                        ps2.setString(5, rs.getString("gender"));
                        ps2.setInt(6, empID);
                        ps2.executeUpdate();
                    }
                    // cập nhật account
                    String sqlUpdateAcc = "UPDATE account SET username=?, password=? WHERE accountID=(SELECT accountID FROM employee WHERE employeeID=?)";
                    try (PreparedStatement ps3 = conn.prepareStatement(sqlUpdateAcc)) {
                        ps3.setString(1, rs.getString("username"));
                        ps3.setString(2, rs.getString("password"));
                        ps3.setInt(3, empID);
                        ps3.executeUpdate();
                    }
                }
            }
        }
        // đổi trạng thái request
        String sql = "UPDATE update_request SET status='APPROVED' WHERE requestID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestID);
            ps.executeUpdate();
        }
    }

    public void rejectRequest(int requestID, Connection conn) throws SQLException {
        String sql = "UPDATE update_request SET status='REJECTED' WHERE requestID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestID);
            ps.executeUpdate();
        }
    }
}
