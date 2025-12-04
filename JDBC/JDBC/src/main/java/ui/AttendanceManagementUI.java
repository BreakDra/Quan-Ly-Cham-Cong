package ui;

import dao.AttendanceDAO;
import model.Attendance;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AttendanceManagementUI extends JFrame {
    private AttendanceDAO dao = new AttendanceDAO();
    private JTable table;
    private DefaultTableModel model;
    private JButton btnRefresh;


    public AttendanceManagementUI() {
        setTitle("Attendance Management - Admin View");
        setSize(900, 500);
        setLocationRelativeTo(null);

        // Buttons
        JPanel panelButtons = new JPanel();
        btnRefresh = new JButton("Refresh");
        panelButtons.add(btnRefresh);
        add(panelButtons, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{
                "AttendanceID", "EmployeeID", "ShiftID", "In Time", "Out Time", "Work Hours"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions
        btnRefresh.addActionListener(e -> loadAllHistory());

        loadAllHistory();
        setVisible(true);
    }

    private void loadAllHistory() {
        model.setRowCount(0);
        List<Attendance> list = dao.getAllHistory(); // ✅ cần viết thêm hàm này trong DAO
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Attendance at : list) {
            model.addRow(new Object[]{
                    at.getAttendanceID(),
                    at.getEmployeeID(),
                    at.getShiftID(),
                    at.getInTime() != null ? sdf.format(at.getInTime()) : "",
                    at.getOutTime() != null ? sdf.format(at.getOutTime()) : "",
                    at.getWorkHours()
            });
        }
    }
}
