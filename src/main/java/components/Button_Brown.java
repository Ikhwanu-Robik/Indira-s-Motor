package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class Button_Brown extends JButton {

    public Button_Brown(String btn) {
        this.setPreferredSize(new Dimension(200, 50));
        this.setBackground(new Color(0xA0522D));
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.PLAIN, 18));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        this.setText(btn);

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}
