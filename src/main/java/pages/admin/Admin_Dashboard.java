package pages.admin;

import components.Content_Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;  
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Admin_Dashboard {

    // Constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
    private static final Color BROWN_COLOR = new Color(0xA0522D);

    public static Content_Panel init() {
        Content_Panel adminDashboardPanel = createContentPanel();
        
        return adminDashboardPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        // Welcome label
        JLabel titleLabel = createTitleLabel("Selamat Datang", BROWN_COLOR);
        JLabel usernameLabel = createTitleLabel("Admin", Color.WHITE);

        // Add label to content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
        contentPanel.add(usernameLabel, gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(TITLE_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}
