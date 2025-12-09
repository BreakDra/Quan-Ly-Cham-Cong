package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quan_ly_nhan_su?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "An_cat_m0i";

    private static boolean printed = false; // kiểm soát in log

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            if (!printed) {
                System.out.println(" Kết nối MySQL thành công!");
                printed = true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Kết nối MySQL thất bại!");
            e.printStackTrace();
        }
        return conn;
    }
}
