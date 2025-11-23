package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;

public class EmployeeDashboard extends JFrame {
    private Account empAccount;

    public EmployeeDashboard(Account acc) {
        this.empAccount = acc;
        setTitle("Employee Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton btnAttendance = new JButton("Start/Stop Attendance");
        JButton btnHistory = new JButton("View Attendance History");

        panel.add(btnAttendance);
        panel.add(btnHistory);

        btnAttendance.addActionListener(e -> new AttendanceStartStopUI(empAccount.getEmployeeID()));
        btnHistory.addActionListener(e -> new AttendanceStartStopUI(empAccount.getEmployeeID()).viewHistory());

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
