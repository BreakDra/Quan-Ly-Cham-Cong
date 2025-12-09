package ui;

import dao.AccountDAO;
import dao.EmployeeDAO;
import model.Account;
import model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditEmployeeUI extends JFrame {

    private JTextField txtFullname, txtUsername, txtPassword, txtPhone, txtEmail, txtAddress, txtSalary;
    private JComboBox<String> cbGender, cbRole;
    private JButton btnUpdate;

    private EmployeeDAO empDAO = new EmployeeDAO();
    private AccountDAO accDAO = new AccountDAO();
    private Employee emp;
    private Account acc;

    public EditEmployeeUI(int employeeID) {
        setTitle("Cập nhật nhân viên");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(11, 2, 5, 5));

        emp = empDAO.getById(employeeID);
        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
            dispose();
            return;
        }

        acc = accDAO.getById(emp.getAccountID());
        if (acc == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy account cho employeeID: " + employeeID);
            dispose();
            return;
        }

        txtFullname = new JTextField(emp.getFullname());
        txtUsername = new JTextField(acc.getUsername());
        txtPassword = new JTextField(acc.getPassword());
        txtPhone = new JTextField(emp.getPhone());
        txtEmail = new JTextField(emp.getEmail());
        txtAddress = new JTextField(emp.getAddress());
        txtSalary = new JTextField(String.valueOf(emp.getSalary()));

        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cbGender.setSelectedItem(emp.getGender());

        cbRole = new JComboBox<>(new String[]{"EMPLOYEE", "ADMIN"});
        cbRole.setSelectedItem(acc.getRole());

        btnUpdate = new JButton("Cập nhật");

        add(new JLabel("Họ tên:")); add(txtFullname);
        add(new JLabel("Giới tính:")); add(cbGender);
        add(new JLabel("SĐT:")); add(txtPhone);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("Địa chỉ:")); add(txtAddress);
        add(new JLabel("Lương:")); add(txtSalary);
        add(new JLabel("Username:")); add(txtUsername);
        add(new JLabel("Password:")); add(txtPassword);
        add(new JLabel("Role:")); add(cbRole);
        add(new JLabel("")); add(btnUpdate);

        btnUpdate.addActionListener(this::handleUpdate);

        setVisible(true);
    }

    private void handleUpdate(ActionEvent e) {
        // cập nhật account
        acc.setUsername(txtUsername.getText().trim());
        acc.setPassword(txtPassword.getText().trim());
        acc.setRole(cbRole.getSelectedItem().toString());
        accDAO.update(acc);

        // cập nhật employee
        emp.setFullname(txtFullname.getText().trim());
        emp.setGender(cbGender.getSelectedItem().toString());
        emp.setPhone(txtPhone.getText().trim());
        emp.setEmail(txtEmail.getText().trim());
        emp.setAddress(txtAddress.getText().trim());
        emp.setSalary(Double.parseDouble(txtSalary.getText().trim()));

        boolean success = empDAO.update(emp);

        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!");
        }
    }
}
