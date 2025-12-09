package dao;

import model.Shift;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO {

    public List<Shift> getAll() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM shift";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Shift s = new Shift();
                s.setShiftID(rs.getInt("shiftID"));
                s.setShiftName(rs.getString("shiftName"));
                s.setTimeStart(rs.getTime("timeStart")); // sửa từ getString -> getTime
                s.setTimeEnd(rs.getTime("timeEnd"));     // sửa từ getString -> getTime
                s.setBreakMinutes(rs.getInt("breakMinutes"));
                s.setOvertimeRate(rs.getDouble("overtimeRate"));
                list.add(s);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Shift s) {
        String sql = "INSERT INTO shift(shiftName, timeStart, timeEnd, breakMinutes, overtimeRate) VALUES (?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getShiftName());
            ps.setTime(2, s.getTimeStart());
            ps.setTime(3, s.getTimeEnd());
            ps.setInt(4, s.getBreakMinutes());
            ps.setDouble(5, s.getOvertimeRate());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Shift s) {
        String sql = "UPDATE shift SET shiftName=?, timeStart=?, timeEnd=?, breakMinutes=?, overtimeRate=? WHERE shiftID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getShiftName());
            ps.setTime(2, s.getTimeStart());
            ps.setTime(3, s.getTimeEnd());
            ps.setInt(4, s.getBreakMinutes());
            ps.setDouble(5, s.getOvertimeRate());
            ps.setInt(6, s.getShiftID());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int shiftID) {
        String sql = "DELETE FROM Shift WHERE shiftID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, shiftID);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No shift found with ID: " + shiftID);
            }
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting shift: " + e.getMessage());
            return false;
        }
    }

}
