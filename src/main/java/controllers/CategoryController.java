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
public class CategoryController extends AbstractController {
    public String table_name = "categories";

    @Override
    public ArrayList<HashMap<String, String>> read(ArrayList<String> columns) {
        Database db = new Database();
        ResultSet rs;
        String stringColumns = "";
        ArrayList<HashMap<String, String>> categories = new ArrayList<>();
        for (String column: columns) {
            stringColumns += (column + ",");
        }
        stringColumns = stringColumns.substring(0, stringColumns.length() - 1);
        
        //edge case where the frontend puts in *
        stringColumns = stringColumns.equals("*") ? "id, name" : stringColumns;
        columns.clear();
        columns.add("id");
        columns.add("name");
         
        try {
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT " + stringColumns + " FROM " + this.table_name);
            
            if(!rs.isBeforeFirst()) {
                db.close();
                return null;
            }
            
            rs.first();
            do {
                HashMap<String, String> row = new HashMap<>();
                for (String column: columns) {
                    row.put(column, rs.getString(column));
                }
                categories.add(row);
            }while(rs.next());
            
            db.close();

            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void create(HashMap<String, String> values) {
        String insertStatement = "INSERT INTO " + this.table_name + " (name) VALUES (" + "\"" + values.get("name") + "\"" + ")";
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
        String updateStatement = "UPDATE " + this.table_name + " SET name = \"" + values.get("name") + "\"" + "WHERE id = " + id;
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
            ArrayList<HashMap<String, String>> categories = new ArrayList<>();
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(whereStatement);
            
            if(!rs.isBeforeFirst()) {
                db.close();
                return null;
            }
            //I expect the line below will throw an exception if ResultSet is empty a.k.a no result found for such query
            rs.first();
            do {
                HashMap<String, String> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("name", rs.getString("name"));
                categories.add(row);
            } while(rs.next());

            db.close();

            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
