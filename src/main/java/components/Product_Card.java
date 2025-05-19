package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.TransactionController;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import pages.admin.Admin_Product_Info;
import pages.cashier.Cashier_Product_Info;

public class Product_Card extends JPanel {

    private final JPanel productNameLabel;
    private final JLabel productPriceLabel;
    private String productName;
    private String productPrice;
    private String imageUrl;
    private String categoryName;
    private BiConsumer<Content_Panel, Integer> reloadCallback;
    private boolean isCallerCashier;
    private TransactionController transactionSession;
    private String productId;
	protected String stock;
	private int qtyInCart;

    public Product_Card(BiConsumer<Content_Panel, Integer> reloadCallback, boolean isCallerCashier, String id, String name,
            int price, String image_url, String categoryName, String stock) {
        this.reloadCallback = reloadCallback;
        this.isCallerCashier = isCallerCashier;
        this.productName = name;
        this.productPrice = Integer.toString(price);
        this.imageUrl = image_url;
        this.categoryName = categoryName;
        this.stock = stock;

        setLayout(new BorderLayout());
        initialCard();

        productNameLabel = createCardName(id, name);
        productPriceLabel = createCardPrice(price);
        JLabel productImage = createImage(image_url);

        add(productNameLabel, BorderLayout.NORTH);
        add(productImage, BorderLayout.CENTER);
        add(productPriceLabel, BorderLayout.SOUTH);
    }

    public Product_Card(BiConsumer<Content_Panel, Integer> reloadCallback, boolean isCallerCashier, String id, String name,
            int price, String image_url, String categoryName, TransactionController transactionSession, String stock, int  qtyInCart) {
        this.productId = id;
        this.reloadCallback = reloadCallback;
        this.isCallerCashier = isCallerCashier;
        this.productName = name;
        this.productPrice = Integer.toString(price);
        this.imageUrl = image_url;
        this.categoryName = categoryName;
        this.transactionSession = transactionSession;
        this.stock = stock;
        this.qtyInCart = qtyInCart;

        setLayout(new BorderLayout());
        initialCard();

        productNameLabel = createCardName(id, name);
        productPriceLabel = createCardPrice(price);
        JLabel productImage = createImage(image_url);

        add(productNameLabel, BorderLayout.NORTH);
        add(productImage, BorderLayout.CENTER);
        add(productPriceLabel, BorderLayout.SOUTH);
    }
    
    private boolean checkIsStockAvailable() {
    	if (qtyInCart + 1 > Integer.parseInt(stock)) {
    		return false;
    	}
    	return true;
    }

    private void initialCard() {
        setPreferredSize(new Dimension(300, 200));
        setBackground(new Color(0xE0E0E0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setToolTipText("Product Information Card");
    }

    private JPanel createCardName(String id, String name) {
        JLabel nameLabel = new JLabel(id + ". " + name);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(nameLabel, BorderLayout.CENTER);

        ImageIcon cartIcon = new ImageIcon(Product_Card.class.getClassLoader().getResource("assets/cart_icon.png"));

        if (isCallerCashier) {
            File image = null;

            JButton plusButton = new JButton(cartIcon);
            plusButton.setFocusPainted(false);
            plusButton.setPreferredSize(new Dimension(45, 30));
            plusButton.setFont(new Font("Arial", Font.BOLD, 16));
            plusButton.addActionListener(e -> {
                int productId = Integer.parseInt(this.productId);
                
                boolean isStockAvailable = checkIsStockAvailable();
                
                if (isStockAvailable) {
                	transactionSession.addToCart(productId, 1);

                    JOptionPane.showMessageDialog(null, "Menambahkan " + name + " ke keranjang. Lihat di tab Transaksi");
                } else {
                	JOptionPane.showMessageDialog(null, "Stok produk tidak tersedia untuk dimasukkan ke keranjang", "STOCK NOT ENOUGH", JOptionPane.ERROR_MESSAGE);
                }
            });

            topPanel.add(plusButton, BorderLayout.EAST);
        }

        return topPanel;
    }

    private JLabel createImage(String image_url) {
        File dir = new File("C:/IndiraMotorKasir/assets");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        JLabel logo = null;
        try {
            File image = new File("C:/IndiraMotorKasir/assets/" + image_url);
            InputStream input = new FileInputStream(image);

            BufferedImage originalImage = ImageIO.read(input);
            logo = new JLabel(new ImageIcon(originalImage));
        } catch (IOException e) {
            logo = new JLabel("Image not found");
        }
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isCallerCashier) {
                    reloadCallback.accept(Cashier_Product_Info.init((BiConsumer<Content_Panel, Integer>) Product_Card.this.reloadCallback,
                            Product_Card.this.productName, Product_Card.this.productPrice, Product_Card.this.imageUrl,
                            Product_Card.this.categoryName, Product_Card.this.stock, transactionSession), Integer.valueOf(1));
                } else {
                    reloadCallback.accept(Admin_Product_Info.init(reloadCallback, Product_Card.this.productName, Product_Card.this.productPrice, Product_Card.this.imageUrl, Product_Card.this.categoryName, Product_Card.this.stock), Integer.valueOf(2));
                }
            }
        });

        return logo;
    }

    private JLabel createCardPrice(int price) {
        JLabel label = new JLabel("Rp. " + price, SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        return label;
    }

    public String getProductPrice() {
        return productPriceLabel.getText();
    }
}
