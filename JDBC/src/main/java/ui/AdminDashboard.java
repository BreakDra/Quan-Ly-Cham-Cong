package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private Account admin;

    public AdminDashboard(Account admin) {
        this.admin = admin;
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton btnEmployee = new JButton("Manage Employees");
        JButton btnDepartment = new JButton("Manage Departments");
        JButton btnPosition = new JButton("Manage Positions");
        JButton btnShift = new JButton("Manage Shifts");
        JButton btnAttendance = new JButton("Start/Stop Attendance");

        panel.add(btnEmployee);
        panel.add(btnDepartment);
        panel.add(btnPosition);
        panel.add(btnShift);
        panel.add(btnAttendance);

        btnEmployee.addActionListener(e -> new EmployeeManagementUI());
        btnDepartment.addActionListener(e -> new DepartmentManagementUI());
        btnPosition.addActionListener(e -> new PositionManagementUI());
        btnShift.addActionListener(e -> new ShiftManagementUI());
        btnAttendance.addActionListener(e -> new AttendanceStartStopUI(admin.getEmployeeID()));

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
