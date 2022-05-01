package onlineBookStore;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtilityClass {
	final static String DB_Driver = "com.mysql.cj.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://localhost:3306/mybookstore";
	final static String DB_UN = "root";
	final static String DB_PASS = "0000";
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DB_Driver);
			con = DriverManager.getConnection(DB_URL, DB_UN, DB_PASS);
			if(con!=null)
				System.out.println("Connected...");
			else
				System.out.println("Connection Failed");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
