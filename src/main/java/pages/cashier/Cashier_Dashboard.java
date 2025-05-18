package pages.cashier;

import components.Content_Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Cashier_Dashboard {

    // Constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
    private static final Color BROWN_COLOR = new Color(0xA0522D);
    private static String loggedInUsername = "Cashier";
    private static BiConsumer<Content_Panel, Integer> reloadCallback;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback, String loggedInUsername) {
        Cashier_Dashboard.loggedInUsername = loggedInUsername;
        Cashier_Dashboard.reloadCallback = reloadCallback;
        
        Content_Panel cashierDashboardPanel = createContentPanel();
        return cashierDashboardPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        // Welcome label
        JLabel titleLabel = createTitleLabel("Selamat Datang", BROWN_COLOR);
        JLabel usernameLabel = createTitleLabel(loggedInUsername, Color.WHITE);

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
