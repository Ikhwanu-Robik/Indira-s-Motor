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
import controllers.CashierController;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin_Cashier {

    // Constants
    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);

    private static MainFrame adminCashierFrame = null;
    
    private static ArrayList<String> cashierNames = new ArrayList<>();

    public static void getCashiers() {
//        Clear the cashiers array
        cashierNames = new ArrayList<>();
        
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");

        ArrayList<HashMap<String, String>> cashiers = new CashierController().read(columns);

        if (cashiers != null) {
            for (HashMap<String, String> cashier : cashiers) {
                cashierNames.add(cashier.get("username"));
            }
        } else {
            cashierNames.add("no cashier found");
        }
    }

    public void main(String[] args) {
        MainFrame frame = new MainFrame("Admin Cashier");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        adminCashierFrame = frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(new Admin_Cashier().getClass().getResource("/assets/indira_logo.png"));
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
        
        Admin_Products adminProducts = new Admin_Products();
        Admin_Report adminReport = new Admin_Report();

        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminProducts.main(new String[0]);
                adminCashierFrame.dispose();
            }
        });
        
        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminReport.main(new String[0]);
                adminCashierFrame.dispose();
            }
        });

        // Add components to nav panel
        navPanel.add(Box.createVerticalStrut(70));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(80));
        navPanel.add(navEmployee);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navProduct);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navReport);
        navPanel.add(Box.createVerticalStrut(150));
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
                adminCashierFrame.dispose();
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

        // Calling the API
        getCashiers();
        // Add cashier cards to the panel
        for (String name : cashierNames) {
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
