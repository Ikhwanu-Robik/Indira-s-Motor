package indira_s_motor;

import database.Database;
import pages.Login;

public class Indira_s_motor {
    public static void main(String[] args) {
    	Database.prepareDatabase();
    	
        Login.init();
    }
}
