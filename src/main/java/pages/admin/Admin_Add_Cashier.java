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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import components.Button_Brown;
import components.Button_White;
import components.Content_Panel;
import components.Nav_Panel;

public class Admin_Add_Cashier {

    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
    private static final Color INPUT_COLOR = new Color(0xD9D9D9);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font NAV_FONT_BOLD = new Font("Arial", Font.BOLD, 24);
    private static final Font NAV_FONT_PLAIN = new Font("Arial", Font.PLAIN, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);

    public static void main(String[] args) {
        JFrame frame = createMainFrame();

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(new Admin_Add_Cashier().getClass().getResource("/assets/icons-removebg.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav links
        JLabel navEmployee = createNavLabel("Pegawai", true);
        JLabel navProduct = createNavLabel("Produk", false);
        JLabel navReport = createNavLabel("Laporan", false);
        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        JButton logoutBtn = createLogoutButton();

        // Add the components to the nav panel
        navPanel.add(Box.createVerticalStrut(100));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(100));
        navPanel.add(navEmployee);
        navPanel.add(Box.createVerticalStrut(60));
        navPanel.add(navProduct);
        navPanel.add(Box.createVerticalStrut(60));
        navPanel.add(navReport);
        navPanel.add(Box.createVerticalStrut(250));
        navPanel.add(logoutBtn);

        return navPanel;
    }

    private static JLabel createNavLabel(String text, boolean isActive) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(isActive ? NAV_FONT_BOLD : NAV_FONT_PLAIN);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        return label;
    }

    private static JButton createLogoutButton() {
        JButton logoutBtn = new JButton("Keluar");
        logoutBtn.setForeground(new Color(0xA0522D));
        logoutBtn.setBackground(Color.white);
        logoutBtn.setMaximumSize(new Dimension(180, 50));
        logoutBtn.setPreferredSize(new Dimension(180, 50));
        logoutBtn.setFont(BUTTON_FONT);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        logoutBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return logoutBtn;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new BorderLayout());

        JPanel centerContent = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(centerContent, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    private static JPanel createFormPanel() {
        JPanel centerContent = new JPanel();
        centerContent.setLayout(new GridBagLayout());
        centerContent.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = createGridBagConstraints();

        // Title
        JLabel title = new JLabel("Tambahkan Pegawai");
        title.setForeground(Color.black);
        title.setFont(TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        // Username field
        JPanel userPanel = createInputPanel("Nama User:", gbc);

        // Password field
        JPanel passPanel = createPasswordPanel("Password:", gbc);

        // Add components to center content
        centerContent.add(title, gbc);
        centerContent.add(userPanel, gbc);
        centerContent.add(passPanel, gbc);

        return centerContent;
    }

    private static GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 15, 5);
        return gbc;
    }

    private static JPanel createInputPanel(String labelText, GridBagConstraints gbc) {
        JLabel textLabel = new JLabel(labelText);
        textLabel.setForeground(Color.black);
        textLabel.setFont(LABEL_FONT);

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(600, 50));
        inputField.setBackground(INPUT_COLOR);

        JPanel panel = new JPanel();
        panel.add(textLabel, gbc);
        panel.add(inputField, gbc);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    private static JPanel createPasswordPanel(String labelText, GridBagConstraints gbc) {
        JLabel textLabel = new JLabel(labelText);
        textLabel.setForeground(Color.black);
        textLabel.setFont(LABEL_FONT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(600, 50));
        passwordField.setBackground(INPUT_COLOR);

        JPanel panel = new JPanel();
        panel.add(textLabel, gbc);
        panel.add(passwordField, gbc);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));

        Admin_Cashier adminCashier = new Admin_Cashier();

        // Create buttons
        Button_Brown addButton = new Button_Brown("Tambahkan +");
        Button_White cancelButton = new Button_White("Batal");

        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @SuppressWarnings("static-access")
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminCashier.main(new String[0]);
            }
        });

        // Button container panels
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setBackground(BACKGROUND_COLOR);
        rightButtonPanel.add(addButton);

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setBackground(BACKGROUND_COLOR);
        leftButtonPanel.add(cancelButton);

        // Position buttons
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);

        return buttonPanel;
    }
}
