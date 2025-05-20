package pages.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import components.Button_Brown;
import components.Button_White;
import components.Content_Panel;
import controllers.CashierController;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class Admin_Add_Cashier {

    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
    private static final Color INPUT_COLOR = new Color(0xD9D9D9);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 24);
    private static JTextField usernameInput = null;
    private static JPasswordField passwordInput = null;
    private static BiConsumer<Content_Panel, Integer> reloadCallback;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback) {
        Admin_Add_Cashier.reloadCallback = reloadCallback;
        
        Content_Panel adminAddCashierPanel = createContentPanel();

        return adminAddCashierPanel;
    }
    
    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new BorderLayout());

        JPanel centerContent = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(centerContent, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    private static JPanel createFormPanel() {
        JPanel centerContent = new JPanel();
        centerContent.setLayout(new GridBagLayout());
        centerContent.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = createGridBagConstraints();

        // Title
        JLabel title = new JLabel("Tambahkan Pegawai");
        title.setForeground(Color.black);
        title.setFont(TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        // Username field
        JPanel userPanel = createInputPanel("Nama User:", gbc);

        // Password field
        JPanel passPanel = createPasswordPanel("Password:", gbc);

        // Add components to center content
        centerContent.add(title, gbc);
        centerContent.add(userPanel, gbc);
        centerContent.add(passPanel, gbc);

        return centerContent;
    }

    private static GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 15, 5);
        return gbc;
    }

    private static JPanel createInputPanel(String labelText, GridBagConstraints gbc) {
        JLabel textLabel = new JLabel(labelText);
        textLabel.setForeground(Color.black);
        textLabel.setFont(LABEL_FONT);

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(600, 50));
        inputField.setBackground(INPUT_COLOR);
        usernameInput = inputField;

        JPanel panel = new JPanel();
        panel.add(textLabel, gbc);
        panel.add(inputField, gbc);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    private static JPanel createPasswordPanel(String labelText, GridBagConstraints gbc) {
        JLabel textLabel = new JLabel(labelText);
        textLabel.setForeground(Color.black);
        textLabel.setFont(LABEL_FONT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(600, 50));
        passwordField.setBackground(INPUT_COLOR);
        passwordInput = passwordField;

        JPanel panel = new JPanel();
        panel.add(textLabel, gbc);
        panel.add(passwordField, gbc);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));

        // Create buttons
        Button_Brown addButton = new Button_Brown("Tambahkan +");
        Button_White cancelButton = new Button_White("Batal");

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @SuppressWarnings("static-access")
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HashMap<String, String> cashier_data = new HashMap<>();

                if (passwordInput.getPassword().length == 0 || usernameInput.getText().equals("") || usernameInput.getText() == null) {
                	JOptionPane.showMessageDialog(null, "Input harus diisi!");
                } else {
                	String password = "";
                    for (char letter: passwordInput.getPassword()) {
                        password += letter;
                    }
                    
                    cashier_data.put("username", usernameInput.getText());
                    cashier_data.put("password", password);
                    
                    new CashierController().create(cashier_data);
                    reloadCallback.accept(Admin_Cashier.init(reloadCallback), Integer.valueOf(1));
                }
            }
        });
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @SuppressWarnings("static-access")
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadCallback.accept(Admin_Cashier.init(reloadCallback), Integer.valueOf(1));
            }
        });

        // Button container panels
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setBackground(BACKGROUND_COLOR);
        rightButtonPanel.add(addButton);

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setBackground(BACKGROUND_COLOR);
        leftButtonPanel.add(cancelButton);

        // Position buttons
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);

        return buttonPanel;
    }
}
