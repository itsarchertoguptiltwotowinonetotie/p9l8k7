import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class AdminManagementFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JComboBox<String> teamComboBox;

    // Player input fields
    private JTextField playerIdField;
    private JTextField playerFirstNameField;
    private JTextField playerLastNameField;
    private JComboBox<String> dominantArmComboBox;
    private JTextField dateOfBirthField; // Format: YYYY-MM-DD
    private JTextField countryField;
    private JComboBox<String> genderComboBox;
    private JTextField jerseyNumberField;
    private JTextField positionField;

    // Customer input fields
    private JTextField customerIdField;
    private JTextField customerNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField customerAgeField;
    private JComboBox<String> customerGenderComboBox;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField upiField;
    private JTextField cardField;
    private JTextField redeemField;
    private JComboBox<String> ratingComboBox;

    private String DB_URL = "jdbc:oracle:thin:@localhost:1521/XE"; // Update this
    private String DB_USER = "system"; // Update this
    private String DB_PASSWORD = "humaira146"; // Update this

    public AdminManagementFrame() {
        setTitle("Admin Management");
        setSize(965, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // No layout manager
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // Alice Blue background

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 935, 610); // Set bounds for tabbed pane
        tabbedPane.addTab("Players", createPlayerPanel());
        tabbedPane.addTab("Teams", createTeamPanel());
        tabbedPane.addTab("Customers", createCustomerPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));

        // Set bounds for input fields and labels
        int labelX = 275;
        int inputX = 475;
        int height = 30;
        int y = 20;

        JLabel lblTeam = new JLabel("Select Team:");
        lblTeam.setBounds(labelX, y, 150, height);
        panel.add(lblTeam);

        teamComboBox = new JComboBox<>();
        populateTeamComboBox(teamComboBox);
        teamComboBox.setBounds(inputX, y, 200, height);
        panel.add(teamComboBox);
        y += height + 20;

        JLabel lblPlayerId = new JLabel("Player ID :");
        lblPlayerId.setBounds(labelX, y, 250, height);
        panel.add(lblPlayerId);

        playerIdField = new JTextField(15);
        playerIdField.setBounds(inputX, y, 200, height);
        panel.add(playerIdField);
        y += height + 20;

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(labelX, y, 100, height);
        panel.add(lblFirstName);

        playerFirstNameField = new JTextField(15);
        playerFirstNameField.setBounds(inputX, y, 200, height);
        panel.add(playerFirstNameField);
        y += height + 20;

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(labelX, y, 100, height);
        panel.add(lblLastName);

        playerLastNameField = new JTextField(15);
        playerLastNameField.setBounds(inputX, y, 200, height);
        panel.add(playerLastNameField);
        y += height + 20;

        JLabel lblDominantArm = new JLabel("Dominant Arm:");
        lblDominantArm.setBounds(labelX, y, 150, height);
        panel.add(lblDominantArm);

        dominantArmComboBox = new JComboBox<>(new String[] {"Right", "Left", "Ambidextrous"});
        dominantArmComboBox.setBounds(inputX, y, 200, height);
        panel.add(dominantArmComboBox);
        y += height + 20;

        JLabel lblDateOfBirth = new JLabel("Date of Birth (YYYY-MM-DD):");
        lblDateOfBirth.setBounds(labelX, y, 200, height);
        panel.add(lblDateOfBirth);

        dateOfBirthField = new JTextField(15);
        dateOfBirthField.setBounds(inputX, y, 200, height);
        panel.add(dateOfBirthField);
        y += height + 20;

        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setBounds(labelX, y, 100, height);
        panel.add(lblCountry);

        countryField = new JTextField(15);
        countryField.setBounds(inputX, y, 200, height);
        panel.add(countryField);
        y += height + 20;

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(labelX, y, 100, height);
        panel.add(lblGender);

        genderComboBox = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        genderComboBox.setBounds(inputX, y, 200, height);
        panel.add(genderComboBox);
        y += height + 20;

        JLabel lblJerseyNumber = new JLabel("Jersey Number:");
        lblJerseyNumber.setBounds(labelX, y, 150, height);
        panel.add(lblJerseyNumber);

        jerseyNumberField = new JTextField(5);
        jerseyNumberField.setBounds(inputX, y, 200, height);
        panel.add(jerseyNumberField);
        y += height + 20;

        // Position Field
        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setBounds(labelX, y, 150, height);
        panel.add(lblPosition);

        positionField = new JTextField(5);
        positionField.setBounds(inputX, y, 200, height);
        panel.add(positionField);
        y += height + 30; // Add extra space for button

        // Add Player Button
        JButton btnAddPlayer = new JButton("Add Player");
        btnAddPlayer.setBounds(inputX-50-100-25, y, 150, height);
        btnAddPlayer.addActionListener(e -> addPlayer());
        panel.add(btnAddPlayer);

        JButton btndeleteplayer = new JButton("Delete Player");
        btndeleteplayer.setBounds(inputX+150-100-25,y,150, height);
        btndeleteplayer.addActionListener(e -> deleteplayer());
        panel.add(btndeleteplayer);

        panel.setPreferredSize(new Dimension(1200, y + 300));

        return panel;
    }

    private JPanel createTeamPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));

        int labelX = 330;
        int inputX = 430;
        int height = 30;
        int y = 120;


        JLabel lblTeamId = new JLabel("Team ID :");
        lblTeamId.setBounds(labelX, y, 250, height);
        panel.add(lblTeamId);

        JTextField teamIdField = new JTextField(15);
        teamIdField.setBounds(inputX, y, 200, height);
        panel.add(teamIdField);
        y += height + 20;

        // Team Name
        JLabel lblTeamName = new JLabel("Team Name:");
        lblTeamName.setBounds(labelX, y, 150, height);
        panel.add(lblTeamName);

        JLabel text = new JLabel("For Updating team's logo, contact the DBA");
        text.setBounds(labelX, y-150, 250, height+100);
        panel.add(text);

        JTextField teamNameField = new JTextField(15);
        teamNameField.setBounds(inputX, y, 200, height);
        panel.add(teamNameField);
        y += height + 20;

        // Add Team Button
        JButton btnCreateTeam = new JButton("Create Team");
        btnCreateTeam.setBounds(inputX-150+25, y, 150, height);
        btnCreateTeam.addActionListener(e -> createTeam(teamIdField, teamNameField));
        panel.add(btnCreateTeam);

        JButton btndeleteTeam = new JButton("Delete Team");
        btndeleteTeam.setBounds(inputX+200-150+25,y,150,height);
        btndeleteTeam.addActionListener(e -> deleteTeam());
        panel.add(btndeleteTeam);

        panel.setPreferredSize(new Dimension(1200, y + 300));

        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
    
        int labelX1 = 30; // X position for the first column labels
        int inputX1 = 230; // X position for the first column input fields
        int labelX2 = 500; // X position for the second column labels
        int inputX2 = 700; // X position for the second column input fields
        int height = 30;
        int y = 20;

        
        // First Column
        JLabel lblCustomerId = new JLabel("Customer ID :");
        lblCustomerId.setBounds(labelX1, y, 250, height);
        panel.add(lblCustomerId);
    
        customerIdField = new JTextField(15);
        customerIdField.setBounds(inputX1, y, 200, height);
        panel.add(customerIdField);
        y += height + 20;
    
        JLabel lblCustomerName = new JLabel("Full Name:");
        lblCustomerName.setBounds(labelX1, y, 100, height);
        panel.add(lblCustomerName);
    
        customerNameField = new JTextField(15);
        customerNameField.setBounds(inputX1, y, 200, height);
        panel.add(customerNameField);
        y += height + 20;
    
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(labelX1, y, 100, height);
        panel.add(lblUsername);
    
        usernameField = new JTextField(15);
        usernameField.setBounds(inputX1, y, 200, height);
        panel.add(usernameField);
        y += height + 20;
    
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(labelX1, y, 100, height);
        panel.add(lblPassword);
    
        passwordField = new JPasswordField(15);
        passwordField.setBounds(inputX1, y, 200, height);
        panel.add(passwordField);
        y += height + 20;
    
        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(labelX1, y, 100, height);
        panel.add(lblAge);
    
        customerAgeField = new JTextField(5);
        customerAgeField.setBounds(inputX1, y, 200, height);
        panel.add(customerAgeField);
        y += height + 20;
    
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(labelX1, y, 100, height);
        panel.add(lblGender);
    
        customerGenderComboBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        customerGenderComboBox.setBounds(inputX1, y, 200, height);
        panel.add(customerGenderComboBox);
    
        // Reset y for the second column
        y = 20;
    
        // Second Column
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(labelX2, y, 100, height);
        panel.add(lblEmail);
    
        emailField = new JTextField(20);
        emailField.setBounds(inputX2, y, 200, height);
        panel.add(emailField);
        y += height + 20;
    
        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(labelX2, y, 150, height);
        panel.add(lblPhone);
    
        phoneField = new JTextField(15);
        phoneField.setBounds(inputX2, y, 200, height);
        panel.add(phoneField);
        y += height + 20;
    
        JLabel lblUpi = new JLabel("UPI ID:");
        lblUpi.setBounds(labelX2, y, 100, height);
        panel.add(lblUpi);
    
        upiField = new JTextField(20);
        upiField.setBounds(inputX2, y, 200, height);
        panel.add(upiField);
        y += height + 20;
    
        JLabel lblCard = new JLabel("Card Number:");
        lblCard.setBounds(labelX2, y, 150, height);
        panel.add(lblCard);
    
        cardField = new JTextField(16);
        cardField.setBounds(inputX2, y, 200, height);
        panel.add(cardField);
        y += height + 20;
    
        JLabel lblRedeem = new JLabel("Redeem Code:");
        lblRedeem.setBounds(labelX2, y, 150, height);
        panel.add(lblRedeem);
    
        redeemField = new JTextField(10);
        redeemField.setBounds(inputX2, y, 200, height);
        panel.add(redeemField);
        y += height + 20;
    
        JLabel lblRating = new JLabel("Rating (Optional):");
        lblRating.setBounds(labelX2, y, 150, height);
        panel.add(lblRating);
    
        ratingComboBox = new JComboBox<>(new String[] { "1", "2", "3", "4", "5" }); // Example ratings
        ratingComboBox.setBounds(inputX2, y, 200, height);
        panel.add(ratingComboBox);
        y += height + 30; // Add space before button
    
        // Add Customer Button
        JButton btnAddCustomer = new JButton("Add Customer");
        btnAddCustomer.setBounds(400-50-25, y, 150, height); // Centered between the two columns
        btnAddCustomer.addActionListener(e -> addCustomer());
        panel.add(btnAddCustomer);

        JButton btndeletecus = new JButton("Delete customer");
        btndeletecus.setBounds(600-50-25, y, 150, height);
        btndeletecus.addActionListener(e -> deletecus());
        panel.add(btndeletecus);
    
    
        // Expand the panel size to accommodate all components
        panel.setPreferredSize(new Dimension(2000, y + 800));
    
        return panel;
    }

    private void deletecus(){
        String name = JOptionPane.showInputDialog(null, "Enter The Customer id:", "Deleting Customer", JOptionPane.QUESTION_MESSAGE);
        
        if (name != null && !name.trim().isEmpty()) {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement("DELETE from customer where customer_id = ?")){
             stmt.setString(1,name);
             int rs = stmt.executeUpdate();
             if(rs>=1){
                JOptionPane.showMessageDialog(null,"The Customer has been deleted", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            // Display the entered name in a message dialog
            //JOptionPane.showMessageDialog(null, "Hello, " + name + "!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If no name was entered, display an error message
            JOptionPane.showMessageDialog(null, "Customer Does Not Exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void deleteplayer(){
        String name = JOptionPane.showInputDialog(null, "Enter The Player id:", "Deleting Customer", JOptionPane.QUESTION_MESSAGE);
        
        if (name != null && !name.trim().isEmpty()) {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement("DELETE from player_details where player_id = ?")){
             stmt.setString(1,name);
             int rs = stmt.executeUpdate();
             if(rs>=1){
                JOptionPane.showMessageDialog(null,"The Player has been deleted", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            // Display the entered name in a message dialog
            //JOptionPane.showMessageDialog(null, "Hello, " + name + "!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If no name was entered, display an error message
            JOptionPane.showMessageDialog(null, "Player Does Not Exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (name != null && !name.trim().isEmpty()) {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement("DELETE from bowler_details where player_id = ?")){
             stmt.setString(1,name);
             int rs = stmt.executeUpdate();
             //if(rs>=1){
             //   JOptionPane.showMessageDialog(null,"The Player has been deleted", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
             //}
        } catch (SQLException e) {
            e.printStackTrace();
        }

            // Display the entered name in a message dialog
            //JOptionPane.showMessageDialog(null, "Hello, " + name + "!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void deleteTeam(){
        String name = JOptionPane.showInputDialog(null, "Enter The Team id:", "Deleting Customer", JOptionPane.QUESTION_MESSAGE);
        int x1 = isPlayersAssociated(name);
        if(x1!=0){
            JOptionPane.showMessageDialog(null,"Empty the Team first", "Players Associated with the Team", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
        if (name != null && !name.trim().isEmpty()) {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             PreparedStatement stmt = con.prepareStatement("DELETE from teams where team_id = ?")){
             stmt.setString(1,name);
             int rs = stmt.executeUpdate();
             if(rs>=1){
                JOptionPane.showMessageDialog(null,"The Team has been deleted", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            // Display the entered name in a message dialog
            //JOptionPane.showMessageDialog(null, "Hello, " + name + "!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If no name was entered, display an error message
            JOptionPane.showMessageDialog(null, "Team Does Not Exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    private void populateTeamComboBox(JComboBox<String> teamComboBox) {
        List<String> teamNames = getTeamNames();
        for (String team : teamNames) {
            teamComboBox.addItem(team);
        }
    }

    private List<String> getTeamNames() {
        List<String> teams = new ArrayList<>();
        String query = "SELECT team_name FROM teams";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                teams.add(rs.getString("team_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    private int isPlayersAssociated(String team_id){
        int x=0;
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from player_details where team_id = '"+team_id+"'")) {
            while (rs.next()) {
                x++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }
    private void createTeam(JTextField teamIdField, JTextField teamNameField) {
        String teamId = teamIdField.getText().trim();
        if (teamId.isEmpty()) {
            teamId = generateTeamId(); // Auto-generate Team ID if left blank
        }
        String teamName = teamNameField.getText().trim();
        if (teamName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a team name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("INSERT INTO teams (team_id, team_name) VALUES (?, ?)")) {
            ps.setString(1, teamId);
            ps.setString(2, teamName);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Team Created Successfully!");
            teamComboBox.addItem(teamName); // Add new team to combo box
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addPlayer() {
        String teamName = (String) teamComboBox.getSelectedItem();
        String firstName = playerFirstNameField.getText().trim();
        String lastName = playerLastNameField.getText().trim();

        if (teamName == null || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Handle automatic ID generation
        String playerId = playerIdField.getText().trim();
        if (playerId.isEmpty()) {
            playerId = generatePlayerId(); // Auto-generate Player ID
        }

        String dominantArm = (String) dominantArmComboBox.getSelectedItem();
        String dateOfBirth = dateOfBirthField.getText().trim();
        String country = countryField.getText().trim();
        String gender = (String) genderComboBox.getSelectedItem();
        Integer jerseyNumber = jerseyNumberField.getText().isEmpty() ? null : Integer.parseInt(jerseyNumberField.getText());
        Integer position = positionField.getText().isEmpty() ? null : Integer.parseInt(positionField.getText());

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement psAddPlayer = conn.prepareStatement("INSERT INTO player_details (player_id, team_id, first_name, last_name, dominant_arm, date_of_birth, country, gender, jersey_number, position) VALUES (?, (SELECT team_id FROM teams WHERE team_name = ?), ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?)")) {

            psAddPlayer.setString(1, playerId);
            psAddPlayer.setString(2, teamName);
            psAddPlayer.setString(3, firstName);
            psAddPlayer.setString(4, lastName);
            psAddPlayer.setString(5, dominantArm);
            psAddPlayer.setString(6, dateOfBirth);
            psAddPlayer.setString(7, country);
            psAddPlayer.setString(8, gender);
            if (jerseyNumber != null) {
                psAddPlayer.setInt(9, jerseyNumber);
            } else {
                psAddPlayer.setNull(9, Types.INTEGER);
            }
            if (position != null) {
                psAddPlayer.setInt(10, position);
            } else {
                psAddPlayer.setNull(10, Types.INTEGER);
            }

            psAddPlayer.executeUpdate();
            JOptionPane.showMessageDialog(this, "Player Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement psAddPlayer = conn.prepareStatement("INSERT INTO bowler_details (player_id, team_id, first_name, last_name) VALUES (?, (SELECT team_id FROM teams WHERE team_name = ?), ?, ?)")) {

            psAddPlayer.setString(1, playerId);
            psAddPlayer.setString(2, teamName);
            psAddPlayer.setString(3, firstName);
            psAddPlayer.setString(4, lastName);

            psAddPlayer.executeUpdate();
            //JOptionPane.showMessageDialog(this, "Player Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addCustomer() {
        String customerId = customerIdField.getText().trim();
        if (customerId.isEmpty()) {
            customerId = generateCustomerId(); // Auto-generate Customer ID if left blank
        }
        String customerName = customerNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (customerName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer customerAge = customerAgeField.getText().isEmpty() ? null : Integer.parseInt(customerAgeField.getText());
        String customerGender = (String) customerGenderComboBox.getSelectedItem();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String upi = upiField.getText().trim();
        String card = cardField.getText().trim();
        String redeem = redeemField.getText().trim();
        Integer rating = ratingComboBox.getSelectedIndex() + 1; // Get rating from combo box selection

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (customer_id, customer_name, c_username, c_password, customer_age, customer_gender, email, upi, card, redeem_code, rating_provided) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
             
            ps.setString(1, customerId);
            ps.setString(2, customerName);
            ps.setString(3, username);
            ps.setString(4, password);
            if (customerAge != null) {
                ps.setInt(5, customerAge);
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setString(6, customerGender);
            ps.setString(7, email);
            ps.setString(8, upi);
            ps.setString(9, card);
            ps.setString(10, redeem);
            if (rating != null) {
                ps.setInt(11, rating);
            } else {
                ps.setNull(11, Types.INTEGER);
            }

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer Added Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String generatePlayerId() {
        return "P" + System.currentTimeMillis(); // Simple player ID generation
    }

    private String generateTeamId() {
        return "T" + System.currentTimeMillis(); // Simple team ID generation
    }

    private String generateCustomerId() {
        return "C" + System.currentTimeMillis(); // Simple customer ID generation
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminManagementFrame::new);
    }
}
public class v2 {
    public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/XE";
    public static final String DB_USER = "system";
    public static final String DB_PASSWORD = "humaira146";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new v2().createLoginFrame());
    }

    private void createLoginFrame() {
        JFrame frame = new JFrame("Fantasy Cricket Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

        Font font = new Font("Arial", Font.PLAIN, 14);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 50, 100, 25);
        lblUsername.setFont(font);
        frame.add(lblUsername);

        JTextField txtUsername = new JTextField(15);
        txtUsername.setBounds(150, 50, 150, 25);
        txtUsername.setFont(font);
        frame.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 100, 100, 25);
        lblPassword.setFont(font);
        frame.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setBounds(150, 100, 150, 25);
        txtPassword.setFont(font);
        frame.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(50, 150, 100, 25);
        btnLogin.setFont(font);
        frame.add(btnLogin);

        JButton btnSignup = new JButton("Sign Up");
        btnSignup.setBounds(200, 150, 100, 25);
        btnSignup.setFont(font);
        frame.add(btnSignup);

        btnLogin.addActionListener(e -> authenticateUser(txtUsername.getText(), new String(txtPassword.getPassword())));
        btnSignup.addActionListener(e -> createSignupFrame());

        frame.setVisible(true);
    }

    private void createSignupFrame() {
        JFrame frame = new JFrame("Fantasy Cricket Signup");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

        Font font = new Font("Arial", Font.PLAIN, 14);

        JLabel lblTitle = new JLabel("Sign Up", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(150, 20, 200, 30);
        frame.add(lblTitle);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 70, 100, 25);
        lblName.setFont(font);
        frame.add(lblName);
        
        JTextField txtName = new JTextField(15);
        txtName.setBounds(200, 70, 200, 25);
        txtName.setFont(font);
        frame.add(txtName);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 110, 100, 25);
        lblUsername.setFont(font);
        frame.add(lblUsername);
        
        JTextField txtUsername = new JTextField(15);
        txtUsername.setBounds(200, 110, 200, 25);
        txtUsername.setFont(font);
        frame.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 150, 100, 25);
        lblPassword.setFont(font);
        frame.add(lblPassword);
        
        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setBounds(200, 150, 200, 25);
        txtPassword.setFont(font);
        frame.add(txtPassword);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(50, 190, 100, 25);
        lblAge.setFont(font);
        frame.add(lblAge);
        
        JTextField txtAge = new JTextField(5);
        txtAge.setBounds(200, 190, 200, 25);
        txtAge.setFont(font);
        frame.add(txtAge);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(50, 230, 100, 25);
        lblGender.setFont(font);
        frame.add(lblGender);
        
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> cbGender = new JComboBox<>(genders);
        cbGender.setBounds(200, 230, 200, 25);
        cbGender.setFont(font);
        frame.add(cbGender);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 270, 100, 25);
        lblEmail.setFont(font);
        frame.add(lblEmail);
   
        JTextField txtEmail = new JTextField(20);
        txtEmail.setBounds(200, 270, 200, 25);
        txtEmail.setFont(font);
        frame.add(txtEmail);

        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(50, 310, 100, 25);
        lblPhone.setFont(font);
        frame.add(lblPhone);
        
        JTextField txtPhone = new JTextField(15);
        txtPhone.setBounds(200, 310, 200, 25);
        txtPhone.setFont(font);
        frame.add(txtPhone);

        JLabel lblUpi = new JLabel("UPI ID:");
        lblUpi.setBounds(50, 350, 100, 25);
        lblUpi.setFont(font);
        frame.add(lblUpi);
        
        JTextField txtUpi = new JTextField(20);
        txtUpi.setBounds(200, 350, 200, 25);
        txtUpi.setFont(font);
        frame.add(txtUpi);

        JLabel lblCard = new JLabel("Card Number:");
        lblCard.setBounds(50, 390, 100, 25);
        lblCard.setFont(font);
        frame.add(lblCard);
        
        JTextField txtCard = new JTextField(16);
        txtCard.setBounds(200, 390, 200, 25);
        txtCard.setFont(font);
        frame.add(txtCard);

        JLabel lblRedeem = new JLabel("Redeem Code:");
        lblRedeem.setBounds(50, 430, 100, 25);
        lblRedeem.setFont(font);
        frame.add(lblRedeem);
        
        JTextField txtRedeem = new JTextField(10);
        txtRedeem.setBounds(200, 430, 200, 25);
        txtRedeem.setFont(font);
        frame.add(txtRedeem);

        JLabel lblRating = new JLabel("Rating (Optional):");
        lblRating.setBounds(50, 470, 100, 25);
        lblRating.setFont(font);
        frame.add(lblRating);
        
        JTextField txtRating = new JTextField(5);
        txtRating.setBounds(200, 470, 200, 25);
        txtRating.setFont(font);
        frame.add(txtRating);

        JButton btnSubmit = new JButton("Sign Up");
        btnSubmit.setBounds(150, 510, 200, 30);
        btnSubmit.setFont(font);
        frame.add(btnSubmit);

        btnSubmit.addActionListener(e -> registerUser(txtName.getText(), txtUsername.getText(), new String(txtPassword.getPassword()), txtAge.getText(), cbGender.getSelectedItem().toString(), txtEmail.getText(), txtPhone.getText(), txtUpi.getText(), txtCard.getText(), txtRedeem.getText(), txtRating.getText()));

        frame.setVisible(true);
    }

    private void authenticateUser(String username, String password) {
        if ("admin".equals(username) && "pass123".equals(password)){
            JOptionPane.showMessageDialog(null, "Login Successful!");
            //System.out.println("hello idiot");
                new AdminManagementFrame(); // Open the admin management frame on successful login
                //dispose(); // Close the login frame
        }
        else{
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM CUSTOMER WHERE C_USERNAME = ? AND C_PASSWORD  = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                new CricketScoreUpdatingSystem(); // Open the score update frame on successful login
                //new AdminManagementFrame();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }


    private void registerUser(String name, String username, String password, String age, String gender, String email, String phone, String upi, String card, String redeem, String rating) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement cs = conn.prepareCall("{? = call is_username_unique(?)}")) {
    
            // Set the input parameters
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString(2, username);
    
            // Execute the validation function
            cs.execute();
            String validationResult = cs.getString(1);
    
            if (!"VALID".equals(validationResult)) {
                // Validation failed, show error message
                JOptionPane.showMessageDialog(null, validationResult, "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Validation passed, proceed with registration
            PreparedStatement ps = conn.prepareStatement("INSERT INTO CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, C_USERNAME, C_PASSWORD, CUSTOMER_AGE, CUSTOMER_GENDER, EMAIL, UPI, CARD, REDEEM_CODE, RATING_PROVIDED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String customerID = generateUniqueCustomerId(conn);
            if (customerID != null) {
                ps.setString(1, customerID);
                ps.setString(2, name);
                ps.setString(3, username);
                ps.setString(4, password);
                ps.setInt(5, Integer.parseInt(age));
                ps.setString(6, gender);
                ps.setString(7, email);
                ps.setString(8, upi);
                ps.setString(9, card);
                ps.setString(10, redeem);
                ps.setInt(11, rating.isEmpty() ? 0 : Integer.parseInt(rating));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Error in generating Customer ID, retry", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String generateUniqueCustomerId(Connection conn) throws SQLException {
        String customerID;
        do {
            customerID = generateCode();
            try (PreparedStatement ps = conn.prepareStatement("SELECT CUSTOMER_ID FROM CUSTOMER WHERE CUSTOMER_ID = ?")) {
                ps.setString(1, customerID);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return customerID; // ID is unique
                }
            }
        } while (true); // Keep generating until a unique ID is found
    }

    public static String generateCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Generates a random number (0-9999)
        return "C" + randomNumber;
    }
}
class CricketScoreUpdatingSystem extends JFrame {
    private JComboBox<String> homeTeamComboBox;
    private JComboBox<String> awayTeamComboBox;
    private JTextArea homePlayersTextArea;
    private JTextArea awayPlayersTextArea;
    public static JRadioButton homeTossWinnerRadioButton;
    public static JRadioButton awayTossWinnerRadioButton;
    public static JRadioButton batRadioButton;
    public static JRadioButton fieldRadioButton;
    public static String ht;
    public static String at;

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Update this with your actual database URL
    private static final String USER = "system"; // Update this with your actual database username
    private static final String PASSWORD = "humaira146"; // Update this with your actual database passworda

    public CricketScoreUpdatingSystem() {
        setTitle("Enter Match Details");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel homeTeamLabel = new JLabel("Home Team:");
        homeTeamLabel.setBounds(50, 50, 100, 30);
        add(homeTeamLabel);

        homeTeamComboBox = new JComboBox<>();
        homeTeamComboBox.setBounds(150, 50, 300, 30);
        add(homeTeamComboBox);

        JLabel awayTeamLabel = new JLabel("Away Team:");
        awayTeamLabel.setBounds(50, 100, 100, 30);
        add(awayTeamLabel);

        awayTeamComboBox = new JComboBox<>();
        awayTeamComboBox.setBounds(150, 100, 300, 30);
        add(awayTeamComboBox);

        JLabel homePlayersLabel = new JLabel("Home Team Players:");
        homePlayersLabel.setBounds(50, 150, 150, 30);
        add(homePlayersLabel);

        homePlayersTextArea = new JTextArea();
        JScrollPane homeScrollPane = new JScrollPane(homePlayersTextArea);
        homeScrollPane.setBounds(50, 180, 200, 195);
        add(homeScrollPane);

        JLabel awayPlayersLabel = new JLabel("Away Team Players:");
        awayPlayersLabel.setBounds(300, 150, 150, 30);
        add(awayPlayersLabel);

        awayPlayersTextArea = new JTextArea();
        JScrollPane awayScrollPane = new JScrollPane(awayPlayersTextArea);
        awayScrollPane.setBounds(300, 180, 200, 195);
        add(awayScrollPane);

        JLabel tossWinnerLabel = new JLabel("Toss Winner:");
        tossWinnerLabel.setBounds(50, 400, 100, 30);
        add(tossWinnerLabel);

        homeTossWinnerRadioButton = new JRadioButton("Home");
        homeTossWinnerRadioButton.setBounds(150, 400, 100, 30);
        add(homeTossWinnerRadioButton);

        awayTossWinnerRadioButton = new JRadioButton("Away");
        awayTossWinnerRadioButton.setBounds(250, 400, 100, 30);
        add(awayTossWinnerRadioButton);

        ButtonGroup tossWinnerGroup = new ButtonGroup();
        tossWinnerGroup.add(homeTossWinnerRadioButton);
        tossWinnerGroup.add(awayTossWinnerRadioButton);

        JLabel tossChoiceLabel = new JLabel("Toss Choice:");
        tossChoiceLabel.setBounds(50, 450, 100, 30);
        add(tossChoiceLabel);

        batRadioButton = new JRadioButton("Bat");
        batRadioButton.setBounds(150, 450, 100, 30);
        add(batRadioButton);

        fieldRadioButton = new JRadioButton("Field");
        fieldRadioButton.setBounds(250, 450, 100, 30);
        add(fieldRadioButton);

        ButtonGroup tossChoiceGroup = new ButtonGroup();
        tossChoiceGroup.add(batRadioButton);
        tossChoiceGroup.add(fieldRadioButton);

        JButton finishButton = new JButton("Finish Details");
        finishButton.setBounds(150, 500, 200, 30);
        finishButton.addActionListener(e -> {
            // Perform any actions needed before closing
            dispose(); // Close this match details frame
            new ScoreUpdateFrame(); // Open the score updating frame
        });
        add(finishButton);

        setVisible(true);

        List<String> teamNames = getTeamNames();
        for (String team : teamNames) {
            homeTeamComboBox.addItem(team);
            awayTeamComboBox.addItem(team);
        }

        homeTeamComboBox.addActionListener(new TeamSelectionListener());
        awayTeamComboBox.addActionListener(new TeamSelectionListener());

        setVisible(true);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<String> getTeamNames() {
        List<String> teams = new ArrayList<>();
        String query = "SELECT team_name FROM teams";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                teams.add(rs.getString("team_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public static List<String> getPlayers(String teamName) {
        List<String> players = new ArrayList<>();
        String x11;
        String x12;
        String query = "SELECT first_name, last_name FROM player_details natural join teams where team_name ='"+teamName+"'";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                x11 = rs.getString("first_name");
                x12 = rs.getString("last_name");
                players.add(""+x11+" "+x12+"");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    private class TeamSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == homeTeamComboBox) {
                ht = homeTeamComboBox.getSelectedItem().toString();
                List<String> homePlayers = getPlayers(ht);
                homePlayersTextArea.setText("");
                for (String player : homePlayers) {
                    homePlayersTextArea.append(player + "\n");
                }
            } else if (e.getSource() == awayTeamComboBox) {
                at = awayTeamComboBox.getSelectedItem().toString();
                List<String> awayPlayers = getPlayers(at);
                awayPlayersTextArea.setText("");
                for (String player : awayPlayers) {
                    awayPlayersTextArea.append(player + "\n");
                }
            }
        }
    }
    public static void main(String[] args) {
        new CricketScoreUpdatingSystem();
    }
}

class ScoreUpdateFrame extends JFrame {

    private String s2;
    private String s1;

    private int pos1;
    private int crp;
    private int pos2;
    private int m = 1;

    private JTable homePlayersTable;
    private JTable awayPlayersTable;
    private DefaultTableModel homeTableModel;
    private DefaultTableModel awayTableModel;

    private int homeRuns = 0;
    private int homeBalls = 0;
    private int homeWickets = 0;
    private Integer homeTarget = 0;

    private int awayRuns;
    private int awayBalls;
    private int awayWickets;
    private Integer awayTarget = 0;

    private JLabel homeScoreLabel;
    private JLabel homeRunRateLabel;
    private JLabel homeTargetLabel;

    private JLabel awayScoreLabel;
    private JLabel awayRunRateLabel;
    private JLabel awayTargetLabel;

    private JLabel homeTeamLogoLabel;
    private JLabel awayTeamLogoLabel;

    private JButton addRun1Button;
    private JButton addRun2Button;
    private JButton addRun3Button;
    private JButton addRun4Button;
    private JButton addRun6Button;
    private JButton addWicketButton;
    private JButton undoButton;
    private JComboBox<String> extrasComboBox;
    private JButton addExtraButton;
    private JButton resetButton;

    private Stack<Action> actionStack;

    private boolean isAwayTeam;

    private Connection conn;

    private boolean homeInningsCompleted = false;
    private boolean awayInningsCompleted = false;

  /*CREATE OR REPLACE TRIGGER update_economy
    AFTER UPDATE ON player_details
    FOR EACH ROW
    BEGIN
        UPDATE bowler_details
        SET economy = CASE
                WHEN balls_bowled > 0 THEN runs_conceded / (balls_bowled / 6)
                ELSE 0
            END;
END;
/           */


    private JComboBox<String> bowlerComboBox; // Add a JComboBox for selecting the bowler

    private interface Action {
        void undo();
    }

    public ScoreUpdateFrame() {
        try {
            conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
            initializeUI();
            resetScores();
            updateScoreLabels();
            displayTeamLogos();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void resetScores() {

        try {
            conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement("update player_details set runs = 0 where 1=1");
            ps.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("update player_details set balls = 0 where 1=1");
            ps2.executeUpdate();
            PreparedStatement ps3 = conn.prepareStatement("update player_details set strike_rate = 0 where 1=1");
            ps3.executeUpdate();
            PreparedStatement ps4 = conn.prepareStatement("update player_details set fours = 0 where 1=1");
            ps4.executeUpdate();
            PreparedStatement ps5 = conn.prepareStatement("update player_details set sixes = 0 where 1=1");
            ps5.executeUpdate();
            PreparedStatement ps6 = conn.prepareStatement("update bowler_details set runs_conceded = 0 where 1=1");
            ps6.executeUpdate();
            PreparedStatement ps7 = conn.prepareStatement("update bowler_details set balls_bowled = 0 where 1=1");
            ps7.executeUpdate();
            PreparedStatement ps8 = conn.prepareStatement("update bowler_details set overs_bowled = '0.0' where 1=1");
            ps8.executeUpdate();
            PreparedStatement ps9 = conn.prepareStatement("update bowler_details set maidens = 0 where 1=1");
            ps9.executeUpdate();
            PreparedStatement ps10 = conn.prepareStatement("update bowler_details set economy = 0 where 1=1");
            ps10.executeUpdate();
            PreparedStatement ps11 = conn.prepareStatement("update bowler_details set wickets = 0 where 1=1");
            ps11.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        updatePlayerStats();
        homeRuns = 0;
        homeBalls = 0;
        homeWickets = 0;
        homeTarget = null; // Set target to null

        awayRuns = 0;
        awayBalls = 0;
        awayWickets = 0;
        awayTarget = null; // Set target to null

        homeInningsCompleted = false;
        awayInningsCompleted = false;

        isAwayTeam = false; // Start with Home Team
        
        m=1;
        pos1 = m;
        m++;
        pos2 = m;
        m++;
        crp = pos1;

        actionStack.clear(); // Clear undo stack
        updateScoreLabels(); // Update the UI labels
    }

    private void initializeUI() {
        if (CricketScoreUpdatingSystem.homeTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.batRadioButton.isSelected()) {
            s1 = CricketScoreUpdatingSystem.ht;
            s2 = CricketScoreUpdatingSystem.at;   
        } else if (CricketScoreUpdatingSystem.homeTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.fieldRadioButton.isSelected()) {
            s1 = CricketScoreUpdatingSystem.at;
            s2 = CricketScoreUpdatingSystem.ht;
        } else if (CricketScoreUpdatingSystem.awayTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.batRadioButton.isSelected()) {
            s1 = CricketScoreUpdatingSystem.at;
            s2 = CricketScoreUpdatingSystem.ht;
        } else {
            s1 = CricketScoreUpdatingSystem.ht;
            s2 = CricketScoreUpdatingSystem.at;   
        }

        // Add labels for team logos
        homeTeamLogoLabel = new JLabel();
        homeTeamLogoLabel.setBounds(50, 350, 150, 150); // Position near the home team JTable
        add(homeTeamLogoLabel);

        awayTeamLogoLabel = new JLabel();
        awayTeamLogoLabel.setBounds(50, 580, 150, 150); // Position near the away team JTable
        add(awayTeamLogoLabel);

        JLabel l1 = new JLabel("Choose Bowler : ");
        l1.setBounds(30, 230, 200, 30);
        add(l1);

        bowlerComboBox = new JComboBox<>();
        bowlerComboBox.setBounds(170, 230, 200, 30);
        add(bowlerComboBox);
        
        // Populate the bowler combo box based on the current batting team
        populateBowlerComboBox();

        setTitle("Live Score Update");
        setSize(1600, 1000); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        pos1 = m;
        m++;
        pos2 = m;
        m++;
        crp = pos1;
        try {
            conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement("update player_details set runs = 0 where 1=1");
            ps.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("update player_details set balls = 0 where 1=1");
            ps2.executeUpdate();
            PreparedStatement ps3 = conn.prepareStatement("update player_details set strike_rate = 0 where 1=1");
            ps3.executeUpdate();
            PreparedStatement ps4 = conn.prepareStatement("update player_details set fours = 0 where 1=1");
            ps4.executeUpdate();
            PreparedStatement ps5 = conn.prepareStatement("update player_details set sixes = 0 where 1=1");
            ps5.executeUpdate();
            PreparedStatement ps6 = conn.prepareStatement("update bowler_details set wickets = 0 where 1=1");
            ps6.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String[] columnNames = {"Player", "Runs", "Balls", "4s", "6s", "S/R","Overs","Runs_given","Wickets","Economy"};

        homeTableModel = new DefaultTableModel(columnNames, 0);
        homePlayersTable = new JTable(homeTableModel);
        homePlayersTable.setFillsViewportHeight(true);
        JScrollPane homeScrollPane = new JScrollPane(homePlayersTable);
        homeScrollPane.setBounds(250, 320, 1050, 200); 
        add(homeScrollPane);

        awayTableModel = new DefaultTableModel(columnNames, 0);
        awayPlayersTable = new JTable(awayTableModel);
        awayPlayersTable.setFillsViewportHeight(true);
        JScrollPane awayScrollPane = new JScrollPane(awayPlayersTable);
        awayScrollPane.setBounds(250, 550, 1050, 200); 
        add(awayScrollPane);

        updatePlayerStats();

        JPanel homePanel = new JPanel();
        if (CricketScoreUpdatingSystem.homeTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.batRadioButton.isSelected()) {
            homePanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.ht));
        } else if (CricketScoreUpdatingSystem.homeTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.fieldRadioButton.isSelected()) {
            homePanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.at));
        } else if (CricketScoreUpdatingSystem.awayTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.batRadioButton.isSelected()) {
            homePanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.at));
        } else {
            homePanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.ht));
        }
        homePanel.setBounds(0, 0, 750, 150); 
        homePanel.setLayout(null); 

        homeScoreLabel = new JLabel();
        homeScoreLabel.setBounds(10, 20, 300, 30);
        homePanel.add(homeScoreLabel);

        homeRunRateLabel = new JLabel();
        homeRunRateLabel.setBounds(10, 60, 300, 30);
        homePanel.add(homeRunRateLabel);

        homeTargetLabel = new JLabel(); 
        homeTargetLabel.setBounds(10, 100, 300, 30);
        homePanel.add(homeTargetLabel);

        add(homePanel); 

        JPanel awayPanel = new JPanel();
        if (CricketScoreUpdatingSystem.homeTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.batRadioButton.isSelected()) {
            awayPanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.at));
        } else if (CricketScoreUpdatingSystem.homeTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.fieldRadioButton.isSelected()) {
            awayPanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.ht));
        } else if (CricketScoreUpdatingSystem.awayTossWinnerRadioButton.isSelected() && CricketScoreUpdatingSystem.batRadioButton.isSelected()) {
            awayPanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.ht));
        } else {
            awayPanel.setBorder(BorderFactory.createTitledBorder(CricketScoreUpdatingSystem.at));
        }
        awayPanel.setBounds(750, 0, 850, 150); 
        awayPanel.setLayout(null); 

        awayScoreLabel = new JLabel();
        awayScoreLabel.setBounds(10, 20, 300, 30);
        awayPanel.add(awayScoreLabel);

        awayRunRateLabel = new JLabel();
        awayRunRateLabel.setBounds(10, 60, 300, 30);
        awayPanel.add(awayRunRateLabel);

        awayTargetLabel = new JLabel(); 
        awayTargetLabel.setBounds(10, 100, 300, 30);
        awayPanel.add(awayTargetLabel);

        add(awayPanel); 
        int buttonWidth = 120;
        int buttonHeight = 50;
        int spacing = 20;
        int startX = (1400 - (5 * buttonWidth + 4 * spacing)) / 2;

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(0, 150, 1400, 200);

        JButton addWicketHomeButton = new JButton("<-- Wicket");
        addWicketHomeButton.setBounds(60, 10, 120, 50);
        addWicketHomeButton.addActionListener(e -> {
            if (crp == pos1) {
                pos1 = m;
                m++;
                crp = pos1;
            } else if (crp == pos2) {
                pos2 = m;
                m++;
                crp = pos2;
            } else {
                crp = pos1;
            }
            updatePlayerStats();
            addWicketHomeTeam();
        });
        controlPanel.add(addWicketHomeButton);

        addWicketButton = new JButton("Wicket -->");
        addWicketButton.setBounds(startX + 4 * (buttonWidth + spacing) + 160+80+50+(buttonWidth/2), 10, 120, 50);
        addWicketButton.addActionListener(e -> {
            if (crp == pos1) {
                pos1 = m;
                m++;
                crp = pos1;
            } else if (crp == pos2) {
                pos2 = m;
                m++;
                crp = pos2;
            } else {
                crp = pos1;
            }
            updatePlayerStats();
            addWicket();
        });
        controlPanel.add(addWicketButton);

        JButton addRun0Button = new JButton("Dot ball");
        addRun0Button.setBounds(startX -140+50+(buttonWidth/2), 10, buttonWidth, buttonHeight);
        addRun0Button.addActionListener(e -> {
            add0todb(crp);
            updatePlayerStats();
            addRuns(0);});
        controlPanel.add(addRun0Button);

        addRun1Button = new JButton("1 Run");
        addRun1Button.setBounds(startX +50+(buttonWidth/2), 10, buttonWidth, buttonHeight);
        addRun1Button.addActionListener(e -> {
            add1todb(crp);
            if(crp == pos1){
                crp = pos2;
            }
            else if(crp == pos2){
                crp = pos1;
            }
            else{
                crp = pos1;
            }
            updatePlayerStats();
            addRuns(1);});
        controlPanel.add(addRun1Button);

        addRun2Button = new JButton("2 Runs");
        addRun2Button.setBounds(startX+50 + buttonWidth + spacing+(buttonWidth/2), 10, buttonWidth, buttonHeight);
        addRun2Button.addActionListener(e -> {
            add2todb(crp);
            updatePlayerStats();
            addRuns(2);});
        controlPanel.add(addRun2Button);

        addRun3Button = new JButton("3 Runs");
        addRun3Button.setBounds(startX + 2 * (buttonWidth + spacing)+50+(buttonWidth/2), 10, buttonWidth, buttonHeight);
        addRun3Button.addActionListener(e -> {
            add3todb(crp);
            if(crp == pos1){
                crp = pos2;
            }
            else if(crp == pos2){
                crp = pos1;
            }
            else{
                crp = pos1;
            }
            updatePlayerStats();
            addRuns(3);});
        controlPanel.add(addRun3Button);

        addRun4Button = new JButton("4 Runs");
        addRun4Button.setBounds(startX + 3 * (buttonWidth + spacing)+50+(buttonWidth/2), 10, buttonWidth, buttonHeight);
        addRun4Button.addActionListener(e -> {
            add4todb(crp);
            updatePlayerStats();
            addRuns(4);});
        controlPanel.add(addRun4Button);

        addRun6Button = new JButton("6 Runs");
        addRun6Button.setBounds(startX + 4 * (buttonWidth + spacing)+50+(buttonWidth/2), 10, buttonWidth, buttonHeight);
        addRun6Button.addActionListener(e -> {
            add6todb(crp);
            updatePlayerStats();
            addRuns(6);});
        controlPanel.add(addRun6Button);

        String[] extrasOptions = {"Select Extra", "No-Ball", "Wide", "Bye", "Leg Bye"};
        extrasComboBox = new JComboBox<>(extrasOptions);
        extrasComboBox.setBounds(500 , 80, 150, 40); 
        controlPanel.add(extrasComboBox);

        addExtraButton = new JButton("Add Extra");
        addExtraButton.setBounds(650 + 125, 80, 150, 40); 
        addExtraButton.addActionListener(e -> addExtra());
        controlPanel.add(addExtraButton);

        resetButton = new JButton("Reset Scores");
        resetButton.setBounds(startX + 4 * (buttonWidth + spacing) + 160+100, 80, 200, 50); 
        resetButton.addActionListener(e -> resetScores());
        controlPanel.add(resetButton);

        add(controlPanel);
        actionStack = new Stack<>();

        add(controlPanel);

        setVisible(true);
        setLocationRelativeTo(null); 
    }

    private void populateBowlerComboBox() {
        bowlerComboBox.removeAllItems();
        List<String> bowlers = isAwayTeam ? CricketScoreUpdatingSystem.getPlayers(s1) : CricketScoreUpdatingSystem.getPlayers(s2);
        for (String bowler : bowlers) {
            bowlerComboBox.addItem(bowler);
        }
    }

    private void updatePlayerStats() {
        String player_name="";
int runs=0;
int balls=0;
int fours=0;
int sixes=0;
double strikeRate=0;
int balls_bowled=0;
String overs="";
int Runs_given=0;
int wickets=0;
int maidens=0;
int economy=0;

        homeTableModel.setRowCount(0);
        awayTableModel.setRowCount(0);
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
        String user = "system"; 
        String password = "humaira146"; 

        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM player_details natural join teams natural join bowler_details where team_name LIKE '"+CricketScoreUpdatingSystem.ht+"' order by position";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                player_name = rs.getString("first_name") + " " + rs.getString("last_name");
                runs = rs.getInt("runs");
                balls = rs.getInt("balls");
                fours = rs.getInt("fours");
                sixes = rs.getInt("sixes");
                strikeRate = rs.getDouble("strike_rate");
                balls_bowled = rs.getInt("balls_bowled");
                overs = rs.getString("overs_bowled");
                Runs_given = rs.getInt("runs_conceded");
                wickets = rs.getInt("wickets");
                maidens = rs.getInt("maidens");
                economy = rs.getInt("economy");  
                homeTableModel.addRow(new Object[]{player_name, runs, balls, fours, sixes, strikeRate, overs,Runs_given,wickets,economy});
            }
           
            stmt1 = conn.createStatement();

            String sql1 = "SELECT * FROM player_details natural join teams natural join bowler_details where team_name LIKE '"+CricketScoreUpdatingSystem.at+"' order by position";
            rs = stmt1.executeQuery(sql1);
            //System.out.println(CricketScoreUpdatingSystem.at);

            while (rs.next()) {
                player_name = rs.getString("first_name") + " " + rs.getString("last_name");
                runs = rs.getInt("runs");
                balls = rs.getInt("balls");
                fours = rs.getInt("fours");
                sixes = rs.getInt("sixes");
                strikeRate = rs.getDouble("strike_rate");
                balls_bowled = rs.getInt("balls_bowled");
                overs = rs.getString("overs_bowled");
                Runs_given = rs.getInt("runs_conceded");
                wickets = rs.getInt("wickets");
                maidens = rs.getInt("maidens");
                economy = rs.getInt("economy");  
                awayTableModel.addRow(new Object[]{player_name, runs, balls, fours, sixes, strikeRate, overs,Runs_given,wickets,economy});                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addRuns(int runsToAdd) {
        if (isAwayTeam) {
            if (!awayInningsCompleted) {
                awayRuns += runsToAdd;
                awayBalls++;
    
                // Check if an over is completed
                if (awayBalls % 6 == 0) {
                    promptBowlerChange();
                }
    
                actionStack.push(() -> {
                    awayRuns -= runsToAdd;
                    awayBalls--;
                    updateScoreLabels();
                });
                updateScoreLabels();
                checkTargetReached();
                checkInningsStatus();
            } else {
                JOptionPane.showMessageDialog(this, "Away Team innings already completed.");
            }
        } else {
            if (!homeInningsCompleted) {
                homeRuns += runsToAdd;
                homeBalls++;
    
                // Check if an over is completed
                if (homeBalls % 6 == 0) {
                    promptBowlerChange();
                }
    
                actionStack.push(() -> {
                    homeRuns -= runsToAdd;
                    homeBalls--;
                    updateScoreLabels();
                });
                updateScoreLabels();
                checkInningsStatus();
            } else {
                JOptionPane.showMessageDialog(this, "Home Team innings already completed.");
            }
        }
    }
    private void addWicket() {
        if (isAwayTeam) {
            if (!awayInningsCompleted) {
                awayWickets++;
                awayBalls++;
    
                // Check if an over is completed
                if (awayBalls % 6 == 0) {
                    promptBowlerChange();
                }
    
                String selectedBowler = (String) bowlerComboBox.getSelectedItem();
                updateBowlerWickets(selectedBowler);
    
                actionStack.push(() -> {
                    awayWickets--;
                    awayBalls--;
                    updateScoreLabels();
                });
                updateScoreLabels();
                updatePlayerStats();
    
                if (awayWickets >= 10) {
                    awayInningsCompleted = true;
                    JOptionPane.showMessageDialog(this, "Away Team innings completed! Total runs: " + awayRuns);
                    checkFinalResult();
                }
    
                checkTargetReached();
            } else {
                JOptionPane.showMessageDialog(this, "Away Team innings already completed.");
            }
        }
    }
    private void updateBowlerWickets(String bowler) {
        try {
            conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
            String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
            PreparedStatement ps = conn.prepareStatement("UPDATE bowler_details SET wickets = wickets + 1, balls_bowled = balls_bowled + 1  WHERE first_name || ' ' || last_name = ?");
            ps.setString(1, bowler);
            ps.executeUpdate();
           // PreparedStatement ps1 = conn.prepareStatement("UPDATE bowler_details SET balls_bowled = balls_bowled + 1 WHERE first_name || ' ' || last_name = ?");
           // ps1.setString(1, bowler);
            //ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        private void checkFinalResult() {
            if (homeInningsCompleted && awayInningsCompleted) {
                String resultMessage;
                if (awayRuns > homeRuns) {
                    int wicketsRemaining = 10 - awayWickets;
                    resultMessage = "Away Team wins by " + wicketsRemaining + " wickets!";
                    resetScores();
                } else if (homeRuns > awayRuns) {
                    int wicketsRemaining = 10 - homeWickets;
                    resultMessage = "Home Team wins by " + wicketsRemaining + " wickets!";
                    resetScores();
                } else {
                    resultMessage = "The match is a tie!";
                    resetScores();
                }
                JOptionPane.showMessageDialog(this, resultMessage);
            }
        }
        private void addWicketHomeTeam() {
            if (!homeInningsCompleted) {
                homeWickets++;
                homeBalls++;
    
                String selectedBowler = (String) bowlerComboBox.getSelectedItem();
                updateBowlerWickets(selectedBowler);
    
                actionStack.push(() -> {
                    homeWickets--;
                    homeBalls--;
                    updateScoreLabels();
                });
    
                updateScoreLabels();
                updatePlayerStats();
    
                if (homeWickets >= 10) {
                    homeInningsCompleted = true;
                    awayTarget = homeRuns + 1;
                    JOptionPane.showMessageDialog(this, "Home Team innings completed! Total runs: " + homeRuns + ". Target for Away Team: " + awayTarget);
                    startAwayTeamInnings();
                    checkFinalResult();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Home Team innings already completed.");
            }
        }
    
        private void startAwayTeamInnings() {
            // Resetting the away team's score and wickets
            awayRuns = 0;
            awayWickets = 0;
            awayBalls = 0; // Ensure balls are reset for the new innings
            awayInningsCompleted = false; // Ensure innings are not marked as completed yet
        
            // Display a message indicating that the away team's innings have started
            JOptionPane.showMessageDialog(this, "Away Team innings started! Target: " + awayTarget);
            
            // Set the isAwayTeam flag to true to switch context
            isAwayTeam = true;
            
            // Update UI for the away team to start scoring
            updateScoreLabels(); // Refresh score UI
        }
        private void addExtra() {
            if (isAwayTeam) {
                if (!awayInningsCompleted) {
                    awayRuns += 1; // Each extra adds 1 run
                    awayBalls++;
        
                    // Check if an over is completed
                    if (awayBalls % 6 == 0) {
                        promptBowlerChange();
                    }
        
                    actionStack.push(() -> {
                        awayRuns -= 1; // Undo action
                        updateScoreLabels();
                    });
                    updateScoreLabels();
                    checkTargetReached();
                    checkInningsStatus();
                } else {
                    JOptionPane.showMessageDialog(this, "Away Team innings already completed.");
                }
            } else {
                if (!homeInningsCompleted) {
                    homeRuns += 1; // Each extra adds 1 run
                    homeBalls++;
        
                    // Check if an over is completed
                    if (homeBalls % 6 == 0) {
                        promptBowlerChange();
                    }
        
                    actionStack.push(() -> {
                        homeRuns -= 1; // Undo action
                        updateScoreLabels();
                    });
                    updateScoreLabels();
                    checkInningsStatus();
                } else {
                    JOptionPane.showMessageDialog(this, "Home Team innings already completed.");
                }
            }
        }
        private void promptBowlerChange() {
            String currentBowler = (String) bowlerComboBox.getSelectedItem(); // Get the current bowler
        
            // Get the list of bowlers excluding the current bowler
            List<String> availableBowlers = new ArrayList<>();
            for (int i = 0; i < bowlerComboBox.getItemCount(); i++) {
                String bowler = bowlerComboBox.getItemAt(i);
                if (!bowler.equals(currentBowler)) {
                    availableBowlers.add(bowler);
                }
            }
        
            // Show a dialog to select a new bowler
            String selectedBowler = (String) JOptionPane.showInputDialog(
                this,
                "Select a new bowler:",
                "Change Bowler",
                JOptionPane.PLAIN_MESSAGE,
                null,
                availableBowlers.toArray(), // Populate with available bowlers
                availableBowlers.isEmpty() ? null : availableBowlers.get(0) // Default selection
            );
        
            // Update the bowlerComboBox if a new bowler is selected
            if (selectedBowler != null) {
                bowlerComboBox.setSelectedItem(selectedBowler);
            }
        }

        private void undoLastAction() {
            if (!actionStack.isEmpty()) {
                actionStack.pop().undo();
            }
        }

        private void updateScoreLabels() {
            // Update Home Team labels
            homeScoreLabel.setText("Score: " + homeRuns + "/" + homeWickets + " (" + getOvers(homeBalls) + ")");
            homeRunRateLabel.setText("Run Rate: " + getRunRate(homeRuns, homeBalls));
            homeTargetLabel.setText("Target: " + (homeTarget != null ? homeTarget.toString() : "N/A"));

            // Update Away Team labels
            awayScoreLabel.setText("Score: " + awayRuns + "/" + awayWickets + " (" + getOvers(awayBalls) + ")");
            awayRunRateLabel.setText("Run Rate: " + getRunRate(awayRuns, awayBalls));
            awayTargetLabel.setText("Target: " + (awayTarget != null ? awayTarget.toString() : "N/A"));
        }

        private void checkInningsStatus() {
    // Check for Home Team Innings Completion
    if (homeWickets >= 10 || homeBalls >= 12) { // Assuming 2 overs = 12 balls for Home
        if (!homeInningsCompleted) {
            awayTarget = homeRuns + 1;  // Set the target for the away team here
            JOptionPane.showMessageDialog(this, "Innings Completed! Total: " + homeRuns + ". Target : " + awayTarget);
            m=1;
            pos1 = m;
            m++;
            pos2 = m;
            m++;
            crp = pos1;
            homeInningsCompleted = true;
            isAwayTeam = true; // Switch to Away Team
        }
    }
            // Check for Away Team Innings Completion
            if (awayWickets >= 10 || awayBalls >= 12) { // Assuming 2 overs = 12 balls for Away
                if (!awayInningsCompleted) {
                    awayInningsCompleted = true;
                    checkTargetReached(); // Check if target is reached at the end of the innings
                }
            }
        }

        private void checkTargetReached() {
            if (awayTarget != null && awayRuns >= awayTarget && !awayInningsCompleted) {
                // Indicate the innings is complete when the target is chased
                awayInningsCompleted = true;
                
                // Show the innings completed message
                JOptionPane.showMessageDialog(this, "Innings completed! Away Team Total Runs: " + awayRuns);
                
                // Calculate the number of wickets remaining
                int wicketsRemaining = 10 - awayWickets;
                String resultMessage = "Away Team wins by " + wicketsRemaining + " wickets!";
                
                // Show the result of the match in a subsequent dialog box
                JOptionPane.showMessageDialog(this, resultMessage);
                
                // Optionally call the method to reset scores or handle the next steps
                resetScores(); // Reset or potentially navigate to initial state
            }
        }
        private String getOvers(int balls) {
            int overs = balls / 6; // Calculate overs
            int remainingBalls = balls % 6; // Remaining balls
            return overs + "." + remainingBalls; // Format with overs and balls
        }
        private void add1todb(int pos){
            try {
                conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
                String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
                //System.out.println(words[0]+""+words[1]);
                if(isAwayTeam){
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 1 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
                else{
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 1 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        private void add3todb(int pos){
            try {
                conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
                String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
                if(isAwayTeam){
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 3 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 1 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
                else{
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 3 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 3 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void add0todb(int pos){
            try {
                conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
                String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
                if(isAwayTeam){
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 0 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 0 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
                else{
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 0 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 0 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void add2todb(int pos){
            try {
                conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
                String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
                if(isAwayTeam){
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 2 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 2 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
                else{
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 2 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 2 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps2.setString(1,words[0]);
                    ps2.setString(2,words[1]);
                    ResultSet rs = ps2.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        private void add4todb(int pos){
            try {
                conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
                String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
                if(isAwayTeam){
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 4 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("update player_details set fours = fours + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps2.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 4 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps5 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps5.setString(1,words[0]);
                    ps5.setString(2,words[1]);
                    ResultSet rs = ps5.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
                else{
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 4 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("update player_details set fours = fours + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps2.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 4 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps5 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps5.setString(1,words[0]);
                    ps5.setString(2,words[1]);
                    ResultSet rs = ps5.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        private void add6todb(int pos){
            try {
                conn = DriverManager.getConnection(v2.DB_URL, v2.DB_USER, v2.DB_PASSWORD);
                String x = (String) bowlerComboBox.getSelectedItem();
                String[] words = x.split(" ");
                if(isAwayTeam){
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 6 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("update player_details set sixes = sixes + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s2+"')");
                    ps2.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 6 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps5 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps5.setString(1,words[0]);
                    ps5.setString(2,words[1]);
                    ResultSet rs = ps5.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
                else{
                    PreparedStatement ps = conn.prepareStatement("update player_details set runs = runs + 6 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps.executeUpdate();
                    PreparedStatement ps1 = conn.prepareStatement("update player_details set balls = balls + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps1.executeUpdate();
                    PreparedStatement ps2 = conn.prepareStatement("update player_details set sixes = sixes + 1 where position = '"+pos+"' and team_id in (select team_id from teams where team_name like '"+s1+"')");
                    ps2.executeUpdate();
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1, runs_conceded = runs_conceded + 6 where  first_name= ? and last_name = ?");
                    
                    ps4.setString(1,words[0]);
                    ps4.setString(2,words[1]);
                    ps4.executeUpdate();
                    PreparedStatement ps5 = conn.prepareStatement("select balls_bowled from bowler_details where first_name= ? and last_name = ?");
                    ps5.setString(1,words[0]);
                    ps5.setString(2,words[1]);
                    ResultSet rs = ps5.executeQuery();
                    int mm =0;
                    while (rs.next()) {
                        mm = rs.getInt("balls_bowled");
                    }
                    //System.out.println(getOvers(mm));
                    String as = getOvers(mm);
                    PreparedStatement ps3 = conn.prepareStatement("update bowler_details set overs_bowled = ? where first_name= ? and last_name = ?");
                    ps3.setString(1,as);
                    ps3.setString(2,words[0]);
                    ps3.setString(3,words[1]);
                    ps3.executeUpdate();
                    String plsql = "{call update_sr}";

                    // Create CallableStatement
                    CallableStatement callableStatement = conn.prepareCall(plsql);
                    callableStatement.execute();
                }
            } catch (SQLException ex) { 
                ex.printStackTrace();
            }
        }

        private void displayTeamLogos() {
            // Display home team logo
            BufferedImage homeLogo = getTeamLogo(CricketScoreUpdatingSystem.ht);
            if (homeLogo != null) {
                Image scaledHomeLogo = homeLogo.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize to fit JLabel
                homeTeamLogoLabel.setIcon(new ImageIcon(scaledHomeLogo));
            }
    
            // Display away team logo
            BufferedImage awayLogo = getTeamLogo(CricketScoreUpdatingSystem.at);
            if (awayLogo != null) {
                Image scaledAwayLogo = awayLogo.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize to fit JLabel
                awayTeamLogoLabel.setIcon(new ImageIcon(scaledAwayLogo));
            }
        }
    
        private BufferedImage getTeamLogo(String teamName) {
            String query = "SELECT team_logo FROM teams WHERE team_name = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, teamName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    InputStream input = rs.getBinaryStream("team_logo");
                    return ImageIO.read(input); // Convert BLOB to BufferedImage
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null; // Return null if no logo is found
        }

        private String getRunRate(int runs, int balls) {
            double runRate = (balls == 0) ? 0.0 : (double) runs * 6 / balls; // Calculate run rate
            return String.format("%.2f", runRate); // Format to two decimal places
        }

        public static void main(String[] args) {
            new ScoreUpdateFrame();
        }
    }
