package pages;

import components.Login_Panel;
import components.Side_Panel;
import controllers.AdminController;
import database.Database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login {

    public static void init() {
        // Create the main frame with specified size
        JFrame frame = new JFrame("Login Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());

        // Initialize the panels you imported
        Login_Panel login_pane = new Login_Panel();
        Side_Panel side_pane = new Side_Panel();

        // Configure the login panel - set to half the frame width
        login_pane.setLayout(new BoxLayout(login_pane, BoxLayout.Y_AXIS));

        // Add components to login panel
        JLabel login_title = new JLabel("Masuk");
        login_title.setForeground(new Color(0xA0522D));
        login_title.setFont(new Font("Arial", Font.BOLD, 48));
        login_title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Subtitle
        JLabel login_subtitle = new JLabel("Masuk ke Akun Anda!");
        login_subtitle.setFont(new Font("Arial", Font.PLAIN, 24));
        login_subtitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Username field - scaled for larger screen
        JTextField username = new JTextField("Nama");
        username.setMaximumSize(new Dimension(550, 60));
        username.setPreferredSize(new Dimension(550, 60));
        username.setFont(new Font("Arial", Font.PLAIN, 18));
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        username.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        // Password field - scaled for larger screen
        JPasswordField password = new JPasswordField("Password");
        password.setMaximumSize(new Dimension(550, 60));
        password.setPreferredSize(new Dimension(550, 60));
        password.setFont(new Font("Arial", Font.PLAIN, 18));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        password.setAlignmentX(JPasswordField.CENTER_ALIGNMENT);
//        password.setEchoChar((char) 0); // Show password text initially

        // Login button - scaled for larger screen
        JButton login_btn = new JButton("Masuk");
        login_btn.setForeground(Color.WHITE);
        login_btn.setBackground(new Color(0xA0522D));
        login_btn.setMaximumSize(new Dimension(240, 60));
        login_btn.setPreferredSize(new Dimension(240, 60));
        login_btn.setFont(new Font("Arial", Font.BOLD, 20));
        login_btn.setFocusPainted(false);
        login_btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        login_btn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        login_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @SuppressWarnings("static-access")
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String usernameValue = username.getText();
                char[] passwordChar = password.getPassword();
                //getPassword returns char[]                
                //so I moved it to a String, but that defeats the purpose of using char[] which is to secure it
                //calling to database require the use of String, unfortunately.
                //I can check the letter one by one
                //But as the original password is already hashed- with String.hashCode() -I HAVE to use String.
                String passwordValue = "";
                for (char letter: passwordChar) {
                    passwordValue += letter;
                }
                
                HashMap<String, String> form_data = new HashMap<>();
                form_data.put("username", usernameValue);
                form_data.put("password", passwordValue);
                
                controllers.LoginController loginSession = new controllers.LoginController();
                HashMap<String, String> response = loginSession.authenticate(form_data);
                
                if (response.get("status").equals("error")) {
                    //displays an error pop up
                    JFrame errorFrame = new JFrame();
                    errorFrame.setSize(480, 360);
 
                    JLabel message = new JLabel();
                    message.setText(response.get("message"));
                    errorFrame.add(message);
                    
                    errorFrame.setVisible(true);
                }
                else if (response.get("status").equals("success")) {
                    //redirect to dashboard
                    if (loginSession.role.equals("cashier")) {
                        //display cashier dashboard
                        frame.dispose();
                        CashierLayout.init(loginSession);
                    }
                    else if (loginSession.role.equals("admin")) {
                        //display admin dashboard
                        frame.dispose();
                        AdminLayout.init(loginSession);
                    }
                }
            }
        });

        // Add components to login panel with proper spacing for larger screen
        login_pane.add(Box.createVerticalStrut(200)); // Space at top
        login_pane.add(login_title);
        login_pane.add(Box.createVerticalStrut(20));
        login_pane.add(login_subtitle);
        login_pane.add(Box.createVerticalStrut(100));
        login_pane.add(username);
        login_pane.add(Box.createVerticalStrut(40));
        login_pane.add(password);
        login_pane.add(Box.createVerticalStrut(80));
        login_pane.add(login_btn);

        // Configure the side panel - set to half the frame width
        side_pane.setBackground(new Color(0xA0522D));
        side_pane.setLayout(new GridBagLayout());

        // Welcome text - scaled for larger screen
        JLabel welcome_text1 = new JLabel("Selamat Datang");
        welcome_text1.setForeground(Color.WHITE);
        welcome_text1.setFont(new Font("Arial", Font.BOLD, 56));
        welcome_text1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel welcome_text2 = new JLabel("Kembali !");
        welcome_text2.setForeground(Color.WHITE);
        welcome_text2.setFont(new Font("Arial", Font.BOLD, 56));
        welcome_text2.setHorizontalAlignment(SwingConstants.CENTER);

        // Add welcome text to side panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        side_pane.add(welcome_text1, gbc);
        side_pane.add(welcome_text2, gbc);

        // Add panels to frame
        frame.add(login_pane, BorderLayout.WEST);
        frame.add(side_pane, BorderLayout.EAST);

        // Center the frame on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
