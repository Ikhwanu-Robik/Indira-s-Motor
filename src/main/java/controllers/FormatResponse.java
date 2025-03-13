/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.HashMap;

/**
 *
 * @author MyBook Hype AMD
 */
public class FormatResponse {

    public static HashMap<String, String> success(String status, String message) {
        HashMap<String, String> success = new HashMap<>();
        success.put("status", status);
        success.put("message", message);

        return success;
    }
    
    public static HashMap<String, String> error(String status, String message) {
        HashMap<String, String> error = new HashMap<>();
        error.put("status", status);
        error.put("message", message);

        return error;
    }
}
