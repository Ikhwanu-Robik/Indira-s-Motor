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

    public Product_Card(BiConsumer<Content_Panel, Integer> reloadCallback, boolean isCallerCashier, String id, String name,
            int price, String image_url, String categoryName) {
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

    private JPanel createCardName(String id, String name) {
        JLabel nameLabel = new JLabel(id + ". " + name);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton plusButton = new JButton("+");
        plusButton.setFocusPainted(false);
        plusButton.setPreferredSize(new Dimension(45, 30));
        plusButton.setFont(new Font("Arial", Font.BOLD, 16));
        plusButton.addActionListener(e -> {
            // Tambahkan aksi jika tombol + ditekan
            JOptionPane.showMessageDialog(null, "Menambahkan " + name);
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // Agar background mengikuti card
        topPanel.add(nameLabel, BorderLayout.CENTER);
        topPanel.add(plusButton, BorderLayout.EAST);

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
                            Product_Card.this.categoryName), Integer.valueOf(1));
                } else {
                    JOptionPane.showMessageDialog(null, "Admin's Product Info page is not ready yet :(", "SORRY!",
                            JOptionPane.INFORMATION_MESSAGE);
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

    public String getProductPrice() {
        return productPriceLabel.getText();
    }
}
