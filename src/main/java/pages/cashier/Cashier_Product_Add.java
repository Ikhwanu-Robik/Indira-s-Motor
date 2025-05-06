package pages.cashier;

import components.AddInput;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.itextpdf.awt.geom.Dimension;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Cashier_Product_Add {

    // Constants
    private static MainFrame productAddFrame = null;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Cashier Dashboard");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        productAddFrame = frame;
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
                productAddFrame.dispose();
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

        JLabel title = createTitleLabel("Tambahkan Produk", Color.BLACK);
        JPanel field = createInputPanel();
        JPanel dropDown = createDropDownPanel();
        JPanel buttonPanel = createButtonPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);

        contentPanel.add(title, gbc);
        contentPanel.add(field, gbc);
        contentPanel.add(dropDown, gbc);
        contentPanel.add(buttonPanel, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(createLabeledField("Nama Produk:"));
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(createLabeledField("Harga:"));
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(createLabeledField("Stok:"));

        return inputPanel;
    }

    private static JPanel createLabeledField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JTextField input = new AddInput();
        input.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(input);

        return panel;
    }

    private static JPanel createDropDownPanel() {
        // Panel utama untuk dua dropdown disusun horizontal
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10)); // spacing antar dropdown

        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel brandLabel = new JLabel("Merk:");
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] brands = {"Yamala", "Honga", "Daisatsu"};
        JComboBox<String> brandComboBox = new JComboBox<>(brands);
        brandComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        brandPanel.add(brandLabel);
        brandPanel.add(Box.createVerticalStrut(5));
        brandPanel.add(brandComboBox);

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] categories = {"Oli", "Ban", "Rantai"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createVerticalStrut(5));
        categoryPanel.add(categoryComboBox);

        mainPanel.add(brandPanel);
        mainPanel.add(categoryPanel);

        return mainPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        Cashier_Product cashierProduct = new Cashier_Product();

        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(0xE0E0E0));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productAddFrame.dispose();
                cashierProduct.main(new String[0]);
            }
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(cancelBtn);

        JButton addImageBtn = new JButton("+Gambar");
        addImageBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        addImageBtn.setForeground(Color.BLACK);
        addImageBtn.setBackground(new Color(0xE0E0E0));

        JButton addBtn = new JButton("Tambahkan");
        addBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(0xA0522D));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        addImageBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        addBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(addImageBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(addBtn);

        // Tambahkan ke panel utama
        btnPanel.add(leftPanel, BorderLayout.WEST);
        btnPanel.add(rightPanel, BorderLayout.EAST);

        return btnPanel;
    }

}
