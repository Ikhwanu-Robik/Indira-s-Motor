package indira_s_motor;

import controllers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//imports for uploading file
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Indira_s_motor {

    public static void listProducts() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        ArrayList<HashMap<String, String>> products = new ProductController().read(columns);

        System.out.println("LIST OF PRODUCTS");
        for (HashMap<String, String> product : products) {
            System.out.println(product.get("id") + " " + product.get("name") + " " + product.get("price") + " " + product.get("stock"));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
// *
// *      
// *      
// *      
// *
//logging in
/*
        HashMap<String, String> form_data = new HashMap<>();
        form_data.put("username", "rasyid");
        form_data.put("password", "12345678");

        LoginController loginSession = new LoginController();
        HashMap<String, String> authResponse = loginSession.authenticate(form_data);
        if (authResponse.get("status").equals("success")) {
            System.out.println("Logged in as : " + loginSession.username + " with role :  " + loginSession.role);
        } else {
            System.out.println(authResponse.get("message"));
        }
        */

// *
// *
// *
// *
//getting data from regular controller
/*
          ArrayList<String> columns = new ArrayList<>();
          columns.add("*");
          ArrayList<HashMap<String, String>> categories = new CategoryController().read(columns);
          
          if(categories != null) {
              System.out.println("LIST OF CATEGORIES");
              for (HashMap<String, String> cashier: categories) {
                  System.out.println("ID : " + cashier.get("id") + " | " + "NAME : " + cashier.get("name"));
              }
          }
          else {
              System.out.println("No category found");
          }
         */

// *
// *
// *
// *
//updating the data
/*      
          ArrayList<String> columns = new ArrayList<>();
          columns.add("*");
          ArrayList<HashMap<String, String>> categories = new CategoryController().read(columns);

          int id = 1;
          HashMap<String, String> newData = new HashMap<>();
          //change the name to oli
          newData.put("name", "oli");
          new CategoryController().update(id, newData);
         */

// *
// *
// *
// *
//creating product
/*
        System.out.println("\n\nCreating new Product\n\n");

        String name;
        String image_url;
        String price;
        String stock;
        String brand_id;

        Scanner input = new Scanner(System.in);
        System.out.println("Input name.");
        name = input.nextLine();
        System.out.println("Input price.");
        price = input.nextLine();
        System.out.println("Input stock.");
        stock = input.nextLine();
        System.out.println("Input brand_id.");
        brand_id = input.nextLine();

        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        image_url = fc.getSelectedFile().getName();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();

            try {
                // Get the current directory's canonical path
                File currentDir = new File(".").getCanonicalFile();

                // Create a File object for the "images" directory within the current directory
                File imagesDir = new File(currentDir, "src//main//resources//assets");
                System.out.println(imagesDir.getCanonicalPath());

                // Define the target file within the images directory
                File targetFile = new File(imagesDir, selectedFile.getName());

                // Copy the selected file to the target location
                Files.copy(
                        selectedFile.toPath(),
                        targetFile.toPath()
                );

            } catch (IOException ex) {
                Logger.getLogger(Indira_s_motor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        HashMap<String, String> product_data = new HashMap<>();
        product_data.put("name", name);
        product_data.put("image_url", image_url);
        product_data.put("price", price);
        product_data.put("stock", stock);
        product_data.put("brand_id", brand_id);
        new ProductController().create(product_data);

        System.out.println("\n\n");
        listProducts();
         */

// *
// *
// *
// *
//get products with filter
/*
        TransactionController transactionSession = new TransactionController(loginSession.authenticated_user_id);

        ArrayList<HashMap<String, String>> products = transactionSession.getProducts(1, 1);

        System.out.println("LIST OF *OIL*id1* PRODUCTS BY *AHM*id1*");
        for (HashMap<String, String> product : products) {
            System.out.println(product.get("id") + " " + product.get("name") + " " + product.get("price") + " " + product.get("stock"));
        }
         */

// *
// *
// *
// *
//making transaction
/*
        listProducts();
        
        TransactionController transactionSession = new TransactionController(loginSession.authenticated_user_id);
        transactionSession.createCartIfMissing();
        System.out.println("NOTE : if you answer no, it will order your whole cart");
        String response;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("Add product to cart. (YES / NO)");

            response = input.nextLine();
            if (response.equals("YES")) {
                System.out.println("  Product id?");
                int product_id = input.nextInt();
                System.out.println("  How much?");
                int qty = input.nextInt();

                transactionSession.addToCart(product_id, qty);
            }
        } while (!response.equals("NO"));

        transactionSession.makeOrder(transactionSession.cart_id, 5000);
         */

// *
// *
// *
// *
//print a data of order
/*
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, String> type = new HashMap<>();
        type.put("type", "receipt");
        data.add(type);
        //get data / latest order
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        HashMap<String, String> order = new OrderController().read(columns).getLast();
        data.add(order);
        
        try {
            new PrintController().print(data);
        } catch (IOException er) {
            Logger.getLogger(Indira_s_motor.class.getName()).log(Level.SEVERE, null, er);
        }
         */

// *
// *
// *
// *
//getting reports
/*        ArrayList<HashMap<String, String>> reports = new ReportController().getReports();

        System.out.println("Order Records");
        for (HashMap<String, String> report : reports) {
            int cart_id = 0;

            for (String key : report.keySet()) {
                System.out.print(key + " : " + report.get(key) + " | ");

                if (key.equals("cart_id")) {
                    cart_id = Integer.parseInt(report.get(key));
                }
            }
            System.out.println("\n");

            System.out.println("Details");
            ArrayList<HashMap<String, String>> cart_products = new CartController().getCartProducts(cart_id);
            for (HashMap<String, String> product : cart_products) {
                for (String prod_key : product.keySet()) {
                    System.out.print(prod_key + " : " + product.get(prod_key) + " | ");
                }
                System.out.println("");
            }
            System.out.println("\n");
        }
         */

// *
// *
// *
// *
//print a data of report
/*
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, String> type = new HashMap<>();
        type.put("type", "report");
        data.add(type);
        
        //get data / latest order
        ArrayList<HashMap<String, String>> report = new ReportController().getReports();
        for (HashMap<String, String> report_row: report) {
            data.add(report_row);
        }
        
        try {
            new PrintController().print(data);
        } catch (IOException er) {
            Logger.getLogger(Indira_s_motor.class.getName()).log(Level.SEVERE, null, er);
        }
         */

// *
// *
// *
// *
//find a specific data
/*
          ArrayList<HashMap<String, String>> cashier = new CategoryController().findWhere("name", "oli");
          System.out.println("ID : " + cashier.get(0).get("id") + " | " + "NAME : " + cashier.get(0).get("name"));
         */

// *
// *
// *
// *
//delete a data
/*
          int id = 1;
          new CategoryController().delete(id);
          */

// *
// *
// *
// *
//getting reports
/*        ArrayList<HashMap<String, String>> reports = new ReportController().getReports();

        System.out.println("Order Records");
        for (HashMap<String, String> report : reports) {
            int cart_id = 0;

            for (String key : report.keySet()) {
                System.out.print(key + " : " + report.get(key) + " | ");

                if (key.equals("cart_id")) {
                    cart_id = Integer.parseInt(report.get(key));
                }
            }
            System.out.println("\n");

            System.out.println("Details");
            ArrayList<HashMap<String, String>> cart_products = new CartController().getCartProducts(cart_id);
            for (HashMap<String, String> product : cart_products) {
                for (String prod_key : product.keySet()) {
                    System.out.print(prod_key + " : " + product.get(prod_key) + " | ");
                }
                System.out.println("");
            }
            System.out.println("\n");
        }
         */
//        listProducts();

// *
// *
// *
// *
//creating product
/*
        System.out.println("\n\nCreating new Product\n\n");

        String name;
        String image_url;
        String price;
        String stock;
        String brand_id;

        Scanner input = new Scanner(System.in);
        System.out.println("Input name.");
        name = input.nextLine();
        System.out.println("Input price.");
        price = input.nextLine();
        System.out.println("Input stock.");
        stock = input.nextLine();
        System.out.println("Input brand_id.");
        brand_id = input.nextLine();

        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        image_url = fc.getSelectedFile().getName();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();

            try {
                // Get the current directory's canonical path
                File currentDir = new File(".").getCanonicalFile();

                // Create a File object for the "images" directory within the current directory
                File imagesDir = new File(currentDir, "images");

                // Define the target file within the images directory
                File targetFile = new File(imagesDir, selectedFile.getName());

                // Copy the selected file to the target location
                Files.copy(
                        selectedFile.toPath(),
                        targetFile.toPath()
                );

            } catch (IOException ex) {
                Logger.getLogger(Indira_s_motor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        HashMap<String, String> product_data = new HashMap<>();
        product_data.put("name", name);
        product_data.put("image_url", image_url);
        product_data.put("price", price);
        product_data.put("stock", stock);
        product_data.put("brand_id", brand_id);
        new ProductController().create(product_data);

        System.out.println("\n\n");
        listProducts();
         */

// *
// *
// *
// *
//Example of making transaction
/*
          TransactionController transactionSession = new TransactionController(loginSession.authenticated_user_id);
          transactionSession.createCartIfMissing();
          System.out.println("NOTE : if you answer no, it will order your whole cart");
          String response;
          do {
            Scanner input = new Scanner(System.in);
            System.out.println("Add product to cart. (YES / NO)");
            
            response = input.nextLine();
            if (response.equals("YES")) {
                System.out.println("  Product id?");
                int product_id = input.nextInt();
                System.out.println("  How much?");
                int qty = input.nextInt();
                
                transactionSession.addToCart(product_id, qty);
            }
          } while(!response.equals("NO"));

          transactionSession.makeOrder(transactionSession.cart_id, 5000);
         */

// *
// *
// *
// *
//get products with filter
/*
        TransactionController transactionSession = new TransactionController(loginSession.authenticated_user_id);

        ArrayList<HashMap<String, String>> products = transactionSession.getProducts(1, 1);

        System.out.println("LIST OF *OIL*id1* PRODUCTS BY *AHM*id1*");
        for (HashMap<String, String> product : products) {
            System.out.println(product.get("id") + " " + product.get("name") + " " + product.get("price") + " " + product.get("stock"));
        }
         */

// *
// *
// *
// *
//getting data from regular controller
/*
          ArrayList<String> columns = new ArrayList<>();
          columns.add("*");
          ArrayList<HashMap<String, String>> categories = new CategoryController().read(columns);
          
          if(categories != null) {
              System.out.println("LIST OF CATEGORIES");
              for (HashMap<String, String> cashier: categories) {
                  System.out.println("ID : " + cashier.get("id") + " | " + "NAME : " + cashier.get("name"));
              }
          }
          else {
              System.out.println("No category found");
          }
         */

// *
// *
// *
// *
//updating the data
/*      
          ArrayList<String> columns = new ArrayList<>();
          columns.add("*");
          ArrayList<HashMap<String, String>> categories = new CategoryController().read(columns);

          int id = 1;
          HashMap<String, String> newData = new HashMap<>();
          //change the name to oli
          newData.put("name", "oli");
          new CategoryController().update(id, newData);
         */
    }
}
