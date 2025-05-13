package pages.cashier;

import components.Button_Brown;
import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Cashier_Transaction {

    // Constants
    private static MainFrame transactionFrame = null;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Cashier Dashboard");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        transactionFrame = frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(Cashier_Dashboard.class.getClassLoader().getResource("assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav items
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
                transactionFrame.dispose();
            }
        });
        navBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierBrand.main(new String[0]);
                transactionFrame.dispose();
            }
        });
        navCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierCategory.main(new String[0]);
                transactionFrame.dispose();
            }
        });
        navTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierTransaction.main(new String[0]);
                transactionFrame.dispose();
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

        // Welcome label
        JLabel titleLabel = createTitleLabel("Transaksi", Color.BLACK);

        JScrollPane transactionTable = createTableTransaction();

        JPanel transactionField = createTransactionFieldPanel();

        // Add label to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(transactionTable, gbc);
        contentPanel.add(transactionField, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JScrollPane createTableTransaction() {
        // Nama kolom
        String[] columnNames = {"uuid", "nama_produk", "kategori", "harga", "jumlah", "total"};

        // Data kosong untuk inisialisasi awal
        Object[][] data = {};

        // Buat model tabel
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Buat tabel
        JTable table = new JTable(tableModel);

        // Styling tabel
        table.setBackground(new Color(45, 45, 45));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(Color.DARK_GRAY);

        // Styling header tabel
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setReorderingAllowed(false);

        // Set preferred width kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(400);
        table.getColumnModel().getColumn(1).setPreferredWidth(1000);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);
        table.getColumnModel().getColumn(3).setPreferredWidth(500);
        table.getColumnModel().getColumn(4).setPreferredWidth(500);
        table.getColumnModel().getColumn(5).setPreferredWidth(500);

        // ScrollPane untuk tabel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));

        return scrollPane;
    }

    private static JPanel createTransactionFieldPanel() {
        JPanel transactionField = new JPanel();
        transactionField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Left Panel
        JButton deleteBtn = new JButton("Hapus");
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        deleteBtn.setForeground(Color.BLACK);
        deleteBtn.setBackground(new Color(0xE0E0E0));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(deleteBtn);

        // Right Panel
        JLabel serviceLabel = new JLabel("Jasa:");
        serviceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField serviceField = new JTextField();
        serviceField.setPreferredSize(new Dimension(300, 50));
        JPanel servicePanel = new JPanel();
        servicePanel.add(serviceLabel);
        servicePanel.add(serviceField);

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField totalField = new JTextField();
        totalField.setPreferredSize(new Dimension(300, 50));
        JPanel totalPanel = new JPanel();
        totalPanel.add(totalLabel);
        totalPanel.add(totalField);

        JButton payBtn = new Button_Brown("Bayar");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(servicePanel);
        rightPanel.add(totalPanel);
        rightPanel.add(payBtn);

        transactionField.add(leftPanel, BorderLayout.WEST);
        transactionField.add(rightPanel, BorderLayout.EAST);

        return transactionField;
    }
}
