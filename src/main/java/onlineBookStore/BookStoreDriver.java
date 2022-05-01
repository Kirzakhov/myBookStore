package onlineBookStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookStoreDriver {
	static Connection con = ConnectionUtilityClass.getConnection();
	static PreparedStatement pst = null;
	static ResultSet rs = null;
	public static void main(String[] args) throws IOException, SQLException {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Do you want to sell or buy? S/B");
			char ch = br.readLine().toUpperCase().charAt(0);
			if(ch=='S') {
				System.out.println("**********Welcome Seller**********");
				System.out.println("Books Trending");
				Buyer.trending(con, pst, rs);
				System.out.println("Please enter your name");
				String sellerName = br.readLine().trim();
				System.out.println("Please enter your email");
				String sellerEmail = br.readLine().trim();
				System.out.println("Please enter your phone number");
				long sellerPhone = Long.parseLong(br.readLine());
				if(!Seller.checkSeller(con, pst, rs, sellerEmail)) {
					Seller.addSeller(con, pst, rs, sellerName, sellerEmail, sellerPhone);
				}
				int sid = Seller.getId(con, pst, rs, sellerEmail);
				System.out.println("Enter bookname");
				String bName = br.readLine().trim();
				System.out.println("Enter author name");
				String aName = br.readLine().trim();
				System.out.println("Enter price of book");
				float bPrice = Float.parseFloat(br.readLine());
				System.out.println("Enter stock quantity");
				int bStock = Integer.parseInt(br.readLine());
				Book.addBook(con, pst, rs, bName, aName, bPrice, bStock, sid);
			}
			else if(ch=='B'){
				System.out.println("*********Welcome Buyer**********");
				System.out.println("Books Available");
				Book.display(con, pst, rs);
				System.out.println("Please enter name of the book you wanna buy");
				String bookName = br.readLine().trim();
				System.out.println("Please enter author name of the book you wanna buy");
				String authorName = br.readLine().trim();
				System.out.println("Please enter the number of copies you wanna buy");
				int copies = Integer.parseInt(br.readLine());
				int id = Book.checkAvailability(con, pst, rs, bookName, authorName, copies);
				if(id==0)
					System.out.println("Book not available in store");
				else {
					System.out.println("Please enter your name");
					String buyerName = br.readLine().trim();
					System.out.println("Please enter your email");
					String buyerEmail = br.readLine().trim();
					System.out.println("Please enter your phone number");
					long buyerPhone = Long.parseLong(br.readLine());
					System.out.println("Please enter your address");
					String buyerAddress = br.readLine().trim();
					float price = Book.getPrice(con, pst, rs, id);
					System.out.println("Please pay the price of the book, Rs."+price);
					float pay = Float.parseFloat(br.readLine());
					if(pay==price) {
						Book.updateStock(con, pst, rs, id, copies);
						Buyer.placeOrder(con, pst, rs, buyerName, buyerEmail, buyerPhone, buyerAddress, bookName, 
								authorName, copies);
						String em = Book.sellerInfo(con, pst, rs, id);
						System.out.println("You can follow up with seller at following mail: "+em);
					}
					else
						System.out.println("Please pay the required amount of the book");
				}
			}
			else {
				System.out.println("Please provide a proper input");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			con.close();
		}
	}

}
