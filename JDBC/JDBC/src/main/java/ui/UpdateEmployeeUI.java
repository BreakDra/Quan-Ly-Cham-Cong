package ui;

import dao.AccountDAO;
import dao.EmployeeDAO;
import model.Account;
import model.Employee;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateEmployeeUI extends JFrame {
    private int employeeID;
    private EmployeeDAO empDAO = new EmployeeDAO();
    private AccountDAO accDAO = new AccountDAO();
    private JTextField txtName, txtEmail, txtPhone, txtAddress, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbGender;
    private JButton btnSave;

    public UpdateEmployeeUI(int employeeID) {
        this.employeeID = employeeID;

        setTitle("Update Employee Info");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Employee emp = empDAO.getById(employeeID);
        Account acc = accDAO.getById(emp.getAccountID());


// kiểm tra account có tồn tại không
        if (acc == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy account cho employeeID: " + employeeID,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose(); // đóng form, không hiển thị phần update
            return;
        }


        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Fullname:"));
        txtName = new JTextField(emp.getFullname());
        panel.add(txtName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField(emp.getEmail());
        panel.add(txtEmail);

        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField(emp.getPhone());
        panel.add(txtPhone);

        panel.add(new JLabel("Address:"));
        txtAddress = new JTextField(emp.getAddress());
        panel.add(txtAddress);

        panel.add(new JLabel("Gender:"));
        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cbGender.setSelectedItem(emp.getGender());
        panel.add(cbGender);

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField(acc.getUsername());
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(acc.getPassword());
        panel.add(txtPassword);

        btnSave = new JButton("Save");
        panel.add(new JLabel());
        panel.add(btnSave);

        add(panel);

        btnSave.addActionListener(e -> saveInfo(emp, acc));
        setVisible(true);
    }

    private void saveInfo(Employee emp, Account acc) {
        String fullname = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String gender = (String) cbGender.getSelectedItem();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        try (Connection conn = util.DBConnection.getConnection()) {
            String sql = "INSERT INTO update_request(employeeID, fullname, email, phone, address, gender, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, employeeID);
                ps.setString(2, fullname);
                ps.setString(3, email);
                ps.setString(4, phone);
                ps.setString(5, address);
                ps.setString(6, gender);
                ps.setString(7, username);
                ps.setString(8, password);
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Yêu cầu cập nhật đã được gửi lên admin để duyệt!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi gửi yêu cầu!");
        }
    }
}
