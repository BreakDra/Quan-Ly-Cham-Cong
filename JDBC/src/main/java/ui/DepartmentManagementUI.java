package ui;

import dao.DepartmentDAO;
import model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentManagementUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private DepartmentDAO dao = new DepartmentDAO();

    public DepartmentManagementUI() {
        setTitle("Department Management");
        setSize(600, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Note"}, 0);
        table = new JTable(model);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Department> list = dao.getAll();
        for (Department d : list) {
            model.addRow(new Object[]{d.getDepartmentID(), d.getDepartmentName(), d.getPhone(), d.getNote()});
        }
    }
}
