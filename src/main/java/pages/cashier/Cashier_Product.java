package pages.cashier;

import components.Content_Panel;
import components.Product_Card;
import components.Search_Bar;
import components.ui.AddButton;
import controllers.BrandController;
import controllers.CartController;
import controllers.CategoryController;
import controllers.ProductController;
import controllers.TransactionController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pages.admin.Admin_Products;

public class Cashier_Product {
    private static Search_Bar searchBar = null;
    private static JPanel cardPanel = null;
    private static ArrayList<HashMap<String, String>> categories = null;
    private static ArrayList<HashMap<String, String>> brands = null;
    private static ArrayList<HashMap<String, String>> products = null;
    private static BiConsumer<Content_Panel, Integer> reloadCallback;
	private static TransactionController transactionSession;
	private static ArrayList<HashMap<String, String>> cartProducts;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback, TransactionController transactionSession) {
        Cashier_Product.reloadCallback = reloadCallback;
        Cashier_Product.transactionSession = transactionSession;
        fetchDatabase();

        Content_Panel cashierProductPanel = createContentPanel();
        return cashierProductPanel;
    }
    
    private static void fetchDatabase() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("name");
        columns.add("price");
        columns.add("brand_id");
        columns.add("image_url");
        columns.add("stock");
        Cashier_Product.products = new ProductController().read(columns);
        ArrayList<String> all_col = new ArrayList<>();
        all_col.add("*");
        Cashier_Product.brands = new BrandController().read(all_col);
        all_col.clear();
        all_col.add("*");
        Cashier_Product.categories = new CategoryController().read(all_col);
        
        Cashier_Product.cartProducts = new CartController().getCartProducts(transactionSession.cart_id);
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new BorderLayout(10, 10)); // spacing between components

        JPanel searchPanel = createFilterPanel();
        JScrollPane cardScrollPane = new JScrollPane(createCardPanel());
        cardScrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(cardScrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private static JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new BorderLayout());

        // Panel kiri untuk search dan filter
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchBar = new Search_Bar();
        leftPanel.add(searchBar);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener((ActionEvent e) -> {
            String id = searchBar.getSearchText();
            displayProducts(id);
        });
        leftPanel.add(searchBtn);

        JButton filterBtn = new JButton("Apply Filter");
        filterBtn.addActionListener((ActionEvent e) -> {
            String brandName = searchBar.getSelectedBrand();
            String categoryName = searchBar.getSelectedCategory();
            displayProducts(categoryName, brandName);
        });
        leftPanel.add(filterBtn);

        filterPanel.add(leftPanel, BorderLayout.WEST);

        // Tombol "Tambah Produk" di kanan
        JButton addButton = new AddButton("Tambah Produk");
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadCallback.accept(Cashier_Product_Add.init(reloadCallback, transactionSession), Integer.valueOf(1));
            }
        });
        rightPanel.add(addButton);

        filterPanel.add(rightPanel, BorderLayout.EAST);

        return filterPanel;
    }

    private static void displayProducts() {
        cardPanel.removeAll();

        for (HashMap<String, String> product : products) {
            HashMap<String, String> productBrand = null;
            for (HashMap<String, String> brand : brands) {
                if (product.get("brand_id").equals(brand.get("id"))) {
                    productBrand = brand;
                    break;
                }
            }

            String productCategory = null;
            for (HashMap<String, String> category : categories) {
                if (productBrand.get("category_id").equals(category.get("id"))) {
                    productCategory = category.get("name");
                    break;
                }
            }
            
            int qtyInCart = 0;
            if (cartProducts != null) {
            	for (HashMap<String, String> cartProduct : cartProducts) {
                	if (product.get("id").trim().equals(cartProduct.get("product_id").trim())) {
                		qtyInCart = Integer.parseInt(cartProduct.get("qty"));
                	}
                }
            }
            cardPanel.add(new Product_Card(reloadCallback, true, product.get("id"), product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url"), productCategory, transactionSession, product.get("stock"), qtyInCart));
        }

        cardPanel.updateUI();
    }

    private static void displayProducts(String id) {
        cardPanel.removeAll();

        HashMap<String, String> product = null;
        for (HashMap<String, String> productObj : products) {
            if (productObj.get("id").equals(id)) {
                product = productObj;
                break;
            }
        }

        if (product != null) {
            HashMap<String, String> productBrand = null;
            for (HashMap<String, String> brand : brands) {
                if (product.get("brand_id").equals(brand.get("id"))) {
                    productBrand = brand;
                    break;
                }
            }

            String productCategory = null;
            for (HashMap<String, String> category : categories) {
                if (productBrand.get("category_id").equals(category.get("id"))) {
                    productCategory = category.get("name");
                    break;
                }
            }

            int qtyInCart = 0;
            if (cartProducts != null) {
            	for (HashMap<String, String> cartProduct : cartProducts) {
                	if (product.get("id").trim().equals(cartProduct.get("product_id").trim())) {
                		qtyInCart = Integer.parseInt(cartProduct.get("qty"));
                	}
                }
            }
            cardPanel.add(new Product_Card(reloadCallback, true, product.get("id"), product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url"), productCategory, transactionSession, product.get("stock"), qtyInCart));
        } else {
            cardPanel.add(new JLabel("Produk tidak ditemukan"));
        }

        cardPanel.updateUI();
    }

    private static void displayProducts(String categoryName, String brandName) {
        cardPanel.removeAll();

        for (HashMap<String, String> product : products) {
            HashMap<String, String> productBrand = null;
            for (HashMap<String, String> brand : brands) {
                if (product.get("brand_id").equals(brand.get("id"))) {
                    productBrand = brand;
                    break;
                }
            }

            String productCategory = null;
            for (HashMap<String, String> category : categories) {
                if (productBrand.get("category_id").equals(category.get("id"))) {
                    productCategory = category.get("name");
                    break;
                }
            }

            int qtyInCart = 0;
            if (cartProducts != null) {
            	for (HashMap<String, String> cartProduct : cartProducts) {
                	if (product.get("id").trim().equals(cartProduct.get("product_id").trim())) {
                		qtyInCart = Integer.parseInt(cartProduct.get("qty"));
                	}
                }
            }
            if (categoryName.equals(productCategory) && brandName.equals(productBrand.get("name"))) {
                cardPanel.add(new Product_Card(reloadCallback, true, product.get("id"), product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url"), productCategory, transactionSession, product.get("stock"), qtyInCart));
            }
        }

        cardPanel.updateUI();
    }

    private static JPanel createCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Grid layout untuk kartu

        if (products != null) {
            Admin_Products.cardPanel = cardPanel;
            displayProducts();
        } else {
            System.out.println("No category found");
        }

        return cardPanel;
    }
}
