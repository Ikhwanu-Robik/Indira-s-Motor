package pages.cashier;

import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import controllers.CartController;
import controllers.ReportController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Cashier_Brand {

    // Constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
    private static final Color BROWN_COLOR = new Color(0xA0522D);
    private static MainFrame brandFrame = null;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Cashier Dashboard");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        brandFrame = frame;
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
        NavLabel navReport = new NavLabel("Transaksi", false);
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navBrand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navCategory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        Cashier_Product cashierProduct = new Cashier_Product();
        Cashier_Brand cashierBrand = new Cashier_Brand();

        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brandFrame.dispose();
                cashierProduct.main(new String[0]);
            }
        });
        navBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashierBrand.main(new String[0]);
                brandFrame.dispose();
            }
        });
        // nav.addMouseListener(new java.awt.event.MouseAdapter() {
        //     @Override
        //     public void mouseClicked(java.awt.event.MouseEvent evt) {
        //         adminReport.main(new String[0]);
        //         dashboardFrame.dispose();
        //     }
        // });
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

        // Welcome label
        JLabel titleLabel = createTitleLabel("Merk Produk", Color.black);
        JScrollPane tableBrands = createTableBrands();
        JPanel buttonPanel = createButtonPanel();

        // Add label to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(tableBrands, gbc);
        contentPanel.add(buttonPanel, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(TITLE_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JScrollPane createTableBrands() {
        // Nama kolom
        String[] columnNames = {"id_merk", "merk", "kategori"};

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
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);

        // ScrollPane untuk tabel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));

        return scrollPane;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JButton cancelBtn = new JButton("Hapus");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(0xE0E0E0));

        Cashier_Brand_Add brandAddPage = new Cashier_Brand_Add();
        JButton editBtn = new JButton("Tambah Merk+");
        editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        editBtn.setForeground(Color.WHITE);
        editBtn.setBackground(new Color(0xA0522D));
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brandFrame.dispose();
                brandAddPage.main(new String[0]);
            }
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(cancelBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(editBtn);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        return buttonPanel;
    }
}
