package pages.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import components.Button_Brown;
import components.Button_White;
import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import controllers.CashierController;
import java.util.HashMap;
import javax.swing.JFrame;

public class Admin_Add_Cashier {
//
//    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
//    private static final Color INPUT_COLOR = new Color(0xD9D9D9);
//    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
//    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 24);
//    private static JTextField usernameInput = null;
//    private static JPasswordField passwordInput = null;
//    private static MainFrame addCashierFrame = null;
//
//    public static void main(String[] args) {
//        MainFrame frame = new MainFrame("Add Cashier");
//
//        Nav_Panel navPanel = createNavPanel();
//        Content_Panel contentPanel = createContentPanel();
//
//        frame.add(navPanel, BorderLayout.WEST);
//        frame.add(contentPanel, BorderLayout.CENTER);
//
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        
//        addCashierFrame = frame;
//    }
//
//    private static Nav_Panel createNavPanel() {
//        Nav_Panel navPanel = new Nav_Panel();
//        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
//
//        // Logo
//        ImageIcon icon = new ImageIcon(new Admin_Add_Cashier().getClass().getResource("/assets/indira_logo.png"));
//        JLabel logo = new JLabel();
//        logo.setIcon(icon);
//        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
//
//        // Nav links
//        NavLabel navEmployee = new NavLabel("Pegawai", true);
//        NavLabel navProduct = new NavLabel("Produk", false);
//        NavLabel navReport = new NavLabel("Laporan", false);
//        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//        // Logout button
//        LogoutButton logoutBtn = new LogoutButton("Keluar");
//
//        // Add the components to the nav panel
//        navPanel.add(Box.createVerticalStrut(70));
//        navPanel.add(logo);
//        navPanel.add(Box.createVerticalStrut(80));
//        navPanel.add(navEmployee);
//        navPanel.add(Box.createVerticalStrut(40));
//        navPanel.add(navProduct);
//        navPanel.add(Box.createVerticalStrut(40));
//        navPanel.add(navReport);
//        navPanel.add(Box.createVerticalStrut(150));
//        navPanel.add(logoutBtn);
//
//        return navPanel;
//    }
//
//    private static Content_Panel createContentPanel() {
//        Content_Panel contentPanel = new Content_Panel();
//        contentPanel.setLayout(new BorderLayout());
//
//        JPanel centerContent = createFormPanel();
//        JPanel buttonPanel = createButtonPanel();
//
//        contentPanel.add(centerContent, BorderLayout.CENTER);
//        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        return contentPanel;
//    }
//
//    private static JPanel createFormPanel() {
//        JPanel centerContent = new JPanel();
//        centerContent.setLayout(new GridBagLayout());
//        centerContent.setBackground(BACKGROUND_COLOR);
//
//        GridBagConstraints gbc = createGridBagConstraints();
//
//        // Title
//        JLabel title = new JLabel("Tambahkan Pegawai");
//        title.setForeground(Color.black);
//        title.setFont(TITLE_FONT);
//        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
//
//        // Username field
//        JPanel userPanel = createInputPanel("Nama User:", gbc);
//
//        // Password field
//        JPanel passPanel = createPasswordPanel("Password:", gbc);
//
//        // Add components to center content
//        centerContent.add(title, gbc);
//        centerContent.add(userPanel, gbc);
//        centerContent.add(passPanel, gbc);
//
//        return centerContent;
//    }
//
//    private static GridBagConstraints createGridBagConstraints() {
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.insets = new Insets(15, 5, 15, 5);
//        return gbc;
//    }
//
//    private static JPanel createInputPanel(String labelText, GridBagConstraints gbc) {
//        JLabel textLabel = new JLabel(labelText);
//        textLabel.setForeground(Color.black);
//        textLabel.setFont(LABEL_FONT);
//
//        JTextField inputField = new JTextField();
//        inputField.setPreferredSize(new Dimension(600, 50));
//        inputField.setBackground(INPUT_COLOR);
//        usernameInput = inputField;
//
//        JPanel panel = new JPanel();
//        panel.add(textLabel, gbc);
//        panel.add(inputField, gbc);
//        panel.setBackground(BACKGROUND_COLOR);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//        return panel;
//    }
//
//    private static JPanel createPasswordPanel(String labelText, GridBagConstraints gbc) {
//        JLabel textLabel = new JLabel(labelText);
//        textLabel.setForeground(Color.black);
//        textLabel.setFont(LABEL_FONT);
//
//        JPasswordField passwordField = new JPasswordField();
//        passwordField.setPreferredSize(new Dimension(600, 50));
//        passwordField.setBackground(INPUT_COLOR);
//        passwordInput = passwordField;
//
//        JPanel panel = new JPanel();
//        panel.add(textLabel, gbc);
//        panel.add(passwordField, gbc);
//        panel.setBackground(BACKGROUND_COLOR);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//        return panel;
//    }
//
//    private static JPanel createButtonPanel() {
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BorderLayout());
//        buttonPanel.setBackground(BACKGROUND_COLOR);
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));
//
//        Admin_Cashier adminCashier = new Admin_Cashier();
//
//        // Create buttons
//        Button_Brown addButton = new Button_Brown("Tambahkan +");
//        Button_White cancelButton = new Button_White("Batal");
//
//        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
//            @SuppressWarnings("static-access")
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                HashMap<String, String> cashier_data = new HashMap<>();
//
//                String password = "";
//                for (char letter: passwordInput.getPassword()) {
//                    password += letter;
//                }
//                
//                cashier_data.put("username", usernameInput.getText());
//                cashier_data.put("password", password);
//                
//                new CashierController().create(cashier_data);
//                
//                addCashierFrame.dispose();
//                adminCashier.main(new String[0]);
//            }
//        });
//        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
//            @SuppressWarnings("static-access")
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                addCashierFrame.dispose();
//                adminCashier.main(new String[0]);
//            }
//        });
//
//        // Button container panels
//        JPanel rightButtonPanel = new JPanel();
//        rightButtonPanel.setBackground(BACKGROUND_COLOR);
//        rightButtonPanel.add(addButton);
//
//        JPanel leftButtonPanel = new JPanel();
//        leftButtonPanel.setBackground(BACKGROUND_COLOR);
//        leftButtonPanel.add(cancelButton);
//
//        // Position buttons
//        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
//        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
//
//        return buttonPanel;
//    }
}
