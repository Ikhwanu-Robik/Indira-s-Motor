package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class Button_White extends JButton {

    public Button_White(String btn) {
        this.setPreferredSize(new Dimension(150, 50));
        this.setBackground(new Color(0xD9D9D9));
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.PLAIN, 18));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        this.setText(btn);

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}
