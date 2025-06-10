## student management system 

This is simple student management system desktop application for school project using jdbc driver and Jframe. 

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
Certainly! Here's a basic `README.md` for your Student Management System project:

Certainly! Here's an updated `README.md` for your Student Management System project that includes the MySQL setup steps:


# Student Management System

A simple Java Swing application for managing student information. It allows you to add, delete, and search for student records.

## Features

- Add student records with details such as name, ID, grade, date of birth, gender, contact, and email.
- Reset form fields.
- Delete student records.
- Search for a student by ID.
- Connects to a MySQL database to store and retrieve student data.

## Prerequisites

Before running the application, you need the following:

- Java Development Kit (JDK)
- MySQL Server
- MySQL Connector/J library (JDBC driver)
- A MySQL database named "student"
- A MySQL user with the username "root" and password "user" (You can modify the database credentials in the code)

## MySQL Setup

1. Open a MySQL client (e.g., MySQL Workbench or the MySQL command-line client).

2. To list all databases, run the following command:

   ```sql
   SHOW DATABASES;
   ```

3. Create a new database named "student" if it doesn't already exist:

   ```sql
   CREATE DATABASE student;
   ```

4. Switch to the "student" database:

   ```sql
   USE student;
   ```

5. Verify that you're in the "student" database:

   ```sql
   SELECT DATABASE();
   ```

6. Create a table named "students" to store student records. The table structure should match the fields you have in your Java application (name, ID, grade, date of birth, gender, contact, and email). Here's an example table structure:

   ```sql
   CREATE TABLE students (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255),
       student_id VARCHAR(255) UNIQUE,
       grade VARCHAR(255),
       date_of_birth DATE,
       gender VARCHAR(10),
       contact VARCHAR(20),
       email VARCHAR(255)
   );
   ```

7. You can view the table structure with:

   ```sql
   DESCRIBE students;
   ```

8. Now you have the "student" database and the "students" table ready to use with your Java application.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/StudentManagementSystem.git
   ```

2. Open the project in your preferred Java IDE (e.g., VS Code, IntelliJ IDEA, or Eclipse).

3. Configure your MySQL database settings in the code:

   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
   private static final String DB_USER = "root";
   private static final String DB_PASSWORD = "user";
   ```

4. Run the application.

## Usage

1. Launch the application.

2. Enter student details, such as name, ID, grade, date of birth, gender, contact, and email.

3. Click the "Add Student" button to add a student record.

4. Use the "Reset" button to clear the input fields.

5. Select a record in the table and click the "Delete Record" button to remove it.

6. Use the "Search by ID" field to search for a student by ID.

## Contributing

Feel free to contribute to this project by creating pull requests or reporting issues on GitHub.

---

Feel free to customize the README file to include additional information, screenshots, or instructions as needed. You can then push the README file to your GitHub repository to provide documentation for your project.

![Screenshot 2023-11-08 174322](https://github.com/atharvmaske/student-management-system-/assets/132181444/b31b6e56-873b-4b09-8911-524944a7419b)

![Screenshot 2023-11-08 174331](https://github.com/atharvmaske/student-management-system-/assets/132181444/61198222-2876-423a-83b1-55f6b17ae764)

![Screenshot 2023-11-08 174516](https://github.com/atharvmaske/student-management-system-/assets/132181444/94919254-3418-42bf-9b29-c74f693b4dfa)

![Screenshot 2023-11-08 174629](https://github.com/atharvmaske/student-management-system-/assets/132181444/cb2dea5c-1fa6-4e0d-8477-f1a3fdf085cf)



```

This updated README includes MySQL setup steps and a sample table structure for the "students" table in the MySQL database. You can further customize it as needed.

🔹 1. Console-Based OOP (Java)
✅ Features:
Text-based input/output

Ideal for learning logic and OOP concepts

📄 File: ConsoleStudentManagement.java
java
Copy
Edit
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String id, name, grade;
    
    Student(String id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Grade: " + grade);
    }
}

public class ConsoleStudentManagement {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 3);
    }

    static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student Grade: ");
        String grade = scanner.nextLine();
        students.add(new Student(id, name, grade));
        System.out.println("Student added successfully.");
    }

    static void viewStudents() {
        System.out.println("\nList of Students:");
        for (Student s : students) {
            s.display();
        }
    }
}
🔹 2. GUI-Based OOP (Java Swing)
✅ Features:
Uses JFrame, JButton, JTextField, etc.

Better user interaction via graphical elements

📄 File: GUIStudentManagement.java
java
Copy
Edit
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class GUIStudent {
    String id, name, grade;

    GUIStudent(String id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
    }
}

public class GUIStudentManagement extends JFrame implements ActionListener {
    JTextField idField, nameField, gradeField;
    JButton addButton, showButton;
    JTextArea displayArea;
    ArrayList<GUIStudent> studentList = new ArrayList<>();

    public GUIStudentManagement() {
        setTitle("GUI Student Management");
        setSize(400, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Student ID:"));
        idField = new JTextField(20);
        add(idField);

        add(new JLabel("Student Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Student Grade:"));
        gradeField = new JTextField(20);
        add(gradeField);

        addButton = new JButton("Add Student");
        showButton = new JButton("Show Students");
        add(addButton);
        add(showButton);

        displayArea = new JTextArea(10, 30);
        add(new JScrollPane(displayArea));

        addButton.addActionListener(this);
        showButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String id = idField.getText();
            String name = nameField.getText();
            String grade = gradeField.getText();
            studentList.add(new GUIStudent(id, name, grade));
            displayArea.setText("Student added successfully.");
            idField.setText(""); nameField.setText(""); gradeField.setText("");
        }

        if (e.getSource() == showButton) {
            displayArea.setText("");
            for (GUIStudent s : studentList) {
                displayArea.append(s + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIStudentManagement().setVisible(true));
    }
}
🔍 Difference Between Console and GUI OOP in Java:
Feature	Console-Based	GUI-Based (Swing)
Interface	Text-based	Graphical (buttons, text fields, etc.)
Interaction	Sequential input	Event-driven (button clicks, etc.)
Learning Focus	Good for OOP basics and logic	Good for real-world apps and UI design
Dependencies	No extra libraries	Requires javax.swing and AWT
