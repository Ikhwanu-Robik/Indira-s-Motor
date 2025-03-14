package components.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NavLabel extends JLabel {

    private static final Font NAV_FONT_BOLD = new Font("Arial", Font.BOLD, 24);
    private static final Font NAV_FONT_PLAIN = new Font("Arial", Font.PLAIN, 24);
    
    public NavLabel(String text, boolean isActive) {
        this.setText(text);
        this.setForeground(Color.WHITE);
        this.setFont(isActive ? NAV_FONT_BOLD : NAV_FONT_PLAIN);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(JTextField.CENTER_ALIGNMENT);
    }
    
}
