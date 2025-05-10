/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import pages.admin.Admin_Cashier;
import pages.admin.Admin_Dashboard;
import pages.admin.Admin_Products;
import pages.admin.Admin_Report;

/**
 *
 * @author MyBook Hype AMD
 */
public class AdminLayout {
    private static MainFrame frame;
    private static Content_Panel contentPanel;

    public static void init() {
        frame = new MainFrame("Admin Dashboard");

        Nav_Panel navPanel = createNavPanel();
        contentPanel = Admin_Dashboard.init();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void reloadContent(Content_Panel newPanel) {
        frame.remove(contentPanel);

        contentPanel = newPanel;
        frame.add(contentPanel);

        contentPanel.updateUI();
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        ImageIcon icon = new ImageIcon(Admin_Dashboard.class.getClassLoader().getResource("assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav items
        // Nav links
        NavLabel navEmployee = new NavLabel("Pegawai", false);
        NavLabel navProduct = new NavLabel("Produk", false);
        NavLabel navReport = new NavLabel("Laporan", false);
        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        LogoutButton logoutBtn = new LogoutButton("Keluar");
        logoutBtn.addActionListener((e) -> {
            frame.dispose();
            Login.init();
        });

        navEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Content_Panel adminCashierPanel = Admin_Cashier.init(AdminLayout::reloadContent);
                reloadContent(adminCashierPanel);
            }
        });

        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadContent(Admin_Products.init(AdminLayout::reloadContent));
            }
        });

        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Content_Panel adminReportPanel = Admin_Report.init(AdminLayout::reloadContent);
                reloadContent(adminReportPanel);
            }
        });

        navPanel.add(Box.createVerticalStrut(70));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(80));
        navPanel.add(navEmployee);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navProduct);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navReport);
        navPanel.add(Box.createVerticalStrut(170));
        navPanel.add(logoutBtn);

        return navPanel;
    }
}
