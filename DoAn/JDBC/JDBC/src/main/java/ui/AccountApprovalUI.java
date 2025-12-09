package ui;

import dao.AccountDAO;
import model.Account;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AccountApprovalUI extends JFrame {
    private AccountDAO dao = new AccountDAO();
    private JTable table;
    private DefaultTableModel model;
    private JButton btnApprove, btnReject, btnRefresh;

    public AccountApprovalUI() {
        setTitle("Account Approval - Admin");
        setSize(600, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"AccountID", "Username", "Role"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        btnApprove = new JButton("Approve");
        btnReject = new JButton("Reject");
        btnRefresh = new JButton("Refresh");
        panel.add(btnApprove);
        panel.add(btnReject);
        panel.add(btnRefresh);
        add(panel, BorderLayout.SOUTH);

        btnApprove.addActionListener(e -> approveAccount());
        btnReject.addActionListener(e -> rejectAccount());
        btnRefresh.addActionListener(e -> loadPending());

        loadPending();
        setVisible(true);
    }

    private void loadPending() {
        model.setRowCount(0);
        List<Account> list = dao.getPendingAccounts();
        for (Account acc : list) {
            model.addRow(new Object[]{acc.getAccountID(), acc.getUsername(), acc.getRole()});
        }
    }

    private void approveAccount() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            int accountID = (int) model.getValueAt(selected, 0);
            boolean success = dao.approve(accountID);
            JOptionPane.showMessageDialog(this, success ? "Approved!" : "Failed to approve");
            loadPending();
        }
    }

    private void rejectAccount() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            int accountID = (int) model.getValueAt(selected, 0);
            boolean success = dao.reject(accountID);
            JOptionPane.showMessageDialog(this, success ? "Rejected!" : "Failed to reject");
            loadPending();
        }
    }
}
