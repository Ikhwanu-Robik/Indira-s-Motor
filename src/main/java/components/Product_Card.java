/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Product_Card extends JPanel {

    private final JLabel productNameLabel;
    private final JLabel productPriceLabel;

    public Product_Card(String id, String name, int price, String image_url) {
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
        }
        else {
            logo.setText("A reload is needed to display the image");
        }
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
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
