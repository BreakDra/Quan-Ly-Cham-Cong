package ui;

import dao.EmployeeDAO;
import model.Employee;
import java.sql.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private EmployeeDAO dao = new EmployeeDAO();

    // Ô nhập liệu
    private JTextField txtID = new JTextField();
    private JTextField txtFullname = new JTextField();
    private JTextField txtGender = new JTextField();
    private JTextField txtBirthdate = new JTextField();
    private JTextField txtPhone = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtAddress = new JTextField();
    private JTextField txtHireDate = new JTextField();
    private JTextField txtSalary = new JTextField();
    private JTextField txtDeptID = new JTextField();
    private JTextField txtPositionID = new JTextField();

    public EmployeeManagementUI() {
        setTitle("Employee Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Panel nhập liệu phía trên bảng: label trên, ô nhập dưới
        JPanel inputPanel = new JPanel(new GridLayout(2, 11, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin nhân viên"));

        // Hàng 1: label
        inputPanel.add(new JLabel("ID"));
        inputPanel.add(new JLabel("Fullname"));
        inputPanel.add(new JLabel("Gender"));
        inputPanel.add(new JLabel("Birthdate"));
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(new JLabel("Email"));
        inputPanel.add(new JLabel("Address"));
        inputPanel.add(new JLabel("HireDate"));
        inputPanel.add(new JLabel("Salary"));
        inputPanel.add(new JLabel("DeptID"));
        inputPanel.add(new JLabel("PositionID"));

        // Hàng 2: ô nhập
        inputPanel.add(txtID);
        inputPanel.add(txtFullname);
        inputPanel.add(txtGender);
        inputPanel.add(txtBirthdate);
        inputPanel.add(txtPhone);
        inputPanel.add(txtEmail);
        inputPanel.add(txtAddress);
        inputPanel.add(txtHireDate);
        inputPanel.add(txtSalary);
        inputPanel.add(txtDeptID);
        inputPanel.add(txtPositionID);

        add(inputPanel, BorderLayout.NORTH);

        // ✅ Bảng hiển thị dữ liệu
        model = new DefaultTableModel(new String[]{
                "ID", "Fullname", "Gender", "Birthdate", "Phone", "Email",
                "Address", "HireDate", "Salary", "DeptID", "PositionID"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ✅ Nút chức năng phía dưới bảng
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);

        // Sự kiện nút Add
        btnAdd.addActionListener(e -> {
            Employee emp = new Employee();
            emp.setEmployeeID(Integer.parseInt(txtID.getText().trim()));
            emp.setFullname(txtFullname.getText().trim());
            emp.setGender(txtGender.getText().trim());
            emp.setBirthdate(Date.valueOf(txtBirthdate.getText().trim()));
            emp.setPhone(txtPhone.getText().trim());
            emp.setEmail(txtEmail.getText().trim());
            emp.setAddress(txtAddress.getText().trim());
            emp.setHireDate(Date.valueOf(txtHireDate.getText().trim()));
            emp.setSalary(Double.parseDouble(txtSalary.getText().trim()));
            emp.setDepartmentID(txtDeptID.getText().trim());
            emp.setPositionID(txtPositionID.getText().trim());

            if (dao.insert(emp)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
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
                JOptionPane.showMessageDialog(this, "Chọn một nhân viên để sửa!");
                return;
            }
            int id = Integer.parseInt(txtID.getText().trim());
            Employee emp = dao.getById(id); // lấy bản gốc từ DB

            // cập nhật từ ô nhập
            emp.setFullname(txtFullname.getText().trim());
            emp.setGender(txtGender.getText().trim());
            emp.setBirthdate(Date.valueOf(txtBirthdate.getText().trim()));
            emp.setPhone(txtPhone.getText().trim());
            emp.setEmail(txtEmail.getText().trim());
            emp.setAddress(txtAddress.getText().trim());
            emp.setHireDate(Date.valueOf(txtHireDate.getText().trim()));
            emp.setSalary(Double.parseDouble(txtSalary.getText().trim()));
            emp.setDepartmentID(txtDeptID.getText().trim());
            emp.setPositionID(txtPositionID.getText().trim());

            if (dao.update(emp)) {
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
                JOptionPane.showMessageDialog(this, "Chọn một nhân viên để xóa!");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.delete(id);
                loadData();
                clearInput();
            }
        });

        // Khi chọn dòng trong bảng → đổ dữ liệu lên ô nhập
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtID.setText(model.getValueAt(row, 0).toString());
                txtFullname.setText(model.getValueAt(row, 1).toString());
                txtGender.setText(model.getValueAt(row, 2).toString());
                txtBirthdate.setText(model.getValueAt(row, 3).toString());
                txtPhone.setText(model.getValueAt(row, 4).toString());
                txtEmail.setText(model.getValueAt(row, 5).toString());
                txtAddress.setText(model.getValueAt(row, 6).toString());
                txtHireDate.setText(model.getValueAt(row, 7).toString());
                txtSalary.setText(model.getValueAt(row, 8).toString());
                txtDeptID.setText(model.getValueAt(row, 9).toString());
                txtPositionID.setText(model.getValueAt(row, 10).toString());
            }
        });

        loadData();
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

    private void clearInput() {
        txtID.setText("");
        txtFullname.setText("");
        txtGender.setText("");
        txtBirthdate.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtHireDate.setText("");
        txtSalary.setText("");
        txtDeptID.setText("");
        txtPositionID.setText("");
    }
}
