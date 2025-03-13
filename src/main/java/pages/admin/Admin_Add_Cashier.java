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
import components.Button_Brown;
import components.Button_White;
import components.Content_Panel;
import components.Nav_Panel;
import javax.swing.JPasswordField;

public class Admin_Add_Cashier {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 15, 5);

        Nav_Panel nav_pane = new Nav_Panel();
        Content_Panel content_pane = new Content_Panel();

        nav_pane.setLayout(new BoxLayout(nav_pane, BoxLayout.Y_AXIS));
        content_pane.setLayout(new BorderLayout()); // Mengubah layout menjadi BorderLayout

        // Panel untuk tombol Tambah Pegawai
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(new Color(0xE4E4E4)); // Match background color

        // Buat tombol Tambah Pegawai menggunakan kelas Button yang sudah ada
        Button_Brown addButton = new Button_Brown("Tambahkan +");
        Button_White cancleButton = new Button_White("Batal");

        // Panel untuk menampung tombol di sisi kanan
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setBackground(new Color(0xE4E4E4));
        rightButtonPanel.add(addButton);

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setBackground(new Color(0xE4E4E4));
        leftButtonPanel.add(cancleButton);

        // Menambahkan panel tombol ke sisi utara dengan margin
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100)); // Tambahkan padding

        // Panel untuk input
        JPanel center_content = new JPanel();
        center_content.setLayout(new GridBagLayout());
        center_content.setBackground(new Color(0xE4E4E4));

        // Title
        JLabel title = new JLabel("Tambahkan Pegawai");
        title.setForeground(Color.black);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        // Username
        JLabel text_user = new JLabel("Nama User:");
        text_user.setForeground(Color.black);
        text_user.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(600, 50));
        username.setBackground(new Color(0xD9D9D9));

        JPanel user_panel = new JPanel();
        user_panel.add(text_user, gbc);
        user_panel.add(username, gbc);
        user_panel.setBackground(new Color(0xE4E4E4));
        user_panel.setLayout(new BoxLayout(user_panel, BoxLayout.Y_AXIS));

        // Password
        JLabel text_pass = new JLabel("Password:");
        text_pass.setForeground(Color.black);
        text_pass.setFont(new Font("Arial", Font.BOLD, 24));

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(600, 50));
        password.setBackground(new Color(0xD9D9D9));

        JPanel pass_panel = new JPanel();
        pass_panel.add(text_pass, gbc);
        pass_panel.add(password, gbc);
        pass_panel.setBackground(new Color(0xE4E4E4));
        pass_panel.setLayout(new BoxLayout(pass_panel, BoxLayout.Y_AXIS));

        center_content.add(title, gbc);
        center_content.add(user_panel, gbc);
        center_content.add(pass_panel, gbc);
        // Menyusun layout untuk Content_Panel
        content_pane.add(buttonPanel, BorderLayout.SOUTH);
        content_pane.add(center_content, BorderLayout.CENTER);

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

        ImageIcon icon = new ImageIcon(new Admin_Dashboard().getClass().getResource("/assets/icons-removebg.png"));
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
        frame.add(content_pane, BorderLayout.CENTER); // Gunakan CENTER bukan EAST agar sesuai gambar
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
