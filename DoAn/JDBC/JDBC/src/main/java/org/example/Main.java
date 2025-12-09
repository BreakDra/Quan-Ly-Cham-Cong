package org.example;

import ui.LoginUI;
import ui.AdminDashboardUI;
import ui.EmployeeDashboardUI;
import dao.EmployeeDAO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginUI loginUI = new LoginUI();

            loginUI.setLoginListener(account -> {
                loginUI.dispose();

                if ("ADMIN".equalsIgnoreCase(account.getRole())) {
                    new AdminDashboardUI(account).setVisible(true);
                } else {
                    EmployeeDAO empDAO = new EmployeeDAO();
                    int empId = empDAO.findEmployeeIdByAccountId(account.getAccountID());
                    if (empId == -1) {
                        JOptionPane.showMessageDialog(null, "No employee linked to this account!");
                        return;
                    }
                    new EmployeeDashboardUI(account, empId).setVisible(true);

                }
            });


            loginUI.setVisible(true);
        });
    }
}
