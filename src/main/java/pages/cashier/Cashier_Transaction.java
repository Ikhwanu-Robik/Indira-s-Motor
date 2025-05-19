package pages.cashier;

import components.Button_Brown;
import components.Content_Panel;
import controllers.CartController;
import controllers.LoginController;
import controllers.OrderController;
import controllers.PrintController;
import controllers.TransactionController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Cashier_Transaction {
	private static BiConsumer<Content_Panel, Integer> reloadCallback;
	private static LoginController loginSession;
	private static ArrayList<HashMap<String, String>> cartProducts;
	private static TransactionController transactionSession;
	private static int total = 0;
	private static JTextField serviceField;
	private static JTextField totalField;
	private static JTable table;
	private static DefaultTableModel tableModel;
 
    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback, LoginController loginSession, TransactionController transactionSession) {
    	Cashier_Transaction.reloadCallback = reloadCallback;
    	Cashier_Transaction.loginSession = loginSession;
    	Cashier_Transaction.transactionSession = transactionSession;
    	fetchDatabase();
        Content_Panel cashierTransactionPanel = createContentPanel();

        return cashierTransactionPanel;
    }
    
    public static void fetchDatabase() {
    	if (new CartController().checkCartExistence(loginSession.authenticated_user_id) != -1) {
    		cartProducts = new CartController().getCartProducts(transactionSession.cart_id);
    	} else {
    		cartProducts = new ArrayList<HashMap<String, String>>();
    	}
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
        String[] columnNames = {"uuid", "nama_produk", "kategori", "harga", "jumlah", "total", "product_id"};

        // Data kosong untuk inisialisasi awal
        Object[][] data = new Object[0][7];
        if (cartProducts != null) {
        	data = new Object[cartProducts.size()][7];
            int i = 0;
            for (HashMap<String, String> cartProduct : cartProducts) {
            	data[i][0] = cartProduct.get("id");
            	data[i][1] = cartProduct.get("name");
            	data[i][2] = cartProduct.get("category_name");
            	data[i][3] = cartProduct.get("price");
            	data[i][4] = cartProduct.get("qty");
            	data[i][5] = Integer.parseInt(cartProduct.get("qty")) * Integer.parseInt(cartProduct.get("price"));
            	data[i][6] = cartProduct.get("product_id");
            	
            	i++;
            }
        }
        

        // Buat model tabel
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        Cashier_Transaction.tableModel = tableModel;
        
        // Buat tabel
        JTable table = new JTable(tableModel);
        Cashier_Transaction.table = table;
        
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
    
    private static void countTotal() {
    	if (table.getRowCount() == 0) {
    		total = 0;
    	}
    	else {
    		total = 0;
    		for (int row = 0; row < table.getRowCount(); row++) {
        		int currentTotal = Integer.parseInt(table.getValueAt(row, 5).toString());
        		total += currentTotal;
        	}
    	}
    }
    
    private static void updateTotalField() {
    	int fee = 0;
    	if (!serviceField.getText().equals("")) {
    		fee = Integer.parseInt(serviceField.getText());
    	}
    	countTotal();
    	totalField.setText(Integer.toString(fee + total));
    }

    private static JPanel createTransactionFieldPanel() {
        JPanel transactionField = new JPanel();
        transactionField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Left Panel
        JButton deleteBtn = new JButton("Hapus");
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        deleteBtn.setForeground(Color.BLACK);
        deleteBtn.setBackground(new Color(0xE0E0E0));
        deleteBtn.addActionListener((e) -> {
        	int row = table.getSelectedRow();
        	int productId = Integer.parseInt(tableModel.getValueAt(row, 6).toString());
        	
        	tableModel.removeRow(row);
        	
        	new CartController().removeProduct(productId, transactionSession.cart_id);
        	
        	updateTotalField();
        });
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(deleteBtn);

        // Right Panel
        JLabel serviceLabel = new JLabel("Jasa:");
        serviceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField serviceField = new JTextField();
        ((AbstractDocument) serviceField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        Cashier_Transaction.serviceField = serviceField;
        serviceField.setPreferredSize(new Dimension(300, 50));
        serviceField.setText("0");
        serviceField.getDocument().addDocumentListener(new DocumentListener() {
        	public void insertUpdate(DocumentEvent e) { updateTotalField(); }
            public void removeUpdate(DocumentEvent e) { updateTotalField(); }
            public void changedUpdate(DocumentEvent e) { updateTotalField(); }
        });
        JPanel servicePanel = new JPanel();
        servicePanel.add(serviceLabel);
        servicePanel.add(serviceField);

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField totalField = new JTextField();
        Cashier_Transaction.totalField = totalField;
        totalField.setPreferredSize(new Dimension(300, 50));
        totalField.setEditable(false);
        totalField.setText(Integer.toString(Integer.parseInt(serviceField.getText()) + total));
        JPanel totalPanel = new JPanel();
        totalPanel.add(totalLabel);
        totalPanel.add(totalField);

        updateTotalField();
        
        JButton payBtn = new Button_Brown("Bayar");
        payBtn.addActionListener((e) -> {
        	if (table.getRowCount() == 0) {
        		JOptionPane.showMessageDialog(null, "Belum ada produknya UwU");
        	}
        	else {
        		try {
        			transactionSession.makeOrder(transactionSession.cart_id, Integer.parseInt(serviceField.getText()));
        			
        			int response = JOptionPane.showConfirmDialog(null, "Transaksi berhasil!, Apakah notanya mau dicetak?", "BERHASIL!", JOptionPane.YES_NO_OPTION);
                	if (response == 0) {
                		ArrayList<HashMap<String, String>> data = new ArrayList<>();
                        HashMap<String, String> type = new HashMap<>();
                        type.put("type", "receipt");
                        data.add(type);
                        //get data / latest order
                        ArrayList<String> columns = new ArrayList<>();
                        columns.add("*");
                        HashMap<String, String> order = new OrderController().read(columns).getLast();
                        data.add(order);
                        
                        try {
                           new PrintController().print(data);
                        } catch (IOException er) {
                            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                	};
                	
                	transactionSession.createCartIfMissing();
                	reloadCallback.accept(Cashier_Transaction.init(reloadCallback, loginSession, transactionSession), Integer.valueOf(4));
        		} catch (Exception e1) {
        			System.err.println(e1);
        		}
        	}
        });

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
