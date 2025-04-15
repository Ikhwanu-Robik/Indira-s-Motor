package pages.admin;

import java.awt.BorderLayout;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import components.Content_Panel;
import components.Nav_Panel;
import components.Product_Card;
import components.Search_Bar;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Admin_Products {

    // Constants
    private static MainFrame adminProductFrame = null;
//    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);

    public void main(String[] args) {
        MainFrame frame = new MainFrame("Admin Products");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        adminProductFrame = frame;
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
        NavLabel navEmployee = new NavLabel("Pegawai", false);
        NavLabel navProduct = new NavLabel("Produk", true);
        NavLabel navReport = new NavLabel("Laporan", false);
        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        Admin_Cashier adminCashier = new Admin_Cashier();
        Admin_Report adminReport = new Admin_Report();

        navEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminCashier.main(new String[0]);
                adminProductFrame.dispose();
            }
        });

        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminReport.main(new String[0]);
                adminProductFrame.dispose();
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
        contentPanel.setLayout(new BorderLayout(10, 10)); // spacing between components

        JPanel searchPanel = createFilterPanel();
        JScrollPane cardScrollPane = new JScrollPane(createCardPanel());
        cardScrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(cardScrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private static JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        Search_Bar searchBar = new Search_Bar();
        filterPanel.add(searchBar);

        return filterPanel;
    }

    private static JPanel createCardPanel() {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Grid layout untuk kartu

        cardPanel.add(new Product_Card("Busi", 15000));
        cardPanel.add(new Product_Card("Oli", 45000));
        cardPanel.add(new Product_Card("Ban", 250000));
        cardPanel.add(new Product_Card("Spion", 60000));
        cardPanel.add(new Product_Card("Baut", 5000));
        cardPanel.add(new Product_Card("Box", 30000));
        cardPanel.add(new Product_Card("Knalpot", 120000));
        cardPanel.add(new Product_Card("Mur", 3000));
        cardPanel.add(new Product_Card("Pintu", 2000000));
        cardPanel.add(new Product_Card("Kaca", 500000));

        return cardPanel;
    }

}
