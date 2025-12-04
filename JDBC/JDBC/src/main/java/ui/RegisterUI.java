package ui;

import dao.AccountDAO;
import dao.EmployeeDAO;
import model.Account;
import model.Employee;

import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {
    private final JTextField txtName = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JTextField txtPhone = new JTextField();
    private final JTextField txtBirthdate = new JTextField();
    private final JComboBox<String> cbGender;
    private final JTextField txtAddress = new JTextField();
    private final JButton btnRegister = new JButton("Register");
    private final JButton btnBack = new JButton("Back");

    public RegisterUI() {
        setTitle("Register Employee Account");
        setSize(480, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout(20, 20));

        // Tiêu đề
        JLabel lblTitle = new JLabel("Register New Employee", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 33, 33));
        add(lblTitle, BorderLayout.NORTH);

        // Form nhập liệu
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
        panelForm.setBackground(new Color(245, 245, 245));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        addField(panelForm, "Name:", txtName);
        addField(panelForm, "Birthdate (dd-MM-yyyy):", txtBirthdate);
        addField(panelForm, "Email:", txtEmail);
        addField(panelForm, "Phone:", txtPhone);
        panelForm.add(new JLabel("Gender:"));
        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        panelForm.add(cbGender);
        addField(panelForm, "Address:", txtAddress);
        addField(panelForm, "Username:", txtUsername);
        addField(panelForm, "Password:", txtPassword);

        add(panelForm, BorderLayout.CENTER);

        // Panel nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.setBackground(new Color(245, 245, 245));
        styleButton(btnRegister, new Color(34, 139, 34));
        styleButton(btnBack, new Color(178, 34, 34));
        panelButtons.add(btnRegister);
        panelButtons.add(btnBack);
        add(panelButtons, BorderLayout.SOUTH);

        // Action
        btnRegister.addActionListener(e -> registerAccount());
        btnBack.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addField(JPanel panel, String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(lbl);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(field);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void registerAccount() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String phone = txtPhone.getText().trim();
        String birthdateStr = txtBirthdate.getText().trim();
        String gender = (String) cbGender.getSelectedItem();
        String address = txtAddress.getText().trim();

        java.sql.Date birthdate = null;
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            java.util.Date utilDate = sdf.parse(birthdateStr);
            birthdate = new java.sql.Date(utilDate.getTime());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Birthdate format must be dd-MM-yyyy");
            return;
        }

        AccountDAO accDAO = new AccountDAO();
        if (accDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already taken!");
            return;
        }

        // Tạo account với active = FALSE
        Account acc = new Account(0, username, password, "EMPLOYEE");
        acc.setActive(false);
        int accountId = accDAO.createAccount(acc);
        if (accountId <= 0) {
            JOptionPane.showMessageDialog(this, "Failed to create account!");
            return;
        }

        Employee emp = new Employee();
        emp.setFullname(name);
        emp.setEmail(email);
        emp.setPhone(phone);
        emp.setBirthdate(birthdate);
        emp.setGender(gender);
        emp.setAddress(address);
        emp.setDepartmentID("D001"); // tạm gán mặc định
        emp.setPositionID("P001");   // tạm gán mặc định
        emp.setAccountID(accountId);

        boolean success = new EmployeeDAO().createEmployeeForNewAccount(emp);
        JOptionPane.showMessageDialog(this,
                success ? "Registration successful! Please wait for Admin approval."
                        : "Failed to create employee!");
        if (success) dispose();
    }
}
