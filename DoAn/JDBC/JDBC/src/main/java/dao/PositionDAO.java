package dao;

import model.Position;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO {

    public List<Position> getAll() {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT * FROM positions";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Position p = new Position();
                p.setPositionID(rs.getString("positionID"));
                p.setPositionName(rs.getString("positionName"));
                p.setNote(rs.getString("note"));
                list.add(p);
            }

        } catch (Exception e) { e.printStackTrace();}
        return list;
    }

    public boolean insert(Position p) {
        String sql = "INSERT INTO positions(positionID, positionName, note) VALUES (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getPositionID());
            ps.setString(2, p.getPositionName());
            ps.setString(3, p.getNote());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(Position p) {
        String sql = "UPDATE positions SET positionName=?, note=? WHERE positionID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getPositionName());
            ps.setString(2, p.getNote());
            ps.setString(3, p.getPositionID());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String positionID) {
        String sql = "DELETE FROM positions WHERE positionID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, positionID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
