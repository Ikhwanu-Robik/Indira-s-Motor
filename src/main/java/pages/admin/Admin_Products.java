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

import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;

public class Admin_Products {

    // Constants
//    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
    
    public void main(String[] args) {
        MainFrame frame = new MainFrame("Admin Products");

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
            }
        });
     
        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminReport.main(new String[0]);
            }
        });

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

        return contentPanel;
    }

}
