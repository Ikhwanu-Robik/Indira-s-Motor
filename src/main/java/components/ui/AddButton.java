package components.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class AddButton extends JButton {

    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    public AddButton(String text) {
        this.setForeground(Color.white);
        this.setBackground(new Color(0xA0522D));
        this.setMaximumSize(new Dimension(160, 40));
        this.setPreferredSize(new Dimension(160, 40));
        this.setFont(BUTTON_FONT);
        this.setFocusPainted(false);
        this.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        this.setAlignmentX(JButton.CENTER_ALIGNMENT);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setText(text);
    }

}
