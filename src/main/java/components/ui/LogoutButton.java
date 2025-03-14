package components.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class LogoutButton extends JButton {

    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);

    public LogoutButton(String text) {
        this.setForeground(new Color(0xA0522D));
        this.setBackground(Color.white);
        this.setMaximumSize(new Dimension(180, 50));
        this.setPreferredSize(new Dimension(180, 50));
        this.setFont(BUTTON_FONT);
        this.setFocusPainted(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        this.setAlignmentX(JButton.CENTER_ALIGNMENT);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setText(text);
    }

}
