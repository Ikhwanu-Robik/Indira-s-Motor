package pages.cashier;

import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cashier_Product_Info {

    // Constants
    private static MainFrame productInfoFrame = null;
    private static String productName;
    private static String productPrice;
    private static String imageUrl;
    private static String categoryName;

    public static void main(String[] args) {
        Cashier_Product_Info.productName = args[0];
        Cashier_Product_Info.productPrice = args[1];
        Cashier_Product_Info.imageUrl = args[2];
        Cashier_Product_Info.categoryName = args[3];
        
        MainFrame frame = new MainFrame("Cashier Product Info");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        productInfoFrame = frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(Cashier_Dashboard.class.getClassLoader().getResource("assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav links
        NavLabel navProduct = new NavLabel("Produk", false);
        NavLabel navBrand = new NavLabel("Merk", false);
        NavLabel navCategory = new NavLabel("Kategori", false);
        NavLabel navReport = new NavLabel("Laporan", false);
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navBrand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navCategory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        Cashier_Product cashierProduct = new Cashier_Product();

        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productInfoFrame.dispose();
                cashierProduct.main(new String[0]);
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
        navPanel.add(navReport);
        navPanel.add(Box.createVerticalStrut(100));
        navPanel.add(logoutBtn);

        return navPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JPanel image = createImagePanel("C:\\Users\\Arthur\\Downloads\\10854966.jpg");

        // Add label to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(image, gbc);
        return contentPanel;
    }

    private static JPanel createImagePanel(String imagePath) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(850, 700));

        try {
            InputStream input = Cashier_Product_Info.class.getResourceAsStream("/assets/" + Cashier_Product_Info.imageUrl);
            
            BufferedImage originalImage = ImageIO.read(input);
            Image scaledImage = originalImage.getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imagePanel.add(imageLabel);
        } catch (IOException e) {
            JLabel placeholder = new JLabel("No Image Available");
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            imagePanel.add(placeholder);
        }

        JPanel productDescription = createDescriptionPanel("Oli Amahay", 20000, "Oli");
        imagePanel.add(productDescription);

        imagePanel.add(Box.createVerticalStrut(20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BorderLayout());
        buttonContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel buttonPanel = createButtonPanel();
        buttonContainer.add(buttonPanel, BorderLayout.CENTER);

        imagePanel.add(buttonContainer);

        return imagePanel;
    }

    private static JPanel createDescriptionPanel(String name, double price, String category) {
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridBagLayout());

        // Add label to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel productName = new JLabel(Cashier_Product_Info.productName);
        productName.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel productPrice = new JLabel("Rp " + Cashier_Product_Info.productPrice);
        productPrice.setFont(new Font("Arial", Font.ITALIC, 18));

        JLabel productCategory = new JLabel(Cashier_Product_Info.categoryName);
        productCategory.setFont(new Font("Arial", Font.PLAIN, 16));

        descriptionPanel.add(productName, gbc);
        descriptionPanel.add(productPrice, gbc);
        descriptionPanel.add(productCategory, gbc);

        return descriptionPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        Cashier_Product cashierProduct = new Cashier_Product();

        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(0xE0E0E0));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productInfoFrame.dispose();
                cashierProduct.main(new String[0]);
            }
        });

        JButton addBtn = new JButton("Edit");
        addBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(0xA0522D));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(cancelBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(addBtn);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        return buttonPanel;
    }

}
