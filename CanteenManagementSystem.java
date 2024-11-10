import java.awt.*;
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
        setLayout(new FlowLayout());

        JButton viewBalance = new JButton("View Balance");
        viewBalance.addActionListener(e -> viewBalance());
        add(viewBalance);

        JButton viewHistory = new JButton("View Purchase History");
        viewHistory.addActionListener(e -> viewHistory());
        add(viewHistory);

        setVisible(true);
    }

    private void viewBalance() {
        if (selectedStudent != null) {
            JOptionPane.showMessageDialog(this, "The current Balance is: " + selectedStudent.walletBalance);
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
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel selectStudentLabel = new JLabel("Enter Student Username:");
        selectStudentLabel.setBounds(50, 30, 150, 25);
        add(selectStudentLabel);

        studentUsernameField = new JTextField(15);
        studentUsernameField.setBounds(200, 30, 150, 25);
        add(studentUsernameField);

        JButton selectButton = new JButton("Select Student");
        selectButton.setBounds(125, 70, 150, 25);
        selectButton.addActionListener(e -> selectStudent());
        add(selectButton);

        JButton viewBalanceButton = new JButton("View Student Balance");
        viewBalanceButton.setBounds(100, 110, 200, 25);
        viewBalanceButton.addActionListener(e -> viewBalance());
        add(viewBalanceButton);

        JButton viewHistoryButton = new JButton("View Purchase History");
        viewHistoryButton.setBounds(100, 150, 200, 25);
        viewHistoryButton.addActionListener(e -> viewPurchaseHistory());
        add(viewHistoryButton);

        JButton rechargeWalletButton = new JButton("Recharge Wallet");
        rechargeWalletButton.setBounds(100, 190, 200, 25);
        rechargeWalletButton.addActionListener(e -> rechargeWallet());
        add(rechargeWalletButton);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setBounds(100, 230, 200, 25);
        placeOrderButton.addActionListener(e -> placeOrder());
        add(placeOrderButton);

        setLocationRelativeTo(null);
        setVisible(true);
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

    JLabel lmenu,l4,l5,l6;
    JTextArea Menu;
    JTextField itemNameField, quantityField, totalField, newItemField;
    JButton addButton, calculateButton, resetButton, removeItemButton, clearBillButton,checkoutButton;
    JTextArea billArea;
    private Student student;

    JLabel l1,l2,l3;
    HashMap<String, Double> menu;
    double totalAmount = 0;

    public Cms(Student student) {
        this.student = student;
        menu = new HashMap<>();
        menu.put("Vada", 8.0);
        menu.put("Bajji", 10.0);
        menu.put("Veg-puff", 15.0);
        menu.put("Egg-puff", 20.0);
        menu.put("Chicken-puff", 25.0);
        menu.put("Milkshake", 35.0);


        setTitle("vs");
        setSize(1400,1000);
        setLayout(null);
        setVisible(true);

        l1=new JLabel("Item Name");
        l1.setBounds(60, 400, 70, 30);
        add(l1);
        itemNameField = new JTextField(20);
        itemNameField.setBounds(150, 400,200, 30);
        add(itemNameField);

        l2=new JLabel("Quantity");
        l2.setBounds(60, 450, 70, 30);
        add(l2);
        quantityField = new JTextField(20);
        quantityField.setBounds(150, 450,200, 30);
        add(quantityField);

        addButton = new JButton("Add Item");
        addButton.addActionListener(this);
        addButton.setBounds(150, 500,200, 30);
        add(addButton);

        l3=new JLabel("Current Orders");
        l3.setBounds(700, 90, 100, 30);
        add(l3);

        billArea = new JTextArea(10, 40);
        billArea.setBounds(700, 120 , 300, 200);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(billArea);

        removeItemButton = new JButton("Remove Item");
        removeItemButton.addActionListener(this);
        removeItemButton.setBounds(750, 350,200, 30);
        add(removeItemButton);


        calculateButton = new JButton("Calculate Bill");
        calculateButton.addActionListener(this);
        calculateButton.setBounds(750, 500 , 250, 40);
        add(calculateButton);

        clearBillButton = new JButton("Clear Bill");
        clearBillButton.addActionListener(this);
        clearBillButton.setBounds(650, 400, 150, 30);
        add(clearBillButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setBounds(950, 400, 150, 30);
        add(resetButton);


        l5=new JLabel("Total Amount");
        l5.setBounds(700, 600, 70, 30);
        add(l5);
        totalField = new JTextField(20);
        totalField.setEditable(false);
        totalField.setBounds(800, 600,200, 30);
        add(totalField);


        lmenu=new JLabel("MENU");
        lmenu.setBounds(60, 50, 250, 30);
        add(lmenu);
        Menu = new JTextArea("");
        Menu.setEditable(false);
        Menu.setBounds(60, 90, 500, 250);
        Menu.setText("Items\t\t\t\tPrice\n\nVada\t\t\t\t8\n\nBajji\t\t\t\t10\n\nVeg-puff \t\t\t\t15\n\nEgg-puff\t\t\t\t20\n\nChicken-puff\t\t\t25\n\nMilkshake\t\t\t35");
        add(Menu);

        checkoutButton = new JButton("CheckOut");
        checkoutButton.addActionListener(this);
        checkoutButton.setBounds(1000, 750, 300, 50);
        add(checkoutButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Item")) {
            String itemName = itemNameField.getText();
            String quantityText = quantityField.getText();
            if (menu.containsKey(itemName)) {
                double price = menu.get(itemName);
                int quantity = Integer.parseInt(quantityText);
                double totalItemPrice = price * quantity;
                totalAmount += totalItemPrice;
                billArea.append(itemName + " x " + quantity + " = " + totalItemPrice + "\n");
            } else {
                JOptionPane.showMessageDialog(Cms.this, "Item not found", "Invalid Order", JOptionPane.QUESTION_MESSAGE);
            }
        } else if (command.equals("Remove Item")) {
            String itemName = itemNameField.getText();
            String quantityText = quantityField.getText();
            if (menu.containsKey(itemName)) {
                double price = menu.get(itemName);
                int quantity = Integer.parseInt(quantityText);
                double totalItemPrice = price * quantity;
                totalAmount -= totalItemPrice;
                billArea.append("Removed: " + itemName + " x " + quantity + "\n");
            } else {
                itemNameField.setText("Item not found!");
            }
        } else if (command.equals("Calculate Bill")) {
            totalField.setText(String.valueOf(totalAmount));
        } else if (command.equals("Clear Bill")) {
            totalField.setText("");
            billArea.setText("");
            totalAmount = 0;
        } else if (command.equals("Reset")) {
            itemNameField.setText("");
            quantityField.setText("");
            totalField.setText("");
            billArea.setText("");
            totalAmount = 0;
        } 
        else if (command.equals("CheckOut")) {
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
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(e -> addStudent());

        JButton removeStudentButton = new JButton("Remove Student");
        removeStudentButton.addActionListener(e -> removeStudent());

        JButton manageMenuButton = new JButton("Manage Menu");
        manageMenuButton.addActionListener(e -> manageMenu());

        JButton RsButton = new JButton("Registered Students");
        RsButton.addActionListener(e -> availableStudents());

        JButton viewStudentButton = new JButton("View Student Module");
        viewStudentButton.addActionListener(e -> viewStudentModule());

        add(addStudentButton);
        add(removeStudentButton);
        add(manageMenuButton);
        add(viewStudentButton);
        add(RsButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void customizeButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40)); 
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog("Enter new student's Name:");
        String username = JOptionPane.showInputDialog("Enter new student's username:");

        if (username != null && !username.trim().isEmpty()) {
            String password = JOptionPane.showInputDialog("Enter new student's Password:");
            double initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial wallet balance:"));
            AdminModule.students.put(username, new Student(username, password,name, initialBalance));
            JOptionPane.showMessageDialog(AdminModuleGUI.this, "Student added successfully!");
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

    public static void main(String[] args) {
        new AdminModuleGUI();
    }
}



class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login Page");
        setSize(450, 325);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        JTextField userText = new JTextField();
        userText.setBounds(150, 50, 150, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 25);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(150, 100, 150, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 150, 120, 25);

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
                JOptionPane.showMessageDialog(null, "Invalid credentials");
            }
        });

        add(userLabel);
        add(userText);
        add(passwordLabel);
        add(passwordText);
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
