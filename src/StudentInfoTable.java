import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class StudentInfoTable {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;

    public void createAndShowStudentInfoGUI() {
        JFrame frame = new JFrame("Student Information Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);

        // Create a label with a background image
        ImageIcon imageIcon = new ImageIcon("path_to_background_image.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(resizedImageIcon);
        background.setLayout(new BorderLayout());
        
        String[] columnNames = {"Student ID", "Last Name", "First Name", "Middle Name", "Course", "Year Level", "Address", "Contact Number", "Birthday", "Position", "Organization"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Load data from the database
        loadStudentData(model);

        JScrollPane scrollPane = new JScrollPane(table);
        background.add(scrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton logoutButton = new JButton("Logout");

        topPanel.add(new JLabel("Search by Student ID:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(deleteButton);
        topPanel.add(logoutButton);

        background.add(topPanel, BorderLayout.NORTH);

        JButton addButton = new JButton("Add Data");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudentInformation().createAndShowSignUpGUI();
            }
        });
        background.add(addButton, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = searchField.getText().trim();
                if (!studentId.isEmpty()) {
                    searchStudentData(studentId);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a Student ID to search.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String studentId = model.getValueAt(selectedRow, 0).toString();
                    deleteStudentData(studentId);
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginForm().createAndShowLoginGUI();
            }
        });

        frame.add(background);
        frame.setVisible(true);
    }

    private void loadStudentData(DefaultTableModel model) {
        String url = "jdbc:mysql://localhost:3306/db_cite";
        String usernameDB = "root";
        String passwordDB = "";

        try (Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            String sql = "SELECT * FROM student_info";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String studentId = resultSet.getString("Student_ID");
                String lastName = resultSet.getString("Student_LastName");
                String firstName = resultSet.getString("Student_FirstName");
                String middleName = resultSet.getString("Student_MiddleName");
                String course = resultSet.getString("Course");
                String yearLevel = resultSet.getString("Year");
                String address = resultSet.getString("Address");
                String contactNumber = resultSet.getString("Contact_Number");
                String birthday = resultSet.getString("Birthday");
                String position = resultSet.getString("Position");
                String organization = resultSet.getString("Organization");

                model.addRow(new Object[]{studentId, lastName, firstName, middleName, course, yearLevel, address, contactNumber, birthday, position, organization});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchStudentData(String studentId) {
        String url = "jdbc:mysql://localhost:3306/db_cite";
        String usernameDB = "root";
        String passwordDB = "";

        try (Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            String sql = "SELECT * FROM student_info WHERE Student_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            model.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String id = resultSet.getString("Student_ID");
                String lastName = resultSet.getString("Student_LastName");
                String firstName = resultSet.getString("Student_FirstName");
                String middleName = resultSet.getString("Student_MiddleName");
                String course = resultSet.getString("Course");
                String yearLevel = resultSet.getString("Year");
                String address = resultSet.getString("Address");
                String contactNumber = resultSet.getString("Contact_Number");
                String birthday = resultSet.getString("Birthday");
                String position = resultSet.getString("Position");
                String organization = resultSet.getString("Organization");

                model.addRow(new Object[]{id, lastName, firstName, middleName, course, yearLevel, address, contactNumber, birthday, position, organization});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteStudentData(String studentId) {
        String url = "jdbc:mysql://localhost:3306/db_cite";
        String usernameDB = "root";
        String passwordDB = "";

        try (Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            String sql = "DELETE FROM student_info WHERE Student_ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
