package database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    String host = "jdbc:sqlite:C:/IndiraMotorKasir/database.sqlite";   
    Connection conn = null;
    
    public static void prepareDatabase() {
    	File dir = new File("C:/IndiraMotorKasir");
    	if (!dir.exists()) {
    		dir.mkdirs();
    	}
    	File sqliteDb = new File("C:/IndiraMotorKasir/database.sqlite");
    	boolean isFileAlreadyExist = sqliteDb.exists();
    	try {
			sqliteDb.createNewFile();
		} catch (IOException e) {
			System.err.println(e);
		}
    	
    	Connection conn = null;
    	
    	if (!isFileAlreadyExist) {
    		try {
        		conn = DriverManager.getConnection("jdbc:sqlite:C:/IndiraMotorKasir/database.sqlite");
        		Statement stmt = conn.createStatement();
        		
        		String sql = "PRAGMA foreign_keys = ON;\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE admins (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  username TEXT NOT NULL,\r\n"
        				+ "  password INTEGER NOT NULL\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "INSERT INTO admins (id, username, password) VALUES\r\n"
        				+ "(1, 'admin', -1861353340);\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE brands (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  name TEXT NOT NULL,\r\n"
        				+ "  category_id INTEGER NOT NULL,\r\n"
        				+ "  FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT ON UPDATE RESTRICT\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE carts (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  cashier_id INTEGER NOT NULL,\r\n"
        				+ "  has_order INTEGER NOT NULL,\r\n"
        				+ "  FOREIGN KEY (cashier_id) REFERENCES cashiers(id)\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE cart_product (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  cart_id INTEGER NOT NULL,\r\n"
        				+ "  product_id INTEGER NOT NULL,\r\n"
        				+ "  qty INTEGER NOT NULL,\r\n"
        				+ "  FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE RESTRICT ON UPDATE RESTRICT,\r\n"
        				+ "  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT ON UPDATE RESTRICT\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE cashiers (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  username TEXT NOT NULL,\r\n"
        				+ "  password INTEGER NOT NULL\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE categories (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  name TEXT NOT NULL\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE orders (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  cart_id INTEGER NOT NULL UNIQUE,\r\n"
        				+ "  fee INTEGER NOT NULL,\r\n"
        				+ "  total INTEGER NOT NULL,\r\n"
        				+ "  date TEXT NOT NULL,\r\n"
        				+ "  FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE RESTRICT ON UPDATE RESTRICT\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "CREATE TABLE products (\r\n"
        				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
        				+ "  name TEXT NOT NULL,\r\n"
        				+ "  image_url TEXT NOT NULL,\r\n"
        				+ "  price INTEGER NOT NULL,\r\n"
        				+ "  stock INTEGER NOT NULL,\r\n"
        				+ "  brand_id INTEGER NOT NULL,\r\n"
        				+ "  FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE RESTRICT ON UPDATE RESTRICT\r\n"
        				+ ");\r\n"
        				+ "\r\n"
        				+ "-- Optional: create indexes (not automatically created in SQLite for foreign keys)\r\n"
        				+ "CREATE INDEX idx_brands_category_id ON brands (category_id);\r\n"
        				+ "CREATE INDEX idx_carts_cashier_id ON carts (cashier_id);\r\n"
        				+ "CREATE INDEX idx_cart_product_cart_id ON cart_product (cart_id);\r\n"
        				+ "CREATE INDEX idx_cart_product_product_id ON cart_product (product_id);\r\n"
        				+ "CREATE INDEX idx_orders_cart_id ON orders (cart_id);\r\n"
        				+ "CREATE INDEX idx_products_brand_id ON products (brand_id);";
        		
        		stmt.executeUpdate(sql);
        	} catch (SQLException e) {
        		System.err.println(e);
        	}
    	}
    	
    	if (conn != null) {
    		try {    			
				conn.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
    	}
    }
    
    public Connection connect() {
        try {
            this.conn = DriverManager.getConnection(host);
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        
        return this.conn;
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }   
}
