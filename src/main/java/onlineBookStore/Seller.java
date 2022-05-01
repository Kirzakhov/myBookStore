package onlineBookStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Seller {
	public static boolean checkSeller(Connection con, PreparedStatement pst, ResultSet rs, 
			String email) throws SQLException {
		String sel = "select * from seller where email=?";
		pst = con.prepareStatement(sel);
		pst.setString(1, email);
		rs = pst.executeQuery();
		if(rs.next())
			return true;
		else
			return false;
		
	}
	public static int getId(Connection con, PreparedStatement pst, ResultSet rs, String email) 
			throws SQLException {
		String sel = "select id from seller where email=?";
		pst = con.prepareStatement(sel);
		pst.setString(1, email);
		rs = pst.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		return id;
	}
	public static void addSeller(Connection con, PreparedStatement pst, ResultSet rs, String name,
			String email, long phone) throws SQLException {
		String ins = "insert into seller(name, email, phone) values(?,?,?)";
		pst = con.prepareStatement(ins);
		pst.setString(1, name);
		pst.setString(2, email);
		pst.setLong(3, phone);
		int i = pst.executeUpdate();
		if(i>0)
			System.out.println("Seller added successfully");
		else
			System.out.println("Not added");
	}
}
