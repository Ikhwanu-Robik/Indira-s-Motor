package pages;

import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import controllers.LoginController;
import controllers.TransactionController;

import java.awt.BorderLayout;
import java.awt.Cursor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import pages.cashier.Cashier_Brand;
import pages.cashier.Cashier_Category;  
import pages.cashier.Cashier_Dashboard;
import pages.cashier.Cashier_Product;
import pages.cashier.Cashier_Transaction;

public class CashierLayout {
    private static MainFrame frame;
    private static Content_Panel contentPanel;
    private static LoginController loginSession;
    private static TransactionController transactionSession;
    private static int origin;
    private static int ORIGIN_PRODUCT = 1;
    private static int ORIGIN_BRAND = 2;
    private static int ORIGIN_CATEGORY = 3;
    private static int ORIGIN_TRANSACTION = 4;
	private static Nav_Panel navPanel;

    public static void init(LoginController loginSession) {
        CashierLayout.loginSession = loginSession;
        startTransactionSession();
        frame = new MainFrame("Cashier Dashboard");

        Nav_Panel navPanel = createNavPanel();
        contentPanel = Cashier_Dashboard.init(CashierLayout::reloadContent, loginSession.username);

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void startTransactionSession() {
    	transactionSession = new TransactionController(loginSession.authenticated_user_id);
    }
    
    public static void reloadContent(Content_Panel newPanel, Integer origin) {
    	CashierLayout.origin = origin;
		frame.remove(navPanel);
    	
    	navPanel = createNavPanel();
    	frame.add(navPanel, BorderLayout.WEST);
    	
    	navPanel.updateUI();
    	
        frame.remove(contentPanel);

        contentPanel = newPanel;
        frame.add(contentPanel);

        contentPanel.updateUI();
        
    }
    
    private static Nav_Panel createNavPanel() {
        CashierLayout.navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(Cashier_Dashboard.class.getClassLoader().getResource("assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav items
        // Nav links
        NavLabel navProduct = null;
        if (CashierLayout.origin == CashierLayout.ORIGIN_PRODUCT) {
        	navProduct = new NavLabel("Produk", true);
        }
        else {
        	navProduct = new NavLabel("Produk", false);
        }
        
        NavLabel navBrand = null;
        if (CashierLayout.origin == CashierLayout.ORIGIN_BRAND) {
        	navBrand =  new NavLabel("Brand", true);
        }
        else {
        	navBrand =  new NavLabel("Brand", false);
        }
        
        NavLabel navCategory = null;
        if (CashierLayout.origin == CashierLayout.ORIGIN_CATEGORY) {
        	navCategory = new NavLabel("Kategori", true);
        }
        else {
        	navCategory = new NavLabel("Kategori", false);
        }
        
        NavLabel navTransaction = null;
        if (CashierLayout.origin == CashierLayout.ORIGIN_TRANSACTION) {
        	navTransaction = new NavLabel("Transaksi", true);
        }
        else {
        	navTransaction = new NavLabel("Transaksi", false);
        }
        
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navBrand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navCategory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navTransaction.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");
        logoutBtn.addActionListener((e) -> {
        	if (loginSession.logout(loginSession.authenticated_user_id)) {
        		frame.dispose();
                Login.init();
        	} else {
        		JOptionPane.showMessageDialog(null, "Logout unsuccessful", "ERROR", JOptionPane.ERROR_MESSAGE);
        	}
        });

        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadContent(Cashier_Product.init(CashierLayout::reloadContent, transactionSession), Integer.valueOf(1));
            }
        });
         navBrand.addMouseListener(new java.awt.event.MouseAdapter() {
             @Override
             public void mouseClicked(java.awt.event.MouseEvent evt) {
                 reloadContent(Cashier_Brand.init(CashierLayout::reloadContent), Integer.valueOf(2));
             }
         });
         navCategory.addMouseListener(new java.awt.event.MouseAdapter() {
             @Override
             public void mouseClicked(java.awt.event.MouseEvent evt) {
                 reloadContent(Cashier_Category.init(CashierLayout::reloadContent), Integer.valueOf(3));
             }
         });
         navTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
             @Override
             public void mouseClicked(java.awt.event.MouseEvent evt) {
                 reloadContent(Cashier_Transaction.init(CashierLayout::reloadContent, loginSession, transactionSession), Integer.valueOf(4));
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
}
