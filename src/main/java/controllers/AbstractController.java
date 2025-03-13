package controllers;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author MyBook Hype AMD
 */
public abstract class AbstractController {

    public abstract ArrayList<HashMap<String, String>> read(ArrayList<String> columns);

    public abstract void create(HashMap<String, String> values);
    
    public abstract void update(int id, HashMap<String, String> values);
    
    public abstract void delete(int id);
    
    public abstract ArrayList<HashMap<String, String>> findWhere(String column, String value);
}
