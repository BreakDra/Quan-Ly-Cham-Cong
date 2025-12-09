package ui;

import dao.ShiftDAO;
import model.Shift;
import java.sql.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShiftManagementUI extends JFrame {
    private ShiftDAO dao = new ShiftDAO();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtShiftID, txtShiftName, txtTimeStart, txtTimeEnd, txtBreakMinutes, txtOvertimeRate;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

    public ShiftManagementUI() {
        setTitle("Shift Management");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        txtShiftID = new JTextField();
        txtShiftID.setEditable(false); // auto-increment
        txtShiftName = new JTextField();
        txtTimeStart = new JTextField();
        txtTimeEnd = new JTextField();
        txtBreakMinutes = new JTextField();
        txtOvertimeRate = new JTextField();

        inputPanel.add(new JLabel("Shift ID"));
        inputPanel.add(new JLabel("Shift Name"));
        inputPanel.add(new JLabel("Start Time"));
        inputPanel.add(new JLabel("End Time"));
        inputPanel.add(new JLabel("Break Minutes"));
        inputPanel.add(new JLabel("Overtime Rate"));

        inputPanel.add(txtShiftID);
        inputPanel.add(txtShiftName);
        inputPanel.add(txtTimeStart);
        inputPanel.add(txtTimeEnd);
        inputPanel.add(txtBreakMinutes);
        inputPanel.add(txtOvertimeRate);

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
                 "Shift ID", "Shift Name", "Start", "End", "Break", "Overtime Rate"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addShift());
        btnUpdate.addActionListener(e -> updateShift());
        btnDelete.addActionListener(e -> deleteShift());
        btnRefresh.addActionListener(e -> loadShifts());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    txtShiftID.setText(model.getValueAt(selected, 0).toString());
                    txtShiftName.setText(model.getValueAt(selected, 1).toString());
                    txtTimeStart.setText(model.getValueAt(selected, 2).toString());
                    txtTimeEnd.setText(model.getValueAt(selected, 3).toString());
                    txtBreakMinutes.setText(model.getValueAt(selected, 4).toString());
                    txtOvertimeRate.setText(model.getValueAt(selected, 5).toString());
                }
            }
        });

        loadShifts();
        setVisible(true);
    }

    private void addShift() {
        try {
            Shift s = new Shift();
            s.setShiftName(txtShiftName.getText());
            s.setTimeStart(Time.valueOf(txtTimeStart.getText().trim())); // sửa ở đây
            s.setTimeEnd(Time.valueOf(txtTimeEnd.getText().trim()));     // sửa ở đây
            s.setBreakMinutes(Integer.parseInt(txtBreakMinutes.getText()));
            s.setOvertimeRate(Double.parseDouble(txtOvertimeRate.getText()));

            boolean success = dao.insert(s);
            JOptionPane.showMessageDialog(this, success ? "Added successfully" : "Failed to add");
            loadShifts();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Time must be in format HH:mm:ss");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }


    private void updateShift() {
        try {
            Shift s = new Shift();
            s.setShiftID(Integer.parseInt(txtShiftID.getText()));
            s.setShiftName(txtShiftName.getText());
            s.setTimeStart(Time.valueOf(txtTimeStart.getText().trim())); // sửa ở đây
            s.setTimeEnd(Time.valueOf(txtTimeEnd.getText().trim()));     // sửa ở đây
            s.setBreakMinutes(Integer.parseInt(txtBreakMinutes.getText()));
            s.setOvertimeRate(Double.parseDouble(txtOvertimeRate.getText()));

            boolean success = dao.update(s);
            JOptionPane.showMessageDialog(this, success ? "Updated successfully" : "Failed to update");
            loadShifts();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Time must be in format HH:mm:ss");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }


    private void deleteShift() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select a shift to delete.");
            return;
        }
        int shiftID = Integer.parseInt(model.getValueAt(selected, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete shift " + shiftID + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dao.delete(shiftID);
            JOptionPane.showMessageDialog(this, success ? "Deleted successfully" : "Failed to delete");
            loadShifts();
        }
    }

    private void loadShifts() {
        model.setRowCount(0);
        List<Shift> list = dao.getAll();
        int stt = 1;
        for (Shift s : list) {
            model.addRow(new Object[]{
                    s.getShiftID(),
                    s.getShiftName(),
                    s.getTimeStart(),
                    s.getTimeEnd(),
                    s.getBreakMinutes(),
                    s.getOvertimeRate()
            });
        }
    }

}
