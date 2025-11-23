package ui;

import dao.AccountDAO;
import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Interface callback để Main nhận thông tin khi login thành công
    public interface LoginListener {
        void onLoginSuccess(Account account);
    }

    private LoginListener loginListener;

    // Method để Main đăng ký callback
    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public LoginUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // mở giữa màn hình
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(20);

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        btnLogin = new JButton("Login");

        // --- Thêm vào layout ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblUsername, gbc);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(lblPassword, gbc);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // --- Xử lý nút Login ---
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Please enter username and password", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.login(username, password);

                if (account != null) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Login successful! Role: " + account.getRole(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (loginListener != null) {
                        loginListener.onLoginSuccess(account); // thông báo Main
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
