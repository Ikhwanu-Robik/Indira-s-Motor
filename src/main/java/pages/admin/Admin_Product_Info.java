//package pages.admin;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//
//import components.Content_Panel;
//import components.Nav_Panel;
//import components.ui.LogoutButton;
//import components.ui.MainFrame;
//import components.ui.NavLabel;
//import controllers.CartController;
//import controllers.ReportController;
//import java.awt.Font;
//import java.util.ArrayList;
//import java.util.HashMap;
//import javax.swing.SwingConstants;
//
//public class Admin_Product_Info {
//
//    // Constants
//    private static MainFrame adminReportFrame = null;
////    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
//    public void main(String[] args) {
//        MainFrame frame = new MainFrame("Admin Products");
//
//        Nav_Panel navPanel = createNavPanel();
//        Content_Panel contentPanel = createContentPanel();
//
//        frame.add(navPanel, BorderLayout.WEST);
//        frame.add(contentPanel, BorderLayout.CENTER);
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        
//        adminReportFrame = frame;
//    }
//
//    private static Nav_Panel createNavPanel() {
//        Nav_Panel navPanel = new Nav_Panel();
//        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
//
//        // Logo
//        ImageIcon icon = new ImageIcon(new Admin_Cashier().getClass().getResource("/assets/indira_logo.png"));
//        JLabel logo = new JLabel();
//        logo.setIcon(icon);
//        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
//
//        // Nav links
//        NavLabel navEmployee = new NavLabel("Pegawai", false);
//        NavLabel navProduct = new NavLabel("Produk", false);
//        NavLabel navReport = new NavLabel("Laporan", true);
//        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//        Admin_Cashier adminCashier = new Admin_Cashier();
//        Admin_Products adminProducts = new Admin_Products();
//
//        navEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                adminCashier.main(new String[0]);
//                adminReportFrame.dispose();
//            }
//        });
//
//        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                adminProducts.main(new String[0]);
//                adminReportFrame.dispose();
//            }
//        });
//
//        // Logout button
//        LogoutButton logoutBtn = new LogoutButton("Keluar");
//
//        // Add components to nav panel
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
//        contentPanel.setLayout(new GridBagLayout());
//
//        JLabel titleLabel = createTitleLabel("Laporan Penjualan", new Color(0x00000));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//
//        contentPanel.add(titleLabel, gbc);
////        contentPanel.add("", gbc);
//
//        return contentPanel;
//    }
//
//    private static JLabel createTitleLabel(String text, Color color) {
//        JLabel label = new JLabel(text);
//        label.setForeground(color);
//        label.setFont(new Font("Arial", Font.BOLD, 30));
//        label.setHorizontalAlignment(SwingConstants.CENTER);
//        return label;
//    }
//
//
//
//}
