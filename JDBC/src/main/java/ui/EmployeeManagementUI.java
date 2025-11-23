package ui;

import dao.EmployeeDAO;
import model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private EmployeeDAO dao = new EmployeeDAO();

    public EmployeeManagementUI() {
        setTitle("Employee Management");
        setSize(900, 500);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{
                "ID", "Fullname", "Gender", "Birthdate", "Phone", "Email",
                "Address", "HireDate", "Salary", "DeptID", "PositionID"}, 0);
        table = new JTable(model);
        loadData();

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addEmployee());
        btnEdit.addActionListener(e -> editEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());

        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Employee> list = dao.getAll();
        for (Employee e : list) {
            model.addRow(new Object[]{
                    e.getEmployeeID(), e.getFullname(), e.getGender(), e.getBirthdate(),
                    e.getPhone(), e.getEmail(), e.getAddress(), e.getHireDate(),
                    e.getSalary(), e.getDepartmentID(), e.getPositionID()
            });
        }
    }

    private void addEmployee() {
        JOptionPane.showMessageDialog(this, "Add employee UI to implement.");
    }

    private void editEmployee() {
        JOptionPane.showMessageDialog(this, "Edit employee UI to implement.");
    }

    private void deleteEmployee() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) table.getValueAt(row, 0);
            dao.delete(id);
            loadData();
        }
    }
}
