/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Product_Card extends JPanel {

    private final JLabel productNameLabel;
    private final JLabel productPriceLabel;

    public Product_Card(String name, int price) {
        setLayout(new BorderLayout()); 
        initialCard();

        productNameLabel = createCardName(name);
        productPriceLabel = createCardPrice(price);

        add(productNameLabel, BorderLayout.NORTH);
        add(productPriceLabel, BorderLayout.SOUTH);
    }

    private void initialCard() {
        setPreferredSize(new Dimension(300, 200));
        setBackground(new Color(0xE0E0E0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setToolTipText("Product Information Card");
    }

    private JLabel createCardName(String name) {
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        return label;
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
