package pages.admin;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import components.Content_Panel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import javax.swing.SwingConstants;

public class Admin_Report_Detail {
    private static ArrayList<HashMap<String, String>> products = null;
    private static Consumer<Content_Panel> reloadCallback;

    public static Content_Panel init(ArrayList<HashMap<String, String>> products, Consumer<Content_Panel> reloadCallback) {
        Admin_Report_Detail.products = products;
        Admin_Report_Detail.reloadCallback = reloadCallback;
        
        Content_Panel contentPanel = createContentPanel();
        
        return contentPanel;
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = createTitleLabel("Laporan Penjualan", new Color(0x00000));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel.add(titleLabel, gbc);
//        contentPanel.add("", gbc);

        return contentPanel;
    }

    private static JLabel createTitleLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

}
