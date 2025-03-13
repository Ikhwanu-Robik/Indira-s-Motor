package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cashier_Card extends JPanel {
    private JLabel cashier_name;
    
    // Constructor dengan parameter untuk nama kasir
    public Cashier_Card(String name) {
        // Setup panel
        this.setPreferredSize(new Dimension(900, 60));
        this.setBackground(new Color(0xD9D9D9));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Buat komponen label nama kasir
        cashier_name = new JLabel(name);
        cashier_name.setForeground(Color.BLACK);
        cashier_name.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Tambahkan ke panel
        // this.add(Box.createHorizontalStrut(50));
        this.add(cashier_name);
    }
    
    // Method untuk mendapatkan nama kasir
    public String getCashierName() {
        return this.cashier_name.getText();
    }
}