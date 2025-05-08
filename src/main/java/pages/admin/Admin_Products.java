package pages.admin;

import java.awt.BorderLayout;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import components.Content_Panel;
import components.Nav_Panel;
import components.Product_Card;
import components.Search_Bar;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import controllers.BrandController;
import controllers.CategoryController;
import controllers.ProductController;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Admin_Products {

    // Constants
    private static MainFrame adminProductFrame = null;
    private static Search_Bar searchBar = null;
//    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
    public static JPanel cardPanel = null;
    private static ArrayList<HashMap<String, String>> categories = null;
    private static ArrayList<HashMap<String, String>> brands = null;
    private static ArrayList<HashMap<String, String>> products = null;

    public void main(String[] args) {
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
        // TODO : upon usage, the value of all_col is changed with brand's columns. FIND OUT WHY
        all_col.clear();
        all_col.add("*");
        Admin_Products.categories = new CategoryController().read(all_col);

        MainFrame frame = new MainFrame("Admin Products");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        adminProductFrame = frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(new Admin_Cashier().getClass().getResource("/assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav links
        NavLabel navEmployee = new NavLabel("Pegawai", false);
        NavLabel navProduct = new NavLabel("Produk", true);
        NavLabel navReport = new NavLabel("Laporan", false);
        navEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        Admin_Cashier adminCashier = new Admin_Cashier();
        Admin_Report adminReport = new Admin_Report();

        navEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminCashier.main(new String[0]);
                adminProductFrame.dispose();
            }
        });

        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminReport.main(new String[0]);
                adminProductFrame.dispose();
            }
        });

        // Add components to nav panel
        navPanel.add(Box.createVerticalStrut(70));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(80));
        navPanel.add(navEmployee);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navProduct);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navReport);
        navPanel.add(Box.createVerticalStrut(150));
        navPanel.add(logoutBtn);

        return navPanel;
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
