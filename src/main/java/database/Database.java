package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    String host = "jdbc:mysql://127.0.0.1:3306/";   
    String database = "indira_s_motor";
    String username = "root";
    String password = "arthurmysql";
    Connection conn = null;
    
    public Connection connect() {
        try {
            this.conn = DriverManager.getConnection(host + database, username, password);
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
