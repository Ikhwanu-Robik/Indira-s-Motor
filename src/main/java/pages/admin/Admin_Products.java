package pages.admin;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import components.Content_Panel;
import components.Product_Card;
import components.Search_Bar;
import controllers.BrandController;
import controllers.CategoryController;
import controllers.ProductController;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Admin_Products {

    private static Search_Bar searchBar = null;
    public static JPanel cardPanel = null;
    private static ArrayList<HashMap<String, String>> categories = null;
    private static ArrayList<HashMap<String, String>> brands = null;
    private static ArrayList<HashMap<String, String>> products = null;

    public static Content_Panel init() {
        fetchDatabase();

        Content_Panel adminProductsPanel = createContentPanel();

        return adminProductsPanel;
    }

    private static void fetchDatabase() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("name");
        columns.add("price");
        columns.add("brand_id");
        columns.add("image_url");
        Admin_Products.products = new ProductController().read(columns);
        ArrayList<String> all_col = new ArrayList<>();
        all_col.add("*");
        Admin_Products.brands = new BrandController().read(all_col);
        all_col.clear();
        all_col.add("*");
        Admin_Products.categories = new CategoryController().read(all_col);
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
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchBar = new Search_Bar();
        filterPanel.add(searchBar);

        JButton searchBtn = new JButton();
        JLabel searchBtnLabel = new JLabel("Search");
        searchBtn.add(searchBtnLabel);
        filterPanel.add(searchBtn);
        searchBtn.addActionListener((ActionEvent e) -> {
            String id = searchBar.getSearchText();

            displayProducts(id);
        });

        JButton filterBtn = new JButton();
        JLabel filterBtnLabel = new JLabel("Apply Filter");
        filterBtn.add(filterBtnLabel);
        filterPanel.add(filterBtn);
        filterBtn.addActionListener((ActionEvent e) -> {
            String brandName = searchBar.getSelectedBrand();
            String categoryName = searchBar.getSelectedCategory();

            displayProducts(categoryName, brandName);
        });

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

            cardPanel.add(new Product_Card(product.get("id"), product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url"), productCategory));
        }
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

            cardPanel.add(new Product_Card(product.get("id"), product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url"), productCategory));
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

            if (categoryName.equals(productCategory) && brandName.equals(productBrand.get("name"))) {
                cardPanel.add(new Product_Card(product.get("id"), product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url"), productCategory));
            }
        }

        cardPanel.updateUI();
    }

    private static JPanel createCardPanel() {
        JPanel cardPanel = new JPanel();
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
