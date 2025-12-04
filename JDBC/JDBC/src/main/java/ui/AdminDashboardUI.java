package ui;

import model.Account;
import javax.swing.*;
import java.awt.*;

public class AdminDashboardUI extends JFrame {
    private Account admin;

    public AdminDashboardUI(Account admin) {
        this.admin = admin;
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(33, 33, 33));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 20, 20)); // tăng lên 3x3 để đủ chỗ
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton btnEmployee = createStyledButton("Manage Employees");
        JButton btnDepartment = createStyledButton("Manage Departments");
        JButton btnPosition = createStyledButton("Manage Positions");
        JButton btnShift = createStyledButton("Manage Shifts");
        JButton btnAttendance = createStyledButton("Attendance History");
        JButton btnApproveAccounts = createStyledButton("Approve Accounts");
        JButton btnUpdateRequests = createStyledButton("Update Requests"); // ✅ nút mới
        JButton btnLogout = createStyledButton("Logout");

        buttonPanel.add(btnEmployee);
        buttonPanel.add(btnDepartment);
        buttonPanel.add(btnPosition);
        buttonPanel.add(btnShift);
        buttonPanel.add(btnAttendance);
        buttonPanel.add(btnApproveAccounts);
        buttonPanel.add(btnUpdateRequests); // ✅ thêm vào panel
        buttonPanel.add(btnLogout);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JLabel lblFooter = new JLabel("Logged in as: " + admin.getUsername(), SwingConstants.RIGHT);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblFooter.setForeground(new Color(100, 100, 100));
        mainPanel.add(lblFooter, BorderLayout.SOUTH);

        // Action listeners
        btnEmployee.addActionListener(e -> new EmployeeManagementUI());
        btnDepartment.addActionListener(e -> new DepartmentManagementUI());
        btnPosition.addActionListener(e -> new PositionManagementUI());
        btnShift.addActionListener(e -> new ShiftManagementUI());
        btnAttendance.addActionListener(e -> new AttendanceManagementUI());
        btnApproveAccounts.addActionListener(e -> new AccountApprovalUI());
        btnUpdateRequests.addActionListener(e -> new UpdateRequestsUI()); // ✅ mở giao diện duyệt yêu cầu
        btnLogout.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null, "You have logged out.");
            new LoginUI();
        });

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(new Color(50, 50, 50));
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
