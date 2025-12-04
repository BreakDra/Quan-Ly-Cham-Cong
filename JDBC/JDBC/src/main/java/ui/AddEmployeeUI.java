package ui;

import dao.AccountDAO;
import dao.EmployeeDAO;
import model.Account;
import model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class AddEmployeeUI extends JFrame {

    private JTextField txtFullname, txtUsername, txtPassword, txtPhone, txtEmail, txtAddress, txtSalary;
    private JComboBox<String> cbGender, cbRole;
    private JButton btnAdd;

    public AddEmployeeUI() {
        setTitle("Thêm nhân viên mới");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(11, 2, 5, 5));

        txtFullname = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtAddress = new JTextField();
        txtSalary = new JTextField();

        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cbRole = new JComboBox<>(new String[]{"EMPLOYEE", "ADMIN"});

        btnAdd = new JButton("Thêm nhân viên");

        add(new JLabel("Họ tên:")); add(txtFullname);
        add(new JLabel("Giới tính:")); add(cbGender);
        add(new JLabel("SĐT:")); add(txtPhone);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("Địa chỉ:")); add(txtAddress);
        add(new JLabel("Lương:")); add(txtSalary);
        add(new JLabel("Username:")); add(txtUsername);
        add(new JLabel("Password:")); add(txtPassword);
        add(new JLabel("Role:")); add(cbRole);
        add(new JLabel("")); add(btnAdd);

        btnAdd.addActionListener(this::handleAdd);

        setVisible(true);
    }

    private void handleAdd(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = cbRole.getSelectedItem().toString();

        AccountDAO accDAO = new AccountDAO();
        if (accDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username đã tồn tại!");
            return;
        }

        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);
        acc.setRole(role);
        acc.setActive(true);

        int accountID = accDAO.createAccount(acc);
        if (accountID <= 0) {
            JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!");
            return;
        }

        Employee emp = new Employee();
        emp.setAccountID(accountID);
        emp.setFullname(txtFullname.getText().trim());
        emp.setGender(cbGender.getSelectedItem().toString());
        emp.setPhone(txtPhone.getText().trim());
        emp.setEmail(txtEmail.getText().trim());
        emp.setAddress(txtAddress.getText().trim());
        emp.setSalary(Double.parseDouble(txtSalary.getText().trim()));
        emp.setHireDate(new java.sql.Date(new Date().getTime()));
        emp.setDepartmentID("D001"); // giả định
        emp.setPositionID("P001");   // giả định
        emp.setImagePath("facebook.com"); // giả định

        EmployeeDAO empDAO = new EmployeeDAO();
        boolean success = empDAO.insert(emp);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
        }
    }
}
