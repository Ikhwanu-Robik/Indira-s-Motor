package pages.cashier;

import components.AddInput;
import components.Content_Panel;
import controllers.BrandController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Cashier_Brand_Add {

    private static Consumer<Content_Panel> reloadCallback;
    private static ArrayList<HashMap<String, String>> categories;
    private static JTextField brandNameField;
    private static JComboBox<String> categoryCombo;

    public static Content_Panel init(Consumer<Content_Panel> reloadCallback, ArrayList<HashMap<String, String>> categories) {
        Cashier_Brand_Add.reloadCallback = reloadCallback;
        Cashier_Brand_Add.categories = categories;

        Content_Panel cashierBrandAddPanel = createContentPanel();

        return cashierBrandAddPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel title = createTitleLabel("Tambah Merk", Color.BLACK);
        JPanel field = createInputPanel();
        JPanel dropDown = createDropDownPanel();
        JPanel buttonPanel = createButtonPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);

        contentPanel.add(title, gbc);
        contentPanel.add(field, gbc);
        contentPanel.add(dropDown, gbc);
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

        inputPanel.add(createLabeledField("Nama Merk:"));
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
        
        Cashier_Brand_Add.brandNameField = input;

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(input);

        return panel;
    }

    private static JPanel createDropDownPanel() {
        // Panel utama untuk dua dropdown disusun horizontal
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] categoriesArray = new String[Cashier_Brand_Add.categories.size()];
        int i = 0;
        for (HashMap<String, String> category : categories) {
            categoriesArray[i++] = category.get("name");
        }
        JComboBox<String> categoryComboBox = new JComboBox<>(categoriesArray);
        categoryComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        Cashier_Brand_Add.categoryCombo = categoryComboBox;

        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createVerticalStrut(5));
        categoryPanel.add(categoryComboBox);

        mainPanel.add(brandPanel);
        mainPanel.add(categoryPanel);

        return mainPanel;
    }
    
    private static String getCategoryId(String categoryName) {
        String id = null;
        for (HashMap<String, String> category : categories) {
            if (category.get("name").trim().equals(categoryName.trim())) {
                id = category.get("id");
            }
        }
        return id;
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
                reloadCallback.accept(Cashier_Brand.init(reloadCallback));
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
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String name = Cashier_Brand_Add.brandNameField.getText();
                String categoryId = getCategoryId(Cashier_Brand_Add.categoryCombo.getSelectedItem().toString());
                
                HashMap<String, String> values = new HashMap<>();
                values.put("name", name);
                values.put("category_id", categoryId);
                
                new BrandController().create(values);
                
                JOptionPane.showMessageDialog(null, "Berhasil menambahkan Merek");
                brandNameField.setText("");
            }
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
