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

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JButton;

public class Cashier_Category_Add {

    // Constants
    private static MainFrame categoryAddFrame = null;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Cashier Add Category");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        categoryAddFrame = frame;
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
                categoryAddFrame.dispose();
            }
        });
        navBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierBrand.main(new String[0]);
                categoryAddFrame.dispose();
            }
        });
        navCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierCategory.main(new String[0]);
                categoryAddFrame.dispose();
            }
        });
        navTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierTransaction.main(new String[0]);
                categoryAddFrame.dispose();
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

        JLabel title = createTitleLabel("Tambah Kategori", Color.BLACK);
        JPanel field = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);

        contentPanel.add(title, gbc);
        contentPanel.add(field, gbc);
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

        inputPanel.add(createLabeledField("Kategori:"));
        inputPanel.add(Box.createVerticalStrut(15));

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

    private static JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Cashier_Category cashierCategory = new Cashier_Category();

        // Tombol "Batal"
        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(0xE0E0E0));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoryAddFrame.dispose();
                cashierCategory.main(new String[0]);
            }
        });

        // Panel kiri untuk tombol "Batal"
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(cancelBtn);

        // Tombol "Tambahkan"
        JButton addBtn = new JButton("Tambahkan");
        addBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(0xA0522D));

        // Panel kanan untuk tombol "Tambahkan"
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(addBtn);

        // Tambahkan panel kiri dan kanan ke panel utama
        btnPanel.add(leftPanel, BorderLayout.WEST);
        btnPanel.add(rightPanel, BorderLayout.EAST);

        return btnPanel;
    }

}
