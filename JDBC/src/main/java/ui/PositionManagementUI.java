package ui;

import dao.PositionDAO;
import model.Position;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PositionManagementUI extends JFrame {
    private PositionDAO dao = new PositionDAO();

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtPositionID, txtPositionName, txtNote;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

    public PositionManagementUI() {
        setTitle("Position Management");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        txtPositionID = new JTextField();
        txtPositionName = new JTextField();
        txtNote = new JTextField();

        inputPanel.add(new JLabel("Position ID"));
        inputPanel.add(new JLabel("Position Name"));
        inputPanel.add(new JLabel("Note"));

        inputPanel.add(txtPositionID);
        inputPanel.add(txtPositionName);
        inputPanel.add(txtNote);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);

        add(btnPanel, BorderLayout.SOUTH);

        // Table
        model = new DefaultTableModel(new String[]{
                "Position ID", "Position Name", "Note"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addPosition());
        btnUpdate.addActionListener(e -> updatePosition());
        btnDelete.addActionListener(e -> deletePosition());
        btnRefresh.addActionListener(e -> loadPositions());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    txtPositionID.setText(model.getValueAt(selected, 0).toString());
                    txtPositionName.setText(model.getValueAt(selected, 1).toString());
                    txtNote.setText(model.getValueAt(selected, 2).toString());
                }
            }
        });

        loadPositions();
        setVisible(true);
    }

    private void addPosition() {
        try {
            Position p = new Position();
            p.setPositionID(txtPositionID.getText());
            p.setPositionName(txtPositionName.getText());
            p.setNote(txtNote.getText());

            boolean success = dao.insert(p);
            JOptionPane.showMessageDialog(this, success ? "Added successfully" : "Failed to add");
            loadPositions();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void updatePosition() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select a position to update.");
            return;
        }
        try {
            Position p = new Position();
            p.setPositionID(txtPositionID.getText());
            p.setPositionName(txtPositionName.getText());
            p.setNote(txtNote.getText());

            boolean success = dao.update(p); // cần thêm method update(Position p) trong PositionDAO
            JOptionPane.showMessageDialog(this, success ? "Updated successfully" : "Failed to update");
            loadPositions();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void deletePosition() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select a position to delete.");
            return;
        }
        String positionID = model.getValueAt(selected, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete position " + positionID + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dao.delete(positionID); // cần thêm method delete(String positionID) trong DAO
            JOptionPane.showMessageDialog(this, success ? "Deleted successfully" : "Failed to delete");
            loadPositions();
        }
    }

    private void loadPositions() {
        model.setRowCount(0);
        List<Position> list = dao.getAll();
        for (Position p : list) {
            model.addRow(new Object[]{
                    p.getPositionID(),
                    p.getPositionName(),
                    p.getNote()
            });
        }
    }
}
