/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package indira_s_motor_ant;

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
          form_data.put("username", "nafisa");
          form_data.put("password", "12345678");
          //original -1426118335
          LoginController login = new LoginController();
          Boolean isLoggedIn = login.authenticate(form_data);
          if (isLoggedIn) {
              System.out.println("Logged in as : " + login.username + " as an " + login.role);
          }
          else {
              System.out.println("Login failed");
          }
    }
}
