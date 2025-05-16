package pages.admin;

import components.Button_Brown;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import components.Content_Panel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Admin_Report_Detail {

    private static ArrayList<HashMap<String, String>> products = null;
    private static Consumer<Content_Panel> reloadCallback;

    public static Content_Panel init(ArrayList<HashMap<String, String>> products, Consumer<Content_Panel> reloadCallback) {
        Admin_Report_Detail.products = products;
        Admin_Report_Detail.reloadCallback = reloadCallback;

        Content_Panel contentPanel = createContentPanel();

        return contentPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = createTitleLabel("Laporan Penjualan", new Color(0x00000));
        JScrollPane tableDetails = createTableDetails();
        JPanel btnPanel = createButtonPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(tableDetails, gbc);
        contentPanel.add(btnPanel, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JScrollPane createTableDetails() {
        // Nama kolom
        String[] columnNames = {"nama_kasir", "tanggal", "kategori", "produk", "jumlah", "total"};

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
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);

        // ScrollPane untuk tabel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));

        return scrollPane;
    }

    public static JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton backBtn = new JButton("Kembali");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        backBtn.setForeground(Color.BLACK);
        backBtn.setBackground(new Color(0xE0E0E0));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(backBtn);

        JButton printBtn = new Button_Brown("Cetak Struk");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(printBtn);

        btnPanel.add(leftPanel, BorderLayout.WEST);
        btnPanel.add(rightPanel, BorderLayout.EAST);

        return btnPanel;
    }

}
