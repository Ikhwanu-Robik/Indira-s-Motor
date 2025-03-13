///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package controllers;

import java.util.ArrayList;
import java.util.HashMap;

///**
// *
// * @author MyBook Hype AMD
// */
public class LoginController {

    public String username;
    public int authenticated_user_id;
    public String role;

    public Boolean validate(HashMap<String, String> form_data) {
        ArrayList<HashMap<String, String>> cashier = new CashierController().findWhere("username", form_data.get("username"));
        ArrayList<HashMap<String, String>> admin = new AdminController().findWhere("username", form_data.get("username"));
        return admin != null || cashier != null;
    }

    public HashMap<String, String> authenticate(HashMap<String, String> form_data) {
        Boolean isAdmin = false;
        if (validate(form_data)) {
            isAdmin = null != new AdminController().findWhere("username", form_data.get("username"));
        } else {
            return FormatResponse.success("error", "Invalid input. That username does not exist");
        }

        if (!isAdmin) {
            HashMap<String, String> cashier = new CashierController().findWhere("username", form_data.get("username")).get(0);

            Boolean isLoggedIn = new CashierController()._attempt(Integer.parseInt(cashier.get("id")), form_data.get("password"));
            if (!isLoggedIn) {
                return FormatResponse.error("error", "Your password is wrong");
            } else {
                this.username = cashier.get("username");
                this.authenticated_user_id = Integer.parseInt(cashier.get("id"));
                this.role = "cashier";

                return FormatResponse.success("success", "Log in successful");
            }
        } else if (isAdmin) {
            HashMap<String, String> admin = new AdminController().findWhere("username", form_data.get("username")).get(0);

            Boolean isLoggedIn = new AdminController()._attempt(Integer.parseInt(admin.get("id")), form_data.get("password"));
            if (!isLoggedIn) {
                return FormatResponse.error("error", "Your password is wrong");
            } else {
                this.username = admin.get("username");
                this.authenticated_user_id = Integer.parseInt(admin.get("id"));
                this.role = "admin";

                return FormatResponse.success("success", "Log in successful");
            }
        }

        return FormatResponse.error("error", "Something unexpected happen");
    }

    public HashMap<String, String> authUser() {
        HashMap<String, String> user = new HashMap<>();
        user.put("id", Integer.toString(this.authenticated_user_id));
        user.put("username", this.username);
        user.put("role", this.role);
        return user;
    }

    public Boolean logout(int user_id) {
        if (authUser().get("id").equals(Integer.toString(user_id))) {
            this.authenticated_user_id = 0;
            this.username = null;
            this.role = null;

            return true;
        }

        return false;
    }
}
