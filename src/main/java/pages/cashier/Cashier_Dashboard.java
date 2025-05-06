package pages.cashier;

import components.Content_Panel;
import components.Nav_Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Cashier_Dashboard {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cashier Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());

        Nav_Panel nav_pane = new Nav_Panel();
        Content_Panel content_pane = new Content_Panel();

        nav_pane.setLayout(new BoxLayout(nav_pane, BoxLayout.Y_AXIS));
        content_pane.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Selamat Datang");
        title.setForeground(new Color(0xA0522D));
        title.setFont(new Font("Arial", Font.BOLD, 56));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title_username = new JLabel("Cashier");
        title_username.setForeground(Color.WHITE);
        title_username.setFont(new Font("Arial", Font.BOLD, 56));
        title_username.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nav_1 = new JLabel("Produk");
        nav_1.setForeground(Color.WHITE);
        nav_1.setFont(new Font("Arial", Font.PLAIN, 24));
        nav_1.setHorizontalAlignment(SwingConstants.CENTER);
        nav_1.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JLabel nav_2 = new JLabel("Kategori");
        nav_2.setForeground(Color.WHITE);
        nav_2.setFont(new Font("Arial", Font.PLAIN, 24));
        nav_2.setHorizontalAlignment(SwingConstants.CENTER);
        nav_2.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JLabel nav_3 = new JLabel("Merk");
        nav_3.setForeground(Color.WHITE);
        nav_3.setFont(new Font("Arial", Font.PLAIN, 24));
        nav_3.setHorizontalAlignment(SwingConstants.CENTER);
        nav_3.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JLabel nav_4 = new JLabel("Transaksi");
        nav_4.setForeground(Color.WHITE);
        nav_4.setFont(new Font("Arial", Font.PLAIN, 24));
        nav_4.setHorizontalAlignment(SwingConstants.CENTER);
        nav_4.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        
        JButton logout_btn = new JButton("Keluar");
        logout_btn.setForeground(new Color(0xA0522D));
        logout_btn.setBackground(Color.white);
        logout_btn.setMaximumSize(new Dimension(180, 50));
        logout_btn.setPreferredSize(new Dimension(180, 50));
        logout_btn.setFont(new Font("Arial", Font.BOLD, 20));
        logout_btn.setFocusPainted(false);
        logout_btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        logout_btn.setAlignmentX(JButton.CENTER_ALIGNMENT);

        ImageIcon icon = new ImageIcon(new Cashier_Dashboard().getClass().getResource("/assets/icons-removebg.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        nav_pane.add(Box.createVerticalStrut(100));
        nav_pane.add(logo);
        nav_pane.add(Box.createVerticalStrut(100));
        nav_pane.add(nav_1);
        nav_pane.add(Box.createVerticalStrut(60));
        nav_pane.add(nav_2);
        nav_pane.add(Box.createVerticalStrut(60));
        nav_pane.add(nav_3);
        nav_pane.add(Box.createVerticalStrut(60));
        nav_pane.add(nav_4);
        nav_pane.add(Box.createVerticalStrut(160));
        nav_pane.add(logout_btn);

        content_pane.add(title, gbc);
        content_pane.add(title_username, gbc);

        frame.add(nav_pane, BorderLayout.WEST);
        frame.add(content_pane, BorderLayout.EAST);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
