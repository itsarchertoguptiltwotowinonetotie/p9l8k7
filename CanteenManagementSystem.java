import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

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

        setTitle("vs");
        setSize(1400, 1000);
        setLayout(null);
        setVisible(true);

        l1 = new JLabel("Quantity");
        l1.setBounds(60, 400, 70, 30);
        add(l1);
        quantityField = new JTextField(20);
        quantityField.setBounds(150, 400, 200, 30);
        add(quantityField);

        // ComboBox for item selection
        l2 = new JLabel("Select Item");
        l2.setBounds(60, 450, 100, 30);
        add(l2);

        // Create the combo box with item names from the menu HashMap keys
        itemComboBox = new JComboBox<>(menu.keySet().toArray(new String[0]));
        itemComboBox.setBounds(150, 450, 200, 30);
        add(itemComboBox);

        addButton = new JButton("Add Item");
        addButton.addActionListener(this);
        addButton.setBounds(150, 500, 200, 30);
        add(addButton);

        l3 = new JLabel("Current Orders");
        l3.setBounds(700, 90, 100, 30);
        add(l3);

        billArea = new JTextArea(10, 40);
        billArea.setBounds(700, 120, 300, 200);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(billArea);

        removeItemButton = new JButton("Remove Item");
        removeItemButton.addActionListener(this);
        removeItemButton.setBounds(750, 350, 200, 30);
        add(removeItemButton);

        calculateButton = new JButton("Calculate Bill");
        calculateButton.addActionListener(this);
        calculateButton.setBounds(750, 500, 250, 40);
        add(calculateButton);

        clearBillButton = new JButton("Clear Bill");
        clearBillButton.addActionListener(this);
        clearBillButton.setBounds(650, 400, 150, 30);
        add(clearBillButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setBounds(950, 400, 150, 30);
        add(resetButton);

        l5 = new JLabel("Total Amount");
        l5.setBounds(700, 600, 70, 30);
        add(l5);
        totalField = new JTextField(20);
        totalField.setEditable(false);
        totalField.setBounds(800, 600, 200, 30);
        add(totalField);

        lmenu = new JLabel("MENU");
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
