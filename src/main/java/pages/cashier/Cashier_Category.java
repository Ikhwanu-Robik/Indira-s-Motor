package pages.cashier;

import components.Content_Panel;
import controllers.CategoryController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Cashier_Category {

    // Constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
    private static BiConsumer<Content_Panel, Integer> reloadCallback;
	private static ArrayList<HashMap<String, String>> categories;
	private static JTable categoryTable;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback) {
    	Cashier_Category.reloadCallback = reloadCallback;
    	
    	fetchDatabase();
    	
        Content_Panel cashierCategoryPanel = createContentPanel();

        return cashierCategoryPanel;
    }
    
    private static void fetchDatabase() {
        ArrayList<String> all_col = new ArrayList<>();
        all_col.add("*");
        Cashier_Category.categories = new CategoryController().read(all_col);
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        // Welcome label
        JLabel titleLabel = createTitleLabel("Kategori Produk", Color.black);
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
        String[] columnNames = {"id_kategori", "kategori"};

        // Data kosong untuk inisialisasi awal
        Object[][] data = {};
        
        if (categories != null) {
        	int brandsCount = categories.size();
            int i = 0;
            data = new Object[brandsCount][3];
            for (HashMap<String, String> category : categories) {
                data[i][0] = category.get("id");
                data[i][1] = category.get("name");
                
                i++;
            }
        }

        // Buat model tabel
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Buat tabel
        JTable table = new JTable(tableModel);
        Cashier_Category.categoryTable = table;

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
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int response = JOptionPane.showConfirmDialog(null, "Semua Produk dari Merek ini akan ikut terhapus", "PERINGATAN!", JOptionPane.WARNING_MESSAGE);
                
                if (response == JOptionPane.YES_OPTION) {
                    int row = categoryTable.getSelectedRow();
                    int brandId = Integer.parseInt(categoryTable.getValueAt(row, 0).toString());
                    
                    new CategoryController().delete(brandId);
                   
                    JOptionPane.showMessageDialog(null, "Merek dan semua produknya dihapus.");
                    reloadCallback.accept(Cashier_Category.init(reloadCallback), Integer.valueOf(3));
                }
            }
        });

        JButton editBtn = new JButton("Tambah Kategori+");
        editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        editBtn.setForeground(Color.WHITE);
        editBtn.setBackground(new Color(0xA0522D));
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadCallback.accept(Cashier_Category_Add.init(reloadCallback), Integer.valueOf(3));
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
