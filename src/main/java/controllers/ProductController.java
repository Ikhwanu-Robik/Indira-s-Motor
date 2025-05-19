package controllers;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author MyBook Hype AMD
 */
public class ProductController extends AbstractController {

	public String table_name = "products";

	@Override
	public ArrayList<HashMap<String, String>> read(ArrayList<String> columns) {
		Database db = new Database();
		ResultSet rs;
		String stringColumns = "";
		ArrayList<HashMap<String, String>> products = new ArrayList<>();
		for (String column : columns) {
			stringColumns += (column + ",");
		}
		stringColumns = stringColumns.substring(0, stringColumns.length() - 1);

		// edge case where the frontend puts in *
		if (stringColumns.equals("*")) {
			stringColumns = "id, name, image_url, price, stock, brand_id";

			columns.clear();

			columns.add("id");
			columns.add("name");
			columns.add("image_url");
			columns.add("price");
			columns.add("stock");
			columns.add("brand_id");
		}

		try {
			Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT " + stringColumns + " FROM " + this.table_name);

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
				products.add(row);
			} while (rs.next());
			products.removeFirst();

			db.close();

			return products;
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public void create(HashMap<String, String> values) {
		String insertStatement = "INSERT INTO " + this.table_name;
		insertStatement += " (name, image_url, price, stock, brand_id) VALUES (";
		insertStatement += "\"" + values.get("name") + "\"" + ", ";
		insertStatement += "\"" + values.get("image_url") + "\"" + ", ";
		insertStatement += "\"" + values.get("price") + "\"" + ", ";
		insertStatement += "\"" + values.get("stock") + "\"" + ", ";
		insertStatement += "\"" + values.get("brand_id") + "\"";
		insertStatement += ")";

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
		String updateStatement = "UPDATE " + this.table_name + " SET";
		updateStatement += " name = \"" + values.get("name") + "\"" + ", ";
		updateStatement += " image_url = \"" + values.get("image_url") + "\"" + ", ";
		updateStatement += " price = \"" + values.get("price") + "\"" + ", ";
		updateStatement += " stock = \"" + values.get("stock") + "\"" + ", ";
		updateStatement += " brand_id = \"" + values.get("brand_id") + "\"";
		updateStatement += " WHERE id = " + id;
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
		String deleteStatement = "DELETE FROM " + this.table_name + " WHERE id = " + id;

		try {
			Database db = new Database();
			Statement stmt = db.connect().createStatement();
			stmt.execute(deleteStatement);

			db.close();
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public ArrayList<HashMap<String, String>> findWhere(String column, String value) {
		String whereStatement = "SELECT * FROM " + this.table_name + " WHERE " + column + " = \"" + value + "\"";
		try {
			Database db = new Database();
			ResultSet rs;
			ArrayList<HashMap<String, String>> products = new ArrayList<>();
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
				row.put("name", rs.getString("name"));
				row.put("image_url", rs.getString("image_url"));
				row.put("price", rs.getString("price"));
				row.put("stock", rs.getString("stock"));
				row.put("brand_id", rs.getString("brand_id"));
				products.add(row);
			} while (rs.next());
			products.removeFirst();

			db.close();

			return products;
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	public ArrayList<HashMap<String, String>> getBrand(int product_id) {
		String id = this.findWhere("id", Integer.toString(product_id)).getFirst().get("brand_id");
		return new BrandController().findWhere("id", id);
	}

	public ArrayList<HashMap<String, String>> getDetailedProducts() {
		Database db = new Database();
		ResultSet rs;
		ArrayList<HashMap<String, String>> products = new ArrayList<>();
		ArrayList<String> columns = new ArrayList<>();
		columns.add("id");
		columns.add("name");
		columns.add("image_url");
		columns.add("price");
		columns.add("stock");
		columns.add("brand_id");
		columns.add("brand_name");
		columns.add("category_name");

		try {
			Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT products.id, products.name, image_url, price, stock, brands.id AS brand_id, brands.name AS brand_name, categories.name AS category_name FROM " + this.table_name + " JOIN brands ON products.brand_id = brands.id JOIN categories ON brands.category_id = categories.id");

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
				products.add(row);
			} while (rs.next());

			db.close();

			return products;
		} catch (SQLException ex) {
			Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
