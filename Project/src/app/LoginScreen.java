package app;

import core.CourseMate;

import javax.swing.*;
import java.awt.*;

/**
 * Login screen for NSU CourseMate.
 */
public class LoginScreen extends JFrame {

    private final JTextField idField = new JTextField();
    private final JPasswordField passField = new JPasswordField();
    private final CourseMate courseMate;
    

    // Hardcoded credentials
    private static final String VALID_ID = "12345";
    private static final String VALID_PASS = "abcde";

    public LoginScreen(CourseMate courseMate) {
        super("NSU CourseMate – Login");
        
        this.courseMate = courseMate;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(0, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        root.setBackground(new Color(245, 245, 245));
        add(root);

        // Title panel with logo and text
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        titlePanel.setBackground(new Color(245, 245, 245));
        JLabel logoLabel = new JLabel(loadLogo(40, 40));
        titlePanel.add(logoLabel);

        JLabel title = new JLabel("Welcome to NSU CourseMate", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 18));
        titlePanel.add(title);

        root.add(titlePanel, BorderLayout.NORTH);

        // Login form
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 150, 255), 2, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        form.add(new JLabel("Student ID:"));
        form.add(idField);
        form.add(new JLabel("Password:"));
        form.add(passField);
        root.add(form, BorderLayout.CENTER);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.addActionListener(e -> tryLogin());
        root.add(loginBtn, BorderLayout.SOUTH);
    }

    // Load and scale logo
    private ImageIcon loadLogo(int width, int height) {
        ImageIcon icon = new ImageIcon("images/logo.png");
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void tryLogin() {
        String id = idField.getText().trim();
        String pass = new String(passField.getPassword());

        if (VALID_ID.equals(id) && VALID_PASS.equals(pass)) {
            SwingUtilities.invokeLater(() -> {
                new HomeScreen(courseMate).setVisible(true);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid ID or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseMate cm = CourseMate.loadFromFile(); // Load saved data
            LoginScreen screen = new LoginScreen(cm);  // Pass loaded instance
            screen.setVisible(true);
        });
    }

}
