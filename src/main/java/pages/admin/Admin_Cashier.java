package pages.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import components.Button_Brown;
import components.Cashier_Card;
import components.Content_Panel;
import components.Nav_Panel;

public class Admin_Cashier {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());

        Nav_Panel nav_pane = new Nav_Panel();
        Content_Panel content_pane = new Content_Panel();

        nav_pane.setLayout(new BoxLayout(nav_pane, BoxLayout.Y_AXIS));
        content_pane.setLayout(new BorderLayout());

        // Panel untuk tombol Tambah Pegawai
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(new Color(0xE4E4E4));

        // Buat tombol Tambah Pegawai menggunakan kelas Button yang sudah ada
        Button_Brown addButton = new Button_Brown("Tambah Pegawai +");

        // Panel untuk menampung tombol di sisi kanan
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setBackground(new Color(0xE4E4E4));
        rightButtonPanel.add(addButton);

        // Menambahkan panel tombol ke sisi utara dengan margin
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 100));

        // Create a content panel for the cashier cards with GridBagLayout
        JPanel cashierListPanel = new JPanel();
        cashierListPanel.setLayout(new GridBagLayout());
        cashierListPanel.setBackground(new Color(0xE4E4E4));

        // Tambahkan kartu kasir
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; // Change to NORTH to align at top
        gbc.insets = new Insets(15, 5, 15, 5);
        gbc.weightx = 1.0; // Allow horizontal expansion

        String[] nama = {"Ikhwanu", "Rasyid", "Dio", "Bagus", "Belva", "Suyadi", "Geno", "Supri", "Slamet", "Sugeng"};

        for (String n : nama) {
            Cashier_Card name = new Cashier_Card(n);
            cashierListPanel.add(name, gbc);
        }

        // Create JScrollPane and add the content panel to it
        JScrollPane scrollPane = new JScrollPane(cashierListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Remove border
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Make scrolling smoother

        // Menyusun layout untuk Content_Panel
        content_pane.add(buttonPanel, BorderLayout.NORTH);
        content_pane.add(scrollPane, BorderLayout.CENTER);

        // Menambahkan konten navigasi
        JLabel nav_1 = new JLabel("Pegawai");
        nav_1.setForeground(Color.WHITE);
        nav_1.setFont(new Font("Arial", Font.BOLD, 24));
        nav_1.setHorizontalAlignment(SwingConstants.CENTER);
        nav_1.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JLabel nav_2 = new JLabel("Produk");
        nav_2.setForeground(Color.WHITE);
        nav_2.setFont(new Font("Arial", Font.PLAIN, 24));
        nav_2.setHorizontalAlignment(SwingConstants.CENTER);
        nav_2.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JLabel nav_3 = new JLabel("Laporan");
        nav_3.setForeground(Color.WHITE);
        nav_3.setFont(new Font("Arial", Font.PLAIN, 24));
        nav_3.setHorizontalAlignment(SwingConstants.CENTER);
        nav_3.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JButton logout_btn = new JButton("Keluar");
        logout_btn.setForeground(new Color(0xA0522D));
        logout_btn.setBackground(Color.white);
        logout_btn.setMaximumSize(new Dimension(180, 50));
        logout_btn.setPreferredSize(new Dimension(180, 50));
        logout_btn.setFont(new Font("Arial", Font.BOLD, 20));
        logout_btn.setFocusPainted(false);
        logout_btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        logout_btn.setAlignmentX(JButton.CENTER_ALIGNMENT);

        ImageIcon icon = new ImageIcon(new Admin_Cashier().getClass().getResource("/assets/icons-removebg.png"));
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Tambahkan komponen ke nav_pane
        nav_pane.add(Box.createVerticalStrut(100));
        nav_pane.add(logo);
        nav_pane.add(Box.createVerticalStrut(100));
        nav_pane.add(nav_1);
        nav_pane.add(Box.createVerticalStrut(60));
        nav_pane.add(nav_2);
        nav_pane.add(Box.createVerticalStrut(60));
        nav_pane.add(nav_3);
        nav_pane.add(Box.createVerticalStrut(250));
        nav_pane.add(logout_btn);

        frame.add(nav_pane, BorderLayout.WEST);
        frame.add(content_pane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
