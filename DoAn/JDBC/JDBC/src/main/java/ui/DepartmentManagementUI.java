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

    private JTextField txtID = new JTextField();
    private JTextField txtName = new JTextField();
    private JTextField txtPhone = new JTextField();
    private JTextField txtNote = new JTextField();

    public DepartmentManagementUI() {
        setTitle("Department Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Panel nhập liệu phía trên bảng: 4 label trên, 4 ô nhập dưới
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin phòng ban"));

        // Hàng 1: label
        inputPanel.add(new JLabel("ID"));
        inputPanel.add(new JLabel("Name"));
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(new JLabel("Note"));

        // Hàng 2: ô nhập
        inputPanel.add(txtID);
        inputPanel.add(txtName);
        inputPanel.add(txtPhone);
        inputPanel.add(txtNote);

        add(inputPanel, BorderLayout.NORTH);

        // ✅ Bảng hiển thị dữ liệu
        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Note"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ Nút chức năng phía dưới bảng
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện nút Add
        btnAdd.addActionListener(e -> {
            Department d = new Department();
            d.setDepartmentID(txtID.getText().trim());
            d.setDepartmentName(txtName.getText().trim());
            d.setPhone(txtPhone.getText().trim());
            d.setNote(txtNote.getText().trim());

            if (dao.insert(d)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        // Sự kiện nút Edit
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn một dòng để sửa!");
                return;
            }
            Department d = new Department();
            d.setDepartmentID(txtID.getText().trim());
            d.setDepartmentName(txtName.getText().trim());
            d.setPhone(txtPhone.getText().trim());
            d.setNote(txtNote.getText().trim());

            if (dao.update(d)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        // Sự kiện nút Delete
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn một dòng để xóa!");
                return;
            }
            String deptID = (String) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa phòng ban này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.delete(deptID);
                loadData();
                clearInput();
            }
        });

        // Khi chọn dòng trong bảng → đổ dữ liệu lên ô nhập
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtID.setText((String) model.getValueAt(row, 0));
                txtName.setText((String) model.getValueAt(row, 1));
                txtPhone.setText((String) model.getValueAt(row, 2));
                txtNote.setText((String) model.getValueAt(row, 3));
            }
        });

        loadData();
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Department> list = dao.getAll();
        for (Department d : list) {
            model.addRow(new Object[]{d.getDepartmentID(), d.getDepartmentName(), d.getPhone(), d.getNote()});
        }
    }

    private void clearInput() {
        txtID.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtNote.setText("");
    }
}
