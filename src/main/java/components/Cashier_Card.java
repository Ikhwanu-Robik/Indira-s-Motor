package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cashier_Card extends JPanel {
    // Constants
    private static final Color BACKGROUND_COLOR = new Color(0xD9D9D9);
    private static final Font NAME_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Dimension CARD_SIZE = new Dimension(900, 60);
    
    private final JLabel cashierNameLabel;
    
    public Cashier_Card(String name) {
        initializePanel();
        cashierNameLabel = createNameLabel(name);
        add(cashierNameLabel);
    }
    
    private void initializePanel() {
        setPreferredSize(CARD_SIZE);
        setBackground(BACKGROUND_COLOR);
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    
    private JLabel createNameLabel(String name) {
        JLabel label = new JLabel(name);
        label.setForeground(Color.BLACK);
        label.setFont(NAME_FONT);
        return label;
    }
    
    public String getCashierName() {
        return cashierNameLabel.getText();
    }
}