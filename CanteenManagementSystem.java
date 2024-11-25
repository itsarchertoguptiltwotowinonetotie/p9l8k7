package db_cms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.util.Date;

class Student {
    String username, password, name;
    double walletBalance;
    ArrayList<String> purchaseHistory;

    public Student(String username, String password, String name, double initialBalance) {
        this.name = name;
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
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");

            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("select * from students");

            while (rs.next()) {
            	students.put(rs.getString(1),new Student(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(1)));
            }

        } catch (Exception e1) {
            System.out.println(e1);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");

            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("select * from menuitems");

            while (rs.next()) {
            	menu.put(rs.getString(1), rs.getDouble(2));
            }

        } catch (Exception e1) {
            System.out.println(e1);
        }
    }
}

class StudentModule extends JFrame {
    private String selectedStudent;

    public StudentModule(String s1) {
        selectedStudent = s1;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM students WHERE username = ?");
            preparedStatement.setString(1, selectedStudent);
            ResultSet rs1 = preparedStatement.executeQuery();
            while (rs1.next()) {
                String st = (rs1.getString(3));
                setTitle(st);
            }
        } catch (Exception e1) {
            System.out.println(e1);
            JOptionPane.showMessageDialog(this, "Invalid Student username");
        }
        
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null); 

        getContentPane().setBackground(new Color(204, 229, 255)); 
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        JButton viewBalance = createStyledButton("View Balance", buttonFont, 100, 70);
        viewBalance.addActionListener(e -> viewBalance());
        add(viewBalance);

        JButton viewHistory = createStyledButton("View Purchase History", buttonFont, 100, 110);
        viewHistory.addActionListener(e -> viewHistory());
        add(viewHistory);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBounds(x, y, 200, 40);
        button.setBackground(new Color(135, 206, 235)); 
        button.setForeground(Color.WHITE); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        button.setToolTipText("Click to " + text); 
        return button;
    }

    private void viewBalance() {
        if (selectedStudent != null) {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                 PreparedStatement preparedStatement = con.prepareStatement("SELECT balance FROM students WHERE username = ?")) {
                 
                preparedStatement.setString(1, selectedStudent);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    JOptionPane.showMessageDialog(this, "Current Balance is ₹" + balance);
                }
            } catch (Exception e1) {
                System.out.println(e1);
            }  
        } else {
            JOptionPane.showMessageDialog(this, "No student selected.");
        }
    }

    private void viewHistory() {
        if (selectedStudent != null) {
            StringBuilder studentList = new StringBuilder("ITEM \t PRICE \t QUANTITY \t AMOUNT \t TIME\n");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                PreparedStatement preparedStatement1 = con.prepareStatement("SELECT * FROM purchasehistory WHERE adm_no = ?");
                preparedStatement1.setString(1, selectedStudent);
                ResultSet rs = preparedStatement1.executeQuery();
                while (rs.next()) {
                    studentList.append(rs.getString(2)).append("       ").append(rs.getInt(3)).append("               ")
                        .append(rs.getInt(4)).append("                ").append(rs.getInt(5)).append("           ")
                        .append(rs.getTime(6)).append("\n");
                }
                JOptionPane.showMessageDialog(this, studentList);
            } catch (Exception e1) {
                System.out.println(e1);
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
        setSize(500, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(204, 229, 255));

        Font font = new Font("Segoe UI", Font.BOLD, 14);

        JLabel selectStudentLabel = new JLabel("Enter Student Username:");
        selectStudentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        selectStudentLabel.setBounds(50, 30, 180, 25);
        add(selectStudentLabel);

        studentUsernameField = new JTextField(15);
        studentUsernameField.setBounds(220, 30, 200, 25);
        studentUsernameField.setFont(font);
        add(studentUsernameField);

        JButton selectButton = createStyledButton("Select Student", font, 100, 70);
        selectButton.addActionListener(e -> selectStudent());
        add(selectButton);

        JButton viewBalanceButton = createStyledButton("View Student Balance", font, 100, 110);
        viewBalanceButton.addActionListener(e -> viewBalance());
        add(viewBalanceButton);

        JButton viewHistoryButton = createStyledButton("View Purchase History", font, 100, 150);
        viewHistoryButton.addActionListener(e -> viewPurchaseHistory());
        add(viewHistoryButton);

        JButton rechargeWalletButton = createStyledButton("Recharge Wallet", font, 100, 190);
        rechargeWalletButton.addActionListener(e -> rechargeWallet());
        add(rechargeWalletButton);

        JButton placeOrderButton = createStyledButton("Place Order", font, 100, 230);
        placeOrderButton.addActionListener(e -> placeOrder());
        add(placeOrderButton);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBounds(x, y, 250, 35);
        button.setBackground(new Color(135, 206, 235)); // Light blue
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
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM students WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs1 = preparedStatement.executeQuery();
            if (rs1.next()) {
                JOptionPane.showMessageDialog(this, "Student " + username + " selected.");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (Exception e1) {
            System.out.println(e1);
            JOptionPane.showMessageDialog(this, "Invalid Student username");
        }
    }

    private void viewBalance() {
        if (selectedStudent != null) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                String sql = "SELECT balance FROM students WHERE username = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, selectedStudent.username);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    JOptionPane.showMessageDialog(this, "Current Balance is ₹" + balance);
                }
            } catch (Exception e1) {
                System.out.println(e1);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No student selected.");
        }
    }

    private void viewPurchaseHistory() {
        if (selectedStudent != null) {
            StringBuilder studentList = new StringBuilder("ITEM \t PRICE \t QUANTITY \t AMOUNT \t TIME\n");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                String sql = "SELECT * FROM purchasehistory WHERE adm_no = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, selectedStudent.username);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    studentList.append(rs.getString(2)).append("       ").append(rs.getInt(3)).append("               ")
                            .append(rs.getInt(4)).append("                ").append(rs.getInt(5)).append("           ")
                            .append(rs.getTime(6)).append("\n");
                }
                JOptionPane.showMessageDialog(this, studentList);
            } catch (Exception e1) {
                System.out.println(e1);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No student selected.");
        }
    }

    private void rechargeWallet() {
    	double balance;
        if (selectedStudent != null) {
            String amountString = JOptionPane.showInputDialog("Enter amount to recharge:");
            if (amountString != null && !amountString.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountString);
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                        String sql = "SELECT balance FROM students WHERE username = ?";
                        PreparedStatement preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setString(1, selectedStudent.username);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            balance = rs.getDouble("balance");
                            selectedStudent.walletBalance = balance + amount;
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                         PreparedStatement preparedStatement = con.prepareStatement("UPDATE students SET balance = ? WHERE username = ?")) {
                        preparedStatement.setDouble(1, selectedStudent.walletBalance);
                        preparedStatement.setString(2, selectedStudent.username);
                        int rowsAffected = preparedStatement.executeUpdate();
                    } catch (SQLException e5) {
						// TODO Auto-generated catch block
						e5.printStackTrace();
					}
                    JOptionPane.showMessageDialog(this, "Wallet recharged successfully! Current Balance: ₹" + selectedStudent.walletBalance);
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
    JLabel lmenu, l1, l2, l3;
    JTextArea Menu;
    JTextField quantityField, totalField;
    JButton addButton, calculateButton, resetButton, removeItemButton, clearBillButton, checkoutButton;
    JTextArea billArea;
    private Student student;
    private HashMap<String, Double> menu;  // Keep the menu in a HashMap

    double totalAmount = 0;
    JComboBox<String> itemComboBox;

    public Cms(Student student) {
        this.student = student;
        menu = new HashMap<>();
        loadMenuFromDatabase();  // Load menu from the database

        // Set up frame properties
        setTitle("Canteen Management System");
        setSize(1400, 1000);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));
        setVisible(true);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        l1 = new JLabel("Quantity");
        l1.setFont(labelFont);
        l1.setBounds(60, 400, 100, 30);
        add(l1);
        quantityField = new JTextField(20);
        quantityField.setBounds(150, 400, 200, 30);
        add(quantityField);

        l2 = new JLabel("Select Item");
        l2.setFont(labelFont);
        l2.setBounds(60, 450, 100, 30);
        add(l2);

        itemComboBox = new JComboBox<>(menu.keySet().toArray(new String[0]));
        itemComboBox.setBounds(150, 450, 200, 30);
        add(itemComboBox);

        addButton = new JButton("Add Item");
        addButton.setFont(buttonFont);
        addButton.addActionListener(this);
        addButton.setBounds(150, 500, 200, 30);
        add(addButton);

        l3 = new JLabel("Current Orders");
        l3.setFont(labelFont);
        l3.setBounds(700, 90, 150, 30);
        add(l3);

        billArea = new JTextArea(10, 40);
        billArea.setBounds(700, 120, 300, 200);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        billArea.setBackground(new Color(255, 255, 255));
        billArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(billArea);

        removeItemButton = new JButton("Remove Item");
        removeItemButton.setFont(buttonFont);
        removeItemButton.addActionListener(this);
        removeItemButton.setBounds(750, 350, 200, 30);
        add(removeItemButton);

        calculateButton = new JButton("Calculate Bill");
        calculateButton.setFont(buttonFont);
        calculateButton.addActionListener(this);
        calculateButton.setBounds(750, 500, 250, 40);
        add(calculateButton);

        clearBillButton = new JButton("Clear Bill");
        clearBillButton.setFont(buttonFont);
        clearBillButton.addActionListener(this);
        clearBillButton.setBounds(650, 400, 150, 30);
        add(clearBillButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(buttonFont);
        resetButton.addActionListener(this);
        resetButton.setBounds(950, 400, 150, 30);
        add(resetButton);

        checkoutButton = new JButton("CheckOut");
        checkoutButton.setFont(buttonFont);
        checkoutButton.addActionListener(this);
        checkoutButton.setBounds(1000, 750, 300, 50);
        add(checkoutButton);
    }

    private void loadMenuFromDatabase() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT items, price FROM menuitems");
            while (rs.next()) {
                String itemName = rs.getString("items");
                double itemPrice = rs.getDouble("price");
                menu.put(itemName, itemPrice);
            }
            itemComboBox.setModel(new DefaultComboBoxModel<>(menu.keySet().toArray(new String[0]))); // Update the JComboBox
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Item")) {
            String itemName = (String) itemComboBox.getSelectedItem();
            String quantityText = quantityField.getText();

            if (itemName != null && !quantityText.isEmpty()) {
                double price = menu.get(itemName);
                int quantity = Integer.parseInt(quantityText);

                double totalItemPrice = price * quantity;
                totalAmount += totalItemPrice;
                billArea.append(itemName + " x " + quantity + " = ₹" + totalItemPrice + "\n");

                // Add purchase to database
                addToPurchaseHistory(student.username, itemName, price, quantity, totalItemPrice);
            }
        } else if (command.equals("Calculate Bill")) {
            System.out.println("Total Amount: " + totalAmount);
        } else if (command.equals("Clear Bill")) {
            billArea.setText("");
            totalAmount = 0;
        } else if (command.equals("Reset")) {
            quantityField.setText("");
            billArea.setText("");
            totalAmount = 0;
            itemComboBox.setSelectedIndex(0);
        }
    }

    private void addToPurchaseHistory(String studentUsername, String itemName, double price, int quantity, double totalItemPrice) {
        // Implement the logic to add purchase history to the database
        // You can create a table named `purchasehistory` to store the information
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
            String sql = "INSERT INTO purchasehistory (adm_no, item, price, quantity, amount, time) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, studentUsername);
                ps.setString(2, itemName);
                ps.setDouble(3, price);
                ps.setInt(4, quantity);
                ps.setDouble(5, totalItemPrice);
                ps.setTime(6, new Time(System.currentTimeMillis()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class ManageMenu extends JFrame implements ActionListener {
    private JComboBox<String> itemComboBox;
    private JTextField addItemField;
    private JTextField addPriceField;
    private JTextField updatePriceField;
    private JTextField updateStockField;

    public ManageMenu(HashMap<String, Double> menu) {
        setTitle("Manage Menu");
        setSize(425, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        
        // Initialize components
        JLabel selectItemLabel = new JLabel("Select Item to Update:");
        selectItemLabel.setBounds(20, 20, 160, 25);
        add(selectItemLabel);
        
        itemComboBox = new JComboBox<>(menu.keySet().toArray(new String[0]));
        itemComboBox.setBounds(180, 20, 200, 25);
        add(itemComboBox);
        
        JLabel updatePriceLabel = new JLabel("Update Price:");
        updatePriceLabel.setBounds(20, 60, 120, 25);
        add(updatePriceLabel);
        
        updatePriceField = new JTextField();
        updatePriceField.setBounds(180, 60, 200, 25);
        add(updatePriceField);
        
        JLabel updateStockLabel = new JLabel("Update Stock:");
        updateStockLabel.setBounds(20, 100, 120, 25);
        add(updateStockLabel);
        
        updateStockField = new JTextField();
        updateStockField.setBounds(180, 100, 200, 25);
        add(updateStockField);
        
        JButton updateButton = new JButton("Update Item");
        updateButton.setBounds(20, 140, 150, 30);
        updateButton.addActionListener(e -> updateItem());
        add(updateButton);
        
        JLabel addItemLabel = new JLabel("Add New Item:");
        addItemLabel.setBounds(20, 180, 150, 25);
        add(addItemLabel);
        
        addItemField = new JTextField();
        addItemField.setBounds(150, 180, 100, 25);
        add(addItemField);
        
        JLabel addPriceLabel = new JLabel("Price:");
        addPriceLabel.setBounds(290, 180, 50, 25);
        add(addPriceLabel);
        
        addPriceField = new JTextField();
        addPriceField.setBounds(340, 180, 50, 25);
        add(addPriceField);
        
        JButton addButton = new JButton("Add Item");
        addButton.setBounds(20, 230, 150, 30);
        addButton.addActionListener(this);
        add(addButton);
        
        JButton removeButton = new JButton("Remove Item");
        removeButton.setBounds(200, 230, 150, 30);
        removeButton.addActionListener(this);
        add(removeButton);
        
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void updateItem() {
        String selectedItem = (String) itemComboBox.getSelectedItem();
        String newPrice = updatePriceField.getText();
        String newStock = updateStockField.getText();
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146")) {
            if (!newPrice.isEmpty()) {
                double price = Double.parseDouble(newPrice);
                try (PreparedStatement ps = con.prepareStatement("UPDATE menuitems SET price = ? WHERE items = ?")) {
                    ps.setDouble(1, price);
                    ps.setString(2, selectedItem);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Price updated for: " + selectedItem + " - ₹" + price);
                }
            }
            if (!newStock.isEmpty()) {
                int stock = Integer.parseInt(newStock);
                try (PreparedStatement ps = con.prepareStatement("UPDATE menuitems SET stock = ? WHERE items = ?")) {
                    ps.setInt(1, stock);
                    ps.setString(2, selectedItem);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Stock updated for: " + selectedItem + " - " + stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating item");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for price or stock");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String newItemText = addItemField.getText();
        String newItemPriceText = addPriceField.getText();
        
        if (command.equals("Add Item")) {
            if (!newItemText.isEmpty() && !newItemPriceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(newItemPriceText);
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                         PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO menuitems (items, price, stock) VALUES (?, ?, ?)")) {
                        preparedStatement.setString(1, newItemText);
                        preparedStatement.setDouble(2, price);
                        preparedStatement.setInt(3, 0); // Set initial stock to 0
                        preparedStatement.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(this, "Item added: " + newItemText + " - ₹" + price);
                    itemComboBox.addItem(newItemText); // Refresh item list                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price entered!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding item to database");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter an item and a price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("Remove Item")) {
            String selectedItem = (String) itemComboBox.getSelectedItem();
            if (selectedItem != null) {
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                     PreparedStatement pstmt = con.prepareStatement("DELETE FROM menuitems WHERE items = ?")) {
                    pstmt.setString(1, selectedItem);
                    pstmt.executeUpdate();
                } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                JOptionPane.showMessageDialog(this, "Item removed: " + selectedItem);
                itemComboBox.removeItem(selectedItem); // Refresh item list
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

        getContentPane().setBackground(new Color(204, 229, 255)); // Light blue
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
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                         PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO students (username,password,name,balance) VALUES (?, ?, ?, ?)")) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);
                        preparedStatement.setString(3, name);
                        preparedStatement.setDouble(4, initialBalance);
                        preparedStatement.executeUpdate();
                    }
                } catch (Exception e3) {
                    System.out.println(e3);
                }
                JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(AdminModuleGUI.this, "Invalid balance entered.");
            }
        }
    }

    private void removeStudent() {
        String username = JOptionPane.showInputDialog("Enter student's username to remove:");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE username = ?")) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student removed successfully!");
        } catch (SQLException e2) {
            System.out.println(e2);
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student not found.");
        }
    }

    private void manageMenu() {
        new ManageMenu(AdminModule.menu);
    }

    private void availableStudents() {
        StringBuilder studentList = new StringBuilder("Registered Students:\n");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");

            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("select * from students");

            while (rs.next()) {
                studentList.append(rs.getString(1)).append(" - ").append(rs.getString(3)).append("\n");
            }

        } catch (Exception e1) {
            System.out.println(e1);
        }
        JOptionPane.showMessageDialog(AdminModuleGUI.this, studentList.toString(), "Registered Students", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewStudentModule() {
        String username = JOptionPane.showInputDialog("Enter student's username to access module:");
        try {
            new StudentModule(username);
        } catch (Exception e3) {
            System.out.println(e3);
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student not found.");
        }
    }
}

class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login Page");
        setSize(450, 325);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        userLabel.setBounds(50, 50, 100, 25);
        add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(150, 50, 200, 25);
        userText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(50, 100, 100, 25);
        add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(150, 100, 200, 25);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setBounds(150, 150, 120, 30);
        loginButton.setBackground(new Color(173, 216, 230));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            Student selectedStudent = AdminModule.students.get(username);
            if (username.equals("worker") && password.equals("worker")) {
                new WorkerModule();
            } else if (username.equals("admin") && password.equals("admin")) {
                new AdminModuleGUI();
            } else {
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1", "root", "humaira146");
                    PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM students WHERE username = ?");
                    preparedStatement.setString(1, username);
                    ResultSet rs1 = preparedStatement.executeQuery();
                    if (rs1.next() && password.equals(rs1.getString(2))) {
                        new StudentModule(username);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                    JOptionPane.showMessageDialog(this, "Invalid Student username");
                }
            }
        });
        add(loginButton);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

public class CanteenManagementSystem {
    public static void main(String[] args) {
        new LoginPage();
    }
}
