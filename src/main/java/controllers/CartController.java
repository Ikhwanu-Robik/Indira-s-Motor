/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author MyBook Hype AMD
 */
public class CartController extends AbstractController {

	public String table_name = "carts";

	@Override
	public ArrayList<HashMap<String, String>> read(ArrayList<String> columns) {
		Database db = new Database();
		ResultSet rs;
		String stringColumns = "";
		ArrayList<HashMap<String, String>> categories = new ArrayList<>();
		for (String column : columns) {
			stringColumns += (column + ",");
		}
		stringColumns = stringColumns.substring(0, stringColumns.length() - 1);

		// edge case where the frontend puts in *
		if (stringColumns.equals("*")) {
			stringColumns = "id, cashier_id, has_order";
			columns.clear();
			columns.add("id");
			columns.add("cashier_id");
			columns.add("has_order");
		}

		try {
			Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT " + stringColumns + " FROM " + this.table_name);

			if (!rs.isBeforeFirst()) {
				db.close();
				return null;
			}

			rs.first();
			do {
				HashMap<String, String> row = new HashMap<>();
				for (String column : columns) {
					row.put(column, rs.getString(column));
				}
				categories.add(row);
			} while (rs.next());

			db.close();

			return categories;
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public void create(HashMap<String, String> values) {
		String insertStatement = "INSERT INTO " + this.table_name + " (cashier_id, has_order) VALUES (" + "\""
				+ values.get("cashier_id") + "\"" + ", " + "\"" + values.get("has_order") + "\"" + ")";
		try {
			Database db = new Database();
			Statement stmt = db.connect().createStatement();
			stmt.execute(insertStatement);

			db.close();
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void update(int id, HashMap<String, String> values) {
		String updateStatement = "UPDATE " + this.table_name + " SET cashier_id = \"" + values.get("cashier_id")
				+ "\", has_order = \"" + values.get("has_order") + "\"" + "WHERE id = " + id;
		try {
			Database db = new Database();
			Statement stmt = db.connect().createStatement();
			stmt.execute(updateStatement);

			db.close();
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void delete(int id) {
		// order cant be deleted
	}

	@Override
	public ArrayList<HashMap<String, String>> findWhere(String column, String value) {
		String whereStatement = "SELECT * FROM " + this.table_name + " WHERE " + column + " = \"" + value + "\"";
		try {
			Database db = new Database();
			ResultSet rs;
			ArrayList<HashMap<String, String>> categories = new ArrayList<>();
			Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(whereStatement);

			if (!rs.isBeforeFirst()) {
				db.close();
				return null;
			}
			// I expect the line below will throw an exception if ResultSet is empty a.k.a
			// no result found for such query
//			rs.first();
			do {
				HashMap<String, String> row = new HashMap<>();
				row.put("id", rs.getString("id"));
				row.put("cashier_id", rs.getString("cashier_id"));
				row.put("has_order", rs.getString("has_order"));
				categories.add(row);
			} while (rs.next());

			db.close();

			return categories;
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	public void addProduct(int product_id, int qty, int cart_id) {
		// only allow this function to run if has_order = false
		HashMap<String, String> cart = this.findWhere("id", Integer.toString(cart_id)).getFirst();
		if (!cart.get("has_order").equals("0")) {
			return;
		}

		String createQuery;
		
		boolean isProductInCart = false;
		int oldQty = 0;
		ArrayList<HashMap<String, String>> cartProducts = getCartProducts(cart_id);
		if (cartProducts != null) {
			for (HashMap<String, String> cartProduct : cartProducts) {
				if (cartProduct.get("product_id").trim().equals(Integer.toString(product_id))) {
					isProductInCart = true;
					oldQty = Integer.parseInt(cartProduct.get("qty"));
				}
			}
		}
		if (!isProductInCart) {
			createQuery = "INSERT INTO cart_product (cart_id, product_id, qty) VALUES (" + cart_id + ", "
					+ product_id + ", " + qty + ")";
		} else {
			int newQty = oldQty + qty;
			createQuery = "UPDATE cart_product SET qty = " + newQty + " WHERE cart_id = " + cart_id + " AND " + " product_id = " + product_id; 
		}
		
		Database db = new Database();

		Statement createStatement;
		try {
			createStatement = db.connect().createStatement();
			createStatement.execute(createQuery);
		} catch (SQLException ex) {
			Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void removeProduct(int product_id, int cart_id) {
		Database db = new Database();

		String deleteQuery = "DELETE FROM cart_product WHERE product_id = " + product_id + " AND cart_id = " + cart_id;
		System.out.println(deleteQuery);
		Statement createStatement;
		try {
			createStatement = db.connect().createStatement();
			createStatement.execute(deleteQuery);
		} catch (SQLException ex) {
			Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public ArrayList<HashMap<String, String>> getCartProducts(int cart_id) {
		Database db = new Database();
		ResultSet rs;
		String query = "SELECT cart_product.id, cart_product.cart_id, cart_product.qty, products.id AS product_id, products.name, products.image_url, products.price, products.stock, products.brand_id, categories.name AS category_name FROM cart_product JOIN products ON cart_product.product_id = products.id JOIN brands ON products.brand_id = brands.id JOIN categories ON brands.category_id = categories.id WHERE cart_product.cart_id = "
				+ cart_id;
		ArrayList<HashMap<String, String>> cart_products = new ArrayList<>();
		ArrayList<String> columns = new ArrayList<>();
		columns.clear();
		columns.add("id");
		columns.add("cart_id");
		columns.add("product_id");
		columns.add("name");
		columns.add("qty");
		columns.add("price");
		columns.add("image_url");
		columns.add("stock");
		columns.add("brand_id");
		columns.add("category_name");

		try {
			Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);

			if (!rs.isBeforeFirst()) {
				db.close();
				return null;
			}

//			rs.first();
			do {
				HashMap<String, String> row = new HashMap<>();
				for (String column : columns) {
					row.put(column, rs.getString(column));
				}
				cart_products.add(row);
			} while (rs.next());
			cart_products.removeFirst();		
											
			db.close();

			return cart_products;
		} catch (SQLException ex) {
			Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	public int getTotal(int cart_id) {
		int total = 0;

		// cart_product.findWhere("cart_id", cart.id)
		Database db = new Database();
		ResultSet rs = null;
		String getCartProductQuery = "SELECT * FROM cart_product WHERE cart_id = " + cart_id;
		Statement stmt;

		try {
			stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(getCartProductQuery);
		} catch (SQLException ex) {
			Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
//			rs.first();
			do {
				int qty = rs.getInt("qty");
				int price = Integer.parseInt(
						new ProductController().findWhere("id", rs.getString("product_id")).getFirst().get("price"));

				total += (qty * price);
			} while (rs.next());
		} catch (SQLException ex) {
			Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return total;
	}

	public int checkCartExistence(int cashier_id) {
		Database db = new Database();
		ResultSet rs = null;
		String sql = "SELECT id FROM carts WHERE cashier_id = " + cashier_id
				+ " AND has_order = 0 ORDER BY id ASC LIMIT 1";
		Statement stmt;

		try {
			stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

			if (!rs.isBeforeFirst()) {
				db.close();
				return -1;
			}

//			rs.first();

			int id = rs.getInt("id");
			db.close();
			return id;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex, "SQL ERROR - CHECK CART EXISTENCE", JOptionPane.ERROR_MESSAGE);
		}

		return -1;
	}
}
