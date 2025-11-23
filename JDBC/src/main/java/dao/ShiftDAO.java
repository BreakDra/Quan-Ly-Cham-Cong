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
                s.setTimeStart(rs.getString("timeStart"));
                s.setTimeEnd(rs.getString("timeEnd"));
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
            ps.setString(2, s.getTimeStart());
            ps.setString(3, s.getTimeEnd());
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
            ps.setString(2, s.getTimeStart());
            ps.setString(3, s.getTimeEnd());
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
        String sql = "DELETE FROM shift WHERE shiftID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, shiftID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
