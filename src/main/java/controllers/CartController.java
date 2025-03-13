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

        //edge case where the frontend puts in *
        stringColumns = stringColumns.equals("*") ? "id, cashier_id, has_order" : stringColumns;
        columns.clear();
        columns.add("id");
        columns.add("cashier_id");
        columns.add("has_order");

        try {
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
        String insertStatement = "INSERT INTO " + this.table_name + " (cashier_id, has_order) VALUES (" + "\"" + values.get("cashier_id") + "\"" + ", " + "\"" + values.get("has_order") + "\"" + ")";
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
        String updateStatement = "UPDATE " + this.table_name + " SET cashier_id = \"" + values.get("cashier_id") + "\", has_order = \"" + values.get("has_order") + "\"" + "WHERE id = " + id;
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
        //order cant be deleted
    }

    @Override
    public ArrayList<HashMap<String, String>> findWhere(String column, String value) {
        String whereStatement = "SELECT * FROM " + this.table_name + " WHERE " + column + " = \"" + value + "\"";
        try {
            Database db = new Database();
            ResultSet rs;
            ArrayList<HashMap<String, String>> categories = new ArrayList<>();
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(whereStatement);

            if (!rs.isBeforeFirst()) {
                db.close();
                return null;
            }
            //I expect the line below will throw an exception if ResultSet is empty a.k.a no result found for such query
            rs.first();
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
        //only allow this function to run if has_order = false
        HashMap<String, String> cart = this.findWhere("id", Integer.toString(cart_id)).getFirst();
        if (!cart.get("has_order").equals("0")) {
            //no
        } else {
            Database db = new Database();

            //cart_product.create(cart_id, product_id, qty)
            String createQuery = "INSERT INTO cart_product (cart_id, product_id, qty) VALUES (" + cart_id + ", " + product_id + ", " + qty + ")";
            Statement createStatement;
            try {
                createStatement = db.connect().createStatement();
                createStatement.execute(createQuery);
            } catch (SQLException ex) {
                Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<HashMap<String, String>> getCartProducts(int cart_id) {
        Database db = new Database();
        ResultSet rs;
        String query = "SELECT * FROM cart_product WHERE cart_id = " + cart_id;
        ArrayList<HashMap<String, String>> cart_products = new ArrayList<>();
        ArrayList<String> columns = new ArrayList<>();
        columns.clear();
        columns.add("id");
        columns.add("cart_id");
        columns.add("product_id");
        columns.add("qty");
        
        try {
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(query);
            
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
                cart_products.add(row);
            } while (rs.next());

            db.close();

            return cart_products;
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
        
    public int getTotal(int cart_id) {
        int total = 0;

        //cart_product.findWhere("cart_id", cart.id)
        Database db = new Database();
        ResultSet rs = null;
        String getCartProductQuery = "SELECT * FROM cart_product WHERE cart_id = " + cart_id;
        Statement stmt;

        try {
            stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(getCartProductQuery);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            rs.first();
            do {
                int qty = rs.getInt("qty");
                int price = Integer.parseInt(new ProductController().findWhere("id", rs.getString("product_id")).getFirst().get("price"));

                total += (qty * price);
            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;
    }
}
