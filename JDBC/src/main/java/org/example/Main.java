package org.example;

import dao.*;
import model.*;
import ui.*;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // --- 1️⃣ Test các DAO (in console) ---
        try {
            System.out.println("=== Test PositionDAO ===");
            PositionDAO positionDAO = new PositionDAO();
            List<Position> positions = positionDAO.getAll();
            for (Position p : positions) {
                System.out.println(p.getPositionID() + " | " + p.getPositionName() + " | " + p.getNote());
            }

            System.out.println("\n=== Test DepartmentDAO ===");
            DepartmentDAO departmentDAO = new DepartmentDAO();
            departmentDAO.getAll().forEach(d ->
                    System.out.println(d.getDepartmentID() + " | " + d.getDepartmentName())
            );

            System.out.println("\n=== Test EmployeeDAO ===");
            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.getAll().forEach(e ->
                    System.out.println(e.getEmployeeID() + " | " + e.getFullname() + " | " + e.getDepartmentID() + " | " + e.getPositionID())
            );

            System.out.println("\n=== Test ShiftDAO ===");
            ShiftDAO shiftDAO = new ShiftDAO();
            shiftDAO.getAll().forEach(s ->
                    System.out.println(s.getShiftID() + " | " + s.getShiftName() + " | " + s.getTimeStart() + "-" + s.getTimeEnd())
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // --- 2️⃣ Mở LoginUI ---
        SwingUtilities.invokeLater(() -> {
            LoginUI loginUI = new LoginUI();

            // --- 3️⃣ Thiết lập listener khi login thành công ---
            loginUI.setLoginListener(account -> {
                loginUI.dispose(); // đóng LoginUI

                // Phân quyền theo role
                if ("ADMIN".equalsIgnoreCase(account.getRole())) {
                    AdminDashboard adminUI = new AdminDashboard(account);
                    adminUI.setVisible(true);
                } else {
                    EmployeeDashboard empUI = new EmployeeDashboard(account);
                    empUI.setVisible(true);
                }
            });

            loginUI.setVisible(true);
        });
    }
}
