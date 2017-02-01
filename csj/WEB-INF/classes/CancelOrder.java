import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


public class CancelOrder extends HttpServlet {
	long diffDays = 0;

	private static final long serialVersionUID = 1L;


	public void init() throws ServletException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Connection conn = null;
        Statement stmt = null;

		try {
			//Get the values from the form
			//String orderNo = "";
			String searchParameter = request.getParameter("orderNo");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			//out.println(searchParameter);

			out.println("<HTML><HEAD><TITLE>CancelOrder</TITLE></HEAD>");
			out.println("<BODY>");

			Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94"); 
			stmt = conn.createStatement();

			// Class.forName("com.mysql.jdbc.Driver");
			// Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94");
			PreparedStatement ps = conn.prepareStatement("delete from CustomerOrders where randstring=?");
			ps.setString(1, searchParameter);
			ps.execute();

			//out.println(ps.toString());
			out.println("<h4>your order has been cancelled !!</h4>");
			out.println("<a href='welcomeLogin.html'>Home</a>");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}


