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
public class OrderController extends AbstractController {

    public String table_name = "orders";

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
        if (stringColumns.equals("*")) {
            stringColumns = "id, cart_id, fee, total, date";
            columns.clear();
            columns.add("id");
            columns.add("cart_id");
            columns.add("fee");
            columns.add("total");
            columns.add("date");
        }

        try {
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT " + stringColumns + " FROM " + this.table_name);

            if (!rs.isBeforeFirst()) {
                db.close();
                return null;
            }

//            rs.first();
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
        String insertStatement = "INSERT INTO " + this.table_name + " (cart_id, fee, total, date) VALUES (";
        insertStatement += "\"" + values.get("cart_id") + "\"" + ", ";
        insertStatement += "\"" + values.get("fee") + "\"" + ", ";
        insertStatement += "\"" + values.get("total") + "\"" + ", ";
        insertStatement += "\"" + values.get("date") + "\"";
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
        String updateStatement = "UPDATE " + this.table_name;
        updateStatement += "SET cart_id = " + "\"" + values.get("cart_id") + "\"" + ", ";
        updateStatement += "SET fee = " + "\"" + values.get("fee") + "\"" + ", ";
        updateStatement += "SET total = " + "\"" + values.get("total") + "\"" + ", ";
        updateStatement += "SET date = " + "\"" + values.get("date") + "\"";
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
            ArrayList<HashMap<String, String>> orders = new ArrayList<>();
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(whereStatement);

            if (!rs.isBeforeFirst()) {
                db.close();
                return null;
            }
            //I expect the line below will throw an exception if ResultSet is empty a.k.a no result found for such query
//            rs.first();
            do {
                HashMap<String, String> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("cart_id", rs.getString("cart_id"));
                row.put("fee", rs.getString("fee"));
                row.put("date", rs.getString("date"));
                row.put("total", rs.getString("total"));
                orders.add(row);
            } while (rs.next());

            db.close();

            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
