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
public class BrandController extends AbstractController {
    public String table_name = "brands";

    @Override
    public ArrayList<HashMap<String, String>> read(ArrayList<String> columns) {
        Database db = new Database();
        ResultSet rs;
        String stringColumns = "";
        ArrayList<HashMap<String, String>> brands = new ArrayList<>();
        for (String column: columns) {
            stringColumns += (column + ",");
        }
        stringColumns = stringColumns.substring(0, stringColumns.length() - 1);
        
        //edge case where the frontend puts in *
        if (stringColumns.equals("*")) {
            stringColumns = "id, name, category_id";
            columns.clear();
            columns.add("id");
            columns.add("name");
            columns.add("category_id");
        }
         
        try {
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT " + stringColumns + " FROM " + this.table_name);
            
            if(!rs.isBeforeFirst()) {
                db.close();
                return null;
            }
            
//            rs.first();
            do {
                HashMap<String, String> row = new HashMap<>();
                for (String column: columns) {
                    row.put(column, rs.getString(column));
                }
                brands.add(row);
            }while(rs.next());
            brands.removeFirst();
            
            db.close();

            return brands;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void create(HashMap<String, String> values) {
        String insertStatement = "INSERT INTO " + this.table_name + " (name, category_id) VALUES (" + "\"" + values.get("name") + "\"" + ", " + "\"" + values.get("category_id") + "\"" + ")";
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
        String updateStatement = "UPDATE " + this.table_name + " SET name = " +  "\"" + values.get("name") + "\"" + ", " + "\"" + values.get("category_id") + "\"" + " WHERE id = " + id;
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
            ArrayList<HashMap<String, String>> brands = new ArrayList<>();
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(whereStatement);
            
            if(!rs.isBeforeFirst()) {
                db.close();
                return null;
            }
            //I expect the line below will throw an exception if ResultSet is empty a.k.a no result found for such query
//            rs.first();
            do {
                HashMap<String, String> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("name", rs.getString("name"));
                row.put("category_id", rs.getString("category_id"));
                brands.add(row);
            } while(rs.next());

            db.close();

            return brands;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public ArrayList<HashMap<String, String>> getCategory(int category_id) {
        String id = this.findWhere("id", Integer.toString(category_id)).getFirst().get("brand_id");
        return new CategoryController().findWhere("id", id);
    }
}
