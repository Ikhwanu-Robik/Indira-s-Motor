/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MyBook Hype AMD
 */
public class CashierController extends AbstractController {

    public String table_name = "cashiers";

    @Override
    public ArrayList<HashMap<String, String>> read(ArrayList<String> columns) {
        Database db = new Database();
        ResultSet rs;
        String stringColumns = "";
        ArrayList<HashMap<String, String>> cashiers = new ArrayList<>();
        for (String column: columns) {
            stringColumns += (column + ",");
        }
        stringColumns = stringColumns.substring(0, stringColumns.length() - 1);
        
        //edge case where the frontend puts in *
        stringColumns = stringColumns.equals("*") ? "id, username" : stringColumns;
        columns.clear();
        columns.add("id");
        columns.add("username");
         
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
                cashiers.add(row);
            }while(rs.next());
            
            db.close();

            return cashiers;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void create(HashMap<String, String> values) {
        values.put("password", Integer.toString(values.get("password").hashCode()));

        String insertStatement = "INSERT INTO " + this.table_name + " (username, password) VALUES (" + "\"" + values.get("username") + "\"" + ", " + "\"" + values.get("password") + "\"" + ")";
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
        values.put("password", Integer.toString(values.get("password").hashCode()));

        String updateStatement = "UPDATE " + this.table_name + " SET username = \"" + values.get("username") + "\", password = " + values.get("password") + " WHERE id = " + id;
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
            ArrayList<HashMap<String, String>> cashiers = new ArrayList<>();
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
                row.put("username", rs.getString("username"));
                row.put("password", rs.getString("password"));
                cashiers.add(row);
            } while(rs.next());

            db.close();

            return cashiers;
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public Boolean _attempt(int id, String password) {
        Database db = new Database();
        ResultSet rs;
        Statement stmt;
        Boolean attemptSuccess = false;
        password = Integer.toString(password.hashCode());

        try {
            stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM " + this.table_name + " WHERE id = " + id);
            rs.next();
            attemptSuccess = password.equals(rs.getString("password"));
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return attemptSuccess;
    }
}
