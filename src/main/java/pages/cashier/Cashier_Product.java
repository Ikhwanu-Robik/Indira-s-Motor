package pages.cashier;

import components.Content_Panel;
import components.Nav_Panel;
import components.Product_Card;
import components.Search_Bar;
import components.ui.AddButton;
import components.ui.LogoutButton;
import components.ui.MainFrame;
import components.ui.NavLabel;
import controllers.BrandController;
import controllers.CategoryController;
import controllers.ProductController;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pages.admin.Admin_Products;

public class Cashier_Product {

    // Constants
    private static MainFrame productFrame = null;
    private static Search_Bar searchBar = null;
//    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
    private static JPanel cardPanel = null;
    private static ArrayList<HashMap<String, String>> categories = null;
    private static ArrayList<HashMap<String, String>> brands = null;
    private static ArrayList<HashMap<String, String>> products = null;

    public static void main(String[] args) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("name");
        columns.add("price");
        columns.add("brand_id");
        columns.add("image_url");
        Cashier_Product.products = new ProductController().read(columns);
        ArrayList<String> all_col = new ArrayList<>();
        all_col.add("*");
        Cashier_Product.brands = new BrandController().read(all_col);
        // TODO : upon usage, the value of all_col is changed with brand's columns. FIND OUT WHY
        all_col.clear();
        all_col.add("*");
        Cashier_Product.categories = new CategoryController().read(all_col);

        MainFrame frame = new MainFrame("Cashier Dashboard");

        Nav_Panel navPanel = createNavPanel();
        Content_Panel contentPanel = createContentPanel();

        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        productFrame = frame;
    }

    private static Nav_Panel createNavPanel() {
        Nav_Panel navPanel = new Nav_Panel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon icon = new ImageIcon(Cashier_Dashboard.class.getClassLoader().getResource("assets/indira_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Nav items
        // Nav links
        NavLabel navProduct = new NavLabel("Produk", true);
        NavLabel navBrand = new NavLabel("Merk", false);
        NavLabel navCategory = new NavLabel("Kategori", false);
        NavLabel navReport = new NavLabel("Laporan", false);
        navProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navBrand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navCategory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navReport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logout button
        LogoutButton logoutBtn = new LogoutButton("Keluar");

        // nav.addMouseListener(new java.awt.event.MouseAdapter() {
        //     @Override
        //     public void mouseClicked(java.awt.event.MouseEvent evt) {
        //         dashboardFrame.dispose();
        //         adminCashier.main(new String[0]);
        //     }
        // });
        // nav.addMouseListener(new java.awt.event.MouseAdapter() {
        //     @Override
        //     public void mouseClicked(java.awt.event.MouseEvent evt) {
        //         adminProducts.main(new String[0]);
        //         dashboardFrame.dispose();
        //     }
        // });
        // nav.addMouseListener(new java.awt.event.MouseAdapter() {
        //     @Override
        //     public void mouseClicked(java.awt.event.MouseEvent evt) {
        //         adminReport.main(new String[0]);
        //         dashboardFrame.dispose();
        //     }
        // });
        // Add components to nav panel
        navPanel.add(Box.createVerticalStrut(70));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(80));
        navPanel.add(navProduct);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navBrand);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navCategory);
        navPanel.add(Box.createVerticalStrut(40));
        navPanel.add(navReport);
        navPanel.add(Box.createVerticalStrut(100));
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
        
        Cashier_Product_Add addProduct = new Cashier_Product_Add();
        
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productFrame.dispose();
                addProduct.main(new String[0]);
            }
        });
        rightPanel.add(addButton);
    
        filterPanel.add(rightPanel, BorderLayout.EAST);
    
        return filterPanel;
    }
    

    private static void displayProducts() {
        cardPanel.removeAll();

        for (HashMap<String, String> product : products) {
            cardPanel.add(new Product_Card(product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url")));
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
            cardPanel.add(new Product_Card(product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url")));
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
                cardPanel.add(new Product_Card(product.get("name"), Integer.parseInt(product.get("price")), product.get("image_url")));
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
