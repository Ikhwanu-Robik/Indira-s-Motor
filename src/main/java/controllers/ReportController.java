/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MyBook Hype AMD
 */
public class ReportController {

    public ArrayList<HashMap<String, String>> getReports() {
        Database db = new Database();
        ResultSet rs = null;
        ArrayList<HashMap<String, String>> result = new ArrayList<>();

        String query = """
                       SELECT `orders`.`id`, `orders`.`cart_id`, `orders`.`fee`, `orders`.`total`, `orders`.`date`, `cashiers`.`username`, COUNT(`cart_product`.`id`) AS product_types FROM `orders`
                       JOIN `carts` ON `orders`.`cart_id` = `carts`.`id`
                       JOIN `cashiers` ON `carts`.`cashier_id` = `cashiers`.id
                       JOIN `cart_product` ON `carts`.`id` = `cart_product`.`cart_id`
                       GROUP BY cart_id""";
        try {
            Statement stmt = db.connect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
        } catch (SQLException er) {
            System.out.println(er);
        }

        try {
            if (!rs.isBeforeFirst()) {
                return null;
            }

            rs.first();
            do {
                HashMap<String, String> row = new HashMap<>();
                
                row.put("id", rs.getString("id"));
                row.put("cart_id", rs.getString("cart_id"));
                row.put("fee", rs.getString("fee"));
                row.put("total", rs.getString("total"));
                row.put("date", rs.getString("date"));
                row.put("username", rs.getString("username"));
                row.put("product_types", rs.getString("product_types"));
// TODO : a bug where the result will be filled with rs length amount of last resultset duplicates 
// FIX : moving the definition of row HashMap from outside to inside the do while block
// EXPLANATION : when we call result.add(row), we add a pointer to the location of row into result. Basically it's putting one same variable twice

                result.add(row);
            } while(rs.next());
            
        } catch (SQLException er) {
            System.out.println(er);
        }

        try {
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
