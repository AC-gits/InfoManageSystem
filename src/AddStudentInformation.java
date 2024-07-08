import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddStudentInformation {
    public void createAndShowSignUpGUI() {
        JFrame frame = new JFrame("Add Student Information");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JLabel background = new JLabel(new ImageIcon("PUP_BUILDING3.png"));
        background.setLayout(new GridLayout(12, 2, 10, 10));

        JTextField studentIdField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField middleNameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField yearLevelField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField contactNumberField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField organizationField = new JTextField();

        background.add(new JLabel("Student ID:"));
        background.add(studentIdField);
        background.add(new JLabel("Last Name:"));
        background.add(lastNameField);
        background.add(new JLabel("First Name:"));
        background.add(firstNameField);
        background.add(new JLabel("Middle Name:"));
        background.add(middleNameField);
        background.add(new JLabel("Course:"));
        background.add(courseField);
        background.add(new JLabel("Year Level:"));
        background.add(yearLevelField);
        background.add(new JLabel("Address (City Only):"));
        background.add(addressField);
        background.add(new JLabel("Contact Number:"));
        background.add(contactNumberField);
        background.add(new JLabel("Birthday (DD/MM/YYYY):"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.add(new JComboBox<>(getDays()));
        datePanel.add(new JComboBox<>(getMonths()));
        datePanel.add(new JComboBox<>(getYears()));
        background.add(datePanel);
        background.add(new JLabel("Position:"));
        background.add(positionField);
        background.add(new JLabel("Organization:"));
        background.add(organizationField);

        JButton addButton = new JButton("Add Student Information");
        background.add(new JLabel()); // Empty space
        background.add(addButton);

        frame.add(background);
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = studentIdField.getText();
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String middleName = middleNameField.getText();
                String course = courseField.getText();
                String yearLevel = yearLevelField.getText();
                String address = addressField.getText();
                String contactNumber = contactNumberField.getText();
                String birthday = getBirthday(datePanel);
                String position = positionField.getText();
                String organization = organizationField.getText();

                if (addStudentData(studentId, lastName, firstName, middleName, course, yearLevel, address, contactNumber, birthday, position, organization)) {
                    JOptionPane.showMessageDialog(frame, "Student data added successfully!");
                    frame.dispose();
                    new StudentInfoTable().createAndShowStudentInfoGUI();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error adding student data.");
                }
            }
        });
    }

    private boolean addStudentData(String studentId, String lastName, String firstName, String middleName, String course, String yearLevel, String address, String contactNumber, String birthday, String position, String organization) {
        String url = "jdbc:mysql://localhost:3306/db_cite";
        String usernameDB = "root";
        String passwordDB = "";

        try (Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            String sql = "INSERT INTO student_info (Student_ID, Student_LastName, Student_FirstName, Student_MiddleName, Course, Year, Address, Contact_Number, Birthday, Position, Organization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, middleName);
            statement.setString(5, course);
            statement.setString(6, yearLevel);
            statement.setString(7, address);
            statement.setString(8, contactNumber);
            statement.setString(9, birthday);
            statement.setString(10, position);
            statement.setString(11, organization);
            statement.executeUpdate();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private String getBirthday(JPanel datePanel) {
        JComboBox dayBox = (JComboBox) datePanel.getComponent(0);
        JComboBox monthBox = (JComboBox) datePanel.getComponent(1);
        JComboBox yearBox = (JComboBox) datePanel.getComponent(2);
        return yearBox.getSelectedItem() + "/" + monthBox.getSelectedItem() + "/" + dayBox.getSelectedItem();
    }

    private String[] getDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        return days;
    }

    private String[] getMonths() {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = String.valueOf(i + 1);
        }
        return months;
    }

    private String[] getYears() {
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(2024 - i);
        }
        return years;
    }
}