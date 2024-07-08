import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpForm {
    public void createAndShowSignUpGUI() {
        JFrame frame = new JFrame("Create New Admin Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        
        ImageIcon imageIcon = new ImageIcon("PUP_BUILDING3.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(scaledImage);
        JLabel panel = new JLabel(resizedImageIcon);
        //JLabel background = new JLabel(new ImageIcon("/mnt/data/PUP_BUILDING1.png"));
        panel.setLayout(null);
        frame.add(panel);
        frame.setVisible(true);
        
        
        
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("CREATE NEW ADMIN ACCOUNT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 50, 400, 30);
        panel.add(titleLabel);
        
        JLabel noticeLabel = new JLabel("*Please enter correct information");
        noticeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        noticeLabel.setBounds(50, 80, 400, 20);
        panel.add(noticeLabel);

        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setBounds(150, 150, 80, 25);
        panel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(230, 150, 200, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setBounds(150, 190, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(230, 190, 200, 25);
        panel.add(passwordField);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 230, 100, 25);
        panel.add(backButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(330, 230, 100, 25);
        panel.add(signUpButton);

        frame.add(panel);
        frame.setVisible(true);
        
        
        frame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginForm().createAndShowLoginGUI();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                } else {
                    if (registerAccount(email, password)) {
                        JOptionPane.showMessageDialog(frame, "Account created successfully. You can now log in.");
                        frame.dispose();
                        new LoginForm().createAndShowLoginGUI();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Account creation failed. Please try again.");
                    }
                }
            }
        });
    }

    private boolean registerAccount(String email, String password) {
        String url = "jdbc:mysql://localhost:3306/db_cite";
        String usernameDB = "root";
        String passwordDB = "";

        try (Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            String sql = "INSERT INTO `admin_info` (`Email`, `Passwords`) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
}
