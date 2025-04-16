import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

public class TextileManagement {
    private static final String DB_URL = "jdbc:oracle:thin:@192.168.31.204:1521:xe";// Change this as per your database settings
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "root";
    
    private static Connection conn = null;
    private static JFrame loginFrame, adminFrame, employeeFrame, customerFrame, registerFrame;
    
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            showLoginFrame();  // Removed createTables() call
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showLoginFrame() {
        loginFrame = new JFrame("Textile Management System - Login");
        loginFrame.setSize(500, 400);
        loginFrame.setLocationRelativeTo(null); // Center on screen
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180)); // Steel blue
        JLabel titleLabel = new JLabel("Textile Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        formPanel.setBackground(new Color(240, 248, 255));

        JLabel userLabel = new JLabel("user_name:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(200, 30));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(200, 30));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Style buttons
        styleButton(loginButton);
        styleButton(registerButton);

        // Add components
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners remain the same
        loginButton.addActionListener(e -> {
            String user_name = userField.getText();
            String password = new String(passField.getPassword());
            validateLogin(user_name, password);
        });

        registerButton.addActionListener(e -> showRegisterFrame());

        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }

    private static void validateLogin(String user_name, String password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT role FROM User_ WHERE user_name = ? AND password = ?");
            pstmt.setString(1, user_name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                switch (role) {
                    case "admin": showAdminFrame(); break;
                    case "employee": showEmployeeFrame(); break;
                    case "customer": showCustomerFrame(user_name); break; // Pass user_name
                }
                loginFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAdminFrame() {
        adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(800, 600);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(240, 245, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton manageUsers = createDashboardButton("Manage Users", "👥");
        JButton manageProducts = createDashboardButton("Manage Products", "📦");
        JButton viewReports = createDashboardButton("View Reports", "📊");
        JButton logout = createDashboardButton("Logout", "🚪");

        buttonPanel.add(manageUsers);
        buttonPanel.add(manageProducts);
        buttonPanel.add(viewReports);
        buttonPanel.add(logout);

        // Action listeners
        manageUsers.addActionListener(e -> manageUsers());
        manageProducts.addActionListener(e -> manageProducts());
        viewReports.addActionListener(e -> viewReports());
        logout.addActionListener(e -> {
            adminFrame.dispose();
            showLoginFrame();
        });

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        adminFrame.add(mainPanel);
        adminFrame.setVisible(true);
    }

    private static void showEmployeeFrame() {
        employeeFrame = new JFrame("Employee Dashboard");
        employeeFrame.setSize(800, 600);
        employeeFrame.setLocationRelativeTo(null);
        employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Employee Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(240, 245, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton manageInventory = createDashboardButton("Manage Inventory", "📦");
        JButton processOrders = createDashboardButton("Process Orders", "📝");
        JButton viewStats = createDashboardButton("View Statistics", "📊");
        JButton logout = createDashboardButton("Logout", "🚪");

        buttonPanel.add(manageInventory);
        buttonPanel.add(processOrders);
        buttonPanel.add(viewStats);
        buttonPanel.add(logout);

        // Action listeners
        manageInventory.addActionListener(e -> manageInventory());
        processOrders.addActionListener(e -> processOrders());
        viewStats.addActionListener(e -> viewEmployeeStats());
        logout.addActionListener(e -> {
            employeeFrame.dispose();
            showLoginFrame();
        });

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        employeeFrame.add(mainPanel);
        employeeFrame.setVisible(true);
    }

    private static void manageInventory() {
        JFrame frame = new JFrame("Manage Inventory");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Inventory Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Table Panel
        String[] columns = {"ID", "Name", "Price", "Current Stock", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Update Stock Panel
        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        updatePanel.setBackground(new Color(240, 245, 255));

        JTextField productIdField = new JTextField(10);
        JTextField stockField = new JTextField(10);
        JButton updateButton = new JButton("Update Stock");
        styleButton(updateButton);

        updatePanel.add(new JLabel("Product ID:"));
        updatePanel.add(productIdField);
        updatePanel.add(new JLabel("New Stock:"));
        updatePanel.add(stockField);
        updatePanel.add(updateButton);

        updateButton.addActionListener(e -> {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int newStock = Integer.parseInt(stockField.getText());
                
                if (newStock < 0) {
                    throw new IllegalArgumentException("Stock cannot be negative");
                }

                PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE Products SET stock = ? WHERE product_id = ?");
                pstmt.setInt(1, newStock);
                pstmt.setInt(2, productId);
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    refreshInventoryTable(model);
                    JOptionPane.showMessageDialog(frame, "Stock updated successfully!");
                    productIdField.setText("");
                    stockField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Product not found!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error updating stock!");
                ex.printStackTrace();
            }
        });

        refreshInventoryTable(model);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(updatePanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void processOrders() {
        JFrame frame = new JFrame("Process Orders");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Order Processing");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Table Panel
        String[] columns = {"Order ID", "Customer", "Product", "Quantity", "Order Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Process Panel
        JPanel processPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        processPanel.setBackground(new Color(240, 245, 255));

        JButton processButton = new JButton("Process Selected Order");
        JButton completeButton = new JButton("Mark as Completed");
        styleButton(processButton);
        styleButton(completeButton);

        processPanel.add(processButton);
        processPanel.add(completeButton);

        // Action Listeners
        processButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int orderId = (Integer) table.getValueAt(row, 0);
                String currentStatus = (String) table.getValueAt(row, 5);
                
                if ("Pending".equals(currentStatus)) {
                    updateOrderStatus(orderId, "Processing", model);
                    refreshOrdersTable(model);
                } else {
                    JOptionPane.showMessageDialog(frame, "Order is already being processed or completed!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an order to process");
            }
        });

        completeButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int orderId = (Integer) table.getValueAt(row, 0);
                String currentStatus = (String) table.getValueAt(row, 5);
                
                if ("Processing".equals(currentStatus)) {
                    updateOrderStatus(orderId, "Completed", model);
                    refreshOrdersTable(model);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "Order must be in Processing status to be marked as Completed!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an order to complete");
            }
        });

        refreshOrdersTable(model);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(processPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void viewEmployeeStats() {
        JFrame frame = new JFrame("Employee Statistics");
        frame.setSize(1200, 800); // Increased size
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Employee Statistics Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(240, 245, 255));

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statsPanel.setBackground(new Color(240, 245, 255));

        try {
            // Total Orders
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as total FROM Orders");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Total Orders", rs.getInt("total"));
            }

            // Pending Orders
            pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as pending FROM Orders WHERE status = 'Pending'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Pending Orders", rs.getInt("pending"));
            }

            // Processing Orders
            pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as processing FROM Orders WHERE status = 'Processing'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Processing Orders", rs.getInt("processing"));
            }

            // Completed Orders
            pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as completed FROM Orders WHERE status = 'Completed'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Completed Orders", rs.getInt("completed"));
            }

            // Tables Panel - holds both revenue and orders tables
            JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            tablesPanel.setBackground(new Color(240, 245, 255));

            // Revenue Panel
            JPanel revenuePanel = new JPanel(new BorderLayout(10, 10));
            revenuePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Revenue Statistics",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
            ));
            revenuePanel.setBackground(new Color(240, 245, 255));

            // Revenue Stats Panel
            JPanel revenueStatsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            revenueStatsPanel.setBackground(new Color(240, 245, 255));
            revenueStatsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Get total revenue using function
            CallableStatement cs = conn.prepareCall("{? = call calculate_total_revenue()}");
            cs.registerOutParameter(1, Types.NUMERIC);
            cs.execute();
            double totalRevenue = cs.getDouble(1);
            addStatCard(revenueStatsPanel, "Total Revenue", String.format("₹%.2f", totalRevenue));

            // Get current month revenue
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = new Date(cal.getTimeInMillis());
            Date endDate = new Date(System.currentTimeMillis());

            cs = conn.prepareCall("{call get_revenue_by_daterange(?, ?, ?)}");
            cs.setDate(1, startDate);
            cs.setDate(2, endDate);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.execute();
            double monthlyRevenue = cs.getDouble(3);
            addStatCard(revenueStatsPanel, "This Month's Revenue", String.format("₹%.2f", monthlyRevenue));

            revenuePanel.add(revenueStatsPanel, BorderLayout.NORTH);

            // Recent Orders Panel
            JPanel recentOrdersPanel = new JPanel(new BorderLayout(10, 10));
            recentOrdersPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Recent Orders",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
            ));
            recentOrdersPanel.setBackground(new Color(240, 245, 255));

            String[] columns = {"Order ID", "Customer", "Product", "Quantity", "Status"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            JTable recentTable = new JTable(model);
            JScrollPane ordersScrollPane = new JScrollPane(recentTable);

            pstmt = conn.prepareStatement(
                "SELECT o.order_id, o.user_name, p.name, o.quantity, o.status " +
                "FROM Orders o " +
                "JOIN Products p ON o.product_id = p.product_id " +
                "ORDER BY o.order_date DESC " +
                "FETCH FIRST 10 ROWS ONLY");
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getString("user_name"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getString("status")
                });
            }

            recentOrdersPanel.add(ordersScrollPane);

            // Add panels to the layout
            tablesPanel.add(revenuePanel);
            tablesPanel.add(recentOrdersPanel);
            
            contentPanel.add(statsPanel, BorderLayout.NORTH);
            contentPanel.add(tablesPanel, BorderLayout.CENTER);

            frame.add(titlePanel, BorderLayout.NORTH);
            frame.add(contentPanel, BorderLayout.CENTER);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching statistics: " + e.getMessage());
        }

        frame.setVisible(true);
    }

   /* private static void viewEmployeeStats() {
        JFrame frame = new JFrame("Employee Statistics");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Employee Statistics Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsPanel.setBackground(new Color(240, 245, 255));

        try {
            // Total Orders
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as total FROM Orders");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Total Orders", rs.getInt("total"));
            }

            // Pending Orders
            pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as pending FROM Orders WHERE status = 'Pending'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Pending Orders", rs.getInt("pending"));
            }

            // Processing Orders
            pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as processing FROM Orders WHERE status = 'Processing'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Processing Orders", rs.getInt("processing"));
            }

            // Completed Orders
            pstmt = conn.prepareStatement(
                "SELECT COUNT(*) as completed FROM Orders WHERE status = 'Completed'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                addStatCard(statsPanel, "Completed Orders", rs.getInt("completed"));
            }

            // Add Revenue Statistics Panel with fixed width
            JPanel revenuePanel = new JPanel(new BorderLayout(10, 10));
            revenuePanel.setBackground(new Color(240, 245, 255));
            revenuePanel.setPreferredSize(new Dimension(300, 0)); // Set fixed width
            revenuePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Revenue Statistics",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
            ));

            // Create grid panel for revenue stats with fixed layout
            JPanel revenueStatsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            revenueStatsPanel.setBackground(new Color(240, 245, 255));
            revenueStatsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Get total revenue using function - Fixed CallableStatement
            CallableStatement cs = conn.prepareCall("{? = call calculate_total_revenue()}");
            cs.registerOutParameter(1, Types.NUMERIC);
            cs.execute();
            double totalRevenue = cs.getDouble(1);
            addStatCard(revenueStatsPanel, "Total Revenue", String.format("₹%.2f", totalRevenue));

            // Get current month revenue using procedure - Fixed procedure call
            cs = conn.prepareCall("{call get_revenue_by_daterange(?, ?, ?)}");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = new Date(cal.getTimeInMillis());
            Date endDate = new Date(System.currentTimeMillis());
            
            cs.setDate(1, startDate);
            cs.setDate(2, endDate);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.execute();
            
            double monthlyRevenue = cs.getDouble(3);
            addStatCard(revenueStatsPanel, "This Month's Revenue", String.format("₹%.2f", monthlyRevenue));

            // Recent Transactions Panel
            JPanel transactionsPanel = new JPanel(new BorderLayout(5, 5));
            transactionsPanel.setBackground(new Color(240, 245, 255));
            transactionsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Recent Transactions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
            ));
            
            String[] columns = {"Order ID", "Customer", "Amount", "Date"};
            DefaultTableModel revenueModel = new DefaultTableModel(columns, 0);
            JTable revenueTable = new JTable(revenueModel);
            JScrollPane scrollPane = new JScrollPane(revenueTable);

            // Get recent transactions from view
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(
                "SELECT order_id, user_name, total_amount, order_date " +
                "FROM order_revenue_view " +
                "ORDER BY order_date DESC " +
                "FETCH FIRST 5 ROWS ONLY"
            );
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("order_id"),
                    rs.getString("user_name"),
                    String.format("₹%.2f", rs.getDouble("total_amount")),
                    rs.getDate("order_date")
                };
                revenueModel.addRow(row);
            }

            // Add components to revenue panel
            revenuePanel.add(revenueStatsPanel, BorderLayout.NORTH);
            revenuePanel.add(transactionsPanel, BorderLayout.CENTER);
            transactionsPanel.add(scrollPane, BorderLayout.CENTER);

            // Add the revenue panel to the main frame
            frame.add(revenuePanel, BorderLayout.EAST);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching revenue statistics: " + e.getMessage());
        }

        // Recent Orders Panel
        JPanel recentOrdersPanel = new JPanel(new BorderLayout(10, 10));
        recentOrdersPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Recent Orders",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        recentOrdersPanel.setBackground(new Color(240, 245, 255));

        String[] columns = {"Order ID", "Customer", "Product", "Quantity", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable recentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(recentTable);

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT o.order_id, o.user_name, p.name, o.quantity, o.status " +
                "FROM Orders o " +
                "JOIN Products p ON o.product_id = p.product_id " +
                "ORDER BY o.order_date DESC " +
                "FETCH FIRST 10 ROWS ONLY");
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getString("user_name"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        recentOrdersPanel.add(scrollPane);

        // Add panels to frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(statsPanel, BorderLayout.CENTER);
        frame.add(recentOrdersPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    } */

    private static void refreshInventoryTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT product_id, name, price, stock, " +
                "CASE " +
                "    WHEN stock = 0 THEN 'Out of Stock' " +
                "    WHEN stock < 10 THEN 'Low Stock' " +
                "    ELSE 'In Stock' " +
                "END as status " +
                "FROM Products ORDER BY stock ASC");

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void refreshOrdersTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT o.order_id, o.user_name, p.name, o.quantity, " +
                "o.order_date, o.status " +
                "FROM Orders o " +
                "JOIN Products p ON o.product_id = p.product_id " +
                "ORDER BY o.order_date DESC");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("order_id"),
                    rs.getString("user_name"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDate("order_date"),
                    rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateOrderStatus(int orderId, String status, DefaultTableModel model) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Orders SET status = ? WHERE order_id = ?");
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                refreshOrdersTable(model);
                JOptionPane.showMessageDialog(null, "Order status updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error updating order status!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error!");
        }
    }

    private static void showCustomerFrame(String user_name) { // Add user_name parameter
        customerFrame = new JFrame("Customer Dashboard - " + user_name);
        customerFrame.setSize(800, 600);
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Customer Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(240, 245, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton viewProducts = createDashboardButton("View Products", "🛍");
        JButton placeOrder = createDashboardButton("Place Order", "📦");
        JButton orderHistory = createDashboardButton("Order History", "📋");
        JButton logout = createDashboardButton("Logout", "🚪");

        buttonPanel.add(viewProducts);
        buttonPanel.add(placeOrder);
        buttonPanel.add(orderHistory);
        buttonPanel.add(logout);

        viewProducts.addActionListener(e -> viewProducts()); // Changed from manageProducts()
        placeOrder.addActionListener(e -> placeOrder(user_name)); // Pass actual user_name
        orderHistory.addActionListener(e -> viewOrderHistory(user_name)); // Pass actual user_name
        logout.addActionListener(e -> {
            customerFrame.dispose();
            showLoginFrame();
        });

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        customerFrame.add(mainPanel);
        customerFrame.setVisible(true);
    }

    private static void showRegisterFrame() {
        registerFrame = new JFrame("Register New User");
        registerFrame.setSize(500, 400);
        registerFrame.setLocationRelativeTo(null);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("user_name:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"customer", "employee"});

        // Style components
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userField.setPreferredSize(new Dimension(200, 30));
        passField.setPreferredSize(new Dimension(200, 30));
        roleBox.setPreferredSize(new Dimension(200, 30));

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roleBox, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 255));

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");
        styleButton(registerButton);
        styleButton(backButton);

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // Action listeners remain the same
        registerButton.addActionListener(e -> {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO User_ (user_name, password, role) VALUES (?, ?, ?)");
                pstmt.setString(1, userField.getText());
                pstmt.setString(2, new String(passField.getPassword()));
                pstmt.setString(3, (String) roleBox.getSelectedItem());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(registerFrame, "Registration successful!");
                registerFrame.dispose();
                loginFrame.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(registerFrame, "Registration failed!");
            }
        });

        backButton.addActionListener(e -> {
            registerFrame.dispose();
            loginFrame.setVisible(true);
        });

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        registerFrame.add(mainPanel);
        loginFrame.setVisible(false);
        registerFrame.setVisible(true);
    }

    private static void manageUsers() {
        JFrame frame = new JFrame("Manage Users");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Table Panel
        String[] columns = {"user_name", "Role", "Full Name", "Age", "Date of Birth"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 255));

        JButton addUserButton = new JButton("Add User");
        JButton addEmployeeButton = new JButton("Add Employee");
        JButton removeButton = new JButton("Remove Selected");
        
        styleButton(addUserButton);
        styleButton(addEmployeeButton);
        styleButton(removeButton);

        buttonPanel.add(addUserButton);
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(removeButton);

        // Action Listeners
        addUserButton.addActionListener(e -> showAddUserFrame("customer"));
        addEmployeeButton.addActionListener(e -> showAddUserFrame("employee"));
        removeButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String user_name = (String) table.getValueAt(row, 0);
                removeUser(user_name, model);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a user to remove");
            }
        });

        refreshUserTable(model);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void showAddUserFrame(String role) {
        JFrame addFrame = new JFrame("Add " + (role.equals("employee") ? "Employee" : "User"));
        addFrame.setSize(500, 400);
        addFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Add New " + (role.equals("employee") ? "Employee" : "User"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields - remove ageField
        JTextField user_nameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField nameField = new JTextField(20);
        JTextField dobField = new JTextField(20);

        // Add components - remove age field
        addFormField(formPanel, "user_name:", user_nameField, gbc, 0);
        addFormField(formPanel, "Password:", passwordField, gbc, 1);
        addFormField(formPanel, "Full Name:", nameField, gbc, 2);
        addFormField(formPanel, "Date of Birth (YYYY-MM-DD):", dobField, gbc, 3);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 255));

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        styleButton(saveButton);
        styleButton(cancelButton);

        // Modify save action listener
        saveButton.addActionListener(e -> {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO User_ (user_name, password, role, full_name, dob) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, user_nameField.getText());
                pstmt.setString(2, new String(passwordField.getPassword()));
                pstmt.setString(3, role);
                pstmt.setString(4, nameField.getText());
                pstmt.setDate(5, java.sql.Date.valueOf(dobField.getText()));
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(addFrame, "User added successfully!");
                addFrame.dispose();
            } catch (SQLException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(addFrame, "Error adding user: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> addFrame.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addFrame.add(mainPanel);
        addFrame.setVisible(true);
    }

    private static void removeUser(String user_name, DefaultTableModel model) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM User_ WHERE user_name = ?");
            pstmt.setString(1, user_name);
            pstmt.executeUpdate();
            refreshUserTable(model);
            JOptionPane.showMessageDialog(null, "User removed successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error removing user!");
            e.printStackTrace();
        }
    }

    private static void refreshUserTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT user_name, role, full_name, age, dob FROM User_ ORDER BY role, user_name");
            while (rs.next()) {
                Object[] row = {
                    rs.getString("user_name"),
                    rs.getString("role"),
                    rs.getString("full_name"),
                    rs.getInt("age"),
                    rs.getDate("dob")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addFormField(JPanel panel, String label, JComponent field, 
            GridBagConstraints gbc, int row) {
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblField, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private static void manageProducts() {
        JFrame frame = new JFrame("Manage Products");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Manage Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Table Panel
        String[] columns = {"ID", "Name", "Price", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 255));

        JButton addButton = new JButton("Add New Product");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");
        
        styleButton(addButton);
        styleButton(updateButton);
        styleButton(deleteButton);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Action Listeners
        addButton.addActionListener(e -> showAddProductFrame(model));
        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int productId = (Integer) table.getValueAt(row, 0);
                showUpdateProductFrame(productId, model);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a product to update");
            }
        });
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int productId = (Integer) table.getValueAt(row, 0);
                deleteProduct(productId, model);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a product to delete");
            }
        });

        refreshProductTable(model);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void showAddProductFrame(DefaultTableModel parentModel) {
        JFrame addFrame = new JFrame("Add New Product");
        addFrame.setSize(500, 400);
        addFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Add New Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField stockField = new JTextField(20);

        // Add components
        addFormField(formPanel, "Product Name:", nameField, gbc, 0);
        addFormField(formPanel, "Price:", priceField, gbc, 1);
        addFormField(formPanel, "Initial Stock:", stockField, gbc, 2);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 255));

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        styleButton(saveButton);
        styleButton(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Product name cannot be empty");
                }
                
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                
                if (price < 0 || stock < 0) {
                    throw new IllegalArgumentException("Price and stock must be positive");
                }

                PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO Products (product_id, name, price, stock) VALUES (?, ?, ?, ?)");
                pstmt.setInt(1, getNextProductId());
                pstmt.setString(2, nameField.getText().trim());
                pstmt.setDouble(3, price);
                pstmt.setInt(4, stock);
                pstmt.executeUpdate();
                
                refreshProductTable(parentModel);
                JOptionPane.showMessageDialog(addFrame, "Product added successfully!");
                addFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addFrame, "Please enter valid numbers for price and stock!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(addFrame, ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addFrame, "Error adding product: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> addFrame.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addFrame.add(mainPanel);
        addFrame.setVisible(true);
    }

    private static void showUpdateProductFrame(int productId, DefaultTableModel parentModel) {
        JFrame updateFrame = new JFrame("Update Product");
        updateFrame.setSize(400, 300);
        updateFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Update Product Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField priceField = new JTextField(15);
        JTextField stockField = new JTextField(15);

        addFormField(formPanel, "New Price:", priceField, gbc, 0);
        addFormField(formPanel, "New Stock:", stockField, gbc, 1);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 255));

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        styleButton(saveButton);
        styleButton(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE Products SET price = ?, stock = ? WHERE product_id = ?");
                pstmt.setDouble(1, Double.parseDouble(priceField.getText()));
                pstmt.setInt(2, Integer.parseInt(stockField.getText()));
                pstmt.setInt(3, productId);
                pstmt.executeUpdate();
                
                refreshProductTable(parentModel);
                JOptionPane.showMessageDialog(updateFrame, "Product updated successfully!");
                updateFrame.dispose();
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(updateFrame, "Error updating product!");
            }
        });

        cancelButton.addActionListener(e -> updateFrame.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        updateFrame.add(mainPanel);
        updateFrame.setVisible(true);
    }

    private static void deleteProduct(int productId, DefaultTableModel model) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this product?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM Products WHERE product_id = ?");
                pstmt.setInt(1, productId);
                pstmt.executeUpdate();
                refreshProductTable(model);
                JOptionPane.showMessageDialog(null, "Product deleted successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting product!");
                e.printStackTrace();
            }
        }
    }

    private static void placeOrder(String user_name) {
        // Verify user exists before placing order
        try {
            PreparedStatement checkUser = conn.prepareStatement(
                "SELECT user_name FROM User_ WHERE user_name = ?");
            checkUser.setString(1, user_name);
            ResultSet userRs = checkUser.executeQuery();
            
            if (!userRs.next()) {
                JOptionPane.showMessageDialog(null, "Invalid user!");
                return;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }

        JFrame frame = new JFrame("Place Order");
        frame.setSize(800, 600);  // Increased size
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));  // Added gaps

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Place Order");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Table Panel
        String[] columns = {"ID", "Name", "Price", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        refreshProductTable(model);

        // Order Panel
        JPanel orderPanel = new JPanel(new GridBagLayout());
        orderPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        orderPanel.setBackground(new Color(240, 245, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField productIdField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JButton orderButton = new JButton("Place Order");
        styleButton(orderButton);

        // Add components with proper layout
        gbc.gridx = 0; gbc.gridy = 0;
        orderPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        orderPanel.add(productIdField, gbc);
        gbc.gridx = 2;
        orderPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 3;
        orderPanel.add(quantityField, gbc);
        gbc.gridx = 4;
        orderPanel.add(orderButton, gbc);

        // Action listener remains the same but with added validation
        orderButton.addActionListener(e -> {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                
                // Add stock validation
                PreparedStatement checkStock = conn.prepareStatement(
                    "SELECT stock FROM Products WHERE product_id = ?");
                checkStock.setInt(1, productId);
                ResultSet rs = checkStock.executeQuery();
                
                if (rs.next() && rs.getInt("stock") >= quantity) {
                    PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO Orders (order_id, user_name, product_id, quantity, order_date) " +
                        "VALUES (?, ?, ?, ?, SYSDATE)");
                    pstmt.setInt(1, getNextOrderId());
                    pstmt.setString(2, user_name);
                    pstmt.setInt(3, productId);
                    pstmt.setInt(4, quantity);
                    pstmt.executeUpdate();

                    // Update stock
                    pstmt = conn.prepareStatement(
                        "UPDATE Products SET stock = stock - ? WHERE product_id = ?");
                    pstmt.setInt(1, quantity);
                    pstmt.setInt(2, productId);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Order placed successfully!");
                    refreshProductTable(model);
                    productIdField.setText("");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient stock!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error placing order!");
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
            }
        });

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(orderPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void viewOrderHistory(String user_name) {
        JFrame frame = new JFrame("Order History");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        String[] columns = {"Order ID", "Product", "Quantity", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT o.order_id, p.name, o.quantity, o.order_date " +
                "FROM Orders o JOIN Products p ON o.product_id = p.product_id " +
                "WHERE o.user_name = ? ORDER BY o.order_date DESC");
            pstmt.setString(1, user_name);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("order_id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDate("order_date")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static void viewProducts() {
        JFrame frame = new JFrame("Available Products");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Available Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Table Panel
        String[] columns = {"ID", "Name", "Price", "Available Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Customize table appearance
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Load products
        refreshProductTable(model);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void refreshProductTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products");
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getNextProductId() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(product_id) + 1 FROM Products");
        return rs.next() ? rs.getInt(1) : 1;
    }

    private static int getNextOrderId() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(order_id) + 1 FROM Orders");
        return rs.next() ? rs.getInt(1) : 1;
    }

    // Update the styleButton method to adjust size based on text
    private static void styleButton(JButton button) {
        // Get preferred size based on text
        Dimension prefSize = button.getPreferredSize();
        // Add padding
        button.setPreferredSize(new Dimension(prefSize.width + 40, 35));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());

        // Hover effect remains the same
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    // Add viewReports method
    private static void viewReports() {
        JFrame frame = new JFrame("Admin Reports");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Sales Report Panel
        tabbedPane.addTab("Sales Report", createSalesReportPanel());
        
        // Inventory Report Panel
        tabbedPane.addTab("Inventory Report", createInventoryReportPanel());
        
        // User Statistics Panel
        tabbedPane.addTab("User Statistics", createUserStatsPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createSalesReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Sales Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Product ID", "Product Name", "Total Quantity Sold", "Total Revenue"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT p.product_id, p.name, " +
                "SUM(o.quantity) as total_sold, " +
                "SUM(o.quantity * p.price) as total_revenue " +
                "FROM Products p " +
                "LEFT JOIN Orders o ON p.product_id = o.product_id " +
                "GROUP BY p.product_id, p.name " +
                "ORDER BY total_revenue DESC NULLS LAST");
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getInt("total_sold"),
                    String.format("₹%.2f", rs.getDouble("total_revenue"))
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createInventoryReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Inventory Status");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Product ID", "Name", "Current Stock", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT product_id, name, stock, " +
                "CASE " +
                "    WHEN stock = 0 THEN 'Out of Stock' " +
                "    WHEN stock < 10 THEN 'Low Stock' " +
                "    ELSE 'In Stock' " +
                "END as status " +
                "FROM Products " +
                "ORDER BY stock ASC");

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createUserStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for statistics cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBackground(new Color(240, 245, 255));

        try {
            // Total Users
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) as total, " +
                "SUM(CASE WHEN role = 'customer' THEN 1 ELSE 0 END) as customers, " +
                "SUM(CASE WHEN role = 'employee' THEN 1 ELSE 0 END) as employees " +
                "FROM User_");

            if (rs.next()) {
                addStatCard(statsPanel, "Total Users", rs.getInt("total"));
                addStatCard(statsPanel, "Customers", rs.getInt("customers"));
                addStatCard(statsPanel, "Employees", rs.getInt("employees"));
            }

            // Total Orders
            rs = stmt.executeQuery("SELECT COUNT(*) as total_orders FROM Orders");
            if (rs.next()) {
                addStatCard(statsPanel, "Total Orders", rs.getInt("total_orders"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }

    private static void addStatCard(JPanel panel, String title, int value) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(new Color(70, 130, 180));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        panel.add(card);
    }

    // Helper method to create dashboard buttons
    private static JButton createDashboardButton(String text, String icon) {
        JButton button = new JButton("<html><center>" + icon + "<br>" + text + "</center></html>");
        button.setPreferredSize(new Dimension(200, 100));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    // Add overloaded addStatCard method for String values
    private static void addStatCard(JPanel panel, String title, String value) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(new Color(70, 130, 180));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        panel.add(card);
    }
}
