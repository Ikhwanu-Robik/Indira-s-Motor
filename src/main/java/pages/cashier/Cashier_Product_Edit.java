package pages.cashier;

import components.AddInput;
import components.Content_Panel;
import controllers.BrandController;
import controllers.CategoryController;
import controllers.ProductController;
import controllers.TransactionController;
import indira_s_motor.Indira_s_motor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Cashier_Product_Edit {

    private static BiConsumer<Content_Panel, Integer> reloadCallback;
    private static String productPrice;
    private static String productStock;
    private static String productName;
    private static String brandName;
    private static ArrayList<HashMap<String, String>> brands;
    private static String categoryName;
    private static ArrayList<HashMap<String, String>> categories;
    private static JFileChooser fileChooser;
    private static int fileChooserReturnValue = -1;
    private static String imageUrl;
    private static JComboBox<String> brandComboBox;
    private static HashMap<String, JTextField> inputFields = new HashMap<>();
    private static JComboBox<String> categoryComboBox;
    private static String productId;
	private static TransactionController transactionSession;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback, String productName, TransactionController transactionSession) {
        Cashier_Product_Edit.reloadCallback = reloadCallback;
        Cashier_Product_Edit.productName = productName;
        Cashier_Product_Edit.transactionSession = transactionSession;
        fetchDatabase();

        Content_Panel cashierProductEditPanel = createContentPanel();
        return cashierProductEditPanel;
    }

    private static void fetchDatabase() {
        ArrayList<String> all_col = new ArrayList<>();
        all_col.add("*");
        Cashier_Product_Edit.brands = new BrandController().read(all_col);

        all_col.clear();
        all_col.add("*");
        Cashier_Product_Edit.categories = new CategoryController().read(all_col);

        all_col.clear();
        all_col.add("*");
        HashMap<String, String> product = new ProductController().findWhere("name", productName).getFirst();
        Cashier_Product_Edit.productId = product.get("id");
        Cashier_Product_Edit.productPrice = product.get("price");
        Cashier_Product_Edit.productStock = product.get("stock");
        Cashier_Product_Edit.imageUrl = product.get("image_url");

        HashMap<String, String> brand = new BrandController().findWhere("id", product.get("brand_id")).getFirst();
        Cashier_Product_Edit.brandName = brand.get("name");
        Cashier_Product_Edit.categoryName = new CategoryController().findWhere("id", brand.get("category_id")).getFirst().get("name");
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel title = createTitleLabel("Edit Produk", Color.BLACK);
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

        inputPanel.add(createLabeledField("Nama Produk:", productName));
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(createLabeledField("Harga:", productPrice));
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(createLabeledField("Stok:", productStock));

        return inputPanel;
    }

    private static JPanel createLabeledField(String labelText, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JTextField input = new AddInput();
        input.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setText(value);

        inputFields.put(labelText, input);

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

        JLabel brandLabel = new JLabel("Merk:");
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] brands = new String[Cashier_Product_Edit.brands.size()];
        int i = 0;
        for (HashMap<String, String> brand : Cashier_Product_Edit.brands) {
            brands[i++] = brand.get("name");
        }
        JComboBox<String> brandComboBox = new JComboBox<>(brands);
        brandComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        Cashier_Product_Edit.brandComboBox = brandComboBox;

//        Select the product-to-be-edited's brand
        for (int row = 0; row < brands.length; row++) {
            if (brandComboBox.getItemAt(row).trim().equals(brandName)) {
                brandComboBox.setSelectedIndex(row);
            }
        }

        brandPanel.add(brandLabel);
        brandPanel.add(Box.createVerticalStrut(5));
        brandPanel.add(brandComboBox);

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] categories = new String[Cashier_Product_Edit.categories.size()];
        int j = 0;
        for (HashMap<String, String> category : Cashier_Product_Edit.categories) {
            categories[j++] = category.get("name");
        }
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        Cashier_Product_Edit.categoryComboBox = categoryComboBox;

//        Select the product-to-be-edited's category
        for (int row = 0; row < categories.length; row++) {
            if (categoryComboBox.getItemAt(row).trim().equals(categoryName)) {
                categoryComboBox.setSelectedIndex(row);
            }
        }

        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createVerticalStrut(5));
        categoryPanel.add(categoryComboBox);

        mainPanel.add(brandPanel);
        mainPanel.add(categoryPanel);

        return mainPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(0xE0E0E0));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadCallback.accept(Cashier_Product.init(reloadCallback, transactionSession), Integer.valueOf(1));
            }
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(cancelBtn);

        JButton addImageBtn = new JButton("+Gambar");
        addImageBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        addImageBtn.setForeground(Color.BLACK);
        addImageBtn.setBackground(new Color(0xE0E0E0));
        addImageBtn.addActionListener((ActionEvent e) -> {
            final JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files (*.jpg, *.png)", "jpg", "jpeg",
					"png");
			fc.setFileFilter(filter);

			// Optional: disable "All Files" option
			fc.setAcceptAllFileFilterUsed(false);
            int returnVal = fc.showOpenDialog(fc);

            fileChooser = fc;
            fileChooserReturnValue = returnVal;
        });

        JButton addBtn = new JButton("Update");
        addBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(0xA0522D));
        addBtn.addActionListener((ActionEvent e) -> {
            String productId = Cashier_Product_Edit.productId;
            String name = inputFields.get("Nama Produk:").getText();
            String price = inputFields.get("Harga:").getText();
            String stock = inputFields.get("Stok:").getText();
            String brandName = Cashier_Product_Edit.brandComboBox.getSelectedItem().toString();
            String brand_id = null;
            String brand_category_id = null;
            String newImageName = null;
            for (HashMap<String, String> brand : brands) {
                if (brand.get("name").trim().equals(brandName.trim())) {
                    brand_id = brand.get("id");
                    brand_category_id = brand.get("category_id");
                }
            }
            String categoryName = Cashier_Product_Edit.categoryComboBox.getSelectedItem().toString();
            String category_id = null;
            for (HashMap<String, String> category : categories) {
                if (category.get("name").trim().equals(categoryName.trim())) {
                    category_id = category.get("id");
                }
            }

            boolean isBrandMatchCategory = brand_category_id.trim().equals(category_id.trim());
            if (!isBrandMatchCategory) {
                JOptionPane.showMessageDialog(null, "Brand tidak sesuai dengan Kategori");
                return;
            }

            //save the new image
            if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
            	File selectedFile = fileChooser.getSelectedFile();
            	newImageName = selectedFile.getName();

                try {
                    // Create a File object for the "images" directory within the current directory
                    File imagesDir = new File("C:/IndiraMotorKasir/assets");
                    
                    if (!imagesDir.exists()) {
                    	imagesDir.mkdirs();
                    }

                    // Define the target file within the images directory
                    File targetFile = new File(imagesDir, selectedFile.getName());

                    // Copy the selected file to the target location
                    Files.copy(
                            selectedFile.toPath(),
                            targetFile.toPath());

                } catch (IOException ex) {
                    Logger.getLogger(Indira_s_motor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
//           delete the old image
//           TODO : the image cannot be deleted because it is being opened by this program
            String oldImageUrl = new ProductController().findWhere("id", productId).getFirst().get("image_url");
            File oldImage = new File("C:/IndiraMotorKasir/assets/" + oldImageUrl);
            oldImage.delete();

            HashMap<String, String> product_data = new HashMap<>();
            product_data.put("name", name);
            product_data.put("image_url", newImageName);
            product_data.put("price", price);
            product_data.put("stock", stock);
            product_data.put("brand_id", brand_id);
            new ProductController().update(Integer.parseInt(productId), product_data);

            JOptionPane.showMessageDialog(null, "Produk diedit");
            for (String key : inputFields.keySet()) {
                inputFields.get(key).setText("");
            }
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        addImageBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        addBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(addImageBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(addBtn);

        // Tambahkan ke panel utama
        btnPanel.add(leftPanel, BorderLayout.WEST);
        btnPanel.add(rightPanel, BorderLayout.EAST);

        return btnPanel;
    }
}
