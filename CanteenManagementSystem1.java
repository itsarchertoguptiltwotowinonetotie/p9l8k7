import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;


class Student {
    String username, password,name;
    double walletBalance;
    ArrayList<String> purchaseHistory;

    public Student(String username, String password,String name,double initialBalance) {
        this.name=name;
        this.username = username;
        this.password = password;
        this.walletBalance = initialBalance;
        this.purchaseHistory = new ArrayList<>();
    }
}


class AdminModule {
    static HashMap<String, Student> students = new HashMap<>();
    static HashMap<String, Double> menu = new HashMap<>();

    static {
        students.put("19430", new Student("19430", "19430","Pothiraju", 125.0));
        students.put("19491", new Student("19491", "19491","Sujan" ,250.0));
        students.put("19523", new Student("19523", "19523","Sai Ramkumar", 185.0));
        students.put("19535", new Student("19535", "19535","Naveen Balan", 5.0));
        students.put("19869", new Student("19869", "19869", "Malesh Kumar",64.0));
        students.put("20091", new Student("20091", "20091", "Siva Selvan",77.0));
        students.put("20116", new Student("20116", "20116", "Vigneshwaran",100.0));

        menu.put("Vada", 8.0);
        menu.put("Bajji", 10.0);
        menu.put("Veg-puff", 15.0);
        menu.put("Egg-puff", 20.0);
        menu.put("Chicken-puff", 25.0);
        menu.put("Milkshake", 35.0);
    }
}


class StudentModule extends JFrame {
    private Student selectedStudent;

    public StudentModule(Student s1) {
        selectedStudent = s1;
        setTitle(s1.name);
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null); 
// Using null layout for manual positioning

        // Set calming background color
        getContentPane().setBackground(new Color(204, 229, 255));
// Light blue background

        // Font for buttons
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // View balance button
        JButton viewBalance = createStyledButton("View Balance", buttonFont, 100, 70);
        viewBalance.addActionListener(e -> viewBalance());
        add(viewBalance);

        // View purchase history button
        JButton viewHistory = createStyledButton("View Purchase History", buttonFont, 100, 110);
        viewHistory.addActionListener(e -> viewHistory());
        add(viewHistory);

        // Set window to be visible at the center
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to create a styled button
    private JButton createStyledButton(String text, Font font, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBounds(x, y, 200, 40);
// Adjusted button size
        button.setBackground(new Color(135, 206, 235)); 
// Light sky blue color
        button.setForeground(Color.WHITE); 
// White text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true)); 
// Blue border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
// Hand cursor on hover
        button.setToolTipText("Click to " + text); 
// Tooltip for user interaction
        return button;
    }

    private void viewBalance() {
        if (selectedStudent != null) {
            JOptionPane.showMessageDialog(this, "The current Balance is: ₹" + selectedStudent.walletBalance);
        } else {
            JOptionPane.showMessageDialog(this, "No student selected.");
        }
    }

    private void viewHistory() {
        if (selectedStudent != null) {
            if (selectedStudent.purchaseHistory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No purchase history available.");
            } else {
                StringBuilder history = new StringBuilder("Purchase History:\n");
                for (String purchase : selectedStudent.purchaseHistory) {
                    history.append(purchase).append("\n");
                }
                JOptionPane.showMessageDialog(this, history.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No student selected.");
        }
    }
}


class WorkerModule extends JFrame {
    private JTextField studentUsernameField;
    private Student selectedStudent;

    public WorkerModule() {
        setTitle("Worker Module");
        setSize(500, 350);  // Adjusted window size
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Set a calming background color
        getContentPane().setBackground(new Color(204, 229, 255));
// Light blue

        // Font for buttons and labels
        Font font = new Font("Segoe UI", Font.BOLD, 14);

        // Label for student username input
        JLabel selectStudentLabel = new JLabel("Enter Student Username:");
        selectStudentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        selectStudentLabel.setBounds(50, 30, 180, 25);
        add(selectStudentLabel);

        // Text field for student username input
        studentUsernameField = new JTextField(15);
        studentUsernameField.setBounds(220, 30, 200, 25);
        studentUsernameField.setFont(font);
        add(studentUsernameField);

        // Select student button
        JButton selectButton = createStyledButton("Select Student", font, 100, 70);
        selectButton.addActionListener(e -> selectStudent());
        add(selectButton);

        // View balance button
        JButton viewBalanceButton = createStyledButton("View Student Balance", font, 100, 110);
        viewBalanceButton.addActionListener(e -> viewBalance());
        add(viewBalanceButton);

        // View purchase history button
        JButton viewHistoryButton = createStyledButton("View Purchase History", font, 100, 150);
        viewHistoryButton.addActionListener(e -> viewPurchaseHistory());
        add(viewHistoryButton);

        // Recharge wallet button
        JButton rechargeWalletButton = createStyledButton("Recharge Wallet", font, 100, 190);
        rechargeWalletButton.addActionListener(e -> rechargeWallet());
        add(rechargeWalletButton);

        // Place order button
        JButton placeOrderButton = createStyledButton("Place Order", font, 100, 230);
        placeOrderButton.addActionListener(e -> placeOrder());
        add(placeOrderButton);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBounds(x, y, 250, 35);  // Adjusted width for better consistency
        button.setBackground(new Color(135, 206, 235)); // Light sky blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("Click to " + text);
        return button;
    }

    private void selectStudent() {
        String username = studentUsernameField.getText().trim();
        selectedStudent = AdminModule.students.get(username);

        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Student not found.");
        } else {
            JOptionPane.showMessageDialog(this, "Student " + username + " selected.");
        }
    }

    private void viewBalance() {
        if (selectedStudent != null) {
            JOptionPane.showMessageDialog(this, "Current wallet balance: " + selectedStudent.walletBalance);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
        }
    }

    private void viewPurchaseHistory() {
        if (selectedStudent != null) {
            if (selectedStudent.purchaseHistory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No purchase history available.");
            } else {
                StringBuilder history = new StringBuilder("Purchase History:\n");
                for (String purchase : selectedStudent.purchaseHistory) {
                    history.append(purchase).append("\n");
                }
                JOptionPane.showMessageDialog(this, history.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
        }
    }

    private void rechargeWallet() {
        if (selectedStudent != null) {
            String amountString = JOptionPane.showInputDialog("Enter amount to recharge:");
            if (amountString != null && !amountString.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountString);
                    selectedStudent.walletBalance += amount;
                    JOptionPane.showMessageDialog(this, "Wallet recharged successfully! Current Balance: " + selectedStudent.walletBalance);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
        }
    }

    private void placeOrder() {
        if (selectedStudent != null) {
            new Cms(selectedStudent); 
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
        }
    }
}



class Cms extends JFrame implements ActionListener {

    JLabel lmenu, l4, l5, l6;
    JTextArea Menu;
    JTextField quantityField, totalField;
    JButton addButton, calculateButton, resetButton, removeItemButton, clearBillButton, checkoutButton;
    JTextArea billArea;
    private Student student;

    JLabel l1, l2, l3;
    HashMap<String, Double> menu;
    double totalAmount = 0;

    JComboBox<String> itemComboBox;

    public Cms(Student student) {
        this.student = student;
        menu = new HashMap<>();
        menu.put("Vada", 8.0);
        menu.put("Bajji", 10.0);
        menu.put("Veg-puff", 15.0);
        menu.put("Egg-puff", 20.0);
        menu.put("Chicken-puff", 25.0);
        menu.put("Milkshake", 35.0);

        // Set up frame properties
        setTitle("Canteen Management System");
        setSize(1400, 1000);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255)); 
// Light blue background
        setVisible(true);

        // Font settings for labels and buttons
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        // Label and field for Quantity
        l1 = new JLabel("Quantity");
        l1.setFont(labelFont);
        l1.setBounds(60, 400, 100, 30);
        add(l1);
        quantityField = new JTextField(20);
        quantityField.setBounds(150, 400, 200, 30);
        add(quantityField);

        // Label and ComboBox for Item selection
        l2 = new JLabel("Select Item");
        l2.setFont(labelFont);
        l2.setBounds(60, 450, 100, 30);
        add(l2);

        // ComboBox for item selection from menu HashMap keys
        itemComboBox = new JComboBox<>(menu.keySet().toArray(new String[0]));
        itemComboBox.setBounds(150, 450, 200, 30);
        add(itemComboBox);

        // Add Item Button
        addButton = new JButton("Add Item");
        addButton.setFont(buttonFont);
        addButton.addActionListener(this);
        addButton.setBounds(150, 500, 200, 30);
        add(addButton);

        // Label for current orders
        l3 = new JLabel("Current Orders");
        l3.setFont(labelFont);
        l3.setBounds(700, 90, 150, 30);
        add(l3);

        // Bill area
        billArea = new JTextArea(10, 40);
        billArea.setBounds(700, 120, 300, 200);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        billArea.setBackground(new Color(255, 255, 255));  // White background for bill area
        billArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(billArea);

        // Remove Item Button
        removeItemButton = new JButton("Remove Item");
        removeItemButton.setFont(buttonFont);
        removeItemButton.addActionListener(this);
        removeItemButton.setBounds(750, 350, 200, 30);
        add(removeItemButton);

        // Calculate Bill Button
        calculateButton = new JButton("Calculate Bill");
        calculateButton.setFont(buttonFont);
        calculateButton.addActionListener(this);
        calculateButton.setBounds(750, 500, 250, 40);
        add(calculateButton);

        // Clear Bill Button
        clearBillButton = new JButton("Clear Bill");
        clearBillButton.setFont(buttonFont);
        clearBillButton.addActionListener(this);
        clearBillButton.setBounds(650, 400, 150, 30);
        add(clearBillButton);

        // Reset Button
        resetButton = new JButton("Reset");
        resetButton.setFont(buttonFont);
        resetButton.addActionListener(this);
        resetButton.setBounds(950, 400, 150, 30);
        add(resetButton);

        // Total Amount Label and Field
        l5 = new JLabel("Total Amount");
        l5.setFont(labelFont);
        l5.setBounds(700, 600, 100, 30);
        add(l5);
        totalField = new JTextField(20);
        totalField.setEditable(false);
        totalField.setBounds(800, 600, 200, 30);
        add(totalField);

        // Menu Label
        lmenu = new JLabel("MENU");
        lmenu.setFont(new Font("Arial", Font.BOLD, 16));
        lmenu.setBounds(60, 50, 250, 30);
        add(lmenu);

        // Menu display in text area
        Menu = new JTextArea("");
        Menu.setEditable(false);
        Menu.setBounds(60, 90, 500, 250);
        Menu.setText("Items\t\t\t\tPrice\n\nVada\t\t\t\t8\n\nBajji\t\t\t\t10\n\nVeg-puff \t\t\t\t15\n\nEgg-puff\t\t\t\t20\n\nChicken-puff\t\t\t25\n\nMilkshake\t\t\t35");
        Menu.setFont(new Font("Monospaced", Font.PLAIN, 12));
        Menu.setBackground(new Color(255, 255, 255)); 
// White background for the menu area
        Menu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(Menu);

        // Checkout Button
        checkoutButton = new JButton("CheckOut");
        checkoutButton.setFont(buttonFont);
        checkoutButton.addActionListener(this);
        checkoutButton.setBounds(1000, 750, 300, 50);
        add(checkoutButton);

        // Set default close operation
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Item")) {
            // Get the selected item from the ComboBox
            String itemName = (String) itemComboBox.getSelectedItem();
            String quantityText = quantityField.getText();

            if (!itemName.equals("Select an Item") && menu.containsKey(itemName)) {
                double price = menu.get(itemName);
                int quantity = Integer.parseInt(quantityText);
                double totalItemPrice = price * quantity;
                totalAmount += totalItemPrice;
                billArea.append(itemName + " x " + quantity + " = " + totalItemPrice + "\n");
            } else {
                JOptionPane.showMessageDialog(Cms.this, "Please select a valid item and enter quantity", "Invalid Order", JOptionPane.QUESTION_MESSAGE);
            }
        } else if (command.equals("Remove Item")) {
            // Similar logic for remove item
            String itemName = (String) itemComboBox.getSelectedItem();
            String quantityText = quantityField.getText();

            if (menu.containsKey(itemName)) {
                double price = menu.get(itemName);
                int quantity = Integer.parseInt(quantityText);
                double totalItemPrice = price * quantity;
                totalAmount -= totalItemPrice;
                billArea.append("Removed: " + itemName + " x " + quantity + "\n");
            } else {
                JOptionPane.showMessageDialog(Cms.this, "Item not found", "Invalid Order", JOptionPane.QUESTION_MESSAGE);
            }
        } else if (command.equals("Calculate Bill")) {
            totalField.setText(String.valueOf(totalAmount));
        } else if (command.equals("Clear Bill")) {
            totalField.setText("");
            billArea.setText("");
            totalAmount = 0;
        } else if (command.equals("Reset")) {
            quantityField.setText("");
            billArea.setText("");
            totalField.setText("");
            totalAmount = 0;
            itemComboBox.setSelectedIndex(0);  // Reset ComboBox to the first item
        } else if (command.equals("CheckOut")) {
            if (totalAmount != 0) {
                if (student.walletBalance >= totalAmount) {
                    student.walletBalance -= totalAmount;
                    student.purchaseHistory.add("Order placed with total: $" + totalAmount);
                    JOptionPane.showMessageDialog(this, "Order placed successfully! Remaining Balance: $" + student.walletBalance);
                    billArea.setText(""); 
                    totalAmount = 0; 
                    totalField.setText(""); 
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!", "Checkout Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No items added to order!", "Checkout Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}


class AdminModuleGUI extends JFrame {
    public AdminModuleGUI() {
        setTitle("Admin Module");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setLocationRelativeTo(null);

        // Set a relaxing background color
        getContentPane().setBackground(new Color(204, 229, 255)); // Light blue

        // Add custom font and button styling
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        JButton addStudentButton = createStyledButton("Add Student", buttonFont);
        addStudentButton.addActionListener(e -> addStudent());

        JButton removeStudentButton = createStyledButton("Remove Student", buttonFont);
        removeStudentButton.addActionListener(e -> removeStudent());

        JButton manageMenuButton = createStyledButton("Manage Menu", buttonFont);
        manageMenuButton.addActionListener(e -> manageMenu());

        JButton RsButton = createStyledButton("Registered Students", buttonFont);
        RsButton.addActionListener(e -> availableStudents());

        JButton viewStudentButton = createStyledButton("View Student Module", buttonFont);
        viewStudentButton.addActionListener(e -> viewStudentModule());

        add(addStudentButton);
        add(removeStudentButton);
        add(manageMenuButton);
        add(viewStudentButton);
        add(RsButton);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(new Dimension(250, 40));
        button.setBackground(new Color(135, 206, 235)); 
// Light sky blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("Click to " + text);
        return button;
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog("Enter new student's Name:");
        String username = JOptionPane.showInputDialog("Enter new student's username:");

        if (username != null && !username.trim().isEmpty()) {
            String password = JOptionPane.showInputDialog("Enter new student's Password:");
            double initialBalance = 0;
            try {
                initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial wallet balance:"));
                AdminModule.students.put(username, new Student(username, password, name, initialBalance));
                JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(AdminModuleGUI.this, "Invalid balance entered.");
            }
        }
    }

    private void removeStudent() {
        String username = JOptionPane.showInputDialog("Enter student's username to remove:");
        if (AdminModule.students.remove(username) != null) {
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student removed successfully!");
        } else {
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student not found.");
        }
    }

    private void manageMenu() {
        StringBuilder menuList = new StringBuilder("Current Menu:\n");
        for (String item : AdminModule.menu.keySet()) {
            menuList.append(item).append(" - ₹").append(AdminModule.menu.get(item)).append("\n");
        }
        JOptionPane.showMessageDialog(AdminModuleGUI.this, menuList.toString(), "Manage Menu", JOptionPane.INFORMATION_MESSAGE);
    }

    private void availableStudents() {
        StringBuilder studentList = new StringBuilder("Registered Students:\n");
        for (String item : AdminModule.students.keySet()) {
            studentList.append(item).append(" - ").append(AdminModule.students.get(item).name).append("\n");
        }
        JOptionPane.showMessageDialog(AdminModuleGUI.this, studentList.toString(), "Registered Students", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewStudentModule() {
        String username = JOptionPane.showInputDialog("Enter student's username to access module:");
        Student selectedStudent = AdminModule.students.get(username);
        if (selectedStudent != null) {
            new StudentModule(selectedStudent);
        } else {
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student not found.");
        }
    }
}


class LoginPage extends JFrame {

    public LoginPage() {
        // Set up frame properties
        setTitle("Login Page");
        setSize(450, 325);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));  
// Light blue background

        // Font settings for labels and buttons
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        userLabel.setBounds(50, 50, 100, 25);
        add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(150, 50, 200, 25);
        userText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userText);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(50, 100, 100, 25);
        add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(150, 100, 200, 25);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passwordText);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setBounds(150, 150, 120, 30);
        loginButton.setBackground(new Color(173, 216, 230)); 
// Light blue background for the button
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            Student selectedStudent = AdminModule.students.get(username);

            if (username.equals("worker") && password.equals("worker")) {
                new WorkerModule();
            } else if (username.equals("admin") && password.equals("admin")) {
                new AdminModuleGUI();
            } else if (selectedStudent != null && username.equals(selectedStudent.username) && password.equals(selectedStudent.password)) {
                new StudentModule(selectedStudent);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(loginButton);

        // Center the window on screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

public class CanteenManagementSystem {
    public static void main(String[] args) {
        new LoginPage();
    }
}
