/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JOptionPane;

/**
 *
 * @author MyBook Hype AMD
 */
public class TransactionController {

    public int cart_id = -1;
    public int cashier_id;

    public TransactionController(int cashier_id) {
        this.cashier_id = cashier_id;
        this.createCartIfMissing();
    }

    public void createCartIfMissing() {
    	this.cart_id = new CartController().checkCartExistence(this.cashier_id);
    	
        if (cart_id == -1) {
            HashMap<String, String> values = new HashMap<>();
            values.put("cashier_id", Integer.toString(this.cashier_id));
            values.put("has_order", "0");
            new CartController().create(values);

            this.cart_id = new CartController().checkCartExistence(this.cashier_id);
        }
    }

    public void addToCart(int product_id, int qty) {
        new CartController().addProduct(product_id, qty, this.cart_id);
    }

    public void makeOrder(int cart_id, int fee) throws Exception {
        //reduce the stock
        ArrayList<HashMap<String, String>> cartProducts = new CartController().getCartProducts(cart_id);
        for (HashMap<String, String> product: cartProducts) {            
            HashMap<String, String> oldValues = new HashMap<>();
            oldValues.put("name", product.get("name"));
            oldValues.put("image_url", product.get("image_url"));
            oldValues.put("price", product.get("price"));
            oldValues.put("stock", product.get("stock"));
            oldValues.put("brand_id", product.get("brand_id"));
            
            HashMap<String, String> newValues = new HashMap<>();
            
            newValues.put("name", oldValues.get("name"));
            newValues.put("image_url", oldValues.get("image_url"));
            newValues.put("price", oldValues.get("price"));
            
            int oldStock = Integer.parseInt(oldValues.get("stock"));
            int qtyBought = Integer.parseInt(product.get("qty"));
            
            if (qtyBought > oldStock) {
            	System.err.println("kamu belinya kebanyakan kami gak stoknya kurang");
            	JOptionPane.showMessageDialog(null, "kamu belinya kebanyakan kami stoknya kurang", "ERROR", JOptionPane.ERROR_MESSAGE);
            	throw new Exception("Stok tidak cukup");
            }
            
            int newStock = oldStock - qtyBought;
            
            newValues.put("stock", Integer.toString(newStock));
            newValues.put("brand_id", oldValues.get("brand_id"));
            
            new ProductController().update(Integer.parseInt(product.get("product_id")), newValues);
        }
        
        //make the order
        HashMap<String, String> values = new HashMap<>();
        values.put("cart_id", Integer.toString(cart_id));
        values.put("fee", Integer.toString(fee));
        values.put("total", Integer.toString(new CartController().getTotal(this.cart_id)));

        String[] time = GregorianCalendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta")).getTime().toString().split(" ");
        String monthAsNumber = "";
        switch (time[1]) {
            case "Jan" -> {
                monthAsNumber = "01";
            }
            case "Feb" -> {
                monthAsNumber = "02";
            }
            case "Mar" -> {
                monthAsNumber = "03";
            }
            case "Apr" -> {
                monthAsNumber = "04";
            }
            case "May" -> {
                monthAsNumber = "05";
            }
            case "Jun" -> {
                monthAsNumber = "06";
            }
            case "Jul" -> {
                monthAsNumber = "07";
            }
            case "Aug" -> {
                monthAsNumber = "08";
            }
            case "Sep" -> {
                monthAsNumber = "09";
            }
            case "Oct" -> {
                monthAsNumber = "10";
            }
            case "Nov" -> {
                monthAsNumber = "11";
            }
            case "Dec" -> {
                monthAsNumber = "12";
            }
        }
        String date = time[5] + "-" + monthAsNumber + "-" + time[2];
        
        values.put("date", date);
        new OrderController().create(values);
        
//  set the cart's has_order to TRUE
        HashMap<String, String> updateValues = new HashMap<>();
        updateValues.put("cashier_id", Integer.toString(this.cashier_id));
        updateValues.put("has_order", "1");
        new CartController().update(this.cart_id, updateValues);
//  reset this transaction session cart id
        this.cart_id = -1;
    }

    public ArrayList<HashMap<String, String>> getProducts() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        ArrayList<HashMap<String, String>> products = new ProductController().read(columns);

        return products;
    }

    public ArrayList<HashMap<String, String>> getProducts(int brand_id) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        ArrayList<HashMap<String, String>> products = new ProductController().read(columns);

        ArrayList<HashMap<String, String>> products_by_brand = new ArrayList<>();
        for (HashMap<String, String> product : products) {
            if (product.get("brand_id").equals(Integer.toString(brand_id))) {
                products_by_brand.add(product);
            }
        }

        return products_by_brand;
    }

    public ArrayList<HashMap<String, String>> getProducts(int brand_id, int category_id) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        ArrayList<HashMap<String, String>> products = new ProductController().read(columns);
 
        ArrayList<HashMap<String, String>> products_by_brand = new ArrayList<>();
        for (HashMap<String, String> product : products) {
            if (product.get("brand_id").equals(Integer.toString(brand_id))) {
                products_by_brand.add(product);
            }
        }
        
        ArrayList<HashMap<String, String>> products_by_category = new ArrayList<>();
        for (HashMap<String, String> product : products_by_brand) {
            HashMap<String, String> brand = new ProductController().getBrand(Integer.parseInt(product.get("id"))).getFirst();

            if (brand.get("category_id").equals(Integer.toString(category_id))) {
                products_by_category.add(product);
            }
        }

        //if there is no result for said category, then return null a.k.a your category-brand combination doesn't exist
        if (products_by_category.isEmpty()) {
            return null;
        }
        return products_by_category;
    }   
}
