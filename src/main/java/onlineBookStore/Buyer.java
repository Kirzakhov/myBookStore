package onlineBookStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Buyer {
	public static void placeOrder(Connection con, PreparedStatement pst, ResultSet rs, 
			String buyerName, String buyerEmail, long buyerPhone, String buyerAddress, 
			String bookName, String authorName, int copies) throws SQLException {
		String ins = "insert into buyer(name, phone, email, address, bname, aname, copies) values(?,?,?,?,?,?,?)";
		pst = con.prepareStatement(ins);
		pst.setString(1, buyerName);
		pst.setLong(2, buyerPhone);
		pst.setString(3, buyerEmail);
		pst.setString(4, buyerAddress);
		pst.setString(5, bookName);
		pst.setString(6, authorName);
		pst.setInt(7, copies);
		int i = pst.executeUpdate();
		if(i>0)
			System.out.println("Order placed successfully");
		else
			System.out.println("Not added");
	}
	public static void makePayment() {
		
	}
	public static void trending(Connection con, PreparedStatement pst, ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String sel = "select distinct bname, aname from buyer order by orderdate desc limit 3";
		pst = con.prepareStatement(sel);
		rs = pst.executeQuery();
		System.out.println("Book Name\tAuthor Name");
		while(rs.next()) {
			String bName = rs.getString(1); //rs.getString("bname");
			String aName = rs.getString(2); //rs.getString("aname");
			System.out.println(bName + "\t\t" +aName);
		}
	}
}
