package dao;

import model.Attendance;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public boolean startShift(int employeeID, int shiftID) {
        if (employeeID <= 0) return false; // tránh lỗi FK
        String sql = "INSERT INTO attendance(employeeID, shiftID, inTime, status) VALUES (?, ?, NOW(), 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeID);
            ps.setInt(2, shiftID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean stopShift(int employeeID) {
        String sql = """
                UPDATE attendance
                SET outTime = NOW(),
                    workHours = TIMESTAMPDIFF(MINUTE, inTime, NOW()) / 60,
                    overtimeMinutes = GREATEST(TIMESTAMPDIFF(MINUTE, inTime, NOW()) - 480, 0)
                WHERE employeeID=? AND outTime IS NULL
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeID);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public List<Attendance> getAllHistory() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Attendance at = new Attendance();
                at.setAttendanceID(rs.getInt("attendanceID"));
                at.setEmployeeID(rs.getInt("employeeID"));
                at.setShiftID(rs.getInt("shiftID"));
                at.setInTime(rs.getTimestamp("inTime"));
                at.setOutTime(rs.getTimestamp("outTime"));
                at.setWorkHours(rs.getDouble("workHours"));
                list.add(at);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Attendance> getHistory(int employeeID) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE employeeID=? ORDER BY attendanceID DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance at = new Attendance();
                at.setAttendanceID(rs.getInt("attendanceID"));
                at.setEmployeeID(rs.getInt("employeeID"));
                at.setShiftID(rs.getInt("shiftID"));
                at.setInTime(rs.getTimestamp("inTime"));
                at.setOutTime(rs.getTimestamp("outTime"));
                at.setWorkHours(rs.getDouble("workHours"));
                at.setOvertimeMinutes(rs.getInt("overtimeMinutes"));
                at.setStatus(rs.getInt("status"));
                at.setApprovedBy((Integer) rs.getObject("approvedBy"));

                list.add(at);
            }

        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
