import javax.servlet.http.*;
import java.net.*;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.*;

public class PlaceOrder extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String htmlString = "";
		HttpSession session = request.getSession();
		String userid = request.getParameter("userid");
		String cardNum = request.getParameter("cardNum");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");




		Random r = new Random( System.currentTimeMillis());
		int rand = 10000 + r.nextInt(20000);
		String randString = Integer.toString(rand);
		Enumeration paramNames = request.getParameterNames();
		String storeorder = randString + "-";
		String title = "Your Order Placed Successfully";
		out.println("<HTML>\n" +
		            "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
		            "<BODY BGCOLOR=\"#FDF5E6\">\n" +
		            "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
		            "<H3 ALIGN=CENTER>Your Order Number :" + randString + "</H3>\n" +
		            "<TABLE BORDER=1 ALIGN=CENTER>\n" +
		            "<TR BGCOLOR=\"#FFAD00\">\n" +
		            "<TH>Parameter Name</TH>");
		out.print("<TR><TD> First Name:"  + request.getParameter("userid") + "\n</TD></TR>");
		out.print("<TR><TD> Address :" + request.getParameter("address") + "\n</TD></TR>");
		out.print("<TR><TD> Phone Number:"  + request.getParameter("phoneNumber") + "\n</TD></TR>");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 14); // Adding 14 days
		String output = sd.format(c.getTime());

		out.print("<TR><TD> Delivery Date:"  + output + "\n</TD></TR>");
		//out.print("<TD>" + output + "\n</TD></TR>");
		out.println("</TABLE>\n");


		storeorder += output;

		ShoppingCart cart;
		cart = (ShoppingCart)session.getAttribute("shoppingCart");
		/*if (cart == null) {
			out.println("Cart is empty");
		}
		else {
			out.println("Cart is not empty");
		}*/
		List itemsOrdered = cart.getItemsOrdered();




		Connection conn = null;
		Statement stmt = null;






		/*for(int i=0;i<itemsOrdered.size();i++) {

				ItemOrder order= (ItemOrder)itemsOrdered.get(i);
		MySQLDataStoreUtilities obj = new MySQLDataStoreUtilities();
		obj.insertOrder(userid,order.getItemID(),order.getItemName(),order.getUnitCost(),order.getNumItems(),cardNum,address,phoneNumber,randString);
		}*/
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94");
			stmt = conn.createStatement();

			for (int i = 0; i < itemsOrdered.size(); i++) {

				ItemOrder order = (ItemOrder)itemsOrdered.get(i);

				String sqlStr =  "INSERT INTO Customerorders(username,ItemID,ItemName,UnitCost,NumItems,cardnum,address,phoneNumber,randstring) " + "VALUES (?,?,?,?,?,?,?,?,?);";
				PreparedStatement pst = conn.prepareStatement(sqlStr);
				pst.setString(1, userid);
				pst.setString(2, order.getItemID());
				pst.setString(3, order.getItemName());
				pst.setInt(4, order.getUnitCost());
				pst.setInt(5, order.getNumItems());
				pst.setString(6, cardNum);
				pst.setString(7, address);
				pst.setString(8, phoneNumber);
				pst.setString(9, randString);

				pst.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


// htmlString +="<h2>Your Order has been placed!!!!!</h2>";
		htmlString += "<a href='welcomeLogin.html'> <img src='https://cdn4.iconfinder.com/data/icons/pictype-free-vector-icons/16/home-512.png' style='height: 60px; width: 60px;margin-left: 50%;margin-top: 40px;'/> </a>";
		out.println(htmlString);
	}
}


