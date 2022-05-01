package onlineBookStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
	public static void addBook(Connection con, PreparedStatement pst, ResultSet rs, 
			String bName, String aName, float bPrice, int bStock, int sid) throws SQLException {
		String ins = "insert into book(bnname, aname, price, stock, sellerid) values(?,?,?,?,?)";
		pst = con.prepareStatement(ins);
		pst.setString(1, bName);
		pst.setString(2, aName);
		pst.setFloat(3, bPrice);
		pst.setInt(4, bStock);
		pst.setInt(5, sid);
		int i = pst.executeUpdate();
		if(i>0)
			System.out.println("Book added successfully");
		else
			System.out.println("Not added");
	}
	public static int checkAvailability(Connection con, PreparedStatement pst, ResultSet rs, 
			String bookName, String authorName, int copies) throws SQLException {
		String sel = "select * from book where bnname=? and aname=?";
		pst = con.prepareStatement(sel);
		pst.setString(1, bookName);
		pst.setString(2, authorName);
		rs = pst.executeQuery();
		float minPrice = Float.MAX_VALUE;
		int id;
		int tId = 0;
		float price;
		int stock;
		while(rs.next()) {
			id = rs.getInt(1);
			price = rs.getFloat(4);
			stock = rs.getInt(5);
			if(price<minPrice && copies<=stock) {
				minPrice = price;
				tId = id;
			}
		}
		return tId;
	}
	public static void updateStock(Connection con, PreparedStatement pst, ResultSet rs, int id, int copies) throws SQLException {
		String sel = "select * from book where id=?";
		pst = con.prepareStatement(sel);
		pst.setInt(1, id);
		rs = pst.executeQuery();
		rs.next();
		int st = rs.getInt(5);
		int so = rs.getInt(6) + copies;
		int rem = st - copies;
		String upd = "update book set stock=?, sold=? where id=?";
		pst = con.prepareStatement(upd);
		pst.setInt(1, rem);
		pst.setInt(2, so);
		pst.setInt(3, id);
		int i = pst.executeUpdate();
		if(i>0)
			System.out.println("Data updated successfully");
		else
			System.out.println("Not updated");
	}
	public static String sellerInfo(Connection con, PreparedStatement pst, ResultSet rs, int id) throws SQLException {
		String sel = "select * from book where id=?";
		pst = con.prepareStatement(sel);
		pst.setInt(1, id);
		rs = pst.executeQuery();
		rs.next();
		int sid = rs.getInt(7);
		String sel1 = "select * from seller where id=?";
		pst = con.prepareStatement(sel1);
		pst.setInt(1, sid);
		rs = pst.executeQuery();
		rs.next();
		String em = rs.getString(3);
		return em;
		
	}
	public static float getPrice(Connection con, PreparedStatement pst, ResultSet rs, int id) throws SQLException {
		String sel = "select * from book where id=?";
		pst = con.prepareStatement(sel);
		pst.setInt(1, id);
		rs = pst.executeQuery();
		rs.next();
		float price = rs.getFloat(4);
		return price;
	}
	public static void display(Connection con, PreparedStatement pst, ResultSet rs) 
			throws SQLException {
		String sel = "select * from book";
		pst = con.prepareStatement(sel);
		rs = pst.executeQuery();
		System.out.println("Book Name\tAuthor Name\tPrice in Rs.\tStock");
		while(rs.next()) {
			String bName = rs.getString(2); //rs.getString("bnname");
			String aName = rs.getString(3); //rs.getString("aname");
			float price = rs.getFloat(4);
			int stock = rs.getInt(5);
			System.out.println(bName + "\t\t" +aName+"\t\t"+price+"\t\t"+stock);
		}
		
	}
}
