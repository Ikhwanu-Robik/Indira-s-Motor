package pages.cashier;

import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Cashier_Dashboard {

    // Constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
    private static final Color BROWN_COLOR = new Color(0xA0522D);
    private static MainFrame dashboardFrame = null;
    private static String loggedInUsername = "Cashier";

    public static void main(String[] args) {
        if (args.length != 0) {
            loggedInUsername = args[0];
        }
        MainFrame frame = new MainFrame("Cashier Dashboard");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        dashboardFrame = frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(Cashier_Dashboard.class.getClassLoader().getResource("assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav items
        // Nav links
        NavLabel navProduct = new NavLabel("Produk", false);
        NavLabel navBrand = new NavLabel("Merk", false);
        NavLabel navCategory = new NavLabel("Kategori", false);
        NavLabel navTransaction = new NavLabel("Transaksi", false);
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navBrand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navCategory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navTransaction.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        Cashier_Product cashierProduct = new Cashier_Product();
        Cashier_Brand cashierBrand = new Cashier_Brand();
        Cashier_Category cashierCategory = new Cashier_Category();
        Cashier_Transaction cashierTransaction = new Cashier_Transaction();
        
        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierProduct.main(new String[0]);
                dashboardFrame.dispose();
            }
        });
        navBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierBrand.main(new String[0]);
                dashboardFrame.dispose();
            }
        });
        navCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierCategory.main(new String[0]);
                dashboardFrame.dispose();
            }
        });
        navTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierTransaction.main(new String[0]);
                dashboardFrame.dispose();
            }
        });
        // Add components to nav panel
        navPanel.add(Box.createVerticalStrut(70));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(80));
        navPanel.add(navProduct);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navBrand);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navCategory);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navTransaction);
        navPanel.add(Box.createVerticalStrut(100));
        navPanel.add(logoutBtn);

        return navPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        // Welcome label
        JLabel titleLabel = createTitleLabel("Selamat Datang", BROWN_COLOR);
        JLabel usernameLabel = createTitleLabel(loggedInUsername, Color.WHITE);

        // Add label to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(usernameLabel, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(TITLE_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}
