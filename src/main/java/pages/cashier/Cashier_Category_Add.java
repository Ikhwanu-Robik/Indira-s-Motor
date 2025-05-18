package pages.cashier;

import components.AddInput;
import components.Content_Panel;
import components.Nav_Panel;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import controllers.BrandController;
import controllers.CategoryController;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JButton;

public class Cashier_Category_Add {
	private static BiConsumer<Content_Panel, Integer> reloadCallback;
	private static JTextField categoryNameField;
	
    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback) {
    	Cashier_Category_Add.reloadCallback = reloadCallback;
        Content_Panel cashierCategoryAddPanel = createContentPanel();

        return cashierCategoryAddPanel;
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
        Cashier_Category_Add.categoryNameField = input;

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(input);

        return panel;
    }

    private static JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Tombol "Batal"
        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(0xE0E0E0));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadCallback.accept(Cashier_Category.init(reloadCallback), Integer.valueOf(3));
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
        addBtn.addActionListener((e) -> {
        	String name = Cashier_Category_Add.categoryNameField.getText();
            
            HashMap<String, String> values = new HashMap<>();
            values.put("name", name);
            
            new CategoryController().create(values);
            
            JOptionPane.showMessageDialog(null, "Berhasil menambahkan Kategori");
            categoryNameField.setText("");
        });

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
