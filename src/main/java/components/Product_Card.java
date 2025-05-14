/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import pages.cashier.Cashier_Product_Info;

public class Product_Card extends JPanel {

    private final JLabel productNameLabel;
    private final JLabel productPriceLabel;
    private String productName;
    private String productPrice;
    private String imageUrl;
    private String categoryName;
    private Consumer<Content_Panel> reloadCallback;
    private boolean isCallerCashier;

    public Product_Card(Consumer<Content_Panel> reloadCallback, boolean isCallerCashier, String id, String name, int price, String image_url, String categoryName) {
    	this.reloadCallback = reloadCallback;
    	this.isCallerCashier = isCallerCashier;
        this.productName = name;
        this.productPrice = Integer.toString(price);
        this.imageUrl = image_url;
        this.categoryName = categoryName;

        setLayout(new BorderLayout());
        initialCard();

        productNameLabel = createCardName(id, name);
        productPriceLabel = createCardPrice(price);
        JLabel productImage = createImage(image_url);

        add(productNameLabel, BorderLayout.NORTH);
        add(productImage, BorderLayout.CENTER);
        add(productPriceLabel, BorderLayout.SOUTH);
    }

    private void initialCard() {
        setPreferredSize(new Dimension(300, 200));
        setBackground(new Color(0xE0E0E0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setToolTipText("Product Information Card");
    }

    private JLabel createCardName(String id, String name) {
        JLabel label = new JLabel(id + " " + name, SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        return label;
    }

    private JLabel createImage(String image_url) {
        URL full_url = getClass().getResource("/assets/" + image_url);
        JLabel logo = new JLabel();
        if (full_url != null) {
            ImageIcon icon = new ImageIcon(full_url);
            logo.setIcon(icon);
        } else {
            logo.setText("A reload is needed to display the image");
        }
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isCallerCashier) {
                	reloadCallback.accept(Cashier_Product_Info.init(
                            Product_Card.this.reloadCallback,
                            Product_Card.this.productName,
                            Product_Card.this.productPrice,
                            Product_Card.this.imageUrl,
                            Product_Card.this.categoryName
                    ));
                } else {
                	JOptionPane.showMessageDialog(null, "Admin's Product Info page is not ready yet :(", "SORRY!", JOptionPane.INFORMATION_MESSAGE);
//                	TODO : make Admin_Product_Info
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

    public String getProductName() {
        return productNameLabel.getText();
    }

    public String getProductPrice() {
        return productPriceLabel.getText();
    }
}
