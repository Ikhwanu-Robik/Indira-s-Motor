//package components;
//
//import java.awt.Dimension;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JComboBox;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//public class Report_Filter extends JPanel {
//
//    private static final int WIDTH = 200;
//        
//    private final 
//
//    public Report_Filter() {
//        initializePanel();
//
//        searchField = createSearchField();
//        categoryCombo = createCategoryCombo();
//        brandCombo = createBrandCombo();
//
//        add(searchField);
//        add(Box.createVerticalStrut(10));
//        add(categoryCombo);
//        add(Box.createVerticalStrut(10));
//        add(brandCombo);
//    }
//
//    private void initializePanel() {
//        setPreferredSize(new Dimension(200, 100));
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//    }
//
//    private JTextField createSearchField() {
//        JTextField field = new JTextField(SEARCH_PLACEHOLDER);
//        field.setMaximumSize(new Dimension(WIDTH, 30));
//        return field;
//    }
//
//    private JComboBox<String> createCategoryCombo() {
//        String[] categories = {"Ban", "Oli", "Spion", "Baut"};
//        JComboBox<String> combo = new JComboBox<>(categories);
//        combo.setMaximumSize(new Dimension(WIDTH, 25));
//        return combo;
//    }
//
//    private JComboBox<String> createBrandCombo() {
//        String[] brands = {"Yamaha", "Honda", "Kawasaki"};
//        JComboBox<String> combo = new JComboBox<>(brands);
//        combo.setMaximumSize(new Dimension(WIDTH, 25));
//        return combo;
//    }
//
//    public String getSearchText() {
//        return searchField.getText();
//    }
//
//    public String getSelectedCategory() {
//        return (String) categoryCombo.getSelectedItem();
//    }
//
//    public String getSelectedBrand() {
//        return (String) brandCombo.getSelectedItem();
//    }
//}
