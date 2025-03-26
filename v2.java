import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM CUSTOMER WHERE C_USERNAME = ? AND C_PASSWORD  = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                new CricketScoreUpdatingSystem(); // Open the score update frame on successful login
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void registerUser(String name, String username, String password, String age, String gender, String email, String phone, String upi, String card, String redeem, String rating) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("INSERT INTO CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, C_USERNAME, C_PASSWORD, CUSTOMER_AGE, CUSTOMER_GENDER, EMAIL, UPI, CARD, REDEEM_CODE, RATING_PROVIDED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
             
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
        homeScrollPane.setBounds(25, 320, 1050, 200); 
        add(homeScrollPane);

        awayTableModel = new DefaultTableModel(columnNames, 0);
        awayPlayersTable = new JTable(awayTableModel);
        awayPlayersTable.setFillsViewportHeight(true);
        JScrollPane awayScrollPane = new JScrollPane(awayPlayersTable);
        awayScrollPane.setBounds(25, 550, 1050, 200); 
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
        addWicketHomeButton.setBounds(30, 10, 120, 50);
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
        addWicketButton.setBounds(startX + 4 * (buttonWidth + spacing) + 160+80, 10, 120, 50);
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

        addRun1Button = new JButton("1 Run");
        addRun1Button.setBounds(startX, 10, buttonWidth, buttonHeight);
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
        addRun2Button.setBounds(startX + buttonWidth + spacing, 10, buttonWidth, buttonHeight);
        addRun2Button.addActionListener(e -> {
            add2todb(crp);
            updatePlayerStats();
            addRuns(2);});
        controlPanel.add(addRun2Button);

        addRun3Button = new JButton("3 Runs");
        addRun3Button.setBounds(startX + 2 * (buttonWidth + spacing), 10, buttonWidth, buttonHeight);
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
        addRun4Button.setBounds(startX + 3 * (buttonWidth + spacing), 10, buttonWidth, buttonHeight);
        addRun4Button.addActionListener(e -> {
            add4todb(crp);
            updatePlayerStats();
            addRuns(4);});
        controlPanel.add(addRun4Button);

        addRun6Button = new JButton("6 Runs");
        addRun6Button.setBounds(startX + 4 * (buttonWidth + spacing), 10, buttonWidth, buttonHeight);
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
                    PreparedStatement ps4 = conn.prepareStatement("update bowler_details set balls_bowled = balls_bowled + 1 where  first_name= ? and last_name = ?");
                    
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
        private String getRunRate(int runs, int balls) {
            double runRate = (balls == 0) ? 0.0 : (double) runs * 6 / balls; // Calculate run rate
            return String.format("%.2f", runRate); // Format to two decimal places
        }

        public static void main(String[] args) {
            new ScoreUpdateFrame();
        }
    }
