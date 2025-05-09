package pages.admin;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import components.Content_Panel;
import controllers.CartController;
import controllers.ReportController;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Admin_Report {
    private static ArrayList<HashMap<String, String>> reports = null;

    public static Content_Panel init() {
        Content_Panel adminReportPanel = createContentPanel();

        return adminReportPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = createTitleLabel("Laporan Penjualan", new Color(0x00000));
        JScrollPane reportTable = createTableReport();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(reportTable, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JScrollPane createTableReport() {
        String[] columnNames = {"tanggal", "nama_kasir", "jumlah_produk", "total", "jasa", "detail", "cart_id"};

        reports = new ReportController().getReports();

        Object[][] data = new Object[reports.size()][columnNames.length];

        int i = 0;
        for (HashMap<String, String> report : reports) {
            data[i] = new Object[]{
                report.get("date"), // perbaiki urutan kolom sesuai header
                report.get("username"),
                report.get("product_types"),
                report.get("total"),
                report.get("fee"),
                "Detail",
                report.get("cart_id")
            };
            i++;
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        JTable table = new JTable(tableModel);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn hiddenColumn = columnModel.getColumn(6);
        columnModel.removeColumn(hiddenColumn);

        // Styling tabel
        table.setBackground(new Color(45, 45, 45)); // background gelap
        table.setForeground(Color.WHITE); // teks putih
        table.setFont(new Font("SansSerif", Font.PLAIN, 14)); // font lebih besar
        table.setRowHeight(28); // tinggi baris
        table.setGridColor(Color.DARK_GRAY); // warna garis pemisah

        // Styling header tabel
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setReorderingAllowed(false); // mencegah drag kolom

        // Set preferred width kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);

        class ButtonRenderer extends JButton implements TableCellRenderer {

            public ButtonRenderer() {
                setOpaque(true);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                setText((value == null) ? "Detail" : value.toString());
                return this;
            }
        }

        class ButtonEditor extends DefaultCellEditor {

            protected JButton button;
            private String label;
            private boolean clicked;
            private JTable table;

            public ButtonEditor(JCheckBox checkbox, JTable table) {
                super(checkbox);
                this.table = table;
                button = new JButton();
                button.setOpaque(true);

                button.addActionListener(e -> fireEditingStopped());
            }

            @Override
            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected, int row, int column) {
                label = (value == null) ? "View" : value.toString();
                button.setText(label);
                clicked = true;
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                if (clicked) {
                    int row = table.getSelectedRow();
                    String cartId = tableModel.getValueAt(row, 6).toString();

                    ArrayList<HashMap<String, String>> cartProducts = new CartController()
                            .getCartProducts(Integer.parseInt(cartId));

//                    Admin_Report_Detail.main(cartProducts);
                }
                clicked = false;
                return label;
            }

            @Override
            public boolean stopCellEditing() {
                clicked = false;
                return super.stopCellEditing();
            }

            @Override
            protected void fireEditingStopped() {
                super.fireEditingStopped();
            }
        }

        table.getColumn("detail").setCellRenderer(new ButtonRenderer());
        table.getColumn("detail").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        table.getColumnModel().getColumn(0).setPreferredWidth(120); // tangal
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // nama_kasir
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // jumlah_produk
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // total
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // jasa
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // detail (boolean)

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45)); // latar belakang scrollpane sama

        return scrollPane;
    }

}
