/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package indira_s_motor_ant;

import controllers.CashierController;
import controllers.LoginController;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author MyBook Hype AMD
 */
public class Indira_s_motor_ant {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          HashMap<String, String> form_data = new HashMap<>();
          form_data.put("username", "rasyid");
          form_data.put("password", "12345678");
          
          LoginController login = new LoginController();
          HashMap<String, String> authResponse = login.authenticate(form_data);
          if (authResponse.get("status").equals("success")) {
              System.out.println("Logged in as : " + login.username + " with role :  " + login.role);
          }
          else {
              System.out.println(authResponse.get("message"));
          }
          
          //creating data
          HashMap<String, String> cashier_data = new HashMap<>();
          cashier_data.put("username", "naruto");
          cashier_data.put("password", "12345678");
          new CashierController().create(cashier_data);

          //keeping all cashiers' id
          ArrayList<String> cashier_ids = new ArrayList<>();

          //getting data
          ArrayList<String> columns = new ArrayList<>();
          columns.add("*");
          ArrayList<HashMap<String, String>> cashiers = new CashierController().read(columns);
          if(cashiers != null) {
              System.out.println("LIST OF CASHIERS");
              for (HashMap<String, String> cashier: cashiers) {
                  cashier_ids.add(cashier.get("id"));
                  
                  System.out.println("ID : " + cashier.get("id") + " | " + "USERNAME : " + cashier.get("username"));
              }
          }
          else {
              System.out.println("No cashier found");
          }
          
          //updating the data
          int id = Integer.parseInt(cashier_ids.get(cashier_ids.size() - 1));
          HashMap<String, String> newData = new HashMap<>();
          newData.put("username", "gamergirl");
          newData.put("password", "1245678");
          new CashierController().update(id, newData);
          
          cashiers = new CashierController().read(columns);
          if(cashiers != null) {
              System.out.println("LIST OF CASHIERS");
              for (HashMap<String, String> cashier: cashiers) {
                  System.out.println("ID : " + cashier.get("id") + " | " + "USERNAME : " + cashier.get("username"));
              }
          }
          else {
              System.out.println("No cashier found");
          }
          
          //find a specific data
          ArrayList<HashMap<String, String>> cashier = new CashierController().findWhere("username", "gamergirl");
          System.out.println("ID : " + cashier.get(0).get("id") + " | " + "USERNAME : " + cashier.get(0).get("username"));
          
          //delete a data
          int latest_cashier_id = Integer.parseInt(cashier_ids.get(cashier_ids.size() - 1));
          new CashierController().delete(latest_cashier_id);
          
          cashiers = new CashierController().read(columns);
          if(cashiers != null) {
              System.out.println("LIST OF CASHIERS");
              for (HashMap<String, String> cashier1: cashiers) {
                  System.out.println("ID : " + cashier1.get("id") + " | " + "USERNAME : " + cashier1.get("username"));
              }
          }
          else {
              System.out.println("No cashier found");
          }
    }
}
