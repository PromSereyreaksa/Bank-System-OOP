package GUI;

import GUI.customer_gui.MainMenuForm;
import GUI.customer_gui.RegisterForm;
import GUI.staff_gui.StaffDashboard;
import database.StaffDAO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import user.Customer;
import user.staff.Staff;

public class Login extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton cancelButton;
    
    private Color brandBlue = new Color(0, 102, 204);
    private Color brandGreen = new Color(0, 175, 0);
    
    public Login() {
        setTitle("Bank Portal - Login");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        initializeComponents();
        addActionListeners();
    }
    
    private void initializeComponents() {
        // Header
        JLabel headerLabel = new JLabel("BANK PORTAL", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(brandBlue);
        headerLabel.setBounds(262, 100, 500, 50);
        add(headerLabel);
        
        // Login panel
        JPanel loginPanel = new JPanel(null);
        loginPanel.setBounds(312, 200, 400, 400);
        loginPanel.setBorder(BorderFactory.createLineBorder(brandBlue, 2));
        
        JLabel loginLabel = new JLabel("Login", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(brandBlue);
        loginLabel.setBounds(100, 30, 200, 40);
        loginPanel.add(loginLabel);
        
        JLabel emailLabel = new JLabel("Email or Phone:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(50, 100, 200, 30);
        loginPanel.add(emailLabel);
        
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBounds(50, 130, 300, 40);
        loginPanel.add(emailField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setBounds(50, 180, 100, 30);
        loginPanel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(50, 210, 300, 40);
        loginPanel.add(passwordField);
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBounds(50, 280, 140, 40);
        loginButton.setBackground(brandBlue);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);
        
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBounds(210, 280, 140, 40);
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(brandBlue);
        registerButton.setFocusPainted(false);
        loginPanel.add(registerButton);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBounds(50, 340, 300, 40);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        loginPanel.add(cancelButton);
        
        add(loginPanel);
        
        // Version label
        JLabel versionLabel = new JLabel("Bank Portal v1.0", JLabel.RIGHT);
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        versionLabel.setBounds(824, 698, 150, 20);
        add(versionLabel);
    }
    
    private void addActionListeners() {
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        cancelButton.addActionListener(e -> System.exit(0));
        
        // Allow login by pressing Enter in password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }
    
    private void handleLogin() {
        String emailOrPhone = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (emailOrPhone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email/phone and password.", 
                                         "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // First, try customer login
            Customer customerInstance = new Customer();
            Customer customer = customerInstance.login(emailOrPhone, password);
            
            if (customer != null) {
                // Customer login successful
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + customer.getFullName(),
                                              "Login Success", JOptionPane.INFORMATION_MESSAGE);
                
                MainMenuForm mainMenuForm = new MainMenuForm(customer);
                mainMenuForm.setVisible(true);
                dispose();
                return;
            }
            
            // If customer login fails, try staff login
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.verifyCredentials(emailOrPhone, password);
            
            if (staff != null) {
                // Staff login successful
                boolean isAdmin = (emailOrPhone.equals("admin") || staff.getRole() == Staff.StaffRole.MANAGER);
                
                String message = isAdmin ? "Admin Login Successful!" : "Employee Login Successful!";
                JOptionPane.showMessageDialog(this, message, 
                                              "Login Success", JOptionPane.INFORMATION_MESSAGE);
                
                StaffDashboard dashboard = new StaffDashboard(staff, isAdmin);
                dashboard.setVisible(true);
                dispose();
                return;
            }
            
            // If both logins fail
            JOptionPane.showMessageDialog(this, "Invalid email/phone or password.", 
                                         "Login Failed", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage(), 
                                         "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRegister() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}