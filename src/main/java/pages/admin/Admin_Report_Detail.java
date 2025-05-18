package pages.admin;

import components.Button_Brown;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import components.Content_Panel;
import controllers.CartController;
import controllers.OrderController;
import controllers.PrintController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jdatepicker.impl.*;
import java.util.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Admin_Report_Detail {
	private static String cartId;
    private static ArrayList<HashMap<String, String>> products = null;
    private static Consumer<Content_Panel> reloadCallback;
	private static String date;
	private static String cashierName;
	private static HashMap<String, String> order;

    public static Content_Panel init(String cartId, String date, String cashierName, Consumer<Content_Panel> reloadCallback) {
    	Admin_Report_Detail.cartId = cartId;
    	Admin_Report_Detail.date = date;
    	Admin_Report_Detail.cashierName = cashierName;
    	Admin_Report_Detail.order = new OrderController().findWhere("cart_id", cartId).getLast();
        products = new CartController()
                .getCartProducts(Integer.parseInt(cartId));
    	
        Admin_Report_Detail.reloadCallback = reloadCallback;

        Content_Panel contentPanel = createContentPanel();

        return contentPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = createTitleLabel("Laporan Penjualan", new Color(0x00000));
        JLabel reportCashier = createTitleLabel("Kasir : " + cashierName, new Color(0x00000));
        JLabel reportDate = createTitleLabel("Tanggal : " + date, new Color(0x00000));
        JScrollPane tableDetails = createTableDetails();
        JLabel totalLabel = createTitleLabel("Total : " + new CartController().getTotal(Integer.parseInt(cartId)), new Color(0x00000));
        JLabel feeLabel = createTitleLabel("Jasa : " + order.get("fee"), new Color(0x00000));
        JPanel btnPanel = createButtonPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(reportCashier, gbc);
        contentPanel.add(reportDate, gbc);
        contentPanel.add(tableDetails, gbc);
        contentPanel.add(totalLabel, gbc);
        contentPanel.add(feeLabel, gbc);
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
        String[] columnNames = {"Produk", "Jumlah", "Harga", "Total"};

        // Data kosong untuk inisialisasi awal
        Object[][] data = {};
        if (products != null) {
        	data = new Object[products.size()][4];
        	int i = 0;
            for (HashMap<String, String> product : products) {
            	data[i][0] = product.get("name");
            	data[i][1] = product.get("qty");
            	data[i][2] = product.get("price");
            	data[i][3] = Integer.parseInt(product.get("qty")) * Integer.parseInt(product.get("price"));
            }
        }
         
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

        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadCallback.accept(Admin_Report.init(reloadCallback));
            }
        });
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(backBtn);

        JButton printBtn = new Button_Brown("Cetak Struk");
        printBtn.addActionListener((e) -> {
        	ArrayList<HashMap<String, String>> data = new ArrayList<>();
        	HashMap<String, String> type = new HashMap<>();
        	type.put("type", "receipt");
        	data.add(type);
        	data.add(order);
        	
        	try {
				new PrintController().print(data);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Something is wrong " + e1, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(printBtn);

        btnPanel.add(leftPanel, BorderLayout.WEST);
        btnPanel.add(rightPanel, BorderLayout.EAST);

        return btnPanel;
    }
}
    