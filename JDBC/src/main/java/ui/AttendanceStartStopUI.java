package ui;

import dao.AttendanceDAO;
import model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AttendanceStartStopUI extends JFrame {
    private int employeeID;
    private AttendanceDAO dao = new AttendanceDAO();

    private JButton btnStart, btnStop, btnRefresh;
    private JTable table;
    private DefaultTableModel model;

    public AttendanceStartStopUI(int employeeID) {
        this.employeeID = employeeID;

        setTitle("Attendance Management - EmployeeID: " + employeeID);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Buttons
        JPanel panelButtons = new JPanel();
        btnStart = new JButton("Start Shift");
        btnStop = new JButton("Stop Shift");
        btnRefresh = new JButton("Refresh History");
        panelButtons.add(btnStart);
        panelButtons.add(btnStop);
        panelButtons.add(btnRefresh);

        add(panelButtons, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{
                "AttendanceID", "EmployeeID", "ShiftID", "In Time", "Out Time", "Work Hours"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button actions
        btnStart.addActionListener(e -> startShift());
        btnStop.addActionListener(e -> stopShift());
        btnRefresh.addActionListener(e -> loadHistory());

        loadHistory();
        setVisible(true);
    }

    private void startShift() {
        String shiftStr = JOptionPane.showInputDialog(this, "Enter ShiftID to start:");
        if (shiftStr != null && !shiftStr.isEmpty()) {
            int shiftID = Integer.parseInt(shiftStr);
            boolean success = dao.startShift(employeeID, shiftID);
            JOptionPane.showMessageDialog(this, success ? "Shift started!" : "Failed to start shift.");
            loadHistory();
        }
    }

    private void stopShift() {
        boolean success = dao.stopShift(employeeID);
        JOptionPane.showMessageDialog(this, success ? "Shift stopped!" : "Failed to stop shift.");
        loadHistory();
    }

    public void loadHistory() {
        model.setRowCount(0);
        List<Attendance> list = dao.getHistory(employeeID);
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

    // Optional method to view history from EmployeeDashboard
    public void viewHistory() {
        loadHistory();
    }
}
