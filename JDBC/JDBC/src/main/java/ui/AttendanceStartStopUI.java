package ui;

import dao.AttendanceDAO;
import model.Attendance;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import dao.ShiftDAO;
import model.Shift;
import java.text.SimpleDateFormat;
import java.util.List;
import java.time.LocalTime;

public class AttendanceStartStopUI extends JFrame {
    private int employeeID;
    private AttendanceDAO dao = new AttendanceDAO();
    private JButton btnStart, btnStop, btnRefresh;
    private JTable table;
    private DefaultTableModel model;
    private boolean hasStarted = false; // ✅ trạng thái ca làm

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
        model = new DefaultTableModel(new String[]{"AttendanceID", "EmployeeID", "ShiftID", "In Time", "Out Time", "Work Hours"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button actions
        btnStart.addActionListener(e -> autoStartShift());
        btnStop.addActionListener(e -> stopShift());
        btnRefresh.addActionListener(e -> loadHistory());

        // ban đầu chưa bắt đầu ca thì không cho kết thúc
        btnStop.setEnabled(false);

        loadHistory();
        setVisible(true);
    }

    private void autoStartShift() {
        if (hasStarted) {
            JOptionPane.showMessageDialog(this, "Bạn đã bắt đầu ca rồi, không thể chấm công lại!");
            return;
        }

        LocalTime now = LocalTime.now();
        int shiftID = -1;

        // Lấy danh sách ca làm từ DB
        List<Shift> shifts = new ShiftDAO().getAll();
        for (Shift s : shifts) {
            LocalTime start = s.getTimeStart().toLocalTime();
            LocalTime end = s.getTimeEnd().toLocalTime();

            if (end.isAfter(start)) {
                // ca bình thường trong cùng ngày
                if (!now.isBefore(start) && !now.isAfter(end)) {
                    shiftID = s.getShiftID();
                    break;
                }
            } else {
                // ca qua ngày (ví dụ 22:00 -> 06:00)
                if (!now.isBefore(start) || !now.isAfter(end)) {
                    shiftID = s.getShiftID();
                    break;
                }
            }
        }


        if (shiftID != -1) {
            boolean success = dao.startShift(employeeID, shiftID);
            if (success) {
                JOptionPane.showMessageDialog(this, "Start Shift thành công (ShiftID: " + shiftID + ")");
                hasStarted = true;
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Không thể bắt đầu ca.");
            }
            loadHistory();
        } else {
            JOptionPane.showMessageDialog(this, "Hiện tại không nằm trong khung giờ ca làm.");
        }
    }


    private void stopShift() {
        if (!hasStarted) {
            JOptionPane.showMessageDialog(this, "Bạn chưa bắt đầu ca, không thể kết thúc!");
            return;
        }

        boolean success = dao.stopShift(employeeID);
        if (success) {
            JOptionPane.showMessageDialog(this, "Shift stopped!");
            hasStarted = false;
            btnStart.setEnabled(true);   // ✅ cho phép bắt đầu ca mới
            btnStop.setEnabled(false);   // ✅ disable nút Stop
        } else {
            JOptionPane.showMessageDialog(this, "Failed to stop shift.");
        }
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

    public void viewHistory() {
        loadHistory();
    }
}
