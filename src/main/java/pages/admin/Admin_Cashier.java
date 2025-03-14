package pages.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.Button_Brown;
import components.Cashier_Card;
import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;

public class Admin_Cashier {

    // Constants
    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);

    // Data Dummy
    private static final String[] CASHIER_NAMES = {
            "Ikhwanu", "Rasyid", "Dio", "Bagus", "Belva",
            "Suyadi", "Geno", "Supri", "Slamet", "Sugeng"
    };

    public void main(String[] args) {
        MainFrame frame = new MainFrame("Admin Cashier");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(new Admin_Cashier().getClass().getResource("/assets/icons-removebg.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav links
        NavLabel navEmployee = new NavLabel("Pegawai", true);
        NavLabel navProduct = new NavLabel("Produk", false);
        NavLabel navReport = new NavLabel("Laporan", false);
        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        // Add components to nav panel
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

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        JScrollPane scrollPane = createCashierListScrollPane();

        contentPanel.add(buttonPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 100));

        Admin_Add_Cashier adminAddCashier = new Admin_Add_Cashier();

        // Create "Add Employee" button
        Button_Brown addButton = new Button_Brown("Tambah Pegawai +");

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @SuppressWarnings("static-access")
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddCashier.main(new String[0]);
            }
        });

        // Panel for right-aligned button
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setBackground(BACKGROUND_COLOR);
        rightButtonPanel.add(addButton);

        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        return buttonPanel;
    }

    private static JScrollPane createCashierListScrollPane() {
        JPanel cashierListPanel = new JPanel();
        cashierListPanel.setLayout(new GridBagLayout());
        cashierListPanel.setBackground(BACKGROUND_COLOR);

        // Configure GridBagConstraints for cashier cards
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(15, 5, 15, 5);
        gbc.weightx = 1.0;

        // Add cashier cards to the panel
        for (String name : CASHIER_NAMES) {
            Cashier_Card cashierCard = new Cashier_Card(name);
            cashierListPanel.add(cashierCard, gbc);
        }

        // Create scroll pane for the cashier list
        JScrollPane scrollPane = new JScrollPane(cashierListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }
}
