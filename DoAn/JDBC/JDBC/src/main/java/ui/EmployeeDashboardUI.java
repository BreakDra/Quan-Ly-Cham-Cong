package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;

public class EmployeeDashboardUI extends JFrame {
    private Account empAccount;
    private int employeeID;

    public EmployeeDashboardUI(Account acc, int employeeID) {
        this.empAccount = acc;
        this.employeeID = employeeID;

        System.out.println("EmployeeDashboard opened for account: " + empAccount.getUsername() + ", employeeID=" + employeeID);

        setTitle("Employee Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề
        JLabel lblTitle = new JLabel("Employee Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(33, 33, 33));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton btnAttendance = createStyledButton("Attendance");
        JButton btnHistory = createStyledButton("History");
        JButton btnUpdate = createStyledButton("Update Info");
        JButton btnLogout = createStyledButton("Logout");

        buttonPanel.add(btnAttendance);
        buttonPanel.add(btnHistory);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnLogout);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JLabel lblFooter = new JLabel("Logged in as: " + empAccount.getUsername(), SwingConstants.RIGHT);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblFooter.setForeground(new Color(100, 100, 100));
        mainPanel.add(lblFooter, BorderLayout.SOUTH);

        // Action listeners
        btnAttendance.addActionListener(e -> new AttendanceStartStopUI(employeeID));
        btnHistory.addActionListener(e -> new AttendanceStartStopUI(employeeID).viewHistory());
        btnUpdate.addActionListener(e -> new UpdateEmployeeUI(employeeID));
        btnLogout.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null, "You have logged out.");
            // có thể quay về màn hình login
        });

        add(mainPanel);
        setVisible(true);
    }

    // Hàm tạo nút với style đồng bộ
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240)); // nền sáng
        button.setForeground(new Color(50, 50, 50));   // chữ tối
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true)); // viền bo tròn
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // đổi con trỏ khi hover
        return button;
    }
}
