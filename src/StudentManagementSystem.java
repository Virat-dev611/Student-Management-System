/**
 * StudentManagementSystem.java
 * 
 * A GUI-based Student Management System application using Swing and MySQL database.
 * This application allows users to perform CRUD operations on student records.
 * 
 * Key Features:
 * - Add new student records with validation
 * - View all students in a table
 * - Delete selected student records
 * - Search students by ID
 * - Data persistence using MySQL database
 * 
 * @author Virat
 * @version 1.0
 * @since 2025-06-10
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StudentManagementSystem extends JFrame implements ActionListener {
    
    // GUI Components
    private JLabel titleLabel;
    @SuppressWarnings("unused")
    private JLabel nameLabel, idLabel, gradeLabel, dobLabel, genderLabel, contactLabel, emailLabel;
    private JTextField nameField, idField, gradeField, dobField, contactField, emailField, searchField;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JButton addButton, resetButton, deleteButton, searchButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    // Database Configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "user";
    private Connection connection;

    /**
     * Constructor - Initializes the GUI components and database connection
     */
    public StudentManagementSystem() {
        // Frame setup
        setTitle("Student Management System");
        setLayout(null);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initializeComponents();
        setupDatabaseConnection();
        loadStudentData();
        
        setVisible(true);
    }
    
    /**
     * Initializes all GUI components and adds them to the frame
     */
    private void initializeComponents() {
        // Title Label
        titleLabel = new JLabel("STUDENT MANAGEMENT SYSTEM");
        titleLabel.setBounds(250, 10, 700, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel);
        
        // Initialize labels and text fields
        createLabelAndField("Student Name", 50, 80, nameField = new JTextField(), 200, 80);
        createLabelAndField("Student ID", 50, 120, idField = new JTextField(), 200, 120);
        createLabelAndField("Student Grade", 50, 160, gradeField = new JTextField(), 200, 160);
        createLabelAndField("Date of Birth (dd-mm-yyyy)", 50, 200, dobField = new JTextField(), 200, 200);
        createLabelAndField("Contact", 50, 280, contactField = new JTextField(), 200, 280);
        createLabelAndField("Email", 50, 320, emailField = new JTextField(), 200, 320);
        
        // Gender Radio Buttons
        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(50, 240, 150, 30);
        add(genderLabel);
        
        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(200, 240, 80, 30);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(290, 240, 100, 30);
        
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        add(maleRadio);
        add(femaleRadio);
        
        // Buttons
        addButton = createButton("Add Student", 650, 150, this);
        resetButton = createButton("Reset", 650, 200, this);
        deleteButton = createButton("Delete", 650, 250, this);
        
        // Search Components
        searchField = new JTextField();
        searchField.setBounds(50, 360, 300, 30);
        add(searchField);
        
        searchButton = createButton("Search by ID", 360, 360, this);
        
        // Student Table
        String[] columnNames = {"Name", "ID", "Grade", "DOB", "Gender", "Contact", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(50, 400, 860, 150);
        add(scrollPane);
    }
    
    /**
     * Helper method to create label and corresponding text field
     */
    private void createLabelAndField(String labelText, int x, int y, JTextField field, int fieldX, int fieldY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 150, 30);
        add(label);
        
        field.setBounds(fieldX, fieldY, 200, 30);
        add(field);
    }
    
    /**
     * Helper method to create buttons
     */
    private JButton createButton(String text, int x, int y, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 30);
        button.addActionListener(listener);
        add(button);
        return button;
    }
    
    /**
     * Establishes connection to MySQL database
     */
    private void setupDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Loads student data from database into the table
     */
    private void loadStudentData() {
        try {
            String query = "SELECT student_name, student_id, student_grade, " +
                           "DATE_FORMAT(dob, '%d-%m-%Y'), gender, contact, email FROM students";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString(1), // name
                    rs.getString(2), // id
                    rs.getString(3), // grade
                    rs.getString(4), // dob
                    rs.getString(5), // gender
                    rs.getString(6), // contact
                    rs.getString(7)  // email
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handles button click events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addStudent();
        } else if (e.getSource() == resetButton) {
            resetForm();
        } else if (e.getSource() == deleteButton) {
            deleteStudent();
        } else if (e.getSource() == searchButton) {
            searchStudent();
        }
    }
    
    /**
     * Validates and adds a new student record
     */
    private void addStudent() {
        // Get form values
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String grade = gradeField.getText().trim();
        String dob = dobField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String gender = maleRadio.isSelected() ? "Male" : "Female";
        
        // Validate inputs
        if (!validateInputs(name, id, grade, dob, contact, email)) {
            return;
        }
        
        // Add to table
        tableModel.addRow(new Object[]{name, id, grade, dob, gender, contact, email});
        
        // Insert into database
        insertStudentToDatabase(name, id, grade, dob, gender, contact, email);
        
        // Reset form
        resetForm();
    }
    
    /**
     * Validates all form inputs
     */
    private boolean validateInputs(String name, String id, String grade, String dob, 
                                 String contact, String email) {
        if (name.isEmpty() || id.isEmpty() || grade.isEmpty() || 
            dob.isEmpty() || contact.isEmpty() || email.isEmpty()) {
            showError("All fields are required!");
            return false;
        }
        
        if (!isValidEmail(email)) {
            showError("Invalid email format!");
            return false;
        }
        
        if (!isValidDate(dob)) {
            showError("Invalid date format! Use dd-mm-yyyy");
            return false;
        }
        
        if (!isNumeric(id)) {
            showError("Student ID must be numeric!");
            return false;
        }
        
        if (!isValidGrade(grade)) {
            showError("Grade must be numeric!");
            return false;
        }
        
        if (!isValidContact(contact)) {
            showError("Contact must contain only numbers!");
            return false;
        }
        
        return true;
    }
    
    /**
     * Inserts student record into database
     */
    private void insertStudentToDatabase(String name, String id, String grade, String dob, 
                                      String gender, String contact, String email) {
        try {
            String query = "INSERT INTO students VALUES (?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'), ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            
            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.setString(3, grade);
            stmt.setString(4, dob);
            stmt.setString(5, gender);
            stmt.setString(6, contact);
            stmt.setString(7, email);
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            }
        } catch (SQLException e) {
            showError("Database error: " + e.getMessage());
        }
    }
    
    /**
     * Resets all form fields
     */
    private void resetForm() {
        nameField.setText("");
        idField.setText("");
        gradeField.setText("");
        dobField.setText("");
        contactField.setText("");
        emailField.setText("");
        genderGroup.clearSelection();
    }
    
    /**
     * Deletes selected student record
     */
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a student to delete");
            return;
        }
        
        String id = tableModel.getValueAt(selectedRow, 1).toString();
        
        try {
            String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            }
        } catch (SQLException e) {
            showError("Database error: " + e.getMessage());
        }
    }
    
    /**
     * Searches for student by ID
     */
    private void searchStudent() {
        String searchId = searchField.getText().trim();
        
        if (searchId.isEmpty()) {
            showError("Please enter an ID to search");
            return;
        }
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 1).equals(searchId)) {
                studentTable.setRowSelectionInterval(i, i);
                studentTable.scrollRectToVisible(studentTable.getCellRect(i, 0, true));
                return;
            }
        }
        
        showError("Student with ID " + searchId + " not found");
    }
    
    // ========== VALIDATION METHODS ========== //
    
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
    
    private boolean isValidDate(String date) {
        try {
            new SimpleDateFormat("dd-MM-yyyy").parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    
    private boolean isValidGrade(String grade) {
        return grade.matches("^\\d*\\.?\\d+$");
    }
    
    private boolean isValidContact(String contact) {
        return contact.matches("\\d+");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementSystem();
        });
    }
}