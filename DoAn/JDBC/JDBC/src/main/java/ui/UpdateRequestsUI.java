package ui;

import dao.UpdateRequestDAO;
import model.UpdateRequest;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import util.DBConnection;

public class UpdateRequestsUI extends JFrame {
    private JTable table;
    private UpdateRequestDAO dao = new UpdateRequestDAO();

    private List<UpdateRequest> requests;

    public UpdateRequestsUI() {
        setTitle("Duyệt yêu cầu cập nhật");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnApprove = new JButton("Approve");
        JButton btnReject = new JButton("Reject");

        btnApprove.addActionListener(e -> handleAction("APPROVED"));
        btnReject.addActionListener(e -> handleAction("REJECTED"));

        panelButtons.add(btnApprove);
        panelButtons.add(btnReject);
        add(panelButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        try (Connection conn = DBConnection.getConnection()) {
            requests = dao.getPendingRequests(conn);
            table = new JTable(new UpdateRequestTableModel(requests));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu yêu cầu!");
        }
    }

    private void handleAction(String action) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một yêu cầu trước!");
            return;
        }

        int requestID = (int) table.getValueAt(row, 0);
        try (Connection conn = DBConnection.getConnection()) {
            if ("APPROVED".equals(action)) {
                dao.approveRequest(requestID, conn);
                JOptionPane.showMessageDialog(this, "Đã duyệt yêu cầu!");
            } else {
                dao.rejectRequest(requestID, conn);
                JOptionPane.showMessageDialog(this, "Đã từ chối yêu cầu!");
            }
            loadData(); // refresh lại danh sách
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xử lý yêu cầu!");
        }
    }

    // TableModel hiển thị danh sách yêu cầu
    static class UpdateRequestTableModel extends AbstractTableModel {
        private final String[] columnNames = {
                "RequestID", "EmployeeID", "Fullname", "Email", "Phone", "Address", "Gender", "Username", "Password", "Status", "Request Date"
        };
        private final List<UpdateRequest> data;

        public UpdateRequestTableModel(List<UpdateRequest> data) {
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            UpdateRequest r = data.get(row);
            switch (col) {
                case 0: return r.getRequestID();
                case 1: return r.getEmployeeID();
                case 2: return r.getFullname();
                case 3: return r.getEmail();
                case 4: return r.getPhone();
                case 5: return r.getAddress();
                case 6: return r.getGender();
                case 7: return r.getUsername();
                case 8: return r.getPassword();
                case 9: return r.getStatus();
                case 10: return r.getRequestDate();
                default: return null;
            }
        }
    }
}
