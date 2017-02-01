import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;


import java.net.UnknownHostException;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
public class intoSql {

	private static List<Product> products = null;

	public static List<Product> getAllProducts() {
		if(products == null) {
			insertIntoDB();
			//products = getProductsFromDB()
		}
		return products;
	}

	public static void  insertIntoDB() {
		//HttpSession session = request.getSession();
		//String ProductType = request.getParameter("category");
		//XMLlist prList = new XMLlist("ProductCatalog.xml", session);
		XMLlist prList = new XMLlist("ProductCatalog.xml");
		//List<Product> prlist123 = (ArrayList<Product>)session.getAttribute("productlist");
		List<Product> prlist123 = prList.getProducts();
		System.out.println(prlist123.size());
		String name;


		for (int i = 0; i < prlist123.size(); i++) {
			System.out.println(prlist123.get(i).getType());
			System.out.println(prlist123.get(i).getName());
			//System.out.println(prlist123.get(i).getRetailer());

			name = prlist123.get(i).name;
			String num = (String)prlist123.get(i).id;
			String image = prlist123.get(i).image;
			int price = prlist123.get(i).price;
			String type = prlist123.get(i).getType();
			String retailer = prlist123.get(i).retailer;
			String productCondition = prlist123.get(i).condition;

			try {
				//connect to mysql data base;
				Connection conn = null;
				Statement stmt = null;
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94"); // <== Check!

				stmt = conn.createStatement();

				String sqlStr =  "INSERT  INTO products(ProductId,ProductName,image,retailer,ProductCondition,ProductType,ProductPrice) " + "VALUES (?,?,?,?,?,?,?);";
				PreparedStatement pst = conn.prepareStatement(sqlStr);
				pst.setString(1, num);
				pst.setString(2, name);
				pst.setString(3, image);
				pst.setString(4, retailer);
				pst.setString(5, productCondition);
				pst.setString(6, type);
				pst.setInt(7, price);
				pst.execute();
				System.out.println("Document inserted successfully");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

public static void  deleteFromDB() {
				try {
				//connect to mysql data base;
				Connection conn = null;
				Statement stmt = null;
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94"); // <== Check!

				stmt = conn.createStatement();

				String sqlStr =  "truncate table products";
				PreparedStatement pst = conn.prepareStatement(sqlStr);
				pst.execute();
				} catch (Exception e) {
				System.out.println(e);
			}
}



	
}



