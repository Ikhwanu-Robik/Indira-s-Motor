package pages.admin;

import components.Button_Brown;
import components.Cashier_Card;
import components.Content_Panel;
import controllers.CashierController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Admin_Cashier {

    // Constants
    private static final Color BACKGROUND_COLOR = new Color(0xE4E4E4);
    private static ArrayList<String> cashierNames = new ArrayList<>();
    private static BiConsumer<Content_Panel, Integer> reloadCallback;

    public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback) {
        Admin_Cashier.reloadCallback = reloadCallback;
        Content_Panel adminCashierPanel = createContentPanel();

        return adminCashierPanel;
    }

    public static void getCashiers() {
//        Clear the cashiers array
        cashierNames = new ArrayList<>();

        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");

        ArrayList<HashMap<String, String>> cashiers = new CashierController().read(columns);

        if (cashiers != null) {
            for (HashMap<String, String> cashier : cashiers) {
                cashierNames.add(cashier.get("username"));
            }
        } else {
            cashierNames.add("no cashier found");
        }
    }

    private static Content_Panel createContentPanel() {
        Content_Panel contentPanel = new Content_Panel();
        contentPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        JScrollPane scrollPane = createCashierListScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        contentPanel.add(buttonPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 100));

        // Create "Add Employee" button
        Button_Brown addButton = new Button_Brown("Tambah Pegawai +");

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @SuppressWarnings("static-access")
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Content_Panel adminAddCashierPanel = Admin_Add_Cashier.init(reloadCallback);
                
                reloadCallback.accept(adminAddCashierPanel, Integer.valueOf(1));
            }
        });

        // Panel for right-aligned button
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setBackground(BACKGROUND_COLOR);
        rightButtonPanel.add(addButton);

        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        return buttonPanel;
    }

    private static JScrollPane createCashierListScrollPane() {
        JPanel cashierListPanel = new JPanel();
        cashierListPanel.setLayout(new GridBagLayout());
        cashierListPanel.setBackground(BACKGROUND_COLOR);

        // Configure GridBagConstraints for cashier cards
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(15, 5, 15, 5);
        gbc.weightx = 1.0;

        // Calling the API
        getCashiers();
        // Add cashier cards to the panel
        for (String name : cashierNames) {
            Cashier_Card cashierCard = new Cashier_Card(name);
            cashierListPanel.add(cashierCard, gbc);
        }

        // Create scroll pane for the cashier list
        JScrollPane scrollPane = new JScrollPane(cashierListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }
}
