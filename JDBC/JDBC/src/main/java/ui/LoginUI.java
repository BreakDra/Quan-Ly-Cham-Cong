package ui;

import dao.AccountDAO;
import model.Account;
import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private AccountDAO dao = new AccountDAO();

    public interface LoginListener {
        void onLoginSuccess(Account account);
    }

    private LoginListener loginListener;

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public LoginUI() {
        setTitle("Login");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // màu nền tổng thể
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout(20, 20));

        // Tiêu đề
        JLabel lblTitle = new JLabel("Welcome to Attendance System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 33, 33));
        add(lblTitle, BorderLayout.NORTH);

        // Panel nhập thông tin
        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInput.setBackground(new Color(245, 245, 245));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panelInput.add(lblUser);
        panelInput.add(txtUsername);
        panelInput.add(lblPass);
        panelInput.add(txtPassword);

        add(panelInput, BorderLayout.CENTER);

        // Panel chứa nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.setBackground(new Color(245, 245, 245));

        btnLogin = createStyledButton("Login", new Color(70, 130, 180));
        btnRegister = createStyledButton("Register", new Color(34, 139, 34));

        panelButtons.add(btnLogin);
        panelButtons.add(btnRegister);
        add(panelButtons, BorderLayout.SOUTH);

        // Action Login
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            Account acc = dao.login(username, password);
            if (acc == null) {
                JOptionPane.showMessageDialog(this, "Sai thông tin hoặc tài khoản chưa được duyệt!");
            } else {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                if (loginListener != null) {
                    loginListener.onLoginSuccess(acc);
                }
            }
        });

        // Action Register
        btnRegister.addActionListener(e -> new RegisterUI());

        setVisible(true);
    }

    // Hàm tạo nút đẹp
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
