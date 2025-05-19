package pages.admin;

import components.Content_Panel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Admin_Product_Info {

	private static String productName;
	private static String productPrice;
	private static String imageUrl;
	private static String categoryName;
	private static BiConsumer<Content_Panel, Integer> reloadCallback;
	private static String stock;

	public static Content_Panel init(BiConsumer<Content_Panel, Integer> reloadCallback, String productName,
			String productPrice, String imageUrl, String categoryName, String stock) {
		Admin_Product_Info.reloadCallback = reloadCallback;
		Admin_Product_Info.productName = productName;
		Admin_Product_Info.productPrice = productPrice;
		Admin_Product_Info.imageUrl = imageUrl;
		Admin_Product_Info.categoryName = categoryName;
		Admin_Product_Info.stock = stock;

		Content_Panel cashierProductInfoPanel = createContentPanel();
		return cashierProductInfoPanel;
	}

	private static Content_Panel createContentPanel() {
		Content_Panel contentPanel = new Content_Panel();
		contentPanel.setLayout(new GridBagLayout());

		JPanel image = createImagePanel(imageUrl);

		// Add label to content panel
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		contentPanel.add(image, gbc);
		return contentPanel;
	}

	private static JPanel createImagePanel(String imagePath) {
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		imagePanel.setBackground(Color.WHITE);
		imagePanel.setPreferredSize(new Dimension(850, 700));

		try {
			File dir = new File("C:/IndiraMotorKasir/assets");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File image = new File("C:/IndiraMotorKasir/assets/" + Admin_Product_Info.imageUrl);
			InputStream input = new FileInputStream(image);

			BufferedImage originalImage = ImageIO.read(input);
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();

			int scaledWidth;
			int scaledHeight;
			if (height < 500 && width < 800) {
				int multiplier = Math.round(height / 500);
				scaledWidth = width * multiplier;
				scaledHeight = height * multiplier;
			} else if (height > 500 && width > 800) {
				scaledWidth = width * 500 / height;
				scaledHeight = 500;
			} else if (height > 500 && width < 800) {
				scaledWidth = width * 500 / height;
				scaledHeight = 500;
			} else {
				scaledWidth = width;
				scaledHeight = height;
			}

			Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
			imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			imagePanel.add(imageLabel);
		} catch (IOException e) {
			JLabel placeholder = new JLabel("No Image Available");
			placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
			imagePanel.add(placeholder);
		}

		JPanel productDescription = createDescriptionPanel("Oli Amahay", 20000, "Oli");
		imagePanel.add(productDescription);

		imagePanel.add(Box.createVerticalStrut(20));

		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new BorderLayout());
		buttonContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

		JPanel buttonPanel = createButtonPanel();
		buttonContainer.add(buttonPanel, BorderLayout.CENTER);

		imagePanel.add(buttonContainer);

		return imagePanel;
	}

	private static JPanel createDescriptionPanel(String name, double price, String category) {
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new GridBagLayout());

		// Add label to content panel
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel productName = new JLabel(Admin_Product_Info.productName);
		productName.setFont(new Font("Arial", Font.BOLD, 20));

		JLabel productPrice = new JLabel("Rp " + Admin_Product_Info.productPrice);
		productPrice.setFont(new Font("Arial", Font.ITALIC, 18));

		JLabel productCategory = new JLabel(Admin_Product_Info.categoryName);
		productCategory.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel productStock = new JLabel("Stok:" + Admin_Product_Info.stock);
		productStock.setFont(new Font("Arial", Font.BOLD, 16));

		descriptionPanel.add(productName, gbc);
		descriptionPanel.add(productPrice, gbc);
		descriptionPanel.add(productCategory, gbc);
		descriptionPanel.add(productStock, gbc);

		return descriptionPanel;
	}

	private static JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		JButton cancelBtn = new JButton("Batal");
		cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
		cancelBtn.setForeground(Color.BLACK);
		cancelBtn.setBackground(new Color(0xE0E0E0));
		cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				reloadCallback.accept(Admin_Products.init(reloadCallback), Integer.valueOf(2));
			}
		});

//        JButton editBtn = new JButton("Edit");
//        editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
//        editBtn.setForeground(Color.WHITE);
//        editBtn.setBackground(new Color(0xA0522D));
//        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                reloadCallback.accept(productEdit.init(reloadCallback, productName));
//            }
//        });
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		leftPanel.add(cancelBtn);

//        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        rightPanel.add(editBtn);
		buttonPanel.add(leftPanel, BorderLayout.WEST);
//        buttonPanel.add(rightPanel, BorderLayout.EAST);

		return buttonPanel;
	}

}
