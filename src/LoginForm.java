import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm {
    public void createAndShowLoginGUI() {
        JFrame frame = new JFrame("Faculty Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Create a label with a background image
        ImageIcon imageIcon = new ImageIcon("PUP_BUILDING3.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(resizedImageIcon);
        //JLabel background = new JLabel(new ImageIcon("/mnt/data/PUP_BUILDING1.png"));
        background.setLayout(null);
         
        ImageIcon Logo = new ImageIcon("PUP_LOGO.png");
        Image ResizedLogo = Logo.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
        ImageIcon FinalLogo = new ImageIcon(ResizedLogo);
        JLabel lblLogo = new JLabel(FinalLogo);
        lblLogo.setBounds(50, 0, 100, 100);
        background.add(lblLogo);

        JLabel universityLabel = new JLabel("Polytechnic University of the Philippines");
        universityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        universityLabel.setBounds(150, 20, 400, 30);
        background.add(universityLabel);

        JLabel instituteLabel = new JLabel("Institute of Bachelors in Information Technology Studies");
        instituteLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        instituteLabel.setBounds(150, 40, 400, 30);
        background.add(instituteLabel);

        JLabel loginLabel = new JLabel("Faculty LogIn");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setBounds(80, 100, 300, 30);
        background.add(loginLabel);

        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setBounds(150, 150, 80, 25);
        background.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(230, 150, 200, 25);
        background.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setBounds(150, 190, 80, 25);
        background.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(230, 190, 200, 25);
        background.add(passwordField);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(230, 230, 90, 25);
        background.add(loginButton);

        JButton signUpButton = new JButton("sign up");
        signUpButton.setBounds(340, 230, 90, 25);
        background.add(signUpButton);

        frame.add(background);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (validateLogin(email, password)) {
                    frame.dispose();
                    LoadingScreen loadingScreen = new LoadingScreen(new Runnable() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    new StudentInfoTable().createAndShowStudentInfoGUI();
                                }
                            });
                        }
                    });
                    loadingScreen.showLoadingScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                InfoManageSystem.showSignUpForm();
            }
        });
    }

    private boolean validateLogin(String email, String password) {
        String url = "jdbc:mysql://localhost:3306/db_cite";
        String usernameDB = "root";
        String passwordDB = "";

        try (Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            String sql = "SELECT * FROM admin_info WHERE `Email` = ? AND `Passwords` = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
}

