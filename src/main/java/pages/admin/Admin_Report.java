package pages.admin;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import components.Content_Panel;
import controllers.PrintController;
import controllers.ReportController;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.function.BiConsumer;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Admin_Report {

    private static ArrayList<HashMap<String, String>> reports = null;
    private static BiConsumer<Content_Panel, Integer> reloadCallback;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback) {
        Admin_Report.reloadCallback = reloadCallback;
        reports = new ReportController().getReports();

        Content_Panel adminReportPanel = createContentPanel();

        return adminReportPanel;
    }

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback, ArrayList<HashMap<String, String>> filteredReport) {
        Admin_Report.reloadCallback = reloadCallback;
        reports = filteredReport;

        Content_Panel adminReportPanel = createContentPanel();

        return adminReportPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = createTitleLabel("Laporan Penjualan", new Color(0x00000));
        JPanel filterPanel = createFilterPanel();
        JButton printBtn = createPrintButton();
        JScrollPane reportTable = createTableReport();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(filterPanel, gbc);
        contentPanel.add(printBtn, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
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

    private static JButton createPrintButton() {
        JButton printBtn = new JButton();
        printBtn.setText("Print");
        printBtn.addActionListener((e) -> {
            ArrayList<HashMap<String, String>> data = new ArrayList<>();
            HashMap<String, String> type = new HashMap<>();
            type.put("type", "report");
            data.add(type);

            for (HashMap<String, String> report_row : reports) {
                data.add(report_row);
            }

            try {
                new PrintController().print(data);
            } catch (IOException er) {
                JOptionPane.showMessageDialog(null, "Gagal mengprint data : " + er.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        return printBtn;
    }

    private static JScrollPane createTableReport() {
        String[] columnNames = {"tanggal", "nama_kasir", "jumlah_produk", "total", "jasa", "detail", "cart_id"};

        Object[][] data = {};

        if (reports != null) {
            data = new Object[reports.size()][columnNames.length];

            int i = 0;
            for (HashMap<String, String> report : reports) {
                data[i] = new Object[]{
                    report.get("date"),
                    report.get("username"),
                    report.get("product_types"),
                    report.get("total"),
                    report.get("fee"),
                    "Detail",
                    report.get("cart_id")
                };
                i++;
            }
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
                    String date = tableModel.getValueAt(row, 0).toString();
                    String username = tableModel.getValueAt(row, 1).toString();

                    reloadCallback.accept(Admin_Report_Detail.init(cartId, date, username, reloadCallback), Integer.valueOf(3));
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

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(180);
        table.getColumnModel().getColumn(4).setPreferredWidth(180);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.getViewport().setBackground(new Color(45, 45, 45));

        return scrollPane;
    }

    public static JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();

        // ComboBox Timeline
        String[] time = {"Sebelum", "Saat", "Sesudah"};
        JComboBox<String> timeline = new JComboBox<>(time);
        timeline.setBounds(20, 20, 120, 30);
        filterPanel.add(timeline);

        // Model dan Properti untuk DatePicker
        UtilDateModel model = new UtilDateModel();
        model.setSelected(true); // Menandai tanggal saat ini sebagai default

        Properties p = new Properties();
        p.put("text.today", "Hari Ini");
        p.put("text.month", "Bulan");
        p.put("text.year", "Tahun");

        ImageIcon calendarIcon = new ImageIcon(Admin_Report.class.getResource("assets/calendar_icon.png"));

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(160, 20, 150, 30);
        filterPanel.add(datePicker);

        for (Component comp : datePicker.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setIcon(calendarIcon); // Ganti ikon
                button.setText("");           // Hapus teks jika perlu
                button.setMargin(new Insets(0, 0, 0, 0)); // Opsional: hilangkan margin
            }
        }

        JButton applyFilterBtn = new JButton("Apply Filter");
        applyFilterBtn.addActionListener((e) -> {
            int date = model.getDay();
//        	because the month is represented from 0 to 11
            int month = model.getMonth() + 1;
            int year = model.getYear();
            int when = timeline.getSelectedIndex();

            ArrayList<HashMap<String, String>> filteredReport = new ReportController().filter(when, date, month, year);
            reloadCallback.accept(Admin_Report.init(reloadCallback, filteredReport), Integer.valueOf(3));
        });
        filterPanel.add(applyFilterBtn);

        return filterPanel;
    }

    // Formatter untuk DatePicker
    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

}
